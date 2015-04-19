package lineales.dinamicas;

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
	
	public boolean insertar(int elemNuevo, int elemPadre, char pos) {
		boolean resultado = false;
		
		
		
		return resultado;
	}
	
	public boolean esVacio() {
		return (raiz == null);
	}
	
	public void vaciar() {
		raiz = null;
	}
	
	public int altura() {
		int altura = -1;
		
		if (!esVacio()) {
			
		}
		
		return altura;
	}
	
	public int nivel(int elem) {
		int nivel = -1;
		
		if (!esVacio()) {
			
		}
		
		return nivel;
	}
	
	public int padre(int elem) {
		int elemPadre = 0;
		
		if (!esVacio()) {
			
		}
		
		return elemPadre;
	}
	
	public ListaInt listarPreorden() {
		ListaInt lista = new ListaInt();
		
		if (!esVacio()) {
			
		}
		
		return lista;
	}
	
	public ListaInt listarInorden() {
		ListaInt lista = new ListaInt();
		
		if (!esVacio()) {
			
		}
		
		return lista;
	}
	
	public ListaInt listarPosorden() {
		ListaInt lista = new ListaInt();
		
		if (!esVacio()) {
			
		}
		
		return lista;
	}
	
	public ListaInt listarNiveles() {
		ListaInt lista = new ListaInt();
		
		if (!esVacio()) {
			
		}
		
		return lista;
	}
	
	public ArbolBin clonar() {
		ArbolBin clon = new ArbolBin();
		
		
		
		return clon;
	}
	
	public String toString() {
		String cadena = "[";
		
		
		
		return cadena;
	}
}
