package negocio.pelicula;

import java.util.List;

public interface SAPelicula {

	int registrarPelicula(TPelicula pelicula);
	int modificarPelicula(TPelicula pelicula);
	int borrarPelicula(int id);
	TPelicula buscarPeliculaPorID(int id);
	List<TPelicula> mostrarListaPeliculas();
	List<TPelicula> mostrarPeliculasPorFecha(String fecha);
	
}
