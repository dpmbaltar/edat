package grafos.dinamicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 * Implementación de Grafo ponderado no dirigido para elementos de tipo T y etiqueta double.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 *
 * @param <T> el tipo de los elementos
 */
public class GrafoPonderado<T> extends Grafo<T, Double> {

    /**
     * Devuelve una lista de caminos desde origen a destino con una longitud numérica máxima dada.
     *
     * @param origen el origen
     * @param destino el destino
     * @param longitudMaxima la longitud numérica máxima
     * @return la lista de lista de elementos
     */
    public Lista<Lista<String>> caminosHastaLongitud(T origen, T destino, double longitudMaxima) {
        Lista<Lista<String>> caminos = new Lista<>();

        // Evitar buscar caminos de origen igual al destino
        if (!origen.equals(destino)) {
            NodoVertice<T, Double> verticeOrigen = buscarVertice(origen);
            NodoVertice<T, Double> verticeDestino = buscarVertice(destino);

            // Proceder a buscar sólo si existe el origen y destino
            if (verticeOrigen != null && verticeDestino != null) {
                caminosHastaLongitudDesde(verticeOrigen, destino, longitudMaxima, new Camino(), caminos);
            }
        }

        return caminos;
    }

    private void caminosHastaLongitudDesde(
            NodoVertice<T, Double> vertice,
            T destino,
            double distanciaMax,
            Camino caminoActual,
            Lista<Lista<String>> caminosValidos) {
        Lista<T> elementosRecorridos = caminoActual.getElementos();
        double distanciaRecorrida = caminoActual.getLongitud();
        double distancia;

        if (vertice != null) {
            elementosRecorridos.insertar(vertice.getElemento(), elementosRecorridos.longitud() + 1);

            if (vertice.getElemento().equals(destino)) { // Destino encontrado
                if (distanciaRecorrida <= distanciaMax) {
                    caminosValidos.insertar(caminoActual.getElementos().clone(), caminosValidos.longitud() + 1);
                }
            } else { // Destino no encontrado; buscar en los adyacentes
                NodoVertice<T, Double> verticeAdy;
                NodoAdyacente<T, Double> adyacente = vertice.getPrimerAdyacente();

                while (adyacente != null) {
                    verticeAdy = adyacente.getVertice();
                    distancia = distanciaRecorrida + adyacente.getEtiqueta();

                    // Continuar buscando caminos solo cuando no supere la distancia máxima
                    if (distancia <= distanciaMax
                            && elementosRecorridos.localizar(verticeAdy.getElemento()) < 0) {
                        caminoActual.setLongitud(distancia);
                        caminosHastaLongitudDesde(verticeAdy, destino, distanciaMax, caminoActual, caminosValidos);
                    }

                    adyacente = adyacente.getSiguienteAdyacente();
                }
            }

            elementosRecorridos.eliminar(elementosRecorridos.longitud());
        }
    }

    /**
     * Camino más corto según el peso de la etiqueta.
     *
     * @param origen el origen
     * @param destino el destino
     * @return el camino más corto
     */
    public Lista<T> caminoMasCortoEtiqueta2(T origen, T destino) {
        Camino camino = new Camino();
        NodoVertice<T, Double> vertice = buscarVertice(origen);

        if (vertice != null && destino != null) {
            double distancia, distanciaAdy, distanciaPredAdy;
            int posicionPredecesorAdy;
            NodoVertice<T, Double> predecesor, predecesorAdy;
            NodoVertice<T, Double> verticeDestino = null;
            NodoAdyacente<T, Double> adyacente;

            // Estructuras auxiliares
            Lista<T> visitados = new Lista<>();
            Lista<NodoVertice<T, Double>> predecesores = new Lista<>();
            Cola<NodoVertice<T, Double>> cola = new Cola<>();

            // Crear origen con predecesor nulo y distancia 0, y luego visitarlo
            predecesor = new NodoVertice<>(vertice.getElemento());
            predecesor.setPrimerAdyacente(new NodoAdyacente<>(null, null, 0.0));
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
                visitados = camino.getElementos();
                visitados.insertar(verticeDestino.getElemento(), 1);
                adyacente = verticeDestino.getPrimerAdyacente();

                while (adyacente != null) {
                    if (adyacente.getVertice() != null) {
                        distancia += adyacente.getEtiqueta();
                        visitados.insertar(adyacente.getVertice().getElemento(), 1);
                        adyacente = adyacente.getVertice().getPrimerAdyacente();
                    } else {
                        adyacente = null;
                        camino.setLongitud(distancia);
                    }
                }
            }
        }

        return camino.getElementos();
    }

    /**
     * Camino más corto según el peso de la etiqueta.
     *
     * @param origen el origen
     * @param destino el destino
     * @return el camino más corto
     */
    public Lista<T> caminoMasCortoEtiqueta(T origen, T destino) {
        Camino<T> camino = new Camino<>();
        camino.setLongitud(Double.MAX_VALUE);

        // Evitar buscar caminos de origen igual al destino
        if (!origen.equals(destino)) {
            NodoVertice<T, Double> verticeOrigen = buscarVertice(origen);
            NodoVertice<T, Double> verticeDestino = buscarVertice(destino);

            // Proceder a buscar sólo si existe el origen y destino
            if (verticeOrigen != null && verticeDestino != null) {
                caminoMasCortoEtiquetaDesde(verticeOrigen, destino, new Camino<>(), camino);
            }
        }

        return camino.getElementos();
    }

    private void caminoMasCortoEtiquetaDesde(
            NodoVertice<T, Double> vertice,
            T destino,
            Camino caminoActual,
            Camino caminoMasCorto) {
        Lista<T> elementosRecorridos = caminoActual.getElementos();
        double longitudRecorrida = caminoActual.getLongitud();
        double longitud;

        if (vertice != null) {
            elementosRecorridos.insertar(vertice.getElemento(), elementosRecorridos.longitud() + 1);

            // Destino encontrado
            if (vertice.getElemento().equals(destino)
                    && longitudRecorrida < caminoMasCorto.getLongitud()) {
                caminoMasCorto.setLongitud(longitudRecorrida);
                caminoMasCorto.setElementos(caminoActual.getElementos().clone());
            } else { // Destino no encontrado; buscar en los adyacentes
                NodoVertice<T, Double> verticeAdy;
                NodoAdyacente<T, Double> adyacente = vertice.getPrimerAdyacente();

                while (adyacente != null) {
                    verticeAdy = adyacente.getVertice();
                    longitud = longitudRecorrida + adyacente.getEtiqueta();

                    // Continuar buscando caminos solo cuando no supere la distancia mínima encontrada
                    if (longitud < caminoMasCorto.getLongitud()
                            && elementosRecorridos.localizar(verticeAdy.getElemento()) < 0) {
                        caminoActual.setLongitud(longitud);
                        caminoMasCortoEtiquetaDesde(verticeAdy, destino, caminoActual, caminoMasCorto);
                    }

                    adyacente = adyacente.getSiguienteAdyacente();
                }
            }

            elementosRecorridos.eliminar(elementosRecorridos.longitud());
        }
    }

}
