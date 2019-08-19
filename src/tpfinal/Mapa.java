package tpfinal;

import grafos.dinamicas.Grafo;
import grafos.dinamicas.NodoAdyacente;
import grafos.dinamicas.NodoVertice;
import lineales.dinamicas.Lista;

public class Mapa extends Grafo<String, Integer> {

    /**
     * Devuelve una lista con las locaciones adyacentes a una locación dada.
     *
     * @param locacion la locación
     * @return la lista de locaciones adyacentes
     */
    public Lista<String> listarAdyacentes(String locacion) {
        Lista<String> adyacentes = new Lista<>();
        NodoVertice<String, Integer> vertice = buscarVertice(locacion);

        if (vertice != null) {
            NodoAdyacente<String, Integer> adyacente = vertice.getPrimerAdyacente();

            while (adyacente != null) {
                adyacentes.insertar(adyacente.getVertice().getElemento(), adyacentes.longitud() + 1);
                adyacente = adyacente.getSiguienteAdyacente();
            }
        }

        return adyacentes;
    }
}
