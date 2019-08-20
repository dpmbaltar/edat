package conjuntistas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 * Implementación de Diccionario (impl. como Árbol AVL).
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

        if (raiz == null) {
            raiz = new NodoAVLDicc<C, E>(clave, elemento);
            insertado = true;
        } else {
            insertado = insertar(clave, elemento, raiz, null);
        }

        return insertado;
    }

    private boolean insertar(C clave, E elemento, NodoAVLDicc<C, E> nodo, NodoAVLDicc<C, E> padre) {
        boolean insertado = false;

        if (nodo != null) {
            NodoAVLDicc<C, E> izquierdo, derecho, nuevo;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            // Si la clave es menor a la del nodo, insertar en el sub-árbol izquierdo
            // Si la clave es mayor a la del nodo, insertar en el sub-árbol derecho
            // Si la clave es igual a la del nodo, el resultado será falso y no continuará hacia los nodos hijos
            if (clave.compareTo(nodo.getClave()) < 0) {
                if (izquierdo == null) {
                    nuevo = new NodoAVLDicc<C, E>(clave, elemento);
                    nodo.setIzquierdo(nuevo);

                    // Actualizar altura del nodo si corresponde
                    if (derecho == null) {
                        nodo.setAltura(1);
                    }

                    insertado = true;
                } else {
                    insertado = insertar(clave, elemento, izquierdo, nodo);
                }
            } else if (clave.compareTo(nodo.getClave()) > 0) {
                if (derecho == null) {
                    nuevo = new NodoAVLDicc<C, E>(clave, elemento);
                    nodo.setDerecho(nuevo);

                    // Actualizar altura del nodo si corresponde
                    if (izquierdo == null) {
                        nodo.setAltura(1);
                    }

                    insertado = true;
                } else {
                    insertado = insertar(clave, elemento, derecho, nodo);
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
    private void actualizarAltura(NodoAVLDicc<C, E> nodo) {
        if (nodo != null) {
            NodoAVLDicc<C, E> izquierdo = nodo.getIzquierdo();
            NodoAVLDicc<C, E> derecho = nodo.getDerecho();
            int alturaIzquierdo = izquierdo != null ? izquierdo.getAltura() : -1;
            int alturaDerecho = derecho != null ? derecho.getAltura() : -1;
            nodo.setAltura(Math.max(alturaIzquierdo, alturaDerecho) + 1);
        }
    }

    /**
     * Balancea el sub-árbol correspondiente al nodo dado.
     *
     * @param nodo el nodo a balancear
     * @param padre el nodo padre
     */
    private void balancear(NodoAVLDicc<C, E> nodo, NodoAVLDicc<C, E> padre) {
        if (nodo != null) {
            boolean balanceado = false;
            NodoAVLDicc<C, E> nodoHijo, reemplazo = null;
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
                    C elementoPadre, elementoReemplazo;
                    elementoPadre = padre.getClave();
                    elementoReemplazo = reemplazo.getClave();

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
    private int balance(NodoAVLDicc<C, E> nodo) {
        int alturaIzquierdo, alturaDerecho;
        NodoAVLDicc<C, E> izquierdo, derecho;

        izquierdo = nodo.getIzquierdo();
        derecho = nodo.getDerecho();
        alturaIzquierdo = izquierdo != null ? izquierdo.getAltura() : -1;
        alturaDerecho = derecho != null ? derecho.getAltura() : -1;

        return alturaIzquierdo - alturaDerecho;
    }

    /**
     * Aplica una rotación simple a la derecha al sub-árbol correspondiente al nodo dado, y devuelve el nuevo nodo
     * raíz del sub-árbol.
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
    private NodoAVLDicc<C, E> rotarIzquierda(NodoAVLDicc<C, E> nodo) {
        NodoAVLDicc<C, E> derecho, derechoHI;

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
    private NodoAVLDicc<C, E> rotarDerechaIzquierda(NodoAVLDicc<C, E> nodo) {
        NodoAVLDicc<C, E> rotado = rotarDerecha(nodo.getDerecho());
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
    public boolean eliminar(C elemento) {
        return eliminar(elemento, raiz, null, null);
    }

    private boolean eliminar(C elemento, NodoAVLDicc<C, E> nodo, NodoAVLDicc<C, E> padre, NodoAVLDicc<C, E> padreAnterior) {
        boolean eliminado = false;

        if (nodo != null) {
            C elemPadre;
            NodoAVLDicc<C, E> izquierdo, derecho;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            // Buscar el elemento a eliminar
            if (elemento.compareTo(nodo.getClave()) < 0) {
                eliminado = izquierdo == null ? false : eliminar(elemento, izquierdo, nodo, padre);
            } else if (elemento.compareTo(nodo.getClave()) > 0) {
                eliminado = derecho == null ? false : eliminar(elemento, derecho, nodo, padre);
            } else {
                // Elemento encontrado. Eliminarlo según los 3 casos posibles:
                if (izquierdo == null && derecho == null) { // Caso 1: nodo hoja
                    elemPadre = padre.getClave();

                    if (elemento.compareTo(elemPadre) < 0) {
                        padre.setIzquierdo(null);
                    } else if (elemento.compareTo(elemPadre) > 0) {
                        padre.setDerecho(null);
                    }

                    eliminado = true;
                } else if (izquierdo != null && derecho != null) { // Caso 3: nodo con ambos hijos
                    C elemMinDerecho = minimo(derecho);
                    eliminado = eliminar(elemMinDerecho, derecho, nodo, padre);
                    nodo.setClave(elemMinDerecho);
                } else { // Caso 2: nodo con un solo hijo
                    NodoAVLDicc<C, E> reemplazo = derecho == null ? izquierdo : derecho;
                    elemPadre = padre.getClave();

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

    private C minimo(NodoAVLDicc<C, E> nodo) {
        NodoAVLDicc<C, E> izquierdo = nodo, minimo = null;

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
        boolean existe = false;

        if (raiz != null) {
            NodoAVLDicc<C, E> nodo = raiz;

            while (nodo != null) {
                if (clave.compareTo(nodo.getClave()) < 0) {
                    nodo = nodo.getIzquierdo();
                } else if (clave.compareTo(nodo.getClave()) > 0) {
                    nodo = nodo.getDerecho();
                } else {
                    existe = true;
                    nodo = null;
                }
            }
        }

        return existe;
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

        if (raiz != null) {
            NodoAVLDicc<C, E> nodo = raiz;

            while (nodo != null) {
                if (clave.compareTo(nodo.getClave()) < 0) {
                    nodo = nodo.getIzquierdo();
                } else if (clave.compareTo(nodo.getClave()) > 0) {
                    nodo = nodo.getDerecho();
                } else {
                    elemento = nodo.getElemento();
                    nodo = null;
                }
            }
        }

        return elemento;
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
     * Recorre la estructura completa y devuelve una lista ordenada con las claves de los elementos que se encuentran
     * almacenados en ella.
     *
     * @return la lista con las claves
     */
    public Lista<C> listarClaves() {
        Lista<C> lista = new Lista<C>();
        listarClaves(raiz, lista);

        return lista;
    }

    private void listarClaves(NodoAVLDicc<C, E> nodo, Lista<C> lista) {
        if (nodo != null) {
            NodoAVLDicc<C, E> izquierdo, derecho;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

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
            NodoAVLDicc<C, E> izquierdo, derecho;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            listarDatos(izquierdo, lista);
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            listarDatos(derecho, lista);
        }
    }

    /**
     * Devuelve una lista con las claves del árbol - ordenados de menor a mayor - mientras que la clave se encuentre
     * dentro del rango mínimo y máximo especificado.
     *
     * @param minimo la clave mínima del rango
     * @param maximo la clave máxima del rango
     * @return la lista de claves
     */
    public Lista<C> listarRango(C minima, C maxima) {
        Lista<C> lista = new Lista<C>();
        listarRango(minima, maxima, raiz, lista);

        return lista;
    }

    private void listarRango(C minima, C maxima, NodoAVLDicc<C, E> nodo, Lista<C> lista) {
        if (nodo != null) {
            NodoAVLDicc<C, E> izquierdo, derecho;
            C elemento;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();
            elemento = nodo.getClave();

            listarRango(minima, maxima, izquierdo, lista);

            if (elemento.compareTo(minima) >= 0 && elemento.compareTo(maxima) <= 0) {
                lista.insertar(elemento, lista.longitud() + 1);
            }

            listarRango(minima, maxima, derecho, lista);
        }
    }

    /**
     * Devuelve una lista por niveles con los elementos del árbol.
     *
     * @return la lista por niveles
     */
    public Lista<C> listarNiveles() {
        Lista<C> lista = new Lista<C>();

        if (raiz != null) {
            NodoAVLDicc<C, E> nodo, hijoIzquierdo, hijoDerecho;
            Cola<NodoAVLDicc<C, E>> cola = new Cola<>();
            cola.poner(raiz);

            while (!cola.esVacia()) {
                nodo = cola.obtenerFrente();

                if (nodo != null) {
                    lista.insertar(nodo.getClave(), lista.longitud() + 1);
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
    public Lista<Lista<C>> listarNivelesCompletos(int hastaNivel) {
        Lista<Lista<C>> lista = new Lista<>();

        if (raiz != null) {
            int nivelActual = 0;
            int nivelMaxElementos = 1;
            NodoAVLDicc<C, E> nodo;
            Lista<C> nivel = new Lista<>();
            Cola<NodoAVLDicc<C, E>> cola = new Cola<>();
            cola.poner(raiz);

            while (!cola.esVacia() && nivelActual <= hastaNivel) {
                nodo = cola.obtenerFrente();
                cola.sacar();

                if (nodo != null) {
                    nivel.insertar(nodo.getClave(), nivel.longitud() + 1);
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
            NodoAVLDicc<C, E> nodo;
            Lista<Integer> nivel = new Lista<>();
            Cola<NodoAVLDicc<C, E>> cola = new Cola<>();
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
                nodoClon.setIzquierdo(new NodoAVLDicc<C, E>(hijoIzquierdo.getClave(), hijoIzquierdo.getElemento()));
                clonarNodo(hijoIzquierdo, nodoClon.getIzquierdo());
            }

            if (hijoDerecho != null) {
                nodoClon.setDerecho(new NodoAVLDicc<C, E>(hijoDerecho.getClave(), hijoDerecho.getElemento()));
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
