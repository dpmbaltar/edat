package utiles;

/**
 * Contenedor un valor de tipo T. Ejemplos:
 * <pre>
 * Valor<Integer> entero = new Valor<>(256);
 * Valor<Lista<String>> listaCadenas = new Valor<>(Lista<String>());
 * listaCadenas.getValor().insertar("Cadena", 1);
 * </pre>
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 * @param <T> el tipo del valor
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
     * @param valor el valor
     */
    public Valor(T valor) {
        this.valor = valor;
    }

    /**
     * Devuelve el valor.
     *
     * @return el valor
     */
    public T getValor() {
        return valor;
    }

    /**
     * Establece el valor.
     *
     * @param valor el valor
     */
    public void setValor(T valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
