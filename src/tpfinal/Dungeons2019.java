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
    public static final int PONER_JUGADORES_EN_ESPERA = 17;

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
     * Equipos registrados (hashmap con clave tipo String y dato tipo Equipo).
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
     * Ítems disponibles en el juego.
     */
    private AVLItems items; //TODO: El AVL debe aceptar ítems con precios iguales

    /**
     * El mapa del juego (grafo etiquetado con elementos tipo String y etiquetas tipo Integer).
     */
    private Mapa mapa;

    /**
     * La cadena del menú.
     */
    private String menu;

    /**
     * Tabla hash para los códigos de los ítems.
     */
    private static final TablaHashAbierto<String> codigos = new TablaHashAbierto<>();
    private static char letra = 'A';
    private static int secuencia = 0;

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
        jugadores = new Diccionario<>();
        esperando = new ColaPrioridad<>();
        items = new AVLItems();
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
                case PONER_JUGADORES_EN_ESPERA:
                    ponerJugadoresEnEspera();
                    break;
                case AGREGAR_ITEM:
                    agregarItem();
                    break;
                case BORRAR_ITEM:
                    borrarItem();
                    break;
                case MODIFICAR_ITEM:
                    modificarItem();
                    break;
                case CONSULTAR_ITEM:
                    consultarItem();
                    break;
                case MOSTRAR_ITEMS_HASTA_PRECIO:
                    mostrarItemsHastaPrecio();
                    break;
                case MOSTRAR_ITEMS_DESDE_HASTA_PRECIO:
                    mostrarItemsDesdeHastaPrecio();
                    break;
                case AGREGAR_LOCACION:
                    agregarLocacion();
                    break;
                case BORRAR_LOCACION:
                    borrarLocacion();
                    break;
                case MODIFICAR_LOCACION:
                    //TODO: modificarLocacion()
                    break;
                case MOSTRAR_LOCACIONES_ADYACENTES:
                    mostrarLocacionesAdyacentes();
                    break;
                case MOSTRAR_CAMINO_MAS_CORTO:
                    //TODO: mostrarCaminoMasCorto()
                    break;
                case MOSTRAR_CAMINO_MAS_DIRECTO:
                    mostrarCaminoMasDirecto();
                    break;
                case MOSTRAR_CAMINO_HASTA_DISTANCIA:
                    //TODO: mostrarCaminoHastaDistancia()
                    break;
                case MOSTRAR_CAMINO_SIN_LOCACION:
                    //TODO: mostrarCaminoSinLocacion()
                    break;
                case CONSULTAR_EQUIPO:
                    consultarEquipo();
                    break;
                case CREAR_EQUIPO:
                    crearEquipo();
                    break;
                case INICIAR_BATALLA_ENTRE_EQUIPOS:
                    //TODO: iniciarBatallaEntreEquipos();
                case MOSTRAR_RANKING_JUGADORES:
                    //TODO: mostrarRankingJugadores();
                case MOSTRAR_ITEMS_ULTIMA_DISPONIBILIDAD:
                    //TODO: mostrarItemsUltimaDisponibilidad();
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
     * TODO: Separar menús según tipo de operación
     */
    public void menuJugadores() {
        System.out.println("~~~{ Jugadores }~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("   <1> Agregar jugador");
        System.out.println("   <2> Borrar jugador");
        System.out.println("   <3> Modificar jugador");
        System.out.println("   <4> Consultar jugador");
        System.out.println("   <5> Filtrar jugadores");
        System.out.println("   <6> Poner jugador en espera");
        System.out.println("   <7> Poner todos los jugadores en espera");
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
            case PONER_JUGADORES_EN_ESPERA:
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

    private static void titulo(String titulo) {
        System.out.println(
                String.format("~~~{ %s }~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", titulo)
                        .substring(0, 77));
    }

    /**
     * Método de utilidad para esperar hasta que el usuario quiera continuar presionando "Entrar".
     */
    private static void pausar() {
        System.out.println("Presionar [Entrar] para continuar...");
        TecladoIn.readLine();
    }

    /**
     * Agrega información al registro del juego.
     */
    private void log(String info) {
        System.out.println(info);
        //TODO: Registrar acciones en archivo LOG
    }

    private static int leerDistancia() {
        return Funciones.leerEnteroPositivo("Distancia: ",
                "El distancia ingresada no es válida.\r\nDebe ser un entero positivo.\r\nReintentar: ");
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

    private static String leerPrefijoUsuario() {
        return Funciones.leerPalabra("Prefijo: ",
                "El prefijo ingresado no es válido.\r\nReintentar: ", 1, 20);
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
     * B. ABM de ítems:
     * Agregar ítem.
     */
    public void agregarItem() {
        titulo("Agregar ítem");

        Item item = new Item();
        item.setCodigo(generarCodigoItem());
        item.setNombre(leerNombreItem());
        item.setPrecio(leerPrecio());
        item.setAtaque(leerAtaque());
        item.setDefensa(leerDefensa());
        item.setCantidad(leerDisponibilidad());
        items.insertar(item);

        log(String.format("Se agregó el ítem \"%s\" (%s)", item.getNombre(), item.getCodigo()));
    }

    public static String generarCodigoItem() {
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

    private static String leerCodigoItem() {
        return Funciones.leerPalabra("Código: ",
                "El código ingresado no es válido.\r\nDebe ser una letra seguido de 3 dígitos.\r\nReintentar: ", 4, 4);
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

    private static String formatearDinero(int dinero) {
        return Funciones.formatearDinero(dinero, '§');
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

    private static String leerLocacion() {
        return Funciones.leerFrase("Nombre de locación: ",
                "El nombre de locación ingresado no es válido."
                        + "\r\nDebe ser una frase alfanumérica de 2 a 50 caracteres.\r\nReintentar: ",
                2, 50);
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
     * Muestra el estado del juego.
     */
    public void mostrarSistema() {
        int opcion = 0;

        do {
            titulo("Mostrar sistema");
            opcion = Funciones.leerEntero(
                    "   <1> Jugadores\r\n"
                            + "   <2> Ítems\r\n"
                            + "   <3> Locaciones\r\n"
                            + "   <4> Equipos\r\n"
                            + "   <5> Jugadores esperando\r\n"
                            + "   <0> Cancelar\r\n"
                            + "Opción: ",
                    "La opción no es válida.\r\n Reintentar: ", 0, 5);

            switch (opcion) {
                case 1:
                    System.out.println("Jugadores:");
                    Lista<Jugador> listaJugadores = jugadores.listarDatos();

                    for (int i = 1; i <= listaJugadores.longitud(); i++) {
                        System.out.println(listaJugadores.recuperar(i));
                    }

                    pausar();
                    break;
                case 2: // Mostrar ítems
                    System.out.println("Ítems:");
                    System.out.println("Código; Nombre; Precio; Ataque; Defensa");
                    Lista<Item> items = this.items.listar();

                    for (int i = 1; i <= items.longitud(); i++) {
                        System.out.println(items.recuperar(i));
                    }

                    pausar();
                    break;
                case 3:
                    System.out.println("Mapa:");
                    System.out.println(mapa);
                    break;
                case 4:
                    System.out.println("Equipos:");
                    Iterator<Equipo> iterador = equipos.values().iterator();

                    while (iterador.hasNext()) {
                        System.out.println(iterador.next().getNombre());
                    }

                    pausar();
                    break;
                case 5:
                    System.out.println("Jugadores en espera:");
                    System.out.println(esperando);
                    pausar();
                    break;
            }
        } while(opcion > 0);
    }
}
