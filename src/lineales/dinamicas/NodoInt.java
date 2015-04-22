package lineales.dinamicas;

/**
 * Implementación de un nodo de datos tipo entero.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class NodoInt {
	
	/**
	 * Elemento del nodo.
	 */
	private int elem;
	
	/**
	 * Enlace del nodo.
	 */
	private NodoInt enlace;
	
	/**
	 * Crea y devuelve un nodo sin enlace y elemento "0".
	 */
	public NodoInt() {
		this(0, null);
	}
	
	/**
	 * Crea y devuelve un nodo sín enlace, y el elemento establecido.
	 * 
	 * @param elem
	 */
	public NodoInt(int elem) {
		this(elem, null);
	}
	
	/**
	 * Crea y devuelve un nodo con el elemento y enlace establecidos.
	 * 
	 * @param elem
	 * @param enlace
	 */
	public NodoInt(int elem, NodoInt enlace) {
		this.elem = elem;
		this.enlace = enlace;
	}
	
	/**
	 * Devuelve el elemento del nodo.
	 * 
	 * @return
	 */
	public int getElem() {
		return elem;
	}
	
	/**
	 * Devuelve el enlace del nodo.
	 * 
	 * @return
	 */
	public NodoInt getEnlace() {
		return enlace;
	}
	
	/**
	 * Establece el elemento del nodo.
	 * 
	 * @param elem
	 */
	public void setElem(int elem) {
		this.elem = elem;
	}
	
	/**
	 * Establece el enlace del nodo.
	 * 
	 * @param enlace
	 */
	public void setEnlace(NodoInt enlace) {
		this.enlace = enlace;
	}
}
