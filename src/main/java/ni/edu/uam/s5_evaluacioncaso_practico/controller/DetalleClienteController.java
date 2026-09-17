package ni.edu.uam.s5_evaluacioncaso_practico.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import ni.edu.uam.s5_evaluacioncaso_practico.model.Cliente;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Navegacion;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DetalleClienteController {

    @FXML
    private BorderPane rootPane;

    @FXML
    private ImageView imgFotografia;

    @FXML
    private Label lblEstadoFotografia;

    @FXML
    private Label lblNombreCompleto;

    @FXML
    private Label lblTipoCliente;

    @FXML
    private Label lblCiudad;

    @FXML
    private Label lblFechaNacimiento;

    @FXML
    private Label lblTipoSolicitud;

    @FXML
    private ListView<String> listaServicios;

    /*
     * Recibe el cliente seleccionado en la tabla y muestra
     * toda su información en la ventana de detalle.
     */
    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            return;
        }

        lblNombreCompleto.setText(
                cliente.getNombreCompleto()
        );

        lblTipoCliente.setText(
                cliente.getTipoCliente().toString()
        );

        lblCiudad.setText(
                cliente.getCiudad()
        );

        DateTimeFormatter formatoFecha =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        lblFechaNacimiento.setText(
                cliente.getFechaNacimiento().format(formatoFecha)
        );

        lblTipoSolicitud.setText(
                cliente.getTipoSolicitud().toString()
        );

        cargarServicios(cliente);
        cargarFotografia(cliente.getRutaFotografia());
    }

    /*
     * Los servicios elegidos durante el registro se muestran
     * en un ListView para facilitar su lectura.
     */
    private void cargarServicios(Cliente cliente) {
        List<String> servicios = cliente
                .getServiciosInteres()
                .stream()
                .map(Object::toString)
                .toList();

        if (servicios.isEmpty()) {
            listaServicios.setItems(
                    FXCollections.observableArrayList(
                            "Sin servicios seleccionados"
                    )
            );
            return;
        }

        listaServicios.setItems(
                FXCollections.observableArrayList(servicios)
        );
    }

    /*
     * La fotografía puede dejar de existir después de registrar
     * al cliente, por eso se comprueba antes de mostrarla.
     */
    private void cargarFotografia(String ruta) {
        if (ruta == null || ruta.isBlank()) {
            mostrarSinFotografia("Sin fotografía");
            return;
        }

        File archivo = new File(ruta);

        if (!archivo.exists() || !archivo.isFile()) {
            mostrarSinFotografia(
                    "Fotografía no disponible"
            );
            return;
        }

        try {
            Image imagen = new Image(
                    archivo.toURI().toString(),
                    false
            );

            if (imagen.isError()
                    || imagen.getWidth() <= 0
                    || imagen.getHeight() <= 0) {

                mostrarSinFotografia(
                        "Fotografía no disponible"
                );
                return;
            }

            imgFotografia.setImage(imagen);
            lblEstadoFotografia.setText(
                    archivo.getName()
            );

        } catch (Exception e) {
            mostrarSinFotografia(
                    "Fotografía no disponible"
            );
        }
    }

    private void mostrarSinFotografia(String mensaje) {
        imgFotografia.setImage(null);
        lblEstadoFotografia.setText(mensaje);
    }

    /*
     * Se solicita el foco para que la ventana pueda detectar
     * correctamente la tecla ESC.
     */
    public void enfocarVentana() {
        Platform.runLater(() -> {
            if (rootPane != null) {
                rootPane.requestFocus();
            }
        });
    }

    @FXML
    private void manejarTeclado(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            cerrar();
            event.consume();
        }
    }

    @FXML
    private void cerrar() {
        Navegacion.cerrarVentana(rootPane);
    }
}