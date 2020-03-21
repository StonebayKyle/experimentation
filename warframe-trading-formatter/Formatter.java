import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.StringBuilder;

public class Formatter {

    private ArrayList<String> items;

    private boolean needBrackets;
    private boolean needSpacesBetween;
    private boolean shouldRemoveOutstandingSpaces;

    private String prefix;
    private String suffix;

    private String between;
    private String tagger;

    private String listPrefix;
    private String listSuffix;
    
    private int sortID;
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
        tagger = "";

        listPrefix = "";
        listSuffix = "";

        sortID = 0;
        characterLimit = 0;
    }
    
    public Formatter(ArrayList<String> items, boolean needBrackets, boolean needSpacesBetween, boolean shouldRemoveOutstandingSpaces,
    String prefix, String suffix, String listPrefix, String listSuffix, String between, String tagger, int sortID, int characterLimit) {
        this.items = items;
        
        this.needBrackets = needBrackets;
        this.needSpacesBetween = needSpacesBetween;
        this.shouldRemoveOutstandingSpaces = shouldRemoveOutstandingSpaces;
        
        this.prefix = prefix;
        this.suffix = suffix;

        this.between = between;
        this.tagger = tagger;

        this.listPrefix = listPrefix;
        this.listSuffix = listSuffix;

        this.sortID = sortID;
        // must subtract limit by everything in concatItems() that wasn't already placed into the items list as one item.
        // this is done once inside handleCharacterLimit() so items will properly go into overflow due to what should be user error
        this.characterLimit = characterLimit;
    }
    
    public void setModifications() {
        items = getPreparedItemList();
        
        for (int i = 0; i < items.size(); i++) {
            String modifiedString = getModifiedString(items.get(i), i);
            
            if (modifiedString == null) { 
                items.remove(i); // remove empty Strings
                i--; // don't skip index after because of the .remove() frameshift.
            } else {
                items.set(i, modifiedString);
            }
        }
        
    }
    
    private ArrayList<String> getPreparedItemList() {
        ArrayList<String> list = new ArrayList<>();
        // trimming must be done before sorting because spaces have less priority than letters, and there is often inconsistant whitespace in input.
        for (String item : items) {
            if (shouldRemoveOutstandingSpaces) {
                list.add(item.trim());
            } else {
                list.add(item);
            }
        }

        if (sortID > 0) { 
            list = getSortedArrayList(list);
        }

        return list;
    }
    
    private String getModifiedString(String item, int i) {
        if (item.length() == 0) { return null; } // don't format empty items

        StringBuilder itemBuilder = new StringBuilder(item);
        itemBuilder = modifyStart(itemBuilder);
        
        item = itemBuilder.toString();
        item = modifyEnd(item);
        
        return item;
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
        // for things inside of the concatItems() loop, that must be handled inside the loop (such as appendBetween).
        characterLimit -= listPrefix.length() + listSuffix.length();

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).length() > characterLimit) { // overflow
                overflowItems.add(items.get(i));
            } else {
                
                int nextLength = charactersThisCopy + items.get(i).length();
                if (nextLength > characterLimit // if next word length pushes characters over limit
                && (output.equals("") || itemsWithinCopies.size() > 0)) { // AND if (this is first run OR there are items ready to be placed)
                    // place and go to next copy
                    output += limitBreakString("  Copy " + copyNumber + "  ", copyNumber == 1);
                    output += concatItems(itemsWithinCopies); // finally add to output before moving on
                    itemsWithinCopies.clear();
                    copyNumber++;
                    charactersThisCopy = 0;
                }
                // add to this copy
                charactersThisCopy += items.get(i).length();
                if (i < items.size()-1) { // appendBetween() length handling
                    charactersThisCopy += appendBetween().length();
                }
                itemsWithinCopies.add(items.get(i));
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
    
    private ArrayList<String> getSortedArrayList(ArrayList<String> list) {
        if (sortID == 1) { // Sort A -> Z
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareToIgnoreCase(o2);
                }
            });
            return list;
        }
        if (sortID == 2) { // Sort Z -> A
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o2.compareToIgnoreCase(o1);
                }
            });
            return list;
        }
        if (sortID == 3) { // Sort randomly/shuffle
            Collections.shuffle(list);
            return list;
        }
        
        return list; // Shouldn't reach here
    }

    private StringBuilder modifyStart(StringBuilder itemBuilder) {
        if (needBrackets) itemBuilder.insert(0, "[");
        itemBuilder.insert(0, prefix);

        return itemBuilder;
    }

    private String modifyEnd(String item) {
        int tagIndex = item.indexOf(tagger);
        if (tagIndex != -1 && tagIndex < item.length()-1) { // make sure there is a tagIndex and there is text after tagIndex
            String tag = item.substring(tagIndex+1, item.length()); // get tag
            item = item.substring(0, tagIndex); // remove tag from item

            if (needBrackets) item += "]";
            item += suffix;
            
            item += tag; // put tag after suffix
        } else { // no tag
            if (needBrackets) item += "]";
            item += suffix;
        }

        return item;
    }
    
    public String getFinalOutput() { 
        updateFinalOutput();
        return finalOutput; 
    }

}