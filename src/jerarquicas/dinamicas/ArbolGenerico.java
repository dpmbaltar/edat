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
            //Nodo<T> nodoPadre = buscarNodo(elementoPadre);
            Busqueda<T> busqueda = buscar(elementoPadre);
            Nodo<T> nodoPadre = busqueda != null ? busqueda.getNodo() : null;

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
     * @deprecated usar buscar(T)
     * @param elemento
     * @return
     */
    @Deprecated
    private Nodo<T> buscarNodo(T elemento) {
        return buscarNodo(elemento, raiz);
    }

    /**
     * Busca un nodo en forma recursiva con el elemento a partir de un nodo
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
    
    private Busqueda<T> buscar(T elemento) {
        return buscar(elemento, raiz, null, new Lista<T>(), 0);
    }
    
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
        //return buscarNivel(elemento, raiz, 0);
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

    public Lista<T> listarPreorden() {
        Lista<T> lista = new Lista<T>();
        preorden(raiz, lista);
        return lista;
    }

    private void preorden(Nodo<T> nodo, Lista<T> lista) {
        // FIXME
        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo(),
                    derecho = nodo.getDerecho();

            lista.insertar(nodo.getElemento(), lista.longitud() + 1);

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
