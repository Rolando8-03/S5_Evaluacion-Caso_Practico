# S5_Evaluación-Caso_Práctico

## CNU0525 - Programación de Aplicaciones de Escritorio - Grupo 2

Aplicación JavaFX para el registro y consulta de solicitudes de clientes. El proyecto demuestra controles JavaFX, eventos, menús, barras de herramientas, diálogos, selección de archivos y carpetas, navegación entre formularios y paso de datos.

## Integrantes y ramas

| Integrante | Rama | Responsabilidad principal |
|---|---|---|
| Rolando Mayorga| `Rolando_Acceso` | Configuración, arranque, inicio de sesión, alertas y documentación |
| Mauro Delgado| `Mauro_Registro` | Modelo, enums, almacenamiento temporal, registro y validaciones |
| Aris Gavarrete| `Aris_Consulta` | Consulta, TableView, ContextMenu, MouseEvent, detalle y paso de datos |
| Dylan Mora| `Dylan_Navegacion` | Ventana principal, navegación, MenuBar, ToolBar, Dialog, DirectoryChooser y CSS |

## Flujo de la aplicación

1. Inicio de sesión.
2. Ventana principal.
3. Registro de clientes.
4. Los clientes se almacenan temporalmente en `ClienteStore` mediante una `ObservableList`.
5. La ventana de consulta utiliza la misma lista, por lo que los registros permanecen disponibles aunque se cierre y vuelva a abrir una ventana mientras la aplicación continúe ejecutándose.
6. Un doble clic o la opción `Ver detalle` del ContextMenu envía el cliente seleccionado a la ventana de detalle.

## Eventos implementados

- `ActionEvent`: botones, opciones de menú, guardar, limpiar y navegación.
- `MouseEvent`: doble clic sobre un registro del TableView.
- `KeyEvent`: ENTER en el inicio de sesión y ESC en la ventana de detalle.

## Componentes requeridos

- Label
- Button
- TextField
- PasswordField
- ComboBox
- ListView
- CheckBox
- RadioButton
- ToggleGroup
- TableView
- DatePicker
- ImageView
- MenuBar
- ToolBar
- ContextMenu
- Alert de información
- Alert de advertencia/error
- Alert de confirmación
- Dialog
- FileChooser
- DirectoryChooser
