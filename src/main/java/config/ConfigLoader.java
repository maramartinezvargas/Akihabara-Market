package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase encargada de cargar los parámetros de configuración desde el archivo config.properties.
 * 
 * Permite acceder a valores como el usuario, la contraseña y la URL de la base de datos,
 * necesarios para establecer la conexión con MySQL.
 * 
 * El archivo config.properties debe estar ubicado en la raíz del proyecto y no debe ser versionado.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public class ConfigLoader {

    private static final String CONFIG_FILE = "config.properties";

    /**
     * Devuelve el valor asociado a una clave específica del archivo de configuración.
     * 
     * Si ocurre algún error al leer el archivo, devuelve null.
     * 
     * @param key Clave de la propiedad que se quiere obtener.
     * @return Valor asociado a la clave indicada o null si no se puede cargar el archivo o la clave no existe.
     */
    public static String getPropiedades(String key) {
        Properties prop = new Properties();

        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            prop.load(fis);
            return prop.getProperty(key);
        } catch (IOException e) {
            System.err.println("Error al cargar config.properties: " + e.getMessage());
            return null;
        }
    }
}
