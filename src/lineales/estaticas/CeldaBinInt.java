package lineales.estaticas;

public class CeldaBinInt {

	private boolean enUso;
	private int elemento;
	private int izquierdo;
	private int derecho;
	
	public CeldaBinInt() {
		enUso = false;
		izquierdo = derecho = -1;
	}
	
	public CeldaBinInt(int izquierdo, int derecho) {
		enUso = true;
		this.izquierdo = izquierdo;
		this.derecho = derecho;
	}
	
	public CeldaBinInt(int elemento, int izquierdo, int derecho) {
		this(izquierdo, derecho);
		this.elemento = elemento;
	}
	
	public int getElemento() {
		return elemento;
	}
	
	public int getIzquierdo() {
		return izquierdo;
	}
	
	public int getDerecho() {
		return derecho;
	}
	
	public boolean getEnUso() {
		return enUso;
	}
	
	public void setElemento(int e) {
		elemento = e;
	}
	
	public void setIzquierdo(int i) {
		izquierdo= i;
	}
	
	public void setDerecho(int d) {
		derecho = d;
	}
	
	public void setEnUso(boolean eu) {
		enUso = eu;
	}
}
