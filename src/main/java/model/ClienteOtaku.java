package model;

import java.time.LocalDate;

/**
 * Clase que representa a un cliente registrado en Akihabara Market.
 * 
 * Contiene los datos personales básicos y la fecha de registro.
 * Se usa para insertar, consultar y actualizar clientes en la base de datos.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public class ClienteOtaku {
	
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;

    /**
     * Constructor sin ID (útil para insertar nuevos clientes).
     * 
     * @param nombre Nombre del cliente.
     * @param email Email del cliente.
     * @param telefono Teléfono del cliente.
     * @param fechaRegistro Fecha en que se registró.
     */
    public ClienteOtaku(String nombre, String email, String telefono, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Constructor con ID (usado al consultar clientes desde la base de datos).
     * 
     * @param id ID del cliente.
     * @param nombre Nombre del cliente.
     * @param email Email del cliente.
     * @param telefono Teléfono del cliente.
     * @param fechaRegistro Fecha en que se registró.
     */
    public ClienteOtaku(int id, String nombre, String email, String telefono, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y setters

    public int getId() { 
    	return id; 
    }
    
    public void setId(int id) { 
    	this.id = id; 
    }

    public String getNombre() { 
    	return nombre; 
    }
    
    public void setNombre(String nombre) { 
    	this.nombre = nombre; 
    }

    public String getEmail() { 
    	return email; 
    }
    
    public void setEmail(String email) { 
    	this.email = email; 
    }

    public String getTelefono() { 
    	return telefono; 
    }
    
    public void setTelefono(String telefono) { 
    	this.telefono = telefono; 
    }

    public LocalDate getFechaRegistro() { 
    	return fechaRegistro; 
    }
    
    public void setFechaRegistro(LocalDate fechaRegistro) { 
    	this.fechaRegistro = fechaRegistro; 
    }

}
