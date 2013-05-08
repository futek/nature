package nature.ui;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

class ControlPane extends JPanel {
	public JButton controlButton, resetButton;
	public JSlider sleepSlider;
	public JLabel iterationLabel;

	public ControlPane() {
		// Components
		controlButton = new JButton("Start");
		controlButton.setMinimumSize(new Dimension(100, 0));

		resetButton = new JButton("Reset");
		resetButton.setMinimumSize(new Dimension(100, 0));
		resetButton.setEnabled(false);

		iterationLabel = new JLabel();

		JLabel sleepLabel = new JLabel("Sleep (ms):");
		sleepSlider = new JSlider(0, 100, 0);
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