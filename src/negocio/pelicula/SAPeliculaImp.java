package negocio.pelicula;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import integracion.factoriaDAO.DAOFactoria;
import integracion.pelicula.DAOPelicula;

public class SAPeliculaImp implements SAPelicula {

	private final String FORMATO_FECHA = "YYYY-MM-DD";
	
	public int registrarPelicula(TPelicula pelicula) {
		DAOPelicula daoPelicula = DAOFactoria.getInstancia().generarDAOPelicula();
		return daoPelicula.registrarPelicula(pelicula);	
	}

	public int modificarPelicula(TPelicula pelicula) {
		DAOPelicula daoPelicula = DAOFactoria.getInstancia().generarDAOPelicula();
		TPelicula peliculaLeida = daoPelicula.buscarPeliculaPorID(pelicula.getID());
		if (peliculaLeida != null)
			return  daoPelicula.modificarPelicula(pelicula);

		return 0;
	}

	public int borrarPelicula(int id) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");

		DAOPelicula daoPelicula = DAOFactoria.getInstancia().generarDAOPelicula();
		TPelicula peliculaLeida = daoPelicula.buscarPeliculaPorID(id);
		if (peliculaLeida != null) 
			return daoPelicula.borrarPelicula(id);
			
		return 0;
	}

	public TPelicula buscarPeliculaPorID(int id) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");

		DAOPelicula daoPelicula = DAOFactoria.getInstancia().generarDAOPelicula();
		return daoPelicula.buscarPeliculaPorID(id);
	}

	public List<TPelicula> mostrarListaPeliculas() {
		DAOPelicula daoPelicula = DAOFactoria.getInstancia().generarDAOPelicula();
		return daoPelicula.mostrarListaPeliculas();
	}

	public List<TPelicula> mostrarPeliculasPorFecha(String fecha) {  
		verificarFecha(fecha);
		
		DAOPelicula daoPelicula = DAOFactoria.getInstancia().generarDAOPelicula();
		return daoPelicula.mostrarPeliculasPorFecha(fecha);
	}
	
	private void verificarFecha(String fecha) {
		try {
			new SimpleDateFormat(FORMATO_FECHA).parse(fecha);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Fecha incorrecta. Formato fecha: " + FORMATO_FECHA + ".");
		}
	}
}
