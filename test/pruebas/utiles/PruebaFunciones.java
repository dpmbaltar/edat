package pruebas.utiles;

import lineales.dinamicas.Lista;
import utiles.Funciones;

/**
 * Prueba implementación de Funciones.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class PruebaFunciones {

    /**
     * Constructor ejecuta todas las pruebas de la clase.
     */
    public PruebaFunciones() {
        pruebaDigitosBin();
        pruebaDigitosDec();
        pruebaDigitosHex();
        pruebaDigitosOct();
        pruebaDoblamiento();
        pruebaDigitosCentrales();
        pruebaSumaCaracteres();
        //pruebaPosicionesTablaHash();
    }

    /**
     * Prueba @link {@link utiles.Funciones#digitosBin(int)}.
     */
    public void pruebaDigitosBin() {
        assert Funciones.digitosBin(0b0) == 1 : "0 debe tener 1 dígito binario";
        assert Funciones.digitosBin(0b1) == 1 : "1 debe tener 1 dígito binario";
        assert Funciones.digitosBin(0b10) == 2 : "10 debe tener 2 dígitos binario";
        assert Funciones.digitosBin(0b11) == 2 : "11 debe tener 2 dígitos binario";
        assert Funciones.digitosBin(0b100) == 3 : "100 debe tener 3 dígitos binario";
        assert Funciones.digitosBin(0b101) == 3 : "101 debe tener 3 dígitos binario";
        assert Funciones.digitosBin(0b110) == 3 : "110 debe tener 3 dígitos binario";
        assert Funciones.digitosBin(0b111) == 3 : "111 debe tener 3 dígitos binario";
        assert Funciones.digitosBin(0b1000) == 4 : "1000 debe tener 4 dígitos binario";
        assert Funciones.digitosBin(0b1001) == 4 : "1001 debe tener 4 dígitos binario";
    }

    /**
     * Prueba @link {@link utiles.Funciones#digitosDec(int)}.
     */
    public void pruebaDigitosDec() {
        assert Funciones.digitosDec(0) == 1 : "0 debe tener 1 dígito decimal";
        assert Funciones.digitosDec(1) == 1 : "1 debe tener 1 dígito decimal";
        assert Funciones.digitosDec(12) == 2 : "12 debe tener 2 dígitos decimales";
        assert Funciones.digitosDec(123) == 3 : "123 debe tener 3 dígitos decimales";
        assert Funciones.digitosDec(1234) == 4 : "1234 debe tener 4 dígitos decimales";
        assert Funciones.digitosDec(12345) == 5 : "12345 debe tener 5 dígitos decimales";
        assert Funciones.digitosDec(123456) == 6 : "123456 debe tener 6 dígitos decimales";
        assert Funciones.digitosDec(1234567) == 7 : "1234567 debe tener 7 dígitos decimales";
        assert Funciones.digitosDec(12345678) == 8 : "12345678 debe tener 8 dígitos decimales";
        assert Funciones.digitosDec(123456789) == 9 : "123456789 debe tener 9 dígitos decimales";
        assert Funciones.digitosDec(1234567890) == 10 : "1234567890 debe tener 10 dígitos decimales";
    }

    /**
     * Prueba @link {@link utiles.Funciones#digitosHex(int)}.
     */
    public void pruebaDigitosHex() {
        assert Funciones.digitosHex(0x0) == 1 : "0 debe tener 1 dígito hexadecimal";
        assert Funciones.digitosHex(0x1) == 1 : "1 debe tener 1 dígito hexadecimal";
        assert Funciones.digitosHex(0xA) == 1 : "A debe tener 1 dígito hexadecimal";
        assert Funciones.digitosHex(0xAB) == 2 : "AB debe tener 2 dígitos hexadecimales";
        assert Funciones.digitosHex(0xABC) == 3 : "ABC debe tener 3 dígitos hexadecimales";
        assert Funciones.digitosHex(0xABCD) == 4 : "ABCD debe tener 4 dígitos hexadecimales";
        assert Funciones.digitosHex(0xABCDE) == 5 : "ABCDE debe tener 5 dígitos hexadecimales";
        assert Funciones.digitosHex(0xABCDEF) == 6 : "ABCDEF debe tener 6 dígitos hexadecimales";
        assert Funciones.digitosHex(0xABCDEF0) == 7 : "ABCDEF0 debe tener 7 dígitos hexadecimales";
        assert Funciones.digitosHex(0xABCDEF00) == 8 : "ABCDEF00 debe tener 8 dígitos hexadecimales";
    }

    /**
     * Prueba @link {@link utiles.Funciones#digitosOct(int)}.
     */
    public void pruebaDigitosOct() {
        assert Funciones.digitosOct(00) == 1 : "00 debe tener 1 dígito octal";
        assert Funciones.digitosOct(01) == 1 : "01 debe tener 1 dígito octal";
        assert Funciones.digitosOct(07) == 1 : "07 debe tener 1 dígito octal";
        assert Funciones.digitosOct(012) == 2 : "012 debe tener 2 dígitos octales";
        assert Funciones.digitosOct(0123) == 3 : "0123 debe tener 3 dígitos octales";
        assert Funciones.digitosOct(01234) == 4 : "01234 debe tener 4 dígitos octales";
        assert Funciones.digitosOct(012345) == 5 : "012345 debe tener 5 dígitos octales";
        assert Funciones.digitosOct(0123456) == 6 : "0123456 debe tener 6 dígitos octales";
        assert Funciones.digitosOct(01234567) == 7 : "01234567 debe tener 7 dígitos octales";
        assert Funciones.digitosOct(012345670) == 8 : "012345670 debe tener 8 dígitos octales";
    }

    /**
     * Prueba {@link utiles.Funciones#doblamiento(int, int)}.
     */
    public void pruebaDoblamiento() {
        assert Funciones.doblamiento(123456789, 1) == 0: "Debe generar 0 = (123456789)%1";
        assert Funciones.doblamiento(123456789, 10) == 1: "Debe generar 1 = (1234+5678+9)%10";
        assert Funciones.doblamiento(123456789, 100) == 68: "Debe generar 68 = (123+456+789)%100";
        assert Funciones.doblamiento(123456789, 1000) == 189: "Debe generar 189 = (12+34+56+78+9)%1000";
        assert Funciones.doblamiento(123456789, 10000) == 6804: "Debe generar 6804 = (1+2+3+4+5+6789)%10000";
        assert Funciones.doblamiento(123456789, 100000) == 810: "Debe generar 810 = (1+2+3+4+5+6+789)%100000";
        assert Funciones.doblamiento(123456789, 1000000) == 117: "Debe generar 117 = (1+2+3+4+5+6+7+89)%1000000";
        assert Funciones.doblamiento(123456789, 10000000) == 45: "Debe generar 45 = (1+2+3+4+5+6+7+8+9)%10000000";
        assert Funciones.doblamiento(0, 8) == 0: "Debe generar 0 = 0%8";
        assert Funciones.doblamiento(1, 8) == 1: "Debe generar 1 = 1%8";
        assert Funciones.doblamiento(2, 8) == 2: "Debe generar 2 = 2%8";
        assert Funciones.doblamiento(4, 8) == 4: "Debe generar 4 = 4%8";
        assert Funciones.doblamiento(8, 8) == 0: "Debe generar 0 = 8%8";
    }

    /**
     * Prueba {@link utiles.Funciones#digitosCentrales(int, int)}.
     */
    public void pruebaDigitosCentrales() {
        assert Funciones.digitosCentrales(8, 100) == 64 : "Los dígitos centrales de 8 y tam. 100 debe ser 64";
        assert Funciones.digitosCentrales(64, 100) == 9 : "Los dígitos centrales de 64 y tam. 100 debe ser 9";
        assert Funciones.digitosCentrales(256, 100) == 55 : "Los dígitos centrales de 256 y tam. 100 debe ser 55";
        assert Funciones.digitosCentrales(512, 100) == 21 : "Los dígitos centrales de 512 y tam. 100 debe ser 21";
        assert Funciones.digitosCentrales(1024, 100) == 48 : "Los dígitos centrales de 1024 y tam. 100 debe ser 48";
    }

    /**
     * Prueba {@link utiles.Funciones#sumaCaracteres(String, int)}.
     */
    public void pruebaSumaCaracteres() {
        assert Funciones.sumaCaracteres("abc", 100) == 94 : "La suma de \"abc\" y tam. 100 debe ser 94";
        assert Funciones.sumaCaracteres("ABC", 100) == 98 : "La suma de \"ABC\" y tam. 100 debe ser 98";
        assert Funciones.sumaCaracteres("xyz", 100) == 63 : "La suma de \"xyz\" y tam. 100 debe ser 63";
        assert Funciones.sumaCaracteres("XYZ", 100) == 67 : "La suma de \"XYZ\" y tam. 100 debe ser 67";
    }

    /**
     * @see conjuntistas.TablaHashCerrado
     */
    @SuppressWarnings("unused")
    private void pruebaPosicionesTablaHash() {
        int m = 31;
        int primos[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};

        for (int n = 0; n < 10000; n++) {
            for (int j = 0; j < primos.length; j++) {
                int posicion = Funciones.doblamiento(n, m);
                int incremento = Funciones.modPrimo(n, primos[j], m);
                int intento = 1;
                int pos = posicion;
                Lista<Integer> cubiertos = new Lista<>();

                while (cubiertos.longitud() < m) {
                    if (cubiertos.localizar(pos) > -1) {
                        pos = (posicion + intento * incremento) % m;
                        intento++;
                    } else {
                        cubiertos.insertar(pos, cubiertos.longitud() + 1);
                    }
                }

                System.out.println("entero: " + n + " tamaño: " + m + " primo: " + primos[j] + " ¡OK!");
            }
        }
    }
}
