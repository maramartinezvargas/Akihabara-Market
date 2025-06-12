package controller;

import java.awt.EventQueue;
import javax.swing.JFrame;
import view.InterfazGrafica;

/**
 * Clase principal para lanzar la interfaz gráfica de la aplicación Akihabara Market (implementada con Swing Java).
 * Inicia la ventana principal de la GUI.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public class MainGrafica {

    /**
     * Método main que arranca la interfaz gráfica.
     * Se usa EventQueue.invokeLater para asegurar que la GUI se construya en el hilo adecuado
     * (Event Dispatch Thread (EDT), que es el hilo principal que gestiona los eventos de la interfaz gráfica.)
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InterfazGrafica frame = new InterfazGrafica();
                    frame.setSize(940, 496);                    
                    frame.setLocationRelativeTo(null);         
                    frame.setResizable(false);                  
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                    frame.setAlwaysOnTop(true);
                    frame.toFront();
                    frame.requestFocus();
                    frame.setAlwaysOnTop(false);

                } catch (Exception e) {
                    e.printStackTrace(); // Se muestra cualquier error al lanzar la interfaz
                }
            }
        });
    }
}
