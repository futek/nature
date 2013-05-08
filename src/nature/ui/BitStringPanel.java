package nature.ui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BitStringPanel extends JPanel {
	public InitializationPane initializationPane;
	public FitnessPane fitnessPane;
	public AlgorithmPane algorithmPane;
	public ControlPane controlPane;
	public VisualizationPane visualizationPane;

	public BitStringPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		initializationPane = new InitializationPane();
		fitnessPane = new FitnessPane();
		algorithmPane = new AlgorithmPane();
		controlPane = new ControlPane();
		visualizationPane = new VisualizationPane();

		addSection("Initialization", initializationPane);
		addSection("Fitness Goal", fitnessPane);
		addSection("Algorithm", algorithmPane);
		addSection("Control", controlPane);
	}

	private void addSection(String title, JComponent component) {
		add(Box.createRigidArea(new Dimension(0, 5)));
		component.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(component);
	}

	class InitializationPane extends JPanel {
		public JIntegerField lengthField;
		public JBinaryField customField;
		public JDoubleField probField;
		public JCheckBox customCheckBox;

		public InitializationPane() {
			// Components
			JLabel lengthLabel = new JLabel("Length:");
			lengthField = new JIntegerField(1, Integer.MAX_VALUE);
			lengthLabel.setLabelFor(lengthField);

			JLabel probLabel = new JLabel("Probability:");
			probField = new JDoubleField(0, 1);
			probLabel.setLabelFor(probField);

			// TODO: Implement checkbox behavior
			customCheckBox = new JCheckBox();

			JLabel customLabel = new JLabel("Custom:");
			customField = new JBinaryField(10);
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
		public JLabel currentFitnessLabel;

		public FitnessPane() {
			// Components
			goalComboBox = new JComboBox<String>();
			goalComboBox.addItem("Maximize");
			goalComboBox.addItem("Minimize");

			funcComboBox = new JComboBox<String>();
			funcComboBox.addItem("number of ones");
			funcComboBox.addItem("number of leading ones");

			currentFitnessLabel = new JLabel("Current fitness: ");

			// Layout
			GroupLayout layout = new GroupLayout(this);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			setLayout(layout);

			layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addComponent(goalComboBox)
							.addComponent(funcComboBox)
					)
					.addComponent(currentFitnessLabel)
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(goalComboBox)
							.addComponent(funcComboBox)
					)
					.addComponent(currentFitnessLabel)
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
