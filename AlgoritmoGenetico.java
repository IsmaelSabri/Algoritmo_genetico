
import java.util.*;
import java.util.Arrays;
//import org.apache.commons.math3.util.Precision;

/**
 * Clase que implementa el algoritmo genético
 * 
 * @author isma
 * @version 1
 */
public class AlgoritmoGenetico {

	private Cromosoma[] poblacion;
	private TipoSeleccion tipoSeleccion;
	private TipoCruce tipoCruce;
	private TipoOperacion tipoOperacion;
	private int numeroMaximoGeneraciones;
	private int tamanoElite;
	private double probabilidadCruce;
	private double probabilidadMutacion;
	private Cromosoma mejorIndividuo;
	private Random random = new Random();

	public AlgoritmoGenetico(Cromosoma[] poblacion, String tipoSeleccion, String tipoCruce,
			int numeroMaximoGeneraciones, int tamanoElite, double probabilidadCruce, double probabilidadMutacion) {
		super();
		if (tipoSeleccion.equals(TipoSeleccion.RULETA.toString())) {
			this.tipoSeleccion = TipoSeleccion.RULETA;
		} else {
			this.tipoSeleccion = TipoSeleccion.TORNEO;
		}
		if (tipoCruce.equals(TipoCruce.SPX.toString())) {
			this.tipoCruce = TipoCruce.SPX;
		} else {
			this.tipoCruce = TipoCruce.UNIFORME;
		}
		this.poblacion = poblacion;
		this.numeroMaximoGeneraciones = numeroMaximoGeneraciones;
		this.tamanoElite = tamanoElite;
		this.probabilidadCruce = probabilidadCruce;
		this.probabilidadMutacion = probabilidadMutacion;
	}

	public AlgoritmoGenetico() {

	}

	public void setNumeroMaximoGeneraciones(int numeroMaximoGeneraciones) {
		this.numeroMaximoGeneraciones = numeroMaximoGeneraciones;
	}

	public int getNumeroMaximoGeneraciones() {
		return numeroMaximoGeneraciones;
	}

	public void setTamanoElite(int TamanoElite) {
		this.tamanoElite = TamanoElite;
	}

	public int getTamanoElite() {
		return tamanoElite;
	}

	public void setTipoSeleccion(TipoSeleccion tipoSeleccion) {
		this.tipoSeleccion = tipoSeleccion;
	}

	public TipoSeleccion getTipoSeleccion() {
		return tipoSeleccion;
	}

	public void setProbabilidadCruce(double probabilidadCruce) {
		this.probabilidadCruce = probabilidadCruce;
	}

	public double getProbabilidadCruce() {
		return probabilidadCruce;
	}

	public void setTipoCruce(TipoCruce tipoCruce) {
		this.tipoCruce = tipoCruce;
	}

	public TipoCruce getTipoCruce() {
		return tipoCruce;
	}

	public void setProbabilidadMutacion(double probabilidadMutacion) {
		this.probabilidadMutacion = probabilidadMutacion;
	}

	public double getProbabilidadMutacion() {
		return probabilidadMutacion;
	}

	public void ejecutar(Cromosoma[] poblacion, TipoOperacion tipoOperacion) {
		int numeroGeneracionActual = 0;
		int tamanoEliteAux = 0;
		Cromosoma[] poblacionTemporal;
		Cromosoma[] padres;
		Cromosoma[] hijos;
		this.poblacion = poblacion;
		this.tipoOperacion = tipoOperacion;
		while (numeroGeneracionActual < numeroMaximoGeneraciones) { // Mientras no se cumpla el criterio de terminación
			if (tipoOperacion == TipoOperacion.MAXIMIZAR) { // Se ordena en función el tipo de operacion
				Arrays.sort(this.poblacion, new Ordenacion(TipoOperacion.MAXIMIZAR.toString()));
			} else {
				Arrays.sort(this.poblacion, new Ordenacion(TipoOperacion.MINIMIZAR.toString()));
			}
			poblacionTemporal = new Cromosoma[this.poblacion.length];
			if (tamanoElite > 0) {
				// System.arraycopy(this.poblacion, 0, poblacionTemporal, 0, tamanoElite); // SI
				// elitismo: copiar en población temporal mejores individuos
				funcionFitness(poblacionTemporal);
				tamanoEliteAux = tamanoElite;
			}
			while (tamanoEliteAux < poblacion.length) {
				padres = seleccionPadres();
				if (Utilidades.generarAleatorio() > probabilidadCruce) { // mutar uno de los descendientes (prob. Pm)
					hijos = reproduccion(padres); // x2
					if (Utilidades.generarAleatorio() > probabilidadMutacion) {
						hijos[0].mutar();
					} else {
						hijos[1].mutar();
					}
					if ((tamanoEliteAux + 2) > poblacion.length) { // añadir descendientes a la población temporal
						if (random.nextBoolean()) {
							poblacionTemporal[tamanoEliteAux] = hijos[0];
						} else {
							poblacionTemporal[tamanoEliteAux] = hijos[1];
						}
					} else {
						poblacionTemporal[tamanoEliteAux] = hijos[0];
						poblacionTemporal[tamanoEliteAux + 1] = hijos[1];
					}
				} else if ((tamanoEliteAux + 2) > poblacion.length) { // añadir padres a la población temporal
					if (random.nextBoolean()) {
						poblacionTemporal[tamanoEliteAux] = padres[0];
					} else {
						poblacionTemporal[tamanoEliteAux] = padres[1];
					}
				} else {
					poblacionTemporal[tamanoEliteAux] = padres[0];
					poblacionTemporal[tamanoEliteAux + 1] = padres[1];
				}
				tamanoEliteAux += 2;
			}
			if (tipoOperacion == TipoOperacion.MAXIMIZAR) {
				Arrays.sort(poblacionTemporal, new Ordenacion(TipoOperacion.MAXIMIZAR.toString()));
			} else {
				Arrays.sort(poblacionTemporal, new Ordenacion(TipoOperacion.MINIMIZAR.toString()));
			}
			this.poblacion = poblacionTemporal;
			mejorIndividuo = this.poblacion[0];
			if (tamanoElite < poblacionTemporal.length) {
				System.out.println(imprimir(numeroGeneracionActual));
			}
			numeroGeneracionActual++;
			tamanoEliteAux = 0;
		}
		System.out.println("Parametros: numeroIndividuos = " + String.valueOf(this.poblacion.length)
				+ ", numeroMaximoGeneraciones = " + String.valueOf(numeroMaximoGeneraciones) + ", seleccion = "
				+ tipoSeleccion + ", probabilidadCruce = " + String.valueOf(probabilidadCruce) + ", Cruce = "
				+ tipoCruce.toString() + ", probabilidadMutacion = " + String.valueOf(probabilidadMutacion)
				+ ", tamanoElite = " + tamanoElite);
	}

	public String imprimir(int numGeneracion) {
		String s = "";
		s += "Generacion " + String.valueOf(numGeneracion) + " de " + numeroMaximoGeneraciones;
		s += ": Mejor individuo=Cromosoma [genes=[" + mejorIndividuo.toString();
		s += "], fenotipo=" + mejorIndividuo.fenotipo();
		s += ", aptitud=" + mejorIndividuo.aptitud();
		return s;
	}

	private void funcionFitness(Cromosoma[] poblacionTemporal) {
		for (int i = 0; i < tamanoElite; i++) {
			try {
				poblacionTemporal[i] = this.poblacion[i].clone();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private Cromosoma[] seleccionPadres() {
		Cromosoma[] individuos;
		if (tipoSeleccion.equals(TipoSeleccion.TORNEO)) {
			individuos = torneo();
		} else {
			individuos = ruleta();
		}
		return individuos;
	}

	private Cromosoma[] torneo() {
		Cromosoma[] padres = new Cromosoma[2];
		int random1 = random.nextInt((int) this.poblacion.length),random2;
		do {
			random2 = random.nextInt((int) this.poblacion.length);
		} while (random1 == random2);
		for (int i = 0; i < 2; i++) {
			if (tipoOperacion == TipoOperacion.MAXIMIZAR) {
				Ordenacion ascendente = new Ordenacion(TipoOperacion.MAXIMIZAR.toString());
				int comparacion = ascendente.compare(poblacion[random1], poblacion[random2]);
				if (comparacion == -1) {
					padres[i] = poblacion[random1]; // a +apto
				} else if (comparacion == 1) {
					padres[i] = poblacion[random2]; // b +apto
				} else {
					padres[i] = poblacion[(int) Math.floor(Math.random() * 1)]; // a y b iguales
				}
			} else {
				Ordenacion descendente = new Ordenacion(TipoOperacion.MINIMIZAR.toString());
				int comparacion2 = descendente.compare(poblacion[random1], poblacion[random2]);
				if (comparacion2 == 1) {
					padres[i] = poblacion[random1];
				} else if (comparacion2 == -1) {
					padres[i] = poblacion[random2];
				} else {
					padres[i] = poblacion[random.nextInt(1)]; // a y b iguales
				}
			}
		}
		return padres;
	}

	private Cromosoma[] ruleta() {
		double[] fitnessNormalizado = new double[this.poblacion.length];
		double[] fitnessAcumulado = new double[this.poblacion.length];
		int pos_1 = 0, pos_2 = 0;
		double aptitudes = 0;
		Cromosoma[] padres = new Cromosoma[2];
		if (tipoOperacion == TipoOperacion.MAXIMIZAR) {
			for (int i = 0; i < this.poblacion.length; i++) {
				aptitudes += this.poblacion[i].aptitud(); // sumatoria aptitudes
			}
			for (int i = 0; i < this.poblacion.length; i++) { // Fitness Normalizado
				fitnessNormalizado[i] = this.poblacion[i].aptitud() / aptitudes;
			}
		} else {
			double factorEscalado = this.poblacion[0].aptitud() - this.poblacion[this.poblacion.length - 1].aptitud();
			for (int i = 0; i < this.poblacion.length; i++) {
				aptitudes += this.poblacion[i].aptitud() + factorEscalado;
			}
			for (int i = 0; i < this.poblacion.length; i++) { // Fitness Normalizado
				fitnessNormalizado[i] = (this.poblacion[i].aptitud() + factorEscalado) / aptitudes;
			}
		}
		for (int i = 0; i < this.poblacion.length; i++) { // Fitness acumulado
			if (i == 0) {
				fitnessAcumulado[i] = fitnessNormalizado[i];
			} else {
				fitnessAcumulado[i] = fitnessNormalizado[i] + fitnessAcumulado[i - 1];
			}
		}
		Arrays.sort(fitnessAcumulado);
		double min = fitnessAcumulado[0];
		double max = fitnessAcumulado[fitnessAcumulado.length - 1] + 0.1; // Precision.round(fitnessAcumulado[fitnessAcumulado.length
																			// // - 1], 1);
		double random_ = random.nextDouble() * (max - min) + min;
		for (int i = 1; i < fitnessAcumulado.length; i++) {
			if (fitnessAcumulado[i] > random_) {
				pos_1 = i - 1;
				break;
			}
		}
		do {
			double num2 = random.nextDouble() * (max - min) + min;
			for (int i = 1; i < fitnessAcumulado.length; i++) {
				if (fitnessAcumulado[i] > num2) {
					pos_2 = i - 1;
					break;
				}
			}
		} while (pos_1 == pos_2); // las porciones de ruleta no pueden coincidir
		padres[0] = (Cromosoma) poblacion[pos_1];
		padres[1] = (Cromosoma) poblacion[pos_2];
		return padres;
	}

	private Cromosoma[] reproduccion(Cromosoma[] padres) {
		boolean[][] genesHijos;
		boolean[] genesPadre = padres[0].getGenes();
		boolean[] genesMadre = padres[1].getGenes();
		Cromosoma[] hijos = new Cromosoma[2];
		try {
			hijos[0] = padres[0];
			hijos[1] = padres[1];
			if (tipoCruce.equals(TipoCruce.SPX)) {
				genesHijos = cruceEnUnPunto(genesPadre, genesMadre);
			} else {
				genesHijos = cruceUniforme(genesPadre, genesMadre);
			}
			hijos[0].setGenes(genesHijos[0]);
			hijos[1].setGenes(genesHijos[1]);
			return hijos;
		} catch (Exception e) {
			return null;
		}
	}

	private boolean[][] cruceEnUnPunto(boolean[] genotipoPadre, boolean[] genotipoMadre) {
		boolean[][] genotipoHijos = new boolean[2][genotipoPadre.length];
		int puntoCruce = (int) Math.random() * genotipoPadre.length;
		for (int i = 0; i < puntoCruce; i++) {
			genotipoHijos[0][i] = genotipoPadre[i];
			genotipoHijos[1][i] = genotipoMadre[i];
		}
		for (int i = puntoCruce; i < genotipoPadre.length; i++) {
			genotipoHijos[0][i] = genotipoMadre[i];
			genotipoHijos[1][i] = genotipoPadre[i];
		}
		return genotipoHijos;
	}

	private boolean[][] cruceUniforme(boolean[] genotipoPadre, boolean[] genotipoMadre) {
		boolean[][] genotipoHijos = new boolean[2][genotipoPadre.length];
		for (int i = 0; i < genotipoPadre.length; i++) {
			if (new Random().nextInt(2) == 0) {
				genotipoHijos[0][i] = genotipoPadre[i];
				genotipoHijos[1][i] = genotipoMadre[i];
			} else {
				genotipoHijos[0][i] = genotipoMadre[i];
				genotipoHijos[1][i] = genotipoPadre[i];
			}
		}
		return genotipoHijos;
	}

}
