package tpo1.lineales.dinamicas;

/**
 * Implementación de un nodo de tipo entero de un solo enlace.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class NodoInt {

    /**
     * El elemento del nodo.
     */
    private int elemento;

    /**
     * El enlace del nodo.
     */
    private NodoInt enlace;

    /**
     * Constructor vacío.
     */
    public NodoInt() {
    }

    /**
     * Crea y devuelve un nodo sin enlace, y el elemento establecido.
     *
     * @param elemento
     */
    public NodoInt(int elemento) {
        this(elemento, null);
    }

    /**
     * Crea y devuelve un nodo sin elemento, y el enlace establecido.
     *
     * @param elemento
     */
    public NodoInt(NodoInt enlace) {
        this(0, enlace);
    }

    /**
     * Crea y devuelve un nodo con el elemento y enlace establecidos.
     *
     * @param elemento
     * @param enlace
     */
    public NodoInt(int elemento, NodoInt enlace) {
        this.elemento = elemento;
        this.enlace = enlace;
    }

    /**
     * Devuelve el elemento del nodo.
     *
     * @return
     */
    public int getElemento() {
        return elemento;
    }

    /**
     * Devuelve el enlace del nodo.
     *
     * @return
     */
    public NodoInt getEnlace() {
        return enlace;
    }

    /**
     * Establece el elemento del nodo.
     *
     * @param elemento
     */
    public void setElemento(int elemento) {
        this.elemento = elemento;
    }

    /**
     * Establece el enlace del nodo.
     *
     * @param enlace
     */
    public void setEnlace(NodoInt enlace) {
        this.enlace = enlace;
    }
}
