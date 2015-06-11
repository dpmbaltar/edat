package conjuntistas;

import jerarquicas.dinamicas.Nodo;
import lineales.dinamicas.Lista;

/**
 * Implementación de Árbol Binario de Búsqueda.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class ArbolBB<T extends Comparable<T>> {

    /**
     * El nodo raíz del árbol.
     */
    private Nodo<T> raiz;

    /**
     * Crea y devuelve un árbol BB vacío.
     */
    public ArbolBB() {
        raiz = null;
    }

    public boolean insertar(T elemento) {
        boolean resultado = false;

        if (raiz == null) {
            raiz = new Nodo<T>(elemento);
            resultado = true;
        } else {
            resultado = insertar(elemento, raiz);
        }

        return resultado;
    }

    private boolean insertar(T elemento, Nodo<T> nodo) {
        boolean resultado = false;

        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo(),
                    derecho = nodo.getDerecho();

            // Si el elemento es menor al del nodo, insertar en el sub-árbol
            // izquierdo. Si el elemento es mayor al del nodo, insertar en el
            // sub-árbol derecho. Si el elemento es igual al del nodo, el
            // resultado será falso y no continuará hacia los nodos hijos.
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                if (izquierdo == null) {
                    nodo.setIzquierdo(new Nodo<T>(elemento));
                    resultado = true;
                } else {
                    resultado = insertar(elemento, izquierdo);
                }
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                if (derecho == null) {
                    nodo.setDerecho(new Nodo<T>(elemento));
                    resultado = true;
                } else {
                    resultado = insertar(elemento, derecho);
                }
            }
        }

        return resultado;
    }

    public boolean eliminar(T elemento) {
        return eliminar(elemento, raiz, null);
    }

    private boolean eliminar(T elemento, Nodo<T> nodo, Nodo<T> padre) {
        boolean resultado = false;

        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo(),
                    derecho = nodo.getDerecho();
            T elementoNodo = nodo.getElemento();

            // Buscar (recursivamente) el elemento a eliminar
            if (elemento.compareTo(elementoNodo) < 0) {
                if (izquierdo != null) {
                    resultado = eliminar(elemento, izquierdo, nodo);
                }
            } else if (elemento.compareTo(elementoNodo) > 0) {
                if (derecho != null) {
                    resultado = eliminar(elemento, derecho, nodo);
                }
            } else {
                // Elemento encontrado. Eliminarlo según los 3 casos posibles
                if (izquierdo == null && derecho == null) {
                    // Caso 1:
                    T elementoPadre = padre.getElemento();
                    if (elemento.compareTo(elementoPadre) < 0) {
                        padre.setIzquierdo(null);
                    } else if (elemento.compareTo(elementoPadre) > 0) {
                        padre.setDerecho(null);
                    }
                    resultado = true;
                } else if (izquierdo != null && derecho != null) {
                    // Caso 3:
                    T elementoMinimoDerecho = minimo(derecho).getElemento();
                    resultado = eliminar(elementoMinimoDerecho, derecho, nodo);
                    nodo.setElemento(elementoMinimoDerecho);
                } else {
                    // Caso 2:
                    Nodo<T> reemplazo = derecho == null ? izquierdo : derecho;
                    T elementoPadre = padre.getElemento();
                    if (elemento.compareTo(elementoPadre) < 0) {
                        padre.setIzquierdo(reemplazo);
                    } else if (elemento.compareTo(elementoPadre) > 0) {
                        padre.setDerecho(reemplazo);
                    }
                    resultado = true;
                }
            }
        }

        return resultado;
    }

    public boolean pertenece(T elemento) {
        boolean resultado = false;
        
        return resultado;
    }

    public T maximo() {
        return raiz == null ? null : maximo(raiz).getElemento();
    }

    /**
     * Devuelve el nodo con el elemento máximo, a partir de un nodo dado.
     * 
     * @param nodo
     * @return
     */
    private Nodo<T> maximo(Nodo<T> nodo) {
        Nodo<T> derecho, maximo = null;

        while (nodo != null) {
            derecho = nodo.getDerecho();
            if (derecho == null) {
                maximo = nodo;
            }
            nodo = derecho;
        }

        return maximo;
    }

    public T minimo() {
        return raiz == null ? null : minimo(raiz).getElemento();
    }

    /**
     * Devuelve el nodo con el elemento mínimo, a partir de un nodo dado.
     * 
     * @param nodo
     * @return
     */
    private Nodo<T> minimo(Nodo<T> nodo) {
        Nodo<T> izquierdo, minimo = null;

        while (nodo != null) {
            izquierdo = nodo.getIzquierdo();
            if (izquierdo == null) {
                minimo = nodo;
            }
            nodo = izquierdo;
        }

        return minimo;
    }

    public boolean vacio() {
        return raiz == null;
    }

    public void vaciar() {
        raiz = null;
    }

    public Lista<T> listar() {
        Lista<T> lista = new Lista<T>();
        inorden(raiz, lista);
        return lista;
    }

    private void inorden(Nodo<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo(),
                    derecho = nodo.getDerecho();

            inorden(izquierdo, lista);
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            inorden(derecho, lista);
        }
    }

    public Lista<T> listarRango(T minimo, T maximo) {
        Lista<T> lista = new Lista<T>();
        
        return lista;
    }

    public ArbolBB<T> clonar() {
        ArbolBB<T> clon = new ArbolBB<T>();
        
        return clon;
    }

    public String toString() {
        return listar().toString();
    }
}
