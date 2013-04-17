package nature;

import java.awt.EventQueue;

import javax.swing.JFrame;

import nature.ui.NaturePanel;
import nature.ui.VisualizationPanel;

public class Nature implements Runnable {
	public static final NaturePanel naturePanel = new NaturePanel();
	public static final VisualizationPanel visualizationPanel = new VisualizationPanel();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Nature());
	}

	@Override
	public void run() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(naturePanel);
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		JFrame visualizer = new JFrame();
		visualizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		visualizer.add(visualizationPanel);
		visualizer.pack();
		visualizer.setMinimumSize(visualizer.getSize());
		visualizer.setLocation(850, 150);
		visualizer.setVisible(true);
	}
}
