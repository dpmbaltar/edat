package pruebas.conjuntistas;

import conjuntistas.Diccionario;

/**
 * Prueba implementación de Diccionario (impl. como Árbol AVL).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaDiccionario {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaDiccionario() {
        pruebaInsertar();
        pruebaEliminar();
        pruebaExisteClave();
        pruebaObtenerInformacion();
        pruebaVacio();
        pruebaVaciar();
        pruebaListarClaves();
        pruebaListarDatos();
        pruebaClonar();
//        pruebaDiccionario();
    }

    /**
     * Prueba {@link conjuntistas.Diccionario#insertar(Comparable, Object)}.
     */
    public void pruebaInsertar() {
        Diccionario<Integer, String> dicc = new Diccionario<>();
        assert dicc.insertar(8, "Ocho") : "Debe insertar 8-Ocho al diccionario";
        assert !dicc.insertar(8, "Ocho") : "No debe insertar 8-Ocho al diccionario (ya existe)";
    }

    /**
     * Prueba {@link conjuntistas.Diccionario#eliminar(Comparable)}.
     */
    public void pruebaEliminar() {
        Diccionario<Integer, String> dicc = crearDiccionario();
        assert dicc.eliminar(1) : "Debe eliminar 1 del diccionario";
        assert dicc.listarClaves().toString().equals("[2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]")
                : "Debe listar la secuencia de enteros del 1 al 16 sin el 1";
        assert dicc.eliminar(5) : "Debe eliminar 5 del diccionario";
        assert dicc.listarClaves().toString().equals("[2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]")
                : "Debe listar la secuencia de enteros del 2 al 16 sin el 5";
        assert dicc.eliminar(15) : "Debe eliminar 15 del diccionario";
        assert dicc.listarClaves().toString().equals("[2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16]")
                : "Debe listar la secuencia de enteros del 2 al 16 sin el 5 y 15";
        assert dicc.eliminar(11) : "Debe eliminar 11 del diccionario";
        assert dicc.listarClaves().toString().equals("[2, 3, 4, 6, 7, 8, 9, 10, 12, 13, 14, 16]")
                : "Debe listar la secuencia de enteros del 2 al 16 sin el 5, 11 y 15";
        assert dicc.eliminar(8) : "Debe eliminar 8 del diccionario";
        assert dicc.listarClaves().toString().equals("[2, 3, 4, 6, 7, 9, 10, 12, 13, 14, 16]")
                : "Debe listar la secuencia de enteros del 2 al 16 sin el 5, 8, 11 y 15";
        assert !dicc.eliminar(8) : "No debe eliminar 8 del diccionario (inexistente)";
    }

    /**
     * Prueba {@link conjuntistas.Diccionario#existeClave(Comparable)}.
     */
    public void pruebaExisteClave() {
        Diccionario<Integer, String> dicc = crearDiccionario();
        assert dicc.existeClave(8) : "8 debe pertenecer al diccionario";
        assert !dicc.existeClave(20) : "20 no debe pertenecer al diccionario (inexistente)";
    }

    /**
     * Prueba {@link conjuntistas.Diccionario#obtenerInformacion(Comparable)}.
     */
    public void pruebaObtenerInformacion() {
        Diccionario<Integer, String> dicc = crearDiccionario();
        assert dicc.obtenerInformacion(8).equals("Ocho") : "Debe obtener \"Ocho\"";
        assert dicc.obtenerInformacion(1).equals("Uno") : "Debe obtener \"Uno\"";
        assert dicc.obtenerInformacion(16).equals("Dieciseis") : "Debe obtener \"Dieciseis\"";
    }

    /**
     * Prueba {@link conjuntistas.Diccionario#esVacio()}.
     */
    public void pruebaVacio() {
        Diccionario<Integer, String> dicc = new Diccionario<>();
        assert dicc.esVacio() : "El diccionario debe ser vacío";
        dicc.insertar(8, "Ocho");
        assert !dicc.esVacio() : "El diccionario no debe ser vacío";
    }

    /**
     * Prueba {@link conjuntistas.Diccionario#vaciar()}.
     */
    public void pruebaVaciar() {
        Diccionario<Integer, String> dicc = crearDiccionario();
        dicc.vaciar();
        assert dicc.esVacio() : "El diccionario debe ser vacío";
    }

    /**
     * Prueba {@link conjuntistas.Diccionario#listarClaves()}.
     */
    public void pruebaListarClaves() {
        Diccionario<Integer, String> dicc = crearDiccionario();
        assert dicc.listarClaves().toString().equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]")
                : "Debe listar la secuencia de enteros del 1 al 16";
    }

    /**
     * Prueba {@link conjuntistas.Diccionario#listarDatos()}.
     */
    public void pruebaListarDatos() {
        Diccionario<Integer, String> dicc = crearDiccionario();
        assert dicc.listarDatos().toString().equals(
                "[Uno, Dos, Tres, Cuatro, Cinco, Seis, Siete, Ocho, Nueve, Diez, Once, Doce, Trece, Catorce, Quince, "
                        + "Dieciseis]")
                : "Debe listar la secuencia de cadenas \"Uno\", \"Dos\", ..., \"Dieciseis\"";
    }

    /**
     * Prueba {@link conjuntistas.Diccionario#clone()}.
     */
    public void pruebaClonar() {
        Diccionario<Integer, String> dicc = crearDiccionario();
        Diccionario<Integer, String> clon = dicc.clone();
        assert dicc.toString().equals(clon.toString())
                : "Clon del diccionario debe ser una copia exacta del original";
    }

//    /**
//     * Prueba {@link conjuntistas.ArbolAVL} (funcionamiento interno).
//     */
//    public void pruebaDiccionario() {
//        ArbolAVL<Integer> avl;
//        avl = new ArbolAVL<Integer>();
//        avl.insertar(10);
//        avl.insertar(5);
//        avl.insertar(3);
//        assert avl.listarNiveles().toString().equals("[5, 3, 10]")
//                : "Debe listar [5, 3, 10]";
//        assert avl.listar().toString().equals("[3, 5, 10]")
//                : "Debe listar [3, 5, 10]";
//        // Rotación izquierda
//        avl = new ArbolAVL<Integer>();
//        avl.insertar(8);
//        avl.insertar(5);
//        avl.insertar(15);
//        avl.insertar(13);
//        avl.insertar(20);
//        avl.insertar(29);
//        assert avl.listarNiveles().toString().equals("[15, 8, 20, 5, 13, 29]")
//                : "Debe listar [15, 8, 20, 5, 13, 29]";
//        assert avl.listar().toString().equals("[5, 8, 13, 15, 20, 29]")
//                : "Debe listar [5, 8, 13, 15, 20, 29]";
//        // Rotación derecha
//        avl = new ArbolAVL<Integer>();
//        avl.insertar(10);
//        avl.insertar(5);
//        avl.insertar(15);
//        avl.insertar(3);
//        avl.insertar(7);
//        avl.insertar(4);
//        assert avl.listarNiveles().toString().equals("[5, 3, 10, 4, 7, 15]")
//                : "Debe listar [5, 3, 10, 4, 7, 15]";
//        assert avl.listar().toString().equals("[3, 4, 5, 7, 10, 15]")
//                : "Debe listar [3, 4, 5, 7, 10, 15]";
//        // Rotación derecha-izquierda
//        avl = new ArbolAVL<Integer>();
//        avl.insertar(10);
//        avl.insertar(5);
//        avl.insertar(15);
//        avl.insertar(12);
//        avl.insertar(17);
//        avl.insertar(13);
//        assert avl.listarNiveles().toString().equals("[12, 10, 15, 5, 13, 17]")
//                : "Debe listar [12, 10, 15, 5, 13, 17]";
//        assert avl.listar().toString().equals("[5, 10, 12, 13, 15, 17]")
//                : "Debe listar [5, 10, 12, 13, 15, 17]";
//        // Rotación izquierda-derecha
//        avl = new ArbolAVL<Integer>();
//        avl.insertar(12);
//        avl.insertar(5);
//        avl.insertar(23);
//        avl.insertar(3);
//        avl.insertar(8);
//        avl.insertar(10);
//        assert avl.listarNiveles().toString().equals("[8, 5, 12, 3, 10, 23]")
//                : "Debe listar [8, 5, 12, 3, 10, 23]";
//        assert avl.listar().toString().equals("[3, 5, 8, 10, 12, 23]")
//                : "Debe listar [3, 5, 8, 10, 12, 23]";
//    }

    /**
     * Crea un Diccionario de prueba.
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
     * @return el diccionario de prueba
     */
    public static Diccionario<Integer, String> crearDiccionario() {
        Diccionario<Integer, String> dicc = new Diccionario<>();
        dicc.insertar(8, "Ocho");
        dicc.insertar(15, "Quince");
        dicc.insertar(4, "Cuatro");
        dicc.insertar(1, "Uno");
        dicc.insertar(2, "Dos");
        dicc.insertar(11, "Once");
        dicc.insertar(13, "Trece");
        dicc.insertar(14, "Catorce");
        dicc.insertar(12, "Doce");
        dicc.insertar(3, "Tres");
        dicc.insertar(6, "Seis");
        dicc.insertar(9, "Nueve");
        dicc.insertar(5, "Cinco");
        dicc.insertar(7, "Siete");
        dicc.insertar(10, "Diez");
        dicc.insertar(16, "Dieciseis");

        return dicc;
    }
}
