package ni.edu.uam.s5_evaluacioncaso_practico.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Alertas;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Navegacion;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Validaciones;

public class InicioSesionController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private void iniciarSesion() {
        if (Validaciones.textoVacio(txtUsuario.getText()) ||
                Validaciones.textoVacio(txtContrasena.getText())) {
            Alertas.error("Datos incompletos", "Debe ingresar el usuario y la contraseña.");
            return;
        }

        Navegacion.abrirVentana("principal-view.fxml", "Sistema de Gestión de Solicitudes");
        Navegacion.cerrarVentana(txtUsuario);
    }

    @FXML
    private void salir() {
        if (Alertas.confirmar("Confirmar salida", "¿Desea cerrar la aplicación?")) {
            Platform.exit();
        }
    }

    @FXML
    private void manejarTeclado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            iniciarSesion();
        }
    }
}
