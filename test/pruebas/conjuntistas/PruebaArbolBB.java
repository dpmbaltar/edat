package pruebas.conjuntistas;

import conjuntistas.ArbolBB;

/**
 * Prueba implementación de Árbol Binario de Búsqueda.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaArbolBB {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaArbolBB() {
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
    }

    /**
     * Prueba {@link conjuntistas.ArbolBB#insertar(Comparable)}.
     */
    public void pruebaInsertar() {
        ArbolBB<Integer> abb = new ArbolBB<Integer>();
        assert abb.insertar(8) : "Debe insertar 8 al árbol";
        assert !abb.insertar(8)
                : "No debe insertar 8 al árbol (elemento existente)";
    }

    /**
     * Prueba {@link conjuntistas.ArbolBB#eliminar(Comparable)}.
     */
    public void pruebaEliminar() {
        ArbolBB<Integer> abb = crearArbolBB();
        assert abb.eliminar(1) : "Debe eliminar 1 del árbol";
        assert abb.eliminar(5) : "Debe eliminar 5 del árbol";
        assert abb.eliminar(15) : "Debe eliminar 15 del árbol";
        assert abb.eliminar(11) : "Debe eliminar 11 del árbol";
        assert abb.eliminar(8) : "Debe eliminar 8 del árbol";
        assert !abb.eliminar(8)
                : "No debe eliminar 8 del árbol (elemento inexistente)";
    }

    /**
     * Prueba {@link conjuntistas.ArbolBB#pertenece(Comparable)}.
     */
    public void pruebaPertenece() {
        ArbolBB<Integer> abb = crearArbolBB();
        assert abb.pertenece(8) : "8 debe pertenecer al árbol";
        assert !abb.pertenece(17)
                : "17 no debe pertenecer al árbol (elemento inexistente)";
    }

    /**
     * Prueba {@link conjuntistas.ArbolBB#maximo()}.
     */
    public void pruebaMaximo() {
        ArbolBB<Integer> abb = crearArbolBB();
        assert abb.maximo() == 16 : "16 debe ser el elemento máximo del árbol";
        abb.eliminar(16);
        abb.eliminar(15);
        assert abb.maximo() == 14 : "14 debe ser el elemento máximo del árbol";
    }

    /**
     * Prueba {@link conjuntistas.ArbolBB#minimo()}.
     */
    public void pruebaMinimo() {
        ArbolBB<Integer> abb = crearArbolBB();
        assert abb.minimo() == 1 : "1 debe ser el elemento mínimo del árbol";
        abb.eliminar(1);
        assert abb.minimo() == 2 : "2 debe ser el elemento mínimo del árbol";
    }

    /**
     * Prueba {@link conjuntistas.ArbolBB#vacio()}.
     */
    public void pruebaVacio() {
        ArbolBB<Integer> abb = new ArbolBB<Integer>();
        assert abb.vacio() : "Árbol debe ser vacío";
        abb.insertar(8);
        assert !abb.vacio() : "Árbol no debe ser vacío";
    }

    /**
     * Prueba {@link conjuntistas.ArbolBB#vaciar()}.
     */
    public void pruebaVaciar() {
        ArbolBB<Integer> abb = crearArbolBB();
        abb.vaciar();
        assert abb.vacio() : "Árbol debe ser vacío";
    }

    /**
     * Prueba {@link conjuntistas.ArbolBB#listar()}.
     */
    public void pruebaListar() {
        ArbolBB<Integer> abb = crearArbolBB();
        assert abb.listar().toString().equals(
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]")
                : "Debe listar la secuencia de enteros del 1 al 16";
    }

    /**
     * Prueba {@link conjuntistas.ArbolBB#listarRango(Comparable, Comparable)}.
     */
    public void pruebaListarRango() {
        ArbolBB<Integer> abb = crearArbolBB();
        assert abb.listarRango(8, 13).toString().equals(
                "[8, 9, 10, 11, 12, 13]")
                : "Debe listar la secuencia de enteros del 8 al 13 (inclusive)";
    }

    /**
     * Prueba {@link conjuntistas.ArbolBB#clonar()}.
     */
    public void pruebaClonar() {
        ArbolBB<Integer> abb = crearArbolBB(), clon = abb.clonar();
        assert abb.toString().equals(clon.toString())
                : "Clon del árbol debe ser una copia exacta del original";
    }

    /**
     * Crea un Árbol BB de prueba.
     * El estado final interno correcto según el orden de inserción debe ser:
     * <pre>
     *                     ( 8)
     *                     /  \
     *                    /    \
     *                   /      \
     *                  /        \
     *                 /          \
     *                /            \
     *               /              \
     *              /                \
     *           ( 4)                (15)
     *           /  \                /  \
     *          /    \              /    \
     *         /      \            /      \
     *      ( 1)      ( 6)      (11)      (16)
     *         \      /  \      /  \
     *         ( 2)( 5)  ( 7)(10)  (13)
     *            \          /     /  \
     *            ( 3)    ( 9)  (12)  (14)
     * </pre>
     *
     * @return el árbol BB de prueba
     */
    public static ArbolBB<Integer> crearArbolBB() {
        ArbolBB<Integer> abb = new ArbolBB<Integer>();
        abb.insertar(8);
        abb.insertar(15);
        abb.insertar(4);
        abb.insertar(1);
        abb.insertar(2);
        abb.insertar(11);
        abb.insertar(13);
        abb.insertar(14);
        abb.insertar(12);
        abb.insertar(10);
        abb.insertar(3);
        abb.insertar(6);
        abb.insertar(9);
        abb.insertar(7);
        abb.insertar(5);
        abb.insertar(16);

        return abb;
    }
}
