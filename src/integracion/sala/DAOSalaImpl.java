package integracion.sala;

import negocio.sala.TSala;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOSalaImpl implements DAOSala{
	
	Connection conexion;
	PreparedStatement pStatement;
	ResultSet rs;

	public int borrarSala(int id){
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("UPDATE salas SET activo = 'false' WHERE id_sala = " + id);
			pStatement.executeUpdate();
			closeConnection();
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public TSala buscarSalaPorID(int id){
		TSala sala = null;
		
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT * FROM salas WHERE id_sala = " + id);
			rs = pStatement.executeQuery();
			if (rs.next()){
				sala = new TSala(rs.getInt(1), rs.getInt(2));
				if (!rs.getBoolean(3)){
					sala = null;
				}
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sala;	
	}

	public int registrarSala(int numAsientos){
		Integer idSala = 0;
		
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("INSERT INTO salas (id_sala, num_asiento) VALUES (null,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pStatement.setInt(1, numAsientos);
			pStatement.executeUpdate();
			rs = pStatement.getGeneratedKeys();
			if (rs.next()){
				idSala = rs.getInt(1);
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idSala;
	}

	public int modificarSala(TSala tSala){
		Integer sala = 0;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("UPDATE salas SET num_asiento = " + tSala.getAsientos() + " WHERE id_sala = " + tSala.getID());
			pStatement.executeUpdate();
			sala = tSala.getID();
			closeConnection();
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sala;
	}

	public List<TSala> mostrarListaSalas(){
		List<TSala> lista = new ArrayList<TSala>();
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT * FROM salas WHERE activo = 'true'");
			rs = pStatement.executeQuery();
			while (rs.next()){
				lista.add(new TSala(rs.getInt(1), rs.getInt(2)));
			}
			closeConnection();
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List<TSala> mostrarSalasPorPelicula(int id_peli){
		List<TSala> lista = new ArrayList<TSala>();
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT DISTINCT SA.id_sala, SA.num_asiento FROM salas SA JOIN pase PA USING (id_sala) WHERE SA.activo = 'true' AND PA.id_peliculas = " + id_peli);
			rs = pStatement.executeQuery();
			while (rs.next()){
				lista.add(new TSala(rs.getInt(1), rs.getInt(2)));
			}
			closeConnection();
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	private void closeConnection() throws SQLException {
		conexion.close();
	}
	
	private void closeResultSet() throws SQLException {
		rs.close();
	}
	
	private void closeStatement() throws SQLException {
		pStatement.close();
	}
	
	
}
