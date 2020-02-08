package conjuntistas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Nodo;

/**
 * Implementación de Árbol AVL que acepta elementos duplicados o que tienen el mismo orden.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class ArbolAVLMultiple<T extends Comparable<T>> {

    /**
     * El nodo raíz del árbol.
     */
    protected NodoAVLMultiple<T> raiz;

    /**
     * Constructor vacío.
     */
    public ArbolAVLMultiple() {
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

        if (elemento != null) {
            if (raiz == null) {
                raiz = new NodoAVLMultiple<>(elemento);
                insertado = true;
            } else {
                insertado = insertar(elemento, raiz, null);
            }
        }

        return insertado;
    }

    /**
     * Inserta un elemento al sub-árbol correspondiente al nodo dado.
     *
     * @param elemento el elemento a insertar
     * @param nodo el nodo del sub-árbol izquierdo o derecho
     * @param padre el nodo padre
     * @return siempre verdadero
     */
    private boolean insertar(T elemento, NodoAVLMultiple<T> nodo, NodoAVLMultiple<T> padre) {
        boolean insertado = false;

        if (nodo != null) {
            NodoAVLMultiple<T> izquierdo = nodo.getIzquierdo();
            NodoAVLMultiple<T> derecho = nodo.getDerecho();
            NodoAVLMultiple<T> nuevo;

            // Si el elemento es menor al del nodo, insertar en el sub-árbol izquierdo
            // Si el elemento es mayor al del nodo, insertar en el sub-árbol derecho
            // Si el elemento es igual al del nodo, agrupar con elementos de igual orden
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                if (izquierdo == null) {
                    nuevo = new NodoAVLMultiple<>(elemento);
                    nodo.setIzquierdo(nuevo);
                    nodo.recalcularAltura();
                    insertado = true;
                } else {
                    insertado = insertar(elemento, izquierdo, nodo);
                }
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                if (derecho == null) {
                    nuevo = new NodoAVLMultiple<>(elemento);
                    nodo.setDerecho(nuevo);
                    nodo.recalcularAltura();
                    insertado = true;
                } else {
                    insertado = insertar(elemento, derecho, nodo);
                }
            } else {
                nodo.setEnlace(new Nodo<>(elemento, nodo.getEnlace()));
            }

            // Vuelta de la recursión:
            // Balancear el nodo si es necesario (lo determina el método balancear())
            // Actualizar altura de los nodos padre
            if (insertado) {
                if (padre != null) {
                    padre.recalcularAltura();
                }

                balancear(nodo, padre);
            }
        }

        return true;
    }

    /**
     * Balancea el sub-árbol correspondiente al nodo dado.
     *
     * @param nodo el nodo a balancear
     * @param padre el nodo padre
     */
    private void balancear(NodoAVLMultiple<T> nodo, NodoAVLMultiple<T> padre) {
        if (nodo != null) {
            boolean balanceado = false;
            NodoAVLMultiple<T> nodoHijo;
            NodoAVLMultiple<T> reemplazo = null;
            int balanceHijo;
            int balanceNodo = nodo.balance();

            // Detectar si el nodo está balanceado. De no estarlo, hacer las
            // rotaciones necesarias para que éste quede balanceado
            if (balanceNodo == 2) {
                nodoHijo = nodo.getIzquierdo();
                balanceHijo = nodoHijo.balance();

                if (balanceHijo == 1 || balanceHijo == 0) { // 0 cuando es una eliminación
                    reemplazo = rotarDerecha(nodo);
                } else if (balanceHijo == -1) {
                    reemplazo = rotarIzquierdaDerecha(nodo);
                }

                balanceado = true;
            } else if (balanceNodo == -2) {
                nodoHijo = nodo.getDerecho();
                balanceHijo = nodoHijo.balance();

                if (balanceHijo == 1) {
                    reemplazo = rotarDerechaIzquierda(nodo);
                } else if (balanceHijo == -1 || balanceHijo == 0) { // 0 cuando es una eliminación
                    reemplazo = rotarIzquierda(nodo);
                }

                balanceado = true;
            }

            // Reemplazar el nodo correspondiente al padre, si ha sido rotado
            if (balanceado) {
                if (padre == null) {
                    raiz = reemplazo;
                } else {
                    T elementoPadre = padre.getElemento();
                    T elementoReemplazo = reemplazo.getElemento();

                    if (elementoReemplazo.compareTo(elementoPadre) < 0) {
                        padre.setIzquierdo(reemplazo);
                    } else if (elementoReemplazo.compareTo(elementoPadre) > 0) {
                        padre.setDerecho(reemplazo);
                    }

                    padre.recalcularAltura();
                }
            }
        }
    }

    /**
     * Aplica una rotación simple a la derecha al sub-árbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del sub-árbol.
     *
     * @param nodo el nodo a rotar
     * @return el nodo rotado
     */
    private NodoAVLMultiple<T> rotarDerecha(NodoAVLMultiple<T> nodo) {
        NodoAVLMultiple<T> izquierdo, izquierdoHD;

        izquierdo = nodo.getIzquierdo();
        izquierdoHD = izquierdo == null ? null : izquierdo.getDerecho();
        izquierdo.setDerecho(nodo);
        nodo.setIzquierdo(izquierdoHD);
        nodo.recalcularAltura();
        izquierdo.recalcularAltura();

        return izquierdo;
    }

    /**
     * Aplica una rotación simple a la izquierda al sub-árbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del sub-árbol.
     *
     * @param nodo el nodo a rotar
     * @return el nodo rotado
     */
    private NodoAVLMultiple<T> rotarIzquierda(NodoAVLMultiple<T> nodo) {
        NodoAVLMultiple<T> derecho, derechoHI;

        derecho = nodo.getDerecho();
        derechoHI = derecho == null ? null : derecho.getIzquierdo();
        derecho.setIzquierdo(nodo);
        nodo.setDerecho(derechoHI);
        nodo.recalcularAltura();
        derecho.recalcularAltura();

        return derecho;
    }

    /**
     * Aplica una rotación doble derecha-izquierda al sub-árbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del sub-árbol.
     *
     * @param nodo el nodo a rotar
     * @return el nodo rotado
     */
    private NodoAVLMultiple<T> rotarDerechaIzquierda(NodoAVLMultiple<T> nodo) {
        NodoAVLMultiple<T> rotado = rotarDerecha(nodo.getDerecho());
        nodo.setDerecho(rotado);

        return rotarIzquierda(nodo);
    }

    /**
     * Aplica una rotación doble izquierda-derecha al sub-árbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del sub-árbol.
     *
     * @param nodo el nodo a rotar
     * @return el nodo rotado
     */
    private NodoAVLMultiple<T> rotarIzquierdaDerecha(NodoAVLMultiple<T> nodo) {
        NodoAVLMultiple<T> rotado = rotarIzquierda(nodo.getIzquierdo());
        nodo.setIzquierdo(rotado);

        return rotarDerecha(nodo);
    }

    /**
     * Elimina el elemento del árbol.
     *
     * @param elemento el elemento a eliminar
     * @return verdadero si el elemento fue eliminado, falso en caso contrario
     */
    public boolean eliminar(T elemento) {
        return elemento == null ? false: eliminar(elemento, raiz, null, null);
    }

    /**
     * Elimina un elemento del sub-árbol correspondiente al nodo dado.
     *
     * @param elemento el elemento a eliminar
     * @param nodo el nodo desde donde buscar el elemento a eliminar
     * @return verdadero si el elemento fue eliminado, falso en caso contrario
     */
    private boolean eliminar(T elemento, NodoAVLMultiple<T> nodo, NodoAVLMultiple<T> padre,
            NodoAVLMultiple<T> padreAnterior) {
        boolean eliminado = false;
        boolean agrupado = false;

        if (nodo != null) {
            NodoAVLMultiple<T> izquierdo = nodo.getIzquierdo();
            NodoAVLMultiple<T> derecho = nodo.getDerecho();
            Nodo<T> enlace = nodo.getEnlace();

            // Buscar el elemento a eliminar
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                eliminado = izquierdo == null ? false : eliminar(elemento, izquierdo, nodo, padre);
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                eliminado = derecho == null ? false : eliminar(elemento, derecho, nodo, padre);
            } else if (elemento.equals(nodo.getElemento())) {
                // Elemento encontrado
                // Eliminarlo según los siguientes 4 casos posibles:
                if (enlace != null) { // Caso 1: elemento agrupado
                    nodo.setElemento(enlace.getElemento());
                    nodo.setEnlace(enlace.getEnlace());
                    eliminado = true;
                    agrupado = true;
                } else if (izquierdo == null && derecho == null) { // Caso 2: nodo hoja
                    if (elemento.compareTo(padre.getElemento()) < 0) {
                        padre.setIzquierdo(null);
                    } else if (elemento.compareTo(padre.getElemento()) > 0) {
                        padre.setDerecho(null);
                    }

                    eliminado = true;
                } else if (izquierdo != null && derecho != null) { // Caso 3: nodo con ambos hijos
                    T minimoDerecho = minimo(derecho);
                    eliminado = eliminar(minimoDerecho, derecho, nodo, padre);
                    nodo.setElemento(minimoDerecho);
                } else { // Caso 4: nodo con un solo hijo
                    NodoAVLMultiple<T> reemplazo = derecho == null ? izquierdo : derecho;

                    if (padre == null) {
                        raiz = reemplazo;
                    } else {
                        if (elemento.compareTo(padre.getElemento()) < 0) {
                            padre.setIzquierdo(reemplazo);
                        } else if (elemento.compareTo(padre.getElemento()) > 0) {
                            padre.setDerecho(reemplazo);
                        }
                    }

                    eliminado = true;
                }
            } else {
                // Elemento posiblemente agrupado con otros del mismo orden
                Nodo<T> enlacePrevio = null;

                while (!eliminado && enlace != null) {
                    if (elemento.equals(enlace.getElemento())) {
                        if (enlacePrevio != null) {
                            enlacePrevio.setEnlace(enlace.getEnlace());
                        } else {
                            nodo.setEnlace(enlace.getEnlace());
                        }

                        eliminado = true;
                        agrupado = true;
                    } else {
                        enlacePrevio = enlace;
                        enlace = enlace.getEnlace();
                    }
                }
            }

            // Vuelta de la recursión:
            // Balancear el nodo si es necesario (lo determina el método balancear())
            // Actualizar altura de los nodos padre
            if (!agrupado && eliminado) {
                if (padre != null) {
                    padre.recalcularAltura();
                }

                balancear(padre, padreAnterior);
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
        boolean existe = false;

        if (elemento != null && raiz != null) {
            NodoAVLMultiple<T> nodo = raiz;

            while (!existe && nodo != null) {
                if (elemento.compareTo(nodo.getElemento()) < 0) {
                    nodo = nodo.getIzquierdo();
                } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                    nodo = nodo.getDerecho();
                } else if (elemento.equals(nodo.getElemento())) {
                    existe = true;
                } else {
                    Nodo<T> enlace = nodo.getEnlace();

                    while (!existe && enlace != null) {
                        if (elemento.equals(enlace.getElemento())) {
                            existe = true;
                        } else {
                            enlace = enlace.getEnlace();
                        }
                    }

                    nodo = null;
                }
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
    private T maximo(NodoAVLMultiple<T> nodo) {
        NodoAVLMultiple<T> derecho = nodo;
        NodoAVLMultiple<T> maximo = null;

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
    private T minimo(NodoAVLMultiple<T> nodo) {
        NodoAVLMultiple<T> izquierdo = nodo;
        NodoAVLMultiple<T> minimo = null;

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
     * Devuelve una lista con los elementos del árbol - ordenados de menor a mayor -.
     *
     * @return la lista con los elementos ordenados de menor a mayor
     */
    public Lista<T> listar() {
        Lista<T> lista = new Lista<>();
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
    private void listar(NodoAVLMultiple<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            NodoAVLMultiple<T> izquierdo = nodo.getIzquierdo();
            NodoAVLMultiple<T> derecho = nodo.getDerecho();

            listar(izquierdo, lista);
            listarMultiple(nodo, lista);
            listar(derecho, lista);
        }
    }

    protected void listarMultiple(NodoAVLMultiple<T> nodo, Lista<T> lista) {
        int posicion = lista.longitud() + 1;
        Nodo<T> enlace = nodo.getEnlace();
        lista.insertar(nodo.getElemento(), posicion);
        posicion++;

        while (enlace != null) {
            lista.insertar(enlace.getElemento(), posicion);
            enlace = enlace.getEnlace();
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
        Lista<T> lista = new Lista<>();
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
    private void listarRango(T minimo, T maximo, NodoAVLMultiple<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            NodoAVLMultiple<T> izquierdo = nodo.getIzquierdo();
            NodoAVLMultiple<T> derecho = nodo.getDerecho();
            T elemento = nodo.getElemento();

            listarRango(minimo, maximo, izquierdo, lista);

            if (elemento.compareTo(minimo) >= 0 && elemento.compareTo(maximo) <= 0) {
                listarMultiple(nodo, lista);
            }

            listarRango(minimo, maximo, derecho, lista);
        }
    }

    /**
     * Devuelve una lista por niveles con los elementos del árbol.
     *
     * @return la lista por niveles
     */
    public Lista<T> listarNiveles() {
        Lista<T> lista = new Lista<>();

        if (raiz != null) {
            NodoAVLMultiple<T> nodo, hijoIzquierdo, hijoDerecho;
            Cola<NodoAVLMultiple<T>> cola = new Cola<>();
            cola.poner(raiz);

            while (!cola.esVacia()) {
                nodo = cola.obtenerFrente();

                if (nodo != null) {
                    listarMultiple(nodo, lista);
                    hijoIzquierdo = nodo.getIzquierdo();
                    hijoDerecho = nodo.getDerecho();

                    if (hijoIzquierdo != null) {
                        cola.poner(hijoIzquierdo);
                    }

                    if (hijoDerecho != null) {
                        cola.poner(hijoDerecho);
                    }
                }

                cola.sacar();
            }
        }

        return lista;
    }

    /**
     * Devuelve una lista de listas por niveles con los elementos del árbol, incluyendo nulos.
     *
     * @param hastaNivel el nivel máximo a listar
     * @return la lista de lista de niveles
     */
    public Lista<Lista<T>> listarNivelesCompletos(int hastaNivel) {
        Lista<Lista<T>> lista = new Lista<>();

        if (raiz != null) {
            int nivelActual = 0;
            int nivelMaxElementos = 1;
            NodoAVLMultiple<T> nodo;
            Lista<T> nivel = new Lista<>();
            Cola<NodoAVLMultiple<T>> cola = new Cola<>();
            cola.poner(raiz);

            while (!cola.esVacia() && nivelActual <= hastaNivel) {
                nodo = cola.obtenerFrente();
                cola.sacar();

                if (nodo != null) {
                    nivel.insertar(nodo.getElemento(), nivel.longitud() + 1);
                    cola.poner(nodo.getIzquierdo());
                    cola.poner(nodo.getDerecho());
                } else {
                    nivel.insertar(null, nivel.longitud() + 1);
                    cola.poner(null);
                    cola.poner(null);
                }

                if (nivel.longitud() == nivelMaxElementos) {
                    lista.insertar(nivel, lista.longitud() + 1);
                    nivel = new Lista<>();
                    nivelMaxElementos *= 2;
                    nivelActual++;
                }
            }
        }

        return lista;
    }

    @Override
    public ArbolAVLMultiple<T> clone() {
        ArbolAVLMultiple<T> clon = new ArbolAVLMultiple<>();

        if (!esVacio()) {
            clon.raiz = new NodoAVLMultiple<>(raiz.getElemento());
            clon.raiz.setAltura(raiz.getAltura());
            clonarNodo(raiz, clon.raiz);
        }

        return clon;
    }

    /**
     * Clona el arbol a partir de un nodo.
     *
     * @param nodo el nodo desde donde clonar
     * @param nodoClon el nodo clon
     */
    private void clonarNodo(NodoAVLMultiple<T> nodo, NodoAVLMultiple<T> nodoClon) {
        if (nodo != null) {
            NodoAVLMultiple<T> hijoIzquierdo = nodo.getIzquierdo();
            NodoAVLMultiple<T> hijoDerecho = nodo.getDerecho();
            nodoClon.setAltura(nodo.getAltura());

            if (hijoIzquierdo != null) {
                nodoClon.setIzquierdo(new NodoAVLMultiple<>(hijoIzquierdo.getElemento()));
                clonarNodo(hijoIzquierdo, nodoClon.getIzquierdo());
            }

            if (hijoDerecho != null) {
                nodoClon.setDerecho(new NodoAVLMultiple<>(hijoDerecho.getElemento()));
                clonarNodo(hijoDerecho, nodoClon.getDerecho());
            }
        }
    }

    @Override
    public String toString() {
        return listar().toString();
    }
}
