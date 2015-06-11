package pruebas.conjuntistas;

import conjuntistas.ArbolBB;

/**
 * Prueba implementación de Árbol Binario de Búsqueda.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaArbolBB {

    public static void main(String[] args) {
        try {
            // Inicio de pruebas
            pruebaInsertar();
            pruebaEliminar();
            pruebaPertenece();
            pruebaMaximo();
            pruebaMinimo();
            pruebaVacio();
            pruebaVaciar();
            pruebaListar();
            pruebaListarRango();
            pruebaClonar();
            // Fin de pruebas
            System.out.println("Prueba OK");
        } catch (AssertionError e) {
            System.out.println("Error de prueba: " + e.getMessage());
            System.out.println("Detalles:");
            e.printStackTrace();
        }
    }

    private static void pruebaInsertar() {
        
    }

    private static void pruebaEliminar() {
        
    }

    private static void pruebaPertenece() {
        
    }

    private static void pruebaMaximo() {
        
    }

    private static void pruebaMinimo() {
        
    }

    private static void pruebaVacio() {
        
    }

    private static void pruebaVaciar() {
        
    }

    private static void pruebaListar() {
        
    }

    private static void pruebaListarRango() {
        
    }

    private static void pruebaClonar() {
        
    }
}
