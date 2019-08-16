package tpfinal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

import conjuntistas.ArbolAVL;
import lineales.dinamicas.Lista;
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

    public static final int CONSULTAR_JUGADOR = 10;
    public static final int FILTRAR_JUGADORES = 11;

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
    private static Mapa mapa; //TODO: El mapa debe ser un grafo etiquetado

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
        int accion = 0;

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
                case CONSULTAR_JUGADOR:
                    consultarJugador();
                    break;
                case FILTRAR_JUGADORES:
                    filtrarJugadores();
                    break;
            }

            if (accion > 0) {
                pausar();
            }
        } while (accion > 0);

        System.out.println("~ FIN ~");
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
                        }
                        break;
                    case 'L':
                        mapa.insertarVertice(linea.substring(2).replace(';', ' ').trim());
                        break;
                    case 'C':
                        String[] partes = linea.substring(2).split(";");

                        if (partes.length >= 3) {
                            mapa.insertarArco(partes[0].trim(), partes[1].trim());
                            //TODO: Implementar y usar grafo etiquetado
                            try {
                                int etiqueta = Integer.valueOf(partes[2].trim());
                            } catch (NumberFormatException e) {}
                        }
                        break;
                }

                linea = archivoEstado.readLine();
            }

            archivoEstado.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            if (accion < 0 || accion > 20) {
                System.out.println("La opción no es válida");
            }
        } while (accion < 0 || accion > 20);

        return accion;
    }

    public static void pausar() {
        System.out.println("Presionar Entrar para volver al menú...");
        TecladoIn.readLine();
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

    /**
     * I. Consultas sobre jugadores:
     * Dado un nombre de usuario de un jugador, mostrar todos sus datos.
     */
    private static void consultarJugador() {
        String usuario = leerUsuario();

        if (jugadores.containsKey(usuario)) {
            Jugador jugador = jugadores.get(usuario);
            StringBuilder datos = new StringBuilder();
            datos.append("Usuario:   ").append(jugador.getUsuario()).append("\r\n");
            datos.append("Tipo:      ").append(jugador.getTipo()).append("\r\n");
            datos.append("Categoría: ").append(jugador.getCategoria()).append("\r\n");
            datos.append("Dinero:    ").append(jugador.getDinero()).append("\r\n");
            datos.append("Salud:     ").append(jugador.getSalud()).append("\r\n");
            datos.append("Victorias: ").append(jugador.getVictorias()).append("\r\n");
            datos.append("Derrotas:  ").append(jugador.getDerrotas()).append("\r\n");
            datos.append("Equipo:    ");

            if (jugador.tieneEquipo()) {
                datos.append(jugador.getEquipo().getNombre());
            } else {
                datos.append("(sin asignar)");
            }

            datos.append("\r\n");
            System.out.println(datos);
        } else {
            System.out.println(String.format("El jugador \"%s\" no existe", usuario));
        }
    }

    /**
     * I. Consultas sobre jugadores:
     * Dada una subcadena, mostrar todos los nombres de usuarios que comienzan con esa subcadena.
     */
    private static void filtrarJugadores() {
        String prefijo = leerUsuario();
        Set<String> claves = jugadores.keySet();
        String[] usuarios = claves.toArray(new String[claves.size()]);
        Lista<String> filtrados = new Lista<>();

        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i].startsWith(prefijo)) {
                filtrados.insertar(usuarios[i], filtrados.longitud() + 1);
            }
        }

        if (filtrados.longitud() > 0) {
            String usuario;
            System.out.println(String.format("Usuarios que comienzan con \"%s\":", prefijo));

            for (int i = 1; i <= filtrados.longitud(); i++) {
                usuario = filtrados.recuperar(i);

                if (usuario.startsWith(prefijo)) {
                    System.out.println(i + ": " + usuario);
                }
            }
        } else {
            System.out.println(String.format("No existen jugadores cuyo nombre comience con \"%s\"", prefijo));
        }
    }
}
