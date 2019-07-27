package pruebas.conjuntistas;

import conjuntistas.TablaHashAbierto;

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
    }

    /**
     * Prueba {@link conjuntistas.TablaHashAbierto#insertar(Object)}.
     */
    public void pruebaInsertar() {
        TablaHashAbierto<Integer> th = new TablaHashAbierto<>();
        assert th.insertar(1) : "Debe insertar 1 en la tabla hash";
        assert th.insertar(2) : "Debe insertar 2 en la tabla hash";
        assert th.insertar(256) : "Debe insertar 256 en la tabla hash";
    }
}
