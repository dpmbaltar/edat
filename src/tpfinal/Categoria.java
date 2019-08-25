package tpfinal;

/**
 * Categoría de jugador/equipo.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public enum Categoria {

    /**
     * Indica la categoría "Profesional".
     */
    PROFESIONAL,

    /**
     * Indica la categoría "Aficionado".
     */
    AFICIONADO,

    /**
     * Indica la categoría "Novato".
     */
    NOVATO;

    /**
     * Devuelve una categoría desde una cadena.
     *
     * @param cadena la cadena
     * @return la categoría correspondiente
     */
    public static Categoria desdeCadena(String cadena) {
        Categoria categoria;

        switch (cadena.toUpperCase()) {
            case "PROFESIONAL":
                categoria = PROFESIONAL;
                break;
            case "NOVATO":
                categoria = NOVATO;
                break;
            case "AFICIONADO":
                categoria = AFICIONADO;
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
                categoria = PROFESIONAL;
                break;
            case 1:
                categoria = AFICIONADO;
                break;
            case 2:
                categoria = NOVATO;
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
