package negocio.empleado;

public abstract class TEmpleado {

	private final String PATRON_DNI = "[0-9]{8}\\w";
	
	private int id;
	private String dni;
	private String nombre;
	private boolean activo;
	private boolean completo;
	
	public TEmpleado(String dni, String nombre, boolean activo, boolean completo) {
		if (nombre.trim().length() == 0) throw new IllegalArgumentException("Nombre incorrecto.");
		if (!dni.trim().matches(PATRON_DNI)) throw new IllegalArgumentException("DNI incorrecto. Formato: 99999999X");
		
		this.id = 0;
		this.dni = dni;
		this.nombre = nombre;
		this.activo = activo;
		this.completo = completo;
	}
	
	public TEmpleado(int id, String dni, String nombre, boolean activo, boolean completo){
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		if (nombre.trim().length() == 0) throw new IllegalArgumentException("Nombre incorrecto.");
		if (!dni.trim().matches(PATRON_DNI)) throw new IllegalArgumentException("DNI incorrecto. Formato: 99999999X");
		
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.activo = activo;
		this.completo = completo;
	}
	
	public int getID() {
		return id;
	}

	public String getDNI() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isActivo() {
		return activo;
	}

	public boolean isCompleto() {
		return completo;
	}
	
	public abstract double getSueldo();
	public abstract void setSueldo(double sueldo);
	public abstract int getHoras();
	public abstract void setHoras(int horas);
	public abstract double getSueldoPorHoras();
	public abstract void setSueldoPorHoras(double sueldoPorHoras);

}
