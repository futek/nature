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
	}
}
