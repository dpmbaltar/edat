package tpfinal;

import lineales.dinamicas.Lista;

/**
 * Representa un camino en el juego.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Camino {

    /**
     * Distancia en kms. entre el origen y el destino.
     */
    private int distancia;

    /**
     * Lista de locaciones del camino.
     */
    private final Lista<String> locaciones;

    /**
     * Constructor con distancia y locaciones.
     *
     * @param distancia la distancia
     * @param locaciones las locaciones
     */
    public Camino(int distancia, Lista<String> locaciones) {
        this.distancia = distancia;
        this.locaciones = locaciones;
    }

    /**
     * Constructor vacío.
     */
    public Camino() {
        this(0, new Lista<>());
    }

    /**
     * Devuelve la distancia en kms. del camino.
     *
     * @return la distancia
     */
    public int getDistancia() {
        return distancia;
    }

    /**
     * Establece la distancia en kms. del camino.
     *
     * @param distancia la distancia
     */
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    /**
     * Devuelve las locaciones del camino.
     *
     * @return las locaciones
     */
    public Lista<String> getLocaciones() {
        return locaciones;
    }

    /**
     * Devuelve el origen del camino.
     *
     * @return la locación de origen
     */
    public String origen() {
        return locaciones.esVacia() ? null : locaciones.recuperar(1);
    }

    /**
     * Devuelve el destino del camino.
     *
     * @return la locación de destino
     */
    public String destino() {
        return locaciones.esVacia() ? null : locaciones.recuperar(locaciones.longitud());
    }

    @Override
    public String toString() {
        return String.format("[%d:%s]", distancia, locaciones.toString());
    }

    @Override
    public Camino clone() {
        return new Camino(distancia, locaciones.clone());
    }
    
}
