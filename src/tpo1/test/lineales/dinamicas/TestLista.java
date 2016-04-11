package tpo1.test.lineales.dinamicas;

import tpo1.lineales.dinamicas.ListaInt;
import tpo1.lineales.dinamicas.ColaInt;
import tpo1.lineales.dinamicas.PilaInt;
import utiles.TecladoIn;

/**
 * Trabajo Práctico Obligatorio No. 1
 * Estructuras de Datos, 2016
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class TestLista {

    private static ListaInt lista;

    public static final int ACCION_SALIR = 0;
    public static final int ACCION_CONCATENAR = 1;
    public static final int ACCION_INVERTIR = 2;
    public static final int ACCION_COMPROBAR = 3;

    /**
     * @param args
     */
    public static void main(String[] args) {
        int accion, longitud;
        String digitos;

        // Solicitar acción
        mostrarMenu();
        accion = leerAccion();

        // Ejecutar acción solicitada
        switch (accion) {
            case ACCION_CONCATENAR:

                break;
            case ACCION_INVERTIR:

                break;
            case ACCION_COMPROBAR:

                break;
            case ACCION_SALIR:
                System.out.println(">>> Finalizado <<<");
                break;
        }
    }

    /**
     * Verifíca si un carácter es un dígito.
     *
     * @param caracter
     * @return
     */
    public static boolean esDigito(char caracter) {
        return 48 <= caracter && caracter <= 57;
    }

    /**
     * Verifica si una cadena no vacía contiene sólo dígitos.
     *
     * @param cadena
     * @return
     */
    public static boolean validarDigitos(String cadena) {
        boolean esValida = false;
        int i, longitudCadena = cadena.length();
        char caracter;

        if (longitudCadena > 0) {
            esValida = true;
            i = 0;
            while (i < longitudCadena && esValida) {
                caracter = cadena.charAt(i);
                if (!esDigito(caracter)) {
                    esValida = false;
                }
                i++;
            }
        }

        return esValida;
    }

    /**
     * Solicita y lee una cadena de dígitos válida para generar la cola.
     *
     * @return
     */
    public static String leerDigitos() {
        String digitos = "";

        while (!validarDigitos(digitos)) {
            System.out.println("Ingrese una cadena que contenga sólo dígitos:");
            System.out.println(">>> ");
            digitos = TecladoIn.readLine().trim();
        }

        return digitos;
    }

    /**
     * Solicita y lee una longitud válida para generar la cola.
     *
     * @return
     */
    public static int leerLongitud() {
        int longitud = -1;

        while (longitud < 0) {
            System.out.println("Ingrese una longitud válida:");
            System.out.println(">>> ");
            longitud = TecladoIn.readLineInt();
        }

        return longitud;
    }

    /**
     * Solicita y lee una acción válida del menú.
     *
     * @return
     */
    public static int leerAccion() {
        int accion = -1;

        while (accion < 0 || accion > 3) {
            System.out.println("Ingrese una opción válida:");
            System.out.println(">>> ");
            accion = TecladoIn.readLineInt();
        }

        return accion;
    }

    /**
     * Muestra el menú del programa.
     */
    public static void mostrarMenu() {
        System.out.println("Elija una opción:");
        System.out.println("    [1] Concatenar");
        System.out.println("    [2] Invertir");
        System.out.println("    [3] Comprobar");
        System.out.println("    [0] Salir");
        System.out.println();
        System.out.println(">>> ");
    }

    public ListaInt concatenar() {
        ListaInt concatenado = new ListaInt();

        return concatenado;
    }

    public ListaInt invertir() {
        ListaInt invertido = new ListaInt();

        return invertido;
    }

    public boolean comprobar() {
        boolean comprobado = false;

        return comprobado;
    }

    /*public void pruebaInsertar() {
        assert lista.insertar(1, 1) : "Debe insertar 1@1";
        assert !lista.insertar(2, 0)
             : "No debe insertar 2@0 (pos. inválida: < 1)";
        assert !lista.insertar(3, 3)
             : "No debe insertar 3@3 (pos. inválida: > longitud de lista + 1)";
    }

    public void pruebaRecuperar() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        assert lista.recuperar(3) == 3 : "Debe recuperar 3@3";
    }

    public void pruebaEliminar() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        assert lista.eliminar(3) : "Debe eliminar 3@3";
        assert lista.recuperar(3) == null : "No debe recuperar 3@3 (eliminado)";
    }

    public void pruebaLocalizar() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        assert lista.localizar(2) == 2 : "Debe localizar 2@2";
        assert lista.localizar(4) == -1 : "No debe localizar 4 (inexistente)";
    }

    public void pruebaLongitud() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        assert lista.longitud() == 3 : "Longitud de lista debe ser 3";
        lista.eliminar(3);
        assert lista.longitud() == 2 : "Longitud de lista debe ser 2";
        lista.eliminar(2);
        lista.eliminar(1);
        assert lista.longitud() == 0 : "Longitud de lista debe ser 0";
    }

    public void pruebaEsVacia() {
        assert lista.esVacia() : "Lista debe ser vacía";
        lista.insertar(1, 1);
        assert !lista.esVacia() : "Lista no debe ser vacía";
    }

    public void pruebaVaciar() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        lista.vaciar();
        assert !lista.eliminar(1) : "No debe eliminar de lista vacía";
        assert lista.localizar(2) == -1 : "No debe localizar 2 (lista vacía)";
        assert lista.recuperar(3) == null : "No debe recuperar 3 (lista vacía)";
        assert lista.longitud() == 0 : "Longitud de lista debe ser 0";
        assert lista.esVacia() : "Lista debe ser vacía";
    }

    public void pruebaClonar() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        Lista<Integer> clon = lista.clonar();
        assert lista.recuperar(1) == clon.recuperar(1)
             : "Elemento @1 de lista debe ser igual al de su clon";
        lista.eliminar(1);
        clon.eliminar(1);
        assert lista.recuperar(2) == clon.recuperar(2)
             : "Elemento @2 de lista debe ser igual al de su clon";
        lista.eliminar(1);
        clon.eliminar(1);
        assert lista.recuperar(3) == clon.recuperar(3)
             : "Elemento @3 de lista debe ser igual al de su clon";
        lista.eliminar(1);
        clon.eliminar(1);
        assert lista.esVacia() && clon.esVacia()
             : "Lista y su clon deben ser vacías";
    }

    public void pruebaToString() {
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        assert lista.toString().equals("[1, 2, 3]")
             : "Lista con elementos 1, 2, 3 debe ser representada como "
             + "[1, 2, 3] en forma de cadena";
    }*/
}
