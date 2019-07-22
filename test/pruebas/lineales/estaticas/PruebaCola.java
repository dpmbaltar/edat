package pruebas.lineales.estaticas;

import lineales.estaticas.Cola;

/**
 * Prueba implementación de Cola estática.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaCola {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
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
     * Prueba {@link lineales.estaticas.Cola#poner(Object)}.
     */
    public void pruebaPoner() {
        Cola<Integer> cola = new Cola<>();
        assert cola.poner(1) : "Debe poner 1";
    }

    /**
     * Prueba {@link lineales.estaticas.Cola#obtenerFrente()}.
     */
    public void pruebaObtenerFrente() {
        Cola<Integer> cola = new Cola<>();
        assert cola.obtenerFrente() == null : "Frente de cola vacía debe ser nulo";
        cola = crearCola();
        assert cola.obtenerFrente() == 1 : "Frente de cola debe ser 1";
    }

    /**
     * Prueba {@link lineales.estaticas.Cola#sacar()}.
     */
    public void pruebaSacar() {
        Cola<Integer> cola = new Cola<>();
        assert !cola.sacar() : "No debe sacar de cola vacía";
        cola = crearCola();
        assert cola.sacar() : "Debe sacar 1";
        assert cola.obtenerFrente() == 2 : "Frente de cola debe ser 2";
    }

    /**
     * Prueba {@link lineales.estaticas.Cola#esVacia()}.
     */
    public void pruebaEsVacia() {
        Cola<Integer> cola = new Cola<>();
        assert cola.esVacia() : "Cola debe ser vacía";
        cola.poner(1);
        assert !cola.esVacia() : "Cola no debe ser vacía";
    }

    /**
     * Prueba {@link lineales.estaticas.Cola#vaciar()}.
     */
    public void pruebaVaciar() {
        Cola<Integer> cola = crearCola();
        cola.vaciar();
        assert !cola.sacar() : "No debe sacar de cola vacía";
        assert cola.obtenerFrente() == null : "Frente de cola debe ser nulo";
        assert cola.esVacia() : "Cola debe ser vacía";
    }

    /**
     * Prueba {@link lineales.estaticas.Cola#clonar()}.
     */
    public void pruebaClonar() {
        Cola<Integer> cola = crearCola();
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
        assert cola.esVacia() && clon.esVacia() : "Cola y su clon deben ser vacías";
    }

    /**
     * Prueba {@link lineales.estaticas.Cola#toString()}.
     */
    public void pruebaToString() {
        Cola<Integer> cola = crearCola();
        assert cola.toString().equals("[1, 2, 3]") : "La cola debe ser representada como \"[1, 2, 3]\"";
    }

    /**
     * Crea una cola de enteros de prueba con elementos 1, 2 y 3.
     *
     * @return la cola de enteros
     */
    public static Cola<Integer> crearCola() {
        Cola<Integer> cola = new Cola<>();
        cola.poner(1);
        cola.poner(2);
        cola.poner(3);

        return cola;
    }
}
