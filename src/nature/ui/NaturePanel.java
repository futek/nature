package nature.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nature.Nature;

public class NaturePanel extends JPanel {
	private final JTabbedPane tabbedPane = new JTabbedPane();
	public final BitStringPanel bitStringPanel = new BitStringPanel();
	public final PermutationPanel permutationPanel = new PermutationPanel();

	public NaturePanel() {
		super(new GridLayout(1, 1));

		new BitStringPanelController(bitStringPanel);
		new PermutationPanelController(permutationPanel);

		tabbedPane.addTab("Bit String", bitStringPanel);
		tabbedPane.addTab("Permutation", permutationPanel);

		tabbedPane.addChangeListener(new TabbedPaneHandler());

		add(tabbedPane);
	}

	class TabbedPaneHandler implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			Nature.visualizationPanel.removeAll();

			if (tabbedPane.getSelectedComponent() == bitStringPanel) {
				Nature.visualizationPanel.add(bitStringPanel.visualizationPane);
			} else {
				Nature.visualizationPanel.add(permutationPanel.visualizationPane);
			}

			Nature.visualizationPanel.revalidate();
			Nature.visualizationPanel.repaint();
		}
	}
}
