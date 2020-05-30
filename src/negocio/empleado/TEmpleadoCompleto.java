package negocio.empleado;

public class TEmpleadoCompleto extends TEmpleado {
	
	private final double SUELDO_MINIMO = 950;
	
	private double sueldo;
	private static boolean completo = true;
	
	public TEmpleadoCompleto(String dni, String nombre, boolean activo, double sueldo) {
		super(dni, nombre, activo, completo);
		if (sueldo < SUELDO_MINIMO) throw new IllegalArgumentException("Sueldo inferior al mínimo (" + SUELDO_MINIMO + "€).");
		this.sueldo = sueldo;
	}
	
	public TEmpleadoCompleto(int id, String dni, String nombre, boolean activo, double sueldo) {
		super(id, dni, nombre, activo, completo);
		if (sueldo < SUELDO_MINIMO) throw new IllegalArgumentException("Sueldo inferior al mínimo (" + SUELDO_MINIMO + "€).");
		this.sueldo = sueldo;
	}

	public double getSueldo() {
		return sueldo;
	}
	
	public void setSueldo(double sueldo) {
		this.sueldo = sueldo;
	}

	public int getHoras() {return 0;}

	public void setHoras(int horas) {}

	public double getSueldoPorHoras() {return 0;}

	public void setSueldoPorHoras(double sueldoPorHoras) {}

}
