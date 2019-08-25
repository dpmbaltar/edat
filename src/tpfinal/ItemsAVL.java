package tpfinal;

import conjuntistas.ArbolAVL;
import conjuntistas.NodoAVL;
import lineales.dinamicas.Lista;

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
     * Devuelve una lista con los items, ordenados por precio de menor a mayor, mientras que el precio se encuentre
     * dentro del rango mínimo y máximo especificado (ambos inclusive).
     *
     * @param minimo el precio mínimo del rango
     * @param maximo el precio máximo del rango
     * @return la lista de items
     */
    public Lista<Item> listarRangoPorPrecio(int minimo, int maximo) {
        Lista<Item> lista = new Lista<>();
        listarRangoPorPrecio(minimo, maximo, raiz, lista);

        return lista;
    }

    private void listarRangoPorPrecio(int minimo, int maximo, NodoAVL<Item> nodo, Lista<Item> lista) {
        if (nodo != null) {
            NodoAVL<Item> izquierdo = nodo.getIzquierdo();
            NodoAVL<Item> derecho = nodo.getDerecho();
            Item item = nodo.getElemento();
            int precio = item.getPrecio();

            listarRangoPorPrecio(minimo, maximo, izquierdo, lista);

            if (minimo <= precio && precio <= maximo) {
                lista.insertar(item, lista.longitud() + 1);
            }

            listarRangoPorPrecio(minimo, maximo, derecho, lista);
        }
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
     * Busca el nodo del código de ítem dado a partir del nodo raíz.
     *
     * @param elemento el código a buscar
     * @return el nodo si el ítem fue encontrado, nulo en caso contrario
     */
    private NodoAVL<Item> buscarNodo(String codigo) {
        return buscarNodo(codigo, raiz);
    }

    /**
     * Busca el nodo del código de ítem dado a partir de un nodo en particular.
     *
     * @param elemento el código a buscar
     * @param nodo el nodo desde donde buscar el ítem
     * @return el nodo si el ítem fue encontrado, nulo en caso contrario
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
