
/**
 * 
 * @author isma
 * @version 1
 */
public class Funcion1 extends Cromosoma {
	// Constructor
	public Funcion1(double limiteInferior, double limiteSuperior, int toleranciaRepresentacion) {
		super(limiteInferior, limiteSuperior, toleranciaRepresentacion);
	}

	@Override
	public double aptitud() {
		double x = (double) fenotipo();
		return x + Math.abs(Math.sin(32 * Math.PI * x));
	}

}