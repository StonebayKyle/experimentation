public class Main {
    
    public static void main(String[] args) {
        Screen screen = new Screen();
        
        Formatter formatter = new Formatter(Parser.getItems("input.txt", ","));
        formatter.setModifications();
        System.out.println(formatter.getFinalOutput());
    }
}