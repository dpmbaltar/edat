package jerarquicas.dinamicas;

import java.util.LinkedList;

import lineales.dinamicas.ListaInt;

/**
 * Implementación dinámica de Árbol Binaro.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class ArbolBinInt {

    private NodoArbolInt raiz;

    public ArbolBinInt() {
        raiz = null;
    }

    /**
     * Busca y retorna el nodo del elemento especificado.
     * 
     * @param elemento
     * @return
     */
    private NodoArbolInt buscarNodo(int elemento) {
        return buscarNodo(elemento, raiz);
    }

    private NodoArbolInt buscarNodo(int elemento, NodoArbolInt nodo) {
        NodoArbolInt buscado = null;

        if (nodo != null) {
            if (nodo.getElemento() == elemento) {
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

    public boolean insertarRaiz(int elemento) {
        return insertar(elemento, 0, 'R');
    }

    /**
     * 
     * @param elementoNuevo
     * @param elementoPadre
     * @param posicion
     * @return
     */
    public boolean insertar(int elementoNuevo, int elementoPadre, char posicion) {
        boolean resultado = false;

        if (esVacio()) {
            raiz = new NodoArbolInt(elementoNuevo);
            resultado = true;
        } else {
            NodoArbolInt padre = buscarNodo(elementoPadre);
            if (padre != null) {
                if (posicion == 'I' && padre.getIzquierdo() == null) {
                    padre.setIzquierdo(new NodoArbolInt(elementoNuevo));
                    resultado = true;
                } else if (posicion == 'D' && padre.getDerecho() == null) {
                    padre.setDerecho(new NodoArbolInt(elementoNuevo));
                    resultado = true;
                }
            }
        }

        return resultado;
    }

    /**
     * 
     * @return
     */
    public boolean esVacio() {
        return (raiz == null);
    }

    /**
     * Elimina todos los nodos del árbol.
     */
    public void vaciar() {
        raiz = null;
    }

    /**
     * 
     * @param nodo
     * @param nivel
     * @param altura
     * @return
     */
    private int calcularAltura(NodoArbolInt nodo, int nivel, int altura) {
        NodoArbolInt nodoIzquierdo = nodo.getIzquierdo(), nodoDerecho = nodo
                .getDerecho();

        if (nodoIzquierdo == null) {
            if (nivel > altura) {
                altura = nivel;
            }
        } else {
            altura = calcularAltura(nodoIzquierdo, (nivel + 1), altura);
        }

        if (nodoDerecho == null) {
            if (nivel > altura) {
                altura = nivel;
            }
        } else {
            altura = calcularAltura(nodoDerecho, (nivel + 1), altura);
        }

        return altura;
    }

    /**
     * 
     * @return
     */
    public int altura() {
        int altura = -1;

        if (!esVacio()) {
            altura = calcularAltura(raiz, 0, 0);
        }

        return altura;
    }

    private int calcularNivel(int elemento, int nivelElemento, int nivelActual,
            NodoArbolInt nodo) {
        if (nodo != null) {
            if (nodo.getElemento() == elemento) {
                nivelElemento = nivelActual;
            } else {
                nivelElemento = calcularNivel(elemento, nivelElemento,
                        (nivelActual + 1), nodo.getIzquierdo());
                if (nivelElemento == -1) {
                    nivelElemento = calcularNivel(elemento, nivelElemento,
                            (nivelActual + 1), nodo.getDerecho());
                }
            }
        }

        return nivelElemento;
    }

    /**
     * 
     * @param elemento
     * @return
     */
    public int nivel(int elemento) {
        int nivel = -1;

        if (!esVacio()) {
            nivel = calcularNivel(elemento, nivel, 0, raiz);
        }

        return nivel;
    }

    private NodoArbolInt buscarPadre(int elemento, NodoArbolInt padre,
            NodoArbolInt hijo) {
        NodoArbolInt padreBuscado = null;

        if (hijo != null) {
            if (hijo.getElemento() == elemento) {
                padreBuscado = padre;
            } else {
                padreBuscado = buscarPadre(elemento, hijo, hijo.getIzquierdo());
                if (padreBuscado == null) {
                    padreBuscado = buscarPadre(elemento, hijo,
                            hijo.getDerecho());
                }
            }
        }

        return padreBuscado;
    }

    public int padre(int elemento) {
        int elementoPadre = 0;

        if (!esVacio()) {
            NodoArbolInt padre = buscarPadre(elemento, null, raiz);
            if (padre != null) {
                elementoPadre = padre.getElemento();
            }
        }

        return elementoPadre;
    }

    /**
     * Completa la lista con los elementos recorridos en pre-orden.
     * 
     * @param nodo
     * @param lista
     */
    private void preorden(NodoArbolInt nodo, ListaInt lista) {
        NodoArbolInt izquierdo = nodo.getIzquierdo(), derecho = nodo
                .getDerecho();

        lista.insertar(nodo.getElemento(), (lista.longitud() + 1));

        if (izquierdo != null) {
            preorden(izquierdo, lista);
        }

        if (derecho != null) {
            preorden(derecho, lista);
        }
    }

    /**
     * Retorna lista de los elementos recorridos en pre-orden.
     * 
     * @return
     */
    public ListaInt listarPreorden() {
        ListaInt lista = new ListaInt();

        if (!esVacio()) {
            preorden(raiz, lista);
        }

        return lista;
    }

    /**
     * Completa la lista con los elementos recorridos en in-orden.
     * 
     * @param nodo
     * @param lista
     * @return
     */
    private void inorden(NodoArbolInt nodo, ListaInt lista) {
        NodoArbolInt izquierdo = nodo.getIzquierdo(), derecho = nodo
                .getDerecho();

        if (izquierdo != null) {
            inorden(izquierdo, lista);
        }

        lista.insertar(nodo.getElemento(), (lista.longitud() + 1));

        if (derecho != null) {
            inorden(derecho, lista);
        }
    }

    /**
     * Retorna lista de los elementos recorridos en in-orden.
     * 
     * @return
     */
    public ListaInt listarInorden() {
        ListaInt lista = new ListaInt();

        if (!esVacio()) {
            inorden(raiz, lista);
        }

        return lista;
    }

    /**
     * Completa la lista con los elementos recorridos en pos-orden.
     * 
     * @param nodo
     * @param lista
     * @return
     */
    private void posorden(NodoArbolInt nodo, ListaInt lista) {
        NodoArbolInt izquierdo = nodo.getIzquierdo(), derecho = nodo
                .getDerecho();

        if (izquierdo != null) {
            posorden(izquierdo, lista);
        }

        if (derecho != null) {
            posorden(derecho, lista);
        }

        lista.insertar(nodo.getElemento(), (lista.longitud() + 1));
    }

    /**
     * Retorna lista de los elementos recorridos en pos-orden.
     * 
     * @return
     */
    public ListaInt listarPosorden() {
        ListaInt lista = new ListaInt();

        if (!esVacio()) {
            posorden(raiz, lista);
        }

        return lista;
    }

    public ListaInt listarNiveles() {
        ListaInt lista = new ListaInt();

        if (!esVacio()) {
            NodoArbolInt nodo, nodoIzquierdo, nodoDerecho;
            LinkedList<NodoArbolInt> cola = new LinkedList<NodoArbolInt>();
            cola.addFirst(raiz);

            while (!cola.isEmpty()) {
                nodo = cola.pollFirst();
                lista.insertar(nodo.getElemento(), (lista.longitud() + 1));
                nodoIzquierdo = nodo.getIzquierdo();
                nodoDerecho = nodo.getDerecho();

                if (nodoIzquierdo != null) {
                    cola.addLast(nodoIzquierdo);
                }

                if (nodoDerecho != null) {
                    cola.addLast(nodoDerecho);
                }
            }
        }

        return lista;
    }

    private void copiar(ArbolBinInt arbol, NodoArbolInt nodo) {
        if (nodo == null) {
            arbol.insertar(raiz.getElemento(), 0, 'R');
            copiar(arbol, raiz);
        } else {
            NodoArbolInt izquierdo = nodo.getIzquierdo(), derecho = nodo
                    .getDerecho();
            int elementoPadre = nodo.getElemento();

            if (izquierdo != null) {
                arbol.insertar(izquierdo.getElemento(), elementoPadre, 'I');
                copiar(arbol, izquierdo);
            }

            if (derecho != null) {
                arbol.insertar(derecho.getElemento(), elementoPadre, 'D');
                copiar(arbol, derecho);
            }
        }
    }

    public ArbolBinInt clonar() {
        ArbolBinInt clon = new ArbolBinInt();

        if (!esVacio()) {
            copiar(clon, null);
        }

        return clon;
    }

    public String toString() {
        return listarNiveles().toString();
    }

    /**
     * Suma todas las ramas a partir de un nodo dado, en forma recursiva. Método
     * implementado en el 1er parcial.
     * 
     * @param nodo
     * @param suma
     * @return
     */
    private int sumarRamas(NodoArbolInt nodo, int suma) {
        int resultado = 0;
        NodoArbolInt izquierdo, derecho;

        if (nodo != null) {
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();
            suma += nodo.getElemento();

            if (izquierdo == null && derecho == null) {
                resultado = suma;
            } else {
                if (izquierdo != null) {
                    resultado += sumarRamas(izquierdo, suma);
                }
                if (derecho != null) {
                    resultado += sumarRamas(derecho, suma);
                }
            }
        }

        return resultado;
    }

    /**
     * Suma todas las ramas. Método implementado en el 1er parcial.
     * 
     * @return
     */
    public int sumarRamas() {
        return sumarRamas(raiz, 0);
    }
}
