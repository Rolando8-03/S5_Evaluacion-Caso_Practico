package ni.edu.uam.s5_evaluacioncaso_practico.model.enums;

public enum TipoSolicitud {
    INFORMACION("Información"),
    SOPORTE("Soporte"),
    CONTRATACION("Contratación");

    private final String nombre;

    TipoSolicitud(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
