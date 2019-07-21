package pruebas.lineales.estaticas;

import lineales.estaticas.Pila;

/**
 * Prueba implementación de Pila estática.
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

    /**
     * Prueba lineales.estaticas.Pila.apilar().
     */
    private void pruebaApilar() {
        Pila<Integer> pila = new Pila<>();
        assert pila.apilar(1) : "Debe apilar 1";
    }

    /**
     * Prueba lineales.estaticas.Pila.obtenerTope().
     */
    private void pruebaObtenerTope() {
        Pila<Integer> pila = new Pila<>();
        assert pila.obtenerTope() == null : "Tope de pila vacía debe ser nulo";
        pila = crearPila();
        assert pila.obtenerTope() == 3 : "Tope de pila debe ser 3";
    }

    /**
     * Prueba lineales.estaticas.Pila.desapilar().
     */
    private void pruebaDesapilar() {
        Pila<Integer> pila = new Pila<>();
        assert !pila.desapilar() : "No debe desapilar pila vacía";
        pila = crearPila();
        assert pila.desapilar() : "Debe desapilar 3";
        assert pila.obtenerTope() == 2 : "Tope de pila debe ser 2";
    }

    /**
     * Prueba lineales.estaticas.Pila.esVacia().
     */
    private void pruebaEsVacia() {
        Pila<Integer> pila = new Pila<>();
        assert pila.esVacia() : "Pila debe ser vacía";
        pila.apilar(1);
        assert !pila.esVacia() : "Pila no debe ser vacía";
    }

    /**
     * Prueba lineales.estaticas.Pila.vaciar().
     */
    private void pruebaVaciar() {
        Pila<Integer> pila = crearPila();
        pila.vaciar();
        assert !pila.desapilar() : "No debe desapilar pila vacía";
        assert pila.obtenerTope() == null : "Tope de pila debe ser nulo";
        assert pila.esVacia() : "Pila debe ser vacía";
    }

    /**
     * Prueba lineales.estaticas.Pila.clonar().
     */
    private void pruebaClonar() {
        Pila<Integer> pila = crearPila();
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
        assert pila.esVacia() && clon.esVacia() : "Pila y su clon deben ser vacías";
    }

    /**
     * Prueba lineales.estaticas.Pila.toString().
     */
    private void pruebaToString() {
        Pila<Integer> pila = crearPila();
        assert pila.toString().equals("[3, 2, 1]") : "La pila debe ser representada como \"[3, 2, 1]\"";
    }

    /**
     * Crea una pila de enteros de prueba con elementos 1, 2 y 3.
     *
     * @return la pila de enteros
     */
    private static Pila<Integer> crearPila() {
        Pila<Integer> pila = new Pila<>();
        pila.apilar(1);
        pila.apilar(2);
        pila.apilar(3);

        return pila;
    }
}
