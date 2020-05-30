  package negocio.factura;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import negocio.pase.TPase;

public class TFactura {
	
	private int id, idEmpleado;
	private double precioTotal;
	private String fecha;
	private int cantidadTotal;
	private LinkedList<Pair<TPase, Integer>> listaPases;

	public TFactura(){
		precioTotal = 0.00;
		cantidadTotal = 0;
		listaPases = new LinkedList<Pair<TPase, Integer>>();
		fecha = fechaActual();
	}
	
	public TFactura(int id, double precioTotal, String fecha, int cantidadTotal, int idEmpleado) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		if (precioTotal < 0.00) throw new IllegalArgumentException("Se ha introducido un precio negativo.");
		if (cantidadTotal < 0) throw new IllegalArgumentException("Cantidad Total negativa.");
		
		this.id = id;
		this.precioTotal = precioTotal;
		this.fecha = fecha;
		this.idEmpleado = idEmpleado;
		this.cantidadTotal = cantidadTotal;
		this.listaPases = new LinkedList<Pair<TPase, Integer>>();
	}
	
	public TFactura(int id, int idEmpleado, String fecha) {//Constructor para el daoFactura
		if (id < 1 || idEmpleado < 1) throw new IllegalArgumentException("ID incorrecto.");
		
		this.id = id;
		this.fecha = fecha;
		this.idEmpleado = idEmpleado;
		listaPases = new LinkedList<Pair<TPase, Integer>>();
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public String getFecha() {
		return fecha;
	}
	
	private String fechaActual(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return df.format(date);
	}
	
	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getCantidadTotal() {
		return cantidadTotal;
	}

	public LinkedList<Pair<TPase, Integer>> getListaPases() {
		return listaPases;
	}
	
	public Pair<TPase, Integer> getFirstPase() {
		if (!listaPases.isEmpty())
			return listaPases.get(0);
		
		return null;
	}
	
	public Pair<TPase, Integer> encontrarPase(int id) {
		Pair<TPase, Integer> pase = null;
		boolean encontrado = false;
		int i = 0;
		while (i < listaPases.size() && !encontrado) {
			Pair<TPase, Integer> p = listaPases.get(i);
			if (p.getFirst().getID() == id) {
				pase = p;
				encontrado = true;
			}
			i++;
		}
		return pase;	
	}
	
	public void añadirPase(int id, int cantidad, double precioPase) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		if (cantidad < 1) throw new IllegalArgumentException("Cantidad insuficiente.");
		//El precio de pase ya se verifica al registrar pase
		//if (precioPase < 0) throw new IllegalArgumentException("Precio negativo."); 
		
		Pair<TPase, Integer> pase = encontrarPase(id);
		if (encontrarPase(id) == null)
			listaPases.add(new Pair<TPase, Integer>(new TPase(id, precioPase), cantidad)); //AÃ±ade el pase con cantidad especifica
		else
			pase.setSecond(pase.getSecond()+cantidad); //Actualiza la cantidad del pase
		
		cantidadTotal += cantidad; //Actualiza la cantidad total
		precioTotal += cantidad*precioPase;
	}
	
	public void quitarPase(int id, int cantidad) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		if (cantidad < 1) throw new IllegalArgumentException("Cantidad insuficiente.");
		
		Pair<TPase, Integer> pase = encontrarPase(id);
		if (encontrarPase(id) == null)
			throw new NullPointerException("El pase especificado no existe.");
		
		double precioPase = pase.getFirst().getPrecio();
		int cantidadPase = pase.getSecond();
		if (cantidad >= cantidadPase) { //Si la cantidad especificada es mayor o igual lo borra y actualiza cantidadTotal 
			listaPases.remove(pase);
			cantidadTotal -= cantidadPase;
			precioTotal -= cantidadPase*precioPase;
		} else {
			pase.setSecond(cantidadPase-cantidad);
			cantidadTotal -= cantidad;	
			precioTotal -= cantidad*precioPase;
		}
	}
}
