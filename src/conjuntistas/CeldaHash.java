/**
 *
 */
package conjuntistas;

/**
 * Implementación de una Celda Hash.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de elemento
 */
public class CeldaHash<T> {

    /**
     * El elemento de la celda.
     */
    private T elem;

    /**
     * El estado de la celda.
     */
    private int estado;

    /**
     * Constructor vacío.
     */
    public CeldaHash() {
        this(null, 0);
    }

    /**
     * Constructor con elemento.
     *
     * @param elem el elemento
     */
    public CeldaHash(T elem) {
        this(elem, 0);
    }

    /**
     * Constructor con elemento y estado.
     *
     * @param elem el elemento
     * @param estado el estado
     */
    public CeldaHash(T elem, int estado) {
        this.elem = elem;
        this.estado = estado;
    }

    /**
     * Devuelve el elemento de la celda.
     *
     * @return el elemento
     */
    public T getElem() {
        return elem;
    }

    /**
     * Establece el elemento de la celda.
     *
     * @param elem el elemento
     */
    public void setElem(T elem) {
        this.elem = elem;
    }

    /**
     * Devuelve el estado de la celda.
     *
     * @return el estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la celda.
     *
     * @param estado el estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }
}
