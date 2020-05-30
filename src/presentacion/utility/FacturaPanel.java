package presentacion.utility;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import negocio.factura.Pair;
import negocio.factura.TFactura;
import negocio.pase.TPase;

@SuppressWarnings("serial")
public class FacturaPanel extends JPanel {

	JLabel facturaID, empleadoID, fecha, precioTotal;
	TablePanel paseTable;
	
	public FacturaPanel(Dimension tableDim) {
		init(tableDim);
	}
	
	private void init(Dimension tableDim) {
		setOpaque(false);
		setMaximumSize(new Dimension(1024, 460));
		
		JPanel facturaPanel = new JPanel();
		facturaPanel.setLayout(new BoxLayout(facturaPanel, BoxLayout.Y_AXIS));
		facturaPanel.setOpaque(false);
		facturaPanel.setMaximumSize(new Dimension(1024, 450));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setOpaque(false);
		topPanel.setMaximumSize(new Dimension(600, 30));
		
		facturaID = new JLabel("ID FACTURA: 1");
		empleadoID = new JLabel("ID EMPLEADO: 1");
		fecha = new JLabel("DD/MM/YYYY");
		
		topPanel.add(facturaID);
		topPanel.add(Box.createRigidArea(new Dimension(30, 0)));	
		topPanel.add(empleadoID);
		topPanel.add(Box.createRigidArea(new Dimension(320, 0)));		
		topPanel.add(fecha);
		
		//--
		
		paseTable = new TablePanel(tableDim, Arrays.asList("ID PASE", "CANTIDAD", "PRECIO"));
		facturaPanel.add(paseTable);
		
		//--
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.setOpaque(false);
		bottomPanel.setMaximumSize(new Dimension(600, 30));
		
		precioTotal = new JLabel("PRECIO TOTAL: 1000.00");
		precioTotal.setAlignmentX(SwingConstants.RIGHT);
		bottomPanel.add(precioTotal);
		
		//--
		
		facturaPanel.add(topPanel);
		facturaPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		facturaPanel.add(paseTable);
		facturaPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		facturaPanel.add(bottomPanel);
		
		add(facturaPanel);
	}
	
	public void MostrarTFactura(TFactura f) {
		facturaID.setText("ID FACTURA: " + f.getID());
		empleadoID.setText("ID EMPLEADO: " + f.getIdEmpleado());
		fecha.setText(f.getFecha().toString());
		precioTotal.setText("PRECIO TOTAL: " + f.getPrecioTotal());
		
		for (Pair<TPase, Integer> p : f.getListaPases())
			paseTable.addRow(new Object[]{p.getFirst().getID(), "x" + p.getSecond(), p.getFirst().getPrecio()}, false);
	}
}
