package nature.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nature.Algorithm;
import nature.BitString;
import nature.FitnessGoal;
import nature.OnePlusOneBitString;
import nature.ProgressListener;

public class BitStringPanelController {
	private BitStringPanel view;
	private Algorithm<BitString> algorithm;

	public BitStringPanelController(BitStringPanel view) {
		this.view = view;

		AlgorithmPaneHandler algorithmPaneHandler = new AlgorithmPaneHandler();
		view.algorithmPane.algoComboBox.addActionListener(algorithmPaneHandler);


		ControlPaneHandler controlPaneHandler = new ControlPaneHandler();
		view.controlPane.controlButton.addActionListener(controlPaneHandler);
		view.controlPane.resetButton.addActionListener(controlPaneHandler);
		view.controlPane.sleepSlider.addChangeListener(controlPaneHandler);
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

				view.repaint();
				view.revalidate();
				view.repaint();
				view.revalidate();
			}
		}
	}

	private class ControlPaneHandler implements ActionListener, ChangeListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "Start":
					FitnessGoal<BitString> fitnessGoal = getFitnessGoal();

					// TODO: Handle invalid numbers...
					int length = Integer.parseInt(view.initializationPane.lengthField.getText());
					double prob = Double.parseDouble(view.initializationPane.probField.getText());
					BitString startState = new BitString(length, prob);

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
		public void select(BitString state) {
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
						public int compare(BitString original, BitString mutation) {
							return new Integer(original.numberOfOnes()).compareTo(mutation.numberOfOnes());
						}

						@Override
						public double difference(BitString original, BitString mutation) {
							return (double)(original.numberOfOnes() -  mutation.numberOfOnes());
						}

						@Override
						public boolean isOptimal(BitString state) {
							return state.numberOfOnes() == state.length();
						}
					};
				} else {
					fitnessGoal = new FitnessGoal<BitString>() {
						@Override
						public int compare(BitString original, BitString mutation) {
							return new Integer(mutation.numberOfOnes()).compareTo(original.numberOfOnes());
						}

						@Override
						public double difference(BitString original, BitString mutation) {
							return (double)(mutation.numberOfOnes() - original.numberOfOnes());
						}

						@Override
						public boolean isOptimal(BitString state) {
							return state.numberOfOnes() == 0;
						}
					};
				}
				break;

			case "number of leading ones":
				if (goalSelection.equals("Maximize")) {
					fitnessGoal = new FitnessGoal<BitString>() {
						@Override
						public int compare(BitString original, BitString mutation) {
							return new Integer(original.numberOfLeadingOnes()).compareTo(mutation.numberOfLeadingOnes());
						}

						@Override
						public double difference(BitString original, BitString mutation) {
							return (double)(original.numberOfLeadingOnes() - mutation.numberOfLeadingOnes());
						}

						@Override
						public boolean isOptimal(BitString state) {
							return state.numberOfOnes() == state.length();
						}
					};
				} else {
					fitnessGoal = new FitnessGoal<BitString>() {
						@Override
						public int compare(BitString original, BitString mutation) {
							return new Integer(mutation.numberOfLeadingOnes()).compareTo(original.numberOfLeadingOnes());
						}

						@Override
						public double difference(BitString original, BitString mutation) {
							return (double)(mutation.numberOfLeadingOnes() - original.numberOfLeadingOnes());
						}

						@Override
						public boolean isOptimal(BitString state) {
							return state.numberOfLeadingOnes() == 0;
						}
					};
				}
				break;
		}

		return fitnessGoal;
	}

	private Algorithm<BitString> getAlgorithm(FitnessGoal<BitString> fitnessGoal, BitString startState) {
		// TODO: Read algo combobox and parameters
		algorithm = new OnePlusOneBitString(new ProgressHandler(), fitnessGoal, startState, 1.0 / startState.length()); // TODO: Read parameter

		return algorithm;
	}
}
