package ni.edu.uam.s5_evaluacioncaso_practico.util;

import java.io.File;

public final class ConfiguracionApp {

    private static File carpetaTrabajo;

    private ConfiguracionApp() {
    }

    public static File getCarpetaTrabajo() {
        return carpetaTrabajo;
    }

    public static void setCarpetaTrabajo(File carpetaTrabajo) {
        ConfiguracionApp.carpetaTrabajo = carpetaTrabajo;
    }
}
