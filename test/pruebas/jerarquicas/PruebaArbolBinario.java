package pruebas.jerarquicas;

import jerarquicas.ArbolBinario;

/**
 * Prueba implementación de Árbol Binario (de enteros).
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaArbolBinario {

    protected ArbolBinario<Integer> ab;

    public PruebaArbolBinario() {
        preparar();
        pruebaInsertar();
        preparar();
        pruebaEsVacio();
        preparar();
        pruebaVaciar();
        preparar();
        pruebaAltura();
        preparar();
        pruebaNivel();
        preparar();
        pruebaPadre();
        preparar();
        pruebaListarPreorden();
        preparar();
        pruebaListarInorden();
        preparar();
        pruebaListarPosorden();
        preparar();
        pruebaListarNiveles();
        preparar();
        pruebaClonar();
    }

    protected void preparar() {
        ab = new ArbolBinario<Integer>();
    }

    /**
     * Prueba jerarquicas.ArbolBinario.insertar().
     */
    protected void pruebaInsertar() {
        assert ab.insertarRaiz(1) : "Debe insertar 1 como raíz";
        assert ab.insertarIzquierdo(2, 1)
             : "Debe insertar 2 como hijo izq. de 1";
        assert ab.insertarDerecho(3, 1)
             : "Debe insertar 3 como hijo der. de 1";
        assert !ab.insertarRaiz(1)
             : "No debe insertar 1 como raíz (raíz existe)";
        assert !ab.insertarIzquierdo(5, 4)
             : "No debe insertar 5 como hijo izq. de 4 (inexistente)";
        assert !ab.insertarDerecho(6, 4)
             : "No debe insertar 6 como hijo der. de 4 (inexistente)";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.esVacio().
     */
    protected void pruebaEsVacio() {
        assert ab.esVacio() : "Árbol debe ser vacío";
        ab.insertarRaiz(1);
        assert !ab.esVacio() : "Árbol no debe ser vacío";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.vaciar().
     */
    protected void pruebaVaciar() {
        ab = crearArbolBinarioCompleto();
        ab.vaciar();
        assert ab.esVacio() : "Árbol debe ser vacío";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.altura().
     */
    protected void pruebaAltura() {
        ab = crearArbolBinarioCompleto();
        assert ab.altura() == 3 : "Altura del árbol debe ser 3";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.nivel().
     */
    protected void pruebaNivel() {
        ab = crearArbolBinarioCompleto();
        assert ab.nivel(1) == 0 : "Nivel de 1 debe ser 0";
        assert ab.nivel(2) == 1 : "Nivel de 2 debe ser 1";
        assert ab.nivel(4) == 2 : "Nivel de 4 debe ser 2";
        assert ab.nivel(8) == 3 : "Nivel de 8 debe ser 3";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.padre().
     */
    protected void pruebaPadre() {
        ab = crearArbolBinarioCompleto();
        assert ab.padre(10) == 5 : "Padre de 10 debe ser 5";
        assert ab.padre(16) == null : "Padre de 16 debe ser nulo (inexistente)";
        assert ab.padre(1) == null : "Padre de 1 debe ser nulo";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.listarPreorden().
     */
    protected void pruebaListarPreorden() {
        ab = crearArbolBinarioCompleto();
        assert ab.listarPreorden().toString()
                 .equals("[1, 2, 4, 8, 9, 5, 10, 11, 3, 6, 12, 13, 7, 14, 15]")
             : "Lista en preorden del árbol debe ser: "
             + "[1, 2, 4, 8, 9, 5, 10, 11, 3, 6, 12, 13, 7, 14, 15]";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.listarInorden().
     */
    protected void pruebaListarInorden() {
        ab = crearArbolBinarioCompleto();
        assert ab.listarInorden().toString()
                 .equals("[8, 4, 9, 2, 10, 5, 11, 1, 12, 6, 13, 3, 14, 7, 15]")
             : "Lista en preorden del árbol debe ser: "
             + "[8, 4, 9, 2, 10, 5, 11, 1, 12, 6, 13, 3, 14, 7, 15]";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.listarPosorden().
     */
    protected void pruebaListarPosorden() {
        ab = crearArbolBinarioCompleto();
        assert ab.listarPosorden().toString()
                 .equals("[8, 9, 4, 10, 11, 5, 2, 12, 13, 6, 14, 15, 7, 3, 1]")
             : "Lista en preorden del árbol debe ser: "
             + "[8, 9, 4, 10, 11, 5, 2, 12, 13, 6, 14, 15, 7, 3, 1]";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.listarNiveles().
     */
    protected void pruebaListarNiveles() {
        ab = crearArbolBinarioCompleto();
        assert ab.listarNiveles().toString()
                 .equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]")
             : "Lista en preorden del árbol debe ser: "
             + "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.clonar().
     */
    protected void pruebaClonar() {
        ab = crearArbolBinarioCompleto();
        assert ab.toString().equals(ab.clonar().toString())
             : "Árbol debe ser igual a su clon";
    }

    /**
     * Crea un árbol binario completo de prueba:
     *
     * Tipo:        ArbolBinario<Integer>
     * Altura:      3
     * Niveles:     4
     * Elementos:   15
     * Preorden:    [1, 2, 4, 8, 9, 5, 10, 11, 3, 6, 12, 13, 7, 14, 15]
     * Inorden:     [8, 4, 9, 2, 10, 5, 11, 1, 12, 6, 13, 3, 14, 7, 15]
     * Posorden:    [8, 9, 4, 10, 11, 5, 2, 12, 13, 6, 14, 15, 7, 3, 1]
     * Por niveles: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
     *
     *         ____1____
     *        /         \
     *      _2_         _3_
     *     /   \       /   \
     *    4     5     6     7
     *   / \   / \   / \   / \
     *  8   9 10 11 12 13 14 15
     *
     * @return
     */
    protected ArbolBinario<Integer> crearArbolBinarioCompleto() {
        ArbolBinario<Integer> ab = new ArbolBinario<Integer>();
        ab.insertarRaiz(1);
        ab.insertarIzquierdo(2, 1);
        ab.insertarDerecho(3, 1);
        ab.insertarIzquierdo(4, 2);
        ab.insertarDerecho(5, 2);
        ab.insertarIzquierdo(6, 3);
        ab.insertarDerecho(7, 3);
        ab.insertarIzquierdo(8, 4);
        ab.insertarDerecho(9, 4);
        ab.insertarIzquierdo(10, 5);
        ab.insertarDerecho(11, 5);
        ab.insertarIzquierdo(12, 6);
        ab.insertarDerecho(13, 6);
        ab.insertarIzquierdo(14,  7);
        ab.insertarDerecho(15, 7);

        return ab;
    }
}
