package pruebas.jerarquicas;

import jerarquicas.dinamicas.ArbolBinInt;

//import jerarquicas.estaticas.ArbolBin;

public class PruebaArbolBin {

    /**
     * @param args
     */
    public static void main(String[] args) {
        pruebaArbolBinInt();
    }

    public static void pruebaArbolBinInt() {
        ArbolBinInt a1 = new ArbolBinInt();

        System.out.println("Insertar raiz (1):");
        a1.insertarRaiz(1);
        System.out.println(a1);
        System.out.println("Insertar (2@1,I):" + a1.insertar(2, 1, 'I'));
        System.out.println("Insertar (3@1,D):" + a1.insertar(3, 1, 'D'));
        System.out.println("Insertar (4@2,I):" + a1.insertar(4, 2, 'I'));
        System.out.println("Insertar (5@2,D):" + a1.insertar(5, 2, 'D'));
        System.out.println("Insertar (6@3,I):" + a1.insertar(6, 3, 'I'));
        System.out.println("Insertar (7@3,D):" + a1.insertar(7, 3, 'D'));
        System.out.println("Insertar (8@5,D):" + a1.insertar(8, 5, 'D'));
        System.out.println("Insertar (9@6,I):" + a1.insertar(9, 6, 'I'));

        // Listar en pre-orden
        System.out.println(a1.listarPreorden());
        // Listar en in-orden
        System.out.println(a1.listarInorden());
        // Listar en pos-orden
        System.out.println(a1.listarPosorden());
        // Listar niveles
        System.out.println(a1.listarNiveles());
        // Mostrar altura del árbol
        System.out.println("Altura del árbol: " + a1.altura());
        // Mostrar nivel del elem. 7
        System.out.println("Nivel del elem. 7: " + a1.nivel(7));
        // Mostrar elem. padre de 5
        System.out.println("Padre del elem. 5: " + a1.padre(5));
        // Clonar árbol a a2
        System.out.println("Clonar a1 a a2 y mostrar:");
        ArbolBinInt a2 = a1.clonar();
        System.out.println(a2);
        System.out.println("Sumar ramas de a2:");
        System.out.println(a2.sumarRamas());

        // Mostrar arbol a1
        System.out.println(a1);
    }
}
