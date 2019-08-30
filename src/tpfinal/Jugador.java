package tpfinal;

import lineales.dinamicas.Lista;

/**
 * Jugador (comparable por nombre de usuario).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Jugador implements Comparable<Jugador> {

    /**
     * Nombre de usuario.
     */
    private String usuario;

    /**
     * Tipo de jugador (guerrero o defensor).
     */
    private TipoJugador tipo;

    /**
     * Categoría (novato, aficionado o profesional).
     */
    private Categoria categoria;

    /**
     * Dinero recolectado.
     */
    private int dinero;

    /**
     * Salud del jugador (0% a 100%).
     */
    private double salud;

    /**
     * Cantidad de veces que el personaje ha sido derrotado.
     */
    private int derrotas;

    /**
     * Cantidad de batallas individuales ganadas.
     */
    private int victorias;

    /**
     * Equipo al que pertenece.
     */
    private Equipo equipo;

    /**
     * Lista de ítems adquiridos por el jugador.
     */
    private Lista<Item> items;

    /**
     * Si el jugador espera por un equipo.
     */
    private boolean esperando;

    /**
     * Constructor con nombre de usuario, tipo, categoría y dinero.
     *
     * @param usuario el nombre de usuario
     * @param tipo el tipo de jugador
     * @param categoria la categoría
     * @param dinero el dinero inicial
     */
    public Jugador(String usuario, TipoJugador tipo, Categoria categoria, int dinero) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.categoria = categoria;
        this.dinero = dinero;
        this.salud = 1.00;
        this.derrotas = 0;
        this.victorias = 0;
        this.equipo = null;
        this.items = new Lista<Item>();
        this.esperando = false;
    }

    /**
     * Devuelve el nombre de usuario.
     *
     * @return el nombre de usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param usuario el nuevo nombre de usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Devuelve el tipo de jugador.
     *
     * @return el tipo de jugador
     */
    public TipoJugador getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de jugador.
     *
     * @param tipo el nuevo tipo de jugador
     */
    public void setTipo(TipoJugador tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve la categoría del jugador.
     *
     * @return la categoría del jugador
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del jugador.
     *
     * @param categoria la nueva categoría del jugador
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Devuelve el dinero del jugador.
     *
     * @return el dinero del jugador
     */
    public int getDinero() {
        return dinero;
    }

    /**
     * Establece el dinero del jugador.
     *
     * @param dinero el nuevo dinero del jugador
     */
    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    /**
     * Devuelve la salud del jugador.
     *
     * @return la salud del jugador
     */
    public double getSalud() {
        return salud;
    }

    /**
     * Establece la salud del jugador.
     *
     * @param salud la nueva salud del jugador
     */
    public void setSalud(double salud) {
        this.salud = salud;
    }

    /**
     * Devuelve la cantidad de derrotas del jugador.
     *
     * @return la cantidad de derrotas del jugador
     */
    public int getDerrotas() {
        return derrotas;
    }

    /**
     * Establece la cantidad de derrotas del jugador.
     *
     * @param derrotas la nueva cantidad de derrotas del jugador
     */
    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    /**
     * Devuelve la cantidad de victorias del jugador.
     *
     * @return la cantidad de victorias del jugador
     */
    public int getVictorias() {
        return victorias;
    }

    /**
     * Establece la cantidad de victorias del jugador.
     *
     * @param victorias la nueva cantidad de victorias del jugador
     */
    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    /**
     * Devuelve el equipo del jugador.
     *
     * @return el equipo del jugador
     */
    public Equipo getEquipo() {
        return equipo;
    }

    /**
     * Establece el equipo del jugador.
     *
     * @param equipo el nuevo equipo del jugador
     */
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    /**
     * Devuelve los ítems del jugador.
     *
     * @return los ítems del jugador
     */
    public Lista<Item> getItems() {
        return items;
    }

    /**
     * Establece los ítems del jugador.
     *
     * @param items los nuevos ítems del jugador
     */
    public void setItems(Lista<Item> items) {
        this.items = items;
    }

    /**
     * Devuelve verdadero si el jugador está en espera, falso en caso contrario.
     *
     * @return verdadero si el jugador está en espera, falso en caso contrario
     */
    public boolean getEsperando() {
        return esperando;
    }

    /**
     * Establece si el jugador está en espera o no.
     *
     * @param esperando verdadero si el jugador está en espera, falso en caso contrario
     */
    public void setEsperando(boolean esperando) {
        this.esperando = esperando;
    }

    /**
     * Devuelve verdadero si el jugador tiene equipo, falso en caso contrario.
     *
     * @return verdadero si el jugador tiene equipo, falso en caso contrario
     */
    public boolean tieneEquipo() {
        return equipo != null;
    }

    /**
     * Calcula el ataque del jugador.
     *
     * @return el ataque del jugador
     */
    public int calcularAtaque() {
        return (ataqueBase() * ataqueCategoria()) + ataqueItems();
    }

    private int ataqueBase() {
        int ataque = 0;

        switch (tipo) {
            case GUERRERO:
                ataque += 100;
                break;
            case DEFENSOR:
                ataque += 25;
                break;
        }

        return ataque;
    }

    private int ataqueCategoria() {
        int ataque = 0;

        switch (categoria) {
            case PROFESIONAL:
                ataque *= 5;
                break;
            case AFICIONADO:
                ataque *= 4;
                break;
            case NOVATO:
                ataque *= 3;
                break;
        }

        return ataque;
    }

    private int ataqueItems() {
        int ataque = 0;

        for (int i = 1; i <= items.longitud(); i++) {
            ataque += items.recuperar(i).getAtaque();
        }

        return ataque;
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        cadena.append(usuario).append(';');
        cadena.append(tipo).append(';');
        cadena.append(categoria).append(';');
        cadena.append(dinero).append(';');
        cadena.append('<');

        for (int i = 1; i <= items.longitud(); i++) {
            cadena.append(items.recuperar(i).getCodigo());

            if (i < items.longitud()) {
                cadena.append(',');
            }
        }

        cadena.append('>');

        return cadena.toString();
    }

    @Override
    public int compareTo(Jugador otroJugador) {
        return usuario.compareTo(otroJugador.getUsuario());
    }
}
