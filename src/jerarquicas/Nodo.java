package jerarquicas;

/**
 * Implementación de un nodo de dos enlaces: izquierdo y derecho.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <E> el tipo de elemento
 */
public class Nodo<E> {

    /**
     * El elemento del nodo.
     */
    private E elemento;

    /**
     * El nodo izquierdo.
     */
    private Nodo<E> izquierdo;

    /**
     * El nodo derecho.
     */
    private Nodo<E> derecho;

    /**
     * Constructor vacío.
     */
    public Nodo() {
        this(null, null, null);
    }

    /**
     * Constructor con el elemento.
     *
     * @param elemento el elemento
     */
    public Nodo(E elemento) {
        this(elemento, null, null);
    }

    /**
     * Constructor con el elemento, los enlaces izquierdo y derecho.
     *
     * @param elemento el elemento
     * @param izquierdo el nodo izquierdo
     * @param derecho el nodo derecho
     */
    public Nodo(E elemento, Nodo<E> izquierdo, Nodo<E> derecho) {
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
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
    public Nodo<E> getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establece el nodo izquierdo.
     *
     * @param izquierdo el nodo
     */
    public void setIzquierdo(Nodo<E> izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Devuelve el nodo derecho.
     *
     * @return el nodo derecho
     */
    public Nodo<E> getDerecho() {
        return derecho;
    }

    /**
     * Establece el nodo derecho.
     *
     * @param derecho el nodo
     */
    public void setDerecho(Nodo<E> derecho) {
        this.derecho = derecho;
    }
}
