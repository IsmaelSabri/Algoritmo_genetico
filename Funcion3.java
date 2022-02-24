/**
 * 
 * @author isma
 * @version 1
 */
public class Funcion3 extends Cromosoma {
	// Constructor
	public Funcion3(double limiteInferior, double limiteSuperior, int toleranciaRepresentacion) {
		super(limiteInferior, limiteSuperior, toleranciaRepresentacion);
	}

	@Override
	public double aptitud() {
		double x = (double) fenotipo();
		return (Math.sin(x) / (1 + (Math.cos(7 * x) / (1 + x))));
	}

}
