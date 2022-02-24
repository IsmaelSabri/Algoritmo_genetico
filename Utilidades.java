
/**
 * Clase con métodos útiles para la ejecución del algoritmo
 * 
 * @author isma
 * @version 1
 */
public class Utilidades {

	public Utilidades() {
	}

	public static double generarAleatorio() {
		return new java.util.Random().nextDouble();
	}

	public static int obtenerLongitud(double limiteInferior, double limiteSuperior, int tolerancia) {
		double resta = limiteSuperior - limiteInferior;
		double division = resta / Math.pow(10, tolerancia * (-1));
		double suma = 1 + division;
		double logaritmo = Math.log10(suma) / Math.log10(2);
		int redondeo = (int) Math.rint(logaritmo) + 1;
		return redondeo;
	}

	public static int toIntValue(boolean[] array) {
		int decimalValue = 0;
		int exp = 0;
		int index = array.length - 1;
		while (index >= 0) {
			if (array[index])
				decimalValue += Math.pow(2, exp);
			exp++;
			index--;
		}
		return decimalValue;
	}
}
