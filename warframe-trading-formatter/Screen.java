import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JPanel {

    private static final long serialVersionUID = -5529336786288984928L;

    private String title;
    private int width, height;

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

        JFrame frame = new JFrame(title);
        frame.add(this);

        frame.setSize(width, height);

        // frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    

}