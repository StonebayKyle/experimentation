import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.*;

public class LabeledField extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JLabel label;
    private JTextField textField;

    private JLabel defaultLabel;

    private static final int HEIGHT = 32; // should be half of testing height in order to not overlap with whitespace.

    public LabeledField(int x, int y, String labelText, String fieldText) {
        init(labelText, fieldText);
        setBounds(x, y, textField.getX()+(int)(textField.getWidth()*1.1), HEIGHT);
    }

    public LabeledField(int x, int y, String labelText, String fieldText, String defaultLabelText) {
        init(labelText, fieldText);

        defaultLabel = InitHelper.initLabel(textField.getX()+(int)(textField.getWidth()*1.1), 0, defaultLabelText);
        add(defaultLabel);

        setBounds(x, y, defaultLabel.getX() + (int)(defaultLabel.getWidth()*1.3), HEIGHT);
    }

    private void init(String labelText, String fieldText) {
        setLayout(null);

        label = InitHelper.initLabel(0, 0, labelText);
        add(label);
        textField = InitHelper.initTextField(label.getWidth(), 0, fieldText);
        add(textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public JTextField getTextField() { return textField; }

    public static int getHEIGHT() { return HEIGHT; }

    public static void main(String[] args) {
        LabeledField labeledField = new LabeledField(10, 340, "Prefix:", "_item", "(optional)");
        JFrame frame = new JFrame("LabeledField Test");
        frame.add(labeledField);
        frame.setSize(labeledField.getWidth(),labeledField.getHeight()*2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

    }
}