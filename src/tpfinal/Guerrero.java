package tpfinal;

public class Guerrero extends Jugador {

    public Guerrero(String usuario, Categoria categoria, int dinero) {
        super(usuario, categoria, dinero);
    }

    /**
     * Devuelve el ataque base del jugador.
     */
    @Override
    protected int ataqueBase() {
        return 100;
    }

    /**
     * Devuelve la defensa base del jugador.
     */
    @Override
    protected int defensaBase() {
        return 50;
    }

}
