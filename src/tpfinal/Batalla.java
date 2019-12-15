package tpfinal;

import java.util.Random;

import lineales.dinamicas.Lista;

/**
 * Batalla entre equipos.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Batalla {

    public static final int RONDAS = 2;

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
        boolean ganaAtacante = false;
        boolean ganaAtacado = false;
        int ronda = 1;
        Equipo ganador = null;

        System.out.println(String.format("Batalla entre %s y %s:", equipo1.getNombre(), equipo2.getNombre()));

        // Realizar ataques
        while (!ganaAtacante && !ganaAtacado && ronda <= RONDAS) {
            System.out.println(String.format("Ronda %d:", ronda));
            ganaAtacante = atacarEquipo(equipo1, equipo2);

            if (!ganaAtacante) {
                ganaAtacado = atacarEquipo(equipo2, equipo1);

                if (!ganaAtacado) {
                    ronda++;
                } else {
                    ganador = equipo2;
                }
            } else {
                ganador = equipo1;
            }

            System.out.println();
        }

        // Resultó en empate
        if (ganador == null) {
            System.out.println(String.format("La batalla entre %s y %s resultó en empate",
                    equipo1.getNombre(), equipo2.getNombre()));
        } else { // Hay un ganador
            System.out.println(String.format("El equipo %s gana la batalla", ganador.getNombre()));
        }
    }

    /**
     * Ejecuta un ataque de un equipo a otro.
     *
     * @param equipoAtacante el equipo atacante
     * @param equipoDefensor el equipo atacado
     * @return
     */
    private boolean atacarEquipo(Equipo equipoAtacante, Equipo equipoDefensor) {
        boolean equipoDerrotado = false;
        boolean jugadorDerrotado = false;
        int i = 1;
        int j = 1;
        int jugadoresDerrotados = 0;
        Jugador jugadorAtacante = null;
        Jugador jugadorDefensor = null;
        Lista<Jugador> jugadoresAtacantes = equipoAtacante.getJugadores();
        Lista<Jugador> jugadoresDefensores = equipoDefensor.getJugadores();

        // Atacar a los jugadores no derrotados del equipo defensor
        while (!equipoDerrotado && i <= jugadoresAtacantes.longitud()) {
            jugadorAtacante = jugadoresAtacantes.recuperar(i);

            // Evitar que los jugadores atacantes derrotados participen
            if (jugadorAtacante.getSalud() > 0) {
                while (!equipoDerrotado && j < jugadoresDefensores.longitud()) {
                    jugadorDefensor = jugadoresDefensores.recuperar(j);

                    // Atacar jugadores no derrotados
                    if (jugadorDefensor.getSalud() > 0) {
                        jugadorDerrotado = atacarJugador(jugadorAtacante, jugadorDefensor);
                    }

                    // Verificar si el equipo defensor fue derrotado
                    if (jugadorDerrotado) {
                        jugadorDerrotado = false;
                        jugadoresDerrotados++;
                        equipoDerrotado = jugadoresDerrotados == jugadoresDefensores.longitud();

                        if (equipoDerrotado) {
                            System.out.println(String.format("El equipo %s fue derrotado", equipoDefensor.getNombre()));
                        }
                    }

                    j++;
                }
            }

            i++;
        }

        return equipoDerrotado;
    }

    /**
     * Ejecuta un ataque de un jugador a otro.
     *
     * @param atacante el jugador atacante
     * @param atacado el jugador atacado
     * @return verdadero si el atacante derrota al atacado, falso en caso contrario
     */
    private boolean atacarJugador(Jugador atacante, Jugador atacado) {
        boolean derrotado = false;
        double ataque, defensa, danio;
        ataque = atacante.calcularAtaque() * coeficienteAtaque();
        defensa = atacado.calcularDefensa();
        danio = ataque - defensa;

        System.out.println(String.format("%s ataca a %s", atacante, atacado));
        System.out.println(String.format("%s - ataque: %.2f ", atacante.getUsuario(), ataque));
        System.out.println(String.format("%s - defensa: %.2f ", atacado.getUsuario(), defensa));

        if (danio > 0) { // Ataque exitoso
            atacado.lastimar((int) danio);
            System.out.println(String.format("Jugador %s (%d/%d) ataca con éxito a %s (%d/%d) causandole %.2f de daño",
                    atacante.getUsuario(), atacante.getSalud(), atacante.getSaludTotal(),
                    atacado.getUsuario(), atacado.getSalud(), atacado.getSaludTotal(), danio));

            if (atacado.getSalud() == 0) {
                derrotado = true;
                System.out.println(String.format("El jugador %s fue derrotado", atacado.getUsuario()));
            }
        } else { // Ataque no exitoso
            System.out.println(String.format(
                    "Jugador %s (%d/%d) se defiende con éxito de %s (%d/%d) absorbiendo %.2f de ataque",
                    atacado.getUsuario(), atacado.getSalud(), atacado.getSaludTotal(),
                    atacante.getUsuario(), atacante.getSalud(), atacante.getSaludTotal(), ataque));
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
