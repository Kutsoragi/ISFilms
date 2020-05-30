package presentacion.factoria;

import presentacion.main.IGUI;
import presentacion.main.GUIEmpleadoImp;
import presentacion.main.GUIFacturaImp;
import presentacion.main.GUIMainImp;
import presentacion.main.GUIPaseImp;
import presentacion.main.GUIPeliculaImp;
import presentacion.main.GUISalaImp;

public class GUIFactoriaImp extends GUIFactoria {

	@Override
	public IGUI generarGUIMain() {
		return new GUIMainImp();
	}

	@Override
	public IGUI generarGUISala() {
		return new GUISalaImp();
	}

	@Override
	public IGUI generarGUIPelicula() {
		return new GUIPeliculaImp();
	}

	@Override
	public IGUI generarGUIEmpleado() {
		return new GUIEmpleadoImp();
	}
	
	@Override
	public IGUI generarGUIPase() {
		return new GUIPaseImp();
	}
	
	@Override
	public IGUI generarGUIFactura() {
		return new GUIFacturaImp();
	}


}