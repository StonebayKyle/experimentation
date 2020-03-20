import javax.swing.*;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Screen extends JPanel implements ActionListener {

    private static final long serialVersionUID = -5529336786288984928L;

    private String title;
    private int width, height;

    private JTextArea centralTextArea;

    private ArrayList<String> inputHistory;
    private JButton undoButton;

    private LabeledBoolButton bracketBoolButton;
    private LabeledBoolButton spacesBetweenBoolButton;
    private LabeledBoolButton removeOutstandingSpacesBoolButton;

    private LabeledField prefixField;
    private LabeledField suffixField;
    private LabeledField betweenField;
    private LabeledField separatorField;

    private LabeledField listPrefixField;
    private LabeledField listSuffixField;

    private CharacterLimitMenu characterLimitMenu;
    private SortMenu sortMenu;

    private static final int WIDTH_DEFAULT = 800;
    private static final int HEIGHT_DEFAULT = 560;
    private static final String TITLE_DEFAULT = "Warframe Trading Text Formatter - Created by StonebayKyle";

    public Screen(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        init();
    }

    public Screen(String title) {
        this.title = title;
        this.width = WIDTH_DEFAULT;
        this.height = HEIGHT_DEFAULT;
        init();
    }

    public Screen(int width, int height) {
        this.title = TITLE_DEFAULT;
        init();
    }

    public Screen() {
        this.title = TITLE_DEFAULT;
        this.width = WIDTH_DEFAULT;
        this.height = HEIGHT_DEFAULT;
        init();
    }

    private void init() {
        setLayout(null);
        setSize(width, height);

        inputHistory = new ArrayList<>();

        JButton formatButton = InitHelper.initButton(width / 2 - 50, 20, 100, 40, "Format!", "format", this);
        add(formatButton);

        undoButton = InitHelper.initButton(500, 30, 75, 30, "Undo", "undo", this);
        undoButton.setEnabled(false);
        add(undoButton);

        JScrollPane scrollPane = initScrollPane(500, 200);
        add(scrollPane);

        initLeftCol();

        initMiddleCol();

        characterLimitMenu = new CharacterLimitMenu(475, 300);
        add(characterLimitMenu);

        sortMenu = new SortMenu(400, 455);
        add(sortMenu);

    }

    private void initLeftCol() {
        int offset = 300, numDown = 0; // pixels from top (height)
        bracketBoolButton = new LabeledBoolButton(10, offset + numDown, "Linkable Brackets:", true, "ON", "OFF");
        add(bracketBoolButton);
        numDown += LabeledBoolButton.getHEIGHT();

        prefixField = new LabeledField(10, offset + numDown, "Prefix:", "_item");
        add(prefixField);
        numDown += LabeledField.getHEIGHT();

        suffixField = new LabeledField(10, offset + numDown, "Suffix:", "item_");
        add(suffixField);
        numDown += LabeledField.getHEIGHT();

        betweenField = new LabeledField(10, offset + numDown, "Between:", "item_item");
        add(betweenField);
        numDown += LabeledField.getHEIGHT();

        separatorField = new LabeledField(10, offset + numDown, "Separation Marker:", "item*item", "(default: , )");
        add(separatorField);
        numDown += LabeledField.getHEIGHT();

        spacesBetweenBoolButton = new LabeledBoolButton(10, offset + numDown, "Put Spaces Between Items: ", false, "ON",
                "OFF");
        add(spacesBetweenBoolButton);
        numDown += LabeledBoolButton.getHEIGHT();
        removeOutstandingSpacesBoolButton = new LabeledBoolButton(10, offset + numDown, "Remove Outstanding Spaces:",
                true, "ON", "OFF", "(recommended)");
        add(removeOutstandingSpacesBoolButton);
        numDown += LabeledBoolButton.getHEIGHT();
    }

    private void initMiddleCol() {
        listPrefixField = new LabeledField(prefixField.getX() + prefixField.getWidth(), prefixField.getY(),
                "List Prefix:", "_list");
        add(listPrefixField);

        listSuffixField = new LabeledField((suffixField.getX()) + suffixField.getWidth(), suffixField.getY(),
                "List Suffix:", "list_");
        add(listSuffixField);
    }

    private JScrollPane initScrollPane(int w, int h) {
        centralTextArea = InitHelper.initTextArea("Paste your items here");
        JScrollPane scrollPane = new JScrollPane(centralTextArea);
        scrollPane.setBounds(width / 2 - (int) (w * .5), 80, w, h);

        return scrollPane;
    }

    private void formatText(String inputFile, String outputFile, String separator) throws Exception {
        buildInputHistory();
        centralTextArea.write(new FileWriter(inputFile, false));

        ArrayList<String> items = Parser.getItems(inputFile, separator);

        Formatter formatter = new Formatter(items, bracketBoolButton.isOn(), spacesBetweenBoolButton.isOn(),
                removeOutstandingSpacesBoolButton.isOn(), InitHelper.getStringContents(prefixField.getTextField()),
                InitHelper.getStringContents(suffixField.getTextField()),
                InitHelper.getStringContents(listPrefixField.getTextField()),
                InitHelper.getStringContents(listSuffixField.getTextField()),
                InitHelper.getStringContents(betweenField.getTextField()), sortMenu.getSortID(), characterLimitMenu.getLimit());

        formatter.setModifications();

        String outputText = formatter.getFinalOutput();
        centralTextArea.setText(outputText);

        BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
        out.write(outputText);
        out.close();
    }

    // save and upkeep history for undoing
    private void buildInputHistory() {
        inputHistory.add(centralTextArea.getText());
        undoButton.setEnabled(true);

        int maxHistorySize = 20;
        if (inputHistory.size() > maxHistorySize) {
            inputHistory.remove(0);
        }
    }

    private void undoFormat() {
        if (inputHistory.size() > 0) {
            int latestIndex = inputHistory.size() - 1;
            centralTextArea.setText(inputHistory.get(latestIndex));
            inputHistory.remove(latestIndex);
        }

        if (inputHistory.size() == 0) {
            undoButton.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("format".equals(e.getActionCommand())) {
            if (!InitHelper.textIsDefault(centralTextArea)) {
                try {
                    formatText("input.txt", "output.txt",
                            InitHelper.getStringContents(separatorField.getTextField(), ","));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
                                          
        } else if ("undo".equals(e.getActionCommand())) {
            undoFormat();
        } else {
            System.out.println("Unknown Action!");
        }
    }

    public String getTitle() { return title; }

    public static void main(String[] args) {
        Screen screen = new Screen();
        JFrame frame = new JFrame(screen.getTitle());
        frame.add(screen);
        frame.setSize(screen.getWidth(), screen.getHeight());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}