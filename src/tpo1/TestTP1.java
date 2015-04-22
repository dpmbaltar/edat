package tpo1;

import java.util.regex.Pattern;

import lineales.dinamicas.PilaInt;
import lineales.dinamicas.ColaInt;

/**
 * Trabajo Práctico Obligatorio No. 1.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class TestTP1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		pruebaGenerarCola();
	}
	
	public static void pruebaGenerarCola() {
		String s = "0123456789";
		String r = "["+Pattern.quote("[, ]")+"]+";
		
		assert generarCola(s, 3).toString().replaceAll(r, "")
			.equals("2105438769");
		assert generarCola(s, 4).toString().replaceAll(r, "")
			.equals("3210765498");
		assert generarCola(s, 5).toString().replaceAll(r, "")
			.equals("4321098765");
	}
	
	public static ColaInt generarCola(String s, int x) {
		ColaInt cola = new ColaInt();
		
		if (!s.isEmpty()) {
			//TODO
		}
		
		return cola;
	}
}
