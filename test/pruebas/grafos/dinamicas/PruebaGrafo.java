package pruebas.grafos.dinamicas;

import grafos.dinamicas.Grafo;

/**
 * Prueba implementación de Grafo.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaGrafo {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaGrafo() {
        pruebaInsertarVertice();
        pruebaInsertarArco();
        pruebaEliminarVertice();
        pruebaEliminarArco();
        pruebaExisteVertice();
        pruebaExisteArco();
        pruebaExisteCamino();
        pruebaCaminoMasCorto();
        pruebaCaminoMasLargo();
        pruebaListarEnProfundidad();
        pruebaListarEnAnchura();
        pruebaEsVacio();
        pruebaClone();
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#insertarVertice(Object)}.
     */
    public void pruebaInsertarVertice() {
        Grafo<String, Integer> grafo = new Grafo<>();
        assert grafo.insertarVertice("A") : "Debe insertar A";
        assert !grafo.insertarVertice("A") : "No debe insertar A (ya existe)";
        assert grafo.insertarVertice("B") : "Debe insertar B";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#insertarArco(Object, Object)}.
     */
    public void pruebaInsertarArco() {
        Grafo<String, Integer> grafo = new Grafo<>();
        grafo.insertarVertice("A");
        grafo.insertarVertice("B");
        assert grafo.insertarArco("A", "B", 1) : "Debe insertar arco de A a B";
        assert !grafo.insertarArco("A", "C", 1) : "No debe insertar arco de A a C (no existe C)";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#eliminarVertice(Object)}.
     */
    public void pruebaEliminarVertice() {
        Grafo<String, Integer> grafo = crearGrafo();
        assert grafo.eliminarVertice("A") : "Debe eliminar A";
        assert !grafo.eliminarVertice("A") : "No debe eliminar A (no existe)";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#eliminarArco(Object, Object)}.
     */
    public void pruebaEliminarArco() {
        Grafo<String, Integer> grafo = crearGrafo();
        assert grafo.eliminarArco("A", "B") : "Debe eliminar arco A-B";
        assert !grafo.eliminarArco("A", "B") : "No debe eliminar arco A-B (no existe)";
        assert !grafo.eliminarArco("B", "A") : "No debe eliminar arco B-A (no existe)";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#existeVertice(Object)}.
     */
    public void pruebaExisteVertice() {
        Grafo<String, Integer> grafo = crearGrafo();
        assert grafo.existeVertice("A") : "Debe existir A";
        assert !grafo.existeVertice("K") : "No debe existir K";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#existeArco(Object, Object)}.
     */
    public void pruebaExisteArco() {
        Grafo<String, Integer> grafo = crearGrafo();
        assert grafo.existeArco("A", "B") : "Debe existir arco A-B";
        assert grafo.existeArco("B", "A") : "Debe existir arco B-A";
        assert grafo.existeArco("B", "C") : "Debe existir arco B-C";
        assert grafo.existeArco("C", "B") : "Debe existir arco C-B";
        assert !grafo.existeArco("C", "A") : "No debe existir arco C-A";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#existeCamino(Object, Object)}.
     */
    public void pruebaExisteCamino() {
        Grafo<String, Integer> grafo = crearGrafo();
        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

        // Deben existir caminos entre todos los vértices (grafo conectado)
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices.length; j++) {
                assert grafo.existeCamino(vertices[i], vertices[j])
                        : String.format("Debe existir camino %s..%s", vertices[i], vertices[j]);
            }
        }

        // Ahora no deben existir algunos caminos (grafo no conectado)
        grafo.eliminarVertice("B");
        grafo.eliminarArco("I", "J");
        assert !grafo.existeCamino("A", "D") : "No debe existir camino A..C";
        assert !grafo.existeCamino("G", "F") : "No debe existir camino G..F";
        assert !grafo.existeCamino("I", "J") : "No debe existir camino I..J";
        assert !grafo.existeCamino("H", "E") : "No debe existir camino H..E";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#caminoMasCorto(Object, Object)}.
     */
    public void pruebaCaminoMasCorto() {
        Grafo<String, Integer> grafo = crearGrafo();
        assert grafo.caminoMasCorto("A", "D").toString().equals("[A, B, C, D]")
                : "Camino más corto A..D debe ser [A, B, C, D]";
        assert grafo.caminoMasCorto("A", "J").toString().equals("[A, E, I, J]")
                : "Camino más corto A..J debe ser [A, E, I, J]";
        assert grafo.caminoMasCorto("F", "G").toString().equals("[F, B, C, G]")
                : "Camino más corto F..G debe ser [F, B, C, G]";
        assert grafo.caminoMasCorto("H", "E").toString().equals("[H, J, I, E]")
                : "Camino más corto H..E debe ser [H, J, I, E]";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#caminoMasLargo(Object, Object)}.
     */
    public void pruebaCaminoMasLargo() {
        Grafo<String, Integer> grafo = crearGrafo();
        assert grafo.caminoMasLargo("A", "D").toString().equals("[A, B, F, E, I, J, H, C, G, D]")
                : "Camino más largo A..D debe ser [A, B, F, E, I, J, H, C, G, D]";
        assert grafo.caminoMasLargo("A", "J").toString().equals("[A, E, I, F, B, C, D, G, H, J]")
                : "Camino más largo A..J debe ser [A, E, I, F, B, C, D, G, H, J]";
        assert grafo.caminoMasLargo("F", "G").toString().equals("[F, B, A, E, I, J, H, C, D, G]")
                : "Camino más largo F..G debe ser [F, B, A, E, I, J, H, C, D, G]";
        assert grafo.caminoMasLargo("H", "E").toString().equals("[H, G, D, C, B, A, F, I, E]")
                : "Camino más largo H..E debe ser [H, G, D, C, B, A, F, I, E]";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#listarEnProfundidad()}.
     */
    public void pruebaListarEnProfundidad() {
        Grafo<String, Integer> grafo = crearGrafo();
        assert grafo.listarEnProfundidad().toString().equals("[A, B, F, E, I, J, H, C, D, G]")
                : "Debe listar: [A, B, F, E, I, J, H, C, D, G]";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#listarEnAnchura()}.
     */
    public void pruebaListarEnAnchura() {
        Grafo<String, Integer> grafo = crearGrafo();
        assert grafo.listarEnAnchura().toString().equals("[B, E, F, A, C, I, D, G, H, J]")
                : "Debe listar: [B, E, F, A, C, I, D, G, H, J]";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#esVacio()}.
     */
    public void pruebaEsVacio() {
        Grafo<String, Integer> grafo = new Grafo<>();
        assert grafo.esVacio() : "Debe ser vacío";
        grafo = crearGrafo();
        assert !grafo.esVacio() : "No debe ser vacío";
    }

    /**
     * Prueba {@link grafos.dinamicas.Grafo#clone()}.
     */
    public void pruebaClone() {
        Grafo<String, Integer> grafo = crearGrafo();
        grafo.insertarArco("A", "A", 1);
        grafo.insertarArco("H", "J", 1);
        grafo.insertarArco("F", "F", 1);
        Grafo<String, Integer> clon = grafo.clone();
        assert grafo.toString().equals(clon.toString()) : "El grafo debe ser igual a su clon";
    }

    /**
     * Crea un grafo de tipo "String" de prueba:
     * <pre>
     * (A)---(B)---(C)---(D)
     *  | \   |     | \ /
     *  |  \  |     |  X
     *  |   \ |     | / \
     * (E)---(F)   (G)---(H)
     *   \   /           /
     *    \ /           /
     *    (I)---------(J)
     * </pre>
     *
     * @return el grafo de prueba
     */
    public static Grafo<String, Integer> crearGrafo() {
        Grafo<String, Integer> grafo = new Grafo<>();
        grafo.insertarVertice("J");
        grafo.insertarVertice("I");
        grafo.insertarVertice("H");
        grafo.insertarVertice("G");
        grafo.insertarVertice("F");
        grafo.insertarVertice("E");
        grafo.insertarVertice("D");
        grafo.insertarVertice("C");
        grafo.insertarVertice("B");
        grafo.insertarVertice("A");
        grafo.insertarArco("I", "J", 1);
        grafo.insertarArco("H", "J", 1);
        grafo.insertarArco("G", "H", 1);
        grafo.insertarArco("F", "I", 1);
        grafo.insertarArco("E", "I", 1);
        grafo.insertarArco("E", "F", 1);
        grafo.insertarArco("D", "G", 1);
        grafo.insertarArco("C", "H", 1);
        grafo.insertarArco("C", "G", 1);
        grafo.insertarArco("C", "D", 1);
        grafo.insertarArco("B", "C", 1);
        grafo.insertarArco("B", "F", 1);
        grafo.insertarArco("A", "F", 1);
        grafo.insertarArco("A", "E", 1);
        grafo.insertarArco("A", "B", 1);

        return grafo;
    }
}
