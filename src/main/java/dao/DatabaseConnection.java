package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import config.ConfigLoader;

/**
 * Clase encargada de gestionar la conexión con la base de datos MySQL.
 * 
 * Utiliza JDBC y los parámetros definidos en el archivo config.properties para establecer la conexión.
 * La conexión se crea una única vez y se reutiliza mientras esté activa.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public class DatabaseConnection {

    private static Connection conn = null;

    /**
     * Establece y devuelve una conexión a la base de datos MySQL.
     * 
     * Si ya existe una conexión activa, se reutiliza. La configuración se obtiene desde
     * el archivo config.properties mediante la clase ConfigLoader.
     * 
     * @return Objeto Connection si la conexión se ha establecido correctamente, o null si ha fallado.
     */
    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Carga del driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Carga de credenciales desde config.properties
                String usr = ConfigLoader.getPropiedades("db.user"); 
                String pwd = ConfigLoader.getPropiedades("db.password"); 
                String url = ConfigLoader.getPropiedades("db.url");

                // Establecimiento de la conexión
                conn = DriverManager.getConnection(url, usr, pwd);
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al cargar el driver JDBC: " + ex.getMessage());
            } catch (SQLException ex) {
                System.out.println("ERROR DE CONEXIÓN A LA BASE DE DATOS.");
            } catch (Exception ex) {
                System.out.println("Otro error inesperado: " + ex.getMessage());
            }
        }
        return conn;
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     * Se usa para llamar a este método al finalizar la aplicación y liberar recursos.
     */
    public static void cerrarConexion() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
