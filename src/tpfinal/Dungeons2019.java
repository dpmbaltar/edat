package tpfinal;

import java.util.HashMap;

import conjuntistas.ArbolAVL;

public class Dungeons2019 {

    /**
     * Equipos registrados.
     */
    private HashMap<String, Equipo> equipos;

    /**
     * Jugadores en el juego.
     */
    private ArbolAVL<Jugador> jugadores;

    /**
     * Ítems disponibles en el juego.
     */
    private ArbolAVL items;

    /**
     * El mapa del juego.
     */
    private Mapa mapa;

    /**
     * Programa principal.
     *
     * @param args argumentos
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public Dungeons2019() {
        this.equipos = new HashMap<>();
        this.jugadores = new ArbolAVL<>();
        this.mapa = new Mapa();
    }
}
