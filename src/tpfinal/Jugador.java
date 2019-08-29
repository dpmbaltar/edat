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
     * Salud del jugador (0 a 100).
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
     * Constructor con nombre de usuario, categoría y equipo.
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
        this.salud = 100.00;
        this.derrotas = 0;
        this.victorias = 0;
        this.equipo = null;
        this.items = new Lista<Item>();
        this.esperando = false;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public TipoJugador getTipo() {
        return tipo;
    }

    public void setTipo(TipoJugador tipo) {
        this.tipo = tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public double getSalud() {
        return salud;
    }

    public void setSalud(double salud) {
        this.salud = salud;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Lista<Item> getItems() {
        return items;
    }

    public void setItems(Lista<Item> items) {
        this.items = items;
    }

    public void setEsperando(boolean esperando) {
        this.esperando = esperando;
    }

    public boolean tieneEquipo() {
        return equipo != null;
    }

    public boolean esperaEquipo() {
        return esperando;
    }

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
