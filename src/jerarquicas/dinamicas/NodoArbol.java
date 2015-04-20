package jerarquicas.dinamicas;

public class NodoArbol {

	private int elemento;
	private NodoArbol izquierdo;
	private NodoArbol derecho;
	
	public NodoArbol() {
		izquierdo = null;
		derecho = null;
	}
	
	public NodoArbol(int elemento) {
		this();
		this.elemento = elemento;
	}
	
	public NodoArbol(int elemento, NodoArbol izquierdo, NodoArbol derecho) {
		this.elemento = elemento;
		this.izquierdo = izquierdo;
		this.derecho = derecho;
	}
	
	public NodoArbol(NodoArbol izquierdo, NodoArbol derecho) {
		this.izquierdo = izquierdo;
		this.derecho = derecho;
	}
	
	public int getElemento() {
		return elemento;
	}
	
	public NodoArbol getIzquierdo() {
		return izquierdo;
	}
	
	public NodoArbol getDerecho() {
		return derecho;
	}
	
	public void setElemento(int e) {
		elemento = e;
	}
	
	public void setIzquierdo(NodoArbol i) {
		izquierdo = i;
	}
	
	public void setDerecho(NodoArbol d) {
		derecho = d;
	}
}
