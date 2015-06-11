package conjuntistas;

/**
 * Implementación de Árbol AVL.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBB<T> {

    @Override
    public boolean insertar(T elemento) {
        return false;
        
    }

    @Override
    public boolean eliminar(T elemento) {
        return false;
        
    }

    protected void balancear(NodoAVL<T> nodo) {
        
    }

    protected void rotarDerecha(NodoAVL<T> nodo) {
        
    }
    
    protected void rotarIzquierda(NodoAVL<T> nodo) {
        
    }

    protected void rotarDerechaIzquierda(NodoAVL<T> nodo) {
        
    }

    protected void rotarIzquierdaDerecha(NodoAVL<T> nodo) {
        
    }

    @Override
    public ArbolAVL<T> clonar() {
        return null;
        
    }
}
