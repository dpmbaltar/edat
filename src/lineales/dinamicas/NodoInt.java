package lineales.dinamicas;

/**
 * Implementación de un nodo de datos tipo entero.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class NodoInt {
	
	/**
	 * Valor contenido por el nodo.
	 */
	private int elem;
	
	/**
	 * Enlace del nodo.
	 */
	private NodoInt enlace;
	
	/**
	 * Crea y devuelve un nodo sín enlace.
	 */
	public NodoInt(int elem) {
		this.elem = elem;
		enlace = null;
	}
	
	/**
	 * Crea y devuelve un nodo con el valor y enlace establecidos.
	 * 
	 * @param elem
	 * @param enlace
	 */
	public NodoInt(int elem, NodoInt enlace) {
		this.elem = elem;
		this.enlace = enlace;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getElem() {
		return elem;
	}
	
	/**
	 * 
	 * @return
	 */
	public NodoInt getEnlace() {
		return enlace;
	}
	
	/**
	 * 
	 * @param elem
	 */
	public void setElem(int elem) {
		this.elem = elem;
	}
	
	/**
	 * 
	 * @param enlace
	 */
	public void setEnlace(NodoInt enlace) {
		this.enlace = enlace;
	}
}
