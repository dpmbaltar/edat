package pruebas.lineales;

import lineales.dinamicas.Pila;
//import lineales.estaticas.Pila;

/**
 * Prueba implementación de Pila dinámica/estática.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class PruebaPila {

    public static void main(String[] arg) {
        try {
            pruebaApilar();
            pruebaObtenerTope();
            pruebaDesapilar();
            pruebaEsVacia();
            pruebaVaciar();
            pruebaClonar();
            pruebaToString();
            System.out.println("Prueba OK");
        } catch (AssertionError e) {
            System.out.println("Error de prueba: " + e.getMessage());
            System.out.println("Detalles: ");
            e.printStackTrace();
        }
    }
    
    protected static void pruebaApilar() {
        Pila<Character> pila = new Pila<Character>();
        assert pila.apilar('a') : "Debe apilar 'a'";
    }
    
    protected static void pruebaObtenerTope() {
        Pila<Character> pila = new Pila<Character>();
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        assert pila.obtenerTope() == 'c' : "Tope de pila debe ser 'c'";
    }
    
    protected static void pruebaDesapilar() {
        Pila<Character> pila = new Pila<Character>();
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        assert pila.desapilar() : "Debe desapilar 'c' de la pila";
        assert pila.obtenerTope() == 'b' : "Tope de pila debe ser 'b'";
    }

    protected static void pruebaEsVacia() {
        Pila<Character> pila = new Pila<Character>();
        assert pila.esVacia() : "Pila debe ser vacía";
        pila.apilar('a');
        assert pila.esVacia() == false : "Pila no debe ser vacía";
    }
    
    protected static void pruebaVaciar() {
        Pila<Character> pila = new Pila<Character>();
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        pila.vaciar();
        assert pila.esVacia() : "Pila debe ser vacía";
        assert pila.obtenerTope() == null : "Tope de pila debe ser nulo";
    }
    
    protected static void pruebaClonar() {
        Pila<Character> pila = new Pila<Character>();
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        Pila<Character> clon = pila.clonar();
        assert pila.obtenerTope() == clon.obtenerTope() : "Pila debe ser igual a su clon";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope() == clon.obtenerTope() : "Pila debe ser igual a su clon";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope() == clon.obtenerTope() : "Pila debe ser igual a su clon";
        pila.desapilar();
        clon.desapilar();
        assert pila.esVacia() && clon.esVacia() : "Pila y su clon deben ser vacías";
    }

    protected static void pruebaToString() {
        Pila<Character> pila = new Pila<Character>();
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        assert pila.toString().equals("[c b a]") : "Pila con elementos 'a', 'b', 'c' debe ser representada como [c b a] en forma de cadena";
    }
}
