package nature;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Permutation {
	private static final Random random = new Random();

	List<Point2D.Double> graph = new ArrayList<Point2D.Double>();
	int[] permutation;

	public Permutation(List<Point2D.Double> graph, int[] permutation) {
		this.graph = graph;
		this.permutation = permutation;
	}

	public Permutation(String stream) throws IllegalArgumentException {
		// Load graph
		try {
			Scanner scanner = new Scanner(stream);
			while (scanner.hasNext()) {
				double x = scanner.nextDouble();
				double y = scanner.nextDouble();
				graph.add(new Point2D.Double(x, y));
			}
			scanner.close();
		} catch (InputMismatchException e) {
			throw new IllegalArgumentException("Invalid format");
		}

		// Initial permutation
		permutation = new int[graph.size()];
		for (int i = 0; i < permutation.length; i++) {
			permutation[i] = i;
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

	public Permutation twoOpt(Permutation p) {
		int[] permutation = p.permutation;

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

		return new Permutation(graph, mutation);
	}

	public Permutation threeOpt(Permutation p) {
		int[] permutation = p.permutation;

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

		return new Permutation(graph, mutation);
	}
}