package negocio.empleado;

public class TEmpleadoParcial extends TEmpleado {

	private final double SUELDO_POR_HORA_MINIMO = 7.50;
	
	private int horas;
	private double sueldoPorHoras;
	private static boolean completo = false;
	
	public TEmpleadoParcial(String dni, String nombre, boolean activo, int horas, double sueldoPorHoras) {
		super(dni, nombre, activo, completo);
		
		if (horas < 1) throw new IllegalArgumentException("Cantidad de horas insuficiente.");
		if (sueldoPorHoras < SUELDO_POR_HORA_MINIMO) throw new IllegalArgumentException("Sueldo por hora inferior al minimo (" + SUELDO_POR_HORA_MINIMO + ".");
		
		this.horas = horas;
		this.sueldoPorHoras = sueldoPorHoras;
	}
	
	public TEmpleadoParcial(int id, String dni, String nombre, boolean activo, int horas, double sueldoPorHoras) {
		super(id, dni, nombre, activo, completo);
		
		if (horas < 1) throw new IllegalArgumentException("Cantidad de horas insuficiente.");
		if (sueldoPorHoras < SUELDO_POR_HORA_MINIMO) throw new IllegalArgumentException("Sueldo por hora inferior al minimo (" + SUELDO_POR_HORA_MINIMO + ".");
		
		this.horas = horas;
		this.sueldoPorHoras = sueldoPorHoras;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public double getSueldoPorHoras() {
		return sueldoPorHoras;
	}

	public void setSueldoPorHoras(double sueldoPorHoras) {
		this.sueldoPorHoras = sueldoPorHoras;
	}

	public double getSueldo() {return 0;}

	public void setSueldo(double sueldo) {}

}
