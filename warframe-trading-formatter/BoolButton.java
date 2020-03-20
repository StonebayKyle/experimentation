import javax.swing.JToggleButton;
import java.awt.event.*;

public class BoolButton extends JToggleButton implements ActionListener {

    private static final long serialVersionUID = 1L;
    
    private boolean isOn;
    private String onText;
    private String offText;

    public BoolButton(int x, int y, boolean isOn, String onText, String offText) {
        this.isOn = isOn;
        this.onText = onText;
        this.offText = offText;

        update();
        setSelected(isOn);
        addActionListener(this);

        int width = onText.length() >= offText.length() ? onText.length()*20 : offText.length()*20;
        setBounds(x, y, width, 25);
    }

    private void toggle() {
        isOn = !isOn;
        update();
    }

    private void update() {
        String text = isOn ? onText : offText;
        setText(text);
        setSelected(isOn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        toggle();
    }

    public boolean isOn() { return isOn; }

    public void setOn(boolean isOn) { 
        this.isOn = isOn; 
        update();
    }
    
}