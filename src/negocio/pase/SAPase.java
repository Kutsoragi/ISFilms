package negocio.pase;

import java.util.List;

public interface SAPase {

	int registrarPase(TPase pase);
	int modificarPase(TPase pase);
	int borrarPase(int id);
	TPase buscarPasePorID(int id);
	List<TPase> mostrarListaPases();
	List<TPase> mostrarPasesPorPelicula(int idPelicula);
	
}
