package tpo1.test.lineales.dinamicas;

import tpo1.lineales.dinamicas.ColaInt;
import tpo1.lineales.dinamicas.ListaInt;

public class TestSimulacro {
    public static void main(String[] args) {
        pruebaInsertarPromedio();
        pruebaEliminarApariciones();
        System.out.println("Prueba OK");
    }

    public static ColaInt generar(ColaInt c1) {
        ColaInt cola = new ColaInt();



        return cola;
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
