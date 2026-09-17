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

import java.util.Map;

public class InicioSesionController {

    /*
     * Como esta práctica no utiliza base de datos, los usuarios se mantienen
     * temporalmente en memoria. Esto permite validar que el usuario realmente
     * exista sin agregar tecnologías que todavía no se han estudiado.
     */
    private static final Map<String, String> USUARIOS = Map.of(
            "admin", "1234"
    );

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText();

        if (Validaciones.textoVacio(usuario)
                || Validaciones.textoVacio(contrasena)) {

            Alertas.error(
                    "Datos incompletos",
                    "Debe ingresar el usuario y la contraseña."
            );
            return;
        }

        String contrasenaRegistrada = USUARIOS.get(usuario);

        if (contrasenaRegistrada == null
                || !contrasenaRegistrada.equals(contrasena)) {

            Alertas.error(
                    "Acceso denegado",
                    "El usuario o la contraseña son incorrectos."
            );

            txtContrasena.clear();
            txtContrasena.requestFocus();
            return;
        }

        Navegacion.abrirVentana(
                "principal-view.fxml",
                "Sistema de Gestión de Solicitudes"
        );

        Navegacion.cerrarVentana(txtUsuario);
    }

    @FXML
    private void salir() {
        if (Alertas.confirmar(
                "Confirmar salida",
                "¿Desea cerrar la aplicación?"
        )) {
            Platform.exit();
        }
    }

    @FXML
    private void manejarTeclado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            iniciarSesion();
            event.consume();
        }
    }
}