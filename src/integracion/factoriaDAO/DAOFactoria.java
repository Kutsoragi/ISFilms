package integracion.factoriaDAO;
import integracion.empleado.DAOEmpleado;
import integracion.factura.DAOFactura;
import integracion.pase.DAOPase;
import integracion.pelicula.DAOPelicula;
import integracion.sala.DAOSala;


public abstract class DAOFactoria{
		
		private static DAOFactoria instancia;
		
		public static DAOFactoria getInstancia(){
			if(instancia == null){
				instancia = new DAOFactoriaImp();
			}
			return instancia;
		}
		public abstract DAOSala generarDAOSala();
		public abstract DAOPelicula generarDAOPelicula();
		public abstract DAOPase generarDAOPase();
		public abstract DAOEmpleado generarDAOEmpleado();
		public abstract DAOFactura generarDAOFactura();
	}
