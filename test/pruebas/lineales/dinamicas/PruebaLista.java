package pruebas.lineales.dinamicas;

import lineales.dinamicas.Lista;

/**
 * Prueba implementación de Lista (de enteros).
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaLista {

    protected Lista<Integer> lista;

    public PruebaLista() {
        preparar();
        pruebaInsertar();
        preparar();
        pruebaRecuperar();
        preparar();
        pruebaEliminar();
        preparar();
        pruebaLocalizar();
        preparar();
        pruebaLongitud();
        preparar();
        pruebaEsVacia();
        preparar();
        pruebaVaciar();
        preparar();
        pruebaClonar();
        preparar();
        pruebaToString();
    }

    protected void preparar() {
        lista = new Lista<Integer>();
    }

    /**
     * Prueba lineales.dinamicas.Lista.insertar().
     */
    protected void pruebaInsertar() {
        assert lista.insertar(1, 1) : "Debe insertar 1@1";
        assert !lista.insertar(2, 0)
             : "No debe insertar 2@0 (pos. inválida: < 1)";
        assert !lista.insertar(3, 3)
             : "No debe insertar 3@3 (pos. inválida: > longitud de lista + 1)";
    }

    /**
     * Prueba lineales.dinamicas.Lista.recuperar().
     */
    protected void pruebaRecuperar() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        assert lista.recuperar(3) == 3 : "Debe recuperar 3@3";
    }

    /**
     * Prueba lineales.dinamicas.Lista.eliminar().
     */
    protected void pruebaEliminar() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        assert lista.eliminar(3) : "Debe eliminar 3@3";
        assert lista.recuperar(3) == null : "No debe recuperar 3@3 (eliminado)";
    }

    /**
     * Prueba lineales.dinamicas.Lista.localizar().
     */
    protected void pruebaLocalizar() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        assert lista.localizar(2) == 2 : "Debe localizar 2@2";
        assert lista.localizar(4) == -1 : "No debe localizar 4 (inexistente)";
    }

    /**
     * Prueba lineales.dinamicas.Lista.longitud().
     */
    protected void pruebaLongitud() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        assert lista.longitud() == 3 : "Longitud de lista debe ser 3";
        lista.eliminar(3);
        assert lista.longitud() == 2 : "Longitud de lista debe ser 2";
        lista.eliminar(2);
        lista.eliminar(1);
        assert lista.longitud() == 0 : "Longitud de lista debe ser 0";
    }

    /**
     * Prueba lineales.dinamicas.Lista.esVacia().
     */
    protected void pruebaEsVacia() {
        assert lista.esVacia() : "Lista debe ser vacía";
        lista.insertar(1, 1);
        assert !lista.esVacia() : "Lista no debe ser vacía";
    }

    /**
     * Prueba lineales.dinamicas.Lista.vaciar().
     */
    protected void pruebaVaciar() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
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
    protected void pruebaClonar() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        Lista<Integer> clon = lista.clonar();
        assert lista.recuperar(1) == clon.recuperar(1)
             : "Elemento @1 de lista debe ser igual al de su clon";
        lista.eliminar(1);
        clon.eliminar(1);
        assert lista.recuperar(2) == clon.recuperar(2)
             : "Elemento @2 de lista debe ser igual al de su clon";
        lista.eliminar(1);
        clon.eliminar(1);
        assert lista.recuperar(3) == clon.recuperar(3)
             : "Elemento @3 de lista debe ser igual al de su clon";
        lista.eliminar(1);
        clon.eliminar(1);
        assert lista.esVacia() && clon.esVacia()
             : "Lista y su clon deben ser vacías";
    }

    /**
     * Prueba lineales.dinamicas.Lista.toString().
     */
    protected void pruebaToString() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        assert lista.toString().equals("[1, 2, 3]")
             : "Lista con elementos 1, 2, 3 debe ser representada como "
             + "[1, 2, 3] en forma de cadena";
    }
}
