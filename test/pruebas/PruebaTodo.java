package pruebas;

import pruebas.lineales.PruebaCola;
import pruebas.lineales.PruebaPila;
import pruebas.lineales.PruebaLista;

/**
 * Prueba implementación de todas las estructuras de datos.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaTodo {
    
    public static void main(String[] args) {
        try {
            PruebaCola pruebaCola = new PruebaCola();
            PruebaPila pruebaPila = new PruebaPila();
            PruebaLista pruebaLista = new PruebaLista();
            System.out.println("ÉXITO de prueba.");
        } catch (AssertionError e) {
            System.out.println("ERROR de prueba: " + e.getMessage());
            System.out.println("Detalles: ");
            e.printStackTrace();
        }
    }
}
