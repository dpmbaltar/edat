package pruebas.lineales.dinamicas;

import lineales.dinamicas.ColaPrioridad;

/**
 * Prueba implementación de Cola de Prioridad dinámica.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaColaPrioridad {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaColaPrioridad() {
        pruebaInsertar();
        pruebaEliminarFrente();
        pruebaObtenerFrente();
        pruebaEsVacia();
        pruebaVaciar();
        pruebaClone();
        pruebaToString();
    }

    /**
     * Prueba {@link lineales.dinamicas.ColaPrioridad#insertar(Object)}.
     */
    public void pruebaInsertar() {
        ColaPrioridad<String, Integer> cola = new ColaPrioridad<>();
        assert cola.insertar("Uno", 1) : "Debe poner \"Uno\"";
    }

    /**
     * Prueba {@link lineales.dinamicas.ColaPrioridad#eliminarFrente()}.
     */
    public void pruebaEliminarFrente() {
        ColaPrioridad<String, Integer> cola = new ColaPrioridad<>();
        assert !cola.eliminarFrente() : "No debe sacar de cola vacía";
        cola.insertar("Dos", 2);
        cola.insertar("Tres", 2);
        cola.insertar("Uno", 1);
        assert cola.eliminarFrente() : "Debe sacar \"Uno\"";
        assert cola.obtenerFrente().equals("Dos") : "Frente de cola debe ser \"Dos\"";
        cola = crearColaPrioridad();
        cola.eliminarFrente();
        cola.eliminarFrente();
        cola.eliminarFrente();
        cola.eliminarFrente();
        cola.eliminarFrente();
        cola.eliminarFrente();
        cola.eliminarFrente();
        cola.eliminarFrente();
        cola.eliminarFrente();
        cola.eliminarFrente();
        assert !cola.eliminarFrente() : "No debe sacar de cola vacía";
        assert cola.esVacia() : "Cola debe ser vacía";
    }

    /**
     * Prueba {@link lineales.dinamicas.ColaPrioridad#obtenerFrente()}.
     */
    public void pruebaObtenerFrente() {
        ColaPrioridad<String, Integer> cola = new ColaPrioridad<>();
        assert cola.obtenerFrente() == null : "Frente de cola vacía debe ser nulo";
        cola.insertar("Uno", 1);
        assert cola.obtenerFrente().equals("Uno") : "Frente de cola debe ser \"Uno\"";
    }

    /**
     * Prueba {@link lineales.dinamicas.ColaPrioridad#esVacia()}.
     */
    public void pruebaEsVacia() {
        ColaPrioridad<String, Integer> cola = new ColaPrioridad<>();
        assert cola.esVacia() : "Cola debe ser vacía";
        cola.insertar("Uno", 1);
        assert !cola.esVacia() : "Cola no debe ser vacía";
    }

    /**
     * Prueba {@link lineales.dinamicas.ColaPrioridad#vaciar()}.
     */
    public void pruebaVaciar() {
        ColaPrioridad<String, Integer> cola = crearColaPrioridad();
        cola.vaciar();
        assert !cola.eliminarFrente() : "No debe sacar de cola vacía";
        assert cola.obtenerFrente() == null : "Frente de cola debe ser nulo";
        assert cola.esVacia() : "Cola debe ser vacía";
    }

    /**
     * Prueba {@link lineales.dinamicas.ColaPrioridad#clone()}.
     */
    public void pruebaClone() {
        ColaPrioridad<String, Integer> cola = crearColaPrioridad();
        ColaPrioridad<String, Integer> clon = cola.clone();
        assert cola.obtenerFrente().equals(clon.obtenerFrente())
                : "Frente de cola debe ser igual al de su clon (\"Uno\")";
        cola.eliminarFrente();
        clon.eliminarFrente();
        assert cola.obtenerFrente().equals(clon.obtenerFrente())
                : "Frente de cola debe ser igual al de su clon (\"Dos\")";
        cola.eliminarFrente();
        clon.eliminarFrente();
        assert cola.obtenerFrente().equals(clon.obtenerFrente())
                : "Frente de cola debe ser igual al de su clon (\"Tres\")";
    }

    /**
     * Prueba {@link lineales.dinamicas.ColaPrioridad#toString()}.
     */
    public void pruebaToString() {
        ColaPrioridad<String, Integer> cola = crearColaPrioridad();
        assert cola.toString().equals(
                "[0:[Uno], 1:[Dos, Tres], 2:[Cuatro, Cinco, Seis, Siete], 3:[Ocho, Nueve], 4:[Diez]]")
                : "La cola debe ser representada como "
                        + "\"[0:[Uno], 1:[Dos, Tres], 2:[Cuatro, Cinco, Seis, Siete], 3:[Ocho, Nueve], 4:[Diez]]\"";
    }

    /**
     * Crea una cola de cadenas y prioridad entera de prueba, con elementos "Uno", "Dos", ..., "Diez".
     *
     * @return la cola de cadenas
     */
    public static ColaPrioridad<String, Integer> crearColaPrioridad() {
        ColaPrioridad<String, Integer> cola = new ColaPrioridad<>();
        cola.insertar("Diez", 4);
        cola.insertar("Cuatro", 2);
        cola.insertar("Cinco", 2);
        cola.insertar("Seis", 2);
        cola.insertar("Dos", 1);
        cola.insertar("Tres", 1);
        cola.insertar("Ocho", 3);
        cola.insertar("Nueve", 3);
        cola.insertar("Siete", 2);
        cola.insertar("Uno", 0);

        return cola;
    }
}
