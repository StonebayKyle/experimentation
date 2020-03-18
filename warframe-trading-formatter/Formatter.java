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

    private String listPrefix;
    private String listSuffix;
    
    private int characterLimit;

    private String finalOutput;
    
    public Formatter(ArrayList<String> items) {
        this.items = items;
        
        needBrackets = false;
        needSpacesBetween = false;
        shouldRemoveOutstandingSpaces = false;
        
        prefix = "";
        suffix = "";
        between = "";

        listPrefix = "";
        listSuffix = "";

        characterLimit = 0;
    }
    
    public Formatter(ArrayList<String> items, boolean needBrackets, boolean needSpacesBetween, boolean shouldRemoveOutstandingSpaces,
    String prefix, String suffix, String listPrefix, String listSuffix, String between, int characterLimit) {
        this.items = items;
        
        this.needBrackets = needBrackets;
        this.needSpacesBetween = needSpacesBetween;
        this.shouldRemoveOutstandingSpaces = shouldRemoveOutstandingSpaces;
        
        this.prefix = prefix;
        this.suffix = suffix;
        this.between = between;

        this.listPrefix = listPrefix;
        this.listSuffix = listSuffix;

        // must subtract limit by everything in concatItems() that wasn't already placed into the items list as one item.
        // this is done once inside handleCharacterLimit() so items will properly go into overflow due to what should be user error
        this.characterLimit = characterLimit;
    }
    
    public void setModifications() {
        for (int i = 0; i < items.size(); i++) {
            String modifiedString = getModifiedString(items.get(i), i);

            if (modifiedString == null) { 
                items.remove(i); // remove empty Strings
            } else {
                items.set(i, modifiedString);
            }
        }
    }
    
    private void updateFinalOutput() {
        if (characterLimit > 0) {
            finalOutput = handleCharacterLimit();
        } else {
            finalOutput = concatItems(items);
        }
    }

    // Use when adding a (copy) part to the output.
    // Does listPrefix, concats, appendsBetween, and listSuffix 
    private String concatItems(ArrayList<String> items) {
        String output = listPrefix;
        for (int i = 0; i < items.size(); i++) { // every length increasing action inside of loop must also be accounted for inside of another loop for max characters
            output += items.get(i);
            if (i < items.size()-1) output += appendBetween();
        }
        output += listSuffix;
        return output;
    }

    // gets between String
    private String appendBetween() {
        String output = "";
        if (needSpacesBetween && between.length() > 0) { // spaces will go before and after defined between
            output += " ";
        }

        output += between;
        if (needSpacesBetween) output += " ";

        return output;
    }

    private String handleCharacterLimit() {
        ArrayList<String> overflowItems = new ArrayList<>(); // items that could not fit within the specified characterLimit
        ArrayList<String> itemsWithinCopies = new ArrayList<>(); // items to be concatinated at the end of each copy in order to allow for a proper "between" String field.
        String output = "";

        int copyNumber = 1;
        int charactersThisCopy = 0;

        // must subtract limit by everything in concatItems() that wasn't already placed into the items list as one item.
        // for things inside of the concatItems() loop, that must be handled inside the loop.
        System.out.println("Initial limit: " + characterLimit);
        characterLimit -= listPrefix.length() + listSuffix.length();
        System.out.println("MODIFIED limit: " + characterLimit);

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).length() > characterLimit) { // overflow
                overflowItems.add(items.get(i));
            } else {
                
                int nextLength = charactersThisCopy + items.get(i).length();
                System.out.println("nextLength: " + nextLength);
                if (nextLength > characterLimit // if next word length pushes characters over limit
                && (output.equals("") || itemsWithinCopies.size() > 0)) { // AND if (this is first run OR there are items ready to be placed)
                    // place and go to next copy
                    output += limitBreakString("  Copy " + copyNumber + "  ", copyNumber == 1);
                    output += concatItems(itemsWithinCopies); // finally add to output before moving on
                    itemsWithinCopies.clear();
                    copyNumber++;
                    charactersThisCopy = 0;
                    System.out.println("BREAKER " + copyNumber);
                }
                // add to this copy
                charactersThisCopy += items.get(i).length();
                if (i < items.size()-1) { // appendBetween() length handling
                    charactersThisCopy += appendBetween().length();
                }
                itemsWithinCopies.add(items.get(i));
                System.out.println("IN LIST: " + concatItems(itemsWithinCopies));
            }
        }

        if (itemsWithinCopies.size() > 0) { // place final copy part
            output += limitBreakString("  Copy " + copyNumber + "  ", copyNumber == 1);
            output += concatItems(itemsWithinCopies);
        }

        if (overflowItems.size() > 0) { // place overflow
            output += limitBreakString("  Overflow  ", copyNumber == 1);
            output += concatItems(overflowItems);
        }

        return output;
    }
    
    private static String limitBreakString(String breakText, boolean firstCopy) {
        String output = "";
        
        String breaker = "-";
        int amount = 20;

        if (!firstCopy) output += "\n"; // makes sure there isn't an unnecessary space at the beginning

        output += insertBreakCharacters(breaker, amount);
        output += breakText;
        output += insertBreakCharacters(breaker, amount);
        output += "\n";

        return output;
    }


    private static String insertBreakCharacters(String breaker, int amount) {
        String output = "";
        for (int i = 0; i < amount; i++) {
            output += breaker;
        }
        return output;
    }
    
    private String getModifiedString(String item, int i) {
        if (shouldRemoveOutstandingSpaces) {
            item = removeOutstandingSpaces(item);
        }

        if (item.length() == 0) { return null; } // don't format empty items
        
        StringBuilder itemBuilder = new StringBuilder(item);
        itemBuilder = modifyStart(itemBuilder);
        
        item = itemBuilder.toString();
        item = modifyEnd(item);

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


    // Removes (likely accidental) spaces at the beginning and end of an item
    private String removeOutstandingSpaces(String item) {
        // check is required in while loop because the size is changing dynamically and may go under length of 1.
        // from front
        while (item.length() > 0 && item.substring(0,1).equals(" ")) { 
            item = item.substring(1,item.length());
        }
        // from back
        while (item.length() > 0 && item.substring(item.length()-1, item.length()).equals(" ")) {
            item = item.substring(0, item.length()-2);
        }
        return item;
    }
    
    public String getFinalOutput() { 
        updateFinalOutput();
        return finalOutput; 
    }

}