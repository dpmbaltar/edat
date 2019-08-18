package tpfinal;

import lineales.dinamicas.Lista;

/**
 * Jugador.
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
     * Salud (0 a 100).
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
     * Constructor vacío.
     */
    public Jugador() {
        this(null, null, null, 0);
    }

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

    public boolean tieneEquipo() {
        return equipo != null;
    }

    /**
     * Crea un jugador desde una cadena de acorde al formato:
     * <code>
     * J: usuario;tipo;categoría;dinero;&lt;cód. ítem 1{,cód. ítem 2,...}&gt;salud;victorias;derrotas;equipo
     * </code>
     *
     * @param cadena
     * @return
     */
    public static Jugador crearDesdeCadena(String cadena) {
        Jugador nuevoJugador = null;
        String[] partes = cadena.split(";");

        if (partes.length >= 5) {
            nuevoJugador = new Jugador();
            nuevoJugador.usuario = partes[0].trim();
            nuevoJugador.tipo = TipoJugador.valueOf(partes[1].trim().toUpperCase());
            nuevoJugador.categoria = Categoria.valueOf(partes[2].trim().toUpperCase());

            try {
                nuevoJugador.dinero = Integer.valueOf(partes[3].trim());
            } catch (NumberFormatException e) {}
        }

        return nuevoJugador;
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        cadena.append(usuario).append("; ");
        cadena.append(tipo).append("; ");
        cadena.append(categoria).append("; ");
        cadena.append(dinero).append("; ");
        cadena.append("<");

        for (int i = 1; i <= items.longitud(); i++) {
            cadena.append(items.recuperar(i));
            if (i < items.longitud()) {
                cadena.append(",");
            }
        }

        cadena.append(">");

        return cadena.toString();
    }

    @Override
    public int compareTo(Jugador otroJugador) {
        return usuario.compareTo(otroJugador.getUsuario());
    }
}
