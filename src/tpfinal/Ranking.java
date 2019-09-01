package tpfinal;

import conjuntistas.ArbolAVLMultiple;
import conjuntistas.NodoAVLMultiple;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Nodo;

/**
 * Ranking de Jugadores.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Ranking extends ArbolAVLMultiple<Puesto> {

    /**
     * Puesto auxiliar para inserción, búsqueda y eliminación de jugadores.
     */
    private Puesto puestoAuxiliar;

    /**
     * Constructor vacío.
     */
    public Ranking() {
        super();
        puestoAuxiliar = new Puesto();
    }

    public boolean insertar(Jugador jugador) {
        boolean insertado = false;
        puestoAuxiliar.setJugador(jugador);

        if (insertar(puestoAuxiliar)) {
            puestoAuxiliar = new Puesto();
            insertado = true;
        } else {
            puestoAuxiliar.setJugador(null);
        }

        return insertado;
    }

    public boolean eliminar(Jugador jugador) {
        boolean eliminado = false;
        puestoAuxiliar.setJugador(jugador);

        if (eliminar(puestoAuxiliar)) {
            eliminado = true;
        }

        puestoAuxiliar.setJugador(null);

        return eliminado;
    }

    /**
     * Devuelve una lista con los elementos del árbol - ordenados de menor a mayor -.
     *
     * @return la lista con los elementos ordenados de menor a mayor
     */
    public Lista<Jugador> listarJugadores() {
        Lista<Jugador> lista = new Lista<>();
        listarJugadores(raiz, lista);

        return lista;
    }

    /**
     * Inserta los elementos del árbol - ordenados de menor a mayor - en la lista dada, del sub-árbol correspondiente
     * al nodo dado.
     *
     * @param nodo el nodo desde donde listar los elementos
     * @param lista la lista de elementos
     */
    private void listarJugadores(NodoAVLMultiple<Puesto> nodo, Lista<Jugador> lista) {
        if (nodo != null) {
            NodoAVLMultiple<Puesto> izquierdo = nodo.getIzquierdo();
            NodoAVLMultiple<Puesto> derecho = nodo.getDerecho();

            listarJugadores(izquierdo, lista);
            listarMultipleJugador(nodo, lista);
            listarJugadores(derecho, lista);
        }
    }

    private void listarMultipleJugador(NodoAVLMultiple<Puesto> nodo, Lista<Jugador> lista) {
        int posicion = lista.longitud() + 1;
        Nodo<Puesto> enlace = nodo.getEnlace();
        lista.insertar(nodo.getElemento().getJugador(), posicion);
        posicion++;

        while (enlace != null) {
            lista.insertar(enlace.getElemento().getJugador(), posicion);
            enlace = enlace.getEnlace();
        }
    }
}
