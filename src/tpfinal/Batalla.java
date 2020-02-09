package tpfinal;

import lineales.dinamicas.Lista;
import utiles.Funciones;

/**
 * Batalla entre equipos.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Batalla {

    /**
     * Cantidad de rondas por batalla.
     */
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
     * Instancia del juego.
     */
    private Juego juego;

    /**
     * Constructor con el juego y ambos equipos.
     *
     * @param juego el juego
     * @param primerEquipo el 1er equipo
     * @param segundoEquipo el 2do equipo
     */
    public Batalla(Juego juego, Equipo equipo1, Equipo equipo2) {
        // Comienza la batalla el equipo de menor categoría
        if (equipo1.equals(equipo2)) {
            throw new IllegalArgumentException(
                    String.format("El equipo \"%s\" no puede atacarse a si mismo", equipo1.getNombre()));
        } else if (!equipo1.getLocacion().equals(equipo2.getLocacion())) {
            throw new IllegalArgumentException(
                    String.format("Los equipos \"%s\" y \"%s\" deben estar en una misma locación",
                            equipo1.getNombre(), equipo2.getNombre()));
        } else if (equipo1.getCategoria().compareTo(equipo2.getCategoria()) >= 0) {
            this.equipo1 = equipo1;
            this.equipo2 = equipo2;
        } else {
            this.equipo1 = equipo2;
            this.equipo2 = equipo1;
        }

        this.juego = juego;
    }

    /**
     * Inicia la batalla.
     */
    public void iniciar() {
        int derrotadosAtacante = 0;
        int derrotadosAtacado = 0;
        int ronda = 1;
        Equipo ganador = null;
        Jugador jugador1, jugador2;
        Lista<Jugador> jugadores1, jugadores2;

        juego.log(String.format("Se inicia batalla entre %s y %s:", equipo1.getNombre(), equipo2.getNombre()));
        System.out.println();

        // Realizar ataques
        while (derrotadosAtacante < Equipo.CANTIDAD_JUGADORES && derrotadosAtacado < Equipo.CANTIDAD_JUGADORES
                && ronda <= RONDAS) {
            juego.log(String.format("Se inicia la ronda %d:", ronda));
            derrotadosAtacante += atacarEquipo(equipo1, equipo2);

            if (derrotadosAtacante < Equipo.CANTIDAD_JUGADORES) {
                derrotadosAtacado += atacarEquipo(equipo2, equipo1);

                if (derrotadosAtacado < Equipo.CANTIDAD_JUGADORES) {
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
            // Sumar dinero extra por empate a los jugadores
            for (int i = 1; i <= Equipo.CANTIDAD_JUGADORES; i++) {
                jugadores1.recuperar(i).agregarDinero(500);
                jugadores2.recuperar(i).agregarDinero(500);
            }

            juego.log(String.format("La batalla entre \"%s\" y \"%s\" resultó en empate",
                    equipo1.getNombre(), equipo2.getNombre()));
        } else { // Hay un ganador
            jugadores1 = ganador.getJugadores(); // Ganadores
            jugadores2 = ganador.equals(equipo1) ? equipo2.getJugadores() : equipo1.getJugadores(); // Perdedores
            Lista<String> locacionesAdyacentes = juego.getMapa().listarAdyacentes(ganador.getLocacion());

            // Sumar dinero extra al ganador, y quitar la mitad al perdedor
            for (int i = 1; i <= Equipo.CANTIDAD_JUGADORES; i++) {
                jugadores1.recuperar(i).agregarDinero(1000);
                jugadores2.recuperar(i).quitarDinero(500);
            }

            juego.log(String.format("El equipo \"%s\" gana la batalla", ganador.getNombre()));
            Juego.titulo(String.format("Elegir nueva ubicación para el equipo \"%s\"", ganador.getNombre()));

            for (int i = 1; i <= locacionesAdyacentes.longitud(); i++) {
                Juego.opcion(i, locacionesAdyacentes.recuperar(i));
            }

            int opcion = Juego.leerOpcion(1, locacionesAdyacentes.longitud());
            ganador.setLocacion(locacionesAdyacentes.recuperar(opcion));
            juego.log(String.format("El equipo \"%s\" se ha movido a \"%s\"", ganador.getNombre(),
                    ganador.getLocacion()));
        }

        // Reestablecer salud de los jugadores
        equipo1.reestablecerSalud();
        equipo2.reestablecerSalud();
    }

    /**
     * Ejecuta un ataque de un equipo a otro.
     *
     * @param equipoAtacante el equipo atacante
     * @param equipoDefensor el equipo atacado
     * @return la cantidad de jugadores derrotados
     */
    private int atacarEquipo(Equipo equipoAtacante, Equipo equipoDefensor) {
        int jugadoresDerrotados = 0;
        boolean equipoDerrotado = false;
        int i = 1;
        int j = 1;
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
            juego.log(String.format("El equipo \"%s\" fue derrotado", equipoDefensor.getNombre()));
        }

        return jugadoresDerrotados;
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
            juego.log(String.format(
                    "%s (%d/%d) ataca con éxito a %s (%d/%d) causandole %d de daño",
                    atacante.getUsuario(), atacante.getSalud(), atacante.getSaludTotal(),
                    atacado.getUsuario(), atacado.getSalud(), atacado.getSaludTotal(), danio));

            // Oponente derrotado
            if (atacado.getSalud() == 0) {
                derrotado = true;
                juego.log(String.format("El jugador \"%s\" fue derrotado por \"%s\"", atacado.getUsuario(),
                        atacante.getUsuario()));
            }
        } else { // Ataque no exitoso
            juego.log(String.format(
                    "%s (%d/%d) se defiende con éxito de %s (%d/%d)",
                    atacado.getUsuario(), atacado.getSalud(), atacado.getSaludTotal(),
                    atacante.getUsuario(), atacante.getSalud(), atacante.getSaludTotal()));
        }

        return derrotado;
    }

}
