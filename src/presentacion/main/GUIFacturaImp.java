package presentacion.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import negocio.factura.Pair;
import negocio.factura.TFactura;
import negocio.pase.TPase;
import presentacion.controlador.EventosFactura;
import presentacion.controlador.EventosGUI;
import presentacion.controlador.SingletonControlador;
import presentacion.utility.ErrorPanel;
import presentacion.utility.FacturaPanel;
import presentacion.utility.FormPanel;
import presentacion.utility.PanelButton;
import presentacion.utility.PanelGridBagConstraints;
import presentacion.utility.RoundButton;
import presentacion.utility.TablePanel;

@SuppressWarnings("serial")
public class GUIFacturaImp extends JPanel implements IGUI {

	private int INICIO_PANEL_HEIGHT = 300;
	
	private String nombre = "FACTURA";
	
	private JPanel inicioPanel, panelActual;
	private ErrorPanel errorPanel;
	
	private TablePanel carritoTable;
	private JLabel carritoPrecio;
	
	public GUIFacturaImp() {
		init();
	}
	
	public void init() {
		setLayout(new BorderLayout());
		setOpaque(false);
		setMaximumSize(new Dimension(1024, 460));
		
		inicioPanel = new JPanel(new GridBagLayout());
		inicioPanel.setOpaque(false);
		inicioPanel.setMaximumSize(new Dimension(1024, INICIO_PANEL_HEIGHT));
		
		GridBagConstraints c = new PanelGridBagConstraints();
		
		JButton abrirFacturaBtn = new PanelButton("resources/icons/operaciones/abrir-factura.png", new Color(11,83,148));
		abrirFacturaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carritoPanel();
				SingletonControlador.getInstancia().accion(EventosFactura.ABRIR_FACTURA, null);
				System.out.println("Abrir Factura");
			}
		});
		inicioPanel.add(abrirFacturaBtn, c);
		
		c.gridx++;
		JButton devolverPaseBtn = new PanelButton("resources/icons/operaciones/devolver-pase.png", new Color(61,133,198));
		devolverPaseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				devolverPasePanel();
				System.out.println("Devolver Pase");
			}
		});
		inicioPanel.add(devolverPaseBtn, c);
		
		c.gridx++;
		JButton buscarBtn = new PanelButton("resources/icons/operaciones/buscar.png", new Color(111,168,220));
		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPanel();
				System.out.println("Buscar");
			}
		});
		inicioPanel.add(buscarBtn, c);
		
		c.gridx++;
		JButton listarBtn = new PanelButton("resources/icons/operaciones/listar.png", new Color(143, 182, 217));
		listarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SingletonControlador.getInstancia().accion(EventosFactura.MOSTRAR_LISTA_FACTURA, null);
				System.out.println("Listar");
			}
		});
		inicioPanel.add(listarBtn, c);
		
		//--
		
		errorPanel = new ErrorPanel(460 - INICIO_PANEL_HEIGHT);

		RoundButton backBtn = new RoundButton(100);
		backBtn.setMaximumSize(new Dimension(70,70));
		backBtn.setBackground(new Color(51,83,148));
		backBtn.setBorder(null);
		backBtn.setFocusPainted(false);
		backBtn.setIcon("resources/icons/back.png");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorPanel.clear();
				if (panelActual.equals(inicioPanel))
					SingletonControlador.getInstancia().accion(EventosGUI.MOSTRAR_INICIO, null);
				else {
					remove(panelActual);
					inicioPanel.setVisible(true);
					panelActual = inicioPanel;
				}
				System.out.println("Volver");
			}
		});
		errorPanel.add(backBtn);
		
		inicioPanel.setBorder(BorderFactory.createEmptyBorder((460-INICIO_PANEL_HEIGHT)/2,0,0,0));
		add(inicioPanel, BorderLayout.CENTER);
		add(errorPanel, BorderLayout.PAGE_END);
		panelActual = inicioPanel;
	}
	
	//-- METODOS AUXILIARES
	
	private void updatePanel(JPanel newPanel, Integer offset) {
		if (panelActual.equals(inicioPanel))
			inicioPanel.setVisible(false);
		else
			remove(panelActual);
		
		newPanel.setBorder(BorderFactory.createEmptyBorder(offset == null ? (460-INICIO_PANEL_HEIGHT)/2 : offset,0,0,0));
		add(newPanel, BorderLayout.CENTER);
		errorPanel.clear();
		revalidate();
		repaint();
		panelActual = newPanel;
	}
	
	private JButton createToolButton(String iconPath, Color color, String tooltip) {
		RoundButton button = new RoundButton(100);
		button.setMaximumSize(new Dimension(35, 35));
		button.setBackground(color);
		button.setBorder(null);
		button.setFocusPainted(false);
		button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		button.setToolTipText(tooltip);
		button.setIcon(iconPath);
		
		return button;
	}
	
	private Pair<Integer, Integer> paseOptionPane(String operacion) {
		JTextField idPaseField = new JTextField();
		JTextField cantidadPaseField = new JTextField();
		final JComponent[] input = new JComponent[] {
		        new JLabel("ID Pase"),
		        idPaseField,
		        new JLabel("Cantidad"),
		        cantidadPaseField,
		};
		Object[] options = {operacion, "Cancelar"};
		int result = JOptionPane.showOptionDialog(null, input, operacion + " Pase", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		if (result == JOptionPane.OK_OPTION) {
			int idPase = Integer.parseInt(idPaseField.getText());
			int cantidadPase = Integer.parseInt(cantidadPaseField.getText());
			return new Pair<Integer, Integer>(idPase, cantidadPase);
		}
		
		return null;
	}
	
	private Integer empleadoOptionPane() {
		JTextField idEmpleadoField = new JTextField();
		final JComponent[] input = new JComponent[] {
		        new JLabel("ID Empleado"),
		        idEmpleadoField,
		};
		Object[] options = {"Aceptar", "Cancelar"};
		int result = JOptionPane.showOptionDialog(null, input, "Empleado Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (result == JOptionPane.OK_OPTION)
			return Integer.valueOf(idEmpleadoField.getText());
		
		return null;
	}
	
	//-- PANELES
	
	private void carritoPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(1024, 460));
		
		JPanel facturaPanel = new JPanel();
		facturaPanel.setLayout(new BoxLayout(facturaPanel, BoxLayout.X_AXIS));
		facturaPanel.setOpaque(false);
		facturaPanel.setMaximumSize(new Dimension(1024, 320));
		
		JPanel toolPanel = new JPanel();
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
		toolPanel.setBackground(new Color(70, 70, 70));
		toolPanel.setMaximumSize(new Dimension(80, 320));
		
		//--
		
		TablePanel paseTable = new TablePanel(new Dimension(600, 320), Arrays.asList("ID PASE", "CANTIDAD", "PRECIO"));
		
		//--
		
		JLabel totalTitle = new JLabel("TOTAL: ");
		totalTitle.setForeground(new Color(230,230,230));
		totalTitle.setFont(new Font("Arial", Font.BOLD, 12));
		totalTitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		JLabel totalPrecio = new JLabel("0.0");
		totalPrecio.setForeground(new Color(230,230,230));
		totalPrecio.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		toolPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JButton a�adirPaseBtn = createToolButton("resources/icons/operaciones/añadir-pase.png", new Color(63, 164, 31), "Añade un pase a la factura");
		a�adirPaseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Pair<Integer, Integer> pases = paseOptionPane("Añadir");
					if (pases != null)
						SingletonControlador.getInstancia().accion(EventosFactura.A�ADIR_PASE, pases);
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
				}
			}
		});
		toolPanel.add(a�adirPaseBtn);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JButton quitarPase = createToolButton("resources/icons/operaciones/quitar-pase.png", new Color(220, 34, 34), "Quita un pase de la factura");
		quitarPase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Pair<Integer, Integer> pases = paseOptionPane("Quitar");
					if (pases != null)
						SingletonControlador.getInstancia().accion(EventosFactura.QUITAR_PASE, pases);
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
				}
			}
		});
		toolPanel.add(quitarPase);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JButton cerrarBtn = createToolButton("resources/icons/operaciones/cerrar-factura.png", new Color(38, 180, 237), "Confirma y cierra la factura");
		cerrarBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				final JComponent[] input = new JComponent[] {
				        new JLabel("¿Desea confirmar y cerrar la factura?"),
				};
				Object[] options = {"Si", "No"};
				int result = JOptionPane.showOptionDialog(null, input, "Confirmar Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				
				if (result == JOptionPane.OK_OPTION) {
					try {
						Integer idEmpleado = empleadoOptionPane();
						if (idEmpleado != null)
							SingletonControlador.getInstancia().accion(EventosFactura.CERRAR_FACTURA, idEmpleado);
					} catch (NumberFormatException ex) {
						errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
					}
				}
			}
		});
		toolPanel.add(cerrarBtn);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 80)));
		toolPanel.add(totalTitle);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		toolPanel.add(totalPrecio);
		
		//--
		
		facturaPanel.add(Box.createRigidArea(new Dimension(170, 0)));
		facturaPanel.add(paseTable);
		facturaPanel.add(toolPanel);

		panel.add(facturaPanel);
		
		carritoTable = paseTable;
		carritoPrecio = totalPrecio;
		
		updatePanel(panel, 40);
	}
	
	private void devolverPasePanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID FACTURA", "ID PASE", "CANTIDAD"));
		
		panel.getEnterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer idFactura = Integer.valueOf(panel.getFieldText("ID FACTURA"));
					Integer idPase = Integer.valueOf(panel.getFieldText("ID PASE"));
					Integer cantidad = Integer.valueOf(panel.getFieldText("CANTIDAD"));
					SingletonControlador.getInstancia().accion(EventosFactura.DEVOLVER_PASE, new Vector<Integer>(Arrays.asList(idFactura, idPase, cantidad)));
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void buscarPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID"));
		
		panel.getEnterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(panel.getFieldText("ID"));	
					SingletonControlador.getInstancia().accion(EventosFactura.BUSCAR_FACTURA, id);
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void mostrarPanel(TFactura factura) {
		FacturaPanel panel = new FacturaPanel(new Dimension(600, 175));
		panel.MostrarTFactura(factura);
		updatePanel(panel, null);
	}
	
	private void listarPanel(List<TFactura> facturas) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(1024, 460));
		
		JPanel listarPanel = new JPanel();
		listarPanel.setLayout(new BoxLayout(listarPanel, BoxLayout.Y_AXIS));
		listarPanel.setOpaque(false);
		listarPanel.setMaximumSize(new Dimension(1024, 400));
		
		for (TFactura f : facturas) {
			FacturaPanel fPanel = new FacturaPanel(new Dimension(600, 150));
			fPanel.MostrarTFactura(f);
			listarPanel.add(fPanel);
			listarPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		}
		
		JScrollPane scrollPane = new JScrollPane(listarPanel);
		scrollPane.setPreferredSize(new Dimension(700, 300));
		scrollPane.setMaximumSize(new Dimension(700, 300));
		scrollPane.setOpaque(false);
		panel.add(scrollPane);
		
		updatePanel(panel, 50);
	}
	
	//-- METODOS IMPLEMENTADOS
	
	public String getNombre() {
		return nombre;
	}
	
	public JPanel getMainPanel() {
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public void actualizar(int evento, Object datos) {
		switch (evento) {
		
			//SUCCESS
			case EventosFactura.A�ADIR_PASE_OK:
			case EventosFactura.QUITAR_PASE_OK:
				TFactura f = (TFactura) datos;
				Pair<TPase, Integer> p = f.getFirstPase();
				if (carritoTable != null && carritoPrecio != null) {
					if (evento == EventosFactura.A�ADIR_PASE_OK)
						carritoTable.addRow(new Object[]{p.getFirst().getID(), p.getSecond(), p.getFirst().getPrecio()}, true);
					else
						carritoTable.updateRowByIdAt(Integer.toString(p.getFirst().getID()), -p.getSecond(), 1);
					carritoPrecio.setText(Double.toString(f.getPrecioTotal()));
					errorPanel.clear();
				}
				break;
				
			case EventosFactura.CERRAR_FACTURA_OK:
				//Volver a Inicio
				remove(panelActual);
				carritoTable = null;
				carritoPrecio = null;
				inicioPanel.setVisible(true);
				panelActual = inicioPanel;
				errorPanel.showOutputMessage((String) datos, true);
				break;
				
			case EventosFactura.DEVOLVER_PASE_OK:
				errorPanel.showOutputMessage((String) datos, true);
				break;
				
			case EventosFactura.BUSCAR_FACTURA_OK:
				mostrarPanel((TFactura) datos);
				break;
			case EventosFactura.MOSTRAR_LISTA_FACTURA_OK:
				listarPanel((LinkedList<TFactura>) datos);
				break;
				
			//ERROR
			case EventosFactura.A�ADIR_PASE_KO:
			case EventosFactura.QUITAR_PASE_KO:
			case EventosFactura.CERRAR_FACTURA_KO:
			case EventosFactura.DEVOLVER_PASE_KO:
			case EventosFactura.BUSCAR_FACTURA_KO:
			case EventosFactura.MOSTRAR_LISTA_FACTURA_KO:
				errorPanel.showOutputMessage((String) datos, false);
				break;
		}		
	}

}
