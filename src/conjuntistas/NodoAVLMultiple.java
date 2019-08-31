package conjuntistas;

import lineales.dinamicas.Nodo;

/**
 * Implementación de un nodo para un AVL que acepta elementos duplicados o
 * elementos distintos que al ser comparados representan el mismo valor.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <E> el tipo de elemento
 */
public class NodoAVLMultiple<E> {

    /**
     * El elemento del nodo.
     */
    private E elemento;

    /**
     * El nodo izquierdo.
     */
    private NodoAVLMultiple<E> izquierdo;

    /**
     * El nodo derecho.
     */
    private NodoAVLMultiple<E> derecho;

    /**
     * El enlace a otro elemento que representa un mismo valor comparable.
     */
    private Nodo<E> enlace;

    /**
     * La altura del nodo.
     */
    private int altura;

    /**
     * Constructor con elemento.
     *
     * @param elemento el elemento
     */
    public NodoAVLMultiple(E elemento) {
        this(elemento, null, null, null, 0);
    }

    /**
     * Constructor con elemento y enlace.
     *
     * @param elemento el elemento
     * @param enlace el enlace
     */
    public NodoAVLMultiple(E elemento, Nodo<E> enlace) {
        this(elemento, null, null, enlace, 0);
    }

    /**
     * Constructor con elemento, izquierdo y derecho.
     *
     * @param elemento el elemento
     * @param izquierdo el nodo izquierdo
     * @param derecho el nodo derecho
     */
    public NodoAVLMultiple(E elemento, NodoAVLMultiple<E> izquierdo, NodoAVLMultiple<E> derecho) {
        this(elemento, izquierdo, derecho, null, 0);
        recalcularAltura();
    }

    /**
     * Constructor con elemento, izquierdo, derecho, enlace y altura.
     *
     * @param elemento el elemento
     * @param izquierdo el nodo izquierdo
     * @param derecho el nodo derecho
     * @param enlace el enlace
     * @param altura la altura
     */
    public NodoAVLMultiple(E elemento, NodoAVLMultiple<E> izquierdo, NodoAVLMultiple<E> derecho, Nodo<E> enlace,
            int altura) {
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.enlace = enlace;
        this.altura = altura;
    }

    /**
     * Devuelve el elemento.
     *
     * @return el elemento
     */
    public E getElemento() {
        return elemento;
    }

    /**
     * Establece el elemento.
     *
     * @param elemento el elemento
     */
    public void setElemento(E elemento) {
        this.elemento = elemento;
    }

    /**
     * Devuelve el nodo izquierdo.
     *
     * @return el nodo izquierdo
     */
    public NodoAVLMultiple<E> getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establece el nodo izquierdo.
     *
     * @param izquierdo el nodo
     */
    public void setIzquierdo(NodoAVLMultiple<E> izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Devuelve el nodo derecho.
     *
     * @return el nodo derecho
     */
    public NodoAVLMultiple<E> getDerecho() {
        return derecho;
    }

    /**
     * Establece el nodo derecho.
     *
     * @param derecho el nodo
     */
    public void setDerecho(NodoAVLMultiple<E> derecho) {
        this.derecho = derecho;
    }

    /**
     * Devuelve el enlace.
     *
     * @return el enlace
     */
    public Nodo<E> getEnlace() {
        return enlace;
    }

    /**
     * Establece el enlace.
     *
     * @param enlace el enlace
     */
    public void setEnlace(Nodo<E> enlace) {
        this.enlace = enlace;
    }

    /**
     * Devuelve la altura del nodo.
     *
     * @return la altura
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Establece la altura del nodo.
     *
     * @param altura la altura
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     * Recalcula la altura del nodo.
     */
    public void recalcularAltura() {
        int alturaIzquierdo = izquierdo != null ? izquierdo.getAltura() : -1;
        int alturaDerecho = derecho != null ? derecho.getAltura() : -1;
        altura = Math.max(alturaIzquierdo, alturaDerecho) + 1;
    }

    /**
     * Devuelve el balance del nodo.
     *
     * @return el balance del nodo
     */
    public int balance() {
        int alturaIzquierdo = izquierdo != null ? izquierdo.getAltura() : -1;
        int alturaDerecho = derecho != null ? derecho.getAltura() : -1;

        return alturaIzquierdo - alturaDerecho;
    }
}
