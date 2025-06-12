package dao;

import java.util.List;
import model.ClienteOtaku;

/**
 * Interfaz que define las operaciones CRUD para la gestión de clientes en Akihabara Market.
 * 
 * Contiene los métodos necesarios para interactuar con la base de datos de clientes.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public interface ClienteDAOInterface {

    /**
     * Inserta un nuevo cliente en la base de datos.
     * 
     * @param cliente Cliente a agregar.
     */
    void agregarCliente(ClienteOtaku cliente);

    /**
     * Busca un cliente por su ID.
     * 
     * @param id Identificador del cliente.
     * @return ClienteOtaku si se encuentra, o null si no existe.
     */
    ClienteOtaku obtenerClientePorId(int id);

    /**
     * Devuelve una lista con todos los clientes registrados.
     * 
     * @return Lista de objetos ClienteOtaku.
     */
    List<ClienteOtaku> obtenerTodosLosClientes();

    /**
     * Actualiza los datos de un cliente existente.
     * 
     * @param cliente Objeto con los datos actualizados.
     * @return true si se actualizó correctamente, false si hubo error.
     */
    boolean actualizarCliente(ClienteOtaku cliente);

    /**
     * Elimina un cliente de la base de datos a partir de su ID.
     * 
     * @param id Identificador del cliente.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    boolean eliminarCliente(int id);

    /**
     * Busca un cliente por su correo electrónico.
     * 
     * @param email Email del cliente.
     * @return ClienteOtaku si se encuentra, o null si no existe.
     */
    ClienteOtaku buscarPorEmail(String email);
}
