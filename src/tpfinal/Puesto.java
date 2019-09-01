package tpfinal;

/**
 * Puesto del Ranking de Jugadores.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Puesto implements Comparable<Puesto> {

    /**
     * El jugador del puesto.
     */
    private Jugador jugador;

    /**
     * Constructor vacío.
     */
    public Puesto() {
        this(null);
    }

    /**
     * Constructor con jugador.
     *
     * @param jugador el jugador
     */
    public Puesto(Jugador jugador) {
        this.jugador = jugador;
    }

    /**
     * Devuelve el jugador del puesto.
     *
     * @return el jugador del puesto
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Establece el jugador del puesto.
     *
     * @param jugador el jugador del puesto
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public boolean equals(Object o) {
        return jugador.equals(((Puesto) o).getJugador());
    }

    @Override
    public int compareTo(Puesto otroPuesto) {
        int resultado = 0;

        if (jugador.getVictorias() < otroPuesto.getJugador().getVictorias()) {
            resultado = 1;
        } else if (jugador.getVictorias() > otroPuesto.getJugador().getVictorias()) {
            resultado = -1;
        }

        return resultado;
    }
}
