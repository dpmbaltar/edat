package tpfinal;

import lineales.dinamicas.Lista;

public class ListaItem extends Lista<Item> implements Comparable<ListaItem> {

    @Override
    public int compareTo(ListaItem li) {
        int resultado = 0;

        if (!esVacia() && !li.esVacia()) {
            resultado = recuperar(1).compareTo(li.recuperar(1));
        }

        return resultado;
    }
}
