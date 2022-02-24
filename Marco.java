
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Interfaz para recoger los datos de la ejecución
 * 
 * @author isma
 * @version 1
 */
public class Marco extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFuncionseleccin;
	private JTextField txtMin;
	private JTextField txtMax;
	private JTextField textTolerancia;
	private JTextField textTamano;
	private JTextField textGeneraciones;
	private JTextField textElite;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JSlider slider;
	private JSlider slider_1;
	private JLabel lblNewLabel;
	private JLabel lblLmites;
	private JLabel lblTolerancia;
	private JLabel lblTamao;
	private JLabel lblTipoEjecucin;
	private JLabel lblNewLabel_1;

	/* Ejecución estandar */
	public static void ejecucionEstandar() {
		int toleranciaRepresentacion = 3;
		int tamanyoPoblacion = 5;
		double limiteInferior = 0.0;
		double limiteSuperior = 1.0;
		Cromosoma[] poblacion = new Cromosoma[tamanyoPoblacion];
		for (int i = 0; i < tamanyoPoblacion; i++)
			poblacion[i] = new Funcion1(limiteInferior, limiteSuperior, toleranciaRepresentacion);
		AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico();
		algoritmoGenetico.setNumeroMaximoGeneraciones(50);
		algoritmoGenetico.setTamanoElite(1);
		algoritmoGenetico.setProbabilidadCruce(0.5);
		algoritmoGenetico.setProbabilidadMutacion(0.2);
		algoritmoGenetico.setTipoSeleccion(TipoSeleccion.TORNEO);
		algoritmoGenetico.setTipoCruce(TipoCruce.UNIFORME);
		algoritmoGenetico.ejecutar(poblacion, TipoOperacion.MAXIMIZAR);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String... args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Marco frame = new Marco();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Marco() {
		pack();
		comboBox = new JComboBox(TipoOperacion.values());
		comboBox_1 = new JComboBox(TipoSeleccion.values());
		comboBox_2 = new JComboBox(TipoCruce.values());
		slider = new JSlider(0, 100, 50);
		slider_1 = new JSlider();
		lblNewLabel = new JLabel("Función(1 ,2 o 3)");
		lblLmites = new JLabel("Límites");
		lblTolerancia = new JLabel("Tolerancia");
		lblTamao = new JLabel("Tamaño población");
		lblTipoEjecucin = new JLabel("Tipo ejecución");
		lblNewLabel_1 = new JLabel("Generaciones máx.");
		txtFuncionseleccin = new JTextField();

		setTitle("Algoritmo genetico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1950, 315, 480, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel.setBounds(12, 24, 131, 17);
		contentPane.add(lblNewLabel);

		txtFuncionseleccin.setBounds(143, 24, 114, 19);
		contentPane.add(txtFuncionseleccin);
		txtFuncionseleccin.setColumns(10);

		lblLmites.setBounds(286, 26, 70, 13);
		contentPane.add(lblLmites);

		txtMin = new JTextField();
		txtMin.setText("Min");
		txtMin.setBounds(349, 23, 53, 19);
		contentPane.add(txtMin);
		txtMin.setColumns(10);

		txtMax = new JTextField();

		txtMax.setText("Max");
		txtMax.setBounds(414, 23, 53, 19);
		contentPane.add(txtMax);
		txtMax.setColumns(10);

		lblTolerancia.setBounds(267, 120, 95, 15);
		contentPane.add(lblTolerancia);

		textTolerancia = new JTextField();
		textTolerancia.setBounds(353, 118, 114, 19);
		contentPane.add(textTolerancia);
		textTolerancia.setColumns(10);

		lblTamao.setBounds(12, 212, 131, 15);
		contentPane.add(lblTamao);

		textTamano = new JTextField();
		textTamano.setBounds(143, 212, 53, 19);
		contentPane.add(textTamano);
		textTamano.setColumns(10);

		lblTipoEjecucin.setBounds(12, 75, 107, 15);
		contentPane.add(lblTipoEjecucin);

		comboBox.setBounds(143, 70, 114, 24);
		contentPane.add(comboBox);

		lblNewLabel_1.setBounds(267, 75, 135, 15);
		contentPane.add(lblNewLabel_1);

		textGeneraciones = new JTextField();
		textGeneraciones.setBounds(414, 73, 53, 19);
		contentPane.add(textGeneraciones);
		textGeneraciones.setColumns(10);

		JLabel lblTamaolite = new JLabel("Tamaño élite");
		lblTamaolite.setBounds(267, 165, 95, 15);
		contentPane.add(lblTamaolite);

		textElite = new JTextField();
		textElite.setBounds(371, 163, 96, 19);
		contentPane.add(textElite);
		textElite.setColumns(10);

		JLabel lblSeleccin = new JLabel("Selección");
		lblSeleccin.setBounds(12, 120, 70, 15);
		contentPane.add(lblSeleccin);

		comboBox_1.setBounds(143, 115, 95, 24);
		contentPane.add(comboBox_1);

		JLabel lblProbabilidadDeCruce = new JLabel("Probabilidad de cruce");
		lblProbabilidadDeCruce.setBounds(12, 361, 154, 15);
		contentPane.add(lblProbabilidadDeCruce);

		slider.setMajorTickSpacing(25);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setFont(new Font("Serif", Font.ITALIC, 12));
		slider.setBounds(214, 361, 200, 42);
		contentPane.add(slider);

		JLabel lblNewLabel_2 = new JLabel("Cruce");
		lblNewLabel_2.setBounds(12, 165, 70, 15);
		contentPane.add(lblNewLabel_2);

		comboBox_2.setBounds(143, 160, 88, 24);
		contentPane.add(comboBox_2);

		JLabel lblProbabilidadDeMutacin = new JLabel("Probabilidad de mutación");
		lblProbabilidadDeMutacin.setBounds(12, 291, 187, 15);
		contentPane.add(lblProbabilidadDeMutacin);

		slider_1.setMajorTickSpacing(25);
		slider_1.setMinorTickSpacing(5);
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);
		slider_1.setFont(new Font("Serif", Font.ITALIC, 12));
		slider_1.setBounds(214, 279, 200, 51);
		contentPane.add(slider_1);

		JButton btnEjecucinStandard = new JButton("Ejecución estandar");
		btnEjecucinStandard.setBounds(12, 420, 216, 25);
		contentPane.add(btnEjecucinStandard);
		btnEjecucinStandard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ejecucionEstandar();
			}
		});

		JButton btnEjecutarLosParmetros = new JButton("Ejecutar los parámetros");
		btnEjecutarLosParmetros.setBounds(251, 420, 216, 25);
		contentPane.add(btnEjecutarLosParmetros);
		btnEjecutarLosParmetros.addActionListener(new Ventana(this));
	}

	public String seleccion() {
		return txtFuncionseleccin.getText();
	}

	public String limiteMin() {
		return txtMin.getText();
	}

	public String limiteMax() {
		return txtMax.getText();
	}

	public String tolerancia() {
		return textTolerancia.getText();
	}

	public String tamanyoPoblacion() {
		return textTamano.getText();
	}

	public String tamanyoElite() {
		return textElite.getText();
	}

	public String generacionesMax() {
		return textGeneraciones.getText();
	}

	public String leerComboEjecucion() {
		return comboBox.getSelectedItem().toString();
	}

	public String leerComboSeleccion() {
		return comboBox_1.getSelectedItem().toString();
	}

	public String leerComboCruce() {
		return comboBox_2.getSelectedItem().toString();
	}

	public int leerSliderMutacion() {
		return slider.getValue();
	}

	public int leerSliderCruce() {
		return slider_1.getValue();
	}

}
