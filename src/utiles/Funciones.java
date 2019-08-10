package utiles;

/**
 * Implementación de funciones de utilidad.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Funciones {

    /**
     * Suma los caracteres de una cadena y devuelve la suma si es menor a m, o (suma mod m) si es mayor a m.
     *
     * @param s la cadena de entrada
     * @param m el tamaño de la tabla
     * @return el resultado
     */
    public static int sumaCaracteres(String s, int m) {
        int suma = 0;
        char[] caracteres = s.toCharArray();

        for (int i = 0; i < caracteres.length; i++) {
            suma += caracteres[i];
        }

        return suma % m;
    }

    /**
     * Implementación de función "Doblamiento" para Tabla Hash.
     *
     * @param n el entero
     * @param m el tamaño de la tabla
     * @return el entero resultante
     */
    public static int doblamiento(int n, int m) {
        int suma = n;
        int digitos = digitosDec(n);
        int divisor = digitosDec(m);

        if (digitos >= divisor) {
            int desde = 0;
            int parte = digitos / divisor;
            int resto = digitos % divisor;
            suma = 0;

            for (int i = parte; i <= (digitos - resto); i += parte) {
                suma += subintDec(n, desde, i);
                desde = i;
            }

            if (resto > 0) {
                desde = digitos - resto;
                suma += subintDec(n, desde, digitos);
            }
        }

        return suma % m;
    }

    /**
     * Consiste en elevar al cuadrado la clave y luego tomar los dígitos centrales como dirección. El número de
     * dígitos a tomar queda determinado por el tamaño M.
     *
     * @param n el entero
     * @param m el tamaño de la tabla
     * @return los dígitos centrales según n y m dados
     */
    public static int digitosCentrales(int n, int m) {
        int cuadrado = n * n;
        int resultado = cuadrado;

        if (cuadrado >= m && m > 0) {
            int maximosDigitos = digitosDec(m - 1);
            int desde = (digitosDec(cuadrado) / 2) - (maximosDigitos / 2);
            int hasta = desde + (maximosDigitos / 2 + maximosDigitos % 2) + 1;
            resultado = subintDec(cuadrado, desde, hasta);
        }

        return resultado % m;
    }

    /**
     * Devuelve primo menos el entero MOD primo.
     *
     * @param n el entero
     * @param p el número primo
     * @param m el tamaño de la tabla
     * @return el resultado
     */
    public static int modPrimo(int n, int p, int m) {
        return (p - (n % p)) % m;
    }

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

    /**
     * Igual que {@link utiles.Funciones#subint(int, int, int, int)} con base binaria hasta el último dígito.
     *
     * @param binario el binario
     * @param desde la posición desde (inclusive)
     * @return el entero resultante según desde
     */
    public static int subintBin(int binario, int desde) {
        return subint(binario, desde, digitosBin(binario), 0b10);
    }

    /**
     * Igual que {@link utiles.Funciones#subint(int, int, int, int)} con base binaria.
     *
     * @param binario el binario
     * @param desde la posición desde (inclusive)
     * @param hasta la posición desde (exclusive)
     * @return el entero resultante según desde y hasta
     */
    public static int subintBin(int binario, int desde, int hasta) {
        return subint(binario, desde, hasta, 0b10);
    }

    /**
     * Igual que {@link utiles.Funciones#subint(int, int, int, int)} con base decimal hasta el último dígito.
     *
     * @param decimal el decimal
     * @param desde la posición desde (inclusive)
     * @return el entero resultante según desde
     */
    public static int subintDec(int decimal, int desde) {
        return subint(decimal, desde, digitosDec(decimal), 10);
    }

    /**
     * Igual que {@link utiles.Funciones#subint(int, int, int, int)} con base decimal.
     *
     * @param decimal el decimal
     * @param desde la posición desde (inclusive)
     * @param hasta la posición desde (exclusive)
     * @return el entero resultante según desde y hasta
     */
    public static int subintDec(int decimal, int desde, int hasta) {
        return subint(decimal, desde, hasta, 10);
    }

    /**
     * Igual que {@link utiles.Funciones#subint(int, int, int, int)} con base hexadecimal hasta el último dígito.
     *
     * @param hexadecimal el hexadecimal
     * @param desde la posición desde (inclusive)
     * @return el entero resultante según desde
     */
    public static int subintHex(int hexadecimal, int desde) {
        return subint(hexadecimal, desde, digitosHex(hexadecimal), 0x10);
    }

    /**
     * Igual que {@link utiles.Funciones#subint(int, int, int, int)} con base hexadecimal.
     *
     * @param hexadecimal el hexadecimal
     * @param desde la posición desde (inclusive)
     * @param hasta la posición desde (exclusive)
     * @return el entero resultante según desde y hasta
     */
    public static int subintHex(int hexadecimal, int desde, int hasta) {
        return subint(hexadecimal, desde, hasta, 0x10);
    }

    /**
     * Igual que {@link utiles.Funciones#subint(int, int, int, int)} con base octal hasta el último dígito.
     *
     * @param octal el octal
     * @param desde la posición desde (inclusive)
     * @return el entero resultante según desde
     */
    public static int subintOct(int octal, int desde) {
        return subint(octal, desde, digitosOct(octal), 010);
    }

    /**
     * Igual que {@link utiles.Funciones#subint(int, int, int, int)} con base octal.
     *
     * @param octal el octal
     * @param desde la posición desde (inclusive)
     * @param hasta la posición desde (exclusive)
     * @return el entero resultante según desde y hasta
     */
    public static int subintOct(int octal, int desde, int hasta) {
        return subint(octal, desde, hasta, 010);
    }

    /**
     * Devuelve una porción de dígitos de un entero (sin contar el signo) al igual que String.substring(), pero con
     * enteros como entrada, como por ejemplo: <code>Funciones.subint(123456789, 1, 3, 10) // 23</code>
     * Precondición: 0 &lt;= desde &lt; hasta &lt;= digitos
     *
     * @param numero el número entero
     * @param desde la posición desde (inclusive)
     * @param hasta la posición hasta (exclusive)
     * @param base la base del número entero
     * @return el entero resultante según se indique desde y hasta, 0 si desde y hasta son incorrectos
     */
    public static int subint(int numero, int desde, int hasta, int base) {
        int signo = numero >= 0 ? 1 : 0;
        int subentero = numero >= 0 ? numero : -numero;
        int digitos = digitos(numero, base);

        if (desde >= 0 && hasta >= 0 && hasta > desde && hasta <= digitos) {
            subentero -= numero % (int) (Math.pow(base, Math.abs(digitos - hasta)));
            subentero /= (int) (Math.pow(base, Math.abs(digitos - hasta)));
            subentero %= (int) (Math.pow(base, digitos(subentero, base) - Math.abs(desde)));
        } else {
            subentero = 0;
        }

        return signo * subentero;
    }
}
