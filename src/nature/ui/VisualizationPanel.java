package nature.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JPanel;

public class VisualizationPanel extends JPanel {
//	public VisualizationPane visualizationPane;

	public VisualizationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

//		visualizationPane = new VisualizationPane();

	}

//	public class VisualizationPane extends JPanel {
//		public GraphVisualizer drawingPane;
//
//		public VisualizationPane() {
//			// Components
//			drawingPane = new GraphVisualizer();
//			drawingPane.setBackground(new Color(255, 255, 255));
//			drawingPane.setMinimumSize(new Dimension(400, 200));
//
//			// Layout
//			GroupLayout layout = new GroupLayout(this);
//			layout.setAutoCreateGaps(true);
//			layout.setAutoCreateContainerGaps(true);
//			setLayout(layout);
//
//			layout.setHorizontalGroup(layout.createSequentialGroup()
//					.addComponent(drawingPane)
//			);
//
//			layout.setVerticalGroup(layout.createSequentialGroup()
//					.addComponent(drawingPane)
//			);
//		}
//	}
}
