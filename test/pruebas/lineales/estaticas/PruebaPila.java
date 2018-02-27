package pruebas.lineales.estaticas;

import lineales.estaticas.Pila;
//import lineales.dinamicas.Pila;

/**
 * Prueba implementación de Pila estática (de enteros).
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaPila {

    protected Pila<Integer> pila;

    public PruebaPila() {
        preparar();
        pruebaApilar();
        preparar();
        pruebaObtenerTope();
        preparar();
        pruebaDesapilar();
        preparar();
        pruebaEsVacia();
        preparar();
        pruebaVaciar();
        preparar();
        pruebaClonar();
        preparar();
        pruebaToString();
    }

    protected void preparar() {
        pila = new Pila<Integer>();
    }

    /**
     * Prueba lineales.estaticas.Pila.apilar().
     */
    protected void pruebaApilar() {
        assert pila.apilar(1) : "Debe apilar 1";
    }

    /**
     * Prueba lineales.estaticas.Pila.obtenerTope().
     */
    protected void pruebaObtenerTope() {
        assert pila.obtenerTope() == null
             : "Tope de pila vacía debe ser nulo";
        pila.apilar(1);
        pila.apilar(2);
        pila.apilar(3);
        assert pila.obtenerTope() == 3
             : "Tope de pila debe ser 3";
    }

    /**
     * Prueba lineales.estaticas.Pila.desapilar().
     */
    protected void pruebaDesapilar() {
        assert !pila.desapilar() : "No debe desapilar pila vacía";
        pila.apilar(1);
        pila.apilar(2);
        pila.apilar(3);
        assert pila.desapilar() : "Debe desapilar 3";
        assert pila.obtenerTope() == 2
             : "Tope de pila debe ser 2";
    }

    /**
     * Prueba lineales.estaticas.Pila.esVacia().
     */
    protected void pruebaEsVacia() {
        assert pila.esVacia() : "Pila debe ser vacía";
        pila.apilar(1);
        assert !pila.esVacia() : "Pila no debe ser vacía";
    }

    /**
     * Prueba lineales.estaticas.Pila.vaciar().
     */
    protected void pruebaVaciar() {
        pila.apilar(1);
        pila.apilar(2);
        pila.apilar(3);
        pila.vaciar();
        assert !pila.desapilar() : "No debe desapilar pila vacía";
        assert pila.obtenerTope() == null : "Tope de pila debe ser nulo";
        assert pila.esVacia() : "Pila debe ser vacía";
    }

    /**
     * Prueba lineales.estaticas.Pila.clonar().
     */
    protected void pruebaClonar() {
        pila.apilar(1);
        pila.apilar(2);
        pila.apilar(3);
        Pila<Integer> clon = pila.clonar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Tope de pila debe ser igual al de su clon (1)";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Tope de pila debe ser igual al de su clon (2)";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Tope de pila debe ser igual al de su clon (3)";
        pila.desapilar();
        clon.desapilar();
        assert pila.esVacia() && clon.esVacia()
             : "Pila y su clon deben ser vacías";
    }

    /**
     * Prueba lineales.estaticas.Pila.toString().
     */
    protected void pruebaToString() {
        pila.apilar(1);
        pila.apilar(2);
        pila.apilar(3);
        assert pila.toString().equals("[3, 2, 1]")
             : "Pila con elementos 1, 2, 3 debe ser representada como "
             + "[3, 2, 1] en forma de cadena";
    }
}
