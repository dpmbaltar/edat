package lineales.dinamicas;

/**
 * Implementación de Lista dinámica.
 * 
 * @author Diego P. M. Baltar <www.dpmbaltar.com.ar>
 */
public class ListaInt {

	/**
	 * El nodo cabecera.
	 */
	private NodoInt cabecera;
	
	/**
	 * La cantidad de elementos que contiene la lista.
	 */
	private int longitud;
	
	/**
	 * Crea y devuelve una lista vacía.
	 */
	public ListaInt() {
		this.cabecera = null;
		this.longitud = 0;
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
	public boolean insertar(int elemento, int posicion) {
		boolean resultado = false;
		
		if (1 <= posicion && posicion <= (this.longitud + 1)) {
			if (this.longitud == 0) {
				this.cabecera = new NodoInt(elemento);
			} else if (this.longitud >= 1) {
				NodoInt nodoNuevo = new NodoInt(elemento);
				
				if (posicion == 1) {
					nodoNuevo.setEnlace(this.cabecera);
					this.cabecera = nodoNuevo;
				} else {
					int puntero = 1;
					NodoInt nodoPrevio = this.cabecera;
					
					while (puntero < (posicion - 1)) {
						nodoPrevio = nodoPrevio.getEnlace();
						puntero++;
					}
					
					nodoNuevo.setEnlace(nodoPrevio.getEnlace());
					nodoPrevio.setEnlace(nodoNuevo);
				}
			}
			
			this.longitud++;
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
		NodoInt nodo;
		
		if (1 <= pos && pos <= this.longitud) {
			if (pos == 1) {
				this.cabecera = this.cabecera.getEnlace();
			} else {
				nodo = this.cabecera;
				i = 1;
				while (i < (pos - 1)) {
					nodo = nodo.getEnlace();
					i++;
				}
				nodo.setEnlace(nodo.getEnlace().getEnlace());
			}
			
			this.longitud--;
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
	public int recuperar(int pos) {
		int i, elem = 0;
		NodoInt nodo;
		
		if (1 <= pos && pos <= this.longitud) {
			i = 1;
			nodo = this.cabecera;
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
		int elemPos = -1, pos = 0;
		NodoInt nodo;
		
		if (!this.esVacia()) {
			nodo = this.cabecera;
			while (nodo != null && elemPos < 0) {
				if (nodo.getElem() == elem) {
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
		NodoInt nodo = this.cabecera;
		
		while (nodo != null) {
			nodo = nodo.getEnlace();
			longitud++;
		}
		
		return longitud;
		*/
		return this.longitud;
	}
	
	/**
	 * Quita todos los elementos de la lista.
	 * El manejo de memoria es similar al explicado anteriormente para Cola y
	 * Pila dinámicas.
	 */
	public void vaciar() {
		this.cabecera = null;
	}
	
	/**
	 * Devuelve verdadero si la lista no tiene elementos y falso en caso
	 * contrario.
	 * 
	 * @return
	 */
	public boolean esVacia() {
		return (this.cabecera == null);
	}
	
	/**
	 * Crea una copia exacta de la lista original.
	 * 
	 * @return
	 */
	public ListaInt clonar() {
		ListaInt clon = new ListaInt();
		
		
		
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
			NodoInt nodo = cabecera;
			while (nodo != null) {
				cadena+= nodo.getElem()+" ";
				nodo = nodo.getEnlace();
			}
		}
		
		cadena = cadena.trim()+"]";
		
		return cadena;
	}
}
