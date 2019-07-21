package pruebas.conjuntistas;

import conjuntistas.HeapMinimo;

/**
 * Prueba implementación de Heap Mínimo.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaHeapMinimo {

    public PruebaHeapMinimo() {
        pruebaInsertar();
        pruebaRecuperarCima();
        pruebaEliminarCima();
        pruebaEsVacio();
        pruebaVaciar();
        pruebaToString();
        pruebaHeapMinimo();
    }

    private void pruebaInsertar() {
        HeapMinimo<Integer> heap = new HeapMinimo<Integer>();
        assert heap.insertar(8) : "Debe insertar 8 en el heap";
    }

    private void pruebaRecuperarCima() {
        HeapMinimo<Integer> heap = crearHeapMinimo();
        assert heap.recuperarCima() == 1 : "Debe recuperar como cima a 1";
    }

    private void pruebaEliminarCima() {
        HeapMinimo<Integer> heap = crearHeapMinimo();
        assert heap.eliminarCima() : "Debe eliminar cima (1)";
        assert heap.recuperarCima() == 2 : "Debe recuperar como cima a 2";
    }

    private void pruebaEsVacio() {
        HeapMinimo<Integer> heap = new HeapMinimo<Integer>();
        assert heap.esVacio() : "Heap debe ser vacío";
        heap.insertar(8);
        assert !heap.esVacio() : "Heap no debe ser vacío";
    }

    private void pruebaVaciar() {
        HeapMinimo<Integer> heap = crearHeapMinimo();
        heap.vaciar();
        assert heap.esVacio() : "Heap debe ser vacío";
        assert heap.recuperarCima() == null : "Debe recuperar cima nula";
    }

    private void pruebaToString() {
        HeapMinimo<Integer> heap = crearHeapMinimo();
        assert heap.toString().equals("[1, 2, 8, 16, 4, 15, 63, 31]")
                : "Debe ser los elementos del Heap de izq. a der. por niveles";
    }

    private void pruebaHeapMinimo() {
        HeapMinimo<Integer> heap = new HeapMinimo<Integer>();
        assert heap.insertar(7) : "Debe insertar 7 en el heap";
        heap.insertar(3);
        heap.insertar(4);
        heap.insertar(5);
        assert heap.toString().equals("[3, 5, 4, 7]")
                : "Debe ser los elementos del Heap por niveles [3, 5, 4, 7]";
        heap.eliminarCima();
        assert heap.toString().equals("[4, 5, 7]")
                : "Debe ser los elementos del Heap por niveles [4, 5, 7]";
    }

    private static HeapMinimo<Integer> crearHeapMinimo() {
        HeapMinimo<Integer> heap = new HeapMinimo<Integer>();
        heap.insertar(8);
        heap.insertar(16);
        heap.insertar(4);
        heap.insertar(2);
        heap.insertar(1);
        heap.insertar(15);
        heap.insertar(63);
        heap.insertar(31);

        return heap;
    }
}
