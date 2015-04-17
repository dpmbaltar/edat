package pruebas.lineales;

import lineales.dinamicas.ListaInt;

/**
 * Implementación de Lista dinámica.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class PruebaLista {

	public static void main(String[] args) {
		pruebaListaInt();
	}
	
	public static void pruebaListaInt() {
		ListaInt l1 = new ListaInt();
		// Insertar 1, 2, 3, 4
		System.out.println("Crear lista vacía (l1):");
		System.out.println(l1);
		System.out.println("Insertar elem. 1@1: "+l1.insertar(1, 1));
		System.out.println(l1);
		System.out.println("Insertar elem. 2@2: "+l1.insertar(2, 2));
		System.out.println(l1);
		System.out.println("Insertar elem. 3@3: "+l1.insertar(3, 3));
		System.out.println(l1);
		System.out.println("Insertar elem. 4@4: "+l1.insertar(4, 4));
		System.out.println(l1);
		// Eliminar 1 y 3
		System.out.println("Eliminar elem. @1: "+l1.eliminar(1));
		System.out.println(l1);
		System.out.println("Eliminar elem. @2: "+l1.eliminar(2));
		System.out.println(l1);
		// Eliminar elemento inexistente en la lista
		System.out.println("Eliminar elem. @5: "+l1.eliminar(5));
		System.out.println(l1);
		// Obtener cantidad de elementos
		System.out.println("Longitud: "+l1.longitud());
		System.out.println(l1);
		System.out.println("Insertar elem. 10@3: "+l1.insertar(10, 3));
		System.out.println(l1);
		System.out.println("Insertar elem. 8@3: "+l1.insertar(8, 3));
		System.out.println(l1);
		System.out.println("Insertar elem. 6@3: "+l1.insertar(6, 3));
		System.out.println(l1);
		// Crear copia
		ListaInt l2 = l1.clonar();
		System.out.println("Crear copia de l1 (l2):");
		System.out.println(l2);
	}
}
