package negocio.pase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TPase {
	
	private final String FORMATO_FECHA = "YYYY-MM-DD";
	private final String PATRON_HORA = "(?:[0-1]\\d|2[0-3]):[0-5]\\d"; // HH:MM 24H
	private final double PRECIO_MINIMO = 0.50;
	
	private int id;
	private double precio;
	private String fecha;
	private String horaInicio;
	private String horaFin;
	private int idPelicula;
	private int idSala;
	
	public TPase(int id, double precio) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		if (precio < PRECIO_MINIMO) throw new IllegalArgumentException("Precio inferior al mínimo " + PRECIO_MINIMO + ".");
		
		this.id = id;
		this.precio = precio;
	}
	
	public TPase(String horaInicio, String horaFin, String fecha, double precio, int idPelicula, int idSala) {
		if (precio < PRECIO_MINIMO) throw new IllegalArgumentException("Precio inferior al mínimo " + PRECIO_MINIMO + ".");
		verificarFecha(fecha);
		verificarHora(horaInicio, "inicio");
		verificarHora(horaFin, "finalización");
		if (horaInicio.trim().compareTo(horaFin.trim()) >= 0) throw new IllegalArgumentException("Hora Inicio no puede ser mayor o igual que Hora Fin.");
		if (idPelicula < 1) throw new IllegalArgumentException("ID Película incorrecto.");
		if (idSala < 1) throw new IllegalArgumentException("ID Sala incorrecto.");
		
		this.id = 0;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.fecha = fecha;
		this.precio = precio;
		this.idPelicula = idPelicula;
		this.idSala = idSala;
	}
	
	public TPase(int id, String horaInicio, String horaFin, String fecha, double precio, int idPelicula, int idSala) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		if (precio < PRECIO_MINIMO) throw new IllegalArgumentException("Precio inferior al mínimo " + PRECIO_MINIMO + ".");
		verificarFecha(fecha);
		verificarHora(horaInicio, "inicio");
		verificarHora(horaFin, "finalización");
		if (horaInicio.trim().compareTo(horaFin.trim()) >= 0) throw new IllegalArgumentException("Hora Inicio no puede ser mayor o igual que Hora Fin.");
		if (idPelicula < 1) throw new IllegalArgumentException("ID Película incorrecto.");
		if (idSala < 1) throw new IllegalArgumentException("ID Sala incorrecto.");
		
		this.id = id;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.fecha = fecha;
		this.precio = precio;				
		this.idPelicula = idPelicula;
		this.idSala = idSala;
	}
	
	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		verificarFecha(fecha);
		this.fecha = fecha;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	
	public int getPelicula() {
		return idPelicula;
	}
	
	public void setPelicula(int idPelicula) {
		this.idPelicula = idPelicula;
	}
	
	public int getSala() {
		return idSala;
	}
	
	public void setSala(int idSala) {
		this.idSala = idSala;
	}
	
	private void verificarFecha(String fecha) {
		try {
			new SimpleDateFormat(FORMATO_FECHA).parse(fecha);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Fecha incorrecta. Formato fecha: " + FORMATO_FECHA + ".");
		}
	}
	
	private void verificarHora(String hora, String tipoHora) {
		if (!hora.trim().matches(PATRON_HORA)) throw new IllegalArgumentException("Hora de " + tipoHora + " incorrecta. Formato hora: HH:MM (24H).");
	}
}
