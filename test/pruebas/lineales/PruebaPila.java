package pruebas.lineales;

import lineales.dinamicas.Pila;
//import lineales.estaticas.Pila;

/**
 * Prueba implementación de Pila dinámica/estática.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaPila {
    
    public PruebaPila() {
        pruebaApilar();
        pruebaObtenerTope();
        pruebaDesapilar();
        pruebaEsVacia();
        pruebaVaciar();
        pruebaClonar();
        pruebaToString();
    }
    
    protected void pruebaApilar() {
        Pila<Character> pila = new Pila<Character>();
        assert pila.apilar('a') : "Debe apilar 'a'";
    }
    
    protected void pruebaObtenerTope() {
        Pila<Character> pila = new Pila<Character>();
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        assert pila.obtenerTope() == 'c' : "Tope de pila debe ser 'c'";
    }
    
    protected void pruebaDesapilar() {
        Pila<Character> pila = new Pila<Character>();
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        assert pila.desapilar() : "Debe desapilar 'c' de la pila";
        assert pila.obtenerTope() == 'b' : "Tope de pila debe ser 'b'";
    }
    
    protected void pruebaEsVacia() {
        Pila<Character> pila = new Pila<Character>();
        assert pila.esVacia() : "Pila debe ser vacía";
        pila.apilar('a');
        assert pila.esVacia() == false : "Pila no debe ser vacía";
    }
    
    protected void pruebaVaciar() {
        Pila<Character> pila = new Pila<Character>();
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        pila.vaciar();
        assert pila.esVacia() : "Pila debe ser vacía";
        assert pila.obtenerTope() == null : "Tope de pila debe ser nulo";
    }
    
    protected void pruebaClonar() {
        Pila<Character> pila = new Pila<Character>();
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        Pila<Character> clon = pila.clonar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Pila debe ser igual a su clon";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Pila debe ser igual a su clon";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Pila debe ser igual a su clon";
        pila.desapilar();
        clon.desapilar();
        assert pila.esVacia() && clon.esVacia()
             : "Pila y su clon deben ser vacías";
    }
    
    protected void pruebaToString() {
        Pila<Character> pila = new Pila<Character>();
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        assert pila.toString().equals("[c b a]")
             : "Pila con elementos 'a', 'b', 'c' debe ser representada como "
             + "[c b a] en forma de cadena";
    }
}
