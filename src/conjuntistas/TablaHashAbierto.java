package conjuntistas;

import lineales.dinamicas.Lista;

/**
 * Implementación de Tabla Hash.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class TablaHashAbierto<T> {

    /**
     * El tamaño máximo de la tabla hash (32 por defecto).
     */
    public static final int TAM = 32;

    /**
     * La cantidad de elementos en la tabla.
     */
    private int cantidad;

    /**
     * El arreglo de la tabla hash.
     */
    private Object[] tabla;

    /**
     * Constructor vacío.
     */
    public TablaHashAbierto() {
        tabla = new Object[TAM];
        cantidad = 0;
    }

    private int hash(T elemento) {
        int hashElemento = 0;

        if (elemento instanceof Integer) {
            hashElemento = hashEntero((Integer) elemento);
        } else if (elemento instanceof String) {
            hashElemento = hashCadena((String) elemento);
        } else {
            hashElemento = elemento.hashCode() % tabla.length;
        }

        return hashElemento;
    }

    private int hashEntero(Integer elemento) {
        return 0;
    }

    private int hashCadena(String elemento) {
        return 0;
    }

    public boolean insertar(T elemento) {
        return false;
    }

    public boolean eliminar(T elemento) {
        return false;
    }

    public boolean pertenece(T elemento) {
        return false;
    }

    public Lista<T> listar() {
        return new Lista<>();
    }

    public boolean esVacio() {
        return false;
    }

    /**
     * Elimina todos los elementos de la tabla hash.
     */
    @SuppressWarnings("unchecked")
    public void vaciar() {
        tabla = new Object[TAM];
        cantidad = 0;
    }

    @Override
    public TablaHashAbierto<T> clone() {
        return new TablaHashAbierto<>();
    }
}
