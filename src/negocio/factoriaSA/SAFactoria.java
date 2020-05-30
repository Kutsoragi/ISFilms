package negocio.factoriaSA;

import negocio.empleado.SAEmpleado;
import negocio.factura.SAFactura;
import negocio.pase.SAPase;
import negocio.pelicula.SAPelicula;
import negocio.sala.SASala;

public abstract class SAFactoria {
	
	private static SAFactoria instancia;
	
	public static SAFactoria getInstancia(){
		if(instancia == null){
			instancia = new SAFactoriaImpl();
		}
		return instancia;
	}
	
	public abstract SASala generarSASala();
	public abstract SAPelicula generarSAPelicula();
	public abstract SAPase generarSAPase();
	public abstract SAEmpleado generarSAEmpleado();
	public abstract SAFactura generarSAFactura();
}
