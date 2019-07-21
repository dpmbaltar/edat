package conjuntistas;

/**
 * Implementación de Heap Mínimo.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class HeapMinimo<T extends Comparable<T>> {

    /**
     * Indica la capacidad de elementos del Heap por defecto (31).
     */
    private static final int TAM = 31;

    /**
     * El índice del último elemento insertado al Heap.
     */
    private int ultimo;

    /**
     * Arreglo de elementos del Heap.
     */
    private Object[] heap;

    /**
     * Constructor con capacidad para 15 elementos.
     */
    public HeapMinimo() {
        this(TAM);
    }

    /**
     * Constructor con capacidad especificada (debe ser mayor a cero).
     *
     * @param capacidad la capidad del Heap
     */
    public HeapMinimo(int capacidad) {
        heap = new Object[capacidad > 0 ? capacidad : TAM];
        ultimo = 0;
    }

    /**
     * Inserta un nuevo elemento al Heap.
     *
     * @param elemento el elemento a insertar
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean insertar(T elemento) {
        boolean insertado = false;

        if (ultimo < heap.length) {
            heap[ultimo] = elemento;
            ultimo++;
            hacerSubir();
            insertado = true;
        }

        return insertado;
    }

    /**
     * Asegura que luego de insertar un elemento, la cima del Heap sea el elemento correcto (el mínimo). Se calculan
     * las posiciones como si las posiciones del arreglo comenzaran en 1, pero se accede a los elementos iniciando
     * desde 0, es decir, se resta 1.
     */
    @SuppressWarnings("unchecked")
    private void hacerSubir() {
        if (ultimo > 1) {
            boolean subir = true;
            int posPadre = ultimo / 2, posElem = ultimo;
            T ultimoElem = (T) heap[posElem - 1];

            while (subir && posPadre > 0) {
                T padre = (T) heap[posPadre - 1];

                if (padre != null && ultimoElem.compareTo(padre) < 0) {
                    heap[posElem - 1] = padre;
                    heap[posPadre - 1] = ultimoElem;
                    posElem = posPadre;
                    posPadre = posElem / 2;
                } else {
                    subir = false;
                }
            }
        }
    }

    /**
     * Asegura que luego de borrar la cima, la nueva cima del Heap sea el elemento correcto (el mínimo). Se calculan
     * las posiciones como si las posiciones del arreglo comenzaran en 1, pero se accede a los elementos iniciando
     * desde 0, es decir, se resta 1.
     */
    @SuppressWarnings("unchecked")
    private void hacerBajar() {
        if (ultimo > 1) {
            boolean bajar = true;
            int posPadre = 1, posHijoMenor = -1, posIzquierdo, posDerecho;
            T ultimoElem = (T) heap[0], hijoMenor, izquierdo, derecho;

            while (bajar && ((posPadre * 2) + 1 <= heap.length)) {
                posIzquierdo = 2 * posPadre;
                posDerecho = (2 * posPadre) + 1;
                izquierdo = (T) heap[posIzquierdo - 1];
                derecho = (T) heap[posDerecho - 1];

                // Si tiene ambos hijos, tomar el menor
                if (izquierdo != null && derecho != null) {
                    if (izquierdo.compareTo(derecho) < 0) {
                        posHijoMenor = posIzquierdo;
                    } else {
                        posHijoMenor = posDerecho;
                    }
                } else if (izquierdo != null) {
                    posHijoMenor = posIzquierdo;
                } else {
                    bajar = false;
                }

                // Intercambiar con el hijo menor, si existe
                if (posHijoMenor > 0) {
                    hijoMenor = (T) heap[posHijoMenor - 1];

                    if (ultimoElem.compareTo(hijoMenor) > 0) {
                        heap[posPadre - 1] = hijoMenor;
                        heap[posHijoMenor - 1] = ultimoElem;
                        posPadre = posHijoMenor;
                        posHijoMenor = -1;
                    } else {
                        bajar = false;
                    }
                }
            }
        }
    }

    /**
     * Elimina el elemento de la cima del Heap.
     *
     * @return verdadero si la cima fue eliminada, falso en caso contrario
     */
    public boolean eliminarCima() {
        boolean resultado = false;

        if (ultimo > 0) {
            ultimo--;
            heap[0] = heap[ultimo];
            heap[ultimo] = null;
            hacerBajar();
            resultado = true;
        }

        return resultado;
    }

    /**
     * Devuelve el elemento de la cima del Heap.
     *
     * @return la cima
     */
    @SuppressWarnings("unchecked")
    public T recuperarCima() {
        return (T) heap[0];
    }

    /**
     * Devuelve verdadero si el Heap no tiene elementos, o falso en caso contrario.
     *
     * @return verdadero si es vacío, falso en caso contrario
     */
    public boolean esVacio() {
        return ultimo == 0;
    }

    /**
     * Elimina todos los elementos del Heap.
     */
    public void vaciar() {
        for (int i = ultimo - 1; i >= 0; i--) {
            heap[i] = null;
        }

        ultimo = 0;
    }

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
