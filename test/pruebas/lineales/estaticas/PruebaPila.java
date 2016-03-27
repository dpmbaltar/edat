package pruebas.lineales.estaticas;

import lineales.estaticas.Pila;

/**
 * Prueba implementación de Pila estática (de caracteres).
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaPila {

	protected Pila<Character> pila;
	
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
    	pila = new Pila<Character>();
    }

    /**
     * Prueba lineales.estaticas.Pila.apilar().
     */
    protected void pruebaApilar() {
        assert pila.apilar('a') : "Debe apilar 'a'";
    }

    /**
     * Prueba lineales.estaticas.Pila.obtenerTope().
     */
    protected void pruebaObtenerTope() {
    	assert (Character)pila.obtenerTope() == null
             : "Tope de pila vacía debe ser nulo";
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        assert (Character)pila.obtenerTope() == 'c'
             : "Tope de pila debe ser 'c'";
    }

    /**
     * Prueba lineales.estaticas.Pila.desapilar().
     */
    protected void pruebaDesapilar() {
    	assert !pila.desapilar() : "No debe desapilar pila vacía";
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        assert pila.desapilar() : "Debe desapilar 'c'";
        assert (Character)pila.obtenerTope() == 'b'
        	 : "Tope de pila debe ser 'b'";
    }

    /**
     * Prueba lineales.estaticas.Pila.esVacia().
     */
    protected void pruebaEsVacia() {
        assert pila.esVacia() : "Pila debe ser vacía";
        pila.apilar('a');
        assert !pila.esVacia() : "Pila no debe ser vacía";
    }

    /**
     * Prueba lineales.estaticas.Pila.vaciar().
     */
    protected void pruebaVaciar() {
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        pila.vaciar();
        assert pila.esVacia() : "Pila debe ser vacía";
        assert pila.obtenerTope() == null : "Tope de pila debe ser nulo";
    }

    /**
     * Prueba lineales.estaticas.Pila.clonar().
     */
	protected void pruebaClonar() {
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        Pila<Character> clon = (Pila<Character>)pila.clonar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Tope de pila debe ser igual al de su clon ('c')";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Tope de pila debe ser igual al de su clon ('b')";
        pila.desapilar();
        clon.desapilar();
        assert pila.obtenerTope() == clon.obtenerTope()
             : "Tope de pila debe ser igual al de su clon ('a')";
        pila.desapilar();
        clon.desapilar();
        assert pila.esVacia() && clon.esVacia()
             : "Pila y su clon deben ser vacías";
    }

	/**
     * Prueba lineales.estaticas.Pila.toString().
     */
    protected void pruebaToString() {
        pila.apilar('a');
        pila.apilar('b');
        pila.apilar('c');
        assert pila.toString().equals("[c b a]")
             : "Pila con elementos 'a', 'b', 'c' debe ser representada como "
             + "[c b a] en forma de cadena";
    }
}
