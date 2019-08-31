package pruebas.conjuntistas;

import conjuntistas.ArbolAVLMultiple;
import java.util.concurrent.ThreadLocalRandom;
import lineales.dinamicas.Lista;

/**
 * Prueba implementación de Árbol AVL Múltiple.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public final class PruebaArbolAVLMultiple {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaArbolAVLMultiple() {
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
        ArbolAVLMultiple<Integer> avl = new ArbolAVLMultiple<>();
        assert avl.insertar(8) : "Debe insertar 8 al árbol";
        assert avl.insertar(8) : "Debe insertar 8 al árbol (duplicado)";
        assert avl.insertar(8) : "Debe insertar 8 al árbol (triplicado)";
        assert !avl.insertar(null) : "No debe insertar nulo al árbol";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#eliminar(Comparable)}.
     */
    public void pruebaEliminar() {
        ArbolAVLMultiple<Integer> avl = crearArbolAVLMultiple();
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
        assert !avl.eliminar(null) : "No debe eliminar nulo del árbol (siempre inexistente)";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#pertenece(Comparable)}.
     */
    public void pruebaPertenece() {
        ArbolAVLMultiple<Integer> avl = crearArbolAVLMultiple();
        assert avl.pertenece(8) : "8 debe pertenecer al árbol";
        assert !avl.pertenece(20) : "20 no debe pertenecer al árbol (inexistente)";
        assert !avl.pertenece(null) : "null no debe pertenecer al árbol (siempre inexistente)";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#maximo()}.
     */
    public void pruebaMaximo() {
        ArbolAVLMultiple<Integer> avl = crearArbolAVLMultiple();
        assert avl.maximo() == 16 : "16 debe ser el elemento máximo del árbol";
        avl.eliminar(16);
        assert avl.maximo() == 15 : "15 debe ser el elemento máximo del árbol";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#minimo()}.
     */
    public void pruebaMinimo() {
        ArbolAVLMultiple<Integer> avl = crearArbolAVLMultiple();
        assert avl.minimo() == 1 : "1 debe ser el elemento mínimo del árbol";
        avl.eliminar(1);
        assert avl.minimo() == 2 : "2 debe ser el elemento mínimo del árbol";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#esVacio()}.
     */
    public void pruebaVacio() {
        ArbolAVLMultiple<Integer> avl = new ArbolAVLMultiple<>();
        assert avl.esVacio() : "Árbol debe ser vacío";
        avl.insertar(8);
        assert !avl.esVacio() : "Árbol no debe ser vacío";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#vaciar()}.
     */
    public void pruebaVaciar() {
        ArbolAVLMultiple<Integer> avl = crearArbolAVLMultiple();
        avl.vaciar();
        assert avl.esVacio() : "Árbol debe ser vacío";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#listar()}.
     */
    public void pruebaListar() {
        ArbolAVLMultiple<Integer> avl = crearArbolAVLMultiple();
        assert avl.listar().toString().equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]")
              : "Debe listar la secuencia de enteros del 1 al 16";
        avl.vaciar();
        avl.insertar(1);
        avl.insertar(2);
        avl.insertar(3);
        avl.insertar(1);
        avl.insertar(3);
        assert avl.listar().toString().equals("[1, 1, 2, 3, 3]")
              : "Debe listar la secuencia de enteros [1, 1, 2, 3, 3]";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#listarRango(Comparable, Comparable)}.
     */
    public void pruebaListarRango() {
        ArbolAVLMultiple<Integer> avl = crearArbolAVLMultiple();
        assert avl.listarRango(8, 13).toString().equals("[8, 9, 10, 11, 12, 13]")
              : "Debe listar la secuencia de enteros del 8 al 13 (inclusive)";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL#clone()}.
     */
    public void pruebaClonar() {
        ArbolAVLMultiple<Integer> avl = crearArbolAVLMultiple();
        ArbolAVLMultiple<Integer> clon = avl.clone();
        assert avl.toString().equals(clon.toString())
                : "Clon del árbol debe ser una copia exacta del original";
    }

    /**
     * Prueba {@link conjuntistas.ArbolAVL} (funcionamiento interno).
     */
    public void pruebaArbolAVL() {
        ArbolAVLMultiple<Integer> avl = new ArbolAVLMultiple<>();
        avl.insertar(10);
        avl.insertar(5);
        avl.insertar(3);

        assert avl.listarNiveles().toString().equals("[5, 3, 10]")
                : "Debe listar [5, 3, 10]";
        assert avl.listar().toString().equals("[3, 5, 10]")
                : "Debe listar [3, 5, 10]";

        // Rotación izquierda
        avl.vaciar();
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
        avl.vaciar();
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
        avl.vaciar();
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
        avl.vaciar();
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

        // Balanceo correcto:
        // Se inserta en el AVL una lista de enteros en orden aleatorio
        // Al listar los enteros del AVL deben estar ordenados
        avl.vaciar();
        Lista<Integer> lista = new Lista<>();

        for (int i = 1; i <= 100; i++) {
            lista.insertar(i, ThreadLocalRandom.current().nextInt(0, lista.longitud() + 1) + 1);
        }

        for (int i = 1; i <= 100; i++) {
            avl.insertar(lista.recuperar(i));
        }

        lista = avl.listar();

        for (int i = 1; i <= 100; i++) {
            assert lista.recuperar(i).equals(i) : "El balanceo del AVL debe ser correcto";
        }
    }

    /**
     * Crea un Árbol AVL Múltiple de prueba.
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
    public static ArbolAVLMultiple<Integer> crearArbolAVLMultiple() {
        ArbolAVLMultiple<Integer> avl = new ArbolAVLMultiple<>();
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
