package lineales.dinamicas;

/**
 * Implementación de un nodo de datos tipo String.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class NodoString {
	
	/**
	 * Elemento del nodo.
	 */
	private String elem;
	
	/**
	 * Enlace del nodo.
	 */
	private NodoString enlace;
	
	/**
	 * Crea y devuelve un nodo sin enlace y elemento nulo.
	 */
	public NodoString() {
		this(null, null);
	}
	
	/**
	 * Crea y devuelve un nodo sín enlace, y el elemento establecido.
	 * 
	 * @param elem
	 */
	public NodoString(String elem) {
		this(elem, null);
	}
	
	/**
	 * Crea y devuelve un nodo con el elemento y enlace establecidos.
	 * 
	 * @param elem
	 * @param enlace
	 */
	public NodoString(String elem, NodoString enlace) {
		this.elem = elem;
		this.enlace = enlace;
	}
	
	/**
	 * Devuelve el elemento del nodo.
	 * 
	 * @return
	 */
	public String getElem() {
		return elem;
	}
	
	/**
	 * Devuelve el enlace del nodo.
	 * 
	 * @return
	 */
	public NodoString getEnlace() {
		return enlace;
	}
	
	/**
	 * Establece el elemento del nodo.
	 * 
	 * @param elem
	 */
	public void setElem(String elem) {
		this.elem = elem;
	}
	
	/**
	 * Establece el enlace del nodo.
	 * 
	 * @param enlace
	 */
	public void setEnlace(NodoString enlace) {
		this.enlace = enlace;
	}
}
