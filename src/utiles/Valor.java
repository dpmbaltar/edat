package utiles;

/**
 * Contenedor un valor de tipo T. Ejemplos:
 *
 * Valor<Integer> entero = new Valor<>(256);
 * Valor<Lista<String>> listaCadenas = new Valor<>(Lista<String>());
 * listaCadenas.getValor().insertar("Cadena", 1);
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 * @param <T>
 */
public class Valor<T> {

    /**
     * El valor de tipo T.
     */
    private T valor;

    /**
     * Constructor vacío.
     */
    public Valor() {
        this(null);
    }

    /**
     * Constructor con el valor de tipo T.
     *
     * @param valor
     */
    public Valor(T valor) {
        this.valor = valor;
    }

    /**
     * Devuelve el valor.
     *
     * @return
     */
    public T getValor() {
        return valor;
    }

    /**
     * Establece el valor.
     *
     * @param valor
     */
    public void setValor(T valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
