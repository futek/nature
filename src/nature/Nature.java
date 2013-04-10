package nature;

import java.awt.EventQueue;

import javax.swing.JFrame;

import nature.ui.NaturePanel;

public class Nature implements Runnable {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Nature());
	}


	@Override
	public void run() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new NaturePanel());
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

//		JFrame visualizer = new JFrame();
//		visualizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		visualizer.add(new OnionVisualizer());
//		visualizer.pack();
//		visualizer.setMinimumSize(visualizer.getSize());
//		visualizer.setLocationRelativeTo(frame);
//		visualizer.setVisible(true);
	}
}
