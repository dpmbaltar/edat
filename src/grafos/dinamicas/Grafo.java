package grafos.dinamicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import utiles.Valor;

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
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean insertarVertice(T elemento) {
        boolean insertado = false;
        NodoVertice<T> vertice = buscarVertice(elemento);

        if (vertice == null) {
            inicio = new NodoVertice<T>(elemento, inicio);
            insertado = true;
        }

        return insertado;
    }

    /**
     * Busca un nodo adyacente a partir del nodo vértice dado.
     *
     * @param vertice el nodo vértice
     * @param destino el elemento destino
     * @return el adyacente buscado, nulo si no fue encontrado
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
     * @return el vértice buscado, nulo si no fue encontrado
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
     * @param elemento el elemento del vértice a eliminar
     * @return verdadero si el elemento fue eliminado, falso en caso contrario
     */
    public boolean eliminarVertice(T elemento) {
        boolean eliminado = false;
        NodoVertice<T> verticeAnterior = null, verticeActual = inicio;
        NodoAdyacente<T> adyacente;

        // No se utiliza buscarVertice() ya que también se necesita el nodo vértice anterior
        while (verticeActual != null && !verticeActual.getElemento().equals(elemento)) {
            verticeAnterior = verticeActual;
            verticeActual = verticeActual.getSiguienteVertice();
        }

        // Eliminar vertice si fue encontrado
        if (verticeActual != null) {
            if (verticeAnterior == null) {
                inicio = verticeActual.getSiguienteVertice();
            } else {
                verticeAnterior.setSiguienteVertice(verticeActual.getSiguienteVertice());
            }

            adyacente = verticeActual.getPrimerAdyacente();

            // Eliminar referencias al vértice como destino
            while (adyacente != null) {
                eliminarAdyacente(adyacente.getVertice(), elemento);
                adyacente = adyacente.getSiguienteAdyacente();
            }

            eliminado = true;
        }

        return eliminado;
    }

    /**
     * Eliminar un nodo adyacente a partir del nodo vértice dado.
     *
     * @param vertice el nodo vértice
     * @param destino el elemento destino
     * @return el adyacente eliminado, nulo si no fue encontrado
     */
    private NodoAdyacente<T> eliminarAdyacente(NodoVertice<T> vertice, T destino) {
        NodoAdyacente<T> adyacenteActual = null;

        if (vertice != null) {
            NodoAdyacente<T> adyacenteAnterior = null;
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
     * Dados dos elementos de tipo T (origen y destino) agrega el arco en la estructura, sólo si ambos vértices ya
     * existen en el grafo. Si puede realizar la inserción devuelve verdadero, en caso contrario devuelve falso.
     *
     * @param origen el elemento origen
     * @param destino el elemento destino
     * @return verdadero si el arco fue insertado, falso en caso contrario
     */
    public boolean insertarArco(T origen, T destino) {
        boolean insertado = false;
        NodoVertice<T> verticeOrigen = buscarVertice(origen);

        if (verticeOrigen != null) {
            NodoVertice<T> verticeDestino = buscarVertice(destino);

            if (verticeDestino != null) {
                NodoAdyacente<T> adyacenteOrigen, adyacenteDestino;
                adyacenteOrigen = new NodoAdyacente<T>(verticeDestino, verticeOrigen.getPrimerAdyacente());
                verticeOrigen.setPrimerAdyacente(adyacenteOrigen);
                adyacenteDestino = new NodoAdyacente<T>(verticeOrigen, verticeDestino.getPrimerAdyacente());
                verticeDestino.setPrimerAdyacente(adyacenteDestino);
                insertado = true;
            }
        }

        return insertado;
    }

    /**
     * Dados dos elementos de tipo T (origen y destino) se quita de la estructura el arco que une ambos vértices. Si el
     * arco existe y se puede realizar la eliminación con éxito devuelve verdadero, en caso contrario devuelve falso.

     * @param origen el elemento origen
     * @param destino el elemento destino
     * @return verdadero si el arco fue eliminado, falso en caso contrario
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
        boolean eliminado = false;
        NodoVertice<T> verticeOrigen = buscarVertice(origen);

        if (verticeOrigen != null) {
            NodoAdyacente<T> adyacenteEliminado = eliminarAdyacente(verticeOrigen, destino);

            if (reciproco && adyacenteEliminado != null) {
                adyacenteEliminado = eliminarAdyacente(adyacenteEliminado.getVertice(), origen);
            }

            eliminado = adyacenteEliminado != null;
        }

        return eliminado;
    }

    /**
     * Dado un elemento, devuelve verdadero si está en la estructura y falso en caso contrario.
     *
     * @param elemento el elemento a buscar
     * @return verdadero si el vértice existe, falso en caso contrario
     */
    public boolean existeVertice(T elemento) {
        return buscarVertice(elemento) != null;
    }

    /**
     * Dados dos elementos de tipo T (origen y destino), devuelve verdadero si existe un arco en la estructura que los
     * une y falso en caso contrario.

     * @param origen el elemento origen
     * @param destino el elemento destino
     * @return verdadero si el arco existe, falso en caso contrario
     */
    public boolean existeArco(T origen, T destino) {
        return buscarAdyacente(buscarVertice(origen), destino) != null;
    }

    /**
     * Dados dos elementos de tipo T (origen y destino), devuelve verdadero si existe al menos un camino que permite
     * llegar del vértice origen al vértice destino y falso en caso contrario.
     *
     * @param origen el elemento origen
     * @param destino el elemento destino
     * @return verdadero si existe un camino de origen a destino, falso en caso contrario
     */
    public boolean existeCamino(T origen, T destino) {
        boolean existe = false;
        NodoVertice<T> verticeOrigen = buscarVertice(origen);
        NodoVertice<T> verticeDestino = buscarVertice(destino);

        if (verticeOrigen != null && verticeDestino != null) {
            existe = existeCaminoDesde(verticeOrigen, destino, new Lista<T>());
        }

        return existe;
    }

    /**
     * Verifica si hay un camino desde el vértice dado al destino, en forma recursiva, evitando vértices ya visitados.
     *
     * @param vertice el vértice a visitar
     * @param destino el elemento destino
     * @param visitados lista de vértices visitados
     * @return verdadero si existe un camino del vértice actual a destino, falso en caso contrario
     */
    private boolean existeCaminoDesde(NodoVertice<T> vertice, T destino, Lista<T> visitados) {
        boolean existe = false;

        if (vertice != null) {
            if (vertice.getElemento().equals(destino)) {
                existe = true;
            } else {
                visitados.insertar(vertice.getElemento(), visitados.longitud() + 1);
                NodoAdyacente<T> adyacente = vertice.getPrimerAdyacente();

                while (adyacente != null && !existe) {
                    if (visitados.localizar(adyacente.getVertice().getElemento()) < 0) {
                        existe = existeCaminoDesde(adyacente.getVertice(), destino, visitados);
                    }

                    adyacente = adyacente.getSiguienteAdyacente();
                }
            }
        }

        return existe;
    }

    /**
     * Dados dos elementos de tipo T (origen y destino), devuelve un camino (lista de vértices) que indique el camino
     * que pasa por menos vértices que permite llegar del vértice origen al vértice destino. Si hay más de un camino
     * con igual cantidad de vértices, devuelve cualquiera de ellos. Si alguno de los vértices no existe o no hay
     * camino posible entre ellos devuelve la lista vacía.
     *
     * Esta implementación es medianamente mejor que caminoMasCorto2(), ya que recorre a lo sumo cada vértice una sola
     * vez, como en listarEnAnchura(), y no utiliza recursión. Sin embargo, utiliza una cola y lista auxiliar, que a lo
     * sumo, tiene la mísma cantidad de elementos que vértices en el grafo, pero con una sóla referencia al vértice
     * predecesor, para armar el camino al final del método. También se agregó el método equals() a NodoVertice<T>,
     * que devuelve verdadero sólo si el elemento es igual al elemento del otro nodo, para poder ubicar un vértice en
     * la lista con el método Lista<T>.localizar(). Quizá sería mejor buscar una versión alternativa para esto sin
     * alterar la clase NodoVertice<T>.
     *
     * @param origen el elemento origen
     * @param destino el elemento destino
     * @return la lista con el camino más corto
     */
    public Lista<T> caminoMasCorto(T origen, T destino) {
        Lista<T> camino = new Lista<>();
        NodoVertice<T> vertice = buscarVertice(origen);

        if (vertice != null) {
            boolean finalizar = false;
            T elemento;
            NodoVertice<T> predecesor;
            NodoAdyacente<T> adyacente;
            Cola<NodoVertice<T>> colaVertices = new Cola<>();
            Lista<NodoVertice<T>> predecesores = new Lista<>();
            Lista<T> visitados = new Lista<>();

            // Visitar el origen primero y guardarlo como predecesor
            elemento = vertice.getElemento();
            visitados.insertar(elemento, 1);
            predecesor = new NodoVertice<T>(elemento);
            predecesores.insertar(predecesor, 1);
            colaVertices.poner(vertice);

            // Recorrer cada vértice como en listar en anchura
            while (!colaVertices.esVacia() && !finalizar) {
                vertice = colaVertices.obtenerFrente();
                colaVertices.sacar();
                //TODO: Se agregó NodoVertice<T>.equals() para localizar vértices por elemento. ¿Buscar alternativa?
                predecesor = predecesores.recuperar(predecesores.localizar(vertice));
                adyacente = vertice.getPrimerAdyacente();

                // Visitar cada vértice adyacente guardando su predecesor
                while (adyacente != null && !finalizar) {
                    elemento = adyacente.getVertice().getElemento();

                    if (visitados.localizar(elemento) < 0) {
                        visitados.insertar(elemento, visitados.longitud() + 1);
                        predecesores.insertar(new NodoVertice<T>(elemento, predecesor), predecesores.longitud() + 1);
                        colaVertices.poner(adyacente.getVertice());

                        // Finalizar si el destino fue encontrado
                        if (elemento.equals(destino)) {
                            finalizar = true;
                        }
                    }

                    adyacente = adyacente.getSiguienteAdyacente();
                }
            }

            vertice = predecesores.recuperar(predecesores.longitud());

            // Obtener el camino más corto a través de la lista auxiliar de predecesores
            while (vertice != null) {
                camino.insertar(vertice.getElemento(), 1);
                vertice = vertice.getSiguienteVertice();
            }
        }

        return camino;
    }

    /**
     * Dados dos elementos de tipo T (origen y destino), devuelve un camino (lista de vértices) que indique el camino
     * que pasa por menos vértices que permite llegar del vértice origen al vértice destino. Si hay más de un camino
     * con igual cantidad de vértices, devuelve cualquiera de ellos. Si alguno de los vértices no existe o no hay
     * camino posible entre ellos devuelve la lista vacía.
     *
     * @deprecated
     * Esta implementación recorre casi todos los caminos posibles desde el origen manteniendo el camino más corto por
     * parámetro, incluyendo a veces caminos que no llegan al destino, y por lo tanto es más costoso que el anterior.
     *
     * @param origen el elemento origen
     * @param destino el elemento destino
     * @return la lista con el camino más corto
     */
    @Deprecated
    public Lista<T> caminoMasCorto2(T origen, T destino) {
        Valor<Lista<T>> camino = new Valor<>(new Lista<>());
        NodoVertice<T> vertice = buscarVertice(origen);

        if (vertice != null) {
            caminoMasCortoDesde(vertice, destino, new Lista<>(), camino, new Valor<Integer>(Integer.MAX_VALUE));
        }

        return camino.getValor();
    }

    /**
     * Busca recursivamente el camino mas corto desde el vértice dado al elemento destino. Si el elemento del vértice
     * dado es el destino, entonces se ha encontrado un camino. Luego, si el camino encontrado es de menor longitud
     * que minimaLongitud, entonces se guarda el camino en el contenedor caminoMin.
     *
     * @deprecated
     * Parte de la implementación de caminoMasCorto2().
     *
     * @param vertice el vertice de origen
     * @param destino el elemento destino
     * @param camino el camino actual
     * @param caminoMin el camino mínimo encontrado
     * @param minimaLongitud la longitud del camino mínimo encontrado
     */
    @Deprecated
    private void caminoMasCortoDesde(
            NodoVertice<T> vertice,
            T destino,
            Lista<T> camino,
            Valor<Lista<T>> caminoMin,
            Valor<Integer> minimaLongitud) {
        int nuevaLongitud = camino.longitud() + 1;

        // Continuar la búsqueda en el vértice sólo si la longitud del camino actual es menor a la mínima encontrada
        if (nuevaLongitud < minimaLongitud.getValor() && vertice != null) {
            camino.insertar(vertice.getElemento(), nuevaLongitud);

            if (vertice.getElemento().equals(destino)) {
                // Destino encontrado
                if (camino.longitud() < minimaLongitud.getValor()) {
                    caminoMin.setValor(camino.clonar());
                    minimaLongitud.setValor(caminoMin.getValor().longitud());
                }
            } else {
                // Destino no encontrado; buscar en los adyacentes
                NodoAdyacente<T> adyacente = vertice.getPrimerAdyacente();

                while (adyacente != null) {
                    if (camino.localizar(adyacente.getVertice().getElemento()) < 0) {
                        caminoMasCortoDesde(adyacente.getVertice(), destino, camino, caminoMin, minimaLongitud);
                    }

                    adyacente = adyacente.getSiguienteAdyacente();
                }
            }

            camino.eliminar(nuevaLongitud);
        }
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
        Valor<Lista<T>> camino = new Valor<>(new Lista<>());
        NodoVertice<T> vertice = buscarVertice(origen);

        if (vertice != null) {
            caminoMasLargoDesde(vertice, destino, new Lista<>(), camino, new Valor<Integer>(-1));
        }

        return camino.getValor();
    }

    /**
     * Busca recursivamente el camino más largo desde el vértice dado al elemento destino. Si el elemento del vértice
     * dado es el destino, entonces se ha encontrado un camino. Luego, si el camino encontrado es de mayor longitud
     * que maximaLongitud, entonces se guarda el camino en el contenedor caminoMax.
     *
     * @param vertice el vertice de origen
     * @param destino el elemento destino
     * @param camino el camino actual
     * @param caminoMax el camino máximo encontrado
     * @param maximaLongitud la longitud del camino máximo encontrado
     */
    private void caminoMasLargoDesde(
            NodoVertice<T> vertice,
            T destino,
            Lista<T> camino,
            Valor<Lista<T>> caminoMax,
            Valor<Integer> maximaLongitud) {
        int nuevaLongitud = camino.longitud() + 1;

        if (vertice != null) {
            camino.insertar(vertice.getElemento(), nuevaLongitud);
            if (vertice.getElemento().equals(destino)) {
                // Destino encontrado
                if (camino.longitud() > maximaLongitud.getValor()) {
                    caminoMax.setValor(camino.clonar());
                    maximaLongitud.setValor(caminoMax.getValor().longitud());
                }
            } else {
                // Destino no encontrado; buscar en los adyacentes
                NodoAdyacente<T> adyacente = vertice.getPrimerAdyacente();
                while (adyacente != null) {
                    if (camino.localizar(adyacente.getVertice().getElemento()) < 0) {
                        caminoMasLargoDesde(adyacente.getVertice(), destino, camino, caminoMax, maximaLongitud);
                    }

                    adyacente = adyacente.getSiguienteAdyacente();
                }
            }

            camino.eliminar(nuevaLongitud);
        }
    }

    /**
     * Devuelve una lista con los vértices del grafo visitados según el recorrido en profundidad.
     *
     * @return
     */
    public Lista<T> listarEnProfundidad() {
        Lista<T> visitados = new Lista<>();
        NodoVertice<T> vertice = inicio;

        while (vertice != null) {
            if (visitados.localizar(vertice.getElemento()) < 0) {
                listarEnProfundidadDesde(vertice, visitados);
            }

            vertice = vertice.getSiguienteVertice();
        }

        return visitados;
    }

    private void listarEnProfundidadDesde(NodoVertice<T> vertice, Lista<T> visitados) {
        if (vertice != null) {
            visitados.insertar(vertice.getElemento(), visitados.longitud() + 1);
            NodoAdyacente<T> adyacente = vertice.getPrimerAdyacente();

            while (adyacente != null) {
                if (visitados.localizar(adyacente.getVertice().getElemento()) < 0) {
                    listarEnProfundidadDesde(adyacente.getVertice(), visitados);
                }

                adyacente = adyacente.getSiguienteAdyacente();
            }
        }
    }

    /**
     * Devuelve una lista con los vértices del grafo visitados según el recorrido en anchura.
     *
     * @return
     */
    public Lista<T> listarEnAnchura() {
        Lista<T> visitados = new Lista<>();
        NodoVertice<T> vertice = inicio;

        while (vertice != null) {
            if (visitados.localizar(vertice.getElemento()) < 0) {
                listarEnAnchuraDesde(vertice, visitados);
            }

            vertice = vertice.getSiguienteVertice();
        }

        return visitados;
    }

    private void listarEnAnchuraDesde(NodoVertice<T> vertice, Lista<T> visitados) {
        if (vertice != null) {
            NodoVertice<T> verticeFrente;
            NodoAdyacente<T> adyacente;
            Cola<NodoVertice<T>> colaVertices = new Cola<>();
            colaVertices.poner(vertice);

            while (!colaVertices.esVacia()) {
                verticeFrente = colaVertices.obtenerFrente();
                colaVertices.sacar();
                //visitados.insertar(verticeFrente.getElemento(), visitados.longitud() + 1);
                adyacente = verticeFrente.getPrimerAdyacente();

                while (adyacente != null) {
                    if (visitados.localizar(adyacente.getVertice().getElemento()) < 0) {
                        colaVertices.poner(adyacente.getVertice());
                        visitados.insertar(adyacente.getVertice().getElemento(), visitados.longitud() + 1);
                    }

                    adyacente = adyacente.getSiguienteAdyacente();
                }
            }
        }
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
        Grafo<T> clon = new Grafo<>();
        NodoVertice<T> inicioClon = null;
        NodoVertice<T> vertice = inicio;
        NodoAdyacente<T> adyacente = null;
        Lista<String> listaArcos = new Lista<>();
        String arco;
        int posArco;

        while (vertice != null) {
            inicioClon = new NodoVertice<T>(vertice.getElemento(), inicioClon);
            adyacente = vertice.getPrimerAdyacente();

            while (adyacente != null) {
                arco = adyacente.getVertice().getElemento().toString()+"-"+vertice.getElemento().toString();
                posArco = listaArcos.localizar(arco);

                if (posArco >= 0) {
                    listaArcos.eliminar(posArco);
                } else {
                    arco = vertice.getElemento().toString()+"-"+adyacente.getVertice().getElemento().toString();
                    listaArcos.insertar(arco, listaArcos.longitud() + 1);
                    inicioClon.setPrimerAdyacente(
                            new NodoAdyacente<T>(adyacente.getVertice(), inicioClon.getPrimerAdyacente()));
                }

                adyacente = adyacente.getSiguienteAdyacente();
            }

            vertice = vertice.getSiguienteVertice();
        }

        // Insertar todos los vértices
        vertice = inicioClon;
        while (vertice != null) {
            clon.insertarVertice(vertice.getElemento());
            vertice = vertice.getSiguienteVertice();
        }

        // Insertar todos los arcos
        vertice = inicioClon;
        while (vertice != null) {
            adyacente = vertice.getPrimerAdyacente();
            while (adyacente != null) {
                //FIXME: la estructura interna de arcos queda distinta en algunos casos, pero equivalente
                clon.insertarArco(vertice.getElemento(), adyacente.getVertice().getElemento());
                adyacente = adyacente.getSiguienteAdyacente();
            }

            vertice = vertice.getSiguienteVertice();
        }

        return clon;
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
        /*while (vertice != null) {
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
        }*/

        // Se forma la cadena recorriendo en orden inverso del cual fueron agregados los vértices de acorde al modelo
        while (vertice != null) {
            cadena.append(String.valueOf(vertice.getElemento())).append(": [");
            adyacente = vertice.getPrimerAdyacente();

            while (adyacente != null) {
                cadena.append(String.valueOf(adyacente.getVertice().getElemento()));
                adyacente = adyacente.getSiguienteAdyacente();
                if (adyacente != null) {
                    cadena.append(", ");
                }
            }

            cadena.append("]");
            vertice = vertice.getSiguienteVertice();

            if (vertice != null) {
                cadena.append("\r\n");
            }
        }

        return cadena.toString();
    }
}
