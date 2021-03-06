package tpfinal;

import java.util.Random;

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
     * Categoría (novato, aficionado o profesional).
     */
    private Categoria categoria;

    /**
     * Dinero recolectado.
     */
    private int dinero;

    /**
     * Salud actual del jugador.
     */
    private int salud;

    /**
     * Salud total del jugador.
     */
    private int saludTotal;

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
     * Jugador de igual puesto en el ranking de jugadores.
     */
    private Jugador siguienteIgualPuesto;

    /**
     * Constructor con nombre de usuario, categoría y dinero.
     *
     * @param usuario el nombre de usuario
     * @param categoria la categoría
     * @param dinero el dinero inicial
     */
    public Jugador(String usuario, Categoria categoria, int dinero) {
        this.usuario = usuario;
        this.categoria = categoria;
        this.dinero = dinero;
        this.salud = 500;
        this.saludTotal = 500;
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
    public int getSalud() {
        return salud;
    }

    /**
     * Establece la salud del jugador.
     *
     * @param salud la nueva salud del jugador
     */
    public void setSalud(int salud) {
        this.salud = salud;
    }

    /**
     * Devuelve la salud total del jugador.
     *
     * @return la salud total del jugador
     */
    public int getSaludTotal() {
        return saludTotal;
    }

    /**
     * Establece la salud total del jugador.
     *
     * @param salud la nueva salud total del jugador
     */
    public void setSaludTotal(int saludTotal) {
        this.saludTotal = saludTotal;
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
     * Devuelve el siguiente jugador de igual puesto en el ranking.
     *
     * @return el jugador siguiente
     */
    public Jugador getSiguienteIgualPuesto() {
        return siguienteIgualPuesto;
    }

    /**
     * Establece el siguiente jugador de igual puesto en el ranking.
     *
     * @param siguienteIgualPuesto el jugador siguiente
     */
    public void setSiguienteIgualPuesto(Jugador siguienteIgualPuesto) {
        this.siguienteIgualPuesto = siguienteIgualPuesto;
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
     * Agrega dinero al jugador.
     *
     * @param dinero la cantidad de dinero
     * @return siempre verdadero
     */
    public boolean agregarDinero(int dinero) {
        this.dinero += dinero;
        return true;
    }

    /**
     * Saca dinero del jugador.
     *
     * @param dinero la cantidad de dinero
     * @return verdadero si el dinero pudo ser quitado, falso en caso contrario
     */
    public boolean quitarDinero(int dinero) {
        boolean quitado = false;

        if (this.dinero >= dinero) {
            this.dinero -= dinero;
            quitado = true;
        }

        return quitado;
    }

    /**
     * Intenta comprar un un ítem y devuelve verdadero si lo pudo comprar, falso en caso contrario.
     *
     * @param item el ítem a comprar
     * @return verdadero si pudo comprar el ítem, falso en caso contrario
     */
    public boolean comprarItem(Item item) {
        boolean comprado = false;

        if (item.getCantidadDisponible() > 0 && item.getPrecio() <= dinero) {
            item.disminuirDisponibilidad();
            items.insertar(item, items.longitud() + 1);
            dinero -= item.getPrecio();
            comprado = true;
        }

        return comprado;
    }

    /**
     * Calcula el ataque del jugador.
     *
     * @return el ataque del jugador
     */
    public int calcularAtaque() {
        return (ataqueBase() * multiplicador()) + ataqueItems();
    }

    protected int ataqueBase() {
        return 1;
    }

    protected int multiplicador() {
        return 5 - categoria.ordinal();
        /*int multiplicador = 0;

        switch (categoria) {
            case PROFESIONAL:
                multiplicador = 5;
                break;
            case AFICIONADO:
                multiplicador = 4;
                break;
            case NOVATO:
                multiplicador = 3;
                break;
        }

        return multiplicador;*/
    }

    protected int ataqueItems() {
        int ataque = 0;

        for (int i = 1; i <= items.longitud(); i++) {
            ataque += items.recuperar(i).getAtaque();
        }

        return ataque;
    }

    /**
     * Calcula la defensa del jugador.
     *
     * @return la defensa del jugador
     */
    public int calcularDefensa() {
        return (defensaBase() * multiplicador()) + defensaItems();
    }

    protected int defensaBase() {
       return 1;
    }

    protected int defensaItems() {
        int defensa = 0;

        for (int i = 1; i <= items.longitud(); i++) {
            defensa += items.recuperar(i).getDefensa();
        }

        return defensa;
    }

    /**
     * Ataca a un jugador, aumentando la cantidad de victorias, y la cantidad de derrotas del oponente, si gana.
     *
     * @param oponente el jugador atacado
     * @return un entero indicando el danio causado (positivo), o el daño absorbido (negativo, incluyendo el cero)
     */
    public int atacar(Jugador oponente) {
        int danio;
        double ataque, defensa;
        ataque = calcularAtaque() * coeficienteAtaque();
        defensa = oponente.calcularDefensa();
        danio = (int) (ataque - defensa);

        // Deteriorar items
        deteriorarItems();
        oponente.deteriorarItems();

        // Ataque exitoso
        if (danio > 0) {
            oponente.lastimar(danio);

            // Oponente vencido
            if (oponente.getSalud() == 0) {
                agregarDinero(1000);
                incrementarVictorias();
                oponente.incrementarDerrotas();
            }
        }

        return danio;
    }

    /**
     * Calcula un valor aleatorio entre 0,5 y 1,5.
     *
     * @return el número aleatorio
     */
    private static double coeficienteAtaque() {
        return ((Math.round((new Random()).nextDouble() * 10)) + 5) / 10.0;
    }

    /**
     * Decrementa la salud del jugador según los puntos dados.
     *
     * @param danio la cantidad de salud a quitar
     */
    public void lastimar(int danio) {
        salud = danio > salud ? 0 : salud - danio;
    }

    /**
     * Reduce el ataque y defensa de cada ítem del jugador en 10 puntos.
     */
    public void deteriorarItems() {
        Item item;

        for (int i = 1; i <= items.longitud(); i++) {
            item = items.recuperar(i);
            item.deteriorar(10);

            // Descargar ítems totalmente gastados
            if (item.getAtaque() == 0 && item.getDefensa() == 0) {
                items.eliminar(i);
                i--;
            }
        }
    }

    /**
     * Incrementa la cantidad de victorias en 1.
     */
    public void incrementarVictorias() {
        victorias++;
    }

    /**
     * Incrementa la cantidad de derrotas en 1.
     */
    public void incrementarDerrotas() {
        derrotas++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Jugador other = (Jugador) obj;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        cadena.append(usuario).append(';');
        cadena.append(getClass().getSimpleName()).append(';');
        cadena.append(categoria).append(';');
        cadena.append(dinero).append(';');
        cadena.append('<');

        for (int i = 1; i <= items.longitud(); i++) {
            cadena.append(items.recuperar(i).getCodigo());

            if (i < items.longitud()) {
                cadena.append(',');
            }
        }

        cadena.append('>').append(';');
        cadena.append(victorias).append(';');
        cadena.append(derrotas);

        return cadena.toString();
    }

    @Override
    public int compareTo(Jugador otroJugador) {
        return usuario.compareTo(otroJugador.getUsuario());
    }
}
