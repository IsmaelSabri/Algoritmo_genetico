
import java.lang.Comparable;
import java.util.Random;

/**
 * Write a description of class Cromosoma here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Cromosoma implements Cloneable {

	private boolean[] genes;
	private Random r = new Random();
	private double limiteInferior;
	private double limiteSuperior;
	private int toleranciaRepresentacion;

	public Cromosoma(double limiteInferior, double limiteSuperior, int toleranciaRepresentacion) {
		super();
		this.limiteInferior = limiteInferior;
		this.limiteSuperior = limiteSuperior;
		this.toleranciaRepresentacion = toleranciaRepresentacion;
		// Se crea el genotipo aleatoriamente
		this.crearCromosoma();
	}

	public abstract double aptitud();

	private void crearCromosoma() {
		int longitudCromosoma = Utilidades.obtenerLongitud(limiteInferior, limiteSuperior, toleranciaRepresentacion);
		genes = new boolean[longitudCromosoma];
		for (int i = 0; i < longitudCromosoma; i++)
			genes[i] = r.nextBoolean();
	}

	public double fenotipo() {
		int valorDecimal = Utilidades.toIntValue(genes);
		double fenotipo = limiteInferior
				+ (limiteSuperior - limiteInferior) * valorDecimal / (Math.pow(2, genes.length) - 1);
		return fenotipo;
	}

	public boolean[] getGenes() {
		return genes;
	}

	public void setGenes(boolean genes[]) {
		this.genes = genes;
	}

	public Cromosoma clone() throws CloneNotSupportedException {
		return (Cromosoma) super.clone();
	}

	public void mutar() {
		int posicion= (int) Math.floor(Math.random() * this.genes.length);
		this.genes[posicion] = !this.genes[posicion];
	}

	public String toString() {
		String numeroBinario = "";
		for (int i = 0; i < genes.length; i++)
			numeroBinario += ((Boolean) genes[i]).booleanValue() ? "1" : "0";
		return numeroBinario;
	}

}
