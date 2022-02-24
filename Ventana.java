import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
/**
 *  Clase que dispara los eventos de la clase Marco
 *  
 * @author isma
 * @version 1
 */
public class Ventana implements ActionListener {

	private Marco marco;

	public Ventana(Marco marco) {
		this.marco = marco;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String tipof = marco.seleccion();
		String limiteInf = marco.limiteMin();
		String limiteMax = marco.limiteMax();
		String maxGen = marco.generacionesMax();
		String toleranci = marco.tolerancia();
		String tamanyoE = marco.tamanyoElite();
		String tamanyoP = marco.tamanyoPoblacion();
		String tipoEjec = marco.leerComboEjecucion();
		String tipoSel = marco.leerComboSeleccion();
		String tipoCruc = marco.leerComboCruce();
		double probMutacion = (double)marco.leerSliderMutacion();
		double probCruce = (double)marco.leerSliderCruce();
		List<Double> lista=new ArrayList<>();
		List<Double> lista2=new ArrayList<>();

		if (tipof.isEmpty()) {
			JOptionPane.showMessageDialog(marco, "Por favor rellene el campo de la función", "Información",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (limiteInf.isEmpty()) {
			JOptionPane.showMessageDialog(marco, "Por favor rellene el campo del límite inferior", "Información",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (limiteMax.isEmpty()) {
			JOptionPane.showMessageDialog(marco, "Por favor rellene el campo del límite inferior", "Información",
					JOptionPane.INFORMATION_MESSAGE);
		} /*else if(Integer.parseInt(limiteInf)>Integer.parseInt(limiteMax)) {
			JOptionPane.showMessageDialog(marco, "El límite máximo no puede ser inferior al límite mínimo", "Alerta",
					JOptionPane.WARNING_MESSAGE);
		}*/else if (maxGen.isEmpty()) {
			JOptionPane.showMessageDialog(marco,
					"Por favor rellene el campo que hace referencia al número de generaciones máximas", "Información",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (toleranci.isEmpty()) {
			JOptionPane.showMessageDialog(marco, "Por favor rellene el campo de la tolerancia", "Información",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (tamanyoE.isEmpty()) {
			JOptionPane.showMessageDialog(marco, "Por favor rellene el campo del tamanyo de la élite", "Información",
					JOptionPane.INFORMATION_MESSAGE);
		}else if(Integer.parseInt(tamanyoE)<1) {
			JOptionPane.showMessageDialog(marco, "El tamaño de la élite ha de ser mayor que 0",
					"Información", JOptionPane.WARNING_MESSAGE);
		}else if (tamanyoP.isEmpty()) {
			JOptionPane.showMessageDialog(marco, "Por favor rellene el campo del tamanyo de la población",
					"Información", JOptionPane.INFORMATION_MESSAGE);
		} else {
			int tipoFuncion = Integer.parseInt(tipof);
			double limiteInferior = Double.parseDouble(limiteInf);
			double limiteSuperior = Double.parseDouble(limiteMax);
			int maximasGeneraciones = Integer.parseInt(maxGen); // ?
			int tolerancia = Integer.parseInt(toleranci);
			int tamanyoElite = Integer.parseInt(tamanyoE);
			int tamanyoPoblacion = Integer.parseInt(tamanyoP);
			Cromosoma[] poblacion = new Cromosoma[tamanyoPoblacion];
			AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(poblacion,tipoSel,tipoCruc,maximasGeneraciones,tamanyoElite,probCruce,probMutacion);
			switch (tipoFuncion) {
			case 1:
				for (int i = 0; i < tamanyoPoblacion; i++) {
					poblacion[i] = new Funcion1(limiteInferior, limiteSuperior, tolerancia);
					lista.add(poblacion[i].aptitud());
				}
				break;
			case 2:
				for (int i = 0; i < tamanyoPoblacion; i++) {
					poblacion[i] = new Funcion2(limiteInferior, limiteSuperior, tolerancia);
					lista.add(poblacion[i].aptitud());
				}
				break;
			case 3:
				for (int i = 0; i < tamanyoPoblacion; i++) {
					poblacion[i] = new Funcion3(limiteInferior, limiteSuperior, tolerancia);
					lista.add(poblacion[i].aptitud());
				}
				break;
			}
			GraphPanel g = new GraphPanel(lista,lista2);
			if (tipoEjec.equals("MAXIMIZAR")) {
				algoritmoGenetico.ejecutar(poblacion, TipoOperacion.MAXIMIZAR);
				g.createAndShowGui(lista, lista.size(),Integer.parseInt(limiteMax));
			} else {
				algoritmoGenetico.ejecutar(poblacion, TipoOperacion.MINIMIZAR);
				g.createAndShowGui(lista, lista.size(),Integer.parseInt(limiteMax));
			}
			
		}
		
	}
	
	

}
