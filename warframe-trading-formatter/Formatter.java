import java.util.ArrayList;
import java.lang.StringBuilder;

public class Formatter {

    private ArrayList<String> items;

    private boolean needBrackets;
    private boolean needSpacesBetween;
    private boolean shouldRemoveOutstandingSpaces;

    private String prefix;
    private String suffix;
    private String between;


    private String finalOutput;

    public Formatter(ArrayList<String> items) {
        this.items = items;

        needBrackets = false;
        needSpacesBetween = false;
        shouldRemoveOutstandingSpaces = false;

        prefix = "";
        suffix = "";
        between = "";
    }

    public Formatter(ArrayList<String> items, boolean needBrackets, boolean needSpacesBetween, boolean shouldRemoveOutstandingSpaces,
                        String prefix, String suffix, String between) {
        this.items = items;

        this.needBrackets = needBrackets;
        this.needSpacesBetween = needSpacesBetween;
        this.shouldRemoveOutstandingSpaces = shouldRemoveOutstandingSpaces;

        this.prefix = prefix;
        this.suffix = suffix;
        this.between = between;
    }

    public void setModifications() {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, getModifiedString(items.get(i), i)); 
        }
    }

    private String getModifiedString(String item, int i) {
        if (shouldRemoveOutstandingSpaces) {
            item = removeOutstandingSpaces(item);
        }

        StringBuilder itemBuilder = new StringBuilder(item);
        itemBuilder = modifyStart(itemBuilder);
        
        item = itemBuilder.toString();
        item = modifyEnd(item);

        if (i != items.size()-1) {
            item = appendBetween(item);
        }

        return item;
    }

    private StringBuilder modifyStart(StringBuilder itemBuilder) {
        if (needBrackets) itemBuilder.insert(0, "[");
        itemBuilder.insert(0, prefix);

        return itemBuilder;
    }

    private String modifyEnd(String item) {
        if (needBrackets) item += "]";
        item += suffix;

        return item;
    }

    private String appendBetween(String item) {
        if (needSpacesBetween && !between.equals("")) {
            item += " ";
        }
        
        item += between;
        if (needSpacesBetween) item += " ";

        return item;
    }

    // Removes (likely accidental) spaces at the beginning and end of an item
    private String removeOutstandingSpaces(String item) {
        // from front
        while (item.substring(0,1).equals(" ")) {
            item = item.substring(1,item.length());
        }
        // from back
        while (item.substring(item.length()-1, item.length()).equals(" ")) {
            item = item.substring(0, item.length()-2);
        }

        return item;
    }
    
    public String getFinalOutput() {
        finalOutput = updateFinalOutput();
        return finalOutput; 
    }

    private String updateFinalOutput() {
        String output = "";
        for (String item : items) {
            output += item;
        }

        return output;
    }
}