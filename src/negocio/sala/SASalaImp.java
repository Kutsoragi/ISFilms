package negocio.sala;

import java.util.List;
import integracion.factoriaDAO.DAOFactoria;
import integracion.pelicula.DAOPelicula;
import integracion.sala.DAOSala;
import negocio.pelicula.TPelicula;

public class SASalaImp implements SASala {
	
	//-- TEMA 3 Página 148

	private final Integer ASIENTOS_MINIMO = 30;
	private final Integer ASIENTOS_MAXIMO = 200;
	
	public int registrarSala(int asientos){
		if (asientos < ASIENTOS_MINIMO) throw new IllegalArgumentException("Número de asientos inferior al mínimo (" + ASIENTOS_MINIMO.toString() + ").");
		if (asientos > ASIENTOS_MAXIMO) throw new IllegalArgumentException("Número de asientos superior al máximo (" + ASIENTOS_MAXIMO.toString() + ").");
		
		DAOSala daoSala = DAOFactoria.getInstancia().generarDAOSala();
		return daoSala.registrarSala(asientos);
	}
	
	public int modificarSala(TSala sala){
		DAOSala daoSala = DAOFactoria.getInstancia().generarDAOSala();
		TSala salaLeida = daoSala.buscarSalaPorID(sala.getID());
		if (salaLeida != null)
			return daoSala.modificarSala(sala);

		return 0;
	}
	
	public int borrarSala(int id){
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		
		DAOSala daoSala = DAOFactoria.getInstancia().generarDAOSala();
		TSala salaLeida = daoSala.buscarSalaPorID(id);
		if (salaLeida != null) 
			return daoSala.borrarSala(id);

		return 0;
	}
	
	public TSala buscarSalaPorID(int id){
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");

		DAOSala daoSala = DAOFactoria.getInstancia().generarDAOSala();
		return daoSala.buscarSalaPorID(id);
	}
	
	public List<TSala> mostrarListaSalas(){
		DAOSala daoSala = DAOFactoria.getInstancia().generarDAOSala();
		return daoSala.mostrarListaSalas();
	}
	
	public List<TSala> mostrarSalasPorPelicula(int idPelicula){
		if (idPelicula < 1) throw new IllegalArgumentException("ID incorrecto.");

		DAOPelicula daoPelicula = DAOFactoria.getInstancia().generarDAOPelicula();
		TPelicula peliculaLeida = daoPelicula.buscarPeliculaPorID(idPelicula);
		if (peliculaLeida != null) {
			DAOSala daoSala = DAOFactoria.getInstancia().generarDAOSala();
			return daoSala.mostrarSalasPorPelicula(idPelicula);
		}
			
		return null;
	}
}
