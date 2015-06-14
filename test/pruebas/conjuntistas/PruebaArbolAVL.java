package pruebas.conjuntistas;

import conjuntistas.ArbolAVL;

/**
 * Prueba implementación de Árbol AVL.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaArbolAVL {

    public static void main(String[] args) {
        try {
            // Inicio de pruebas
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
            // Fin de pruebas
            System.out.println("Prueba OK");
        } catch (AssertionError e) {
            System.out.println("Error de prueba: " + e.getMessage());
            System.out.println("Detalles:");
            e.printStackTrace();
        }
    }

    private static void pruebaInsertar() {
        ArbolAVL<Integer> avl = new ArbolAVL<Integer>();
        assert avl.insertar(8) : "Debe insertar 8 al árbol";
        assert !avl.insertar(8) : "No debe insertar 8 al árbol (elemento existente)";
    }

    private static void pruebaEliminar() {
        ArbolAVL<Integer> avl = crearArbolAVLDePrueba();
        assert avl.eliminar(1) : "Debe eliminar 1 del árbol";
        assert avl.eliminar(5) : "Debe eliminar 5 del árbol";
        assert avl.eliminar(15) : "Debe eliminar 15 del árbol";
        assert avl.eliminar(11) : "Debe eliminar 11 del árbol";
        assert avl.eliminar(8) : "Debe eliminar 8 del árbol";
        assert !avl.eliminar(8) : "No debe eliminar 8 del árbol (elemento inexistente)";
    }

    private static void pruebaPertenece() {
        ArbolAVL<Integer> avl = crearArbolAVLDePrueba();
        assert avl.pertenece(8) : "8 debe pertenecer al árbol";
        assert !avl.pertenece(16) : "16 no debe pertenecer al árbol (elemento inexistente)";
    }

    private static void pruebaMaximo() {
        ArbolAVL<Integer> avl = crearArbolAVLDePrueba();
        assert avl.maximo() == 15 : "15 debe ser el elemento máximo del árbol";
        avl.eliminar(15);
        assert avl.maximo() == 14 : "14 debe ser el elemento máximo del árbol";
    }

    private static void pruebaMinimo() {
        ArbolAVL<Integer> avl = crearArbolAVLDePrueba();
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
        ArbolAVL<Integer> avl = crearArbolAVLDePrueba();
        avl.vaciar();
        assert avl.vacio() : "Árbol debe ser vacío";
    }

    private static void pruebaListar() {
        ArbolAVL<Integer> avl = crearArbolAVLDePrueba();
        assert avl.listar().toString()
                  .equals("[1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]") : "Debe listar la secuencia de enteros del 1 al 15";
    }

    private static void pruebaListarRango() {
        ArbolAVL<Integer> avl = crearArbolAVLDePrueba();
        assert avl.listarRango(8, 13).toString()
                  .equals("[8 9 10 11 12 13]") : "Debe listar la secuencia de enteros del 8 al 13 (inclusive)";
    }

    private static void pruebaClonar() {
        ArbolAVL<Integer> avl = crearArbolAVLDePrueba(),
                          clon = avl.clonar();
        assert avl.toString().equals(clon.toString()) : "Clon del árbol debe ser una copia exacta del original";
    }

    private static ArbolAVL<Integer> crearArbolAVLDePrueba() {
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
        avl.insertar(7);
        avl.insertar(5);
        avl.insertar(10);

        return avl;
    }
}
