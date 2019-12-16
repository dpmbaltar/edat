package tpfinal;

/**
 * Ítem (comparable por precio).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Item implements Comparable<Item> {

    /**
     * Código alfanumérico del ítem, que debe corresponderse unívocamente con la instancia del ítem, pero puede tener
     * copias del mísmo según la cantidad establecida.
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
     * Cantidad del ítem (1 indica que el ítem es único).
     */
    private int cantidad;

    /**
     * Cantidad disponible del ítem.
     */
    private int cantidadDisponible;

    /**
     * Constructor con nombre, precio, ataque, defensa y cantidad.
     *
     * @param codigo el código del ítem
     * @param nombre el nombre del ítem
     * @param precio el precio del ítem
     * @param ataque los puntos de ataque del ítem
     * @param defensa los puntos de defensa del ítem
     * @param cantidad la cantidad total disponible del ítem
     * @param cantidadDisp la cantidad disponible del ítem
     */
    public Item(String codigo, String nombre, int precio, int ataque, int defensa, int cantidad, int cantidadDisp) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.ataque = ataque;
        this.defensa = defensa;
        this.cantidad = cantidad;
        this.cantidadDisponible = cantidadDisp;
    }

    /**
     * Devuelve el código del ítem.
     *
     * @return el código del ítem
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del ítem.
     *
     * @param codigo el nuevo código del ítem
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el nombre del ítem.
     *
     * @return el nombre del ítem
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del ítem.
     *
     * @param nombre el nuevo nombre del ítem
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el precio del ítem.
     *
     * @return el precio del ítem
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del ítem.
     *
     * @param precio el nuevo precio del ítem
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * Devuelve el ataque del ítem.
     *
     * @return el ataque del ítem
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Establece el ataque del ítem.
     *
     * @param ataque el nuevo ataque del ítem
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     * Devuelve la defensa del ítem.
     *
     * @return la defensa del ítem
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * Establece la defensa del ítem.
     *
     * @param defensa la nueva defensa del ítem
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    /**
     * Devuelve la cantidad del ítem.
     *
     * @return la cantidad del ítem
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del ítem.
     *
     * @param cantidad la nueva cantidad del ítem
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve la cantidad disponible.
     *
     * @return la cantidad disponible
     */
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    /**
     * Establece la cantidad disponible.
     *
     * @param cantidadDisponible la nueva cantidad disponible
     */
    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    /**
     * Aumenta en una unidad la cantidad disponible, según la cantidad máxima.
     *
     * @return verdadero si fue aumentada, falso en caso contrario
     */
    public boolean aumentarDisponibilidad() {
        boolean aumentada = false;

        if (cantidadDisponible < cantidad) {
            cantidadDisponible++;
        }

        return aumentada;
    }

    /**
     * Disminuye en una unidad la cantidad disponible, según la cantidad máxima.
     *
     * @return verdadero si fue disminuída, falso en caso contrario
     */
    public boolean disminuirDisponibilidad() {
        boolean disminuida = false;

        if (cantidadDisponible > 0) {
            cantidadDisponible--;
        }

        return disminuida;
    }

    /**
     * Devuelve verdadero si el ítem es único (si cantidad es igual a uno).
     *
     * @return verdadero si el ítem es único, falso en caso contrario
     */
    public boolean esUnico() {
        return cantidad == 1;
    }

    /**
     * Reduce los puntos de ataque y defensa del ítem, según la cantidad dada (el punto límite es cero).
     *
     * @param puntos la cantidad de puntos a disminuir
     */
    public void deteriorar(int puntos) {
        ataque = ataque > puntos ? ataque - puntos : 0;
        defensa = defensa > puntos ? defensa - puntos : 0;
    }

    @Override
    public boolean equals(Object otro) {
        return codigo.equalsIgnoreCase(((Item) otro).getCodigo());
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        cadena.append(codigo).append(';');
        cadena.append(nombre).append(';');
        cadena.append(precio).append(';');
        cadena.append(ataque).append(';');
        cadena.append(defensa).append(';');
        cadena.append(cantidad).append(';');
        cadena.append(cantidadDisponible);

        return cadena.toString();
    }

    @Override
    public int compareTo(Item otroItem) {
        int resultado = 0;

        if (precio < otroItem.precio) {
            resultado = -1;
        } else if (precio > otroItem.precio) {
            resultado = 1;
        }

        return resultado;
    }
}
