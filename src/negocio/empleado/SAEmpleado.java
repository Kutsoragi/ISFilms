package negocio.empleado;

import java.util.List;

public interface SAEmpleado {

	int registrarEmpleado(TEmpleado empleado);
	int borrarEmpleado(int id);
	int modificarEmpleado(TEmpleado empleado);
	TEmpleado buscarEmpleadoPorID(int id);
	List<TEmpleado> mostrarListaEmpleados();
	List<TEmpleado> mostrarEmpleadosPorJornada(boolean esCompleta);
	
}
