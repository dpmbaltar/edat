package lineales.dinamicas;

/**
 * Implementación de nodo para Cola de Prioridad con elementos tipo E y prioridad tipo P (Comparable).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 *
 * @param <E> el tipo de elemento
 * @param <P> el tipo de prioridad
 */
public class NodoCP<E, P extends Comparable<P>> {

    /**
     * Los elementos.
     */
    private Cola<E> elementos;

    /**
     * El nodo enlazado.
     */
    private NodoCP<E, P> enlace;

    /**
     * La prioridad.
     */
    private P prioridad;

    /**
     * Constructor con la prioridad.
     *
     * @param prioridad la prioridad
     */
    public NodoCP(P prioridad) {
        this(prioridad, new Cola<>(), null);
    }

    /**
     * Constructor con la prioridad y los elementos.
     *
     * @param prioridad la prioridad
     * @param elementos los elementos
     */
    public NodoCP(P prioridad, Cola<E> elementos) {
        this(prioridad, elementos, null);
    }

    /**
     * Constructor con la prioridad y el enlace.
     *
     * @param prioridad la prioridad
     * @param enlace el nodo enlace
     */
    public NodoCP(P prioridad, NodoCP<E, P> enlace) {
        this(prioridad, new Cola<>(), enlace);
    }

    /**
     * Constructor con la prioridad, elementos y el enlace.
     *
     * @param prioridad la prioridad
     * @param elementos la cola de elementos
     * @param enlace el nodo enlace
     */
    public NodoCP(P prioridad, Cola<E> elementos, NodoCP<E, P> enlace) {
        this.elementos = elementos;
        this.enlace = enlace;
        this.prioridad = prioridad;
    }

    /**
     * Devuelve la prioridad.
     *
     * @return la prioridad
     */
    public P getPrioridad() {
        return prioridad;
    }

    /**
     * Devuelve la cola de elementos.
     *
     * @return la cola de elementos
     */
    public Cola<E> getElementos() {
        return elementos;
    }

    /**
     * Devuelve el nodo enlace.
     *
     * @return el nodo enlace
     */
    public NodoCP<E, P> getEnlace() {
        return enlace;
    }

    /**
     * Establece el el nodo enlace.
     *
     * @param enlace el nodo enlace
     */
    public void setEnlace(NodoCP<E, P> enlace) {
        this.enlace = enlace;
    }
}
