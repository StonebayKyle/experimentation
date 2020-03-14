import java.util.ArrayList;

public class Formatter {

    private ArrayList<String> items;

    private String finalOutput;

    public Formatter(ArrayList<String> items) {
        this.items = items;
    }

    public void placeBrackets() {

    }

    public void placeStarts() {

    }

    public void placeEnds() {

    }

    public String getFinalOutput() {
        finalOutput = updateFinalOutput();
        return finalOutput; 
    }

    private String updateFinalOutput() {
        String output = "";
        for (int i = 0; i < items.size(); i++) {
            output += items.get(i) + " ";
        }

        return output;
    }
}