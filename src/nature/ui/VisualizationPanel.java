package nature.ui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import nature.Nature;

public class VisualizationPanel extends JPanel {
	public VisualizationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		add(Nature.naturePanel.bitStringPanel.visualizationPane);
//		add(Nature.naturePanel.permutationPanel.visualizationPane);
	}
}
