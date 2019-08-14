package tpfinal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

import conjuntistas.ArbolAVL;
import utiles.TecladoIn;

/**
 * Juego.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Dungeons2019 {

    /**
     * El archivo del menú.
     */
    private static final String ARCHIVO_MENU = "menu.txt";

    /**
     * El archivo de estado del juego.
     */
    private static final String ARCHIVO_ESTADO = "estado.csv";

    /**
     * El archivo de registro.
     */
    private static final String ARCHIVO_REGISTRO = "registro.log";

    public static final int ALTA_JUGADOR = 1;
    public static final int BAJA_JUGADOR = 2;
    public static final int MODI_JUGADOR = 3;
    public static final int ALTA_ITEM = 4;
    public static final int BAJA_ITEM = 5;
    public static final int MODI_ITEM = 6;
    public static final int ALTA_LOCACION = 7;
    public static final int BAJA_LOCACION = 8;
    public static final int MODI_LOCACION = 9;

    /**
     * Equipos registrados.
     */
    private static HashMap<String, Equipo> equipos;

    /**
     * Jugadores en el juego.
     */
    private static HashMap<String, Jugador> jugadores; //TODO: Debe ser una impl. de Tabla de Búsqueda con AVL

    /**
     * Jugadores en espera.
     */
    private static PriorityQueue<Jugador> esperando; //TODO: Debe ser una impl. de Cola de Prioridad

    /**
     * Ítems disponibles en el juego.
     */
    private static ArbolAVL<Item> items; //TODO: El AVL debe aceptar ítems con precios iguales

    /**
     * El mapa del juego.
     */
    private static Mapa mapa; //TODO: El mapa debe usar un grafo etiquetado

    /**
     * La cadena del menú.
     */
    private static String menu;

    /**
     * Programa principal.
     *
     * @param args argumentos
     */
    public static void main(String[] args) {
        equipos = new HashMap<>();
        jugadores = new HashMap<>();
        items = new ArbolAVL<>();
        mapa = new Mapa();
        cargar();
        /*int accion = 0;

        do {
            accion = menu();

            switch (accion) {
                case ALTA_JUGADOR:
                    altaJugador();
                    break;
                case BAJA_JUGADOR:
                    bajaJugador();
                    break;
                case MODI_JUGADOR:
                    modiJugador();
                    break;
            }
        } while (accion > 0);

        System.out.println("~ FIN ~");*/
    }

    /**
     * Inicializa el juego desde el archivo de estado (se asume formato válido).
     */
    public static void cargar() {
        try {
            String url = Dungeons2019.class.getResource(ARCHIVO_ESTADO).getPath();
            BufferedReader archivoEstado = new BufferedReader(new FileReader(url));
            String linea = archivoEstado.readLine();

            while (linea != null) {
                switch (linea.charAt(0)) {
                    case 'I':
                        Item item = Item.crearDesdeCadena(linea.substring(2));
                        if (item != null) {
                            items.insertar(item);
                        }
                        break;
                    case 'J':
                        Jugador jugador = Jugador.crearDesdeCadena(linea.substring(2));
                        if (jugador != null) {
                            jugadores.put(jugador.getUsuario(), jugador);
                            System.out.println(jugador);
                        }
                        break;
                }

                linea = archivoEstado.readLine();
            }

            archivoEstado.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(items);
    }

    /**
     * Muestra el menú principal.
     */
    public static int menu() {
        int accion = 0;

        if (menu == null) {
            try {
                String url = Dungeons2019.class.getResource(ARCHIVO_MENU).getPath();
                BufferedReader archivoMenu = new BufferedReader(new FileReader(url));
                String linea = archivoMenu.readLine();
                StringBuilder leido = new StringBuilder();

                while (linea != null) {
                    leido.append(linea).append("\r\n");
                    linea = archivoMenu.readLine();
                }

                menu = leido.toString();
                archivoMenu.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(menu);

        do {
            accion = TecladoIn.readLineInt();
            if (accion < 0 || accion > 9) {
                System.out.println("La opción no es válida");
            }
        } while (accion < 0 || accion > 9);

        return accion;
    }

    /**
     * Alta de jugador.
     */
    private static void altaJugador() {
        int tipo = leerTipo();
        Jugador jugador = null;
        String usuario = leerUsuario();
        Categoria categoria = leerCategoria();
        int dinero = TecladoIn.readLineInt();
        //TODO: Leer tipo

        //TODO: Registrar acción en registro.log
        System.out.println(jugador.toString());
        jugadores.put(usuario, jugador);
    }

    /**
     * Lee un nombre de usuario.
     *
     * @return el usuario leído
     */
    private static String leerUsuario() {
        String usuario;

        do {
            System.out.println("Ingresar el nombre de usuario:");
            usuario = TecladoIn.readLineWord();

            //FIXME: Validar nombre de usuario
            if (usuario.isEmpty()) {
                System.out.println("El nombre ingresado no es válido");
            }
        } while (usuario.isEmpty());

        return usuario;
    }

    /**
     * Lee una categoría.
     *
     * @return la categoría leída
     */
    private static Categoria leerCategoria() {
        Categoria categoria;

        do {
            System.out.println("Ingresar categoría (NOVATO: 0, AFICIONADO: 1, PROFESIONAL: 2):");
            categoria = Categoria.desdeEntero(TecladoIn.readLineInt());

            if (categoria == null) {
                System.out.println("La opción ingresada no es válida");
            }
        } while (categoria == null);

        return categoria;
    }

    /**
     * Lee el tipo de jugador.
     *
     * @return el tipo de jugador
     */
    private static int leerTipo() {
        int tipo;

        do {
            System.out.println("Ingresar el tipo de jugador (GUERRERO: 0, DEFENSOR: 1):");
            tipo = TecladoIn.readLineInt();

            if (tipo < 0 || tipo > 1) {
                System.out.println("La opción ingresada no es válida");
            }
        } while (tipo < 0 || tipo > 1);

        return tipo;
    }

    private static void bajaJugador() {
        // TODO Auto-generated method stub

    }

    private static void modiJugador() {
        // TODO Auto-generated method stub

    }
}
