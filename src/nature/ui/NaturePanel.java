package nature.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class NaturePanel extends JPanel {
	public NaturePanel() {
		super(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();

		BitStringPanel bitStringPanel = new BitStringPanel();
		PermutationPanel permutationPanel = new PermutationPanel();

		new BitStringPanelController(bitStringPanel);
		new PermutationPanelController(permutationPanel);

		tabbedPane.addTab("Bit String", bitStringPanel);
		tabbedPane.addTab("Permutation", permutationPanel);

		add(tabbedPane);
	}
}
