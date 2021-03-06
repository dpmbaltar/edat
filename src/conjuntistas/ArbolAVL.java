package conjuntistas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 * Implementación de Árbol AVL.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class ArbolAVL<T extends Comparable<T>> {

    /**
     * El nodo raíz del árbol.
     */
    protected NodoAVL<T> raiz;

    /**
     * Constructor vacío.
     */
    public ArbolAVL() {
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
                raiz = new NodoAVL<>(elemento);
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
    private boolean insertar(T elemento, NodoAVL<T> nodo, NodoAVL<T> padre) {
        boolean insertado = false;

        if (nodo != null) {
            NodoAVL<T> izquierdo = nodo.getIzquierdo();
            NodoAVL<T> derecho = nodo.getDerecho();
            NodoAVL<T> nuevo;

            // Si el elemento es menor al del nodo, insertar en el sub-árbol izquierdo
            // Si el elemento es mayor al del nodo, insertar en el sub-árbol derecho
            // Si el elemento es igual al del nodo, el resultado será falso y no continuará hacia los nodos hijos
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                if (izquierdo == null) {
                    nuevo = new NodoAVL<>(elemento);
                    nodo.setIzquierdo(nuevo);
                    nodo.recalcularAltura();
                    insertado = true;
                } else {
                    insertado = insertar(elemento, izquierdo, nodo);
                }
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                if (derecho == null) {
                    nuevo = new NodoAVL<>(elemento);
                    nodo.setDerecho(nuevo);
                    nodo.recalcularAltura();
                    insertado = true;
                } else {
                    insertado = insertar(elemento, derecho, nodo);
                }
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

        return insertado;
    }

    /**
     * Balancea el sub-árbol correspondiente al nodo dado.
     *
     * @param nodo el nodo a balancear
     * @param padre el nodo padre
     */
    private void balancear(NodoAVL<T> nodo, NodoAVL<T> padre) {
        if (nodo != null) {
            boolean balanceado = false;
            NodoAVL<T> nodoHijo;
            NodoAVL<T> reemplazo = null;
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
    private NodoAVL<T> rotarDerecha(NodoAVL<T> nodo) {
        NodoAVL<T> izquierdo, izquierdoHD;

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
    private NodoAVL<T> rotarIzquierda(NodoAVL<T> nodo) {
        NodoAVL<T> derecho, derechoHI;

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
    private NodoAVL<T> rotarDerechaIzquierda(NodoAVL<T> nodo) {
        NodoAVL<T> rotado = rotarDerecha(nodo.getDerecho());
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
    private NodoAVL<T> rotarIzquierdaDerecha(NodoAVL<T> nodo) {
        NodoAVL<T> rotado = rotarIzquierda(nodo.getIzquierdo());
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
        return elemento == null ? false : eliminar(elemento, raiz, null, null);
    }

    /**
     * Elimina un elemento del sub-árbol correspondiente al nodo dado.
     *
     * @param elemento el elemento a eliminar
     * @param nodo el nodo desde donde buscar el elemento a eliminar
     * @return verdadero si el elemento fue eliminado, falso en caso contrario
     */
    private boolean eliminar(T elemento, NodoAVL<T> nodo, NodoAVL<T> padre, NodoAVL<T> padreAnterior) {
        boolean eliminado = false;

        if (nodo != null) {
            NodoAVL<T> izquierdo = nodo.getIzquierdo();
            NodoAVL<T> derecho = nodo.getDerecho();

            // Buscar el elemento a eliminar
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                eliminado = izquierdo == null ? false : eliminar(elemento, izquierdo, nodo, padre);
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                eliminado = derecho == null ? false : eliminar(elemento, derecho, nodo, padre);
            } else if (elemento.equals(nodo.getElemento())) {
                // Elemento encontrado
                // Eliminarlo según los siguientes 3 casos posibles:
                if (izquierdo == null && derecho == null) { // Caso 1: nodo hoja
                    if (elemento.compareTo(padre.getElemento()) < 0) {
                        padre.setIzquierdo(null);
                    } else if (elemento.compareTo(padre.getElemento()) > 0) {
                        padre.setDerecho(null);
                    }

                    eliminado = true;
                } else if (izquierdo != null && derecho != null) { // Caso 2: nodo con ambos hijos
                    T minimoDerecho = minimo(derecho);
                    eliminado = eliminar(minimoDerecho, derecho, nodo, padre);
                    nodo.setElemento(minimoDerecho);
                } else { // Caso 3: nodo con un solo hijo
                    NodoAVL<T> reemplazo = derecho == null ? izquierdo : derecho;

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
        boolean existe = false;

        if (elemento != null && raiz != null) {
            NodoAVL<T> nodo = raiz;

            while (!existe && nodo != null) {
                if (elemento.compareTo(nodo.getElemento()) < 0) {
                    nodo = nodo.getIzquierdo();
                } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                    nodo = nodo.getDerecho();
                } else if (elemento.equals(nodo.getElemento())) {
                    existe = true;
                } else {
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
    private T maximo(NodoAVL<T> nodo) {
        NodoAVL<T> derecho = nodo;
        NodoAVL<T> maximo = null;

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
    private T minimo(NodoAVL<T> nodo) {
        NodoAVL<T> izquierdo = nodo;
        NodoAVL<T> minimo = null;

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
    private void listar(NodoAVL<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            NodoAVL<T> izquierdo = nodo.getIzquierdo();
            NodoAVL<T> derecho = nodo.getDerecho();

            listar(izquierdo, lista);
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            listar(derecho, lista);
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

        if (minimo.compareTo(maximo) <= 0) {
            listarRango(minimo, maximo, raiz, lista);
        }

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
    private void listarRango(T minimo, T maximo, NodoAVL<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            T elemento = nodo.getElemento();
            NodoAVL<T> izquierdo = nodo.getIzquierdo();
            NodoAVL<T> derecho = nodo.getDerecho();

            if (minimo.compareTo(elemento) < 0 && izquierdo != null) {
                listarRango(minimo, maximo, izquierdo, lista);
            }

            if (minimo.compareTo(elemento) <= 0 && elemento.compareTo(maximo) <= 0) {
                lista.insertar(elemento, lista.longitud() + 1);
            }

            if (elemento.compareTo(maximo) < 0 && derecho != null) {
                listarRango(minimo, maximo, derecho, lista);
            }
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
            NodoAVL<T> nodo, hijoIzquierdo, hijoDerecho;
            Cola<NodoAVL<T>> cola = new Cola<>();
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
            NodoAVL<T> nodo;
            Lista<T> nivel = new Lista<>();
            Cola<NodoAVL<T>> cola = new Cola<>();
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
     * Método de utilidad para pruebas.
     *
     * @param hastaNivel el nivel máximo a listar
     * @return la lista de lista de niveles
     */
    public Lista<Lista<Integer>> listarNivelesCompletosAltura(int hastaNivel) {
        Lista<Lista<Integer>> lista = new Lista<>();

        if (raiz != null) {
            int nivelActual = 0;
            int nivelMaxElementos = 1;
            NodoAVL<T> nodo;
            Lista<Integer> nivel = new Lista<>();
            Cola<NodoAVL<T>> cola = new Cola<>();
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
    public ArbolAVL<T> clone() {
        ArbolAVL<T> clon = new ArbolAVL<>();

        if (!esVacio()) {
            clon.raiz = new NodoAVL<T>(raiz.getElemento());
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
    private void clonarNodo(NodoAVL<T> nodo, NodoAVL<T> nodoClon) {
        if (nodo != null) {
            NodoAVL<T> hijoIzquierdo = nodo.getIzquierdo();
            NodoAVL<T> hijoDerecho = nodo.getDerecho();
            nodoClon.setAltura(nodo.getAltura());

            if (hijoIzquierdo != null) {
                nodoClon.setIzquierdo(new NodoAVL<>(hijoIzquierdo.getElemento()));
                clonarNodo(hijoIzquierdo, nodoClon.getIzquierdo());
            }

            if (hijoDerecho != null) {
                nodoClon.setDerecho(new NodoAVL<>(hijoDerecho.getElemento()));
                clonarNodo(hijoDerecho, nodoClon.getDerecho());
            }
        }
    }

    /**
     * Devuelve la representación en forma de cadena del árbol.
     */
    @Override
    public String toString() {
        return listar().toString();
    }
}
