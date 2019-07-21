package pruebas.conjuntistas;

import conjuntistas.HeapMaximo;

/**
 * Prueba implementación de Heap Máximo.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaHeapMaximo {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaHeapMaximo() {
        pruebaInsertar();
        pruebaRecuperarCima();
        pruebaEliminarCima();
        pruebaEsVacio();
        pruebaVaciar();
        pruebaToString();
        pruebaHeapMaximo();
    }

    /**
     * Prueba {@link conjuntistas.HeapMaximo#insertar(Comparable)}.
     */
    public void pruebaInsertar() {
        HeapMaximo<Integer> heap = new HeapMaximo<Integer>();
        assert heap.insertar(8) : "Debe insertar 8 en el heap";
    }

    /**
     * Prueba {@link conjuntistas.HeapMaximo#recuperarCima()}.
     */
    public void pruebaRecuperarCima() {
        HeapMaximo<Integer> heap = crearHeapMaximo();
        assert heap.recuperarCima() == 63 : "Debe recuperar como cima a 63";
    }

    /**
     * Prueba {@link conjuntistas.HeapMaximo#eliminarCima()}.
     */
    public void pruebaEliminarCima() {
        HeapMaximo<Integer> heap = crearHeapMaximo();
        assert heap.eliminarCima() : "Debe eliminar cima (63)";
        assert heap.recuperarCima() == 31 : "Debe recuperar como cima a 31";
    }

    /**
     * Prueba {@link conjuntistas.HeapMaximo#esVacio()}.
     */
    public void pruebaEsVacio() {
        HeapMaximo<Integer> heap = new HeapMaximo<Integer>();
        assert heap.esVacio() : "Heap debe ser vacío";
        heap.insertar(8);
        assert !heap.esVacio() : "Heap no debe ser vacío";
    }

    /**
     * Prueba {@link conjuntistas.HeapMaximo#vaciar()}.
     */
    public void pruebaVaciar() {
        HeapMaximo<Integer> heap = crearHeapMaximo();
        heap.vaciar();
        assert heap.esVacio() : "Heap debe ser vacío";
        assert heap.recuperarCima() == null : "Debe recuperar cima nula";
    }

    /**
     * Prueba {@link conjuntistas.HeapMaximo#toString()}.
     */
    public void pruebaToString() {
        HeapMaximo<Integer> heap = crearHeapMaximo();
        assert heap.toString().equals("[63, 31, 16, 8, 1, 4, 15, 2]")
                : "Debe ser los elementos del Heap de izq. a der. por niveles";
    }

    /**
     * Prueba {@link conjuntistas.HeapMaximo}.
     */
    public void pruebaHeapMaximo() {
        HeapMaximo<Integer> heap = new HeapMaximo<Integer>();
        assert heap.insertar(5) : "Debe insertar 5 en el heap";
        heap.insertar(4);
        heap.insertar(3);
        heap.insertar(7);
        assert heap.toString().equals("[7, 5, 3, 4]")
                : "Debe ser los elementos del Heap por niveles [7, 5, 3, 4]";
        heap.eliminarCima();
        assert heap.toString().equals("[5, 4, 3]")
                : "Debe ser los elementos del Heap por niveles [5, 4, 3]";
    }

    /**
     * Crea un Heap máximo de prueba.
     *
     * @return el heap máximo de prueba
     */
    public static HeapMaximo<Integer> crearHeapMaximo() {
        HeapMaximo<Integer> heap = new HeapMaximo<Integer>();
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
