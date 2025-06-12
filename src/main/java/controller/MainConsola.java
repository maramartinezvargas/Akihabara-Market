package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import dao.ClienteDAOImpl;
import dao.DatabaseConnection;
import dao.ProductoDAOImpl;
import model.ClienteOtaku;
import model.ProductoOtaku;
import service.LlmService;
import view.InterfazConsola;

/**
 * Clase principal (MainConsola) del proyecto Akihabara Market.
 * Contiene el método main que ejecuta la aplicación de gestión 
 * de productos otaku y clientes mediante consola.
 *
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public class MainConsola {

	/**
	 * Método principal que establece la conexión con la base de datos y
	 * lanza el menú principal de la aplicación por consola. Permite 
	 * gestionar productos, clientes y acceder al asistente IA.
	 *
	 * @param args Argumentos de línea de comandos (no utilizados).
	 */
	public static void main(String[] args) {

		ProductoDAOImpl daoProductos = new ProductoDAOImpl();
		ClienteDAOImpl daoClientes = new ClienteDAOImpl();
		InterfazConsola consola = new InterfazConsola(daoProductos, daoClientes);
		LlmService llmService = new LlmService(daoProductos, consola);
		
		Connection conn = DatabaseConnection.getConnection();
		
		
		if (conn == null) {
			System.err.println("No se ha podido establecer conexión con la base de datos."
					+ "\nVerifica los credenciales de acceso.");
			return;
		}else {
			consola.exitoConexion();
		}
		
	
		/*
		// Productos de prueba cargados inicialmente
		ProductoOtaku p1 = new ProductoOtaku("Figura de Anya Forger", "Figura", 59.95, 8);
		ProductoOtaku p2 = new ProductoOtaku("Manga Chainsaw Man Vol.1", "Manga", 9.99, 20);
		ProductoOtaku p3 = new ProductoOtaku("Póster Studio Ghibli Colección", "Póster", 15.59, 15);
		daoProductos.agregarProducto(p1);
		daoProductos.agregarProducto(p2);
		daoProductos.agregarProducto(p3);

		// Clientes de prueba cargados inicialmente
		ClienteOtaku c1 = new ClienteOtaku("Mara", "mara@gmail.com", "647807965", LocalDate.now());
		ClienteOtaku c2 = new ClienteOtaku("Juan", "juan@gmail.com", "610252635", LocalDate.now());
		ClienteOtaku c3 = new ClienteOtaku("Pepe", "pepe@gmail.com", "678495623", LocalDate.now());
		daoClientes.agregarCliente(c1);
		daoClientes.agregarCliente(c2);
		daoClientes.agregarCliente(c3);
		*/
		
		int opcionGeneral = 0;
		
		// Bucle del menú principal
		while (opcionGeneral != 3) {
			consola.mostrarMenuInicial();
			opcionGeneral = consola.pedirInt("Elige una opción: ");

			switch (opcionGeneral) {
			case 1:
				gestionarInventario(consola, llmService);
				break;
			case 2:
				gestionarClientes(consola);
				break;
			case 3:
				consola.salir();
				break;
			default:
				consola.mostrarOpcionErronea();
			}
		}
		
		// Cierre de conexión y escáner para liberar recursos
		consola.cerrarScanner();
		DatabaseConnection.cerrarConexion();
	}
	
	/**
	 * Muestra y gestiona el menú de inventario:
	 * permite añadir, consultar, actualizar, eliminar productos
	 * y utilizar un asistente IA para generar descripciones o sugerencias.
	 *
	 * @param consola Interfaz de consola para entrada/salida
	 * @param llmService Servicio de IA para generación de descripciones o categorías
	 */
	private static void gestionarInventario(InterfazConsola consola, LlmService llmService) {
		int opc = 0;

		while (opc != 8) {

			// Mostrar menú y solicitar opción del usuario
			consola.mostrarMenuGestionInventario();
			opc = consola.pedirInt("Elige una opcion:");

			switch (opc) {

			case 1: // agregar producto
				String nom = consola.pedirDato("Nombre del producto: ", "El nombre");
				String cat = consola.pedirDato("Categoría del producto: ", "La categoría");
				double pre = consola.pedirDouble("Precio del producto: ");
				int sto = consola.pedirInt("Cantidad en stock: ");
				ProductoOtaku nuevoProducto = new ProductoOtaku(nom, cat, pre, sto);
				consola.getDaoProductos().agregarProducto(nuevoProducto);
				break;

			case 2: // buscar producto por ID
				int id = consola.pedirInt("ID del producto: ");
				ProductoOtaku producto = consola.getDaoProductos().obtenerProductoPorId(id);
				consola.mostrarProducto(producto);
				break;

			case 3: // buscar producto por nombre
				String nombre = consola.pedirString("Nombre del producto: ");
				consola.mostrarListaProductos(consola.getDaoProductos().buscarProductosPorNombre(nombre));
				break;

			case 4: // mostrar productos
				List<ProductoOtaku> lista = consola.getDaoProductos().obtenerTodosLosProductos();
				consola.mostrarListaProductos(lista);
				break;

			case 5: // actualizar producto
				int idUpdate = consola.pedirInt("ID del producto: ");
				ProductoOtaku productoActualizado = consola.updateProducto(idUpdate, consola.getDaoProductos());
				consola.confirmarUpdateProducto(consola.getDaoProductos().actualizarProducto(productoActualizado));
				break;

			case 6: // eliminar producto
				int idDelete = consola.pedirInt("ID del producto: ");
				consola.confirmarEliminacion(consola.getDaoProductos().eliminarProducto(idDelete));
				break;

			case 7: // asistente IA
				int opcAsistente = 0;
				do {
					consola.mostrarMenuAsistente();
					opcAsistente = consola.pedirInt("Elige una opción: ");

					switch (opcAsistente) {
					case 1: // generar descripción de un producto
						String prompt = null;
						int idPrompt = consola.pedirInt("Introduce ID: ");
						if (consola.getDaoProductos().obtenerProductoPorId(idPrompt) != null) {
							prompt = llmService.obtenerDescripcion(idPrompt);
							consola.mostrarPrompt(llmService.llmService(prompt));
						} else {
							consola.mostrarPrompt(prompt);
						}
						break;

					case 2: // sugerir categoría para un producto
						String nombrePrompt = consola.pedirString("Nombre del producto nuevo: ");
						String prompt2 = llmService.obtenerCategoria(nombrePrompt);
						consola.mostrarPrompt(llmService.llmService(prompt2));
						break;
					case 3: // cancelar
						consola.mostrarCancelar();
						break;
					default:
						consola.mostrarOpcionErronea();
						break;
					}

				} while (opcAsistente != 3);

				break;
			case 8: // volver al menú principal
				break;
			default:
				consola.mostrarOpcionErronea();
				break;
			}
		}
	}

	/**
	 * Muestra y gestiona el menú de clientes:
	 * permite añadir, consultar, actualizar y eliminar registros de clientes.
	 *
	 * @param consola Interfaz de consola para entrada/salida
	 */
	private static void gestionarClientes(InterfazConsola consola) {
		int opc = 0;
		while (opc != 7) {
			consola.mostrarMenuGestionClientes();
			opc = consola.pedirInt("Elige una opción: ");

			switch (opc) {
			case 1:
				ClienteOtaku nuevoCliente = consola.pedirNuevoCliente(consola.getDaoClientes());
				consola.getDaoClientes().agregarCliente(nuevoCliente);
				break;

			case 2:
				int id = consola.pedirInt("ID del cliente: ");
				ClienteOtaku clientePorId = consola.getDaoClientes().obtenerClientePorId(id);
				consola.mostrarCliente(clientePorId);
				break;
			case 3:
				String emailBuscado;
				do {
					emailBuscado = consola.pedirDato("Email del cliente: ", "El email");
					if (!consola.getDaoClientes().esEmailValido(emailBuscado)) {
						consola.mostrarErrorEmailInvalido();
					}
				} while (!consola.getDaoClientes().esEmailValido(emailBuscado));
				ClienteOtaku clientePorEmail = consola.getDaoClientes().buscarPorEmail(emailBuscado);
				consola.mostrarCliente(clientePorEmail);
				break;
			case 4:
				List<ClienteOtaku> listaClientes = consola.getDaoClientes().obtenerTodosLosClientes();
				consola.mostrarListaClientes(listaClientes);
				break;
			case 5:
				int idCliente = consola.pedirInt("ID del cliente: ");
				ClienteOtaku clienteActualizado = consola.updateCliente(idCliente, consola.getDaoClientes());
				if (clienteActualizado != null) {
					consola.confirmarUpdateCliente(consola.getDaoClientes().actualizarCliente(clienteActualizado));
				}
				break;
			case 6:
				int idDelete = consola.pedirInt("ID del cliente: ");
				ClienteOtaku clienteAEliminar = consola.getDaoClientes().obtenerClientePorId(idDelete);

				if (clienteAEliminar == null) {
					consola.noExisteCliente();
				} else {
					consola.confirmarEliminacion(consola.getDaoClientes().eliminarCliente(idDelete));
				}
				break;
			case 7: // volver al menú principal
				break;
			default:
				consola.mostrarOpcionErronea();
				break;
			}
		}
	}
}

