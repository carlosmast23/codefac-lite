# AGENTS.md

## Proposito
`codefac-lite` es un monorepo de Codefac Lite: un sistema modular para facturacion, inventario, CRM, cartera, compras, servicios, transporte, POS, restaurante, contabilidad y gestion academica. El flujo principal hoy sigue siendo la aplicacion de escritorio Swing, con un frente web adicional en JSF/PrimeFaces y un workspace Android separado.

## Regla de continuidad
Estos archivos son documentacion viva y deben mantenerse al dia sin esperar un pedido explicito del usuario:

- `AGENTS.md`
- `docs/codex-context.md`
- `docs/codex-handoff.md`
- `docs/ui-standards.md`

Actualizalos siempre que cambien de forma importante:

- la arquitectura o los modulos activos
- reglas de negocio
- estado operativo del sistema
- decisiones tecnicas relevantes
- hotspots de rendimiento o bugs repetitivos
- flujos UI/UX importantes

## Donde mirar primero
- Aggregator Maven: `workspace/mavenCodefacLite/pom.xml`
- Entrada escritorio: `workspace/main/src/main/java/ec/com/codesoft/codefaclite/main/init/Main.java`
- Persistencia JPA: `workspace/servidor/src/main/resources/META-INF/persistence.xml`
- Base de datos y facades: `workspace/servidor/src/main/java/ec/com/codesoft/codefaclite/servidor/facade/AbstractFacade.java`
- Contratos/entidades compartidas: `workspace/servidor-interfaz/src/main/java/...`
- Cliente RMI: `workspace/servidor-interfaz/src/main/java/ec/com/codesoft/codefaclite/servidorinterfaz/controller/ServiceFactory.java`
- Registro RMI del servidor: `workspace/servidor/src/main/java/ec/com/codesoft/codefaclite/servicios/controller/ControllerServiceUtil.java`
- Registro de pantallas en menu: `workspace/servidor-interfaz/src/main/java/ec/com/codesoft/codefaclite/servidorinterfaz/enumerados/VentanaEnum.java`
- Permisos de menu por perfil: `workspace/servidor/src/main/java/ec/com/codesoft/codefaclite/servidor/service/PerfilService.java`
- Shell principal Swing: `workspace/main/src/main/java/ec/com/codesoft/codefaclite/main/model/GeneralPanelModel.java`
- Web template: `workspace/app-web/codefacweb/src/main/webapp/template/codefac_template.xhtml`

## Mapa rapido del repo
- `workspace/`: codigo principal del sistema.
- `workspace/main`: bootstrap, shell de escritorio, arranque, modo cliente/servidor, actualizacion y licencias.
- `workspace/controlador`: controladores, dialogos, reportes, vistas y componentes compartidos.
- `workspace/coreCodefacLite`: utilidades de UI, bindings, validacion, componentes base.
- `workspace/servidor-interfaz`: entidades JPA, enums, contratos de servicio y objetos compartidos.
- `workspace/servidor`: implementacion de servicios, facades, persistencia y logica transaccional.
- `workspace/app-web`: aplicacion web JSF/PrimeFaces.
- `workspace_app`: app Android/Gradle.
- `workspace_movil`: workspace movil adicional/legado.

## Principios de codigo

- **Reutilizable antes que duplicado.** Cuando un bloque de logica aparece en dos sitios, o tiene probabilidad alta de aparecer, extraelo a un metodo en la capa correcta antes de que se duplique.
- **La capa correcta para logica de calculo/transformacion de entidades es `FacturaModelControlador` (y sus equivalentes por modulo), no el Model de la pantalla.** El Model orquesta UI; el Controlador encapsula operaciones sobre el grafo de entidades.
- **Patron establecido para invertir calculos de IVA sobre detalles:** usar `controlador.invertirCalculoDetallesAFactura(factura, agregarValorIva)` definido en `FacturaModelControlador`. Este metodo itera los detalles llamando `invertirCalculoNVIaFactura` + `calcularTotalesDetallesFactura` y luego `calcularTotalesDesdeDetalles`. No replicar ese loop directamente en el Model.
- **Cuando se modifica `ivaPorcentaje` en un detalle, siempre llamar `calcularTotalesDetallesFactura()` inmediatamente despues.** `calcularTotalesDesdeDetalles` en `Factura` lee `totalFinal` del detalle para derivar el IVA del comprobante; si `totalFinal` esta desactualizado el IVA total queda en cero.

## Reglas de trabajo recomendadas
- Prioriza `workspace/` salvo que la tarea apunte explicitamente a `workspace_app` o `workspace_movil`.
- Sigue el flujo completo antes de cambiar logica: `Panel/Form` -> `Model/Controlador` -> `ServiceFactory/ServiceIf` -> `servidor/service` -> entidad/persistencia.
- Cuando toques UI Swing generada por NetBeans, revisa y mantiene sincronizados el `.java` y el `.form`.
- Cuando toques comprobantes, valida tambien impacto en inventario/kardex, cartera, datos adicionales, impresion y comprobante electronico.
- Para rendimiento, mide en `run`, no en `debug`; en este proyecto el depurador distorsiona mucho las percepciones.
- No trates las carpetas `workspace/main/Derby2.DB*`, `codefac.jar`, `updater.jar`, `key.codefac*` y archivos similares como codigo fuente. Son datos/runtime y se deben tocar solo si la tarea lo pide.
- Respeta el target de cada modulo. La mayor parte del escritorio compila a Java 8, pero `workspace/app-web/codefacweb` sigue con `source/target 1.6`.

## Trampa de los 3 pasos al agregar una entidad nueva

Cada vez que se agrega una entidad JPA nueva con UI, hay tres puntos que NO son obvios y causan errores de arranque o de RMI si se olvidan:

1. **`persistence.xml`** — registrar `<class>...NuevaEntidad</class>`. Si falta, EclipseLink lanza `non-entity` al arrancar.
2. **`ControllerServiceUtil.java`** — agregar `mapRecursos.put(NuevaService.class, NuevaServiceIf.class)`. Si falta, el cliente lanza `NotBoundException` al intentar usar el servicio.
3. **`VentanaEnum.java` + `PerfilService.java`** — registrar la pantalla en el enum y asignarla a los perfiles. Si falta, la pantalla simplemente no aparece en el menu.

Ver checklist completo de 14 pasos en `docs/codex-context.md`.

## Comprobantes electronicos (SRI)

El XML de los comprobantes electronicos (factura, nota de credito, liquidacion de compra, guia de remision, retencion) se genera con **JAXB**, no con StringBuilder. Las clases estan en `workspace/facturacionElectronica/src/main/java/ec/com/codesoft/codefaclite/facturacionelectronica/jaxb/*` (ej. `InformacionFactura`, `InformacionNotaCredito`, `InformacionLiquidacionCompra`), son POJOs escritos a mano con `@XmlType(propOrder = {...})` + `@XmlElement`. **El orden de los tags en el XML final lo define solo el array `propOrder`**, no el orden de los metodos. El marshalling ocurre en `ComprobanteElectronicoService.generarXml`. Los XSD oficiales del SRI estan guardados como referencia en `recursos/sri/esquemasXsd/` y `recursos/sri/esquemasXml/`; antes de agregar un campo nuevo, confirmar ahi la posicion exacta y a que tipos de comprobante aplica.

`ComprobanteDataFactura.java` y `ComprobanteDataCompra.java` (en `workspace/servidor-interfaz/.../comprobantesElectronicos/`) tienen logica **duplicada**: ambas construyen `informacionComprobante` como `InformacionFactura` o `InformacionLiquidacionCompra` segun el tipo de documento. Cualquier campo que dependa del tipo concreto (ej. `placa`, `moneda`) hay que setearlo con `instanceof` en **los dos archivos**, no solo en uno.

## Hotspots conocidos al retomar
- Busqueda de clientes/establecimientos en modo academico: `ClienteEstablecimientoBusquedaDialogo` puede volverse lenta por `LEFT JOIN` a estudiantes, `DISTINCT`, `LOWER(...) LIKE` y `FetchType.EAGER` en `Persona.estudiantes`.
- Facturacion/nota de credito: los cambios parciales deben validar devolucion de inventario y reactivacion de `KardexItemEspecifico`.
- Proformas sin IVA: el parametro `PROFORMA_GENERAR_SIN_IVA` controla nuevas proformas y la factura guarda `PROFORMA_SIN_IVA`; al convertir una proforma marcada sin IVA a factura se reutiliza `FacturaDetalle.invertirCalculoNVIaFactura(true)` para restaurar IVA como base + impuesto. La restauracion debe ejecutarse tambien en `cargarFacturaDesdeProforma(...)`, no depender solo del listener del combo de documento.
- Pantallas con combos string-based: hay varios valores de negocio guardados como texto plano y no como enum persistido.
- Maven puede fallar por metadata/cache local incluso cuando el repo esta bien; si pasa, diferenciar problema del workspace vs. problema del codigo.

## Checklist breve al retomar
1. Leer `docs/codex-context.md`.
2. Leer `docs/codex-handoff.md`.
3. Si la tarea toca UI o web, leer `docs/ui-standards.md`.
4. Abrir los archivos del flujo afectado y confirmar si hay `.form`, entidad, servicio e impacto transaccional.
