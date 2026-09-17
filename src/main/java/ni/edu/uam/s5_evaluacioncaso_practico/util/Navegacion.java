package ni.edu.uam.s5_evaluacioncaso_practico.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class Navegacion {

    private static final String RUTA_VISTAS =
            "/ni/edu/uam/s5_evaluacioncaso_practico/view/";

    private static final String RUTA_CSS =
            "/ni/edu/uam/s5_evaluacioncaso_practico/css/styles.css";

    private Navegacion() {
    }

    public static Parent cargarVista(String archivoFxml) {
        try {
            return crearLoader(archivoFxml).load();

        } catch (IOException e) {
            throw new IllegalStateException(
                    "No se pudo cargar la vista: " + archivoFxml,
                    e
            );
        }
    }

    public static FXMLLoader crearLoader(String archivoFxml) {
        URL recurso = Navegacion.class.getResource(
                RUTA_VISTAS + archivoFxml
        );

        if (recurso == null) {
            throw new IllegalArgumentException(
                    "No existe la vista: " + archivoFxml
            );
        }

        return new FXMLLoader(recurso);
    }

    public static Scene crearEscena(Parent root) {
        Scene scene = new Scene(root);

        URL css = Navegacion.class.getResource(RUTA_CSS);

        if (css != null) {
            scene.getStylesheets().add(
                    css.toExternalForm()
            );
        }

        return scene;
    }

    public static Stage abrirVentana(
            String archivoFxml,
            String titulo
    ) {
        Parent root = cargarVista(archivoFxml);

        return mostrarVentana(
                root,
                titulo
        );
    }

    public static Stage mostrarVentana(
            Parent root,
            String titulo
    ) {
        Stage stage = new Stage();

        stage.setTitle(titulo);
        stage.setScene(crearEscena(root));

        /*
         * Las vistas fueron diseñadas con un tamaño definido en Scene Builder,
         * por eso se evita que puedan maximizarse o redimensionarse.
         */
        stage.setResizable(false);

        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();

        return stage;
    }

    public static void cerrarVentana(Node nodo) {
        if (nodo == null
                || nodo.getScene() == null
                || nodo.getScene().getWindow() == null) {
            return;
        }

        Stage stage =
                (Stage) nodo.getScene().getWindow();

        stage.close();
    }
}