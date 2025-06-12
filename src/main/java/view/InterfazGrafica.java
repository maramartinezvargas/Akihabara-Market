package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dao.ClienteDAOImpl;
import dao.ProductoDAOImpl;
import model.ClienteOtaku;
import model.ProductoOtaku;
import service.LlmService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 * Clase que representa la interfaz gráfica principal de la aplicación Akihabara Market.
 * 
 * Esta interfaz permite gestionar productos y clientes mediante un conjunto de pestañas organizadas
 * para realizar operaciones CRUD (crear, leer, actualizar, eliminar) y ofrece funciones asistidas 
 * por IA para generar descripciones y categorías de productos.
 * 
 * La clase utiliza componentes Swing personalizados y está dividida en dos paneles principales:
 * <ul>
 *   <li>Gestión de Inventario</li>
 *   <li>Gestión de Clientes</li>
 * </ul>
 * 
 * Funcionalidades incluidas:
 * <ul>
 *   <li>CRUD de productos y clientes</li>
 *   <li>Búsqueda por ID y nombre</li>
 *   <li>Visualización de listados completos en tablas</li>
 *   <li>Interacción con un asistente IA para generar descripciones y categorías</li>
 *   <li>Estilo visual personalizado con colores temáticos</li>
 * </ul>
 * 
 * Se utiliza `ProductoDAOImpl` y `ClienteDAOImpl` para el acceso a base de datos, y `LlmService`
 * para las funcionalidades inteligentes asistidas.
 * 
 * @author Tamara Martínez
 * @version 1.0
 * @since 13/06/2025
 */
public class InterfazGrafica extends JFrame {

	ProductoDAOImpl daoProductos = new ProductoDAOImpl();
	ClienteDAOImpl daoClientes = new ClienteDAOImpl();
	LlmService llmService = new LlmService(daoProductos);

	private JPanel ventanaPrincipalJPanel;
	private JTextField textField;
	private JTextField nombreTextField;
	private JTextField categoriaField;
	private JTextField precioTextField;
	private JTextField categoriaTextField;
	private JTextField stockTextField;

	private JTabbedPane inventarioTabbedPane;
	private JTabbedPane clientesTabbedPane;
	private JTextField nombreConsultaPorNombreTextField;
	private JTable table;
	private JTextField consultarPorIDtextField;
	private JTable tableConsultaProductoPorNombre;
	private JTextField textFieldEliminarProducto;
	private JTextField textFieldIdAsistenteIA;
	private JTextField textFieldDescripciónGenerada;
	private JTextField textFieldNombreProductoCategoria;
	private JTextField textFieldIdProductoActualizar;
	private JTextField textFieldNombreClienteAniadir;
	private JTextField textFieldEmailClienteAniadir;
	private JTextField textFieldTelfonoClienteAniadir;
	private JTextField textFieldIDCliente;
	private JTextField textFieldEmailClientePorEmail;
	private JTextField textField_7;
	private JTextField textFieldEliminarClienteID;
	private JTable tableClientes;

	public InterfazGrafica() {
		// Logo
		setIconImage(Toolkit.getDefaultToolkit().getImage("icono.png"));
		setTitle("Akihabara Market");
		setForeground(new Color(255, 255, 255));
		// setMaximumSize(new Dimension (940, 496));

		setFont(new Font("Noto Sans", Font.PLAIN, 14));
		setBackground(new Color(196, 25, 25));
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		JButton gestionClientesButton = new JButton("Clientes");
		gestionClientesButton.setBounds(0, 334, 211, 123);
		gestionClientesButton.setFont(new Font("Noto Sans", Font.BOLD, 14));
		gestionClientesButton.setBackground(new Color(196, 25, 25));
		gestionClientesButton.setForeground(new Color(255, 255, 255));
		gestionClientesButton.setFocusPainted(false);
		getContentPane().add(gestionClientesButton);

		JButton gestionInventarioButton = new JButton("Inventario");
		gestionInventarioButton.setBounds(0, 212, 211, 123);
		gestionInventarioButton.setForeground(new Color(255, 255, 255));
		gestionInventarioButton.setBackground(new Color(196, 25, 25));
		gestionInventarioButton.setFont(new Font("Noto Sans", Font.BOLD, 14));
		gestionInventarioButton.setFocusPainted(false);
		getContentPane().add(gestionInventarioButton);

		// Estilo general de pestañas personalizado
		UIManager.put("TabbedPane.selected", new Color(198, 25, 25));
		UIManager.put("TabbedPane.foreground", new Color(255, 255, 255));
		UIManager.put("TabbedPane.background", new Color(0, 0, 0));

		// Estilo general de pestañas personalizado del Asistente IA
		UIManager.put("tabbedPaneAsistenteIA.selected", new Color(198, 25, 25));
		UIManager.put("tabbedPaneAsistenteIA.foreground", new Color(255, 255, 255));
		UIManager.put("tabbedPaneAsistenteIA.background", new Color(0, 0, 0));

		// ############################################################################################################

		// PANEL DE GESTIÓN DEL INVENTARIO
		JPanel panelGestionProductos = new JPanel();
		panelGestionProductos.setBounds(209, 0, 734, 461);
		getContentPane().add(panelGestionProductos);
		panelGestionProductos.setLayout(null);

		JLabel logoLbl = new JLabel("");
		logoLbl.setBounds(9, 11, 190, 190);
		getContentPane().add(logoLbl);
		logoLbl.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/akihabaramarketlogo.png")).getImage()
				.getScaledInstance(190, 190, Image.SCALE_SMOOTH)));

		inventarioTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		inventarioTabbedPane.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		inventarioTabbedPane.setBounds(0, 0, 733, 461);
		inventarioTabbedPane.setBackground(new Color(255, 255, 255));
		panelGestionProductos.add(inventarioTabbedPane);

		// ############################################################################################################

		// PANEL AÑADIR PRODUCTO AL INVENTARIO
		JPanel aniadirPanel = new JPanel();
		aniadirPanel.setBackground(new Color(255, 255, 255));
		inventarioTabbedPane.addTab("Añadir", null, aniadirPanel, null);
		inventarioTabbedPane.setBackgroundAt(0, new Color(243, 163, 163));
		inventarioTabbedPane.setForegroundAt(0, new Color(255, 255, 255));
		// inventarioTabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
		aniadirPanel.setLayout(null);

		nombreTextField = new JTextField();
		nombreTextField.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		nombreTextField.setBounds(33, 112, 638, 25);
		aniadirPanel.add(nombreTextField);
		nombreTextField.setColumns(10);

		JLabel nombrelbl = new JLabel("Nombre");
		nombrelbl.setFont(new Font("Noto Sans", Font.BOLD, 14));
		nombrelbl.setBounds(33, 87, 71, 25);
		aniadirPanel.add(nombrelbl);

		JLabel Stocklbl = new JLabel("Stock");
		Stocklbl.setFont(new Font("Noto Sans", Font.BOLD, 14));
		Stocklbl.setBounds(455, 148, 71, 25);
		aniadirPanel.add(Stocklbl);

		categoriaTextField = new JTextField();
		categoriaTextField.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		categoriaTextField.setColumns(10);
		categoriaTextField.setBounds(33, 173, 249, 25);
		aniadirPanel.add(categoriaTextField);

		JLabel preciolbl = new JLabel("€");
		preciolbl.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		preciolbl.setBounds(405, 174, 40, 25);
		aniadirPanel.add(preciolbl);

		precioTextField = new JTextField();
		precioTextField.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		precioTextField.setColumns(10);
		precioTextField.setBounds(335, 173, 60, 25);
		aniadirPanel.add(precioTextField);

		JLabel categorialbl_1 = new JLabel("Categoria");
		categorialbl_1.setFont(new Font("Noto Sans", Font.BOLD, 14));
		categorialbl_1.setBounds(33, 148, 71, 25);
		aniadirPanel.add(categorialbl_1);

		JButton aniadirBtn = new JButton("Añadir");
		aniadirBtn.setForeground(new Color(255, 255, 255));

		/**
		 * Listener para el botón "Añadir".
		 * Obtiene los datos introducidos en los campos de texto, crear un nuevo producto y almacenarlo en la base de
		 * datos. Muestra mensajes de error si los campos están vacíos o hay datos
		 * inválidos.
		 */
		aniadirBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nombreProducto = nombreTextField.getText();
					String categoriaProducto = categoriaTextField.getText();
					double precioProducto = Double.parseDouble(precioTextField.getText());
					int stockProducto = Integer.parseInt(stockTextField.getText());

					if (nombreProducto != null || !nombreProducto.trim().isEmpty()) {
						ProductoOtaku p = new ProductoOtaku(nombreProducto, categoriaProducto, precioProducto,
								stockProducto);
						daoProductos.agregarProducto(p);
						JOptionPane.showMessageDialog(null, "Producto añadido.");
					} else {
						JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: Revisa los datos introducidos.");
				}
			}
		});

		aniadirBtn.setBackground(new Color(196, 25, 25));
		aniadirBtn.setFont(new Font("Noto Sans", Font.BOLD, 14));
		aniadirBtn.setBounds(546, 333, 125, 36);
		aniadirPanel.add(aniadirBtn);

		stockTextField = new JTextField();
		stockTextField.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		stockTextField.setColumns(10);
		stockTextField.setBounds(455, 173, 61, 25);
		aniadirPanel.add(stockTextField);

		JTextPane txtpnAniadirTitulo = new JTextPane();
		txtpnAniadirTitulo.setEditable(false);
		txtpnAniadirTitulo.setForeground(new Color(196, 25, 25));
		txtpnAniadirTitulo.setFont(new Font("Noto Sans", Font.BOLD, 20));
		txtpnAniadirTitulo.setText("Añadir producto nuevo");
		txtpnAniadirTitulo.setBounds(33, 33, 313, 36);
		aniadirPanel.add(txtpnAniadirTitulo);

		JLabel preciolbl_1 = new JLabel("Precio");
		preciolbl_1.setFont(new Font("Noto Sans", Font.BOLD, 14));
		preciolbl_1.setBounds(335, 148, 71, 25);
		aniadirPanel.add(preciolbl_1);

		JLabel lblUnidadesDisponibles = new JLabel("unidades disponibles");
		lblUnidadesDisponibles.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		lblUnidadesDisponibles.setBounds(526, 174, 145, 25);
		aniadirPanel.add(lblUnidadesDisponibles);

		// ############################################################################################################

		// PANEL CONSULTAR PRODUCTO POR ID
		JPanel consultarPorIDPanel = new JPanel();
		consultarPorIDPanel.setLayout(null);
		consultarPorIDPanel.setBackground(Color.WHITE);
		inventarioTabbedPane.addTab("Buscar por ID", null, consultarPorIDPanel, null);
		inventarioTabbedPane.setForegroundAt(1, new Color(255, 255, 255));
		inventarioTabbedPane.setBackgroundAt(1, new Color(243, 163, 163));

		JTextPane txtpnConsultarPorIDTitulo = new JTextPane();
		txtpnConsultarPorIDTitulo.setEditable(false);
		txtpnConsultarPorIDTitulo.setText("Consultar información de producto por ID");
		txtpnConsultarPorIDTitulo.setForeground(new Color(196, 25, 25));
		txtpnConsultarPorIDTitulo.setFont(new Font("Noto Sans", Font.BOLD, 20));
		txtpnConsultarPorIDTitulo.setBounds(33, 33, 527, 36);
		consultarPorIDPanel.add(txtpnConsultarPorIDTitulo);

		JLabel idConsultaLbl = new JLabel("ID del producto");
		idConsultaLbl.setFont(new Font("Noto Sans", Font.BOLD, 14));
		idConsultaLbl.setBounds(33, 80, 247, 25);
		consultarPorIDPanel.add(idConsultaLbl);

		consultarPorIDtextField = new JTextField();
		consultarPorIDtextField.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		consultarPorIDtextField.setColumns(10);
		consultarPorIDtextField.setBounds(33, 105, 247, 25);
		consultarPorIDPanel.add(consultarPorIDtextField);

		// Panel para mostrar resultado de consultar la información de producto por ID
		JPanel panelMostrarInformaciónProductoID;
		JLabel lblNombreResultado;
		JLabel lblCategoriaResultado;
		JLabel lblPrecioResultado;
		JLabel lblStockResultado;

		// Panel para mostrar resultado de consultar la información de producto por ID
		panelMostrarInformaciónProductoID = new JPanel();
		panelMostrarInformaciónProductoID.setLayout(null);
		panelMostrarInformaciónProductoID.setBounds(33, 181, 628, 224);
		panelMostrarInformaciónProductoID.setBackground(new Color(255, 255, 255));
		panelMostrarInformaciónProductoID.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		consultarPorIDPanel.add(panelMostrarInformaciónProductoID);

		// Etiquetas para mostrar los datos
		lblNombreResultado = new JLabel("");
		lblNombreResultado.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		lblNombreResultado.setBounds(110, 23, 510, 25);
		panelMostrarInformaciónProductoID.add(lblNombreResultado);

		lblCategoriaResultado = new JLabel("");
		lblCategoriaResultado.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		lblCategoriaResultado.setBounds(110, 71, 510, 25);
		panelMostrarInformaciónProductoID.add(lblCategoriaResultado);

		lblPrecioResultado = new JLabel("");
		lblPrecioResultado.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		lblPrecioResultado.setBounds(110, 114, 510, 25);
		panelMostrarInformaciónProductoID.add(lblPrecioResultado);

		lblStockResultado = new JLabel("");
		lblStockResultado.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		lblStockResultado.setBounds(110, 167, 510, 25);
		panelMostrarInformaciónProductoID.add(lblStockResultado);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblNombre.setBounds(20, 23, 78, 25);
		panelMostrarInformaciónProductoID.add(lblNombre);

		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblCategoria.setBounds(20, 71, 78, 25);
		panelMostrarInformaciónProductoID.add(lblCategoria);

		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblPrecio.setBounds(20, 119, 78, 25);
		panelMostrarInformaciónProductoID.add(lblPrecio);

		JLabel lblStock = new JLabel("Stock");
		lblStock.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblStock.setBounds(20, 167, 78, 25);
		panelMostrarInformaciónProductoID.add(lblStock);

		/**
		 * Listener para el botón "Buscar" por ID. Busca un producto en la base de datos
		 * por su ID e imprime los resultados en pantalla. Muestra mensajes si el campo
		 * está vacío, el ID es inválido o no se encuentra el producto.
		 */
		JButton btnConsultarPorID = new JButton("Buscar");
		btnConsultarPorID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idProducto = Integer.parseInt(consultarPorIDtextField.getText());

					if (consultarPorIDtextField.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El campo ID no puede estar vacío.");
						return;
					}

					ProductoOtaku producto = daoProductos.obtenerProductoPorId(idProducto);

					if (producto == null) {
						JOptionPane.showMessageDialog(null, "No se ha encontrado coincidencias.");
						// Limpiar etiquetas si no hay producto
						lblNombreResultado.setText("");
						lblCategoriaResultado.setText("");
						lblPrecioResultado.setText("");
						lblStockResultado.setText("");
						return;
					}

					// Mostrar datos del producto en el panel
					lblNombreResultado.setText(producto.getNombre());
					lblCategoriaResultado.setText(producto.getCategoria());
					lblPrecioResultado.setText(String.format("%.2f€", producto.getPrecio()));
					lblStockResultado.setText(Integer.toString(producto.getStock()));

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID inválido. Debe ser un número.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: Revisa los datos introducidos.");
				}
			}
		});

		btnConsultarPorID.setForeground(Color.WHITE);
		btnConsultarPorID.setFont(new Font("Noto Sans", Font.BOLD, 14));
		btnConsultarPorID.setBackground(new Color(196, 25, 25));
		btnConsultarPorID.setBounds(290, 94, 114, 36);
		consultarPorIDPanel.add(btnConsultarPorID);

		// ############################################################################################################

		// PANEL CONSULTAR PRODUCTO POR NOMBRE
		JPanel consultarPorNombrePanel = new JPanel();
		consultarPorNombrePanel.setBackground(new Color(255, 255, 255));
		inventarioTabbedPane.addTab("Buscar por nombre", null, consultarPorNombrePanel, null);
		inventarioTabbedPane.setForegroundAt(2, new Color(255, 255, 255));
		inventarioTabbedPane.setBackgroundAt(2, new Color(243, 163, 163));
		consultarPorNombrePanel.setLayout(null);

		JTextPane txtpnConsultarPorNombreTitulo = new JTextPane();
		txtpnConsultarPorNombreTitulo.setEditable(false);
		txtpnConsultarPorNombreTitulo.setBounds(33, 33, 527, 36);
		txtpnConsultarPorNombreTitulo.setText("Consultar información de producto por nombre");
		txtpnConsultarPorNombreTitulo.setForeground(new Color(196, 25, 25));
		txtpnConsultarPorNombreTitulo.setFont(new Font("Noto Sans", Font.BOLD, 20));
		consultarPorNombrePanel.add(txtpnConsultarPorNombreTitulo);

		JLabel nombreConsultaPorNombrelbl = new JLabel("Nombre");
		nombreConsultaPorNombrelbl.setFont(new Font("Noto Sans", Font.BOLD, 14));
		nombreConsultaPorNombrelbl.setBounds(33, 80, 71, 25);
		consultarPorNombrePanel.add(nombreConsultaPorNombrelbl);

		nombreConsultaPorNombreTextField = new JTextField();
		nombreConsultaPorNombreTextField.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		nombreConsultaPorNombreTextField.setColumns(10);
		nombreConsultaPorNombreTextField.setBounds(33, 105, 494, 25);
		consultarPorNombrePanel.add(nombreConsultaPorNombreTextField);
		JButton btnBuscarPorNombre = new JButton("Buscar");

		/**
		 * Listener para el botón "Buscar" por nombre. Busca productos cuyo nombre
		 * contenga el texto ingresado y los muestra en una tabla. Notifica al usuario
		 * si no se encuentran coincidencias o si hay errores.
		 */
		btnBuscarPorNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nombreBuscar = nombreConsultaPorNombreTextField.getText();

					if (nombreBuscar.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El campo de nombre no puede estar vacío.");
						return;
					}

					List<ProductoOtaku> lista = daoProductos.buscarProductosPorNombre(nombreBuscar);
					if (lista == null || lista.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No se ha encontrado coincidencias.");
						return;
					}

					cargarResultadosEnTablaNombre(lista);
					JOptionPane.showMessageDialog(null, "Se ha encontrado " + lista.size() + " coincidencia/s.");

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: Revisa los datos introducidos.");
				}
			}
		});

		btnBuscarPorNombre.setForeground(Color.WHITE);
		btnBuscarPorNombre.setFont(new Font("Noto Sans", Font.BOLD, 14));
		btnBuscarPorNombre.setBackground(new Color(196, 25, 25));
		btnBuscarPorNombre.setBounds(548, 94, 114, 36);
		consultarPorNombrePanel.add(btnBuscarPorNombre);

		// TABLA QUE MUESTRA RESULTADOS DE BUSCAR UN PRODUCTO POR NOMBRE
		tableConsultaProductoPorNombre = new JTable();
		tableConsultaProductoPorNombre.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		tableConsultaProductoPorNombre.setForeground(new Color(0, 0, 0));
		tableConsultaProductoPorNombre.setBackground(new Color(255, 255, 255));
		tableConsultaProductoPorNombre.setBounds(33, 188, 623, 203);
		consultarPorNombrePanel.add(tableConsultaProductoPorNombre);

		// Crear un ScrollPane para poder visualizar todos los registros
		JScrollPane scrollPaneBuscarPorNombre = new JScrollPane(tableConsultaProductoPorNombre);
		scrollPaneBuscarPorNombre.setBounds(33, 161, 629, 238);
		consultarPorNombrePanel.add(scrollPaneBuscarPorNombre);

		// ############################################################################################################

		// PANEL VER INVENTARIO COMPLETO
		JPanel verInventarioPanel = new JPanel();
		verInventarioPanel.setBackground(new Color(255, 255, 255));
		inventarioTabbedPane.addTab("Ver inventario", null, verInventarioPanel, null);
		inventarioTabbedPane.setForegroundAt(3, new Color(255, 255, 255));
		inventarioTabbedPane.setForegroundAt(3, new Color(255, 255, 255));
		inventarioTabbedPane.setBackgroundAt(3, new Color(243, 163, 163));
		verInventarioPanel.setLayout(null);

		// Titulo del panel de inventario completo
		JTextPane txtpnListadoCompletoDe = new JTextPane();
		txtpnListadoCompletoDe.setEditable(false);
		txtpnListadoCompletoDe.setBounds(33, 33, 527, 36);
		txtpnListadoCompletoDe.setText("Listado completo de productos");
		txtpnListadoCompletoDe.setForeground(new Color(196, 25, 25));
		txtpnListadoCompletoDe.setFont(new Font("Noto Sans", Font.BOLD, 20));
		verInventarioPanel.add(txtpnListadoCompletoDe);

		// Tabla que carga el inventario completo
		table = new JTable();
		table.setBounds(43, 68, 505, 275);
		verInventarioPanel.add(table);

		// Crear un ScrollPane para poder visualizar todos los registros
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(33, 68, 625, 331);
		verInventarioPanel.add(scrollPane);

		List<ProductoOtaku> productos = daoProductos.obtenerTodosLosProductos();
		cargarInventarioEnTabla(productos);

		/**
		 * Listener para el cambio de pestañas en el inventario. Si la pestaña activa es
		 * "Ver inventario", recarga todos los productos desde la base de datos.
		 */
		inventarioTabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int id = inventarioTabbedPane.getSelectedIndex();
				String titulo = inventarioTabbedPane.getTitleAt(id);
				if (titulo.equals("Ver inventario")) {
					List<ProductoOtaku> productos = daoProductos.obtenerTodosLosProductos();
					cargarInventarioEnTabla(productos);
				}
			}
		});

		// ############################################################################################################

		JPanel actualizarPanel = new JPanel();
		actualizarPanel.setBackground(new Color(255, 255, 255));
		inventarioTabbedPane.addTab("Actualizar", null, actualizarPanel, null);
		actualizarPanel.setLayout(null);

		JTextPane txtpnActualizarProductoTitulo = new JTextPane();
		txtpnActualizarProductoTitulo.setText("Actualizar producto");
		txtpnActualizarProductoTitulo.setForeground(new Color(196, 25, 25));
		txtpnActualizarProductoTitulo.setFont(new Font("Noto Sans", Font.BOLD, 20));
		txtpnActualizarProductoTitulo.setEditable(false);
		txtpnActualizarProductoTitulo.setBounds(63, 164, 204, 36);
		actualizarPanel.add(txtpnActualizarProductoTitulo);

		JLabel idProductoActualizarlbl = new JLabel("Id del producto");
		idProductoActualizarlbl.setFont(new Font("Noto Sans", Font.BOLD, 14));
		idProductoActualizarlbl.setBounds(111, 210, 108, 25);
		actualizarPanel.add(idProductoActualizarlbl);
		JButton BtnModificarNombre = new JButton("Editar nombre");

		/**
		 * Listener para el botón "Editar nombre" en la pestaña de actualización.
		 * Permite al usuario modificar el nombre de un producto existente usando su ID.
		 */
		BtnModificarNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(textFieldIdProductoActualizar.getText().trim());
					ProductoOtaku producto = daoProductos.obtenerProductoPorId(id);

					if (producto == null) {
						JOptionPane.showMessageDialog(null, "No existe un producto con ese ID.");
						return;
					}

					String nuevoNombre = JOptionPane.showInputDialog(
							"NOMBRE ACTUAL: " + producto.getNombre() + "\n\nIntroduce el nuevo nombre del producto:");
					if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
						return;
					}

					producto.setNombre(nuevoNombre);
					if (daoProductos.actualizarProducto(producto)) {
						JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.");
					} else {
						JOptionPane.showMessageDialog(null, "Error al actualizar el producto.");
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID inválido. Introduce un número.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: Revisa los datos introducidos.");
				}
			}
		});

		BtnModificarNombre.setForeground(Color.WHITE);
		BtnModificarNombre.setFont(new Font("Noto Sans", Font.BOLD, 14));
		BtnModificarNombre.setBackground(new Color(196, 25, 25));
		BtnModificarNombre.setBounds(356, 58, 190, 36);
		actualizarPanel.add(BtnModificarNombre);

		textFieldIdProductoActualizar = new JTextField();
		textFieldIdProductoActualizar.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textFieldIdProductoActualizar.setColumns(10);
		textFieldIdProductoActualizar.setBounds(111, 235, 108, 25);
		actualizarPanel.add(textFieldIdProductoActualizar);

		JButton BtnEditarCategoria = new JButton("Editar categoría");
		BtnEditarCategoria.setForeground(Color.WHITE);
		BtnEditarCategoria.setFont(new Font("Noto Sans", Font.BOLD, 14));
		BtnEditarCategoria.setBackground(new Color(196, 25, 25));
		BtnEditarCategoria.setBounds(356, 150, 190, 36);
		actualizarPanel.add(BtnEditarCategoria);

		/**
		 * Listener para el botón "Editar categoría". Solicita al usuario una nueva
		 * categoría para actualizar un producto ya existente.
		 */
		BtnEditarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(textFieldIdProductoActualizar.getText().trim());
					ProductoOtaku producto = daoProductos.obtenerProductoPorId(id);

					if (producto == null) {
						JOptionPane.showMessageDialog(null, "No existe un producto con ese ID.");
						return;
					}

					String nuevaCategoria = JOptionPane.showInputDialog("CATEGORÍA ACTUAL: " + producto.getCategoria()
							+ "\n\nIntroduce la nueva categoría del producto:");
					if (nuevaCategoria == null || nuevaCategoria.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "La categoría no puede estar vacía.");
						return;
					}

					producto.setCategoria(nuevaCategoria);
					if (daoProductos.actualizarProducto(producto)) {
						JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.");
					} else {
						JOptionPane.showMessageDialog(null, "Error al actualizar el producto.");
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID inválido. Introduce un número.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: Revisa los datos introducidos.");
				}
			}
		});

		JButton BtnEditarStock = new JButton("Editar stock");
		BtnEditarStock.setForeground(Color.WHITE);
		BtnEditarStock.setFont(new Font("Noto Sans", Font.BOLD, 14));
		BtnEditarStock.setBackground(new Color(196, 25, 25));
		BtnEditarStock.setBounds(356, 242, 190, 36);
		actualizarPanel.add(BtnEditarStock);
		/**
		 * Listener para el botón "Editar stock". Solicita un nuevo valor para el stock
		 * de un producto, validando los datos antes de actualizar.
		 */
		BtnEditarStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(textFieldIdProductoActualizar.getText().trim());
					ProductoOtaku producto = daoProductos.obtenerProductoPorId(id);

					if (producto == null) {
						JOptionPane.showMessageDialog(null, "No existe un producto con ese ID.");
						return;
					}

					String nuevoStockStr = JOptionPane.showInputDialog(
							"STOCK ACTUAL: " + producto.getStock() + "\n\nIntroduce el nuevo stock del producto:");
					if (nuevoStockStr == null || nuevoStockStr.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El stock no puede estar vacío.");
						return;
					}

					int nuevoStock = Integer.parseInt(nuevoStockStr);
					producto.setStock(nuevoStock);
					if (daoProductos.actualizarProducto(producto)) {
						JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.");
					} else {
						JOptionPane.showMessageDialog(null, "Error al actualizar el producto.");
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Stock o ID inválido. Deben ser números válidos.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: Revisa los datos introducidos.");
				}
			}
		});

		JButton BtnEditarPrecio = new JButton("Editar precio");
		BtnEditarPrecio.setForeground(Color.WHITE);
		BtnEditarPrecio.setFont(new Font("Noto Sans", Font.BOLD, 14));
		BtnEditarPrecio.setBackground(new Color(196, 25, 25));
		BtnEditarPrecio.setBounds(356, 334, 190, 36);
		actualizarPanel.add(BtnEditarPrecio);

		/**
		 * Listener para el botón "Editar precio". Permite al usuario modificar el
		 * precio de un producto mediante un input.
		 */
		BtnEditarPrecio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(textFieldIdProductoActualizar.getText().trim());
					ProductoOtaku producto = daoProductos.obtenerProductoPorId(id);

					if (producto == null) {
						JOptionPane.showMessageDialog(null, "No existe un producto con ese ID.");
						return;
					}

					String nuevoPrecioStr = JOptionPane.showInputDialog(
							"PRECIO ACTUAL: " + producto.getPrecio() + "\n\nIntroduce el nuevo precio del producto:");
					if (nuevoPrecioStr == null || nuevoPrecioStr.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El precio no puede estar vacío.");
						return;
					}

					double nuevoPrecio = Double.valueOf(nuevoPrecioStr);
					producto.setPrecio(nuevoPrecio);
					if (daoProductos.actualizarProducto(producto)) {
						JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.");
					} else {
						JOptionPane.showMessageDialog(null, "Error al actualizar el producto.");
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Precio o ID inválido. Deben ser números válidos.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: Revisa los datos introducidos.");
				}
			}
		});
		inventarioTabbedPane.setForegroundAt(4, new Color(255, 255, 255));
		inventarioTabbedPane.setBackgroundAt(4, new Color(243, 163, 163));

		// ############################################################################################################

		JPanel eliminarPanel = new JPanel();
		eliminarPanel.setBackground(new Color(255, 255, 255));
		inventarioTabbedPane.addTab("Eliminar", null, eliminarPanel, null);
		eliminarPanel.setLayout(null);

		JTextPane txtpnEliminarUnProducto = new JTextPane();
		txtpnEliminarUnProducto.setEditable(false);
		txtpnEliminarUnProducto.setBounds(33, 33, 527, 36);
		txtpnEliminarUnProducto.setText("Eliminar un producto");
		txtpnEliminarUnProducto.setForeground(new Color(196, 25, 25));
		txtpnEliminarUnProducto.setFont(new Font("Dialog", Font.BOLD, 20));
		eliminarPanel.add(txtpnEliminarUnProducto);

		JLabel LblEliminarProducto = new JLabel("ID del producto");
		LblEliminarProducto.setFont(new Font("Noto Sans", Font.BOLD, 14));
		LblEliminarProducto.setBounds(33, 80, 126, 25);
		eliminarPanel.add(LblEliminarProducto);

		textFieldEliminarProducto = new JTextField();
		textFieldEliminarProducto.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textFieldEliminarProducto.setColumns(10);
		textFieldEliminarProducto.setBounds(33, 105, 114, 25);
		eliminarPanel.add(textFieldEliminarProducto);

		JButton btnEliminarPorID = new JButton("Eliminar");
		/**
		 * Listener para el botón "Eliminar producto". Elimina un producto de la base de
		 * datos si se confirma la operación.
		 */
		btnEliminarPorID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idProducto = Integer.parseInt(textFieldEliminarProducto.getText());

					if (textFieldEliminarProducto.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El campo ID no puede estar vacío.");
						return;
					}

					ProductoOtaku producto = daoProductos.obtenerProductoPorId(idProducto);

					if (producto == null) {
						JOptionPane.showMessageDialog(null, "No se ha encontrado coincidencias.");
						return;
					}

					int confirmacion = JOptionPane.showConfirmDialog(null,
							"¿Seguro que deseas eliminar el producto con ID: " + idProducto + "?",
							"Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

					if (confirmacion == JOptionPane.YES_OPTION) {
						// ELIMINAR EL PRODUCTO
						daoProductos.eliminarProducto(idProducto);
						JOptionPane.showMessageDialog(null, "Eliminado.");
					}

					if (confirmacion == JOptionPane.NO_OPTION) {
						// Cancelado. No elimino el producto
						JOptionPane.showMessageDialog(null, "Cancelado.");
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID inválido.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: Revisa los datos introducidos.");
				}
			}
		});
		btnEliminarPorID.setForeground(Color.WHITE);
		btnEliminarPorID.setFont(new Font("Noto Sans", Font.BOLD, 14));
		btnEliminarPorID.setBackground(new Color(196, 25, 25));
		btnEliminarPorID.setBounds(33, 141, 114, 36);
		eliminarPanel.add(btnEliminarPorID);
		inventarioTabbedPane.setForegroundAt(5, new Color(255, 255, 255));
		inventarioTabbedPane.setBackgroundAt(5, new Color(243, 163, 163));

		// ############################################################################################################

		// PANEL PESTAÑA ASISTENTE DE IA - GENERAR DESCRIPCIÓN POR ID DE PRODUCTO
		JPanel asistenteIAPanel = new JPanel();
		asistenteIAPanel.setBackground(new Color(255, 255, 255));
		inventarioTabbedPane.addTab("Asistente IA", null, asistenteIAPanel, null);
		asistenteIAPanel.setLayout(null);

		JTextPane txtpnAsistenteDeIa = new JTextPane();
		txtpnAsistenteDeIa.setText("Asistente de IA");
		txtpnAsistenteDeIa.setForeground(new Color(196, 25, 25));
		txtpnAsistenteDeIa.setFont(new Font("Noto Sans", Font.BOLD, 20));
		txtpnAsistenteDeIa.setEditable(false);
		txtpnAsistenteDeIa.setBounds(33, 33, 313, 36);
		asistenteIAPanel.add(txtpnAsistenteDeIa);

		JPanel panelAsistenteIA = new JPanel();
		panelAsistenteIA.setBackground(new Color(255, 255, 255));
		panelAsistenteIA.setBounds(33, 95, 628, 305);
		asistenteIAPanel.add(panelAsistenteIA);
		panelAsistenteIA.setLayout(null);

		JTabbedPane tabbedPaneAsistenteIA = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneAsistenteIA.setBackground(new Color(255, 255, 255));
		tabbedPaneAsistenteIA.setBounds(0, 5, 628, 300);
		panelAsistenteIA.add(tabbedPaneAsistenteIA);

		JPanel panelGenerarDescripcionesIA = new JPanel();
		panelGenerarDescripcionesIA.setBackground(new Color(255, 255, 255));
		tabbedPaneAsistenteIA.addTab("Generar descripciones", null, panelGenerarDescripcionesIA, null);
		tabbedPaneAsistenteIA.setForegroundAt(0, new Color(255, 255, 255));
		tabbedPaneAsistenteIA.setBackgroundAt(0, new Color(243, 163, 163));
		panelGenerarDescripcionesIA.setLayout(null);

		textFieldIdAsistenteIA = new JTextField();
		textFieldIdAsistenteIA.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textFieldIdAsistenteIA.setColumns(10);
		textFieldIdAsistenteIA.setBounds(10, 44, 63, 25);
		panelGenerarDescripcionesIA.add(textFieldIdAsistenteIA);

		JLabel lblIdProductoAsistenteIA = new JLabel("Id del Producto");
		lblIdProductoAsistenteIA.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblIdProductoAsistenteIA.setBounds(10, 11, 107, 25);
		panelGenerarDescripcionesIA.add(lblIdProductoAsistenteIA);

		// Area de texto para la descripción generada por la IA
		JTextArea textAreaDescripciónGenerada = new JTextArea();
		textAreaDescripciónGenerada.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textAreaDescripciónGenerada.setBounds(10, 126, 592, 132);
		textAreaDescripciónGenerada.setLineWrap(true);
		textAreaDescripciónGenerada.setWrapStyleWord(true);
		textAreaDescripciónGenerada.setEditable(false);

		// Por si la descripción sobrepasase el espacio del JTextArea
		JScrollPane scrollDescripcion = new JScrollPane(textAreaDescripciónGenerada);
		scrollDescripcion.setBounds(10, 126, 592, 132);
		panelGenerarDescripcionesIA.add(scrollDescripcion);

		JButton BtnGenerarDescripcion = new JButton("Generar descripción");
		/**
		 * Listener para el botón "Generar descripción" del asistente IA. Obtiene la
		 * descripción de un producto a través de un modelo de lenguaje basado en IA.
		 */
		BtnGenerarDescripcion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idProducto = Integer.parseInt(textFieldIdAsistenteIA.getText());

					if (textFieldIdAsistenteIA.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El campo ID no puede estar vacío.");
						return;
					}

					ProductoOtaku producto = daoProductos.obtenerProductoPorId(idProducto);

					if (producto == null) {
						JOptionPane.showMessageDialog(null,
								"No se ha encontrado coincidencias de productos con esa ID.");
						return;
					} else {
						// Muestra la respuesta del asistente de IA
						String prompt = llmService.obtenerDescripcion(idProducto);
						String resp = llmService.llmService(prompt);
						textAreaDescripciónGenerada.setText(resp);
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID inválido.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: No se ha podido generar la descripción.");
				}
			}
		});

		BtnGenerarDescripcion.setForeground(Color.WHITE);
		BtnGenerarDescripcion.setFont(new Font("Noto Sans", Font.BOLD, 14));
		BtnGenerarDescripcion.setBackground(new Color(196, 25, 25));
		BtnGenerarDescripcion.setBounds(10, 80, 193, 36);
		panelGenerarDescripcionesIA.add(BtnGenerarDescripcion);

		// ############################################################################################################

		// PANEL PESTAÑA ASISTENTE DE IA - GENERAR CATEGORIA POR NOMBRE DE PRODUCTO
		JPanel panelGenerarCategoria = new JPanel();
		panelGenerarCategoria.setForeground(new Color(0, 0, 0));
		panelGenerarCategoria.setBackground(new Color(255, 255, 255));
		tabbedPaneAsistenteIA.addTab("GenerarCategoria", null, panelGenerarCategoria, null);
		panelGenerarCategoria.setLayout(null);

		JLabel lblNombreProductoAsistenteI = new JLabel("Nombre del producto");
		lblNombreProductoAsistenteI.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblNombreProductoAsistenteI.setBounds(10, 11, 193, 25);
		panelGenerarCategoria.add(lblNombreProductoAsistenteI);

		textFieldNombreProductoCategoria = new JTextField();
		textFieldNombreProductoCategoria.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textFieldNombreProductoCategoria.setColumns(10);
		textFieldNombreProductoCategoria.setBounds(10, 44, 193, 25);
		panelGenerarCategoria.add(textFieldNombreProductoCategoria);

		JTextArea textAreaCategoriaGeneradaIA = new JTextArea();
		textAreaCategoriaGeneradaIA.setWrapStyleWord(true);
		textAreaCategoriaGeneradaIA.setLineWrap(true);
		textAreaCategoriaGeneradaIA.setFont(new Font("Noto Sans", Font.PLAIN, 47));
		textAreaCategoriaGeneradaIA.setEditable(false);
		textAreaCategoriaGeneradaIA.setBounds(10, 127, 375, 72);
		panelGenerarCategoria.add(textAreaCategoriaGeneradaIA);
		tabbedPaneAsistenteIA.setForegroundAt(1, new Color(255, 255, 255));
		tabbedPaneAsistenteIA.setBackgroundAt(1, new Color(243, 163, 163));
		tabbedPaneAsistenteIA.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		inventarioTabbedPane.setForegroundAt(6, new Color(255, 255, 255));
		inventarioTabbedPane.setBackgroundAt(6, new Color(243, 163, 163));

		JButton btnGenerarCategoria = new JButton("Generar categoría");
		/**
		 * Listener para el botón "Generar categoría" del asistente IA. Solicita al
		 * modelo de IA que sugiera una categoría en base al nombre del producto.
		 */
		btnGenerarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nombreProducto = textFieldNombreProductoCategoria.getText();

					if (nombreProducto.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
						return;
					} else {
						// Muestra la respuesta del asistente de IA
						String prompt = llmService.obtenerCategoria(nombreProducto);
						String resp = llmService.llmService(prompt);
						textAreaCategoriaGeneradaIA.setText(resp);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: No se ha podido generar la categoría.");
				}
			}
		});

		btnGenerarCategoria.setForeground(Color.WHITE);
		btnGenerarCategoria.setFont(new Font("Dialog", Font.BOLD, 14));
		btnGenerarCategoria.setBackground(new Color(196, 25, 25));
		btnGenerarCategoria.setBounds(10, 80, 193, 36);
		panelGenerarCategoria.add(btnGenerarCategoria);

		// ##################################################################################################################
		// ##################################################################################################################

		// PANEL GESTIÓN DE CLIENTES
		JPanel panelGestionClientes = new JPanel();
		panelGestionClientes.setBounds(209, 0, 734, 461);
		panelGestionClientes.setLayout(null);
		panelGestionClientes.setBackground(Color.WHITE);
		panelGestionClientes.setVisible(false);
		getContentPane().add(panelGestionClientes);

		clientesTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		clientesTabbedPane.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		clientesTabbedPane.setBackground(new Color(255, 255, 255));
		clientesTabbedPane.setBounds(0, 0, 734, 461);
		panelGestionClientes.add(clientesTabbedPane);

		JPanel aniadirClientePanel = new JPanel();
		aniadirClientePanel.setForeground(new Color(0, 0, 0));
		aniadirClientePanel.setBackground(new Color(255, 255, 255));
		clientesTabbedPane.addTab("Añadir", null, aniadirClientePanel, null);
		clientesTabbedPane.setBackgroundAt(0, new Color(243, 163, 163));
		aniadirClientePanel.setLayout(null);

		JTextPane txtpnTituloCliente = new JTextPane();
		txtpnTituloCliente.setForeground(new Color(196, 25, 25));
		txtpnTituloCliente.setBounds(33, 33, 262, 32);
		txtpnTituloCliente.setText("Añadir un nuevo cliente");
		txtpnTituloCliente.setFont(new Font("Noto Sans", Font.BOLD, 20));
		txtpnTituloCliente.setEditable(false);
		txtpnTituloCliente.setBackground(Color.WHITE);
		aniadirClientePanel.add(txtpnTituloCliente);

		textFieldNombreClienteAniadir = new JTextField();
		textFieldNombreClienteAniadir.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textFieldNombreClienteAniadir.setBounds(33, 104, 285, 30);
		aniadirClientePanel.add(textFieldNombreClienteAniadir);
		textFieldNombreClienteAniadir.setColumns(10);

		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(33, 79, 82, 20);
		aniadirClientePanel.add(lblNewLabel);

		textFieldEmailClienteAniadir = new JTextField();
		textFieldEmailClienteAniadir.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textFieldEmailClienteAniadir.setColumns(10);
		textFieldEmailClienteAniadir.setBounds(33, 169, 449, 30);
		aniadirClientePanel.add(textFieldEmailClienteAniadir);

		JLabel lblEmailClienteAniadir = new JLabel("Email");
		lblEmailClienteAniadir.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblEmailClienteAniadir.setBackground(Color.WHITE);
		lblEmailClienteAniadir.setBounds(33, 145, 82, 23);
		aniadirClientePanel.add(lblEmailClienteAniadir);

		textFieldTelfonoClienteAniadir = new JTextField();
		textFieldTelfonoClienteAniadir.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textFieldTelfonoClienteAniadir.setColumns(10);
		textFieldTelfonoClienteAniadir.setBounds(33, 235, 285, 30);
		aniadirClientePanel.add(textFieldTelfonoClienteAniadir);

		JLabel lblTelefonoClienteAniadir = new JLabel("Teléfono");
		lblTelefonoClienteAniadir.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblTelefonoClienteAniadir.setBackground(Color.WHITE);
		lblTelefonoClienteAniadir.setBounds(33, 210, 82, 24);
		aniadirClientePanel.add(lblTelefonoClienteAniadir);

		JButton btnNewButton = new JButton("Añadir cliente");
		/**
		 * Listener para el botón "Añadir cliente".
		 * 
		 * Valida los campos del formulario, comprueba la validez del email
		 * y registra un nuevo cliente en la base de datos si todo es correcto.
		 */

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreCliente = textFieldNombreClienteAniadir.getText().trim();
				String emailCliente = textFieldEmailClienteAniadir.getText().trim();
				String telefonoCliente = textFieldTelfonoClienteAniadir.getText().trim();

				if (nombreCliente.isEmpty() || emailCliente.isEmpty() || telefonoCliente.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Por favor, rellena todos los campos.");
					return;
				}

				if (!daoClientes.esEmailValido(emailCliente)) {
					JOptionPane.showMessageDialog(null, "El email introducido no es válido.");
					return;
				}

				ClienteOtaku cliente = new ClienteOtaku(nombreCliente, emailCliente, telefonoCliente, LocalDate.now());
				daoClientes.agregarCliente(cliente);

				JOptionPane.showMessageDialog(null, "Cliente añadido correctamente.");

				textFieldNombreClienteAniadir.setText("");
				textFieldEmailClienteAniadir.setText("");
				textFieldTelfonoClienteAniadir.setText("");
			}
		});

		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(128, 0, 0));
		btnNewButton.setFont(new Font("Noto Sans", Font.BOLD, 14));
		btnNewButton.setBounds(529, 369, 138, 32);
		aniadirClientePanel.add(btnNewButton);

		JPanel buscarClientePorIDpanel = new JPanel();
		buscarClientePorIDpanel.setBackground(new Color(255, 255, 255));
		clientesTabbedPane.addTab("Buscar por ID", null, buscarClientePorIDpanel, null);
		clientesTabbedPane.setBackgroundAt(1, new Color(243, 163, 163));
		buscarClientePorIDpanel.setLayout(null);

		JTextPane txtpnConsultarClientePorID = new JTextPane();
		txtpnConsultarClientePorID.setText("Consultar cliente por ID");
		txtpnConsultarClientePorID.setForeground(new Color(196, 25, 25));
		txtpnConsultarClientePorID.setFont(new Font("Noto Sans", Font.BOLD, 20));
		txtpnConsultarClientePorID.setEditable(false);
		txtpnConsultarClientePorID.setBackground(Color.WHITE);
		txtpnConsultarClientePorID.setBounds(33, 33, 262, 32);
		buscarClientePorIDpanel.add(txtpnConsultarClientePorID);

		JLabel lblIdDelCliente = new JLabel("ID del cliente");
		lblIdDelCliente.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblIdDelCliente.setBackground(Color.WHITE);
		lblIdDelCliente.setBounds(33, 76, 150, 20);
		buscarClientePorIDpanel.add(lblIdDelCliente);

		textFieldIDCliente = new JTextField();
		textFieldIDCliente.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textFieldIDCliente.setColumns(10);
		textFieldIDCliente.setBounds(33, 101, 96, 30);
		buscarClientePorIDpanel.add(textFieldIDCliente);

		JLabel lblNombreClientePorID = new JLabel("Nombre");
		lblNombreClientePorID.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblNombreClientePorID.setBackground(Color.WHITE);
		lblNombreClientePorID.setBounds(33, 184, 96, 30);
		buscarClientePorIDpanel.add(lblNombreClientePorID);

		JLabel lblEmailClientePorId = new JLabel("Email");
		lblEmailClientePorId.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblEmailClientePorId.setBackground(Color.WHITE);
		lblEmailClientePorId.setBounds(33, 229, 96, 34);
		buscarClientePorIDpanel.add(lblEmailClientePorId);

		JLabel lblTelfonoClientePorID = new JLabel("Teléfono");
		lblTelfonoClientePorID.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblTelfonoClientePorID.setBackground(Color.WHITE);
		lblTelfonoClientePorID.setBounds(33, 280, 96, 30);
		buscarClientePorIDpanel.add(lblTelfonoClientePorID);

		JLabel lblFechaDeRegistro = new JLabel("Fecha de registro");
		lblFechaDeRegistro.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblFechaDeRegistro.setBackground(Color.WHITE);
		lblFechaDeRegistro.setBounds(33, 332, 138, 32);
		buscarClientePorIDpanel.add(lblFechaDeRegistro);

		JTextArea textAreaNombreClienteId = new JTextArea();
		textAreaNombreClienteId.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textAreaNombreClienteId.setBounds(177, 184, 184, 30);
		buscarClientePorIDpanel.add(textAreaNombreClienteId);

		JTextArea textAreaEmailClienteID = new JTextArea();
		textAreaEmailClienteID.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textAreaEmailClienteID.setBounds(177, 229, 184, 30);
		buscarClientePorIDpanel.add(textAreaEmailClienteID);

		JTextArea textAreaTelefonoClienteID = new JTextArea();
		textAreaTelefonoClienteID.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textAreaTelefonoClienteID.setBounds(181, 280, 184, 30);
		buscarClientePorIDpanel.add(textAreaTelefonoClienteID);

		JTextArea textAreaFechaRegistroClienteID = new JTextArea();
		textAreaFechaRegistroClienteID.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textAreaFechaRegistroClienteID.setBounds(181, 332, 184, 30);
		buscarClientePorIDpanel.add(textAreaFechaRegistroClienteID);

		JButton btnBuscarCliente = new JButton("Buscar cliente");
		btnBuscarCliente.setForeground(Color.WHITE);
		btnBuscarCliente.setFont(new Font("Noto Sans", Font.BOLD, 14));
		btnBuscarCliente.setBackground(new Color(128, 0, 0));
		btnBuscarCliente.setBounds(157, 99, 138, 32);
		buscarClientePorIDpanel.add(btnBuscarCliente);
		
		/**
		 * Listener para el botón "Buscar cliente por ID".
		 * 
		 * Busca y muestra los datos de un cliente registrado a partir del ID introducido.
		 * Si el cliente no existe, muestra un mensaje de advertencia.
		 */

		btnBuscarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String idTexto = textFieldIDCliente.getText().trim();
					if (idTexto.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Por favor, introduce un ID.");
						return;
					}

					int id = Integer.parseInt(idTexto);
					ClienteOtaku cliente = daoClientes.obtenerClientePorId(id);

					if (cliente == null) {
						JOptionPane.showMessageDialog(null, "No se ha encontrado ningún cliente con ese ID.");
						return;
					}

					// Mostrar datos en los JTextArea
					textAreaNombreClienteId.setText(cliente.getNombre());
					textAreaEmailClienteID.setText(cliente.getEmail());
					textAreaTelefonoClienteID.setText(cliente.getTelefono());
					textAreaFechaRegistroClienteID.setText(cliente.getFechaRegistro().toString());

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID inválido. Debe ser un número.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR al buscar cliente: " + ex.getMessage());
				}
			}
		});

		JPanel buscarClientePorEmailPanel = new JPanel();
		buscarClientePorEmailPanel.setBackground(new Color(255, 255, 255));
		clientesTabbedPane.addTab("Buscar por email", null, buscarClientePorEmailPanel, null);
		clientesTabbedPane.setBackgroundAt(2, new Color(243, 163, 163));
		buscarClientePorEmailPanel.setLayout(null);

		JTextPane txtpnConsultarClientePor = new JTextPane();
		txtpnConsultarClientePor.setText("Consultar cliente por email");
		txtpnConsultarClientePor.setForeground(new Color(196, 25, 25));
		txtpnConsultarClientePor.setFont(new Font("Noto Sans", Font.BOLD, 20));
		txtpnConsultarClientePor.setEditable(false);
		txtpnConsultarClientePor.setBackground(Color.WHITE);
		txtpnConsultarClientePor.setBounds(33, 33, 350, 32);
		buscarClientePorEmailPanel.add(txtpnConsultarClientePor);

		JLabel lblEmailDelCliente = new JLabel("Email del cliente");
		lblEmailDelCliente.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblEmailDelCliente.setBackground(Color.WHITE);
		lblEmailDelCliente.setBounds(33, 76, 150, 20);
		buscarClientePorEmailPanel.add(lblEmailDelCliente);

		textFieldEmailClientePorEmail = new JTextField();
		textFieldEmailClientePorEmail.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textFieldEmailClientePorEmail.setColumns(10);
		textFieldEmailClientePorEmail.setBounds(33, 101, 464, 30);
		buscarClientePorEmailPanel.add(textFieldEmailClientePorEmail);

		JLabel lblNombreClientePorID_1 = new JLabel("Nombre");
		lblNombreClientePorID_1.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblNombreClientePorID_1.setBackground(Color.WHITE);
		lblNombreClientePorID_1.setBounds(33, 184, 150, 30);
		buscarClientePorEmailPanel.add(lblNombreClientePorID_1);

		JTextArea textAreaNombreClienteId_1 = new JTextArea();
		textAreaNombreClienteId_1.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textAreaNombreClienteId_1.setBounds(177, 184, 184, 30);
		buscarClientePorEmailPanel.add(textAreaNombreClienteId_1);

		JLabel lblEmailClientePorId_1 = new JLabel("Email");
		lblEmailClientePorId_1.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblEmailClientePorId_1.setBackground(Color.WHITE);
		lblEmailClientePorId_1.setBounds(33, 229, 150, 30);
		buscarClientePorEmailPanel.add(lblEmailClientePorId_1);

		JTextArea textAreaEmailClienteID_1 = new JTextArea();
		textAreaEmailClienteID_1.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textAreaEmailClienteID_1.setBounds(177, 229, 184, 30);
		buscarClientePorEmailPanel.add(textAreaEmailClienteID_1);

		JLabel lblTelfonoClientePorID_1 = new JLabel("Teléfono");
		lblTelfonoClientePorID_1.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblTelfonoClientePorID_1.setBackground(Color.WHITE);
		lblTelfonoClientePorID_1.setBounds(33, 280, 150, 30);
		buscarClientePorEmailPanel.add(lblTelfonoClientePorID_1);

		JTextArea textAreaTelefonoClienteID_1 = new JTextArea();
		textAreaTelefonoClienteID_1.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textAreaTelefonoClienteID_1.setBounds(177, 280, 184, 30);
		buscarClientePorEmailPanel.add(textAreaTelefonoClienteID_1);

		JLabel lblFechaDeRegistro_1 = new JLabel("Fecha de registro");
		lblFechaDeRegistro_1.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblFechaDeRegistro_1.setBackground(Color.WHITE);
		lblFechaDeRegistro_1.setBounds(33, 332, 150, 30);
		buscarClientePorEmailPanel.add(lblFechaDeRegistro_1);

		JTextArea textAreaFechaRegistroClienteID_1 = new JTextArea();
		textAreaFechaRegistroClienteID_1.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textAreaFechaRegistroClienteID_1.setBounds(177, 332, 184, 30);
		buscarClientePorEmailPanel.add(textAreaFechaRegistroClienteID_1);

		JButton btnBuscarClientePorEmail = new JButton("Buscar cliente");
		btnBuscarClientePorEmail.setForeground(Color.WHITE);
		btnBuscarClientePorEmail.setFont(new Font("Noto Sans", Font.BOLD, 14));
		btnBuscarClientePorEmail.setBackground(new Color(128, 0, 0));
		btnBuscarClientePorEmail.setBounds(520, 99, 138, 32);
		buscarClientePorEmailPanel.add(btnBuscarClientePorEmail);
		
		/**
		 * Listener para el botón "Buscar cliente por email".
		 * 
		 * Localiza un cliente en la base de datos según el email proporcionado y
		 * muestra su información si existe.
		 */

		btnBuscarClientePorEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String email = textFieldEmailClientePorEmail.getText().trim();
					if (email.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Por favor, introduce un email.");
						return;
					}

					ClienteOtaku cliente = daoClientes.buscarPorEmail(email);

					if (cliente == null) {
						JOptionPane.showMessageDialog(null, "No se ha encontrado ningún cliente con ese email.");
						return;
					}

					// Mostrar datos en los JTextArea
					textAreaNombreClienteId_1.setText(cliente.getNombre());
					textAreaEmailClienteID_1.setText(cliente.getEmail());
					textAreaTelefonoClienteID_1.setText(cliente.getTelefono());
					textAreaFechaRegistroClienteID_1.setText(cliente.getFechaRegistro().toString());

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR al buscar cliente: " + ex.getMessage());
				}
			}
		});

		// Mostrar todos los registros de clientes en una tabla
		JPanel mostrarTodospanel = new JPanel();
		mostrarTodospanel.setBackground(new Color(255, 255, 255));
		clientesTabbedPane.addTab("Listado de clientes", null, mostrarTodospanel, null);
		clientesTabbedPane.setBackgroundAt(3, new Color(243, 163, 163));
		mostrarTodospanel.setLayout(null);

		JTextPane txtpnListadoCompletoDeClientes = new JTextPane();
		txtpnListadoCompletoDeClientes.setBounds(33, 33, 490, 30);
		txtpnListadoCompletoDeClientes.setText("Listado completo de clientes registrados");
		txtpnListadoCompletoDeClientes.setForeground(new Color(196, 25, 25));
		txtpnListadoCompletoDeClientes.setFont(new Font("Dialog", Font.BOLD, 20));
		txtpnListadoCompletoDeClientes.setEditable(false);
		txtpnListadoCompletoDeClientes.setBackground(Color.WHITE);
		mostrarTodospanel.add(txtpnListadoCompletoDeClientes);

		tableClientes = new JTable();
		tableClientes.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		tableClientes.setForeground(new Color(0, 0, 0));
		tableClientes.setBackground(new Color(255, 255, 255));

		JScrollPane scrollPaneClientes = new JScrollPane(tableClientes);
		scrollPaneClientes.setBounds(33, 68, 660, 331);
		mostrarTodospanel.add(scrollPaneClientes);

		/**
		 * Listener del "ChangeListener" del panel de pestañas de clientes. Carga
		 * automáticamente el listado completo de clientes si la pestaña correspondiente
		 * está activa.
		 */
		clientesTabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int index = clientesTabbedPane.getSelectedIndex();
				String titulo = clientesTabbedPane.getTitleAt(index);
				if (titulo.equals("Listado de clientes")) {
					List<ClienteOtaku> clientes = daoClientes.obtenerTodosLosClientes();
					cargarClientesEnTabla(clientes);
				}
			}
		});

		// #########################################################################################
		JPanel actualizarClientePanel = new JPanel();
		actualizarClientePanel.setBackground(new Color(255, 255, 255));
		clientesTabbedPane.addTab("Actualizar", null, actualizarClientePanel, null);
		clientesTabbedPane.setBackgroundAt(4, new Color(243, 163, 163));
		actualizarClientePanel.setLayout(null);

		JTextPane txtpnActualizarCliente = new JTextPane();
		txtpnActualizarCliente.setText("Actualizar información de cliente");
		txtpnActualizarCliente.setForeground(new Color(196, 25, 25));
		txtpnActualizarCliente.setFont(new Font("Noto Sans", Font.BOLD, 20));
		txtpnActualizarCliente.setEditable(false);
		txtpnActualizarCliente.setBackground(Color.WHITE);
		txtpnActualizarCliente.setBounds(33, 33, 350, 32);
		actualizarClientePanel.add(txtpnActualizarCliente);

		JLabel lblIdDelCliente_1 = new JLabel("ID del cliente");
		lblIdDelCliente_1.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblIdDelCliente_1.setBackground(Color.WHITE);
		lblIdDelCliente_1.setBounds(33, 76, 150, 20);
		actualizarClientePanel.add(lblIdDelCliente_1);

		textField_7 = new JTextField();
		textField_7.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		textField_7.setColumns(10);
		textField_7.setBounds(33, 101, 96, 30);
		actualizarClientePanel.add(textField_7);

		JButton btnEditarNombre = new JButton("Editar nombre");
		btnEditarNombre.setForeground(Color.WHITE);
		btnEditarNombre.setFont(new Font("Noto Sans", Font.BOLD, 14));
		btnEditarNombre.setBackground(new Color(128, 0, 0));
		btnEditarNombre.setBounds(33, 172, 295, 32);
		actualizarClientePanel.add(btnEditarNombre);
		btnEditarNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(textField_7.getText().trim());
					ClienteOtaku cliente = daoClientes.obtenerClientePorId(id);

					if (cliente == null) {
						JOptionPane.showMessageDialog(null, "No existe un cliente con ese ID.");
						return;
					}

					String nuevoNombre = JOptionPane.showInputDialog(
							"NOMBRE ACTUAL: " + cliente.getNombre() + "\n\nIntroduce el nuevo nombre del cliente:");

					if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
						return;
					}

					cliente.setNombre(nuevoNombre);
					if (daoClientes.actualizarCliente(cliente)) {
						JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente.");
					} else {
						JOptionPane.showMessageDialog(null, "Error al actualizar el cliente.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: Revisa los datos introducidos.");
				}
			}
		});

		JButton btnEditarEmail = new JButton("Editar email");
		btnEditarEmail.setForeground(Color.WHITE);
		btnEditarEmail.setFont(new Font("Noto Sans", Font.BOLD, 14));
		btnEditarEmail.setBackground(new Color(128, 0, 0));
		btnEditarEmail.setBounds(33, 233, 295, 32);
		actualizarClientePanel.add(btnEditarEmail);
		
		/**
		 * Listener para el botón "Editar email del cliente".
		 * 
		 * Permite modificar el email de un cliente, asegurándose de que el nuevo
		 * correo sea válido antes de actualizarlo en la base de datos.
		 */

		btnEditarEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(textField_7.getText().trim());
					ClienteOtaku cliente = daoClientes.obtenerClientePorId(id);

					if (cliente == null) {
						JOptionPane.showMessageDialog(null, "No existe un cliente con ese ID.");
						return;
					}

					String nuevoEmail = JOptionPane.showInputDialog(
							"EMAIL ACTUAL: " + cliente.getEmail() + "\n\nIntroduce el nuevo email del cliente:");

					if (nuevoEmail == null || nuevoEmail.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El email no puede estar vacío.");
						return;
					}

					if (!daoClientes.esEmailValido(nuevoEmail)) {
						JOptionPane.showMessageDialog(null, "El email introducido no es válido.");
						return;
					}

					cliente.setEmail(nuevoEmail);
					if (daoClientes.actualizarCliente(cliente)) {
						JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente.");
					} else {
						JOptionPane.showMessageDialog(null, "Error al actualizar el cliente.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: Revisa los datos introducidos.");
				}
			}
		});

		JButton btnEditarTelfono = new JButton("Editar teléfono");
		btnEditarTelfono.setForeground(Color.WHITE);
		btnEditarTelfono.setFont(new Font("Noto Sans", Font.BOLD, 14));
		btnEditarTelfono.setBackground(new Color(128, 0, 0));
		btnEditarTelfono.setBounds(33, 293, 295, 32);
		actualizarClientePanel.add(btnEditarTelfono);
		
		/**
		 * Listener para el botón "Editar teléfono del cliente".
		 * 
		 * Solicita un nuevo número de teléfono para el cliente correspondiente
		 * y actualiza la base de datos si es válido.
		 */

		btnEditarTelfono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(textField_7.getText().trim());
					ClienteOtaku cliente = daoClientes.obtenerClientePorId(id);

					if (cliente == null) {
						JOptionPane.showMessageDialog(null, "No existe un cliente con ese ID.");
						return;
					}

					String nuevoTelefono = JOptionPane.showInputDialog("TELÉFONO ACTUAL: " + cliente.getTelefono()
							+ "\n\nIntroduce el nuevo teléfono del cliente:");

					if (nuevoTelefono == null || nuevoTelefono.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El teléfono no puede estar vacío.");
						return;
					}

					cliente.setTelefono(nuevoTelefono);
					if (daoClientes.actualizarCliente(cliente)) {
						JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente.");
					} else {
						JOptionPane.showMessageDialog(null, "Error al actualizar el cliente.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: Revisa los datos introducidos.");
				}
			}
		});

		JPanel eliminarClientepanel = new JPanel();
		eliminarClientepanel.setBackground(new Color(255, 255, 255));
		clientesTabbedPane.addTab("Eliminar", null, eliminarClientepanel, null);
		clientesTabbedPane.setBackgroundAt(5, new Color(243, 163, 163));
		eliminarClientepanel.setLayout(null);

		textFieldEliminarClienteID = new JTextField();
		textFieldEliminarClienteID.setFont(new Font("Dialog", Font.PLAIN, 14));
		textFieldEliminarClienteID.setColumns(10);
		textFieldEliminarClienteID.setBounds(33, 101, 96, 30);
		eliminarClientepanel.add(textFieldEliminarClienteID);

		JLabel lblIdDelCliente_1_1 = new JLabel("ID del cliente");
		lblIdDelCliente_1_1.setFont(new Font("Noto Sans", Font.BOLD, 14));
		lblIdDelCliente_1_1.setBackground(Color.WHITE);
		lblIdDelCliente_1_1.setBounds(33, 76, 150, 20);
		eliminarClientepanel.add(lblIdDelCliente_1_1);

		JTextPane textFieldEliminarCliente = new JTextPane();
		textFieldEliminarCliente.setText("Eliminar cliente registrado");
		textFieldEliminarCliente.setForeground(new Color(196, 25, 25));
		textFieldEliminarCliente.setFont(new Font("Noto Sans", Font.BOLD, 20));
		textFieldEliminarCliente.setEditable(false);
		textFieldEliminarCliente.setBackground(Color.WHITE);
		textFieldEliminarCliente.setBounds(33, 33, 350, 32);
		eliminarClientepanel.add(textFieldEliminarCliente);

		JButton btnEliminarCliente = new JButton("Eliminar");
		btnEliminarCliente.setForeground(Color.WHITE);
		btnEliminarCliente.setFont(new Font("Noto Sans", Font.BOLD, 14));
		btnEliminarCliente.setBackground(new Color(128, 0, 0));
		btnEliminarCliente.setBounds(152, 99, 120, 32);
		eliminarClientepanel.add(btnEliminarCliente);
		
		/**
		 * Listener del cambio de pestañas en la sección de Clientes.
		 * 
		 * Si se selecciona la pestaña "Listado de clientes", se carga y muestra
		 * automáticamente la lista completa de clientes desde la base de datos.
		 */

		btnEliminarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String idTexto = textFieldEliminarClienteID.getText().trim();

					if (idTexto.isEmpty()) {
						JOptionPane.showMessageDialog(null, "El campo ID no puede estar vacío.");
						return;
					}

					int id = Integer.parseInt(idTexto);
					ClienteOtaku cliente = daoClientes.obtenerClientePorId(id);

					if (cliente == null) {
						JOptionPane.showMessageDialog(null, "No se ha encontrado ningún cliente con ese ID.");
						return;
					}

					int confirmacion = JOptionPane.showConfirmDialog(null,
							"¿Seguro que deseas eliminar al cliente con ID: " + id + "?\n\nEmail: "
									+ cliente.getEmail(),
							"Confirmar eliminación", JOptionPane.YES_NO_OPTION);

					if (confirmacion == JOptionPane.YES_OPTION) {
						if (daoClientes.eliminarCliente(id)) {
							JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
							textFieldEliminarClienteID.setText("");
						} else {
							JOptionPane.showMessageDialog(null, "Error al eliminar el cliente.");
						}
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Introduce un ID válido (numérico).");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
				}
			}
		});

		// CAMBIO DE VISIBILIDAD DE LOS PANELES DE GESTIÓN DE INVENTARIO Y CLIENTES
		/**
		 * Listener para cambiar entre vistas de productos y clientes. Al hacer clic en
		 * "Inventario", se muestra la vista o espacio de productos.
		 */
		gestionInventarioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelGestionProductos.setVisible(true);
				panelGestionClientes.setVisible(false);
			}
		});
		/**
		 * Listener para cambiar entre vistas de productos y clientes. Al hacer clic en
		 * "Clientes", se muestra la vista o espacio de gestión de clientes.
		 */
		gestionClientesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelGestionProductos.setVisible(false);
				panelGestionClientes.setVisible(true);
			}
		});

	}

	/**
	 * Carga el listado de productos en la tabla principal de inventario.
	 * 
	 * Formatea y muestra los productos en una tabla, estableciendo también el ancho
	 * personalizado de las columnas para una mejor visualización.
	 * 
	 * @param productos Lista de productos obtenida desde la base de datos.
	 */
	private void cargarInventarioEnTabla(List<ProductoOtaku> productos) {
		// Recupero la lista con productos llamando al método implementado para
		// recuperar los productos de la BD en ProductosDAOImpl.java

		// Defino los nombres de las columnas
		String[] columnas = { "ID", "Nombre", "Categoría", "Precio", "Stock" };
		String[][] datos = new String[productos.size()][5];

		for (int i = 0; i < productos.size(); i++) {
			ProductoOtaku p = productos.get(i); // recupero el objeto ProductoOtaku de cada registro en cada iteración
												// para obtener el estado de sus atributos
			datos[i][0] = String.valueOf(p.getId()); // id
			datos[i][1] = p.getNombre(); // Nombre
			datos[i][2] = p.getCategoria(); // Categoria
			datos[i][3] = String.format("%.2f€", p.getPrecio()); // Precio a 2decimales
			datos[i][4] = String.valueOf(p.getStock()); // Stock
		}

		table.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));

		// ancho personalizdo de las columnas
		table.getColumnModel().getColumn(0).setPreferredWidth(20); // ID
		table.getColumnModel().getColumn(1).setPreferredWidth(170); // Nombre
		table.getColumnModel().getColumn(2).setPreferredWidth(90); // Categoría
		table.getColumnModel().getColumn(3).setPreferredWidth(60); // Precio
		table.getColumnModel().getColumn(4).setPreferredWidth(50); // Stock
	}

	/**
	 * Carga el resultado de la búsqueda por nombre de producto en la tabla correspondiente.
	 * 
	 * Muestra los datos de los productos coincidentes en una tabla e incluye formato 
	 * personalizado para las columnas.
	 * 
	 * @param productos Lista de productos coincidentes por nombre.
	 */

	private void cargarResultadosEnTablaNombre(List<ProductoOtaku> productos) {
		String[] columnas = { "ID", "Nombre", "Categoría", "Precio", "Stock" };
		String[][] datos = new String[productos.size()][5];

		for (int i = 0; i < productos.size(); i++) {
			ProductoOtaku p = productos.get(i);
			datos[i][0] = String.valueOf(p.getId());
			datos[i][1] = p.getNombre();
			datos[i][2] = p.getCategoria();
			datos[i][3] = String.format("%.2f€", p.getPrecio());
			datos[i][4] = String.valueOf(p.getStock());
		}

		tableConsultaProductoPorNombre.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));

		// Ancho de columnas personalizado
		tableConsultaProductoPorNombre.getColumnModel().getColumn(0).setPreferredWidth(20); // ID
		tableConsultaProductoPorNombre.getColumnModel().getColumn(1).setPreferredWidth(170); // Nombre
		tableConsultaProductoPorNombre.getColumnModel().getColumn(2).setPreferredWidth(90); // Categoría
		tableConsultaProductoPorNombre.getColumnModel().getColumn(3).setPreferredWidth(60); // Precio
		tableConsultaProductoPorNombre.getColumnModel().getColumn(4).setPreferredWidth(50); // Stock

	}

	/**
	 * Carga todos los clientes registrados en la tabla de visualización del panel de clientes.
	 * 
	 * Convierte los objetos ClienteOtaku en filas de la tabla y define el tamaño de cada columna.
	 * 
	 * @param clientes Lista de clientes obtenida desde la base de datos.
	 */
	private void cargarClientesEnTabla(List<ClienteOtaku> clientes) {
		String[] columnas = { "ID", "Nombre", "Email", "Teléfono", "Fecha de registro" };
		String[][] datos = new String[clientes.size()][5];

		for (int i = 0; i < clientes.size(); i++) {
			ClienteOtaku c = clientes.get(i);
			datos[i][0] = String.valueOf(c.getId());
			datos[i][1] = c.getNombre();
			datos[i][2] = c.getEmail();
			datos[i][3] = c.getTelefono();
			datos[i][4] = c.getFechaRegistro().toString();
		}

		tableClientes.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));

		tableClientes.getColumnModel().getColumn(0).setPreferredWidth(20);
		tableClientes.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableClientes.getColumnModel().getColumn(2).setPreferredWidth(200);
		tableClientes.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableClientes.getColumnModel().getColumn(4).setPreferredWidth(100);
	}

}
