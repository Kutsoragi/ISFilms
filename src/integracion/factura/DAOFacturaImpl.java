package integracion.factura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import negocio.factura.Pair;
import negocio.factura.TFactura;
import negocio.pase.TPase;

public class DAOFacturaImpl implements DAOFactura {

	Connection conexion;
	PreparedStatement pStatement;
	ResultSet rs;

	public TFactura buscarFacturaPorID(int id) {
		TFactura factura = null;
		
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT FA.id_factura, FA.id_empleado, FA.id_pase, FA.cantidad, FA.fecha, PA.precio FROM factura FA JOIN pase PA USING (id_pase) WHERE FA.id_factura = " + id);
			rs = pStatement.executeQuery();
			if (rs.next()){
				factura = new TFactura(rs.getInt(1), rs.getInt(2), rs.getString(5));
				factura.añadirPase(rs.getInt(3), rs.getInt(4), rs.getDouble(6));
				while(rs.next()){
					factura.añadirPase(rs.getInt(3), rs.getInt(4), rs.getDouble(6));
				}
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return factura;
	}

	public List<TFactura> mostrarListaFacturas() {
		LinkedList<TFactura> lista = new LinkedList<TFactura>();
		TFactura factura = null;
		int auxIdFactura;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT FA.id_factura, FA.id_empleado, FA.id_pase, FA.cantidad, FA.fecha, PA.precio FROM factura FA JOIN pase PA USING (id_pase) ORDER BY FA.id_factura ASC");
			rs = pStatement.executeQuery();
			if (rs.next()){
				auxIdFactura = rs.getInt(1);
				factura = new TFactura(rs.getInt(1), rs.getInt(2), rs.getString(5));
				factura.añadirPase(rs.getInt(3), rs.getInt(4), rs.getDouble(6));
				while(rs.next()){
					if(auxIdFactura == rs.getInt(1)){
						factura.añadirPase(rs.getInt(3), rs.getInt(4), rs.getDouble(6));
					}
					else{
						auxIdFactura = rs.getInt(1);
						lista.add(factura);
						factura = new TFactura(rs.getInt(1), rs.getInt(2), rs.getString(5));
						factura.añadirPase(rs.getInt(3), rs.getInt(4), rs.getDouble(6));
					}
				}
				lista.add(factura);
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public boolean cerrarFactura(TFactura tFactura, int id_empleado) {
		boolean cerrada = false;
		LinkedList<Pair<TPase, Integer>> lista = tFactura.getListaPases();
		int idFactura;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("INSERT INTO factura (id_factura, id_empleado, id_pase, cantidad, fecha, precio_total) VALUES (null,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			pStatement.setInt(1, id_empleado);
			pStatement.setInt(2, lista.get(0).getFirst().getID());
			pStatement.setInt(3, lista.get(0).getSecond());
			pStatement.setString(4, tFactura.getFecha());
			pStatement.setDouble(5, lista.get(0).getSecond() * lista.get(0).getFirst().getPrecio());
			pStatement.executeUpdate();
			rs = pStatement.getGeneratedKeys();
			if (rs.next()){
				idFactura = rs.getInt(1);
				for (int i = 1; i< lista.size(); i++){
					pStatement = conexion.prepareStatement("INSERT INTO factura (id_factura, id_empleado, id_pase, cantidad, fecha, precio_total) VALUES (?,?,?,?,?,?)");
					pStatement.setInt(1, idFactura);
					pStatement.setInt(2, id_empleado);
					pStatement.setInt(3, lista.get(i).getFirst().getID());
					pStatement.setInt(4, lista.get(i).getSecond());
					pStatement.setString(5, tFactura.getFecha());
					pStatement.setDouble(6, lista.get(i).getSecond() * lista.get(i).getFirst().getPrecio());
					pStatement.executeUpdate();
				}
				cerrada = true;
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cerrada;
	}

	public boolean devolverPase(int idFactura, int idPase, int cantidad) {
		boolean modificada = false;
		double nuevoPrecioTotal;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT FA.cantidad, FA.precio_total, PA.precio FROM factura FA JOIN pase PA USING (id_pase) WHERE FA.id_factura = " + idFactura + " AND PA.id_pase = " + idPase);
			rs = pStatement.executeQuery();
			if (rs.next()){
				nuevoPrecioTotal = rs.getDouble(2) - (rs.getDouble(3) * cantidad);
				System.out.println(nuevoPrecioTotal + " " + (rs.getInt(1) - cantidad));
				pStatement = conexion.prepareStatement("UPDATE factura SET cantidad = ?, precio_total = ? WHERE id_factura = " + idFactura + " AND id_pase = " + idPase);
				pStatement.setInt(1, rs.getInt(1) - cantidad);
				pStatement.setDouble(2, nuevoPrecioTotal);
				pStatement.executeUpdate();
				modificada = true;
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modificada;
	}
	
	public boolean paseActivo(int idPase){
		boolean activo = false;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("SELECT activo FROM pase WHERE id_pase = " + idPase);
			rs = pStatement.executeQuery();
			if (rs.next()){
				activo = rs.getBoolean(1);
			}
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activo;
	}
	
	public void eliminarPase(int idFactura, int idPase){
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/isfilms", "root", "");
			pStatement = conexion.prepareStatement("DELETE FROM factura WHERE id_factura = " + idFactura + " AND id_pase = " + idPase);
			pStatement.executeUpdate();
			closeConnection();
			closeStatement();
			closeResultSet();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
