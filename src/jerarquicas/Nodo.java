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
     * La altura del nodo.
     */
    private int altura;

    /**
     * Crea y devuelve un nodo sin elemento ni enlaces.
     */
    public Nodo() {
        this(null, null, null, -1);
    }

    /**
     * Crea y devuelve un nodo con el elemento establecido.
     * 
     * @param elemento
     */
    public Nodo(E elemento) {
        this(elemento, null, null, 0);
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
        this(elemento, izquierdo, derecho,
                Math.max(izquierdo.getAltura(), derecho.getAltura()) + 1);
    }

    /**
     * Crea y devuelve un nodo con el elemento, los enlaces izquierdo y derecho
     * establecidos y su altura.
     * 
     * @param elemento
     * @param izquierdo
     * @param derecho
     * @param altura
     */
    public Nodo(E elemento, Nodo<E> izquierdo, Nodo<E> derecho, int altura) {
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = altura;
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

    /**
     * Devuelve la altura del nodo.
     * 
     * @return
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Establece la altura del nodo.
     * 
     * @param altura
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }

}
