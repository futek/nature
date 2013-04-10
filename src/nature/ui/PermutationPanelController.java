package nature.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nature.Algorithm;
import nature.FitnessGoal;
import nature.Permutation;
import nature.ProgressListener;

public class PermutationPanelController {
	private PermutationPanel view;
	private Algorithm<Permutation> algorithm;
	private Permutation startState;

	public PermutationPanelController(PermutationPanel view) {
		this.view = view;

		InitializationPaneHandler initializationPaneHandler = new InitializationPaneHandler();
		view.initializationPane.loadButton.addActionListener(initializationPaneHandler);

		AlgorithmPaneHandler algorithmPaneHandler = new AlgorithmPaneHandler();
		view.algorithmPane.algoComboBox.addActionListener(algorithmPaneHandler);

		ControlPaneHandler controlPaneHandler = new ControlPaneHandler();
		view.controlPane.controlButton.addActionListener(controlPaneHandler);
		view.controlPane.resetButton.addActionListener(controlPaneHandler);
		view.controlPane.sleepSlider.addChangeListener(controlPaneHandler);
		view.controlPane.controlButton.setEnabled(false);
	}

	private class InitializationPaneHandler implements ActionListener {
		private JFileChooser fileChooser = new JFileChooser();

		@Override
		public void actionPerformed(ActionEvent evt) {
			System.out.println("actionCommand: " + evt.getActionCommand());
			if (evt.getActionCommand().equals("Load Graph")) {
				int returnVal = fileChooser.showOpenDialog(view);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();

					try {
						InputStream stream = new FileInputStream(file);
						startState = new Permutation(stream);
						view.initializationPane.statusLabel.setText("Status: Graph loaded (" + startState.numberOfNodes() + " nodes)");
						view.controlPane.controlButton.setEnabled(true);
						view.visualizationPane.drawingPane.setPermutation(startState);
					} catch (IllegalArgumentException | FileNotFoundException e) {
						view.initializationPane.statusLabel.setText("Status: " + e.getMessage());
					}
				}
			}
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
					FitnessGoal<Permutation> fitnessGoal = getFitnessGoal();

					// TODO: Handle invalid numbers...
//					int length = Integer.parseInt(view.initializationPane.lengthField.getText());
//					double prob = Double.parseDouble(view.initializationPane.probField.getText());
//					Permutation startState = new Permutation("1 2\n3 4\n");

					// TODO: Disable start button until graph has been loaded

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
					// view.visualizationPane.onionVisualizer.clearPoints();

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

	private class ProgressHandler implements ProgressListener<Permutation> {
		@Override
		public void step(long iteration) {
			view.controlPane.iterationLabel.setText("Iteration: " + iteration);
		}

		@Override
		public void select(Permutation state) {
			// view.visualizationPane.onionVisualizer.addPoint(state);
		}

		@Override
		public void done() {
			view.controlPane.controlButton.setText("Reset");
		}
	}

	private FitnessGoal<Permutation> getFitnessGoal() {
		FitnessGoal<Permutation> fitnessGoal = null;

		String goalSelection = (String) view.fitnessPane.goalComboBox.getSelectedItem();
		String funcSelection = (String) view.fitnessPane.funcComboBox.getSelectedItem();


		switch (funcSelection) {
			case "length of tour":
				if (goalSelection.equals("Maximize")) {
					fitnessGoal = new FitnessGoal<Permutation>() {
						@Override
						public int compare(Permutation original, Permutation mutation) {
							return new Double(original.lengthOfTour()).compareTo(mutation.lengthOfTour());
						}

						@Override
						public double difference(Permutation original, Permutation mutation) {
							return original.lengthOfTour() - mutation.lengthOfTour();
						}
					};
				} else {
					fitnessGoal = new FitnessGoal<Permutation>() {
						@Override
						public int compare(Permutation original, Permutation mutation) {
							return new Double(mutation.lengthOfTour()).compareTo(original.lengthOfTour());
						}

						@Override
						public double difference(Permutation original, Permutation mutation) {
							return mutation.lengthOfTour() - original.lengthOfTour();
						}
					};
				}
				break;
		}

		return fitnessGoal;
	}

	private Algorithm<Permutation> getAlgorithm(FitnessGoal<Permutation> fitnessGoal, Permutation startState) {
		// TODO: Implement
		return null;
	}
}
