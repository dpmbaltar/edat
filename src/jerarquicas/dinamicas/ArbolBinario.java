package jerarquicas.dinamicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 * Implementación dinámica de Árbol Binaro.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class ArbolBinario<T> {

    public enum Posicion { IZQUIERDO, DERECHO, RAIZ }

    /**
     * El nodo raíz del árbol.
     */
    private Nodo<T> raiz;

    /**
     * Crea y devuelve un árbol binario vacío.
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
        boolean resultado = false;

        switch (posicion) {
            case RAIZ:
                if (raiz == null) {
                    raiz = new Nodo<T>(elemento);
                    resultado = true;
                }
                break;
            default:
                Nodo<T> nodoPadre = buscarNodo(padre);
                if (nodoPadre != null) {
                    Nodo<T> nodoNuevo = new Nodo<T>(elemento, nodoPadre);
                    resultado = true;
                    
                    switch (posicion) {
                        case IZQUIERDO:
                            nodoPadre.setIzquierdo(nodoNuevo);
                        break;
                        case DERECHO:
                            nodoPadre.setDerecho(nodoNuevo);
                            break;
                    }
                }
        }

        return resultado;
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
     * Busca y retorna el nodo del elemento especificado a partir del nodo raíz.
     * 
     * @param elemento
     * @return
     */
    protected Nodo<T> buscarNodo(T elemento) {
        return buscarNodo(elemento, raiz);
    }

    /**
     * Busca y retorna el nodo del elemento especificado a partir de un nodo
     * en particular.
     * 
     * @param elemento
     * @param nodo
     * @return
     */
    protected Nodo<T> buscarNodo(T elemento, Nodo<T> nodo) {
        Nodo<T> buscado = null;

        if (nodo != null) {
            if (((Object) nodo.getElemento()).equals(elemento)) {
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
     * Devuelve verdadero si el árbol no tiene elementos, de lo contrario
     * devuelve falso.
     * 
     * @return
     */
    public boolean esVacio() {
        return raiz == null;
    }

    /**
     * Elimina todos los nodos del árbol.
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
        return alturaNodo(raiz);
    }

    /**
     * Calcula la altura del sub-árbol correspondiente a un nodo del árbol.
     * 
     * @param nodo
     * @param nivel
     * @param altura
     * @return
     */
    private int alturaNodo(Nodo<T> nodo) {
        int altura = 0, maxima = 0;

        if (nodo != null) {
            Nodo<T> hijoIzquierdo = nodo.getIzquierdo(),
                    hijoDerecho = nodo.getDerecho();

            if (hijoIzquierdo != null) {
                altura = 1 + alturaNodo(hijoIzquierdo);
                maxima = altura;
            }

            if (hijoDerecho != null) {
                altura = 1 + alturaNodo(hijoDerecho);
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
        int nivel = -1;

        if (raiz != null) {
            Nodo<T> nodo = buscarNodo(elemento);
            while (nodo != null) {
                nodo = nodo.getPadre();
                nivel++;
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
        T padre = null;

        if (raiz != null) {
            Nodo<T> nodo = buscarNodo(elemento);
            if (nodo != null) {
                Nodo<T> nodoPadre = nodo.getPadre();
                padre = nodoPadre == null ? null : nodoPadre.getElemento();
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

    private void preorden(Nodo<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            Nodo<T> hijoIzquierdo = nodo.getIzquierdo(),
                    hijoDerecho = nodo.getDerecho();

            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            preorden(hijoIzquierdo, lista);
            preorden(hijoDerecho, lista);
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

    private void inorden(Nodo<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            Nodo<T> hijoIzquierdo = nodo.getIzquierdo(),
                    hijoDerecho = nodo.getDerecho();

            inorden(hijoIzquierdo, lista);
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            inorden(hijoDerecho, lista);
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

    private void posorden(Nodo<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            Nodo<T> hijoIzquierdo = nodo.getIzquierdo(),
                    hijoDerecho = nodo.getDerecho();

            posorden(hijoIzquierdo, lista);
            posorden(hijoDerecho, lista);
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
    public String toString() {
        return listarNiveles().toString();
    }

    /**
     * Suma todas las ramas a partir de un nodo dado, en forma recursiva.
     * Método implementado en el 1er parcial.
     * 
     * @param nodo
     * @param suma
     * @return
     */
    private int sumarRamas(Nodo<T> nodo, int suma) {
        int resultado = 0;
        Nodo<T> izquierdo, derecho;

        if (nodo != null) {
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();
            suma += (Integer) nodo.getElemento();

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
     * Suma todas las ramas.
     * Método implementado en el 1er parcial.
     * 
     * @return
     */
    public int sumarRamas() {
        return sumarRamas(raiz, 0);
    }
}
