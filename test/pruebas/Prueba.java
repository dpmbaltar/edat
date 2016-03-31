package pruebas;

/**
 * Prueba implementación de todas las estructuras de datos.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Prueba {

    private static Object prueba;

    public static void main(String[] args) {
        try {
            try {
                assert false;
                System.out.println(
                    "ERROR: las pruebas deben ser ejecutadas con el parámetro "+
                    "-enableassertions (ó -ea). Leer README.md!"
                );
                System.exit(0);
            } catch (AssertionError e) {
            }

            // Inicio de pruebas
            prueba = new pruebas.lineales.estaticas.PruebaPila();
            exito();
            prueba = new pruebas.lineales.dinamicas.PruebaPila();
            exito();
            prueba = new pruebas.lineales.estaticas.PruebaCola();
            exito();
            prueba = new pruebas.lineales.dinamicas.PruebaCola();
            exito();
            prueba = new pruebas.lineales.dinamicas.PruebaLista();
            exito();
            // Fin de pruebas
            System.out.println();
            System.out.println("¡ÉXITO de prueba!");
        } catch (AssertionError e) {
            System.out.println("¡ERROR de prueba!");
            System.out.println();
            error(e);
        }
    }

    /**
     * Muestra mensaje de éxito de la prueba actual.
     */
    private static void exito() {
        System.out.println(prueba.getClass().getName() + " OK");
    }

    /**
     * Muestra mensaje de error de la prueba actual.
     */
    private static void error(AssertionError error) {
        StackTraceElement origen = error.getStackTrace()[0];
        System.out.println(error.getMessage());
        System.out.print("    en " + origen.getClassName());
        System.out.print("." + origen.getMethodName() + "()");
        System.out.print(" (" + origen.getFileName());
        System.out.print(":" + origen.getLineNumber() + ")");
        System.out.println();
    }
}
