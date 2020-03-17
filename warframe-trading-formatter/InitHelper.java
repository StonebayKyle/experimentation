
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

    public static JTextField initTextField(int x, int y, int w, int h, String defaultText) {
        JTextField textField = new JTextField(defaultText);
        textField.setBounds(x, y, w, h);
        
        textField = (JTextField)addDefaultTextClear(textField, defaultText);

        return textField;
    }

    public static JTextField initTextField(int x, int y, String defaultText) {
        return initTextField(x, y, 80, 25, defaultText);
    }

    // should be used with JScrollPane or similar container
    public static JTextArea initTextArea(String defaultText) {
        JTextArea textArea = new JTextArea(defaultText);
        textArea.setLineWrap(true);

        textArea = (JTextArea)addDefaultTextClear(textArea, defaultText);

        return textArea;
    }

    public static JTextComponent addDefaultTextClear(JTextComponent textComponent, String defaultText) {
        textComponent.setFont(textComponent.getFont().deriveFont(2));
        
        textComponent.addFocusListener(new FocusListener() {
            @Override
			public void focusGained(FocusEvent e) {
                if (textComponent.getFont().getStyle() == 2) {
                    textComponent.setFont(textComponent.getFont().deriveFont(0));
                    textComponent.setText("");
                }
            }
			@Override
			public void focusLost(FocusEvent e) {
                if (textComponent.getText().equals("")) {
                    textComponent.setFont(textComponent.getFont().deriveFont(2));
                    textComponent.setText(defaultText);
                }
            }
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