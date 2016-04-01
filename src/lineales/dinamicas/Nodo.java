package lineales.dinamicas;

/**
 * Implementación de un nodo genérico de un solo enlace.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <E>
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
     * Crea y devuelve un nodo sin enlace y elemento nulo.
     */
    public Nodo() {
        this(null, null);
    }

    /**
     * Crea y devuelve un nodo sin enlace, y el elemento establecido.
     *
     * @param elemento
     */
    public Nodo(E elemento) {
        this(elemento, null);
    }

    /**
     * Crea y devuelve un nodo sin elemento, y el enlace establecido.
     *
     * @param elemento
     */
    public Nodo(Nodo<E> enlace) {
        this(null, enlace);
    }

    /**
     * Crea y devuelve un nodo con el elemento y enlace establecidos.
     *
     * @param elemento
     * @param enlace
     */
    public Nodo(E elemento, Nodo<E> enlace) {
        this.elemento = elemento;
        this.enlace = enlace;
    }

    /**
     * Devuelve el elemento del nodo.
     *
     * @return
     */
    public E getElemento() {
        return elemento;
    }

    /**
     * Devuelve el enlace del nodo.
     *
     * @return
     */
    public Nodo<E> getEnlace() {
        return enlace;
    }

    /**
     * Establece el elemento del nodo.
     *
     * @param elemento
     */
    public void setElemento(E elemento) {
        this.elemento = elemento;
    }

    /**
     * Establece el enlace del nodo.
     *
     * @param enlace
     */
    public void setEnlace(Nodo<E> enlace) {
        this.enlace = enlace;
    }
}
