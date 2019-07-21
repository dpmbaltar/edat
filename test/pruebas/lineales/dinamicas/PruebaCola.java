package pruebas.lineales.dinamicas;

import lineales.dinamicas.Cola;

/**
 * Prueba implementación de Cola dinámica.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaCola {

    public PruebaCola() {
        pruebaPoner();
        pruebaObtenerFrente();
        pruebaSacar();
        pruebaEsVacia();
        pruebaVaciar();
        pruebaClonar();
        pruebaToString();
    }

    /**
     * Prueba lineales.dinamicas.Cola.poner().
     */
    private void pruebaPoner() {
        Cola<String> cola = new Cola<>();
        assert cola.poner("Uno") : "Debe poner \"Uno\"";
    }

    /**
     * Prueba lineales.dinamicas.Cola.obtenerFrente().
     */
    private void pruebaObtenerFrente() {
        Cola<String> cola = new Cola<>();
        assert cola.obtenerFrente() == null : "Frente de cola vacía debe ser nulo";
        cola.poner("Uno");
        assert cola.obtenerFrente().equals("Uno") : "Frente de cola debe ser \"Uno\"";
    }

    /**
     * Prueba lineales.dinamicas.Cola.sacar().
     */
    private void pruebaSacar() {
        Cola<String> cola = new Cola<>();
        assert !cola.sacar() : "No debe sacar de cola vacía";
        cola.poner("Uno");
        cola.poner("Dos");
        assert cola.sacar() : "Debe sacar \"Uno\"";
        assert cola.obtenerFrente().equals("Dos") : "Frente de cola debe ser \"Dos\"";
    }

    /**
     * Prueba lineales.dinamicas.Cola.esVacia().
     */
    private void pruebaEsVacia() {
        Cola<String> cola = new Cola<>();
        assert cola.esVacia() : "Cola debe ser vacía";
        cola.poner("Uno");
        assert !cola.esVacia() : "Cola no debe ser vacía";
    }

    /**
     * Prueba lineales.dinamicas.Cola.vaciar().
     */
    private void pruebaVaciar() {
        Cola<String> cola = crearCola();
        cola.vaciar();
        assert !cola.sacar() : "No debe sacar de cola vacía";
        assert cola.obtenerFrente() == null : "Frente de cola debe ser nulo";
        assert cola.esVacia() : "Cola debe ser vacía";
    }

    /**
     * Prueba lineales.dinamicas.Cola.clonar().
     */
    private void pruebaClonar() {
        Cola<String> cola = crearCola();
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
        assert cola.esVacia() && clon.esVacia() : "Cola y su clon deben ser vacías";
    }

    /**
     * Prueba lineales.dinamicas.Cola.toString().
     */
    private void pruebaToString() {
        Cola<String> cola = crearCola();
        assert cola.toString().equals("[Uno, Dos, Tres]") : "La cola debe ser representada como \"[Uno, Dos, Tres]\"";
    }

    /**
     * Crea una cola de cadenas de prueba con elementos "Uno", "Dos" y "Tres".
     *
     * @return la cola de cadenas
     */
    private static Cola<String> crearCola() {
        Cola<String> cola = new Cola<>();
        cola.poner("Uno");
        cola.poner("Dos");
        cola.poner("Tres");

        return cola;
    }
}
