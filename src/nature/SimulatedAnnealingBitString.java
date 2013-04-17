package nature;

import java.util.Random;

public class SimulatedAnnealingBitString extends Algorithm<BitString> {
	private static final Random random = new Random();

	private FitnessGoal<BitString> fitnessGoal;
	private BitString current;
	private double initialTemperature;
	private int maxTime;

	public SimulatedAnnealingBitString(ProgressListener<BitString> progressListener, FitnessGoal<BitString> fitnessGoal, BitString bitString, double initialTemperature, int maxTime) {
		super(progressListener);

		this.initialTemperature = initialTemperature;
		this.maxTime = maxTime;
		this.fitnessGoal = fitnessGoal;
		this.current = bitString;
	}

	private double temperature(long time) {
		return initialTemperature - time * (initialTemperature / maxTime);
	}

	private double alpha(long time) {
		return Math.exp(1.0 / temperature(time));
	}

	@Override
	public void init() {
	}

	@Override
	public void step(long iteration) {		
		BitString mutation = current.localMutation();

		int currentFitness = fitnessGoal.evaluate(current);
		int mutationFitness = fitnessGoal.evaluate(mutation);

		double probability = Math.min(1.0, Math.pow(alpha(iteration), fitnessGoal.difference(currentFitness, mutationFitness)));
		if (random.nextDouble() < probability) {
			current = mutation;

			progressListener.select(current, currentFitness);
			if (fitnessGoal.isOptimal(current, currentFitness)) {
				progressListener.done();
				cancel();
			}
		}
		
		if (iteration >= maxTime) {
			progressListener.done();
			cancel();
		}
	}
}
