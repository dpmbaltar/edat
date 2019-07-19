package conjuntistas;

import jerarquicas.Nodo;
import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 * Implementación de Árbol AVL.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBB<T> {

    /**
     * Inserta un elemento en el árbol.
     */
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
     * @param elemento
     * @param nodo
     * @param padre
     * @return
     */
    private boolean insertar(T elemento, Nodo<T> nodo, Nodo<T> padre) {
        boolean insertado = false;

        if (nodo != null) {
            Nodo<T> izquierdo, derecho, nuevo;
            izquierdo = nodo.getIzquierdo();
            derecho = nodo.getDerecho();

            // Si el elemento es menor al del nodo, insertar en el sub-árbol
            // izquierdo. Si el elemento es mayor al del nodo, insertar en el
            // sub-árbol derecho. Si el elemento es igual al del nodo, el
            // resultado será falso y no continuará hacia los nodos hijos.
            if (elemento.compareTo(nodo.getElemento()) < 0) {
                if (izquierdo == null) {
                    nuevo = new Nodo<T>(elemento);
                    nodo.setIzquierdo(nuevo);
                    if (derecho == null)
                        nodo.setAltura(1);
                    insertado = true;
                } else {
                    insertado = insertar(elemento, izquierdo, nodo);
                }
            } else if (elemento.compareTo(nodo.getElemento()) > 0) {
                if (derecho == null) {
                    nuevo = new Nodo<T>(elemento);
                    nodo.setDerecho(nuevo);
                    if (izquierdo == null)
                        nodo.setAltura(1);
                    insertado = true;
                } else {
                    insertado = insertar(elemento, derecho, nodo);
                }
            }

            // Balancear si es necesario
            if (insertado) {
                actualizarAltura(padre);
                balancear(nodo, padre);
                /*Nodo<T> rotado = null;
                boolean balanceado = false;
                int alturaIzquierdo, alturaDerecho, balance, balanceHijo;
                alturaIzquierdo = alturaDerecho = -1;
                izquierdo = nodo.getIzquierdo();
                derecho = nodo.getDerecho();

                if (izquierdo != null)
                    alturaIzquierdo = izquierdo.getAltura();
                if (derecho != null)
                    alturaDerecho = derecho.getAltura();
                nodo.setAltura(Math.max(alturaIzquierdo, alturaDerecho) + 1);

                balance = alturaIzquierdo - alturaDerecho;
                // System.out.println("nodo: "+nodo.getElemento()+" balance:
                // "+balance);
                // Aplicar rotaciones, según corresponda
                if (balance == 2) {
                    balanceHijo = balance(izquierdo);
                    if (balanceHijo == 1)
                        rotado = rotarDerecha(nodo);
                    else if (balanceHijo == -1)
                        rotado = rotarIzquierdaDerecha(nodo);
                    balanceado = true;
                } else if (balance == -2) {
                    balanceHijo = balance(derecho);
                    if (balanceHijo == -1)
                        rotado = rotarIzquierda(nodo);
                    else if (balanceHijo == 1)
                        rotado = rotarDerechaIzquierda(nodo);
                    balanceado = true;*/
                }

                // Actualizar enlace del padre al nodo rotado, si corresponde
                /*if (balanceado) {
                    if (padre == null) {
                        raiz = rotado;
                    } else {
                        Nodo<T> hermano = null;
                        T elementoPadre = padre.getElemento();
                        if (elemento.compareTo(elementoPadre) < 0) {
                            padre.setIzquierdo(rotado);
                            hermano = padre.getDerecho();
                        } else if (elemento.compareTo(elementoPadre) > 0) {
                            padre.setDerecho(rotado);
                            hermano = padre.getIzquierdo();
                        }

//                        // Actualizar altura del padre
//                        if (hermano == null)
//                            padre.setAltura(rotado.getAltura() + 1);
//                        else
//                            padre.setAltura(Math.max(rotado.getAltura(),
//                                    hermano.getAltura()) + 1);
                    }
                }*/
            }

        return insertado;
    }

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
        boolean eliminado = super.eliminar(elemento);
        if (eliminado)
            balancear();

        return eliminado;
    }

    /**
     * Balancea el árbol.
     */
    private void balancear() {
        balancear(raiz, null);
    }

    /**
     * Balancea el sub-árbol correspondiente al nodo dado.
     *
     * @param nodo
     * @param padre
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

                if (balanceHijo == 1) {
                    reemplazo = rotarDerecha(nodo);
                } else if (balanceHijo == -1) {
                    reemplazo = rotarIzquierdaDerecha(nodo);
                }/* else if (balanceHijo > 1 || balanceHijo < -1) {
                    balancear(nodoHijo, nodo);
                }*/

                balanceado = true;
            } else if (balanceNodo == -2) {
                nodoHijo = nodo.getDerecho();
                balanceHijo = balance(nodoHijo);

                if (balanceHijo == 1) {
                    reemplazo = rotarDerechaIzquierda(nodo);
                } else if (balanceHijo == -1) {
                    reemplazo = rotarIzquierda(nodo);
                }/* else if (balanceHijo > 1 || balanceHijo < -1) {
                    balancear(nodoHijo, nodo);
                }*/

                balanceado = true;
            }

            // Reemplazar el nodo correspondiente al padre, si ha sido rotado
            /*if (!balanceado) {
                // Balancear nodos hijos
                balancear(nodo.getIzquierdo(), nodo);
                balancear(nodo.getDerecho(), nodo);
            } else if (reemplazo != null) {*/
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
     * Utilizar jerarquicas.Nodo<T>.getAltura()
     *
     * @param nodo el nodo a calcular su altura
     * @return la altura del nodo
     */
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
     * Aplica una rotación simple a la derecha al sub-árbol correspondiente al
     * nodo dado, y devuelve el nuevo nodo raíz del sub-árbol.
     *
     * @param nodo
     * @return
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
     * Aplica una rotación simple a la izquierda al sub-árbol correspondiente al
     * nodo dado, y devuelve el nuevo nodo raíz del sub-árbol.
     *
     * @param nodo
     * @return
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
     * Aplica una rotación doble derecha-izquierda al sub-árbol correspondiente
     * al nodo dado, y devuelve el nuevo nodo raíz del sub-árbol.
     *
     * @param nodo
     * @return
     */
    private Nodo<T> rotarDerechaIzquierda(Nodo<T> nodo) {
        Nodo<T> rotado = rotarDerecha(nodo.getDerecho());
        nodo.setDerecho(rotado);

        return rotarIzquierda(nodo);
    }

    /**
     * Aplica una rotación doble izquierda-derecha al sub-árbol correspondiente
     * al nodo dado, y devuelve el nuevo nodo raíz del sub-árbol.
     *
     * @param nodo
     * @return
     */
    private Nodo<T> rotarIzquierdaDerecha(Nodo<T> nodo) {
        Nodo<T> rotado = rotarIzquierda(nodo.getIzquierdo());
        nodo.setIzquierdo(rotado);

        return rotarDerecha(nodo);
    }

    /**
     * Devuelve una lista por niveles con los elementos del árbol.
     *
     * @return
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
                    if (hijoIzquierdo != null)
                        cola.poner(hijoIzquierdo);
                    if (hijoDerecho != null)
                        cola.poner(hijoDerecho);
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
     * @return
     */
    public Lista<Lista<T>> listarNiveles2() {
        Lista<Lista<T>> lista = new Lista<>();

        if (raiz != null) {
            int nivelMaxElementos = 1;
            Nodo<T> nodo;
            Lista<T> nivel = new Lista<T>();
            Cola<Nodo<T>> cola = new Cola<Nodo<T>>();
            cola.poner(raiz);

            while (!cola.esVacia()) {
                nodo = cola.obtenerFrente();
                cola.sacar();

                if (nodo != null) {
                    nivel.insertar(nodo.getElemento(), nivel.longitud() + 1);
                    cola.poner(nodo.getIzquierdo());
                    cola.poner(nodo.getDerecho());
                } else {
                    nivel.insertar(null, nivel.longitud() + 1);
                }

                if (nivel.longitud() == nivelMaxElementos) {
                    lista.insertar(nivel, lista.longitud() + 1);
                    nivel = new Lista<>();
                    nivelMaxElementos*= 2;
                }
            }
        }

        return lista;
    }

    @Override
    public ArbolAVL<T> clonar() {
        ArbolAVL<T> clon = new ArbolAVL<T>();
        clonar(raiz, clon);

        return clon;
    }

    private void clonar(Nodo<T> nodo, ArbolAVL<T> arbol) {
        if (nodo != null) {
            Nodo<T> izquierdo = nodo.getIzquierdo();
            Nodo<T> derecho = nodo.getDerecho();
            arbol.insertar(nodo.getElemento());
            clonar(izquierdo, arbol);
            clonar(derecho, arbol);
        }
    }

}
