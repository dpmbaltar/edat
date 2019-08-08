package conjuntistas;

/**
 * Implementación de Tabla Hash (cerrado).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class TablaHashCerrado<T> extends TablaHashAbierto<T> {

    public static final int VACIO = 0;
    public static final int OCUPADO = 1;
    public static final int BORRADO = -1;

    /**
     * Constructor con tamaño de tabla.
     *
     * @param tam el tamaño de la tabla
     */
    public TablaHashCerrado(int tam) {
        super(tam);

        for (int i = 0; i < hash.length; i++) {
            hash[i] = new CeldaHash<>();
        }
    }

    @Override
    public boolean insertar(T elemento) {
        return false;
    }

    @Override
    public boolean eliminar(T elemento) {
        return false;
    }

    @Override
    public boolean pertenece(T elemento) {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TablaHashCerrado<T> clone() {
        TablaHashCerrado<T> clon = new TablaHashCerrado<>(hash.length);
        clon.cantidad = cantidad;

        for (int i = 0; i < hash.length; i++) {
            ((CeldaHash<T>) clon.hash[i]).setElem((T) hash[i]);
            ((CeldaHash<T>) clon.hash[i]).setEstado(((CeldaHash<T>) hash[i]).getEstado());
        }

        return clon;
    }
}
