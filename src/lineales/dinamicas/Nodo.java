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
    private E elemento;

    /**
     * El enlace del nodo.
     */
    private Nodo<E> enlace;

    /**
     * Constructor con elemento.
     *
     * @param elemento el elemento
     */
    public Nodo(E elemento) {
        this(elemento, null);
    }

    /**
     * Constructor con enlace.
     *
     * @param enlace el enlace
     */
    public Nodo(Nodo<E> enlace) {
        this(null, enlace);
    }

    /**
     * Constructor con elemento y enlace.
     *
     * @param elemento el elemento
     * @param enlace el enlace
     */
    public Nodo(E elemento, Nodo<E> enlace) {
        this.elemento = elemento;
        this.enlace = enlace;
    }

    /**
     * Devuelve el elemento del nodo.
     *
     * @return el elemento
     */
    public E getElemento() {
        return elemento;
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
     * @param elemento el elemento
     */
    public void setElemento(E elemento) {
        this.elemento = elemento;
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
