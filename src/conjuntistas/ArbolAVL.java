package conjuntistas;

import jerarquicas.Nodo;

/**
 * Implementación de Árbol AVL.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBB<T> {

    /**
     * Inserta un elemento en el árbol.
     */
    @Override
    public boolean insertar(T elemento) {
        boolean insertado = super.insertar(elemento);
        if (insertado)
            balancear();

        return insertado;
    }

    /**
     * Inserta un elemento al sub-árbol correspondiente al nodo dado.
     * 
     * @param elemento
     * @param nodo
     * @return
     */
    @Override
    protected boolean insertar(T elemento, Nodo<T> nodo) {
        boolean insertado = false;

        if (nodo != null) {
            Nodo<T> izquierdo, derecho, nuevo;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            // Si el elemento es menor al del nodo, insertar en el sub-árbol
            // izquierdo. Si el elemento es mayor al del nodo, insertar en el
            // sub-árbol derecho. Si el elemento es igual al del nodo, el
            // resultado será falso y no continuará hacia los nodos hijos.
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                if (izquierdo == null) {
                    nuevo = new Nodo<T>(elemento);
                    nodo.setIzquierdo(nuevo);
                    insertado = true;
                } else {
                    insertado = insertar(elemento, izquierdo);
                }
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                if (derecho == null) {
                    nuevo = new Nodo<T>(elemento);
                    nodo.setDerecho(nuevo);
                    insertado = true;
                } else {
                    insertado = insertar(elemento, derecho);
                }
            }
        }

        return insertado;
    }

    @Override
    public boolean eliminar(T elemento) {
        boolean eliminado = super.eliminar(elemento);
        if (eliminado)
            balancear();

        return eliminado;
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
            boolean balanceado = false;
            Nodo<T> nodoHijo, reemplazo = null;
            int balanceHijo, balanceNodo = balance(nodo);

            // Detectar si el nodo está balanceado. De no estarlo, hacer las
            // rotaciones necesarias para que éste quede balanceado
            if (balanceNodo == 2) {
                nodoHijo = nodo.getIzquierdo();
                balanceHijo = balance(nodoHijo);

                if (balanceHijo == 1) {
                    reemplazo = rotarDerecha(nodo);
                } else if (balanceHijo == -1) {
                    reemplazo = rotarIzquierdaDerecha(nodo);
                } else if (balanceHijo > 1 || balanceHijo < -1) {
                    balancear(nodoHijo, nodo);
                }

                balanceado = true;
            } else if (balanceNodo == -2) {
                nodoHijo = nodo.getDerecho();
                balanceHijo = balance(nodoHijo);

                if (balanceHijo == 1) {
                    reemplazo = rotarDerechaIzquierda(nodo);
                } else if (balanceHijo == -1) {
                    reemplazo = rotarIzquierda(nodo);
                } else if (balanceHijo > 1 || balanceHijo < -1) {
                    balancear(nodoHijo, nodo);
                }

                balanceado = true;
            }

            // Reemplazar el nodo correspondiente al padre, si ha sido rotado
            if (!balanceado) {
                // Balancear nodos hijos
                balancear(nodo.getIzquierdo(), nodo);
                balancear(nodo.getDerecho(), nodo);
            } else if (reemplazo != null) {
                if (padre == null) {
                    raiz = reemplazo;
                } else {
                    Nodo<T> hermano = null;
                    T elementoPadre, elementoReemplazo;

                    elementoPadre = padre.getElemento();
                    elementoReemplazo = reemplazo.getElemento();

                    if (elementoReemplazo.compareTo(elementoPadre) < 0) {
                        padre.setIzquierdo(reemplazo);
                        hermano = padre.getDerecho();
                    } else if (elementoReemplazo.compareTo(elementoPadre) > 0) {
                        padre.setDerecho(reemplazo);
                        hermano = padre.getIzquierdo();
                    }
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
    protected int balance(Nodo<T> nodo) {
        return altura(nodo.getIzquierdo()) - altura(nodo.getDerecho());
    }

    /**
     * Devuelve la altura de un nodo.
     * 
     * @param nodo
     * @return
     */
    protected int altura(Nodo<T> nodo) {
        int altura = -1;

        if (nodo != null) {
            int alturaIzquierdo, alturaDerecho;
            Nodo<T> hijoIzquierdo, hijoDerecho;
            hijoIzquierdo = nodo.getIzquierdo();
            hijoDerecho = nodo.getDerecho();
            alturaIzquierdo = altura(hijoIzquierdo);
            alturaDerecho = altura(hijoDerecho);
            altura++;

            if (alturaIzquierdo > alturaDerecho)
                altura += alturaIzquierdo;
            else
                altura += alturaDerecho;
        }

        return altura;
    }

    /**
     * Aplica una rotación simple a la derecha al sub-árbol correspondiente al
     * nodo dado, y devuelve el nuevo nodo raíz del sub-árbol.
     * 
     * @param nodo
     * @return
     */
    protected Nodo<T> rotarDerecha(Nodo<T> nodo) {
        Nodo<T> izquierdo, izquierdoHD;

        izquierdo = nodo.getIzquierdo();
        izquierdoHD = izquierdo == null ? null : izquierdo.getDerecho();
        izquierdo.setDerecho(nodo);
        nodo.setIzquierdo(izquierdoHD);

        return izquierdo;
    }

    /**
     * Aplica una rotación simple a la izquierda al sub-árbol correspondiente al
     * nodo dado, y devuelve el nuevo nodo raíz del sub-árbol.
     * 
     * @param nodo
     * @return
     */
    protected Nodo<T> rotarIzquierda(Nodo<T> nodo) {
        Nodo<T> derecho, derechoHI;

        derecho = nodo.getDerecho();
        derechoHI = derecho == null ? null : derecho.getIzquierdo();
        derecho.setIzquierdo(nodo);
        nodo.setDerecho(derechoHI);

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
        Nodo<T> rotado = rotarDerecha(nodo.getDerecho());
        nodo.setDerecho(rotado);

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
        Nodo<T> rotado = rotarIzquierda(nodo.getIzquierdo());
        nodo.setIzquierdo(rotado);

        return rotarDerecha(nodo);
    }

    @Override
    public ArbolAVL<T> clonar() {
        ArbolAVL<T> clon = new ArbolAVL<T>();
        clonar(raiz, clon);

        return clon;
    }

    protected void clonar(Nodo<T> nodo, ArbolAVL<T> arbol) {
        if (nodo != null) {
            Nodo<T> izquierdo, derecho;

            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            arbol.insertar(nodo.getElemento());
            clonar(izquierdo, arbol);
            clonar(derecho, arbol);
        }
    }
}
