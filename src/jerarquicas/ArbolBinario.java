package jerarquicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 * Implementación de Árbol Binaro.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class ArbolBinario<T> {

    /**
     * Enumeración de posibles posiciones al insertar un elemento.
     */
    public enum Posicion {

        /**
         * Indica la posición izquierda.
         */
        IZQUIERDO,

        /**
         * Indica la posición derecha.
         */
        DERECHO
    }

    /**
     * El nodo raíz del árbol.
     */
    private Nodo<T> raiz;

    /**
     * Constructor vacío.
     */
    public ArbolBinario() {
        raiz = null;
    }

    /**
     * Inserta el elemento dado como raíz del árbol, o como hijo izquierdo/derecho del padre especificado, si éste
     * existe en el árbol.
     *
     * @param elemento el elemento a insertar
     * @param padre el elemento padre
     * @param posicion la posición (izquierda o derecha)
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean insertar(T elemento, T padre, Posicion posicion) {
        boolean resultado = false;

        if (raiz == null) {
            raiz = new Nodo<T>(elemento);
            resultado = true;
        } else {
            Nodo<T> nodoPadre = buscarNodo(padre);

            if (nodoPadre != null) {
                if (posicion == Posicion.IZQUIERDO && nodoPadre.getIzquierdo() == null) {
                    nodoPadre.setIzquierdo(new Nodo<T>(elemento));
                    resultado = true;
                } else if (posicion == Posicion.DERECHO && nodoPadre.getDerecho() == null) {
                    nodoPadre.setDerecho(new Nodo<T>(elemento));
                    resultado = true;
                }
            }
        }

        return resultado;
    }

    /**
     * Inserta el elemento dado como raíz del árbol.
     *
     * @param elemento el elemento a insertar
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean insertarRaiz(T elemento) {
        return insertar(elemento, null, null);
    }

    /**
     * Inserta el elemento dado como hijo izquierdo del padre especificado.
     *
     * @param elemento el elemento a insertar
     * @param padre el elemento padre
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean insertarIzquierdo(T elemento, T padre) {
        return insertar(elemento, padre, Posicion.IZQUIERDO);
    }

    /**
     * Inserta el elemento dado como hijo derecho del padre especificado.
     *
     * @param elemento el elemento a insertar
     * @param padre el elemento padre
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean insertarDerecho(T elemento, T padre) {
        return insertar(elemento, padre, Posicion.DERECHO);
    }

    /**
     * Busca el nodo del elemento dado a partir del nodo raíz.
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
            if (nodo.getElemento().equals(elemento)) {
                buscado = nodo;
            } else {
                buscado = buscarNodo(elemento, nodo.getIzquierdo());

                if (buscado == null) {
                    buscado = buscarNodo(elemento, nodo.getDerecho());
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
     * Elimina todos los elementos del árbol.
     */
    public void vaciar() {
        raiz = null;
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
            Nodo<T> hijoIzquierdo = nodo.getIzquierdo(),
                    hijoDerecho = nodo.getDerecho();

            if (hijoIzquierdo != null) {
                altura = 1 + altura(hijoIzquierdo);

                if (altura > maxima) {
                    maxima = altura;
                }
            }

            if (hijoDerecho != null) {
                altura = 1 + altura(hijoDerecho);

                if (altura > maxima) {
                    maxima = altura;
                }
            }
        }

        return maxima;
    }

    /**
     * Devuelve el nivel del elemento dado.
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
            if (nodo.getElemento().equals(elemento)) {
                nivel = nivelActual;
            } else {
                nivelActual++;
                nivel = nivel(elemento, nodo.getIzquierdo(), nivelActual);

                if (nivel == -1) {
                    nivel = nivel(elemento, nodo.getDerecho(), nivelActual);
                }
            }
        }

        return nivel;
    }

    /**
     * Devuelve el elemento padre del elemento dado.
     *
     * @param elemento el elemento
     * @return el elemento padre si fue encontrado, nulo en caso contrario
     */
    public T padre(T elemento) {
        return padre(elemento, raiz, null);
    }

    /**
     * Obtiene el padre del elemento dado, a partir de un nodo y su padre.
     *
     * @param elemento el elemento
     * @param nodo el nodo desde donde buscar el padre
     * @param padreActual el padre actual
     * @return el elemento padre si fue encontrado, nulo en caso contrario
     */
    private T padre(T elemento, Nodo<T> nodo, T padreActual) {
        T padre = null;

        if (nodo != null) {
            if (nodo.getElemento().equals(elemento)) {
                padre = padreActual;
            } else {
                padreActual = nodo.getElemento();
                padre = padre(elemento, nodo.getIzquierdo(), padreActual);
                if (padre == null) {
                    padre = padre(elemento, nodo.getDerecho(), padreActual);
                }
            }
        }

        return padre;
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
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            preorden(nodo.getIzquierdo(), lista);
            preorden(nodo.getDerecho(), lista);
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
            inorden(nodo.getIzquierdo(), lista);
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            inorden(nodo.getDerecho(), lista);
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
            posorden(nodo.getIzquierdo(), lista);
            posorden(nodo.getDerecho(), lista);
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
        }
    }

    /**
     * Devuelve una lista por niveles con los elementos del árbol.
     *
     * @return la lista en niveles
     */
    public Lista<T> listarNiveles() {
        Lista<T> lista = new Lista<T>();

        if (raiz != null) {
            Nodo<T> nodo, hijoIzquierdo, hijoDerecho;
            Cola<Nodo<T>> cola = new Cola<Nodo<T>>();
            cola.poner(raiz);

            while (!cola.esVacia()) {
                nodo = cola.obtenerFrente();
                cola.sacar();
                lista.insertar(nodo.getElemento(), lista.longitud() + 1);
                hijoIzquierdo = nodo.getIzquierdo();
                hijoDerecho = nodo.getDerecho();

                if (hijoIzquierdo != null) {
                    cola.poner(hijoIzquierdo);
                }

                if (hijoDerecho != null) {
                    cola.poner(hijoDerecho);
                }
            }
        }

        return lista;
    }

    /**
     * Devuelve una copia exacta del árbol.
     *
     * @return una copia del árbol
     */
    public ArbolBinario<T> clonar() {
        ArbolBinario<T> clon = new ArbolBinario<T>();

        if (!esVacio()) {
            clon.raiz = new Nodo<T>(raiz.getElemento());
            clonar(raiz, clon.raiz);
        }

        return clon;
    }

    /**
     * Clona el arbol a partir de un nodo.
     *
     * @param nodo el nodo desde donde clonar
     * @param nodoClon el nodo del árbol clon
     */
    private void clonar(Nodo<T> nodo, Nodo<T> nodoClon) {
        if (nodo != null) {
            Nodo<T> hijoIzquierdo = nodo.getIzquierdo(),
                    hijoDerecho = nodo.getDerecho();

            if (hijoIzquierdo != null) {
                nodoClon.setIzquierdo(new Nodo<T>(hijoIzquierdo.getElemento()));
                clonar(hijoIzquierdo, nodoClon.getIzquierdo());
            }

            if (hijoDerecho != null) {
                nodoClon.setDerecho(new Nodo<T>(hijoDerecho.getElemento()));
                clonar(hijoDerecho, nodoClon.getDerecho());
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

    /**
     * Suma todas las ramas a partir de un nodo dado, en forma recursiva. Método implementado en el 1er parcial (mal
     * implementado; corregido).
     *
     * <p>Corrección: no era necesario utilizar el parámetro auxiliar int suma, ya que al hacerlo se esta sumando de
     * más. El método sólo debe recorrer el árbol recursivamente e ir sumando el valor de cada nodo con el valor de los
     * nodos hijos, si es que existen. En caso de que sólo se quiera sumar los elementos de los nodos interiores
     * (aquellos con al menos un hijo) se debe agregar la condición de que exista al menos un hijo para sumar el valor
     * del nodo. Por ejemplo:</p>
     *
     * <pre>
     * Nodo<T> izquierdo, derecho;
     * izquierdo = nodo.getIzquierdo();
     * derecho = nodo.getDerecho();
     *
     * if (izquierdo != null || derecho != null) {
     *     resultado += (Integer) nodo.getElemento();
     *     resultado += sumarRamas(izquierdo);
     *     resultado += sumarRamas(derecho);
     * }
     * </pre>
     *
     * @param nodo el nodo desde donde sumar las ramas
     * @return la suma de las ramas
     */
    private int sumarRamas(Nodo<T> nodo) {// , int suma) {
        int resultado = 0;
//        Nodo<T> izquierdo, derecho;

        if (nodo != null) {
//            izquierdo = nodo.getIzquierdo();
//            derecho = nodo.getDerecho();
//            suma += (Integer)nodo.getElemento();
//
//            if (izquierdo == null && derecho == null) {
//                resultado = suma;
//            } else {
//                if (izquierdo != null) {
//                    resultado += sumarRamas(izquierdo, suma);
//                }
//                if (derecho != null) {
//                    resultado += sumarRamas(derecho, suma);
//                }
//            }
            resultado += (Integer) nodo.getElemento();
            resultado += sumarRamas(nodo.getIzquierdo());
            resultado += sumarRamas(nodo.getDerecho());
        }

        return resultado;
    }

    /**
     * Suma todas las ramas. Método implementado en el 1er parcial.
     *
     * @return la suma de las ramas
     */
    public int sumarRamas() {
        return sumarRamas(raiz);
    }

    /**
     * Ejercicio 3.1, Simulacro, Parcial 1.
     *
     * @param lista la lista de elementos del patrón
     * @return verdadero si el patrón existe, falso en caso contrario
     */
    public boolean verificarPatron(Lista<T> lista) {
        return lista.esVacia() ? true : verificarPatron(raiz, lista, 1);
    }

    /**
     * Ejercicio 3.1, Simulacro, Parcial 1.
     *
     * @param nodo el nodo desde donde verificar el patrón de elementos
     * @param lista la lista de elementos del patrón
     * @param posicion la posición
     * @return verdadero si el patrón existe, falso en caso contrario
     */
    private boolean verificarPatron(Nodo<T> nodo, Lista<T> lista, int posicion) {
        boolean existe = false;
        Nodo<T> hijoIzquierdo, hijoDerecho;
        T elementoNodo, elementoLista;

        if (nodo != null) {
            elementoNodo = nodo.getElemento();
            elementoLista = lista.recuperar(posicion);

            if (elementoNodo.equals(elementoLista)) {
                posicion++;

                if (posicion > lista.longitud()) { // Camino completo
                    existe = true;
                } else { // Seguir verificando camino en hijos
                    hijoIzquierdo = nodo.getIzquierdo();
                    hijoDerecho = nodo.getDerecho();
                    existe = verificarPatron(hijoIzquierdo, lista, posicion);

                    if (!existe) {
                        existe = verificarPatron(hijoDerecho, lista, posicion);
                    }
                }
            }
        }

        return existe;
    }

    /**
     * Ejercicio 3.2, Simulacro, Parcial 1.
     *
     * @return la lista de los elementos frontera
     */
    public Lista<T> frontera() {
        Lista<T> lista = new Lista<T>();
        frontera(raiz, lista);
        return lista;
    }

    /**
     * Ejercicio 3.2, Simulacro, Parcial 1.
     *
     * @param nodo el nododesde donde recolectar los elementos frontera
     * @param lista la lista con los elementos frontera
     */
    private void frontera(Nodo<T> nodo, Lista<T> lista) {
        Nodo<T> hijoIzquierdo, hijoDerecho;

        if (nodo != null) {
            hijoIzquierdo = nodo.getIzquierdo();
            hijoDerecho = nodo.getDerecho();

            if (hijoIzquierdo == null && hijoDerecho == null) {
                lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            } else {
                if (hijoIzquierdo != null) {
                    frontera(hijoIzquierdo, lista);
                }

                if (hijoDerecho != null) {
                    frontera(hijoDerecho, lista);
                }
            }
        }
    }

    /**
     * Ejercicio 3.3, Simulacro, Parcial 1.
     *
     * @return el clon del árbol con los hijos invertidos
     */
    public ArbolBinario<T> clonarHijosInvertidos() {
        ArbolBinario<T> clon = new ArbolBinario<T>();
        clonarHijosInvertidos(raiz, clon);

        return clon;
    }

    /**
     * Ejercicio 3.3, Simulacro, Parcial 1.
     *
     * @param nodo el nodo desde donde clonar
     * @param arbol el clon del árbol
     */
    private void clonarHijosInvertidos(Nodo<T> nodo, ArbolBinario<T> arbol) {
        if (nodo != null) {
            Nodo<T> hijoIzquierdo = nodo.getIzquierdo(),
                    hijoDerecho = nodo.getDerecho();

            if (arbol.esVacio()) {
                arbol.insertarRaiz(nodo.getElemento());
            }

            if (hijoDerecho != null) {
                arbol.insertarIzquierdo(hijoDerecho.getElemento(), nodo.getElemento());
                clonarHijosInvertidos(hijoDerecho, arbol);
            }

            if (hijoIzquierdo != null) {
                arbol.insertarDerecho(hijoIzquierdo.getElemento(), nodo.getElemento());
                clonarHijosInvertidos(hijoIzquierdo, arbol);
            }
        }
    }

}
