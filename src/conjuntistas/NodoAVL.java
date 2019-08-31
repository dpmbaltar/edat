package conjuntistas;

/**
 * Implementación de un nodo de dos enlaces: izquierdo y derecho, y altura para AVL.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <E> el tipo de elemento
 */
public class NodoAVL<E> {

    /**
     * El elemento del nodo.
     */
    private E elemento;

    /**
     * El nodo izquierdo.
     */
    private NodoAVL<E> izquierdo;

    /**
     * El nodo derecho.
     */
    private NodoAVL<E> derecho;

    /**
     * La altura del nodo.
     */
    private int altura;

    /**
     * Constructor con el elemento.
     *
     * @param elemento el elemento
     */
    public NodoAVL(E elemento) {
        this(elemento, null, null, 0);
    }

    /**
     * Constructor con el elemento y los enlaces izquierdo y derecho.
     *
     * @param elemento el elemento
     * @param izquierdo el nodo izquierdo
     * @param derecho el nodo derecho
     */
    public NodoAVL(E elemento, NodoAVL<E> izquierdo, NodoAVL<E> derecho) {
        this(elemento, izquierdo, derecho, 0);
        recalcularAltura();
    }

    /**
     * Constructor con el elemento, los enlaces izquierdo y derecho, y su altura.
     *
     * @param elemento el elemento
     * @param izquierdo el nodo izquierdo
     * @param derecho el nodo derecho
     * @param altura la altura
     */
    public NodoAVL(E elemento, NodoAVL<E> izquierdo, NodoAVL<E> derecho, int altura) {
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
    public E getElemento() {
        return elemento;
    }

    /**
     * Establece el elemento.
     *
     * @param elemento el elemento
     */
    public void setElemento(E elemento) {
        this.elemento = elemento;
    }

    /**
     * Devuelve el nodo izquierdo.
     *
     * @return el nodo izquierdo
     */
    public NodoAVL<E> getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establece el nodo izquierdo.
     *
     * @param izquierdo el nodo
     */
    public void setIzquierdo(NodoAVL<E> izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Devuelve el nodo derecho.
     *
     * @return el nodo derecho
     */
    public NodoAVL<E> getDerecho() {
        return derecho;
    }

    /**
     * Establece el nodo derecho.
     *
     * @param derecho el nodo
     */
    public void setDerecho(NodoAVL<E> derecho) {
        this.derecho = derecho;
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

    /**
     * Recalcula la altura del nodo.
     */
    public void recalcularAltura() {
        int alturaIzquierdo = izquierdo != null ? izquierdo.getAltura() : -1;
        int alturaDerecho = derecho != null ? derecho.getAltura() : -1;
        altura = Math.max(alturaIzquierdo, alturaDerecho) + 1;
    }

    /**
     * Devuelve el balance del nodo.
     *
     * @return el balance del nodo
     */
    public int balance() {
        int alturaIzquierdo = izquierdo != null ? izquierdo.getAltura() : -1;
        int alturaDerecho = derecho != null ? derecho.getAltura() : -1;

        return alturaIzquierdo - alturaDerecho;
    }
}
