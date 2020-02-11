package tpfinal;

import java.util.concurrent.ThreadLocalRandom;

import grafos.dinamicas.Grafo;
import grafos.dinamicas.NodoAdyacente;
import grafos.dinamicas.NodoVertice;
import lineales.dinamicas.Cola;
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
     * Actualiza una locación existente en el mapa.
     *
     * @param locacion el nombre de la locación
     * @param nuevaLocacion el nombre nuevo
     * @return verdadero si fue cambiado, falso en caso contrario
     */
    public boolean modificarVertice(String locacion, String nuevaLocacion) {
        boolean modificado = false;
        NodoVertice<String, Integer> vertice = buscarVertice(locacion);
        NodoVertice<String, Integer> verticeNuevaLocacion = buscarVertice(nuevaLocacion);

        if (vertice != null && verticeNuevaLocacion == null) {
            vertice.setElemento(nuevaLocacion);
            modificado = true;
        }

        return modificado;
    }

    /**
     * Actualiza la etiqueta (distancia) de un arco entre una locación y otra.
     *
     * @param locacion la locación origen
     * @param locacionAdyacente la locación destino
     * @param nuevaEtiqueta el nuevo valor de la etiqueta (distancia)
     * @return verdadero si fue modificada, falso en caso contrario
     */
    public boolean modificarEtiqueta(String locacion, String locacionAdyacente, Integer nuevaEtiqueta) {
        boolean modificada = false;
        NodoVertice<String, Integer> vertice = buscarVertice(locacion);

        if (vertice != null) {
            NodoAdyacente<String, Integer> adyacente = buscarAdyacente(vertice, locacionAdyacente);

            if (adyacente != null) {
                NodoAdyacente<String, Integer> reciproco = buscarAdyacente(adyacente.getVertice(), locacion);
                adyacente.setEtiqueta(nuevaEtiqueta);
                reciproco.setEtiqueta(nuevaEtiqueta);
                modificada = true;
            }
        }

        return modificada;
    }

    /**
     * Devuelve una lista de las locaciones del mapa.
     *
     * @return la lista de locaciones
     */
    public Lista<String> listarVertices() {
        Lista<String> vertices = new Lista<>();
        NodoVertice<String, Integer> vertice = inicio;

        while (vertice != null) {
            vertices.insertar(vertice.getElemento(), vertices.longitud() + 1);
            vertice = vertice.getSiguienteVertice();
        }

        return vertices;
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

    /**
     * Camino más corto según el peso de la etiqueta.
     *
     * @param origen el origen
     * @param destino el destino
     * @return el camino más corto
     */
    public Camino caminoMasCortoKms(String origen, String destino) {
        Camino camino = new Camino();
        NodoVertice<String, Integer> vertice = buscarVertice(origen);

        if (vertice != null && destino != null) {
            int distancia, distanciaAdy, distanciaPredAdy;
            int posicionPredecesorAdy;
            NodoVertice<String, Integer> predecesor, predecesorAdy;
            NodoVertice<String, Integer> verticeDestino = null;
            NodoAdyacente<String, Integer> adyacente;

            // Estructuras auxiliares
            Lista<String> visitados = new Lista<>();
            Lista<NodoVertice<String, Integer>> predecesores = new Lista<>();
            Cola<NodoVertice<String, Integer>> cola = new Cola<>();

            // Crear origen con predecesor nulo y distancia 0, y luego visitarlo
            predecesor = new NodoVertice<>(vertice.getElemento());
            predecesor.setPrimerAdyacente(new NodoAdyacente<>(null, null, 0));
            predecesores.insertar(predecesor, 1);
            cola.poner(vertice);

            // Visitar elementos no visitados
            while (!cola.esVacia()) {
                vertice = cola.obtenerFrente();
                cola.sacar();
                visitados.insertar(vertice.getElemento(), visitados.longitud() + 1);
                adyacente = vertice.getPrimerAdyacente();
                predecesor = predecesores.recuperar(predecesores.localizar(vertice));
                distancia = predecesor.getPrimerAdyacente().getEtiqueta();

                while (adyacente != null) {
                    if (visitados.localizar(adyacente.getVertice().getElemento()) < 0) {
                        distanciaAdy = adyacente.getEtiqueta() + distancia;
                        posicionPredecesorAdy = predecesores.localizar(adyacente.getVertice());

                        // Crear/obtener el vertice auxilar (adyacente, predecesor)
                        if (posicionPredecesorAdy < 0) {
                            predecesorAdy = new NodoVertice<>(adyacente.getVertice().getElemento());
                            predecesorAdy.setPrimerAdyacente(new NodoAdyacente<>(predecesor, null, distanciaAdy));
                            predecesores.insertar(predecesorAdy, predecesores.longitud() + 1);
                            cola.poner(adyacente.getVertice());
                        } else {
                            predecesorAdy = predecesores.recuperar(posicionPredecesorAdy);
                        }

                        // Distancia del adyacente al predecesor
                        distanciaPredAdy = predecesorAdy.getPrimerAdyacente().getEtiqueta() + adyacente.getEtiqueta();

                        // Actualizar predecesor y distancia si es necesario
                        if (distanciaPredAdy < distancia) {
                            predecesor.getPrimerAdyacente().setVertice(predecesorAdy);
                            predecesor.getPrimerAdyacente().setEtiqueta(distanciaPredAdy);
                        }

                        // Obtener vertice destino
                        if (adyacente.getVertice().getElemento().equals(destino)) {
                            verticeDestino = predecesorAdy;
                        }
                    }

                    adyacente = adyacente.getSiguienteAdyacente();
                }
            }

            // Obtener camino
            if (verticeDestino != null) {
                distancia = 0;
                visitados = camino.getLocaciones();
                visitados.insertar(verticeDestino.getElemento(), 1);
                adyacente = verticeDestino.getPrimerAdyacente();

                while (adyacente != null) {
                    if (adyacente.getVertice() != null) {
                        distancia += adyacente.getEtiqueta();
                        visitados.insertar(adyacente.getVertice().getElemento(), 1);
                        adyacente = adyacente.getVertice().getPrimerAdyacente();
                    } else {
                        adyacente = null;
                        camino.setDistancia(distancia);
                    }
                }
            }
        }

        return camino;
    }

    public Lista<Camino> caminosHastaDistancia(String origen, String destino, int distanciaMaxima) {
        Lista<Camino> caminos = new Lista<>();

        // Evitar buscar caminos de origen igual al destino
        if (!origen.equals(destino)) {
            NodoVertice<String, Integer> verticeOrigen = buscarVertice(origen);
            NodoVertice<String, Integer> verticeDestino = buscarVertice(destino);

            // Proceder a buscar sólo si existe el origen y destino
            if (verticeOrigen != null && verticeDestino != null) {
                caminosHastaDistanciaDesde(verticeOrigen, destino, distanciaMaxima, new Camino(), caminos);
            }
        }

        return caminos;
    }

    private void caminosHastaDistanciaDesde(
            NodoVertice<String, Integer> vertice,
            String destino,
            int distanciaMax,
            Camino caminoActual,
            Lista<Camino> caminosValidos) {
        Lista<String> locaciones = caminoActual.getLocaciones();
        int distanciaRecorrida = caminoActual.getDistancia();
        int distancia;

        if (vertice != null) {
            locaciones.insertar(vertice.getElemento(), locaciones.longitud() + 1);

            if (vertice.getElemento().equals(destino)) { // Destino encontrado
                if (distanciaRecorrida <= distanciaMax) {
                    caminosValidos.insertar(caminoActual.clone(), caminosValidos.longitud() + 1);
                }
            } else { // Destino no encontrado; buscar en los adyacentes
                NodoVertice<String, Integer> verticeAdy;
                NodoAdyacente<String, Integer> adyacente = vertice.getPrimerAdyacente();

                while (adyacente != null) {
                    verticeAdy = adyacente.getVertice();
                    distancia = distanciaRecorrida + adyacente.getEtiqueta();

                    // Continuar buscando caminos solo cuando no supere la distancia máxima
                    if (distancia <= distanciaMax
                            && locaciones.localizar(verticeAdy.getElemento()) < 0) {
                        caminoActual.setDistancia(distancia);
                        caminosHastaDistanciaDesde(verticeAdy, destino, distanciaMax, caminoActual, caminosValidos);
                    }

                    adyacente = adyacente.getSiguienteAdyacente();
                }
            }

            locaciones.eliminar(locaciones.longitud());
        }
    }

    /**
     * Devuelve la representación del mapa en formato CSV (de 7 columnas).
     *
     * @return el mapa en CSV
     */
    public String exportar() {
        StringBuilder cadena = new StringBuilder();
        StringBuilder cadenaCaminos = new StringBuilder();
        String locacionOrigen = null;
        String locacionDestino = null;
        String caminoOpuesto = null;
        NodoVertice<String, Integer> vertice = inicio;
        NodoAdyacente<String, Integer> adyacente;

        while (vertice != null) {
            locacionOrigen = String.valueOf(vertice.getElemento());
            cadena.append(String.format("L:%s;;;;;;\r\n", locacionOrigen));
            adyacente = vertice.getPrimerAdyacente();

            while (adyacente != null) {
                locacionDestino = String.valueOf(adyacente.getVertice().getElemento());
                caminoOpuesto = locacionDestino + ";" + locacionOrigen;

                if (cadenaCaminos.indexOf(caminoOpuesto) == -1) {
                    cadenaCaminos.append("C:").append(locacionOrigen).append(";");
                    cadenaCaminos.append(locacionDestino).append(";");
                    cadenaCaminos.append(String.valueOf(adyacente.getEtiqueta())).append(";;;;\r\n");
                }

                adyacente = adyacente.getSiguienteAdyacente();
            }

            vertice = vertice.getSiguienteVertice();
        }

        return cadena.append(cadenaCaminos.toString()).toString();
    }
}
