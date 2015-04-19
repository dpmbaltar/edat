package lineales.dinamicas;

public class NodoArbol {

	private int elemento;
	private NodoArbol izquierdo;
	private NodoArbol derecho;
	
	public NodoArbol(NodoArbol izquierdo, NodoArbol derecho) {
		this.izquierdo = null;
		this.derecho = null;
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
