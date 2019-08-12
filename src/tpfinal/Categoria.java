package tpfinal;

/**
 * Categoría.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public enum Categoria {

    /**
     * Indica la categoría novato.
     */
    NOVATO,

    /**
     * Indica la categoría aficionado.
     */
    AFICIONADO,

    /**
     * Indica la categoría profesional.
     */
    PROFESIONAL;

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
