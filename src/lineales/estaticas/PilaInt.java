package lineales.estaticas;

/**
 * Implementación de Pila estática.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class PilaInt {

    /**
     * El tamaño de la pila.
     */
    private static final int TAM = 32;

    /**
     * Los elementos de la pila.
     */
    private int[] pila;

    /**
     * Posición del elemento tope de la pila; -1 si la pila está vacía.
     */
    private int tope;

    /**
     * Crea y devuelve la pila vacía.
     */
    public PilaInt() {
        this.pila = new int[TAM];
        this.tope = -1;
    }

    /**
     * Pone el elemento nuevoElem en el tope de la pila. Devuelve verdadero si
     * el elemento se pudo apilar y falso en caso contrario.
     * 
     * @param nuevoElem
     * @return
     */
    public boolean apilar(int nuevoElem) {
        boolean resultado = false;

        if (tope < (TAM - 1)) {
            pila[++tope] = nuevoElem;
            resultado = true;
        }

        return resultado;
    }

    /**
     * Saca el elemento del tope de la pila. Devuelve verdadero si la pila no
     * estaba vacía al momento de desapilar (es decir que se pudo desapilar) y
     * falso en caso contrario.
     * 
     * @return
     */
    public boolean desapilar() {
        boolean resultado = false;

        if (tope >= 0) {
            pila[tope--] = 0;
            resultado = true;
        }

        return resultado;
    }

    /**
     * Devuelve el elemento en el tope de la pila. Precondición: la pila no está
     * vacía.
     * 
     * @return
     */
    public int obtenerTope() {
        int topeElem = 0;

        if (tope >= 0) {
            topeElem = pila[tope];
        }

        return topeElem;
    }

    /**
     * Devuelve verdadero si la pila no tiene elementos y falso en caso
     * contrario.
     * 
     * @return
     */
    public boolean esVacia() {
        return (tope < 0);
    }

    /**
     * Saca todos los elementos de la pila.
     */
    public void vaciar() {
        pila = new int[TAM];
        tope = -1;
    }

    /**
     * Devuelve una copia de la pila original.
     * 
     * @return
     */
    public PilaInt clonar() {
        PilaInt clon = new PilaInt();
        int i;

        if (tope >= 0) {
            for (i = 0; i <= tope; i++) {
                clon.apilar(pila[i]);
            }
        }

        return clon;
    }

    /**
     * Devuelve una cadena de caracteres formada por todos los elementos de la
     * pila para poder mostrarla por pantalla. Es recomendable utilizar este
     * método únicamente en la etapa de prueba y luego comentar el código.
     */
    public String toString() {
        String cadena = "[";
        int i;

        if (tope >= 0) {
            for (i = tope; i >= 0; i--) {
                cadena += pila[i] + " ";
            }
            cadena = cadena.trim();
        }

        cadena += "]";

        return cadena;
    }
}
