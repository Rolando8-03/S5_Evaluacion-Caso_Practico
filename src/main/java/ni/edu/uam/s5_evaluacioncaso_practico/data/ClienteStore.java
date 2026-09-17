package ni.edu.uam.s5_evaluacioncaso_practico.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ni.edu.uam.s5_evaluacioncaso_practico.model.Cliente;

public final class ClienteStore {

    private static final ObservableList<Cliente> CLIENTES = FXCollections.observableArrayList();
    private static int siguienteId = 1;

    private ClienteStore() {
    }

    public static void agregarCliente(Cliente cliente) {
        cliente.setId(siguienteId++);
        CLIENTES.add(cliente);
    }

    public static ObservableList<Cliente> obtenerClientes() {
        return CLIENTES;
    }
}
