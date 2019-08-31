package conjuntistas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 * Implementación de Árbol AVL que acepta elementos duplicados o elementos
 * distintos que al ser comparados representan el mismo valor.
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
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    private boolean insertar(T elemento, NodoAVLMultiple<T> nodo, NodoAVLMultiple<T> padre) {
        boolean insertado = false;

        if (nodo != null) {
            NodoAVLMultiple<T> izquierdo, derecho, nuevo;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            // Si el elemento es menor al del nodo, insertar en el sub-árbol izquierdo
            // Si el elemento es mayor al del nodo, insertar en el sub-árbol derecho
            // Si el elemento es igual al del nodo, insertar como enlace al elemento o aumentar referencias
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                if (izquierdo == null) {
                    nuevo = new NodoAVLMultiple<>(elemento);
                    nodo.setIzquierdo(nuevo);

                    // Actualizar altura del nodo si corresponde
                    if (derecho == null) {
                        nodo.setAltura(1);
                    }

                    insertado = true;
                } else {
                    insertado = insertar(elemento, izquierdo, nodo);
                }
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                if (derecho == null) {
                    nuevo = new NodoAVLMultiple<>(elemento);
                    nodo.setDerecho(nuevo);

                    // Actualizar altura del nodo si corresponde
                    if (izquierdo == null) {
                        nodo.setAltura(1);
                    }

                    insertado = true;
                } else {
                    insertado = insertar(elemento, derecho, nodo);
                }
            } else {
                insertarMultiple(elemento, nodo);
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
     * Inserta un elemento que ya existe o que representa un valor igual a un
     * elemento existente.
     *
     * @param elemento el elemento
     * @param nodo el nodo
     */
    private void insertarMultiple(T elemento, NodoAVLMultiple<T> nodo) {
        if (elemento.equals(nodo.getElemento())) {
            nodo.aumentarCantidad();
        } else if (nodo.getEnlace() == null) {
            nodo.setEnlace(new NodoMultiple<>(elemento));
        } else {
            NodoMultiple<T> enlaceActual = nodo.getEnlace();
            NodoMultiple<T> enlacePrevio;

            while (enlaceActual != null) {
                if (elemento.equals(enlaceActual.getElemento())) {
                    enlaceActual.aumentarCantidad();
                } else {
                    enlacePrevio = enlaceActual;
                    enlaceActual = enlaceActual.getEnlace();

                    if (enlaceActual == null) {
                        enlacePrevio.setEnlace(new NodoMultiple<>(elemento));
                    }
                }
            }
        }
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
            NodoAVLMultiple<T> nodoHijo, reemplazo = null;
            int balanceHijo, balanceNodo = nodo.balance();

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
                    T elementoPadre, elementoReemplazo;
                    elementoPadre = padre.getElemento();
                    elementoReemplazo = reemplazo.getElemento();

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
        return elemento != null ? eliminar(elemento, raiz, null, null) : false;
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

        if (nodo != null) {
            T elemPadre;
            NodoAVLMultiple<T> izquierdo, derecho;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            // Buscar el elemento a eliminar
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                eliminado = izquierdo == null ? false : eliminar(elemento, izquierdo, nodo, padre);
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                eliminado = derecho == null ? false : eliminar(elemento, derecho, nodo, padre);
            } else {
                // Elemento encontrado. Eliminarlo según los 3 casos posibles:
                if (izquierdo == null && derecho == null) { // Caso 1: nodo hoja
                    elemPadre = padre.getElemento();

                    if (elemento.compareTo(elemPadre) < 0) {
                        padre.setIzquierdo(null);
                    } else if (elemento.compareTo(elemPadre) > 0) {
                        padre.setDerecho(null);
                    }

                    eliminado = true;
                } else if (izquierdo != null && derecho != null) { // Caso 3: nodo con ambos hijos
                    T elemMinDerecho = minimo(derecho);
                    eliminado = eliminar(elemMinDerecho, derecho, nodo, padre);
                    nodo.setElemento(elemMinDerecho);
                } else { // Caso 2: nodo con un solo hijo
                    NodoAVLMultiple<T> reemplazo = derecho == null ? izquierdo : derecho;
                    elemPadre = padre.getElemento();

                    if (elemento.compareTo(elemPadre) < 0) {
                        padre.setIzquierdo(reemplazo);
                    } else if (elemento.compareTo(elemPadre) > 0) {
                        padre.setDerecho(reemplazo);
                    }

                    eliminado = true;
                }
            }

            // Vuelta de la recursión:
            // Balancear el nodo si es necesario (lo determina el método balancear())
            // Actualizar altura de los nodos padre
            if (eliminado) {
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
        return elemento != null ? pertenece(elemento, raiz) : false;
    }

    /**
     * Devuelve verdadero si el elemento dado pertenece al sub-árbol correspondiente al nodo dado, falso en caso
     * contrario.
     *
     * @param elemento el elemento a buscar
     * @param nodo el nodo desde donde buscar el elemento
     * @return verdadero si el elemento fue encontrado, falso en caso contrario
     */
    private boolean pertenece(T elemento, NodoAVLMultiple<T> nodo) {
        boolean existe = false;

        if (nodo != null) {
            NodoAVLMultiple<T> izquierdo = nodo.getIzquierdo();
            NodoAVLMultiple<T> derecho = nodo.getDerecho();

            if (elemento.compareTo(nodo.getElemento()) < 0) {
                existe = pertenece(elemento, izquierdo);
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                existe = pertenece(elemento, derecho);
            } else {
                if (elemento.equals(nodo.getElemento())) {
                    existe = true;
                } else if (nodo.getEnlace() != null) {
                    NodoMultiple<T> enlace = nodo.getEnlace();

                    while (!existe && enlace != null) {
                        if (elemento.equals(enlace.getElemento())) {
                            existe = true;
                        } else {
                            enlace = enlace.getEnlace();
                        }
                    }
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
        NodoAVLMultiple<T> derecho = nodo, maximo = null;

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
        NodoAVLMultiple<T> izquierdo = nodo, minimo = null;

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
            NodoMultiple<T> enlace = nodo.getEnlace();

            listar(izquierdo, lista);
            listarMultiple(nodo, lista);
            listar(derecho, lista);
        }
    }

    private void listarMultiple(NodoAVLMultiple<T> nodo, Lista<T> lista) {
        NodoMultiple<T> enlace = nodo.getEnlace();

        for (int i = 0; i < nodo.getCantidad(); i++) {
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
        }

        while (enlace != null) {
            for (int i = 0; i < enlace.getCantidad(); i++) {
                lista.insertar(enlace.getElemento(), lista.longitud() + 1);
            }

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
            NodoAVLMultiple<T> izquierdo, derecho;
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
                    lista.insertar(nodo.getElemento(), lista.longitud() + 1);
                    hijoIzquierdo = nodo.getIzquierdo();
                    hijoDerecho = nodo.getDerecho();

                    if (hijoIzquierdo != null) {
                        cola.poner(hijoIzquierdo);
                    }

                    if (hijoDerecho != null) {
                        cola.poner(hijoDerecho);
                    }
                } else {
                    lista.insertar(null, lista.longitud() + 1);
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

    /**
     * Devuelve una lista de listas por niveles con las alturas de los elementos del árbol, incluyendo nulos.
     *
     * @param hastaNivel el nivel máximo a listar
     * @return la lista de lista de niveles
     */
    public Lista<Lista<Integer>> listarNivelesAltura(int hastaNivel) {
        Lista<Lista<Integer>> lista = new Lista<>();

        if (raiz != null) {
            int nivelActual = 0;
            int nivelMaxElementos = 1;
            NodoAVLMultiple<T> nodo;
            Lista<Integer> nivel = new Lista<>();
            Cola<NodoAVLMultiple<T>> cola = new Cola<>();
            cola.poner(raiz);

            while (!cola.esVacia() && nivelActual <= hastaNivel) {
                nodo = cola.obtenerFrente();
                cola.sacar();

                if (nodo != null) {
                    nivel.insertar(nodo.getAltura(), nivel.longitud() + 1);
                    cola.poner(nodo.getIzquierdo());
                    cola.poner(nodo.getDerecho());
                } else {
                    nivel.insertar(-1, nivel.longitud() + 1);
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
