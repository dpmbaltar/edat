package tpfinal;

import java.util.concurrent.ThreadLocalRandom;

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
    private Categoria categoria;

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
     * @param locacion la locación
     */
    public Equipo(String nombre, String locacion) {
        this.nombre = nombre;
        this.categoria = null;
        this.locacion = locacion;
        this.jugadores = new Lista<Jugador>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
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

    public boolean agregarJugador(Jugador jugador) {
        boolean agregado = false;

        if (jugadores.longitud() < 3) {
            jugadores.insertar(jugador, jugadores.longitud() + 1);

            if (categoria == null || categoria.compareTo(jugador.getCategoria()) < 0) {
                categoria = jugador.getCategoria();
            }
        }

        return agregado;
    }

    public Jugador jugadorAleatorio() {
        Jugador jugador = null;

        if (jugadores.longitud() > 1) {
            jugador = jugadores.recuperar(ThreadLocalRandom.current().nextInt(1, jugadores.longitud()));
        }

        return jugador;
    }
}
