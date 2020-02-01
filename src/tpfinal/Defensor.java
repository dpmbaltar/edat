package tpfinal;

public class Defensor extends Jugador {

    public Defensor(String usuario, Categoria categoria, int dinero) {
        super(usuario, categoria, dinero);
    }

    /**
     * Devuelve el ataque base del jugador.
     */
    @Override
    protected int ataqueBase() {
        return 25;
    }

    /**
     * Devuelve la defensa base del jugador.
     */
    @Override
    protected int defensaBase() {
        return 90;
    }

}
