package pruebas.lineales.estaticas;

import lineales.estaticas.Cola;

/**
 * Prueba implementación de Cola estática (de enteros).
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaCola {

    protected Cola<Integer> cola;

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
        cola = new Cola<Integer>();
    }

    /**
     * Prueba lineales.estaticas.Cola.poner().
     */
    protected void pruebaPoner() {
        assert cola.poner(1) : "Debe poner 1";
    }

    /**
     * Prueba lineales.estaticas.Cola.obtenerFrente().
     */
    protected void pruebaObtenerFrente() {
        assert cola.obtenerFrente() == null
             : "Frente de cola vacía debe ser nulo";
        cola.poner(1);
        cola.poner(2);
        cola.poner(3);
        assert cola.obtenerFrente() == 1
             : "Frente de cola debe ser 1";
    }

    /**
     * Prueba lineales.estaticas.Cola.sacar().
     */
    protected void pruebaSacar() {
        assert !cola.sacar() : "No debe sacar de cola vacía";
        cola.poner(1);
        cola.poner(2);
        cola.poner(3);
        assert cola.sacar() : "Debe sacar 1";
        assert cola.obtenerFrente() == 2
             : "Frente de cola debe ser 2";
    }

    /**
     * Prueba lineales.estaticas.Cola.esVacia().
     */
    protected void pruebaEsVacia() {
        assert cola.esVacia() : "Cola debe ser vacía";
        cola.poner(1);
        assert !cola.esVacia() : "Cola no debe ser vacía";
    }

    /**
     * Prueba lineales.estaticas.Cola.vaciar().
     */
    protected void pruebaVaciar() {
        cola.poner(1);
        cola.poner(2);
        cola.poner(3);
        cola.vaciar();
        assert !cola.sacar() : "No debe sacar de cola vacía";
        assert cola.obtenerFrente() == null : "Frente de cola debe ser nulo";
        assert cola.esVacia() : "Cola debe ser vacía";
    }

    /**
     * Prueba lineales.estaticas.Cola.clonar().
     */
    protected void pruebaClonar() {
        cola.poner(1);
        cola.poner(2);
        cola.poner(3);
        Cola<Integer> clon = cola.clonar();
        assert cola.obtenerFrente() == clon.obtenerFrente()
             : "Frente de cola debe ser igual al de su clon (1)";
        cola.sacar();
        clon.sacar();
        assert cola.obtenerFrente() == clon.obtenerFrente()
             : "Frente de cola debe ser igual al de su clon (2)";
        cola.sacar();
        clon.sacar();
        assert cola.obtenerFrente() == clon.obtenerFrente()
             : "Frente de cola debe ser igual al de su clon (3)";
        cola.sacar();
        clon.sacar();
        assert cola.esVacia() && clon.esVacia()
             : "Cola y su clon deben ser vacías";
    }

    /**
     * Prueba lineales.estaticas.Cola.toString().
     */
    protected void pruebaToString() {
        cola.poner(1);
        cola.poner(2);
        cola.poner(3);
        assert cola.toString().equals("[1, 2, 3]")
             : "Cola con elementos 1, 2, 3 debe ser representada como "
             + "[1, 2, 3] en forma de cadena";
    }
}
