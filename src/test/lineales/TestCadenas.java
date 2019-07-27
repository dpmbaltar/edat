package test.lineales;

import lineales.estaticas.Cola;
import lineales.estaticas.Pila;
import utiles.TecladoIn;

public class TestCadenas {

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

    public static int menu() {
        int accion = 0;
        String[] opciones = {
            "Probar Generar",
            "Probar Generar (con entrada de datos)"
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

    public static void pruebaGenerarConEntrada() {
        String entrada;

        do { // Validar char entre 65 y 90 (A-Z)
            System.out.println("Ingresar cadena:");
            entrada = TecladoIn.readLine();

            if (entrada.isEmpty()) {
                System.out.println("La entrada no es válida");
            }
        } while (entrada.isEmpty());

        System.out.print("Salida:   ");
        System.out.println(aCadena(generar(aCola(entrada))));
        System.out.println();
    }

    private static String aCadena(Cola<Character> cola) {
        StringBuilder cadena = new StringBuilder("");

        while (!cola.esVacia()) {
            cadena.append(cola.obtenerFrente());
            cola.sacar();
        }

        return cadena.toString();
    }

    private static Cola<Character> aCola(String cadena) {
        Cola<Character> cola = new Cola<>();

        for (int i = 0; i < cadena.length(); i++) {
            cola.poner(cadena.charAt(i));
        }

        return cola;
    }

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
