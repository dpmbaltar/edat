package pruebas.lineales;

//import lineales.estaticas.ColaInt;
import lineales.dinamicas.ColaInt;

public class PruebaCola {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			pruebaColaInt();
		} catch (AssertionError ae) {
			System.out.println(ae.getMessage());
		} finally {
			System.out.println("Prueba ColaInt OK");
		}
	}
	
	public static void pruebaColaInt() {
		ColaInt c1 = new ColaInt(),
				c2;
		
		// Probar poner()
		assert c1.poner(1);
		assert c1.poner(2);
		assert c1.poner(3);
		assert c1.poner(4);
		
		// Probar obtenerFrente()
		assert c1.obtenerFrente() == 1;
		
		// Probar toString()
		assert c1.toString().equals("[1, 2, 3, 4]");
		
		c2 = c1.clonar();
		
		// Probar clonar()
		assert c1.toString().equals(c2.toString());
		
		// Probar sacar()
		assert c2.sacar();
		assert c2.sacar();
		assert c2.sacar();
		assert c2.sacar();
		assert c2.sacar() == false;
		
		c1.vaciar();
		
		// Probar vaciar()
		assert c1.toString().equals("[]");
		
		// Probar esVacia()
		assert c1.esVacia();
		assert c2.esVacia();
	}
}
