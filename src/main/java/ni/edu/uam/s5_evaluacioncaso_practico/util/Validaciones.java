package ni.edu.uam.s5_evaluacioncaso_practico.util;

import java.time.LocalDate;

public final class Validaciones {

    private Validaciones() {
    }

    public static boolean textoVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    public static boolean nombreValido(String texto) {
        if (textoVacio(texto)) {
            return false;
        }

        String textoLimpio = texto.trim();

        if (textoLimpio.length() < 2 || textoLimpio.length() > 60) {
            return false;
        }

        // Permite letras, espacios, apóstrofes y guiones.
        // También acepta caracteres como á, é, í, ó, ú y ñ.
        return textoLimpio.matches(
                "[\\p{L}]+(?:[\\p{L}\\s'’-]*[\\p{L}])?"
        );
    }

    public static boolean fechaNacimientoInvalida(LocalDate fecha) {
        return fecha == null || fecha.isAfter(LocalDate.now());
    }
}