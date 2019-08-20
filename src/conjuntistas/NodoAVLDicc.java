package conjuntistas;

/**
 * Implementación de un nodo para Diccionario (impl. como Árbol AVL).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 *
 * @param <C> el tipo de clave
 * @param <E> el tipo de elemento
 */
public class NodoAVLDicc<C extends Comparable<C>, E> {

    /**
     * La clave del nodo.
     */
    private C clave;

    /**
     * El elemento del nodo.
     */
    private E elemento;

    /**
     * El nodo izquierdo.
     */
    private NodoAVLDicc<C, E> izquierdo;

    /**
     * El nodo derecho.
     */
    private NodoAVLDicc<C, E> derecho;

    /**
     * La altura del nodo.
     */
    private int altura;

    /**
     * Constructor vacío.
     */
    public NodoAVLDicc() {
        this(null, null, null, null, -1);
    }

    /**
     * Constructor con la clave.
     *
     * @param clave la clave
     * @param elemento el elemento
     */
    public NodoAVLDicc(C clave, E elemento) {
        this(clave, elemento, null, null, 0);
    }

    /**
     * Constructor con la clave y los enlaces izquierdo y derecho.
     *
     * @param clave la clave
     * @param elemento el elemento
     * @param izquierdo el nodo izquierdo
     * @param derecho el nodo derecho
     */
    public NodoAVLDicc(C clave, E elemento, NodoAVLDicc<C, E> izquierdo, NodoAVLDicc<C, E> derecho) {
        this(clave, elemento, izquierdo, derecho, 1 +
                Math.max(izquierdo != null ? izquierdo.getAltura() : -1, derecho != null ? derecho.getAltura() : -1));
    }

    /**
     * Constructor con la clave, los enlaces izquierdo y derecho, y su altura.
     *
     * @param clave la clave
     * @param elemento el elemento
     * @param izquierdo el nodo izquierdo
     * @param derecho el nodo derecho
     * @param altura la altura
     */
    public NodoAVLDicc(C clave, E elemento, NodoAVLDicc<C, E> izquierdo, NodoAVLDicc<C, E> derecho, int altura) {
        this.clave = clave;
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = altura;
    }

    /**
     * Devuelve la clave.
     *
     * @return la clave
     */
    public C getClave() {
        return clave;
    }

    /**
     * Establece la clave.
     *
     * @param clave la clave
     */
    public void setClave(C clave) {
        this.clave = clave;
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
    public NodoAVLDicc<C, E> getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establece el nodo izquierdo.
     *
     * @param izquierdo el nodo
     */
    public void setIzquierdo(NodoAVLDicc<C, E> izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Devuelve el nodo derecho.
     *
     * @return el nodo derecho
     */
    public NodoAVLDicc<C, E> getDerecho() {
        return derecho;
    }

    /**
     * Establece el nodo derecho.
     *
     * @param derecho el nodo
     */
    public void setDerecho(NodoAVLDicc<C, E> derecho) {
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
}
