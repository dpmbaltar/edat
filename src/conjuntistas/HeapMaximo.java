package conjuntistas;

/**
 * Implementación de Heap Máximo.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class HeapMaximo<T extends Comparable<T>> {

    /**
     * Indica la capacidad de elementos del Heap por defecto (1F).
     */
    private static final int TAM = 0x1F;

    /**
     * El índice del último elemento insertado al Heap.
     */
    private int ultimo;

    /**
     * Arreglo de elementos del Heap.
     */
    private Object[] heap;

    /**
     * Crea y devuelve un heap vacío, con capacidad para 15 elementos.
     */
    public HeapMaximo() {
        this(TAM);
    }

    /**
     * Crea y devuelve un heap vacío con capacidad especificada (debe ser > 0).
     *
     * @param capacidad
     */
    public HeapMaximo(int capacidad) {
        heap = new Object[capacidad > 0 ? capacidad : TAM];
        ultimo = 0;
    }

    /**
     * Inserta un nuevo elemento al Heap.
     *
     * @param elemento
     * @return
     */
    public boolean insertar(T elemento) {
        boolean resultado = false;

        if (ultimo < heap.length) {
            ultimo++;
            heap[ultimo - 1] = elemento;
            hacerSubir();
            resultado = true;
        }

        return resultado;
    }

    /**
     * Asegura que luego de insertar un elemento, la cima del Heap sea el
     * elemento correcto (el mínimo).
     */
    @SuppressWarnings("unchecked")
    private void hacerSubir() {
        if (ultimo > 1) {
            boolean subir = true;
            int posicionPadre = ultimo / 2,
                posicionElemento = ultimo;
            T elemento = (T) heap[posicionElemento - 1];

            while (subir && posicionPadre > 0) {
                T padre = (T) heap[posicionPadre - 1];
                if (padre != null && elemento.compareTo(padre) > 0) {
                    heap[posicionElemento - 1] = padre;
                    heap[posicionPadre - 1] = elemento;
                    posicionElemento = posicionPadre;
                    posicionPadre = posicionElemento / 2;
                } else {
                    subir = false;
                }
            }
        }
    }

    /**
     * Asegura que luego de borrar la cima, la nueva cima del Heap sea el
     * elemento correcto (el mínimo).
     */
    @SuppressWarnings("unchecked")
    private void hacerBajar() {
        if (ultimo > 1) {
            boolean bajar = true;
            int posicionPadre = 1;
            T elemento = (T) heap[0];

            while (bajar && ((posicionPadre * 2) + 1 <= heap.length)) {
                int posicionIzquierdo = 2 * posicionPadre,
                    posicionDerecho = (2 * posicionPadre) + 1;
                T izquierdo = (T) heap[posicionIzquierdo - 1],
                  derecho = (T) heap[posicionDerecho - 1];

                if (izquierdo != null && elemento.compareTo(izquierdo) < 0) {
                    heap[posicionPadre - 1] = izquierdo;
                    heap[posicionIzquierdo - 1] = elemento;
                    posicionPadre = posicionIzquierdo;
                } else if (derecho != null && elemento.compareTo(derecho) < 0) {
                    heap[posicionPadre - 1] = derecho;
                    heap[posicionDerecho - 1] = elemento;
                    posicionPadre = posicionDerecho;
                } else {
                    bajar = false;
                }
            }
        }
    }

    /**
     * Elimina el elemento de la cima del Heap.
     *
     * @return
     */
    public boolean eliminarCima() {
        boolean resultado = false;

        if (ultimo > 0) {
            heap[0] = heap[ultimo - 1];
            heap[ultimo - 1] = null;
            ultimo--;
            hacerBajar();
            resultado = true;
        }

        return resultado;
    }

    /**
     * Devuelve el elemento de la cima del Heap.
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public T recuperarCima() {
        return (T) heap[0];
    }

    /**
     * Devuelve verdadero si el Heap no tiene elementos, o falso en caso
     * contrario.
     *
     * @return
     */
    public boolean esVacio() {
        return ultimo < 1;
    }

    /**
     * Elimina todos los elementos del Heap.
     */
    public void vaciar() {
        for (; ultimo > 0; ultimo--) {
            heap[ultimo - 1] = null;
        }
    }

    /**
     * Devuelve la representación en forma de cadena de carácteres del Heap.
     */
    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder("[");

        if (ultimo > 0) {
            for (int i = 0; i < ultimo; i++) {
                cadena.append(heap[i]);
                if ((i + 1) < ultimo) {
                    cadena.append(", ");
                }
            }
        }

        cadena.append(']');

        return cadena.toString();
    }
}
