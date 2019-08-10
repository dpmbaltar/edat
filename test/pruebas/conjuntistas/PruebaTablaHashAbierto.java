package pruebas.conjuntistas;

import conjuntistas.TablaHashAbierto;
import lineales.dinamicas.Lista;

/**
 * Prueba implementación de Tabla Hash Abierto.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaTablaHashAbierto {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaTablaHashAbierto() {
        pruebaInsertar();
        pruebaEliminar();
        pruebaPertenece();
        pruebaListar();
        pruebaEsVacia();
        pruebaVaciar();
        pruebaClone();
    }

    /**
     * Prueba {@link conjuntistas.TablaHashAbierto#insertar(Object)}.
     */
    public void pruebaInsertar() {
        TablaHashAbierto<Integer> th = new TablaHashAbierto<>();
        assert th.insertar(1) : "Debe insertar 1 en la tabla hash";
        assert th.insertar(2) : "Debe insertar 2 en la tabla hash";
        assert th.insertar(256) : "Debe insertar 256 en la tabla hash";
        assert !th.insertar(256) : "No debe insertar 256 en la tabla hash (existente)";
    }

    /**
     * Prueba {@link conjuntistas.TablaHashAbierto#eliminar(Object)}.
     */
    public void pruebaEliminar() {
        TablaHashAbierto<Integer> th = crearTablaHashAbierto();
        assert th.eliminar(0) : "Debe eliminar 0";
        assert th.eliminar(16384) : "Debe eliminar 16384";
        assert !th.eliminar(0) : "No debe eliminar 0 (inexistente)";
    }

    /**
     * Prueba {@link conjuntistas.TablaHashAbierto#pertenece(Object)}.
     */
    public void pruebaPertenece() {
        TablaHashAbierto<Integer> th = crearTablaHashAbierto();
        assert th.pertenece(0) : "0 debe pertenecer a la tabla";
        assert !th.pertenece(2) : "2 no debe pertenecer a la tabla";
    }

    /**
     * Prueba {@link conjuntistas.TablaHashAbierto#listar()}.
     */
    public void pruebaListar() {
        TablaHashAbierto<Integer> th = crearTablaHashAbierto();
        Lista<Integer> lista = th.listar();
        Integer[] elementos = crearElementos();

        for (int i = 0; i < elementos.length; i++) {
            assert lista.localizar(elementos[i]) > -1
                    : "Debe localizar " + elementos[i] + " en la lista";
        }
    }

    /**
     * Prueba {@link conjuntistas.TablaHashAbierto#esVacia()}.
     */
    public void pruebaEsVacia() {
        TablaHashAbierto<Integer> th = new TablaHashAbierto<>();
        assert th.esVacia() : "Debe ser vacía";
        th.insertar(1);
        assert !th.esVacia() : "No debe ser vacía";
    }

    /**
     * Prueba {@link conjuntistas.TablaHashAbierto#vaciar()}.
     */
    public void pruebaVaciar() {
        TablaHashAbierto<Integer> th = crearTablaHashAbierto();
        th.vaciar();
        assert th.esVacia() : "Debe ser vacía";
        th.insertar(1);
        assert !th.esVacia() : "No debe ser vacía";
    }

    /**
     * Prueba {@link conjuntistas.TablaHashAbierto#clone()}.
     */
    public void pruebaClone() {
        TablaHashAbierto<Integer> th = crearTablaHashAbierto();
        TablaHashAbierto<Integer> clon = th.clone();
        assert th.toString().equals(clon.toString()) : "La tabla hash debe ser igual a su clon";
    }

    /**
     * Crea y devuelve una Tabla Hash Abierto de prueba.
     *
     * @return la tabla hash
     */
    public static TablaHashAbierto<Integer> crearTablaHashAbierto() {
        TablaHashAbierto<Integer> th = new TablaHashAbierto<>();
        Integer[] elementos = crearElementos();

        for (int i = 0; i < elementos.length; i++) {
            th.insertar(elementos[i]);
        }

        return th;
    }

    /**
     * Crea un arreglo de enteros para la tabla de prueba.
     *
     * @return el arreglo de enteros
     */
    private static Integer[] crearElementos() {
        Integer[] elementos = {0, 1, 64, 128, 256, 512, 1024, 2048, 8192, 16384};
        return elementos;
    }
}
