import javax.swing.*;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Screen extends JPanel implements ActionListener {

    private static final long serialVersionUID = -5529336786288984928L;

    private String title;
    private int width, height;

    private JTextArea centralTextArea;

    private LabeledBoolButton bracketBoolButton;
    private LabeledBoolButton spacesBetweenBoolButton;
    private LabeledBoolButton removeOutstandingSpacesBoolButton;

    private LabeledField prefixField;
    private LabeledField betweenField;
    private LabeledField suffixField;
    private LabeledField separatorField;

    private static final int WIDTH_DEFAULT = 800;
    private static final int HEIGHT_DEFAULT = 800;
    private static final String TITLE_DEFAULT = "Warframe Trading Text Formatter";

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

        JButton formatButton = initFormatButton(100, 40);
        add(formatButton);

        JScrollPane scrollPane = initScrollPane(500, 200);
        add(scrollPane);

        initLeftCol();

        CharacterLimitMenu characterLimitMenu = new CharacterLimitMenu(500, 300);
        add(characterLimitMenu);

    }

    private void initLeftCol() {
        int offset = 300, numDown = 0; // pixels from top (height)
        bracketBoolButton = new LabeledBoolButton(10, offset + numDown, "Linkable Brackets:", true, "ON", "OFF");
        add(bracketBoolButton);
        numDown += LabeledBoolButton.getHEIGHT();


        prefixField = new LabeledField(10, offset + numDown, "Prefix:", "_item", "(optional)");
        add(prefixField);
        numDown += LabeledField.getHEIGHT();

        suffixField = new LabeledField(10, offset + numDown, "Suffix:", "item_", "(optional)");
        add(suffixField);
        numDown += LabeledField.getHEIGHT();
        
        betweenField = new LabeledField(10, offset + numDown, "Between:", "item_item", "(optional)");
        add(betweenField);
        numDown += LabeledField.getHEIGHT();

        separatorField = new LabeledField(10, offset + numDown, "Separation Marker:", "item*item", "(optional default: , )");
        add(separatorField);
        numDown += LabeledField.getHEIGHT();

        spacesBetweenBoolButton = new LabeledBoolButton(10, offset + numDown, "Put Spaces Between Items: ", false, "ON", "OFF");
        add(spacesBetweenBoolButton);
        numDown += LabeledBoolButton.getHEIGHT();
        removeOutstandingSpacesBoolButton = new LabeledBoolButton(10, offset + numDown, "Remove Outstanding Spaces:", true, "ON", "OFF", "(recommended:ON)");
        add(removeOutstandingSpacesBoolButton);
        numDown += LabeledBoolButton.getHEIGHT();
    }

    private JButton initFormatButton(int w, int h) {
        JButton formatButton = new JButton("Format!");
        formatButton.setActionCommand("format");
        formatButton.addActionListener(this);
        formatButton.setBounds(width / 2 - (int) (w * .5), 10, w, h); // centered horizontally
        return formatButton;
    }

    private JScrollPane initScrollPane(int w, int h) {
        centralTextArea = InitHelper.initTextArea("Paste your items here");
        JScrollPane scrollPane = new JScrollPane(centralTextArea);
        scrollPane.setBounds(width / 2 - (int) (w * .5), 80, w, h);
        
        return scrollPane;
    }

    private void formatText(String inputFile, String outputFile, String separator) {
        try {
            centralTextArea.write(new FileWriter(inputFile, false));
            ArrayList<String> items = Parser.getItems(inputFile, separator);

            Formatter formatter = new Formatter(items, bracketBoolButton.isOn(), spacesBetweenBoolButton.isOn(), 
                                        removeOutstandingSpacesBoolButton.isOn(), InitHelper.getStringContents(prefixField.getTextField()),
                                        InitHelper.getStringContents(suffixField.getTextField()),
                                        InitHelper.getStringContents(betweenField.getTextField()));

            formatter.setModifications();

            String outputText = formatter.getFinalOutput();
            centralTextArea.setText(outputText);

            BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
            out.write(outputText);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("format".equals(e.getActionCommand())) {
            if (centralTextArea.getFont().getStyle() == 0) formatText("input.txt", "output.txt",
                                                            InitHelper.getStringContents(separatorField.getTextField(), ","));
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