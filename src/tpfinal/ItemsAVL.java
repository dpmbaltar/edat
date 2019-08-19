package tpfinal;

import conjuntistas.ArbolAVL;
import conjuntistas.NodoAVL;

/**
 * Arbol AVL de ítems.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class ItemsAVL extends ArbolAVL<Item> {

    /**
     * Constructor vacío.
     */
    public ItemsAVL() {
        raiz = null;
    }

    /**
     * Devuelve el ítem con el código dado, si existe en la estructura, nulo en caso contrario.
     *
     * @param codigo el código del ítem
     * @return el ítem si existe, nulo en caso contrario
     */
    public Item obtener(String codigo) {
        NodoAVL<Item> nodo = buscarNodo(codigo);
        return nodo != null ? nodo.getElemento() : null;
    }

    /**
     * Busca el nodo del elemento dado a partir del nodo raíz.
     *
     * @param elemento el elemento a buscar
     * @return el nodo si el elemento fue encontrado, nulo en caso contrario
     */
    private NodoAVL<Item> buscarNodo(String codigo) {
        return buscarNodo(codigo, raiz);
    }

    /**
     * Busca el nodo del elemento dado a partir de un nodo en particular.
     *
     * @param elemento el elemento a buscar
     * @param nodo el nodo desde donde buscar el elemento
     * @return el nodo si el elemento fue encontrado, nulo en caso contrario
     */
    private NodoAVL<Item> buscarNodo(String codigo, NodoAVL<Item> nodo) {
        NodoAVL<Item> buscado = null;

        if (nodo != null) {
            if (nodo.getElemento().getCodigo().equals(codigo)) {
                buscado = nodo;
            } else {
                buscado = buscarNodo(codigo, nodo.getIzquierdo());

                if (buscado == null) {
                    buscado = buscarNodo(codigo, nodo.getDerecho());
                }
            }
        }

        return buscado;
    }
}
