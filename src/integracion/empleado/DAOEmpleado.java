package integracion.empleado;

import java.util.List;

import negocio.empleado.TEmpleado;

public interface DAOEmpleado {

	int registrarEmpleado(TEmpleado tEmpleado);
	int borrarEmpleado(int id);
	void modificarEmpleado(TEmpleado tEmpleado);
	TEmpleado buscarEmpleadoPorID(int id);
	List<TEmpleado> mostrarListaEmpleados();
	List<TEmpleado> mostrarEmpleadosPorJornada(boolean esCompleta);
	public TEmpleado buscarEmpleadoPorDNI(String dni);
}
