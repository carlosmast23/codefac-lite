# UI Standards

Ultima actualizacion: 2026-04-22

Este proyecto si tiene interfaz y ademas conviven dos capas UI:

- escritorio Swing con formularios NetBeans
- web JSF/PrimeFaces

El estandar aqui no es reinventar la experiencia, sino mantener consistencia con el producto existente y proteger la velocidad operativa del usuario.

## Principios UX del proyecto
- Priorizar captura rapida y operacion transaccional sobre estetica experimental.
- Mantener etiquetas y flujos familiares para usuarios administrativos.
- No romper atajos, orden visual o secuencia de digitacion sin una razon fuerte.
- Toda mejora UI debe respetar que muchas pantallas son de alto volumen de uso diario.

## Estandares para Swing
### Base tecnica
- La UI de escritorio usa NetBeans GUI Builder; muchas pantallas tienen pareja `.java` + `.form`.
- Si cambias estructura, items de combos o propiedades visuales generadas, actualiza ambos archivos.
- Manten el patron actual de modelos/controladores del proyecto; no incrustes logica compleja directamente en widgets si ya existe un `Model`/`Controlador`.

### Consistencia visual
- Respetar fuentes, iconos y estilo general ya usados por el sistema.
- Reutilizar recursos existentes de `img/iconos/...` antes de introducir nuevos.
- No cambiar el look & feel global sin revisar `Main.java` y los temas JTattoo configurados.

### Interaccion
- Mantener botones y acciones donde el usuario ya los espera:
  nuevo, grabar, buscar, eliminar, imprimir.
- En formularios extensos, conservar el orden de tabulacion y de digitacion.
- Evitar dialogos nuevos si el flujo ya tiene busqueda, edicion o panel lateral establecidos.

### Datos y validacion
- Usar los bindings/annotations ya existentes cuando apliquen:
  `@ButtonBinding`, `@ComboBoxBinding`, `@ComponenteSecundarioAnotacion`, validaciones de `coreCodefacLite`.
- No asumir que un combo siempre devuelve un tipo fuerte si el dato persistido realmente es un alias string.
- Mostrar mensajes de validacion concretos y accionables; seguir el estilo de `DialogoCodefac` y `CodefacMsj`.

### Rendimiento percibido
- Evitar cargas innecesarias en pantallas de busqueda.
- Si una mejora es de rendimiento, validar en modo `run`; `debug` en este proyecto agrega mucha sobrecarga.
- Si una pantalla depende de relaciones pesadas o datos academicos, revisar joins y fetch antes de culpar a la UI.

## Estandares para web JSF/PrimeFaces
### Base tecnica
- Extender templates existentes, especialmente:
  - `workspace/app-web/codefacweb/src/main/webapp/template/codefac_template.xhtml`
  - `workspace/app-web/codefacweb/src/main/webapp/template/principal_template.xhtml`
- Reutilizar CSS existente:
  - `workspace/app-web/codefacweb/src/main/webapp/resources/css/estilo.css`
  - `workspace/app-web/codefacweb/src/main/webapp/resources/css/plantilla_editada.css`
- Mantener el stack actual JSF/PrimeFaces; no introducir React/Vue/u otro frontend sin una decision explicita de arquitectura.

### Composicion visual
- Seguir el patron actual de:
  toolbar superior, panel de contenido, growl, confirm dialogs y paneles colapsables.
- Usar PrimeFlex y grids existentes para layout responsivo.
- Reutilizar iconos y botones del sistema antes de crear variantes nuevas.

### Comportamiento
- Las acciones principales deben seguir la semantica ya usada por `controllerCodefacMb`.
- Los dialogos de confirmacion de grabar/eliminar deben mantenerse consistentes con la plantilla actual.
- Si agregas nuevas vistas, intenta encajarlas dentro del shell existente en lugar de crear paginas aisladas con comportamiento diferente.

## Estandares de copy y etiquetas
- Preferir texto claro y operacional, en espanol, alineado con la terminologia actual del sistema.
- No renombrar conceptos de negocio ya conocidos por soporte y usuarios salvo necesidad real.
- Si agregas una nueva opcion en un combo, verifica:
  - ubicacion logica dentro del listado
  - persistencia/lectura del valor
  - impacto en reportes, filtros o logica derivada

## Checklist UI antes de cerrar una tarea
1. Verificar si hay `.form` asociado.
2. Confirmar que el cambio no rompe foco, navegacion ni acciones principales.
3. Revisar persistencia real del dato nuevo o editado.
4. Validar mensajes y labels visibles.
5. Si es web, revisar template/CSS existente antes de agregar estilos inline o duplicados.
6. Si el cambio altera comportamiento importante, actualizar tambien `docs/codex-handoff.md`.
