import javax.swing.JToggleButton;

public class ValuedToggleButton extends JToggleButton {

    private static final long serialVersionUID = 1L;

    private int value;

    public ValuedToggleButton(String buttonText, boolean startsOn, int value) {
        super(buttonText, startsOn);
        this.value = value;
    }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
}