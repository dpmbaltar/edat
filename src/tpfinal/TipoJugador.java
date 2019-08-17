package tpfinal;

/**
 * Tipo de jugador.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public enum TipoJugador {

    /**
     * Indica el tipo de jugador "Guerrero".
     */
    GUERRERO,

    /**
     * Indica el tipo de jugador "Defensor".
     */
    DEFENSOR;

    /**
     * Devuelve el tipo de jugador desde una cadena.
     *
     * @param cadena la cadena
     * @return la tipo de jugador correspondiente
     */
    public static TipoJugador desdeCadena(String cadena) {
        TipoJugador tipoJugador;

        switch (cadena.toUpperCase()) {
            case "GUERRERO":
                tipoJugador = GUERRERO;
                break;
            case "DEFENSOR":
                tipoJugador = DEFENSOR;
                break;
            default:
                tipoJugador = null;
        }

        return tipoJugador;
    }

    /**
     * Devuelve el tipo de jugador desde un entero.
     *
     * @param entero el entero
     * @return la tipo de jugador correspondiente
     */
    public static TipoJugador desdeEntero(int entero) {
        TipoJugador tipoJugador;

        switch (entero) {
            case 0:
                tipoJugador = GUERRERO;
                break;
            case 1:
                tipoJugador = DEFENSOR;
                break;
            default:
                tipoJugador = null;
        }

        return tipoJugador;
    }

    /**
     * Devuelve el tipo de jugador como cadena.
     */
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
