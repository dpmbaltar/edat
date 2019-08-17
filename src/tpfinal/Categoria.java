package tpfinal;

/**
 * Categoría de jugador/equipo.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public enum Categoria {

    /**
     * Indica la categoría "Novato".
     */
    NOVATO,

    /**
     * Indica la categoría "Aficionado".
     */
    AFICIONADO,

    /**
     * Indica la categoría "Profesional".
     */
    PROFESIONAL;

    /**
     * Devuelve una categoría desde una cadena.
     *
     * @param cadena la cadena
     * @return la categoría correspondiente
     */
    public static Categoria desdeCadena(String cadena) {
        Categoria categoria;

        switch (cadena.toUpperCase()) {
            case "NOVATO":
                categoria = NOVATO;
                break;
            case "AFICIONADO":
                categoria = AFICIONADO;
                break;
            case "PROFESIONAL":
                categoria = PROFESIONAL;
                break;
            default:
                categoria = null;
        }

        return categoria;
    }

    /**
     * Devuelve una categoría desde un entero.
     *
     * @param entero el entero
     * @return la categoría correspondiente
     */
    public static Categoria desdeEntero(int entero) {
        Categoria categoria;

        switch (entero) {
            case 0:
                categoria = NOVATO;
                break;
            case 1:
                categoria = AFICIONADO;
                break;
            case 2:
                categoria = PROFESIONAL;
                break;
            default:
                categoria = null;
        }

        return categoria;
    }

    /**
     * Devuelve la categoría como cadena.
     */
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
