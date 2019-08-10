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

    /**
     * Indica el estado de celda vacía.
     */
    public static final int VACIO = 0;

    /**
     * Indica el estado de celda ocupada.
     */
    public static final int OCUPADO = 1;

    /**
     * Indica el estado de celda borrada.
     */
    public static final int BORRADO = -1;

    /**
     * Indica el tamaño máximo de la tabla (con el método utilizado debe ser primo).
     */
    public static final int TAM = 31;

    /**
     * Indica el primo máximo menor que el tamaño de la tabla.
     */
    public static final int PRIMO = 29;

    /**
     * Constructor vacío.
     */
    public TablaHashCerrado() {
        super();

        for (int i = 0; i < hash.length; i++) {
            hash[i] = new CeldaHash<>();
        }
    }

    /**
     * Método para rehashing doble: para un funcionamiento óptimo, la constantes TAM y PRIMO deben ser primos, ya
     * que de esta forma se cubren todas las posiciones posibles dentro de la tabla en caso de colisiones.
     *
     * @see utiles.Funciones#modPrimo(int, int, int)
     * @param elemento el elemento
     * @return el resultado
     */
    private int rehash(T elemento) {
        int clave = 0;

        if (elemento instanceof Integer) {
            clave = (Integer) elemento;
        } else if (elemento instanceof String) {
            clave = Funciones.sumaCaracteres((String) elemento, hash.length);
        } else {
            clave = elemento.hashCode();
        }

        return Funciones.modPrimo(clave, PRIMO, hash.length);
    }

    /**
     * Verifica si la tabla esta llena.
     *
     * @return devuelve verdadero si la tabla está llena, falso en caso contrario
     */
    public boolean estaLlena() {
        return cantidad == hash.length;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertar(T elemento) {
        boolean insertado = false;

        if (!estaLlena() && !pertenece(elemento)) {
            int posicion = hash(elemento);
            int incremento = rehash(elemento);
            int intento = 1;
            CeldaHash<T> celda = (CeldaHash<T>) hash[posicion];

            while (!insertado && intento < hash.length) {
                if (celda.getEstado() == OCUPADO) {
                    int nuevaPosicion = (posicion + intento * incremento) % hash.length;
                    celda = (CeldaHash<T>) hash[nuevaPosicion];
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

    @SuppressWarnings("unchecked")
    @Override
    public boolean eliminar(T elemento) {
        boolean encontrado = false;
        int posicion = hash(elemento);
        int incremento = rehash(elemento);
        int intento = 1;
        CeldaHash<T> celda = (CeldaHash<T>) hash[posicion];

        while (!encontrado && intento < hash.length && celda.getEstado() != VACIO) {
            if (celda.getEstado() == OCUPADO) {
                encontrado = celda.getElem().equals(elemento);

                if (encontrado) {
                    celda.setEstado(BORRADO);
                    cantidad--;
                }
            }

            posicion = (posicion + (intento * incremento)) % hash.length;
            celda = (CeldaHash<T>) hash[posicion];
            intento++;
        }

        return encontrado;
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
            }

            int nuevaPosicion = (posicion + intento * incremento) % hash.length;
            celda = (CeldaHash<T>) hash[nuevaPosicion];
            intento++;
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
        TablaHashCerrado<T> clon = new TablaHashCerrado<>();
        clon.cantidad = cantidad;

        for (int i = 0; i < hash.length; i++) {
            ((CeldaHash<T>) clon.hash[i]).setElem((T) ((CeldaHash<Object>) hash[i]).getElem());
            ((CeldaHash<T>) clon.hash[i]).setEstado(((CeldaHash<T>) hash[i]).getEstado());
        }

        return clon;
    }
}
