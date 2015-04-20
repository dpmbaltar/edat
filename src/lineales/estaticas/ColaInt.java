package lineales.estaticas;

/**
 * Implementación de Cola estática.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class ColaInt {
	
	/**
	 * El tamaño de la cola.
	 */
	public static final int TAM = 10;
	
	/**
	 * Los elementos de la cola.
	 */
	private int[] cola;
	
	/**
	 * Posición del elemento del frente de la cola; 0 si la cola está vacía.
	 */
	private int frente;
	
	/**
	 * Posición del último elemento de la cola; 0 si la cola está vacía.
	 */
	private int ultimo;
	
	/**
	 * Crea y devuelve la cola vacía.
	 */
	public ColaInt() {
		this.cola = new int[TAM];
		this.frente = 0;
		this.ultimo = 0;
	}
	
	/**
	 * Pone el elemento al final de la cola. Devuelve verdadero si el elemento
	 * se pudo agregar en la estructura y falso en caso contrario.
	 * 
	 * @param nuevoElem
	 * @return
	 */
	public boolean poner(int nuevoElem) {
		boolean resultado = false;
		int ultimo = (this.ultimo + 1) % TAM;
		
		if (this.esVacia() || ultimo != this.frente) {
			this.cola[this.ultimo] = nuevoElem;
			this.ultimo = ultimo;
			resultado = true;
		}
		
		return resultado;
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
			this.frente = (this.frente + 1) % TAM;
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
			elem = cola[this.frente];
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
		return (frente == ultimo);
	}
	
	/**
	 * Saca todos los elementos de la estructura.
	 */
	public void vaciar() {
		this.frente = this.ultimo;
	}
	
	/**
	 * Devuelve una copia de la cola original
	 * 
	 * @return
	 */
	public ColaInt clonar() {
		ColaInt clon = new ColaInt();
		int frente = this.frente,
			ultimo = this.ultimo;
		
		if (!this.esVacia()) {
			while (frente != ultimo) {
				clon.poner(cola[frente]);
				frente++;
				frente%= TAM;
			}
		}
		
		return clon;
	}
	
	/**
	 * Crea y devuelve una cadena de caracteres formada por todos los elementos
	 * de la cola para poder mostrarla por pantalla.
	 * Es recomendable utilizar este método únicamente en la etapa de prueba y
	 * luego comentar el código.
	 */
	public String toString() {
		String cadena = "[";
		int frente = this.frente,
			ultimo = this.ultimo;
		
		if (!this.esVacia()) {
			while (frente != ultimo) {
				cadena+= cola[frente]+" ";
				frente++;
				frente%= TAM;
			}
		}
		
		cadena = cadena.trim()+"]";
		
		return cadena;
	}
}
