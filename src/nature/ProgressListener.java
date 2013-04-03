package nature;

public interface ProgressListener<T> {
	public void step(long iteration);
	public void select(T state);
	public void done();
}
