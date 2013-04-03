package nature;

public abstract class Fitness<T> {
	public abstract double evaluate(T state);
	public abstract boolean isBetter(T original, T mutation);
	public abstract double optimum();
}
