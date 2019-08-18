package tpfinal;

import conjuntistas.TablaHashAbierto;
import utiles.Funciones;

/**
 * Item.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Item implements Comparable<Item> {

    private static final TablaHashAbierto<String> codigos = new TablaHashAbierto<>();
    private static char letra = 'A';
    private static int secuencia = 0;

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
     * Disponibilidad del ítem (1 indica que es único).
     */
    private int disponibilidad;

    /**
     * Cantidad disponible del ítem.
     */
    private int cantidad;

    /**
     * Constructor vacío.
     */
    public Item() {
        this(null, null, 0, 0, 0, 0);
    }

    /**
     * Constructor con nombre, precio, ataque y defensa.
     *
     * @param codigo el código
     * @param nombre el nombre
     * @param precio el precio
     * @param ataque los puntos de ataque
     * @param defensa los puntos de defensa
     * @param disponibilidad cantidad disponible
     */
    public Item(String codigo, String nombre, int precio, int ataque, int defensa, int disponibilidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.ataque = ataque;
        this.defensa = defensa;
        this.disponibilidad = disponibilidad;
        this.cantidad = disponibilidad;
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

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve verdadero si el ítem es único.
     *
     * @return verdadero si el ítem es único, falso en caso contrario
     */
    public boolean esUnico() {
        return disponibilidad == 1;
    }

    public static String generarCodigo() {
        String codigo = null;

        if (Funciones.esLetraMayus(letra)) {
            do {
                codigo = String.format("%s%03d", letra, secuencia);
                secuencia++;

                if (secuencia > 99) {
                    secuencia = 0;
                    letra++;
                }
            } while (codigos.pertenece(codigo) && Funciones.esLetraMayus(letra));
        }

        codigos.insertar(codigo);

        return codigo;
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
                nuevoItem.disponibilidad = 1;
            }
        }

        return nuevoItem;
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        cadena.append(codigo).append("; ");
        cadena.append(nombre).append("; ");
        cadena.append(precio).append("; ");
        cadena.append(ataque).append("; ");
        cadena.append(defensa).append("; ");

        return cadena.toString();
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
