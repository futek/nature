package nature;

import java.util.Random;

public class SimulatedAnnealingBitString extends Algorithm<BitString> {
	private FitnessGoal<BitString> fitnessGoal;
	private BitString current;
	private double initialTemperature;
	private int maxTime;
	private Random random;

	public SimulatedAnnealingBitString(ProgressListener<BitString> progressListener, FitnessGoal<BitString> fitnessGoal, BitString bitString, double initialTemperature, int maxTime) {
		super(progressListener);
		
		this.initialTemperature = initialTemperature;
		this.maxTime = maxTime;
		this.fitnessGoal = fitnessGoal;
		this.current = bitString;
	}
	
	private double temperature(double time) {
		return initialTemperature - time * (initialTemperature / maxTime);
	}
	
	private double alpha(double time) {
		return Math.exp(1.0 / temperature(time));
	}

	@Override
	public void init() {
	}

	@Override
	public void step(long iteration) {
		BitString mutation = current.localMutation();

		double probability = Math.min(1.0, Math.pow(alpha(iteration), fitnessGoal.difference(current, mutation)));
		if (random.nextDouble() < probability) {
			current = mutation;

			progressListener.select(current);
			if (fitnessGoal.isOptimal(current)) {
				progressListener.done();
				cancel();
			}
		}
		
		

		

		


		
	}
}
