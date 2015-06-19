package pruebas.jerarquicas;

import jerarquicas.ArbolBinario;

/**
 * Prueba implementación de Árbol Binario.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaArbolBinario {

    public static void main(String[] args) {
        try {
            // Inicio de pruebas
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
            // Fin de pruebas
            System.out.println("Prueba OK");
        } catch (AssertionError e) {
            System.out.println("Error de prueba: " + e.getMessage());
            System.out.println("Detalles:");
            e.printStackTrace();
        }
    }

    protected static void pruebaInsertar() {
        ArbolBinario<Character> ab = new ArbolBinario<Character>();
        assert ab.insertarRaiz('a') : "Debe insertar 'a' como raíz del árbol";
        assert ab.insertarIzquierdo('b', 'a') : "Debe insertar 'b' como hijo izquierdo de 'a'";
        assert ab.insertarDerecho('c', 'a') : "Debe insertar 'c' como hijo derecho de 'a'";
        assert !ab.insertarRaiz('a') : "No debe insertar 'a' como raíz (raíz existe)";
        assert !ab.insertarIzquierdo('e', 'd') : "No debe insertar 'e' como hijo izquierdo de 'd' (padre 'd' inexistente)";
        assert !ab.insertarDerecho('f', 'd') : "No debe insertar 'f' como hijo derecho de 'd' (padre 'd' inexistente)";
    }

    protected static void pruebaEsVacio() {
        ArbolBinario<Character> ab = new ArbolBinario<Character>();
        assert ab.esVacio() : "Árbol debe ser vacío";
        ab.insertarRaiz('a');
        assert !ab.esVacio() : "Árbol no debe ser vacío";
    }

    protected static void pruebaVaciar() {
        ArbolBinario<Character> ab = crearArbolDePrueba();
        ab.vaciar();
        assert ab.esVacio() : "Árbol debe ser vacío";
    }

    protected static void pruebaAltura() {
        ArbolBinario<Character> ab = crearArbolDePrueba();
        assert ab.altura() == 3 : "Altura del árbol debe ser 3";
    }

    protected static void pruebaNivel() {
        ArbolBinario<Character> ab = crearArbolDePrueba();
        assert ab.nivel('a') == 0 : "Nivel de 'a' debe ser 0";
        assert ab.nivel('b') == 1 : "Nivel de 'b' debe ser 1";
        assert ab.nivel('e') == 2 : "Nivel de 'e' debe ser 2";
        assert ab.nivel('j') == 3 : "Nivel de 'j' debe ser 3";
    }

    protected static void pruebaPadre() {
        ArbolBinario<Character> ab = crearArbolDePrueba();
        assert ab.padre('m') == 'f' : "Padre de 'm' debe ser 'f'";
    }

    protected static void pruebaListarPreorden() {
        ArbolBinario<Character> ab = crearArbolDePrueba();
        assert ab.listarPreorden().toString()
                 .equals("[a b d h i e j k c f l m g n o]") : "Lista en preorden del árbol debe ser: a b d h i e j k c f l m g n o";
    }

    protected static void pruebaListarInorden() {
        ArbolBinario<Character> ab = crearArbolDePrueba();
        assert ab.listarInorden().toString()
                 .equals("[h d i b j e k a l f m c n g o]") : "Lista en inorden del árbol debe ser: h d i b j e k a l f m c n g o";
    }

    protected static void pruebaListarPosorden() {
        ArbolBinario<Character> ab = crearArbolDePrueba();
        assert ab.listarPosorden().toString()
                 .equals("[h i d j k e b l m f n o g c a]") : "Lista en posorden del árbol debe ser: h i d j k e b l m f n o g c a";
    }

    protected static void pruebaListarNiveles() {
        ArbolBinario<Character> ab = crearArbolDePrueba();
        assert ab.listarNiveles().toString()
                 .equals("[a b c d e f g h i j k l m n o]") : "Lista por niveles del árbol debe ser: a b c d e f g h i j k l m n o";
    }

    protected static void pruebaClonar() {
        ArbolBinario<Character> ab = crearArbolDePrueba();
        assert ab.toString().equals(ab.clonar().toString()) : "Árbol debe ser igual a su clon";
    }

    /**
     * Crea un árbol binario de prueba:
     * 
     * Tipo:                ArbolBinario<Character>
     * Altura:              3
     * Niveles:             4
     * Elementos:           15
     * Listado Preorden:    a b d h i e j k c f l m g n o
     * Listado Inorden:     h d i b j e k a l f m c n g o
     * Listado Posorden:    h i d j k e b l m f n o g c a
     * Listado por niveles: a b c d e f g h i j k l m n o
     * 
     *         ____a____
     *        /         \
     *      _b_         _c_
     *     /   \       /   \
     *    d     e     f     g
     *   / \   / \   / \   / \
     *  h   i j   k l   m n   o
     * 
     * @return
     */
    protected static ArbolBinario<Character> crearArbolDePrueba() {
        ArbolBinario<Character> ab = new ArbolBinario<Character>();
        ab.insertarRaiz('a');
        ab.insertarIzquierdo('b', 'a');
        ab.insertarDerecho('c', 'a');
        ab.insertarIzquierdo('d', 'b');
        ab.insertarDerecho('e', 'b');
        ab.insertarIzquierdo('f', 'c');
        ab.insertarDerecho('g', 'c');
        ab.insertarIzquierdo('h', 'd');
        ab.insertarDerecho('i', 'd');
        ab.insertarIzquierdo('j', 'e');
        ab.insertarDerecho('k', 'e');
        ab.insertarIzquierdo('l', 'f');
        ab.insertarDerecho('m', 'f');
        ab.insertarIzquierdo('n', 'g');
        ab.insertarDerecho('o', 'g');

        return ab;
    }
}
