package tpfinal;

import java.util.concurrent.ThreadLocalRandom;

import grafos.dinamicas.Grafo;
import grafos.dinamicas.NodoAdyacente;
import grafos.dinamicas.NodoVertice;
import lineales.dinamicas.Lista;

/**
 * Mapa del juego.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Mapa extends Grafo<String, Integer> {

    /**
     * Cantidad de locaciones en el mapa.
     */
    private int cantidadLocaciones;

    /**
     * Constructor vacío.
     */
    public Mapa() {
        super();
        cantidadLocaciones = 0;
    }

    @Override
    public boolean insertarVertice(String locacion) {
        boolean insertado = false;

        if (super.insertarVertice(locacion)) {
            insertado = true;
            cantidadLocaciones++;
        }

        return insertado;
    }

    @Override
    public boolean eliminarVertice(String locacion) {
        boolean eliminado = false;

        if (super.eliminarVertice(locacion)) {
            eliminado = true;
            cantidadLocaciones--;
        }

        return eliminado;
    }

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

    /**
     * Devuelve una locación aleatoria.
     *
     * @return la locación obtenida
     */
    public String locacionAleatoria() {
        String locacion = null;

        if (!esVacio()) {
            int aleatorio = ThreadLocalRandom.current().nextInt(0, cantidadLocaciones - 1);
            NodoVertice<String, Integer> nodo = inicio;

            for (int i = 0; i < aleatorio; i++) {
                nodo = nodo.getSiguienteVertice();
            }

            locacion = nodo.getElemento();
        }

        return locacion;
    }
}
