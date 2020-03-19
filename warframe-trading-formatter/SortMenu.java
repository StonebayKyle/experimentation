import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;
public class SortMenu extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private int sortID; // 0 represents no sorting

    private JLabel sortLabel;

    private ValuedToggleButton[] valuedButtons;

    
    public SortMenu(int x, int y) {
        setBounds(x, y, 345, 80);
            setLayout(null);
    
            sortID = 0;
    
            sortLabel = InitHelper.initLabel(0, 0, "Sorting:");
            add(sortLabel);
    
            makeButtons();
        
    }

    private void makeButtons() {
        int x = 0, spacer = 6;
        ValuedToggleButton noSortingButton = initValuedButton(x, 30, 70, "None", 0, true, "noSort");
        add(noSortingButton);

        x += noSortingButton.getWidth() + spacer+8;

        ValuedToggleButton azSortingButton = initValuedButton(x, 30, "A->Z", 1, false, "azSort");
        add(azSortingButton);
        x += azSortingButton.getWidth() + spacer;

        ValuedToggleButton zaSortingButton = initValuedButton(x, 30, "Z->A", 2, false, "zaSort");
        add(zaSortingButton);
        x += zaSortingButton.getWidth() + spacer+8;

        ValuedToggleButton randomSortingButton = initValuedButton(x, 30, 85, "Random", 3, false, "randomSort");
        add(randomSortingButton);
        x += randomSortingButton.getWidth() + spacer;

        valuedButtons = new ValuedToggleButton[4];
        valuedButtons[0] = noSortingButton;
        valuedButtons[1] = azSortingButton;
        valuedButtons[2] = zaSortingButton;
        valuedButtons[3] = randomSortingButton;
    }

    private ValuedToggleButton initValuedButton(int x, int y, String buttonText, int value, boolean startsOn,
            String actionCommand) {
        ValuedToggleButton valuedToggleButton = new ValuedToggleButton(buttonText, startsOn, value);
        valuedToggleButton.setBounds(x, y, 60, 30);
        valuedToggleButton.addActionListener(this);
        valuedToggleButton.setActionCommand(actionCommand);

        return valuedToggleButton;
    }

    private ValuedToggleButton initValuedButton(int x, int y, int w, String buttonText, int value, boolean startsOn,
            String actionCommand) {
        ValuedToggleButton valuedToggleButton = new ValuedToggleButton(buttonText, startsOn, value);
        valuedToggleButton.setBounds(x, y, w, 30);
        valuedToggleButton.addActionListener(this);
        valuedToggleButton.setActionCommand(actionCommand);

        return valuedToggleButton;
    }

    private void setActive(int activeIndex) {
        for (int i = 0; i < valuedButtons.length; i++) {
            if (i == activeIndex) {
                valuedButtons[i].setSelected(true);
                sortID = valuedButtons[i].getValue();
            } else {
                valuedButtons[i].setSelected(false);
            }
        }
    }

    public int getSortID() {
        return sortID;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        toggleButtonActions(e);
    }

    private void toggleButtonActions(ActionEvent e) {
        int activeIndex;
        if ("noSort".equals(e.getActionCommand())) {
            activeIndex = 0;
        } else if ("azSort".equals(e.getActionCommand())) {
            activeIndex = 1;
        } else if ("zaSort".equals(e.getActionCommand())) {
            activeIndex = 2;
        } else if ("randomSort".equals(e.getActionCommand())) {
            activeIndex = 3;

        } else {
            System.out.println("Unknown Action!");
            activeIndex = -1;
        }

        setActive(activeIndex);
    }

    public static void main(String[] args) {
        SortMenu sortMenu = new SortMenu(300, 500);
        JFrame frame = new JFrame("SortMenu Test");
        frame.add(sortMenu);
        frame.setSize(sortMenu.getWidth(), sortMenu.getHeight()+35);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}