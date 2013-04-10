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

public class PermutationPanel extends JPanel {
	public InitializationPane initializationPane;
	public FitnessPane fitnessPane;
	public AlgorithmPane algorithmPane;
	public ControlPane controlPane;
	public VisualizationPane visualizationPane;

	public PermutationPanel() {
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

	public class InitializationPane extends JPanel {
		public JButton loadButton;
		public JLabel statusLabel;

		public InitializationPane() {
			// Components
			loadButton = new JButton("Load Graph");
			statusLabel = new JLabel("Status: No graph loaded");

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

	public class FitnessPane extends JPanel {
		public JComboBox<String> goalComboBox, funcComboBox;

		public FitnessPane() {
			// Components
			goalComboBox = new JComboBox<String>();
			goalComboBox.addItem("Maximize");
			goalComboBox.addItem("Minimize");
			goalComboBox.setSelectedIndex(1);

			funcComboBox = new JComboBox<String>();
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

	public class VisualizationPane extends JPanel {
		public GraphVisualizer drawingPane;

		public VisualizationPane() {
			// Components
			drawingPane = new GraphVisualizer();
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
