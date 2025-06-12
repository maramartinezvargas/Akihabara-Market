package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ClienteOtaku;
import view.InterfazConsola;

/**
 * Implementación del DAO para la entidad ClienteOtaku.
 * 
 * Esta clase gestiona todas las operaciones CRUD sobre la tabla 'clientes' en la base de datos.
 * Utiliza conexión a MySQL a través de JDBC y PreparedStatement para evitar inyecciones SQL.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public class ClienteDAOImpl implements ClienteDAOInterface {

	private Connection conn = DatabaseConnection.getConnection();

	/**
	 * Añade un nuevo cliente a la base de datos.
	 * Valida previamente que el email tenga un formato válido.
	 * 
	 * @param cliente ClienteOtaku a registrar.
	 */
	@Override
	public void agregarCliente(ClienteOtaku cliente) {
		if (!esEmailValido(cliente.getEmail())) {
			System.out.println("Error: el email no tiene un formato válido.");
			return;
		}
		try {
			String sql = "INSERT INTO clientes (nombre, email, telefono, fecha_registro) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cliente.getNombre());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, cliente.getTelefono());
			ps.setDate(4, Date.valueOf(cliente.getFechaRegistro()));

			int affect = ps.executeUpdate();
			if (affect > 0) {
				InterfazConsola.confirmarClienteAniadido(true);
			} else {
				InterfazConsola.confirmarClienteAniadido(false);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al agregar cliente: " + e.getMessage());
		}
	}

	/**
	 * Recupera un cliente por su ID.
	 * 
	 * @param id Identificador del cliente.
	 * @return ClienteOtaku si se encuentra, o null si no existe o hay error.
	 */
	@Override
	public ClienteOtaku obtenerClientePorId(int id) {
		try {
			String sql = "SELECT * FROM clientes WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new ClienteOtaku(
					rs.getInt("id"),
					rs.getString("nombre"),
					rs.getString("email"),
					rs.getString("telefono"),
					rs.getDate("fecha_registro").toLocalDate()
				);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al obtener cliente: " + e.getMessage());
		}

		return null;
	}

	/**
	 * Recupera todos los clientes registrados en la base de datos.
	 * 
	 * @return Lista de objetos ClienteOtaku.
	 */
	@Override
	public List<ClienteOtaku> obtenerTodosLosClientes() {
		List<ClienteOtaku> lista = new ArrayList<>();
		try {
			String sql = "SELECT * FROM clientes";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				ClienteOtaku cliente = new ClienteOtaku(
					rs.getInt("id"),
					rs.getString("nombre"),
					rs.getString("email"),
					rs.getString("telefono"),
					rs.getDate("fecha_registro").toLocalDate()
				);
				lista.add(cliente);
			}

			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Error al obtener todos los clientes: " + e.getMessage());
		}

		return lista;
	}

	/**
	 * Actualiza los datos de un cliente existente.
	 * 
	 * @param cliente ClienteOtaku con los datos actualizados.
	 * @return true si se actualizó correctamente, false en caso contrario.
	 */
	@Override
	public boolean actualizarCliente(ClienteOtaku cliente) {
		try {
			String sql = "UPDATE clientes SET nombre = ?, email = ?, telefono = ?, fecha_registro = ? WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cliente.getNombre());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, cliente.getTelefono());
			ps.setDate(4, Date.valueOf(cliente.getFechaRegistro()));
			ps.setInt(5, cliente.getId());

			int affect = ps.executeUpdate();
			ps.close();
			return affect > 0;

		} catch (SQLException e) {
			System.out.println("Error al actualizar cliente: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Elimina un cliente de la base de datos a partir de su ID.
	 * 
	 * @param id Identificador del cliente.
	 * @return true si se eliminó correctamente, false en caso de error.
	 */
	@Override
	public boolean eliminarCliente(int id) {
		try {
			String sql = "DELETE FROM clientes WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			int affect = ps.executeUpdate();
			ps.close();
			return affect > 0;

		} catch (SQLException e) {
			System.out.println("Error al eliminar cliente: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Busca un cliente por su dirección de email.
	 * 
	 * @param email Email del cliente a buscar.
	 * @return ClienteOtaku si se encuentra, o null si no existe.
	 */
	@Override
	public ClienteOtaku buscarPorEmail(String email) {
		try {
			String sql = "SELECT * FROM clientes WHERE email = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new ClienteOtaku(
					rs.getInt("id"),
					rs.getString("nombre"),
					rs.getString("email"),
					rs.getString("telefono"),
					rs.getDate("fecha_registro").toLocalDate()
				);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al buscar cliente por email: " + e.getMessage());
		}

		return null;
	}

	/**
	 * Valida que el formato del email sea correcto usando una expresión regular básica.
	 * 
	 * @param email Email a validar.
	 * @return true si el formato es válido, false si no lo es o está vacío.
	 */
	public boolean esEmailValido(String email) {
		String patron = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
		return email != null && email.matches(patron);
	}
}
