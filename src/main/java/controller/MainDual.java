package controller;

import java.util.Scanner;

import dao.ClienteDAOImpl;
import dao.ProductoDAOImpl;
import view.InterfazConsola;

/**
 * Clase principal que permite al usuario elegir entre arrancar la aplicación Akihabara Market
 * en modo consola o en modo gráfico (interfaz Swing).
 * 
 * Presenta un menú inicial para que el usuario seleccione el tipo de interfaz que desea utilizar.
 * Una vez seleccionada, delega la ejecución al main correspondiente: {@code MainConsola} o {@code MainGrafica}.
 * 
 * Esta clase actúa como punto de arranque flexible del programa.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public class MainDual {
	
	  /**
     * Método principal que lanza el menú de selección entre los modos de interfaz.
     * 
     * El usuario podrá escoger entre:
     * <ul>
     *   <li>Modo Consola (texto en terminal)</li>
     *   <li>Modo Gráfico (ventana Swing)</li>
     * </ul>
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
    	
    	// Inicialización de los DAO para productos y clientes
    	ProductoDAOImpl daoProductos = new ProductoDAOImpl();
		ClienteDAOImpl daoClientes = new ClienteDAOImpl();
		
        // Instancia de la interfaz de consola para mostrar el menú de arranque
		InterfazConsola consola = new InterfazConsola(daoProductos, daoClientes);

		// Se crea un scanner
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;
        
        // Bucle hasta que el usuario elija una opción válida
        while (opcion != 1 && opcion != 2) {
            consola.mostrarMenuArranqueDual();
            opcion = consola.pedirInt("Elige una opción: ");
        }

        switch (opcion) {
            case 1:
                MainConsola.main(null); // Para arrancar en modo consola
                break;
            case 2:
                MainGrafica.main(null); // Para arrancar en modo gráfico (Swing Java)
                break;
            default:
            	consola.mostrarOpcionErronea();
                break;
        }
    }
}
