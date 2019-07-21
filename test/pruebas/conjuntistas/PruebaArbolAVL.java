package pruebas.conjuntistas;

import conjuntistas.ArbolAVL;

/**
 * Prueba implementación de Árbol AVL.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaArbolAVL {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
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

    /**
     * Prueba {@link conjuntistas.ArbolAVL#insertar(Comparable)}.
     */
    public void pruebaInsertar() {
        ArbolAVL<Integer> avl = new ArbolAVL<Integer>();
        assert avl.insertar(8) : "Debe insertar 8 al árbol";
        assert !avl.insertar(8) : "No debe insertar 8 al árbol (ya existe)";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#eliminar(Comparable)}.
     */
    public void pruebaEliminar() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        assert avl.eliminar(1) : "Debe eliminar 1 del árbol";
        assert avl.listar().toString().equals("[2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]")
                : "Debe listar la secuencia de enteros del 1 al 16 sin el 1";
        assert avl.eliminar(5) : "Debe eliminar 5 del árbol";
        assert avl.listar().toString().equals("[2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]")
                : "Debe listar la secuencia de enteros del 2 al 16 sin el 5";
        assert avl.eliminar(15) : "Debe eliminar 15 del árbol";
        assert avl.listar().toString().equals("[2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16]")
                : "Debe listar la secuencia de enteros del 2 al 16 sin el 5 y 15";
        assert avl.eliminar(11) : "Debe eliminar 11 del árbol";
        assert avl.listar().toString().equals("[2, 3, 4, 6, 7, 8, 9, 10, 12, 13, 14, 16]")
                : "Debe listar la secuencia de enteros del 2 al 16 sin el 5, 11 y 15";
        assert avl.eliminar(8) : "Debe eliminar 8 del árbol";
        assert avl.listar().toString().equals("[2, 3, 4, 6, 7, 9, 10, 12, 13, 14, 16]")
                : "Debe listar la secuencia de enteros del 2 al 16 sin el 5, 8, 11 y 15";
        assert !avl.eliminar(8) : "No debe eliminar 8 del árbol (inexistente)";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#pertenece(Comparable)}.
     */
    public void pruebaPertenece() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        assert avl.pertenece(8) : "8 debe pertenecer al árbol";
        assert !avl.pertenece(20) : "20 no debe pertenecer al árbol (inexistente)";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#maximo()}.
     */
    public void pruebaMaximo() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        assert avl.maximo() == 16 : "16 debe ser el elemento máximo del árbol";
        avl.eliminar(16);
        assert avl.maximo() == 15 : "15 debe ser el elemento máximo del árbol";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#minimo()}.
     */
    public void pruebaMinimo() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        assert avl.minimo() == 1 : "1 debe ser el elemento mínimo del árbol";
        avl.eliminar(1);
        assert avl.minimo() == 2 : "2 debe ser el elemento mínimo del árbol";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#vacio()}.
     */
    public void pruebaVacio() {
        ArbolAVL<Integer> avl = new ArbolAVL<Integer>();
        assert avl.vacio() : "Árbol debe ser vacío";
        avl.insertar(8);
        assert !avl.vacio() : "Árbol no debe ser vacío";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#vaciar()}.
     */
    public void pruebaVaciar() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        avl.vaciar();
        assert avl.vacio() : "Árbol debe ser vacío";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#listar()}.
     */
    public void pruebaListar() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        assert avl.listar().toString().equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]")
              : "Debe listar la secuencia de enteros del 1 al 16";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#listarRango(Comparable, Comparable)}.
     */
    public void pruebaListarRango() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        assert avl.listarRango(8, 13).toString().equals("[8, 9, 10, 11, 12, 13]")
              : "Debe listar la secuencia de enteros del 8 al 13 (inclusive)";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#clonar()}.
     */
    public void pruebaClonar() {
        ArbolAVL<Integer> avl = crearArbolAVL();
        ArbolAVL<Integer> clon = avl.clonar();
        assert avl.toString().equals(clon.toString())
                : "Clon del árbol debe ser una copia exacta del original";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL} (funcionamiento interno).
     */
    public void pruebaArbolAVL() {
        ArbolAVL<Integer> avl;
        avl = new ArbolAVL<Integer>();
        avl.insertar(10);
        avl.insertar(5);
        avl.insertar(3);
        assert avl.listarNiveles().toString().equals("[5, 3, 10]")
                : "Debe listar [5, 3, 10]";
        assert avl.listar().toString().equals("[3, 5, 10]")
                : "Debe listar [3, 5, 10]";
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
        assert avl.listar().toString().equals("[5, 8, 13, 15, 20, 29]")
                : "Debe listar [5, 8, 13, 15, 20, 29]";
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
        assert avl.listar().toString().equals("[3, 4, 5, 7, 10, 15]")
                : "Debe listar [3, 4, 5, 7, 10, 15]";
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
        assert avl.listar().toString().equals("[5, 10, 12, 13, 15, 17]")
                : "Debe listar [5, 10, 12, 13, 15, 17]";
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
        assert avl.listar().toString().equals("[3, 5, 8, 10, 12, 23]")
                : "Debe listar [3, 5, 8, 10, 12, 23]";
    }

    /**
     * Crea un Árbol AVL de prueba.
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
     *           ( 4)                (13)
     *           /  \                /  \
     *          /    \              /    \
     *         /      \            /      \
     *      ( 2)      ( 6)      (11)      (15)
     *      /  \      /  \      /  \      /  \
     *   ( 1)  ( 3)( 5)  ( 7)( 9)  (12)(14)  (16)
     *                           \
     *                           (10)
     * </pre>
     *
     * @return el árbol AVL de prueba
     */
    public static ArbolAVL<Integer> crearArbolAVL() {
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
