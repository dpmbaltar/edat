package tpfinal;

public class Defensor extends Jugador {

    public Defensor(String usuario, TipoJugador tipo, Categoria categoria, int dinero) {
        super(usuario, tipo, categoria, dinero);
    }

    /**
     * Calcula el ataque del jugador.
     *
     * @return el ataque del jugador
     */
    @Override
    public int calcularAtaque() {
        return (25 * multiplicador()) + ataqueItems();
    }

    /**
     * Calcula la defensa del jugador.
     *
     * @return la defensa del jugador
     */
    @Override
    public int calcularDefensa() {
        return (90 * multiplicador()) + defensaItems();
    }

}
