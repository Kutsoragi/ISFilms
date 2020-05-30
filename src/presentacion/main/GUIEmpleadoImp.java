package presentacion.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import negocio.empleado.TEmpleado;
import negocio.empleado.TEmpleadoCompleto;
import negocio.empleado.TEmpleadoParcial;
import presentacion.controlador.EventosGUI;
import presentacion.controlador.EventosEmpleado;
import presentacion.controlador.SingletonControlador;
import presentacion.utility.ErrorPanel;
import presentacion.utility.FormPanel;
import presentacion.utility.PanelButton;
import presentacion.utility.PanelGridBagConstraints;
import presentacion.utility.RoundButton;
import presentacion.utility.TablePanel;
import presentacion.utility.MostrarPanel;

@SuppressWarnings("serial")
public class GUIEmpleadoImp extends JPanel implements IGUI {

	private int INICIO_PANEL_HEIGHT = 300;
	
	private String nombre = "EMPLEADO";
	
	private JPanel inicioPanel, panelActual;
	private ErrorPanel errorPanel;
	
	public GUIEmpleadoImp() {
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
		
		JButton registrarBtn = new PanelButton("resources/icons/operaciones/registrar.png", new Color(11,83,148));
		registrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resJornada = jornadaOptionPane(); //Si retorna OK es jornada Completa  
				if (resJornada == JOptionPane.YES_OPTION)
					registrarPanel(true);
				else if (resJornada == JOptionPane.NO_OPTION)
					registrarPanel(false);
				System.out.println("Registrar");
			}
		});
		inicioPanel.add(registrarBtn, c);
		
		c.gridx++;
		JButton modificarBtn = new PanelButton("resources/icons/operaciones/modificar.png", new Color(61,133,198));
		modificarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resJornada = jornadaOptionPane(); //Si retorna OK es jornada Completa 
				if (resJornada == JOptionPane.YES_OPTION)
					modificarPanel(true);
				else if (resJornada == JOptionPane.NO_OPTION)
					modificarPanel(false);
				System.out.println("Modificar");
			}
		});
		inicioPanel.add(modificarBtn, c);
		
		c.gridx++;
		JButton borrarBtn = new PanelButton("resources/icons/operaciones/borrar.png", new Color(111,168,220));
		borrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarPanel();
				System.out.println("Borrar");
			}
		});
		inicioPanel.add(borrarBtn, c);
		
		c.gridx = 0;
		c.gridy++;
		JButton buscarBtn = new PanelButton("resources/icons/operaciones/buscar.png", new Color(61,133,198));
		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPanel();
				System.out.println("Buscar");
			}
		});
		inicioPanel.add(buscarBtn, c);
		
		c.gridx++;
		JButton mostrarListaBtn = new PanelButton("resources/icons/operaciones/listar.png", new Color(111,168,220));
		mostrarListaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SingletonControlador.getInstancia().accion(EventosEmpleado.MOSTRAR_LISTA_EMPLEADO, null);
				System.out.println("Mostrar Lista");
			}
		});
		inicioPanel.add(mostrarListaBtn, c);
		
		c.gridx++;
		JButton mostrarPorJornadaBtn = new PanelButton("resources/icons/operaciones/mostrar-por-jornada.png", new Color(111,168,220));
		mostrarPorJornadaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				int resJornada = jornadaOptionPane(); //Si retorna OK es jornada Completa 
				if (resJornada == JOptionPane.YES_OPTION)
					SingletonControlador.getInstancia().accion(EventosEmpleado.MOSTRAR_POR_JORNADA_EMPLEADO, true);
				else if (resJornada == JOptionPane.NO_OPTION)
					SingletonControlador.getInstancia().accion(EventosEmpleado.MOSTRAR_POR_JORNADA_EMPLEADO, false);

				System.out.println("Mostrar Por Jornada");
			}
		});
		inicioPanel.add(mostrarPorJornadaBtn, c);
		
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
	
	private int jornadaOptionPane() {
		final JComponent[] input = new JComponent[] { new JLabel("Seleccione la Jornada") };
		Object[] options = {"Completa", "Parcial"};	
		return JOptionPane.showOptionDialog(null, input, "Jornada Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	
	//-- PANELES
	
	private void mainFormPanel(final boolean jornadaCompleta, final int event) { //Se aplica a Registrar y Modificar
		final FormPanel panel;
		
		if (jornadaCompleta == true)
			panel = new FormPanel(Arrays.asList("NOMBRE", "DNI", "SUELDO"));
		else
			panel = new FormPanel(Arrays.asList("NOMBRE", "DNI", "HORAS", "SUELDO/H"));
				
		panel.getEnterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nombre = panel.getFieldText("NOMBRE");
					String dni = panel.getFieldText("DNI");
					if (jornadaCompleta == true) {
						double sueldo = Double.parseDouble(panel.getFieldText("SUELDO"));
						SingletonControlador.getInstancia().accion(event, new TEmpleadoCompleto(dni, nombre, true, sueldo));
					} else {
						int horas = Integer.parseInt(panel.getFieldText("HORAS"));
						double sueldoPorHora = Double.parseDouble(panel.getFieldText("SUELDO/H"));
						SingletonControlador.getInstancia().accion(event, new TEmpleadoParcial(dni, nombre, true, horas, sueldoPorHora));
					}
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
				} catch (IllegalArgumentException ex) {
					errorPanel.showOutputMessage(ex.getMessage(), false);
				}
			}
		});
			
		updatePanel(panel, null);
	}
	
	private void registrarPanel(final boolean jornadaCompleta) {
		mainFormPanel(jornadaCompleta, EventosEmpleado.REGISTRAR_EMPLEADO);
	}
	
	private void modificarPanel(final boolean jornadaCompleta) {
		mainFormPanel(jornadaCompleta, EventosEmpleado.MODIFICAR_EMPLEADO);
	}
	
	private void borrarPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID"));
		
		panel.getEnterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(panel.getFieldText("ID"));	
					SingletonControlador.getInstancia().accion(EventosEmpleado.BORRAR_EMPLEADO, id);
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
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
					SingletonControlador.getInstancia().accion(EventosEmpleado.BUSCAR_EMPLEADO, id);
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void mostrarPanel(TEmpleado e) { // Muestra un transfer dado
		MostrarPanel panel;
		
		if (e.isCompleto()) {
			panel = new MostrarPanel(Arrays.asList("ID", "DNI", "NOMBRE", "JORNADA", "SUELDO", "ACTIVO"));
			panel.setLabelText("JORNADA", "Completa");
			panel.setLabelText("SUELDO", Double.toString(((TEmpleadoCompleto) e).getSueldo()));
		} else {	
			panel = new MostrarPanel(Arrays.asList("ID", "DNI", "NOMBRE", "JORNADA", "HORAS", "SUELDO/H", "ACTIVO"));
			panel.setLabelText("JORNADA", "Parcial");
			panel.setLabelText("HORAS", Integer.toString(((TEmpleadoParcial) e).getHoras()));
			panel.setLabelText("SUELDO/H", Double.toString(((TEmpleadoParcial) e).getSueldoPorHoras()));
		}

		panel.setLabelText("ID", Integer.toString(e.getID()));
		panel.setLabelText("DNI", e.getDNI());
		panel.setLabelText("NOMBRE", e.getNombre());
		panel.setLabelText("ACTIVO", Boolean.toString(e.isActivo()));
		
		updatePanel(panel, 50);
	}	
	
	private void mostrarListaPanel(List<TEmpleado> empleados) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(1024, 460));
		
		TablePanel tablePanel = new TablePanel(new Dimension(775, 275), Arrays.asList("ID", "DNI", "NOMBRE", "JORNADA", "SUELDO", "HORAS", "SUELDO/H", "ACTIVO"));		
		for (TEmpleado e : empleados) {
			Object[] datos;
			if (e.isCompleto())
				datos = new Object[]{Integer.toString(e.getID()), e.getDNI(), e.getNombre(), "Completa", Double.toString(((TEmpleadoCompleto) e).getSueldo()), null, null, e.isActivo()};
			else
				datos = new Object[]{Integer.toString(e.getID()), e.getDNI(), e.getNombre(), "Parcial", null, Integer.toString(((TEmpleadoParcial) e).getHoras()), Double.toString(((TEmpleadoParcial) e).getSueldoPorHoras()), e.isActivo()};
				
			tablePanel.addRow(datos, false);
		}
		
		panel.add(tablePanel);
		
		updatePanel(panel, null);
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
			case EventosEmpleado.REGISTRAR_EMPLEADO_OK:
			case EventosEmpleado.MODIFICAR_EMPLEADO_OK:
			case EventosEmpleado.BORRAR_EMPLEADO_OK:
				errorPanel.showOutputMessage((String) datos, true);
				break;
			case EventosEmpleado.BUSCAR_EMPLEADO_OK:
				mostrarPanel((TEmpleado) datos);
				break;
			case EventosEmpleado.MOSTRAR_LISTA_EMPLEADO_OK:
			case EventosEmpleado.MOSTRAR_POR_JORNADA_EMPLEADO_OK:
				mostrarListaPanel((List<TEmpleado>) datos);
				break;
			case EventosEmpleado.REGISTRAR_EMPLEADO_KO:
			case EventosEmpleado.MODIFICAR_EMPLEADO_KO:
			case EventosEmpleado.BORRAR_EMPLEADO_KO:
			case EventosEmpleado.BUSCAR_EMPLEADO_KO:
			case EventosEmpleado.MOSTRAR_LISTA_EMPLEADO_KO:
			case EventosEmpleado.MOSTRAR_POR_JORNADA_EMPLEADO_KO:
				errorPanel.showOutputMessage((String) datos, false);
				break;
		}
	}
}
