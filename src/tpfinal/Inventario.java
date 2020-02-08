package tpfinal;

import conjuntistas.ArbolAVLMultiple;
import conjuntistas.NodoAVLMultiple;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Nodo;

/**
 * Implementación de inventario de ítems ordenados y agrupados por precio.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Inventario extends ArbolAVLMultiple<Item> {

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

    private void listarRangoPorPrecio(int minimo, int maximo, NodoAVLMultiple<Item> nodo, Lista<Item> lista) {
        if (nodo != null) {
            NodoAVLMultiple<Item> izquierdo = nodo.getIzquierdo();
            NodoAVLMultiple<Item> derecho = nodo.getDerecho();
            Item item = nodo.getElemento();
            int precio = item.getPrecio();

            listarRangoPorPrecio(minimo, maximo, izquierdo, lista);

            if (minimo <= precio && precio <= maximo) {
                listarMultiple(nodo, lista);
            }

            listarRangoPorPrecio(minimo, maximo, derecho, lista);
        }
    }

    /**
     * Devuelve una lista con los ítems de los cual hay uno disponible.
     *
     * @return la lista de ítems
     */
    public Lista<Item> listarUltimosDisponibles() {
        Lista<Item> lista = new Lista<>();
        listarUltimosDisponibles(raiz, lista);

        return lista;
    }

    private void listarUltimosDisponibles(NodoAVLMultiple<Item> nodo, Lista<Item> lista) {
        if (nodo != null) {
            NodoAVLMultiple<Item> izquierdo = nodo.getIzquierdo();
            NodoAVLMultiple<Item> derecho = nodo.getDerecho();
            Item item = nodo.getElemento();

            listarUltimosDisponibles(izquierdo, lista);

            if (item.getCantidadDisponible() == 1) {
                int posicion = lista.longitud() + 1;
                Nodo<Item> enlace = nodo.getEnlace();
                lista.insertar(nodo.getElemento(), posicion);
                posicion++;

                while (enlace != null) {
                    item = enlace.getElemento();

                    if (item.getCantidadDisponible() == 1) {
                        lista.insertar(item, posicion);
                    }

                    enlace = enlace.getEnlace();
                }
            }

            listarUltimosDisponibles(derecho, lista);
        }
    }
}
