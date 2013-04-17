package nature;

public abstract class FitnessGoal<T> {
	public abstract int evaluate(T state);
	public abstract int compare(int originalFitness, int mutationFitness);
	public abstract int difference(int originalFitness, int mutationFitness);

	public boolean isOptimal(T state, int fitness) {
		return false;
	}
}
