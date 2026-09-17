package ni.edu.uam.s5_evaluacioncaso_practico.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ni.edu.uam.s5_evaluacioncaso_practico.data.ClienteStore;
import ni.edu.uam.s5_evaluacioncaso_practico.model.Cliente;
import ni.edu.uam.s5_evaluacioncaso_practico.model.enums.ServicioInteres;
import ni.edu.uam.s5_evaluacioncaso_practico.model.enums.TipoCliente;
import ni.edu.uam.s5_evaluacioncaso_practico.model.enums.TipoSolicitud;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Alertas;
import ni.edu.uam.s5_evaluacioncaso_practico.util.ConfiguracionApp;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Navegacion;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Validaciones;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistroClienteController {

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtApellidos;

    @FXML
    private ComboBox<TipoCliente> cbTipoCliente;

    @FXML
    private ComboBox<String> cbCiudad;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private RadioButton rbInformacion;

    @FXML
    private RadioButton rbSoporte;

    @FXML
    private RadioButton rbContratacion;

    @FXML
    private CheckBox chkAsesoria;

    @FXML
    private CheckBox chkInstalacion;

    @FXML
    private CheckBox chkSoporteTecnico;

    @FXML
    private CheckBox chkMantenimiento;

    @FXML
    private ImageView imgFotografia;

    @FXML
    private Label lblNombreFotografia;

    /*
     * El grupo se crea directamente en el controlador para no depender
     * de la inyección del ToggleGroup desde el archivo FXML.
     */
    private final ToggleGroup grupoTipoSolicitud = new ToggleGroup();

    private String rutaFotografia;

    @FXML
    private void initialize() {
        cargarOpcionesFormulario();
        configurarTiposSolicitud();
        configurarFechaNacimiento();
    }

    private void cargarOpcionesFormulario() {
        cbTipoCliente.setItems(
                FXCollections.observableArrayList(TipoCliente.values())
        );

        cbCiudad.setItems(
                FXCollections.observableArrayList(
                        "Managua",
                        "León",
                        "Granada",
                        "Masaya",
                        "Estelí",
                        "Matagalpa",
                        "Chinandega",
                        "Jinotega",
                        "Rivas",
                        "Carazo"
                )
        );
    }

    private void configurarTiposSolicitud() {
        rbInformacion.setToggleGroup(grupoTipoSolicitud);
        rbSoporte.setToggleGroup(grupoTipoSolicitud);
        rbContratacion.setToggleGroup(grupoTipoSolicitud);

        /*
         * Guardamos el enum dentro de cada RadioButton para recuperar
         * directamente el tipo seleccionado desde el ToggleGroup.
         */
        rbInformacion.setUserData(TipoSolicitud.INFORMACION);
        rbSoporte.setUserData(TipoSolicitud.SOPORTE);
        rbContratacion.setUserData(TipoSolicitud.CONTRATACION);
    }

    private void configurarFechaNacimiento() {
        /*
         * Se evita la edición manual para que la fecha siempre provenga
         * del calendario y sea más fácil controlar valores inválidos.
         */
        dpFechaNacimiento.setEditable(false);

        dpFechaNacimiento.setDayCellFactory(datePicker ->
                new DateCell() {

                    @Override
                    public void updateItem(LocalDate fecha, boolean empty) {
                        super.updateItem(fecha, empty);

                        if (empty || fecha == null) {
                            return;
                        }

                        // Una fecha de nacimiento no puede estar en el futuro.
                        setDisable(fecha.isAfter(LocalDate.now()));
                    }
                }
        );
    }

    @FXML
    private void seleccionarFotografia() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccionar fotografía");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Imágenes",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"
                )
        );

        /*
         * La carpeta configurada solamente se utiliza como ubicación inicial.
         * El usuario puede navegar hacia otra carpeta desde el FileChooser.
         */
        File carpetaTrabajo = ConfiguracionApp.getCarpetaTrabajo();

        if (carpetaTrabajo != null && carpetaTrabajo.isDirectory()) {
            chooser.setInitialDirectory(carpetaTrabajo);
        }

        Stage stage = (Stage) txtNombres
                .getScene()
                .getWindow();

        File archivo = chooser.showOpenDialog(stage);

        if (archivo == null) {
            return;
        }

        try {
            Image imagen = new Image(
                    archivo.toURI().toString(),
                    false
            );

            if (imagen.isError()
                    || imagen.getWidth() <= 0
                    || imagen.getHeight() <= 0) {

                Alertas.error(
                        "Imagen no válida",
                        "No se pudo cargar la fotografía seleccionada."
                );
                return;
            }

            imgFotografia.setImage(imagen);
            rutaFotografia = archivo.getAbsolutePath();
            lblNombreFotografia.setText(archivo.getName());

        } catch (Exception e) {
            rutaFotografia = null;
            imgFotografia.setImage(null);
            lblNombreFotografia.setText(
                    "Sin fotografía seleccionada"
            );

            Alertas.error(
                    "Imagen no válida",
                    "No se pudo cargar la fotografía seleccionada."
            );
        }
    }

    @FXML
    private void guardarCliente() {
        List<String> errores = validarFormulario();

        if (!errores.isEmpty()) {
            Alertas.advertencia(
                    "Datos inválidos",
                    String.join("\n", errores)
            );
            return;
        }

        Toggle seleccionSolicitud =
                grupoTipoSolicitud.getSelectedToggle();

        // Esta comprobación adicional evita continuar si el grupo
        // llegara a quedar sin selección por algún cambio en la interfaz.
        if (seleccionSolicitud == null) {
            Alertas.advertencia(
                    "Datos incompletos",
                    "Seleccione el tipo de solicitud."
            );
            return;
        }

        TipoSolicitud tipoSolicitud =
                (TipoSolicitud) seleccionSolicitud.getUserData();

        Cliente cliente = new Cliente(
                txtNombres.getText().trim(),
                txtApellidos.getText().trim(),
                cbTipoCliente.getValue(),
                cbCiudad.getValue(),
                dpFechaNacimiento.getValue(),
                tipoSolicitud,
                obtenerServiciosSeleccionados(),
                rutaFotografia
        );

        ClienteStore.agregarCliente(cliente);

        Alertas.informacion(
                "Registro exitoso",
                "El cliente fue registrado correctamente."
        );

        limpiarFormulario();
    }

    @FXML
    private void limpiarFormulario() {
        txtNombres.clear();
        txtApellidos.clear();

        cbTipoCliente.getSelectionModel().clearSelection();
        cbCiudad.getSelectionModel().clearSelection();

        dpFechaNacimiento.setValue(null);

        grupoTipoSolicitud.selectToggle(null);

        chkAsesoria.setSelected(false);
        chkInstalacion.setSelected(false);
        chkSoporteTecnico.setSelected(false);
        chkMantenimiento.setSelected(false);

        imgFotografia.setImage(null);

        lblNombreFotografia.setText(
                "Sin fotografía seleccionada"
        );

        rutaFotografia = null;

        txtNombres.requestFocus();
    }

    @FXML
    private void cancelar() {
        Navegacion.cerrarVentana(txtNombres);
    }

    private List<String> validarFormulario() {
        List<String> errores = new ArrayList<>();

        if (Validaciones.textoVacio(txtNombres.getText())) {
            errores.add("• Ingrese los nombres.");
        } else if (!Validaciones.nombreValido(
                txtNombres.getText())) {

            errores.add(
                    "• Los nombres solamente pueden contener letras."
            );
        }

        if (Validaciones.textoVacio(txtApellidos.getText())) {
            errores.add("• Ingrese los apellidos.");
        } else if (!Validaciones.nombreValido(
                txtApellidos.getText())) {

            errores.add(
                    "• Los apellidos solamente pueden contener letras."
            );
        }

        if (cbTipoCliente.getValue() == null) {
            errores.add(
                    "• Seleccione el tipo de cliente."
            );
        }

        if (cbCiudad.getValue() == null) {
            errores.add(
                    "• Seleccione la ciudad."
            );
        }

        if (dpFechaNacimiento.getValue() == null) {
            errores.add(
                    "• Seleccione la fecha de nacimiento."
            );
        } else if (Validaciones.fechaNacimientoInvalida(
                dpFechaNacimiento.getValue())) {

            errores.add(
                    "• La fecha de nacimiento no puede ser futura."
            );
        }

        if (grupoTipoSolicitud.getSelectedToggle() == null) {
            errores.add(
                    "• Seleccione el tipo de solicitud."
            );
        }

        return errores;
    }

    private List<ServicioInteres> obtenerServiciosSeleccionados() {
        List<ServicioInteres> servicios =
                new ArrayList<>();

        if (chkAsesoria.isSelected()) {
            servicios.add(
                    ServicioInteres.ASESORIA
            );
        }

        if (chkInstalacion.isSelected()) {
            servicios.add(
                    ServicioInteres.INSTALACION
            );
        }

        if (chkSoporteTecnico.isSelected()) {
            servicios.add(
                    ServicioInteres.SOPORTE_TECNICO
            );
        }

        if (chkMantenimiento.isSelected()) {
            servicios.add(
                    ServicioInteres.MANTENIMIENTO
            );
        }

        return servicios;
    }
}