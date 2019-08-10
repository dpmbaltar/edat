package pruebas;

/**
 * Prueba implementación de todas las estructuras de datos.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Prueba {

    /**
     * Programa de prueba de todas las estructuras.
     *
     * @param args los argumentos
     */
    public static void main(String[] args) {
        try {
            try {
                assert false;
                System.out.println(
                        "ERROR: las pruebas deben ser ejecutadas con el parámetro de JVM -enableassertions (ó -ea). "
                            + "Leer README.md");
                System.exit(0);
            } catch (AssertionError e) {
            }

            // Inicio de prueba
            exito(new pruebas.utiles.PruebaFunciones());
            exito(new pruebas.lineales.estaticas.PruebaPila());
            exito(new pruebas.lineales.dinamicas.PruebaPila());
            exito(new pruebas.lineales.estaticas.PruebaCola());
            exito(new pruebas.lineales.dinamicas.PruebaCola());
            exito(new pruebas.lineales.dinamicas.PruebaLista());
            exito(new pruebas.jerarquicas.PruebaArbolBinario());
            exito(new pruebas.jerarquicas.PruebaArbolGenerico());
            exito(new pruebas.conjuntistas.PruebaHeapMaximo());
            exito(new pruebas.conjuntistas.PruebaHeapMinimo());
            exito(new pruebas.conjuntistas.PruebaArbolBB());
            exito(new pruebas.conjuntistas.PruebaArbolAVL());
            exito(new pruebas.conjuntistas.PruebaTablaHashAbierto());
            exito(new pruebas.conjuntistas.PruebaTablaHashCerrado());
            exito(new pruebas.grafos.dinamicas.PruebaGrafo());

            // Fin de prueba
            System.out.println();
            System.out.println("¡ÉXITO de prueba!");
        } catch (AssertionError e) {
            // Error de prueba
            System.out.println("¡ERROR de prueba!");
            System.out.println();
            error(e);
        }
    }

    /**
     * Muestra mensaje de éxito de la prueba actual, si no hay errores.
     */
    private static void exito(Object prueba) {
        System.out.println(prueba.getClass().getName() + " ha pasado todas las pruebas con éxito");
    }

    /**
     * Muestra mensaje de error de una prueba.
     */
    private static void error(AssertionError error) {
        StackTraceElement origen = error.getStackTrace()[0];
        System.out.println(error.getMessage());
        System.out.print("    en " + origen.getClassName());
        System.out.print("." + origen.getMethodName() + "()");
        System.out.println();
        System.out.print("    (" + origen.getFileName() + ":" + origen.getLineNumber() + ")");
        System.out.println();
    }
}
