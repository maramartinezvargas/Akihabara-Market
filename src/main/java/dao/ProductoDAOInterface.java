package dao;

import java.util.List;
import model.ProductoOtaku;

/**
 * Interfaz que define las operaciones CRUD básicas para productos en Akihabara Market.
 * 
 * Cualquier clase que gestione productos en la base de datos debe implementar esta interfaz.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public interface ProductoDAOInterface {

    /**
     * Inserta un nuevo producto en la base de datos.
     * 
     * @param producto Producto que se desea añadir.
     */
    void agregarProducto(ProductoOtaku producto);

    /**
     * Recupera un producto a partir de su ID.
     * 
     * @param id Identificador del producto.
     * @return Objeto ProductoOtaku si se encuentra, o null si no existe.
     */
    ProductoOtaku obtenerProductoPorId(int id);

    /**
     * Devuelve todos los productos registrados en la base de datos.
     * 
     * @return Lista de productos, o una lista vacía si no hay registros.
     */
    List<ProductoOtaku> obtenerTodosLosProductos();

    /**
     * Actualiza los datos de un producto existente.
     * 
     * @param producto Producto con los nuevos datos.
     * @return true si se actualizó correctamente, false si hubo algún error.
     */
    boolean actualizarProducto(ProductoOtaku producto);

    /**
     * Elimina un producto de la base de datos por su ID.
     * 
     * @param id ID del producto a eliminar.
     * @return true si se eliminó correctamente, false si no se pudo eliminar.
     */
    boolean eliminarProducto(int id);

    /**
     * Busca productos cuyo nombre coincida parcial o totalmente con el texto indicado.
     * 
     * @param nombre Texto a buscar dentro del nombre de los productos.
     * @return Lista de productos que contienen la cadena indicada en su nombre.
     */
    List<ProductoOtaku> buscarProductosPorNombre(String nombre);
}
