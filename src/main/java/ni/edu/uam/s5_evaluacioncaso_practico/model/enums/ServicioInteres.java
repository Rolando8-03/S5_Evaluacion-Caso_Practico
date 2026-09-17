package ni.edu.uam.s5_evaluacioncaso_practico.model.enums;

public enum ServicioInteres {
    ASESORIA("Asesoría"),
    INSTALACION("Instalación"),
    SOPORTE_TECNICO("Soporte técnico"),
    MANTENIMIENTO("Mantenimiento");

    private final String nombre;

    ServicioInteres(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}