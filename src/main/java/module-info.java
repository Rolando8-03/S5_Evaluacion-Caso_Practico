module ni.edu.uam.s5_evaluacioncaso_practico {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.s5_evaluacioncaso_practico to javafx.fxml;
    exports ni.edu.uam.s5_evaluacioncaso_practico;
    exports ni.edu.uam.s5_evaluacioncaso_practico.app;
    opens ni.edu.uam.s5_evaluacioncaso_practico.app to javafx.fxml;
}