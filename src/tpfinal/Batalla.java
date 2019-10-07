package tpfinal;

import java.util.Random;

import lineales.dinamicas.Lista;

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
     * El 2do equipo (ataca segundo).
     */
    private Equipo equipo2;

    /**
     * Constructor con ambos equipos.
     *
     * @param primerEquipo el 1er equipo
     * @param segundoEquipo el 2do equipo
     */
    public Batalla(Equipo primerEquipo, Equipo segundoEquipo) {
        // Comienza la batalla el equipo de menor categoría
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
        Lista<Jugador> jugadores1 = equipo1.getJugadores();
        Lista<Jugador> jugadores2 = equipo2.getJugadores();
        Jugador jugador1, jugador2;
        double ataque, defensa, danio;

        // Iniciar ataques
        for (int i = 0; i < 3; i++) {
            jugador1 = jugadores1.recuperar(i);
            ataque = jugador1.calcularAtaque() * coeficienteAtaque();
            jugador2 = jugadores2.recuperar(i);
            defensa = jugador2.calcularDefensa();
            danio = ataque - defensa;

            if (danio > 0) {
                // Ataque exitoso
                //jugador1.lastimar(danio);
            } else {
                // Ataque no exitoso
            }
        }
    }

    private void iniciarRonda(Equipo atacante, Equipo atacado) {
        //TODO: iniciarRonda()
    }

    private double coeficienteAtaque() {
        return (Math.round((new Random()).nextDouble() * 10) / 10) + 0.5;
    }

}
