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

    /**
     * Acciones de jugador.
     */
    public static final int AGREGAR_JUGADOR = 11;
    public static final int BORRAR_JUGADOR = 12;
    public static final int MODIFICAR_JUGADOR = 13;
    public static final int CONSULTAR_JUGADOR = 14;
    public static final int FILTRAR_JUGADORES = 15;
    public static final int PONER_JUGADOR_EN_ESPERA = 16;

    /**
     * Acciones de ítem.
     */
    public static final int AGREGAR_ITEM = 21;
    public static final int BORRAR_ITEM = 22;
    public static final int MODIFICAR_ITEM = 23;
    public static final int CONSULTAR_ITEM = 24;
    public static final int MOSTRAR_ITEMS_HASTA_PRECIO = 25;
    public static final int MOSTRAR_ITEMS_DESDE_HASTA_PRECIO = 26;

    /**
     * Acciones de locación.
     */
    public static final int AGREGAR_LOCACION = 31;
    public static final int BORRAR_LOCACION = 32;
    public static final int MODIFICAR_LOCACION = 33;
    public static final int MOSTRAR_LOCACIONES_ADYACENTES = 34;
    public static final int MOSTRAR_CAMINO_MAS_CORTO = 35;
    public static final int MOSTRAR_CAMINO_MAS_DIRECTO = 36;
    public static final int MOSTRAR_CAMINO_HASTA_DISTANCIA = 37;
    public static final int MOSTRAR_CAMINO_SIN_LOCACION = 38;

    /**
     * Acciones de equipo.
     */
    public static final int CONSULTAR_EQUIPO = 41;
    public static final int CREAR_EQUIPO = 42;
    public static final int INICIAR_BATALLA_ENTRE_EQUIPOS = 43;

    /**
     * Acciones generales.
     */
    public static final int MOSTRAR_RANKING_JUGADORES = 51;
    public static final int MOSTRAR_ITEMS_ULTIMA_DISPONIBILIDAD = 52;
    public static final int MOSTRAR_SISTEMA = 53;

    /**
     * Equipos registrados.
     */
    private HashMap<String, Equipo> equipos;

    /**
     * Jugadores en el juego.
     */
    private HashMap<String, Jugador> jugadores; //TODO: Debe ser una impl. de Tabla de Búsqueda con AVL

    /**
     * Jugadores en espera.
     */
    private PriorityQueue<Jugador> esperando; //TODO: Debe ser una impl. de Cola de Prioridad

    /**
     * Ítems disponibles en el juego.
     */
    private ArbolAVL<Item> items; //TODO: El AVL debe aceptar ítems con precios iguales

    /**
     * El mapa del juego.
     */
    private Mapa mapa; //TODO: El mapa debe ser un grafo etiquetado

    /**
     * La cadena del menú.
     */
    private String menu;

    /**
     * Programa principal.
     *
     * @param args argumentos
     */
    public static void main(String[] args) {
        Dungeons2019 juego = new Dungeons2019();
        juego.cargar("estado.csv");
        juego.iniciar();
    }

    /**
     * Constructor.
     */
    public Dungeons2019() {
        equipos = new HashMap<>();
        jugadores = new HashMap<>();
        items = new ArbolAVL<>();
        mapa = new Mapa();
    }

    /**
     * Inicia el juego.
     */
    public void iniciar() {
        int accion = 0;

        do {
            accion = menu();

            switch (accion) {
                case AGREGAR_JUGADOR:
                    agregarJugador();
                    break;
                case BORRAR_JUGADOR:
                    borrarJugador();
                    break;
                case MODIFICAR_JUGADOR:
                    modificarJugador();
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

        System.out.println("~{ FIN }~");
    }

    /**
     * Carga el estado del juego desde un archivo (se asume el formato válido).
     */
    public void cargar(String nombreArchivo) {
        try {
            String url = Dungeons2019.class.getResource(nombreArchivo).getPath();
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
    public int menu() {
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

    private void pausar() {
        System.out.println("Presionar [Entrar] para volver al menú...");
        TecladoIn.readLine();
    }

    /**
     * A. ABM (Altas-Bajas-Modificaciones) de jugadores:
     * Agrega un jugador.
     */
    public void agregarJugador() {
        TipoJugador tipo = leerTipo();
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
    private String leerUsuario() {
        String usuario;

        do {
            System.out.println("Ingresar el nombre de usuario:");
            usuario = TecladoIn.readLineWord();

            //TODO: Validar nombre de usuario
            if (usuario.isEmpty()) {
                System.out.println("El nombre de usuario ingresado no es válido");
            }
        } while (usuario.isEmpty());

        return usuario;
    }

    /**
     * Lee una categoría.
     *
     * @return la categoría leída
     */
    private Categoria leerCategoria() {
        Categoria categoria;

        do {
            System.out.println("Ingresar categoría (<0> Novato, <1> Aficionado, <2> Profesional):");
            categoria = Categoria.desdeEntero(TecladoIn.readLineInt());

            if (categoria == null) {
                System.out.println("La categoría ingresada no es válida");
            }
        } while (categoria == null);

        return categoria;
    }

    /**
     * Lee el tipo de jugador.
     *
     * @return el tipo de jugador leído
     */
    private TipoJugador leerTipo() {
        TipoJugador tipo;

        do {
            System.out.println("Ingresar el tipo de jugador (<0> Guerrero, <1> Defensor):");
            tipo = TipoJugador.desdeEntero(TecladoIn.readLineInt());

            if (tipo == null) {
                System.out.println("El tipo de jugador ingresado no es válido");
            }
        } while (tipo == null);

        return tipo;
    }

    /**
     * A. ABM (Altas-Bajas-Modificaciones) de jugadores:
     * Borra un jugador.
     */
    public void borrarJugador() {
        //TODO: borrarJugador()
    }

    public void modificarJugador() {
        //TODO: modificarJugador()
    }

    /**
     * I. Consultas sobre jugadores:
     * Dado un nombre de usuario de un jugador, mostrar todos sus datos.
     */
    public void consultarJugador() {
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
    public void filtrarJugadores() {
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
