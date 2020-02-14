package tpfinal;

import grafos.dinamicas.Camino;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;

import conjuntistas.Diccionario;
import conjuntistas.TablaHashAbierto;
import grafos.dinamicas.GrafoPonderado;
import java.util.concurrent.ThreadLocalRandom;
import lineales.dinamicas.ColaPrioridad;
import lineales.dinamicas.Lista;
import utiles.Funciones;

/**
 * Juego.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Juego {

    /**
     * El archivo donde se carga y/o guarda el estado del juego.
     */
    private static final String ARCHIVO_ESTADO = "estado.csv";

    /**
     * El archivo de registro de acciones del juego.
     */
    private static final String ARCHIVO_REGISTRO = "registro.log";

    /**
     * Equipos creados (HashMap con clave tipo String y dato tipo Equipo).
     */
    private HashMap<String, Equipo> equipos;

    /**
     * Jugadores en el juego (diccionario con clave tipo String y dato tipo Jugador).
     */
    private Diccionario<String, Jugador> jugadores;

    /**
     * Jugadores en espera (cola de prioridad con elementos tipo Jugador y prioridad tipo Categoria).
     */
    private ColaPrioridad<Jugador, Categoria> esperando;

    /**
     * Los ítems disponibles en el juego (tabla de búsqueda por código).
     */
    private Diccionario<String, Item> items;

    /**
     * El inventario de ítems para la venta (ordenados por precio).
     */
    private Diccionario<Integer, Item> inventario;

    /**
     * El mapa del juego (grafo etiquetado extendido con elementos tipo String y etiquetas tipo Integer).
     */
    private GrafoPonderado<String> mapa;

    /**
     * Tabla Hash para generar códigos de ítem únicos.
     */
    private static final TablaHashAbierto<String> codigos = new TablaHashAbierto<>();

    /**
     * Letra actual para generar códigos de ítem.
     */
    private static char letra = 'A';

    /**
     * Número actual para generar códigos de ítem.
     */
    private static int secuencia = 0;

    /**
     * Constructor.
     */
    public Juego() {
        equipos = new HashMap<>();
        jugadores = new Diccionario<>();
        esperando = new ColaPrioridad<>();
        items = new Diccionario<>();
        inventario = new Diccionario<>();
        mapa = new GrafoPonderado<>();
    }

    /**
     * Devuelve una copia del mapa del juego.
     *
     * @return el mapa
     */
    public GrafoPonderado<String> getMapa() {
        return (GrafoPonderado<String>) mapa.clone();
    }

    /**
     * Inicia el juego.
     */
    public void iniciar() {
        System.out.println("************************** Calabozos & Estructuras **************************");
        cargar(ARCHIVO_ESTADO);
        menuPrincipal();
        guardar(ARCHIVO_ESTADO);
    }

    /**
     * Carga el estado del juego desde un archivo CSV (se asume formato válido generado por el juego).
     */
    public void cargar(String nombreArchivo) {
        try {
            String url = Juego.class.getResource(nombreArchivo).getPath();
            BufferedReader archivoEstado = new BufferedReader(new FileReader(url));
            String linea = archivoEstado.readLine();

            while (linea != null && !linea.isEmpty()) {
                switch (linea.charAt(0)) {
                    case 'I': // Cargar Ítem
                        insertarItem(crearItemDesdeCadena(linea.substring(2)));
                        break;
                    case 'J': // Cargar Jugador
                        Jugador jugador = crearJugadorDesdeCadena(linea.substring(2));

                        if (jugador != null) {
                            jugadores.insertar(jugador.getUsuario().toLowerCase(), jugador);
                        }
                        break;
                    case 'L': // Cargar Locación
                        mapa.insertarVertice(linea.substring(2).replace(";", ""));
                        break;
                    case 'C': // Cargar Camino
                        String[] partes = linea.substring(2).split(";");

                        if (partes.length >= 3) {
                            double etiqueta = Double.valueOf(partes[2]);
                            mapa.insertarArco(partes[0], partes[1], etiqueta);
                        }
                        break;
                    case 'E': // Cargar Equipo
                        Equipo equipo = crearEquipoDesdeCadena(linea.substring(2));

                        if (equipo != null) {
                            equipos.put(equipo.getNombre().toLowerCase(), equipo);
                        }
                        break;
                    case '#':
                        // Ignorar comentario
                        break;
                }

                linea = archivoEstado.readLine();
            }

            archivoEstado.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Item crearItemDesdeCadena(String cadena) {
        Item nuevoItem = null;
        String[] partes = cadena.split(";");

        if (partes.length >= 7) {
            String codigo = partes[0];
            String nombre = partes[1];
            int precio = Integer.valueOf(partes[2]);
            int ataque = Integer.valueOf(partes[3]);
            int defensa = Integer.valueOf(partes[4]);
            int cantidad = Integer.valueOf(partes[5]);
            int cantidadDisponible = Integer.valueOf(partes[6]);
            nuevoItem = new Item(codigo, nombre, precio, ataque, defensa, cantidad, cantidadDisponible);
        }

        return nuevoItem;
    }

    private Jugador crearJugadorDesdeCadena(String cadena) {
        Jugador nuevoJugador = null;
        String[] partes = cadena.split(";");

        if (partes.length >= 7) {
            String usuario = partes[0];
            Categoria categoria = Categoria.valueOf(partes[2].toUpperCase());
            int dinero = Integer.valueOf(partes[3]);
            int victorias = Integer.valueOf(partes[5]);
            int derrotas = Integer.valueOf(partes[6]);
            String cadenaItems = partes[4].substring(1, partes[4].length() - 1);

            nuevoJugador = crearJugadorSegunTipo(partes[1], usuario, categoria, dinero);
            nuevoJugador.setVictorias(victorias);
            nuevoJugador.setDerrotas(derrotas);

            // Leer y agregar ítems del jugador
            if (!cadenaItems.isEmpty()) {
                String[] codigos = cadenaItems.split(",");
                Lista<Item> itemsJugador = nuevoJugador.getItems();

                for (int i = 0; i < codigos.length; i++) {
                    Item item = items.obtenerInformacion(codigos[i]);
                    if (item != null) {
                        itemsJugador.insertar(item, itemsJugador.longitud() + 1);
                    }
                }
            }
        }

        return nuevoJugador;
    }

    private Equipo crearEquipoDesdeCadena(String cadena) {
        Equipo nuevoEquipo = null;
        String[] partes = cadena.split(";");

        if (partes.length >= 4) {
            String nombre = partes[0];
            Categoria categoria = Categoria.valueOf(partes[1].toUpperCase());
            String locacion = partes[2];
            String cadenaJugadores = partes[3].substring(1, partes[3].length() - 1);
            nuevoEquipo = new Equipo(nombre, locacion);
            nuevoEquipo.setCategoria(categoria);

            // Leer y agregar jugadores del equipo
            if (!cadenaJugadores.isEmpty()) {
                String[] usuarios = cadenaJugadores.split(",");
                Lista<Jugador> jugadoresEquipo = nuevoEquipo.getJugadores();

                for (int i = 0; i < usuarios.length; i++) {
                    Jugador jugador = jugadores.obtenerInformacion(usuarios[i].toLowerCase());
                    if (jugador != null) {
                        jugador.setEsperando(false);
                        jugador.setEquipo(nuevoEquipo);
                        jugadoresEquipo.insertar(jugador, jugadoresEquipo.longitud() + 1);
                    }
                }
            }
        }

        return nuevoEquipo;
    }

    private Jugador crearJugadorSegunTipo(int tipo, String usuario, Categoria categoria, int dinero) {
        String[] tipos = { "Guerrero", "Defensor"};
        return crearJugadorSegunTipo(tipos[tipo], usuario, categoria, dinero);
    }

    private Jugador crearJugadorSegunTipo(String tipo, String usuario, Categoria categoria, int dinero) {
        Jugador jugador = null;

        switch (tipo) {
        case "Guerrero":
            jugador = new Guerrero(usuario, categoria, dinero);
            break;
        case "Defensor":
            jugador = new Defensor(usuario, categoria, dinero);
            break;
        }

        return jugador;
    }

    /**
     * Guarda el estado del juego a un archivo CSV.
     */
    public void guardar(String nombreArchivo) {
        try {
            String url = Juego.class.getResource(nombreArchivo).getPath();
            PrintWriter salida = new PrintWriter(new FileOutputStream(url));
            Lista<Item> listaItems = items.listarDatos();
            Lista<Jugador> listaJugadores = jugadores.listarDatos();
            Iterator<Equipo> iteradorEquipos = equipos.values().iterator();

            // Ítems
            for (int i = 1; i <= listaItems.longitud(); i++) {
                salida.println("I:" + listaItems.recuperar(i));
            }

            // Jugadores
            for (int i = 1; i <= listaJugadores.longitud(); i++) {
                salida.println("J:" + listaJugadores.recuperar(i));
            }

            // Mapa
            salida.print(exportarMapaCSV());

            // Equipos
            while (iteradorEquipos.hasNext()) {
                salida.println("E:" + iteradorEquipos.next());
            }

            salida.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra información del juego y la guarda en el archivo de registro.
     *
     * @param info la información a mostrar y guardar
     */
    public void log(String info) {
        System.out.println(info);

        try {
            String url = Juego.class.getResource(ARCHIVO_REGISTRO).getPath();
            PrintWriter salida = new PrintWriter(new FileOutputStream(url, true));
            LocalDateTime fechaHora = LocalDateTime.now();
            DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            salida.println(String.format("[%s] %s", formatoFechaHora.format(fechaHora), info));
            salida.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Menú principal.
     */
    public void menuPrincipal() {
        int opcion = 0;

        do {
            titulo("Menú principal");
            opcion(1, "Jugadores");
            opcion(2, "Ítems");
            opcion(3, "Locaciones");
            opcion(4, "Equipos");
            opcion(5, "Sistema");
            opcion(0, "Salir");

            opcion = leerOpcion(0, 5);

            switch (opcion) {
                case 1:
                    menuJugadores();
                    break;
                case 2:
                    menuItems();
                    break;
                case 3:
                    menuLocaciones();
                    break;
                case 4:
                    menuEquipos();
                    break;
                case 5:
                    menuSistema();
                    break;
            }
        } while (opcion > 0);

        titulo("Fin");
    }

    /**
     * Muestra un texto con formato de título.
     *
     * @param titulo el texto
     */
    public static void titulo(String titulo) {
        System.out.println(
                String.format("~~~{ %s }~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", titulo)
                        .substring(0, 77));
    }

    /**
     * Muestra una opción numerada con formato.
     *
     * @param numero el número de la opción
     * @param nombre el texto de la opción
     */
    public static void opcion(int numero, String nombre) {
        System.out.println(String.format("   <%d> %s", numero, nombre));
    }

    /**
     * Lee una opción numérica de un rango de enteros dados.
     *
     * @param desde la opción mínima
     * @param hasta la opción máxima
     * @return el número de la opción indicada
     */
    public static int leerOpcion(int desde, int hasta) {
        return Funciones.leerEntero("Opción: ", "La opción ingresada no es válida.\r\nReintentar: ", desde, hasta);
    }

    /**
     * Menú de jugadores.
     */
    public void menuJugadores() {
        int opcion = 0;

        do {
            titulo("Jugadores");
            opcion(1, "Agregar jugador");
            opcion(2, "Borrar jugador");
            opcion(3, "Modificar jugador");
            opcion(4, "Consultar jugador");
            opcion(5, "Filtrar jugadores");
            opcion(6, "Poner jugador en espera");
            opcion(7, "Poner todos los jugadores en espera");
            opcion(0, "Volver");

            opcion = leerOpcion(0, 7);

            switch (opcion) {
                case 1:
                    agregarJugador();
                    break;
                case 2:
                    borrarJugador();
                    break;
                case 3:
                    modificarJugador();
                    break;
                case 4:
                    consultarJugador();
                    break;
                case 5:
                    filtrarJugadores();
                    break;
                case 6:
                    ponerJugadorEnEspera();
                    break;
                case 7:
                    ponerJugadoresEnEspera();
                    break;
            }

            if (opcion > 0) {
                Funciones.pausar();
            }
        } while (opcion > 0);
    }

    /**
     * A. ABM (Altas-Bajas-Modificaciones) de jugadores:
     * Agregar jugador.
     */
    public void agregarJugador() {
        titulo("Agregar jugador");

        int tipo = leerTipo();
        String usuario = leerNombreUsuario();
        Categoria categoria = leerCategoria();
        int dinero = leerDinero();
        Jugador jugador = crearJugadorSegunTipo(tipo, usuario, categoria, dinero);
        jugadores.insertar(usuario.toLowerCase(), jugador);

        log(String.format("Se agregó el jugador \"%s\"", usuario));
    }

    /**
     * A. ABM (Altas-Bajas-Modificaciones) de jugadores:
     * Borrar jugador.
     */
    public void borrarJugador() {
        titulo("Borrar jugador");

        if (!jugadores.esVacio()) {
            String usuario = leerNombreUsuario().toLowerCase();
            Jugador jugador = jugadores.obtenerInformacion(usuario);

            if (jugadores.eliminar(usuario)) {
                log(String.format("Se borró el jugador \"%s\"", usuario));
            } else {
                log(String.format("Se intentó borrar un jugador inexistente \"%s\"", usuario));
            }
        } else {
            System.out.println("No existen jugadores para borrar");
        }
    }

    /**
     * A. ABM (Altas-Bajas-Modificaciones) de jugadores:
     * Modificar jugador.
     */
    public void modificarJugador() {
        titulo("Modificar jugador");

        if (!jugadores.esVacio()) {
            String usuario = leerNombreUsuario().toLowerCase();

            if (jugadores.existeClave(usuario)) {
                modificarJugadorSegunOpcion(usuario);
            } else {
                log(String.format("Se intentó modificar un jugador inexistente \"%s\"", usuario));
            }
        } else {
            System.out.println("No existen jugadores para modificar");
        }
    }

    private void modificarJugadorSegunOpcion(String claveUsuario) {
        Jugador jugador = jugadores.obtenerInformacion(claveUsuario);
        String usuario = jugador.getUsuario();
        int opcion = 0;

        do {
            titulo(String.format("Modificar jugador \"%s\"", usuario));
            opcion(1, "Usuario");
            opcion(2, "Tipo");
            opcion(3, "Categoría");
            opcion(4, "Dinero");
            opcion(0, "Cancelar");

            opcion = leerOpcion(0, 4);

            switch (opcion) {
                case 1: // Modificar usuario de jugador
                    jugadores.eliminar(claveUsuario);
                    String usuarioAnterior = usuario;

                    do {
                        usuario = leerNombreUsuario();
                        claveUsuario = usuario.toLowerCase();
                    } while (jugadores.existeClave(claveUsuario));

                    jugador.setUsuario(usuario);
                    jugadores.insertar(claveUsuario, jugador);

                    log(String.format("Se modificó el usuario del jugador \"%s\" a \"%s\"", usuarioAnterior, usuario));
                    break;
                case 2: // Modificar tipo de jugador
                    int tipo = leerTipo();
                    String[] tipos = { "Guerrero", "Defensor" };

                    if (!jugador.getClass().getSimpleName().equals(tipos[tipo])) {
                        jugador = crearJugadorSegunTipo(tipo, usuario, jugador.getCategoria(), jugador.getDinero());
                    }

                    jugadores.eliminar(claveUsuario);
                    jugadores.insertar(claveUsuario, jugador);
                    log(String.format("Se modificó el tipo del jugador \"%s\" a %s", usuario, tipos[tipo]));
                    break;
                case 3: // Modificar categoría de jugador
                    jugador.setCategoria(leerCategoria());
                    log(String.format("Se modificó la categoría del jugador \"%s\" a %s", usuario,
                            jugador.getCategoria()));
                    break;
                case 4: // Modificar dinero de jugador
                    jugador.setDinero(leerDinero());
                    log(String.format("Se modificó el dinero del jugador \"%s\" a %s", usuario,
                            formDinero(jugador.getDinero())));
                    break;
            }
        } while (opcion != 0);
    }

    /**
     * I. Consultas sobre jugadores:
     * Dado un nombre de usuario de un jugador, mostrar todos sus datos.
     */
    public void consultarJugador() {
        titulo("Consultar jugador");

        if (!jugadores.esVacio()) {
            String usuario = leerNombreUsuario().toLowerCase();

            if (jugadores.existeClave(usuario)) {
                Jugador jugador = jugadores.obtenerInformacion(usuario);
                Lista<Item> itemsJugador = jugador.getItems();
                StringBuilder datos = new StringBuilder();

                datos.append("Usuario:   ").append(jugador.getUsuario()).append("\r\n");
                datos.append("Tipo:      ").append(jugador.getClass().getSimpleName()).append("\r\n");
                datos.append("Categoría: ").append(jugador.getCategoria()).append("\r\n");
                datos.append("Dinero:    ").append(formDinero(jugador.getDinero())).append("\r\n");
                datos.append("Salud:     ").append(jugador.getSalud() + "/" + jugador.getSaludTotal()).append("\r\n");
                datos.append("Victorias: ").append(jugador.getVictorias()).append("\r\n");
                datos.append("Derrotas:  ").append(jugador.getDerrotas()).append("\r\n");
                datos.append("Equipo:    ");

                if (jugador.tieneEquipo()) {
                    datos.append(jugador.getEquipo().getNombre());
                } else if (jugador.getEsperando()) {
                    datos.append("(esperando equipo...)");
                } else {
                    datos.append("-");
                }

                datos.append("\r\n");
                datos.append("Items:     ");

                if (!itemsJugador.esVacia()) {
                    for (int i = 1; i <= itemsJugador.longitud(); i++) {
                        datos.append(formItem(itemsJugador.recuperar(i)));

                        if (i < itemsJugador.longitud()) {
                            datos.append("\r\n           ");
                        }
                    }
                } else {
                    datos.append("-");
                }

                System.out.println(datos);
                log(String.format("Se consultó el jugador \"%s\"", jugador.getUsuario()));
            } else {
                log(String.format("Se intentó consultar un jugador inexistente \"%s\"", usuario));
            }
        } else {
            System.out.println("No existen jugadores para consultar");
        }
    }

    /**
     * I. Consultas sobre jugadores:
     * Dada una subcadena, mostrar todos los nombres de usuarios que comienzan con esa subcadena.
     */
    public void filtrarJugadores() {
        titulo("Filtrar jugadores");

        if (!jugadores.esVacio()) {
            String prefijo = leerPrefijoUsuario();
            Lista<Jugador> datos = jugadores.listarDatos();

            if (datos.longitud() > 0) {
                System.out.println(String.format("Usuarios que comienzan con \"%s\":", prefijo));
                String usuario;

                for (int i = 1; i <= datos.longitud(); i++) {
                    usuario = datos.recuperar(i).getUsuario();

                    if (!usuario.regionMatches(true, 0, prefijo, 0, prefijo.length())) {
                        datos.eliminar(i);
                    } else {
                        System.out.println(String.format("%d: %s", i, usuario));
                    }
                }

                if (datos.esVacia()) {
                    System.out.println(String.format("No existen jugadores cuyo nombre comience con \"%s\"", prefijo));
                }
            }
        } else {
            System.out.println("No existen jugadores para filtrar");
        }
    }

    /**
     * D. Alta de un jugador en la cola de espera por un equipo.
     */
    public void ponerJugadorEnEspera() {
        titulo("Poner jugador en espera");

        if (!jugadores.esVacio()) {
            String usuario = leerNombreUsuario().toLowerCase();

            if (jugadores.existeClave(usuario)) {
                Jugador jugador = jugadores.obtenerInformacion(usuario);

                if (!jugador.tieneEquipo() && !jugador.getEsperando()) {
                    jugador.setEsperando(true);
                    esperando.insertar(jugador, jugador.getCategoria());

                    log(String.format("Se colocó el jugador \"%s\" en espera", jugador.getUsuario()));
                } else {
                    System.out.println(String.format("El jugador \"%s\" ya tiene un equipo (%s)", jugador.getUsuario(),
                            jugador.getEquipo().getNombre()));
                }
            } else {
                System.out.println(String.format("No existe el jugador \"%s\" para colocarlo en espera", usuario));
            }
        } else {
            System.out.println("No existen jugadores para poner en espera");
        }
    }

    /**
     * Alta de todos los jugadores en la cola de espera por un equipo.
     * Agregado para agilizar pruebas.
     */
    public void ponerJugadoresEnEspera() {
        titulo("Poner todos los jugadores en espera");

        if (!jugadores.esVacio()) {
            Lista<Jugador> todos = jugadores.listarDatos();
            int cantidad = todos.longitud();
            int agregados = 0;
            Jugador jugador;

            for (int i = 1; i <= cantidad; i++) {
                jugador = todos.recuperar(i);

                if (!jugador.tieneEquipo() && !jugador.getEsperando()) {
                    jugador.setEsperando(true);
                    esperando.insertar(jugador, jugador.getCategoria());
                    agregados++;
                }
            }

            if (agregados > 0) {
                log(String.format("Se colocaron %d jugadores en espera", agregados));
            } else {
                System.out.println("Todos los jugadores ya están en espera o tienen un equipo");
            }
        } else {
            System.out.println("No existen jugadores para ponerlos en espera");
        }
    }

    /**
     * Solicita y lee un nombre de usuario.
     *
     * @return el usuario leído
     */
    private static String leerNombreUsuario() {
        return Funciones.leerPalabra("Nombre de usuario: ", "El nombre de usuario ingresado no es válido.\r\n"
                + "Debe ser una palabra de 1 a 20 caracteres que inicie con letra.\r\nReintentar: ", 1, 20);
    }

    /**
     * Solicita y lee el tipo de jugador.
     *
     * @return el tipo de jugador leído
     */
    private static int leerTipo() {
        return Funciones.leerEntero("Tipo de jugador <0> Guerrero - <1> Defensor: ",
                "El tipo de jugador ingresado no es válido.\r\nReintentar: ", 0, 1);
    }

    /**
     * Solicita y lee una categoría al usuario.
     *
     * @return la categoría leída
     */
    private static Categoria leerCategoria() {
        return Categoria.desdeEntero(Funciones.leerEntero("Categoría <0> Profesional - <1> Aficionado - <2> Novato: ",
                "La categoría ingresada no es válida.\r\nReintentar: ", 0, 2));
    }

    /**
     * Solicita y lee una cantidad de dinero al usuario.
     *
     * @return la cantidad de dinero leída
     */
    private static int leerDinero() {
        return Funciones.leerEnteroPositivo("Dinero: ",
                "El dinero ingresado no es válido.\r\nDebe ser un entero positivo o cero.\r\nReintentar: ");
    }

    /**
     * Solicita y lee una cantidad de dinero al usuario.
     *
     * @return el prefijo de usuario leído
     */
    private static String leerPrefijoUsuario() {
        return Funciones.leerPalabra("Prefijo: ",
                "El prefijo ingresado no es válido.\r\nReintentar: ", 1, 20);
    }

    /**
     * Menú de ítems.
     */
    public void menuItems() {
        int opcion;

        do {
            titulo("Ítems");
            opcion(1, "Agregar ítem");
            opcion(2, "Borrar ítem");
            opcion(3, "Modificar ítem");
            opcion(4, "Consultar ítem");
            opcion(5, "Mostrar ítems para comprar según jugador");
            opcion(6, "Mostrar ítems para comprar (con precio min. y max.)");
            opcion(0, "Volver");

            opcion = leerOpcion(0, 6);

            switch (opcion) {
                case 1:
                    agregarItem();
                    break;
                case 2:
                    borrarItem();
                    break;
                case 3:
                    modificarItem();
                    break;
                case 4:
                    consultarItem();
                    break;
                case 5:
                    mostrarItemsHastaPrecio();
                    break;
                case 6:
                    mostrarItemsDesdeHastaPrecio();
                    break;
            }

            if (opcion > 0) {
                Funciones.pausar();
            }
        } while (opcion > 0);
    }

    private boolean insertarItem(Item item) {
        Item itemSiguiente = inventario.obtenerInformacion(item.getPrecio());

        // Agregar al inventario de ítems agrupados por precio
        if (itemSiguiente == null) {
            inventario.insertar(item.getPrecio(), item);
        } else {
            item.setSiguienteIgualPrecio(itemSiguiente.getSiguienteIgualPrecio());
            itemSiguiente.setSiguienteIgualPrecio(item);
        }

        return items.insertar(item.getCodigo(), item);
    }

    private boolean eliminarItem(Item item) {
        Item itemSiguiente = inventario.obtenerInformacion(item.getPrecio());

        // Quitar del inventario de ítems agrupados por precio
        if (itemSiguiente != null) {
            if (item.equals(itemSiguiente)) { // Es el primero del grupo de igual precio
                inventario.eliminar(item.getPrecio());
                itemSiguiente = itemSiguiente.getSiguienteIgualPrecio();

                // Si tenía ítems asociados por precio, deben quedar en el inventario
                if (itemSiguiente != null) {
                    inventario.insertar(itemSiguiente.getPrecio(), itemSiguiente);
                }
            } else { // El ítem esta asociado a otro de igual precio
                Item itemAnterior = itemSiguiente;
                itemSiguiente = itemSiguiente.getSiguienteIgualPrecio();
                boolean itemBorrado = false;

                while (itemSiguiente != null && !itemBorrado) {
                    if (itemSiguiente.equals(item)) {
                        itemAnterior.setSiguienteIgualPrecio(itemSiguiente.getSiguienteIgualPrecio());
                        itemBorrado = true;
                    }
                }
            }
        }

        // Borrar ítem del inventario de los jugadores
        Lista<Jugador> listaJugadores = jugadores.listarDatos();
        Lista<Item> listaItems;
        int posicionItem = -1;

        for (int i = 1; i <= listaJugadores.longitud(); i++) {
            listaItems = listaJugadores.recuperar(i).getItems();
            posicionItem = listaItems.localizar(item);

            do {
                listaItems.eliminar(posicionItem);
                posicionItem = listaItems.localizar(item);
            } while (posicionItem > -1);
        }

        return items.eliminar(item.getCodigo());
    }

    /**
     * B. ABM de ítems:
     * Agregar ítem.
     */
    public void agregarItem() {
        titulo("Agregar ítem");

        String codigo = generarCodigoItem();
        String nombre = leerNombreItem();
        int precio = leerPrecio();
        int ataque = leerAtaque();
        int defensa = leerDefensa();
        int cantidad = leerDisponibilidad();
        Item item = new Item(codigo, nombre, precio, ataque, defensa, cantidad, cantidad);
        insertarItem(item);

        log(String.format("Se agregó el ítem \"%s\" (%s)", nombre, codigo));
    }

    /**
     * B. ABM de ítems:
     * Borrar ítem.
     */
    public void borrarItem() {
        titulo("Borrar ítem");

        if (!items.esVacio()) {
            String codigo = leerCodigoItem().toUpperCase();
            Item item = items.obtenerInformacion(codigo);
            boolean borrado = eliminarItem(item);

            if (borrado) {
                log(String.format("Se borró el ítem \"%s\"", codigo));
            } else {
                log(String.format("Se intentó borrar un ítem inexistente \"%s\"", codigo));
            }
        } else {
            System.out.println("No existen ítems para borrar");
        }
    }

    /**
     * B. ABM de ítems:
     * Modificar ítem.
     */
    public void modificarItem() {
        titulo("Modificar ítem");

        if (!items.esVacio()) {
            String codigo = leerCodigoItem().toUpperCase();
            Item item = items.obtenerInformacion(codigo);

            if (item != null) {
                modificarItemsegunOpcion(item);
            } else {
                log(String.format("Se intentó modificar un ítem inexistente \"%s\"", codigo));
            }
        } else {
            System.out.println("No existen ítems para modificar");
        }
    }

    private void modificarItemsegunOpcion(Item item) {
        int opcion = 0;

        do {
            titulo(String.format("Modificar ítem \"%s\"", item.getNombre()));
            opcion(1, "Nombre");
            opcion(2, "Precio");
            opcion(3, "Ataque");
            opcion(4, "Defensa");
            opcion(0, "Cancelar");

            opcion = leerOpcion(0, 4);

            switch (opcion) {
                case 1: // Modificar nombre del ítem
                    String nombreAnterior = item.getNombre();
                    System.out.println("Nombre actual: " + nombreAnterior);
                    String nombreNuevo = leerNombreItem();
                    item.setNombre(nombreNuevo);

                    log(String.format("Se modificó el nombre del ítem \"%s\" a \"%s\"", nombreAnterior, nombreNuevo));
                    break;
                case 2: // Modificar precio del ítem
                    int precioAnterior = item.getPrecio();
                    System.out.println("Precio actual: " + precioAnterior);
                    int precioNuevo = leerPrecio();
                    item.setPrecio(precioNuevo);
                    eliminarItem(item);
                    insertarItem(item);

                    log(String.format("Se modificó el precio del ítem \"%s\" de %s a %s", item.getNombre(),
                            formDinero(precioAnterior), formDinero(precioNuevo)));
                    break;
                case 3: // Modificar puntos de ataque del ítem
                    int ataqueAnterior = item.getAtaque();
                    System.out.println("Ataque actual: " + ataqueAnterior);
                    int ataqueNuevo = leerAtaque();
                    item.setAtaque(ataqueNuevo);

                    log(String.format("Se modificaron los puntos de ataque del ítem \"%s\" de %s a %s",
                            item.getNombre(), ataqueAnterior, ataqueNuevo));
                    break;
                case 4: // Modificar puntos de defensa del ítem
                    int defensaAnterior = item.getDefensa();
                    System.out.println("Defensa actual: " + defensaAnterior);
                    int defensaNueva = leerDefensa();
                    item.setDefensa(defensaNueva);

                    log(String.format("Se modificaron los puntos de defensa del ítem \"%s\" de %s a %s",
                            item.getNombre(), defensaAnterior, defensaNueva));
                    break;
            }
        } while (opcion != 0);
    }

    /**
     * H. Consultas sobre items:
     * Dado un código de ítem, mostrar sus atributos.
     */
    public void consultarItem() {
        titulo("Consultar ítem");

        if (!items.esVacio()) {
            String codigo = leerCodigoItem().toUpperCase();
            Item item = items.obtenerInformacion(codigo);

            if (item != null) {
                StringBuilder datos = new StringBuilder();
                datos.append("Información del ítem:\r\n");
                datos.append("Código:         ").append(item.getCodigo()).append("\r\n");
                datos.append("Nombre:         ").append(item.getNombre()).append("\r\n");
                datos.append("Precio:         ").append(formDinero(item.getPrecio())).append("\r\n");
                datos.append("Ataque:         ").append(item.getAtaque()).append("\r\n");
                datos.append("Defensa:        ").append(item.getDefensa()).append("\r\n");
                datos.append("Disponibilidad: ").append(item.getCantidadDisponible()).append('/');
                datos.append(item.getCantidad());

                System.out.println(datos);
                log(String.format("Se consultó el ítem \"%s\"", codigo));
            } else {
                log(String.format("Se intentó consultar un ítem inexistente \"%s\"", codigo));
            }
        } else {
            System.out.println("No existen ítems para consultar");
        }
    }

    /**
     * Consultas sobre items:
     * Dado un monto de dinero mostrar todos los items que puede comprar el jugador.
     */
    public void mostrarItemsHastaPrecio() {
        titulo("Ítems para comprar según el jugador");

        if (!inventario.esVacio()) {
            String usuario = leerNombreUsuario().toLowerCase();

            if (jugadores.existeClave(usuario)) {
                Jugador jugador = jugadores.obtenerInformacion(usuario);
                int dinero = jugador.getDinero();
                mostrarItemsParaComprar(jugador, inventario.listarRango(0, dinero));
            } else {
                System.out.println("No existen jugadores para consultar");
            }
        } else {
            System.out.println("No existen ítems para consultar");
        }
    }

    /**
     * Consultas sobre items:
     * Dadas dos valores (min y max que pueden no existir en la estructura) devolver todos los ítems que se puedan
     * comprar con un monto de dinero entre min y max (incluyendo ambos límites) ordenado de menor a mayor.
     */
    public void mostrarItemsDesdeHastaPrecio() {
        titulo("Ítems para comprar (con precio min. y max.)");

        if (!inventario.esVacio()) {
            System.out.print("Mínimo ");
            int minimo = leerDinero();
            System.out.print("Máximo ");
            int maximo = leerDinero();
            Lista<Item> itemsPosibles = inventario.listarRango(minimo, maximo);
            Item item;

            for (int i = 1; i <= itemsPosibles.longitud(); i++) {
                item = itemsPosibles.recuperar(i);
                System.out.println(String.format("%d: %s", i, formItem(item)));
            }
        } else {
            System.out.println("No existen ítems para consultar");
        }
    }

    private void mostrarItemsParaComprar(Jugador jugador, Lista<Item> listaItems) {
        if (!listaItems.esVacia()) {
            titulo(String.format("Comprar ítem para \"%s\"", jugador.getUsuario()));

            for (int i = 1; i <= listaItems.longitud(); i++) {
                opcion(i, formItem(listaItems.recuperar(i)));
            }

            opcion(0, "Cancelar");
            int opcion = leerOpcion(0, listaItems.longitud());

            if (opcion > 0) {
                comprarItem(jugador, listaItems.recuperar(opcion));
            }
        } else {
            System.out.println("No hay ítems disponibles para comprar");
        }
    }

    private void comprarItem(Jugador jugador, Item item) {
        if (jugador.comprarItem(item)) {
            log(String.format("El jugador \"%s\" compró \"%s\"", jugador.getUsuario(), item.getNombre()));
        } else {
            System.out.println(String.format("No hay disponibilidad para comprar el ítem %s", item.getNombre()));
        }
    }

    private static String generarCodigoItem() {
        String codigo = null;

        if (Funciones.esLetraMayus(letra)) {
            do {
                codigo = String.format("%s%03d", letra, secuencia);
                secuencia++;

                if (secuencia > 99) {
                    secuencia = 0;
                    letra++;
                }
            } while (codigos.pertenece(codigo) && Funciones.esLetraMayus(letra));
        }

        codigos.insertar(codigo);

        return codigo;
    }

    private static String leerCodigoItem() {
        return Funciones.leerPalabra("Código: ",
                "El código ingresado no es válido.\r\nDebe ser una letra seguido de 3 dígitos.\r\nReintentar: ", 4, 4);
    }

    private static String leerNombreItem() {
        return Funciones.leerFrase("Nombre del ítem: ",
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

    private static String formDinero(int dinero) {
        return Funciones.dinero(dinero, '$');
    }

    private static String formItem(Item item) {
        return String.format("(%s) %s - Atq.: %,d - Def.: %,d - Precio: %s", item.getCodigo(), item.getNombre(),
                item.getAtaque(), item.getDefensa(), formDinero(item.getPrecio()));
    }

    /**
     * Menú de locaciones.
     */
    public void menuLocaciones() {
        int opcion = 0;

        do {
            titulo("Locaciones");
            opcion(1, "Agregar locación");
            opcion(2, "Borrar locación");
            opcion(3, "Modificar locación");
            opcion(4, "Mostrar locaciones adyacentes");
            opcion(5, "Mostrar camino más corto (en kms)");
            opcion(6, "Mostrar camino más directo");
            opcion(7, "Mostrar caminos con distancia máxima");
            opcion(8, "Mostrar camino más directo sin pasar por una locación");
            opcion(0, "Volver");

            opcion = leerOpcion(0, 8);

            switch (opcion) {
                case 1:
                    agregarLocacion();
                    break;
                case 2:
                    borrarLocacion();
                    break;
                case 3:
                    modificarLocacion();
                    break;
                case 4:
                    mostrarLocacionesAdyacentes();
                    break;
                case 5:
                    mostrarCaminoMasCortoKms();
                    break;
                case 6:
                    mostrarCaminoMasDirecto();
                    break;
                case 7:
                    mostrarCaminosHastaDistancia();
                    break;
                case 8:
                    mostrarCaminoSinLocacion();
                    break;
            }

            if (opcion > 0) {
                Funciones.pausar();
            }
        } while (opcion > 0);
    }

    /**
     * C. ABM de locaciones
     * Agregar locación.
     */
    public void agregarLocacion() {
        titulo("Agregar locación");

        String locacion = leerLocacion();
        mapa.insertarVertice(locacion);

        log(String.format("Se agregó la locación \"%s\"", locacion));
    }

    /**
     * C. ABM de locaciones
     * Borrar locación.
     */
    public void borrarLocacion() {
        titulo("Borrar locación");

        if (!mapa.esVacio()) {
            String locacion = leerLocacion();

            if (mapa.eliminarVertice(locacion)) {
                log(String.format("Se borró la locación \"%s\"", locacion));
            } else {
                log(String.format("Se intentó borrar la locación inexistente \"%s\"", locacion));
            }
        } else {
            System.out.println("No existen locaciones para borrar");
        }
    }

    /**
     * C. ABM de locaciones
     * Modificar locación.
     */
    public void modificarLocacion() {
        titulo("Modificar locación");

        if (!mapa.esVacio()) {
            String locacion = leerLocacion();

            if (mapa.existeVertice(locacion)) {
                modificarLocacionSegunOpcion(locacion);
            } else {
                log(String.format("Se intentó modificar la locación inexistente \"%s\"", locacion));
            }
        } else {
            System.out.println("No existen locaciones para modificar");
        }
    }

    private void modificarLocacionSegunOpcion(String locacion) {
        int opcion = 0;

        do {
            titulo(String.format("Modificar locación \"%s\"", locacion));
            opcion(1, "Nombre");
            opcion(2, "Agregar camino");
            opcion(3, "Borrar camino");
            opcion(4, "Modificar camino (distancia)");
            opcion(0, "Cancelar");

            opcion = leerOpcion(0, 4);

            switch (opcion) {
                case 1:
                    locacion = modificarNombreLocacion(locacion);
                    break;
                case 2:
                    agregarCamino(locacion);
                    break;
                case 3:
                    borrarCamino(locacion);
                    break;
                case 4:
                    modificarDistancia(locacion);
                    break;
            }
        } while (opcion > 0);
    }

    private String modificarNombreLocacion(String locacion) {
        String nuevaLocacion = leerLocacion();
        String adyacente;
        Lista<String> adyacentes = mapa.listarAdyacentes(locacion);
        mapa.insertarVertice(nuevaLocacion);

        // Crear nueva locación, copiar adyacentes y borrar la anterior
        for (int i = 1; i <= adyacentes.longitud(); i++) {
            adyacente = adyacentes.recuperar(i);
            mapa.insertarArco(nuevaLocacion, adyacente, mapa.obtenerEtiqueta(locacion, adyacente));
        }

        mapa.eliminarVertice(locacion);
        log(String.format("Se modificó el nombre de la locación \"%s\" a \"%s\"", locacion, nuevaLocacion));

        return nuevaLocacion;
    }

    private void agregarCamino(String locacion) {
        titulo(String.format("Nuevo destino para %s", locacion));
        Lista<String> locaciones = mapa.listarVertices();
        String destino;

        for (int i = 1; i <= locaciones.longitud(); i++) {
            destino = locaciones.recuperar(i);

            if (!destino.equalsIgnoreCase(locacion)) {
                opcion(i, destino);
            }
        }

        opcion(0, "Cancelar");
        int opcion = leerOpcion(0, locaciones.longitud());

        if (opcion > 0) {
            double distancia = leerDistancia();
            destino = locaciones.recuperar(opcion);
            mapa.insertarArco(locacion, destino, distancia);

            log(String.format("Se insertó un camino de una distancia de %.0f kms desde \"%s\" hasta \"%s\"", distancia,
                    locacion, destino));
        }
    }

    private void borrarCamino(String locacion) {
        titulo(String.format("Destino a borrar desde %s", locacion));
        Lista<String> adyacentes = mapa.listarAdyacentes(locacion);

        for (int i = 1; i <= adyacentes.longitud(); i++) {
            opcion(i, adyacentes.recuperar(i));
        }

        opcion(0, "Cancelar");
        int opcion = leerOpcion(0, adyacentes.longitud());

        if (opcion > 0) {
            String destino = adyacentes.recuperar(opcion);
            mapa.eliminarArco(locacion, destino);

            log(String.format("Se eliminó el camino desde \"%s\" hasta \"%s\"", locacion, destino));
        }
    }

    private void modificarDistancia(String locacion) {
        titulo(String.format("Destino a modificar distancia desde %s", locacion));
        Lista<String> adyacentes = mapa.listarAdyacentes(locacion);

        for (int i = 1; i <= adyacentes.longitud(); i++) {
            opcion(i, adyacentes.recuperar(i));
        }

        opcion(0, "Cancelar");
        int opcion = leerOpcion(0, adyacentes.longitud());

        if (opcion > 0) {
            String destino = adyacentes.recuperar(opcion);
            double distancia = leerDistancia();
            mapa.eliminarArco(locacion, destino);
            mapa.insertarArco(locacion, destino, distancia);

            log(String.format("Se modificó la distancia del camino desde \"%s\" hasta \"%s\" a %.0f kms", locacion,
                    destino, distancia));
        }
    }

    /**
     * J. Consultas sobre locaciones:
     * Dado un nombre de locación, mostrar todas las locaciones a las que puede moverse un equipo después de ganar una
     * batalla en dicha locación.
     */
    public void mostrarLocacionesAdyacentes() {
        titulo("Mostrar locaciones adyacentes");

        if (!mapa.esVacio()) {
            String locacion = leerLocacion();
            Lista<String> adyacentes = mapa.listarAdyacentes(locacion);

            if (!adyacentes.esVacia()) {
                for (int i = 1; i <= adyacentes.longitud(); i++) {
                    System.out.println(i + ": " + adyacentes.recuperar(i));
                }

                log(String.format("Se consultaron las locaciones adyacentes a \"%s\"", locacion));
            } else {
                System.out.println(String.format("No existe la locación \"%s\"", locacion));
            }
        } else {
            System.out.println("No existen locaciones para consultar");
        }
    }

    /**
     * J. Consultas sobre locaciones:
     * Dados dos nombres de locaciones A y B:
     * i. Obtener el camino para ir desde A hasta B de menor distancia en km.
     */
    public void mostrarCaminoMasCortoKms() {
        titulo("Mostrar camino más corto (en kms)");

        if (!mapa.esVacio()) {
            String locacion1 = leerLocacion("Locación origen: ");
            String locacion2 = leerLocacion("Locación destino: ");
            Camino caminoMasCortoKms = mapa.caminoMasCortoKms(locacion1, locacion2);
            Lista<String> locaciones = caminoMasCortoKms.getElementos();
            double distancia = caminoMasCortoKms.getLongitud();

            System.out.println(String.format("El camino más corto entre \"%s\" y \"%s\" es de %.0f kms:",
                    locacion1, locacion2, distancia));

            if (!locaciones.esVacia()) {
                for (int i = 1; i <= locaciones.longitud(); i++) {
                    System.out.println(i + ": " + locaciones.recuperar(i));
                }

                log(String.format("Se consultó el camino más corto entre \"%s\" y \"%s\"", locacion1, locacion2));
            } else {
                System.out.println(String.format("Alguna de las locaciones no existe, \"%s\" y/o \"%s\"",
                        locacion1, locacion2));
            }
        } else {
            System.out.println("No existen locaciones para consultar");
        }
    }

    /**
     * J. Consultas sobre locaciones:
     * Dados dos nombres de locaciones A y B:
     * ii. Obtener el camino que llegue de A a B pasando por la mínima cantidad de locaciones.
     */
    public void mostrarCaminoMasDirecto() {
        titulo("Mostrar camino más directo entre locaciones");

        if (!mapa.esVacio()) {
            String locacion1 = leerLocacion("Locación origen: ");
            String locacion2 = leerLocacion("Locación destino: ");
            Lista<String> caminoMasDirecto = mapa.caminoMasCorto(locacion1, locacion2);

            if (!caminoMasDirecto.esVacia()) {
                for (int i = 1; i <= caminoMasDirecto.longitud(); i++) {
                    System.out.println(i + ": " + caminoMasDirecto.recuperar(i));
                }

                log(String.format("Se consultó el camino más directo entre \"%s\" y \"%s\"", locacion1, locacion2));
            } else {
                System.out.println(String.format("Alguna de las locaciones no existe, \"%s\" y/o \"%s\"",
                        locacion1, locacion2));
            }
        } else {
            System.out.println("No existen locaciones para consultar");
        }
    }

    /**
     * J. Consultas sobre locaciones:
     * Dados dos nombres de locaciones A y B:
     * iii. Obtener todos los caminos para llegar de A a B con menos de una cantidad X de km dada.
     */
    public void mostrarCaminosHastaDistancia() {
        titulo("Mostrar caminos con distancia máxima");

        if (!mapa.esVacio()) {
            String locacion1 = leerLocacion("Locación origen: ");
            String locacion2 = leerLocacion("Locación destino: ");
            double distanciaMaxima = leerDistancia("Distancia máxima: ");
            Lista<Lista<String>> caminos = mapa.caminosHastaLongitud(locacion1, locacion2, distanciaMaxima);

            if (!caminos.esVacia()) {
                Lista<String> camino;
                System.out.println("Se encontraron los siguientes caminos:");

                for (int i = 1; i <= caminos.longitud(); i++) {
                    camino = caminos.recuperar(i);
                    System.out.println(String.format("Camino %d: %s", i, camino));
                }

                log(String.format("Se consultaron los caminos más cortos entre \"%s\" y \"%s\" de hasta %.0f kms",
                        locacion1, locacion2, distanciaMaxima));
            } else {
                System.out.println(String.format("Alguna de las locaciones no existe, \"%s\" y/o \"%s\"",
                        locacion1, locacion2));
            }
        } else {
            System.out.println("No existen locaciones para consultar");
        }
    }

    /**
     * J. Consultas sobre locaciones:
     * Dados dos nombres de locaciones A y B:
     * iv. Obtener el camino para llegar de A a B que pase por menos locaciones y que no pase por una locación C dada.
     */
    public void mostrarCaminoSinLocacion() {
        titulo("Mostrar camino más directo sin pasar por una locación");

        if (!mapa.esVacio()) {
            String locacion1, locacion2, locacion3 = null;
            Lista<String> camino;

            do {
                if (locacion3 != null) {
                    System.out.println("La locación evitada no debe ser la de origen ni destino");
                }

                locacion1 = leerLocacion("Locación origen: ");
                locacion2 = leerLocacion("Locación destino: ");
                locacion3 = leerLocacion("Locación evitada: ");
            } while (locacion1.equals(locacion3) || locacion2.equals(locacion3));

            // Obtener camino
            camino = mapa.caminoMasCortoExcepto(locacion1, locacion2, locacion3);

            if (!camino.esVacia()) {
                for (int i = 1; i <= camino.longitud(); i++) {
                    System.out.println(i + ": " + camino.recuperar(i));
                }

                log(String.format("Se consultó el camino más directo entre \"%s\" y \"%s\" y sin pasar por \"%s\"",
                        locacion1, locacion2, locacion3));
            } else {
                System.out.println(String.format("Alguna de las locaciones no existe, \"%s\", \"%s\" y/o \"%s\"",
                        locacion1, locacion2, locacion3));
            }
        } else {
            System.out.println("No existen locaciones para consultar");
        }
    }

    /**
     * Devuelve una locación aleatoria del mapa.
     *
     * @return la locación obtenida
     */
    private String locacionAleatoria() {
        String locacion = null;

        if (!mapa.esVacio()) {
            Lista<String> locaciones = mapa.listarEnAnchura();
            int aleatorio = ThreadLocalRandom.current().nextInt(0, locaciones.longitud() - 1);
            locacion = locaciones.recuperar(aleatorio);
        }

        return locacion;
    }

    /**
     * Devuelve la representación del mapa en formato CSV (de 7 columnas).
     *
     * @return el mapa en CSV
     */
    public String exportarMapaCSV() {
        StringBuilder cadena = new StringBuilder();
        StringBuilder cadenaCaminos = new StringBuilder();
        String locacionOrigen = null;
        String locacionDestino = null;
        String caminoOpuesto = null;
        Lista<String> locaciones = mapa.listarEnAnchura();
        Lista<String> adyacentes;

        for (int i = 1; i <= locaciones.longitud(); i++) {
            locacionOrigen = locaciones.recuperar(i);
            cadena.append(String.format("L:%s;;;;;;\r\n", locacionOrigen));
            adyacentes = mapa.listarAdyacentes(locacionOrigen);

            for (int j = 1; j <= adyacentes.longitud(); j++) {
                locacionDestino = adyacentes.recuperar(j);
                caminoOpuesto = locacionDestino + ";" + locacionOrigen;

                // Verificar que no esté impreso el camino opuesto
                if (cadenaCaminos.indexOf(caminoOpuesto) == -1) {
                    cadenaCaminos.append("C:").append(locacionOrigen).append(";");
                    cadenaCaminos.append(locacionDestino).append(";");
                    cadenaCaminos.append(mapa.obtenerEtiqueta(locacionOrigen, locacionDestino)).append(";;;;\r\n");
                }
            }
        }

        return cadena.append(cadenaCaminos.toString()).toString();
    }

    private static String leerLocacion(String etiqueta) {
        return Funciones.leerFrase(etiqueta != null ? etiqueta : "Nombre de locación: ",
                "El nombre de locación ingresado no es válido."
                        + "\r\nDebe ser una frase alfanumérica de 2 a 50 caracteres.\r\nReintentar: ",
                2, 30);
    }

    private static String leerLocacion() {
        return leerLocacion(null);
    }

    private static double leerDistancia(String etiqueta) {
        return Funciones.leerEnteroPositivo(etiqueta != null ? etiqueta : "Distancia: ",
                "El distancia ingresada no es válida.\r\nDebe ser un entero positivo.\r\nReintentar: ");
    }

    private static double leerDistancia() {
        return leerDistancia(null);
    }

    /**
     * Menú de equipos.
     */
    public void menuEquipos() {
        int opcion = 0;

        do {
            titulo("Equipos");
            opcion(1, "Consultar equipo");
            opcion(2, "Crear equipo");
            opcion(3, "Iniciar batalla entre equipos");
            opcion(0, "Volver");

            opcion = leerOpcion(0, 3);

            switch (opcion) {
                case 1:
                    consultarEquipo();
                    break;
                case 2:
                    crearEquipo();
                    break;
                case 3:
                    iniciarBatallaEntreEquipos();
                    break;
            }

            if (opcion > 0) {
                Funciones.pausar();
            }
        } while (opcion > 0);
    }

    /**
     * E. Creación automática de un equipo.
     */
    public void crearEquipo() {
        titulo("Crear equipo");

        if (esperando.longitud() >= 3) {
            String nombre = leerNombreEquipo();
            Equipo equipo = new Equipo(nombre, locacionAleatoria());
            Lista<Jugador> jugadores = equipo.getJugadores();
            Categoria categoria = null;
            Jugador jugador;

            for (int i = 0; i < Equipo.CANTIDAD_JUGADORES; i++) {
                jugador = esperando.obtenerFrente();
                esperando.eliminarFrente();
                jugador.setEsperando(false);
                jugador.setEquipo(equipo);
                jugadores.insertar(jugador, jugadores.longitud() + 1);

                if (categoria == null || categoria.compareTo(jugador.getCategoria()) < 0) {
                    categoria = jugador.getCategoria();
                }
            }

            equipo.setCategoria(categoria);
            equipos.put(nombre.toLowerCase(), equipo);

            log(String.format("Se creó el equipo \"%s\" de categoría \"%s\"", nombre, equipo.getCategoria()));
        } else {
            System.out.println("No hay suficientes jugadores en espera para crear un equipo");
        }
    }

    private static String leerNombreEquipo() {
        return leerNombreEquipo(null);
    }

    private static String leerNombreEquipo(String etiqueta) {
        return Funciones.leerFrase(etiqueta != null ? etiqueta : "Nombre del equipo: ",
                "El nombre de equipo ingresado no es válido.\r\nReintentar: ", 1, 20);
    }

    /**
     * F. Dados dos equipos X e Y, mostrar el paso a paso de una batalla entre ambos equipos y cómo quedan los estados
     * de los jugadores participantes al finalizar la batalla.
     */
    public void iniciarBatallaEntreEquipos() {
        titulo("Batalla entre equipos");

        if (equipos.size() > 1) {
            String nombreEquipo1 = leerNombreEquipo("Equipo 1: ").toLowerCase();
            String nombreEquipo2 = leerNombreEquipo("Equipo 2: ").toLowerCase();

            if (equipos.containsKey(nombreEquipo1) && equipos.containsKey(nombreEquipo2)) {
                try {
                    Batalla batalla = new Batalla(this, equipos.get(nombreEquipo1), equipos.get(nombreEquipo2));
                    batalla.iniciar();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Al menus un equipo indicado no existe");
            }
        } else {
            System.out.println("Deben existir al menos 2 equipos para una batalla");
        }
    }

    /**
     * G. Consulta sobre equipos:
     * Dado un nombre de un equipo, mostrar nombre de sus jugadores y categoría.
     */
    public void consultarEquipo() {
        titulo("Consultar equipo");

        if (!equipos.isEmpty()) {
            String nombre = leerNombreEquipo().toLowerCase();

            if (equipos.containsKey(nombre)) {
                Equipo equipo = equipos.get(nombre);
                Lista<Jugador> jugadores = equipo.getJugadores();
                StringBuilder cadena = new StringBuilder();

                cadena.append("Nombre:    ").append(equipo.getNombre()).append("\r\n");
                cadena.append("Categoría: ").append(equipo.getCategoria()).append("\r\n");
                cadena.append("Locación:  ").append(equipo.getLocacion()).append("\r\n");
                cadena.append("Jugadores: ");

                for (int i = 1; i <= jugadores.longitud(); i++) {
                    cadena.append(jugadores.recuperar(i).getUsuario());

                    if (i < jugadores.longitud()) {
                        cadena.append(", ");
                    }
                }

                System.out.println(cadena.toString());
                log(String.format("Se consultó el equipo \"%s\"", nombre));
            } else {
                log(String.format("Se intentó consultar un equipo inexistente \"%s\"", nombre));
            }
        } else {
            System.out.println("No existen equipos para consultar");
        }
    }

    /**
     * Menú de sistema.
     */
    public void menuSistema() {
        int opcion = 0;

        do {
            titulo("Sistema");
            opcion(1, "Mostrar ranking de jugadores");
            opcion(2, "Mostrar ítems con última disponibilidad");
            opcion(3, "Mostrar todos los jugadores");
            opcion(4, "Mostrar todos los ítems");
            opcion(5, "Mostrar todas las locaciones");
            opcion(6, "Mostrar todos los equipos");
            opcion(7, "Mostrar todos los jugadores en espera");
            opcion(8, "Mostrar todo");
            opcion(0, "Volver");

            opcion = leerOpcion(0, 8);

            switch (opcion) {
                case 1:
                    mostrarRankingJugadores();
                    break;
                case 2:
                    mostrarItemsUltimaDisponibilidad();
                    break;
                case 3:
                    mostrarJugadores();
                    break;
                case 4:
                    mostrarItems();
                    break;
                case 5:
                    mostrarLocaciones();
                    break;
                case 6:
                    mostrarEquipos();
                    break;
                case 7:
                    mostrarEsperando();
                    break;
                case 8:
                    mostrarTodo();
                    break;
            }

            if (opcion > 0) {
                Funciones.pausar();
            }
        } while (opcion > 0);
    }

    /**
     * K. (*) Consultas generales (considerar utilizar estructuras adicionales):
     * (*) Mostrar un ranking de los jugadores con más batallas individuales ganadas.
     */
    public void mostrarRankingJugadores() {
        titulo("Ranking de jugadores");

        if (!jugadores.esVacio()) {
            Lista<Jugador> listaJugadores = jugadores.listarDatos();
            Diccionario<Integer, Jugador> ranking = new Diccionario<>();
            Jugador jugador, jugadorIgualPuesto;
            int puntaje;

            // Armar ranking por puntaje, tomando en cuenta las victorias y derrotas:
            // puntaje = victorias + (victorias - derrotas)
            for (int i = 1; i <= listaJugadores.longitud(); i++) {
                jugador = listaJugadores.recuperar(i);
                puntaje = jugador.getVictorias() + (jugador.getVictorias() - jugador.getDerrotas());
                jugador.setSiguienteIgualPuesto(null);
                jugadorIgualPuesto = ranking.obtenerInformacion(puntaje);

                if (jugadorIgualPuesto == null) {
                    ranking.insertar(puntaje, jugador);
                } else if (!jugador.equals(jugadorIgualPuesto)) {
                    jugador.setSiguienteIgualPuesto(jugadorIgualPuesto.getSiguienteIgualPuesto());
                    jugadorIgualPuesto.setSiguienteIgualPuesto(jugador);
                }
            }

            listaJugadores = ranking.listarDatos();
            int puesto = 1;

            // Mostrar ranking
            for (int i = listaJugadores.longitud(); i >= 1; i--) {
                jugador = listaJugadores.recuperar(i);
                while (jugador != null) { // Mostrar jugadores de igual puntaje
                    System.out.println(String.format("%d: %s - Victorias: %d - Derrotas: %d", puesto,
                            jugador.getUsuario(), jugador.getVictorias(), jugador.getDerrotas()));
                    jugador = jugador.getSiguienteIgualPuesto();
                    puesto++;
                }
            }
        } else {
            System.out.println("No existen jugadores para consultar ranking");
        }
    }

    /**
     * K. (*) Consultas generales (considerar utilizar estructuras adicionales):
     * (*) Mostrar un listado de todos los ítems de los que hay sólo uno disponible.
     */
    public void mostrarItemsUltimaDisponibilidad() {
        titulo("Ítems con última disponibilidad");

        if (!inventario.esVacio()) {
            Lista<Item> ultimosDisponibles = inventario.listarDatos();
            Item item;

            for (int i = 1; i <= ultimosDisponibles.longitud(); i++) {
                item = ultimosDisponibles.recuperar(i);
                if (item.getCantidadDisponible() == 1) {
                    System.out.println(String.format("%d: %s", i, formItem(item)));
                }
            }

            log("Se consultaron ítems con última disponibilidad");
        } else {
            System.out.println("No existen ítems para consultar");
        }
    }

    /**
     * Muestra estado de todos los jugadores.
     */
    public void mostrarJugadores() {
        titulo("Jugadores");
        System.out.println("Usuario;Tipo;Categoría;Dinero;Ítems");
        Lista<Jugador> listaJugadores = jugadores.listarDatos();

        for (int i = 1; i <= listaJugadores.longitud(); i++) {
            System.out.println(listaJugadores.recuperar(i));
        }
    }

    /**
     * Muestra estado de todos los ítems.
     */
    public void mostrarItems() {
        titulo("Ítems");
        System.out.println("Código;Nombre;Precio;Ataque;Defensa;Cantidad;CantidadDisponible");
        Lista<Item> items = this.items.listarDatos();

        for (int i = 1; i <= items.longitud(); i++) {
            System.out.println(items.recuperar(i));
        }
    }

    /**
     * Muestra estado de todos las locaciones.
     */
    public void mostrarLocaciones() {
        titulo("Locaciones");
        System.out.println(mapa);
    }

    /**
     * Muestra estado de todos los equipos.
     */
    public void mostrarEquipos() {
        titulo("Equipos");
        System.out.println("Nombre;Categoría;Jugadores");
        Iterator<Equipo> iterador = equipos.values().iterator();

        while (iterador.hasNext()) {
            System.out.println(String.valueOf(iterador.next()));
        }
    }

    /**
     * Muestra estado de todos los jugadores en espera.
     */
    public void mostrarEsperando() {
        titulo("Jugadores en espera");
        System.out.println(esperando);
    }

    /**
     * Muestra el estado completo del juego.
     */
    public void mostrarTodo() {
        mostrarJugadores();
        mostrarItems();
        mostrarLocaciones();
        mostrarEquipos();
        mostrarEsperando();
    }
}
