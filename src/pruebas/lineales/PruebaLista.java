package pruebas.lineales;

import lineales.dinamicas.Lista;

/**
 * Prueba implementación de Lista.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class PruebaLista {

    public static void main(String[] args) {
        try {
            pruebaInsertar();
            pruebaRecuperar();
            pruebaEliminar();
            pruebaLocalizar();
            pruebaLongitud();
            pruebaEsVacia();
            pruebaVaciar();
            pruebaClonar();
            pruebaToString();
            System.out.println("Prueba OK");
        } catch (AssertionError e) {
            System.out.println("Error de prueba: " + e.getMessage());
            System.out.println("Detalles: ");
            e.printStackTrace();
        }
    }

    protected static void pruebaInsertar() {
        Lista<Character> lista = new Lista<Character>();
        assert lista.insertar('a', 1) : "Debe insertar 'a' en la posición 1";
        assert lista.insertar('b', 0) == false : "No debe insertar 'b' en la posición 0 (inválida: < 1)";
        assert lista.insertar('c', 3) == false : "No debe insertar 'c' en la posición 3 (inválida: > longitud de lista + 1)";
    }

    protected static void pruebaRecuperar() {
        Lista<Character> lista = new Lista<Character>();
        lista.insertar('a', 1);
        lista.insertar('b', 2);
        lista.insertar('c', 3);
        assert lista.recuperar(3) == 'c' : "Debe recuperar elemento de la posición 3 ('c')";
    }

    protected static void pruebaEliminar() {
        Lista<Character> lista = new Lista<Character>();
        lista.insertar('a', 1);
        lista.insertar('b', 2);
        lista.insertar('c', 3);
        assert lista.eliminar(3) : "Debe eliminar elemento en la posición 3 ('c')";
        assert lista.recuperar(3) == null : "No debe recuperar elemento eliminado ('c')";
    }

    protected static void pruebaLocalizar() {
        Lista<Character> lista = new Lista<Character>();
        lista.insertar('a', 1);
        lista.insertar('b', 2);
        lista.insertar('c', 3);
        assert lista.localizar('b') == 2 : "Debe localizar 'b' en la posición 2";
        assert lista.localizar('d') == -1 : "No debe localizar 'd' (elemento inexistente)";
    }

    protected static void pruebaLongitud() {
        Lista<Character> lista = new Lista<Character>();
        lista.insertar('a', 1);
        lista.insertar('b', 2);
        lista.insertar('c', 3);
        assert lista.longitud() == 3 : "Longitud de lista debe ser 3";
        lista.eliminar(3);
        assert lista.longitud() == 2 : "Longitud de lista debe ser 2";
        lista.eliminar(2);
        lista.eliminar(1);
        assert lista.longitud() == 0 : "Longitud de lista debe ser 0";
    }

    protected static void pruebaEsVacia() {
        Lista<Character> lista = new Lista<Character>();
        assert lista.esVacia() : "Lista debe ser vacía";
        lista.insertar('a', 1);
        assert lista.esVacia() == false : "Lista no debe ser vacía";
    }

    protected static void pruebaVaciar() {
        Lista<Character> lista = new Lista<Character>();
        lista.insertar('a', 1);
        lista.insertar('b', 2);
        lista.insertar('c', 3);
        lista.vaciar();
        assert lista.esVacia() : "Lista debe ser vacía";
        assert lista.longitud() == 0 : "Longitud de lista debe ser 0";
    }

    protected static void pruebaClonar() {
        Lista<Character> lista = new Lista<Character>();
        lista.insertar('a', 1);
        lista.insertar('b', 2);
        lista.insertar('c', 3);
        Lista<Character> clon = lista.clonar();
        assert lista.recuperar(1) == clon.recuperar(1) : "Lista debe ser igual a su clon";
        assert lista.recuperar(2) == clon.recuperar(2) : "Lista debe ser igual a su clon";
        assert lista.recuperar(3) == clon.recuperar(3) : "Lista debe ser igual a su clon";
    }

    protected static void pruebaToString() {
        Lista<Character> lista = new Lista<Character>();
        lista.insertar('a', 1);
        lista.insertar('b', 2);
        lista.insertar('c', 3);
        assert lista.toString().equals("[a b c]") : "Lista con elementos 'a', 'b', 'c' debe ser representada como [a b c] en forma de cadena";
    }
}
