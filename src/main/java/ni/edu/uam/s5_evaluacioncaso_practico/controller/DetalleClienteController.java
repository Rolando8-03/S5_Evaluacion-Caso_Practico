package ni.edu.uam.s5_evaluacioncaso_practico.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ni.edu.uam.s5_evaluacioncaso_practico.model.Cliente;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Navegacion;

import java.io.File;

public class DetalleClienteController {

    @FXML private VBox root;
    @FXML private Label lblId;
    @FXML private Label lblNombre;
    @FXML private Label lblTipoCliente;
    @FXML private Label lblCiudad;
    @FXML private Label lblFechaNacimiento;
    @FXML private Label lblTipoSolicitud;
    @FXML private Label lblFotografia;
    @FXML private ImageView imgFotografia;
    @FXML private ListView<String> listaServicios;

    private Cliente cliente;

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        mostrarDatos();
    }

    private void mostrarDatos() {
        if (cliente == null) return;

        lblId.setText(String.valueOf(cliente.getId()));
        lblNombre.setText(cliente.getNombreCompleto());
        lblTipoCliente.setText(String.valueOf(cliente.getTipoCliente()));
        lblCiudad.setText(cliente.getCiudad());
        lblFechaNacimiento.setText(String.valueOf(cliente.getFechaNacimiento()));
        lblTipoSolicitud.setText(String.valueOf(cliente.getTipoSolicitud()));
        listaServicios.setItems(FXCollections.observableArrayList(
                cliente.getServiciosInteres().stream().map(String::valueOf).toList()
        ));

        String ruta = cliente.getRutaFotografia();
        if (ruta != null && !ruta.isBlank() && new File(ruta).isFile()) {
            imgFotografia.setImage(new Image(new File(ruta).toURI().toString()));
            lblFotografia.setText(new File(ruta).getName());
        } else {
            imgFotografia.setImage(null);
            lblFotografia.setText("Sin fotografía");
        }
    }

    @FXML
    private void manejarTeclado(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            cerrar();
        }
    }

    @FXML
    private void cerrar() {
        Navegacion.cerrarVentana(root);
    }

    public void enfocarVentana() {
        root.requestFocus();
    }
}
