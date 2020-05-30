package integracion.factura;

import java.util.List;

import negocio.factura.TFactura;

public interface DAOFactura {

	boolean cerrarFactura(TFactura _tFactura, int id_empleado);
	boolean devolverPase(int idFactura, int idPase, int cantidad);
	TFactura buscarFacturaPorID(int id);
	List<TFactura> mostrarListaFacturas();
	public boolean paseActivo(int idPase);
	public void eliminarPase(int idFactura, int idPase);

}
