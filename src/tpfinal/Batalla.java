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
    public Batalla(Equipo equipo1, Equipo equipo2) {
        // Comienza la batalla el equipo de menor categoría
        if (equipo1.getCategoria().compareTo(equipo2.getCategoria()) >= 0) {
            this.equipo1 = equipo1;
            this.equipo2 = equipo2;
        } else {
            this.equipo1 = equipo2;
            this.equipo2 = equipo1;
        }
    }

    /**
     * Inicia la batalla.
     */
    public void iniciar() {
        //TODO: iniciar()
        Lista<Jugador> jugadores1 = equipo1.getJugadores();
        Lista<Jugador> jugadores2 = equipo2.getJugadores();
        int ronda = 0;

        // Iniciar ataques
        for (int i = 1; i <= 3; i++) {
            atacar(jugadores1.recuperar(i), jugadores2.recuperar(i));
            atacar(jugadores2.recuperar(i), jugadores1.recuperar(i));
        }
    }

    /**
     * Ejecuta un ataque de un jugador a otro.
     *
     * @param atacante el jugador atacante
     * @param atacado el jugador atacado
     * @return verdadero si el atacante derrota al atacado, falso en caso contrario
     */
    private boolean atacar(Jugador atacante, Jugador atacado) {
        boolean derrotado = false;
        double ataque, defensa, danio;
        ataque = atacante.calcularAtaque() * coeficienteAtaque();
        defensa = atacado.calcularDefensa();
        danio = ataque - defensa;

        System.out.println(String.format("%s ataca a %s", atacante.getUsuario(), atacado.getUsuario()));
        System.out.println(String.format("%s - ataque: %.2f ", atacante.getUsuario(), ataque));
        System.out.println(String.format("%s - defensa: %.2f ", atacado.getUsuario(), defensa));

        if (danio > 0) { // Ataque exitoso
            System.out.println(String.format("%s ataca con éxito a %s causandole %.2f de daño", atacante.getUsuario(),
                    atacado.getUsuario(), danio));
            //jugador1.lastimar(danio);
        } else { // Ataque no exitoso
            System.out.println(String.format("%s se defiende con éxito de %s absorbiendo %.2f de ataque",
                    atacado.getUsuario(), atacante.getUsuario(), ataque));
        }

        return derrotado;
    }

    /**
     * Calcula un valor aleatorio entre 0,5 y 1,5.
     *
     * @return el número aleatorio
     */
    private double coeficienteAtaque() {
        return (Math.round((new Random()).nextDouble() * 10) / 10) + 0.5;
    }

}
