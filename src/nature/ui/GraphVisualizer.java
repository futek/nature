package nature.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import nature.Permutation;

public class GraphVisualizer extends JPanel {
	private static final int NODE_SIZE = 4	;
	private static final Color COLOR = new Color(0, 0, 0);
	private static int padding = 32;

	private Permutation permutation;
	private double[][] pheromone = null;

	private Point transform(Point2D.Double p, Point2D.Double minBounds, Point2D.Double maxBounds, Dimension size) {
		double paddingX = ((double) padding / size.width) * (maxBounds.x - minBounds.x);
		double paddingY = ((double) padding / size.height) * (maxBounds.y - minBounds.y);

		int x = (int) ((p.x - (minBounds.x - paddingX)) / ((maxBounds.x + paddingX) - (minBounds.x - paddingX)) * size.width);
		int y = (int) ((p.y - (minBounds.y - paddingY)) / ((maxBounds.y + paddingY) - (minBounds.y - paddingY)) * size.height);

		return new Point(x, y);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		Dimension size = getSize();

		if (permutation == null) return;

		int[] permutationArray = permutation.getPermutation();
		Point2D.Double[] graph = permutation.getGraph();
		Point2D.Double minBounds = permutation.getMinBounds();
		Point2D.Double maxBounds = permutation.getMaxBounds();

		// Draw pheromones
		if (pheromone != null) {
			double tauMin = 1.0 / permutation.length();
			double tauMax = 1.0 - 1.0 / permutation.length();

			for (int i = 0; i < pheromone.length; i++) {
				for (int j = 0; j < i; j++) {
					// Don't draw minimum pheromones
					if (pheromone[i][j] == tauMin) continue;

					Point2D.Double p1 = graph[i];
					Point2D.Double p2 = graph[j];

					Point p1t = transform(p1, minBounds, maxBounds, size);
					Point p2t = transform(p2, minBounds, maxBounds, size);

					double ratio = (pheromone[i][j] - tauMin) / (tauMax - tauMin);

					g2.setStroke(new BasicStroke((float)(ratio * 10.0)));
					g2.setColor(Color.getHSBColor((float)(60.0f / 360.0f * (1.0f - ratio)), 1.0f, 1.0f));
					g2.drawLine(p1t.x, p1t.y,p2t.x, p2t.y);
				}
			}
		}

		// Draw permutation and vertices
		g2.setStroke(new BasicStroke(1));
		g2.setColor(COLOR);

		for (int i = 1; i <= permutationArray.length; i++) {
			int srcIndex = permutationArray[i - 1];
			int dstIndex = permutationArray[i % permutationArray.length];

			Point2D.Double p1 = graph[srcIndex];
			Point2D.Double p2 = graph[dstIndex];

			Point p1t = transform(p1, minBounds, maxBounds, size);
			Point p2t = transform(p2, minBounds, maxBounds, size);

			g2.drawLine(p1t.x, p1t.y,p2t.x, p2t.y);
			g2.fillOval(p1t.x - NODE_SIZE/2, p1t.y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
		}
	}

	public void setPermutation(Permutation permutation) {
		this.permutation = permutation;
		this.pheromone = null;
		repaint();
	}

	public void setPermutation(Permutation permutation, double[][] pheromone) {
		this.permutation = permutation;
		this.pheromone = pheromone;
		repaint();
	}
}
