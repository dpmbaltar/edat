package jerarquicas.dinamicas;

/**
 * Implementación de un nodo genérico de doble enlace.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Nodo<E> {

	/**
	 * El elemento contenido por el nodo.
	 */
	private E elemento;
	
	/**
	 * El nodo izquierdo.
	 */
	private Nodo<E> izquierdo;
	
	/**
	 * El nodo derecho.
	 */
	private Nodo<E> derecho;
	
	/**
	 * Crea y devuelve un nodo sin enlazar.
	 */
	public Nodo() {
		this(null, null, null);
	}
	
	/**
	 * Crea y devuelve un nodo sin enlazar, estableciendo su elemento.
	 * @param elemento
	 */
	public Nodo(E elemento) {
		this(elemento, null, null);
	}
	
	/**
	 * Crea y devuelve un nodo con los enlaces establecidos.
	 * @param izquierdo
	 * @param derecho
	 */
	public Nodo(Nodo<E> izquierdo, Nodo<E> derecho) {
		this(null, izquierdo, derecho);
	}
	
	/**
	 * Crea y devuelve un nodo con los enlaces y elemento establecidos.
	 * @param elemento
	 * @param izquierdo
	 * @param derecho
	 */
	public Nodo(E elemento, Nodo<E> izquierdo, Nodo<E> derecho) {
		this.elemento = elemento;
		this.izquierdo = izquierdo;
		this.derecho = derecho;
	}

	/**
	 * Devuelve el elemento.
	 * @return
	 */
	public E getElemento() {
		return elemento;
	}

	/**
	 * Establece el elemento.
	 * @param elemento
	 */
	public void setElemento(E elemento) {
		this.elemento = elemento;
	}

	/**
	 * Devuelve el nodo izquierdo.
	 * @return
	 */
	public Nodo<E> getIzquierdo() {
		return izquierdo;
	}

	/**
	 * Establece el nodo izquierdo.
	 * @param izquierdo
	 */
	public void setIzquierdo(Nodo<E> izquierdo) {
		this.izquierdo = izquierdo;
	}

	/**
	 * Devuelve el nodo derecho.
	 * @return
	 */
	public Nodo<E> getDerecho() {
		return derecho;
	}

	/**
	 * Establece el nodo derecho.
	 * @param derecho
	 */
	public void setDerecho(Nodo<E> derecho) {
		this.derecho = derecho;
	}
}
