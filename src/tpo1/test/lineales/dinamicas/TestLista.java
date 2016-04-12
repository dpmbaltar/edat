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
    public static final String EXITO = "EXITO";
    public static final String ERROR = "ERROR";
    public static final int ACCION_SALIR = 0;
    public static final int ACCION_CONCATENAR = 1;
    public static final int ACCION_INVERTIR = 2;
    public static final int ACCION_COMPROBAR = 3;
    public static final int ACCION_PROBAR = 4;

    public static void main(String[] args) {
        int accion;
        boolean r1;
        ListaInt l1, l2, l3;

        mostrarMenu();
        accion = leerAccion();

        while (accion != ACCION_SALIR) {
            switch (accion) {
                case ACCION_CONCATENAR:
                    l1 = leerLista();
                    System.out.println("L1 = " + l1);
                    l2 = leerLista();
                    System.out.println("L2 = " + l2);
                    l3 = concatenar(l1, l2);
                    System.out.println();
                    System.out.println("concatenar(L2, L2) = " + l3);
                    break;
                case ACCION_INVERTIR:
                    l1 = leerLista();
                    System.out.println("L1 = " + l1);
                    l2 = invertir(l1);
                    System.out.println();
                    System.out.println("invertir(L1) = " + l2);
                    break;
                case ACCION_COMPROBAR:
                    l1 = leerLista();
                    System.out.println("L1 = " + l1);
                    r1 = comprobar(l1);
                    System.out.println();
                    System.out.println("comprobar(L1) = " + r1);
                    break;
                case ACCION_PROBAR:
                    prueba();
                    break;
            }

            System.out.println();
            mostrarMenu();
            accion = leerAccion();
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
    public static boolean validarEntero(String cadena) {
        boolean esValida = false;
        int i, longitudCadena = cadena.length();
        char caracter;

        if (longitudCadena > 0) {
            esValida = true;
            i = 0;
            if (cadena.charAt(0) == '+' || cadena.charAt(0) == '-') {
                i = 1;
            }
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
                if (validarEntero(enteros[i])) {
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

        while (accion < 0 || accion > 4) {
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
        System.out.println("    [4] Ejecutar pruebas");
        System.out.println("    [0] Salir");
    }

    //Métodos TPO1

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

    public static ListaInt invertir(ListaInt lista) {
        int posicion = 1;
        int longitud = lista.longitud();
        ListaInt invertido = new ListaInt();
        PilaInt pila = new PilaInt();

        while (posicion <= longitud) {
            pila.apilar(lista.recuperar(posicion));
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
            elemento = -1;

            while (posicion <= longitud[0] && comprobado) {
                elemento = lista.recuperar(posicion);

                //Saltar al siguiente paso y elemento cuando el elemento es cero
                if (elemento == 0) {
                    paso++;
                    posicion++;
                    elemento = lista.recuperar(posicion);
                }

                switch (paso) {
                    //Obtener los elementos hasta el primer cero;
                    //almacenar la cantidad en longitud[1];
                    //almacenar elementos en una cola y pila auxiliar para su
                    //verificación en los pasos siguientes
                    case 1:
                        cola.poner(elemento);
                        pila.apilar(elemento);
                        longitud[1]++;
                        break;
                    //Comparar los elementos a partir del primer cero con los
                    //elementos almacenados en la cola auxiliar
                    case 2:
                        if (!cola.esVacia()
                                && elemento == cola.obtenerFrente()) {
                            cola.sacar();
                        } else {
                            comprobado = false;
                        }
                        longitud[2]++;
                        break;
                    //Comparar los elementos a partir del segundo cero con los
                    //elementos almacenados en la pila auxiliar
                    case 3:
                        if (!pila.esVacia()
                                && elemento == pila.obtenerTope()) {
                            pila.desapilar();
                        } else {
                            comprobado = false;
                        }
                        longitud[3]++;
                        break;
                }

                posicion++;
            }

            //Verificar longitudes finales
            if (longitud[1] != longitud[2] || longitud[1] != longitud[3]) {
                comprobado = false;
            }
        } else {
            comprobado = false;
        }

        return comprobado;
    }

    //Prueba de ListaInt

    public static void prueba() {
        pruebaInsertar();
        pruebaRecuperar();
        pruebaEliminar();
        pruebaLocalizar();
        pruebaLongitud();
        pruebaEsVacia();
        pruebaVaciar();
        pruebaClonar();
    }

    public static void pruebaInsertar() {
        lista = new ListaInt();
        System.out.println(
            "Debe insertar 1 en pos. 1 "+
                (lista.insertar(1, 1) ? EXITO : ERROR));
        System.out.println(
            "No debe insertar 2 en pos. 0 (pos. inválida: < 1) "+
                (!lista.insertar(2, 0) ? EXITO : ERROR));
        System.out.println(
            "No debe insertar 3 en pos. 3 (pos. inválida: > longitud de lista + 1) "+
                (!lista.insertar(3, 3) ? EXITO : ERROR));
    }

    public static void pruebaRecuperar() {
        lista = new ListaInt();
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        System.out.println(
            "Debe recuperar 3 en la pos. 3 "+
                ((lista.recuperar(3) == 3) ? EXITO : ERROR));
    }

    public static void pruebaEliminar() {
        lista = new ListaInt();
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        System.out.println(
            "Debe eliminar 3 de la pos. 3 "+
                (lista.eliminar(3) ? EXITO : ERROR));
        System.out.println(
            "No debe recuperar 3 en la pos. 3 (eliminado) "+
                (lista.recuperar(3) == 0 ? EXITO : ERROR));
    }

    public static void pruebaLocalizar() {
        lista = new ListaInt();
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        System.out.println(
            "Debe localizar 2 en la pos. 2 "+
                (lista.localizar(2) == 2 ? EXITO : ERROR));
        System.out.println(
            "No debe localizar 4 (inexistente) "+
                (lista.localizar(4) == -1 ? EXITO : ERROR));
    }

    public static void pruebaLongitud() {
        lista = new ListaInt();
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        System.out.println(
            "Longitud de lista debe ser 3 "+
                (lista.longitud() == 3 ? EXITO : ERROR));
        lista.eliminar(3);
        System.out.println(
            "Longitud de lista debe ser 2 "+
                (lista.longitud() == 2 ? EXITO : ERROR));
        lista.eliminar(2);
        lista.eliminar(1);
        System.out.println(
            "Longitud de lista debe ser 0 "+
                (lista.longitud() == 0 ? EXITO : ERROR));
    }

    public static void pruebaEsVacia() {
        lista = new ListaInt();
        System.out.println(
            "Lista debe ser vacía "+
                (lista.esVacia() ? EXITO : ERROR));
        lista.insertar(1, 1);
        System.out.println(
            "Lista no debe ser vacía "+
                (!lista.esVacia() ? EXITO : ERROR));
    }

    public static void pruebaVaciar() {
        lista = new ListaInt();
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        lista.vaciar();
        System.out.println(
            "No debe eliminar de lista vacía "+
                (!lista.eliminar(1) ? EXITO : ERROR));
        System.out.println(
            "No debe localizar 2 (lista vacía) "+
                (lista.localizar(2) == -1 ? EXITO : ERROR));
        System.out.println(
            "Longitud de lista debe ser 0 "+
                (lista.longitud() == 0 ? EXITO : ERROR));
        System.out.println(
            "Lista debe ser vacía "+
                (lista.esVacia() ? EXITO : ERROR));
    }

    public static void pruebaClonar() {
        lista = new ListaInt();
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(3, 3);
        ListaInt clon = lista.clonar();
        System.out.println(
            "Elemento en pos. 1 de lista debe ser igual al de su clon "+
                (lista.recuperar(1) == clon.recuperar(1) ? EXITO : ERROR));
        lista.eliminar(1);
        clon.eliminar(1);
        System.out.println(
            "Elemento en pos. 2 de lista debe ser igual al de su clon "+
                (lista.recuperar(2) == clon.recuperar(2) ? EXITO : ERROR));
        lista.eliminar(1);
        clon.eliminar(1);
        System.out.println(
            "Elemento en pos. 3 de lista debe ser igual al de su clon "+
                (lista.recuperar(3) == clon.recuperar(3) ? EXITO : ERROR));
        lista.eliminar(1);
        clon.eliminar(1);
        System.out.println(
            "Lista y su clon deben ser vacías "+
                (lista.esVacia() && clon.esVacia() ? EXITO : ERROR));
    }
}
