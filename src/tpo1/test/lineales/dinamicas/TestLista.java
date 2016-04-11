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
        int accion;
        boolean r1;
        ListaInt l1, l2, l3;

        // Solicitar acción
        mostrarMenu();
        accion = leerAccion();

        // Ejecutar acción solicitada
        switch (accion) {
            case ACCION_CONCATENAR:
                l1 = leerLista();
                System.out.println("L1 = " + l1);
                l2 = leerLista();
                System.out.println("L2 = " + l2);
                l3 = concatenar(l1, l2);
                System.out.println("concatenar(L2, L2) = " + l3);
                break;
            case ACCION_INVERTIR:
                l1 = leerLista();
                System.out.println("L1 = " + l1);
                l2 = invertir(l1);
                System.out.println("invertir(L1) = " + l2);
                break;
            case ACCION_COMPROBAR:
                l1 = leerLista();
                System.out.println("L1 = " + l1);
                r1 = comprobar(l1);
                System.out.println("comprobar(L1) = " + r1);
                break;
        }

        System.out.println(">>> Finalizado <<<");
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
            System.out.print(">>> ");
            digitos = TecladoIn.readLine().trim();
        }

        return digitos;
    }

    /**
     * Solicita y lee una lista de enteros.
     *
     * @return
     */
    public static ListaInt leerLista() {
        int i, z;
        String entrada;
        String[] enteros;
        ListaInt lista = new ListaInt();

        while (lista.esVacia()) {
            System.out.println("Ingrese una lista de enteros separados por espacios:");
            System.out.print(">>> ");
            entrada = TecladoIn.readLine().trim();
            enteros = entrada.split(" ");
            for (i = 0; i < enteros.length; i++) {
                if (validarDigitos(enteros[i])) {
                    z = Integer.parseInt(enteros[i]);
                    lista.insertar(z, lista.longitud() + 1);
                }
            }
        }

        return lista;
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
            System.out.print(">>> ");
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
    }

    public static ListaInt concatenar(ListaInt l1, ListaInt l2) {
        int posicion1 = 1;
        int posicion2 = l1.longitud() + 1;
        int longitud = l1.longitud() + l2.longitud();
        ListaInt concatenado = l1.clonar();

        while (posicion2 <= longitud) {
            concatenado.insertar(l2.recuperar(posicion1), posicion2);
            posicion1++;
            posicion2++;
        }

        return concatenado;
    }

    public static ListaInt invertir(ListaInt l1) {
        int posicion = 1;
        int longitud = l1.longitud();
        ListaInt invertido = new ListaInt();
        PilaInt pila = new PilaInt();

        while (posicion <= longitud) {
            pila.apilar(l1.recuperar(posicion));
            posicion++;
        }

        posicion = 1;

        while (!pila.esVacia()) {
            invertido.insertar(pila.obtenerTope(), posicion);
            pila.desapilar();
            posicion++;
        }

        return invertido;
    }

    public static boolean comprobar(ListaInt lista) {
        boolean comprobado = true;
        int paso, posicion, elemento;
        int[] longitud;
        ColaInt cola;
        PilaInt pila;

        if (!lista.esVacia()) {
            cola = new ColaInt();
            pila = new PilaInt();
            longitud = new int[4];
            longitud[0] = lista.longitud();
            paso = 1;
            posicion = 1;
            elemento = 0;

            while (posicion <= longitud[0] && comprobado) {
                elemento = lista.recuperar(posicion);

                if (elemento == 0) {
                    paso++;
                }

                switch (paso) {
                    case 1:
                        cola.poner(elemento);
                        pila.apilar(elemento);
                        longitud[1]++;
                        break;
                    case 2:
                        if (!cola.esVacia()
                                && elemento == cola.obtenerFrente()) {
                            cola.sacar();
                        } else {
                            comprobado = false;
                        }
                        longitud[2]++;
                        break;
                    case 3:
                        if (!pila.esVacia()
                                && elemento == pila.obtenerTope()) {
                            pila.desapilar();
                        } else {
                            comprobado = false;
                        }
                        longitud[3]++;
                        break;
                    default:
                        break;
                }

                posicion++;
            }
        } else {
            comprobado = false;
        }

        /*if (!l1.esVacia()) {
            longitudL1 = l1.longitud();

            //Parte 1
            cola = new ColaInt();
            pila = new PilaInt();
            longitud1 = 0;
            posicion = 1;
            elemento = l1.recuperar(posicion);
            while (elemento != 0 && posicion < longitudL1) {
                cola.poner(elemento);
                pila.apilar(elemento);
                longitud1++;
                posicion++;
                elemento = l1.recuperar(posicion);
            }

            //Parte 2
            longitud2 = 0;
            posicion++;
            if (posicion < longitudL1) {
                elemento = l1.recuperar(posicion);
                while (elemento != 0 && posicion < longitudL1 && comprobado) {
                    if (elemento == cola.obtenerFrente()) {
                        cola.sacar();
                    } else {
                        comprobado = false;
                    }
                    longitud2++;
                    posicion++;
                    elemento = l1.recuperar(posicion);
                }
            } else {
                comprobado = false;
            }

            //Parte 3
            posicion++;
            if (longitud1 == longitud2 && posicion < longitudL1 && comprobado) {

            }
            while (elemento != 0 && posicion <= longitudL1 && comprobado) {
                if (elemento == pila.obtenerTope()) {
                    pila.desapilar();
                } else {
                    comprobado = false;
                }
            }
        } else {
            comprobado = false;
        }*/

        return comprobado;
    }
}
