package grafos.dinamicas;

/**
 * Implementación de Nodo Adyacente.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class NodoAdyacente<T> {

    private NodoVertice<T> vertice;
    private NodoAdyacente<T> siguienteAdyacente;

    public NodoAdyacente() {
        this(null, null);
    }

    public NodoAdyacente(NodoVertice<T> vertice) {
        this(vertice, null);
    }

    public NodoAdyacente(NodoVertice<T> vertice, NodoAdyacente<T> siguienteAdyacente) {
        this.vertice = vertice;
        this.siguienteAdyacente = siguienteAdyacente;
    }

    public NodoVertice<T> getVertice() {
        return vertice;
    }

    public void setVertice(NodoVertice<T> vertice) {
        this.vertice = vertice;
    }

    public NodoAdyacente<T> getSiguienteAdyacente() {
        return siguienteAdyacente;
    }

    public void setSiguienteAdyacente(NodoAdyacente<T> siguienteAdyacente) {
        this.siguienteAdyacente = siguienteAdyacente;
    }
}
