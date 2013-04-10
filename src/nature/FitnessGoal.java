package nature;

public abstract class FitnessGoal<T> {
	public abstract int compare(T original, T mutation);
	public abstract double difference(T original, T mutation);

	public boolean isOptimal(T state) {
		return false;
	}
}
