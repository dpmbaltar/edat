package grafos.dinamicas;

/**
 * Implementación de Nodo Adyacente.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class NodoAdyacente<T> {

    private NodoAdyacente<T> vertice;
    private NodoVertice<T> siguienteAdyacente;

    public NodoAdyacente<T> getVertice() {
        return vertice;
    }

    public void setVertice(NodoAdyacente<T> vertice) {
        this.vertice = vertice;
    }

    public NodoVertice<T> getSiguienteAdyacente() {
        return siguienteAdyacente;
    }

    public void setSiguienteAdyacente(NodoVertice<T> siguienteAdyacente) {
        this.siguienteAdyacente = siguienteAdyacente;
    }
}
