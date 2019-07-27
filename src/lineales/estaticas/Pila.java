package lineales.estaticas;

/**
 * Implementación de Pila estática.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
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
     * Constructor vacío.
     */
    public Pila() {
        pila = new Object[TAM];
        tope = -1;
    }

    /**
     * Pone el elemento nuevoElemento en el tope de la pila. Devuelve verdadero
     * si el elemento se pudo apilar y falso en caso contrario.
     *
     * @param nuevoElemento el elemento a insertar
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean apilar(T nuevoElemento) {
        boolean resultado = false;

        if (tope < (pila.length - 1)) {
            tope++;
            pila[tope] = nuevoElemento;
            resultado = true;
        }

        return resultado;
    }

    /**
     * Saca el elemento del tope de la pila. Devuelve verdadero si la pila no estaba vacía al momento de desapilar
     * (es decir que se pudo desapilar) y falso en caso contrario.
     *
     * @return verdadero si se puede desapilar un elemento, falso en caso contrario
     */
    public boolean desapilar() {
        boolean resultado = false;

        if (tope > -1) {
            pila[tope] = null;
            tope--;
            resultado = true;
        }

        return resultado;
    }

    /**
     * Devuelve el elemento en el tope de la pila. Precondición: la pila no está vacía.
     *
     * @return el elemento tope si existe, nulo en caso contrario
     */
    @SuppressWarnings("unchecked")
    public T obtenerTope() {
        return tope > -1 ? (T) pila[tope] : null;
    }

    /**
     * Devuelve verdadero si la pila no tiene elementos y falso en caso contrario.
     *
     * @return verdadero si es vacía, falso en caso contrario
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
     * @return la copia de la pila
     */
    @Override
    @SuppressWarnings("unchecked")
    public Pila<T> clone() {
        Pila<T> clon = new Pila<T>();

        for (int i = 0; i <= tope; i++) {
            clon.apilar((T) pila[i]);
        }

        return clon;
    }

    /**
     * Devuelve una cadena de carácteres formada por todos los elementos de la pila para poder mostrarla por pantalla.
     * Es recomendable utilizar este método únicamente en la etapa de prueba y luego comentar el código.
     */
    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder("[");

        for (int i = tope; i > -1; i--) {
            cadena.append(String.valueOf(pila[i]));

            if ((i - 1) > -1) {
                cadena.append(", ");
            }
        }

        cadena.append(']');

        return cadena.toString();
    }

}
