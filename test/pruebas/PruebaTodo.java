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
        	System.out.println(prueba.getClass().getName() + " OK");
        	prueba = new pruebas.lineales.dinamicas.PruebaPila();
        	System.out.println(prueba.getClass().getName() + " OK");
        	prueba = new PruebaCola();
        	System.out.println(prueba.getClass().getName() + " OK");
        	prueba = new PruebaLista();
        	System.out.println(prueba.getClass().getName() + " OK");
        	System.out.println();
            System.out.println("¡ÉXITO de prueba!");
        } catch (AssertionError error) {
        	StackTraceElement origen = error.getStackTrace()[0];
            System.out.println("¡ERROR de prueba!");
            System.out.println();
            System.out.println(error.getMessage());
            System.out.print("    en " + origen.getClassName());
            System.out.print("." + origen.getMethodName() + "()");
            System.out.print(" (" + origen.getFileName());
            System.out.print(":" + origen.getLineNumber() + ")");
            System.out.println();
        }
    }
}
