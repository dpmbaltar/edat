package pruebas.lineales.dinamicas;

import lineales.dinamicas.Pila;

/**
 * Prueba implementación de Pila dinámica (de cadenas).
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaPila {

    protected Pila<String> pila;

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
        pila = new Pila<String>();
    }

    /**
     * Prueba lineales.dinamicas.Pila.apilar().
     */
    protected void pruebaApilar() {
        assert pila.apilar("Uno") : "Debe apilar \"Uno\"";
    }

    /**
     * Prueba lineales.dinamicas.Pila.obtenerTope().
     */
    protected void pruebaObtenerTope() {
        assert pila.obtenerTope() == null
             : "Tope de pila vacía debe ser nulo";
        pila.apilar("Uno");
        pila.apilar("Dos");
        pila.apilar("Tres");
        assert pila.obtenerTope() == "Tres"
             : "Tope de pila debe ser \"Tres\"";
    }

    /**
     * Prueba lineales.dinamicas.Pila.desapilar().
     */
    protected void pruebaDesapilar() {
        assert !pila.desapilar() : "No debe desapilar pila vacía";
        pila.apilar("Uno");
        pila.apilar("Dos");
        pila.apilar("Tres");
        assert pila.desapilar() : "Debe desapilar \"Tres\"";
        assert pila.obtenerTope() == "Dos"
             : "Tope de pila debe ser \"Dos\"";
    }

    /**
     * Prueba lineales.dinamicas.Pila.esVacia().
     */
    protected void pruebaEsVacia() {
        assert pila.esVacia() : "Pila debe ser vacía";
        pila.apilar("Uno");
        assert !pila.esVacia() : "Pila no debe ser vacía";
    }

    /**
     * Prueba lineales.dinamicas.Pila.vaciar().
     */
    protected void pruebaVaciar() {
        pila.apilar("Uno");
        pila.apilar("Dos");
        pila.apilar("Tres");
        pila.vaciar();
        assert !pila.desapilar() : "No debe desapilar pila vacía";
        assert pila.obtenerTope() == null : "Tope de pila debe ser nulo";
        assert pila.esVacia() : "Pila debe ser vacía";
    }

    /**
     * Prueba lineales.dinamicas.Pila.clonar().
     */
    protected void pruebaClonar() {
        pila.apilar("Uno");
        pila.apilar("Dos");
        pila.apilar("Tres");
        Pila<String> clon = pila.clonar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Tope de pila debe ser igual al de su clon (\"Tres\")";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Tope de pila debe ser igual al de su clon (\"Dos\")";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Tope de pila debe ser igual al de su clon (\"Uno\")";
        pila.desapilar();
        clon.desapilar();
        assert pila.esVacia() && clon.esVacia()
             : "Pila y su clon deben ser vacías";
    }

    /**
     * Prueba lineales.dinamicas.Pila.toString().
     */
    protected void pruebaToString() {
        pila.apilar("Uno");
        pila.apilar("Dos");
        pila.apilar("Tres");
        assert pila.toString().equals("[Tres, Dos, Uno]")
             : "Pila con elementos \"Uno\", \"Dos\", \"Tres\" debe ser "
             + "representada como [Uno, Dos, Tres] en forma de cadena";
    }
}
