package nature;

public class OnePlusOneBitString extends Algorithm<BitString> {
	private FitnessGoal<BitString> fitnessGoal;
	private BitString current;
	private double globalMutationProbability;

	public OnePlusOneBitString(ProgressListener<BitString> progressListener, FitnessGoal<BitString> fitnessGoal, BitString bitString, double globalMutationProbability) {
		super(progressListener);

		this.fitnessGoal = fitnessGoal;
		this.current = bitString;
		this.globalMutationProbability = globalMutationProbability;
	}

	@Override
	public void init() {
	}

	@Override
	public void step(long iteration) {
		BitString mutation = current.globalMutation(globalMutationProbability);

		if (fitnessGoal.compare(current, mutation) <= 0) {
			current = mutation;

			progressListener.select(current);
			if (fitnessGoal.isOptimal(current)) {
				progressListener.done();
				cancel();
			}
		}
	}
}
