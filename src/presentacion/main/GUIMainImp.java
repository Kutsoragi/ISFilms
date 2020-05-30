package presentacion.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import presentacion.controlador.EventosEmpleado;
import presentacion.controlador.EventosFactura;
import presentacion.controlador.EventosGUI;
import presentacion.controlador.EventosPase;
import presentacion.controlador.EventosPelicula;
import presentacion.controlador.EventosSala;
import presentacion.controlador.SingletonControlador;
import presentacion.factoria.GUIFactoria;
import presentacion.utility.PanelButton;
import presentacion.utility.PanelGridBagConstraints;

public class GUIMainImp extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private String nombre = "INICIO";
	
	private IGUI currentGUI;
	private JPanel inicioPanel;
	private JLabel menuTitle;
	
	private static GUIFactoria presentacion;
	@SuppressWarnings("unused")
	private static SingletonControlador controlador;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {			
					presentacion = GUIFactoria.getInstancia();
					controlador =  SingletonControlador.getInstancia();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUIMainImp() {
		init();
	}

	public void init() {
		setBackground(new Color(255, 255, 255));
		setUndecorated(true);
		setBounds(100, 100, 1024, 620);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel topBar = new JPanel();
		topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
		topBar.setBackground(new Color(7, 55, 99));
		topBar.setMaximumSize(new Dimension(1024, 50));
		
		topBar.add(Box.createRigidArea(new Dimension(20,0)));
		
		JLabel groupLogo = new JLabel();
		groupLogo.setIcon(new ImageIcon("resources/icons/inicio/ISFilms-logo-small.png"));
		topBar.add(groupLogo);
		
		topBar.add(Box.createRigidArea(new Dimension(8,0)));
		
		JLabel appName = new JLabel("CINEMA MANAGER");
		appName.setFont(new Font("Arial", Font.BOLD, 14));
		appName.setForeground(new Color(215,215,215));
		topBar.add(appName);
		
		topBar.add(Box.createRigidArea(new Dimension(830-23,0))); //logo width = 23px
		
		JButton exitBtn = new JButton("X");
		exitBtn.setFocusPainted(false);
		exitBtn.setBorder(null);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorderPainted(false);
		exitBtn.setPreferredSize(new Dimension(30, 30));
		exitBtn.setFont(new Font("Corbel", Font.BOLD, 19));
		exitBtn.setForeground(new Color(215,215,215));
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JComponent[] input = new JComponent[] { new JLabel("Â¿Desea cerrar el programa?") };
				Object[] options = {"Si", "No"};
				int result = JOptionPane.showOptionDialog(null, input, "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (result == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});
		topBar.add(exitBtn);

		//-------
		
		menuTitle = new JLabel(nombre);
		menuTitle.setFont(new Font("Arial", Font.BOLD, 30));
		menuTitle.setForeground(new Color(7, 55, 99));
		menuTitle.setAlignmentX(CENTER_ALIGNMENT);
		
		JPanel decorationLine = new JPanel();
		decorationLine.setBackground(new Color(7, 55, 99));
		decorationLine.setMaximumSize(new Dimension(800, 5));
		
		//-------

		inicioPanel = new JPanel(new GridBagLayout());
		inicioPanel.setBackground(new Color(243, 243, 243));
		inicioPanel.setMaximumSize(new Dimension(1024, 460));
		
		GridBagConstraints c = new PanelGridBagConstraints();
		JButton salasBtn = new PanelButton("resources/icons/inicio/sala.png", new Color(11,83,148));
		salasBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Mostrar GUISala");
				updatePanel(presentacion.generarGUISala());
			}
		});
		inicioPanel.add(salasBtn, c);
		
		c.gridx++;
		JButton peliculasBtn = new PanelButton("resources/icons/inicio/pelicula.png", new Color(61,133,198));
		peliculasBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Mostrar GUIPelicula");
				updatePanel(presentacion.generarGUIPelicula());
			}
		});
		inicioPanel.add(peliculasBtn, c);
		
		c.gridx++;
		JButton empleadosBtn = new PanelButton("resources/icons/inicio/empleado.png", new Color(111,168,220));
		empleadosBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Mostrar GUIEmpleado");
				updatePanel(presentacion.generarGUIEmpleado());
			}
		});
		inicioPanel.add(empleadosBtn, c);
		
		c.gridx = 0;
		c.gridy++;
		JButton pasesBtn = new PanelButton("resources/icons/inicio/pase.png", new Color(61,133,198));
		pasesBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Mostrar GUIPase");
				updatePanel(presentacion.generarGUIPase());
			}
		});
		inicioPanel.add(pasesBtn, c);
		
		c.gridx++;
		JButton facturasBtn = new PanelButton("resources/icons/inicio/factura.png", new Color(111,168,220));
		facturasBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Mostrar GUIFactura");
				updatePanel(presentacion.generarGUIFactura());
			}
		});
		inicioPanel.add(facturasBtn, c);
		
		add(topBar);
		add(Box.createRigidArea(new Dimension(0,25)));
		add(menuTitle);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(decorationLine);
		add(inicioPanel);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void updatePanel(IGUI newGUI) {
		inicioPanel.setVisible(false);
		currentGUI = newGUI;
		add(currentGUI.getMainPanel());
		menuTitle.setText(newGUI.getNombre());
	}

	private void restaurarInicio() {
		remove(currentGUI.getMainPanel());
		currentGUI = this;
		inicioPanel.setVisible(true);
		menuTitle.setText(nombre);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public JPanel getMainPanel() {
		return inicioPanel;
	}
	
	public void actualizar(int evento, Object datos) {
		switch (evento){
		
			//GUIMAIN
			case EventosGUI.MOSTRAR_INICIO:
				restaurarInicio();
				System.out.println("Retornando a GUIMain");
				break;

			//GUISALA
			case EventosSala.REGISTRAR_SALA_OK:
			case EventosSala.REGISTRAR_SALA_KO:
			case EventosSala.MODIFICAR_SALA_OK:
			case EventosSala.MODIFICAR_SALA_KO:
			case EventosSala.BORRAR_SALA_OK:
			case EventosSala.BORRAR_SALA_KO:
			case EventosSala.BUSCAR_SALA_OK:
			case EventosSala.BUSCAR_SALA_KO:
			case EventosSala.MOSTRAR_LISTA_SALA_OK:
			case EventosSala.MOSTRAR_LISTA_SALA_KO:
			case EventosSala.MOSTRAR_POR_PELICULA_SALA_OK:
			case EventosSala.MOSTRAR_POR_PELICULA_SALA_KO:
				
			//GUIPELICULA
			case EventosPelicula.REGISTRAR_PELICULA_OK:
			case EventosPelicula.REGISTRAR_PELICULA_KO:
			case EventosPelicula.MODIFICAR_PELICULA_OK:
			case EventosPelicula.MODIFICAR_PELICULA_KO:
			case EventosPelicula.BORRAR_PELICULA_OK:
			case EventosPelicula.BORRAR_PELICULA_KO:
			case EventosPelicula.BUSCAR_PELICULA_OK:
			case EventosPelicula.BUSCAR_PELICULA_KO:
			case EventosPelicula.MOSTRAR_LISTA_PELICULA_OK:
			case EventosPelicula.MOSTRAR_LISTA_PELICULA_KO:
			case EventosPelicula.MOSTRAR_POR_FECHA_PELICULA_OK:
			case EventosPelicula.MOSTRAR_POR_FECHA_PELICULA_KO:
				
			//GUIPASE
			case EventosPase.REGISTRAR_PASE_OK:
			case EventosPase.REGISTRAR_PASE_KO:
			case EventosPase.MODIFICAR_PASE_OK:
			case EventosPase.MODIFICAR_PASE_KO:
			case EventosPase.BORRAR_PASE_OK:
			case EventosPase.BORRAR_PASE_KO:
			case EventosPase.BUSCAR_PASE_OK:
			case EventosPase.BUSCAR_PASE_KO:
			case EventosPase.MOSTRAR_LISTA_PASE_OK:
			case EventosPase.MOSTRAR_LISTA_PASE_KO:
			case EventosPase.MOSTRAR_POR_PELICULA_PASE_OK:
			case EventosPase.MOSTRAR_POR_PELICULA_PASE_KO:
			
			//GUIEMPLEADO
			case EventosEmpleado.REGISTRAR_EMPLEADO_OK:
			case EventosEmpleado.REGISTRAR_EMPLEADO_KO:
			case EventosEmpleado.MODIFICAR_EMPLEADO_OK:
			case EventosEmpleado.MODIFICAR_EMPLEADO_KO:
			case EventosEmpleado.BORRAR_EMPLEADO_OK:
			case EventosEmpleado.BORRAR_EMPLEADO_KO:
			case EventosEmpleado.BUSCAR_EMPLEADO_OK:
			case EventosEmpleado.BUSCAR_EMPLEADO_KO:
			case EventosEmpleado.MOSTRAR_LISTA_EMPLEADO_OK:
			case EventosEmpleado.MOSTRAR_LISTA_EMPLEADO_KO:
			case EventosEmpleado.MOSTRAR_POR_JORNADA_EMPLEADO_OK:
			case EventosEmpleado.MOSTRAR_POR_JORNADA_EMPLEADO_KO:
			
			//GUIFACTURA
			case EventosFactura.AÑADIR_PASE_OK:
			case EventosFactura.AÑADIR_PASE_KO:
			case EventosFactura.QUITAR_PASE_OK:
			case EventosFactura.QUITAR_PASE_KO:
			case EventosFactura.CERRAR_FACTURA_OK:
			case EventosFactura.CERRAR_FACTURA_KO:
			case EventosFactura.DEVOLVER_PASE_OK:
			case EventosFactura.DEVOLVER_PASE_KO:
			case EventosFactura.BUSCAR_FACTURA_OK:
			case EventosFactura.BUSCAR_FACTURA_KO:
			case EventosFactura.MOSTRAR_LISTA_FACTURA_OK:
			case EventosFactura.MOSTRAR_LISTA_FACTURA_KO:
				currentGUI.actualizar(evento, datos);
				break;
				
		}
	}
}
