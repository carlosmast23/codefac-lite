# Codefac Lite Context

Ultima actualizacion: 2026-05-13

## Proposito del proyecto
Codefac Lite es una plataforma modular orientada a operacion comercial y administrativa. El codigo evidencia un nucleo fuerte de:

- facturacion y comprobantes electronicos
- CRM y mantenimiento de clientes/proveedores
- inventario y kardex
- cartera y credito
- compras
- servicios/taller/mantenimiento
- transporte
- POS y restaurante
- gestion academica
- contabilidad, impuestos y prestamos

El sistema se usa principalmente como aplicacion de escritorio Swing con modo local o cliente-servidor, y ademas mantiene un frente web JSF/PrimeFaces.

## Stack principal
- Lenguaje principal: Java
- Build principal: Maven
- Parent aggregator: `workspace/mavenCodefacLite/pom.xml`
- Escritorio: Swing + NetBeans GUI Builder (`.form`) + SwingX + JCalendar + JTattoo + JavaFX embebido
- Web: JSF 2.2 + PrimeFaces 7 + PrimeFlex + WAR Maven
- Persistencia: JPA/EclipseLink
- Base de datos soportada en codigo: Derby embebido por defecto y MySQL opcional
- Integracion entre cliente y servidor: RMI via `ServiceFactory`
- Reporteria: JasperReports
- Documentos electronicos: modulo `facturacionElectronica` con generacion/firma/autorizacion SRI

## Restricciones tecnicas relevantes
- La mayor parte del escritorio compila con `source/target 1.8`.
- `workspace/app-web/codefacweb` compila con `source/target 1.6`; no asumir sintaxis moderna alli.
- El proyecto mezcla librerias antiguas y dependencias mas nuevas de Jakarta; evitar refactors transversales grandes sin revisar compatibilidad modulo por modulo.
- Hay repositorios locales tipo `lib/` en algunos modulos por herencia de NetBeans y dependencias historicas.

## Arquitectura operativa
### Entrada de escritorio
`workspace/main/src/main/java/ec/com/codesoft/codefaclite/main/init/Main.java`

Este arranque:

- configura propiedades Derby
- carga tema/look & feel
- valida licencias y actualizaciones
- resuelve modo de inicio: cliente, servidor o cliente-servidor
- inicializa persistencia y/o cliente RMI segun contexto

### Capa compartida de entidades y contratos
`workspace/servidor-interfaz`

Contiene:

- entidades JPA
- enums de negocio
- contratos `ServiceIf`
- objetos de sesion y parametros del sistema

### Persistencia y servicios
`workspace/servidor`

Puntos clave:

- `META-INF/persistence.xml`: unidad `pu_ejemplo`
- `AbstractFacade`: inicializa `EntityManagerFactory`, selecciona Derby/MySQL, helpers de consultas y dialogos
- `service/`: logica transaccional y casos de uso de servidor

### Shell y controladores de UI
- `workspace/main`: shell, login, navegacion, monitor, actualizaciones
- `workspace/controlador`: modelos/controladores compartidos, dialogos, reportes, componentes y vistas base
- `workspace/coreCodefacLite`: validacion, bindings, helpers UI y componentes reutilizables

### Modulos funcionales principales
- `workspace/crm`: clientes, proveedores, empresa, rutas, zonas
- `workspace/facturacion`: factura, proforma, nota de credito, reportes y pantallas de facturacion
- `workspace/inventario`: productos, bodegas, stock, kardex, variantes de producto
- `workspace/cartera`: credito, abonos, cruces
- `workspace/compra`: compras y ordenes
- `workspace/servicios`: taller, mantenimientos, vehiculos
- `workspace/gestionAcademica`: estudiantes, rubros, niveles y flujos academicos
- `workspace/pos`, `workspace/restaurante`, `workspace/transporte`, `workspace/contabilidad`, `workspace/impuestos`, `workspace/prestamos`

### Frente web
`workspace/app-web`

Submodulos:

- `codefacweb`: WAR JSF/PrimeFaces
- `recursosWeb`: recursos compartidos para web

Templates y estilos base:

- `workspace/app-web/codefacweb/src/main/webapp/template/codefac_template.xhtml`
- `workspace/app-web/codefacweb/src/main/webapp/resources/css/estilo.css`

## Reglas de negocio clave observables en codigo
- `Persona` se persiste sobre la tabla `CLIENTE` y representa el operador comercial con variantes de cliente/proveedor segun `tipo`.
- La identificacion de consumidor final esta fija como `9999999999999`.
- `Persona` puede tener multiples `PersonaEstablecimiento`; la direccion/telefono/correo por defecto suelen salir del primer establecimiento activo.
- `Persona` guarda `PVP_DEFECTO` como alias string, no como enum persistido.
- El proyecto maneja datos por empresa, pero algunas busquedas pueden omitir ese filtro si el parametro `DATOS_COMPARTIDOS_EMPRESA` esta en `SI`.
- Las notas de credito referenciadas a factura deben replicar suficiente metadata del detalle original para devolver inventario correctamente.
- Guardar comprobantes no termina en la entidad: tambien puede disparar cartera, kardex, impresion y procesamiento de comprobante electronico.

## Estado actual del sistema
- El workspace principal activo es `workspace/`.
- La app de escritorio es el flujo dominante y tiene un runtime local con muchas bases Derby bajo `workspace/main/Derby2.DB*`.
- Existe un frente web funcional pero secundario frente al escritorio.
- Tambien existe `workspace_app` como app Android/Gradle y `workspace_movil/codefacMobil` como workspace movil adicional.

## Checklist para agregar una entidad nueva con UI

Al crear una entidad JPA con CRUD completo en este sistema se deben tocar exactamente estos 14 archivos. Omitir cualquiera causa errores silenciosos o de arranque.

| # | Modulo | Archivo | Que hacer |
|---|--------|---------|-----------|
| 1 | servidor-interfaz | `entity/XxxEntity.java` | Entidad JPA: @Entity, @Table, campos, getters, equals/hashCode |
| 2 | recursos/sql | `create_xxx.sql` o `create_kardex.sql` | `CREATE TABLE` con `@AGREGAR_TABLA(VERSION_SISTEMA=X)` |
| 3 | servidor | `META-INF/persistence.xml` | `<class>...XxxEntity</class>` — sin esto EclipseLink lanza non-entity al arrancar |
| 4 | servidor-interfaz | `servicios/XxxServiceIf.java` | Interface extends ServiceAbstractIf<T> |
| 5 | servidor | `facade/XxxFacade.java` | extends AbstractFacade<T> |
| 6 | servidor | `service/XxxService.java` | implements XxxServiceIf, logica con ejecutarTransaccion/ejecutarConsulta |
| 7 | servidor | `ControllerServiceUtil.java` | `mapRecursos.put(XxxService.class, XxxServiceIf.class)` — sin esto: NotBoundException RMI |
| 8 | servidor-interfaz | `controller/ServiceFactory.java` | import + metodo `getXxxServiceIf()` |
| 9 | controlador | `busqueda/XxxBusqueda.java` | implements InterfaceModelFind<T>, columnas y query JPQL |
| 10 | controlador | `vista/inventario/XxxControlador.java` | extends ModelControladorAbstract, CRUD, interfaces CommonIf/SwingIf/WebIf |
| 11 | inventario | `panel/XxxPanel.java` + `XxxPanel.form` | abstract class extends ControladorCodefacInterface, @TextFieldBinding |
| 12 | inventario | `model/XxxModel.java` | extends XxxPanel, implements ControladorVistaIf + XxxControlador.SwingIf |
| 13 | servidor-interfaz | `enumerados/VentanaEnum.java` | entrada con clase, codigo 4 letras unico, modulo y categoria |
| 14 | servidor | `service/PerfilService.java` | agregar VentanaEnum.XXX en perfiles relevantes |

### Versiones SQL
Usar la version siguiente a la mas alta en todos los archivos SQL del proyecto. Ultima conocida al crear Variante: `1.3.1.3.8`; se uso `1.4.0.6`.

### Columna opcional en tabla existente
Agregar dentro del `CREATE TABLE` existente con comentario `/*@AGREGAR_COLUMNA(VERSION_SISTEMA=X)*/` en la linea anterior al campo. El campo debe ser nullable para retrocompatibilidad.

## Decisiones y aprendizajes recientes importantes
- Se agrego la entidad `Variante` al modulo de inventario (2026-04-27). Permite asociar talla y color a un producto y vincular ese par Producto+Variante directamente en el Kardex (campo `VARIANTE_ID` nullable para retrocompatibilidad). Archivos clave: `Variante.java`, `VarianteService`, `VarianteControlador`, `VariantePanel/Model`, `VarianteBusqueda`. Version SQL usada: `1.4.0.6`.
- El nombre estandar de productos con variante es `Producto [talla X color Y]`, construido con `Variante.construirNombreConVariante(...)` o `Kardex.obtenerDescripcionConVariante(...)`. Ya se usa en la busqueda de facturacion, en el detalle cargado de factura y en `StockReporteModel`; no se amplio el filtro de busqueda por talla/color.
- Al editar productos, las variantes deben sincronizarse con kardex: una variante activa asegura/crea su kardex por bodega, y una variante eliminada desactiva logicamente (`estado=ELIMINADO`) los kardex producto+variante relacionados. Si se reactiva/agrega de nuevo, el kardex eliminado se vuelve a activar.
- En cliente/CRM, el `PVP_DEFECTO` debe tratarse como alias persistido. La UI no debe asumir que el combo devuelve siempre `Producto.PrecioVenta`; puede venir como string persistido.
- En nota de credito parcial sobre factura, para afectar inventario correctamente hay que preservar y reutilizar metadata de inventario del detalle original: presentacion, lote, `kardexId` e item especifico.
- En la pantalla de facturacion, los cambios sobre combos declarados por GUI Builder deben reflejarse tanto en `FacturacionPanel.java` como en `FacturacionPanel.form`.
- Proformas sin IVA: `ParametroCodefac.PROFORMA_GENERAR_SIN_IVA` permite generar nuevas proformas con detalles IVA 0 aunque el producto grave IVA; la marca historica se guarda en `Factura.PROFORMA_SIN_IVA`. Al convertir una proforma marcada sin IVA a factura se restaura IVA con `FacturaDetalle.invertirCalculoNVIaFactura(true)` para obtener base + IVA. Esta restauracion se dispara desde `cargarFacturaDesdeProforma(...)` cuando el destino explicito es factura, porque los flujos de carga directa pueden saltarse el listener del combo de documento.
- Visor de reportes Jasper: `ParametroCodefac.VISUALIZADOR_REPORTE` permite alternar entre `VisualizadorReporteEnum.PERSONALIZADO_CODEFAC` y `ESTANDAR_JASPER`. El valor por defecto es personalizado para preservar el comportamiento existente; si el boton pequeno de impresora del visor personalizado da problemas, cambiarlo en Configuraciones por Defecto -> Ventas -> Visualizador de Reportes a `Estandar Jasper`.
- Para evaluar consultas o UX pesada, `debug` no es una referencia fiable de rendimiento en este proyecto.

## Problemas recientes conocidos
- Hotspot de busqueda de cliente:
  - `workspace/controlador/src/main/java/ec/com/codesoft/codefaclite/controlador/aplicacion/dialog/busqueda/ClienteEstablecimientoBusquedaDialogo.java`
  - combinacion de `SELECT DISTINCT`, `LEFT JOIN u.persona.estudiantes e`, muchos `LOWER(...) LIKE` y `Persona.estudiantes` en `FetchType.EAGER`
- Riesgo de arranque Derby si ya existe otra instancia usando la base embebida. `Main` y `AbstractFacade` ya contienen manejo para errores tipicos `XSDB6` / `XJ040`.
- Problemas de compilacion pueden venir de cache local Maven danada, no necesariamente del repo. Estado actual: no intentar validar con Maven hasta limpiar/reparar metadata local en `.m2` (`maven-metadata-local.xml` de snapshots como `mavenCodefacLite` y `utilidades`); Maven falla antes de compilar codigo en compiles por modulo. En el reactor completo tambien puede fallar al recompilar con `javac: invalid target release: 1.8` por la configuracion local del JDK/Maven.
- El uso de valores string de negocio en combos y parametros sigue siendo una fuente de fragilidad.

## Proximos focos recomendados
- Revisar la busqueda de cliente/establecimiento para reducir costo en Derby:
  - considerar `EXISTS` en vez de `LEFT JOIN + DISTINCT`
  - evaluar `LAZY` en relaciones academicas si no rompe pantallas
  - revisar indices Derby por tablas mas usadas
- Reducir strings magicos en catalogos UI persistidos como texto.
- Seguir endureciendo escenarios de anulacion parcial de factura y retorno de inventario.
- Diferenciar mejor codigo fuente vs. datos/runtime dentro de `workspace/main`.

## Archivos que se deben revisar primero al retomar
- `workspace/mavenCodefacLite/pom.xml`
- `workspace/main/src/main/java/ec/com/codesoft/codefaclite/main/init/Main.java`
- `workspace/main/src/main/java/ec/com/codesoft/codefaclite/main/model/GeneralPanelModel.java`
- `workspace/servidor/src/main/resources/META-INF/persistence.xml`
- `workspace/servidor/src/main/java/ec/com/codesoft/codefaclite/servidor/facade/AbstractFacade.java`
- `workspace/servidor-interfaz/src/main/java/ec/com/codesoft/codefaclite/servidorinterfaz/controller/ServiceFactory.java`
- `workspace/servidor/src/main/java/ec/com/codesoft/codefaclite/servicios/controller/ControllerServiceUtil.java`
- `workspace/servidor-interfaz/src/main/java/ec/com/codesoft/codefaclite/servidorinterfaz/enumerados/VentanaEnum.java`
- `workspace/servidor/src/main/java/ec/com/codesoft/codefaclite/servidor/service/PerfilService.java`
- `workspace/servidor-interfaz/src/main/java/ec/com/codesoft/codefaclite/servidorinterfaz/entity/Persona.java`
- `workspace/controlador/src/main/java/ec/com/codesoft/codefaclite/controlador/aplicacion/dialog/busqueda/ClienteEstablecimientoBusquedaDialogo.java`
- `workspace/facturacion/src/main/java/ec/com/codesoft/codefaclite/facturacion/panel/FacturacionPanel.java`
- `workspace/controlador/src/main/java/ec/com/codesoft/codefaclite/controlador/vista/factura/NotaCreditoModelControlador.java`
- `workspace/servidor/src/main/java/ec/com/codesoft/codefaclite/servidor/service/NotaCreditoService.java`
- `workspace/app-web/codefacweb/src/main/webapp/template/codefac_template.xhtml`

## Criterio de trabajo recomendado
- Trabajar por flujo completo, no solo por pantalla.
- Confirmar siempre:
  - modulo UI afectado
  - entidad persistida
  - servicio remoto/local implicado
  - impacto en inventario, cartera, impresion o comprobante electronico
- Si tocas formularios Swing generados, validar `.java` y `.form`.
- Si tocas web, respetar template/base CSS existentes y no introducir otro stack frontend.
- Si detectas cambios importantes de arquitectura, negocio o estado operativo, actualizar este documento y los demas archivos de continuidad en el mismo turno.
