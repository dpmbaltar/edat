package pruebas.lineales.dinamicas;

//import lineales.estaticas.PilaInt;
import lineales.dinamicas.PilaInt;

public class PruebaPila {

    /**
     * 
     * @param arg
     */
    public static void main(String[] arg) {
        try {
            pruebaPilaInt();
        } catch (AssertionError ae) {
            System.out.println(ae.getMessage());
        } finally {
            System.out.println("Prueba PilaInt OK");
        }
    }

    public static void pruebaPilaInt() {
        PilaInt p1 = new PilaInt(), p2;

        // Probar apilar()
        assert p1.apilar(1);
        assert p1.apilar(2);
        assert p1.apilar(3);
        assert p1.apilar(4);

        // Probar obtenerTope()
        assert p1.obtenerTope() == 4;

        // Probar toString()
        assert p1.toString().equals("[4, 3, 2, 1]");

        p2 = p1.clonar();

        // Probar clonar()
        assert p1.toString().equals(p2.toString());

        // Probar desapilar()
        assert p2.desapilar();
        assert p2.desapilar();
        assert p2.desapilar();
        assert p2.desapilar();
        assert p2.desapilar() == false;

        p1.vaciar();

        // Probar vaciar()
        assert p1.toString().equals("[]");

        // Probar esVacia()
        assert p1.esVacia();
        assert p2.esVacia();
    }
}
