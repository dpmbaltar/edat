package conjuntistas;

import jerarquicas.dinamicas.Nodo;

/**
 * Implementación de un nodo para Árbol AVL.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <E>
 */
public class NodoAVL<E> {

    /**
     * La altura del nodo.
     */
    private int altura;
    
    /**
     * El elemento del nodo.
     */
    private E elemento;

    /**
     * El nodo izquierdo.
     */
    private NodoAVL<E> izquierdo;

    /**
     * El nodo derecho.
     */
    private NodoAVL<E> derecho;

    /**
     * Crea y devuelve un nodo sin elemento ni enlaces.
     */
    public NodoAVL() {
        this(null, null, null);
    }

    /**
     * Crea y devuelve un nodo con los enlaces izquierdo y derecho establecidos.
     * 
     * @param izquierdo
     * @param derecho
     */
    public NodoAVL(NodoAVL<E> izquierdo, NodoAVL<E> derecho) {
        this(null, izquierdo, derecho);
    }

    /**
     * Crea y devuelve un nodo con el elemento establecido.
     * 
     * @param elemento
     */
    public NodoAVL(E elemento) {
        this(elemento, null, null);
    }

    /**
     * Crea y devuelve un nodo con el elemento y los enlaces izquierdo y derecho
     * establecidos.
     * 
     * @param elemento
     * @param izquierdo
     * @param derecho
     */
    public NodoAVL(E elemento, NodoAVL<E> izquierdo, NodoAVL<E> derecho) {
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = 0;

        if (izquierdo != null) {
            this.altura = 1 + izquierdo.getAltura();
        }

        if (derecho != null && derecho.getAltura() > this.altura) {
            this.altura = 1 + derecho.getAltura();
        }
    }

    /**
     * Devuelve la altura del nodo.
     * 
     * @return
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Establece la altura del nodo.
     * 
     * @param altura
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     * Devuelve el elemento.
     * 
     * @return
     */
    public E getElemento() {
        return elemento;
    }

    /**
     * Establece el elemento.
     * 
     * @param elemento
     */
    public void setElemento(E elemento) {
        this.elemento = elemento;
    }

    /**
     * Devuelve el nodo izquierdo.
     * 
     * @return
     */
    public NodoAVL<E> getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establece el nodo izquierdo.
     * 
     * @param izquierdo
     */
    public void setIzquierdo(NodoAVL<E> izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Devuelve el nodo derecho.
     * 
     * @return
     */
    public NodoAVL<E> getDerecho() {
        return derecho;
    }

    /**
     * Establece el nodo derecho.
     * 
     * @param derecho
     */
    public void setDerecho(NodoAVL<E> derecho) {
        this.derecho = derecho;
    }
}
