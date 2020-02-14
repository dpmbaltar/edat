package pruebas.grafos.dinamicas;

import grafos.dinamicas.GrafoPonderado;

/**
 * Prueba implementación de Grafo ponderado.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaGrafoPonderado {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaGrafoPonderado() {
        pruebaCaminoMasCortoEtiqueta();
    }

    /**
     * Prueba {@link grafos.dinamicas.GrafoPonderado#caminoMasCortoEtiqueta(Object, Object)}.
     */
    public void pruebaCaminoMasCortoEtiqueta() {
        GrafoPonderado<Character> grafo = crearGrafoPonderado();
        assert grafo.caminoMasCortoEtiqueta('A', 'I').toString().equals("[A, F, I]")
                : "Camino más corto de A a I debe ser A,F,I";
        assert grafo.caminoMasCortoEtiqueta('I', 'A').toString().equals("[I, F, A]")
                : "Camino más corto de I a A debe ser I,F,A";
        assert grafo.caminoMasCortoEtiqueta('A', 'H').toString().equals("[A, B, C, H]")
                : "Camino más corto de A a H debe ser A,B,C,H";
        assert grafo.caminoMasCortoEtiqueta('H', 'A').toString().equals("[H, C, B, A]")
                : "Camino más corto de H a A debe ser H,C,B,A";
        assert grafo.caminoMasCortoEtiqueta('I', 'D').toString().equals("[I, F, B, C, G, D]")
                : "Camino más corto de I a D debe ser I,F,B,C,G,D";
    }

    /**
     * Crea un grafo ponderado de tipo "String" de prueba:
     * <pre>
     *     3           7
     * (A)---(B)---(C)---(D)
     *  | \   |  2  | \ /4
     * 1| 2\ 3|    2|  X
     *  |   \ |     | / \3
     * (E)---(F)   (G)---(H)
     *   \ 9 /         8 /
     *  4 \ / 1         / 4
     *    (I)---------(J)
     *           6
     * </pre>
     *
     * @return el grafo de prueba
     */
    public static GrafoPonderado<Character> crearGrafoPonderado() {
        GrafoPonderado<Character> grafo = new GrafoPonderado<>();
        grafo.insertarVertice('J');
        grafo.insertarVertice('I');
        grafo.insertarVertice('H');
        grafo.insertarVertice('G');
        grafo.insertarVertice('F');
        grafo.insertarVertice('E');
        grafo.insertarVertice('D');
        grafo.insertarVertice('C');
        grafo.insertarVertice('B');
        grafo.insertarVertice('A');
        grafo.insertarArco('I', 'J', 6.0);
        grafo.insertarArco('H', 'J', 4.0);
        grafo.insertarArco('G', 'H', 8.0);
        grafo.insertarArco('F', 'I', 1.0);
        grafo.insertarArco('E', 'I', 4.0);
        grafo.insertarArco('E', 'F', 9.0);
        grafo.insertarArco('D', 'G', 4.0);
        grafo.insertarArco('C', 'H', 3.0);
        grafo.insertarArco('C', 'G', 2.0);
        grafo.insertarArco('C', 'D', 7.0);
        grafo.insertarArco('B', 'C', 2.0);
        grafo.insertarArco('B', 'F', 3.0);
        grafo.insertarArco('A', 'F', 2.0);
        grafo.insertarArco('A', 'E', 1.0);
        grafo.insertarArco('A', 'B', 3.0);

        return grafo;
    }
}
