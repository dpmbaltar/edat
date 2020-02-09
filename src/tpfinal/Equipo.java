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
     * Cantidad requerida de jugadores.
     */
    public static final int CANTIDAD_JUGADORES = 3;

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

    /**
     * Devuelve el nombre del equipo.
     *
     * @return el nombre del equipo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del equipo.
     *
     * @param nombre el nombre del equipo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la categoría del equipo.
     *
     * @return la categoría del equipo
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del equipo.
     *
     * @param categoria la categoría del equipo
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Devuelve la locación donde se encuentra el equipo.
     *
     * @return la locación donde se encuentra el equipo
     */
    public String getLocacion() {
        return locacion;
    }

    /**
     * Establece la locación donde se encuentra el equipo.
     *
     * @param locacion la locación donde se encuentra el equipo
     */
    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }

    /**
     * Devuelve los jugadores del equipo.
     *
     * @return los jugadores del equipo
     */
    public Lista<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Reestablece la salud de todos los jugadores.
     */
    public void reestablecerSalud() {
        Jugador jugador;

        for (int i = 1; i <= jugadores.longitud(); i++) {
            jugador = jugadores.recuperar(i);
            jugador.setSalud(jugador.getSaludTotal());
        }
    }

    /**
     * Verifica si el equipo fue derrotado en una batalla.
     *
     * @return verdadero si fue derrotado, falso en caso contrario
     */
    public boolean fueDerrotado() {
        int saludJugadores = 0;
        int i = 1;

        while (saludJugadores == 0 && i <= jugadores.longitud()) {
            saludJugadores += jugadores.recuperar(i).getSalud();
            i++;
        }

        return saludJugadores == 0;
    }

    /**
     * Devuelve un jugador aleatorio del equipo.
     *
     * @return el jugador aleatorio
     */
    public Jugador jugadorAleatorio() {
        Jugador jugador = null;

        if (jugadores.longitud() > 1) {
            jugador = jugadores.recuperar(ThreadLocalRandom.current().nextInt(1, jugadores.longitud()));
        }

        return jugador;
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        cadena.append(nombre).append(';');
        cadena.append(categoria).append(';');
        cadena.append(locacion).append(';');
        cadena.append('<');

        for (int i = 1; i <= jugadores.longitud(); i++) {
            cadena.append(jugadores.recuperar(i).getUsuario());

            if (i < jugadores.longitud()) {
                cadena.append(',');
            }
        }

        cadena.append('>');

        return cadena.toString();
    }
}
