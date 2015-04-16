package lineales.estaticas;

public class PilaInt {
	
	private static final int TAM = 32;
	private int[] pila;
	private int tope;
	
	/**
	 * Crea y devuelve la pila vacía.
	 */
	PilaInt() {
		this.pila = new int[TAM];
		this.tope = -1;
	}
	
	/**
	 * Pone el elemento nuevoElem en el tope de la pila. Devuelve verdadero si
	 * el elemento se pudo apilar y falso en caso contrario.
	 * 
	 * @param nuevoElem
	 * @return
	 */
	public boolean apilar(int nuevoElem) {
		boolean resultado = false;
		
		return resultado;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean desapilar() {
		boolean resultado = false;
		
		return resultado;
	}
	
	/**
	 * 
	 * @return
	 */
	public int obtenerTope() {
		int topeElem;
		
		if (tope >= 0) {
			topeElem = pila[tope];
		}
		
		return topeElem;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean esVacia() {
		return (tope < 0);
	}
	
	/**
	 * 
	 */
	public void vaciar() {
		pila = new int[TAM];
	}
	
	/**
	 * 
	 * @return
	 */
	public PilaInt clonar() {
		PilaInt clon = null;
		
		return clon;
	}
	
	/**
	 * 
	 */
	public String toString() {
		String pilaString = "";
		
		return pilaString;
	}
}
