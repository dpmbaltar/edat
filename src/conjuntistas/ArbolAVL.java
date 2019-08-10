package conjuntistas;

import jerarquicas.Nodo;
import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 * Implementación de Árbol AVL.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo de los elementos
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBB<T> {

    @Override
    public boolean insertar(T elemento) {
        boolean insertado = false;

        if (raiz == null) {
            raiz = new Nodo<T>(elemento);
            insertado = true;
        } else {
            insertado = insertar(elemento, raiz, null);
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
    private boolean insertar(T elemento, Nodo<T> nodo, Nodo<T> padre) {
        boolean insertado = false;

        if (nodo != null) {
            Nodo<T> izquierdo, derecho, nuevo;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            // Si el elemento es menor al del nodo, insertar en el sub-árbol izquierdo
            // Si el elemento es mayor al del nodo, insertar en el sub-árbol derecho
            // Si el elemento es igual al del nodo, el resultado será falso y no continuará hacia los nodos hijos
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                if (izquierdo == null) {
                    nuevo = new Nodo<T>(elemento);
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
                    nuevo = new Nodo<T>(elemento);
                    nodo.setDerecho(nuevo);

                    // Actualizar altura del nodo si corresponde
                    if (izquierdo == null) {
                        nodo.setAltura(1);
                    }

                    insertado = true;
                } else {
                    insertado = insertar(elemento, derecho, nodo);
                }
            }

            // Vuelta de la recursión:
            // Balancear el nodo si es necesario (lo determina el método balancear())
            // Actualizar altura de los nodos padre
            if (insertado) {
                actualizarAltura(padre);
                balancear(nodo, padre);
            }
        }

        return insertado;
    }

    /**
     * Actualiza la altura de un nodo dado.
     *
     * @see Nodo#getAltura
     * @param nodo el nodo a actualizar la altura
     */
    private void actualizarAltura(Nodo<T> nodo) {
        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo();
            Nodo<T> derecho = nodo.getDerecho();
            int alturaIzquierdo = izquierdo != null ? izquierdo.getAltura() : -1;
            int alturaDerecho = derecho != null ? derecho.getAltura() : -1;
            nodo.setAltura(Math.max(alturaIzquierdo, alturaDerecho) + 1);
        }
    }

    @Override
    public boolean eliminar(T elemento) {
        return eliminar(elemento, raiz, null, null);
    }

    protected boolean eliminar(T elemento, Nodo<T> nodo, Nodo<T> padre, Nodo<T> padreAnterior) {
        boolean eliminado = false;

        if (nodo != null) {
            T elemPadre;
            Nodo<T> izquierdo, derecho;
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
                    Nodo<T> reemplazo = derecho == null ? izquierdo : derecho;
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
                actualizarAltura(padre);
                balancear(padre, padreAnterior);
            }
        }

        return eliminado;
    }

    /**
     * Balancea el sub-árbol correspondiente al nodo dado.
     *
     * @param nodo el nodo a balancear
     * @param padre el nodo padre
     */
    private void balancear(Nodo<T> nodo, Nodo<T> padre) {
        if (nodo != null) {
            boolean balanceado = false;
            Nodo<T> nodoHijo, reemplazo = null;
            int balanceHijo, balanceNodo = balance(nodo);

            // Detectar si el nodo está balanceado. De no estarlo, hacer las
            // rotaciones necesarias para que éste quede balanceado
            if (balanceNodo == 2) {
                nodoHijo = nodo.getIzquierdo();
                balanceHijo = balance(nodoHijo);

                if (balanceHijo == 1 || balanceHijo == 0) { // 0 cuando es una eliminación
                    reemplazo = rotarDerecha(nodo);
                } else if (balanceHijo == -1) {
                    reemplazo = rotarIzquierdaDerecha(nodo);
                }

                balanceado = true;
            } else if (balanceNodo == -2) {
                nodoHijo = nodo.getDerecho();
                balanceHijo = balance(nodoHijo);

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

                    actualizarAltura(padre);
                }
            }
        }
    }

    /**
     * Devuelve el balance del nodo dado.
     *
     * @param nodo el nodo a calcular su balance
     * @return el balance del nodo
     */
    private int balance(Nodo<T> nodo) {
        int alturaIzquierdo, alturaDerecho;
        Nodo<T> izquierdo, derecho;

        izquierdo = nodo.getIzquierdo();
        derecho = nodo.getDerecho();
        alturaIzquierdo = izquierdo != null ? izquierdo.getAltura() : -1;
        alturaDerecho = derecho != null ? derecho.getAltura() : -1;

        return alturaIzquierdo - alturaDerecho;
    }

    /**
     * Devuelve la altura de un nodo.
     *
     * @deprecated
     * Utilizar {@link jerarquicas.Nodo#getAltura}
     *
     * @param nodo el nodo a calcular su altura
     * @return la altura del nodo
     */
    @SuppressWarnings("unused")
    @Deprecated
    private int altura(Nodo<T> nodo) {
        int altura = -1;

        if (nodo != null) {
            int alturaIzquierdo, alturaDerecho;
            Nodo<T> hijoIzquierdo, hijoDerecho;

            hijoIzquierdo = nodo.getIzquierdo();
            hijoDerecho = nodo.getDerecho();
            alturaIzquierdo = altura(hijoIzquierdo);
            alturaDerecho = altura(hijoDerecho);
            altura = 0;

            if (alturaIzquierdo > alturaDerecho) {
                altura += alturaIzquierdo;
            } else {
                altura += alturaDerecho;
            }
        }

        return altura;
    }

    /**
     * Aplica una rotación simple a la derecha al sub-árbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del sub-árbol.
     *
     * @param nodo el nodo a rotar
     * @return el nodo rotado
     */
    private Nodo<T> rotarDerecha(Nodo<T> nodo) {
        Nodo<T> izquierdo, izquierdoHD;

        izquierdo = nodo.getIzquierdo();
        izquierdoHD = izquierdo == null ? null : izquierdo.getDerecho();
        izquierdo.setDerecho(nodo);
        nodo.setIzquierdo(izquierdoHD);
        actualizarAltura(nodo);
        actualizarAltura(izquierdo);

        return izquierdo;
    }

    /**
     * Aplica una rotación simple a la izquierda al sub-árbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del sub-árbol.
     *
     * @param nodo el nodo a rotar
     * @return el nodo rotado
     */
    private Nodo<T> rotarIzquierda(Nodo<T> nodo) {
        Nodo<T> derecho, derechoHI;

        derecho = nodo.getDerecho();
        derechoHI = derecho == null ? null : derecho.getIzquierdo();
        derecho.setIzquierdo(nodo);
        nodo.setDerecho(derechoHI);
        actualizarAltura(nodo);
        actualizarAltura(derecho);

        return derecho;
    }

    /**
     * Aplica una rotación doble derecha-izquierda al sub-árbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del sub-árbol.
     *
     * @param nodo el nodo a rotar
     * @return el nodo rotado
     */
    private Nodo<T> rotarDerechaIzquierda(Nodo<T> nodo) {
        Nodo<T> rotado = rotarDerecha(nodo.getDerecho());
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
    private Nodo<T> rotarIzquierdaDerecha(Nodo<T> nodo) {
        Nodo<T> rotado = rotarIzquierda(nodo.getIzquierdo());
        nodo.setIzquierdo(rotado);

        return rotarDerecha(nodo);
    }

    /**
     * Devuelve una lista por niveles con los elementos del árbol.
     *
     * @return la lista por niveles
     */
    public Lista<T> listarNiveles() {
        Lista<T> lista = new Lista<T>();

        if (raiz != null) {
            Nodo<T> nodo, hijoIzquierdo, hijoDerecho;
            Cola<Nodo<T>> cola = new Cola<Nodo<T>>();
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
            Nodo<T> nodo;
            Lista<T> nivel = new Lista<>();
            Cola<Nodo<T>> cola = new Cola<>();
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
     * Devuelve una lista de listas por niveles con los elementos del árbol, incluyendo nulos.
     *
     * @param hastaNivel el nivel máximo a listar
     * @return la lista de lista de niveles
     */
    public Lista<Lista<Integer>> listarNivelesAltura(int hastaNivel) {
        Lista<Lista<Integer>> lista = new Lista<>();

        if (raiz != null) {
            int nivelActual = 0;
            int nivelMaxElementos = 1;
            Nodo<T> nodo;
            Lista<Integer> nivel = new Lista<>();
            Cola<Nodo<T>> cola = new Cola<>();
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
    public ArbolAVL<T> clonar() {
        ArbolAVL<T> clon = new ArbolAVL<T>();
        clonarDesde(raiz, clon);

        return clon;
    }

    /**
     * Clona recursivamente el árbol desde un nodo dado.
     *
     * @param nodo el nodo desde donde clonar
     * @param arbol el clon del árbol
     */
    private void clonarDesde(Nodo<T> nodo, ArbolAVL<T> arbol) {
        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo();
            Nodo<T> derecho = nodo.getDerecho();
            arbol.insertar(nodo.getElemento());
            clonarDesde(izquierdo, arbol);
            clonarDesde(derecho, arbol);
        }
    }

}
