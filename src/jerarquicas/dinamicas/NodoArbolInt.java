package jerarquicas.dinamicas;

public class NodoArbolInt {

    private int elemento;
    private NodoArbolInt izquierdo;
    private NodoArbolInt derecho;

    public NodoArbolInt() {
        izquierdo = null;
        derecho = null;
    }

    public NodoArbolInt(int elemento) {
        this();
        this.elemento = elemento;
    }

    public NodoArbolInt(int elemento, NodoArbolInt izquierdo,
            NodoArbolInt derecho) {
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public NodoArbolInt(NodoArbolInt izquierdo, NodoArbolInt derecho) {
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public int getElemento() {
        return elemento;
    }

    public NodoArbolInt getIzquierdo() {
        return izquierdo;
    }

    public NodoArbolInt getDerecho() {
        return derecho;
    }

    public void setElemento(int e) {
        elemento = e;
    }

    public void setIzquierdo(NodoArbolInt i) {
        izquierdo = i;
    }

    public void setDerecho(NodoArbolInt d) {
        derecho = d;
    }
}
