package ni.edu.uam.s5_evaluacioncaso_practico.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ni.edu.uam.s5_evaluacioncaso_practico.data.ClienteStore;
import ni.edu.uam.s5_evaluacioncaso_practico.model.Cliente;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Alertas;
import ni.edu.uam.s5_evaluacioncaso_practico.util.Navegacion;

import java.io.IOException;
import java.time.LocalDate;

public class ConsultaClientesController {

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, String> colNombreCompleto;

    @FXML
    private TableColumn<Cliente, String> colTipoCliente;

    @FXML
    private TableColumn<Cliente, String> colCiudad;

    @FXML
    private TableColumn<Cliente, LocalDate> colFechaNacimiento;

    @FXML
    private TableColumn<Cliente, String> colTipoSolicitud;

    @FXML
    private Label lblCantidadClientes;

    @FXML
    private void initialize() {
        configurarColumnas();

        /*
         * Se utiliza directamente la lista compartida para conservar
         * los registros aunque esta ventana se cierre y vuelva a abrir.
         */
        tablaClientes.setItems(
                ClienteStore.obtenerClientes()
        );

        actualizarCantidad();

        ClienteStore.obtenerClientes().addListener(
                (javafx.collections.ListChangeListener<Cliente>) cambio ->
                        actualizarCantidad()
        );
    }

    private void configurarColumnas() {
        colNombreCompleto.setCellValueFactory(datos ->
                new ReadOnlyStringWrapper(
                        datos.getValue().getNombreCompleto()
                )
        );

        colTipoCliente.setCellValueFactory(datos ->
                new ReadOnlyStringWrapper(
                        datos.getValue()
                                .getTipoCliente()
                                .toString()
                )
        );

        colCiudad.setCellValueFactory(datos ->
                new ReadOnlyStringWrapper(
                        datos.getValue().getCiudad()
                )
        );

        colFechaNacimiento.setCellValueFactory(datos ->
                new ReadOnlyObjectWrapper<>(
                        datos.getValue()
                                .getFechaNacimiento()
                )
        );

        colTipoSolicitud.setCellValueFactory(datos ->
                new ReadOnlyStringWrapper(
                        datos.getValue()
                                .getTipoSolicitud()
                                .toString()
                )
        );
    }

    @FXML
    private void manejarDobleClic(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY
                && event.getClickCount() == 2) {

            verDetalleSeleccionado();
        }
    }

    @FXML
    private void verDetalleSeleccionado() {
        Cliente cliente = tablaClientes
                .getSelectionModel()
                .getSelectedItem();

        if (cliente == null) {
            Alertas.advertencia(
                    "Sin selección",
                    "Seleccione un cliente para ver su detalle."
            );
            return;
        }

        abrirDetalle(cliente);
    }

    private void abrirDetalle(Cliente cliente) {
        try {
            FXMLLoader loader =
                    Navegacion.crearLoader(
                            "detalle-cliente-view.fxml"
                    );

            /*
             * load() debe ejecutarse antes de obtener el controlador.
             * En este momento JavaFX crea los controles e inyecta los fx:id.
             */
            Parent root = loader.load();

            DetalleClienteController controller =
                    loader.getController();

            controller.setCliente(cliente);

            Navegacion.mostrarVentana(
                    root,
                    "Detalle del cliente"
            );

            controller.enfocarVentana();

        } catch (IOException e) {
            Alertas.error(
                    "Error de navegación",
                    "No fue posible abrir el detalle del cliente."
            );
        }
    }

    @FXML
    private void cerrar() {
        Navegacion.cerrarVentana(tablaClientes);
    }

    private void actualizarCantidad() {
        lblCantidadClientes.setText(
                "Clientes registrados: "
                        + ClienteStore
                        .obtenerClientes()
                        .size()
        );
    }
}