package jerarquicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 * Implementación de Árbol Binaro.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class ArbolBinario<T> {

    /**
     * Enumeración de posibles posiciones al insertar un elemento.
     */
    public enum Posicion {
        IZQUIERDO,
        DERECHO,
        RAIZ
    }

    /**
     * El nodo raíz del árbol.
     */
    private Nodo<T> raiz;

    /**
     * Constructor de árbol binario vacío.
     */
    public ArbolBinario() {
        raiz = null;
    }

    /**
     * Inserta el elemento dado como raíz del árbol, o como hijo
     * izquierdo/derecho del padre especificado, si éste existe en el árbol.
     *
     * @param elemento
     * @param padre
     * @param posicion
     * @return
     */
    public boolean insertar(T elemento, T padre, Posicion posicion) {
        boolean exito = false;

        if (raiz == null && posicion == Posicion.RAIZ) {
            raiz = new Nodo<T>(elemento);
            exito = true;
        } else {
            Nodo<T> nodoPadre = obtenerNodo(padre);
            if (nodoPadre != null) {
                if (posicion == Posicion.IZQUIERDO) {
                    nodoPadre.setIzquierdo(new Nodo<T>(elemento, nodoPadre));
                } else if (posicion == Posicion.DERECHO) {
                    nodoPadre.setDerecho(new Nodo<T>(elemento, nodoPadre));
                }

                exito = true;
            }
        }

        return exito;
    }

    /**
     * Inserta el elemento dado como raíz del árbol.
     *
     * @param elemento
     * @return
     */
    public boolean insertarRaiz(T elemento) {
        return insertar(elemento, null, Posicion.RAIZ);
    }

    /**
     * Inserta el elemento dado como hijo izquierdo del padre especificado.
     *
     * @param elemento
     * @param padre
     * @return
     */
    public boolean insertarIzquierdo(T elemento, T padre) {
        return insertar(elemento, padre, Posicion.IZQUIERDO);
    }

    /**
     * Inserta el elemento dado como hijo derecho del padre especificado.
     *
     * @param elemento
     * @param padre
     * @return
     */
    public boolean insertarDerecho(T elemento, T padre) {
        return insertar(elemento, padre, Posicion.DERECHO);
    }

    /**
     * Obtiene el nodo del elemento dado a partir del nodo raíz.
     *
     * @param elemento
     * @return
     */
    private Nodo<T> obtenerNodo(T elemento) {
        return obtenerNodo(elemento, raiz);
    }

    /**
     * Obtiene el nodo del elemento dado a partir de un nodo en particular.
     *
     * @param elemento
     * @param nodo
     * @return
     */
    private Nodo<T> obtenerNodo(T elemento, Nodo<T> nodo) {
        Nodo<T> buscado = null;

        if (nodo != null) {
            if (nodo.getElemento().equals(elemento)) {
                buscado = nodo;
            } else {
                buscado = obtenerNodo(elemento, nodo.getIzquierdo());
                if (buscado == null) {
                    buscado = obtenerNodo(elemento, nodo.getDerecho());
                }
            }
        }

        return buscado;
    }

    /**
     * Devuelve verdadero si el árbol no tiene elementos, de lo contrario
     * devuelve falso.
     *
     * @return
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
     * @return
     */
    public int altura() {
        return altura(raiz);
    }

    /**
     * Obtiene la altura del árbol correspondiente a un nodo en particular.
     *
     * @param nodo
     * @return
     */
    private int altura(Nodo<T> nodo) {
        int altura = 0,
            maxima = 0;

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
     * @param elemento
     * @return
     */
    public int nivel(T elemento) {
        return nivel(elemento, raiz, 0);
    }

    /**
     * Obtiene el nivel del elemento dado, a partir de un nodo y su nivel.
     *
     * @param elemento
     * @param nodo
     * @param nivelActual
     * @return
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
     * @param elemento
     * @return
     */
    public T padre(T elemento) {
        return padre(elemento, raiz, null);
    }

    /**
     * Obtiene el padre del elemento dado, a partir de un nodo y su padre.
     *
     * @param elemento
     * @param nodo
     * @param padreActual
     * @return
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
     * @return
     */
    public Lista<T> listarPreorden() {
        Lista<T> lista = new Lista<T>();
        preorden(raiz, lista);
        return lista;
    }

    /**
     * Inserta los elementos en preorden en la lista dada, a partir de un nodo.
     *
     * @param nodo
     * @param lista
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
     * @return
     */
    public Lista<T> listarInorden() {
        Lista<T> lista = new Lista<T>();
        inorden(raiz, lista);
        return lista;
    }

    /**
     * Inserta los elementos en inorden en la lista dada, a partir de un nodo.
     *
     * @param nodo
     * @param lista
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
     * @return
     */
    public Lista<T> listarPosorden() {
        Lista<T> lista = new Lista<T>();
        posorden(raiz, lista);
        return lista;
    }

    /**
     * Inserta los elementos en posorden en la lista dada, a partir de un nodo.
     *
     * @param nodo
     * @param lista
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
     * @return
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
     * @return
     */
    public ArbolBinario<T> clonar() {
        ArbolBinario<T> clon = new ArbolBinario<T>();
        clonar(raiz, clon);
        return clon;
    }

    /**
     * Inserta los elementos en el árbol dado, a partir de un nodo.
     *
     * @param nodo
     * @param arbol
     */
    private void clonar(Nodo<T> nodo, ArbolBinario<T> arbol) {
        if (nodo != null) {
            Nodo<T> hijoIzquierdo = nodo.getIzquierdo(),
                    hijoDerecho = nodo.getDerecho();

            if (arbol.esVacio()) {
                arbol.insertarRaiz(nodo.getElemento());
            }

            if (hijoIzquierdo != null) {
                arbol.insertarIzquierdo(hijoIzquierdo.getElemento(),
                                        nodo.getElemento());
                clonar(hijoIzquierdo, arbol);
            }

            if (hijoDerecho != null) {
                arbol.insertarDerecho(hijoDerecho.getElemento(),
                                      nodo.getElemento());
                clonar(hijoDerecho, arbol);
            }
        }
    }

    /**
     * Devuelve la representación del árbol en forma de cadena.
     * Por defecto, éste método equivale a llamar listarNiveles.toString().
     */
    @Override
    public String toString() {
        return listarNiveles().toString();
    }

    /**
     * Suma todas las ramas a partir de un nodo dado, en forma recursiva.
     * Método implementado en el 1er parcial (mal implementado; corregido).
     *
     * Corrección: no era necesario utilizar el parámetro auxiliar int suma, ya
     * que al hacerlo se esta sumando de más. El método sólo debe recorrer el
     * árbol recursivamente e ir sumando el valor de cada nodo con el valor de
     * los nodos hijos, si es que existen.
     * En caso de que sólo se quiera sumar los elementos de los nodos interiores
     * (aquellos con al menos un hijo) se debe agregar la condición de que
     * exista al menos un hijo para sumar el valor del nodo. Por ejemplo:
     *
     * Nodo<T> izquierdo, derecho;
     * izquierdo = nodo.getIzquierdo();
     * derecho = nodo.getDerecho();
     * if (izquierdo != null || derecho != null) {
     *     resultado += (Integer)nodo.getElemento();
     *     resultado += sumarRamas(izquierdo);
     *     resultado += sumarRamas(derecho);
     * }
     *
     * @param nodo
     * @return
     */
    private int sumarRamas(Nodo<T> nodo) {//, int suma) {
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
            resultado += (Integer)nodo.getElemento();
            resultado += sumarRamas(nodo.getIzquierdo());
            resultado += sumarRamas(nodo.getDerecho());
        }

        return resultado;
    }

    /**
     * Suma todas las ramas.
     * Método implementado en el 1er parcial.
     *
     * @return
     */
    public int sumarRamas() {
        return sumarRamas(raiz);
    }

    /**
     * Ejercicio 3.1, Simulacro, Parcial 1
     *
     * @param l1
     * @return
     */
    public boolean verificarPatron(Lista<T> l1) {
        return verificarPatron(raiz, l1, 1);
    }

    public boolean verificarPatron(Nodo<T> nodo, Lista<T> l1, int posicion) {
        boolean exito = false;
        Nodo<T> hijoIzquierdo, hijoDerecho;
        T elementoNodo, elementoLista = l1.recuperar(posicion);

        if (nodo != null) {
            hijoIzquierdo = nodo.getIzquierdo();
            hijoDerecho = nodo.getDerecho();
            elementoNodo = nodo.getElemento();
            if (elementoLista == null) {
                exito = true;
            } else if (elementoNodo.equals(elementoLista)) {
                posicion++;
                if (posicion > l1.longitud()) {
                    exito = true;
                } else {
                    exito = verificarPatron(hijoIzquierdo, l1, posicion);
                    if (!exito) {
                        exito = verificarPatron(hijoDerecho, l1, posicion);
                    }
                }
            }
        }

        return exito;
    }

    /**
     * Ejercicio 3.2, Simulacro, Parcial 1
     *
     * @return
     */
    public Lista<T> frontera() {
        Lista<T> lista = new Lista<T>();
        frontera(raiz, lista);
        return lista;
    }

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
}
