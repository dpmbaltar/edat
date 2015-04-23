package pruebas.jerarquicas;

import jerarquicas.dinamicas.ArbolGenericoString;

public class PruebaArbolGenerico {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		pruebaArbolGenerico();
	}
	
	public static void pruebaArbolGenerico() {
		ArbolGenericoString ags1 = new ArbolGenericoString();
		
		System.out.println("Insertar Uno@[raiz]"+ags1.insertar("Uno", null));
		System.out.println("Insertar Dos@Uno"+ags1.insertar("Dos", "Uno"));
		System.out.println("Insertar Tres@Uno"+ags1.insertar("Tres", "Uno"));
		
		System.out.println(ags1.listarPreorden());
	}
}
