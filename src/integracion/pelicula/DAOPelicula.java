package integracion.pelicula;

import java.util.List;

import negocio.pelicula.TPelicula;

public interface DAOPelicula {
	
	int registrarPelicula(TPelicula tPelicula);
	int modificarPelicula(TPelicula tPelicula);
	int borrarPelicula(int id);
	TPelicula buscarPeliculaPorID(int id);
	List<TPelicula> mostrarListaPeliculas();
	List<TPelicula> mostrarPeliculasPorFecha(String fecha);
}
