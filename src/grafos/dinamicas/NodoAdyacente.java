package grafos.dinamicas;

/**
 * Implementación de Nodo Adyacente para un elemento de tipo T.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 *
 * @param <T> el tipo de elemento
 * @param <E> el tipo de etiqueta
 */
public class NodoAdyacente<T, E extends Comparable<E>> {

    /**
     * El vértice del nodo adyacente.
     */
    private NodoVertice<T, E> vertice;

    /**
     * El nodo adyacente siguiente.
     */
    private NodoAdyacente<T, E> siguienteAdyacente;

    /**
     * La etiqueta.
     */
    private E etiqueta;

    /**
     * Constructor vacío.
     */
    public NodoAdyacente() {
        this(null, null, null);
    }

    /**
     * Constructor con el nodo vértice.
     *
     * @param vertice el nodo vértice
     */
    public NodoAdyacente(NodoVertice<T, E> vertice) {
        this(vertice, null, null);
    }

    /**
     * Constructor con el nodo vértice y etiqueta.
     *
     * @param vertice el nodo vértice
     * @param etiqueta la etiqueta
     */
    public NodoAdyacente(NodoVertice<T, E> vertice, E etiqueta) {
        this(vertice, null, etiqueta);
    }

    /**
     * Constructor con el nodo vértice y el siguiente nodo adyacente.
     *
     * @param vertice el nodo vértice
     * @param siguienteAdyacente el nodo adyacente siguiente
     * @param etiqueta la etiqueta
     */
    public NodoAdyacente(NodoVertice<T, E> vertice, NodoAdyacente<T, E> siguienteAdyacente, E etiqueta) {
        this.vertice = vertice;
        this.siguienteAdyacente = siguienteAdyacente;
        this.etiqueta = etiqueta;
    }

    /**
     * Devuelve el nodo vértice.
     *
     * @return el nodo vértice
     */
    public NodoVertice<T, E> getVertice() {
        return vertice;
    }

    /**
     * Establece el nodo vértice.
     *
     * @param vertice el nuevo nodo vértice
     */
    public void setVertice(NodoVertice<T, E> vertice) {
        this.vertice = vertice;
    }

    /**
     * Devuelve el nodo adyacente siguiente.
     *
     * @return el nodo adyancente
     */
    public NodoAdyacente<T, E> getSiguienteAdyacente() {
        return siguienteAdyacente;
    }

    /**
     * Establece el nodo adyacente siguiente.
     *
     * @param siguienteAdyacente el nodo adyacente
     */
    public void setSiguienteAdyacente(NodoAdyacente<T, E> siguienteAdyacente) {
        this.siguienteAdyacente = siguienteAdyacente;
    }

    /**
     * Devuelve la etiqueta.
     *
     * @return la etiqueta
     */
    public E getEtiqueta() {
        return etiqueta;
    }

    /**
     * Establece la etiqueta.
     *
     * @param etiqueta la etiqueta
     */
    public void setEtiqueta(E etiqueta) {
        this.etiqueta = etiqueta;
    }
}
