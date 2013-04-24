package nature;

public interface CoolingSchedule {
	double temperature(double initialTemperature, double finalTemperature, long maxTime, long time);
}
