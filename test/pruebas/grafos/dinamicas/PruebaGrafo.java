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

    }

    private void pruebaEliminarArco() {

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
