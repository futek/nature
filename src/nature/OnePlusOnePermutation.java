package nature;

import java.util.Random;

public class OnePlusOnePermutation extends Algorithm<Permutation> {
	private static final Random random = new Random();
	
	private FitnessGoal<Permutation> fitnessGoal;
	private Permutation current;

	public OnePlusOnePermutation(ProgressListener<Permutation> progressListener, FitnessGoal<Permutation> fitnessGoal, Permutation permutation) {
		super(progressListener);

		this.fitnessGoal = fitnessGoal;
		this.current = permutation;
	}

	@Override
	public void init() {
	}

	@Override
	public void step(long iteration) {
		Permutation mutation = (random.nextDouble() < 0.5 ? current.twoOpt() : current.threeOpt());

		double currentFitness = fitnessGoal.evaluate(current);
		double mutationFitness = fitnessGoal.evaluate(mutation);

		if (fitnessGoal.compare(currentFitness, mutationFitness) <= 0) {
			current = mutation;

			progressListener.select(current);
			if (fitnessGoal.isOptimal(current, currentFitness)) {
				progressListener.done();
				cancel();
			}
		}
	}
}
