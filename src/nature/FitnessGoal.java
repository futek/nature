package nature;

public abstract class FitnessGoal<T> {
	public abstract double evaluate(T state);
	public abstract int compare(double originalFitness, double mutationFitness);
	public abstract double difference(double originalFitness, double mutationFitness);

	public boolean isOptimal(T state, double fitness) {
		return false;
	}
}
