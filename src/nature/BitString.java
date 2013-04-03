package nature;

public class BitString {
	private boolean[] string;
//	Fitness<BitString> fitness;
//
//	public BitString(Fitness<BitString> fitness) {
//		this.fitness = fitness;
//	}

	public BitString(int length, double probability) {
		string = new boolean[length];
		for (int i = 0; i < string.length; i++) {
			string[i] = (Math.random() < probability);
		}
	}

	public BitString(boolean[] string) {
		this.string = string;
	}

	public BitString(String textString) {
		string = new boolean[textString.length()];
		for (int i = 0; i < textString.length(); i++) {
			char c = textString.charAt(i);
			string[i] = (c == '0' ? false : true);
		}
	}

	public int length() {
		return string.length;
	}

	public int numberOfOnes() {
		int ones = 0;
		for (boolean bit : string) {
			if (bit) ones++;
		}
		return ones;
	}

	public int numberOfLeadingOnes() {
		int ones = 0;
		for (boolean bit : string) {
			ones++;
			if (!bit) return ones;
		}
		return ones;
	}

//	public void OnePlusOne(double globalMutationProbability) {
//		boolean[] current = string;
//		double optimumFitness = fitness.optimum(current);
//		long iterations = 0;
//
//		while (fitness.optimum(current)) {
//			iterations++;
//
//			// publish(iteration)
//
//			boolean[] mutation = globalMutation(current, globalMutationProbability);
//
//			double currentFitness = fitness.evaluate(current);
//			double mutationFitness = fitness.evaluate(mutation);
//
//			if (mutationFitness >= currentFitness) {
//				// publish(mutation);
//				current = mutation;
//			}
//
//			if (mutationFitness == optimumFitness) {
//				break;
//			}
//		}
//	}
//
//	private boolean[] globalMutation(boolean[] original, double probability) {
//		boolean[] mutation = new boolean[original.length];
//		for (int i = 0; i < original.length; i++) {
//			mutation[i] = (Math.random() >= probability ? original[i] : !original[i]);
//		}
//		return mutation;
//	}

	public BitString globalMutation(double probability) {
		boolean[] mutation = new boolean[string.length];
		for (int i = 0; i < string.length; i++) {
			mutation[i] = (Math.random() >= probability ? string[i] : !string[i]);
		}
		return new BitString(mutation);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (boolean bit : string) {
			sb.append((bit ? '1' : '0'));
		}
		return sb.toString();
	}
}
