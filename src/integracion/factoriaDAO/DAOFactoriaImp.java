package integracion.factoriaDAO;
import integracion.empleado.DAOEmpleado;
import integracion.empleado.DAOEmpleadoImpl;
import integracion.factoriaDAO.DAOFactoria;
import integracion.factura.DAOFactura;
import integracion.factura.DAOFacturaImpl;
import integracion.pase.DAOPase;
import integracion.pase.DAOPaseImpl;
import integracion.pelicula.DAOPelicula;
import integracion.pelicula.DAOPeliculaImpl;
import integracion.sala.DAOSala;
import integracion.sala.DAOSalaImpl;

public class DAOFactoriaImp extends DAOFactoria{

	public DAOSala generarDAOSala() {
		return new DAOSalaImpl();
	}
	
	public DAOPelicula generarDAOPelicula() {
		return new DAOPeliculaImpl();
	}

	public DAOPase generarDAOPase() {
		return new DAOPaseImpl();
	}
	
	public DAOEmpleado generarDAOEmpleado() {
		return new DAOEmpleadoImpl();
	}

	public DAOFactura generarDAOFactura() {
		return new DAOFacturaImpl();
	}

}
