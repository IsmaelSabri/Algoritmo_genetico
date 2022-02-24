import java.util.Comparator;
/**
 *  Clase para ordenar a la población en función de la ejecución
 * 
 * @author isma
 * @version 1
 */
public class Ordenacion implements Comparator<Cromosoma> {

	private String ejecucion;

	public Ordenacion(String ejecucion) {
		super();
		this.ejecucion = ejecucion;
	}
	@Override
	public int compare(Cromosoma a, Cromosoma b) {
		int aux=0;
		switch (ejecucion) {
		case "MAXIMIZAR":
			if (a.aptitud() == b.aptitud()) {
				aux = 0;
			} else if (a.aptitud() < b.aptitud()) {
				aux = 1;
			} else {
				aux = -1;
			}
			break;
		case "MINIMIZAR":
			if (a.aptitud() > b.aptitud()) {
				aux = 1;
			} else if (a.aptitud() < b.aptitud()) {
				aux = -1;
			} else {
				aux = 0;
			}
			break;
		}
		return aux;
	}
}
