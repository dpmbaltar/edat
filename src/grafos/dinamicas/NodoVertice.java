package grafos.dinamicas;

/**
 * Implementación de Nodo Vértice para un elemento de tipo T.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 *
 * @param <T> el tipo de elemento
 * @param <E> el tipo de etiqueta
 */
public class NodoVertice<T, E> {

    /**
     * El elemento del vértice.
     */
    private T elemento;

    /**
     * El primer nodo adyacente.
     */
    private NodoAdyacente<T, E> primerAdyacente;

    /**
     * El siguiente nodo vértice.
     */
    private NodoVertice<T, E> siguienteVertice;

    /**
     * Constructor con el elemento del vértice.
     *
     * @param elemento el elemento
     */
    public NodoVertice(T elemento) {
        this(elemento, null);
    }

    /**
     * Constructor con el elemento del vértice y el siguiente vértice.
     *
     * @param elemento el elemento
     * @param siguienteVertice el vértice siguiente
     */
    public NodoVertice(T elemento, NodoVertice<T, E> siguienteVertice) {
        this.elemento = elemento;
        this.siguienteVertice = siguienteVertice;
    }

    /**
     * Devuelve el elemento del nodo vértice.
     *
     * @return el elemento
     */
    public T getElemento() {
        return elemento;
    }

    /**
     * Establece el elemento.
     *
     * @param elemento el elemento
     */
    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    /**
     * Devuelve el primer nodo adyacente.
     *
     * @return el nodo adyacente
     */
    public NodoAdyacente<T, E> getPrimerAdyacente() {
        return primerAdyacente;
    }

    /**
     * Establece el primer nodo adyacente.
     *
     * @param primerAdyacente el nodo adyacente
     */
    public void setPrimerAdyacente(NodoAdyacente<T, E> primerAdyacente) {
        this.primerAdyacente = primerAdyacente;
    }

    /**
     * Devuelve el nodo vértice siguiente.
     *
     * @return el nodo vértice
     */
    public NodoVertice<T, E> getSiguienteVertice() {
        return siguienteVertice;
    }

    /**
     * Establece el nodo vértice siguiente.
     *
     * @param siguienteVertice el nodo vértice
     */
    public void setSiguienteVertice(NodoVertice<T, E> siguienteVertice) {
        this.siguienteVertice = siguienteVertice;
    }

    /**
     * Verifica si el elemento de este nodo es igual al de otro nodo.
     * <p>Agregado para localizar nodos en una lista, necesario en {@link Grafo#caminoMasCorto}.</p>
     *
     * @see Grafo#caminoMasCorto
     * @see Lista#localizar
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        return elemento.equals(((NodoVertice<T, E>) o).getElemento());
    }
}
