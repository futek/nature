package nature.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class PermutationPanel extends JPanel {
	public PermutationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		addSection("Initialization", new InitializationPane());
		addSection("Fitness Goal", new FitnessPane());
		addSection("Algorithm", new AlgorithmPane());
		addSection("Control", new ControlPane());
		addSection("Visualization", new VisualizationPane());
	}

	private void addSection(String title, JComponent component) {
		add(Box.createRigidArea(new Dimension(0, 5)));
		component.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(component);
	}

	private class InitializationPane extends JPanel {
		public InitializationPane() {
			// Components
			JButton loadButton = new JButton("Load Graph");
			JLabel statusLabel = new JLabel("Status: No graph loaded.");

			// Canned
			statusLabel.setText("Status: Graph loaded (1024 nodes).");

			// Layout
			GroupLayout layout = new GroupLayout(this);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			setLayout(layout);

			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addComponent(loadButton)
					.addComponent(statusLabel)
					.addGap(0, 0, Integer.MAX_VALUE) // Force horizontal expansion
			);

			layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(loadButton)
					.addComponent(statusLabel)
			);
		}
	}

	private class FitnessPane extends JPanel {
		public FitnessPane() {
			// Components
			JComboBox<String> goalComboBox = new JComboBox<String>();
			JComboBox<String> funcComboBox = new JComboBox<String>();

			// Canned
			goalComboBox.addItem("Maximize");
			goalComboBox.addItem("Minimize");
			goalComboBox.setSelectedIndex(1);

			funcComboBox.addItem("length of tour");

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

	private class AlgorithmPane extends JPanel {
		public AlgorithmPane() {
			// Components
			JComboBox<String> algoComboBox = new JComboBox<String>();
			algoComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0)); // Avoid vertical expansion

			JSeparator separator = new JSeparator();
			separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6)); // Avoid vertical expansion

			JPanel algoParamPane = new JPanel(); // ...

			// Canned
			algoComboBox.addItem("(1+1) Evolutionary Algorithm");
			algoComboBox.addItem("Simulated Annealing");
			algoComboBox.addItem("Min-Max Ant System");

			{
				GroupLayout layout = new GroupLayout(algoParamPane);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);
				algoParamPane.setLayout(layout);
				JLabel label = new JLabel("Local mutation probability:");
				JTextField field = new JTextField("1/n");
				layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(label).addComponent(field));
				layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label).addComponent(field));
			}

			// Layout
			GroupLayout layout = new GroupLayout(this);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			setLayout(layout);

			layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(algoComboBox)
					.addComponent(separator)
					.addComponent(algoParamPane)
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
					.addComponent(algoComboBox)
					.addComponent(separator)
					.addComponent(algoParamPane)
			);
		}
	}

	private class ControlPane extends JPanel {
		public ControlPane() {
			// Components
			JButton runButton = new JButton("Run");
			JButton resetButton = new JButton("Reset");

			JLabel iterationsLabel = new JLabel();

			// Canned
			iterationsLabel.setText("Iterations: 1626");

			// Layout
			GroupLayout layout = new GroupLayout(this);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			setLayout(layout);

			layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addComponent(runButton)
							.addComponent(resetButton)
					)
					.addComponent(iterationsLabel)
					.addGap(0, 0, Integer.MAX_VALUE) // Force horizontal expansion
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(runButton)
							.addComponent(resetButton)
					)
					.addComponent(iterationsLabel)
			);
		}
	}

	private class VisualizationPane extends JPanel {
		public VisualizationPane() {
			// Components
			JPanel drawingPane = new JPanel(); // ...

			// Canned
			drawingPane.setBackground(new Color(255, 255, 255));
			drawingPane.setMinimumSize(new Dimension(400, 200));

			// Layout
			GroupLayout layout = new GroupLayout(this);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			setLayout(layout);

			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addComponent(drawingPane)
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
					.addComponent(drawingPane)
			);
		}
	}
}
