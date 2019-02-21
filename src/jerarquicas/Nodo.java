package jerarquicas;

/**
 * Implementación de un nodo de dos enlaces: izquierdo y derecho.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <E>
 */
public class Nodo<E> {

    /**
     * El elemento del nodo.
     */
    private E elemento;

    /**
     * El nodo izquierdo.
     */
    private Nodo<E> izquierdo;

    /**
     * El nodo derecho.
     */
    private Nodo<E> derecho;

    /**
     * Crea y devuelve un nodo sin elemento ni enlaces.
     */
    public Nodo() {
        this(null, null, null);
    }

    /**
     * Crea y devuelve un nodo con los enlaces izquierdo y derecho establecidos.
     * 
     * @param izquierdo
     * @param derecho
     */
    public Nodo(Nodo<E> izquierdo, Nodo<E> derecho) {
        this(null, izquierdo, derecho);
    }

    /**
     * Crea y devuelve un nodo con el elemento establecido.
     * 
     * @param elemento
     */
    public Nodo(E elemento) {
        this(elemento, null, null);
    }

    /**
     * Crea y devuelve un nodo con el elemento y los enlaces izquierdo y derecho
     * establecidos.
     * 
     * @param elemento
     * @param izquierdo
     * @param derecho
     */
    public Nodo(E elemento, Nodo<E> izquierdo, Nodo<E> derecho) {
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    /**
     * Devuelve el elemento.
     * 
     * @return
     */
    public E getElemento() {
        return elemento;
    }

    /**
     * Establece el elemento.
     * 
     * @param elemento
     */
    public void setElemento(E elemento) {
        this.elemento = elemento;
    }

    /**
     * Devuelve el nodo izquierdo.
     * 
     * @return
     */
    public Nodo<E> getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establece el nodo izquierdo.
     * 
     * @param izquierdo
     */
    public void setIzquierdo(Nodo<E> izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Devuelve el nodo derecho.
     * 
     * @return
     */
    public Nodo<E> getDerecho() {
        return derecho;
    }

    /**
     * Establece el nodo derecho.
     * 
     * @param derecho
     */
    public void setDerecho(Nodo<E> derecho) {
        this.derecho = derecho;
    }

}
