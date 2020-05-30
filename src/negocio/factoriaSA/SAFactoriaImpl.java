package negocio.factoriaSA;
import negocio.empleado.SAEmpleado;
import negocio.empleado.SAEmpleadoImp;
import negocio.factura.SAFactura;
import negocio.factura.SAFacturaImp;
import negocio.pase.SAPase;
import negocio.pase.SAPaseImp;
import negocio.pelicula.SAPelicula;
import negocio.pelicula.SAPeliculaImp;
import negocio.sala.SASala;
import negocio.sala.SASalaImp;


public class SAFactoriaImpl extends SAFactoria{

	@Override
	public SASala generarSASala() {
		
		return new SASalaImp();
	}

	@Override
	public SAPelicula generarSAPelicula() {
		
		return new SAPeliculaImp();
	}

	@Override
	public SAPase generarSAPase() {
		
		return new SAPaseImp();
	}

	@Override
	public SAEmpleado generarSAEmpleado() {
		
		return new SAEmpleadoImp();
	}

	@Override
	public SAFactura generarSAFactura() {
		
		return new SAFacturaImp();
	}
}
