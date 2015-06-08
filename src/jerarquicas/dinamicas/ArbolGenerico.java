package jerarquicas.dinamicas;

import lineales.dinamicas.Cola;
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
            Busqueda<T> busqueda = buscar(elementoPadre);
            Nodo<T> nodoPadre = busqueda != null ? busqueda.getNodo() : null;

            if (nodoPadre != null) {
                Nodo<T> nodoNuevo = new Nodo<T>(elemento),
                        nodoHermano = nodoPadre.getIzquierdo();

                /*// Agregar nuevo nodo al principio
                if (nodoHermano != null) {
                    nodoNuevo.setDerecho(nodoHermano);
                }

                nodoPadre.setIzquierdo(nodoNuevo);*/

                // Agregar nuevo nodo al final
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
     * Busca un nodo en forma recursiva con el elemento dado a partir del nodo
     * raíz del árbol.
     * 
     * @deprecated usar buscar(T)
     * @param elemento
     * @return
     */
    @Deprecated
    private Nodo<T> buscarNodo(T elemento) {
        return buscarNodo(elemento, raiz);
    }

    /**
     * Busca un nodo en forma recursiva con el elemento dado a partir de un nodo
     * específico.
     * 
     * @deprecated usar buscar(T, Nodo<T>, Nodo<T>, int, Lista<T>)
     * @param elemento
     * @param nodo
     * @return
     */
    @Deprecated
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
     * Busca un nodo en forma recursiva con el elemento dado a partir del nodo
     * raíz, y devuelve información acerca de la ubicación del nodo: como el
     * nodo padre, nivel y lista de ancestros.
     * 
     * @param elemento
     * @return
     */
    private Busqueda<T> buscar(T elemento) {
        return buscar(elemento, raiz, null, new Lista<T>(), 0);
    }

    /**
     * Busca un nodo en forma recursiva con el elemento dado a partir de un nodo
     * específico, y devuelve información acerca de la ubicación del nodo: como
     * el nodo padre, nivel y lista de ancestros.
     * 
     * @param elemento
     * @return
     */
    private Busqueda<T> buscar(T elemento,
                               Nodo<T> nodo,
                               Nodo<T> padre,
                               Lista<T> ancestros,
                               int nivel) {
        Busqueda<T> busqueda = null;

        if (nodo != null) {
            if (((Object) nodo.getElemento()).equals(elemento)) {
                busqueda = new Busqueda<T>(nivel, nodo, padre, ancestros);
            } else {
                ancestros.insertar(nodo.getElemento(), 1);

                // Buscar en los hijos del nodo actual
                busqueda = buscar(elemento,
                                  nodo.getIzquierdo(),
                                  nodo,
                                  ancestros.clonar(),
                                  nivel + 1);

                // Si no fue encontrado, buscar en los hermanos
                // (y en los hijos de los hermanos, en forma recursiva)
                if (busqueda == null) {
                    Nodo<T> hermano = nodo.getDerecho();
                    Lista<T> ancestrosClon;
                    ancestros.eliminar(1);

                    while (hermano != null && busqueda == null) {
                        if (((Object) hermano.getElemento()).equals(elemento)) {
                            busqueda = new Busqueda<T>();
                            busqueda.setNivel(nivel);
                            busqueda.setNodo(hermano);
                            busqueda.setPadre(padre);
                            busqueda.setAncestros(ancestros);
                        } else {
                            ancestrosClon = ancestros.clonar();
                            ancestrosClon.insertar(hermano.getElemento(), 1);
                            busqueda = buscar(elemento,
                                              hermano.getIzquierdo(),
                                              hermano,
                                              ancestrosClon,
                                              nivel + 1);
                            hermano = hermano.getDerecho();
                        }
                    }
                }
            }
        }

        return busqueda;
    }

    private class Busqueda<T> {

        private int nivel;
        private Nodo<T> nodo;
        private Nodo<T> padre;
        private Lista<T> ancestros;

        public Busqueda() {
            this(-1, null, null, null);
        }

        public Busqueda(int nivel,
                        Nodo<T> nodo,
                        Nodo<T> padre,
                        Lista<T> ancestros) {
            this.nivel = nivel;
            this.nodo = nodo;
            this.padre = padre;
            this.ancestros = ancestros;
        }

        public int getNivel() {
            return nivel;
        }

        public void setNivel(int nivel) {
            this.nivel = nivel;
        }

        public Nodo<T> getNodo() {
            return nodo;
        }

        public void setNodo(Nodo<T> nodo) {
            this.nodo = nodo;
        }

        public Nodo<T> getPadre() {
            return padre;
        }

        public void setPadre(Nodo<T> padre) {
            this.padre = padre;
        }

        public Lista<T> getAncestros() {
            return ancestros;
        }

        public void setAncestros(Lista<T> ancestros) {
            this.ancestros = ancestros;
        }
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
        Busqueda<T> busqueda = buscar(elemento);
        return busqueda != null ? busqueda.getNivel() : -1;
    }
    
    /**
     * 
     * @deprecated usar buscar(T, Nodo<T>, Nodo<T>, int, Lista<T>)
     * @param elemento
     * @param nodo
     * @param nivelActual
     * @return
     */
    @Deprecated
    private int nivelNodo(T elemento, Nodo<T> nodo, int nivelActual) {
        int nivel = -1;

        if (nodo != null) {
            if (((Object)nodo.getElemento()).equals(elemento)) {
                nivel = nivelActual;
            } else {
                Nodo<T> hijo = nodo.getIzquierdo(),
                        hermano = nodo.getDerecho();

                if (hijo != null) {
                    nivel = nivelNodo(elemento, hijo, nivelActual + 1);
                }

                while (hermano != null && nivel < 0) {
                    if (((Object)hermano.getElemento()).equals(elemento)) {
                        nivel = nivelActual;
                    } else {
                        nivel = nivelNodo(elemento,
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
        Busqueda<T> busqueda = buscar(elemento);
        Nodo<T> nodoPadre = null;
        T elementoPadre = null;

        if (busqueda != null) {
            nodoPadre = busqueda.getPadre();
            if (nodoPadre != null) {
                elementoPadre = nodoPadre.getElemento();
            }
        }

        return elementoPadre;
    }

    /**
     * Devuelve una lista con los ancestros de un elemento dado, si éste
     * pertenece al árbol, de lo contrario una lista vacía.
     * 
     * @param elemento
     * @return
     */
    public Lista<T> ancestros(T elemento) {
        Busqueda<T> busqueda = buscar(elemento);
        Lista<T> ancestros;

        if (busqueda != null) {
            ancestros = busqueda.getAncestros();
        } else {
            ancestros = new Lista<T>();
        }

        return ancestros;
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
            Nodo<T> primerHijo = nodo.getIzquierdo(),
                    hijoSiguiente = null;

            lista.insertar(nodo.getElemento(), lista.longitud() + 1);

            if (primerHijo != null) {
                preorden(primerHijo, lista);
                hijoSiguiente = primerHijo.getDerecho();
            }

            while (hijoSiguiente != null) {
                preorden(hijoSiguiente, lista);
                hijoSiguiente = hijoSiguiente.getDerecho();
            }
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
            Nodo<T> primerHijo = nodo.getIzquierdo(),
                    hijoSiguiente = null;

            if (primerHijo != null) {
                inorden(primerHijo, lista);
                hijoSiguiente = primerHijo.getDerecho();
            }
            
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);

            while (hijoSiguiente != null) {
                inorden(hijoSiguiente, lista);
                hijoSiguiente = hijoSiguiente.getDerecho();
            }
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
            Nodo<T> primerHijo = nodo.getIzquierdo(),
                    hijoSiguiente = null;

            if (primerHijo != null) {
                posorden(primerHijo, lista);
                hijoSiguiente = primerHijo.getDerecho();
            }

            while (hijoSiguiente != null) {
                posorden(hijoSiguiente, lista);
                hijoSiguiente = hijoSiguiente.getDerecho();
            }

            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
        }
    }

    /**
     * Devuelve una lista por niveles (de izquierda a derecha) con los elementos
     * del árbol.
     * 
     * @return
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
     * @return
     */
    public ArbolGenerico<T> clonar() {
        ArbolGenerico<T> clon = new ArbolGenerico<T>();
        clonar(raiz, null, clon);
        return clon;
    }

    private void clonar(Nodo<T> hijo, Nodo<T> padre, ArbolGenerico<T> arbol) {
        while (hijo != null) {
            arbol.insertar(hijo.getElemento(),
                           padre == null ? null : padre.getElemento());
            clonar(hijo.getIzquierdo(), hijo, arbol);
            hijo = hijo.getDerecho();
        }
    }

    /**
     * Devuelve la representación del árbol en forma de cadena.
     * Por defecto, éste método equivale a llamar listarNiveles.toString().
     */
    public String toString() {
        return listarNiveles().toString();
    }
}
