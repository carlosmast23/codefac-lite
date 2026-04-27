# Codex Handoff

Ultima actualizacion: 2026-04-27

## Estado actual
- Monorepo principal activo en `workspace/`.
- Flujo dominante: escritorio Swing.
- Web disponible en `workspace/app-web/codefacweb`.
- Persistencia principal observada en Derby embebido, con soporte MySQL en `AbstractFacade`.
- Worktree local venia con cambios recientes en facturacion; al retomar conviene revisar `git status` antes de tocar pantallas de factura.

## Ultimas decisiones importantes
- Se agrego entidad `Variante` al modulo de inventario (2026-04-27): talla + color, vinculada a `Kardex` con campo `VARIANTE_ID` nullable (retrocompatible). Version SQL `1.4.0.6`. El flujo completo toco 14 archivos — ver checklist en `docs/codex-context.md`.
- Se confirmo que al agregar cualquier entidad nueva hay tres puntos criticos fuera del flujo obvio: `persistence.xml`, `ControllerServiceUtil.java` (registro RMI) y `VentanaEnum.java` + `PerfilService.java` (menu). Omitir cualquiera causa errores silenciosos o de arranque.
- Se dejo documentado que los archivos de continuidad deben mantenerse de forma proactiva:
  - `AGENTS.md`
  - `docs/codex-context.md`
  - `docs/codex-handoff.md`
  - `docs/ui-standards.md`
- En CRM, el `Pvp Por Defecto` debe resolverse por alias persistido y no asumir un cast directo a `Producto.PrecioVenta`.
- En nota de credito parcial, el retorno de inventario depende de copiar correctamente metadata del detalle de factura original y de reactivar items especificos cuando aplique.
- En facturacion, se agrego la opcion `Cliente Recurrente` en `Origen de Venta`; al tocar ese combo hay que mantener `FacturacionPanel.java` y `FacturacionPanel.form` sincronizados.

## Problemas recientes conocidos
- Consulta lenta de cliente/establecimiento:
  - archivo: `workspace/controlador/.../ClienteEstablecimientoBusquedaDialogo.java`
  - sintomas: lentitud sobre todo en modo academico
  - causas probables: `DISTINCT`, `LEFT JOIN` a estudiantes, `LOWER(...) LIKE`, `FetchType.EAGER` en `Persona.estudiantes`
  - nota practica: en `debug` se percibe mucho peor que en `run`
- Nota de credito parcial:
  - revisar siempre si el kardex sube stock del item correcto, si conserva lote/presentacion y si reabre `KardexItemEspecifico` cuando corresponde
- Arranque/persistencia:
  - Derby embebido puede fallar si otra instancia esta usando la base
  - compilacion Maven puede fallar por metadata local danada aunque el codigo este bien

## Proximos focos recomendados
- Optimizar la busqueda de cliente:
  - probar reemplazo de `LEFT JOIN + DISTINCT` por `EXISTS`
  - revisar indices Derby en `PERSONA_ESTABLECIMIENTO`, `PERSONA` y `ESTUDIANTE`
  - evaluar si `Persona.estudiantes` puede dejar de ser `EAGER`
- Reducir dependencia de strings magicos en combos y parametros persistidos.
- Crear o reforzar pruebas manuales/regresion para:
  - nota de credito parcial
  - origen de venta
  - cliente con `PVP_DEFECTO`

## Archivos recomendados para revisar primero al retomar
- `workspace/main/src/main/java/ec/com/codesoft/codefaclite/main/init/Main.java`
- `workspace/main/src/main/java/ec/com/codesoft/codefaclite/main/model/GeneralPanelModel.java`
- `workspace/servidor/src/main/java/ec/com/codesoft/codefaclite/servidor/facade/AbstractFacade.java`
- `workspace/servidor/src/main/java/ec/com/codesoft/codefaclite/servicios/controller/ControllerServiceUtil.java`
- `workspace/servidor-interfaz/src/main/java/ec/com/codesoft/codefaclite/servidorinterfaz/enumerados/VentanaEnum.java`
- `workspace/servidor/src/main/java/ec/com/codesoft/codefaclite/servidor/service/PerfilService.java`
- `workspace/servidor-interfaz/src/main/java/ec/com/codesoft/codefaclite/servidorinterfaz/entity/Persona.java`
- `workspace/crm/src/main/java/ec/com/codesoft/codefaclite/crm/model/ClienteModel.java`
- `workspace/facturacion/src/main/java/ec/com/codesoft/codefaclite/facturacion/panel/FacturacionPanel.java`
- `workspace/controlador/src/main/java/ec/com/codesoft/codefaclite/controlador/vista/factura/NotaCreditoModelControlador.java`
- `workspace/servidor/src/main/java/ec/com/codesoft/codefaclite/servidor/service/NotaCreditoService.java`
- `workspace/controlador/src/main/java/ec/com/codesoft/codefaclite/controlador/aplicacion/dialog/busqueda/ClienteEstablecimientoBusquedaDialogo.java`

## Forma recomendada de retomar trabajo
1. Confirmar `git status`.
2. Identificar el modulo funcional afectado.
3. Abrir el flujo completo:
   `Panel/Form` -> `Model/Controlador` -> `ServiceIf/ServiceFactory` -> `servidor/service` -> entidad/persistencia.
4. Si la tarea afecta comprobantes, revisar impactos secundarios:
   kardex, cartera, datos adicionales, impresion y comprobante electronico.
5. Si la tarea afecta UI, revisar tambien `docs/ui-standards.md`.

## Notas de continuidad para futuras conversaciones
- Si se introduce un nuevo modulo, cambio de stack, cambio fuerte de reglas de negocio o cambio operativo, actualizar primero este handoff y luego el contexto general.
- Si se resuelve el hotspot de busqueda o se estabiliza una solucion para nota de credito parcial, dejar aqui el resultado y la ruta exacta del cambio.
