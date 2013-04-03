package nature.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class IntegerFilter extends DocumentFilter {
	@Override
	public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
		System.out.println("Hello!");
		
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, text);
		
		if (test(sb.toString())) {
			super.insertString(fb, offset, text, attr);
		} else {
			// TODO: Warn user of invalid input
		}
	}
	
	private boolean test(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);
		
		if (test(sb.toString())) {
			super.replace(fb, offset, length, text, attrs);
		} else {
			// TODO: Warn user of invalid input
		}
	}
	
	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);
		
		if (test(sb.toString())) {
			super.remove(fb, offset, length);
		} else {
			// TODO: Warn user of invalid input
		}
	}
}
