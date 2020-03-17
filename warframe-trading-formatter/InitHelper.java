
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import java.awt.event.*;

public class InitHelper {

    private InitHelper() {}

    public static JLabel initLabel(int x, int y, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, labelText.length()*7, 25);
        
        return label;
    }

    public static JTextField initTextField(int x, int y, String fieldText) {
        JTextField textField = new JTextField(fieldText);
        textField.setBounds(x, y, 80, 25);
        
        textField = (JTextField)addDefaultTextClear(textField);

        return textField;
    }

    // should be used with JScrollPane
    public static JTextArea initTextArea(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(true);

        textArea = (JTextArea)addDefaultTextClear(textArea);

        return textArea;
    }

    public static JTextComponent addDefaultTextClear(JTextComponent textComponent) {
        textComponent.setFont(textComponent.getFont().deriveFont(2));
        
        textComponent.addFocusListener(new FocusListener() {
            @Override
			public void focusGained(FocusEvent e) { 
                textComponent.setText("");
                textComponent.setFont(textComponent.getFont().deriveFont(0));
                textComponent.removeFocusListener(this);
            }
			@Override
			public void focusLost(FocusEvent e) {}
        });

        return textComponent;
    }

    public static String getStringContents(JTextComponent textComponent, String defaultString) {
        if (textComponent.getFont().getStyle() == 2 || textComponent.getText().equals("")) { return defaultString; }
        return textComponent.getText();
    }

    public static String getStringContents(JTextComponent textComponent) {
        return getStringContents(textComponent, "");
    }

}