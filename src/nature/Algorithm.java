/* Inspired by http://stackoverflow.com/a/10669623 */

package nature;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Algorithm<T> implements Runnable {
	private final ReadWriteLock pause = new ReentrantReadWriteLock();
	private volatile boolean cancelled = false;
	private Exception thrown = null;

	protected ProgressListener<T> progressListener;
	private long iteration = 0;
	private long sleep = 0;

	public Algorithm(ProgressListener<T> progressListener) {
		this.progressListener = progressListener;
	}

	@Override
	public void run() {
		try {
			init();

			while (!cancelled) {
				blockIfPaused();

				iteration++;
				step(iteration);
				progressListener.step(iteration);

				if (sleep > 0) {
					Thread.sleep(sleep);
				}
			}
		} catch (Exception e) {
			thrown = e;
			e.printStackTrace();
		}
	}

	private void blockIfPaused() throws InterruptedException {
		try {
			pause.writeLock().lockInterruptibly();
		} finally {
			pause.writeLock().unlock();
		}
	}

	public void pause() {
		pause.readLock().lock();
	}

	public void resume() {
		pause.readLock().unlock();
	}

	public void cancel() {
		cancelled = true;
	}

	public void sleep(long millis) {
		this.sleep = millis;
	}

	public void start() {
		new Thread(this).start();
	}

	public Exception getThrown() {
		return thrown;
	}

	public abstract void init() throws Exception;
	public abstract void step(long iteration) throws Exception;
}
