package pruebas.jerarquicas;

import jerarquicas.ArbolGenerico;

/**
 * Prueba implementación de Árbol Genérico.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class PruebaArbolGenerico {

    public static void main(String[] args) {
        try {
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
            System.out.println("Prueba OK");
        } catch (AssertionError e) {
            System.out.println("Error de prueba: " + e.getMessage());
            System.out.println("Detalles:");
            e.printStackTrace();
        }
    }

    protected static void pruebaInsertar() {
        ArbolGenerico<Character> ag = new ArbolGenerico<Character>();
        assert ag.insertar('a', null) : "Debe insertar 'a' como raíz del árbol";
        assert ag.insertar('b', 'a') : "Debe insertar 'b' como hijo de 'a'";
        assert ag.insertar('c', 'a') : "Debe insertar 'c' como hijo de 'a'";
        assert ag.insertar('d', 'b') : "Debe insertar 'd' como hijo de 'b'";
        assert ag.insertar('e', 'c') : "Debe insertar 'd' como hijo de 'b'";
        assert ag.insertar('f', 'c') : "Debe insertar 'd' como hijo de 'b'";
        assert ag.insertar('h', 'g') == false : "No debe insertar 'h' (padre 'g' inexistente)";
    }

    protected static void pruebaEsVacio() {
        ArbolGenerico<Character> ag = new ArbolGenerico<Character>();
        assert ag.esVacio() : "Árbol debe ser vacío";
        ag.insertar('a', null);
        assert ag.esVacio() == false : "Árbol no debe ser vacío";
    }

    protected static void pruebaPertenece() {
        ArbolGenerico<Character> ag = crearArbolPrueba();
        assert ag.pertenece('a') : "'a' debe pertenecer al árbol";
        assert ag.pertenece('b') : "'b' debe pertenecer al árbol";
        assert ag.pertenece('g') : "'g' debe pertenecer al árbol";
        assert ag.pertenece('k') : "'k' debe pertenecer al árbol";
        assert ag.pertenece('m') : "'m' debe pertenecer al árbol";
        assert ag.pertenece('o') == false : "'o' no debe pertenecer al árbol";
    }

    protected static void pruebaAltura() {
        ArbolGenerico<Character> ag = crearArbolPrueba();
        assert ag.altura() == 4 : "Altura debe ser 4";
    }

    protected static void pruebaNivel() {
        ArbolGenerico<Character> ag = crearArbolPrueba();
        assert ag.nivel('a') == 0 : "Nivel de 'a' debe ser 0";
        assert ag.nivel('b') == 1 : "Nivel de 'b' debe ser 1";
        assert ag.nivel('g') == 2 : "Nivel de 'g' debe ser 2";
        assert ag.nivel('k') == 3 : "Nivel de 'k' debe ser 3";
        assert ag.nivel('m') == 4 : "Nivel de 'm' debe ser 4";
        assert ag.nivel('o') == -1 : "Nivel de 'o' debe ser -1";
    }

    protected static void pruebaPadre() {
        ArbolGenerico<Character> ag = crearArbolPrueba();
        assert ag.padre('a') == null : "Padre de 'a' debe ser nulo";
        assert ag.padre('b') == 'a' : "Padre de 'b' debe ser 'a'";
        assert ag.padre('g') == 'c' : "Padre de 'g' debe ser 'c'";
        assert ag.padre('k') == 'h' : "Padre de 'k' debe ser 'h'";
        assert ag.padre('m') == 'l' : "Padre de 'm' debe ser 'l'";
        assert ag.padre('o') == null : "Padre de 'o' debe ser nulo (elemento 'o' inexistente)";
    }

    protected static void pruebaAncestros() {
        ArbolGenerico<Character> ag = crearArbolPrueba();
        assert ag.ancestros('a').toString().equals("[]") : "'a' no debe tener ancestros";
        assert ag.ancestros('f').toString().equals("[b a]") : "Los ancestros de 'f' deben ser: c a";
        assert ag.ancestros('m').toString().equals("[l h d a]") : "Los ancestros de 'm' deben ser: l h d a";
    }

    protected static void pruebaListarPreorden() {
        ArbolGenerico<Character> ag = crearArbolPrueba();
        assert ag.listarPreorden().toString()
                 .equals("[a b e f c g d h j k l m n i]") : "Listado en preorden debe ser: a b e f c g d h j k l m n i";
    }

    protected static void pruebaListarInorden() {
        ArbolGenerico<Character> ag = crearArbolPrueba();
        assert ag.listarInorden().toString()
                 .equals("[e b f a g c j h k m l n d i]") : "Listado en inorden debe ser: e b f a g c j h k m l n d i";
    }

    protected static void pruebaListarPosorden() {
        ArbolGenerico<Character> ag = crearArbolPrueba();
        assert ag.listarPosorden().toString()
                 .equals("[e f b g c j k m n l h i d a]") : "Listado en posorden debe ser: e f b g c j k m n l h i d a";
    }

    protected static void pruebaListarNiveles() {
        ArbolGenerico<Character> ag = crearArbolPrueba();
        assert ag.listarNiveles().toString()
                 .equals("[a b c d e f g h i j k l m n]") : "Listado por niveles debe ser: a b c d e f g h i j k l m n";
    }
    
    protected static void pruebaClonar() {
        ArbolGenerico<Character> ag = crearArbolPrueba();
        assert ag.toString().equals(ag.clonar().toString()) : "Árbol debe ser igual a su clon";
    }

    /**
     * Provee un Árbol para pruebas.
     * Estructura del Árbol:
     * 
     * Tipo:                ArbolGenerico<Character>
     * Altura:              4
     * Niveles:             5
     * Elementos:           14
     * Listado Preorden:    a b e f c g d h j k l m n i
     * Listado Inorden:     e b f a g c j h k m l n d i
     * Listado Posorden:    e f b g c j k m n l h i d a
     * Listado por niveles: a b c d e f g h i j k l m n
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
    protected static ArbolGenerico<Character> crearArbolPrueba() {
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
