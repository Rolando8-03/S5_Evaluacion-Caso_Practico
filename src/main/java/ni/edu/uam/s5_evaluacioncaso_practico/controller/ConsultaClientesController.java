package ni.edu.uam.s5_evaluacioncaso_practico.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ni.edu.uam.s5_evaluacioncaso_practico.data.ClienteStore;
import ni.edu.uam.s5_evaluacioncaso_practico.model.Cliente;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Navegacion;

public class ConsultaClientesController {

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTipo;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, String> colSolicitud;
    @FXML private Label lblContador;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));

        tablaClientes.setItems(ClienteStore.obtenerClientes());
        actualizarContador();

        tablaClientes.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, actual) -> actualizarContador());

        MenuItem verDetalle = new MenuItem("Ver detalle");
        verDetalle.setOnAction(event -> abrirDetalle());
        ContextMenu menu = new ContextMenu(verDetalle);
        tablaClientes.setContextMenu(menu);
    }

    @FXML
    private void manejarMouse(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            abrirDetalle();
        }
    }

    private void abrirDetalle() {
        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            return;
        }

        var loader = Navegacion.crearLoader("detalle-cliente-view.fxml");
        javafx.scene.Parent root;
        try {
            root = loader.load();
        } catch (java.io.IOException e) {
            throw new IllegalStateException("No se pudo cargar el detalle del cliente.", e);
        }
        DetalleClienteController controller = loader.getController();
        controller.setCliente(cliente);
        Stage stage = Navegacion.mostrarVentana(root, "Detalle del cliente");
        stage.setOnShown(e -> controller.enfocarVentana());
    }

    @FXML
    private void cerrar() {
        Navegacion.cerrarVentana(tablaClientes);
    }

    private void actualizarContador() {
        int cantidad = ClienteStore.obtenerClientes().size();
        lblContador.setText("Clientes registrados: " + cantidad);
    }
}
