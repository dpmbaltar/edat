package tpfinal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import conjuntistas.Diccionario;
import conjuntistas.TablaHashAbierto;
import lineales.dinamicas.ColaPrioridad;
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
     * Ítems disponibles en el juego (AVL modificado para almacenar ítems de igual precio, y ordenados por precio).
     */
    private AVLItems items;

    /**
     * El mapa del juego (grafo etiquetado extendido con elementos tipo String y etiquetas tipo Integer).
     */
    private Mapa mapa;

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
        items = new AVLItems();
        mapa = new Mapa();
    }

    /**
     * Inicia el juego.
     */
    public void iniciar() {
        System.out.println("************************** Calabozos & Estructuras **************************");
        cargar(ARCHIVO_ESTADO);
        menu();
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
                        items.insertar(crearItemDesdeCadena(linea.substring(2)));
                        break;
                    case 'J': // Cargar Jugador
                        Jugador jugador = Jugador.crearDesdeCadena(linea.substring(2));
                        if (jugador != null) {
                            jugadores.insertar(jugador.getUsuario().toLowerCase(), jugador);
                            //TODO: Agregar items
                        }
                        break;
                    case 'L': // Cargar Locación
                        mapa.insertarVertice(linea.substring(2).replace(';', ' ').trim());
                        break;
                    case 'C': // Cargar Camino
                        String[] partes = linea.substring(2).split(";");

                        if (partes.length >= 3) {
                            try {
                                int etiqueta = Integer.valueOf(partes[2].trim());
                                mapa.insertarArco(partes[0].trim(), partes[1].trim(), etiqueta);
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

    private Item crearItemDesdeCadena(String cadena) {
        Item nuevoItem = null;
        String[] partes = cadena.split(";");

        if (partes.length >= 7) {
            String codigo = partes[0].trim();
            String nombre = partes[1].trim();
            int precio = 0;
            int ataque = 0;
            int defensa = 0;
            int cantidad = 0;
            int cantidadDisponible = 0;

            try {
                 precio = Integer.valueOf(partes[2].trim());
                 ataque = Integer.valueOf(partes[3].trim());
                 defensa = Integer.valueOf(partes[4].trim());
                 cantidad = Integer.valueOf(partes[5].trim());
                 cantidadDisponible = Integer.valueOf(partes[6].trim());
            } catch (NumberFormatException e) {}

            nuevoItem = new Item(codigo, nombre, precio, ataque, defensa, cantidad, cantidadDisponible);
        }

        return nuevoItem;
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
        System.out.println(info);
        //TODO: Registrar acciones en archivo LOG
    }

    /**
     * Menú principal.
     */
    public void menu() {
        int opcion = 0;

        do {
            titulo("Menú");
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

        System.out.println("Fin");
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

        jugadores.insertar(nombre.toLowerCase(), new Jugador(nombre, tipo, categoria, dinero));
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

            if (jugadores.eliminar(usuario)) {
                log(String.format("Se borró el jugador \"%s\"", usuario));
            } else {
                log(String.format("Se intentó borrar un jugador inexistente \"%s\"", usuario));
            }
        } else {
            log("No existen jugadores para borrar");
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
            log("No existen jugadores para modificar");
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
                            formatearDinero(jugador.getDinero())));
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
                datos.append("Dinero:    ").append(formatearDinero(jugador.getDinero())).append("\r\n");
                datos.append("Salud:     ").append(jugador.getSalud()).append("\r\n");
                datos.append("Victorias: ").append(jugador.getVictorias()).append("\r\n");
                datos.append("Derrotas:  ").append(jugador.getDerrotas()).append("\r\n");
                datos.append("Equipo:    ");

                if (jugador.tieneEquipo()) {
                    datos.append(jugador.getEquipo().getNombre());
                } else if (jugador.esperaEquipo()) {
                    datos.append("(esperando equipo...)");
                } else {
                    datos.append("-");
                }

                datos.append("\r\n");
                datos.append("Items:     ");

                if (!itemsJugador.esVacia()) {
                    for (int i = 1; i <= itemsJugador.longitud(); i++) {
                        datos.append(itemsJugador.recuperar(i).getNombre()).append("\r\n");
                    }
                } else {
                    datos.append("-");
                }

                datos.append("\r\n");
                System.out.println(datos);

                log(String.format("Se consultó el jugador \"%s\"", jugador.getUsuario()));
            } else {
                log(String.format("Se intentó consultar un jugador inexistente \"%s\"", usuario));
            }
        } else {
            log("No existen jugadores para consultar");
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
                int i = 1;

                while (i <= datos.longitud()) {
                    usuario = datos.recuperar(i).getUsuario();

                    if (!usuario.regionMatches(true, 0, prefijo, 0, prefijo.length())) {
                        datos.eliminar(i);
                    } else {
                        System.out.println(i + ": " + usuario);
                        i++;
                    }
                }

                if (datos.esVacia()) {
                    System.out.println(String.format("No existen jugadores cuyo nombre comience con \"%s\"", prefijo));
                }
            }
        } else {
            log("No existen jugadores para filtrar");
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

                if (!jugador.esperaEquipo()) {
                    jugador.setEsperando(true);
                    esperando.insertar(jugador, jugador.getCategoria());

                    log(String.format("Se colocó el jugador \"%s\" en espera", jugador.getUsuario()));
                } else {
                    log(String.format("El jugador \"%s\" ya tiene un equipo (%s)", jugador.getUsuario(),
                            jugador.getEquipo().getNombre()));
                }
            } else {
                log(String.format("No existe el jugador \"%s\" para colocarlo en espera", usuario));
            }
        } else {
            log("No existen jugadores para poner en espera");
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

                if (!jugador.esperaEquipo()) {
                    jugador.setEsperando(true);
                    esperando.insertar(jugador, jugador.getCategoria());
                    agregados++;
                }
            }

            log(String.format("Se colocaron %d jugadores en espera", agregados));
        } else {
            log("No existen jugadores para ponerlos en espera");
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
            opcion(5, "Mostrar ítems para comprar (con precio max.)");
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
        items.insertar(new Item(codigo, nombre, precio, ataque, defensa, cantidad, cantidad));

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
            Item item = items.obtener(codigo);
            boolean borrado = items.eliminar(item);
            //TODO: Borrar ítem de los jugadores

            if (borrado) {
                log(String.format("Se borró el ítem \"%s\"", codigo));
            } else {
                log(String.format("Se intentó borrar un ítem inexistente \"%s\"", codigo));
            }
        } else {
            log("No existen ítems para borrar");
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
            Item item = items.obtener(codigo);

            if (item != null) {
                modificarItemsegunOpcion(item);
            } else {
                log(String.format("Se intentó modificar un ítem inexistente \"%s\"", codigo));
            }
        } else {
            log("No existen ítems para modificar");
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

                    log(String.format("Se modificó el precio del ítem \"%s\" de %s a %s", item.getNombre(),
                            formatearDinero(precioAnterior), formatearDinero(precioNuevo)));
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
            Item item = items.obtener(codigo);

            if (item != null) {
                StringBuilder datos = new StringBuilder();
                datos.append("Información del ítem:\r\n");
                datos.append("Código:         ").append(item.getCodigo()).append("\r\n");
                datos.append("Nombre:         ").append(item.getNombre()).append("\r\n");
                datos.append("Precio:         ").append(formatearDinero(item.getPrecio())).append("\r\n");
                datos.append("Ataque:         ").append(item.getAtaque()).append("\r\n");
                datos.append("Defensa:        ").append(item.getDefensa()).append("\r\n");
                datos.append("Disponibilidad: ").append(item.getCantidadDisponible()).append('/');
                datos.append(item.getCantidad()).append("\r\n");

                System.out.println(datos);

                log(String.format("Se consultó el ítem \"%s\"", codigo));
            } else {
                log(String.format("Se intentó consultar un ítem inexistente \"%s\"", codigo));
            }
        } else {
            log("No existen ítems para consultar");
        }
    }

    /**
     * Consultas sobre items:
     * Dado un monto de dinero mostrar todos los items que puede comprar el jugador.
     */
    public void mostrarItemsHastaPrecio() {
        titulo("Mostrar ítems para comprar (según cantidad de dinero)");

        if (!items.esVacio()) {
            int dinero = leerDinero();
            Lista<Item> itemsPosibles = items.listarRangoPorPrecio(0, dinero);
            Item item;

            for (int i = 1; i <= itemsPosibles.longitud(); i++) {
                item = itemsPosibles.recuperar(i);
                System.out.println(String.format("%d: (%s) %s - Ataque: %,d - Defensa: %,d - %s", i, item.getCodigo(),
                        item.getNombre(),
                        item.getAtaque(),
                        item.getDefensa(),
                        formatearDinero(item.getPrecio())));
            }
        } else {
            log("No existen ítems para consultar");
        }
    }

    /**
     * Consultas sobre items:
     * Dadas dos valores (min y max que pueden no existir en la estructura) devolver todos los ítems que se puedan
     * comprar con un monto de dinero entre min y max (incluyendo ambos límites) ordenado de menor a mayor.
     */
    public void mostrarItemsDesdeHastaPrecio() {
        titulo("Mostrar ítems para comprar (con precio min. y max.)");

        if (!items.esVacio()) {
            System.out.print("Mínimo ");
            int minimo = leerDinero();
            System.out.print("Máximo ");
            int maximo = leerDinero();
            Lista<Item> itemsPosibles = items.listarRangoPorPrecio(minimo, maximo);
            Item item;

            for (int i = 1; i <= itemsPosibles.longitud(); i++) {
                item = itemsPosibles.recuperar(i);
                System.out.println(String.format("%d: (%s) %s - Ataque: %,d - Defensa: %,d - %s", i, item.getCodigo(),
                        item.getNombre(),
                        item.getAtaque(),
                        item.getDefensa(),
                        formatearDinero(item.getPrecio())));
            }
        } else {
            log("No existen ítems para consultar");
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

    private static String formatearDinero(int dinero) {
        return Funciones.formatearDinero(dinero, '$');
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
                    //TODO: modificarLocacion()
                    break;
                case 4:
                    mostrarLocacionesAdyacentes();
                    break;
                case 5:
                    //TODO: mostrarCaminoMasCorto()
                    break;
                case 6:
                    mostrarCaminoMasDirecto();
                    break;
                case 7:
                    //TODO: mostrarCaminoHastaDistancia()
                    break;
                case 8:
                    //TODO: mostrarCaminoSinLocacion()
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
            log("No existen locaciones para borrar");
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
                log(String.format("Se consultaron las locaciones adyacentes de una locación inexistente \"%s\"",
                        locacion));
            }
        } else {
            log("No existen locaciones para consultar");
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
                log(String.format(
                        "Se consultó el camino más directo entre \"%s\" y \"%s\" siendo al menos una inexistente",
                        locacion1, locacion2));
            }
        } else {
            log("No existen locaciones para consultar");
        }
    }

    private static String leerLocacion() {
        return Funciones.leerFrase("Nombre de locación: ",
                "El nombre de locación ingresado no es válido."
                        + "\r\nDebe ser una frase alfanumérica de 2 a 50 caracteres.\r\nReintentar: ",
                2, 50);
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

            opcion = leerOpcion(0, 3);

            switch (opcion) {
                case 1:
                    consultarEquipo();
                    break;
                case 2:
                    crearEquipo();
                    break;
                case 3:
                    //TODO: iniciarBatallaEntreEquipos();
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
            log("No hay suficientes equipos en espera para crear un equipo");
        }
    }

    private static String leerNombreEquipo() {
        return Funciones.leerFrase("Nombre del equipo: ",
                "El nombre de equipo ingresado no es válido.\r\nReintentar: ", 1, 30);
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

                cadena.append("Nombre:         ").append(equipo.getNombre()).append("\r\n");
                cadena.append("Categoría:      ").append(equipo.getCategoria()).append("\r\n");
                cadena.append("Locación:       ").append(equipo.getLocacion()).append("\r\n");
                cadena.append("Jugadores:      ");

                for (int i = 1; i <= jugadores.longitud(); i++) {
                    cadena.append(jugadores.recuperar(i).getUsuario());

                    if (i < jugadores.longitud()) {
                        cadena.append(", ");
                    }
                }

                cadena.append("\r\n");
                System.out.println(cadena.toString());
                log(String.format("Se consultó el equipo \"%s\"", nombre));
            } else {
                log(String.format("No existe un equipo con nombre \"%s\"", nombre));
            }
        } else {
            log("No existen equipos para consultar");
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
                    //TODO: mostrarRankingJugadores();
                case 2:
                    //TODO: mostrarItemsUltimaDisponibilidad();
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
     * Muestra estado de todos los jugadores.
     */
    public void mostrarJugadores() {
        titulo("Jugadores");
        System.out.println("Usuario; Tipo, Categoría, Dinero, Ítems");
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
        System.out.println("Código; Nombre; Precio; Ataque; Defensa");
        Lista<Item> items = this.items.listar();

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
