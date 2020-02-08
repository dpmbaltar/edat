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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Puesto other = (Puesto) obj;
        if (jugador == null) {
            if (other.jugador != null)
                return false;
        } else if (!jugador.equals(other.jugador))
            return false;
        return true;
    }

    /**
     * Compara dos jugadores de manera tal que, en el ranking, el jugador con más victorias está más a la izquierda, y
     * el de menos victorias a la más a la derecha. Si tienen la misma cantidad de victorias, se toman en cuenta la
     * cantidad de derrotas, siendo el que menos derrotas tiene queda más a la izquierda.
     */
    @Override
    public int compareTo(Puesto otroPuesto) {
        int resultado = 0;

        if (jugador.getVictorias() < otroPuesto.getJugador().getVictorias()) {
            resultado = 1;
        } else if (jugador.getVictorias() > otroPuesto.getJugador().getVictorias()) {
            resultado = -1;
        } else {
            if (jugador.getDerrotas() > otroPuesto.getJugador().getDerrotas()) {
                resultado = 1;
            } else if (jugador.getDerrotas() < otroPuesto.getJugador().getDerrotas()) {
                resultado = -1;
            }
        }

        return resultado;
    }
}
