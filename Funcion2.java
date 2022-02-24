/**
 * 
 * @author isma
 * @version 1
 */
public class Funcion2 extends Cromosoma {
	// Constructor
	public Funcion2(double limiteInferior, double limiteSuperior, int toleranciaRepresentacion) {
		super(limiteInferior, limiteSuperior, toleranciaRepresentacion);
	}

	@Override
	public double aptitud() {
		double x = (double) fenotipo();
		return (Math.sin(x) / (1 + Math.sqrt(x) + (Math.cos(x) / (1 + x))));
	}

}