package pruebas.conjuntistas;

import conjuntistas.ArbolAVL;

/**
 * Prueba implementación de Árbol Binario de Búsqueda.
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
        ArbolAVL<Integer> abb = new ArbolAVL<Integer>();
        assert abb.insertar(8) : "Debe insertar 8 al árbol";
        assert !abb.insertar(8) : "No debe insertar 8 al árbol (elemento existente)";
    }

    private static void pruebaEliminar() {
        ArbolAVL<Integer> abb = crearArbolAVLDePrueba();
        assert abb.eliminar(1) : "Debe eliminar 1 del árbol";
        assert abb.eliminar(5) : "Debe eliminar 5 del árbol";
        assert abb.eliminar(15) : "Debe eliminar 15 del árbol";
        assert abb.eliminar(11) : "Debe eliminar 11 del árbol";
        assert abb.eliminar(8) : "Debe eliminar 8 del árbol";
        assert !abb.eliminar(8) : "No debe eliminar 8 del árbol (elemento inexistente)";
    }

    private static void pruebaPertenece() {
        ArbolAVL<Integer> abb = crearArbolAVLDePrueba();
        assert abb.pertenece(8) : "8 debe pertenecer al árbol";
        assert !abb.pertenece(16) : "16 no debe pertenecer al árbol (elemento inexistente)";
    }

    private static void pruebaMaximo() {
        ArbolAVL<Integer> abb = crearArbolAVLDePrueba();
        assert abb.maximo() == 15 : "15 debe ser el elemento máximo del árbol";
        abb.eliminar(15);
        assert abb.maximo() == 14 : "14 debe ser el elemento máximo del árbol";
    }

    private static void pruebaMinimo() {
        ArbolAVL<Integer> abb = crearArbolAVLDePrueba();
        assert abb.minimo() == 1 : "1 debe ser el elemento mínimo del árbol";
        abb.eliminar(1);
        assert abb.minimo() == 2 : "2 debe ser el elemento mínimo del árbol";
    }

    private static void pruebaVacio() {
        ArbolAVL<Integer> abb = new ArbolAVL<Integer>();
        assert abb.vacio() : "Árbol debe ser vacío";
        abb.insertar(8);
        assert !abb.vacio() : "Árbol no debe ser vacío";
    }

    private static void pruebaVaciar() {
        ArbolAVL<Integer> abb = crearArbolAVLDePrueba();
        abb.vaciar();
        assert abb.vacio() : "Árbol debe ser vacío";
    }

    private static void pruebaListar() {
        ArbolAVL<Integer> abb = crearArbolAVLDePrueba();
        assert abb.listar().toString()
                  .equals("[1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]") : "Debe listar la secuencia de enteros del 1 al 15";
    }

    private static void pruebaListarRango() {
        ArbolAVL<Integer> abb = crearArbolAVLDePrueba();
        assert abb.listarRango(8, 13).toString()
                  .equals("[8 9 10 11 12 13]") : "Debe listar la secuencia de enteros del 8 al 13 (inclusive)";
    }

    private static void pruebaClonar() {
        ArbolAVL<Integer> abb = crearArbolAVLDePrueba(),
                         clon = abb.clonar();
        assert abb.toString().equals(clon.toString()) : "Clon del árbol debe ser una copia exacta del original";
    }

    private static ArbolAVL<Integer> crearArbolAVLDePrueba() {
        ArbolAVL<Integer> abb = new ArbolAVL<Integer>();
        abb.insertar(8);
        abb.insertar(15);
        abb.insertar(4);
        abb.insertar(1);
        abb.insertar(2);
        abb.insertar(11);
        abb.insertar(13);
        abb.insertar(14);
        abb.insertar(12);
        abb.insertar(3);
        abb.insertar(6);
        abb.insertar(9);
        abb.insertar(7);
        abb.insertar(5);
        abb.insertar(10);

        return abb;
    }
}
