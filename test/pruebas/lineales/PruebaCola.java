package pruebas.lineales;

import lineales.dinamicas.Cola;
//import lineales.estaticas.Cola;

/**
 * Prueba implementación de Cola dinámica/estática.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaCola {

    public static void main(String[] args) {
        try {
            pruebaPoner();
            pruebaObtenerFrente();
            pruebaSacar();
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
    
    protected static void pruebaPoner() {
        Cola<Character> cola = new Cola<Character>();
        assert cola.poner('a') : "Debe poner 'a' en el frente";
    }
    
    protected static void pruebaObtenerFrente() {
        Cola<Character> cola = new Cola<Character>();
        cola.poner('a');
        cola.poner('b');
        cola.poner('c');
        assert cola.obtenerFrente() == 'a' : "Frente de cola debe ser 'a'";
    }
    
    protected static void pruebaSacar() {
        Cola<Character> cola = new Cola<Character>();
        cola.poner('a');
        cola.poner('b');
        cola.poner('c');
        assert cola.sacar() : "Debe sacar 'a' de la cola";
        assert cola.obtenerFrente() == 'b' : "Frente de cola debe ser 'b'";
    }

    protected static void pruebaEsVacia() {
        Cola<Character> cola = new Cola<Character>();
        assert cola.esVacia() : "Cola debe ser vacía";
        cola.poner('a');
        assert cola.esVacia() == false : "Cola no debe ser vacía";
    }
    
    protected static void pruebaVaciar() {
        Cola<Character> cola = new Cola<Character>();
        cola.poner('a');
        cola.poner('b');
        cola.poner('c');
        cola.vaciar();
        assert cola.esVacia() : "Cola debe ser vacía";
        assert cola.obtenerFrente() == null : "Frente de cola debe ser nulo";
    }
    
    protected static void pruebaClonar() {
        Cola<Character> cola = new Cola<Character>();
        cola.poner('a');
        cola.poner('b');
        cola.poner('c');
        Cola<Character> clon = cola.clonar();
        assert cola.obtenerFrente() == clon.obtenerFrente() : "Cola debe ser igual a su clon";
        cola.sacar();
        clon.sacar();
        assert cola.obtenerFrente() == clon.obtenerFrente() : "Cola debe ser igual a su clon";
        cola.sacar();
        clon.sacar();
        assert cola.obtenerFrente() == clon.obtenerFrente() : "Cola debe ser igual a su clon";
        cola.sacar();
        clon.sacar();
        assert cola.esVacia() && clon.esVacia() : "Cola y su clon deben ser vacías";
    }

    protected static void pruebaToString() {
        Cola<Character> cola = new Cola<Character>();
        cola.poner('a');
        cola.poner('b');
        cola.poner('c');
        assert cola.toString().equals("[a b c]") : "Cola con elementos 'a', 'b', 'c' debe ser representada como [a b c] en forma de cadena";
    }
}
