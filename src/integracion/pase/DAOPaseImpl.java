package integracion.pase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.pase.TPase;

public class DAOPaseImpl implements DAOPase {

	Connection conexion;
	PreparedStatement pStatement;
	ResultSet rs;

	public int registrarPase(TPase tPase) {
		Integer idPase = 0;
		
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("INSERT INTO pase (id_pase, fecha, hora_ini, precio, hora_fin, id_peliculas, id_sala) VALUES (null,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pStatement.setString(1, tPase.getFecha());
			pStatement.setString(2, tPase.getHoraInicio());
			pStatement.setDouble(3, tPase.getPrecio());
			pStatement.setString(4, tPase.getHoraFin());
			pStatement.setInt(5, tPase.getPelicula());
			pStatement.setInt(6, tPase.getSala());
			pStatement.executeUpdate();
			rs = pStatement.getGeneratedKeys();
			if (rs.next()){
				idPase = rs.getInt(1);
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idPase;
	}

	public int modificarPase(TPase tPase) {
		Integer pase = 0;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("UPDATE pase SET fecha = ?, hora_ini = ?, precio = ?, hora_fin = ?, id_peliculas = ?, id_sala = ?  WHERE id_pase = " + tPase.getID());
			pStatement.setString(1, tPase.getFecha());
			pStatement.setString(2, tPase.getHoraInicio());
			pStatement.setDouble(3, tPase.getPrecio());
			pStatement.setString(4, tPase.getHoraFin());
			pStatement.setInt(5, tPase.getPelicula());
			pStatement.setInt(6, tPase.getSala());
			pStatement.executeUpdate();
			pase = tPase.getID();
			closeConnection();
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pase;
	}

	public int borrarPase(int id) {
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("UPDATE pase SET activo = 'false' WHERE id_pase = " + id);
			pStatement.executeUpdate();
			closeConnection();
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public TPase buscarPasePorID(int id) {
		TPase pase = null;
		
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT * FROM pase WHERE id_pase = " + id);
			rs = pStatement.executeQuery();
			if (rs.next()){
				pase = new TPase(rs.getInt(1), rs.getString(3).substring(0, 5), rs.getString(5).substring(0, 5), rs.getString(2), rs.getDouble(4), rs.getInt(7), rs.getInt(8));
				if (!rs.getBoolean(6)){
					pase = null;
				}
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pase;
	}

	public List<TPase> mostrarListaPases() {
		List<TPase> lista = new ArrayList<TPase>();
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT * FROM pase WHERE activo = 'true'");
			rs = pStatement.executeQuery();
			while (rs.next()){
				lista.add(new TPase(rs.getInt(1), rs.getString(3).substring(0, 5), rs.getString(5).substring(0, 5), rs.getString(2), rs.getDouble(4), rs.getInt(7), rs.getInt(8)));
			}
			closeConnection();
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List<TPase> mostrarPasesPorPeliculas(int idPeli) {
		List<TPase> lista = new ArrayList<TPase>();
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT * FROM pase WHERE activo = 'true' AND id_peliculas = " + idPeli);
			rs = pStatement.executeQuery();
			while (rs.next()){
				lista.add(new TPase(rs.getInt(1), rs.getString(3).substring(0, 5), rs.getString(5).substring(0, 5), rs.getString(2), rs.getDouble(4), rs.getInt(7), rs.getInt(8)));
			}
			closeConnection();
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public boolean franjaValida(TPase tPase){
		boolean valido = true;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT * FROM pase WHERE id_sala = ? AND fecha = ? AND ((hora_ini <= ? AND hora_fin >= ?) OR (hora_ini <= ? AND hora_fin >= ?))");
			pStatement.setInt(1, tPase.getSala());
			pStatement.setString(2, tPase.getFecha());
			pStatement.setString(3, tPase.getHoraInicio());
			pStatement.setString(4, tPase.getHoraInicio());
			pStatement.setString(5, tPase.getHoraFin());
			pStatement.setString(6, tPase.getHoraFin());
			rs = pStatement.executeQuery();
			if (rs.next()){
				valido = false;
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valido;
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
