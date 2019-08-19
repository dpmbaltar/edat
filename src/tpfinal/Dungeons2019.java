package tpfinal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

import lineales.dinamicas.Lista;
import utiles.Funciones;
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
    public static final int SALIR = 0;

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
    private ItemsAVL items; //TODO: El AVL debe aceptar ítems con precios iguales

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
        items = new ItemsAVL();
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
                case PONER_JUGADOR_EN_ESPERA:
                    ponerJugadorEnEspera();
                    break;
                case AGREGAR_ITEM:
                    agregarItem();
                    break;
                case BORRAR_ITEM:
                    borrarItem();
                    break;
                case MODIFICAR_ITEM:
                    borrarItem();
                    break;
                case CONSULTAR_ITEM:
                    consultarItem();
                    break;
                case MOSTRAR_ITEMS_HASTA_PRECIO:
                case MOSTRAR_ITEMS_DESDE_HASTA_PRECIO:
                case AGREGAR_LOCACION:
                case BORRAR_LOCACION:
                case MODIFICAR_LOCACION:
                case MOSTRAR_LOCACIONES_ADYACENTES:
                case MOSTRAR_CAMINO_MAS_CORTO:
                case MOSTRAR_CAMINO_MAS_DIRECTO:
                case MOSTRAR_CAMINO_HASTA_DISTANCIA:
                case MOSTRAR_CAMINO_SIN_LOCACION:
                case CONSULTAR_EQUIPO:
                case CREAR_EQUIPO:
                case INICIAR_BATALLA_ENTRE_EQUIPOS:
                case MOSTRAR_RANKING_JUGADORES:
                case MOSTRAR_ITEMS_ULTIMA_DISPONIBILIDAD:
                    System.out.println("Sin implementar");
                    break;
                case MOSTRAR_SISTEMA:
                    mostrarSistema();
                    break;
                case SALIR:
                    System.out.println("Fin");
                    break;
            }

            if (accion != SALIR) {
                pausar();
            }
        } while (accion != SALIR);
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
                    case 'I': // Cargar Ítem
                        Item item = Item.crearDesdeCadena(linea.substring(2));
                        if (item != null) {
                            items.insertar(item);
                        }
                        break;
                    case 'J': // Cargar Jugador
                        Jugador jugador = Jugador.crearDesdeCadena(linea.substring(2));
                        if (jugador != null) {
                            jugadores.put(jugador.getUsuario().toUpperCase(), jugador);
                            //TODO: Agregar items
                        }
                        break;
                    case 'L': // Cargar Locación
                        mapa.insertarVertice(linea.substring(2).replace(';', ' ').trim());
                        break;
                    case 'C': // Cargar Camino
                        String[] partes = linea.substring(2).split(";");

                        if (partes.length >= 3) {
                            mapa.insertarArco(partes[0].trim(), partes[1].trim(), 1);
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
     * Guarda el estado del juego a un archivo CSV.
     */
    public void guardar() {
        //TODO: Guardar nuevo estado del juego
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

        System.out.print(menu);
        System.out.print("Opción: ");
        accion = TecladoIn.readLineInt();

        while (!esAccionValida(accion)) {
            System.out.println("La opción ingresada no es válida.");
            System.out.print("Intente nuevamente: ");
            accion = TecladoIn.readLineInt();
        }

        return accion;
    }

    /**
     * Verifica si una acción es válida.
     *
     * @param accion la acción a verificar
     * @return verdadero si la acción es válida, falso en caso contrario
     */
    private static boolean esAccionValida(int accion) {
        boolean valida = false;

        switch (accion) {
            case AGREGAR_JUGADOR:
            case BORRAR_JUGADOR:
            case MODIFICAR_JUGADOR:
            case CONSULTAR_JUGADOR:
            case FILTRAR_JUGADORES:
            case PONER_JUGADOR_EN_ESPERA:
            case AGREGAR_ITEM:
            case BORRAR_ITEM:
            case MODIFICAR_ITEM:
            case CONSULTAR_ITEM:
            case MOSTRAR_ITEMS_HASTA_PRECIO:
            case MOSTRAR_ITEMS_DESDE_HASTA_PRECIO:
            case AGREGAR_LOCACION:
            case BORRAR_LOCACION:
            case MODIFICAR_LOCACION:
            case MOSTRAR_LOCACIONES_ADYACENTES:
            case MOSTRAR_CAMINO_MAS_CORTO:
            case MOSTRAR_CAMINO_MAS_DIRECTO:
            case MOSTRAR_CAMINO_HASTA_DISTANCIA:
            case MOSTRAR_CAMINO_SIN_LOCACION:
            case CONSULTAR_EQUIPO:
            case CREAR_EQUIPO:
            case INICIAR_BATALLA_ENTRE_EQUIPOS:
            case MOSTRAR_RANKING_JUGADORES:
            case MOSTRAR_ITEMS_ULTIMA_DISPONIBILIDAD:
            case MOSTRAR_SISTEMA:
            case SALIR:
                valida = true;
        }

        return valida;
    }

    /**
     * Método de utilidad para esperar hasta que el usuario quiera continuar presionando "Entrar".
     */
    private static void pausar() {
        System.out.println("Presionar [Entrar] para volver al menú...");
        TecladoIn.readLine();
    }

    /**
     * Agrega información al registro del juego.
     */
    private void log(String info) {
        System.out.println(info);
        //TODO: Registrar acciones en archivo LOG
    }

    /**
     * Lee un nombre de usuario.
     *
     * @return el usuario leído
     */
    private static String leerNombreUsuario() {
        return Funciones.leerPalabra("Nombre de usuario: ", "El nombre de usuario ingresado no es válido.\r\n"
                + "Debe ser una palabra de 1 a 20 caracteres que inicie con letra.\r\nReintentar: ", 1, 20);
    }

    /**
     * Lee el tipo de jugador.
     *
     * @return el tipo de jugador leído
     */
    private static TipoJugador leerTipo() {
        System.out.print("Tipo de jugador (<0> Guerrero - <1> Defensor): ");
        TipoJugador tipo = TipoJugador.desdeEntero(TecladoIn.readLineInt());

        while (tipo == null) {
            System.out.println("El tipo de jugador ingresado no es válido.");
            System.out.println("Debe ser un entero según se indica: <0> Guerrero, <1> Defensor.");
            System.out.print("Reintentar: ");
            tipo = TipoJugador.desdeEntero(TecladoIn.readLineInt());
        }

        return tipo;
    }

    /**
     * Lee una categoría.
     *
     * @return la categoría leída
     */
    private static Categoria leerCategoria() {
        System.out.print("Categoría (<0> Novato - <1> Aficionado - <2> Profesional): ");
        Categoria categoria = Categoria.desdeEntero(TecladoIn.readLineInt());

        while (categoria == null) {
            System.out.println("La categoría ingresada no es válida.");
            System.out.println("Debe ser un entero según se indica: <0> Novato - <1> Aficionado - <2> Profesional.");
            System.out.print("Reintentar: ");
            categoria = Categoria.desdeEntero(TecladoIn.readLineInt());
        }

        return categoria;
    }

    private static int leerDinero() {
        return Funciones.leerEnteroPositivo("Dinero: ",
                "El dinero ingresado no es válido.\r\nDebe ser un entero positivo o cero.\r\nReintentar: ");
    }

    private static String leerPrefijoUsuario() {
        return Funciones.leerPalabra("Prefijo: ",
                "El prefijo ingresado no es válido.\r\nReintentar: ", 1, 20);
    }

    private static String leerCodigoItem() {
        return Funciones.leerPalabra("Código: ",
                "El código ingresado no es válido.\r\nDebe ser una letra seguido de 3 dígitos.\r\nReintentar: ", 4, 4);
    }

    private static String leerNombreItem() {
        return Funciones.leerFrase("Nombre: ",
                "El nombre ingresado no es válido.\r\nDebe ser una frase alfanumérica de 2 a 50 caracteres."
                        + "\r\nReintentar: ",
                2, 50);
    }

    private static int leerPrecio() {
        return Funciones.leerEntero("Precio: ",
                "El precio ingresado no es válido.\r\nDebe ser un entero positivo.\r\nReintentar: ",
                1, Integer.MAX_VALUE);
    }

    private static int leerAtaque() {
        return Funciones.leerEnteroPositivo("Puntos de ataque: ",
                "Los puntos de ataque ingresados no son válidos.\r\nDebe ser un entero positivo o cero."
                        + "\r\nReintentar: ");
    }

    private static int leerDefensa() {
        return Funciones.leerEnteroPositivo("Puntos de defensa: ",
                "Los puntos de defensa ingresados no son válidos.\r\nDebe ser un entero positivo o cero."
                        + "\r\nReintentar: ");
    }

    private static int leerDisponibilidad() {
        return Funciones.leerEntero("Disponibilidad: ",
                "La disponibilidad ingresada no es válida.\r\nDebe ser un entero positivo.\r\nReintentar: ",
                1, Integer.MAX_VALUE);
    }

    /**
     * A. ABM (Altas-Bajas-Modificaciones) de jugadores:
     * Agregar jugador.
     */
    public void agregarJugador() {
        System.out.println("Agregar jugador...");
        Jugador jugador = new Jugador();
        jugador.setUsuario(leerNombreUsuario());
        jugador.setTipo(leerTipo());
        jugador.setCategoria(leerCategoria());
        jugador.setDinero(leerDinero());
        jugadores.put(jugador.getUsuario().toUpperCase(), jugador);
        log(String.format("Se agregó el jugador \"%s\"", jugador.getUsuario()));
    }

    /**
     * A. ABM (Altas-Bajas-Modificaciones) de jugadores:
     * Borrar jugador.
     */
    public void borrarJugador() {
        System.out.println("Borrar jugador...");
        String usuario = leerNombreUsuario();
        Jugador jugador = jugadores.remove(usuario.toUpperCase());

        if (jugador == null) {
            log(String.format("Se intentó borrar un jugador inexistente \"%s\"", usuario));
        } else {
            log(String.format("Se borró el jugador \"%s\"", jugador.getUsuario()));
        }
    }

    /**
     * A. ABM (Altas-Bajas-Modificaciones) de jugadores:
     * Modificar jugador.
     */
    public void modificarJugador() {
        System.out.println("Modificar jugador...");
        String usuario = leerNombreUsuario();
        String usuarioClave = usuario.toUpperCase();

        if (jugadores.containsKey(usuarioClave)) {
            Jugador jugador = jugadores.get(usuarioClave);
            usuario = jugador.getUsuario();
            int opcion = 0;

            do {
                opcion = leerAccionModificarJugador();

                switch (opcion) {
                    case 1:
                        jugadores.remove(usuarioClave);
                        String usuarioAnt = usuario;
                        usuario = leerNombreUsuario();
                        usuarioClave = usuario.toUpperCase();
                        jugador.setUsuario(usuario);
                        jugadores.put(usuarioClave, jugador);
                        log(String.format("Se modificó el usuario del jugador \"%s\" a \"%s\"", usuarioAnt, usuario));
                        break;
                    case 2:
                        jugador.setTipo(leerTipo());
                        log(String.format("Se modificó el tipo del jugador \"%s\" a %s", usuario, jugador.getTipo()));
                        break;
                    case 3:
                        jugador.setCategoria(leerCategoria());
                        log(String.format("Se modificó la categoría del jugador \"%s\" a %s", usuario,
                                jugador.getCategoria()));
                        break;
                    case 4:
                        jugador.setDinero(leerDinero());
                        log(String.format("Se modificó el dinero del jugador \"%s\" a %d", usuario,
                                jugador.getDinero()));
                        break;
                }
            } while (opcion != 0);
        } else {
            log(String.format("Se intentó modificar un jugador inexistente \"%s\"", usuario));
        }
    }

    /**
     * Lee una opción para modificar jugador.
     *
     * @return la opción de modificación
     */
    private static int leerAccionModificarJugador() {
        System.out.print("Opción a modificar ");
        System.out.print("(<1> Usuario - <2> Tipo - <3> Categoría - <4> Dinero - <0> Cancelar): ");
        int accion = TecladoIn.readLineInt();

        while (accion < 0 || 4 < accion) {
            System.out.println("La opción ingresada no es válida.");
            System.out.println("Debe ser un entero según se indica:");
            System.out.println("<1> Usuario - <2> Tipo - <3> Categoría - <4> Dinero - <0> Cancelar");
            System.out.print("Reintentar: ");
            accion = TecladoIn.readLineInt();
        }

        return accion;
    }

    /**
     * I. Consultas sobre jugadores:
     * Dado un nombre de usuario de un jugador, mostrar todos sus datos.
     */
    public void consultarJugador() {
        String usuario = leerNombreUsuario().toUpperCase();

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
            //TODO: Mostrar ítems
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
        System.out.println("Filtrar jugadores...");
        String prefijo = leerPrefijoUsuario();
        Set<String> claves = jugadores.keySet();
        String[] usuarios = claves.toArray(new String[claves.size()]);
        Lista<Jugador> filtrados = new Lista<>();

        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i].regionMatches(true, 0, prefijo, 0, prefijo.length())) {
                filtrados.insertar(jugadores.get(usuarios[i]), filtrados.longitud() + 1);
            }
        }

        if (filtrados.longitud() > 0) {
            System.out.println(String.format("Usuarios que comienzan con \"%s\":", prefijo));

            for (int i = 1; i <= filtrados.longitud(); i++) {
                System.out.println(i + ": " + filtrados.recuperar(i).getUsuario());
            }
        } else {
            System.out.println(String.format("No existen jugadores cuyo nombre comience con \"%s\"", prefijo));
        }
    }

    /**
     * D. Alta de un jugador en la cola de espera por un equipo
     */
    public void ponerJugadorEnEspera() {
        //TODO: ponerJugadorEnEspera()
        System.out.println("Poner jugador en espera...");
        String usuario = leerNombreUsuario();

        if (jugadores.containsKey(usuario)) {
            esperando.offer(jugadores.get(usuario));
        }
    }

    /**
     * B. ABM de ítems:
     * Agregar ítem.
     */
    public void agregarItem() {
        System.out.println("Agregar ítem...");
        Item item = new Item();
        item.setCodigo(Item.generarCodigo());
        item.setNombre(leerNombreItem());
        item.setPrecio(leerPrecio());
        item.setAtaque(leerAtaque());
        item.setDefensa(leerDefensa());
        item.setDisponibilidad(leerDisponibilidad());
        items.insertar(item);
        log(String.format("Se agregó el ítem \"%s\" (%s)", item.getNombre(), item.getCodigo()));
    }

    /**
     * B. ABM de ítems:
     * Borrar ítem.
     */
    public void borrarItem() {
        System.out.println("Borrar ítem...");
        String codigo = leerCodigoItem().toUpperCase();
        Item item = items.obtener(codigo);
        boolean borrado = items.eliminar(item);
        //TODO: Borrar de los jugadores
        if (borrado) {
            log(String.format("Se borró el ítem \"%s\"", codigo));
        } else {
            log(String.format("Se intentó borrar un ítem inexistente \"%s\"", codigo));
        }
    }

    /**
     * H. Consultas sobre items:
     * Dado un código de ítem, mostrar sus atributos.
     */
    public void consultarItem() {
        System.out.println("Consultar ítem...");
        String codigo = leerCodigoItem().toUpperCase();
        Item item = items.obtener(codigo);

        if (item != null) {
            StringBuilder datos = new StringBuilder();
            datos.append("Código:         ").append(item.getCodigo()).append("\r\n");
            datos.append("Nombre:         ").append(item.getNombre()).append("\r\n");
            datos.append("Precio:         ").append(item.getPrecio()).append("\r\n");
            datos.append("Ataque:         ").append(item.getAtaque()).append("\r\n");
            datos.append("Defensa:        ").append(item.getDefensa()).append("\r\n");
            datos.append("Disponibilidad: ").append(item.getCantidad()).append(" de ");
            datos.append(item.getDisponibilidad()).append("\r\n");

            System.out.println(datos);
            log(String.format("Se consultó el ítem \"%s\"", codigo));
        } else {
            log(String.format("Se intentó consultar un ítem inexistente \"%s\"", codigo));
        }
    }

    /**
     * Muestra el estado del juego.
     */
    public void mostrarSistema() {
        Iterator<Jugador> iterador = jugadores.values().iterator();

        System.out.println("Jugadores:");

        while (iterador.hasNext()) {
            System.out.println(iterador.next());
        }

        System.out.println("Ítems:");

        Lista<Item> items = this.items.listar();

        for (int i = 1; i <= items.longitud(); i++) {
            System.out.println(items.recuperar(i));
        }
    }
}
