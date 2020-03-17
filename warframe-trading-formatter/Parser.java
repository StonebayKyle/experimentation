import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

    private Parser() {}

    
    public static ArrayList<String> getItems(String filePath, String separator) {
        File input = new File(filePath);
        Scanner scan = null;
        ArrayList<String> items = new ArrayList<>();
        
        try {
            scan = new Scanner(input);
            
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] lineArray = line.split(separator);
                
                for (String item : lineArray) {
                    items.add(item);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scan.close();
        }
        
        return items;
    }
    
}