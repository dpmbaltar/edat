package tpo1;

import java.util.regex.Pattern;

import utiles.TecladoIn;

import lineales.dinamicas.PilaInt;
import lineales.dinamicas.ColaInt;

/**
 * Trabajo Práctico Obligatorio No. 1.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class TestTP1 {

    public static final int ACCION_SALIR = 0;
    public static final int ACCION_GENERAR_COLA = 1;

    /**
     * @param args
     */
    public static void main(String[] args) {
        int accion, longitud;
        String digitos;

        // Solicitar acción
        mostrarMenu();
        accion = leerAccion();

        // Ejecutar acción
        switch (accion) {
        case ACCION_GENERAR_COLA:
            digitos = leerDigitos();
            longitud = leerLongitud();
            System.out.println(generarCola(digitos, longitud).toString());
            break;
        case ACCION_SALIR:
            System.out.println("Finalizado.");
            break;
        }

        // pruebaGenerarCola();
    }

    /**
     * Prueba el método generarCola()
     */
    public static void pruebaGenerarCola() {
        String s = "0123456789";
        String r = "[" + Pattern.quote("[, ]") + "]+";

        assert generarCola(s, 3).toString().replaceAll(r, "")
                .equals("2105438769");
        assert generarCola(s, 4).toString().replaceAll(r, "")
                .equals("3210765498");
        assert generarCola(s, 5).toString().replaceAll(r, "")
                .equals("4321098765");
        assert generarCola(s, 1).toString().replaceAll(r, "").equals(s);
        assert generarCola("", 3).toString().replaceAll(r, "").equals("");

        System.out.println(generarCola("01234a56789", 0));
        System.out.println(generarCola("", 0));
        System.out.println(generarCola(s, 0));
        System.out.println(generarCola(s, 1));
        System.out.println(generarCola(s, 2));
        System.out.println(generarCola(s, 3));
        System.out.println(generarCola(s, 4));
        System.out.println(generarCola(s, 5));
        System.out.println(generarCola(s, 6));
        System.out.println(generarCola(s, 7));
        System.out.println(generarCola(s, 8));
        System.out.println(generarCola(s, 9));
        System.out.println(generarCola(s, 10));
        System.out.println(generarCola(s, 11));
    }

    /**
     * Genera una cola de enteros a partir de una cadena (sólo dígitos). Si la
     * cadena contiene algún carácter que no sea un dígito, devuelve una cola
     * vacía.
     * 
     * @param cadena
     * @param x
     * @return
     */
    public static ColaInt generarCola(String cadena, int x) {
        ColaInt cola = new ColaInt();
        PilaInt pila;
        int i, j, longitudCadena = cadena.length();
        char caracter;

        // Ignorar cadenas vacías
        if (!cadena.isEmpty()) {
            // Usar pila auxiliar sólo cuando sea necesario
            if (x >= longitudCadena) {
                for (i = (longitudCadena - 1); i >= 0; i--) {
                    caracter = cadena.charAt(i);
                    if (esDigito(caracter)) {
                        cola.poner(Integer.parseInt(caracter + ""));
                    } else {
                        cola.vaciar();
                        i = -1;
                    }
                }
            } else if (x < 1) {
                for (i = 0; i < longitudCadena; i++) {
                    caracter = cadena.charAt(i);
                    if (esDigito(caracter)) {
                        cola.poner(Integer.parseInt(caracter + ""));
                    } else {
                        cola.vaciar();
                        i = longitudCadena;
                    }
                }
            } else {
                i = 0;
                j = 0;
                pila = new PilaInt();

                while (i < longitudCadena) {
                    caracter = cadena.charAt(i);
                    if (esDigito(caracter)) {
                        // Apilar dígitos para invertir su orden
                        if (j < x) {
                            pila.apilar(Integer.parseInt(caracter + ""));
                            i++;
                            // Si se procesaron todos los dígitos, asegurarse de
                            // desapilar los dígitos restantes a continuación
                            if (i == longitudCadena) {
                                j = x;
                            } else {
                                j++;
                            }
                        }
                        // Desapilar dígitos y poner en la cola en orden inverso
                        if (j == x) {
                            j = 0;
                            while (!pila.esVacia()) {
                                cola.poner(pila.obtenerTope());
                                pila.desapilar();
                            }
                        }
                    } else {
                        cola.vaciar();
                        i = longitudCadena;
                    }
                }
            }
        }

        return cola;
    }

    /**
     * Verifíca si un carácter es un dígito.
     * 
     * @param caracter
     * @return
     */
    public static boolean esDigito(char caracter) {
        return (48 <= caracter && caracter <= 57);
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
            System.out
                    .print("Ingrese una cadena que contenga sólo dígitos:\n>>>");
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
            System.out.print("Ingrese una longitud válida:\n>>>");
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

        while (accion < 0 || accion > 1) {
            System.out.print("Ingrese una opción válida:\n>>>");
            accion = TecladoIn.readLineInt();
        }

        return accion;
    }

    /**
     * Muestra el menú del programa.
     */
    public static void mostrarMenu() {
        System.out.println("Elija una opción:\n" + "  [1] Generar cola\n"
                + "  [0] Salir\n");
    }
}
