package negocio.pelicula;

public class TPelicula {

	private final Integer DURACION_MINIMO = 45;
	private final Integer DURACION_MAXIMO = 250;
	
	int id;
	int duracion;
	String nombre;

	public TPelicula(int duracion, String nombre) {
		if (duracion < DURACION_MINIMO) throw new IllegalArgumentException("Duración inferior al mínimo (" + DURACION_MINIMO.toString() + ").");
		if (duracion > DURACION_MAXIMO) throw new IllegalArgumentException("Duración superior al máximo (" + DURACION_MAXIMO.toString() + ").");
		if (nombre.trim().length() == 0) throw new IllegalArgumentException("Nombre incorrecto.");
		
		this.id = 0;
		this.duracion = duracion;
		this.nombre = nombre;
	}
	
	public TPelicula(int id, int duracion, String nombre) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		if (duracion < DURACION_MINIMO) throw new IllegalArgumentException("Duración inferior al mínimo (" + DURACION_MINIMO.toString() + ").");
		if (duracion > DURACION_MAXIMO) throw new IllegalArgumentException("Duración superior al máximo (" + DURACION_MAXIMO.toString() + ").");
		if (nombre.trim().length() == 0) throw new IllegalArgumentException("Nombre incorrecto.");
		
		this.id = id;
		this.duracion = duracion;
		this.nombre = nombre;
	}
	
	public int getID() {
		return id;
	}

	public int getDuracion() {
		return duracion;
	}
	
	public String getNombre() {
		return nombre;
	}
}
