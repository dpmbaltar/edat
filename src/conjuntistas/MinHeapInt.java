package conjuntistas;

/**
 * Implementación de Heap Mínimo con enteros.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class MinHeapInt {

    private static final int TAM = 32;
    private int[] heap;
    private int ultimo;

    public MinHeapInt() {
        heap = new int[TAM];
        ultimo = -1;
    }

    public boolean insertar(int elem) {
        boolean resultado = false;

        if ((ultimo + 1) < TAM) {
            heap[++ultimo] = elem;
            if (ultimo > 0) {
                hacerSubir();
            }
        }

        return resultado;
    }

    private void hacerSubir() {
        boolean seguir = true;
        int nuevo = heap[ultimo], padre = (ultimo / 2) - 1;

        while (seguir && padre >= 0) {
            if (nuevo < heap[padre]) {
                heap[ultimo] = heap[padre];
                heap[padre] = nuevo;
                padre = (padre / 2) - 1;
            } else {
                seguir = false;
            }
        }
    }

    public boolean eliminarCima() {
        boolean resultado = false;

        if (!esVacio()) {

        }

        return resultado;
    }

    public int recuperarCima() {
        int cima = 0;

        return cima;
    }

    public boolean esVacio() {
        return (ultimo == -1);
    }

    public String toString() {
        int i, nivel;
        String cadena = "";

        if (!esVacio()) {
            cadena += heap[0];
            nivel = 1;
            for (i = 0; i <= ultimo; i++) {

            }
        }

        return cadena;
    }
}
