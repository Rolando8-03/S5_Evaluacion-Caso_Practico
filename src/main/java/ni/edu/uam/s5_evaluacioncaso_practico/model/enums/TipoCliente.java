package ni.edu.uam.s5_evaluacioncaso_practico.model.enums;

public enum TipoCliente {
    REGULAR("Regular"),
    PREFERENCIAL("Preferencial"),
    EMPRESARIAL("Empresarial");

    private final String nombre;

    TipoCliente(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}