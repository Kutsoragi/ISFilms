package negocio.pase;


import java.util.List;
import integracion.factoriaDAO.DAOFactoria;
import integracion.pase.DAOPase;
import integracion.pelicula.DAOPelicula;
import negocio.pelicula.TPelicula;


public class SAPaseImp implements SAPase {

	public int registrarPase(TPase pase) {
		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		if (daoPase.franjaValida(pase)){
			return daoPase.registrarPase(pase);
		}
		else{
			throw new IllegalArgumentException("No puede haber dos pases en la misma sala en un mismo horario");
		}
	}
	
	public int borrarPase(int id) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
			
		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		TPase paseLeido = daoPase.buscarPasePorID(id);
		if (paseLeido != null) 
			return daoPase.borrarPase(id);
		
		return 0;
	}

	public TPase buscarPasePorID(int id) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");

		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		return daoPase.buscarPasePorID(id);
	}
	
	public int modificarPase(TPase pase) {
		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		TPase paseLeido = daoPase.buscarPasePorID(pase.getID());
		if (paseLeido != null) {
			if (daoPase.franjaValida(pase)){
				return daoPase.modificarPase(pase);
			}
			else{
				throw new IllegalArgumentException("No puede haber dos pases en la misma sala en un mismo horario");
			}
		}
		return 0;
	}

	public List<TPase> mostrarListaPases() {
		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		return daoPase.mostrarListaPases();
	}

	public List<TPase> mostrarPasesPorPelicula(int idPelicula) {
		if (idPelicula < 1) throw new IllegalArgumentException("ID incorrecto.");

		DAOPelicula daoPelicula = DAOFactoria.getInstancia().generarDAOPelicula();
		TPelicula peliculaLeida = daoPelicula.buscarPeliculaPorID(idPelicula);
		if (peliculaLeida != null) {
			DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
			return daoPase.mostrarPasesPorPeliculas(idPelicula);
		}

		return null;
	}
}