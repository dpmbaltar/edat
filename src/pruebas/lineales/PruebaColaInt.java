package pruebas.lineales;

import lineales.estaticas.ColaInt;
//import lineales.dinamicas.ColaInt;

public class PruebaColaInt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		pruebaPonerSacarObtenerFrente();
		pruebaVaciar();
	}
	
	public static void pruebaPonerSacarObtenerFrente() {
		ColaInt cola;
		int elem = 1;
		
		System.out.println("Crear cola vacía:");
		cola = new ColaInt();
		System.out.println(cola);
		
		while (elem < ColaInt.TAM) {
			System.out.println("Poner \""+elem+"\": "+cola.poner(elem++));
			System.out.println(cola);
		}
		
		System.out.println("Poner \"10\" (cola llena): "+cola.poner(10));
		System.out.println(cola);
		System.out.println("Obtener frente (1): "+cola.obtenerFrente());
		System.out.println(cola);
		System.out.println("Sacar (1): "+cola.sacar());
		System.out.println(cola);
		System.out.println("Obtener frente (2): "+cola.obtenerFrente());
		System.out.println(cola);
		System.out.println("Sacar (2): "+cola.sacar());
		System.out.println(cola);
		System.out.println("Sacar (3): "+cola.sacar());
		System.out.println(cola);
		System.out.println("Obtener frente (4): "+cola.obtenerFrente());
		System.out.println(cola);
		System.out.println("Sacar (4): "+cola.sacar());
		System.out.println(cola);
		System.out.println("Sacar (5): "+cola.sacar());
		System.out.println(cola);
		System.out.println("Sacar (6): "+cola.sacar());
		System.out.println(cola);
		System.out.println("Sacar (7): "+cola.sacar());
		System.out.println(cola);
		System.out.println("Sacar (8): "+cola.sacar());
		System.out.println(cola);
		System.out.println("Sacar (9): "+cola.sacar());
		System.out.println(cola);
		System.out.println("Sacar (cola vacía): "+cola.sacar());
		System.out.println(cola);
		System.out.println("Poner (2): "+cola.poner(4));
		System.out.println(cola);
		System.out.println("Poner (8): "+cola.poner(8));
		System.out.println(cola);
	}
	
	public static void pruebaVaciar() {
		ColaInt cola = new ColaInt();
		int elem = 0;
		
		while (elem < ColaInt.TAM) {
			cola.poner(++elem);
		}
		
		System.out.println("Vaciar cola \""+cola+"\":");
		cola.vaciar();
		System.out.println(cola);
	}
}
