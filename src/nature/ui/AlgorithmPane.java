package nature.ui;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

class AlgorithmPane extends JPanel {
	public JComboBox<String> algoComboBox;
	public JPanel algoEAParams, algoSAParams, algoMMASParams;
	public JTextField timeField, initTempField, finalTempField, evaporationFactorField, alphaField, betaField;
	public JComboBox<String> coolingComboBox;

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
			algoEAParams.setMaximumSize(new Dimension(0, 0));
		}

		{
			GroupLayout layout = new GroupLayout(algoSAParams);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			algoSAParams.setLayout(layout);

			JLabel initTempLabel = new JLabel("Initial temperature:");
			initTempLabel.setMinimumSize(new Dimension(150, 0));
			initTempField = new JTextField("0.01");
			initTempLabel.setLabelFor(initTempField);

			JLabel finalTempLabel = new JLabel("Final temperature:");
			finalTempLabel.setMinimumSize(new Dimension(150, 0));
			finalTempField = new JTextField("0");
			finalTempLabel.setLabelFor(finalTempField);

			JLabel timeLabel = new JLabel("Maximum time:");
			timeLabel.setMinimumSize(new Dimension(150, 0));
			timeField = new JTextField("1337");
			timeLabel.setLabelFor(timeField);

			// http://www.btluke.com/simanf1.html
			JLabel coolingLabel = new JLabel("Cooling schedule:");
			coolingComboBox = new JComboBox<String>();
			coolingComboBox.addItem("Cooling Schedule 0"); // TODO: Better naming?
			coolingComboBox.addItem("Cooling Schedule 1");
			coolingComboBox.addItem("Cooling Schedule 2");
			coolingComboBox.addItem("Cooling Schedule 3");
			coolingComboBox.addItem("Cooling Schedule 4");
			coolingComboBox.addItem("Cooling Schedule 5");
			coolingComboBox.addItem("Cooling Schedule 6");
			coolingComboBox.addItem("Cooling Schedule 7");
			coolingComboBox.addItem("Easter egg!");
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

			JLabel evaporationFactorLabel = new JLabel("Evaporation factor:");
			evaporationFactorField = new JTextField("0.01");

			JLabel alphaLabel = new JLabel("Alpha:");
			alphaField = new JTextField("1.0");

			JLabel betaLabel = new JLabel("Beta:");
			betaField = new JTextField("1.0");

			layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addComponent(evaporationFactorLabel)
							.addComponent(evaporationFactorField)
					)
					.addGroup(layout.createSequentialGroup()
							.addComponent(alphaLabel)
							.addComponent(alphaField)
					)
					.addGroup(layout.createSequentialGroup()
							.addComponent(betaLabel)
							.addComponent(betaField)
					)
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(evaporationFactorLabel)
							.addComponent(evaporationFactorField)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(alphaLabel)
							.addComponent(alphaField)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(betaLabel)
							.addComponent(betaField)
					)
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