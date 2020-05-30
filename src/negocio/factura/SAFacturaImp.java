package negocio.factura;

import java.util.List;

import integracion.empleado.DAOEmpleado;
import integracion.factoriaDAO.DAOFactoria;
import integracion.factura.DAOFactura;
import integracion.pase.DAOPase;
import negocio.empleado.TEmpleado;
import negocio.pase.TPase;

public class SAFacturaImp implements SAFactura {

	private static TFactura _factura;
	
	public void abrirFactura() {
		_factura = new TFactura();
	}

	public boolean cerrarFactura(int idEmpleado) {
		if (idEmpleado < 1) throw new IllegalArgumentException("ID incorrecto.");
	
		DAOEmpleado daoEmpleado = DAOFactoria.getInstancia().generarDAOEmpleado();	
		TEmpleado empleadLeido = daoEmpleado.buscarEmpleadoPorID(idEmpleado);
		if (empleadLeido != null && empleadLeido.isActivo()) { //Si existe y esta activo
			DAOFactura daoFactura = DAOFactoria.getInstancia().generarDAOFactura();
			return daoFactura.cerrarFactura(_factura, idEmpleado);
		}
		
		return false;
	}

	public TFactura añadirPase(int idPase, int cantidad) {
		if (idPase < 1) throw new IllegalArgumentException("ID de Pase incorrecto.");
		if (cantidad < 1) throw new IllegalArgumentException("Cantidad insuficiente para aÃ±adir.");
		
		TFactura facturaAux = null; //Se usa para devolver unicamente la informacion necesaria de la factura actual
		
		DAOPase daoPase = DAOFactoria.getInstancia().generarDAOPase();
		TPase paseLeido = daoPase.buscarPasePorID(idPase);
		if (paseLeido != null) { //Si existe el pase
			//Actualiza la factura
			_factura.añadirPase(idPase, cantidad, paseLeido.getPrecio());
			
			//Prepara la factura auxiliar para actualizar la vista
			facturaAux = new TFactura();
			facturaAux.añadirPase(idPase, cantidad, paseLeido.getPrecio());
			facturaAux.setPrecioTotal(_factura.getPrecioTotal());
		}
		
		return facturaAux;
	}

	public boolean devolverPase(int idFactura, int idPase, int cantidad) {		
		if (idFactura < 1) throw new IllegalArgumentException("ID de Factura incorrecto.");
		if (idPase < 1) throw new IllegalArgumentException("ID de Pase incorrecto.");
		if (cantidad < 1) throw new IllegalArgumentException("Cantidad insuficiente para devolver.");
		
		DAOFactura daoFactura = DAOFactoria.getInstancia().generarDAOFactura();
		TFactura facturaLeida = daoFactura.buscarFacturaPorID(idFactura);
		if (facturaLeida != null) { //Se confirma que la factura existe
			Pair<TPase, Integer> paseEnFactura = facturaLeida.encontrarPase(idPase); 
			if (paseEnFactura != null && daoFactura.paseActivo(idPase)) { //Se confirma que el pase existe en la factura
				if (cantidad == paseEnFactura.getSecond()) { 
					daoFactura.eliminarPase(idFactura, idPase);
					return true;
				} else if (cantidad > paseEnFactura.getSecond()) {
					throw new IllegalArgumentException("La cantidad excede la correspendiente en la factura (" + paseEnFactura.getSecond() + ").");
				} else{
					return daoFactura.devolverPase(idFactura, idPase, cantidad);
				}
			}
		}
		return false;
	}

	public TFactura quitarPase(int idPase, int cantidad) {
		if (idPase < 1) throw new IllegalArgumentException("ID de Pase incorrecto.");
		if (cantidad < 1) throw new IllegalArgumentException("Cantidad insuficiente para quitar.");
		
		TFactura facturaAux = new TFactura();
		try {
			_factura.quitarPase(idPase, cantidad);
			facturaAux.añadirPase(idPase, cantidad, 1);
			facturaAux.setPrecioTotal(_factura.getPrecioTotal());
			//Factura auxiliar no interesa el precio
		} catch(NullPointerException e) { // El pase no existe en la factura
			throw e;
		}
		return facturaAux;
	}

	public TFactura buscarFacturaPorID(int id) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		
		DAOFactura daoFactura = DAOFactoria.getInstancia().generarDAOFactura();
		return daoFactura.buscarFacturaPorID(id);
	}

	public List<TFactura> listarFacturas() {
		DAOFactura daoFactura = DAOFactoria.getInstancia().generarDAOFactura();
		return daoFactura.mostrarListaFacturas();
	}

}
