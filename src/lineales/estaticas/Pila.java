package lineales.estaticas;

/**
 * Implementación de Pila estática.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class Pila<T> {

    /**
     * El tamaño de la pila (por defecto).
     */
    public static final int TAM = 32;

    /**
     * Los elementos de la pila.
     */
    private Object[] pila;

    /**
     * Posición del elemento tope de la pila; -1 si la pila está vacía.
     */
    private int tope;

    /**
     * Crea y devuelve la pila vacía.
     */
    public Pila() {
        this.pila = new Object[TAM];
        this.tope = -1;
    }

    /**
     * Pone el elemento nuevoElemento en el tope de la pila. Devuelve verdadero
     * si el elemento se pudo apilar y falso en caso contrario.
     *
     * @param nuevoElemento
     * @return
     */
    public boolean apilar(T nuevoElemento) {
        boolean exito = false;

        if (tope < (pila.length - 1)) {
            tope++;
            pila[tope] = nuevoElemento;
            exito = true;
        }

        return exito;
    }

    /**
     * Saca el elemento del tope de la pila. Devuelve verdadero si la pila no
     * estaba vacía al momento de desapilar (es decir que se pudo desapilar) y
     * falso en caso contrario.
     *
     * @return
     */
    public boolean desapilar() {
        boolean exito = false;

        if (tope > -1) {
            pila[tope] = null;
            tope--;
            exito = true;
        }

        return exito;
    }

    /**
     * Devuelve el elemento en el tope de la pila.
     * Precondición: la pila no está vacía.
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public T obtenerTope() {
        return tope > -1 ? (T)pila[tope] : null;
    }

    /**
     * Devuelve verdadero si la pila no tiene elementos y falso en caso
     * contrario.
     *
     * @return
     */
    public boolean esVacia() {
        return tope < 0;
    }

    /**
     * Saca todos los elementos de la pila.
     */
    public void vaciar() {
        pila = new Object[TAM];
        tope = -1;
    }

    /**
     * Devuelve una copia de la pila original.
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Pila<T> clonar() {
        Pila<T> clon = new Pila<T>();
        int posicion;

        for (posicion = 0; posicion <= tope; posicion++) {
            clon.apilar((T)pila[posicion]);
        }

        return clon;
    }

    /**
     * Devuelve una cadena de carácteres formada por todos los elementos de la
     * pila para poder mostrarla por pantalla. Es recomendable utilizar este
     * método únicamente en la etapa de prueba y luego comentar el código.
     */
    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder("[");
        int posicion;

        for (posicion = tope; posicion > -1; posicion--) {
            cadena.append(String.valueOf(pila[posicion]));
            if ((posicion - 1) > -1) {
                cadena.append(", ");
            }
        }

        cadena.append(']');

        return cadena.toString();
    }
}
