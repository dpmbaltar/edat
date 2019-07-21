package grafos.dinamicas;

/**
 * Implementación de Nodo Adyacente para un elemento de tipo T.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de elemento
 */
public class NodoAdyacente<T> {

    /**
     * El vértice del nodo adyacente.
     */
    private NodoVertice<T> vertice;

    /**
     * El nodo adyacente siguiente.
     */
    private NodoAdyacente<T> siguienteAdyacente;

    /**
     * Constructor vacío.
     */
    public NodoAdyacente() {
        this(null, null);
    }

    /**
     * Constructor con el nodo vértice.
     *
     * @param vertice el nodo vértice
     */
    public NodoAdyacente(NodoVertice<T> vertice) {
        this(vertice, null);
    }

    /**
     * Constructor con el nodo vértice y el siguiente nodo adyacente.
     *
     * @param vertice el nodo vértice
     * @param siguienteAdyacente el nodo adyacente siguiente
     */
    public NodoAdyacente(NodoVertice<T> vertice, NodoAdyacente<T> siguienteAdyacente) {
        this.vertice = vertice;
        this.siguienteAdyacente = siguienteAdyacente;
    }

    /**
     * Devuelve el nodo vértice.
     *
     * @return el nodo vértice
     */
    public NodoVertice<T> getVertice() {
        return vertice;
    }

    /**
     * Establece el nodo vértice.
     *
     * @param vertice el nuevo nodo vértice
     */
    public void setVertice(NodoVertice<T> vertice) {
        this.vertice = vertice;
    }

    /**
     * Devuelve el nodo adyacente siguiente.
     *
     * @return el nodo adyancente
     */
    public NodoAdyacente<T> getSiguienteAdyacente() {
        return siguienteAdyacente;
    }

    /**
     * Establece el nodo adyacente siguiente.
     *
     * @param siguienteAdyacente el nodo adyacente
     */
    public void setSiguienteAdyacente(NodoAdyacente<T> siguienteAdyacente) {
        this.siguienteAdyacente = siguienteAdyacente;
    }
}
