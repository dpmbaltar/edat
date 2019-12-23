package tpfinal;

public class Guerrero extends Jugador {

    public Guerrero(String usuario, TipoJugador tipo, Categoria categoria, int dinero) {
        super(usuario, tipo, categoria, dinero);
    }

    /**
     * Calcula el ataque del jugador.
     *
     * @return el ataque del jugador
     */
    @Override
    public int calcularAtaque() {
        return (100 * multiplicador()) + ataqueItems();
    }

    /**
     * Calcula la defensa del jugador.
     *
     * @return la defensa del jugador
     */
    @Override
    public int calcularDefensa() {
        return (50 * multiplicador()) + defensaItems();
    }

}
