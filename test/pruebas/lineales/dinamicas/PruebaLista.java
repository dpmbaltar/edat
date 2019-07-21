package pruebas.lineales.dinamicas;

import lineales.dinamicas.Lista;

/**
 * Prueba implementación de Lista.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaLista {

    public PruebaLista() {
        pruebaInsertar();
        pruebaRecuperar();
        pruebaEliminar();
        pruebaLocalizar();
        pruebaLongitud();
        pruebaEsVacia();
        pruebaVaciar();
        pruebaClonar();
        pruebaToString();
    }

    /**
     * Prueba lineales.dinamicas.Lista.insertar().
     */
    private void pruebaInsertar() {
        Lista<Integer> lista = new Lista<>();
        assert lista.insertar(1, 1) : "Debe insertar 1@1";
        assert !lista.insertar(2, 0) : "No debe insertar 2@0 (pos. inválida: < 1)";
        assert !lista.insertar(3, 3) : "No debe insertar 3@3 (pos. inválida: > longitud de lista + 1)";
    }

    /**
     * Prueba lineales.dinamicas.Lista.recuperar().
     */
    private void pruebaRecuperar() {
        Lista<Integer> lista = crearLista();
        assert lista.recuperar(1) == 1 : "Debe recuperar 1@1";
        assert lista.recuperar(4) == 4 : "Debe recuperar 4@4";
        assert lista.recuperar(8) == 8 : "Debe recuperar 8@8";
    }

    /**
     * Prueba lineales.dinamicas.Lista.eliminar().
     */
    private void pruebaEliminar() {
        Lista<Integer> lista = crearLista();
        assert lista.eliminar(3) : "Debe eliminar 3@3";
        assert lista.recuperar(3) == 4 : "Debe recuperar 4@3 (eliminado 3@3)";
    }

    /**
     * Prueba lineales.dinamicas.Lista.localizar().
     */
    private void pruebaLocalizar() {
        Lista<Integer> lista = crearLista();
        assert lista.localizar(2) == 2 : "Debe localizar 2@2";
        assert lista.localizar(9) == -1 : "No debe localizar 9 (inexistente)";
    }

    /**
     * Prueba lineales.dinamicas.Lista.longitud().
     */
    private void pruebaLongitud() {
        Lista<Integer> lista = crearLista();
        assert lista.longitud() == 8 : "Longitud de lista debe ser 8";
        lista.eliminar(8);
        assert lista.longitud() == 7 : "Longitud de lista debe ser 7";
        lista.eliminar(1);
        lista.eliminar(3);
        assert lista.longitud() == 5 : "Longitud de lista debe ser 5";
    }

    /**
     * Prueba lineales.dinamicas.Lista.esVacia().
     */
    private void pruebaEsVacia() {
        Lista<Integer> lista = new Lista<>();
        assert lista.esVacia() : "Lista debe ser vacía";
        lista.insertar(1, 1);
        assert !lista.esVacia() : "Lista no debe ser vacía";
    }

    /**
     * Prueba lineales.dinamicas.Lista.vaciar().
     */
    private void pruebaVaciar() {
        Lista<Integer> lista = crearLista();
        lista.vaciar();
        assert !lista.eliminar(1) : "No debe eliminar de lista vacía";
        assert lista.localizar(2) == -1 : "No debe localizar 2 (lista vacía)";
        assert lista.recuperar(3) == null : "No debe recuperar 3 (lista vacía)";
        assert lista.longitud() == 0 : "Longitud de lista debe ser 0";
        assert lista.esVacia() : "Lista debe ser vacía";
    }

    /**
     * Prueba lineales.dinamicas.Lista.clonar().
     */
    private void pruebaClonar() {
        Lista<Integer> lista = crearLista();
        Lista<Integer> clon = lista.clonar();
        assert lista.recuperar(1) == clon.recuperar(1) : "Elemento @1 de lista debe ser igual al de su clon";
        lista.eliminar(1);
        clon.eliminar(1);
        assert lista.recuperar(2) == clon.recuperar(2) : "Elemento @2 de lista debe ser igual al de su clon";
        lista.eliminar(1);
        clon.eliminar(1);
        assert lista.recuperar(3) == clon.recuperar(3) : "Elemento @3 de lista debe ser igual al de su clon";
        lista.eliminar(1);
        clon.eliminar(1);
        assert lista.recuperar(4) == clon.recuperar(4) : "Elemento @3 de lista debe ser igual al de su clon";
    }

    /**
     * Prueba lineales.dinamicas.Lista.toString().
     */
    private void pruebaToString() {
        Lista<Integer> lista = crearLista();
        assert lista.toString().equals("[1, 2, 3, 4, 5, 6, 7, 8]")
                : "La lista debe ser representada como [1, 2, 3, 4, 5, 6, 7, 8] como cadena";
    }

    /**
     * Crea una lista de enteros de prueba con elementos del 1 al 8.
     *
     * @return la lista
     */
    private static Lista<Integer> crearLista() {
        Lista<Integer> lista = new Lista<>();
        lista.insertar(1, lista.longitud() + 1);
        lista.insertar(2, lista.longitud() + 1);
        lista.insertar(3, lista.longitud() + 1);
        lista.insertar(4, lista.longitud() + 1);
        lista.insertar(5, lista.longitud() + 1);
        lista.insertar(6, lista.longitud() + 1);
        lista.insertar(7, lista.longitud() + 1);
        lista.insertar(8, lista.longitud() + 1);

        return lista;
    }
}
