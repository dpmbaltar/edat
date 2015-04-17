package lineales.dinamicas;

/**
 * Implementación de Pila dinámica.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class PilaInt {
	
	/**
	 * El nodo que contiene el elemento tope de la pila.
	 */
	private NodoInt tope;
	
	/**
	 * Crea y devuelve la pila vacía.
	 */
	public PilaInt() {
		tope = null;
	}
	
	/**
	 * Pone el elemento nuevoElem en el tope de la pila. Devuelve verdadero si
	 * el elemento se pudo apilar y falso en caso contrario.
	 * 
	 * @param nuevoElem
	 * @return
	 */
	public boolean apilar(int nuevoElem) {
		NodoInt nuevoNodo = new NodoInt(nuevoElem, tope);
		tope = nuevoNodo;
		
		return true;
	}
	
	/**
	 * Saca el elemento del tope de la pila. Devuelve verdadero si la pila no
	 * estaba vacía al momento de desapilar (es decir que se pudo desapilar) y
	 * falso en caso contrario.
	 * 
	 * @return
	 */
	public boolean desapilar() {
		boolean resultado = false;
		
		if (tope != null) {
			tope = tope.getEnlace();
			resultado = true;
		}
		
		return resultado;
	}
	
	/**
	 * Devuelve el elemento en el tope de la pila.
	 * Precondición: la pila no está vacía.
	 * 
	 * @return
	 */
	public int obtenerTope() {
		int topeElem = 0;
		
		if (tope != null) {
			topeElem = tope.getElem();
		}
		
		return topeElem;
	}
	
	/**
	 * Devuelve verdadero si la pila no tiene elementos y falso en caso
	 * contrario.
	 * 
	 * @return
	 */
	public boolean esVacia() {
		return (tope == null);
	}
	
	/**
	 * Saca todos los elementos de la pila.
	 */
	public void vaciar() {
		tope = null;
	}
	
	/**
	 * Devuelve una copia de la pila original.
	 * 
	 * @return
	 */
	public PilaInt clonar() {
		PilaInt aux = new PilaInt(), clon = new PilaInt();
		NodoInt nodo;
		
		if (tope != null) {
			nodo = tope;
			while (nodo != null) {
				aux.apilar(nodo.getElem());
				nodo = nodo.getEnlace();
			}
			while (!aux.esVacia()) {
				clon.apilar(aux.obtenerTope());
				aux.desapilar();
			}
		}
		
		return clon;
	}
	
	/**
	 * Devuelve una cadena de caracteres formada por todos los elementos de la
	 * pila para poder mostrarla por pantalla.
	 * Es recomendable utilizar este método únicamente en la etapa de prueba y
	 * luego comentar el código.
	 */	
	public String toString() {
		String cadena = "[";
		NodoInt nodo;
		
		if (tope != null) {
			nodo = tope;
			while (nodo != null) {
				cadena+= nodo.getElem();
				nodo = nodo.getEnlace();
				if (nodo != null) {
					cadena+= " ";
				}
			}
		}
		
		cadena+= "]";
		
		return cadena;
	}
}
