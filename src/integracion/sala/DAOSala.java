package integracion.sala;

import java.util.List;

import negocio.sala.TSala;

public interface DAOSala {

	public int borrarSala(int id);

	public TSala buscarSalaPorID(int id);

	public int registrarSala(int numAsientos);

	public int modificarSala(TSala tSala);

	public List<TSala> mostrarListaSalas();

	public List<TSala> mostrarSalasPorPelicula(int id_peli);

}
