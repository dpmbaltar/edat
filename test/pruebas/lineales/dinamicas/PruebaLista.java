package pruebas.lineales.dinamicas;

import lineales.dinamicas.Lista;

/**
 * Prueba implementación de Lista.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaLista {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaLista() {
        pruebaInsertar();
        pruebaRecuperar();
        pruebaEliminar();
        pruebaLocalizar();
        pruebaLongitud();
        pruebaEsVacia();
        pruebaVaciar();
        pruebaClone();
        pruebaToString();
    }

    /**
     * Prueba {@link lineales.dinamicas.Lista#insertar(Object, int)}.
     */
    public void pruebaInsertar() {
        Lista<Integer> lista = new Lista<>();
        assert lista.insertar(1, 1) : "Debe insertar 1@1";
        assert !lista.insertar(2, 0) : "No debe insertar 2@0 (pos. inválida: < 1)";
        assert !lista.insertar(3, 3) : "No debe insertar 3@3 (pos. inválida: > longitud de lista + 1)";
    }

    /**
     * Prueba {@link lineales.dinamicas.Lista#recuperar(int)}.
     */
    public void pruebaRecuperar() {
        Lista<Integer> lista = crearLista();
        assert lista.recuperar(1) == 1 : "Debe recuperar 1@1";
        assert lista.recuperar(4) == 4 : "Debe recuperar 4@4";
        assert lista.recuperar(8) == 8 : "Debe recuperar 8@8";
    }

    /**
     * Prueba {@link lineales.dinamicas.Lista#eliminar(int)}.
     */
    public void pruebaEliminar() {
        Lista<Integer> lista = crearLista();
        assert lista.eliminar(3) : "Debe eliminar 3@3";
        assert lista.recuperar(3) == 4 : "Debe recuperar 4@3 (eliminado 3@3)";
    }

    /**
     * Prueba {@link lineales.dinamicas.Lista#localizar(Object)}.
     */
    public void pruebaLocalizar() {
        Lista<Integer> lista = crearLista();
        assert lista.localizar(2) == 2 : "Debe localizar 2@2";
        assert lista.localizar(9) == -1 : "No debe localizar 9 (inexistente)";
    }

    /**
     * Prueba {@link lineales.dinamicas.Lista#longitud()}.
     */
    public void pruebaLongitud() {
        Lista<Integer> lista = crearLista();
        assert lista.longitud() == 8 : "Longitud de lista debe ser 8";
        lista.eliminar(8);
        assert lista.longitud() == 7 : "Longitud de lista debe ser 7";
        lista.eliminar(1);
        lista.eliminar(3);
        assert lista.longitud() == 5 : "Longitud de lista debe ser 5";
    }

    /**
     * Prueba {@link lineales.dinamicas.Lista#esVacia()}.
     */
    public void pruebaEsVacia() {
        Lista<Integer> lista = new Lista<>();
        assert lista.esVacia() : "Lista debe ser vacía";
        lista.insertar(1, 1);
        assert !lista.esVacia() : "Lista no debe ser vacía";
    }

    /**
     * Prueba {@link lineales.dinamicas.Lista#vaciar()}.
     */
    public void pruebaVaciar() {
        Lista<Integer> lista = crearLista();
        lista.vaciar();
        assert !lista.eliminar(1) : "No debe eliminar de lista vacía";
        assert lista.localizar(2) == -1 : "No debe localizar 2 (lista vacía)";
        assert lista.recuperar(3) == null : "No debe recuperar 3 (lista vacía)";
        assert lista.longitud() == 0 : "Longitud de lista debe ser 0";
        assert lista.esVacia() : "Lista debe ser vacía";
    }

    /**
     * Prueba {@link lineales.dinamicas.Lista#clone()}.
     */
    public void pruebaClone() {
        Lista<Integer> lista = crearLista();
        Lista<Integer> clon = lista.clone();
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
     * Prueba {@link lineales.dinamicas.Lista#toString()}.
     */
    public void pruebaToString() {
        Lista<Integer> lista = crearLista();
        assert lista.toString().equals("[1, 2, 3, 4, 5, 6, 7, 8]")
                : "La lista debe ser representada como \"[1, 2, 3, 4, 5, 6, 7, 8]\"";
    }

    /**
     * Crea una lista de enteros de prueba con elementos del 1 al 8.
     *
     * @return la lista
     */
    public static Lista<Integer> crearLista() {
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
