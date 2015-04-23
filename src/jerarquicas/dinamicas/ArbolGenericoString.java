package jerarquicas.dinamicas;

import lineales.dinamicas.ListaInt;
import lineales.dinamicas.ListaString;

/**
 * Implementación dinámica de Árbol Genérico.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class ArbolGenericoString {

	/**
	 * El nodo raíz del árbol.
	 */
	private NodoArbolString raiz;
	
	/**
	 * Crea y devuelve un árbol genérico vacío.
	 */
	public ArbolGenericoString() {
		raiz = null;
	}
	
	/**
	 * Inserta un nuevo elemento al árbol.
	 * 
	 * @param elem
	 * @param padre
	 * @return
	 */
	public boolean insertar(String elem, String padre) {
		boolean resultado = false;
		NodoArbolString nodoPadre = buscarNodo(padre),
						nodoNuevo,
						nodoAuxiliar;
		
		if (esVacio()) {
			nodoNuevo = new NodoArbolString(elem);
			resultado = true;
		} else if (nodoPadre != null) {
			nodoNuevo = new NodoArbolString(elem);
			nodoAuxiliar = nodoPadre.getIzquierdo();
			// Agregar nuevo nodo al principio, si el nodo padre ya tiene hijos
			if (nodoAuxiliar != null) {
				nodoNuevo.setDerecho(nodoAuxiliar);
			}
			nodoPadre.setIzquierdo(nodoNuevo);
			resultado = true;
		}
		
		return resultado;
	}
	
	/**
	 * Busca un nodo en forma recursiva con elemento "elem" a partir del nodo
	 * raíz del árbol.
	 * 
	 * @param elem
	 * @return
	 */
	private NodoArbolString buscarNodo(String elem) {
		return buscarNodo(elem, raiz);
	}
	
	/**
	 * Busca un nodo en forma recursiva con elemento "elem" a partir de un nodo
	 * específico.
	 * 
	 * @param elem
	 * @param nodo
	 * @return
	 */
	private NodoArbolString buscarNodo(String elem, NodoArbolString nodo) {
		NodoArbolString buscado = null, hermano;
		
		if (nodo != null) {
			if (nodo.getElemento().equals(elem)) {
				buscado = nodo;
			} else {
				hermano = nodo.getDerecho();
				while (hermano != null) {
					if (hermano.getElemento().equals(elem)) {
						buscado = hermano;
						hermano = null;
					} else {
						buscado = buscarNodo(elem, buscado.getIzquierdo());
					}
				}
			}
		}
		
		return buscado;
	}
	
	/**
	 * Devuelve verdadero si el árbol no tiene elementos, de lo contrario
	 * devuelve falso.
	 * 
	 * @return
	 */
	public boolean esVacio() {
		return (raiz == null);
	}
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	public boolean pertenece(String elem) {
		boolean resultado = false;
		
		if (!esVacio()) {
			//TODO
		}
		
		return resultado;
	}
	
	/**
	 * Devuelve la altura del árbol.
	 * 
	 * @return
	 */
	public int altura() {
		int altura = 0;
		
		if (!esVacio()) {
			//TODO
		}
		
		return altura;
	}
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	public int nivel(String elem) {
		int nivelElem = -1;
		
		if (!esVacio()) {
			//TODO
		}
		
		return nivelElem;
	}
	
	/**
	 * Devuelve el elemento padre de un elemento dado.
	 * 
	 * @param elem
	 * @return
	 */
	public String padre(String elem) {
		String padre = null;
		
		if (!esVacio()) {
			//TODO
		}
		
		return padre;
	}
	
	public ListaString ancestros(String elem) {
		ListaString lista = new ListaString();
		
		if (!esVacio()) {
			//TODO
		}
		
		return lista;
	}
	
	private void preorden(NodoArbolString nodo, ListaString lista) {
		NodoArbolString izquierdo = nodo.getIzquierdo(),
						derecho = nodo.getDerecho();
		
		lista.insertar(nodo.getElemento(), (lista.longitud() + 1));
		
		if (izquierdo != null) {
			preorden(izquierdo, lista);
		}
		
		while (derecho != null) {
			preorden(derecho, lista);
			derecho = derecho.getDerecho();
		}
	}
	
	public ListaString listarPreorden() {
		ListaString lista = new ListaString();
		
		if (!esVacio()) {
			preorden(raiz, lista);
		}
		
		return lista;
	}
	
	public ListaString listarInorden() {
		ListaString lista = new ListaString();
		
		if (!esVacio()) {
			//TODO
		}
		
		return lista;
	}
	
	public ListaString listarPosorden() {
		ListaString lista = new ListaString();
		
		if (!esVacio()) {
			//TODO
		}
		
		return lista;
	}
	
	public ListaString listarNiveles() {
		ListaString lista = new ListaString();
		
		if (!esVacio()) {
			//TODO
		}
		
		return lista;
	}
	
	public ArbolGenericoString clonar() {
		ArbolGenericoString clon = new ArbolGenericoString();
		
		if (!esVacio()) {
			//TODO
		}
		
		return clon;
	}
	
	/**
	 * 
	 */
	public String toString() {
		int i;
		String cadena = "";
		NodoArbolString nodo;
		
		if (!esVacio()) {
			nodo = raiz;
			while (nodo != null) {
				
			}
		}
		
		return cadena;
	}
}
