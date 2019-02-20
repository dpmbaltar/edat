package lineales.dinamicas;

/**
 * Implementación de Lista dinámica.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class Lista<T> {

    /**
     * El nodo cabecera.
     */
    private Nodo<T> cabecera;

    /**
     * La cantidad de elementos que contiene la lista.
     */
    private int longitud;

    /**
     * Crea y devuelve una lista vacía.
     */
    public Lista() {
        cabecera = null;
        longitud = 0;
    }

    /**
     * Inserta el elemento pasado por parámetro en la posición dada, de manera
     * que la cantidad de elementos de la lista se incrementa en 1. Para una
     * inserción exitosa, la posición recibida debe ser: 1 ≤ posicion ≤
     * longitud(lista) + 1 Devuelve verdadero si se puede insertar correctamente
     * y falso en caso contrario.
     *
     * @param elemento
     * @param posicion
     * @return
     */
    public boolean insertar(T elemento, int posicion) {
        boolean resultado = false;

        if (1 <= posicion && posicion <= (longitud + 1)) {
            Nodo<T> nuevo = new Nodo<T>(elemento);

            if (posicion == 1) {
                nuevo.setEnlace(cabecera);
                cabecera = nuevo;
            } else {
                int pos = 1;
                Nodo<T> aux = cabecera;

                while (pos < (posicion - 1)) {
                    aux = aux.getEnlace();
                    pos++;
                }

                nuevo.setEnlace(aux.getEnlace());
                aux.setEnlace(nuevo);
            }

            longitud++;
            resultado = true;
        }

        return resultado;
    }

    /**
     * Borra el elemento de la posición dada, por lo que la cantidad de
     * elementos de la lista disminuye en uno. Para una eliminación exitosa, la
     * lista no debe estar vacía y la posición recibida debe ser: 1 ≤ posicion ≤
     * longitud(lista) Devuelve verdadero si se puede eliminar correctamente y
     * falso en caso contrario.
     *
     * @param posicion
     * @return
     */
    public boolean eliminar(int posicion) {
        boolean resultado = false;

        if (1 <= posicion && posicion <= longitud) {
            if (posicion == 1) {
                cabecera = cabecera.getEnlace();
            } else {
                int pos = 1;
                Nodo<T> aux = cabecera;

                while (pos < (posicion - 1)) {
                    aux = aux.getEnlace();
                    pos++;
                }

                aux.setEnlace(aux.getEnlace().getEnlace());
            }

            longitud--;
            resultado = true;
        }

        return resultado;
    }

    /**
     * Devuelve el elemento de la posición dada. La precondición es que la
     * posición sea válida.
     *
     * @param posicion
     * @return
     */
    public T recuperar(int posicion) {
        T elemento = null;

        if (1 <= posicion && posicion <= longitud) {
            int pos = 1;
            Nodo<T> nodo = cabecera;

            while (pos < posicion) {
                nodo = nodo.getEnlace();
                pos++;
            }

            elemento = nodo.getElemento();
        }

        return elemento;
    }

    /**
     * Devuelve la posición en la que se encuentra la primera ocurrencia del
     * elemento dado dentro de la lista. En caso de no encontrarlo devuelve -1.
     *
     * @param elemento
     * @return
     */
    public int localizar(T elemento) {
        int posicion = -1;
        int i = 1;
        Nodo<T> nodo = cabecera;
        T actual = null;

        // No utilizar "if": "while" verificará si el nodo cabecera es nulo
        // if (cabecera != null) {
        while (nodo != null && posicion < 0) {
            actual = nodo.getElemento();
            if (actual != null && actual.equals(elemento)) {
                posicion = i;
            } else {
                nodo = nodo.getEnlace();
                i++;
            }
        }
        // }

        return posicion;
    }

    /**
     * Devuelve la cantidad de elementos de la lista.
     *
     * @return
     */
    public int longitud() {
        // Optimización: O(n) -> O(1)
        // int longitud = 0;
        // Nodo<T> nodo = cabecera;
        //
        // while (nodo != null) {
        // nodo = nodo.getEnlace();
        // longitud++;
        // }
        //
        // return longitud;
        return longitud;
    }

    /**
     * Quita todos los elementos de la lista. El manejo de memoria es similar al
     * explicado anteriormente para Cola y Pila dinámicas.
     */
    public void vaciar() {
        cabecera = null;
        longitud = 0;
    }

    /**
     * Devuelve verdadero si la lista no tiene elementos y falso en caso
     * contrario.
     *
     * @return
     */
    public boolean esVacia() {
        return cabecera == null;
    }

    /**
     * Crea una copia exacta de la lista original.
     *
     * @return
     */
    public Lista<T> clonar() {
        int pos = 1;
        Lista<T> clon = new Lista<T>();
        Nodo<T> nodo = cabecera;

        // No utilizar "if": "while" verificará si el nodo cabecera es nulo
        // if (cabecera != null) {
        while (nodo != null) {
            clon.insertar(nodo.getElemento(), pos++);
            nodo = nodo.getEnlace();
        }
        // }

        return clon;
    }

    /**
     * Crea y devuelve una cadena de carácteres formada por todos los elementos
     * de la lista para poder mostrarla por pantalla. Es recomendable utilizar
     * este método únicamente en la etapa de prueba y luego comentar el código.
     */
    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder("[");
        Nodo<T> nodo = cabecera;

        // No utilizar "if": "while" verificará si el nodo cabecera es nulo
        // if (cabecera != null) {
        while (nodo != null) {
            cadena.append(String.valueOf(nodo.getElemento()));
            nodo = nodo.getEnlace();
            if (nodo != null) {
                cadena.append(", ");
            }
        }
        // }

        cadena.append(']');

        return cadena.toString();
    }

}
