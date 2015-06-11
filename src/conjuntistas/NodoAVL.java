package conjuntistas;

import jerarquicas.dinamicas.Nodo;

/**
 * Implementación de un nodo para Árbol AVL.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <E>
 */
public class NodoAVL<E> extends Nodo<E> {

    /**
     * La altura del nodo.
     */
    private int altura;

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
