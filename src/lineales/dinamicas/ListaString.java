package lineales.dinamicas;

/**
 * Implementación de Lista dinámica.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class ListaString {

	/**
	 * El nodo cabecera.
	 */
	private NodoString cabecera;
	
	/**
	 * La cantidad de elementos que contiene la lista.
	 */
	private int longitud;
	
	/**
	 * Crea y devuelve una lista vacía.
	 */
	public ListaString() {
		cabecera = null;
		longitud = 0;
	}
	
	/**
	 * Agrega el elemento pasado por parámetro en la posición pos, de manera que
	 * la cantidad de elementos de la lista se incrementa en 1. Para una
	 * inserción exitosa, la posición recibida debe ser:
	 *   1 ≤ pos ≤ longitud(lista) + 1
	 * Devuelve verdadero si se puede insertar correctamente y falso en caso
	 * contrario.
	 * 
	 * @param elemento
	 * @param posicion
	 * @return
	 */
	public boolean insertar(String elemento, int posicion) {
		boolean resultado = false;
		
		if (1 <= posicion && posicion <= (longitud + 1)) {
			if (longitud == 0) {
				cabecera = new NodoString(elemento);
			} else if (longitud >= 1) {
				NodoString nodoNuevo = new NodoString(elemento);
				
				if (posicion == 1) {
					nodoNuevo.setEnlace(cabecera);
					cabecera = nodoNuevo;
				} else {
					int puntero = 1;
					NodoString nodoPrevio = cabecera;
					
					while (puntero < (posicion - 1)) {
						nodoPrevio = nodoPrevio.getEnlace();
						puntero++;
					}
					
					nodoNuevo.setEnlace(nodoPrevio.getEnlace());
					nodoPrevio.setEnlace(nodoNuevo);
				}
			}
			
			longitud++;
			resultado = true;
		}
		
		return resultado;
	}
	
	/**
	 * Borra el elemento de la posición pos, por lo que la cantidad de elementos
	 * de la lista disminuye en uno. Para una eliminación exitosa, la lista no
	 * debe estar vacía y la posición recibida debe ser:
	 *   1 ≤ pos ≤ longitud(lista)
	 * Devuelve verdadero si se puede eliminar correctamente y falso en caso
	 * contrario.
	 * 
	 * @param pos
	 * @return
	 */
	public boolean eliminar(int pos) {
		boolean resultado = false;
		int i;
		NodoString nodo;
		
		if (1 <= pos && pos <= longitud) {
			if (pos == 1) {
				cabecera = cabecera.getEnlace();
			} else {
				nodo = cabecera;
				i = 1;
				while (i < (pos - 1)) {
					nodo = nodo.getEnlace();
					i++;
				}
				nodo.setEnlace(nodo.getEnlace().getEnlace());
			}
			
			longitud--;
			resultado = true;
		}
		
		return resultado;
	}
	
	/**
	 * Devuelve el elemento de la posición pos. La precondición es que la
	 * posición sea válida.
	 * 
	 * @param pos
	 * @return
	 */
	public String recuperar(int pos) {
		int i;
		String elem = null;
		NodoString nodo;
		
		if (1 <= pos && pos <= longitud) {
			i = 1;
			nodo = cabecera;
			while (i < pos) {
				nodo = nodo.getEnlace();
				i++;
			}
			elem = nodo.getElem();
		}
		
		return elem;
	}
	
	/**
	 * Devuelve la posición en la que se encuentra la primera ocurrencia de elem
	 * dentro de la lista. En caso de no encontrarlo devuelve -1.
	 * 
	 * @param elem
	 * @return
	 */
	public int localizar(int elem) {
		int elemPos = -1, pos = 1;
		NodoString nodo;
		
		if (!esVacia()) {
			nodo = cabecera;
			while (nodo != null && elemPos < 0) {
				if (nodo.getElem().equals(elem)) {
					elemPos = pos;
				} else {
					nodo = nodo.getEnlace();
					pos++;
				}
			}
		}
		
		return pos;
	}
	
	/**
	 * Devuelve la cantidad de elementos de la lista.
	 * 
	 * @return
	 */
	public int longitud() {
		/*
		int longitud = 0;
		NodoInt nodo = cabecera;
		
		while (nodo != null) {
			nodo = nodo.getEnlace();
			longitud++;
		}
		
		return longitud;
		*/
		return longitud;
	}
	
	/**
	 * Quita todos los elementos de la lista.
	 * El manejo de memoria es similar al explicado anteriormente para Cola y
	 * Pila dinámicas.
	 */
	public void vaciar() {
		cabecera = null;
		longitud = 0;
	}
	
	/**
	 * Devuelve verdadero si la lista no tiene elementos y falso en caso
	 * contrario.
	 * 
	 * @return
	 */
	public boolean esVacia() {
		return (cabecera == null);
	}
	
	/**
	 * Crea una copia exacta de la lista original.
	 * 
	 * @return
	 */
	public ListaString clonar() {
		ListaString clon = new ListaString();
		
		if (!esVacia()) {
			int pos = 1;
			NodoString nodo = cabecera;
			while (nodo != null) {
				clon.insertar(nodo.getElem(), pos++);
				nodo = nodo.getEnlace();
			}
		}
		
		return clon;
	}
	
	/**
	 * Crea y devuelve una cadena de caracteres formada por todos los elementos
	 * de la lista para poder mostrarla por pantalla.
	 * Es recomendable utilizar este método únicamente en la etapa de prueba y
	 * luego comentar el código.
	 */
	public String toString() {
		String cadena = "[";
		
		if (!esVacia()) {
			NodoString nodo = cabecera;
			while (nodo != null) {
				cadena+= nodo.getElem()+" ";
				nodo = nodo.getEnlace();
			}
		}
		
		cadena = cadena.trim()+"]";
		
		return cadena;
	}
}