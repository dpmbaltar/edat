package conjuntistas;

import lineales.dinamicas.Lista;
import utiles.Funciones;

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
     * Constructor vacío.
     */
    public TablaHashCerrado() {
        this(TAM);
    }

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

    private int rehash(T elemento) {
        int hashElemento = 0;

        if (elemento instanceof Integer) {
            hashElemento = Funciones.digitosCentrales((Integer) elemento, hash.length);
        } else if (elemento instanceof String) {
            hashElemento = Funciones.sumaCaracteres((String) elemento, hash.length);
            hashElemento = Funciones.digitosCentrales(hashElemento, hash.length);
        } else {
            hashElemento = Funciones.digitosCentrales(elemento.hashCode(), hash.length);
        }

        return hashElemento;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertar(T elemento) {
        boolean insertado = false;
        boolean encontrado = pertenece(elemento);

        if (!encontrado) {
            int posicion = hash(elemento);
            int incremento = rehash(elemento);
            int intento = 1;
            CeldaHash<T> celda = (CeldaHash<T>) hash[posicion];

            while (!insertado && intento < hash.length) {
                if (celda.getEstado() == OCUPADO) {
                    posicion = (posicion + intento * incremento) % hash.length;
                    celda = (CeldaHash<T>) hash[posicion];
                    intento++;
                } else {
                    celda.setElem(elemento);
                    celda.setEstado(OCUPADO);
                    insertado = true;
                }
            }
        }

        return insertado;
    }

    @Override
    public boolean eliminar(T elemento) {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean pertenece(T elemento) {
        boolean encontrado = false;
        int posicion = hash(elemento);
        int incremento = rehash(elemento);
        int intento = 1;
        CeldaHash<T> celda = (CeldaHash<T>) hash[posicion];

        while (!encontrado && intento < hash.length && celda.getEstado() != VACIO) {
            if (celda.getEstado() == OCUPADO && celda.getElem().equals(elemento)) {
                encontrado = true;
            } else {
                posicion = (posicion + intento * incremento) % hash.length;
                celda = (CeldaHash<T>) hash[posicion];
                intento++;
            }
        }

        return encontrado;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Lista<T> listar() {
        Lista<T> lista = new Lista<>();
        CeldaHash<T> celda;

        for (int i = 0; i < hash.length; i++) {
            celda = (CeldaHash<T>) hash[i];

            if (celda.getEstado() == OCUPADO) {
                lista.insertar(celda.getElem(), lista.longitud() + 1);
            }
        }

        return lista;
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
