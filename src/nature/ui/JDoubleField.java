package nature.ui;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

public class JDoubleField extends JTextField {
	public JDoubleField(double min, double max) {
		super();
		DocumentFilter filter = new DoubleFilter(min, max);
		((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
	}

}
