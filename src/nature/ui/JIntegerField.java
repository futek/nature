package nature.ui;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

public class JIntegerField extends JTextField {
	public JIntegerField(int min, int max) {
		super();
		DocumentFilter filter = new IntegerFilter(min, max);
		((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
	}

}
