package ni.edu.uam.s5_evaluacioncaso_practico.util;

import java.time.LocalDate;

public final class Validaciones {

    private Validaciones() {
    }

    public static boolean textoVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    public static boolean fechaNacimientoInvalida(LocalDate fecha) {
        return fecha == null || fecha.isAfter(LocalDate.now());
    }
}
