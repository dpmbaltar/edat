package test.lineales;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Pila;
import utiles.TecladoIn;

/**
 * Programa de prueba de generar(Cola c1).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class TestCadenas {

    /**
     * Programa principal.
     *
     * @param args argumentos
     */
    public static void main(String[] args) {
        int accion;
        do {
            accion = menu();
            switch (accion) {
                case 1:
                    pruebaGenerar();
                    break;
                case 2:
                    pruebaGenerarConEntrada();
                    break;
            }
        } while (accion != 0);
    }

    /**
     * Menú del programa.
     *
     * @return la opción seleccionada
     */
    public static int menu() {
        int accion = 0;
        String[] opciones = {
            "Probar Generar",
            "Probar Generar con entrada de datos"
        };

        System.out.println("----- Trabajo Práctico Obligatorio -----");
        System.out.println("----------------- Menú -----------------");

        for (int i = 0; i < opciones.length; i++) {
            System.out.println(" [" + (i + 1) + "] " + opciones[i]);
        }

        System.out.println(" [0] Salir");
        System.out.println("----------------------------------------");

        do {
            accion = TecladoIn.readLineInt();
            if (accion < 0 || accion > opciones.length) {
                System.out.println("La opción no es válida");
            }
        } while (accion < 0 || accion > opciones.length);

        return accion;
    }

    /**
     * Prueba generar(Cola c1) con varias cadenas.
     */
    public static void pruebaGenerar() {
        String[][] pruebas = {
                {"", ""},
                {"#", "#"},
                {"##", "##"},
                {"A#", "AAA#"},
                {"#AB#", "#ABBAAB#"},
                {"##ABC", "##ABCCBAABC"},
                {"A#B#C#D", "AAA#BBB#CCC#DDD"},
                {"AB#C#DEF", "ABBAAB#CCC#DEFFEDDEF"}
        };

        for (int i = 0; i < pruebas.length; i++) {
            System.out.println("Prueba:   " + i);
            System.out.print("Salida:   ");
            System.out.println(aCadena(generar(aCola(pruebas[i][0]))));
            System.out.print("Esperado: ");
            System.out.println(pruebas[i][1]);
        }

        System.out.println();
    }

    /**
     * Prueba generar(Cola c1) con cadenas de entrada ingresadas por el usuario.
     */
    public static void pruebaGenerarConEntrada() {
        boolean entradaValida = false;
        String entrada;

        do {
            System.out.println("Ingresar cadena no vacía de letras mayúsculas:");
            entrada = TecladoIn.readLine();
            entradaValida = esValida(entrada);

            if (!entradaValida) {
                System.out.println("La entrada no es válida");
            }
        } while (!entradaValida);

        System.out.print("Salida:   ");
        System.out.println(aCadena(generar(aCola(entrada))));
        System.out.println();
    }

    /**
     * Verifica si una cadena es válida (no vacía, con caracteres entre A y Z o #).
     *
     * @param cadena la cadena de entrada
     * @return verdadero si la cadena es válida, falso en caso contrario
     */
    private static boolean esValida(String cadena) {
        int i = 0;
        char caracterActual;
        boolean cadenaValida = !cadena.isEmpty();

        while (cadenaValida && i < cadena.length()) {
            caracterActual = cadena.charAt(i);

            if ((caracterActual < 65 && caracterActual != '#') || caracterActual > 90) {
                cadenaValida = false;
            }

            i++;
        }

        return cadenaValida;
    }

    /**
     * Transforma una cola de caracteres en una cadena.
     *
     * @param cola la cola de caracteres a transformar
     * @return la cadena resultante
     */
    private static String aCadena(Cola<Character> cola) {
        StringBuilder cadena = new StringBuilder("");

        while (!cola.esVacia()) {
            cadena.append(cola.obtenerFrente());
            cola.sacar();
        }

        return cadena.toString();
    }

    /**
     * Transforma una cadena de caracteres en una cola.
     *
     * @param cadena la cadena de caracteres a transformar
     * @return la cola resultante
     */
    private static Cola<Character> aCola(String cadena) {
        Cola<Character> cola = new Cola<>();

        for (int i = 0; i < cadena.length(); i++) {
            cola.poner(cadena.charAt(i));
        }

        return cola;
    }

    /**
     * Dada una cadena de la forma w#x#y#...#z, donde w,x,y,z son cadenas con letras mayúsculas (desde la A a la Z),
     * genera una cadena de la forma ww'w#xx'x#yy'y#...#zz'z, donde w', x', y' y z' son cadenas inversas de w, x, y y z
     * respectivamente. Ejemplo: genera ABBAAB#CCC#DEFFEDDEF a partir de AB#C#DEF
     *
     * @param c1 la cola de caracteres de entrada
     * @return la cola de caracteres resultante
     */
    public static Cola<Character> generar(Cola<Character> c1) {
        Cola<Character> colaSalida = new Cola<>();

        if (!c1.esVacia()) {
            boolean leerCaracter = true;
            char caracterActual;
            Cola<Character> colaAuxiliar = new Cola<>();
            Pila<Character> pilaAuxiliar = new Pila<>();

            while (leerCaracter) {
                // Obtener caracter
                if (!c1.esVacia()) {
                    caracterActual = c1.obtenerFrente();
                    c1.sacar();
                } else {
                    caracterActual = '#';
                    leerCaracter = false;
                }

                // Vaciar la cola y pila auxiliar o acumular, según corresponda
                if (caracterActual == '#') {
                    while (!pilaAuxiliar.esVacia()) {
                        colaSalida.poner(pilaAuxiliar.obtenerTope());
                        pilaAuxiliar.desapilar();
                    }

                    while (!colaAuxiliar.esVacia()) {
                        colaSalida.poner(colaAuxiliar.obtenerFrente());
                        colaAuxiliar.sacar();
                    }

                    // Agregar separador si hay más elementos por leer
                    if (leerCaracter) {
                        colaSalida.poner('#');
                    }
                } else {
                    pilaAuxiliar.apilar(caracterActual);
                    colaAuxiliar.poner(caracterActual);
                    colaSalida.poner(caracterActual);
                }
            }
        }

        return colaSalida;
    }
}
