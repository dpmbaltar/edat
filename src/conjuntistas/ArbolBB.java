package conjuntistas;

import jerarquicas.dinamicas.Nodo;
import lineales.dinamicas.Lista;

/**
 * Implementación de Árbol Binario de Búsqueda.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class ArbolBB<T extends Comparable<T>> {

    /**
     * El nodo raíz del árbol.
     */
    private Nodo<T> raiz;

    /**
     * Crea y devuelve un árbol BB vacío.
     */
    public ArbolBB() {
        raiz = null;
    }

    public boolean insertar(T elemento) {
        boolean resultado = false;
        
        return resultado;
    }

    public boolean eliminar(T elemento) {
        boolean resultado = false;
        
        return resultado;
    }

    public boolean pertenece(T elemento) {
        boolean resultado = false;
        
        return resultado;
    }

    public T maximo() {
        T elementoMaximo = null;
        
        return elementoMaximo;
    }

    public T minimo() {
        T elementoMinimo = null;
        
        return elementoMinimo;
    }

    public boolean vacio() {
        return raiz == null;
    }

    public void vaciar() {
        raiz = null;
    }

    public Lista<T> listar() {
        Lista<T> lista = new Lista<T>();
        
        return lista;
    }

    public Lista<T> listarRango(T minimo, T maximo) {
        Lista<T> lista = new Lista<T>();
        
        return lista;
    }

    public ArbolBB<T> clonar() {
        ArbolBB<T> clon = new ArbolBB<T>();
        
        return clon;
    }

    public String toString() {
        StringBuilder cadena = new StringBuilder();
        cadena.append('[');
        
        cadena.append(']');
        return cadena.toString();
    }
}
