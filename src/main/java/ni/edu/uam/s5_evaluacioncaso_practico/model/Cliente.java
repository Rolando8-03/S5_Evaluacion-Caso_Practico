package ni.edu.uam.s5_evaluacioncaso_practico.model;

import ni.edu.uam.s5_evaluacioncaso_practico.model.enums.ServicioInteres;
import ni.edu.uam.s5_evaluacioncaso_practico.model.enums.TipoCliente;
import ni.edu.uam.s5_evaluacioncaso_practico.model.enums.TipoSolicitud;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private int id;
    private String nombres;
    private String apellidos;
    private TipoCliente tipoCliente;
    private String ciudad;
    private LocalDate fechaNacimiento;
    private TipoSolicitud tipoSolicitud;
    private List<ServicioInteres> serviciosInteres;
    private String rutaFotografia;

    public Cliente(String nombres,
                   String apellidos,
                   TipoCliente tipoCliente,
                   String ciudad,
                   LocalDate fechaNacimiento,
                   TipoSolicitud tipoSolicitud,
                   List<ServicioInteres> serviciosInteres,
                   String rutaFotografia) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoCliente = tipoCliente;
        this.ciudad = ciudad;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSolicitud = tipoSolicitud;
        this.serviciosInteres = new ArrayList<>(serviciosInteres);
        this.rutaFotografia = rutaFotografia;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public TipoSolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public List<ServicioInteres> getServiciosInteres() {
        return new ArrayList<>(serviciosInteres);
    }

    public void setServiciosInteres(List<ServicioInteres> serviciosInteres) {
        this.serviciosInteres = new ArrayList<>(serviciosInteres);
    }

    public String getRutaFotografia() {
        return rutaFotografia;
    }

    public void setRutaFotografia(String rutaFotografia) {
        this.rutaFotografia = rutaFotografia;
    }
}
