package presentacion.factoria;

import presentacion.main.IGUI;

public abstract class GUIFactoria {
	
	private static GUIFactoria instancia;
	
	public static GUIFactoria getInstancia() {
		if (instancia == null)
			instancia = new GUIFactoriaImp();
		return instancia;
	}
	
	public abstract IGUI generarGUIMain();
	public abstract IGUI generarGUISala();
	public abstract IGUI generarGUIPelicula();
	public abstract IGUI generarGUIPase();
	public abstract IGUI generarGUIEmpleado();
	public abstract IGUI generarGUIFactura();
}