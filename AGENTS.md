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

## Reglas de trabajo recomendadas
- Prioriza `workspace/` salvo que la tarea apunte explicitamente a `workspace_app` o `workspace_movil`.
- Sigue el flujo completo antes de cambiar logica: `Panel/Form` -> `Model/Controlador` -> `ServiceFactory/ServiceIf` -> `servidor/service` -> entidad/persistencia.
- Cuando toques UI Swing generada por NetBeans, revisa y mantiene sincronizados el `.java` y el `.form`.
- Cuando toques comprobantes, valida tambien impacto en inventario/kardex, cartera, datos adicionales, impresion y comprobante electronico.
- Para rendimiento, mide en `run`, no en `debug`; en este proyecto el depurador distorsiona mucho las percepciones.
- No trates las carpetas `workspace/main/Derby2.DB*`, `codefac.jar`, `updater.jar`, `key.codefac*` y archivos similares como codigo fuente. Son datos/runtime y se deben tocar solo si la tarea lo pide.
- Respeta el target de cada modulo. La mayor parte del escritorio compila a Java 8, pero `workspace/app-web/codefacweb` sigue con `source/target 1.6`.

## Hotspots conocidos al retomar
- Busqueda de clientes/establecimientos en modo academico: `ClienteEstablecimientoBusquedaDialogo` puede volverse lenta por `LEFT JOIN` a estudiantes, `DISTINCT`, `LOWER(...) LIKE` y `FetchType.EAGER` en `Persona.estudiantes`.
- Facturacion/nota de credito: los cambios parciales deben validar devolucion de inventario y reactivacion de `KardexItemEspecifico`.
- Pantallas con combos string-based: hay varios valores de negocio guardados como texto plano y no como enum persistido.
- Maven puede fallar por metadata/cache local incluso cuando el repo esta bien; si pasa, diferenciar problema del workspace vs. problema del codigo.

## Checklist breve al retomar
1. Leer `docs/codex-context.md`.
2. Leer `docs/codex-handoff.md`.
3. Si la tarea toca UI o web, leer `docs/ui-standards.md`.
4. Abrir los archivos del flujo afectado y confirmar si hay `.form`, entidad, servicio e impacto transaccional.
