package utiles;

/**
 * Implementación de funciones de utilidad.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Funciones {

    /**
     * Devuelve la cantidad de dígitos binarios de un número.
     *
     * @param binario el número a calcular la cantidad de dígitos
     * @return la cantidad de dígitos binarios
     */
    public static int digitosBin(int binario) {
        return digitos(binario, 0b10);
    }

    /**
     * Devuelve la cantidad de dígitos decimales de un número.
     *
     * @param decimal el número a calcular la cantidad de dígitos
     * @return la cantidad de dígitos decimales
     */
    public static int digitosDec(int decimal) {
        return digitos(decimal, 10);
    }

    /**
     * Devuelve la cantidad de dígitos hexadecimales de un número.
     *
     * @param hexadecimal el número a calcular la cantidad de dígitos
     * @return la cantidad de dígitos decimales
     */
    public static int digitosHex(int hexadecimal) {
        return digitos(hexadecimal, 0x10);
    }

    /**
     * Devuelve la cantidad de dígitos octales de un número.
     *
     * @param octal el número a calcular la cantidad de dígitos
     * @return la cantidad de dígitos decimales
     */
    public static int digitosOct(int octal) {
        return digitos(octal, 010);
    }

    /**
     * Devuelve la cantidad de dígitos que tiene un número representado bajo una base dada.
     *
     * @param numero el número a calcular la cantidad de dígitos
     * @param base la base (binaria, octal, decimal o hexadecimal)
     * @return la cantidad de dígitos
     */
    public static int digitos(int numero, int base) {
        int digitos = 1;
        int absoluto = numero >= 0 ? numero : -numero;

        while (absoluto >= base) {
            absoluto /= base;
            digitos++;
        }

        return digitos;
    }

    public static int subintDec(int decimal, int desde, int hasta) {
        return subint(decimal, desde, hasta, 10);
    }

    /**
     * Devuelve una porción de dígitos de un entero (sin contar el signo) al igual que String.substring(), pero con
     * enteros como entrada, como por ejemplo: <code>Funciones.subint(123456789, 1, 3, 10) // 23</code>
     *
     * @param numero el número entero
     * @param desde la posición desde (inclusive)
     * @param hasta la posición hasta (exclusive)
     * @param base la base del número entero
     * @return el entero resultante según se indique desde y hasta
     */
    public static int subint(int numero, int desde, int hasta, int base) {
        int signo = numero >= 0 ? 1 : 0;
        int subentero = numero >= 0 ? numero : -numero;

        if (desde > 0 && hasta > 0 && desde < hasta) {
            subentero -= numero % (int) (Math.pow(base, digitos(numero, base) - Math.abs(hasta)));
            subentero /= (int) (Math.pow(base, digitos(numero, base) - Math.abs(hasta)));
            subentero %= (int) (Math.pow(base, digitos(subentero, base) - Math.abs(desde)));
        }

        return signo * subentero;
    }

    public static int doblamiento(int elem, int tam) {
        int suma = elem;
        int digitos = digitosDec(elem);
        int divisor = digitosDec(tam);

        if (digitos >= divisor) {
            suma = elem;
        }

        return suma;
    }
}
