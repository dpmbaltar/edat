package pruebas.lineales;

import lineales.dinamicas.Lista;

/**
 * Prueba implementación de Lista dinámica.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class PruebaLista {

	public static void main(String[] args) {
		try {
			pruebaInsertar();
			pruebaRecuperar();
			pruebaEliminar();
			pruebaLocalizar();
			pruebaLongitud();
			pruebaEsVacia();
			pruebaVaciar();
			pruebaClonar();
			pruebaToString();
			System.out.println("Prueba OK");
		} catch (AssertionError e) {
			System.out.println("Error de prueba: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	protected static void pruebaInsertar() {
		Lista<Integer> lista = new Lista<Integer>();
		assert lista.insertar(1, 1)
			 : "Insertar 1 en posición 1";
		assert lista.insertar(2, -1) == false
			 : "Insertar 2 en posición inválida (< 1)";
		assert lista.insertar(2, 3) == false
			 : "Insertar 2 en posición inválida (> longitud)";
	}
	
	protected static void pruebaRecuperar() {
		Lista<Integer> lista = new Lista<Integer>();
		lista.insertar(1, 1);
		lista.insertar(2, 2);
		lista.insertar(3, 3);
		assert lista.recuperar(3) == 3
			 : "Recuperar elemento de la posición 3";
	}
	
	protected static void pruebaEliminar() {
		Lista<Integer> lista = new Lista<Integer>();
		lista.insertar(1, 1);
		lista.insertar(2, 2);
		lista.insertar(3, 3);
		assert lista.eliminar(3)
			 : "Eliminar elemento en la posición 3";
		assert lista.recuperar(3) == null
			 : "Recuperar elemento eliminado (3)";
	}
	
	protected static void pruebaLocalizar() {
		Lista<Integer> lista = new Lista<Integer>();
		lista.insertar(1, 1);
		lista.insertar(2, 2);
		lista.insertar(2, 3);
		assert lista.localizar(2) == 2
			 : "Localizar 2";
		assert lista.localizar(3) == -1
			 : "Localizar elemento inexistente (3)";
	}
	
	protected static void pruebaLongitud() {
		Lista<Integer> lista = new Lista<Integer>();
		lista.insertar(1, 1);
		lista.insertar(2, 2);
		lista.insertar(3, 3);
		assert lista.longitud() == 3
			 : "Longitud debe ser 3";
		lista.eliminar(3);
		assert lista.longitud() == 2
			 : "Longitud debe ser 2";
		lista.eliminar(2);
		lista.eliminar(1);
		assert lista.longitud() == 0
			 : "Longitud debe ser 0";
	}
	
	protected static void pruebaEsVacia() {
		Lista<Integer> lista = new Lista<Integer>();
		assert lista.esVacia()
			 : "Lista vacía";
		lista.insertar(1, 1);
		assert lista.esVacia() == false
			 : "Lista no vacía";
	}
	
	protected static void pruebaVaciar() {
		Lista<Integer> lista = new Lista<Integer>();
		lista.insertar(1, 1);
		lista.insertar(2, 2);
		lista.insertar(3, 3);
		lista.vaciar();
		assert lista.esVacia()
			 : "Lista vacía";
		assert lista.longitud() == 0
			 : "Longitud debe ser 0";
	}
	
	protected static void pruebaClonar() {
		Lista<Integer> lista = new Lista<Integer>();
		lista.insertar(1, 1);
		lista.insertar(2, 2);
		lista.insertar(3, 3);
		Lista<Integer> clon = lista.clonar();
		assert lista.recuperar(1) == clon.recuperar(1)
			 : "Lista debe ser igual a su clon";
		assert lista.recuperar(2) == clon.recuperar(2)
			 : "Lista debe ser igual a su clon";
		assert lista.recuperar(3) == clon.recuperar(3)
			 : "Lista debe ser igual a su clon";
	}
	
	protected static void pruebaToString() {
		Lista<Integer> lista = new Lista<Integer>();
		lista.insertar(1, 1);
		lista.insertar(2, 2);
		lista.insertar(3, 3);
		assert lista.toString() == "[1 2 3]"
			 : "Lista con elementos 1, 2, 3 debe ser representada como [1 2 3] "
			 + "en forma de cadena";
	}
}
