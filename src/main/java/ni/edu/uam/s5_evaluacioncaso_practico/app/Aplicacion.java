package ni.edu.uam.s5_evaluacioncaso_practico.app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Navegacion;

public class Aplicacion extends Application {

    @Override
    public void start(Stage stage) {
        Parent root = Navegacion.cargarVista(
                "inicio-sesion-view.fxml"
        );

        stage.setTitle(
                "Sistema de Gestión de Solicitudes"
        );

        stage.setScene(
                Navegacion.crearEscena(root)
        );

        /*
         * El diseño tiene un tamaño definido, por lo que no es necesario
         * permitir que la ventana se maximice o se redimensione.
         */
        stage.setResizable(false);

        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}