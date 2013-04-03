package nature.ui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class BitStringPanel extends JPanel {
	public InitializationPane initializationPane;
	public FitnessPane fitnessPane;
	public AlgorithmPane algorithmPane;
	public ControlPane controlPane;
	public VisualizationPane visualizationPane;

	public BitStringPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		addSection("Initialization", initializationPane = new InitializationPane());
		addSection("Fitness Goal", fitnessPane = new FitnessPane());
		addSection("Algorithm", algorithmPane = new AlgorithmPane());
		addSection("Control", controlPane = new ControlPane());
		addSection("Visualization", visualizationPane = new VisualizationPane());
	}

	private void addSection(String title, JComponent component) {
		add(Box.createRigidArea(new Dimension(0, 5)));
		component.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(component);
	}

	class InitializationPane extends JPanel {
		public JTextField lengthField, probField;

		public InitializationPane() {
			// Components
			JLabel lengthLabel = new JLabel("Length:");
			lengthField = new JTextField();
			lengthLabel.setLabelFor(lengthField);

			JLabel probLabel = new JLabel("Probability:");
			probField = new JTextField();
			probLabel.setLabelFor(probField);

			// TODO: Implement checkbox behavior
			JCheckBox customCheckBox = new JCheckBox();

			JLabel customLabel = new JLabel("Custom:");
			JTextField customField = new JTextField();
			customLabel.setLabelFor(customField);
			customField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0)); // Avoid vertical expansion

			// Defaults
			lengthField.setText("64");
			probField.setText("0.5");
			customField.setEnabled(false);

			// Layout
			GroupLayout layout = new GroupLayout(this);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			setLayout(layout);

			layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addComponent(lengthLabel)
							.addComponent(lengthField)
							.addComponent(probLabel)
							.addComponent(probField)
					)
					.addGroup(layout.createSequentialGroup()
							.addComponent(customCheckBox)
							.addComponent(customLabel)
							.addComponent(customField)
					)
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(lengthLabel)
							.addComponent(lengthField)
							.addComponent(probLabel)
							.addComponent(probField)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER) // BASELINE doesn't work
							.addComponent(customCheckBox)
							.addComponent(customLabel)
							.addComponent(customField)
					)
			);
		}
	}

	class FitnessPane extends JPanel {
		public JComboBox<String> goalComboBox, funcComboBox;

		public FitnessPane() {
			// Components
			goalComboBox = new JComboBox<String>();
			funcComboBox = new JComboBox<String>();

			// Canned
			goalComboBox.addItem("Maximize");
			goalComboBox.addItem("Minimize");

			funcComboBox.addItem("number of ones");
			funcComboBox.addItem("number of leading ones");

			// Layout
			GroupLayout layout = new GroupLayout(this);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			setLayout(layout);

			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addComponent(goalComboBox)
					.addComponent(funcComboBox)
			);

			layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(goalComboBox)
					.addComponent(funcComboBox)
			);
		}
	}

	class AlgorithmPane extends JPanel {
		public JComboBox<String> algoComboBox;
		public JPanel algoEAParams, algoSAParams, algoMMASParams;

		public AlgorithmPane() {
			// Components
			algoComboBox = new JComboBox<String>();
			algoComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0)); // Avoid vertical expansion
			algoComboBox.addItem("(1+1) Evolutionary Algorithm");
			algoComboBox.addItem("Simulated Annealing");
			algoComboBox.addItem("Min-Max Ant System");

			JSeparator separator = new JSeparator();
			separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6)); // Avoid vertical expansion

			algoEAParams = new JPanel();
			algoSAParams = new JPanel();
			algoMMASParams = new JPanel();

			algoSAParams.setVisible(false);
			algoMMASParams.setVisible(false);

			{
				GroupLayout layout = new GroupLayout(algoEAParams);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);
				algoEAParams.setLayout(layout);

				// TODO: Parse using as javascript using Rhino?
				JLabel label = new JLabel("Local mutation probability:");
				JTextField field = new JTextField("1/n");

				layout.setHorizontalGroup(layout.createSequentialGroup()
						.addComponent(label)
						.addComponent(field)
				);

				layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(field)
				);
			}

			{
				GroupLayout layout = new GroupLayout(algoSAParams);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);
				algoSAParams.setLayout(layout);

				JLabel initTempLabel = new JLabel("Initial temperature:");
				initTempLabel.setMinimumSize(new Dimension(150, 0));
				JTextField initTempField = new JTextField("100");
				initTempLabel.setLabelFor(initTempField);

				JLabel finalTempLabel = new JLabel("Final temperature:");
				finalTempLabel.setMinimumSize(new Dimension(150, 0));
				JTextField finalTempField = new JTextField("0");
				finalTempLabel.setLabelFor(finalTempField);

				JLabel timeLabel = new JLabel("Maximum time:");
				timeLabel.setMinimumSize(new Dimension(150, 0));
				JTextField timeField = new JTextField("1000");
				timeLabel.setLabelFor(timeField);

				// http://www.btluke.com/simanf1.html
				JLabel coolingLabel = new JLabel("Cooling schedule:");
				JComboBox<String> coolingComboBox = new JComboBox<String>();
				coolingComboBox.addItem("Cooling Schedule 0"); // TODO: Better naming?
				coolingComboBox.addItem("Cooling Schedule 1");
				coolingComboBox.addItem("Cooling Schedule 2");
				coolingComboBox.addItem("Cooling Schedule 3");
				coolingComboBox.addItem("Cooling Schedule 4");
				coolingComboBox.addItem("Cooling Schedule 5");
				coolingComboBox.addItem("Cooling Schedule 6");
				coolingComboBox.addItem("Cooling Schedule 7");
				coolingComboBox.addItem("Cooling Schedule 8");
				coolingComboBox.addItem("Cooling Schedule 9");
				coolingLabel.setLabelFor(coolingComboBox);

				layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(initTempLabel)
							.addComponent(initTempField)
						)
						.addGroup(layout.createSequentialGroup()
							.addComponent(finalTempLabel)
							.addComponent(finalTempField)
						)
						.addGroup(layout.createSequentialGroup()
							.addComponent(timeLabel)
							.addComponent(timeField)
						)
						.addGroup(layout.createSequentialGroup()
							.addComponent(coolingLabel)
							.addComponent(coolingComboBox)
						)
				);

				layout.setVerticalGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(initTempLabel)
							.addComponent(initTempField)
						)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(finalTempLabel)
							.addComponent(finalTempField)
						)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(timeLabel)
							.addComponent(timeField)
						)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(coolingLabel)
							.addComponent(coolingComboBox)
						)
				);
			}

			{
				GroupLayout layout = new GroupLayout(algoMMASParams);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);
				algoMMASParams.setLayout(layout);

				JLabel label = new JLabel("Evaporation factor:");
				JTextField field = new JTextField("0.01");

				layout.setHorizontalGroup(layout.createSequentialGroup()
						.addComponent(label)
						.addComponent(field)
				);

				layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label)
						.addComponent(field)
				);
			}

			// Layout
			GroupLayout layout = new GroupLayout(this);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			setLayout(layout);

			layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(algoComboBox)
					.addComponent(separator)
					.addComponent(algoEAParams)
					.addComponent(algoSAParams)
					.addComponent(algoMMASParams)
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
					.addComponent(algoComboBox)
					.addComponent(separator)
					.addComponent(algoEAParams)
					.addComponent(algoSAParams)
					.addComponent(algoMMASParams)
			);
		}
	}

	class ControlPane extends JPanel {
		public JButton controlButton, resetButton;
		public JSlider sleepSlider;
		public JLabel iterationLabel;

		public ControlPane() {
			// Components
			controlButton = new JButton("Start");
//			controlButton.addChangeListener(controller);
			controlButton.setMinimumSize(new Dimension(100, 0));

			resetButton = new JButton("Reset");
//			resetButton.addActionListener(controller);
			resetButton.setMinimumSize(new Dimension(100, 0));
			resetButton.setEnabled(false);

			iterationLabel = new JLabel();

			JLabel sleepLabel = new JLabel("Sleep (ms):");
			sleepSlider = new JSlider(0, 100, 0);
//			sleepSlider.addChangeListener(controller);
			sleepSlider.setMajorTickSpacing(10);
			sleepSlider.setMinorTickSpacing(5);
			sleepSlider.setPaintTicks(true);
			sleepSlider.setPaintLabels(true);
			sleepSlider.setSnapToTicks(true);
			sleepLabel.setLabelFor(sleepSlider);

			// Defaults
			iterationLabel.setText("Iteration: 0");

			// Layout
			GroupLayout layout = new GroupLayout(this);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			setLayout(layout);

			layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addComponent(controlButton)
							.addComponent(resetButton)
							.addComponent(iterationLabel)
					)
					.addGroup(layout.createSequentialGroup()
							.addComponent(sleepLabel)
							.addComponent(sleepSlider)
					)
					.addGap(0, 0, Integer.MAX_VALUE) // Force horizontal expansion
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(controlButton)
							.addComponent(resetButton)
							.addComponent(iterationLabel)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(sleepLabel)
							.addComponent(sleepSlider)
					)
			);
		}
	}

	class VisualizationPane extends JPanel {
		public OnionVisualizer onionVisualizer;

		public VisualizationPane() {
			// Components
			onionVisualizer = new OnionVisualizer();
			onionVisualizer.setMinimumSize(new Dimension(400, 200));

			// Layout
			GroupLayout layout = new GroupLayout(this);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			setLayout(layout);

			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addComponent(onionVisualizer)
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
					.addComponent(onionVisualizer)
			);
		}
	}
}
