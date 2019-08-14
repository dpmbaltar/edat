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
    private int precio;

    /**
     * Puntos de ataque del ítem.
     */
    private int ataque;

    /**
     * Puntos de defensa del ítem.
     */
    private int defensa;

    /**
     * Indica si el ítem es único.
     */
    private boolean unico;

    /**
     * Constructor vacío.
     */
    public Item() {
        this(null, null, 0, 0, 0, false);
    }

    /**
     * Constructor con nombre, precio, ataque y defensa.
     *
     * @param codigo el código
     * @param nombre el nombre
     * @param precio el precio
     * @param ataque los puntos de ataque
     * @param defensa los puntos de defensa
     * @param unico si el ítem es único
     */
    public Item(String codigo, String nombre, int precio, int ataque, int defensa, boolean unico) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.ataque = ataque;
        this.defensa = defensa;
        this.unico = unico;
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

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public double getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public double getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    /**
     * Devuelve verdadero si el ítem es único.
     *
     * @return verdadero si el ítem es único, falso en caso contrario
     */
    public boolean esUnico() {
        return unico;
    }

    /**
     * Crea un ítem desde una cadena de acorde al formato:
     * <code>
     * I: código;nombre;precio;ataque;defensa;disponibilidad
     * </code>
     *
     * @param cadena la cadena representando el ítem
     * @return el ítem si pudo ser creado, nulo en caso contrario
     */
    public static Item crearDesdeCadena(String cadena) {
        Item nuevoItem = null;
        String[] partes = cadena.split(";");

        if (partes.length >= 6) {
            nuevoItem = new Item();
            nuevoItem.codigo = partes[0].trim();
            nuevoItem.nombre = partes[1].trim();

            try {
                nuevoItem.precio = Integer.valueOf(partes[2].trim());
                nuevoItem.ataque = Integer.valueOf(partes[3].trim());
                nuevoItem.defensa = Integer.valueOf(partes[4].trim());
                //TODO: Manejar la disponibilidad del ítem
            } catch (NumberFormatException e) {}

            if (nuevoItem.codigo.charAt(0) == 'U') {
                nuevoItem.unico = true;
            }
        }

        return nuevoItem;
    }

    @Override
    public String toString() {
        return codigo;// + ";" + nombre + ";" + precio + ";" + ataque + ";" + defensa;
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
