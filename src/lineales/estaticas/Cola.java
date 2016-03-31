package lineales.estaticas;

/**
 * Implementación de Cola estática.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Cola<T> {

    /**
     * El tamaño de la cola - +1, ya que se reserva una celda - (por defecto).
     */
    public static final int TAM = 32;

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
     * Crea y devuelve la cola vacía.
     */
    public Cola() {
        this.cola = new Object[TAM];
        this.frente = 0;
        this.ultimo = 0;
    }

    /**
     * Pone el elemento al final de la cola. Devuelve verdadero si el elemento
     * se pudo agregar en la estructura y falso en caso contrario.
     *
     * @param nuevoElemento
     * @return
     */
    public boolean poner(T nuevoElemento) {
        boolean exito = false;
        int nuevo = (ultimo + 1) % cola.length;

        if (frente == ultimo || nuevo != frente) {
            cola[ultimo] = nuevoElemento;
            ultimo = nuevo;
            exito = true;
        }

        return exito;
    }

    /**
     * Saca el elemento que está en el frente de la cola. Devuelve verdadero si
     * el elemento se pudo sacar (la estructura no estaba vacía) y falso en caso
     * contrario.
     *
     * @return
     */
    public boolean sacar() {
        boolean exito = false;

        if (frente != ultimo) {
            cola[frente] = null;
            frente = (frente + 1) % cola.length;
            exito = true;
        }

        return exito;
    }

    /**
     * Devuelve el elemento que está en el frente. Precondición: la cola no
     * está vacía.
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public T obtenerFrente() {
        return (T)cola[frente];
    }

    /**
     * Devuelve verdadero si la cola no tiene elementos y falso en caso
     * contrario.
     *
     * @return
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
     * @return
     */
    @SuppressWarnings("unchecked")
    public Cola<T> clonar() {
        Cola<T> clon = new Cola<T>();
        int indice;

        for (indice = frente; (indice % cola.length) != ultimo; indice++) {
            clon.poner((T)cola[indice]);
        }

        return clon;
    }

    /**
     * Crea y devuelve una cadena de carácteres formada por todos los elementos
     * de la cola para poder mostrarla por pantalla. Es recomendable utilizar
     * este método únicamente en la etapa de prueba y luego comentar el código.
     */
    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder("[");
        int indice;

        for (indice = frente; (indice % cola.length) != ultimo; indice++) {
            cadena.append(String.valueOf(cola[indice]));
            if (((indice + 1) % cola.length) != ultimo) {
                cadena.append(", ");
            }
        }

        cadena.append("]");

        return cadena.toString();
    }
}