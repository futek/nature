package nature;

import java.util.Random;

public class SimulatedAnnealingBitString extends Algorithm<BitString> {
	private static final Random random = new Random();

	private FitnessGoal<BitString> fitnessGoal;
	private BitString current;
	private double initialTemperature;
	private double finalTemperature;
	private long maxTime;

	private CoolingSchedule coolingSchedule;


	public SimulatedAnnealingBitString(ProgressListener<BitString> progressListener, FitnessGoal<BitString> fitnessGoal, BitString bitString, double initialTemperature, double finalTemperature, long maxTime, CoolingSchedule coolingSchedule) {
		super(progressListener);

		this.initialTemperature = initialTemperature;
		this.finalTemperature = finalTemperature;
		this.maxTime = maxTime;
		this.fitnessGoal = fitnessGoal;
		this.current = bitString;
		this.coolingSchedule = coolingSchedule;
	}

//	private double temperature(long time) {
//		return initialTemperature - time * (initialTemperature / maxTime);
//	}

	private double alpha(long time) {
		return Math.exp(1.0 / coolingSchedule.temperature(initialTemperature, finalTemperature, maxTime, time));
	}

	@Override
	public void init() {
	}

	@Override
	public void step(long iteration) {		
		BitString mutation = current.localMutation();

		int currentFitness = fitnessGoal.evaluate(current);
		int mutationFitness = fitnessGoal.evaluate(mutation);

		double probability = Math.min(1.0, Math.pow(alpha(iteration), fitnessGoal.difference(currentFitness, mutationFitness)));
		if (random.nextDouble() < probability) {
			current = mutation;

			progressListener.select(current, currentFitness);
			if (fitnessGoal.isOptimal(current, currentFitness)) {
				progressListener.done();
				cancel();
			}
		}
		
		if (iteration >= maxTime) {
			progressListener.done();
			cancel();
		}
	}

	// COOLING SCHEDULES!
	public static final CoolingSchedule COOLING_SCHEDULE_0 = new CoolingSchedule() {
		@Override
		public double temperature(double T0, double TN, long N, long i) {
			return T0 - i * (T0 - TN)/N;
		}
	};
	public static final CoolingSchedule COOLING_SCHEDULE_1 = new CoolingSchedule() {
		@Override
		public double temperature(double T0, double TN, long N, long i) {
			return T0 * Math.pow(TN/T0, (double)i/N);
		}
	};
	public static final CoolingSchedule COOLING_SCHEDULE_2 = new CoolingSchedule() {
		@Override
		public double temperature(double T0, double TN, long N, long i) {
			double A = ((T0-TN) * (N+1))/N;
			double B = T0-A;
			return A/(i+1) + B;
		}
	};
	public static final CoolingSchedule COOLING_SCHEDULE_3 = new CoolingSchedule() {
		@Override
		public double temperature(double T0, double TN, long N, long i) {
			double A = Math.log(T0-TN)/Math.log(N);
			return T0 - Math.pow(i, A);
		}
	};
	public static final CoolingSchedule COOLING_SCHEDULE_4 = new CoolingSchedule() {
		@Override
		public double temperature(double T0, double TN, long N, long i) {
			return (T0-TN)/(1+Math.exp(0.3 * (i- N/2.0))) + TN; 
		}
	};
	public static final CoolingSchedule COOLING_SCHEDULE_5 = new CoolingSchedule() {
		@Override
		public double temperature(double T0, double TN, long N, long i) {
			return 0.5 * (T0-TN) * (1+Math.cos((i*Math.PI)/N)) + TN;
		}
	};
	public static final CoolingSchedule COOLING_SCHEDULE_6 = new CoolingSchedule() {
		@Override
		public double temperature(double T0, double TN, long N, long i) {
			return 0.5 * (T0-TN) * (1+Math.tanh((10.0*i)/N - 5)) + TN;
		}
	};
	public static final CoolingSchedule COOLING_SCHEDULE_7 = new CoolingSchedule() {
		@Override
		public double temperature(double T0, double TN, long N, long i) {
			return (T0-TN)/Math.cos((10.0*i)/N) + TN;
		}
	};
	public static final CoolingSchedule COOLING_SCHEDULE_8 = new CoolingSchedule() {
		@Override
		public double temperature(double T0, double TN, long N, long i) {
			double A = 1.0/N * Math.log(T0/TN);
			return T0 * Math.exp(-A * i);
		}
	};
	public static final CoolingSchedule COOLING_SCHEDULE_9 = new CoolingSchedule() {
		@Override
		public double temperature(double T0, double TN, long N, long i) {
			double A = 1.0/(N*N) * Math.log(T0/TN);
			return T0 * Math.exp(-A * i*i);
		}
	};
	public static final CoolingSchedule[] COOLING_SCHEDULES = new CoolingSchedule[]{
		COOLING_SCHEDULE_0,
		COOLING_SCHEDULE_1,
		COOLING_SCHEDULE_2,
		COOLING_SCHEDULE_3,
		COOLING_SCHEDULE_4,
		COOLING_SCHEDULE_5,
		COOLING_SCHEDULE_6,
		COOLING_SCHEDULE_7,
		COOLING_SCHEDULE_8,
		COOLING_SCHEDULE_9
	};
	
}
