package conjuntistas;

/**
 * Implementación de un nodo para un AVL que acepta elementos duplicados o
 * elementos distintos que al ser comparados representan el mismo valor.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <E> el tipo de elemento
 */
public class NodoMultiple<E> {

    /**
     * El elemento del nodo.
     */
    private E elemento;

    /**
     * El enlace del nodo.
     */
    private NodoMultiple<E> enlace;

    /**
     * Cantidad de referencias al elemento.
     */
    private int cantidad;

    /**
     * Constructor con elemento.
     *
     * @param elemento el elemento
     */
    public NodoMultiple(E elemento) {
        this(elemento, null, 1);
    }

    /**
     * Constructor con elemento y enlace.
     *
     * @param elemento el elemento
     * @param enlace el enlace del nodo
     */
    public NodoMultiple(E elemento, NodoMultiple<E> enlace) {
        this(elemento, enlace, 1);
    }

    /**
     * Constructor con elemento, enlace y cantidad.
     *
     * @param elemento el elemento
     * @param enlace el enlace del nodo
     * @param cantidad la cantidad de referencias al elemento
     */
    public NodoMultiple(E elemento, NodoMultiple<E> enlace, int cantidad) {
        this.elemento = elemento;
        this.enlace = enlace;
        this.cantidad = cantidad;
    }

    /**
     * Devuelve el elemento del nodo.
     *
     * @return el elemento del nodo
     */
    public E getElemento() {
        return elemento;
    }

    /**
     * Establece el elemento del nodo.
     *
     * @param elemento el nuevo elemento del nodo
     */
    public void setElemento(E elemento) {
        this.elemento = elemento;
    }

    /**
     * Devuelve el enlace del nodo.
     *
     * @return el enlace del nodo
     */
    public NodoMultiple<E> getEnlace() {
        return enlace;
    }

    /**
     * Establece el enlace del nodo.
     *
     * @param enlace el nuevo enlace del nodo
     */
    public void setEnlace(NodoMultiple<E> enlace) {
        this.enlace = enlace;
    }

    /**
     * Devuelve la cantidad de referencias al elemento.
     *
     * @return la cantidad de referencias al elemento
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de referencias al elemento.
     *
     * @param cantidad la nueva cantidad de referencias al elemento
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Aumenta la cantidad del elemento en 1.
     */
    public void aumentarCantidad() {
        cantidad++;
    }

    /**
     * Disminuye la cantidad del elemento en 1.
     */
    public void disminuirCantidad() {
        if (cantidad > 1) {
            cantidad--;
        }
    }
}
