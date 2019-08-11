package tpfinal;

/**
 * Item.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Item implements Comparable<Item> {

    /**
     * Código alfanumérico del ítem (único).
     */
    private String codigo;

    /**
     * Nombre del ítem.
     */
    private String nombre;

    /**
     * Precio del ítem.
     */
    private double precio;

    /**
     * Puntos de ataque del ítem.
     */
    private double ataque;

    /**
     * Puntos de defensa del ítem.
     */
    private double defensa;

    /**
     * Cantidad de copias disponibles.
     */
    private static double DISPONIBLES = 10;

    /**
     * Constructor con nombre, precio, ataque y defensa.
     *
     * @param codigo el código
     * @param nombre el nombre
     * @param precio el precio
     * @param ataque los puntos de ataque
     * @param defensa los puntos de defensa
     */
    public Item(String codigo, String nombre, double precio, double ataque, double defensa) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.ataque = ataque;
        this.defensa = defensa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getAtaque() {
        return ataque;
    }

    public void setAtaque(double ataque) {
        this.ataque = ataque;
    }

    public double getDefensa() {
        return defensa;
    }

    public void setDefensa(double defensa) {
        this.defensa = defensa;
    }

    @Override
    public int compareTo(Item otroItem) {
        int resultado = 0;

        if (precio < otroItem.getPrecio()) {
            resultado = -1;
        } else if (precio > otroItem.getPrecio()) {
            resultado = 1;
        }

        return resultado;
    }
}
