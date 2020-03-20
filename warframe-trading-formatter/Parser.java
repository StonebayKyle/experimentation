import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Parser {

    private Parser() {
    }

    public static ArrayList<String> getItems(String filePath, String separator) {
        File input = new File(filePath);
        Scanner scan = null;
        ArrayList<String> items = new ArrayList<>();

        try {
            scan = new Scanner(input);

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] lineArray = line.split(Pattern.quote(separator));

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

    public static String getRawString(String filepath) throws Exception {
        String output = "";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String st;
        while ((st = bufferedReader.readLine()) != null) {
            output += st;
        }

        bufferedReader.close();

        return output;
    }

    public static String getRawString(File file) throws Exception {
        String output = "";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String st;
        while ((st = bufferedReader.readLine()) != null) {
            output += st;
        }

        bufferedReader.close();

        return output;
    }
    
}