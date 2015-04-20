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
		//pruebaEliminarImpares();
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
		// Localizar elemento en l2
		System.out.println("Localizar elem. 6:");
		System.out.println(l2.localizar(6));
		System.out.println("Localizar elem. 2:");
		System.out.println(l2.localizar(2));
		System.out.println("Localizar elem. 10:");
		System.out.println(l2.localizar(10));
		// Obtener elem. @2, @5, @7
		System.out.println("Recuperar elem. @2:");
		System.out.println(l2.recuperar(2));
		System.out.println("Recuperar elem. @5:");
		System.out.println(l2.recuperar(5));
		System.out.println("Recuperar elem. @7 (no existe):");
		System.out.println(l2.recuperar(7));
		// Modificar l1
		System.out.println("Eliminar elem. @2: "+l1.eliminar(2));
		System.out.println(l1);
		System.out.println("Eliminar elem. @3: "+l1.eliminar(3));
		System.out.println(l1);
		System.out.println("Eliminar elem. @2: "+l1.eliminar(2));
		System.out.println(l1);
		// Modificar l2
		System.out.println("Eliminar elem. @1: "+l2.eliminar(1));
		System.out.println(l2);
		System.out.println("Eliminar elem. @4: "+l2.eliminar(4));
		System.out.println(l2);
		// Vaciar l1
		System.out.println("Vaciar l1:");
		l1.vaciar();
		System.out.println(l1);
		// Vaciar l1
		System.out.println("Vaciar l2:");
		l2.vaciar();
		System.out.println(l2);
		// Obtener cantidad de elementos de l1
		System.out.println("Longitud de l1: "+l1.longitud());
		System.out.println(l1);
		// Obtener cantidad de elementos de l2
		System.out.println("Longitud de l1: "+l2.longitud());
		System.out.println(l2);
	}
	
	public static void pruebaEliminarImpares() {
		ListaInt l1 = new ListaInt();
		int i;
		for (i = 0; i < 20; i++) {
			l1.insertar(i, l1.longitud() + 1);
		}
		
		l1.eliminarImpares();
		System.out.println(l1);
	}
}
