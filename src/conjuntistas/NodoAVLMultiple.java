package conjuntistas;

/**
 * Implementación de un nodo para un AVL que acepta elementos duplicados o
 * elementos distintos que al ser comparados representan el mismo valor.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <E> el tipo de elemento
 */
public class NodoAVLMultiple<E> {

    /**
     * El elemento del nodo.
     */
    private E elemento;

    /**
     * El nodo izquierdo.
     */
    private NodoAVLMultiple<E> izquierdo;

    /**
     * El nodo derecho.
     */
    private NodoAVLMultiple<E> derecho;

    /**
     * El enlace a otro elemento que representa un mismo valor comparable.
     */
    private NodoMultiple<E> enlace;

    /**
     * La altura del nodo.
     */
    private int altura;

    /**
     * Cantidad de referencias al elemento.
     */
    private int cantidad;

    /**
     * Constructor con elemento.
     *
     * @param elemento el elemento
     */
    public NodoAVLMultiple(E elemento) {
        this(elemento, null, null, null, 0, 1);
    }

    /**
     * Constructor con elemento y enlace.
     *
     * @param elemento el elemento
     * @param enlace el enlace
     */
    public NodoAVLMultiple(E elemento, NodoMultiple<E> enlace) {
        this(elemento, null, null, enlace, 0, 1);
    }

    /**
     * Constructor con elemento, izquierdo y derecho.
     *
     * @param elemento el elemento
     * @param izquierdo el nodo izquierdo
     * @param derecho el nodo derecho
     */
    public NodoAVLMultiple(E elemento, NodoAVLMultiple<E> izquierdo, NodoAVLMultiple<E> derecho) {
        this(elemento, izquierdo, derecho, null, 0, 1);
        recalcularAltura();
    }

    /**
     * Constructor con elemento, izquierdo, derecho, enlace, altura y cantidad.
     *
     * @param elemento el elemento
     * @param izquierdo el nodo izquierdo
     * @param derecho el nodo derecho
     * @param enlace el enlace
     * @param altura la altura
     * @param cantidad la cantidad de referencias
     */
    public NodoAVLMultiple(E elemento, NodoAVLMultiple<E> izquierdo, NodoAVLMultiple<E> derecho,
            NodoMultiple<E> enlace, int altura, int cantidad) {
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.enlace = enlace;
        this.altura = altura;
        this.cantidad = cantidad;
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
    public NodoAVLMultiple<E> getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establece el nodo izquierdo.
     *
     * @param izquierdo el nodo
     */
    public void setIzquierdo(NodoAVLMultiple<E> izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Devuelve el nodo derecho.
     *
     * @return el nodo derecho
     */
    public NodoAVLMultiple<E> getDerecho() {
        return derecho;
    }

    /**
     * Establece el nodo derecho.
     *
     * @param derecho el nodo
     */
    public void setDerecho(NodoAVLMultiple<E> derecho) {
        this.derecho = derecho;
    }

    /**
     * Devuelve el enlace.
     *
     * @return el enlace
     */
    public NodoMultiple<E> getEnlace() {
        return enlace;
    }

    /**
     * Establece el enlace.
     *
     * @param enlace el enlace
     */
    public void setEnlace(NodoMultiple<E> enlace) {
        this.enlace = enlace;
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
     * Devuelve la cantidad de referencias al elemento.
     *
     * @return la cantidad de referencias al elemento
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de referencias al elemento.
     *
     * @param cantidad la nueva cantidad de referencias al elemento
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    /**
     * Aumenta la cantidad del elemento en 1.
     */
    public void aumentarCantidad() {
        cantidad++;
    }

    /**
     * Disminuye la cantidad del elemento en 1.
     */
    public void disminuirCantidad() {
        if (cantidad > 1) {
            cantidad--;
        }
    }
}
