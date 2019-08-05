package pruebas.utiles;

import utiles.Funciones;

/**
 * Prueba implementación de Funciones.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaFunciones {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaFunciones() {
        pruebaDoblamiento();
    }

    /**
     * Prueba {@link utiles.Funciones#doblamiento(int, int)}.
     */
    public static void pruebaDoblamiento() {
        System.out.println(Funciones.doblamiento(123456789, 1000));
        assert Funciones.doblamiento(123456789, 10) == 1: "Debe generar 1: (1234+5678+9)%10";
        assert Funciones.doblamiento(123456789, 100) == 68: "Debe generar 68: (123+456+789)%100";
        assert Funciones.doblamiento(123456789, 1000) == 189: "Debe generar 189: (12+34+56+78+9)%1000";
    }
}
