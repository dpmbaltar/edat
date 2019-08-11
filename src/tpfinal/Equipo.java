package tpfinal;

import lineales.dinamicas.Lista;

/**
 * Equipo.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Equipo {

    /**
     * El nombre del equipo (único).
     */
    private String nombre;

    /**
     * La categoría del equipo (definida por el jugador con la categoría más baja).
     */
    private String categoria;

    /**
     * Locación actual.
     */
    private String locacion;

    /**
     * Lista de jugadores.
     */
    private Lista<Jugador> jugadores;

    /**
     * Constructor con el nombre.
     *
     * @param nombre nombre del equipo
     */
    public Equipo(String nombre) {
        this.nombre = nombre;
        this.categoria = null;
        this.locacion = null;
        this.jugadores = new Lista<Jugador>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLocacion() {
        return locacion;
    }

    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }

    public Lista<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(Lista<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
}
