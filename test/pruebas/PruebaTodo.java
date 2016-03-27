package pruebas;

import pruebas.lineales.PruebaCola;
import pruebas.lineales.PruebaLista;

/**
 * Prueba implementación de todas las estructuras de datos.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaTodo {
	
	private static Object prueba;
	
    public static void main(String[] args) {
        try {
        	prueba = new pruebas.lineales.estaticas.PruebaPila();
        	exito();
        	prueba = new pruebas.lineales.dinamicas.PruebaPila();
        	exito();
        	prueba = new PruebaCola();
        	exito();
        	prueba = new PruebaLista();
        	exito();
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
