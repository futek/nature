package nature;

import java.awt.geom.Point2D;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Permutation {
	private static final Random random = new Random();
	private static final Pattern keywordPattern = Pattern.compile("^([A-Z_]+)\\p{Blank}*:\\p{Blank}*(\\p{Print}+)\\p{Blank}*$");
	private static final Pattern coordPattern = Pattern.compile("^(\\d+)\\p{Blank}+(\\d+(?:\\.\\d*)?)\\p{Blank}+(\\d+(?:\\.\\d*)?)\\p{Blank}*$");

	//private List<Point2D.Double> graph = new ArrayList<Point2D.Double>();
	private Point2D.Double[] graph;
	private int[] permutation;
	private Point2D.Double minBounds, maxBounds;

	public Permutation(Point2D.Double[] graph, int[] permutation, Point2D.Double minBounds, Point2D.Double maxBounds) {
		this.graph = graph;
		this.permutation = permutation;
		this.minBounds = minBounds;
		this.maxBounds = maxBounds;
	}

	public Permutation(InputStream stream) throws IllegalArgumentException {
		// Parse graph
		String name = "Unnamed";
		String comment = null;
		int dimension = -1;

		boolean inDataSection = false;
		Scanner scanner = new Scanner(stream);
		try {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (inDataSection) {
					if (dimension == -1) {
						throw new IllegalArgumentException("Dimensions not specified.");
					}
					Matcher matcher = coordPattern.matcher(line);
					if (matcher.matches()) {
						try {
							int i = Integer.parseInt(matcher.group(1));
							if (i < 1 || i > dimension) {
								throw new IllegalArgumentException("Coordinate index out of bounds.");
							}

							double x = Double.parseDouble(matcher.group(2));
							double y = Double.parseDouble(matcher.group(3));

							graph[i - 1] = new Point2D.Double(x, y);
						} catch (NumberFormatException e) {
							throw new IllegalArgumentException(e.getMessage());
						}
					} else if (line.equals("EOF")) {
						break;
					} else {
						throw new IllegalArgumentException("Invalid coordinate format.");
					}
				} else {
					Matcher matcher = keywordPattern.matcher(line);
					if (matcher.matches()) {
						String key = matcher.group(1);
						String value = matcher.group(2);
						switch (key) {
							case "NAME": name = value; break;
							case "COMMENT": comment = value; break;
							case "TYPE":
								if (!value.equals("TSP"))
									throw new IllegalArgumentException("Type \"" + value + "\" not supported.");
								break;
							case "DIMENSION":
								try {
									dimension = Integer.parseInt(value);
									if (dimension < 3) {
										throw new IllegalArgumentException("Invalid dimension: " + dimension);
									}
									graph = new Point2D.Double[dimension];
								} catch (NumberFormatException e) {
									throw new IllegalArgumentException(e.getMessage());
								}
								break;
							case "EDGE_WEIGHT_TYPE":
								if (!value.equals("EUC_2D"))
									throw new IllegalArgumentException("Weight type \"" + value + "\" not supported.");
								break;
						}
					} else if (line.equals("NODE_COORD_SECTION")) {
						inDataSection = true;
					} else {
						throw new IllegalArgumentException("Invalid file format.");
					}
				}
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		// Calculate min/max bounds
		double minX = graph[0].x;
		double minY = graph[0].y;
		double maxX = minX;
		double maxY = minY;

		for (int i = 1; i < graph.length; i++) {
			Point2D.Double point = graph[i];

			if (point.x < minX) minX = point.x;
			if (point.y < minY) minY = point.y;
			if (point.x > maxX) maxX = point.x;
			if (point.y > maxY) maxY = point.y;
		}

		minBounds = new Point2D.Double(minX, minY);
		maxBounds = new Point2D.Double(maxX, maxY);

		// Initial permutation
		permutation = new int[graph.length];
		for (int i = 0; i < permutation.length; i++) {
			permutation[i] = i;
		}

//		// Debug: shuffle permutation a bit
//		for (int i = 0; i < permutation.length; i++) {
//			int j = i + random.nextInt(permutation.length - i);
//			int tmp = permutation[i];
//			permutation[i] = permutation[j];
//			permutation[j] = tmp;
//		}
	}

	public int length() {
		return graph.length;
	}

	public double lengthOfTour() {
		double length = 0;

		for (int i = 1; i <= permutation.length; i++) {
			int srcIndex = permutation[i - 1];
			int dstIndex = permutation[i % permutation.length];

			Point2D.Double src = graph[srcIndex];
			Point2D.Double dst = graph[dstIndex];

			length += src.distance(dst);
		}

		return length;
	}

	public Permutation twoOpt() {
		int n = permutation.length;

		int[] mutation = new int[n];

		int a = random.nextInt(n - 1);
		int b = random.nextInt(n - 1 - a) + a + 1;

		for (int i = 0; i < a; i++) {
			mutation[i] = permutation[i];
		}

		for (int i = a; i <= b; i++) {
			mutation[i] = permutation[b - i + a];
		}

		for (int i = b + 1; i < permutation.length; i++) {
			mutation[i] = permutation[i];
		}

		return new Permutation(graph, mutation, minBounds, maxBounds);
	}

	public Permutation threeOpt() {
		int n = permutation.length;

		int[] mutation = new int[n];

		int a = random.nextInt(n - 2);
		int c = random.nextInt(n - 2 - a) + a + 2;
		int b = random.nextInt(c - a - 1) + a + 1;

		for (int i = 0; i < a; i++) {
			mutation[i] = permutation[i];
		}

		for (int i = a; i <= a + (c - b - 1); i++) {
			mutation[i] = permutation[i + (b - a + 1)];
		}

		for (int i =  a + (c - b - 1) + 1; i <= c; i++) {
			mutation[i] = permutation[i - (c - b)];
		}

		for (int i = c + 1; i < permutation.length; i++) {
			mutation[i] = permutation[i];
		}

		return new Permutation(graph, mutation, minBounds, maxBounds);
	}

	public Permutation constructMutation(double[][] pheromone, double alpha, double beta) {
		int[] mutation = new int[permutation.length];

		Set<Integer> nonVisited = new HashSet<Integer>();
		for (int i = 1; i < permutation.length; i++) {
			nonVisited.add(permutation[i]);
		}

		int v = permutation[0];
		int i = 0;

		while (nonVisited.size() > 0) {
			mutation[i] = v;

			double R = 0;
			for (int neighbor : nonVisited) {
				Point2D.Double src = graph[v];
				Point2D.Double dst = graph[neighbor];
				double weight = Point2D.Double.distance(src.x, src.y, dst.x, dst.y);
				double heuristic = 1.0 / weight;
				R += Math.pow(pheromone[v][neighbor], alpha) * Math.pow(heuristic, beta);
			}

			double r = random.nextDouble();
			int w = -1;

			int j = 0;
			double psum = 0;
			for (int neighbor : nonVisited) {
				Point2D.Double src = graph[v];
				Point2D.Double dst = graph[neighbor];
				double weight = Point2D.Double.distance(src.x, src.y, dst.x, dst.y);
				double heuristic = 1.0 / weight;

				double p = Math.pow(pheromone[v][neighbor], alpha) * Math.pow(heuristic, beta) / R;
				psum += p;


				if (r < psum || j == nonVisited.size() - 1) {
					w = neighbor;
					break;
				}

				j++;
			}

			v = w;
			nonVisited.remove(w);

			i++;
		}

		mutation[i] = v;

		return new Permutation(graph, mutation, minBounds, maxBounds);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Point2D.Double node = graph[permutation[0]];
		sb.append("(" + node.x + "," + node.y + ")");
		for (int i = 1; i < permutation.length; i++) {
			node = graph[permutation[i]];
			sb.append(" --> (" + node.x + "," + node.y + ")");
		}
		return sb.toString();
	}

	public int numberOfNodes() {
		return permutation.length;
	}

	public int[] getPermutation() {
		return permutation;
	}

	public Point2D.Double[] getGraph() {
		return graph;
	}

	public Point2D.Double getMinBounds() {
		return minBounds;
	}

	public Point2D.Double getMaxBounds() {
		return maxBounds;
	}
}