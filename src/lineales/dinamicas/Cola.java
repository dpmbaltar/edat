package lineales.dinamicas;

/**
 * Implementación de Cola dinámica.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class Cola<T> {

    /**
     * El nodo del frente de la cola.
     */
    private Nodo<T> frente;

    /**
     * El nodo del final de la cola.
     */
    private Nodo<T> ultimo;

    /**
     * Crea y devuelve una cola vacía.
     */
    public Cola() {
        frente = null;
        ultimo = null;
    }

    /**
     * Pone el elemento al final de la cola. Devuelve verdadero si el elemento
     * se pudo agregar en la estructura y falso en caso contrario.
     *
     * @param nuevoElemento
     * @return
     */
    public boolean poner(T nuevoElemento) {
        Nodo<T> nuevoNodo = new Nodo<T>(nuevoElemento);

        if (frente == null) {
            frente = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            ultimo.setEnlace(nuevoNodo);
            ultimo = nuevoNodo;
        }

        return true;
    }

    /**
     * Saca el elemento que está en el frente de la cola. Devuelve verdadero si
     * el elemento se pudo sacar (la estructura no estaba vacía) y falso en caso
     * contrario.
     *
     * @return
     */
    public boolean sacar() {
        boolean resultado = false;

        if (frente != null) {
            frente = frente.getEnlace();

            if (frente == null) {
                ultimo = null;
            }

            resultado = true;
        }

        return resultado;
    }

    /**
     * Devuelve el elemento que está en el frente. Precondición: la cola no está
     * vacía.
     *
     * @return
     */
    public T obtenerFrente() {
        return frente != null ? frente.getElemento() : null;
    }

    /**
     * Devuelve verdadero si la cola no tiene elementos y falso en caso
     * contrario.
     *
     * @return
     */
    public boolean esVacia() {
        return frente == null;
    }

    /**
     * Saca todos los elementos de la estructura.
     */
    public void vaciar() {
        frente = null;
        ultimo = null;
    }

    /**
     * Devuelve una copia de la cola original.
     *
     * @return
     */
    public Cola<T> clonar() {
        Cola<T> clon = new Cola<T>();

        if (frente != null) {
            Nodo<T> nodo = frente;

            while (nodo != null) {
                clon.poner(nodo.getElemento());
                nodo = nodo.getEnlace();
            }
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

        if (frente != null) {
            Nodo<T> nodo = frente;
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
