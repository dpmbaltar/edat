package pruebas.jerarquicas;

import jerarquicas.ArbolBinario;
import lineales.dinamicas.Lista;

/**
 * Prueba implementación de Árbol Binario.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaArbolBinario {

    public PruebaArbolBinario() {
        pruebaInsertar();
        pruebaEsVacio();
        pruebaVaciar();
        pruebaAltura();
        pruebaNivel();
        pruebaPadre();
        pruebaListarPreorden();
        pruebaListarInorden();
        pruebaListarPosorden();
        pruebaListarNiveles();
        pruebaClonar();
        pruebaSumarRamas();
        pruebaVerificarPatron();
        pruebaFrontera();
        pruebaClonarHijosInvertidos();
    }

    /**
     * Prueba jerarquicas.ArbolBinario.insertar().
     */
    private void pruebaInsertar() {
        ArbolBinario<Integer> ab = new ArbolBinario<>();
        assert ab.insertarRaiz(1) : "Debe insertar 1 como raíz";
        assert ab.insertarIzquierdo(2, 1) : "Debe insertar 2 como hijo izq. de 1";
        assert ab.insertarDerecho(3, 1) : "Debe insertar 3 como hijo der. de 1";
        assert !ab.insertarDerecho(3, 1) : "No debe insertar 3 como hijo der. de 1 (ya existe)";
        assert !ab.insertarRaiz(1) : "No debe insertar 1 como raíz (raíz existe)";
        assert !ab.insertarIzquierdo(5, 4) : "No debe insertar 5 como hijo izq. de 4 (inexistente)";
        assert !ab.insertarDerecho(6, 4) : "No debe insertar 6 como hijo der. de 4 (inexistente)";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.esVacio().
     */
    private void pruebaEsVacio() {
        ArbolBinario<Integer> ab = new ArbolBinario<>();
        assert ab.esVacio() : "Árbol debe ser vacío";
        ab.insertarRaiz(1);
        assert !ab.esVacio() : "Árbol no debe ser vacío";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.vaciar().
     */
    private void pruebaVaciar() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        ab.vaciar();
        assert ab.esVacio() : "Árbol debe ser vacío";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.altura().
     */
    private void pruebaAltura() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        assert ab.altura() == 3 : "Altura del árbol debe ser 3";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.nivel().
     */
    private void pruebaNivel() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        assert ab.nivel(1) == 0 : "Nivel de 1 debe ser 0";
        assert ab.nivel(2) == 1 : "Nivel de 2 debe ser 1";
        assert ab.nivel(4) == 2 : "Nivel de 4 debe ser 2";
        assert ab.nivel(8) == 3 : "Nivel de 8 debe ser 3";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.padre().
     */
    private void pruebaPadre() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        assert ab.padre(10) == 5 : "Padre de 10 debe ser 5";
        assert ab.padre(16) == null : "Padre de 16 debe ser nulo (inexistente)";
        assert ab.padre(1) == null : "Padre de 1 debe ser nulo";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.listarPreorden().
     */
    private void pruebaListarPreorden() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        assert ab.listarPreorden().toString().equals(
                "[1, 2, 4, 8, 9, 5, 10, 11, 3, 6, 12, 13, 7, 14, 15]")
                    : "Lista en preorden del árbol debe ser: [1, 2, 4, 8, 9, 5, 10, 11, 3, 6, 12, 13, 7, 14, 15]";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.listarInorden().
     */
    private void pruebaListarInorden() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        assert ab.listarInorden().toString().equals(
                "[8, 4, 9, 2, 10, 5, 11, 1, 12, 6, 13, 3, 14, 7, 15]")
                    : "Lista en preorden del árbol debe ser: [8, 4, 9, 2, 10, 5, 11, 1, 12, 6, 13, 3, 14, 7, 15]";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.listarPosorden().
     */
    private void pruebaListarPosorden() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        assert ab.listarPosorden().toString().equals(
                "[8, 9, 4, 10, 11, 5, 2, 12, 13, 6, 14, 15, 7, 3, 1]")
                    : "Lista en preorden del árbol debe ser: [8, 9, 4, 10, 11, 5, 2, 12, 13, 6, 14, 15, 7, 3, 1]";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.listarNiveles().
     */
    private void pruebaListarNiveles() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        assert ab.listarNiveles().toString().equals(
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]")
                    : "Lista en preorden del árbol debe ser: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.clonar().
     */
    private void pruebaClonar() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        assert ab.toString().equals(ab.clonar().toString()) : "Árbol debe ser igual a su clon";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.sumarRamas().
     */
    private void pruebaSumarRamas() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        assert ab.sumarRamas() == 120 : "Suma de ramas debe ser igual a 120";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.verificarPatron().
     */
    private void pruebaVerificarPatron() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        Lista<Integer> lista = new Lista<Integer>();
        lista.insertar(1, 1);
        lista.insertar(3, 2);
        lista.insertar(6, 3);
        lista.insertar(13, 4);
        assert ab.verificarPatron(lista) : "Debe verificar que el camino 1-3-6-13 existe en el AB";
        lista.insertar(16, 5);
        assert !ab.verificarPatron(lista) : "No debe verificar que el camino 1-2-5-11-16 existe en el AB";
        lista.vaciar();
        assert ab.verificarPatron(lista) : "Debe verificar que el camino vacío existe en el AB";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.frontera().
     */
    private void pruebaFrontera() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        assert ab.frontera().toString().equals("[8, 9, 10, 11, 12, 13, 14, 15]")
                : "La frontera debe: 8, 9, 10, 11, 12, 13, 14 y 15";
    }

    /**
     * Prueba jerarquicas.ArbolBinario.clonarHijosInvertidos().
     */
    private void pruebaClonarHijosInvertidos() {
        ArbolBinario<Integer> ab = crearArbolBinarioCompleto();
        assert ab.clonarHijosInvertidos().listarNiveles().toString().equals(
                "[1, 3, 2, 7, 6, 5, 4, 15, 14, 13, 12, 11, 10, 9, 8]")
                    : "Debe clonar hijos invertidos";
    }

    /**
     * Crea un árbol binario completo de prueba:
     *
     * Tipo:        {@link ArbolBinario}
     * Altura:      3
     * Niveles:     4
     * Elementos:   15
     * Preorden:    [1, 2, 4, 8, 9, 5, 10, 11, 3, 6, 12, 13, 7, 14, 15]
     * Inorden:     [8, 4, 9, 2, 10, 5, 11, 1, 12, 6, 13, 3, 14, 7, 15]
     * Posorden:    [8, 9, 4, 10, 11, 5, 2, 12, 13, 6, 14, 15, 7, 3, 1]
     * Por niveles: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
     *
     *        ____1____
     *       /         \
     *     _2_         _3_
     *    /   \       /   \
     *   4     5     6     7
     *  / \   / \   / \   / \
     * 8   9 10 11 12 13 14 15
     *
     * @return
     */
    private static ArbolBinario<Integer> crearArbolBinarioCompleto() {
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
        ab.insertarIzquierdo(14, 7);
        ab.insertarDerecho(15, 7);

        return ab;
    }
}
