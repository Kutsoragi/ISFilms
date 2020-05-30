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
import javax.swing.JPanel;

import negocio.sala.TSala;
import presentacion.controlador.EventosGUI;
import presentacion.controlador.EventosSala;
import presentacion.controlador.SingletonControlador;
import presentacion.utility.ErrorPanel;
import presentacion.utility.FormPanel;
import presentacion.utility.PanelButton;
import presentacion.utility.PanelGridBagConstraints;
import presentacion.utility.RoundButton;
import presentacion.utility.TablePanel;
import presentacion.utility.MostrarPanel;

@SuppressWarnings("serial")
public class GUISalaImp extends JPanel implements IGUI {

	private int INICIO_PANEL_HEIGHT = 300;
	
	private String nombre = "SALA";
	
	private JPanel inicioPanel, panelActual;
	private ErrorPanel errorPanel;
	
	public GUISalaImp() {
		init();
	}
	
	public void init() {
		setLayout(new BorderLayout());
		setBackground(new Color(235, 237, 241));
		setMaximumSize(new Dimension(1024, 460));
		
		inicioPanel = new JPanel(new GridBagLayout());
		inicioPanel.setBackground(new Color(235, 237, 241));
		inicioPanel.setMaximumSize(new Dimension(1024, INICIO_PANEL_HEIGHT));
		
		GridBagConstraints c = new PanelGridBagConstraints();
		
		JButton registrarBtn = new PanelButton("resources/icons/operaciones/registrar.png", new Color(11,83,148));
		registrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrarPanel();
				System.out.println("Registrar");
			}
		});
		inicioPanel.add(registrarBtn, c);
		
		c.gridx++;
		JButton modificarBtn = new PanelButton("resources/icons/operaciones/modificar.png", new Color(61,133,198));
		modificarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarPanel();
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
				SingletonControlador.getInstancia().accion(EventosSala.MOSTRAR_LISTA_SALA, null);
				System.out.println("Mostrar Lista");
			}
		});
		inicioPanel.add(mostrarListaBtn, c);
		
		c.gridx++;
		JButton mostrarPorPeliculaBtn = new PanelButton("resources/icons/operaciones/mostrar-por-pelicula.png", new Color(111,168,220));
		mostrarPorPeliculaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPorPeliculaPanel();
				System.out.println("Mostrar Por Pelicula");
			}
		});
		inicioPanel.add(mostrarPorPeliculaBtn, c);
		
		//--
		
		errorPanel = new ErrorPanel(460 - INICIO_PANEL_HEIGHT);

		RoundButton backBtn = new RoundButton(100);
		backBtn.setMaximumSize(new Dimension(70,70));
		backBtn.setBackground(new Color(51,83,148));
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
	
	//-- PANELES
	
	private void registrarPanel() {
		// testList -- Arrays.asList("ID", "ASIENTOS", "EMPLEADOS", "SALAS", "MAXTEST");
		final FormPanel panel = new FormPanel(Arrays.asList("ASIENTOS"));
		
		panel.getEnterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer asientos = Integer.valueOf(panel.getFieldText("ASIENTOS"));
					SingletonControlador.getInstancia().accion(EventosSala.REGISTRAR_SALA, asientos);
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void modificarPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID", "ASIENTOS"));
		
		panel.getEnterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(panel.getFieldText("ID"));	
					int asientos = Integer.parseInt(panel.getFieldText("ASIENTOS"));
					SingletonControlador.getInstancia().accion(EventosSala.MODIFICAR_SALA,  new TSala(id, asientos));
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
				} catch (IllegalArgumentException ex) {
					errorPanel.showOutputMessage(ex.getMessage(), false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void borrarPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID"));
		
		panel.getEnterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer id = Integer.valueOf(panel.getFieldText("ID"));
					SingletonControlador.getInstancia().accion(EventosSala.BORRAR_SALA, id);
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
					Integer id = Integer.valueOf(panel.getFieldText("ID"));
					SingletonControlador.getInstancia().accion(EventosSala.BUSCAR_SALA, id);
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void mostrarPanel(TSala s) { // Muestra un transfer dado
		MostrarPanel panel = new MostrarPanel(Arrays.asList("ID", "ASIENTOS"));
		panel.setLabelText("ID", Integer.toString(s.getID()));
		panel.setLabelText("ASIENTOS", Integer.toString(s.getAsientos()));
		updatePanel(panel, 50);
	}
	
	private void mostrarListaPanel(List<TSala> salas) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(1024, 460));

		TablePanel tablePanel = new TablePanel(new Dimension(775, 275), Arrays.asList("ID", "ASIENTOS"));		
		for (TSala s : salas)
			tablePanel.addRow(new Object[]{Integer.toString(s.getID()), Integer.toString(s.getAsientos())}, false);
		
		panel.add(tablePanel);
		
		updatePanel(panel, null);
	}
	
	private void mostrarPorPeliculaPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID PELICULA"));
		
		panel.getEnterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer id = Integer.valueOf(panel.getFieldText("ID PELICULA"));
					SingletonControlador.getInstancia().accion(EventosSala.MOSTRAR_POR_PELICULA_SALA, id);
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
				}
			}
		});
		
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
		
			//SUCCESS
			case EventosSala.REGISTRAR_SALA_OK:
			case EventosSala.MODIFICAR_SALA_OK:
			case EventosSala.BORRAR_SALA_OK:
				errorPanel.showOutputMessage((String) datos, true);
				break;
			case EventosSala.BUSCAR_SALA_OK:
				mostrarPanel((TSala) datos);
				break;
			case EventosSala.MOSTRAR_LISTA_SALA_OK:
			case EventosSala.MOSTRAR_POR_PELICULA_SALA_OK:
				mostrarListaPanel((List<TSala>) datos);
				break;
			
			//ERROR
			case EventosSala.REGISTRAR_SALA_KO:
			case EventosSala.MODIFICAR_SALA_KO:
			case EventosSala.BORRAR_SALA_KO:
			case EventosSala.BUSCAR_SALA_KO:
			case EventosSala.MOSTRAR_LISTA_SALA_KO:
			case EventosSala.MOSTRAR_POR_PELICULA_SALA_KO:
				errorPanel.showOutputMessage((String) datos, false);
				break;
		}
	}
}
