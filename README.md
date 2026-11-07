# Examen Final — App de pendientes

Aplicación Android en Kotlin para registrar pendientes sin internet ni cuenta de usuario.

## Entregables

- [APK de depuración para instalar en Android](entrega/ExamenFinal.apk).
- Proyecto completo de Android Studio en la raíz de este repositorio.
- Historial de commits por funcionalidad.

## Funcionalidades

- Pantalla principal con ListView y adaptador personalizado: descripción y estado de cada tarea.
- Pantalla para agregar tareas con validación de descripción vacía.
- Pantalla de detalle con descripción completa, estado y fecha de creación.
- Navegación mediante Intents explícitos; el identificador de la tarea se pasa con putExtra.
- Al guardar una tarea se regresa a la lista y se muestra la tarea nueva.
- Marcar y desmarcar tareas con checkbox; las completadas cambian de color y se tachan.
- Eliminar tareas con confirmación.
- Persistencia local con SharedPreferences y JSON, sin backend ni permiso de internet.

## Abrir y ejecutar

1. Clonar este repositorio o descargarlo como ZIP.
2. En Android Studio, elegir **File > Open** y seleccionar la carpeta del proyecto.
3. Permitir la sincronización de Gradle e instalar el SDK solicitado si es necesario.
4. Seleccionar un emulador o teléfono Android (Android 7.0/API 24 o posterior) y pulsar **Run**.

La sincronización inicial puede necesitar internet para descargar herramientas; la aplicación funciona sin conexión. El archivo local.properties depende de la instalación local y no se incluye en Git.

## Código

Los archivos principales están en app/src/main/java/com/example/examenfinaldesweb/:

- MainActivity.kt: lista, adaptador, completar y eliminar.
- AddTaskActivity.kt: formulario y validación.
- TaskDetailActivity.kt: detalle y cambio de estado.
- TaskStore.kt: modelo y persistencia.
- TaskActivity.kt: componentes visuales compartidos.

## Verificación realizada

- assembleDebug: correcto.
- testDebugUnitTest: correcto.
- lintDebug: correcto, sin errores (con advertencias).
- TaskFlowTest: correcto en emulador Android, mediante AndroidJUnitRunner. Verifica descripción vacía, creación, navegación al detalle, completar, lectura del almacenamiento y eliminación.

