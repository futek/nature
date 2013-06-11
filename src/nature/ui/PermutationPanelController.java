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
import nature.CoolingSchedule;
import nature.FitnessGoal;
import nature.MinMaxAntSystemPermutation;
import nature.Nature;
import nature.OnePlusOnePermutation;
import nature.Permutation;
import nature.ProgressListener;
import nature.SimulatedAnnealingPermutation;

public class PermutationPanelController {
	private PermutationPanel view;
	private Algorithm<Permutation> algorithm;
	private Permutation startState;

	public PermutationPanelController(PermutationPanel view) {
		this.view = view;

		InitializationPaneHandler initializationPaneHandler = new InitializationPaneHandler();
		view.initializationPane.loadButton.addActionListener(initializationPaneHandler);
		view.initializationPane.randomizeButton.addActionListener(initializationPaneHandler);

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
			switch (evt.getActionCommand()) {
				case "Load Graph":
					int returnVal = fileChooser.showOpenDialog(view);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();

						try {
							InputStream stream = new FileInputStream(file);
							startState = new Permutation(stream);

							String html = "<html>";
							html += "Status: Graph loaded (" + startState.numberOfNodes() + " nodes)";
							if (startState.getName() != null) {
								html += "<br>Name: " + startState.getName();
							}
							if (startState.getComment() != null) {
								html += "<br>" + startState.getComment();
							}
							html += "<html>";
							view.initializationPane.statusLabel.setText(html);

							view.controlPane.controlButton.setEnabled(true);
							view.initializationPane.randomizeButton.setEnabled(true);

							view.fitnessPane.currentFitnessLabel.setText("Current fitness: " + getFitnessGoal().evaluate(startState));
							view.visualizationPane.drawingPane.setPermutation(startState);
						} catch (IllegalArgumentException | FileNotFoundException e) {
							view.initializationPane.statusLabel.setText("Status: " + e.getMessage());
						}
					}
					break;
				case "Randomize":
					startState = startState.randomize();
					view.fitnessPane.currentFitnessLabel.setText("Current fitness: " + getFitnessGoal().evaluate(startState));
					view.visualizationPane.drawingPane.setPermutation(startState);
					break;
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

				Nature.frame.pack();
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
					view.initializationPane.randomizeButton.setEnabled(false);

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
					view.visualizationPane.drawingPane.setPermutation(startState);

					// Configure buttons
					view.controlPane.controlButton.setText("Start");
					view.controlPane.resetButton.setEnabled(false);
					view.initializationPane.randomizeButton.setEnabled(true);

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
		public void select(Permutation state, int fitness) {
			view.fitnessPane.currentFitnessLabel.setText("Current fitness: " + fitness);
			view.visualizationPane.drawingPane.setPermutation(state);
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
						public int evaluate(Permutation permutation) {
							return (int) Math.round(permutation.lengthOfTour());
						}

						@Override
						public int compare(int originalFitness, int mutationFitness) {
							return new Integer(originalFitness).compareTo(mutationFitness);
						}

						@Override
						public int difference(int originalFitness, int mutationFitness) {
							return mutationFitness - originalFitness;
						}
					};
				} else {
					fitnessGoal = new FitnessGoal<Permutation>() {
						@Override
						public int evaluate(Permutation permutation) {
							return (int) Math.round(permutation.lengthOfTour());
						}

						@Override
						public int compare(int originalFitness, int mutationFitness) {
							return new Integer(mutationFitness).compareTo(originalFitness);
						}

						@Override
						public int difference(int originalFitness, int mutationFitness) {
							return originalFitness - mutationFitness;
						}
					};
				}
				break;
		}

		return fitnessGoal;
	}

	private Algorithm<Permutation> getAlgorithm(FitnessGoal<Permutation> fitnessGoal, Permutation startState) {
		Algorithm<Permutation> algorithm = null;

		String algoSelection = (String)view.algorithmPane.algoComboBox.getSelectedItem();
		switch (algoSelection) {
			case "(1+1) Evolutionary Algorithm":
				algorithm = new OnePlusOnePermutation(new ProgressHandler(), fitnessGoal, startState);
				break;
			case "Simulated Annealing": // TODO: Handle invalid numbers
				double initialTemperature = Double.parseDouble(view.algorithmPane.initTempField.getText());
				double finalTemperature = Double.parseDouble(view.algorithmPane.finalTempField.getText());
				long maxTime = Integer.parseInt(view.algorithmPane.timeField.getText());
				CoolingSchedule coolingSchedule = CoolingSchedule.COOLING_SCHEDULES[view.algorithmPane.coolingComboBox.getSelectedIndex()];
				algorithm = new SimulatedAnnealingPermutation(new ProgressHandler(), fitnessGoal, startState, initialTemperature, finalTemperature, maxTime, coolingSchedule);
				break;
			case "Min-Max Ant System": // TODO: Handle invalid numbers
				double evaporationFactor = Double.parseDouble(view.algorithmPane.evaporationFactorField.getText());
				double alpha = Double.parseDouble(view.algorithmPane.alphaField.getText());
				double beta = Double.parseDouble(view.algorithmPane.betaField.getText());
				algorithm = new MinMaxAntSystemPermutation(new ProgressHandler(), fitnessGoal, startState, evaporationFactor, alpha, beta);
				break;
		}

		return algorithm;
	}
}
