package tpfinal;

/**
 * Batalla entre equipos.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Batalla {

    /**
     * El 1er equipo (ataca primero).
     */
    private Equipo equipo1;

    /**
     * El 2do equipo (ataque segundo).
     */
    private Equipo equipo2;

    /**
     * Constructor con ambos equipos.
     *
     * @param primerEquipo el 1er equipo
     * @param segundoEquipo el 2do equipo
     */
    public Batalla(Equipo primerEquipo, Equipo segundoEquipo) {
        if (equipo1.getCategoria().compareTo(equipo2.getCategoria()) >= 0) {
            this.equipo1 = primerEquipo;
            this.equipo2 = segundoEquipo;
        } else {
            this.equipo1 = segundoEquipo;
            this.equipo2 = primerEquipo;
        }
    }

    /**
     * Inicia la batalla.
     */
    public void iniciar() {
        //TODO: iniciar()
        Jugador jugador1 = equipo1.jugadorAleatorio();
    }
}
