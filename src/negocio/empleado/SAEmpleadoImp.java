package negocio.empleado;


import java.util.List;

import integracion.empleado.DAOEmpleado;
import integracion.factoriaDAO.DAOFactoria;

public class SAEmpleadoImp implements SAEmpleado {

	public int registrarEmpleado(TEmpleado empleado) {
		DAOEmpleado daoEmpleado = DAOFactoria.getInstancia().generarDAOEmpleado();
		TEmpleado empleadoLeido = daoEmpleado.buscarEmpleadoPorDNI(empleado.getDNI());
		if (empleadoLeido == null || (empleadoLeido != null && !empleadoLeido.isActivo()))
			return daoEmpleado.registrarEmpleado(empleado);
		return 0;
	}
	
	public int borrarEmpleado(int id) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");

		DAOEmpleado daoEmpleado = DAOFactoria.getInstancia().generarDAOEmpleado();
		TEmpleado empleadoLeido = daoEmpleado.buscarEmpleadoPorID(id);
		if (empleadoLeido != null) 
			return daoEmpleado.borrarEmpleado(id);
			
		return 0;
	}

	public int modificarEmpleado(TEmpleado empleado) {
		DAOEmpleado daoEmpleado = DAOFactoria.getInstancia().generarDAOEmpleado();
		TEmpleado empleadoLeido = daoEmpleado.buscarEmpleadoPorDNI(empleado.getDNI());
		if (empleadoLeido != null){
			daoEmpleado.modificarEmpleado(empleado);
			return empleadoLeido.getID();
		}
		return 0;
	}

	public TEmpleado buscarEmpleadoPorID(int id) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");

		DAOEmpleado daoEmpleado = DAOFactoria.getInstancia().generarDAOEmpleado();
		return daoEmpleado.buscarEmpleadoPorID(id);
	}

	public List<TEmpleado> mostrarListaEmpleados() {		
		DAOEmpleado daoEmpleado = DAOFactoria.getInstancia().generarDAOEmpleado();
		return daoEmpleado.mostrarListaEmpleados();
	}

	public List<TEmpleado> mostrarEmpleadosPorJornada(boolean esCompleta) {
		DAOEmpleado daoEmpleado = DAOFactoria.getInstancia().generarDAOEmpleado();
		return daoEmpleado.mostrarEmpleadosPorJornada(esCompleta);
	}	
}