/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas.lineales;

//import lineales.estaticas.PilaInt;
import lineales.dinamicas.PilaInt;


/**
 *
 * @author Catedra Estructuras de Datos
 */
public class PruebaPila {

        public static void main(String[] arg){
        PilaInt p1 = new PilaInt();
        System.out.println(p1.toString());
        
        System.out.println("Apila 1: " + p1.apilar(1));
        System.out.println("Apila 2: " + p1.apilar(2));
        System.out.println(p1.toString());
        System.out.println("Apila 3: " + p1.apilar(3));
        System.out.println("Apila 4: " + p1.apilar(4));
        System.out.println("Apila 5: " + p1.apilar(5));
        System.out.println(p1.toString());
        System.out.println("Recupera tope: " + p1.obtenerTope());
        
        System.out.println("Desapila: " + p1.desapilar());
        System.out.println(p1.toString());
        System.out.println("Desapila: " + p1.desapilar());
        System.out.println(p1.toString());
        System.out.println("Apila 6: " + p1.apilar(6));
        System.out.println(p1.toString());
        System.out.println("Recupera tope: " + p1.obtenerTope());
        
        PilaInt p2 = p1.clonar();
        System.out.println("Copia: " + p2.toString());
        
        while (!p1.esVacia()){
            System.out.println("Desapila: " + p1.desapilar());
            System.out.println(p1.toString());
        }
        System.out.println("Se vacio la pila p1");
        System.out.println(p1.toString());
        System.out.println("Desapila en pila vacia: " + p1.desapilar());
        
        
        System.out.println("Verifica copia: " + p2.toString());
        System.out.println("Apila 7: " + p2.apilar(7));
        System.out.println("Apila 8: " + p2.apilar(8));
        System.out.println("Apila 9: " + p2.apilar(9));
        System.out.println("Verifica copia modificada: " + p2.toString());

        System.out.println("Desapila: " + p2.desapilar());
        System.out.println("Desapila: " + p2.desapilar());
        System.out.println("Desapila: " + p2.desapilar());
        System.out.println("Desapila: " + p2.desapilar());
        System.out.println("Verifica copia modificada: " + p2.toString());
        p2.vaciar();
        System.out.println("Vacia copia: " + p2.toString());
    }
}
