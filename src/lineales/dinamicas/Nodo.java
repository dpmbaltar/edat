package lineales.dinamicas;

/**
 * Implementación de un nodo genérico de un solo enlace.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Nodo<T> {
	
	/**
	 * El elemento del nodo.
	 */
	private T elemento;
	
	/**
	 * El enlace del nodo.
	 */
	private Nodo<T> enlace;
	
	/**
	 * Crea y devuelve un nodo sin enlace y elemento nulo.
	 */
	public Nodo() {
		this(null, null);
	}
	
	/**
	 * Crea y devuelve un nodo sin enlace, y el elemento establecido.
	 * @param elemento
	 */
	public Nodo(T elemento) {
		this(elemento, null);
	}
	
	/**
	 * Crea y devuelve un nodo sin elemento, y el enlace establecido.
	 * @param elemento
	 */
	public Nodo(Nodo<T> enlace) {
		this(null, enlace);
	}
	
	/**
	 * Crea y devuelve un nodo con el elemento y enlace establecidos.
	 * @param elemento
	 * @param enlace
	 */
	public Nodo(T elemento, Nodo<T> enlace) {
		this.elemento = elemento;
		this.enlace = enlace;
	}
	
	/**
	 * Devuelve el elemento del nodo.
	 * @return
	 */
	public T getElemento() {
		return elemento;
	}
	
	/**
	 * Devuelve el enlace del nodo.
	 * @return
	 */
	public Nodo<T> getEnlace() {
		return enlace;
	}
	
	/**
	 * Establece el elemento del nodo.
	 * @param elemento
	 */
	public void setElemento(T elemento) {
		this.elemento = elemento;
	}
	
	/**
	 * Establece el enlace del nodo.
	 * @param enlace
	 */
	public void setEnlace(Nodo<T> enlace) {
		this.enlace = enlace;
	}
}
