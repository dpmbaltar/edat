package jerarquicas.dinamicas;

import lineales.dinamicas.Lista;

/**
 * Implementación dinámica de Árbol Genérico.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class ArbolGenerico<T> {

    /**
     * El nodo raíz del árbol.
     */
    private Nodo<T> raiz;

    /**
     * Crea y devuelve un árbol genérico vacío.
     */
    public ArbolGenerico() {
        raiz = null;
    }

    /**
     * Inserta un nuevo elemento al árbol.
     * 
     * @param elemento
     * @param elementoPadre
     * @return
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

                // Agregar nuevo nodo al principio,
                // si el nodo padre ya tiene hijos
                if (nodoHermano != null) {
                    nodoNuevo.setDerecho(nodoHermano);
                }

                nodoPadre.setIzquierdo(nodoNuevo);
                resultado = true;
            }
        }

        return resultado;
    }

    /**
     * Busca un nodo en forma recursiva con el elemento dado a partir del nodo
     * raíz del árbol.
     * 
     * @param elemento
     * @return
     */
    private Nodo<T> buscarNodo(T elemento) {
        return buscarNodo(elemento, raiz);
    }

    /**
     * Busca un nodo en forma recursiva con el elemento a partir de un nodo
     * específico.
     * 
     * @param elemento
     * @param nodo
     * @return
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
                            buscado = buscarNodo(elemento,
                                    hermano.getIzquierdo());
                            hermano = hermano.getDerecho();
                        }
                    }
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
     * Determina si el elemento dado pertenece al árbol.
     * 
     * @param elemento
     * @return
     */
    public boolean pertenece(T elemento) {
        return buscarNodo(elemento) != null;
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
     * Calcula la altura del sub-árbol correspondiente a un nodo particular.
     * 
     * @param nodo
     * @return
     */
    private int alturaNodo(Nodo<T> nodo) {
        int altura = 0, maxima = 0;

        if (nodo != null) {
            Nodo<T> hijo = nodo.getIzquierdo(), hermano = nodo.getDerecho();

            // Calcular la altura del sub-árbol correspondiente al primer hijo
            if (hijo != null) {
                altura = 1 + alturaNodo(hijo);
                maxima = altura;
            }

            // Calcular la altura del sub-árbol correspondiente a cada hijo de
            // los hermanos, y comparar con la altura calculada anteriormente.
            // La altura estará dada por la máxima altura de cada sub-árbol
            while (hermano != null) {
                hijo = hermano.getIzquierdo();
                if (hijo != null) {
                    altura = 1 + alturaNodo(hijo);
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
     * @param elemento
     * @return
     */
    public int nivel(T elemento) {
        return buscarNivel(elemento, raiz, 0);
    }
    
    private int buscarNivel(T elemento, Nodo<T> nodo, int nivelActual) {
        int nivel = -1;
        
        if (nodo != null) {
            if (((Object)nodo.getElemento()).equals(elemento)) {
                nivel = nivelActual;
            } else {
                Nodo<T> hijo = nodo.getIzquierdo(),
                        hermano = nodo.getDerecho();
                
                if (hijo != null) {
                    nivel = buscarNivel(elemento, hijo, nivelActual + 1);
                }
                
                while (hermano != null && nivel < 0) {
                    if (((Object)hermano.getElemento()).equals(elemento)) {
                        nivel = nivelActual;
                    } else {
                        nivel = buscarNivel(elemento,
                                            hermano.getIzquierdo(),
                                            nivelActual + 1);
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
     * @param elemento
     * @return
     */
    public T padre(T elemento) {
        T padre = null;
        // TODO
        return padre;
    }

    public Lista<T> ancestros(T elemento) {
        Lista<T> lista = new Lista<T>();
        // TODO
        return lista;
    }

    public Lista<T> listarPreorden() {
        Lista<T> lista = new Lista<T>();
        preorden(raiz, lista);
        return lista;
    }

    private void preorden(Nodo<T> nodo, Lista<T> lista) {
        // FIXME
        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo(), derecho = nodo
                    .getDerecho();

            lista.insertar(nodo.getElemento(), (lista.longitud() + 1));

            if (izquierdo != null) {
                preorden(izquierdo, lista);
            }

            while (derecho != null) {
                preorden(derecho, lista);
                derecho = derecho.getDerecho();
            }
        }
    }

    public Lista<T> listarInorden() {
        Lista<T> lista = new Lista<T>();
        // TODO
        return lista;
    }

    public Lista<T> listarPosorden() {
        Lista<T> lista = new Lista<T>();
        // TODO
        return lista;
    }

    public Lista<T> listarNiveles() {
        Lista<T> lista = new Lista<T>();
        niveles(raiz, lista);
        return lista;
    }

    private void niveles(Nodo<T> nodo, Lista<T> lista) {
        // FIXME
        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo(), derecho = nodo
                    .getDerecho(), hijo;

            lista.insertar(nodo.getElemento(), (lista.longitud() + 1));

            while (derecho != null) {
                lista.insertar(derecho.getElemento(), (lista.longitud() + 1));
                hijo = derecho.getIzquierdo();
                if (hijo != null) {
                    niveles(hijo, lista);
                }
                derecho = derecho.getDerecho();
            }

            if (izquierdo != null) {
                niveles(izquierdo, lista);
            }
        }
    }

    public ArbolGenerico<T> clonar() {
        ArbolGenerico<T> clon = new ArbolGenerico<T>();
        // TODO
        return clon;
    }

    /**
     * 
     */
    public String toString() {
        StringBuilder cadena = new StringBuilder("[");
        // TODO
        cadena.append(']');

        return cadena.toString();
    }
}
