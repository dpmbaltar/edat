package pruebas.jerarquicas;

import jerarquicas.ArbolGenerico;
import lineales.dinamicas.Lista;

/**
 * Prueba implementación de Árbol Genérico.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaArbolGenerico {

    public PruebaArbolGenerico() {
        pruebaInsertar();
        pruebaEsVacio();
        pruebaPertenece();
        pruebaAltura();
        pruebaNivel();
        pruebaPadre();
        pruebaAncestros();
        pruebaListarPreorden();
        pruebaListarInorden();
        pruebaListarPosorden();
        pruebaListarNiveles();
        pruebaClonar();
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.insertar().
     */
    private void pruebaInsertar() {
        ArbolGenerico<Character> ag = new ArbolGenerico<Character>();
        assert ag.insertar('a', null) : "Debe insertar 'a' como raíz del árbol";
        assert ag.insertar('b', 'a') : "Debe insertar 'b' como hijo de 'a'";
        assert ag.insertar('c', 'a') : "Debe insertar 'c' como hijo de 'a'";
        assert ag.insertar('d', 'b') : "Debe insertar 'd' como hijo de 'b'";
        assert ag.insertar('e', 'c') : "Debe insertar 'e' como hijo de 'c'";
        assert ag.insertar('f', 'c') : "Debe insertar 'f' como hijo de 'c'";
        assert ag.insertar('h', 'g') == false : "No debe insertar 'h' (padre 'g' inexistente)";
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.esVacio().
     */
    private void pruebaEsVacio() {
        ArbolGenerico<Character> ag = new ArbolGenerico<Character>();
        assert ag.esVacio() : "Árbol debe ser vacío";
        ag.insertar('a', null);
        assert ag.esVacio() == false : "Árbol no debe ser vacío";
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.pertenece().
     */
    private void pruebaPertenece() {
        ArbolGenerico<Character> ag = crearArbolGenerico();
        assert ag.pertenece('a') : "'a' debe pertenecer al árbol";
        assert ag.pertenece('g') : "'g' debe pertenecer al árbol";
        assert ag.pertenece('k') : "'k' debe pertenecer al árbol";
        assert ag.pertenece('o') == false : "'o' no debe pertenecer al árbol";
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.altura().
     */
    private void pruebaAltura() {
        ArbolGenerico<Character> ag = new ArbolGenerico<Character>();
        assert ag.altura() == 0 : "Altura debe ser 0";
        ag = crearArbolGenerico();
        assert ag.altura() == 4 : "Altura debe ser 4";
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.nivel().
     */
    private void pruebaNivel() {
        ArbolGenerico<Character> ag = crearArbolGenerico();
        assert ag.nivel('a') == 0 : "Nivel de 'a' debe ser 0";
        assert ag.nivel('b') == 1 : "Nivel de 'b' debe ser 1";
        assert ag.nivel('g') == 2 : "Nivel de 'g' debe ser 2";
        assert ag.nivel('k') == 3 : "Nivel de 'k' debe ser 3";
        assert ag.nivel('m') == 4 : "Nivel de 'm' debe ser 4";
        assert ag.nivel('o') == -1 : "Nivel de 'o' debe ser -1";
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.padre().
     */
    private void pruebaPadre() {
        ArbolGenerico<Character> ag = crearArbolGenerico();
        assert ag.padre('a') == null : "Padre de 'a' debe ser nulo";
        assert ag.padre('b') == 'a' : "Padre de 'b' debe ser 'a'";
        assert ag.padre('g') == 'c' : "Padre de 'g' debe ser 'c'";
        assert ag.padre('k') == 'h' : "Padre de 'k' debe ser 'h'";
        assert ag.padre('m') == 'l' : "Padre de 'm' debe ser 'l'";
        assert ag.padre('o') == null : "Padre de 'o' debe ser nulo (elemento 'o' inexistente)";
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.ancestros().
     */
    private void pruebaAncestros() {
        ArbolGenerico<Character> ag = crearArbolGenerico();
        Lista<Character> lc;
        assert ag.ancestros('a').toString().equals("[]")
                : "'a' no debe tener ancestros";
        assert ag.ancestros('f').toString().equals("[a, b]")
                : "Los ancestros de 'f' deben ser: [a, b]";
        assert ag.ancestros('g').toString().equals("[a, c]")
                : "Los ancestros de 'g' deben ser: [a, c]";
        assert ag.ancestros('l').toString().equals("[a, d, h]")
                : "Los ancestros de 'l' deben ser: [a, d, h]";
        assert ag.ancestros('m').toString().equals("[a, d, h, l]")
                : "Los ancestros de 'm' deben ser: [a, d, h, l]";
        assert ag.ancestros('e').toString().equals(ag.ancestros('f').toString())
                : "Los ancestros de 'e' deben ser igual a los de 'f'";
        assert ag.ancestros('h').toString().equals(ag.ancestros('i').toString())
                : "Los ancestros de 'h' deben ser igual a los de 'i'";
        lc = ag.ancestros('n');
        lc.eliminar(4);
        assert lc.toString().equals(ag.ancestros('k').toString())
                : "Los ancestros de 'n' menos el último, deben ser igual a los de 'k'";
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.listarPreorden().
     */
    private void pruebaListarPreorden() {
        ArbolGenerico<Character> ag = crearArbolGenerico();
        assert ag.listarPreorden().toString().equals("[a, b, e, f, c, g, d, h, j, k, l, m, n, i]")
                : "Listado en preorden debe ser: [a, b, e, f, c, g, d, h, j, k, l, m, n, i]";
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.listarInorden().
     */
    private void pruebaListarInorden() {
        ArbolGenerico<Character> ag = crearArbolGenerico();
        assert ag.listarInorden().toString().equals("[e, b, f, a, g, c, j, h, k, m, l, n, d, i]")
                : "Listado en inorden debe ser: [e, b, f, a, g, c, j, h, k, m, l, n, d, i]";
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.listarPosorden().
     */
    private void pruebaListarPosorden() {
        ArbolGenerico<Character> ag = crearArbolGenerico();
        assert ag.listarPosorden().toString().equals("[e, f, b, g, c, j, k, m, n, l, h, i, d, a]")
                : "Listado en posorden debe ser: [e, f, b, g, c, j, k, m, n, l, h, i, d, a]";
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.listarNiveles().
     */
    private void pruebaListarNiveles() {
        ArbolGenerico<Character> ag = crearArbolGenerico();
        assert ag.listarNiveles().toString().equals("[a, b, c, d, e, f, g, h, i, j, k, l, m, n]")
                : "Listado por niveles debe ser: [a, b, c, d, e, f, g, h, i, j, k, l, m, n]";
    }

    /**
     * Prueba jerarquicas.ArbolGenerico.clonar().
     */
    private void pruebaClonar() {
        ArbolGenerico<Character> ag = crearArbolGenerico();
        assert ag.toString().equals(ag.clonar().toString()) : "Árbol debe ser igual a su clon";
    }

    /**
     * Provee un Árbol para pruebas. Estructura del Árbol:
     *
     * Tipo:        {@link ArbolGenerico}
     * Altura:      4
     * Niveles:     5
     * Elementos:   14
     * Preorden:    [a, b, e, f, c, g, d, h, j, k, l, m, n, i]
     * Inorden:     [e, b, f, a, g, c, j, h, k, m, l, n, d, i]
     * Posorden:    [e, f, b, g, c, j, k, m, n, l, h, i, d, a]
     * Por niveles: [a, b, c, d, e, f, g, h, i, j, k, l, m, n]
     *
     * a
     * +-b
     * | +-e
     * | +-f
     * +-c
     * | +-g
     * +-d
     *   +-h
     *   | +-j
     *   | +-k
     *   | +-l
     *   |   +-m
     *   |   +-n
     *   +-i
     */
    private static ArbolGenerico<Character> crearArbolGenerico() {
        ArbolGenerico<Character> ag = new ArbolGenerico<Character>();
        ag.insertar('a', null);
        ag.insertar('b', 'a');
        ag.insertar('c', 'a');
        ag.insertar('d', 'a');
        ag.insertar('e', 'b');
        ag.insertar('f', 'b');
        ag.insertar('g', 'c');
        ag.insertar('h', 'd');
        ag.insertar('i', 'd');
        ag.insertar('j', 'h');
        ag.insertar('k', 'h');
        ag.insertar('l', 'h');
        ag.insertar('m', 'l');
        ag.insertar('n', 'l');

        return ag;
    }
}
