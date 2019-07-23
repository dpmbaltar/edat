package pruebas.lineales.dinamicas;

import lineales.dinamicas.Pila;

/**
 * Prueba implementación de Pila dinámica.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaPila {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaPila() {
        pruebaApilar();
        pruebaObtenerTope();
        pruebaDesapilar();
        pruebaEsVacia();
        pruebaVaciar();
        pruebaClone();
        pruebaToString();
    }

    /**
     * Prueba {@link lineales.dinamicas.Pila#apilar(Object)}.
     */
    public void pruebaApilar() {
        Pila<String> pila = new Pila<>();
        assert pila.apilar("Uno") : "Debe apilar \"Uno\"";
    }

    /**
     * Prueba {@link lineales.dinamicas.Pila#obtenerTope()}.
     */
    public void pruebaObtenerTope() {
        Pila<String> pila = new Pila<>();
        assert pila.obtenerTope() == null : "Tope de pila vacía debe ser nulo";
        pila = crearPila();
        assert pila.obtenerTope().equals("Tres") : "Tope de pila debe ser \"Tres\"";
    }

    /**
     * Prueba {@link lineales.dinamicas.Pila#desapilar()}.
     */
    public void pruebaDesapilar() {
        Pila<String> pila = new Pila<>();
        assert !pila.desapilar() : "No debe desapilar pila vacía";
        pila = crearPila();
        assert pila.desapilar() : "Debe desapilar \"Tres\"";
        assert pila.obtenerTope().equals("Dos") : "Tope de pila debe ser \"Dos\"";
    }

    /**
     * Prueba {@link lineales.dinamicas.Pila#esVacia()}.
     */
    public void pruebaEsVacia() {
        Pila<String> pila = new Pila<>();
        assert pila.esVacia() : "Pila debe ser vacía";
        pila.apilar("Uno");
        assert !pila.esVacia() : "Pila no debe ser vacía";
    }

    /**
     * Prueba {@link lineales.dinamicas.Pila#vaciar()}.
     */
    public void pruebaVaciar() {
        Pila<String> pila = crearPila();
        pila.vaciar();
        assert !pila.desapilar() : "No debe desapilar pila vacía";
        assert pila.obtenerTope() == null : "Tope de pila debe ser nulo";
        assert pila.esVacia() : "Pila debe ser vacía";
    }

    /**
     * Prueba {@link lineales.dinamicas.Pila#clone()}.
     */
    public void pruebaClone() {
        Pila<String> pila = crearPila();
        Pila<String> clon = pila.clone();
        assert pila.obtenerTope().equals(clon.obtenerTope())
                : "Tope de pila debe ser igual al de su clon (\"Tres\")";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope().equals(clon.obtenerTope())
                : "Tope de pila debe ser igual al de su clon (\"Dos\")";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope().equals(clon.obtenerTope())
                : "Tope de pila debe ser igual al de su clon (\"Uno\")";
        pila.desapilar();
        clon.desapilar();
        assert pila.esVacia() && clon.esVacia() : "Pila y su clon deben ser vacías";
    }

    /**
     * Prueba {@link lineales.dinamicas.Pila#toString()}.
     */
    public void pruebaToString() {
        Pila<String> pila = crearPila();
        assert pila.toString().equals("[Tres, Dos, Uno]") : "La pila debe ser representada como \"[Uno, Dos, Tres]\"";
    }

    /**
     * Crea una pila de cadenas de prueba con elementos "Uno", "Dos" y "Tres".
     *
     * @return la pila de cadenas
     */
    public static Pila<String> crearPila() {
        Pila<String> pila = new Pila<>();
        pila.apilar("Uno");
        pila.apilar("Dos");
        pila.apilar("Tres");

        return pila;
    }
}
