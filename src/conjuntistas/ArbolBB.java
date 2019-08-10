package conjuntistas;

import jerarquicas.Nodo;
import lineales.dinamicas.Lista;

/**
 * Implementación de Árbol Binario de Búsqueda.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class ArbolBB<T extends Comparable<T>> {

    /**
     * El nodo raíz del árbol.
     */
    protected Nodo<T> raiz;

    /**
     * Constructor vacío.
     */
    public ArbolBB() {
        raiz = null;
    }

    /**
     * Inserta un elemento al árbol.
     *
     * @param elemento el elemento a insertar
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean insertar(T elemento) {
        boolean insertado = false;

        if (raiz == null) {
            raiz = new Nodo<T>(elemento);
            insertado = true;
        } else {
            insertado = insertar(elemento, raiz);
        }

        return insertado;
    }

    /**
     * Inserta un elemento al sub-árbol correspondiente al nodo dado.
     *
     * @param elemento el elemento a insertar
     * @param nodo el nodo del sub-árbol izquierdo o derecho
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    protected boolean insertar(T elemento, Nodo<T> nodo) {
        boolean insertado = false;

        if (nodo != null) {
            Nodo<T> izquierdo, derecho, nuevo;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            // Si el elemento es menor al del nodo, insertar en el sub-árbol izquierdo
            // Si el elemento es mayor al del nodo, insertar en el sub-árbol derecho
            // Si el elemento es igual al del nodo, el resultado será falso y no continuará hacia los nodos hijos.
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

    /**
     * Elimina el elemento del árbol.
     *
     * @param elemento el elemento a eliminar
     * @return verdadero si el elemento fue eliminado, falso en caso contrario
     */
    public boolean eliminar(T elemento) {
        return eliminar(elemento, raiz, null);
    }

    /**
     * Elimina un elemento del sub-árbol correspondiente al nodo dado.
     *
     * @param elemento el elemento a eliminar
     * @param nodo el nodo desde donde buscar el elemento a eliminar
     * @return verdadero si el elemento fue eliminado, falso en caso contrario
     */
    protected boolean eliminar(T elemento, Nodo<T> nodo, Nodo<T> padre) {
        boolean eliminado = false;

        if (nodo != null) {
            T elemPadre;
            Nodo<T> izquierdo, derecho;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            // Buscar el elemento a eliminar
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                eliminado = izquierdo == null ? false : eliminar(elemento, izquierdo, nodo);
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                eliminado = derecho == null ? false : eliminar(elemento, derecho, nodo);
            } else {
                // Elemento encontrado. Eliminarlo según los 3 casos posibles:
                if (izquierdo == null && derecho == null) { // Caso 1:
                    elemPadre = padre.getElemento();

                    if (elemento.compareTo(elemPadre) < 0) {
                        padre.setIzquierdo(null);
                    } else if (elemento.compareTo(elemPadre) > 0) {
                        padre.setDerecho(null);
                    }

                    eliminado = true;
                } else if (izquierdo != null && derecho != null) { // Caso 3:
                    T elemMinDerecho = minimo(derecho);
                    eliminado = eliminar(elemMinDerecho, derecho, nodo);
                    nodo.setElemento(elemMinDerecho);
                } else { // Caso 2:
                    Nodo<T> reemplazo = derecho == null ? izquierdo : derecho;
                    elemPadre = padre.getElemento();

                    if (elemento.compareTo(elemPadre) < 0) {
                        padre.setIzquierdo(reemplazo);
                    } else if (elemento.compareTo(elemPadre) > 0) {
                        padre.setDerecho(reemplazo);
                    }

                    eliminado = true;
                }
            }
        }

        return eliminado;
    }

    /**
     * Devuelve verdadero si el elemento dado pertenece al árbol, o falso en caso contrario.
     *
     * @param elemento el elemento a buscar
     * @return verdadero si el elemento fue encontrado, falso en caso contrario
     */
    public boolean pertenece(T elemento) {
        return pertenece(elemento, raiz);
    }

    /**
     * Devuelve verdadero si el elemento dado pertenece al sub-árbol correspondiente al nodo dado, falso en caso
     * contrario.
     *
     * @param elemento el elemento a buscar
     * @param nodo el nodo desde donde buscar el elemento
     * @return verdadero si el elemento fue encontrado, falso en caso contrario
     */
    protected boolean pertenece(T elemento, Nodo<T> nodo) {
        boolean existe = false;

        if (nodo != null) {
            Nodo<T> izquierdo, derecho;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            if (elemento.compareTo(nodo.getElemento()) < 0) {
                existe = pertenece(elemento, izquierdo);
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                existe = pertenece(elemento, derecho);
            } else {
                existe = true;
            }
        }

        return existe;
    }

    /**
     * Devuelve el elemento máximo del árbol.
     *
     * @return el elemento máximo
     */
    public T maximo() {
        return maximo(raiz);
    }

    /**
     * Devuelve el elemento máximo a partir de un nodo.
     *
     * @param nodo el nodo desde donde buscar el elemento máximo
     * @return el elemento máximo
     */
    protected T maximo(Nodo<T> nodo) {
        Nodo<T> derecho = nodo, maximo = null;

        while (derecho != null) {
            maximo = derecho;
            derecho = derecho.getDerecho();
        }

        return maximo == null ? null : maximo.getElemento();
    }

    /**
     * Devuelve el elemento mínimo del árbol.
     *
     * @return el elemento mínimo
     */
    public T minimo() {
        return minimo(raiz);
    }

    /**
     * Devuelve el elemento mínimo a partir de un nodo.
     *
     * @param nodo el nodo desde donde buscar el elemento mínimo
     * @return el elemento mínimo
     */
    protected T minimo(Nodo<T> nodo) {
        Nodo<T> izquierdo = nodo, minimo = null;

        while (izquierdo != null) {
            minimo = izquierdo;
            izquierdo = izquierdo.getIzquierdo();
        }

        return minimo == null ? null : minimo.getElemento();
    }

    /**
     * Devuelve verdadero si el árbol está vacío, o falso en caso contrario.
     *
     * @return verdadero si el árbol está vacío, falso en caso contrario
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
     * Devuelve una lista con los elementos del árbol - ordenados de menor a mayor -.
     *
     * @return la lista con los elementos ordenados de menor a mayor
     */
    public Lista<T> listar() {
        Lista<T> lista = new Lista<T>();
        listar(raiz, lista);

        return lista;
    }

    /**
     * Inserta los elementos del árbol - ordenados de menor a mayor - en la lista dada, del sub-árbol correspondiente
     * al nodo dado.
     *
     * @param nodo el nodo desde donde listar los elementos
     * @param lista la lista de elementos
     */
    protected void listar(Nodo<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            Nodo<T> izquierdo, derecho;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            listar(izquierdo, lista);
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            listar(derecho, lista);
        }
    }

    /**
     * Devuelve una lista con los elementos del árbol - ordenados de menor a mayor - mientras que el elemento se
     * encuentre dentro del rango mínimo y máximo especificado.
     *
     * @param minimo el elemento mínimo del rango
     * @param maximo el elemento máximo del rango
     * @return la lista de elementos
     */
    public Lista<T> listarRango(T minimo, T maximo) {
        Lista<T> lista = new Lista<T>();
        listarRango(minimo, maximo, raiz, lista);

        return lista;
    }

    /**
     * Inserta los elementos del árbol - ordenados de menor a mayor - mientras que el elemento se encuentre dentro del
     * rango mínimo y máximo especificado, en la lista dada, del sub-árbol correspondiente al nodo dado.
     *
     * @param minimo el elemento mínimo del rango
     * @param maximo el elemento máximo del rango
     * @param nodo el nodo desde donde listar los elementos
     * @param lista la lista de elementos
     */
    protected void listarRango(T minimo, T maximo, Nodo<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            Nodo<T> izquierdo, derecho;
            T elemento;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();
            elemento = nodo.getElemento();

            listarRango(minimo, maximo, izquierdo, lista);

            if (elemento.compareTo(minimo) >= 0 && elemento.compareTo(maximo) <= 0) {
                lista.insertar(elemento, lista.longitud() + 1);
            }

            listarRango(minimo, maximo, derecho, lista);
        }
    }

    /**
     * Devuelve una copia exacta del árbol.
     *
     * @return la copia del árbol
     */
    public ArbolBB<T> clone() {
        ArbolBB<T> clon = new ArbolBB<T>();
        Lista<T> lista = listar();
        T elemento = null;
        int longitud = 0, medio = 0;

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

    @Override
    public String toString() {
        return listar().toString();
    }
}
