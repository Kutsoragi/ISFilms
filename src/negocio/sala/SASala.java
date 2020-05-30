package negocio.sala;

import java.util.List;

public interface SASala {
	
	int registrarSala(int asientos);
	int modificarSala(TSala sala);
	int borrarSala(int id);
	TSala buscarSalaPorID(int id);
	List<TSala> mostrarListaSalas();
	List<TSala> mostrarSalasPorPelicula(int idPelicula);
}
