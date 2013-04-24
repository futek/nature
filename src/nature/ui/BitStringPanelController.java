package nature.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nature.Algorithm;
import nature.BitString;
import nature.CoolingSchedule;
import nature.FitnessGoal;
import nature.MinMaxAntSystemBitString;
import nature.Nature;
import nature.OnePlusOneBitString;
import nature.ProgressListener;
import nature.SimulatedAnnealingBitString;

public class BitStringPanelController {
	private BitStringPanel view;
	private Algorithm<BitString> algorithm;

	public BitStringPanelController(BitStringPanel view) {
		this.view = view;

		InitializationPaneHandler initializationPaneHandler = new InitializationPaneHandler();
		view.initializationPane.customCheckBox.addActionListener(initializationPaneHandler);

		AlgorithmPaneHandler algorithmPaneHandler = new AlgorithmPaneHandler();
		view.algorithmPane.algoComboBox.addActionListener(algorithmPaneHandler);

		ControlPaneHandler controlPaneHandler = new ControlPaneHandler();
		view.controlPane.controlButton.addActionListener(controlPaneHandler);
		view.controlPane.resetButton.addActionListener(controlPaneHandler);
		view.controlPane.sleepSlider.addChangeListener(controlPaneHandler);
	}

	private class InitializationPaneHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean enableCustomField = view.initializationPane.customCheckBox.isSelected();
			view.initializationPane.lengthField.setEnabled(!enableCustomField);
			view.initializationPane.probField.setEnabled(!enableCustomField);
			view.initializationPane.customField.setEnabled(enableCustomField);
		}
	}

	private class AlgorithmPaneHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("comboBoxChanged")) {
				String selection = (String) view.algorithmPane.algoComboBox.getSelectedItem();

				view.algorithmPane.algoEAParams.setVisible(false);
				view.algorithmPane.algoSAParams.setVisible(false);
				view.algorithmPane.algoMMASParams.setVisible(false);

				switch (selection) {
					case "(1+1) Evolutionary Algorithm":
						view.algorithmPane.algoEAParams.setVisible(true);
						break;

					case "Simulated Annealing":
						view.algorithmPane.algoSAParams.setVisible(true);
						break;

					case "Min-Max Ant System":
						view.algorithmPane.algoMMASParams.setVisible(true);
						break;
				}

				Nature.frame.pack();
			}
		}
	}

	private class ControlPaneHandler implements ActionListener, ChangeListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "Start":
					FitnessGoal<BitString> fitnessGoal = getFitnessGoal();

					BitString startState = null;
					if (view.initializationPane.customCheckBox.isSelected()) {
						// TODO: Handle invalid bitstrings...
						startState = new BitString(view.initializationPane.customField.getText());
					} else {
						// TODO: Handle invalid numbers...
						int length = Integer.parseInt(view.initializationPane.lengthField.getText());
						double prob = Double.parseDouble(view.initializationPane.probField.getText());
						startState = new BitString(length, prob);
					}

					algorithm = getAlgorithm(fitnessGoal, startState);
					algorithm.sleep(view.controlPane.sleepSlider.getValue());
					algorithm.start();

					// Configure buttons
					view.controlPane.controlButton.setText("Pause");
					view.controlPane.resetButton.setEnabled(true);

					break;

				case "Pause":
					algorithm.pause();

					// Configure buttons
					view.controlPane.controlButton.setText("Resume");

					break;

				case "Resume":
					algorithm.resume();

					// Configure buttons
					view.controlPane.controlButton.setText("Pause");

					break;

				case "Reset":
					algorithm.cancel();
					algorithm = null;
					view.visualizationPane.onionVisualizer.clearPoints();

					// Configure buttons
					view.controlPane.controlButton.setText("Start");
					view.controlPane.resetButton.setEnabled(false);

					break;
			}
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			if (algorithm != null) {
				algorithm.sleep(view.controlPane.sleepSlider.getValue());
			}
		}
	}

	private class ProgressHandler implements ProgressListener<BitString> {
		@Override
		public void step(long iteration) {
			view.controlPane.iterationLabel.setText("Iteration: " + iteration);
		}

		@Override
		public void select(BitString state, int fitness) {
			view.fitnessPane.currentFitnessLabel.setText("Current fitness: " + fitness);
			view.visualizationPane.onionVisualizer.addPoint(state);
		}

		@Override
		public void done() {
			view.controlPane.controlButton.setText("Reset");
		}
	}

	private FitnessGoal<BitString> getFitnessGoal() {
		FitnessGoal<BitString> fitnessGoal = null;

		String goalSelection = (String) view.fitnessPane.goalComboBox.getSelectedItem();
		String funcSelection = (String) view.fitnessPane.funcComboBox.getSelectedItem();


		switch (funcSelection) {
			case "number of ones":
				if (goalSelection.equals("Maximize")) {
					fitnessGoal = new FitnessGoal<BitString>() {
						@Override
						public int evaluate(BitString bitString) {
							return bitString.numberOfOnes();
						}

						@Override
						public int compare(int originalFitness, int mutationFitness) {
							return new Integer(originalFitness).compareTo(mutationFitness);
						}

						@Override
						public int difference(int originalFitness, int mutationFitness) {
							return mutationFitness - originalFitness;
						}

						@Override
						public boolean isOptimal(BitString bitString, int fitness) {
							return fitness == bitString.length();
						}
					};
				} else {
					fitnessGoal = new FitnessGoal<BitString>() {
						@Override
						public int evaluate(BitString bitString) {
							return bitString.numberOfOnes();
						}

						@Override
						public int compare(int originalFitness, int mutationFitness) {
							return new Integer(mutationFitness).compareTo(originalFitness);
						}

						@Override
						public int difference(int originalFitness, int mutationFitness) {
							return originalFitness - mutationFitness;
						}

						@Override
						public boolean isOptimal(BitString bitString, int fitness) {
							return fitness == 0;
						}
					};
				}
				break;

			case "number of leading ones":
				if (goalSelection.equals("Maximize")) {
					fitnessGoal = new FitnessGoal<BitString>() {
						@Override
						public int evaluate(BitString bitString) {
							return bitString.numberOfLeadingOnes();
						}

						@Override
						public int compare(int originalFitness, int mutationFitness) {
							return new Integer(originalFitness).compareTo(mutationFitness);
						}

						@Override
						public int difference(int originalFitness, int mutationFitness) {
							return mutationFitness - originalFitness;
						}

						@Override
						public boolean isOptimal(BitString bitString, int fitness) {
							return fitness == bitString.length();
						}
					};
				} else {
					fitnessGoal = new FitnessGoal<BitString>() {
						@Override
						public int evaluate(BitString bitString) {
							return bitString.numberOfLeadingOnes();
						}

						@Override
						public int compare(int originalFitness, int mutationFitness) {
							return new Integer(mutationFitness).compareTo(originalFitness);
						}

						@Override
						public int difference(int originalFitness, int mutationFitness) {
							return originalFitness - mutationFitness;
						}

						@Override
						public boolean isOptimal(BitString bitString, int fitness) {
							return fitness == 0;
						}
					};
				}
				break;
		}

		return fitnessGoal;
	}

	private Algorithm<BitString> getAlgorithm(FitnessGoal<BitString> fitnessGoal, BitString startState) {
		// TODO: Read algo combobox and parameters
		String algoSelection = (String)view.algorithmPane.algoComboBox.getSelectedItem();

		switch (algoSelection) {
			case "(1+1) Evolutionary Algorithm":
				algorithm = new OnePlusOneBitString(new ProgressHandler(), fitnessGoal, startState, 1.0 / startState.length()); // TODO: Read parameter
				break;
			case "Simulated Annealing": // TODO: Handle invalid numbers
				double initialTemperature = Double.parseDouble(view.algorithmPane.initTempField.getText());
				double finalTemperature = Double.parseDouble(view.algorithmPane.finalTempField.getText());
				long maxTime = Integer.parseInt(view.algorithmPane.timeField.getText());
				CoolingSchedule coolingSchedule = CoolingSchedule.COOLING_SCHEDULES[view.algorithmPane.coolingComboBox.getSelectedIndex()];
				algorithm = new SimulatedAnnealingBitString(new ProgressHandler(), fitnessGoal, startState, initialTemperature, finalTemperature, maxTime, coolingSchedule);
				break;
			case "Min-Max Ant System": // TODO: Handle invalid numbers
				double evaporationFactor = Double.parseDouble(view.algorithmPane.evaporationFactorField.getText());
				algorithm = new MinMaxAntSystemBitString(new ProgressHandler(), fitnessGoal, startState, evaporationFactor);
				break;
		}

		return algorithm;
	}
}
