# Codefac Lite Context

Ultima actualizacion: 2026-10-28

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
Usar la version siguiente a la mas alta en todos los archivos SQL del proyecto (verificar con `grep -rhoE "VERSION_SISTEMA=[0-9.]+" workspace/recursos/src/main/resources/sql`). Ultima conocida: `1.4.1.5` (columna `FACTURA_DETALLE.ORDEN`, 2026-10-28).

**Importante:** la etiqueta sola no alcanza. `UtilidadesServidor.actualizarBaseDatos` (`workspace/servidor/.../util/UtilidadesServidor.java`) solo ejecuta un `@AGREGAR_COLUMNA`/`@AGREGAR_TABLA` si su `VERSION_SISTEMA` es `<= ParametrosSistemaCodefac.VERSION` (constante en `servidor-interfaz/.../info/ParametrosSistemaCodefac.java`). Si se agrega una columna con una version nueva pero no se sube esa constante al mismo valor (o mayor), el `ALTER TABLE` nunca corre, sin ningun error visible — el sintoma tipico es "columna no existe" mucho despues, en produccion.

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
- **Conversion Nota de Venta Interna (NVI) a Factura** (2026-10-23): se agrego la opcion "Convertir NVI en Factura" en el menu "Otras Opciones" de Facturacion (hook generico nuevo `GeneralPanelInterface.otrasOpciones()`, antes sin usar). Permite elegir una o varias NVI (multi-seleccion en el mismo buscador, `NotaVentaInternaBusqueda extends ProformaBusqueda`) y consolidarlas en una sola Factura nueva, o ir agregando una NVI a la vez a un borrador ya cargado (pregunta "Agregar / Limpiar y cargar nuevo / Cancelar"). Puntos clave:
  - `Factura.documentosOrigenAdicionales` (campo `@Transient`, no persistido) y el ya existente `Factura.proforma` (`PEDIDO_ID`) cargan, respectivamente, los origenes "adicionales" y el "principal" que hay que anular al grabar.
  - `FacturacionService.asignarProformaAFactura` recorre origen principal + adicionales y, si el origen es `NOTA_VENTA_INTERNA`, llama `revertirInventarioYCarteraFactura(...)` (extraido de `eliminarFactura`) antes de marcar el origen como `FACTURADO_PROFORMA` — evita duplicar el movimiento de kardex/cartera, ya que la Factura nueva va a generar el suyo propio al grabarse.
  - `Factura.agregarDetalleDesdeOtraFactura` ahora **clona** cada `FacturaDetalle` en vez de reutilizar la misma instancia — si se reutiliza, EclipseLink intenta re-persistir en cascada el detalle "robado" cuando se vuelve a grabar/mergear el documento origen, generando un INSERT fantasma de Factura sin secuencial (bug real encontrado y corregido). Este fix tambien beneficia la consolidacion de Comandas, que usa el mismo metodo.
  - `NotaVentaInternaBusqueda` tuvo que sobrescribir `getEstadoBuscar()` (nuevo metodo en `ProformaBusqueda`, antes hardcodeado a `AUTORIZADO`) porque la NVI se graba con estado `SIN_AUTORIZAR`, nunca `AUTORIZADO` — sin este override el buscador no encontraba ninguna NVI.
- **Caja: ventas "ya cobradas" (conversion NVI) no deben sumar al cierre pero si listarse** (2026-10-23): cada venta genera automaticamente una Cartera tipo "ABO" (abono/cruce automatico, ver `CarteraService.crearCrucesFactura`), y es esa Cartera la que via `grabarMovimientosCaja` crea el `IngresoCaja` real que alimenta `CajaSession.calcularValorCierreTeorico()` — **no** el codigo de `FacturacionService.agregarDatosParaCajaSession`, que esta comentado/muerto. Se agrego `IngresoCaja.informativo` (persistido) + `Cartera.informativoCaja` (`@Transient`, viaja de `CarteraService.crearCrucesFactura` a `grabarMovimientosCaja`), marcado cuando la Factura viene de convertir una NVI. `calcularValorCierreTeorico()` excluye esos movimientos de la suma; el reporte Jasper (`reporteCierreCaja.jrxml` A4 y `cierreCajaTicket.jrxml` ticket) los sigue listando pero con su propia proteccion: los `<variable calculation="Sum">` del `.jrxml` **no heredan** el filtro de Java (suman cada fila del dataset por su cuenta), asi que hubo que agregarles la misma condicion `$F{informativo}` ahi tambien — un total correcto en Java no garantiza que el total impreso en el reporte tambien lo sea. Ademas se encontro y corrigio `CajaSesionService.editar()` (usado al reabrir/corregir una sesion ya guardada), que tenia su **propia** suma manual duplicada y desactualizada, sin el filtro de efectivo/informativo/eliminado que si tiene `calcularValorCierreTeorico()` — se unifico para que use ese mismo metodo.
- Esta instalacion de desarrollo usa **Derby embebido** (`workspace/main/Derby2.DB`, credenciales `root`/`1234`), no la base MySQL `codefac` de `localhost:3306` que tambien existe pero esta vacia/sin usar. Derby solo admite una conexion a la vez: para consultar la base directamente hay que detener la app primero. Ver `C:\Users\CARLOS_CODESOFT\.claude\projects\...\memory\reference_maven_build.md` para el procedimiento completo de compilar/ejecutar/conectar.
- `GuiaRemisionWeb`/comprobante fisico de Guia de Remision: `ComprobanteDataGuiaRemision.java` tenia un bug donde `info.setRazonSocialTransportista(...)` leia `guiaRemision.getRazonSocial()` (el campo generico "cliente" heredado de `ComprobanteEntity`, usado por Factura/NotaCredito/etc.) en vez de `guiaRemision.getTransportista().getRazonSocial()` — el RIDE mostraba el nombre del destinatario duplicado en el bloque del transportista. Corregido 2026-10-14.
- **Cartera: formato de impresion Ticket** (2026-10-23): la pantalla de Cartera (`CarteraModel.imprimirCartera`) ahora pregunta primero "A4 / Ticket / Cancelar" (mismo patron que `CerrarCajaModel.generarReporteCaja`, via `DialogoCodefac.dialogoPreguntaPersonalizada`) antes de imprimir un Abono/Ingreso/Egreso. "A4" conserva el flujo Excel/PDF existente sin cambios (`DialogoCodefac.dialogoReporteOpciones`, compartido por 31+ pantallas — no se toco ese dialogo). Nuevo reporte `reportes/cartera/comprobante_ingreso_egreso_ticket.jrxml` (mismas dimensiones de pagina que `cierreCajaTicket.jrxml`: `pageWidth=180 columnWidth=170`), reutiliza `CarteraData`/`CarteraData.mapParametros` tal cual, sin clase de datos nueva. De paso se corrigio un bug real en `CarteraData.castCarteraData`: `valorCruzado` se seteaba con `cruce.getValor().toString()` sin `.setScale(2,...)` (a diferencia de `totalDocCruzado`/`saldoDocCruzado`, que si lo hacian) — mostraba hasta 6 decimales en ambos reportes (A4 y ticket).
- **Orden de detalles de Factura/Proforma** (2026-10-28): se agrego columna `FACTURA_DETALLE.ORDEN` (`BIGINT` nullable, version SQL `1.4.1.5`) porque antes el despliegue de detalles dependia del `ID` autoincremental, que no refleja el orden real en documentos editados en varias sesiones (sintoma reportado: detalles de una Proforma antigua salian "desordenados"). `Factura.addDetalle(detalle, inicio)` asigna `orden` **una sola vez**, solo si `detalle.getOrden()==null`: `maximo actual + 1` (caso normal) o `minimo actual - 1` si `inicio=true` (parametro `AGREGAR_DETALLE_INICIO`); una vez asignado nunca se vuelve a tocar. `Factura.detalles` usa `@OrderBy("orden ASC")`, asi que **cualquier** lectura de `getDetalles()` (EAGER, incluye el buscador de facturas) ya viene ordenada sin que cada pantalla tenga que acordarse de ordenar manualmente. `getDetallesOrdenados()` ademas tiene un fallback a ordenar por `ID` si algun detalle no tuviera `orden` (defensivo, no deberia pasar tras el backfill `UPDATE FACTURA_DETALLE SET ORDEN=ID WHERE ORDEN IS NULL` que se corrio al agregar la columna). Se agrego UI para corregir el orden a mano: botones "▲"/"▼" en `FacturacionPanel`/`FacturacionModel` (dentro de `jToolBar4`, junto a "Crear Producto"/"Agregar Producto" — **no** en el `jToolBar2` vacio que estaba al lado de la tabla, que al llenarlo con 2 botones quedaba reservando una columna/fila vacia rara por como esta armado el `GridBagLayout` de `PanelDetalleFactura`), que intercambian el `orden` entre la fila seleccionada y su vecina (`moverDetalleSeleccionado(desplazamiento)`). Como `FacturacionModel` es la base de `ProformaModel`, `LiquidacionCompraModel` y `ComandaModel`, el fix y los botones aplican a las cuatro pantallas.
- **Incidente: sustitucion del Derby2.DB de desarrollo** (2026-10-27/28, causa raiz no confirmada): durante una sesion de pruebas con relanzamientos rapidos de la app, el `Derby2.DB` activo paso de ~3674 a ~67975 filas en `FACTURA_DETALLE` de un relanzamiento a otro, perdiendo columnas ya agregadas (`ORDEN`, `INFORMATIVO`), y `key.codefac` aparecio regenerado con credenciales nuevas sin accion manual visible (candidato a investigar si se repite: el flujo de `Main.java` que crea credenciales cuando faltan, o la logica de comprobacion de integridad en `ArchivoComprobacionCodefac`). Sintoma fue que el buscador de facturas dejaba de mostrar resultados porque a esa base le faltaban columnas recientes. **Procedimiento de recuperacion que funciono:** bajar `version` en `workspace/main/codefac.ini` a un valor anterior al de la columna faltante y confirmar `forzar_actualizar=si` — el mecanismo existente (`UtilidadesServidor.actualizarBaseDatos`) reproduce todos los buckets de `@AGREGAR_COLUMNA`/`@AGREGAR_SCRIPT` intermedios; los que ya estaban aplicados tiran error de "columna/tabla ya existe" (`GRAVE: Query Error`, no fatal, el loop sigue) y los que faltaban se crean. Util como primer paso de diagnostico/recuperacion ante cualquier reporte de "no aparecen datos" que huela a columna faltante.

- **Pantalla "Venta Rapida"** (`workspace/facturacion/.../panel/VentaRapidaPanel.java` + `.../model/VentaRapidaModel.java`, modulo `facturacion`, registrada en `VentanaEnum.VENTA_RAPIDA`): grid de productos por categoria tipo POS (heladeria/cafeteria), pensada para cero friccion. No hereda `FacturacionPanel`/`FacturacionModel`: comparte el motor de negocio exclusivamente via `FacturaModelControlador` (`resolverYAgregarProducto`, `agregarDetallesFactura`, etc.), implementando `FacturaModelControlador.FacturaModelInterface` directamente. Componentes reutilizables nuevos en `controlador/.../componentes/`: `ProductoTileCard`, `WrapPanel`, `CarritoLineaPanel`, `CarritoListaPanel`, `IconosVentaRapida` (iconos vectoriales dibujados a mano para evitar glifos Unicode que salen como "tofu"/cuadro segun la fuente). (2026-11-20)
- **Precio de tarjeta de producto debe usar el mismo PVP/impuestos que la linea real** (2026-11-20): se agrego `FacturaModelControlador.obtenerPrecioVentaConImpuestos(Producto)`, que calcula `producto.buscarPrecioPorNombre(pvpDefecto)` + IVA/ICE — la MISMA fuente de precio que usa `agregarProductoVista` al agregar el producto de verdad. Antes, `VentaRapidaModel` tenia su propia formula simplificada (`calcularPrecioConIva`, eliminada) que siempre usaba `producto.getValorUnitario()` (PV1), ignorando el PVP del cliente activo — asi que el precio de la tarjeta podia no coincidir con el precio real de la linea si el cliente tenia una lista de precios distinta. Tambien se agrego la llamada faltante `controlador.seleccionarPvpDefecto(persona)` en `VentaRapidaModel.cargarCliente` (ya existia en `FacturacionModel.cargarCliente`, faltaba replicarla aqui). **Limitacion conocida y aceptada, no resuelta:** este calculo de tarjeta NO incluye descuentos de producto (`DescuentoServiceIf.consultarDescuentosPorProducto` es una consulta remota POR PRODUCTO; llamarla una vez por cada tarjeta del grid congelaria la pantalla al cambiar de categoria). Los descuentos si se aplican correctamente al momento de agregar el producto real (eso ya hacia una sola consulta, por producto puntual, sin cambios). Al 2026-11-20 la tabla `DESCUENTO` esta vacia en el ambiente de pruebas, asi que hoy no hay divergencia real, pero si se configuran descuentos de producto a futuro, la tarjeta podria mostrar un precio mayor al que termina cobrandose. Igual que en Facturacion, cambiar de cliente NO recalcula retroactivamente las lineas ya agregadas al pedido (solo afecta productos nuevos) — comportamiento heredado, no un bug nuevo de esta pantalla.
- **Pendiente de diseño: idempotencia de `FacturacionServiceIf.grabar(...)` ante corte de conexion (RMI)** (2026-11-20, aplica a Venta Rapida Y a Facturacion completa por igual, no es especifico de una pantalla): si la llamada RMI a `grabar(...)` lanza `RemoteException`, hoy no hay forma de saber si la venta ya quedo registrada en el servidor (el commit pudo completarse y solo se perdio la respuesta de vuelta) o si nunca llego a aplicarse. El codigo actual en `VentaRapidaModel.grabar()` distingue el mensaje (no invita a reintentar a ciegas en ese caso) pero **no implementa ninguna proteccion real** todavia. Diseno necesario para la siguiente etapa (no implementar con una consulta previa sin proteccion de concurrencia, eso no resuelve la carrera):
  1. Generar un identificador estable por operacion de venta en el cliente (ej. UUID) antes de llamar a `grabar(...)`, viajando como parte del payload de `Factura` o un parametro nuevo.
  2. En el servidor, `FacturacionService.grabar`/`grabarSinTransaccion` debe usar ese identificador con una restriccion UNICA en base de datos (o un chequeo transaccional atomico) para rechazar un segundo intento con el mismo identificador en vez de crear una venta duplicada.
  3. Si el cliente reintenta (mismo identificador) tras un corte, el servidor debe poder **devolver el resultado de la venta ya registrada** (no un error gen&eacute;rico de duplicado sin datos) para que la UI pueda limpiar el pedido y mostrar el comprobante correcto en vez de dejar al cajero sin saber que paso.
  4. Mientras no exista esto, el procedimiento manual ante un `RemoteException` en `grabar` es: NO reintentar de inmediato, verificar en el listado de ventas/caja si la venta ya aparece.

## Problemas recientes conocidos
- Hotspot de busqueda de cliente:
  - `workspace/controlador/src/main/java/ec/com/codesoft/codefaclite/controlador/aplicacion/dialog/busqueda/ClienteEstablecimientoBusquedaDialogo.java`
  - combinacion de `SELECT DISTINCT`, `LEFT JOIN u.persona.estudiantes e`, muchos `LOWER(...) LIKE` y `Persona.estudiantes` en `FetchType.EAGER`
- Riesgo de arranque Derby si ya existe otra instancia usando la base embebida. `Main` y `AbstractFacade` ya contienen manejo para errores tipicos `XSDB6` / `XJ040`.
- ~~Problemas de compilacion pueden venir de cache local Maven danada~~ **Desactualizado (2026-10-23):** el `mvn` del PATH del sistema es viejo (2.0.9/Java 1.7) y no sirve, pero el Maven embebido de NetBeans con JDK 1.8 **si compila sin problemas** el reactor completo (~45-60s, BUILD SUCCESS). Ver `reference_maven_build.md` en memoria para el comando exacto. Si un modulo editado no refleja un cambio aunque el build diga SUCCESS, usar `clean install` (no `install` solo) — Maven a veces reporta "Nothing to compile" sobre un archivo que si cambio.
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
