import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.*;

public class CharacterLimitMenu extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private int limit; // 0 represents no limit

    private JLabel limitLabel;

    private ValuedToggleButton[] valuedButtons;

    private JTextField customTextField;

    public CharacterLimitMenu(int x, int y) {
        setBounds(x, y, 400, 400);
        setLayout(null);

        limit = 0;

        limitLabel = InitHelper.initLabel(30, 0, getLimitLabelString());
        add(limitLabel);

        makeButtons();

    }

    private void makeButtons() {
        ValuedToggleButton noLimitButton = initValuedButton(0, 30, "None", limit, true, "noLimit");
        add(noLimitButton);

        ValuedToggleButton pmLimitButton = initValuedButton(125, 30, "Private", 100, false, "pmLimit");
        add(pmLimitButton);

        ValuedToggleButton relayLimitButton = initValuedButton(0, 70, "Relay", 150, false, "relayLimit");
        add(relayLimitButton);
        ValuedToggleButton tradeLimitButton = initValuedButton(125, 70, "Trade", 110, false, "tradeLimit");
        add(tradeLimitButton);

        ValuedToggleButton customLimitButton = makeCustomLimitButton(0, 110, "Custom", limit, false, "customLimit",
                "custom max");
        add(customLimitButton);

        valuedButtons = new ValuedToggleButton[5];
        valuedButtons[0] = noLimitButton;
        valuedButtons[1] = relayLimitButton;
        valuedButtons[2] = tradeLimitButton;
        valuedButtons[3] = pmLimitButton;
        valuedButtons[4] = customLimitButton;
    }

    private ValuedToggleButton initValuedButton(int x, int y, String buttonText, int value, boolean startsOn,
            String actionCommand) {
        ValuedToggleButton valuedToggleButton = new ValuedToggleButton(buttonText, startsOn, value);
        valuedToggleButton.setBounds(x, y, 100, 30);
        valuedToggleButton.addActionListener(this);
        valuedToggleButton.setActionCommand(actionCommand);

        return valuedToggleButton;
    }

    private ValuedToggleButton makeCustomLimitButton(int x, int y, String buttonText, int value, boolean startsOn,
            String actionCommand, String fieldText) {
        ValuedToggleButton customLimitButton = initValuedButton(x, y, buttonText, value, startsOn, actionCommand);

        customTextField = initCustomTextField(125, y + (customLimitButton.getHeight() / 8), 100, 25, fieldText);
        add(customTextField);

        return customLimitButton;
    }

    private JTextField initCustomTextField(int x, int y, int w, int h, String defaultText) {
        JTextField textField = new JTextField(defaultText);
        textField.setBounds(x, y, w, h);

        textField.setFont(textField.getFont().deriveFont(2));

        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getFont().getStyle() == 2) {
                    textField.setFont(textField.getFont().deriveFont(0));
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().equals("")) {
                    textField.setFont(textField.getFont().deriveFont(2));
                    textField.setText(defaultText);
                    return;
                }

                setCustom();
                if (valuedButtons[4].isSelected()) {
                    limit = valuedButtons[4].getValue();
                    updateLimitLabel();
                }
            }
            
        });

        return textField;
    } 

    private void updateLimitLabel() {
        limitLabel.setText(getLimitLabelString());
    }

    private String getLimitLabelString() {
        String front = "Max Characters Per Copy: ";
        if (getLimit() == 0) return front + "N/A";
        return front + limit;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        toggleButtonActions(e);
    }

    private void toggleButtonActions(ActionEvent e) {
        int activeIndex;
        if ("noLimit".equals(e.getActionCommand())) {
            activeIndex = 0;
        } else if ("relayLimit".equals(e.getActionCommand())) {
            activeIndex = 1;
        } else if ("tradeLimit".equals(e.getActionCommand())) {
            activeIndex = 2;
        } else if ("pmLimit".equals(e.getActionCommand())) {
            activeIndex = 3;
        } else if ("customLimit".equals(e.getActionCommand())) {
            activeIndex = 4;
            setCustom();

        } else {
            System.out.println("Unknown Action!");
            activeIndex = -1;
        }

        setActive(activeIndex);
        updateLimitLabel();
    }

    private void setActive(int activeIndex) {
        for (int i = 0; i < valuedButtons.length; i++) {
            if (i == activeIndex) {
                valuedButtons[i].setSelected(true);
                limit = valuedButtons[i].getValue();
            } else {
                valuedButtons[i].setSelected(false);
            }
        }
    }

    // sets custom buttom value to whatever is in the field.
    // if value is malformed, due to letters or is blank, sets value to 0 (no limit).
    private void setCustom() {
        try {
            valuedButtons[4].setValue(Integer.parseInt(customTextField.getText()));
        } catch (Exception e) {
            valuedButtons[4].setValue(0);
        }
    }


    public int getLimit() {
        if (limit <= 0) return 0; // 0 represents no limit
        return limit;
    }
    
    public static void main(String[] args) {
        CharacterLimitMenu characterLimitMenu = new CharacterLimitMenu(500, 300);
        JFrame frame = new JFrame("CharacterLimitMenu Test");
        frame.add(characterLimitMenu);
        frame.setSize(characterLimitMenu.getWidth(), characterLimitMenu.getHeight());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
}