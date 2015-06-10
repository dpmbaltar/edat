package pruebas.conjuntistas;

import conjuntistas.HeapMaximo;

/**
 * Prueba implementación de Heap Máximo.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class PruebaHeapMaximo {

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
        HeapMaximo<Integer> heap = new HeapMaximo<Integer>();
        assert heap.insertar(8) : "Debe insertar 8 en el heap";
    }

    protected static void pruebaRecuperarCima() {
        HeapMaximo<Integer> heap = crearHeapDePrueba();
        assert heap.recuperarCima() == 63 : "Debe recuperar como cima a 63";
    }

    protected static void pruebaEliminarCima() {
        HeapMaximo<Integer> heap = crearHeapDePrueba();
        assert heap.eliminarCima() : "Debe eliminar cima (63)";
        assert heap.recuperarCima() == 31 : "Debe recuperar como cima a 31";
    }

    protected static void pruebaEsVacio() {
        HeapMaximo<Integer> heap = new HeapMaximo<Integer>();
        assert heap.esVacio() : "Heap debe ser vacío";
        heap.insertar(8);
        assert !heap.esVacio() : "Heap no debe ser vacío";
    }

    protected static void pruebaVaciar() {
        HeapMaximo<Integer> heap = crearHeapDePrueba();
        heap.vaciar();
        assert heap.esVacio() : "Heap debe ser vacío";
        assert heap.recuperarCima() == null : "Debe recuperar cima nula";
    }

    protected static void pruebaToString() {
        HeapMaximo<Integer> heap = crearHeapDePrueba();
        assert heap.toString().equals("[63 31 16 8 1 4 15 2]") : "Debe devolver los elementos del Heap de izquierda a derecha por niveles";
    }

    protected static HeapMaximo<Integer> crearHeapDePrueba() {
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
