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
    protected Nodo<T> raiz;

    /**
     * Crea y devuelve un árbol BB vacío.
     */
    public ArbolBB() {
        raiz = null;
    }

    /**
     * Inserta un elemento al árbol.
     * 
     * @param elemento
     * @return
     */
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

    /**
     * Inserta un elemento al sub-árbol correspondiente al nodo dado.
     * 
     * @param elemento
     * @param nodo
     * @return
     */
    protected boolean insertar(T elemento, Nodo<T> nodo) {
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

    /**
     * Elimina el elemento del árbol.
     * 
     * @param elemento
     * @return
     */
    public boolean eliminar(T elemento) {
        return eliminar(elemento, raiz, null);
    }

    /**
     * Elimina un elemento del sub-árbol correspondiente al nodo dado.
     * 
     * @param elemento
     * @param nodo
     * @param padre
     * @return
     */
    protected boolean eliminar(T elemento, Nodo<T> nodo, Nodo<T> padre) {
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

    /**
     * Devuelve verdadero si el elemento dado pertenece al árbol, o falso en
     * caso contrario.
     * 
     * @param elemento
     * @return
     */
    public boolean pertenece(T elemento) {
        return pertenece(elemento, raiz);
    }

    /**
     * Devuelve verdadero si el elemento dado pertenece al sub-árbol
     * correspondiente al nodo dado, o falso en caso contrario.
     * 
     * @param elemento
     * @param nodo
     * @return
     */
    protected boolean pertenece(T elemento, Nodo<T> nodo) {
        boolean resultado = false;

        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo(),
                    derecho = nodo.getDerecho();

            if (elemento.compareTo(nodo.getElemento()) < 0) {
                resultado = pertenece(elemento, izquierdo);
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                resultado = pertenece(elemento, derecho);
            } else {
                resultado = true;
            }
        }

        return resultado;
    }

    /**
     * Devuelve el elemento máximo del árbol.
     * 
     * @return
     */
    public T maximo() {
        return raiz == null ? null : maximo(raiz).getElemento();
    }

    /**
     * Devuelve el nodo con el elemento máximo del sub-árbol correspondiente al
     * nodo dado.
     * 
     * @param nodo
     * @return
     */
    protected Nodo<T> maximo(Nodo<T> nodo) {
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

    /**
     * Devuelve el elemento mínimo del árbol.
     * 
     * @return
     */
    public T minimo() {
        return raiz == null ? null : minimo(raiz).getElemento();
    }

    /**
     * Devuelve el nodo con el elemento mínimo del sub-árbol correspondiente al
     * nodo dado.
     * 
     * @param nodo
     * @return
     */
    protected Nodo<T> minimo(Nodo<T> nodo) {
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

    /**
     * Devuelve verdadero si el árbol está vacío, o falso en caso contrario.
     * 
     * @return
     */
    public boolean vacio() {
        return raiz == null;
    }

    /**
     * Elimina todos los elementos del árbol.
     */
    public void vaciar() {
        raiz = null;
    }

    /**
     * Devuelve una lista con los elementos del árbol - ordenados de menor a
     * mayor -.
     * 
     * @return
     */
    public Lista<T> listar() {
        Lista<T> lista = new Lista<T>();
        listar(lista, raiz);
        return lista;
    }

    /**
     * Inserta los elementos del árbol - ordenados de menor a mayor - en la
     * lista dada, del sub-árbol correspondiente al nodo dado.
     * 
     * @param lista
     * @param nodo
     */
    protected void listar(Lista<T> lista, Nodo<T> nodo) {
        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo(),
                    derecho = nodo.getDerecho();

            listar(lista, izquierdo);
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            listar(lista, derecho);
        }
    }

    /**
     * Devuelve una lista con los elementos del árbol - ordenados de menor a
     * mayor - mientras que el elemento se encuentre dentro del rango mínimo y
     * máximo especificado.
     * 
     * @param minimo
     * @param maximo
     * @return
     */
    public Lista<T> listarRango(T minimo, T maximo) {
        Lista<T> lista = new Lista<T>();
        listarRango(minimo, maximo, lista, raiz);
        return lista;
    }

    /**
     * Inserta los elementos del árbol - ordenados de menor a mayor - mientras
     * que el elemento se encuentre dentro del rango mínimo y máximo
     * especificado, en la lista dada, del sub-árbol correspondiente al nodo
     * dado.
     * 
     * @param minimo
     * @param maximo
     * @param lista
     * @param nodo
     */
    protected void listarRango(T minimo, T maximo, Lista<T> lista, Nodo<T> nodo) {
        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo(),
                    derecho = nodo.getDerecho();
            T elemento = nodo.getElemento();
            boolean esMayorIgualQueMinimo = elemento.compareTo(minimo) >= 0,
                    esMenorIgualQueMaximo = elemento.compareTo(maximo) <= 0;

            listarRango(minimo, maximo, lista, izquierdo);

            if (esMayorIgualQueMinimo && esMenorIgualQueMaximo) {
                lista.insertar(elemento, lista.longitud() + 1);
            }

            listarRango(minimo, maximo, lista, derecho);
        }
    }

    /**
     * Devuelve una copia exacta del árbol.
     * 
     * @return
     */
    public ArbolBB<T> clonar() {
        ArbolBB<T> clon = new ArbolBB<T>();
        Lista<T> lista = listar();
        T elemento = null;
        int longitud = 0,
            medio = 0;

        // Insertar elementos al árbol de manera tal que éste quede balanceado
        while (!lista.esVacia()) {
            if (medio == 0) {
                longitud = lista.longitud();
                medio = longitud > 1 ? longitud / 2 : 1;
            } else if (medio > 0) {
                medio = medio > 1 ? medio / 2 : 1;
            }
            
            elemento = lista.recuperar(medio);
            lista.eliminar(medio);

            if (elemento != null) {
                clon.insertar(elemento);
            }
        }

        return clon;
    }

    /**
     * Devuelve la representación del árbol en forma de cadena.
     * Por defecto, éste método equivale a llamar listar.toString().
     */
    public String toString() {
        return listar().toString();
    }
}
