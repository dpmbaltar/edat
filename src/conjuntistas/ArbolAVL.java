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
        boolean insertado = super.insertar(elemento);

        if (insertado) {
            balancear();
        }

        return insertado;
    }

    @Override
    public boolean eliminar(T elemento) {
        boolean eliminado = super.eliminar(elemento);

        if (eliminado) {
            balancear();
        }

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
                    reemplazo.setPadre(null);
                    raiz = reemplazo;
                } else {
                    int alturaHermano, alturaReemplazo;
                    Nodo<T> hermano = null;
                    T elementoPadre, elementoReemplazo;

                    alturaReemplazo = reemplazo.getAltura();
                    elementoPadre = padre.getElemento();
                    elementoReemplazo = reemplazo.getElemento();
                    reemplazo.setPadre(padre);

                    if (elementoReemplazo.compareTo(elementoPadre) < 0) {
                        padre.setIzquierdo(reemplazo);
                        hermano = padre.getDerecho();
                    } else if (elementoReemplazo.compareTo(elementoPadre) > 0) {
                        padre.setDerecho(reemplazo);
                        hermano = padre.getIzquierdo();
                    }

                    // Recalcular altura del nodo
                    alturaHermano = hermano == null ? -1 : hermano.getAltura();
                    if (alturaReemplazo > alturaHermano) {
                        padre.setAltura(alturaReemplazo + 1);
                    } else {
                        padre.setAltura(alturaHermano + 1);
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
        Nodo<T> izquierdo = nodo.getIzquierdo(),
                derecho = nodo.getDerecho();
        int alturaIzquierdo = izquierdo == null ? -1 : izquierdo.getAltura(),
            alturaDerecho = derecho == null ? -1 : derecho.getAltura();

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
                derecho = nodo.getDerecho(),
                auxiliar1 = izquierdo == null ? null : izquierdo.getDerecho(),
                auxiliar2 = izquierdo == null ? null : izquierdo.getIzquierdo();
        int alturaDerecho = derecho == null ? -1 : derecho.getAltura(),
            alturaAuxiliar1 = auxiliar1 == null ? -1 : auxiliar1.getAltura(),
            alturaAuxiliar2 = auxiliar2 == null ? -1 : auxiliar2.getAltura();

        izquierdo.setDerecho(nodo);
        nodo.setPadre(izquierdo);
        nodo.setIzquierdo(auxiliar1);
        if (auxiliar1 != null) {
            auxiliar1.setPadre(nodo);
        }

        // Calcular altura del nodo
        if (alturaDerecho > alturaAuxiliar1) {
            nodo.setAltura(alturaDerecho + 1);
        } else {
            nodo.setAltura(alturaAuxiliar1 + 1);
        }

        // Calcular altura del reemplazo
        if (alturaAuxiliar2 > nodo.getAltura()) {
            izquierdo.setAltura(alturaAuxiliar2 + 1);
        } else {
            izquierdo.setAltura(nodo.getAltura() + 1);
        }

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
                izquierdo = nodo.getIzquierdo(),
                auxiliar1 = derecho == null ? null : derecho.getIzquierdo(),
                auxiliar2 = derecho == null ? null : derecho.getDerecho();
        int alturaIzquierdo = izquierdo == null ? -1 : izquierdo.getAltura(),
            alturaAuxiliar1 = auxiliar1 == null ? -1 : auxiliar1.getAltura(),
            alturaAuxiliar2 = auxiliar2 == null ? -1 : auxiliar2.getAltura();

        derecho.setIzquierdo(nodo);
        nodo.setPadre(derecho);
        nodo.setDerecho(auxiliar1);
        if (auxiliar1 != null) {
            auxiliar1.setPadre(nodo);
        }

        // Recalcular altura del nodo
        if (alturaIzquierdo > alturaAuxiliar1) {
            nodo.setAltura(alturaIzquierdo + 1);
        } else {
            nodo.setAltura(alturaAuxiliar1 + 1);
        }

        // Recalcular altura del reemplazo
        if (alturaAuxiliar2 > nodo.getAltura()) {
            derecho.setAltura(alturaAuxiliar2 + 1);
        } else {
            derecho.setAltura(nodo.getAltura() + 1);
        }

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
        Nodo<T> derecho = nodo.getDerecho(),
                izquierdo = nodo.getIzquierdo(),
                rotado = rotarDerecha(derecho);
        int alturaIzquierdo = izquierdo == null ? -1 : izquierdo.getAltura(),
            alturaRotado = rotado == null ? -1 : rotado.getAltura();

        nodo.setDerecho(rotado);
        rotado.setPadre(nodo);

        // Recalcular altura del nodo
        if (alturaIzquierdo > alturaRotado) {
            nodo.setAltura(alturaIzquierdo + 1);
        } else {
            nodo.setAltura(alturaRotado + 1);
        }

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
        Nodo<T> izquierdo = nodo.getIzquierdo(),
                derecho = nodo.getDerecho(),
                rotado = rotarIzquierda(izquierdo);
        int alturaDerecho = derecho == null ? -1 : derecho.getAltura(),
            alturaRotado = rotado == null ? -1 : rotado.getAltura();

        nodo.setIzquierdo(rotado);
        rotado.setPadre(nodo);

        // Recalcular altura del nodo
        if (alturaDerecho > alturaRotado) {
            nodo.setAltura(alturaDerecho + 1);
        } else {
            nodo.setAltura(alturaRotado + 1);
        }

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
