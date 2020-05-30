package negocio.factura;

import java.util.List;

public interface SAFactura {
	
	public void abrirFactura ();
	public boolean cerrarFactura(int idEmpleado);
	public TFactura añadirPase(int idPase , int cantidad);
	public boolean devolverPase(int idFactura, int idPase, int cantidad);
	public TFactura quitarPase(int idPase, int cantidad);
	public TFactura buscarFacturaPorID(int id);
	public List<TFactura> listarFacturas();
	
}
