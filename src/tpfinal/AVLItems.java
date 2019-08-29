package tpfinal;

import lineales.dinamicas.Lista;

/**
 * Implementación de Árbol AVL para los ítems ordenados y agrupados por precio.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class AVLItems {

    /**
     * El nodo raíz del árbol.
     */
    protected NodoItem raiz;

    /**
     * Constructor vacío.
     */
    public AVLItems() {
        raiz = null;
    }

    /**
     * Inserta un elemento al árbol.
     *
     * @param elemento el elemento a insertar
     * @return verdadero si el elemento fue insertado, falso en caso contrario
     */
    public boolean insertar(Item elemento) {
        boolean insertado = false;

        if (raiz == null) {
            raiz = new NodoItem(elemento);
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
    private boolean insertar(Item elemento, NodoItem nodo, NodoItem padre) {
        boolean insertado = false;

        if (nodo != null) {
            NodoItem izquierdo, derecho, nuevo;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            // Si el elemento es menor al del nodo, insertar en el sub-árbol izquierdo
            // Si el elemento es mayor al del nodo, insertar en el sub-árbol derecho
            // Si el elemento es igual al del nodo, se agrupa por precio si el ítem no esta en el árbol
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                if (izquierdo == null) {
                    nuevo = new NodoItem(elemento);
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
                    nuevo = new NodoItem(elemento);
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
                boolean existe = false;
                NodoItem nodoAnterior = null;
                NodoItem nodoSiguiente = nodo;

                while (nodoSiguiente != null && !existe) {
                    if (elemento.equals(nodo.getElemento())) {
                        existe = true;
                    }

                    nodoAnterior = nodoSiguiente;
                    nodoSiguiente = nodoSiguiente.getSiguiente();

                    if (nodoSiguiente == null && !existe) {
                        nodoAnterior.setSiguiente(new NodoItem(elemento));
                    }
                }

                insertado = !existe;
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
    private void actualizarAltura(NodoItem nodo) {
        if (nodo != null) {
            NodoItem izquierdo = nodo.getIzquierdo();
            NodoItem derecho = nodo.getDerecho();
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
    private void balancear(NodoItem nodo, NodoItem padre) {
        if (nodo != null) {
            boolean balanceado = false;
            NodoItem nodoHijo, reemplazo = null;
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
                    Item elementoPadre, elementoReemplazo;
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
    private int balance(NodoItem nodo) {
        int alturaIzquierdo, alturaDerecho;
        NodoItem izquierdo, derecho;

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
    private NodoItem rotarDerecha(NodoItem nodo) {
        NodoItem izquierdo, izquierdoHD;

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
    private NodoItem rotarIzquierda(NodoItem nodo) {
        NodoItem derecho, derechoHI;

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
    private NodoItem rotarDerechaIzquierda(NodoItem nodo) {
        NodoItem rotado = rotarDerecha(nodo.getDerecho());
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
    private NodoItem rotarIzquierdaDerecha(NodoItem nodo) {
        NodoItem rotado = rotarIzquierda(nodo.getIzquierdo());
        nodo.setIzquierdo(rotado);

        return rotarDerecha(nodo);
    }

    /**
     * Elimina el elemento del árbol.
     *
     * @param elemento el elemento a eliminar
     * @return verdadero si el elemento fue eliminado, falso en caso contrario
     */
    public boolean eliminar(Item elemento) {
        return eliminar(elemento, raiz, null, null);
    }

    /**
     * Elimina un elemento del sub-árbol correspondiente al nodo dado.
     *
     * @param elemento el elemento a eliminar
     * @param nodo el nodo desde donde buscar el elemento a eliminar
     * @return verdadero si el elemento fue eliminado, falso en caso contrario
     */
    private boolean eliminar(Item elemento, NodoItem nodo, NodoItem padre, NodoItem padreAnterior) {
        boolean eliminado = false;

        if (nodo != null) {
            Item elemPadre;
            NodoItem izquierdo, derecho;
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
                    Item elemMinDerecho = minimo(derecho);
                    eliminado = eliminar(elemMinDerecho, derecho, nodo, padre);
                    nodo.setElemento(elemMinDerecho);
                } else { // Caso 2: nodo con un solo hijo
                    NodoItem reemplazo = derecho == null ? izquierdo : derecho;
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
     * Devuelve verdadero si el elemento dado pertenece al árbol, o falso en caso contrario.
     *
     * @param elemento el elemento a buscar
     * @return verdadero si el elemento fue encontrado, falso en caso contrario
     */
    public boolean pertenece(Item elemento) {
        return pertenece(elemento, raiz);
    }

    /**
     * Devuelve verdadero si el elemento dado pertenece al sub-árbol correspondiente al nodo dado, falso en caso
     * contrario.
     *
     * @param elemento el elemento a buscar
     * @param nodo el nodo desde donde buscar el elemento
     * @return verdadero si el elemento fue encontrado, falso en caso contrario
     */
    private boolean pertenece(Item elemento, NodoItem nodo) {
        boolean existe = false;

        if (nodo != null) {
            NodoItem izquierdo, derecho;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            if (elemento.compareTo(nodo.getElemento()) < 0) {
                existe = pertenece(elemento, izquierdo);
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                existe = pertenece(elemento, derecho);
            } else {
                existe = true;
            }
        }

        return existe;
    }

    /**
     * Devuelve el ítem con el código dado, si existe en la estructura, nulo en caso contrario.
     *
     * @param codigo el código del ítem
     * @return el ítem si existe, nulo en caso contrario
     */
    public Item obtener(String codigo) {
        NodoItem nodo = buscarNodo(codigo);
        return nodo != null ? nodo.getElemento() : null;
    }

    /**
     * Busca el nodo del código de ítem dado a partir del nodo raíz.
     *
     * @param elemento el código a buscar
     * @return el nodo si el ítem fue encontrado, nulo en caso contrario
     */
    private NodoItem buscarNodo(String codigo) {
        return buscarNodo(codigo, raiz);
    }

    /**
     * Busca el nodo del código de ítem dado a partir de un nodo en particular.
     *
     * @param elemento el código a buscar
     * @param nodo el nodo desde donde buscar el ítem
     * @return el nodo si el ítem fue encontrado, nulo en caso contrario
     */
    private NodoItem buscarNodo(String codigo, NodoItem nodo) {
        NodoItem buscado = null;

        if (nodo != null) {
            if (nodo.getElemento().getCodigo().equals(codigo)) {
                buscado = nodo;
            } else {
                buscado = buscarNodo(codigo, nodo.getIzquierdo());

                if (buscado == null) {
                    buscado = buscarNodo(codigo, nodo.getDerecho());
                }
            }
        }

        return buscado;
    }

    /**
     * Devuelve el elemento máximo del árbol.
     *
     * @return el elemento máximo
     */
    public Item maximo() {
        return maximo(raiz);
    }

    /**
     * Devuelve el elemento máximo a partir de un nodo.
     *
     * @param nodo el nodo desde donde buscar el elemento máximo
     * @return el elemento máximo
     */
    private Item maximo(NodoItem nodo) {
        NodoItem derecho = nodo, maximo = null;

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
    public Item minimo() {
        return minimo(raiz);
    }

    /**
     * Devuelve el elemento mínimo a partir de un nodo.
     *
     * @param nodo el nodo desde donde buscar el elemento mínimo
     * @return el elemento mínimo
     */
    private Item minimo(NodoItem nodo) {
        NodoItem izquierdo = nodo, minimo = null;

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
    public Lista<Item> listar() {
        Lista<Item> lista = new Lista<Item>();
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
    private void listar(NodoItem nodo, Lista<Item> lista) {
        if (nodo != null) {
            NodoItem izquierdo = nodo.getIzquierdo();
            NodoItem derecho = nodo.getDerecho();
            NodoItem siguiente = nodo;

            listar(izquierdo, lista);

            while (siguiente != null) {
                lista.insertar(siguiente.getElemento(), lista.longitud() + 1);
                siguiente = siguiente.getSiguiente();
            }

            listar(derecho, lista);
        }
    }

    /**
     * Devuelve una lista con los items, ordenados por precio de menor a mayor, mientras que el precio se encuentre
     * dentro del rango mínimo y máximo especificado (ambos inclusive).
     *
     * @param minimo el precio mínimo del rango
     * @param maximo el precio máximo del rango
     * @return la lista de items
     */
    public Lista<Item> listarRangoPorPrecio(int minimo, int maximo) {
        Lista<Item> lista = new Lista<>();
        listarRangoPorPrecio(minimo, maximo, raiz, lista);

        return lista;
    }

    private void listarRangoPorPrecio(int minimo, int maximo, NodoItem nodo, Lista<Item> lista) {
        if (nodo != null) {
            NodoItem izquierdo = nodo.getIzquierdo();
            NodoItem derecho = nodo.getDerecho();
            Item item = nodo.getElemento();
            int precio = item.getPrecio();

            listarRangoPorPrecio(minimo, maximo, izquierdo, lista);

            if (minimo <= precio && precio <= maximo) {
                lista.insertar(item, lista.longitud() + 1);
                NodoItem siguiente = nodo.getSiguiente();

                while (siguiente != null) {
                    lista.insertar(siguiente.getElemento(), lista.longitud() + 1);
                    siguiente = siguiente.getSiguiente();
                }
            }

            listarRangoPorPrecio(minimo, maximo, derecho, lista);
        }
    }

    @Override
    public AVLItems clone() {
        AVLItems clon = new AVLItems();

        if (!esVacio()) {
            clon.raiz = new NodoItem(raiz.getElemento());
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
    private void clonarNodo(NodoItem nodo, NodoItem nodoClon) {
        if (nodo != null) {
            NodoItem hijoIzquierdo = nodo.getIzquierdo();
            NodoItem hijoDerecho = nodo.getDerecho();
            nodoClon.setAltura(nodo.getAltura());

            if (hijoIzquierdo != null) {
                nodoClon.setIzquierdo(new NodoItem(hijoIzquierdo.getElemento()));
                clonarNodo(hijoIzquierdo, nodoClon.getIzquierdo());
            }

            if (hijoDerecho != null) {
                nodoClon.setDerecho(new NodoItem(hijoDerecho.getElemento()));
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
