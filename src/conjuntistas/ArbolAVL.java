package conjuntistas;

import jerarquicas.dinamicas.Nodo;

/**
 * Implementación de Árbol AVL.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBB<T> {

    @Override
    public boolean insertar(T elemento) {
        boolean resultado = super.insertar(elemento);
        balancear();
        return resultado;
    }

    @Override
    public boolean eliminar(T elemento) {
        boolean resultado = super.eliminar(elemento);
        balancear();
        return resultado;
    }

    /**
     * Balancea el árbol.
     */
    protected void balancear() {
        balancear(raiz, null);
    }

    /**
     * Balancea el sub-árbol correspondiente al nodo dado.
     * 
     * @param nodo
     * @param padre
     */
    protected void balancear(Nodo<T> nodo, Nodo<T> padre) {
        if (nodo != null) {
            Nodo<T> reemplazo = null;
            int balanceNodo = balance(nodo),
                balanceHijo;

            // Detectar si el nodo no está balanceado, y de estarlo, hacer las
            // rotaciones necesarias para que éste quede balanceado
            if (balanceNodo == 2) {
                balanceHijo = balance(nodo.getIzquierdo());
                if (balanceHijo == 1) {
                    reemplazo = rotarDerecha(nodo);
                } else if (balanceHijo == -1) {
                    reemplazo = rotarIzquierdaDerecha(nodo);
                }
            } else if (balanceNodo == -2) {
                balanceHijo = balance(nodo.getDerecho());
                if (balanceHijo == 1) {
                    reemplazo = rotarDerechaIzquierda(nodo);
                } else if (balanceHijo == -1) {
                    reemplazo = rotarIzquierda(nodo);
                }
            }

            // Reemplazar el nodo correspondiente al padre, si ha sido rotado
            if (reemplazo != null) {
                if (padre == null) {
                    raiz = reemplazo;
                } else {
                    T elementoPadre = padre.getElemento(),
                      elementoReemplazo = reemplazo.getElemento();
                    if (elementoReemplazo.compareTo(elementoPadre) < 0) {
                        padre.setIzquierdo(reemplazo);
                    } else if (elementoReemplazo.compareTo(elementoPadre) > 0) {
                        padre.setDerecho(reemplazo);
                    }
                }
            }

            // Balancear nodos hijos
            balancear(nodo.getIzquierdo(), nodo);
            balancear(nodo.getDerecho(), nodo);
        }
    }

    /**
     * Devuelve el balance del nodo dado.
     * 
     * @param nodo
     * @return
     */
    protected int balance(Nodo<T> nodo) {
        Nodo<T> izquierdo = nodo.getIzquierdo(),
                   derecho = nodo.getDerecho();
        int alturaIzquierdo = izquierdo == null ? 0 : izquierdo.getAltura(),
            alturaDerecho = derecho == null ? 0 : derecho.getAltura();

        return alturaIzquierdo - alturaDerecho;
    }

    /**
     * Aplica una rotación simple a la derecha al sub-árbol correspondiente
     * al nodo dado, y devuelve el nuevo nodo raíz del sub-árbol.
     * 
     * @param nodo
     * @return
     */
    protected Nodo<T> rotarDerecha(Nodo<T> nodo) {
        Nodo<T> izquierdo = nodo.getIzquierdo(),
                   auxiliar = izquierdo == null ? null : izquierdo.getDerecho();
        izquierdo.setDerecho(nodo);
        nodo.setIzquierdo(auxiliar);

        return izquierdo;
    }

    /**
     * Aplica una rotación simple a la izquierda al sub-árbol correspondiente
     * al nodo dado, y devuelve el nuevo nodo raíz del sub-árbol.
     * 
     * @param nodo
     * @return
     */
    protected Nodo<T> rotarIzquierda(Nodo<T> nodo) {
        Nodo<T> derecho = nodo.getDerecho(),
                   auxiliar = derecho == null ? null : derecho.getIzquierdo();
        derecho.setIzquierdo(nodo);
        nodo.setDerecho(auxiliar);

        return derecho;
    }

    /**
     * Aplica una rotación doble derecha-izquierda al sub-árbol correspondiente
     * al nodo dado, y devuelve el nuevo nodo raíz del sub-árbol.
     * 
     * @param nodo
     * @return
     */
    protected Nodo<T> rotarDerechaIzquierda(Nodo<T> nodo) {
        Nodo<T> derecho = nodo.getDerecho();
        nodo.setDerecho(rotarDerecha(derecho));

        return rotarIzquierda(nodo);
    }

    /**
     * Aplica una rotación doble izquierda-derecha al sub-árbol correspondiente
     * al nodo dado, y devuelve el nuevo nodo raíz del sub-árbol.
     * 
     * @param nodo
     * @return
     */
    protected Nodo<T> rotarIzquierdaDerecha(Nodo<T> nodo) {
        Nodo<T> izquierdo = nodo.getIzquierdo();
        nodo.setIzquierdo(rotarIzquierda(izquierdo));

        return rotarDerecha(nodo);
    }

    @Override
    public ArbolAVL<T> clonar() {
        return null;
        
    }
}
