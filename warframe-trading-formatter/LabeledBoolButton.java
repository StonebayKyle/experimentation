import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;

public class LabeledBoolButton extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JLabel label;
    private BoolButton boolButton;

    private JLabel defaultLabel;

    private static final int HEIGHT = 32; // should be half of testing height in order to not overlap with whitespace.

    public LabeledBoolButton(int x, int y, String labelText, boolean isOn, String onText, String offText) {
        init(labelText, isOn, onText, offText);
        
        setBounds(x, y, boolButton.getX()+(int)(boolButton.getWidth()*1.3), HEIGHT);
    }

    public LabeledBoolButton(int x, int y, String labelText, boolean isOn, String onText, String offText, String defaultLabelText) {
        init(labelText, isOn, onText, offText);

        defaultLabel = InitHelper.initLabel(boolButton.getX()+(int)(boolButton.getWidth()*1.1), 0, defaultLabelText);
        add(defaultLabel);

        setBounds(x, y, defaultLabel.getX()+(int)(defaultLabel.getWidth()*1.3), HEIGHT);
    }

    private void init(String labelText, boolean isOn, String onText, String offText) {
        setLayout(null);

        label = InitHelper.initLabel(0, 0, labelText);
        add(label);

        boolButton = new BoolButton(label.getWidth(), 0, isOn, onText, offText);
        add(boolButton);
    }

    public boolean isOn() { return boolButton.isOn(); }
    public void setOn(boolean isOn) { boolButton.setOn(isOn); }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    public static int getHEIGHT() { return HEIGHT; }

    public static void main(String[] args) {
        LabeledBoolButton labeledBoolButton = new LabeledBoolButton(10, 300, "Linkable Brackets:", true, "ON", "OFF");
        JFrame frame = new JFrame("LabeledBoolButton Test");
        frame.add(labeledBoolButton);
        frame.setSize(labeledBoolButton.getWidth(),labeledBoolButton.getHeight()*2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}