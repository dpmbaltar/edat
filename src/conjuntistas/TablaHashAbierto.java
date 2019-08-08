package conjuntistas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Nodo;
import utiles.Funciones;

/**
 * Implementación de Tabla Hash (abierto).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class TablaHashAbierto<T> {

    /**
     * El tamaño máximo de la tabla hash (100 por defecto).
     */
    public static final int TAM = 100;

    /**
     * La cantidad de elementos en la tabla.
     */
    protected int cantidad;

    /**
     * El arreglo de la tabla hash.
     */
    protected Object[] hash;

    /**
     * Constructor vacío.
     */
    public TablaHashAbierto() {
        this(TAM);
    }

    /**
     * Constructor con tamaño de tabla.
     *
     * @param tam el tamaño de la tabla
     */
    public TablaHashAbierto(int tam) {
        hash = new Object[tam > 0 ? tam : TAM];
        cantidad = 0;
    }

    /**
     * Aplica una función hash al elemento dado y devuelve una posición según el tamaño de la tabla.
     *
     * @param elem el elemento al aplicar la función
     * @return la posición para el elemento
     */
    protected int hash(T elemento) {
        int hashElemento = 0;

        if (elemento instanceof Integer) {
            hashElemento = Funciones.doblamiento((Integer) elemento, hash.length);
        } else if (elemento instanceof String) {
            hashElemento = Funciones.sumaCaracteres((String) elemento, hash.length);
        } else {
            hashElemento = elemento.hashCode() % hash.length;
        }

        return hashElemento;
    }

    /**
     * Recibe un elemento e intenta insertarlo en la tabla. Si todo funciona OK (no está repetido y hay lugar
     * suficiente en la tabla) devuelve verdadero, si hay algún problema devuelve falso.
     *
     * @param elemento el elemento a insertar
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    @SuppressWarnings("unchecked")
    public boolean insertar(T elemento) {
        int posicion = hash(elemento);
        boolean encontrado = pertenece(elemento);

        if (!encontrado) {
            hash[posicion] = new Nodo<T>(elemento, (Nodo<T>) hash[posicion]);
            cantidad++;
        }

        return !encontrado;
    }

    /**
     * Recibe el elemento que se desea eliminar y se procede a quitarlo de la tabla. Si todo funciona OK (el
     * elemento estaba cargado previamente en la tabla) devuelve verdadero, si hay algún problema devuelve falso.
     *
     * @param elemento el elemento a eliminar
     * @return verdadero si el elemento fue eliminado, falso en caso contrario
     */
    @SuppressWarnings("unchecked")
    public boolean eliminar(T elemento) {
        boolean eliminado = false;

        if (cantidad > 0) {
            int posicion = hash(elemento);
            Nodo<T> nodo = (Nodo<T>) hash[posicion];
            Nodo<T> nodoAnterior = null;

            while (!eliminado && nodo != null) {
                if (nodo.getElem().equals(elemento)) {
                    if (nodoAnterior == null) {
                        hash[posicion] = nodo.getEnlace();
                    } else {
                        nodoAnterior.setEnlace(nodo.getEnlace());
                    }

                    cantidad--;
                    eliminado = true;
                }

                nodoAnterior = nodo;
                nodo = nodo.getEnlace();
            }
        }

        return eliminado;
    }

    /**
     * Recibe el elemento y devuelve verdadero si ya está cargado en la tabla y falso en caso contrario.
     *
     * @param elemento el elemento a buscar
     * @return verdadero si existe en la tabla, falso en caso contrario
     */
    @SuppressWarnings("unchecked")
    public boolean pertenece(T elemento) {
        boolean encontrado = false;

        if (cantidad > 0) {
            int posicion = hash(elemento);
            Nodo<T> nodo = (Nodo<T>) hash[posicion];

            while (!encontrado && nodo != null) {
                encontrado = nodo.getElem().equals(elemento);
                nodo = nodo.getEnlace();
            }
        }

        return encontrado;
    }

    /**
     * Recorre la tabla completa y devuelve una lista con los elementos que se encuentran almacenados en la tabla.
     * Es útil para mostrar los datos sin depender del dispositivo de salida (consola, ventana, etc).
     *
     * @return la lista de elementos en la tabla
     */
    @SuppressWarnings("unchecked")
    public Lista<T> listar() {
        Lista<T> lista = new Lista<>();
        Nodo<T> nodo = null;
        int posicion = 0;

        while (posicion < hash.length && lista.longitud() < cantidad) {
            nodo = (Nodo<T>) hash[posicion];

            while (nodo != null) {
                lista.insertar(nodo.getElem(), lista.longitud() + 1);
                nodo = nodo.getEnlace();
            }

            posicion++;
        }

        return lista;
    }

    /**
     * Método de prueba. Devuelve una lista de lista de elementos según como fueron agrupados al colisionar.
     *
     * @return la lista de lista de elementos
     */
    @SuppressWarnings("unchecked")
    public Lista<Lista<T>> listarEstructura() {
        Lista<Lista<T>> lista = new Lista<>();
        Nodo<T> nodo = null;
        Lista<T> elems = null;
        int posicion = 0;

        while (posicion < hash.length && lista.longitud() < cantidad) {
            nodo = (Nodo<T>) hash[posicion];
            elems = null;

            while (nodo != null) {
                if (elems == null) {
                    elems = new Lista<>();
                }

                elems.insertar(nodo.getElem(), elems.longitud() + 1);
                nodo = nodo.getEnlace();
            }

            if (elems != null) {
                lista.insertar(elems, lista.longitud() + 1);
            }

            posicion++;
        }

        return lista;
    }

    /**
     * Devuelve falso si hay al menos un elemento cargado en la tabla y verdadero en caso contrario.
     *
     * @return verdadero si la tabla es vacía, falso en caso contrario
     */
    public boolean esVacia() {
        return cantidad == 0;
    }

    /**
     * Elimina todos los elementos de la tabla hash.
     */
    public void vaciar() {
        hash = new Object[hash.length];
        cantidad = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TablaHashAbierto<T> clone() {
        TablaHashAbierto<T> clon = new TablaHashAbierto<>(hash.length);
        Nodo<T> nodo, nodoClon;
        clon.cantidad = cantidad;

        for (int i = 0; i < hash.length; i++) {
            if (hash[i] != null) {
                clon.hash[i] = new Nodo<T>(((Nodo<T>) hash[i]).getElem());
                nodoClon = (Nodo<T>) clon.hash[i];
                nodo = ((Nodo<T>) hash[i]).getEnlace();

                while (nodo != null) {
                    nodoClon.setEnlace(new Nodo<T>(nodo.getElem()));
                    nodoClon = nodoClon.getEnlace();
                    nodo = nodo.getEnlace();
                }
            }
        }

        return clon;
    }

    /**
     * Equivalente a <pre>listar().toString()</pre>.
     */
    @Override
    public String toString() {
        return listar().toString();
    }
}
