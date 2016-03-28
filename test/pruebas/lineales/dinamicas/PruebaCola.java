package pruebas.lineales.dinamicas;

import lineales.dinamicas.Cola;

/**
 * Prueba implementación de Cola dinámica (de cadenas).
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaCola {

    protected Cola<String> cola;

    public PruebaCola() {
        preparar();
        pruebaPoner();
        preparar();
        pruebaObtenerFrente();
        preparar();
        pruebaSacar();
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
        cola = new Cola<String>();
    }

    /**
     * Prueba lineales.dinamicas.Cola.poner().
     */
    protected void pruebaPoner() {
        assert cola.poner("Uno") : "Debe poner \"Uno\"";
    }

    /**
     * Prueba lineales.dinamicas.Cola.obtenerFrente().
     */
    protected void pruebaObtenerFrente() {
        assert cola.obtenerFrente() == null
             : "Frente de cola vacía debe ser nulo";
        cola.poner("Uno");
        cola.poner("Dos");
        cola.poner("Tres");
        assert cola.obtenerFrente().equals("Uno")
             : "Frente de cola debe ser \"Uno\"";
    }

    /**
     * Prueba lineales.dinamicas.Cola.sacar().
     */
    protected void pruebaSacar() {
        assert !cola.sacar() : "No debe sacar de cola vacía";
        cola.poner("Uno");
        cola.poner("Dos");
        cola.poner("Tres");
        assert cola.sacar() : "Debe sacar \"Uno\"";
        assert cola.obtenerFrente().equals("Dos")
             : "Frente de cola debe ser \"Dos\"";
    }

    /**
     * Prueba lineales.dinamicas.Cola.esVacia().
     */
    protected void pruebaEsVacia() {
        assert cola.esVacia() : "Cola debe ser vacía";
        cola.poner("Uno");
        assert !cola.esVacia() : "Cola no debe ser vacía";
    }

    /**
     * Prueba lineales.dinamicas.Cola.vaciar().
     */
    protected void pruebaVaciar() {
        cola.poner("Uno");
        cola.poner("Dos");
        cola.poner("Tres");
        cola.vaciar();
        assert !cola.sacar() : "No debe sacar de cola vacía";
        assert cola.obtenerFrente() == null : "Frente de cola debe ser nulo";
        assert cola.esVacia() : "Cola debe ser vacía";
    }

    /**
     * Prueba lineales.dinamicas.Cola.clonar().
     */
    protected void pruebaClonar() {
        cola.poner("Uno");
        cola.poner("Dos");
        cola.poner("Tres");
        Cola<String> clon = cola.clonar();
        assert cola.obtenerFrente().equals(clon.obtenerFrente())
             : "Frente de cola debe ser igual al de su clon (\"Uno\")";
        cola.sacar();
        clon.sacar();
        assert cola.obtenerFrente().equals(clon.obtenerFrente())
             : "Frente de cola debe ser igual al de su clon (\"Dos\")";
        cola.sacar();
        clon.sacar();
        assert cola.obtenerFrente().equals(clon.obtenerFrente())
             : "Frente de cola debe ser igual al de su clon (\"Tres\")";
        cola.sacar();
        clon.sacar();
        assert cola.esVacia() && clon.esVacia()
             : "Cola y su clon deben ser vacías";
    }

    /**
     * Prueba lineales.dinamicas.Cola.toString().
     */
    protected void pruebaToString() {
        cola.poner("Uno");
        cola.poner("Dos");
        cola.poner("Tres");
        assert cola.toString().equals("[Uno, Dos, Tres]")
             : "Cola con elementos \"Uno\", \"Dos\", \"Tres\" debe ser "
             + "representada como [Uno, Dos, Tres] en forma de cadena";
    }
}
