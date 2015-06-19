package jerarquicas.dinamicas;

/**
 * Implementación de un nodo de tres enlaces: izquierdo, derecho y padre.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <E>
 */
public class Nodo<E> {

    /**
     * La altura del nodo.
     */
    private int altura;

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
     * El nodo padre.
     */
    private Nodo<E> padre;

    /**
     * Crea y devuelve un nodo sin elemento ni enlaces.
     */
    public Nodo() {
        this(null, null, null, null);
    }

    /**
     * Crea y devuelve un nodo con el enlace padre establecido.
     * 
     * @param padre
     */
    public Nodo(Nodo<E> padre) {
        this(null, null, null, padre);
    }

    /**
     * Crea y devuelve un nodo con los enlaces izquierdo y derecho establecidos.
     * 
     * @param izquierdo
     * @param derecho
     */
    public Nodo(Nodo<E> izquierdo, Nodo<E> derecho) {
        this(null, izquierdo, derecho, null);
    }

    /**
     * Crea y devuelve un nodo con los todos los enlaces establecidos.
     * 
     * @param izquierdo
     * @param derecho
     * @param padre
     */
    public Nodo(Nodo<E> izquierdo, Nodo<E> derecho, Nodo<E> padre) {
        this(null, izquierdo, derecho, padre);
    }

    /**
     * Crea y devuelve un nodo con el elemento establecido.
     * 
     * @param elemento
     */
    public Nodo(E elemento) {
        this(elemento, null, null, null);
    }

    /**
     * Crea y devuelve un nodo con el elemento y el nodo padre establecidos.
     * 
     * @param elemento
     * @param padre
     */
    public Nodo(E elemento, Nodo<E> padre) {
        this(elemento, null, null, padre);
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
        this(elemento, izquierdo, derecho, null);
    }

    /**
     * Crea y devuelve un nodo con el elemento y todos los enlaces establecidos.
     * 
     * @param elemento
     * @param izquierdo
     * @param derecho
     * @param padre
     */
    public Nodo(E elemento, Nodo<E> izquierdo, Nodo<E> derecho, Nodo<E> padre) {
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.padre = padre;
        this.altura = 0;
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
     * Devuelve el nodo padre.
     * 
     * @return
     */
    public Nodo<E> getPadre() {
        return padre;
    }

    /**
     * Establece el nodo padre.
     * 
     * @param padre
     */
    public void setPadre(Nodo<E> padre) {
        this.padre = padre;
    }
}
