package tpfinal;

import lineales.dinamicas.Lista;
import utiles.Funciones;

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
        Jugador jugador1, jugador2;
        Lista<Jugador> jugadores1, jugadores2;

        Dungeons2019.log(String.format("Se inicia batalla entre %s y %s:", equipo1.getNombre(), equipo2.getNombre()));

        // Realizar ataques
        while (!ganaAtacante && !ganaAtacado && ronda <= RONDAS) {
            Dungeons2019.log(String.format("Se inicia la ronda %d:", ronda));
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

        // Mostrar estado final
        jugadores1 = equipo1.getJugadores();
        jugadores2 = equipo2.getJugadores();
        System.out.print(Funciones.rellenarIzquierda("Equipo " + equipo1.getNombre(), 30));
        System.out.print(" | ");
        System.out.println(Funciones.rellenarDerecha("Equipo " + equipo2.getNombre(), 30));

        for (int i = 1; i <= 3; i++) {
            jugador1 = jugadores1.recuperar(i);
            jugador2 = jugadores2.recuperar(i);
            System.out.print(Funciones.rellenarIzquierda(
                    String.format("%s (%d/%d)", jugador1.getUsuario(), jugador1.getSalud(), jugador1.getSaludTotal()),
                    30));
            System.out.print(" | ");
            System.out.print(Funciones.rellenarDerecha(
                    String.format("(%d/%d) %s", jugador2.getSalud(), jugador2.getSaludTotal(), jugador2.getUsuario()),
                    30));
            System.out.println();
        }

        // Resultó en empate
        if (ganador == null) {
            Dungeons2019.log(String.format("La batalla entre %s y %s resultó en empate",
                    equipo1.getNombre(), equipo2.getNombre()));
        } else { // Hay un ganador
            Dungeons2019.log(String.format("El equipo %s gana la batalla", ganador.getNombre()));
        }

        equipo1.reestablecerSalud();
        equipo2.reestablecerSalud();
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
                while (!equipoDerrotado && j <= jugadoresDefensores.longitud()) {
                    jugadorDefensor = jugadoresDefensores.recuperar(j);

                    // Atacar jugadores no derrotados
                    if (jugadorDefensor.getSalud() > 0 && atacarJugador(jugadorAtacante, jugadorDefensor)) {
                        jugadoresDerrotados++;
                        equipoDerrotado = jugadoresDerrotados == jugadoresDefensores.longitud();
                    }

                    j++;
                }

                j = 1;
            }

            i++;
        }

        // Indicar que el atacado fue derrotado
        if (equipoDerrotado) {
            Dungeons2019.log(String.format("El equipo %s fue derrotado", equipoDefensor.getNombre()));
        }
        //TODO: los jugadores derrotados deben persistir por ronda
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
        int danio = atacante.atacar(atacado);

        if (danio > 0) { // Ataque exitoso
            Dungeons2019.log(String.format(
                    "Jugador %s (%d/%d) ataca con éxito a %s (%d/%d) causandole %d de daño",
                    atacante.getUsuario(), atacante.getSalud(), atacante.getSaludTotal(),
                    atacado.getUsuario(), atacado.getSalud(), atacado.getSaludTotal(), danio));

            // Oponente derrotado
            if (atacado.getSalud() == 0) {
                derrotado = true;
                Dungeons2019.log(String.format("El jugador %s fue derrotado", atacado.getUsuario()));
            }
        } else { // Ataque no exitoso
            Dungeons2019.log(String.format(
                    "Jugador %s (%d/%d) se defiende con éxito de %s (%d/%d)",
                    atacado.getUsuario(), atacado.getSalud(), atacado.getSaludTotal(),
                    atacante.getUsuario(), atacante.getSalud(), atacante.getSaludTotal()));
        }

        return derrotado;
    }

}
