package negocio.sala;

public class TSala {
	
	private final Integer ASIENTOS_MINIMO = 30;
	private final Integer ASIENTOS_MAXIMO = 200;
	
	private int id;
	private int nAsientos;
	
	public TSala(int id, int nAsientos) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		if (nAsientos < ASIENTOS_MINIMO) throw new IllegalArgumentException("Número de asientos inferior al mínimo (" + ASIENTOS_MINIMO.toString() + ").");
		if (nAsientos > ASIENTOS_MAXIMO) throw new IllegalArgumentException("Número de asientos superior al máximo (" + ASIENTOS_MAXIMO.toString() + ").");
		
		this.id = id;
		this.nAsientos = nAsientos;
	}
	
	public int getID() {
		return id;
	}

	public int getAsientos() {
		return nAsientos;
	}

}
