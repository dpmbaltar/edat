package tpo1.lineales.dinamicas;

import tpo1.lineales.dinamicas.NodoInt;

/**
 * Implementación de Pila dinámica de enteros.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PilaInt {

    /**
     * El nodo tope de la pila.
     */
    private NodoInt tope;

    /**
     * Crea y devuelve una pila vacía.
     */
    public PilaInt() {
        tope = null;
    }

    /**
     * Pone el elemento nuevoElemento en el tope de la pila. Devuelve verdadero
     * si el elemento se pudo apilar y falso en caso contrario.
     *
     * @param nuevoElemento
     * @return
     */
    public boolean apilar(int nuevoElemento) {
        tope = new NodoInt(nuevoElemento, tope);
        return true;
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

        if (tope != null) {
            tope = tope.getEnlace();
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
    public int obtenerTope() {
        return tope != null ? tope.getElemento() : 0;
    }

    /**
     * Devuelve verdadero si la pila no tiene elementos y falso en caso
     * contrario.
     *
     * @return
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
     * @return
     */
    public PilaInt clonar() {
        PilaInt clon = new PilaInt();

        if (tope != null) {
            PilaInt auxiliar = new PilaInt();
            NodoInt nodo = tope;

            while (nodo != null) {
                auxiliar.apilar(nodo.getElemento());
                nodo = nodo.getEnlace();
            }

            while (!auxiliar.esVacia()) {
                clon.apilar(auxiliar.obtenerTope());
                auxiliar.desapilar();
            }
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

        if (tope != null) {
            NodoInt nodo = tope;
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
