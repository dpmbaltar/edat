package pruebas.conjuntistas;

import conjuntistas.HeapMinimo;

/**
 * Prueba implementación de Heap Mínimo.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaHeapMinimo {

    public static void main(String[] args) {
        try {
            // Inicio de pruebas
            pruebaInsertar();
            pruebaRecuperarCima();
            pruebaEliminarCima();
            pruebaEsVacio();
            pruebaVaciar();
            pruebaToString();
            // Fin de pruebas
            System.out.println("Prueba OK");
        } catch (AssertionError e) {
            System.out.println("Error de prueba: " + e.getMessage());
            System.out.println("Detalles:");
            e.printStackTrace();
        }
    }

    protected static void pruebaInsertar() {
        HeapMinimo<Integer> heap = new HeapMinimo<Integer>();
        assert heap.insertar(8) : "Debe insertar 8 en el heap";
    }

    protected static void pruebaRecuperarCima() {
        HeapMinimo<Integer> heap = crearHeapDePrueba();
        assert heap.recuperarCima() == 1 : "Debe recuperar como cima a 1";
    }

    protected static void pruebaEliminarCima() {
        HeapMinimo<Integer> heap = crearHeapDePrueba();
        assert heap.eliminarCima() : "Debe eliminar cima (1)";
        assert heap.recuperarCima() == 2 : "Debe recuperar como cima a 2";
    }

    protected static void pruebaEsVacio() {
        HeapMinimo<Integer> heap = new HeapMinimo<Integer>();
        assert heap.esVacio() : "Heap debe ser vacío";
        heap.insertar(8);
        assert !heap.esVacio() : "Heap no debe ser vacío";
    }

    protected static void pruebaVaciar() {
        HeapMinimo<Integer> heap = crearHeapDePrueba();
        heap.vaciar();
        assert heap.esVacio() : "Heap debe ser vacío";
        assert heap.recuperarCima() == null : "Debe recuperar cima nula";
    }

    protected static void pruebaToString() {
        HeapMinimo<Integer> heap = crearHeapDePrueba();
        assert heap.toString().equals("[1 2 8 16 4 15 63 31]") : "Debe devolver los elementos del Heap de izquierda a derecha por niveles";
    }

    protected static HeapMinimo<Integer> crearHeapDePrueba() {
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
