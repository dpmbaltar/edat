package grafos.dinamicas;

import lineales.dinamicas.Lista;

/**
 * Estructura auxiliar para caminos de un grafo ponderado.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de elemento
 */
public class Camino<T> {

    /**
     * Longitud entre el origen y el destino.
     */
    private double longitud;

    /**
     * Lista de elementos del camino.
     */
    private Lista<T> elementos;

    /**
     * Constructor con longitud y elementos.
     *
     * @param longitud la longitud
     * @param elementos las elementos
     */
    public Camino(double longitud, Lista<T> elementos) {
        this.longitud = longitud;
        this.elementos = elementos;
    }

    /**
     * Constructor vacío.
     */
    public Camino() {
        this(0.0, new Lista<>());
    }

    /**
     * Devuelve la longitud del camino.
     *
     * @return la longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Establece la longitud del camino.
     *
     * @param longitud la longitud
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * Devuelve los elementos del camino.
     *
     * @return los elementos
     */
    public Lista<T> getElementos() {
        return elementos;
    }

    /**
     * Establece la lista de elementos del camino.
     *
     * @param elementos los elementos
     */
    public void setElementos(Lista<T> elementos) {
        this.elementos = elementos;
    }

    /**
     * Devuelve el origen del camino.
     *
     * @return el elemento origen
     */
    public T origen() {
        return elementos.esVacia() ? null : elementos.recuperar(1);
    }

    /**
     * Devuelve el destino del camino.
     *
     * @return el elemento destino
     */
    public T destino() {
        return elementos.esVacia() ? null : elementos.recuperar(elementos.longitud());
    }

    @Override
    public String toString() {
        return String.format("[%.0f:%s]", longitud, elementos.toString());
    }

    @Override
    public Camino clone() {
        return new Camino(longitud, elementos.clone());
    }

}
