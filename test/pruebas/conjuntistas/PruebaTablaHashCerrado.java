package pruebas.conjuntistas;

import conjuntistas.TablaHashCerrado;

/**
 * Prueba implementación de Tabla Hash Cerrado.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaTablaHashCerrado {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaTablaHashCerrado() {
        pruebaInsertar();
        pruebaEliminar();
        pruebaPertenece();
        pruebaClone();
    }

    /**
     * Prueba {@link conjuntistas.TablaHashCerrado#insertar(Object)}.
     */
    public void pruebaInsertar() {
        TablaHashCerrado<Integer> th = new TablaHashCerrado<>();
        assert th.insertar(1) : "Debe insertar 1 en la tabla hash";
        assert th.insertar(2) : "Debe insertar 2 en la tabla hash";
        assert th.insertar(256) : "Debe insertar 256 en la tabla hash";
        assert !th.insertar(256) : "No debe insertar 256 en la tabla hash (existente)";
    }

    /**
     * Prueba {@link conjuntistas.TablaHashCerrado#eliminar(Object)}.
     */
    public void pruebaEliminar() {
        TablaHashCerrado<Integer> th = crearTablaHashCerrado();
        System.out.println(th);
        assert th.eliminar(0) : "Debe eliminar 0";
        assert th.eliminar(16384) : "Debe eliminar 16384";
        assert !th.eliminar(0) : "No debe eliminar 0 (inexistente)";
    }

    /**
     * Prueba {@link conjuntistas.TablaHashCerrado#pertenece(Object)}.
     */
    public void pruebaPertenece() {
        TablaHashCerrado<Integer> th = crearTablaHashCerrado();
        assert th.pertenece(0) : "0 debe pertenecer a la tabla";
        assert !th.pertenece(2) : "2 no debe pertenecer a la tabla";
    }

    /**
     * Prueba {@link conjuntistas.TablaHashCerrado#clone()}.
     */
    public void pruebaClone() {
        TablaHashCerrado<Integer> th = crearTablaHashCerrado();
        TablaHashCerrado<Integer> clon = th.clone();
        assert th.toString().equals(clon.toString()) : "La tabla hash debe ser igual a su clon";
    }

    /**
     * Crea y devuelve una Tabla Hash Cerrado de prueba de 10 elementos.
     *
     * @return la tabla hash
     */
    public static TablaHashCerrado<Integer> crearTablaHashCerrado() {
        TablaHashCerrado<Integer> th = new TablaHashCerrado<>(10);
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
