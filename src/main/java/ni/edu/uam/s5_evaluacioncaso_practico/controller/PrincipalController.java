package ni.edu.uam.s5_evaluacioncaso_practico.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Alertas;
import ni.edu.uam.s5_evaluacioncaso_practico.util.ConfiguracionApp;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Navegacion;

import java.io.File;

public class PrincipalController {

    @FXML
    private Label lblCarpetaTrabajo;

    @FXML
    private void abrirRegistro() {
        Navegacion.abrirVentana("registro-cliente-view.fxml", "Registro de cliente");
    }

    @FXML
    private void abrirConsulta() {
        Navegacion.abrirVentana("consulta-clientes-view.fxml", "Consulta de clientes");
    }

    @FXML
    private void seleccionarCarpetaTrabajo() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Seleccionar carpeta de trabajo");

        File carpetaActual = ConfiguracionApp.getCarpetaTrabajo();
        if (carpetaActual != null && carpetaActual.isDirectory()) {
            chooser.setInitialDirectory(carpetaActual);
        }

        Stage stage = (Stage) lblCarpetaTrabajo.getScene().getWindow();
        File carpeta = chooser.showDialog(stage);

        if (carpeta != null) {
            ConfiguracionApp.setCarpetaTrabajo(carpeta);
            lblCarpetaTrabajo.setText(carpeta.getAbsolutePath());
            Alertas.informacion("Carpeta seleccionada",
                    "La carpeta de trabajo fue configurada correctamente.");
        }
    }

    @FXML
    private void mostrarAcercaDe() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Acerca del sistema");
        dialog.setHeaderText("Sistema de Gestión de Solicitudes");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        VBox contenido = new VBox(8);
        contenido.getChildren().addAll(
                new Label("Asignatura: Programación de Aplicaciones de Escritorio"),
                new Label("Caso práctico: Eventos, navegación y paso de datos"),
                new Label("Integrantes: Rolando, Mauro, Aris y Dylan")
        );

        dialog.getDialogPane().setContent(contenido);
        dialog.initOwner(lblCarpetaTrabajo.getScene().getWindow());
        dialog.showAndWait();
    }

    @FXML
    private void salir() {
        if (Alertas.confirmar("Confirmar salida", "¿Desea cerrar la aplicación?")) {
            Platform.exit();
        }
    }
}
