package grafos.dinamicas;

/**
 * Implementación de Nodo Vértice.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class NodoVertice<T> {

    private T elemento;
    private NodoAdyacente<T> primerAdyacente;
    private NodoVertice<T> siguienteVertice;

    public NodoVertice(T elemento) {
        this(elemento, null);
    }

    public NodoVertice(T elemento, NodoVertice<T> siguienteVertice) {
        this.elemento = elemento;
        this.siguienteVertice = siguienteVertice;
    }

    public T getElemento() {
        return elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public NodoAdyacente<T> getPrimerAdyacente() {
        return primerAdyacente;
    }

    public void setPrimerAdyacente(NodoAdyacente<T> primerAdyacente) {
        this.primerAdyacente = primerAdyacente;
    }

    public NodoVertice<T> getSiguienteVertice() {
        return siguienteVertice;
    }

    public void setSiguienteVertice(NodoVertice<T> siguienteVertice) {
        this.siguienteVertice = siguienteVertice;
    }
}
