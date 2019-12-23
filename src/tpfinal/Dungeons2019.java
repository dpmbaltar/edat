package tpfinal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import conjuntistas.Diccionario;
import conjuntistas.TablaHashAbierto;
import lineales.dinamicas.ColaPrioridad;
import lineales.dinamicas.Lista;
import utiles.Funciones;

/**
 * Juego.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Dungeons2019 {

    /**
     * El archivo donde se carga y/o guarda el estado del juego.
     */
    private static final String ARCHIVO_ESTADO = "estado.csv";

    /**
     * El archivo de registro de acciones del juego (se reinicia por cada ejecución).
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
    private Inventario inventario;

    /**
     * El mapa del juego (grafo etiquetado extendido con elementos tipo String y etiquetas tipo Integer).
     */
    private Mapa mapa;

    /**
     * El ranking de jugadores (ordenados por cantidad de victorias de mayor a menor).
     */
    private Ranking ranking;

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
     * Programa principal.
     *
     * @param args argumentos
     */
    public static void main(String[] args) {
        Dungeons2019 juego = new Dungeons2019();
        juego.iniciar();
    }

    /**
     * Constructor.
     */
    public Dungeons2019() {
        equipos = new HashMap<>();
        jugadores = new Diccionario<>();
        esperando = new ColaPrioridad<>();
        items = new Diccionario<>();
        inventario = new Inventario();
        mapa = new Mapa();
        ranking = new Ranking();
        /*mapa.insertarVertice("A");
        mapa.insertarVertice("B");
        mapa.insertarVertice("C");
        mapa.insertarVertice("D");
        mapa.insertarArco("A", "B", 5);
        mapa.insertarArco("A", "C", 2);
        mapa.insertarArco("B", "D", 4);
        mapa.insertarArco("C", "D", 1);
        mapa.insertarArco("B", "C", 1);
        System.out.println(mapa.caminoMasCortoKms("A", "D"));
        System.exit(0);*/
    }

    /**
     * Inicia el juego.
     */
    public void iniciar() {
        System.out.println("************************** Calabozos & Estructuras **************************");
        cargar(ARCHIVO_ESTADO);
        System.out.println(mapa.caminoMasCortoKms("Roca Cuervo", "La Llanura de Piedras"));
        menuPrincipal();
        guardar(ARCHIVO_ESTADO);
    }

    /**
     * Carga el estado del juego desde un archivo CSV (se asume formato válido generado por el juego).
     */
    public void cargar(String nombreArchivo) {
        try {
            String url = Dungeons2019.class.getResource(nombreArchivo).getPath();
            BufferedReader archivoEstado = new BufferedReader(new FileReader(url));
            String linea = archivoEstado.readLine();

            while (linea != null) {
                switch (linea.charAt(0)) {
                    case 'I': // Cargar Ítem
                        Item item = crearItemDesdeCadena(linea.substring(2));
                        items.insertar(item.getCodigo(), item);

                        if (item.getCantidadDisponible() > 0) {
                            inventario.insertar(item);
                        }
                        break;
                    case 'J': // Cargar Jugador
                        Jugador jugador = crearJugadorDesdeCadena(linea.substring(2));

                        if (jugador != null) {
                            jugadores.insertar(jugador.getUsuario().toLowerCase(), jugador);
                            ranking.insertar(jugador);
                        }
                        break;
                    case 'L': // Cargar Locación
                        mapa.insertarVertice(linea.substring(2).replace(";", ""));
                        break;
                    case 'C': // Cargar Camino
                        String[] partes = linea.substring(2).split(";");

                        if (partes.length >= 3) {
                            int etiqueta = Integer.valueOf(partes[2]);
                            mapa.insertarArco(partes[0], partes[1], etiqueta);
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

        if (partes.length >= 5) {
            String usuario = partes[0];
            TipoJugador tipo = TipoJugador.valueOf(partes[1].toUpperCase());
            Categoria categoria = Categoria.valueOf(partes[2].toUpperCase());
            int dinero = Integer.valueOf(partes[3]);
            nuevoJugador = new Jugador(usuario, tipo, categoria, dinero);

            // Leer y agregar ítems del jugador
            String cadenaItems = partes[4].substring(1, partes[4].length() - 1);

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

    /**
     * Guarda el estado del juego a un archivo CSV.
     */
    public void guardar(String nombreArchivo) {
        //TODO: Guardar nuevo estado del juego
    }

    /**
     * Agrega información al registro del juego.
     */
    private void log(String info) {
        //TODO: Guardar info. en registro LOG
        try {
            String url = Dungeons2019.class.getResource(ARCHIVO_REGISTRO).getPath();
            //System.out.println(url);
            PrintWriter salida = new PrintWriter(new FileOutputStream(url));
            salida.println(info);
            System.out.println(info);
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

    private static void titulo(String titulo) {
        System.out.println(
                String.format("~~~{ %s }~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", titulo)
                        .substring(0, 77));
    }

    private static void opcion(int numero, String nombre) {
        System.out.println(String.format("   <%d> %s", numero, nombre));
    }

    private static int leerOpcion(int desde, int hasta) {
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

        String nombre = leerNombreUsuario();
        TipoJugador tipo = leerTipo();
        Categoria categoria = leerCategoria();
        int dinero = leerDinero();
        Jugador jugador = new Jugador(nombre, tipo, categoria, dinero);
        jugadores.insertar(nombre.toLowerCase(), jugador);
        ranking.insertar(jugador);

        log(String.format("Se agregó el jugador \"%s\"", nombre));
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
                ranking.eliminar(jugador);
                log(String.format("Se borró el jugador \"%s\"", usuario));
            } else {
                System.out.println(String.format("Se intentó borrar un jugador inexistente \"%s\"", usuario));
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
                System.out.println(String.format("Se intentó modificar un jugador inexistente \"%s\"", usuario));
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
            opcion = leerOpcionModificarJugador();

            switch (opcion) {
                case 1:
                    jugadores.eliminar(claveUsuario);
                    String usuarioAnterior = usuario;
                    usuario = leerNombreUsuario();
                    claveUsuario = usuario.toLowerCase();
                    jugador.setUsuario(usuario);
                    jugadores.insertar(claveUsuario, jugador);

                    log(String.format("Se modificó el usuario del jugador \"%s\" a \"%s\"", usuarioAnterior, usuario));
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
                            formDinero(jugador.getDinero())));
                    break;
            }
        } while (opcion != 0);
    }

    private static int leerOpcionModificarJugador() {
        return Funciones.leerEntero("Modificar <1> Usuario - <2> Tipo - <3> Categoría - <4> Dinero - <0> Cancelar: ",
                "La opción ingresada no es válida.\r\nReintentar: ", 0, 4);
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
                datos.append("Tipo:      ").append(jugador.getTipo()).append("\r\n");
                datos.append("Categoría: ").append(jugador.getCategoria()).append("\r\n");
                datos.append("Dinero:    ").append(formDinero(jugador.getDinero())).append("\r\n");
                datos.append("Salud:     ").append(formSalud(jugador.getSalud())).append("\r\n");
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
                System.out.println(String.format("Se intentó consultar un jugador inexistente \"%s\"", usuario));
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

                if (!jugador.getEsperando()) {
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

                if (!jugador.getEsperando()) {
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
        return TipoJugador.desdeEntero(Funciones.leerEntero("Tipo de jugador <0> Guerrero - <1> Defensor: ",
                "El tipo de jugador ingresado no es válido.\r\nReintentar: ", 0, 1));
    }

    /**
     * Solicita una categoría al usuario.
     *
     * @return la categoría leída
     */
    private static Categoria leerCategoria() {
        return Categoria.desdeEntero(Funciones.leerEntero("Categoría <0> Profesional - <1> Aficionado - <2> Novato: ",
                "La categoría ingresada no es válida.\r\nReintentar: ", 0, 2));
    }

    private static int leerDinero() {
        return Funciones.leerEnteroPositivo("Dinero: ",
                "El dinero ingresado no es válido.\r\nDebe ser un entero positivo o cero.\r\nReintentar: ");
    }

    private static String leerPrefijoUsuario() {
        return Funciones.leerPalabra("Prefijo: ",
                "El prefijo ingresado no es válido.\r\nReintentar: ", 1, 20);
    }

    private static String formSalud(double salud) {
        return Funciones.porcentaje(salud);
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
        items.insertar(codigo, item);
        inventario.insertar(item);

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
            boolean borrado = items.eliminar(codigo) && inventario.eliminar(item);
            //TODO: Borrar ítem de los jugadores

            if (borrado) {
                log(String.format("Se borró el ítem \"%s\"", codigo));
            } else {
                System.out.println(String.format("Se intentó borrar un ítem inexistente \"%s\"", codigo));
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
                System.out.println(String.format("Se intentó modificar un ítem inexistente \"%s\"", codigo));
            }
        } else {
            System.out.println("No existen ítems para modificar");
        }
    }

    private void modificarItemsegunOpcion(Item item) {
        int opcion = 0;

        do {
            opcion = leerOpcionModificarItem();

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
                    inventario.eliminar(item);
                    inventario.insertar(item);

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

    private static int leerOpcionModificarItem() {
        return Funciones.leerEntero("Modificar <1> Nombre - <2> Precio - <3> Ataque - <4> Defensa - <0> Cancelar: ",
                "La opción ingresada no es válida.\r\nReintentar: ", 0, 4);
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
                System.out.println(String.format("Se intentó consultar un ítem inexistente \"%s\"", codigo));
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
                mostrarItemsParaComprar(jugador, inventario.listarRangoPorPrecio(0, dinero));
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
            Lista<Item> itemsPosibles = inventario.listarRangoPorPrecio(minimo, maximo);
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
            if (item.getCantidadDisponible() == 0) {
                inventario.eliminar(item);
            }

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
            opcion(7, "Mostrar camino con distancia máxima");
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
                    //TODO: mostrarCaminoMasCorto();
                    break;
                case 6:
                    mostrarCaminoMasDirecto();
                    break;
                case 7:
                    //TODO: mostrarCaminoHastaDistancia();
                    break;
                case 8:
                    //TODO: mostrarCaminoSinLocacion();
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
                System.out.println(String.format("Se intentó borrar la locación inexistente \"%s\"", locacion));
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
                System.out.println(String.format("No existe la locación \"%s\"", locacion));
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
        mapa.modificarVertice(locacion, nuevaLocacion);

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
            int distancia = leerDistancia();
            destino = locaciones.recuperar(opcion);
            mapa.insertarArco(locacion, destino, distancia);

            log(String.format("Se insertó un camino de una distancia de %d kms desde \"%s\" hasta \"%s\"", distancia,
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
            int distancia = leerDistancia();
            mapa.modificarEtiqueta(locacion, destino, distancia);

            log(String.format("Se modificó la distancia del camino desde \"%s\" hasta \"%s\" a %d kms", locacion,
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
     * ii. Obtener el camino que llegue de A a B pasando por la mínima cantidad de locaciones.
     */
    public void mostrarCaminoMasDirecto() {
        titulo("Mostrar camino más directo entre locaciones");

        if (!mapa.esVacio()) {
            System.out.print("Origen - ");
            String locacion1 = leerLocacion();
            System.out.print("Destino - ");
            String locacion2 = leerLocacion();
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

    private static String leerLocacion() {
        return Funciones.leerFrase("Nombre de locación: ",
                "El nombre de locación ingresado no es válido."
                        + "\r\nDebe ser una frase alfanumérica de 2 a 50 caracteres.\r\nReintentar: ",
                2, 30);
    }

    private static int leerDistancia() {
        return Funciones.leerEnteroPositivo("Distancia: ",
                "El distancia ingresada no es válida.\r\nDebe ser un entero positivo.\r\nReintentar: ");
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
            Equipo equipo = new Equipo(nombre, mapa.locacionAleatoria());
            Jugador jugador;

            for (int i = 0; i < 3; i++) {
                jugador = esperando.obtenerFrente();
                esperando.eliminarFrente();
                jugador.setEsperando(false);
                jugador.setEquipo(equipo);
                equipo.agregarJugador(jugador);
            }

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
                Equipo equipo1 = equipos.get(nombreEquipo1);
                Equipo equipo2 = equipos.get(nombreEquipo2);

                Batalla batalla = new Batalla(equipo1, equipo2);
                batalla.iniciar();
            } else {
                System.out.println("Uno o ambos equipos indicados no existe");
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
                System.out.println(String.format("No existe un equipo con nombre \"%s\"", nombre));
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

    public void mostrarRankingJugadores() {
        titulo("Ranking de jugadores");

        if (!ranking.esVacio()) {
            Lista<Jugador> rankingJugadores = ranking.listarJugadores();
            Jugador jugador;

            for (int i = 1; i <= rankingJugadores.longitud(); i++) {
                jugador = rankingJugadores.recuperar(i);
                System.out.println(String.format("%d: %s - Victorias: %d - Derrotas: %d", i, jugador.getUsuario(),
                        jugador.getVictorias(), jugador.getDerrotas()));
            }
        } else {
            System.out.println("No existen jugadores para consultar ranking");
        }
    }

    /**
     * K. (*) Consultas generales (considerar utilizar estructuras adicionales):
     * (*) Mostrar un ranking de los jugadores con más batallas individuales ganadas.
     */
    public void mostrarItemsUltimaDisponibilidad() {
        titulo("Ítems con última disponibilidad");

        if (!inventario.esVacio()) {
            Lista<Item> ultimosDisponibles = inventario.listarUltimosDisponibles();
            Item item;

            for (int i = 1; i <= ultimosDisponibles.longitud(); i++) {
                item = ultimosDisponibles.recuperar(i);
                System.out.println(String.format("%d: %s", i, formItem(item)));
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
        Iterator<Equipo> iterador = equipos.values().iterator();

        while (iterador.hasNext()) {
            System.out.println(iterador.next().getNombre());
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
