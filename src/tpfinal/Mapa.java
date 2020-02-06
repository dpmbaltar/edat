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

    public boolean modificarVertice(String locacion, String nuevaLocacion) {
        boolean modificado = false;
        NodoVertice<String, Integer> vertice = buscarVertice(locacion);

        if (vertice != null) {
            vertice.setElemento(nuevaLocacion);
            modificado = true;
        }

        return modificado;
    }

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
    public Lista<String> caminoMasCortoKms(String origen, String destino) {
        Lista<String> camino = new Lista<>();
        NodoVertice<String, Integer> vertice = buscarVertice(origen);
        //TODO: caminoMasCortoKms() realizar pruebas y adaptar impl.
        if (vertice != null && destino != null) {

            // Agregar adyacente temporal
            // Apunta al nodo predecesor con menor distancia en kms
            NodoVertice<String, Integer> v = inicio;
            while (v != null) {
                v.setPrimerAdyacente(new NodoAdyacente<>(null, v.getPrimerAdyacente(), null));
                v = v.getSiguienteVertice();
            }

            NodoVertice<String, Integer> verticeDestino = null;
            NodoAdyacente<String, Integer> adyacente;
            Cola<NodoVertice<String, Integer>> cola = new Cola<>();
            Lista<NodoVertice<String, Integer>> visitados = new Lista<>();
            cola.poner(vertice);
            visitados.insertar(vertice, visitados.longitud() + 1);
            vertice.getPrimerAdyacente().setEtiqueta(0);

            while (!cola.esVacia()) {
                vertice = cola.obtenerFrente();
                cola.sacar();
                adyacente = vertice.getPrimerAdyacente();
                int distancia = adyacente.getEtiqueta();
                adyacente = adyacente.getSiguienteAdyacente();

                while (adyacente != null) {
                    if (visitados.localizar(adyacente.getVertice()) < 0) {
                        visitados.insertar(vertice, visitados.longitud() + 1);
                        cola.poner(adyacente.getVertice());
                        int distanciaTotal = adyacente.getEtiqueta() + distancia;

                        // Actualizar predecesor y distancia si es necesario
                        if (adyacente.getVertice().getPrimerAdyacente().getEtiqueta() == null
                                || adyacente.getVertice().getPrimerAdyacente().getEtiqueta() > distanciaTotal) {
                            adyacente.getVertice().getPrimerAdyacente().setVertice(vertice);
                            adyacente.getVertice().getPrimerAdyacente().setEtiqueta(distanciaTotal);
                        }

                        // Obtener vertice destino
                        if (adyacente.getVertice().getElemento().equals(destino)) {
                            verticeDestino = adyacente.getVertice();
                        }
                    }

                    adyacente = adyacente.getSiguienteAdyacente();
                }
            }

            // Obtener camino
            if (verticeDestino != null) {
                camino.insertar(verticeDestino.getElemento(), 1);
                adyacente = verticeDestino.getPrimerAdyacente();
                while (adyacente != null) {
                    if (adyacente.getVertice() != null) {
                        camino.insertar(adyacente.getVertice().getElemento(), 1);
                        adyacente = adyacente.getVertice().getPrimerAdyacente();
                    } else {
                        adyacente = null;
                    }
                }
            }

            // Sacar adyacente temporal
            v = inicio;
            while (v != null) {
                v.setPrimerAdyacente(v.getPrimerAdyacente().getSiguienteAdyacente());
                v = v.getSiguienteVertice();
            }
        }

        return camino;
    }

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
