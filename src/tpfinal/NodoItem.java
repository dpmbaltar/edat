package tpfinal;

/**
 * Implementación de un nodo de dos enlaces: izquierdo y derecho, y altura para AVL.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class NodoItem {

    /**
     * El elemento del nodo.
     */
    private Item elemento;

    /**
     * El nodo izquierdo.
     */
    private NodoItem izquierdo;

    /**
     * El nodo derecho.
     */
    private NodoItem derecho;

    /**
     * El ítem siguiente.
     */
    private NodoItem siguiente;

    /**
     * La altura del nodo.
     */
    private int altura;

    /**
     * Constructor vacío.
     */
    public NodoItem() {
        this(null, null, null, -1);
    }

    /**
     * Constructor con el elemento.
     *
     * @param elemento el elemento
     */
    public NodoItem(Item elemento) {
        this(elemento, null, null, 0);
    }

    /**
     * Constructor con el elemento y los enlaces izquierdo y derecho.
     *
     * @param elemento el elemento
     * @param izquierdo el nodo izquierdo
     * @param derecho el nodo derecho
     */
    public NodoItem(Item elemento, NodoItem izquierdo, NodoItem derecho) {
        this(elemento, izquierdo, derecho, Math.max(izquierdo.getAltura(), derecho.getAltura()) + 1);
    }

    /**
     * Constructor con el elemento, los enlaces izquierdo y derecho, y su altura.
     *
     * @param elemento el elemento
     * @param izquierdo el nodo izquierdo
     * @param derecho el nodo derecho
     * @param altura la altura
     */
    public NodoItem(Item elemento, NodoItem izquierdo, NodoItem derecho, int altura) {
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = altura;
    }

    /**
     * Devuelve el elemento.
     *
     * @return el elemento
     */
    public Item getElemento() {
        return elemento;
    }

    /**
     * Establece el elemento.
     *
     * @param elemento el elemento
     */
    public void setElemento(Item elemento) {
        this.elemento = elemento;
    }

    /**
     * Devuelve el nodo izquierdo.
     *
     * @return el nodo izquierdo
     */
    public NodoItem getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establece el nodo izquierdo.
     *
     * @param izquierdo el nodo
     */
    public void setIzquierdo(NodoItem izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Devuelve el nodo derecho.
     *
     * @return el nodo derecho
     */
    public NodoItem getDerecho() {
        return derecho;
    }

    /**
     * Establece el nodo derecho.
     *
     * @param derecho el nodo
     */
    public void setDerecho(NodoItem derecho) {
        this.derecho = derecho;
    }

    /**
     * Devuelve el ítem siguiente.
     *
     * @return el ítem siguiente
     */
    public NodoItem getSiguiente() {
        return siguiente;
    }

    /**
     * Establece el estand siguiente.
     *
     * @param siguiente el estand siguiente
     */
    public void setSiguiente(NodoItem siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Devuelve la altura del nodo.
     *
     * @return la altura
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Establece la altura del nodo.
     *
     * @param altura la altura
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }
}
