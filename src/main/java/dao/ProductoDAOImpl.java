package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ProductoOtaku;
import view.InterfazConsola;

/**
 * Implementación de la interfaz ProductoDAOInterface.
 * 
 * Gestiona las operaciones CRUD sobre la tabla productos de la base de datos,
 * utilizando JDBC y la conexión proporcionada por DatabaseConnection.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public class ProductoDAOImpl implements ProductoDAOInterface {

	Connection conn = DatabaseConnection.getConnection();

	/**
	 * Inserta un nuevo producto en la base de datos.
	 *
	 * @param producto Objeto ProductoOtaku a registrar.
	 */
	public void agregarProducto(ProductoOtaku producto) {
		try {
			String insertSQL = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(insertSQL);
			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getCategoria());
			ps.setDouble(3, producto.getPrecio());
			ps.setInt(4, producto.getStock());

			int afect = ps.executeUpdate();

			if (afect == 0) {
				InterfazConsola.confirmarProductoAniadido(false);
			} else {
				InterfazConsola.confirmarProductoAniadido(true);
			}

			ps.close();

		} catch (SQLException ex) {
			System.out.println("Error al agregar producto: " + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Error inesperado al agregar producto: " + ex.getMessage());
		}
	}

	/**
	 * Recupera un producto a partir de su ID.
	 *
	 * @param id ID del producto a buscar.
	 * @return Objeto ProductoOtaku si se encuentra, o null si no existe.
	 */
	public ProductoOtaku obtenerProductoPorId(int id) {
		try {
			String query = "SELECT * FROM productos WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String nombre = rs.getString("nombre");
				String categoria = rs.getString("categoria");
				double precio = rs.getDouble("precio");
				int stock = rs.getInt("stock");

				return new ProductoOtaku(id, nombre, categoria, precio, stock);
			}

			rs.close();
			ps.close();

		} catch (SQLException ex) {
			System.out.println("Error al obtener producto por ID: " + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Error inesperado: " + ex.getMessage());
		}
		return null;
	}

	/**
	 * Devuelve todos los productos registrados en la base de datos.
	 *
	 * @return Lista de objetos ProductoOtaku, o null si ocurre un error.
	 */
	public List<ProductoOtaku> obtenerTodosLosProductos() {
		List<ProductoOtaku> listaProductos = new ArrayList<>();
		try {
			Statement st = conn.createStatement();
			String query = "SELECT * FROM productos";
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String categoria = rs.getString("categoria");
				double precio = rs.getDouble("precio");
				int stock = rs.getInt("stock");

				listaProductos.add(new ProductoOtaku(id, nombre, categoria, precio, stock));
			}

			rs.close();
			st.close();
			return listaProductos;

		} catch (SQLException ex) {
			System.out.println("Error al obtener productos: " + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Error inesperado al obtener productos: " + ex.getMessage());
		}
		return null;
	}

	/**
	 * Actualiza los datos de un producto ya existente en la base de datos.
	 *
	 * @param producto Objeto con los nuevos datos.
	 * @return true si se actualizó correctamente, false en caso de error.
	 */
	public boolean actualizarProducto(ProductoOtaku producto) {
		try {
			if (producto != null) {
				String updateSQL = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
				PreparedStatement ps = conn.prepareStatement(updateSQL);

				ps.setString(1, producto.getNombre());
				ps.setString(2, producto.getCategoria());
				ps.setDouble(3, producto.getPrecio());
				ps.setInt(4, producto.getStock());
				ps.setInt(5, producto.getId());

				int afect = ps.executeUpdate();
				ps.close();

				return afect > 0;
			}
		} catch (SQLException e) {
			System.out.println("Error al actualizar producto: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Elimina un producto de la base de datos según su ID.
	 *
	 * @param id Identificador del producto a eliminar.
	 * @return true si se eliminó correctamente, false en caso de error.
	 */
	public boolean eliminarProducto(int id) {
		try {
			String deleteSQL = "DELETE FROM productos WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(deleteSQL);
			ps.setInt(1, id);
			int afect = ps.executeUpdate();
			ps.close();

			return afect > 0;

		} catch (SQLException e) {
			System.out.println("Error al eliminar producto: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Busca productos por coincidencia parcial del nombre.
	 *
	 * @param nombre Cadena o fragmento del nombre a buscar.
	 * @return Lista de productos que coinciden con el nombre proporcionado.
	 */
	public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
		List<ProductoOtaku> listaProductos = new ArrayList<>();

		try {
			String query = "SELECT * FROM productos WHERE nombre LIKE ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "%" + nombre + "%");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String nombreOriginal = rs.getString("nombre");
				String categoria = rs.getString("categoria");
				double precio = rs.getDouble("precio");
				int stock = rs.getInt("stock");

				listaProductos.add(new ProductoOtaku(id, nombreOriginal, categoria, precio, stock));
			}

			rs.close();
			ps.close();

		} catch (SQLException ex) {
			System.out.println("Error al buscar productos por nombre: " + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Error inesperado: " + ex.getMessage());
		}

		return listaProductos;
	}
}
