package model;

/**
 * Clase modelo que representa un producto otaku de la tienda Akihabara Market.
 * 
 * Cada producto tiene un ID único, nombre, categoría, precio y cantidad en
 * stock. Esta clase se utiliza para intercambiar datos entre la base de datos y
 * la aplicación.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public class ProductoOtaku {

	private int id;
	private String nombre;
	private String categoria;
	private double precio;
	private int stock;

	/**
	 * Constructor para crear un nuevo producto sin ID.
	 * 
	 * Se usa principalmente al insertar productos nuevos en la base de datos, ya
	 * que el ID se asigna automáticamente.
	 * 
	 * @param nombre    Nombre del producto.
	 * @param categoria Categoría del producto.
	 * @param precio    Precio del producto.
	 * @param stock     Cantidad en stock.
	 */
	public ProductoOtaku(String nombre, String categoria, double precio, int stock) {
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
	}

	/**
	 * Constructor con ID, usado para representar productos existentes.
	 * 
	 * @param id        ID del producto.
	 * @param nombre    Nombre del producto.
	 * @param categoria Categoría del producto.
	 * @param precio    Precio del producto.
	 * @param stock     Cantidad en stock.
	 */
	public ProductoOtaku(int id, String nombre, String categoria, double precio, int stock) {
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
