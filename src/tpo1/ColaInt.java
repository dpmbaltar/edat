package tpo1;

/**
 * Implementación de Cola dinámica.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class ColaInt {

    /**
     * El nodo del elemento del frente de la cola.
     */
    private NodoInt frente;

    /**
     * El nodo del elemento del final de la cola.
     */
    private NodoInt ultimo;

    /**
     * Crea y devuelve una cola vacía.
     */
    public ColaInt() {
        frente = null;
        ultimo = null;
    }

    /**
     * Pone el elemento al final de la cola. Devuelve verdadero si el elemento
     * se pudo agregar en la estructura y falso en caso contrario.
     * 
     * @param nuevoElem
     * @return
     */
    public boolean poner(int nuevoElem) {
        NodoInt nuevoNodo = new NodoInt(nuevoElem);

        if (esVacia()) {
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

        if (!esVacia()) {
            frente = frente.getEnlace();
            if (frente == null) {
                ultimo = null;
            }
            resultado = true;
        }

        return resultado;
    }

    /**
     * Devuelve el elemento que está en el frente. Precondición : la cola no
     * está vacía.
     * 
     * @return
     */
    public int obtenerFrente() {
        int elem = 0;

        if (!esVacia()) {
            elem = frente.getElem();
        }

        return elem;
    }

    /**
     * Devuelve verdadero si la cola no tiene elementos y falso en caso
     * contrario.
     * 
     * @return
     */
    public boolean esVacia() {
        return (frente == null);
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
    public ColaInt clonar() {
        ColaInt clon = new ColaInt();
        NodoInt nodo;

        if (!esVacia()) {
            nodo = frente;
            while (nodo != null) {
                clon.poner(nodo.getElem());
                nodo = nodo.getEnlace();
            }
        }

        return clon;
    }

    /**
     * Crea y devuelve una cadena de caracteres formada por todos los elementos
     * de la cola para poder mostrarla por pantalla. Es recomendable utilizar
     * este método únicamente en la etapa de prueba y luego comentar el código.
     */
    public String toString() {
        String cadena = "[";
        NodoInt nodo;

        if (!esVacia()) {
            nodo = frente;
            while (nodo != null) {
                cadena += nodo.getElem();
                nodo = nodo.getEnlace();
                if (nodo != null) {
                    cadena += ", ";
                }
            }
        }

        cadena += "]";

        return cadena;
    }
}
