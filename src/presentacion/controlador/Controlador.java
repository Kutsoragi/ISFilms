package presentacion.controlador;

import java.util.List;
import java.util.Vector;

import negocio.empleado.TEmpleado;
import negocio.factoriaSA.SAFactoria;
import negocio.factura.Pair;
import negocio.factura.TFactura;
import negocio.pase.TPase;
import negocio.pelicula.TPelicula;
import negocio.sala.TSala;
import presentacion.factoria.GUIFactoria;
import presentacion.main.IGUI;

public class Controlador extends SingletonControlador{
	
	private SAFactoria saFactoria;
	private IGUI gui;
	
	public Controlador() {
		saFactoria = SAFactoria.getInstancia();
		gui = GUIFactoria.getInstancia().generarGUIMain();
	}
	
	public void accion(int evento, Object datos) {
		switch(evento) {
		
			//GUIMAIN
			case EventosGUI.MOSTRAR_INICIO:
				gui.actualizar(EventosGUI.MOSTRAR_INICIO, null);
				break;
				
			//GUISALA
			case EventosSala.REGISTRAR_SALA: {
				try {
					int res = saFactoria.generarSASala().registrarSala((Integer) datos);
					if (res > 0)
						gui.actualizar(EventosSala.REGISTRAR_SALA_OK, "Sala registrada con ID: " + res + ".");
					else
						gui.actualizar(EventosSala.REGISTRAR_SALA_KO, "La Sala no se pudo registrar con éxito.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosSala.REGISTRAR_SALA_KO, e.getMessage());
				}
				break; }
			case EventosSala.MODIFICAR_SALA: {
				int res = saFactoria.generarSASala().modificarSala((TSala) datos);
				if (res > 0)
					gui.actualizar(EventosSala.MODIFICAR_SALA_OK, "Sala modificada con éxito.");
				else
					gui.actualizar(EventosSala.MODIFICAR_SALA_KO, "Sala no encontrada.");
				break; }
			case EventosSala.BORRAR_SALA: {
				try {
					int res = saFactoria.generarSASala().borrarSala((Integer) datos);
					if (res > 0)
						gui.actualizar(EventosSala.BORRAR_SALA_OK, "Sala borrada con éxito.");
					else
						gui.actualizar(EventosSala.BORRAR_SALA_KO, "Sala no encontrada.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosSala.BORRAR_SALA_KO, e.getMessage());
				}
				break; }
			case EventosSala.BUSCAR_SALA: {
				try {
					TSala res = saFactoria.generarSASala().buscarSalaPorID((Integer) datos);
					if (res != null)
						gui.actualizar(EventosSala.BUSCAR_SALA_OK, res);
					else
						gui.actualizar(EventosSala.BUSCAR_SALA_KO, "Sala no encontrada.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosSala.BUSCAR_SALA_OK, e.getMessage());
				}
				break; }
			case EventosSala.MOSTRAR_LISTA_SALA: {
				List<TSala> res = saFactoria.generarSASala().mostrarListaSalas();
				if (!res.isEmpty())
					gui.actualizar(EventosSala.MOSTRAR_LISTA_SALA_OK, res);
				else
					gui.actualizar(EventosSala.MOSTRAR_LISTA_SALA_KO, "No hay Salas que mostrar.");
				break; }
			case EventosSala.MOSTRAR_POR_PELICULA_SALA: {
				try {
					List<TSala> res = saFactoria.generarSASala().mostrarSalasPorPelicula((Integer) datos);
					if (res != null) {
						if (!res.isEmpty())
							gui.actualizar(EventosSala.MOSTRAR_POR_PELICULA_SALA_OK, res);
						else
							gui.actualizar(EventosSala.MOSTRAR_POR_PELICULA_SALA_KO, "No hay Salas que mostrar.");	
					} else {
 						gui.actualizar(EventosSala.MOSTRAR_POR_PELICULA_SALA_KO, "Película no encontrada.");
					}
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosSala.MOSTRAR_LISTA_SALA_KO, e.getMessage());
				}
				break; }
			
			//GUIPELICULA
			case EventosPelicula.REGISTRAR_PELICULA: {
				int res = saFactoria.generarSAPelicula().registrarPelicula((TPelicula) datos);
				if (res > 0)
					gui.actualizar(EventosPelicula.REGISTRAR_PELICULA_OK, "Pélicula registrada con ID: " + res + ".");
				else
					gui.actualizar(EventosPelicula.REGISTRAR_PELICULA_KO, "La Pélicula no se pudo registrar con éxito.");
				break; }
			case EventosPelicula.MODIFICAR_PELICULA: {
				int res = saFactoria.generarSAPelicula().modificarPelicula((TPelicula) datos);
				if (res > 0)
					gui.actualizar(EventosPelicula.MODIFICAR_PELICULA_OK, "Pélicula modificada con éxito.");
				else
					gui.actualizar(EventosPelicula.MODIFICAR_PELICULA_KO, "Pélicula no encontrada.");
				break; }
			case EventosPelicula.BORRAR_PELICULA: {
				try {
					int res = saFactoria.generarSAPelicula().borrarPelicula((Integer) datos);
					if (res > 0)
						gui.actualizar(EventosPelicula.BORRAR_PELICULA_OK, "Pélicula borrada con éxito.");
					else
						gui.actualizar(EventosPelicula.BORRAR_PELICULA_KO, "Pélicula no encontrada.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosPelicula.BORRAR_PELICULA_KO, e.getMessage());
				}
				break; }
			case EventosPelicula.BUSCAR_PELICULA: {
				try {
					TPelicula res = saFactoria.generarSAPelicula().buscarPeliculaPorID((Integer) datos);
					if (res != null)
						gui.actualizar(EventosPelicula.BUSCAR_PELICULA_OK, res);
					else
						gui.actualizar(EventosPelicula.BUSCAR_PELICULA_KO, "Pélicula no encontrada.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosPelicula.BUSCAR_PELICULA_KO, e.getMessage());
				}
				break; }
			case EventosPelicula.MOSTRAR_LISTA_PELICULA: {
				List<TPelicula> res = saFactoria.generarSAPelicula().mostrarListaPeliculas();
				if (res != null)
					gui.actualizar(EventosPelicula.MOSTRAR_LISTA_PELICULA_OK, res);
				else
					gui.actualizar(EventosPelicula.MOSTRAR_LISTA_PELICULA_KO, "No hay Péliculas que mostrar.");
				break; }
			case EventosPelicula.MOSTRAR_POR_FECHA_PELICULA: {
				try {
					List<TPelicula> res = saFactoria.generarSAPelicula().mostrarPeliculasPorFecha((String) datos);
					if (!res.isEmpty())
						gui.actualizar(EventosPelicula.MOSTRAR_POR_FECHA_PELICULA_OK, res);
					else
						gui.actualizar(EventosPelicula.MOSTRAR_POR_FECHA_PELICULA_KO, "No hay Péliculas que mostrar.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosPelicula.MOSTRAR_POR_FECHA_PELICULA_KO, e.getMessage());
				}
				break; }
			
			//GUIPASE
			case EventosPase.REGISTRAR_PASE: {
				int res = saFactoria.generarSAPase().registrarPase((TPase) datos);
				if (res > 0)
					gui.actualizar(EventosPase.REGISTRAR_PASE_OK, "Pase registrado con ID: " + res + ".");
				else
					gui.actualizar(EventosPase.REGISTRAR_PASE_KO, "El Pase no se pudo registrar con éxito.");
				break; }
			case EventosPase.MODIFICAR_PASE: {
				int res = saFactoria.generarSAPase().modificarPase((TPase) datos);
				if (res > 0)
					gui.actualizar(EventosPase.MODIFICAR_PASE_OK, "Pase modificado con éxito.");
				else
					gui.actualizar(EventosPase.MODIFICAR_PASE_KO, "Hubo un error al modificar el pase.");
				break; }
			case EventosPase.BORRAR_PASE: {
				try {
					int res = saFactoria.generarSAPase().borrarPase((Integer) datos);
					if (res > 0)
						gui.actualizar(EventosPase.BORRAR_PASE_OK, "Pase borrado con éxito.");
					else
						gui.actualizar(EventosPase.BORRAR_PASE_KO, "Pase no encontrado.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosPase.BORRAR_PASE_KO, e.getMessage());
				}
				break; }
			case EventosPase.BUSCAR_PASE: {
				try {
					TPase res = saFactoria.generarSAPase().buscarPasePorID((Integer) datos);
					if (res != null)
						gui.actualizar(EventosPase.BUSCAR_PASE_OK, res);
					else
						gui.actualizar(EventosPase.BUSCAR_PASE_KO, "Pase no encontrado.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosPase.BUSCAR_PASE_KO, e.getMessage());
				}
				break; }
			case EventosPase.MOSTRAR_LISTA_PASE: {
				List<TPase> res = saFactoria.generarSAPase().mostrarListaPases();
				if (res != null)
					gui.actualizar(EventosPase.MOSTRAR_LISTA_PASE_OK, res);
				else
					gui.actualizar(EventosPase.MOSTRAR_LISTA_PASE_KO, "No hay Pases que mostrar.");
				break; }
			case EventosPase.MOSTRAR_POR_PELICULA_PASE: {
				try {
					List<TPase> res = saFactoria.generarSAPase().mostrarPasesPorPelicula((Integer) datos);
					if (res !=null){
						if (!res.isEmpty())
							gui.actualizar(EventosPase.MOSTRAR_POR_PELICULA_PASE_OK, res);
						else
							gui.actualizar(EventosPase.MOSTRAR_POR_PELICULA_PASE_KO, "No hay Pases que mostrar.");	
					}
					else{
						gui.actualizar(EventosPase.MOSTRAR_POR_PELICULA_PASE_KO, "No se encuentra la pelicula.");
					}
					
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosPase.MOSTRAR_POR_PELICULA_PASE_KO, e.getMessage());
				}
				break; }
			
			//GUIEMPLEADO
			case EventosEmpleado.REGISTRAR_EMPLEADO: {
				int res = saFactoria.generarSAEmpleado().registrarEmpleado((TEmpleado) datos);
				if (res > 0)
					gui.actualizar(EventosEmpleado.REGISTRAR_EMPLEADO_OK, "Empleado registrado con ID: " + res + ".");
				else
					gui.actualizar(EventosEmpleado.REGISTRAR_EMPLEADO_KO, "Ya existe un empleado con ese DNI.");
				break; }
			case EventosEmpleado.MODIFICAR_EMPLEADO: {
				int res = saFactoria.generarSAEmpleado().modificarEmpleado((TEmpleado) datos);
				if (res > 0)
					gui.actualizar(EventosEmpleado.MODIFICAR_EMPLEADO_OK, "Empleado modificado con éxito.");
				else
					gui.actualizar(EventosEmpleado.MODIFICAR_EMPLEADO_KO, "Empleado no encontrado.");
				break; }
			case EventosEmpleado.BORRAR_EMPLEADO: {
				try {
					int res = saFactoria.generarSAEmpleado().borrarEmpleado((Integer) datos);
					if (res > 0)
						gui.actualizar(EventosEmpleado.BORRAR_EMPLEADO_OK, "Empleado borrado con éxito.");
					else
						gui.actualizar(EventosEmpleado.BORRAR_EMPLEADO_KO, "Empleado no encontrado.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosEmpleado.BORRAR_EMPLEADO_KO, e.getMessage());
				}
				break; }
			case EventosEmpleado.BUSCAR_EMPLEADO: {
				try {
					TEmpleado res = saFactoria.generarSAEmpleado().buscarEmpleadoPorID((Integer) datos);
					if (res != null)
						gui.actualizar(EventosEmpleado.BUSCAR_EMPLEADO_OK, res);
					else
						gui.actualizar(EventosEmpleado.BUSCAR_EMPLEADO_KO, "Empleado no encontrado.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosEmpleado.BUSCAR_EMPLEADO_KO, e.getMessage());
				}
				break; }
			case EventosEmpleado.MOSTRAR_LISTA_EMPLEADO: {
				List<TEmpleado> res = saFactoria.generarSAEmpleado().mostrarListaEmpleados();
				if (res != null)
					gui.actualizar(EventosEmpleado.MOSTRAR_LISTA_EMPLEADO_OK, res);
				else
					gui.actualizar(EventosEmpleado.MOSTRAR_LISTA_EMPLEADO_KO, "No hay Empleados que mostrar.");
				break; }
			case EventosEmpleado.MOSTRAR_POR_JORNADA_EMPLEADO: {
				List<TEmpleado> res = saFactoria.generarSAEmpleado().mostrarEmpleadosPorJornada((Boolean) datos);
				if (!res.isEmpty())
					gui.actualizar(EventosEmpleado.MOSTRAR_POR_JORNADA_EMPLEADO_OK, res);
				else
					gui.actualizar(EventosEmpleado.MOSTRAR_POR_JORNADA_EMPLEADO_KO, "No hay Empleados que mostrar.");	
				break; }
			
			//GUIFACTURA
			case EventosFactura.ABRIR_FACTURA: {
				saFactoria.generarSAFactura().abrirFactura();
				break; }
			case EventosFactura.CERRAR_FACTURA: {
				try {
					Integer empleadoDeFactura = (Integer) datos;
					boolean res = saFactoria.generarSAFactura().cerrarFactura(empleadoDeFactura);
					if (res) //La factura se cerro con exito
						gui.actualizar(EventosFactura.CERRAR_FACTURA_OK, "La factura ha sido registrada con éxito.");
					else
						gui.actualizar(EventosFactura.CERRAR_FACTURA_KO, "El empleado no existe o no está disponible.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosFactura.CERRAR_FACTURA_KO, e.getMessage());
				}
				break; }
			case EventosFactura.A�ADIR_PASE: {
				try {
					@SuppressWarnings("unchecked")
					Pair<Integer, Integer> pase = (Pair<Integer, Integer>) datos;
					TFactura res = saFactoria.generarSAFactura().a�adirPase(pase.getFirst(), pase.getSecond());
					if (res != null)
						gui.actualizar(EventosFactura.A�ADIR_PASE_OK, res);
					else
						gui.actualizar(EventosFactura.A�ADIR_PASE_KO, "Pase no encontrado.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosFactura.A�ADIR_PASE_KO, e.getMessage());
				}
				break; }
			case EventosFactura.QUITAR_PASE: {
				try {
					@SuppressWarnings("unchecked")
					Pair<Integer, Integer> pase = (Pair<Integer, Integer>) datos;
					TFactura res = saFactoria.generarSAFactura().quitarPase(pase.getFirst(), pase.getSecond());
					if (res != null)
						gui.actualizar(EventosFactura.QUITAR_PASE_OK, res);
					else
						gui.actualizar(EventosFactura.QUITAR_PASE_KO, "Pase no encontrado en la Factura.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosFactura.QUITAR_PASE_KO, e.getMessage());
				}
				break; }
			case EventosFactura.DEVOLVER_PASE: {
				try {
					@SuppressWarnings("unchecked")
					Vector<Integer> paseDatos = (Vector<Integer>) datos;
					boolean res = saFactoria.generarSAFactura().devolverPase(paseDatos.get(0), paseDatos.get(1), paseDatos.get(2));
					if (res)
						gui.actualizar(EventosFactura.DEVOLVER_PASE_OK, "Pases devueltos correctamente.");
					else
						gui.actualizar(EventosFactura.DEVOLVER_PASE_KO, "Pase no encontrado en la Factura.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosFactura.DEVOLVER_PASE_KO, e.getMessage());
				}
				break; }
			case EventosFactura.BUSCAR_FACTURA: {
				try {
					TFactura res = saFactoria.generarSAFactura().buscarFacturaPorID((Integer) datos);
					if (res != null)
						gui.actualizar(EventosFactura.BUSCAR_FACTURA_OK, res);
					else
						gui.actualizar(EventosFactura.BUSCAR_FACTURA_KO, "Factura no encontrada.");
				} catch(IllegalArgumentException e) {
					gui.actualizar(EventosFactura.BUSCAR_FACTURA_KO, e.getMessage());
				}
				break; }
			case EventosFactura.MOSTRAR_LISTA_FACTURA: {
				List<TFactura> res = saFactoria.generarSAFactura().listarFacturas();
				if (!res.isEmpty())
					gui.actualizar(EventosFactura.MOSTRAR_LISTA_FACTURA_OK, res);
				else
					gui.actualizar(EventosFactura.MOSTRAR_LISTA_FACTURA_KO, "No hay Facturas que mostrar.");	
				break; }
		}
	}
	
	
	
}
