import javax.swing.JFrame;

public class Main {
    
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