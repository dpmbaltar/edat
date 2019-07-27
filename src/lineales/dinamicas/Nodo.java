package lineales.dinamicas;

/**
 * Implementación de un nodo genérico de un solo enlace.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <E> el tipo de elemento
 */
public class Nodo<E> {

    /**
     * El elemento del nodo.
     */
    private E elem;

    /**
     * El enlace del nodo.
     */
    private Nodo<E> enlace;

    /**
     * Constructor sin enlace y elemento nulo.
     */
    public Nodo() {
        this(null, null);
    }

    /**
     * Constructor sin enlace, y el elemento.
     *
     * @param elem el elemento
     */
    public Nodo(E elem) {
        this(elem, null);
    }

    /**
     * Constructor sin elemento, y el enlace.
     *
     * @param enlace el enlace
     */
    public Nodo(Nodo<E> enlace) {
        this(null, enlace);
    }

    /**
     * Constructor con el elemento y enlace.
     *
     * @param elem el elemento
     * @param enlace el enlace
     */
    public Nodo(E elem, Nodo<E> enlace) {
        this.elem = elem;
        this.enlace = enlace;
    }

    /**
     * Devuelve el elemento del nodo.
     *
     * @return el elemento
     */
    public E getElem() {
        return elem;
    }

    /**
     * Devuelve el enlace del nodo.
     *
     * @return el enlace
     */
    public Nodo<E> getEnlace() {
        return enlace;
    }

    /**
     * Establece el elemento del nodo.
     *
     * @param elem el elemento
     */
    public void setElemento(E elem) {
        this.elem = elem;
    }

    /**
     * Establece el enlace del nodo.
     *
     * @param enlace el enlace
     */
    public void setEnlace(Nodo<E> enlace) {
        this.enlace = enlace;
    }

}
