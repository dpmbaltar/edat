package grafos.dinamicas;

import lineales.dinamicas.Lista;

/**
 * Implementación de Nodo Vértice para un elemento de tipo T.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de elemento
 */
public class NodoVertice<T> {

    /**
     * El elemento del vértice.
     */
    private T elemento;

    /**
     * El primer nodo adyacente.
     */
    private NodoAdyacente<T> primerAdyacente;

    /**
     * El siguiente nodo vértice.
     */
    private NodoVertice<T> siguienteVertice;

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
    public NodoVertice(T elemento, NodoVertice<T> siguienteVertice) {
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
    public NodoAdyacente<T> getPrimerAdyacente() {
        return primerAdyacente;
    }

    /**
     * Establece el primer nodo adyacente.
     *
     * @param primerAdyacente el nodo adyacente
     */
    public void setPrimerAdyacente(NodoAdyacente<T> primerAdyacente) {
        this.primerAdyacente = primerAdyacente;
    }

    /**
     * Devuelve el nodo vértice siguiente.
     *
     * @return el nodo vértice
     */
    public NodoVertice<T> getSiguienteVertice() {
        return siguienteVertice;
    }

    /**
     * Establece el nodo vértice siguiente.
     *
     * @param siguienteVertice el nodo vértice
     */
    public void setSiguienteVertice(NodoVertice<T> siguienteVertice) {
        this.siguienteVertice = siguienteVertice;
    }

    /**
     * Verifica si el elemento de este nodo es igual al de otro nodo.
     * <p>Agregado para localizar nodos en una lista, necesario en {@link Grafo#caminoMasCorto}.</p>
     *
     * @see Grafo#caminoMasCorto
     * @see Lista#localizar
     */
    @Override
    public boolean equals(Object o) {
        return elemento.equals(((NodoVertice<T>) o).getElemento());
    }
}
