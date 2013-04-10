package nature.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JPanel;

import nature.Permutation;

public class GraphVisualizer extends JPanel {
	private static final int NODE_SIZE = 16;
	private static final Color NODE_COLOR = new Color(255, 0, 0);
	private static final Color LINE_COLOR = new Color(0, 0, 0);
	private static int padding = NODE_SIZE * 2;

	private Permutation permutation;

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
		List<Point2D.Double> graph = permutation.getGraph();
		Point2D.Double minBounds = permutation.getMinBounds();
		Point2D.Double maxBounds = permutation.getMaxBounds();

		g2.setColor(LINE_COLOR);

		for (int i = 1; i <= permutationArray.length; i++) {
			int srcIndex = permutationArray[i - 1];
			int dstIndex = permutationArray[i % permutationArray.length];

			Point2D.Double p1 = graph.get(srcIndex);
			Point2D.Double p2 = graph.get(dstIndex);

//			int x1 = (int) ((p1.x - minBounds.x) / (maxBounds.x - minBounds.x) * size.width);
//			int y1 = (int) ((p1.y - minBounds.y) / (maxBounds.y - minBounds.y) * size.height);
//			int x2 = (int) ((p2.x - minBounds.x) / (maxBounds.x - minBounds.x) * size.width);
//			int y2 = (int) ((p2.y - minBounds.y) / (maxBounds.y - minBounds.y) * size.height);

			Point p1t = transform(p1, minBounds, maxBounds, size);
			Point p2t = transform(p2, minBounds, maxBounds, size);

//			System.out.println("line!... x1: " + x1 + ", y1: " + y1 + ", x2: " + x2 + ", y2: " + y2);

			g2.drawLine(p1t.x, p1t.y,p2t.x, p2t.y);
		}

		g2.setColor(NODE_COLOR);

		for (Point2D.Double p : graph) {
//			int x = (int) ((p.x - minBounds.x) / (maxBounds.x - minBounds.x) * size.width);
//			int y = (int) ((p.y - minBounds.y) / (maxBounds.y - minBounds.y) * size.height);

			Point pt = transform(p, minBounds, maxBounds, size);

			g2.fillOval(pt.x - NODE_SIZE/2, pt.y - NODE_SIZE/2, NODE_SIZE, NODE_SIZE);
		}
	}

	public void setPermutation(Permutation permutation) {
		this.permutation = permutation;
		repaint();
	}
}
