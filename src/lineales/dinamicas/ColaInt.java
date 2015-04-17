package lineales.dinamicas;

/**
 * Implementación de Cola dinámica.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class ColaInt {

	/**
	 * El nodo del elemento del frente de la cola.
	 */
	private NodoInt frente;
	
	/**
	 * El nodo del elemento del final de la cola.
	 */
	private NodoInt ultimo;
	
	/**
	 * Crea y devuelve la cola vacía.
	 */
	public ColaInt() {
		this.frente = null;
		this.ultimo = null;
	}
	
	/**
	 * Pone el elemento al final de la cola. Devuelve verdadero si el elemento
	 * se pudo agregar en la estructura y falso en caso contrario.
	 * 
	 * @param nuevoElem
	 * @return
	 */
	public boolean poner(int nuevoElem) {
		NodoInt nuevoNodo = new NodoInt(nuevoElem);
		
		if (this.esVacia()) {
			this.frente = this.ultimo = nuevoNodo;
		} else {
			nuevoNodo.setEnlace(this.frente);
			this.frente = nuevoNodo;
		}
		
		return true;
	}
	
	/**
	 * Saca el elemento que está en el frente de la cola. Devuelve verdadero si
	 * el elemento se pudo sacar (la estructura no estaba vacía) y falso en caso
	 * contrario.
	 * 
	 * @return
	 */
	public boolean sacar() {
		boolean resultado = false;
		
		if (!this.esVacia()) {
			this.frente = this.frente.getEnlace();
			if (this.frente == null) {
				this.ultimo = null;
			}
			resultado = true;
		}
		
		return resultado;
	}
	
	/**
	 * Devuelve el elemento que está en el frente.
	 * Precondición : la cola no está vacía.
	 * 
	 * @return
	 */
	public int obtenerFrente() {
		int elem = 0;
		
		if (!this.esVacia()) {
			elem = this.frente.getElem();
		}
		
		return elem;
	}
	
	/**
	 * Devuelve verdadero si la cola no tiene elementos y falso en caso
	 * contrario.
	 * 
	 * @return
	 */
	public boolean esVacia() {
		return (this.frente == null);
	}
	
	/**
	 * Saca todos los elementos de la estructura.
	 */
	public void vaciar() {
		this.frente = null;
		this.ultimo = null;
	}
	
	/**
	 * Devuelve una copia de la cola original
	 * 
	 * @return
	 */
	public ColaInt clonar() {
		ColaInt clon;
		
		
		
		return clon;
	}
	
	/**
	 * Crea y devuelve una cadena de caracteres formada por todos los elementos
	 * de la cola para poder mostrarla por pantalla.
	 * Es recomendable utilizar este método únicamente en la etapa de prueba y
	 * luego comentar el código.
	 */
	public String toString() {
		String cadena = "";
		
		
		
		return cadena;
	}
}
