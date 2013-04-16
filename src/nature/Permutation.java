package nature;

import java.awt.geom.Point2D;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Permutation {
	private static final Random random = new Random();

	private List<Point2D.Double> graph = new ArrayList<Point2D.Double>();
	private int[] permutation;
	private Point2D.Double minBounds, maxBounds;

	public Permutation(List<Point2D.Double> graph, int[] permutation, Point2D.Double minBounds, Point2D.Double maxBounds) {
		this.graph = graph;
		this.permutation = permutation;
		this.minBounds = minBounds;
		this.maxBounds = maxBounds;
	}

	public Permutation(InputStream stream) throws IllegalArgumentException {
		// Load graph
		try {
			Scanner scanner = new Scanner(stream);
			scanner.useLocale(Locale.US);
			while (scanner.hasNext()) {
				double x = scanner.nextDouble();
				double y = scanner.nextDouble();
				graph.add(new Point2D.Double(x, y));
			}
			scanner.close();
		} catch (InputMismatchException e) {
			throw new IllegalArgumentException("Invalid format");
		}

		// Calculate min/max bounds
		double minX = graph.get(0).x;
		double minY = graph.get(0).y;
		double maxX = minX;
		double maxY = minY;

		for (int i = 1; i < graph.size(); i++) {
			Point2D.Double point = graph.get(i);

			if (point.x < minX) minX = point.x;
			if (point.y < minY) minY = point.y;
			if (point.x > maxX) maxX = point.x;
			if (point.y > maxY) maxY = point.y;
		}

		minBounds = new Point2D.Double(minX, minY);
		maxBounds = new Point2D.Double(maxX, maxY);

		// Initial permutation
		permutation = new int[graph.size()];
		for (int i = 0; i < permutation.length; i++) {
			permutation[i] = i;
		}

		// Debug: shuffle permutation a bit
		for (int i = 0; i < permutation.length; i++) {
			int j = i + random.nextInt(permutation.length - i);
			int tmp = permutation[i];
			permutation[i] = permutation[j];
			permutation[j] = tmp;
		}
	}

	public double lengthOfTour() {
		double length = 0;

		for (int i = 1; i <= permutation.length; i++) {
			int srcIndex = permutation[i - 1];
			int dstIndex = permutation[i % permutation.length];

			Point2D.Double src = graph.get(srcIndex);
			Point2D.Double dst = graph.get(dstIndex);

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Point2D.Double node = graph.get(permutation[0]);
		sb.append("(" + node.x + "," + node.y + ")");
		for (int i = 1; i < permutation.length; i++) {
			node = graph.get(permutation[i]);
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

	public List<Point2D.Double> getGraph() {
		return graph;
	}

	public Point2D.Double getMinBounds() {
		return minBounds;
	}

	public Point2D.Double getMaxBounds() {
		return maxBounds;
	}
}