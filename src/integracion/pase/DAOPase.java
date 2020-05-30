package integracion.pase;

import java.util.List;

import negocio.pase.TPase;

public interface DAOPase {

	int registrarPase(TPase tPase);
	int modificarPase(TPase tPase);
	int borrarPase(int id);
	TPase buscarPasePorID(int id);
	List<TPase> mostrarListaPases();
	List<TPase> mostrarPasesPorPeliculas(int idPelicula);
	public boolean franjaValida(TPase tPase);
}
