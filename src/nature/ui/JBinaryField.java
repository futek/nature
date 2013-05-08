package nature.ui;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

public class JBinaryField extends JTextField {
	public JBinaryField(int length) {
		super();
		DocumentFilter filter = new BinaryFilter(length);
		((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
	}

}
