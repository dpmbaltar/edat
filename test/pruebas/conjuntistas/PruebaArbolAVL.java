package pruebas.conjuntistas;

import conjuntistas.ArbolAVL;
import lineales.dinamicas.Lista;

/**
 * Prueba implementación de Árbol AVL.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaArbolAVL {

    public PruebaArbolAVL() {
        pruebaInsertar();
        pruebaEliminar();
        pruebaPertenece();
        pruebaMaximo();
        pruebaMinimo();
        pruebaVacio();
        pruebaVaciar();
        pruebaListar();
        pruebaListarRango();
        pruebaClonar();
        pruebaArbolAVL();
    }

    private static void pruebaInsertar() {
        ArbolAVL<Integer> avl = new ArbolAVL<Integer>();
        assert avl.insertar(8) : "Debe insertar 8 al árbol";
        assert !avl.insertar(8) : "No debe insertar 8 al árbol (ya existe)";
    }

    private static void pruebaEliminar() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        avl.eliminar(10);
        Lista<Lista<Integer>> niveles = avl.listarNiveles2();
        for (int i = 1; i <= niveles.longitud(); i++) {
            System.out.println(niveles.recuperar(i));
        }
        assert avl.eliminar(1) : "Debe eliminar 1 del árbol";
        assert avl.eliminar(5) : "Debe eliminar 5 del árbol";
        assert avl.eliminar(15) : "Debe eliminar 15 del árbol";
        assert avl.eliminar(11) : "Debe eliminar 11 del árbol";
        assert avl.eliminar(8) : "Debe eliminar 8 del árbol";
        assert !avl.eliminar(8) : "No debe eliminar 8 del árbol (inexistente)";
    }

    private static void pruebaPertenece() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        assert avl.pertenece(8) : "8 debe pertenecer al árbol";
        assert !avl.pertenece(16)
                : "16 no debe pertenecer al árbol (inexistente)";
    }

    private static void pruebaMaximo() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        assert avl.maximo() == 15 : "15 debe ser el elemento máximo del árbol";
        avl.eliminar(15);
        assert avl.maximo() == 14 : "14 debe ser el elemento máximo del árbol";
    }

    private static void pruebaMinimo() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        assert avl.minimo() == 1 : "1 debe ser el elemento mínimo del árbol";
        avl.eliminar(1);
        assert avl.minimo() == 2 : "2 debe ser el elemento mínimo del árbol";
    }

    private static void pruebaVacio() {
        ArbolAVL<Integer> avl = new ArbolAVL<Integer>();
        assert avl.vacio() : "Árbol debe ser vacío";
        avl.insertar(8);
        assert !avl.vacio() : "Árbol no debe ser vacío";
    }

    private static void pruebaVaciar() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        avl.vaciar();
        assert avl.vacio() : "Árbol debe ser vacío";
    }

    private static void pruebaListar() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        assert avl.listar().toString()
              .equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]")
              : "Debe listar la secuencia de enteros del 1 al 15";
    }

    private static void pruebaListarRango() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        assert avl.listarRango(8, 13).toString()
              .equals("[8, 9, 10, 11, 12, 13]")
              : "Debe listar la secuencia de enteros del 8 al 13 (inclusive)";
    }

    private static void pruebaClonar() {
        ArbolAVL<Integer> avl = crearArbolAVL(),
                          clon = avl.clonar();
        assert avl.toString().equals(clon.toString())
                : "Clon del árbol debe ser una copia exacta del original";
    }

    protected void pruebaArbolAVL() {
        ArbolAVL<Integer> avl;

        avl = new ArbolAVL<Integer>();
        avl.insertar(10);
        avl.insertar(5);
        avl.insertar(3);
        assert avl.listarNiveles().toString().equals("[5, 3, 10]")
                : "Debe listar [5, 3, 10]";
        // Rotación izquierda
        avl = new ArbolAVL<Integer>();
        avl.insertar(8);
        avl.insertar(5);
        avl.insertar(15);
        avl.insertar(13);
        avl.insertar(20);
        avl.insertar(29);
        assert avl.listarNiveles().toString().equals("[15, 8, 20, 5, 13, 29]")
                : "Debe listar [15, 8, 20, 5, 13, 29]";
        // Rotación derecha
        avl = new ArbolAVL<Integer>();
        avl.insertar(10);
        avl.insertar(5);
        avl.insertar(15);
        avl.insertar(3);
        avl.insertar(7);
        avl.insertar(4);
        assert avl.listarNiveles().toString().equals("[5, 3, 10, 4, 7, 15]")
                : "Debe listar [5, 3, 10, 4, 7, 15]";
        // Rotación derecha-izquierda
        avl = new ArbolAVL<Integer>();
        avl.insertar(10);
        avl.insertar(5);
        avl.insertar(15);
        avl.insertar(12);
        avl.insertar(17);
        avl.insertar(13);
        assert avl.listarNiveles().toString().equals("[12, 10, 15, 5, 13, 17]")
                : "Debe listar [12, 10, 15, 5, 13, 17]";
        // Rotación izquierda-derecha
        avl = new ArbolAVL<Integer>();
        avl.insertar(12);
        avl.insertar(5);
        avl.insertar(23);
        avl.insertar(3);
        avl.insertar(8);
        avl.insertar(10);
        assert avl.listarNiveles().toString().equals("[8, 5, 12, 3, 10, 23]")
                : "Debe listar [8, 5, 12, 3, 10, 23]";
    }

    private static ArbolAVL<Integer> crearArbolAVL() {
        ArbolAVL<Integer> avl = new ArbolAVL<Integer>();
        avl.insertar(8);
        avl.insertar(15);
        avl.insertar(4);
        avl.insertar(1);
        avl.insertar(2);
        avl.insertar(11);
        avl.insertar(13);
        avl.insertar(14);
        avl.insertar(12);
        avl.insertar(3);
        avl.insertar(6);
        avl.insertar(9);
        avl.insertar(5);
        avl.insertar(7);
        avl.insertar(10);
        avl.insertar(16);

        return avl;
    }
}
