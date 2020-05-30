package presentacion.main;

import javax.swing.JPanel;

public interface IGUI {
	public String getNombre();
	public JPanel getMainPanel();
	public void actualizar(int evento, Object datos);
}
