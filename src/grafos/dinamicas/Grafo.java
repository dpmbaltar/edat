package grafos.dinamicas;

import lineales.dinamicas.Lista;

/**
 * Implementación de Grafo no dirigido.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class Grafo<T> {

    /**
     * El vértice de inicio del grafo.
     */
    private NodoVertice<T> inicio;

    /**
     * Crea un grafo vacío.
     */
    public Grafo() {
        inicio = null;
    }

    /**
     * Dado un elemento de tipo T se lo agrega a la estructura controlando que no se inserten vértices repetidos.
     * Si puede realizar la inserción devuelve verdadero, en caso contrario devuelve falso.
     *
     * @param elemento el elemento a insertar
     * @return
     */
    public boolean insertarVertice(T elemento) {
        boolean exito = false;
        NodoVertice<T> nodoVertice = buscarVertice(elemento);
        if (nodoVertice == null) {
            inicio = new NodoVertice<T>(elemento, inicio);
            exito = true;
        }

        return exito;
    }

    /**
     * Busca un nodo adyacente a partir del nodo vértice dado.
     *
     * @param vertice el nodo vértice
     * @param destino el elemento destino
     * @return
     */
    private NodoAdyacente<T> buscarAdyacente(NodoVertice<T> vertice, T destino) {
        NodoAdyacente<T> adyacente = null;
        if (vertice != null) {
            adyacente = vertice.getPrimerAdyacente();
            while (adyacente != null && !adyacente.getVertice().getElemento().equals(destino)) {
                adyacente = adyacente.getSiguienteAdyacente();
            }
        }

        return adyacente;
    }

    /**
     * Busca un nodo vértice a partir del elemento dado.
     *
     * @param elemento el elemento a buscar
     * @return
     */
    private NodoVertice<T> buscarVertice(T elemento) {
        NodoVertice<T> vertice = inicio;
        while (vertice != null && !vertice.getElemento().equals(elemento)) {
            vertice = vertice.getSiguienteVertice();
        }

        return vertice;
    }

    /**
     * Dado un elemento de tipo T se lo quita de la estructura. Si se encuentra el vértice, también  deben eliminarse
     * todos los arcos que lo tengan como origen o destino. Si se puede realizar la eliminación con éxito
     * devuelve verdadero, en caso contrario devuelve falso.
     *
     * @param vertice
     * @return
     */
    public boolean eliminarVertice(T vertice) {
        throw new UnsupportedOperationException("Grafo.eliminarVertice() no implementado");
    }

    /**
     * Dados dos elementos de tipo T (origen y destino) agrega el arco en la estructura, sólo si ambos vértices ya
     * existen en el grafo. Si puede realizar la inserción devuelve verdadero, en caso contrario devuelve falso.
     *
     * @param origen
     * @param destino
     * @return
     */
    public boolean insertarArco(T origen, T destino) {
        boolean exito = false;
        NodoVertice<T> verticeOrigen = buscarVertice(origen);

        if (verticeOrigen != null) {
            NodoVertice<T> verticeDestino = buscarVertice(destino);
            if (verticeDestino != null) {
                NodoAdyacente<T> adyacenteDeOrigen, adyacenteDeDestino;
                adyacenteDeOrigen = new NodoAdyacente<T>(verticeDestino, verticeOrigen.getPrimerAdyacente());
                verticeOrigen.setPrimerAdyacente(adyacenteDeOrigen);
                adyacenteDeDestino = new NodoAdyacente<T>(verticeOrigen, verticeDestino.getPrimerAdyacente());
                verticeDestino.setPrimerAdyacente(adyacenteDeDestino);
                exito = true;
            }
        }

        return exito;
    }

    /**
     * Dados dos elementos de tipo T (origen y destino) se quita de la estructura el arco que une ambos vértices. Si el
     * arco existe y se puede realizar la eliminación con éxito devuelve verdadero, en caso contrario devuelve falso.

     * @param origen
     * @param destino
     * @return
     */
    public boolean eliminarArco(T origen, T destino) {
        return eliminarArco(origen, destino, true);
    }

    /**
     * Elimina el arco desde destino a origen, recíprocamente si es especificado.
     *
     * @param origen el elemento origen
     * @param destino el elemento destino
     * @param reciproco si también se debe borrar el arco de destino a origen
     * @return
     */
    private boolean eliminarArco(T origen, T destino, boolean reciproco) {
        boolean exito = false;
        NodoVertice<T> verticeOrigen = buscarVertice(origen);

        if (verticeOrigen != null) {
            NodoAdyacente<T> eliminado = eliminarAdyacente(verticeOrigen, destino);
            if (reciproco && eliminado != null) {
                eliminado = eliminarAdyacente(eliminado.getVertice(), origen);
            }

            exito = eliminado != null;
        }

        return exito;
    }

    /**
     * Eliminar un nodo adyacente a partir del nodo vértice dado.
     *
     * @param vertice el nodo vértice
     * @param destino el elemento destino
     * @return el adyacente eliminado
     */
    private NodoAdyacente<T> eliminarAdyacente(NodoVertice<T> vertice, T destino) {
        NodoAdyacente<T> adyacenteAnterior, adyacenteActual = null;
        if (vertice != null) {
            adyacenteAnterior = null;
            adyacenteActual = vertice.getPrimerAdyacente();

            while (adyacenteActual != null && !adyacenteActual.getVertice().getElemento().equals(destino)) {
                adyacenteAnterior = adyacenteActual;
                adyacenteActual = adyacenteActual.getSiguienteAdyacente();
            }

            // Eliminar adyacente destino si fue encontrado
            if (adyacenteActual != null) {
                if (adyacenteAnterior == null) {
                    vertice.setPrimerAdyacente(adyacenteActual.getSiguienteAdyacente());
                } else {
                    adyacenteAnterior.setSiguienteAdyacente(adyacenteActual.getSiguienteAdyacente());
                }
            }
        }

        return adyacenteActual;
    }

    /**
     * Dado un elemento, devuelve verdadero si está en la estructura y falso en caso contrario.
     *
     * @param elemento el elemento del vértice a buscar
     * @return
     */
    public boolean existeVertice(T elemento) {
        return buscarVertice(elemento) != null;
    }

    /**
     * Dados dos elementos de tipo T (origen y destino), devuelve verdadero si existe un arco en la estructura que los
     * une y falso en caso contrario.

     * @param origen el elemento del vértice origen
     * @param destino el elemento del vértice destino
     * @return verdadero si el arco existe, falso en caso contrario
     */
    public boolean existeArco(T origen, T destino) {
        return buscarAdyacente(buscarVertice(origen), destino) != null;
    }

    /**
     * Dados dos elementos de tipo T (origen y destino), devuelve verdadero si existe al menos un camino que permite
     * llegar del vértice origen al vértice destino y falso en caso contrario.
     *
     * @param origen
     * @param destino
     * @return
     */
    public boolean existeCamino(T origen, T destino) {
        throw new UnsupportedOperationException("Grafo.existeCamino() no implementado");
    }

    /**
     * Dados dos elementos de tipo T (origen y destino), devuelve un camino (lista de vértices) que indique el camino
     * que pasa por menos vértices que permite llegar del vértice origen al vértice destino. Si hay más de un camino
     * con igual cantidad de vértices, devuelve cualquiera de ellos. Si alguno de los vértices no existe o no hay
     * camino posible entre ellos devuelve la lista vacía.
     *
     * @param origen
     * @param destino
     * @return
     */
    public Lista<T> caminoMasCorto(T origen, T destino) {
        throw new UnsupportedOperationException("Grafo.caminoMasCorto() no implementado");
    }

    /**
     * Dados dos elementos de tipo T (origen y destino), devuelve un camino (lista de vértices) que indique el camino
     * que pasa por más vértices (sin ciclos) que permite llegar del vértice origen al vértice destino. Si hay más de
     * un camino con igual cantidad de vértices, devuelve cualquiera de ellos. Si alguno de los vértices no existe o no
     * hay camino posible entre ellos devuelve la lista vacía.
     *
     * @param origen
     * @param destino
     * @return
     */
    public Lista<T> caminoMasLargo(T origen, T destino) {
        throw new UnsupportedOperationException("Grafo.caminoMasLargo() no implementado");
    }

    /**
     * Devuelve una lista con los vértices del grafo visitados según el recorrido en profundidad.
     *
     * @return
     */
    public Lista<T> listarEnProfundidad() {
        throw new UnsupportedOperationException("Grafo.listarEnProfundidad() no implementado");
    }

    /**
     * Devuelve una lista con los vértices del grafo visitados según el recorrido en anchura.
     *
     * @return
     */
    public Lista<T> listarEnAnchura() {
        throw new UnsupportedOperationException("Grafo.listarEnAnchura() no implementado");
    }

    /**
     * Devuelve falso si hay al menos un vértice cargado en el grafo y verdadero en caso contrario.
     *
     * @return
     */
    public boolean esVacio() {
        return inicio == null;
    }

    /**
     * Genera y devuelve un grafo que es equivalente (igual estructura y contenido de los nodos) al original.
     *
     * @return
     */
    public Grafo<T> clonar() {
        throw new UnsupportedOperationException("Grafo.clonar() no implementado");
    }

    /**
     * Genera y devuelve una cadena String que muestra los vértices almacenados en el grafo y qué adyacentes tiene cada
     * uno de ellos.
     */
    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        NodoVertice<T> vertice = inicio;
        NodoAdyacente<T> adyacente;

        // Se forma la cadena recorriendo en forma inversa los vértices y sus adyacentes, para visualizar en el mismo
        // orden en el que los elementos fueron agregados
        while (vertice != null) {
            cadena.insert(0, "]");
            adyacente = vertice.getPrimerAdyacente();

            while (adyacente != null) {
                cadena.insert(0, String.valueOf(adyacente.getVertice().getElemento()));
                adyacente = adyacente.getSiguienteAdyacente();
                if (adyacente != null) {
                    cadena.insert(0, ", ");
                }
            }

            cadena.insert(0, ": [");
            cadena.insert(0, String.valueOf(vertice.getElemento()));
            vertice = vertice.getSiguienteVertice();

            if (vertice != null) {
                cadena.insert(0, "\r\n");
            }
        }

        return cadena.toString();
    }
}
