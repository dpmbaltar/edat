package lineales.estaticas;

/**
 * Implementación de Cola estática.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class Cola<T> {

    /**
     * El tamaño de la cola - +1, ya que se reserva una celda - (por defecto).
     */
    public static final int TAM = 256;

    /**
     * Los elementos de la cola.
     */
    private Object[] cola;

    /**
     * Posición del elemento del frente de la cola; 0 si la cola está vacía.
     */
    private int frente;

    /**
     * Posición del último elemento de la cola; 0 si la cola está vacía.
     */
    private int ultimo;

    /**
     * Constructor vacío.
     */
    public Cola() {
        cola = new Object[TAM];
        frente = 0;
        ultimo = 0;
    }

    /**
     * Pone el elemento al final de la cola. Devuelve verdadero si el elemento se pudo agregar en la estructura y
     * falso en caso contrario.
     *
     * @param nuevoElemento el elemento a insertar
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean poner(T nuevoElemento) {
        boolean resultado = false;
        int nuevo = (ultimo + 1) % cola.length;

        if (frente == ultimo || nuevo != frente) {
            cola[ultimo] = nuevoElemento;
            ultimo = nuevo;
            resultado = true;
        }

        return resultado;
    }

    /**
     * Saca el elemento que está en el frente de la cola. Devuelve verdadero si el elemento se pudo sacar (la
     * estructura no estaba vacía) y falso en caso contrario.
     *
     * @return verdadero si se puede sacar un elemento, falso en caso contrario
     */
    public boolean sacar() {
        boolean resultado = false;

        if (frente != ultimo) {
            cola[frente] = null;
            frente = (frente + 1) % cola.length;
            resultado = true;
        }

        return resultado;
    }

    /**
     * Devuelve el elemento que está en el frente. Precondición: la cola no está vacía.
     *
     * @return el elemento del frente si existe, nulo en caso contrario
     */
    @SuppressWarnings("unchecked")
    public T obtenerFrente() {
        return (T) cola[frente];
    }

    /**
     * Devuelve verdadero si la cola no tiene elementos y falso en caso contrario.
     *
     * @return verdadero si es vacía, falso en caso contrario
     */
    public boolean esVacia() {
        return frente == ultimo;
    }

    /**
     * Saca todos los elementos de la estructura.
     */
    public void vaciar() {
        cola = new Object[TAM];
        frente = 0;
        ultimo = 0;
    }

    /**
     * Devuelve una copia de la cola original.
     *
     * @return la copia de la cola
     */
    @Override
    @SuppressWarnings("unchecked")
    public Cola<T> clone() {
        Cola<T> clon = new Cola<T>();

        for (int i = frente; (i % cola.length) != ultimo; i++) {
            i %= cola.length;
            clon.poner((T) cola[i]);
        }

        return clon;
    }

    /**
     * Crea y devuelve una cadena de carácteres formada por todos los elementos de la cola para poder mostrarla por
     * pantalla. Es recomendable utilizar este método únicamente en la etapa de prueba y luego comentar el código.
     */
    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder("[");
        int i;

        for (i = frente; (i % cola.length) != ultimo; i++) {
            i %= cola.length;
            cadena.append(String.valueOf(cola[i]));

            if (((i + 1) % cola.length) != ultimo) {
                cadena.append(", ");
            }
        }

        cadena.append("]");

        return cadena.toString();
    }

}
