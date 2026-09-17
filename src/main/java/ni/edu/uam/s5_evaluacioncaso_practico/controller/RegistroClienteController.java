package ni.edu.uam.s5_evaluacioncaso_practico.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
    private ToggleGroup grupoTipoSolicitud;

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

    private String rutaFotografia;

    @FXML
    private void initialize() {
        cbTipoCliente.setItems(FXCollections.observableArrayList(TipoCliente.values()));
        cbCiudad.setItems(FXCollections.observableArrayList(
                "Managua", "León", "Granada", "Masaya", "Estelí",
                "Matagalpa", "Chinandega", "Jinotega", "Rivas", "Carazo"
        ));

        rbInformacion.setUserData(TipoSolicitud.INFORMACION);
        rbSoporte.setUserData(TipoSolicitud.SOPORTE);
        rbContratacion.setUserData(TipoSolicitud.CONTRATACION);
    }

    @FXML
    private void seleccionarFotografia() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccionar fotografía");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        File carpetaTrabajo = ConfiguracionApp.getCarpetaTrabajo();
        if (carpetaTrabajo != null && carpetaTrabajo.isDirectory()) {
            chooser.setInitialDirectory(carpetaTrabajo);
        }

        Stage stage = (Stage) txtNombres.getScene().getWindow();
        File archivo = chooser.showOpenDialog(stage);

        if (archivo != null) {
            rutaFotografia = archivo.getAbsolutePath();
            imgFotografia.setImage(new Image(archivo.toURI().toString()));
            lblNombreFotografia.setText(archivo.getName());
        }
    }

    @FXML
    private void guardarCliente() {
        List<String> errores = validarFormulario();

        if (!errores.isEmpty()) {
            Alertas.advertencia("Datos incompletos", String.join("\n", errores));
            return;
        }

        Toggle seleccionSolicitud = grupoTipoSolicitud.getSelectedToggle();
        TipoSolicitud tipoSolicitud = (TipoSolicitud) seleccionSolicitud.getUserData();

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
        Alertas.informacion("Registro exitoso", "El cliente fue registrado correctamente.");
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
        lblNombreFotografia.setText("Sin fotografía seleccionada");
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
        }
        if (Validaciones.textoVacio(txtApellidos.getText())) {
            errores.add("• Ingrese los apellidos.");
        }
        if (cbTipoCliente.getValue() == null) {
            errores.add("• Seleccione el tipo de cliente.");
        }
        if (cbCiudad.getValue() == null) {
            errores.add("• Seleccione la ciudad.");
        }
        if (Validaciones.fechaNacimientoInvalida(dpFechaNacimiento.getValue())) {
            errores.add("• Seleccione una fecha de nacimiento válida.");
        }
        if (grupoTipoSolicitud.getSelectedToggle() == null) {
            errores.add("• Seleccione el tipo de solicitud.");
        }

        return errores;
    }

    private List<ServicioInteres> obtenerServiciosSeleccionados() {
        List<ServicioInteres> servicios = new ArrayList<>();

        if (chkAsesoria.isSelected()) {
            servicios.add(ServicioInteres.ASESORIA);
        }
        if (chkInstalacion.isSelected()) {
            servicios.add(ServicioInteres.INSTALACION);
        }
        if (chkSoporteTecnico.isSelected()) {
            servicios.add(ServicioInteres.SOPORTE_TECNICO);
        }
        if (chkMantenimiento.isSelected()) {
            servicios.add(ServicioInteres.MANTENIMIENTO);
        }

        return servicios;
    }
}
