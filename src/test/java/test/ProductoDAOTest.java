package test;

import dao.ProductoDAOImpl;
import model.ProductoOtaku;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Para forzar el orden de ejecución de los test
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 

public class ProductoDAOTest {
	// Se define una varible de clase DAO para manejarla en todas las pruebas.
    static ProductoDAOImpl dao;
    
    // ID del producto insertado para usar en pruebas posteriores
    static int idGenerado; 

    @BeforeAll   // Se ejecuta una sola vez antes de todos los tests
    public static void inicio() {
    	dao = new ProductoDAOImpl(); // Se inicializa la variable dao
    }

    /**
     * Objetivo del test: Verificar que se puede insertar un producto y recuperarlo por nombre
     * Entrada utilizada: Producto: "Test Producto", "Figuras", 19.99, 10
     * Resultado esperado: El producto se inserta correctamente y se recupera con datos iguales
     * Guarda el ID para las siguientes pruebas.
     */
    @Test
    @Order(1)
    public void testAgregarProducto() {
        ProductoOtaku producto = new ProductoOtaku("Test Producto", "Figuras", 19.99, 10);
        dao.agregarProducto(producto);

        // Comprobamos que se ha insertado buscando por nombre
        List<ProductoOtaku> lista = dao.buscarProductosPorNombre("Test Producto");
        assertFalse(lista.isEmpty(), "La lista no debería estar vacía");

        ProductoOtaku insertado = lista.get(0);
        idGenerado = insertado.getId(); // Guardamos el ID para pruebas posteriores

        assertEquals("Test Producto", insertado.getNombre());
        assertEquals("Figuras", insertado.getCategoria());
        assertEquals(19.99, insertado.getPrecio());
        assertEquals(10, insertado.getStock());
    }

    /**
     * Objetivo del test: Verificar que se puede obtener un producto insertado previamente por su ID
     * Entrada utilizada: ID generado tras la inserción
     * Resultado esperado: Se obtiene un producto no nulo con el ID correcto
     */
    @Test
    @Order(2)
    public void testObtenerProductoPorId() {
        ProductoOtaku producto = dao.obtenerProductoPorId(idGenerado);
        assertNotNull(producto, "El producto no debería ser null");
        assertEquals(idGenerado, producto.getId());
    }

    /**
     * Objetivo del test: Verificar que se actualiza correctamente el precio y stock de un producto
     * Entrada utilizada: Precio: 25.50, Stock: 5
     * Resultado esperado: La actualización devuelve true y los datos se reflejan en la BD
     */
    @Order(3)
    @Test
    public void testActualizarProducto() {
        ProductoOtaku producto = dao.obtenerProductoPorId(idGenerado);
        producto.setPrecio(25.50);
        producto.setStock(5);
        boolean actualizado = dao.actualizarProducto(producto);
        assertTrue(actualizado, "El producto debería haberse actualizado");

        ProductoOtaku actualizadoProducto = dao.obtenerProductoPorId(idGenerado);
        assertEquals(25.50, actualizadoProducto.getPrecio());
        assertEquals(5, actualizadoProducto.getStock());
    }

    /**
     * Objetivo del test: Verificar que la búsqueda por nombre parcial devuelve productos coincidentes
     * Entrada utilizada: Fragmento: "Test"
     * Resultado esperado: Se encuentra el producto insertado previamente
     */
    @Test
    @Order(4)
    public void testBuscarProductosPorNombre() {
    	List<ProductoOtaku> resultados = dao.buscarProductosPorNombre("Test");

	    boolean encontrado = false;
	    for (ProductoOtaku producto : resultados) {
	        if (producto.getId() == idGenerado) {
	            encontrado = true;
	            break;
	        }
	    }
	    assertTrue(encontrado, "El producto buscado no se ha encontrado.");
    }

    /**
     * Objetivo del test: Verificar que se puede recuperar la lista completa de productos
     * Entrada utilizada: -
     * Resultado esperado: Se obtiene una lista no nula con al menos un producto
     */
    @Test
    @Order(5)
    public void testObtenerTodosLosProductos() {
        List<ProductoOtaku> productos = dao.obtenerTodosLosProductos();
        assertNotNull(productos, "La lista de productos no debería ser null");
        assertTrue(productos.size() > 0, "Debería haber al menos un producto en la lista");
    }

    /**
     * Objetivo del test: Verificar que se puede eliminar un producto por su ID y que desaparece de la BD
     * Entrada utilizada: ID generado previamente
     * Resultado esperado:La eliminación devuelve true y ya no se puede recuperar el producto
     */
    @Test
    @Order(6)
    public void testEliminarProducto() {
        boolean eliminado = dao.eliminarProducto(idGenerado);
        assertTrue(eliminado, "El producto debería haberse eliminado");

        ProductoOtaku producto = dao.obtenerProductoPorId(idGenerado);
        assertNull(producto, "El producto ya no debería existir");
    }
}
