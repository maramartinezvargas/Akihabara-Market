package view;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dao.ClienteDAOImpl;
import dao.ProductoDAOImpl;
import model.ClienteOtaku;
import model.ProductoOtaku;

/**
 * Clase que gestiona la interfaz de usuario por consola para el proyecto
 * Akihabara Market. Muestra menús de la app, solicita datos al usuario, asi
 * como mostrar información de los productos. Además, permite actualizar o
 * eliminar productos del inventario.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */

public class InterfazConsola {

	private static final Scanner scanner = new Scanner(System.in);
	
	private final ProductoDAOImpl daoProductos;
	private final ClienteDAOImpl daoClientes;

	public InterfazConsola(ProductoDAOImpl daoProductos, ClienteDAOImpl daoClientes) {
	    this.daoProductos = daoProductos;
	    this.daoClientes = daoClientes;
	}
		
	/**
	 * Muestra el menú principal de la aplicación.
	 */
	public void mostrarMenuInicial() {
		System.out.println("\n --- AKIHABARA MARKET --- " + "\n 1. Gestión de Inventario" + "\n 2. Gestión de Clientes"
				+ "\n 3. Salir");
	}

	/**
	 * Muestra el menú de Gestión de Inventario
	 */
	public void mostrarMenuGestionInventario() {
		System.out.println("\n --- GESTIÓN DE INVENTARIO --- " 
				+ "\n 1. Añadir producto" 
				+ "\n 2. Consultar por ID"
				+ "\n 3. Consultar por nombre" 
				+ "\n 4. Inventario completo"
				+ "\n 5. Actualizar información de un producto" 
				+ "\n 6. Eliminar producto" 
				+ "\n 7. Asistente de IA"
				+ "\n 8. Volver al menú principal");
	}

	/**
	 * Muestra el menú del asistente de inteligencia artificial.
	 */
	public void mostrarMenuAsistente() {
		System.out.println("\n --- ASISTENTE VIRTUAL ---" 
				+ "\n 1. Generar descripción de un producto"
				+ "\n 2. Sugerir categoría para un producto" 
				+ "\n 3. Salir del asistente.");
	}

	public void mostrarMenuGestionClientes() {
		System.out.println("\n--- GESTIÓN DE CLIENTES --- "
				+ "\n 1. Añadir nuevo cliente" 
				+ "\n 2. Consultar cliente por ID"
				+ "\n 3. Consultar cliente por email" 
				+ "\n 4. Consultar todos los clientes registrados"
				+ "\n 5. Actualizar un cliente" 
				+ "\n 6. Eliminar un cliente" 
				+ "\n 7. Volver al menú principal");
	}

	public void mostrarMenuArranqueDual(){
		System.out.println("--- AKIHABARA MARKET ---"
				+ "\n1. Iniciar en modo Consola"
				+ "\n2. Iniciar en modo Gráfico");
	}
	
	/**
	 * Muestra los detalles de un producto individual.
	 * 
	 * @param p el producto a mostrar.
	 */
	public void mostrarProducto(ProductoOtaku p) {
		if (p != null) {
			System.out.println("\n INFORMACIÓN DEL PRODUCTO \n");
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-10s %-50s %-20s %-20s %-20s\n", "ID", "| Nombre", "| Categoría", "| Precio",
					"| Stock");
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-10s %-50s %-20s %-20s %-20s\n", p.getId(), "| " + p.getNombre(),
					"| " + p.getCategoria(), "| " + String.format("%.2f €", p.getPrecio()), "| " + p.getStock());
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------");
		} else {
			System.out.println("\n No hay productos que mostrar.");
		}
	}

	/**
	 * Muestra en tabla una lista de productos.
	 * 
	 * @param lista lista de productos a mostrar.
	 */
	public void mostrarListaProductos(List<ProductoOtaku> lista) {
		if (lista == null || lista.isEmpty()) {
			System.out.println("\nNo hay productos para mostrar.");
			return;
		}
		System.out.println("\n LISTADO DE PRODUCTOS \n");
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-10s %-50s %-20s %-20s %-20s\n", "ID", "| Nombre", "| Categoría", "| Precio", "| Stock");
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------");

		for (ProductoOtaku p : lista) {
			System.out.printf("%-10s %-50s %-20s %-20s %-20s\n", p.getId(), "| " + p.getNombre(),
					"| " + p.getCategoria(), "| " + String.format("%.2f €", p.getPrecio()), "| " + p.getStock());
		}
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------");

	}

	public ProductoOtaku updateProducto(int idUpdate, ProductoDAOImpl daoProductos) {
		ProductoOtaku producto = daoProductos.obtenerProductoPorId(idUpdate);
		if (producto != null) {
			System.out.println("\n --- ACTUALIZAR PRODUCTO ---" + "\n 1. Nombre." + "\n 2. Categoría." + "\n 3. Precio."
					+ "\n 4. Stock." + "\n 5. Cancelar.");
			int opcUpdate = pedirInt("Elige una opción: ");
			switch (opcUpdate) {
			case 1:
				String nombreNuevo = pedirDato("Nombre nuevo: ", "El nombre");
				producto.setNombre(nombreNuevo);
				break;
			case 2:
				String categoriaNueva = pedirDato("Categoria nueva: ", "La categoría");
				producto.setCategoria(categoriaNueva);
				break;
			case 3:
				double precioNuevo = pedirDouble("Precio nuevo: ");
				producto.setPrecio(precioNuevo);
				break;
			case 4:
				int stockNuevo = pedirInt("Stock nuevo: ");
				producto.setStock(stockNuevo);
				break;
			case 5:
				System.out.println("Actualización cancelada.");
				return producto;
			default:
				System.out.println("Opción incorrecta.");
				break;
			}
		}
		return producto;
	}

	/**
	 * Muestra un mensaje indicando si la eliminación de un producto fue exitosa o
	 * no.
	 * 
	 * @param b resultado de la operación de eliminación (true = ejecución
	 *          correcta).
	 */
	public void confirmarEliminacion(Boolean b) {
		if (b) {
			System.out.println("Eliminado.");
		} else {
			System.out.println("ERROR: No se ha podido eliminar.");
		}
	}

	/**
	 * Muestra un mensaje indicando que el producto no existe.
	 */
	public void noExiste() {
		System.out.println("No existe el producto.");
	}

	/**
	 * Muestra la respuesta generada por el asistente IA.
	 * 
	 * @param respuesta -> texto generado por el asistente.
	 */
	public void mostrarPrompt(String respuesta) {
		if (respuesta != null) {
			System.out.println(respuesta);
		} else {
			System.out.println("El asistente no ha podido generar una respuesta.");
		}
	}

	/**
	 * Solicita al usuario un número entero válido con un mensaje personalizado.
	 * 
	 * @param mensaje -> texto que se mostrará al usuario.
	 * @return número entero introducido.
	 */
	public int pedirId(String mensaje) {
		int valor;
		while (true) {
			System.out.print(mensaje);
			try {
				valor = Integer.parseInt(scanner.nextLine());
				return valor;
			} catch (NumberFormatException e) {
				System.out.println("Error: introduce un número válido.\n");
			}
		}
	}

	public void mostrarCliente(ClienteOtaku c) {
		if (c != null) {
			System.out.println("\n INFORMACIÓN DEL CLIENTE \n");
			System.out.println(
					"--------------------------------------------------------------------------------------------------");
			System.out.printf("%-10s %-20s %-20s %-20s %-20s \n", "ID", "| Nombre", "| Email", "| Teléfono",
					"| Fecha de registro");
			System.out.println(
					"--------------------------------------------------------------------------------------------------");
			System.out.printf("%-10s %-20s %-20s %-20s %-20s \n", c.getId(), "| " + c.getNombre(), "| " + c.getEmail(),
					"| " + c.getTelefono(), "| " + c.getFechaRegistro());
			System.out.println(
					"--------------------------------------------------------------------------------------------------");
		} else {
			System.out.println("\n No hay clientes que mostrar.");
		}

	}

	public void mostrarListaClientes(List<ClienteOtaku> lista) {
		if (lista == null || lista.isEmpty()) {
			System.out.println("\nNo hay clientes para mostrar.");
			return;
		}
		System.out.println("\n LISTADO DE CLIENTES \n");
		System.out.println(
				"--------------------------------------------------------------------------------------------------");
		System.out.printf("%-10s %-20s %-20s %-20s %-20s \n", "ID", "| Nombre", "| Email", "| Teléfono",
				"| Fecha de registro");
		System.out.println(
				"--------------------------------------------------------------------------------------------------");

		for (ClienteOtaku c : lista) {
			System.out.printf("%-10s %-20s %-20s %-20s %-20s \n", c.getId(), "| " + c.getNombre(), "| " + c.getEmail(),
					"| " + c.getTelefono(), "| " + c.getFechaRegistro());
		}
		System.out.println(
				"--------------------------------------------------------------------------------------------------");

	}

	public ClienteOtaku pedirNuevoCliente(ClienteDAOImpl daoClientes) {
		String email;
		do {
			email = pedirDato("Email: ", "El email");

			if (!daoClientes.esEmailValido(email)) {
				mostrarErrorEmailInvalido();
				email = null;
				continue;
			}

			if (daoClientes.buscarPorEmail(email) != null) {
				mostrarErrorEmailDuplicado();
				email = null;
			}

		} while (email == null);

		String nombre = pedirDato("Nombre: ", "El nombre");
		String telefono = pedirDato("Teléfono: ", "El teléfono");

		return new ClienteOtaku(nombre, email, telefono, LocalDate.now());
	}

	public ClienteOtaku updateCliente(int idUpdate, ClienteDAOImpl daoClientes) {
		ClienteOtaku cliente = daoClientes.obtenerClientePorId(idUpdate);
		if (cliente != null) {
			System.out.println("\n --- ACTUALIZAR CLIENTE ---" + "\n 1. Nombre" + "\n 2. Email" + "\n 3. Teléfono"
					+ "\n 4. Cancelar");
			int opcUpdate = pedirInt("Elige una opción: ");
			switch (opcUpdate) {
			case 1:
				String nombreNuevo;
				do {
					nombreNuevo = pedirDato("Nuevo nombre: ", "El nombre");
				} while (nombreNuevo.trim().isEmpty());
				cliente.setNombre(nombreNuevo);
				break;
			case 2:
				String emailNuevo;
				do {
					emailNuevo = pedirDato("Nuevo email: ", "El email");
					if (!daoClientes.esEmailValido(emailNuevo)) {
						System.out.println("Error: El email introducido no es válido. Introducelo de nuevo.\n");
						emailNuevo = null;
						continue;
					}
					ClienteOtaku clienteExistente = daoClientes.buscarPorEmail(emailNuevo);
					if (clienteExistente != null && clienteExistente.getId() != cliente.getId()) {
						System.out.println("Error: ya existe un cliente con ese email.\n");
						emailNuevo = null;
					}
				} while (emailNuevo == null);
				cliente.setEmail(emailNuevo);
				break;
			case 3:
				String telefonoNuevo;
				do {
					telefonoNuevo = pedirDato("Nuevo teléfono: ", "El teléfono");
				} while (telefonoNuevo.trim().isEmpty());
				cliente.setTelefono(telefonoNuevo);
				break;
			case 4:
				System.out.println("Actualización cancelada.");
				return null;
			default:
				System.out.println("Opción incorrecta.");
				return null;
			}
			return cliente;
		} else {
			System.out.println("No se ha encontrado ningún cliente con ese ID.");
			return null;
		}
	}

	/**
	 * Solicita al usuario un dato tipo String, validando que no esté vacío.
	 *
	 * @param mensaje Mensaje que se muestra al usuario.
	 * @param dato Nombre del dato solicitado (para mostrar en el mensaje de error).
	 * @return Valor introducido por el usuario, validado como no vacío.
	 */
	public String pedirDato(String mensaje, String dato) {
		String valor;
		while (true) {
			System.out.print(mensaje);
			valor = scanner.nextLine();

			if (valor != null && !valor.trim().isEmpty()) {
				return valor;
			} else {
				System.out.println("Error: " + dato + " no puede estar vacío.\n");
			}
		}
	}


	/**
	 * Solicita al usuario un número entero mayor o igual a 0.
	 *
	 * @param mensaje Mensaje que se muestra al usuario.
	 * @return Número entero introducido por el usuario.
	 */
	public static int pedirInt(String mensaje) {
		int valor = -1;
		while (true) {
			System.out.print(mensaje);
			try {
				while (valor < 0) {
					valor = Integer.parseInt(scanner.nextLine());
					if (valor < 0) {
						System.out.println("ERROR: El valor no puede ser negativo. Vuelve a introducirlo: ");
					}
				}
				return valor;
			} catch (NumberFormatException e) {
				System.out.println("Error: introduce un número entero válido.\n");
			}
		}
	}
	
	/**
	 * Solicita al usuario un número decimal tipo double.
	 *
	 * @param mensaje Mensaje que se muestra al usuario.
	 * @return Número decimal tipo double introducido por el usuario.
	 */
	public static double pedirDouble(String mensaje) {
		double valor;
		while (true) {
			System.out.print(mensaje);
			try {
				String input = scanner.nextLine().replace(",", ".");
				valor = Double.parseDouble(input);
				return valor;
			} catch (NumberFormatException e) {
				System.out.println(
						"Error: introduce un número decimal (double) válido. Usa coma o punto como separador.\n");
			}
		}
	}
	

	/**
	 * Solicita al usuario un número decimal tipo float.
	 *
	 * @param mensaje Mensaje que se muestra al usuario.
	 * @return Número decimal tipo float introducido por el usuario.
	 */
	public static float pedirFloat(String mensaje) {
		float valor;
		while (true) {
			System.out.print(mensaje);
			try {
				String input = scanner.nextLine().replace(",", ".");
				valor = Float.parseFloat(input);
				return valor;
			} catch (NumberFormatException e) {
				System.out.println(
						"Error: introduce un número decimal (float) válido. Usa coma o punto como separador.\n");
			}
		}
	}
	
	/**
	 * Solicita al usuario un único carácter.
	 *
	 * @param mensaje Mensaje que se muestra al usuario.
	 * @return Carácter introducido por el usuario.
	 */
	public static char pedirChar(String mensaje) {
		while (true) {
			System.out.print(mensaje);
			String input = scanner.nextLine();
			if (input.length() == 1) {
				return input.charAt(0);
			} else {
				System.out.println("Error: introduce un solo carácter.\n");
			}
		}
	}
	
	/**
	 * Solicita al usuario una cadena de texto (String) sin validación.
	 *
	 * @param mensaje Mensaje que se muestra al usuario.
	 * @return Texto introducido por el usuario.
	 */
	public static String pedirString(String mensaje) {
		System.out.print(mensaje);
		return scanner.nextLine();
	}
	
	/**
	 * Muestra un mensaje confirmando si se ha añadido el producto correctamente.
	 *
	 * @param exito true si la operación fue exitosa, false en caso contrario.
	 */
	public static void confirmarProductoAniadido(boolean exito) {
	    if (exito) {
	        System.out.println("Producto añadido correctamente.");
	    } else {
	        System.out.println("No se ha podido añadir el producto.");
	    }
	}
	
	/**
	 * Muestra un mensaje confirmando si se ha añadido el cliente correctamente.
	 *
	 * @param exito true si la operación fue exitosa, false en caso contrario.
	 */
	public static void confirmarClienteAniadido(boolean exito) {
	    if (exito) {
	        System.out.println("Producto añadido correctamente.");
	    } else {
	        System.out.println("No se ha podido añadir el producto.");
	    }
	}

	
	/**
	 * Muestra un mensaje confirmando si se ha actualizado el producto correctamente.
	 *
	 * @param b true si se actualizó con éxito, false si hubo error.
	 */
	public void confirmarUpdateProducto(Boolean b) {
		if (b) {
			System.out.println("Producto modificado.");
		} else {
			System.out.println("ERROR: No se ha podido modificar ningún producto.");
		}
	}

	/**
	 * Muestra un mensaje confirmando si se ha actualizado el cliente correctamente.
	 *
	 * @param b true si se actualizó con éxito, false si hubo error.
	 */
	public void confirmarUpdateCliente(Boolean b) {
		if (b) {
			System.out.println("Cliente modificado.");
		} else {
			System.out.println("ERROR: No se ha podido modificar ningún cliente.");
		}
	}

	/**
	 * Muestra un mensaje de despedida al cerrar la aplicación.
	 */
	public void salir() {
		System.out.println("¡Hasta pronto!");
	}

	/**
	 * Informa al usuario que la opción elegida no es válida.
	 */
	public void mostrarOpcionErronea() {
		System.out.println("Opción inválida.");
	}

	/**
	 * Muestra un mensaje al salir del asistente IA.
	 */
	public void mostrarCancelar() {
		System.out.println("Has elegido salir del Asistente de IA.");
	}

	/**
	 * Muestra un mensaje de error si el formato de email introducido por el usuario
	 * no es válido.
	 */
	public void mostrarErrorEmailInvalido() {
		System.out.println("Error: El email introducido no es válido. Introducelo de nuevo.\n");
	}

	/**
	 * Muestra un mensaje de error si ya existe un cliente registrado con ese email.
	 */
	public void mostrarErrorEmailDuplicado() {
		System.out.println("Error: ya existe un cliente registrado con ese email.\n");
	}

	public void noExisteCliente() {
		System.out.println("No se ha encontrado ningún cliente con ese ID.");
	}

	/**
	 * Cierra el escáner de entrada.
	 */
	public static void cerrarScanner() {
		scanner.close();
	}

	/**
	 * Confirmación por consola de exito de la conexión.
	 */
	public static void exitoConexion() {
		System.out.println("Conexión a la Base de Datos establecida correctamente.");
	}
	
	public ProductoDAOImpl getDaoProductos() {
		return daoProductos;
	}

	public ClienteDAOImpl getDaoClientes() {
		return daoClientes;
	}




}
