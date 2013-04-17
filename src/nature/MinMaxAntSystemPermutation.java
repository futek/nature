package nature;

public class MinMaxAntSystemPermutation extends Algorithm<Permutation> {
	private FitnessGoal<Permutation> fitnessGoal;
	private Permutation current;
	private double rho;
	private double[] pheromone;

	public MinMaxAntSystemPermutation(ProgressListener<Permutation> progressListener, FitnessGoal<Permutation> fitnessGoal, Permutation Permutation, double rho) {
		super(progressListener);

		this.rho = rho;
		this.fitnessGoal = fitnessGoal;
		this.current = Permutation;
	}

	private void updatePheromones(Permutation Permutation) {
//		boolean[] string = Permutation.getString();
//		double tauMin = 1.0 / Permutation.length();
//		double tauMax = 1.0 - 1.0 / Permutation.length();
//		for (int i = 0; i < pheromone.length; i++) {
//			if (string[i]) {
//				pheromone[i] = Math.min((1.0 - rho) * pheromone[i] + rho, tauMax);
//			} else {
//				pheromone[i] = Math.max((1.0 - rho) * pheromone[i], tauMin);
//			}
//		}
	}

	@Override
	public void init() {
//		pheromone = new double[current.length()];
//		current = current.constructMutation(pheromone);
//		updatePheromones(current);
	}

	@Override
	public void step(long iteration) {
//		Permutation mutation = current.constructMutation(pheromone);
//
//		double currentFitness = fitnessGoal.evaluate(current);
//		double mutationFitness = fitnessGoal.evaluate(mutation);
//
//		if (fitnessGoal.compare(currentFitness, mutationFitness) <= 0) {
//			current = mutation;
//
//			progressListener.select(current);
//			if (fitnessGoal.isOptimal(current, currentFitness)) {
//				progressListener.done();
//				cancel();
//			}
//		}
//
//		updatePheromones(current);
	}
}
