/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.lineales;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;

/**
 *
 * @author gaba
 */
public class TestLineales {

    static String sOk = "OK!", sErr = "ERROR";

    public static void main(String[] arg) {

        testLista();
        System.out.println("==================================================================================== ");
        testCola();
        System.out.println("==================================================================================== ");
        testPila();
    }

    public static void testLista() {
        System.out.println("COMIENZO TEST LISTA ");
        Lista l1 = new Lista();
        System.out.println("Muestra lista vacia: \t\t\t" + l1.toString());
        System.out.println("Longitud de lista vacia:\t\t\t" + l1.longitud());
        System.out.print("Inserta 5 pos 5 espera FALSE: \t\t\t" + ((l1.insertar(5, 5) == false) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());
        System.out.print("Inserta 2 pos 1 espera TRUE: \t\t\t" + ((l1.insertar(2, 1) == true) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());
        System.out.print("Inserta 1 pos 1 espera TRUE: \t\t\t" + ((l1.insertar(1, 1) == true) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());
        System.out.print("Inserta 3 pos long+1 espera TRUE: \t\t" + ((l1.insertar(3, l1.longitud() + 1) == true) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());
        System.out.print("Inserta 5 pos 4 espera TRUE: \t\t\t" + ((l1.insertar(5, 4) == true) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());
        System.out.print("Inserta 4 pos 4 espera TRUE: \t\t\t" + ((l1.insertar(4, 4) == true) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());
        System.out.println("Inserta 10 pos 0 espera FALSE: \t\t\t" + ((l1.insertar(10, -1) == false) ? sOk : sErr));
        System.out.println("Inserta 10 pos -1 espera FALSE: \t\t" + ((l1.insertar(10, -1) == false) ? sOk : sErr));
        System.out.print("Inserta 10 pos long+2 espera FALSE: \t\t" + ((l1.insertar(10, l1.longitud() + 2) == false) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());

        int longi = l1.longitud();
        for (int i = -1; i <= longi + 2; i++) {
            if (i > 0 && i <= longi) {
                System.out.println("Recupera " + i + " \tespera " + i + " retorna " + l1.recuperar(i) + ":\t\t" + (((int) l1.recuperar(i) == i) ? sOk : sErr));
            } else {
                System.out.println("Recupera " + i + " \tespera NULL retorna " + l1.recuperar(i) + ":\t" + ((l1.recuperar(i) == null) ? sOk : sErr));
            }
        }

        longi = l1.longitud();
        for (int i = -1; i <= longi + 2; i++) {
            if (i > 0 && i <= longi) {
                System.out.println("Localiza " + i + " \tespera " + i + " retorna " + l1.localizar(i) + ":\t\t" + ((l1.localizar(i) == i) ? sOk : sErr));
            } else {
                System.out.println("Localiza " + i + " \tespera -1 retorna " + l1.localizar(i) + ":\t\t" + ((l1.localizar(i) == -1) ? sOk : sErr));
            }
        }

        System.out.println("Elimina pos -1 espera FALSE: \t\t\t" + ((l1.eliminar(-1) == false) ? sOk : sErr));
        System.out.println("Elimina pos 0 espera FALSE: \t\t\t" + ((l1.eliminar(0) == false) ? sOk : sErr));
        System.out.println("Elimina pos 10 espera FALSE: \t\t\t" + ((l1.eliminar(10) == false) ? sOk : sErr));
        System.out.print("Elimina pos 1 espera TRUE y 2,3,4,5: \t\t" + ((l1.eliminar(1) == true) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());
        System.out.print("Elimina pos 3 espera TRUE y 2,3,5: \t\t" + ((l1.eliminar(3) == true) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());
        System.out.print("Elimina ultimo espera TRUE y 2,3: \t\t" + ((l1.eliminar(l1.longitud()) == true) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());
        System.out.print("Inserta 9 pos 2 espera TRUE y 2,9,3: \t\t" + ((l1.insertar(9, 2) == true) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());
        System.out.println("Recupera pos 5 espera NULL: \t\t\t" + ((l1.recuperar(5) == null) ? sOk : sErr));

        Lista l2 = l1.clone();
        System.out.println("Copia espera [2,9,3]: \t\t\t\t" + l2.toString());

        System.out.print("Inserta 10 pos 1 espera [10,2,9,3]: \t\t" + ((l1.insertar(10, 1) == true) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());
        System.out.print("Elimina pos 4 espera true y 10,2,9: \t\t" + ((l1.eliminar(4) == true) ? sOk : sErr));
        System.out.println("\t--> " + l1.toString());

        while (!l1.esVacia()) {
            System.out.print("Elimina cabecera varias veces hasta vaciar: \t" + ((l1.eliminar(1) == true) ? sOk : sErr));
            System.out.println("\t--> " + l1.toString());
        }

        System.out.print("Se vacio la lista l1, espera ver lista vacia");
        System.out.println("\t--> " + l1.toString());
        System.out.println("Eliminar pos 1 en lista vacia espera FALSE: \t" + ((l1.eliminar(1) == false) ? sOk : sErr));
        System.out.println("Eliminar pos 5 en lista vacia espera FALSE: \t" + ((l1.eliminar(5) == false) ? sOk : sErr));
        System.out.println("Sacar en lista vacia espera FALSE: \t\t" + ((l1.eliminar(1) == false) ? sOk : sErr));
        System.out.println("Recupera pos 1 en lista vacia espera NULL: \t" + ((l1.recuperar(1) == null) ? sOk : sErr));
        System.out.println("Recupera pos 5 en lista vacia espera NULL: \t" + ((l1.recuperar(5) == null) ? sOk : sErr));
        System.out.println("Localiza 1 en lista vacia espera -1: \t\t" + ((l1.localizar(1) == -1) ? sOk : sErr));

        System.out.println("Verifica copia anterior espera [2,9,3]: \t--> " + l2.toString());
        System.out.println("Inserta 7,1 espera true : \t\t\t" + ((l2.insertar(7, 1) == true) ? sOk : sErr));
        System.out.println("Copia modificada espera [7,2,9,3]: \t\t--> " + l2.toString());
        System.out.println("Inserta 6,3 espera true: \t\t\t" + ((l2.insertar(6, 3) == true) ? sOk : sErr));
        System.out.println("Copia modificada espera [7,2,6,9,3]: \t\t--> " + l2.toString());
        System.out.println("Inserta 90,ultimo espera true: \t\t\t" + ((l2.insertar(90, l2.longitud() + 1) == true) ? sOk : sErr));
        System.out.println("Copia modificada espera [7,2,6,9,3,90]: \t--> " + l2.toString());

        System.out.println("Elimina pos 3 espera true: \t\t\t" + ((l2.eliminar(3) == true) ? sOk : sErr));
        System.out.println("Copia modificada espera [7,2,9,3,90]: \t\t--> " + l2.toString());
        System.out.println("Elimina elemento 90 : \t\t\t\t" + ((l2.eliminar(l2.localizar(90)) == true) ? sOk : sErr));
        System.out.println("Copia modificada espera [7,2,9,3]: \t\t--> " + l2.toString());
        l2.vaciar();
        System.out.println("Vacia copia: \t\t\t\t\t--> " + l2.toString());

        System.out.println("FIN TEST LISTA");

    }

    public static void testCola() {
        System.out.println("COMIENZO TEST COLA");
        Cola q1 = new Cola();
        System.out.println("Cola vacía: \t\t\t\t\t\t\t--> " + q1.toString());
        boolean exito = true;

        System.out.println("Si es cola estatica tamaño <= 10 se debe llenar");
        int num = 1;
        while (num < 12) {
            if (num < 10) {
                System.out.print("Pone " + num + " espera true: \t\t\t\t\t" + ((q1.poner(num) == true) ? sOk : sErr));
            } else {
                System.out.print("Pone " + num + " espera false en estatica y true en dinamica : \t" + q1.poner(num));
            }
            num++;
            System.out.println("\t--> " + q1.toString());
        }
        System.out.println("Recupera frente espera 1 recupera: \t\t\t" + (((int)q1.obtenerFrente() == 1) ? sOk : sErr));

        System.out.print("Saca espera true : \t\t\t\t\t" + ((q1.sacar() == true) ? sOk : sErr));
        System.out.println("\t--> " + q1.toString());
        System.out.println("Recupera frente espera 2 recupera \t\t\t" + (((int)q1.obtenerFrente() == 2) ? sOk : sErr));
        System.out.print("Saca espera true: \t\t\t\t\t" + ((q1.sacar() == true) ? sOk : sErr));
        System.out.println("\t--> " + q1.toString());
        System.out.println("Recupera frente espera 3 recupera: \t\t\t" + (((int)q1.obtenerFrente() == 3) ? sOk : sErr));
        System.out.print("Pone 23 espera true: \t\t\t\t\t" + ((q1.poner(23) == true) ? sOk : sErr));
        System.out.println("\t--> " + q1.toString());
        System.out.print("Pone 24 espera true: \t\t\t\t\t" + ((q1.poner(24) == true) ? sOk : sErr));
        System.out.println("\t--> " + q1.toString());
        System.out.println("Recupera frente espera 3 recupera: \t\t\t" + (((int)q1.obtenerFrente() == 3) ? sOk : sErr));

        Cola q2 = q1.clone();
        System.out.println("Copia espera [3 4 5 6 7 8 9 <10 11> 23 24]: \t\t\t--> " + q2.toString());

        System.out.println("Pone 7 espera false en estatica, true en dinamica: \t" + q1.poner(7));
        System.out.print("Pone 8 espera false en estatica, true en dinamica: \t" + q1.poner(8));
        System.out.println("\t--> " + q1.toString());

        while (!q1.esVacia()) {
            System.out.print("Saca " + q1.obtenerFrente() + " de cola espera true: \t\t\t\t" + ((q1.sacar() == true) ? sOk : sErr));
            System.out.println("\t--> " + q1.toString());
        }
        System.out.print("Se vacio la cola q1");
        System.out.println("\t\t\t\t\t\t--> " + q1.toString());
        System.out.println("Sacar en cola vacia espera false: \t\t\t" + ((q1.sacar() == false) ? sOk : sErr));
        System.out.println("Recupera frente en cola vacia espera null: \t\t" + ((q1.obtenerFrente() == null) ? sOk : sErr));

        System.out.println("Verifica copia guardada espera [3 4 5 6 7 8 9 <10 11> 23 24]: \t--> " + q2.toString());
        System.out.println("Pone 27 espera true en dinamica y false en estatica: \t" + q2.poner(27));
        System.out.println("Verifica copia espera [3 4 5 6 7 8 9 <10 11> 23 24 <27>]: \t--> " + q2.toString());
        System.out.println("Saca " + q2.obtenerFrente() + " de cola espera true: \t\t\t\t" + ((q2.sacar() == true) ? sOk : sErr));
        System.out.println("Verifica copia espera [4 5 6 7 8 9 <10 11> 23 24 <27>]: \t--> " + q2.toString());
        System.out.println("Saca " + q2.obtenerFrente() + " de cola espera true: \t\t\t\t" + ((q2.sacar() == true) ? sOk : sErr));
        System.out.println("Verifica copia espera [5 6 7 8 9 <10 11> 23 24 <27>]: \t\t--> " + q2.toString());
        System.out.println("Saca " + q2.obtenerFrente() + " de cola espera true:\t\t\t\t" + ((q2.sacar() == true) ? sOk : sErr));
        System.out.println("Verifica copia espera [6 7 8 9 <10 11> 23 24 <27>]: \t\t--> " + q2.toString());
        System.out.println("Pone 28: \t\t\t\t\t\t" + ((q2.poner(28) == true) ? sOk : sErr));
        System.out.println("Pone 29: \t\t\t\t\t\t" + ((q2.poner(29) == true) ? sOk : sErr));
        System.out.println("Verifica copia espera [6 7 8 9 <10 11> 23 24 <27> 28 29]: \t--> " + q2.toString());
        q2.vaciar();
        System.out.println("Vacia copia espera []: \t\t\t\t\t\t--> " + q2.toString());

    }

    public static void testPila() {

        System.out.println("COMIENZO TEST PILA");
        Pila p1 = new Pila();
        System.out.println("\t\t\t\t\t\t\t\t--> " + p1.toString());

        System.out.println("Apila 1:\t\t\t\t\t\t" + ((p1.apilar(1) == true) ? sOk : sErr));
        System.out.print("Apila 2:\t\t\t\t\t\t" + ((p1.apilar(2) == true) ? sOk : sErr));
        System.out.println("\t--> " + p1.toString());
        System.out.println("Apila 3:\t\t\t\t\t\t" + ((p1.apilar(3) == true) ? sOk : sErr));
        System.out.println("Apila 4:\t\t\t\t\t\t" + ((p1.apilar(4) == true) ? sOk : sErr));
        System.out.println("Apila 5:\t\t\t\t\t\t" + ((p1.apilar(5) == true) ? sOk : sErr));
        System.out.println("Apila 6:\t\t\t\t\t\t" + ((p1.apilar(6) == true) ? sOk : sErr));
        System.out.println("Apila 7:\t\t\t\t\t\t" + ((p1.apilar(7) == true) ? sOk : sErr));
        System.out.println("Apila 8:\t\t\t\t\t\t" + ((p1.apilar(8) == true) ? sOk : sErr));
        System.out.println("Apila 9:\t\t\t\t\t\t" + ((p1.apilar(9) == true) ? sOk : sErr));
        System.out.println("Apila 10:\t\t\t\t\t\t" + ((p1.apilar(10) == true) ? sOk : sErr));
        System.out.println("Apila 11 espera false en estatica true en dinamica:\t" + p1.apilar(11));
        if ((int) p1.obtenerTope() == 11) {
            System.out.println("si apilo el 11, lo saca para continuar");
            p1.desapilar();
        }
        System.out.print("espera \t1,2,3,4,5,6,7,8,9,10");
        System.out.println("\t\t\t\t\t--> " + p1.toString());
        System.out.println("Recupera tope espera 10 recupera: \t\t\t" + (((int)p1.obtenerTope() == 10) ? sOk : sErr));

        System.out.println("Desapila: \t\t\t\t\t\t" + ((p1.desapilar() == true) ? sOk : sErr));
        System.out.println("espera \t1,2,3,4,5,6,7,8,9 recupera \t\t\t\t--> " + p1.toString());
        System.out.println("Desapila: \t\t\t\t\t\t" + ((p1.desapilar() == true) ? sOk : sErr));
        System.out.println("espera \t1,2,3,4,5,6,7,8 recupera \t\t\t\t--> " + p1.toString());
        System.out.println("Apila 6: \t\t\t\t\t\t" + ((p1.apilar(6) == true) ? sOk : sErr));
        System.out.println("espera \t1,2,3,4,5,6,7,8,6 recupera \t\t\t\t--> " + p1.toString());
        System.out.println("Recupera tope, espera 6: \t\t\t\t" + (((int)p1.obtenerTope() == 6) ? sOk : sErr));

        Pila p2 = p1.clone();
        System.out.println("Copia espera 1,2,3,4,5,6,7,8,6: \t\t\t\t--> " + p2.toString());

        while (!p1.esVacia()) {
            System.out.print("Desapila espera true: \t\t\t\t\t" + ((p1.desapilar() == true) ? sOk : sErr));
            System.out.println("\t--> " + p1.toString());
        }
        System.out.print("Se vacio la pila p1");
        System.out.println("\t\t\t\t\t\t--> " + p1.toString());
        System.out.println("Desapila en pila vacia espera false: \t\t\t" + ((p1.desapilar() == false) ? sOk : sErr));
        System.out.println("Verifica tope en pila vacia espera null: \t\t" + ((p1.obtenerTope() == null) ? sOk : sErr));

        System.out.println("Verifica copia 1,2,3,4,5,6,7,8,6: \t\t\t\t-->" + p2.toString());
        System.out.println("Apila 7: \t\t\t\t\t\t" + ((p2.apilar(7) == true) ? sOk : sErr));
        System.out.println("Verifica copia 1,2,3,4,5,6,7,8,6,7: \t\t\t\t-->" + p2.toString());
        System.out.println("Apila 8 espera false en estatica: \t\t\t" + p2.apilar(8));
        System.out.println("Apila 9 espera false en estatica: \t\t\t" + p2.apilar(9));

        if ((int) p2.obtenerTope() == 9) {
            System.out.println("si apilo el 8 y el 9, los saca para continuar");
            p2.desapilar();
            p2.desapilar();
        }

        System.out.println("Verifica copia modificada 1,2,3,4,5,6,7,8,6,7: \t\t\t--> " + p2.toString());

        System.out.println("Desapila: \t\t\t\t\t\t" + ((p2.desapilar() == true) ? sOk : sErr));
        System.out.println("Desapila: \t\t\t\t\t\t" + ((p2.desapilar() == true) ? sOk : sErr));
        System.out.println("Desapila: \t\t\t\t\t\t" + ((p2.desapilar() == true) ? sOk : sErr));
        System.out.println("Desapila: \t\t\t\t\t\t" + ((p2.desapilar() == true) ? sOk : sErr));
        System.out.println("Verifica copia modificada, espera 1,2,3,4,5,6: \t\t\t--> " + p2.toString());
        p2.vaciar();
        System.out.println("Vacia copia espera pila vacia: \t\t\t\t\t--> " + p2.toString());
    }
}
