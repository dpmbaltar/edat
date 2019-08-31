package lineales.dinamicas;

/**
 * Implementación de Pila dinámica.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class Pila<T> {

    /**
     * El nodo tope de la pila.
     */
    private Nodo<T> tope;

    /**
     * Constructor vacío.
     */
    public Pila() {
        tope = null;
    }

    /**
     * Pone el elemento nuevoElemento en el tope de la pila. Devuelve verdadero siempre, ya que es una pila dinámica.
     *
     * @param nuevoElemento el elemento a insertar
     * @return siempre verdadero
     */
    public boolean apilar(T nuevoElemento) {
        tope = new Nodo<T>(nuevoElemento, tope);

        return true;
    }

    /**
     * Saca el elemento del tope de la pila. Devuelve verdadero si la pila no estaba vacía al momento de desapilar (es
     * decir que se pudo desapilar) y falso en caso contrario.
     *
     * @return verdadero si se puede desapilar un elemento, falso en caso contrario
     */
    public boolean desapilar() {
        boolean resultado = false;

        if (tope != null) {
            tope = tope.getEnlace();
            resultado = true;
        }

        return resultado;
    }

    /**
     * Devuelve el elemento en el tope de la pila. Precondición: la pila no está vacía.
     *
     * @return el elemento tope si existe, nulo en caso contrario
     */
    public T obtenerTope() {
        return tope != null ? tope.getElemento() : null;
    }

    /**
     * Devuelve verdadero si la pila no tiene elementos y falso en caso contrario.
     *
     * @return verdadero si es vacía, falso en caso contrario
     */
    public boolean esVacia() {
        return tope == null;
    }

    /**
     * Saca todos los elementos de la pila.
     */
    public void vaciar() {
        tope = null;
    }

    /**
     * Devuelve una copia de la pila original.
     *
     * @return la copia de la pila
     */
    @Override
    public Pila<T> clone() {
        Pila<T> clon = new Pila<T>();

        if (tope != null) {
            Nodo<T> nodoSiguiente = tope.getEnlace();
            Nodo<T> nodoClon = new Nodo<>(tope.getElemento());
            clon.tope = nodoClon;

            while (nodoSiguiente != null) {
                nodoClon.setEnlace(new Nodo<T>(nodoSiguiente.getElemento()));
                nodoClon = nodoClon.getEnlace();
                nodoSiguiente = nodoSiguiente.getEnlace();
            }
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

        if (tope != null) {
            Nodo<T> nodo = tope;

            while (nodo != null) {
                cadena.append(String.valueOf(nodo.getElemento()));
                nodo = nodo.getEnlace();

                if (nodo != null) {
                    cadena.append(", ");
                }
            }
        }

        cadena.append(']');

        return cadena.toString();
    }

}
