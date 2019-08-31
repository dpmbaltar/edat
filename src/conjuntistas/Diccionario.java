package conjuntistas;

import lineales.dinamicas.Lista;

/**
 * Implementación de Diccionario (implementación como Árbol AVL).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 *
 * @param <C> el tipo de las claves
 * @param <E> el tipo de los elementos
 */
public class Diccionario<C extends Comparable<C>, E> {

    /**
     * El nodo raíz del árbol.
     */
    protected NodoAVLDicc<C, E> raiz;

    /**
     * Constructor vacío.
     */
    public Diccionario() {
        raiz = null;
    }

    /**
     * Recibe la clave que es única y el dato (información asociada a ella). Si no existe en la estructura un elemento
     * con igual clave, agrega el par (clave, dato) a la estructura. Si la operación termina con éxito devuelve
     * verdadero y falso en caso contrario.
     *
     * @param clave la clave a insertar
     * @param elemento el elemento a insertar
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean insertar(C clave, E elemento) {
        boolean insertado = false;

        if (clave != null && elemento != null) {
            if (raiz == null) {
                raiz = new NodoAVLDicc<>(clave, elemento);
                insertado = true;
            } else {
                insertado = insertar(clave, elemento, raiz, null);
            }
        }

        return insertado;
    }

    private boolean insertar(C clave, E elemento, NodoAVLDicc<C, E> nodo, NodoAVLDicc<C, E> padre) {
        boolean insertado = false;

        if (nodo != null) {
            NodoAVLDicc<C, E> izquierdo = nodo.getIzquierdo();
            NodoAVLDicc<C, E> derecho = nodo.getDerecho();
            NodoAVLDicc<C, E> nuevo;

            // Si la clave es menor a la del nodo, insertar en el subárbol izquierdo
            // Si la clave es mayor a la del nodo, insertar en el subárbol derecho
            // Si la clave es igual a la del nodo, el resultado será falso y no continuará hacia los nodos hijos
            if (clave.compareTo(nodo.getClave()) < 0) {
                if (izquierdo == null) {
                    nuevo = new NodoAVLDicc<>(clave, elemento);
                    nodo.setIzquierdo(nuevo);
                    nodo.recalcularAltura();
                    insertado = true;
                } else {
                    insertado = insertar(clave, elemento, izquierdo, nodo);
                }
            } else if (clave.compareTo(nodo.getClave()) > 0) {
                if (derecho == null) {
                    nuevo = new NodoAVLDicc<>(clave, elemento);
                    nodo.setDerecho(nuevo);
                    nodo.recalcularAltura();
                    insertado = true;
                } else {
                    insertado = insertar(clave, elemento, derecho, nodo);
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
     * Balancea el subárbol correspondiente al nodo dado.
     *
     * @param nodo el nodo a balancear
     * @param padre el nodo padre
     */
    private void balancear(NodoAVLDicc<C, E> nodo, NodoAVLDicc<C, E> padre) {
        if (nodo != null) {
            boolean balanceado = false;
            NodoAVLDicc<C, E> nodoHijo;
            NodoAVLDicc<C, E> reemplazo = null;
            int balanceHijo;
            int balanceNodo = nodo.balance();

            // Detectar si el nodo está balanceado
            // De no estarlo, hacer las rotaciones necesarias para que éste quede balanceado
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
                    C elementoPadre = padre.getClave();
                    C elementoReemplazo = reemplazo.getClave();

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
     * Aplica una rotación simple a la derecha al subárbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del subárbol.
     *
     * @param nodo el nodo a rotar
     * @return el nodo rotado
     */
    private NodoAVLDicc<C, E> rotarDerecha(NodoAVLDicc<C, E> nodo) {
        NodoAVLDicc<C, E> izquierdo, izquierdoHD;

        izquierdo = nodo.getIzquierdo();
        izquierdoHD = izquierdo == null ? null : izquierdo.getDerecho();
        izquierdo.setDerecho(nodo);
        nodo.setIzquierdo(izquierdoHD);
        nodo.recalcularAltura();
        izquierdo.recalcularAltura();

        return izquierdo;
    }

    /**
     * Aplica una rotación simple a la izquierda al subárbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del subárbol.
     *
     * @param nodo el nodo a rotar
     * @return el nodo rotado
     */
    private NodoAVLDicc<C, E> rotarIzquierda(NodoAVLDicc<C, E> nodo) {
        NodoAVLDicc<C, E> derecho, derechoHI;

        derecho = nodo.getDerecho();
        derechoHI = derecho == null ? null : derecho.getIzquierdo();
        derecho.setIzquierdo(nodo);
        nodo.setDerecho(derechoHI);
        nodo.recalcularAltura();
        derecho.recalcularAltura();

        return derecho;
    }

    /**
     * Aplica una rotación doble derecha-izquierda al subárbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del subárbol.
     *
     * @param nodo el nodo a rotar
     * @return el nodo rotado
     */
    private NodoAVLDicc<C, E> rotarDerechaIzquierda(NodoAVLDicc<C, E> nodo) {
        NodoAVLDicc<C, E> rotado = rotarDerecha(nodo.getDerecho());
        nodo.setDerecho(rotado);

        return rotarIzquierda(nodo);
    }

    /**
     * Aplica una rotación doble izquierda-derecha al subárbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del subárbol.
     *
     * @param nodo el nodo a rotar
     * @return el nodo rotado
     */
    private NodoAVLDicc<C, E> rotarIzquierdaDerecha(NodoAVLDicc<C, E> nodo) {
        NodoAVLDicc<C, E> rotado = rotarIzquierda(nodo.getIzquierdo());
        nodo.setIzquierdo(rotado);

        return rotarDerecha(nodo);
    }

    /**
     * Elimina el elemento cuya clave sea la recibida por parámetro. Si lo encuentra y la operación de eliminación
     * termina con éxito devuelve verdadero y falso en caso contrario.
     *
     * @param clave la clave del elemento a eliminar
     * @return verdadero si el elemento fue eliminado, falso en caso contrario
     */
    public boolean eliminar(C clave) {
        return clave == null ? false : eliminar(clave, raiz, null, null);
    }

    private boolean eliminar(C clave, NodoAVLDicc<C, E> nodo, NodoAVLDicc<C, E> padre, NodoAVLDicc<C, E> padreAnterior) {
        boolean eliminado = false;

        if (nodo != null) {
            NodoAVLDicc<C, E> izquierdo = nodo.getIzquierdo();
            NodoAVLDicc<C, E> derecho = nodo.getDerecho();

            // Buscar la clave a eliminar
            if (clave.compareTo(nodo.getClave()) < 0) {
                eliminado = izquierdo == null ? false : eliminar(clave, izquierdo, nodo, padre);
            } else if (clave.compareTo(nodo.getClave()) > 0) {
                eliminado = derecho == null ? false : eliminar(clave, derecho, nodo, padre);
            } else if (clave.equals(nodo.getClave())) {
                // Elemento encontrado
                // Eliminarlo según los siguientes 3 casos posibles:
                if (izquierdo == null && derecho == null) { // Caso 1: nodo hoja
                    if (clave.compareTo(padre.getClave()) < 0) {
                        padre.setIzquierdo(null);
                    } else if (clave.compareTo(padre.getClave()) > 0) {
                        padre.setDerecho(null);
                    }

                    eliminado = true;
                } else if (izquierdo != null && derecho != null) { // Caso 2: nodo con ambos hijos
                    C minimoDerecho = minimo(derecho);
                    eliminado = eliminar(minimoDerecho, derecho, nodo, padre);
                    nodo.setClave(minimoDerecho);
                } else { // Caso 3: nodo con un solo hijo
                    NodoAVLDicc<C, E> reemplazo = derecho != null ? derecho : izquierdo;

                    if (clave.compareTo(padre.getClave()) < 0) {
                        padre.setIzquierdo(reemplazo);
                    } else if (clave.compareTo(padre.getClave()) > 0) {
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
     * Devuelve la clave mínima a partir del nodo dado (método para obtener candidato para eliminación).
     *
     * @param nodo el nodo
     * @return la clave mínima si existe, nulo en caso contrario
     */
    private C minimo(NodoAVLDicc<C, E> nodo) {
        NodoAVLDicc<C, E> izquierdo = nodo;
        NodoAVLDicc<C, E> minimo = null;

        while (izquierdo != null) {
            minimo = izquierdo;
            izquierdo = izquierdo.getIzquierdo();
        }

        return minimo == null ? null : minimo.getClave();
    }

    /**
     * Devuelve verdadero si en la estructura se encuentra almacenado un elemento con la clave recibida por parámetro,
     * caso contrario devuelve falso.
     *
     * @param clave la clave a buscar
     * @return verdadero si la clave fue encontrada, falso en caso contrario
     */
    public boolean existeClave(C clave) {
        return obtenerInformacion(clave) != null;
    }

    /**
     * Si en la estructura se encuentra almacenado un elemento con la clave recibida por parámetro, devuelve la
     * información asociada a ella. Precondición: si no existe un elemento con esa clave no se puede asegurar el
     * funcionamiento de la operación.
     *
     * @param clave la clave del elemento
     * @return el elemento asociado a la clave, si existe, nulo en caso contrario
     */
    public E obtenerInformacion(C clave) {
        E elemento = null;

        if (clave != null && raiz != null) {
            NodoAVLDicc<C, E> nodo = raiz;

            while (elemento == null && nodo != null) {
                if (clave.compareTo(nodo.getClave()) < 0) {
                    nodo = nodo.getIzquierdo();
                } else if (clave.compareTo(nodo.getClave()) > 0) {
                    nodo = nodo.getDerecho();
                } else if (clave.equals(nodo.getClave())) {
                    elemento = nodo.getElemento();
                } else {
                    nodo = null;
                }
            }
        }

        return elemento;
    }

    /**
     * Devuelve verdadero si el diccionario está vacío, o falso en caso contrario.
     *
     * @return verdadero si el diccionario está vacío, falso en caso contrario
     */
    public boolean esVacio() {
        return raiz == null;
    }

    /**
     * Elimina todos los elementos del diccionario.
     */
    public void vaciar() {
        raiz = null;
    }

    /**
     * Recorre la estructura completa y devuelve una lista ordenada con las claves de los elementos que se encuentran
     * almacenados en ella.
     *
     * @return la lista con las claves
     */
    public Lista<C> listarClaves() {
        Lista<C> lista = new Lista<>();
        listarClaves(raiz, lista);

        return lista;
    }

    private void listarClaves(NodoAVLDicc<C, E> nodo, Lista<C> lista) {
        if (nodo != null) {
            NodoAVLDicc<C, E> izquierdo = nodo.getIzquierdo();
            NodoAVLDicc<C, E> derecho = nodo.getDerecho();

            listarClaves(izquierdo, lista);
            lista.insertar(nodo.getClave(), lista.longitud() + 1);
            listarClaves(derecho, lista);
        }
    }

    /**
     * Recorre la estructura completa y devuelve una lista ordenada con la información asociada de los elementos que
     * se encuentran almacenados en ella.
     *
     * @return la lista con los elementos
     */
    public Lista<E> listarDatos() {
        Lista<E> lista = new Lista<>();
        listarDatos(raiz, lista);

        return lista;
    }

    private void listarDatos(NodoAVLDicc<C, E> nodo, Lista<E> lista) {
        if (nodo != null) {
            NodoAVLDicc<C, E> izquierdo = nodo.getIzquierdo();
            NodoAVLDicc<C, E> derecho = nodo.getDerecho();

            listarDatos(izquierdo, lista);
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            listarDatos(derecho, lista);
        }
    }

    @Override
    public Diccionario<C, E> clone() {
        Diccionario<C, E> clon = new Diccionario<>();

        if (!esVacio()) {
            clon.raiz = new NodoAVLDicc<C, E>(raiz.getClave(), raiz.getElemento());
            clon.raiz.setAltura(raiz.getAltura());
            clonarNodo(raiz, clon.raiz);
        }

        return clon;
    }

    private void clonarNodo(NodoAVLDicc<C, E> nodo, NodoAVLDicc<C, E> nodoClon) {
        if (nodo != null) {
            NodoAVLDicc<C, E> hijoIzquierdo = nodo.getIzquierdo();
            NodoAVLDicc<C, E> hijoDerecho = nodo.getDerecho();
            nodoClon.setAltura(nodo.getAltura());

            if (hijoIzquierdo != null) {
                nodoClon.setIzquierdo(new NodoAVLDicc<>(hijoIzquierdo.getClave(), hijoIzquierdo.getElemento()));
                clonarNodo(hijoIzquierdo, nodoClon.getIzquierdo());
            }

            if (hijoDerecho != null) {
                nodoClon.setDerecho(new NodoAVLDicc<>(hijoDerecho.getClave(), hijoDerecho.getElemento()));
                clonarNodo(hijoDerecho, nodoClon.getDerecho());
            }
        }
    }

    @Override
    public String toString() {
        Lista<C> claves = listarClaves();
        Lista<E> elementos = listarDatos();
        StringBuilder cadena = new StringBuilder("[");
        int longitud = claves.longitud();

        for (int i = 1; i <= longitud; i++) {
            cadena.append(claves.recuperar(i)).append(':').append(elementos.recuperar(i));

            if (i < longitud) {
                cadena.append(", ");
            }
        }

        cadena.append("]");

        return cadena.toString();
    }
}
