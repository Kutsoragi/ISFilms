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

import negocio.pelicula.TPelicula;
import presentacion.controlador.EventosGUI;
import presentacion.controlador.EventosPelicula;
import presentacion.controlador.SingletonControlador;
import presentacion.utility.ErrorPanel;
import presentacion.utility.FormPanel;
import presentacion.utility.PanelButton;
import presentacion.utility.PanelGridBagConstraints;
import presentacion.utility.RoundButton;
import presentacion.utility.TablePanel;
import presentacion.utility.MostrarPanel;

@SuppressWarnings("serial")
public class GUIPeliculaImp extends JPanel implements IGUI {

	private int INICIO_PANEL_HEIGHT = 300;
	
	private String nombre = "PELICULA";
	
	private JPanel inicioPanel, panelActual;
	private ErrorPanel errorPanel;
	
	public GUIPeliculaImp() {
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
				SingletonControlador.getInstancia().accion(EventosPelicula.MOSTRAR_LISTA_PELICULA, null);
				System.out.println("Mostrar Lista");
			}
		});
		inicioPanel.add(mostrarListaBtn, c);
		
		c.gridx++;
		JButton mostrarPorFechaBtn = new PanelButton("resources/icons/operaciones/mostrar-por-fecha.png", new Color(111,168,220));
		mostrarPorFechaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPorFechaPanel();
				System.out.println("Mostrar Por Fecha");
			}
		});
		inicioPanel.add(mostrarPorFechaBtn, c);
		
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
	
	//-- PANELES
	
	private void registrarPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("NOMBRE", "DURACION"));
		
		panel.getEnterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nombre = panel.getFieldText("NOMBRE");	
					int duracion = Integer.parseInt(panel.getFieldText("DURACION"));
					SingletonControlador.getInstancia().accion(EventosPelicula.REGISTRAR_PELICULA, new TPelicula(duracion, nombre));
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
				} catch (IllegalArgumentException ex) {
					errorPanel.showOutputMessage(ex.getMessage(), false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void modificarPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID", "NOMBRE", "DURACION"));
		
		panel.getEnterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(panel.getFieldText("ID"));
					String nombre = panel.getFieldText("NOMBRE");	
					int duracion = Integer.parseInt(panel.getFieldText("DURACION"));
					SingletonControlador.getInstancia().accion(EventosPelicula.MODIFICAR_PELICULA, new TPelicula(id, duracion, nombre));
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
					int id = Integer.parseInt(panel.getFieldText("ID"));
					SingletonControlador.getInstancia().accion(EventosPelicula.BORRAR_PELICULA, id);
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
					SingletonControlador.getInstancia().accion(EventosPelicula.BUSCAR_PELICULA, id);
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void mostrarPanel(TPelicula p) { // Muestra un transfer dado
		MostrarPanel panel = new MostrarPanel(Arrays.asList("ID", "NOMBRE", "DURACION"));
		panel.setLabelText("ID", Integer.toString(p.getID()));
		panel.setLabelText("NOMBRE", p.getNombre());
		panel.setLabelText("DURACION", Integer.toString(p.getDuracion()));
		updatePanel(panel, 50);
	}	
	
	private void mostrarListaPanel(List<TPelicula> peliculas) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(1024, 460));
		
		TablePanel tablePanel = new TablePanel(new Dimension(775, 275), Arrays.asList("ID", "NOMBRE", "DURACION"));		
		for (TPelicula p : peliculas)
			tablePanel.addRow(new Object[]{Integer.toString(p.getID()), p.getNombre(), Integer.toString(p.getDuracion())}, false);
		
		panel.add(tablePanel);
		
		updatePanel(panel, null);
	}
	
	private void mostrarPorFechaPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("FECHA"));
		
		panel.getEnterButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SingletonControlador.getInstancia().accion(EventosPelicula.MOSTRAR_POR_FECHA_PELICULA, panel.getFieldText("FECHA"));
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
			case EventosPelicula.REGISTRAR_PELICULA_OK:
			case EventosPelicula.MODIFICAR_PELICULA_OK:
			case EventosPelicula.BORRAR_PELICULA_OK:
				errorPanel.showOutputMessage((String) datos, true);
				break;
			case EventosPelicula.BUSCAR_PELICULA_OK:
				mostrarPanel((TPelicula) datos);
				break;
			case EventosPelicula.MOSTRAR_LISTA_PELICULA_OK:
			case EventosPelicula.MOSTRAR_POR_FECHA_PELICULA_OK:
				mostrarListaPanel((List<TPelicula>) datos);
				break;
				
			//ERROR
			case EventosPelicula.REGISTRAR_PELICULA_KO:
			case EventosPelicula.MODIFICAR_PELICULA_KO:
			case EventosPelicula.BORRAR_PELICULA_KO:
			case EventosPelicula.BUSCAR_PELICULA_KO:
			case EventosPelicula.MOSTRAR_LISTA_PELICULA_KO:
			case EventosPelicula.MOSTRAR_POR_FECHA_PELICULA_KO:
				errorPanel.showOutputMessage((String) datos, false);
				break;
		}
	}
}
