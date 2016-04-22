package tpo1.test.lineales.dinamicas;

import tpo1.lineales.dinamicas.ColaInt;
import tpo1.lineales.dinamicas.ListaInt;
import tpo1.lineales.dinamicas.PilaInt;

public class TestSimulacro {
    public static void main(String[] args) {
        pruebaInsertarPromedio();
        pruebaEliminarApariciones();
        pruebaGenerar();
        System.out.println("Prueba OK");
    }

    /**
     * Ejercicio 2, Simulacro, Parcial 1
     * Aclaración: se utiliza enteros en vez de carácteres, por lo tanto se
     * reemplaza '#' por el 0 (cero).
     *
     * @param c1
     * @return
     */
    public static ColaInt generar(ColaInt c1) {
        boolean c1EsVacia = c1.esVacia();
        ColaInt colaAuxiliar, colaFinal = new ColaInt();
        PilaInt pilaAuxiliar;
        int elemento;

        if (!c1EsVacia) {
            colaAuxiliar = new ColaInt();
            pilaAuxiliar = new PilaInt();
            while (!c1EsVacia || !colaAuxiliar.esVacia()) {
                elemento = c1.obtenerFrente();
                c1.sacar();
                c1EsVacia = c1.esVacia();
                if (elemento == 0 || c1EsVacia) {
                    if (c1EsVacia) {
                        colaFinal.poner(elemento);
                        pilaAuxiliar.apilar(elemento);
                        colaAuxiliar.poner(elemento);
                    }
                    while (!pilaAuxiliar.esVacia()) {
                        colaFinal.poner(pilaAuxiliar.obtenerTope());
                        pilaAuxiliar.desapilar();
                    }
                    while (!colaAuxiliar.esVacia()) {
                        colaFinal.poner(colaAuxiliar.obtenerFrente());
                        colaAuxiliar.sacar();
                    }
                    if (!c1EsVacia) {
                        colaFinal.poner(0);
                    }
                } else {
                    colaFinal.poner(elemento);
                    pilaAuxiliar.apilar(elemento);
                    colaAuxiliar.poner(elemento);
                }
            }
        }

        return colaFinal;
    }

    public static void pruebaGenerar() {
        ColaInt cola = new ColaInt();
        String esperado = "[1, 2, 3, 3, 2, 1, 1, 2, 3, 0, 5, 5, 5, 0, 4, 8, 8, 4, 4, 8]";
        cola.poner(1);
        cola.poner(2);
        cola.poner(3);
        cola.poner(0);
        cola.poner(5);
        cola.poner(0);
        cola.poner(4);
        cola.poner(8);
        assert generar(cola).toString().equals(esperado)
             : "Debe generar cola igual a: "+""
             + "[1, 2, 3, 3, 2, 1, 1, 2, 3, 0, 5, 5, 5, 0, 4, 8, 8, 4, 4, 8]";
    }

    public static void pruebaInsertarPromedio() {
        ListaInt lista = new ListaInt();
        lista.insertar(6, 1);
        lista.insertar(7, 2);
        lista.insertar(5, 3);
        lista.insertar(9, 4);
        lista.insertar(8, 5);
        lista.insertarPromedio();
        assert lista.recuperar(6) == 7 : "Debe insertar promedio igual a 7";
    }

    public static void pruebaEliminarApariciones() {
        ListaInt lista = new ListaInt();
        lista.insertar(1, 1);
        lista.insertar(1, 2);
        lista.insertar(2, 3);
        lista.insertar(3, 4);
        lista.insertar(4, 5);
        lista.insertar(5, 6);
        lista.insertar(1, 7);
        lista.insertar(6, 8);
        lista.insertar(7, 9);
        lista.insertar(1, 10);
        lista.insertar(8, 11);
        lista.insertar(1, 12);
        lista.insertar(9, 13);
        lista.insertar(10, 14);
        lista.insertar(11, 15);
        lista.insertar(1, 16);
        assert lista.eliminarApariciones(1) : "Debe eliminar apariciones de 1";
        assert lista.longitud() == 10 : "Longitud debe ser igual a 10";
        assert !lista.eliminarApariciones(0) : "No debe eliminar 0 (inexist.)";
    }
}
