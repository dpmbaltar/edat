package lineales.dinamicas;

/**
 * Implementación de Cola de Prioridad dinámica.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 *
 * @param <E> el tipo de los elementos
 */
public class ColaPrioridad<E, P extends Comparable<P>> {

    /**
     * El inicio de la lista de elementos ordenados por prioridad.
     */
    private NodoCP<E, P> inicio;

    /**
     * Constructor vacío.
     */
    public ColaPrioridad() {
        inicio = null;
    }

    public boolean insertar(E elemento, P prioridad) {
        boolean insertado = false;
        //TODO: insertar()
        return insertado;
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
    }

    /**
     * Crea y devuelve una copia exacta de la cola.
     */
    @Override
    public ColaPrioridad<E, P> clone() {
        ColaPrioridad<E, P> clon = new ColaPrioridad<>();

        if (inicio != null) {
            clon.inicio = new NodoCP<>(inicio.getPrioridad(), inicio.getElementos());
            NodoCP<E, P> nodoClon = clon.inicio;
            NodoCP<E, P> nodo = inicio.getEnlace();

            while (nodo != null) {
                nodoClon.setEnlace(new NodoCP<>(nodo.getPrioridad(), nodo.getElementos()));
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
        StringBuilder cadena = new StringBuilder('[');
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
