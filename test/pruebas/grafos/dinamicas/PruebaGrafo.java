package pruebas.grafos.dinamicas;

import grafos.dinamicas.Grafo;

/**
 * Prueba implementación de Grafo.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaGrafo {

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
        pruebaClonar();
    }

    private void pruebaInsertarVertice() {
        Grafo<String> grafo = new Grafo<>();
        assert grafo.insertarVertice("A") : "Debe insertar A";
        assert !grafo.insertarVertice("A") : "No debe insertar A (ya existe)";
        assert grafo.insertarVertice("B") : "Debe insertar B";
    }

    private void pruebaInsertarArco() {
        Grafo<String> grafo = new Grafo<>();
        grafo.insertarVertice("A");
        grafo.insertarVertice("B");
        assert grafo.insertarArco("A", "B") : "Debe insertar arco de A a B";
        assert !grafo.insertarArco("A", "C") : "No debe insertar arco de A a C (no existe C)";
    }

    private void pruebaEliminarVertice() {
        Grafo<String> grafo = crearGrafo();
        assert grafo.eliminarVertice("A") : "Debe eliminar A";
        assert !grafo.eliminarVertice("A") : "No debe eliminar A (no existe)";
    }

    private void pruebaEliminarArco() {
        Grafo<String> grafo = crearGrafo();
        assert grafo.eliminarArco("A", "B") : "Debe eliminar arco A-B";
        assert !grafo.eliminarArco("A", "B") : "No debe eliminar arco A-B (no existe)";
        assert !grafo.eliminarArco("B", "A") : "No debe eliminar arco B-A (no existe)";
    }

    private void pruebaExisteVertice() {
        Grafo<String> grafo = crearGrafo();
        assert grafo.existeVertice("A") : "Debe existir A";
        assert !grafo.existeVertice("K") : "No debe existir K";
    }

    private void pruebaExisteArco() {
        Grafo<String> grafo = crearGrafo();
        assert grafo.existeArco("A", "B") : "Debe existir arco A-B";
        assert grafo.existeArco("B", "A") : "Debe existir arco B-A";
        assert grafo.existeArco("B", "C") : "Debe existir arco B-C";
        assert grafo.existeArco("C", "B") : "Debe existir arco C-B";
        assert !grafo.existeArco("C", "A") : "No debe existir arco C-A";
    }

    private void pruebaExisteCamino() {
        Grafo<String> grafo = crearGrafo();
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

    private void pruebaCaminoMasCorto() {
        Grafo<String> grafo = crearGrafo();
        assert grafo.caminoMasCorto("A", "D").toString().equals("[A, B, C, D]")
                : "Camino más corto A..D debe ser [A, B, C, D]";
        assert grafo.caminoMasCorto("A", "J").toString().equals("[A, E, I, J]")
                : "Camino más corto A..J debe ser [A, E, I, J]";
        assert grafo.caminoMasCorto("F", "G").toString().equals("[F, B, C, G]")
                : "Camino más corto F..G debe ser [F, B, C, G]";
        assert grafo.caminoMasCorto("H", "E").toString().equals("[H, J, I, E]")
                : "Camino más corto H..E debe ser [H, J, I, E]";
    }

    private void pruebaCaminoMasLargo() {
        Grafo<String> grafo = crearGrafo();
        assert grafo.caminoMasLargo("A", "D").toString().equals("[A, B, F, E, I, J, H, C, G, D]")
                : "Camino más largo A..D debe ser [A, B, F, E, I, J, H, C, G, D]";
        assert grafo.caminoMasLargo("A", "J").toString().equals("[A, E, I, F, B, C, D, G, H, J]")
                : "Camino más largo A..J debe ser [A, E, I, F, B, C, D, G, H, J]";
        assert grafo.caminoMasLargo("F", "G").toString().equals("[F, B, A, E, I, J, H, C, D, G]")
                : "Camino más largo F..G debe ser [F, B, A, E, I, J, H, C, D, G]";
        assert grafo.caminoMasLargo("H", "E").toString().equals("[H, G, D, C, B, A, F, I, E]")
                : "Camino más largo H..E debe ser [H, G, D, C, B, A, F, I, E]";
    }

    private void pruebaListarEnProfundidad() {
        Grafo<String> grafo = crearGrafo();
        assert grafo.listarEnProfundidad().toString().equals("[A, B, F, E, I, J, H, C, D, G]")
                : "Debe listar: [A, B, F, E, I, J, H, C, D, G]";
    }

    private void pruebaListarEnAnchura() {
        Grafo<String> grafo = crearGrafo();
        assert grafo.listarEnAnchura().toString().equals("[B, E, F, A, C, I, D, G, H, J]")
                : "Debe listar: [B, E, F, A, C, I, D, G, H, J]";
    }

    private void pruebaEsVacio() {
        Grafo<String> grafo = new Grafo<>();
        assert grafo.esVacio() : "Debe ser vacío";
        grafo = crearGrafo();
        assert !grafo.esVacio() : "No debe ser vacío";
    }

    private void pruebaClonar() {
        //TODO: La estructura interna de arcos queda distinta en algunos casos, pero el grafo parece ser equivalente
        Grafo<String> grafo = crearGrafo();
        //grafo.insertarArco("A", "A");
        //grafo.insertarArco("H", "J");
        //grafo.insertarArco("F", "F");
        Grafo<String> clon = grafo.clonar();
        assert grafo.toString().equals(clon.toString()) : "El grafo debe ser igual a su clon";
    }

    /**
     * Crea un grafo de tipo "String" de prueba:
     *
     * (A)---(B)---(C)---(D)
     *  | \   |     | \ /
     *  |  \  |     |  X
     *  |   \ |     | / \
     * (E)---(F)   (G)---(H)
     *   \   /           /
     *    \ /           /
     *    (I)---------(J)
     *
     * @return
     */
    private Grafo<String> crearGrafo() {
        Grafo<String> grafo = new Grafo<>();
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
        grafo.insertarArco("I", "J");
        grafo.insertarArco("H", "J");
        grafo.insertarArco("G", "H");
        grafo.insertarArco("F", "I");
        grafo.insertarArco("E", "I");
        grafo.insertarArco("E", "F");
        grafo.insertarArco("D", "G");
        grafo.insertarArco("C", "H");
        grafo.insertarArco("C", "G");
        grafo.insertarArco("C", "D");
        grafo.insertarArco("B", "C");
        grafo.insertarArco("B", "F");
        grafo.insertarArco("A", "F");
        grafo.insertarArco("A", "E");
        grafo.insertarArco("A", "B");

        return grafo;
    }
}
