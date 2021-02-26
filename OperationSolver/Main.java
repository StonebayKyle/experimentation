import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static Scanner scan;

    public static void main(String[] args) {
        System.out.println("This program finds an equation that equals a target number using every input number, with addition, subtraction, multiplication, and division.");
        scan = new Scanner(System.in);
        
        double targetNum = getRequiredNumber("Enter target number: ");
        
        double[] inputNums = getInputNums();
        scan.close();
        
        System.out.println("\nTarget number: " + targetNum);
        System.out.println("Numbers to reach target: " + Arrays.toString(inputNums));
        
        System.out.println("Operation Solver Started.\n");
        Solution solution = Solver.solve(inputNums, targetNum);

        System.out.println("Solution:\n" + solution);
    }

    private static double[] getInputNums() {
        ArrayList<Double> inputs = new ArrayList<>();

        final char stopChar = 's';

        System.out.println("\nEnter numbers that must be used to reach the target number. Type '" + stopChar + "' to stop.");
        inputs.add(getRequiredNumber("Enter number: "));

        boolean moreInputs = true; // likely not necessary, but avoiding a while(true)
        while (moreInputs) {
            boolean fail = true; // also likely not necessary, but avoiding a while(true)
            while (fail) {
                System.out.println("Enter number ('s' to stop):");
                String input = scan.nextLine();
                // stop early
                if (input.length() != 0 && input.charAt(0) == stopChar) {
                    moreInputs = false;
                    break;
                }

                // verify and add number to list
                try {
                    fail = false;
                    inputs.add(Double.parseDouble(input));
                } catch (Exception e) {
                    fail = true;
                    System.out.println("Invalid input! Try again.");
                }
            }
        }

        return doubleListToArray(inputs);
    }

    private static double getRequiredNumber(String prompt) {
        boolean fail = true; // technically not required, but just avoiding a while(true)
        
        while (fail) {
            System.out.println(prompt);
            String input = scan.nextLine();
            try {
                fail = false;
                return Double.parseDouble(input);
            } catch (Exception e) {
                System.out.println("Invalid input! Try again.");
                fail = true;
            }
        }

        return 0; // this shouldn't happen
    }

    
    public static Operation[] operationListToArray(ArrayList<Operation> list) {
        Operation[] array = new Operation[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        
        return array;
    }
    
    public static double[] doubleListToArray(ArrayList<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static ArrayList<Double> doubleArrayToList(double[] array) {
        ArrayList<Double> list = new ArrayList<>();
        for (double num : array) {
            list.add(num);
        }
        return list;
    }

    public static int randInt(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public static int factorial(int n) {
        if (n == 0) return 1;
        return n * factorial(n-1);
    }

    public static int possibleNonrepeatPermutations(int n, int r) {
        // n types of elements  (how many can go in each slot)
        // r non-repeating elements (array size)
        return factorial(n) / factorial(n - r);
    }

    public static int possibleRepeatPermutations(int n, int r) {
        // n types of elements  (how many can go in each slot)
        // r non-repeating elements (array size)
        return (int)Math.pow(n, r);
    }

    public static void print2DCharArray(char[][] charMatrix) {
        for (char[] charArray : charMatrix) {
            Arrays.toString(charArray);
        }
    }

    public static <T> String listString(ArrayList<T> list) {
        String out = "";
        for (T item : list) {
            out += item.toString();
        }
        return out;
    } 

    public static <T> String list2DString(ArrayList<ArrayList<T>> list2D) {
        String out = "";
        for (ArrayList<T> list : list2D) {
            out += listString(list) + "\n";
        }
        return out;
    }

}