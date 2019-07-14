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
        pruebaEsVacio();
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
        assert !grafo.existeCamino("A", "D") : "No debe existir camino A..C";
        assert !grafo.existeCamino("G", "F") : "No debe existir camino G..F";
        assert !grafo.existeCamino("I", "J") : "No debe existir camino I..J";
        assert !grafo.existeCamino("H", "E") : "No debe existir camino H..E";
    }

    private void pruebaEsVacio() {
        Grafo<String> grafo = new Grafo<>();
        assert grafo.esVacio() : "Debe ser vacío";
        grafo = crearGrafo();
        assert !grafo.esVacio() : "No debe ser vacío";
    }

    /**
     * Crea un grafo de tipo "String" de prueba:
     *
     * (A)---(B)---(C)---(D)
     *  | \   |     | \ /
     *  |  \  |     |  X
     *  |   \ |     | / \
     * (E)---(F)   (G)---(H)
     *   \   /            |
     *    \ /             |
     *    (I)            (J)
     *
     * @return
     */
    private Grafo<String> crearGrafo() {
        Grafo<String> grafo = new Grafo<>();
        grafo.insertarVertice("A");
        grafo.insertarVertice("B");
        grafo.insertarVertice("C");
        grafo.insertarVertice("D");
        grafo.insertarVertice("E");
        grafo.insertarVertice("F");
        grafo.insertarVertice("G");
        grafo.insertarVertice("H");
        grafo.insertarVertice("I");
        grafo.insertarVertice("J");
        grafo.insertarArco("A", "B");
        grafo.insertarArco("A", "E");
        grafo.insertarArco("A", "F");
        grafo.insertarArco("B", "F");
        grafo.insertarArco("B", "C");
        grafo.insertarArco("C", "D");
        grafo.insertarArco("C", "G");
        grafo.insertarArco("C", "H");
        grafo.insertarArco("D", "G");
        grafo.insertarArco("E", "F");
        grafo.insertarArco("E", "I");
        grafo.insertarArco("F", "I");
        grafo.insertarArco("G", "H");
        grafo.insertarArco("H", "J");

        return grafo;
    }
}
