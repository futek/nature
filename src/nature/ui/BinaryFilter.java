package nature.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class BinaryFilter extends DocumentFilter {
	private static final Matcher binaryMatcher = Pattern.compile("[01]+").matcher("");

	private int length;

	public BinaryFilter(int length) {
		this.length = length;
	}

	@Override
	public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
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
			binaryMatcher.reset(text);
			return text.length() <= length && binaryMatcher.matches();
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
