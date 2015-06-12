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

    /**
     * Balancea el sub-árbol correspondiente al nodo dado.
     * 
     * @param nodo
     * @param padre
     */
    protected void balancear(NodoAVL<T> nodo, NodoAVL<T> padre) {
        if (nodo != null) {
            int balance = balance(nodo),
                balanceHijo;
            if (balance == 2) {
                balanceHijo = balance(nodo.getIzquierdo());
                if (balanceHijo == 1) {
                    rotarDerecha(nodo);
                } else if (balanceHijo == -1) {
                    rotarIzquierdaDerecha(nodo);
                }
            } else if (balance == -2) {
                balanceHijo = balance(nodo.getDerecho());
                if (balanceHijo == 1) {
                    rotarIzquierda(nodo);
                } else if (balanceHijo == -1) {
                    rotarDerechaIzquierda(nodo);
                }
            }
        }
    }

    /**
     * Devuelve el balance del nodo dado.
     * 
     * @param nodo
     * @return
     */
    protected int balance(NodoAVL<T> nodo) {
        NodoAVL<T> izquierdo = nodo.getIzquierdo(),
                   derecho = nodo.getDerecho();
        int alturaIzquierdo = izquierdo == null ? -1 : izquierdo.getAltura(),
            alturaDerecho = derecho == null ? -1 : derecho.getAltura();

        return alturaIzquierdo - alturaDerecho;
    }

    protected NodoAVL<T> rotarDerecha(NodoAVL<T> nodo) {
        NodoAVL<T> izquierdo = nodo.getIzquierdo(),
                   auxiliar = izquierdo == null ? null : izquierdo.getDerecho();
        izquierdo.setDerecho(nodo);
        nodo.setIzquierdo(auxiliar);

        return izquierdo;
    }

    protected NodoAVL<T> rotarIzquierda(NodoAVL<T> nodo) {
        NodoAVL<T> derecho = nodo.getDerecho(),
                   auxiliar = derecho == null ? null : derecho.getIzquierdo();
        derecho.setIzquierdo(nodo);
        nodo.setDerecho(auxiliar);

        return derecho;
    }

    protected NodoAVL<T> rotarDerechaIzquierda(NodoAVL<T> nodo) {
        return null;
    }

    protected NodoAVL<T> rotarIzquierdaDerecha(NodoAVL<T> nodo) {
        return null;
    }

    @Override
    public ArbolAVL<T> clonar() {
        return null;
        
    }
}
