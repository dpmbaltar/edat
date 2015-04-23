package jerarquicas.dinamicas;

public class NodoArbolString {

	private String elemento;
	private NodoArbolString izquierdo;
	private NodoArbolString derecho;
	
	public NodoArbolString() {
		izquierdo = null;
		derecho = null;
	}
	
	public NodoArbolString(String elemento) {
		this();
		this.elemento = elemento;
	}
	
	public NodoArbolString(String elemento, NodoArbolString izquierdo, NodoArbolString derecho) {
		this.elemento = elemento;
		this.izquierdo = izquierdo;
		this.derecho = derecho;
	}
	
	public NodoArbolString(NodoArbolString izquierdo, NodoArbolString derecho) {
		this.izquierdo = izquierdo;
		this.derecho = derecho;
	}
	
	public String getElemento() {
		return elemento;
	}
	
	public NodoArbolString getIzquierdo() {
		return izquierdo;
	}
	
	public NodoArbolString getDerecho() {
		return derecho;
	}
	
	public void setElemento(String e) {
		elemento = e;
	}
	
	public void setIzquierdo(NodoArbolString i) {
		izquierdo = i;
	}
	
	public void setDerecho(NodoArbolString d) {
		derecho = d;
	}
}
