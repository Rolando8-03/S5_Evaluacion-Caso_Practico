module ni.edu.uam.s5_evaluacioncaso_practico {
    requires javafx.controls;
    requires javafx.fxml;

    opens ni.edu.uam.s5_evaluacioncaso_practico.controller to javafx.fxml;

    exports ni.edu.uam.s5_evaluacioncaso_practico.app;
    exports ni.edu.uam.s5_evaluacioncaso_practico.model;
    exports ni.edu.uam.s5_evaluacioncaso_practico.model.enums;
}
