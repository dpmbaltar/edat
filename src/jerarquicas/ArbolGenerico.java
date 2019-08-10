package jerarquicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 * Implementación dinámica de Árbol Genérico.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class ArbolGenerico<T> {

    /**
     * El nodo raíz del árbol.
     */
    private Nodo<T> raiz;

    /**
     * Constructor vacío.
     */
    public ArbolGenerico() {
        raiz = null;
    }

    /**
     * Inserta un nuevo elemento al árbol.
     *
     * @param elemento el elemento a insertar
     * @param elementoPadre el elemento padre
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean insertar(T elemento, T elementoPadre) {
        boolean resultado = false;

        if (raiz == null) {
            raiz = new Nodo<T>(elemento);
            resultado = true;
        } else if (elementoPadre != null) {
            Nodo<T> nodoPadre = buscarNodo(elementoPadre);

            if (nodoPadre != null) {
                Nodo<T> nodoNuevo = new Nodo<T>(elemento),
                        nodoHermano = nodoPadre.getIzquierdo();

                // Agregar nuevo nodo al principio
                // Desventaja: mantiene un orden menos coherente de los hijos
                // Ventaja: más eficiente, enlaza con el primer hijo
//                if (nodoHermano != null) {
//                    nodoNuevo.setDerecho(nodoHermano);
//                }
//
//                nodoPadre.setIzquierdo(nodoNuevo);

                // Agregar nuevo nodo al final
                // Desventaja: menos eficiente, ya que recorre todos los hijos
                // Ventaja: mantiene un orden más coherente de los hijos
                if (nodoHermano == null) {
                    nodoPadre.setIzquierdo(nodoNuevo);
                } else {
                    Nodo<T> ultimoHermano = null;

                    while (nodoHermano != null) {
                        ultimoHermano = nodoHermano;
                        nodoHermano = nodoHermano.getDerecho();
                    }

                    ultimoHermano.setDerecho(nodoNuevo);
                }

                resultado = true;
            }
        }

        return resultado;
    }

    /**
     * Busca un nodo en forma recursiva con el elemento dado a partir del nodo raíz del árbol.
     *
     * @param elemento el elemento a buscar
     * @return el nodo si el elemento fue encontrado, nulo en caso contrario
     */
    private Nodo<T> buscarNodo(T elemento) {
        return buscarNodo(elemento, raiz);
    }

    /**
     * Busca el nodo del elemento dado a partir de un nodo en particular.
     *
     * @param elemento el elemento a buscar
     * @param nodo el nodo desde donde buscar el elemento
     * @return el nodo si el elemento fue encontrado, nulo en caso contrario
     */
    private Nodo<T> buscarNodo(T elemento, Nodo<T> nodo) {
        Nodo<T> buscado = null;

        if (nodo != null) {
            if (((Object) nodo.getElemento()).equals(elemento)) {
                buscado = nodo;
            } else {
                // Buscar en los hijos del nodo actual
                buscado = buscarNodo(elemento, nodo.getIzquierdo());

                // Si no fue encontrado, buscar en los hermanos
                // (y en los hijos de los hermanos, en forma recursiva)
                if (buscado == null) {
                    Nodo<T> hermano = nodo.getDerecho();

                    while (hermano != null && buscado == null) {
                        if (((Object) hermano.getElemento()).equals(elemento)) {
                            buscado = hermano;
                        } else {
                            buscado = buscarNodo(elemento, hermano.getIzquierdo());
                            hermano = hermano.getDerecho();
                        }
                    }
                }
            }
        }

        return buscado;
    }

    /**
     * Devuelve verdadero si el árbol no tiene elementos, de lo contrario devuelve falso.
     *
     * @return verdadero si es vacío, falso en caso contrario
     */
    public boolean esVacio() {
        return raiz == null;
    }

    /**
     * Determina si el elemento dado pertenece al árbol.
     *
     * @param elemento el elemento a buscar
     * @return verdadero si el elemento existe, falso en caso contrario
     */
    public boolean pertenece(T elemento) {
        return buscarNodo(elemento) != null;
    }

    /**
     * Devuelve la altura del árbol.
     *
     * @return la altura del arbol
     */
    public int altura() {
        return altura(raiz);
    }

    /**
     * Obtiene la altura del sub-árbol correspondiente a un nodo en particular.
     *
     * @param nodo el nodo del cual calcular su altura
     * @return la altura del nodo
     */
    private int altura(Nodo<T> nodo) {
        int altura = 0, maxima = 0;

        if (nodo != null) {
            Nodo<T> hijo = nodo.getIzquierdo(), hermano = nodo.getDerecho();

            // Calcular la altura del sub-árbol correspondiente al primer hijo
            if (hijo != null) {
                altura = 1 + altura(hijo);
                maxima = altura;
            }

            // Calcular la altura del sub-árbol correspondiente a cada hijo de
            // los hermanos, y comparar con la altura calculada anteriormente.
            // La altura estará dada por la máxima altura de cada sub-árbol
            while (hermano != null) {
                hijo = hermano.getIzquierdo();

                if (hijo != null) {
                    altura = 1 + altura(hijo);

                    if (altura > maxima) {
                        maxima = altura;
                    }
                }

                hermano = hermano.getDerecho();
            }
        }

        return maxima;
    }

    /**
     * Devuelve el nivel del elemento dado, o -1 si el elemento no existe.
     *
     * @param elemento el elemento a calcular el nivel
     * @return el nivel del elemento si fue encontrado, -1 en caso contrario
     */
    public int nivel(T elemento) {
        return nivel(elemento, raiz, 0);
    }

    /**
     * Obtiene el nivel del elemento dado, a partir de un nodo y su nivel.
     *
     * @param elemento el elemento a calcular el nivel
     * @param nodo el nodo desde donde calcular el nivel
     * @param nivelActual el nivel actual
     * @return el nivel del elemento si fue encontrado, -1 en caso contrario
     */
    private int nivel(T elemento, Nodo<T> nodo, int nivelActual) {
        int nivel = -1;

        if (nodo != null) {
            if (((Object) nodo.getElemento()).equals(elemento)) {
                nivel = nivelActual;
            } else {
                Nodo<T> hijo = nodo.getIzquierdo(), hermano = nodo.getDerecho();

                if (hijo != null) {
                    nivel = nivel(elemento, hijo, nivelActual + 1);
                }

                while (hermano != null && nivel < 0) {
                    if (((Object) hermano.getElemento()).equals(elemento)) {
                        nivel = nivelActual;
                    } else {
                        nivel = nivel(elemento, hermano.getIzquierdo(), nivelActual + 1);
                        hermano = hermano.getDerecho();
                    }
                }
            }
        }

        return nivel;
    }

    /**
     * Devuelve el elemento padre de un elemento dado (si existe el elemento).
     *
     * @param elemento el elemento
     * @return el elemento padre si fue encontrado, nulo en caso contrario
     */
    public T padre(T elemento) {
        Nodo<T> padre = padre(elemento, raiz, null);

        return padre == null ? null : padre.getElemento();
    }

    /**
     * Obtiene el padre del elemento dado, a partir de un nodo y su padre.
     *
     * @param elemento el elemento
     * @param nodo el nodo desde donde buscar el padre
     * @param padre el nodo padre
     * @return el elemento padre si fue encontrado, nulo en caso contrario
     */
    private Nodo<T> padre(T elemento, Nodo<T> nodo, Nodo<T> padre) {
        Nodo<T> buscado = null;

        if (nodo != null) {
            if (((Object) nodo.getElemento()).equals(elemento)) {
                buscado = padre;
            } else {
                Nodo<T> hijo = nodo.getIzquierdo(), hermano = nodo.getDerecho();

                if (hijo != null) {
                    buscado = padre(elemento, hijo, nodo);
                }

                while (hermano != null && buscado == null) {
                    // Búsqueda recursiva en los hermanos del nodo
                    buscado = padre(elemento, hermano, padre);
                    hermano = hermano.getDerecho();
                    // Búsqueda iterativa en los hermanos del nodo
//                    if (((Object)hermano.getElemento()).equals(elemento)) {
//                        buscado = padre;
//                    } else {
//                        buscado = padre(elemento,
//                                        hermano.getIzquierdo(),
//                                        hermano);
//                        hermano = hermano.getDerecho();
//                    }
                }
            }
        }

        return buscado;
    }

    /**
     * Devuelve una lista de ancestros de un elemento, si éste pertenece al árbol, de lo contrario una lista vacía.
     *
     * @param elemento el elemento del cual buscar sus ancestros
     * @return la lista de ancestros
     */
    public Lista<T> ancestros(T elemento) {
        Lista<T> ancestros = new Lista<T>();
        ancestros(elemento, raiz, ancestros);

        return ancestros;
    }

    /**
     * Busca los ancestros de un elemento a partir de un nodo dado.
     *
     * @param elemento el elemento del cual buscar sus ancestros
     * @param nodo el nodo desde donde buscar los ancestros
     * @param lista la lista de ancestros
     * @return la lista de ancestros
     */
    private boolean ancestros(T elemento, Nodo<T> nodo, Lista<T> lista) {
        boolean encontrado = false;

        if (nodo != null) {
            T elementoNodo = nodo.getElemento();

            if (((Object) elementoNodo).equals(elemento)) {
                encontrado = true;
            } else {
                encontrado = ancestros(elemento, nodo.getIzquierdo(), lista);

                if (encontrado) {
                    lista.insertar(elementoNodo, 1);
                } else {
                    Nodo<T> hermano = nodo.getDerecho();

                    while (hermano != null && !encontrado) {
                        encontrado = ancestros(elemento, hermano, lista);
                        hermano = hermano.getDerecho();
                    }
                }
            }
        }

        return encontrado;
    }

    /**
     * Devuelve una lista en preorden con los elementos del árbol.
     *
     * @return la lista en preorden
     */
    public Lista<T> listarPreorden() {
        Lista<T> lista = new Lista<T>();
        preorden(raiz, lista);

        return lista;
    }

    /**
     * Inserta los elementos en preorden en la lista dada, a partir de un nodo.
     *
     * @param nodo el nodo desde donde listar
     * @param lista la lista en preorden
     */
    private void preorden(Nodo<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            Nodo<T> hijo = nodo.getIzquierdo();
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);

            while (hijo != null) {
                preorden(hijo, lista);
                hijo = hijo.getDerecho();
            }
        }
    }

    /**
     * Devuelve una lista en inorden con los elementos del árbol.
     *
     * @return la lista en inorden
     */
    public Lista<T> listarInorden() {
        Lista<T> lista = new Lista<T>();
        inorden(raiz, lista);

        return lista;
    }

    /**
     * Inserta los elementos en inorden en la lista dada, a partir de un nodo.
     *
     * @param nodo el nodo desde donde listar
     * @param lista la lista en inorden
     */
    private void inorden(Nodo<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            Nodo<T> hijo = nodo.getIzquierdo();

            if (hijo != null) {
                inorden(hijo, lista);
                hijo = hijo.getDerecho();
            }

            lista.insertar(nodo.getElemento(), lista.longitud() + 1);

            while (hijo != null) {
                inorden(hijo, lista);
                hijo = hijo.getDerecho();
            }
        }
    }

    /**
     * Devuelve una lista en posorden con los elementos del árbol.
     *
     * @return la lista en posorden
     */
    public Lista<T> listarPosorden() {
        Lista<T> lista = new Lista<T>();
        posorden(raiz, lista);

        return lista;
    }

    /**
     * Inserta los elementos en posorden en la lista dada, a partir de un nodo.
     *
     * @param nodo el nodo desde donde listar
     * @param lista la lista en posorden
     */
    private void posorden(Nodo<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            Nodo<T> hijo = nodo.getIzquierdo();

            while (hijo != null) {
                posorden(hijo, lista);
                hijo = hijo.getDerecho();
            }

            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
        }
    }

    /**
     * Devuelve una lista por niveles (de izquierda a derecha) con los elementos del árbol.
     *
     * @return la lista de elementos por nivel
     */
    public Lista<T> listarNiveles() {
        Lista<T> lista = new Lista<T>();

        if (raiz != null) {
            Cola<Nodo<T>> cola = new Cola<Nodo<T>>();
            Nodo<T> nodo, primerHijo, hijoSiguiente;
            cola.poner(raiz);

            while (!cola.esVacia()) {
                nodo = cola.obtenerFrente();
                primerHijo = nodo.getIzquierdo();
                cola.sacar();
                lista.insertar(nodo.getElemento(), lista.longitud() + 1);

                if (primerHijo != null) {
                    cola.poner(primerHijo);
                    hijoSiguiente = primerHijo.getDerecho();

                    while (hijoSiguiente != null) {
                        cola.poner(hijoSiguiente);
                        hijoSiguiente = hijoSiguiente.getDerecho();
                    }
                }
            }
        }

        return lista;
    }

    /**
     * Devuelve una copia exacta del árbol.
     *
     * @return la copia del árbol
     */
    @Override
    public ArbolGenerico<T> clone() {
        ArbolGenerico<T> clon = new ArbolGenerico<T>();

        if (raiz != null) {
            clon.raiz = new Nodo<T>(raiz.getElemento());
            clonarNodo(raiz, clon.raiz);
        }

        return clon;
    }

    /**
     * Copia el árbol desde un nodo dado.
     *
     * @param nodo el nodo desde donde clonar
     * @param nodoClon el nodo clon
     */
    private void clonarNodo(Nodo<T> nodo, Nodo<T> nodoClon) {
        if (nodo != null) {
            Nodo<T> hijo = nodo.getIzquierdo();

            if (hijo != null) {
                Nodo<T> hijoClon = new Nodo<T>(hijo.getElemento());
                nodoClon.setIzquierdo(hijoClon);
                clonarNodo(hijo, hijoClon);
                hijo = hijo.getDerecho();

                while (hijo != null) {
                    hijoClon.setDerecho(new Nodo<T>(hijo.getElemento()));
                    hijoClon = hijoClon.getDerecho();
                    clonarNodo(hijo, hijoClon);
                    hijo = hijo.getDerecho();
                }
            }
        }
    }

    /**
     * Devuelve la representación del árbol en forma de cadena. Por defecto, éste método equivale a llamar
     * listarNiveles.toString().
     */
    @Override
    public String toString() {
        return listarNiveles().toString();
    }
}
