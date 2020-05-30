package integracion.pelicula;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.pelicula.TPelicula;

public class DAOPeliculaImpl implements DAOPelicula{
	
	Connection conexion;
	PreparedStatement pStatement;
	ResultSet rs;

	public int registrarPelicula(TPelicula tPelicula) {
		Integer idPeli = 0;
		
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("INSERT INTO peliculas (id_peliculas, nombre, duracion) VALUES (null,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pStatement.setString(1, tPelicula.getNombre());
			pStatement.setInt(2, tPelicula.getDuracion());
			pStatement.executeUpdate();
			rs = pStatement.getGeneratedKeys();
			if (rs.next()){
				idPeli = rs.getInt(1);
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idPeli;
	}

	public int modificarPelicula(TPelicula tPelicula) {
		Integer peli = 0;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("UPDATE peliculas SET nombre = ?, duracion = ?  WHERE id_peliculas = " + tPelicula.getID());
			pStatement.setString(1, tPelicula.getNombre());
			pStatement.setInt(2, tPelicula.getDuracion());
			pStatement.executeUpdate();
			peli = tPelicula.getID();
			closeConnection();
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return peli;
	}

	public int borrarPelicula(int id) {
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("UPDATE peliculas SET activo = 'false' WHERE id_peliculas = " + id);
			pStatement.executeUpdate();
			closeConnection();
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public TPelicula buscarPeliculaPorID(int id) {
		TPelicula peli = null;
		
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT * FROM peliculas WHERE id_peliculas = " + id);
			rs = pStatement.executeQuery();
			if (rs.next()){
				peli = new TPelicula(rs.getInt(1), rs.getInt(3), rs.getString(2));
				if (!rs.getBoolean(4)){
					peli = null;
				}
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return peli;
	}

	public List<TPelicula> mostrarListaPeliculas() {
		List<TPelicula> lista = new ArrayList<TPelicula>();
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT * FROM peliculas WHERE activo = 'true'");
			rs = pStatement.executeQuery();
			while (rs.next()){
				lista.add(new TPelicula(rs.getInt(1), rs.getInt(3), rs.getString(2)));
			}
			closeConnection();
			closeStatement();
			closeResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List<TPelicula> mostrarPeliculasPorFecha(String fecha) {
		List<TPelicula> lista = new ArrayList<TPelicula>();
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT DISTINCT PE.id_peliculas, PE.nombre, PE.duracion FROM peliculas PE JOIN pase PA USING (id_peliculas) WHERE PE.activo = 'true' AND PA.fecha = ?");
			pStatement.setString(1, fecha);
			rs = pStatement.executeQuery();
			while (rs.next()){
				lista.add(new TPelicula(rs.getInt(1), rs.getInt(3), rs.getString(2)));
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
