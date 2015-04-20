package pruebas.jerarquicas;

import jerarquicas.dinamicas.ArbolBin;
//import jerarquicas.estaticas.ArbolBin;

public class PruebaArbolBin {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		pruebaArbolBinInt();
	}
	
	public static void pruebaArbolBinInt() {
		ArbolBin a1 = new ArbolBin();
		
		System.out.println("Insertar raiz (1):");
		a1.insertarRaiz(1);
		System.out.println(a1);
		System.out.println("Insertar (2@1,I):"+a1.insertar(2, 1, 'I'));
		System.out.println("Insertar (3@1,D):"+a1.insertar(3, 1, 'D'));
		System.out.println("Insertar (4@2,I):"+a1.insertar(4, 2, 'I'));
		System.out.println("Insertar (5@2,D):"+a1.insertar(5, 2, 'D'));
		System.out.println("Insertar (6@3,I):"+a1.insertar(6, 3, 'I'));
		System.out.println("Insertar (7@3,D):"+a1.insertar(7, 3, 'D'));
		System.out.println("Insertar (8@5,D):"+a1.insertar(8, 5, 'D'));
		System.out.println("Insertar (9@6,I):"+a1.insertar(9, 6, 'I'));
		
		// Listar en pre-orden
		System.out.println(a1.listarPreorden());
		// Listar en in-orden
		System.out.println(a1.listarInorden());
		// Listar en pos-orden
		System.out.println(a1.listarPosorden());
		// Listar niveles
		System.out.println(a1.listarNiveles());
		
		System.out.println(a1);
	}
}
