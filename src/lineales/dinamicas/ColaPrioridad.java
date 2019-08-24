package lineales.dinamicas;

/**
 * Implementación de Cola de Prioridad dinámica.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 *
 * @param <E> el tipo de los elementos
 * @param <P> el tipo de prioridad
 */
public class ColaPrioridad<E, P extends Comparable<P>> {

    /**
     * El inicio de la lista de elementos ordenados por prioridad.
     */
    private NodoCP<E, P> inicio;

    /**
     * La cantidad de elementos en la cola.
     */
    private int longitud;

    /**
     * Constructor vacío.
     */
    public ColaPrioridad() {
        inicio = null;
        longitud = 0;
    }

    /**
     * Inserta un elemento en la cola, y devuelve siempre verdadero (por ser impl. dinámica).
     *
     * @param elemento el elemento a insertar
     * @param prioridad la prioridad
     * @return siempre verdadero
     */
    public boolean insertar(E elemento, P prioridad) {
        if (inicio == null) {
            inicio = new NodoCP<>(prioridad);
            inicio.getElementos().poner(elemento);
        } else {
            NodoCP<E, P> nodo = inicio;
            NodoCP<E, P> nodoAnterior = null;

            while (nodo != null) {
                if (nodo.getPrioridad().compareTo(prioridad) < 0) {
                    nodoAnterior = nodo;
                    nodo = nodo.getEnlace();

                    // Insertar al final de la lista de prioridad
                    if (nodo == null) {
                        NodoCP<E, P> nodoNuevo = new NodoCP<>(prioridad, nodo);
                        nodoNuevo.getElementos().poner(elemento);
                        nodoAnterior.setEnlace(nodoNuevo);
                    }
                } else if (nodo.getPrioridad().compareTo(prioridad) > 0) {
                    if (nodoAnterior == null) {
                        inicio = new NodoCP<>(prioridad, inicio);
                        inicio.getElementos().poner(elemento);
                    } else {
                        NodoCP<E, P> nodoNuevo = new NodoCP<>(prioridad, nodo);
                        nodoNuevo.getElementos().poner(elemento);
                        nodoAnterior.setEnlace(nodoNuevo);
                    }

                    nodo = null;
                } else {
                    nodo.getElementos().poner(elemento);
                    nodo = null;
                }
            }
        }

        longitud++;

        return true;
    }

    /**
     * Elimina el frente de la cola, y devuelve verdadero si el frente fue eliminado, falso en caso contrario.
     *
     * @return verdadero si el frente fue eliminado, falso en caso contrario
     */
    public boolean eliminarFrente() {
        boolean eliminado = false;

        if (inicio != null) {
            eliminado = inicio.getElementos().sacar();
            longitud--;

            if (inicio.getElementos().esVacia()) {
                inicio = inicio.getEnlace();
            }
        }

        return eliminado;
    }

    /**
     * Devuelve el elemento del frente de la cola.
     *
     * @return el elemento del frente, nulo si no hay elementos
     */
    public E obtenerFrente() {
        return inicio != null ? inicio.getElementos().obtenerFrente() : null;
    }

    /**
     * Devuelve la cantidad de elementos en la cola.
     *
     * @return la cantidad de elementos
     */
    public int longitud() {
        return longitud;
    }

    /**
     * Verifica si la cola está vacía.
     *
     * @return verdadero si la cola está vacía, falso en caso contrario
     */
    public boolean esVacia() {
        return inicio == null;
    }

    /**
     * Quita todos los elementos de la cola.
     */
    public void vaciar() {
        inicio = null;
        longitud = 0;
    }

    /**
     * Crea y devuelve una copia exacta de la cola.
     */
    @Override
    public ColaPrioridad<E, P> clone() {
        ColaPrioridad<E, P> clon = new ColaPrioridad<>();

        if (inicio != null) {
            clon.inicio = new NodoCP<>(inicio.getPrioridad(), inicio.getElementos().clone());
            NodoCP<E, P> nodoClon = clon.inicio;
            NodoCP<E, P> nodo = inicio.getEnlace();

            while (nodo != null) {
                nodoClon.setEnlace(new NodoCP<>(nodo.getPrioridad(), nodo.getElementos().clone()));
                nodoClon = nodoClon.getEnlace();
                nodo = nodo.getEnlace();
            }
        }

        return clon;
    }

    /**
     * Devuelve la representación en forma de cadena de la cola.
     */
    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder("[");
        NodoCP<E, P> nodo = inicio;

        while (nodo != null) {
            cadena.append(nodo.getPrioridad()).append(':').append(nodo.getElementos());
            nodo = nodo.getEnlace();

            if (nodo != null) {
                cadena.append(", ");
            }
        }

        cadena.append(']');

        return cadena.toString();
    }
}
