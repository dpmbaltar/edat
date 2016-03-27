package pruebas.lineales.dinamicas;

import lineales.dinamicas.Cola;

/**
 * Prueba implementación de Cola dinámica (de caracteres).
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaCola {

    protected Cola<Character> cola;

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
        cola = new Cola<Character>();
    }

    protected void pruebaPoner() {
        assert cola.poner('a') : "Debe poner 'a' en el frente";
    }

    protected void pruebaObtenerFrente() {
        cola.poner('a');
        cola.poner('b');
        cola.poner('c');
        assert cola.obtenerFrente() == 'a' : "Frente de cola debe ser 'a'";
    }

    protected void pruebaSacar() {
        cola.poner('a');
        cola.poner('b');
        cola.poner('c');
        assert cola.sacar() : "Debe sacar 'a' de la cola";
        assert cola.obtenerFrente() == 'b' : "Frente de cola debe ser 'b'";
    }

    protected void pruebaEsVacia() {
        assert cola.esVacia() : "Cola debe ser vacía";
        cola.poner('a');
        assert cola.esVacia() == false : "Cola no debe ser vacía";
    }

    protected void pruebaVaciar() {
        cola.poner('a');
        cola.poner('b');
        cola.poner('c');
        cola.vaciar();
        assert cola.esVacia() : "Cola debe ser vacía";
        assert cola.obtenerFrente() == null : "Frente de cola debe ser nulo";
    }

    protected void pruebaClonar() {
        cola.poner('a');
        cola.poner('b');
        cola.poner('c');
        Cola<Character> clon = cola.clonar();
        assert cola.obtenerFrente() == clon.obtenerFrente()
             : "Cola debe ser igual a su clon";
        cola.sacar();
        clon.sacar();
        assert cola.obtenerFrente() == clon.obtenerFrente()
             : "Cola debe ser igual a su clon";
        cola.sacar();
        clon.sacar();
        assert cola.obtenerFrente() == clon.obtenerFrente()
             : "Cola debe ser igual a su clon";
        cola.sacar();
        clon.sacar();
        assert cola.esVacia() && clon.esVacia()
             : "Cola y su clon deben ser vacías";
    }

    protected void pruebaToString() {
        cola.poner('a');
        cola.poner('b');
        cola.poner('c');
        assert cola.toString().equals("[a b c]")
             : "Cola con elementos 'a', 'b', 'c' debe ser representada como "
             + "[a b c] en forma de cadena";
    }
}
