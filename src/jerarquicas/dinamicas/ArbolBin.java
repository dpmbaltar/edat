package jerarquicas.dinamicas;

import java.util.LinkedList;
import java.util.Queue;

import lineales.dinamicas.ListaInt;

/**
 * Implementación dinámica de Árbol Binaro.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class ArbolBin {

	private NodoArbol raiz;
	
	public ArbolBin() {
		raiz = null;
	}
	
	/**
	 * Busca y retorna el nodo del elemento especificado.
	 * 
	 * @param elemento
	 * @return
	 */
	private NodoArbol buscarNodo(int elemento) {
		return buscarNodo(elemento, raiz);
	}
	
	private NodoArbol buscarNodo(int elemento, NodoArbol nodo) {
		NodoArbol buscado = null;
		
		if (nodo != null) {
			if (nodo.getElemento() == elemento) {
				buscado = nodo;
			} else {
				buscado = buscarNodo(elemento, nodo.getIzquierdo());
				if (buscado == null) {
					buscado = buscarNodo(elemento, nodo.getDerecho());
				}
			}
		}
		
		return buscado;
	}
	
	public boolean insertarRaiz(int elemento) {
		return insertar(elemento, 0, 'R');
	}
	
	/**
	 * 
	 * @param elementoNuevo
	 * @param elementoPadre
	 * @param posicion
	 * @return
	 */
	public boolean insertar(int elementoNuevo,
							int elementoPadre,
							char posicion) {
		boolean resultado = false;
		
		if (esVacio()) {
			raiz = new NodoArbol(elementoNuevo);
			resultado = true;
		} else {
			NodoArbol padre = buscarNodo(elementoPadre);
			if (padre != null) {
				if (posicion == 'I' && padre.getIzquierdo() == null) {
					padre.setIzquierdo(new NodoArbol(elementoNuevo));
					resultado = true;
				} else if (posicion == 'D' && padre.getDerecho() == null) {
					padre.setDerecho(new NodoArbol(elementoNuevo));
					resultado = true;
				}
			}
		}
		
		return resultado;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean esVacio() {
		return (raiz == null);
	}
	
	/**
	 * 
	 */
	public void vaciar() {
		raiz = null;
	}
	
	public int altura() {
		int altura = -1;
		
		if (!esVacio()) {
			
		}
		
		return altura;
	}
	
	public int nivel(int elemento) {
		int nivel = -1;
		
		if (!esVacio()) {
			
		}
		
		return nivel;
	}
	
	public int padre(int elemento) {
		int elementoPadre = 0;
		
		if (!esVacio()) {
			
		}
		
		return elementoPadre;
	}
	
	/**
	 * Completa la lista con los elementos recorridos en pre-orden.
	 * 
	 * @param nodo
	 * @param lista
	 */
	private void preorden(NodoArbol nodo, ListaInt lista) {
		NodoArbol izquierdo = nodo.getIzquierdo(),
					derecho = nodo.getDerecho();
		
		lista.insertar(nodo.getElemento(), (lista.longitud() + 1));
		
		if (izquierdo != null) {
			preorden(izquierdo, lista);
		}
		
		if (derecho != null) {
			preorden(derecho, lista);
		}
	}
	
	/**
	 * Retorna lista de los elementos recorridos en pre-orden.
	 * 
	 * @return
	 */
	public ListaInt listarPreorden() {
		ListaInt lista = new ListaInt();
		
		if (!esVacio()) {
			preorden(raiz, lista);
		}
		
		return lista;
	}
	
	/**
	 * Completa la lista con los elementos recorridos en in-orden.
	 * 
	 * @param nodo
	 * @param lista
	 * @return
	 */
	private void inorden(NodoArbol nodo, ListaInt lista) {
		NodoArbol izquierdo = nodo.getIzquierdo(),
					derecho = nodo.getDerecho();
		
		if (izquierdo != null) {
			inorden(izquierdo, lista);
		}
		
		lista.insertar(nodo.getElemento(), (lista.longitud() + 1));
		
		if (derecho != null) {
			inorden(derecho, lista);
		}
	}
	
	/**
	 * Retorna lista de los elementos recorridos en in-orden.
	 * 
	 * @return
	 */
	public ListaInt listarInorden() {
		ListaInt lista = new ListaInt();
		
		if (!esVacio()) {
			inorden(raiz, lista);
		}
		
		return lista;
	}
	
	/**
	 * Completa la lista con los elementos recorridos en pos-orden.
	 * 
	 * @param nodo
	 * @param lista
	 * @return
	 */
	private void posorden(NodoArbol nodo, ListaInt lista) {
		NodoArbol izquierdo = nodo.getIzquierdo(),
					derecho = nodo.getDerecho();
		
		if (izquierdo != null) {
			posorden(izquierdo, lista);
		}
		
		if (derecho != null) {
			posorden(derecho, lista);
		}
		
		lista.insertar(nodo.getElemento(), (lista.longitud() + 1));	
	}
	
	/**
	 * Retorna lista de los elementos recorridos en pos-orden.
	 * 
	 * @return
	 */
	public ListaInt listarPosorden() {
		ListaInt lista = new ListaInt();
		
		if (!esVacio()) {
			posorden(raiz, lista);
		}
		
		return lista;
	}
	
	public ListaInt listarNiveles() {
		ListaInt lista = new ListaInt();
		
		if (!esVacio()) {
			NodoArbol nodo, nodoIzquierdo, nodoDerecho;
			LinkedList<NodoArbol> cola = new LinkedList<NodoArbol>();
			cola.addFirst(raiz);
			while (!cola.isEmpty()) {
				nodo = cola.pollFirst();
				lista.insertar(nodo.getElemento(), (lista.longitud() + 1));
				nodoIzquierdo = nodo.getIzquierdo();
				nodoDerecho = nodo.getDerecho();
				
				if (nodoIzquierdo != null) {
					cola.addLast(nodoIzquierdo);
				}
				
				if (nodoDerecho != null) {
					cola.addLast(nodoDerecho);
				}
			}
		}
		
		return lista;
	}
	
	public ArbolBin clonar() {
		ArbolBin clon = new ArbolBin();
		
		if (!esVacio()) {
			
		}
		
		return clon;
	}
	
	public String toString() {
		return listarNiveles().toString();
	}
}
