package tpo1.lineales.dinamicas;

import tpo1.lineales.dinamicas.NodoInt;

/**
 * Implementación de Lista dinámica de enteros.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class ListaInt {

    /**
     * El nodo cabecera.
     */
    private NodoInt cabecera;

    /**
     * La cantidad de elementos que contiene la lista.
     */
    private int longitud;

    /**
     * Crea y devuelve una lista vacía.
     */
    public ListaInt() {
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
    public boolean insertar(int elemento, int posicion) {
        boolean exito = false;

        //Comprobar que la posicion sea válida
        if (1 <= posicion && posicion <= (longitud + 1)) {
            //Insertar elemento de acuerdo a la longitud de la lista
            if (longitud == 0) {
                cabecera = new NodoInt(elemento);
            } else if (longitud >= 1) {
                NodoInt nuevo = new NodoInt(elemento);

                if (posicion == 1) {
                    nuevo.setEnlace(cabecera);
                    cabecera = nuevo;
                } else {
                    int puntero = 1;
                    NodoInt nodo = cabecera;

                    while (puntero < (posicion - 1)) {
                        nodo = nodo.getEnlace();
                        puntero++;
                    }

                    nuevo.setEnlace(nodo.getEnlace());
                    nodo.setEnlace(nuevo);
                }
            }

            longitud++;
            exito = true;
        }

        return exito;
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
        boolean exito = false;

        if (1 <= posicion && posicion <= longitud) {
            if (posicion == 1) {
                cabecera = cabecera.getEnlace();
            } else {
                int puntero = 1;
                NodoInt nodo = cabecera;

                while (puntero < (posicion - 1)) {
                    nodo = nodo.getEnlace();
                    puntero++;
                }

                nodo.setEnlace(nodo.getEnlace().getEnlace());
            }

            longitud--;
            exito = true;
        }

        return exito;
    }

    /**
     * Devuelve el elemento de la posición dada. La precondición es que la
     * posición sea válida.
     *
     * @param posicion
     * @return
     */
    public int recuperar(int posicion) {
        int elemento = 0;

        if (1 <= posicion && posicion <= longitud) {
            int puntero = 1;
            NodoInt nodo = cabecera;

            while (puntero < posicion) {
                nodo = nodo.getEnlace();
                puntero++;
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
    public int localizar(int elemento) {
        int posicion = -1;
        int puntero = 1;
        NodoInt nodo = cabecera;
        int actual = 0;

        // No utilizar "if": "while" verificará si el nodo cabecera es nulo
        //if (cabecera != null) {
        while (nodo != null && posicion < 0) {
            actual = nodo.getElemento();
            if (actual == elemento) {
                posicion = puntero;
            } else {
                nodo = nodo.getEnlace();
                puntero++;
            }
        }
        //}

        return posicion;
    }

    /**
     * Devuelve la cantidad de elementos de la lista.
     *
     * @return
     */
    public int longitud() {
        //(Mantener la longitud en una variable de instancia)
        //int longitud = 0;
        //NodoInt nodo = cabecera;
        //
        //while (nodo != null) {
        //    nodo = nodo.getEnlace();
        //    longitud++;
        //}
        //
        //return longitud;
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
    public ListaInt clonar() {
        int posicion = 1;
        ListaInt clon = new ListaInt();
        NodoInt nodo = cabecera;

        // No utilizar "if": "while" verificará si el nodo cabecera es nulo
        //if (cabecera != null) {
        while (nodo != null) {
            clon.insertar(nodo.getElemento(), posicion++);
            nodo = nodo.getEnlace();
        }
        //}

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
        NodoInt nodo = cabecera;

        // No utilizar "if": "while" verificará si el nodo cabecera es nulo
        //if (cabecera != null) {
        while (nodo != null) {
            cadena.append(String.valueOf(nodo.getElemento()));
            nodo = nodo.getEnlace();
            if (nodo != null) {
                cadena.append(", ");
            }
        }
        //}

        cadena.append(']');

        return cadena.toString();
    }
}
