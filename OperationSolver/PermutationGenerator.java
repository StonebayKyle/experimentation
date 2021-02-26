import java.util.ArrayList;
import java.util.Arrays;

// unused code from javabypatel: https://javabypatel.blogspot.com/2015/08/print-all-combinations-of-string-of-length-k.html
public class PermutationGenerator {

    /*
    
    public static void main(String[] args) {
        // method 1
        //permutation("ABC", "", 2);
        //permutationOfBinaryNumbers("", 2);

        // method 2
        GenerateAllPermutationOfNBits obj = new GenerateAllPermutationOfNBits();
        int lengthOfCombination = 2; //(length of the combination to generate)
        int combinationSet = 2;
        //(2 means use 0 and 1 while generating combination)
        //(3 means use 0, 1 and 2 while generating combination)
 
        //obj.printCombination(new int[lengthOfCombination], 0, lengthOfCombination, combinationSet);
 
        System.out.println("Print from Given set...");
        char[] combinationSetArr = new char[]{'A', 'B', 'C'};
        obj.printCombinationOfGivenSet(new char[lengthOfCombination], 0, lengthOfCombination, combinationSetArr);

        // int amountOfPermutations = 9;
        // char[][] saveCombinationSet = new char[amountOfPermutations][lengthOfCombination];
        // obj.saveCombinationOfGivenSet(saveCombinationSet, new char[lengthOfCombination], 0, lengthOfCombination, combinationSetArr);

        // System.out.println("Saved version:");
        // Main.print2DCharArray(saveCombinationSet);

        // new method? 
        System.out.println("New:");
        int[] arr = {1, 2, 3, 4}; // possibilities each slot
        int length = 3; // output permutation length
        int count = 0; // current permutation index

        ArrayList<Integer> perm = new ArrayList<>(length);

        ArrayList<ArrayList<Integer>> save = new ArrayList<>();

        repeatingPermutations(arr, 0, length, perm, "", save, count);

        System.out.println("Save: ");
        System.out.println(Main.list2DString(save));
    }

    */
    
    private static void permutation(String str, String prefix, int lengthOfPermutationString) {
        if (prefix.length()==lengthOfPermutationString) {
            System.out.println(prefix);
        } else {
            for (int i = 0; i < str.length(); i++) {
                permutation(str, prefix + str.charAt(i), lengthOfPermutationString);
            }
        }
    }

    private static void permutationOfBinaryNumbers(String prefix, int lengthOfPermutationString) {
        if (prefix.length()==lengthOfPermutationString) {
            System.out.println(prefix);
        } else {
            permutationOfBinaryNumbers(prefix + "1", lengthOfPermutationString);
            permutationOfBinaryNumbers(prefix + "0", lengthOfPermutationString);
        }
    }

    public void printCombination(int[] arr, int i, int lengthOfCombination, int combinationSet) {
        if (i == lengthOfCombination) {
            System.out.println(Arrays.toString(arr));
            return;
        }
 
        for (int j=0; j<combinationSet; j++) {
            arr[i] = j;
            printCombination(arr, i+1, lengthOfCombination, combinationSet);
        }
    }
 
    public void printCombinationOfGivenSet(char[] arr, int i, int lengthOfCombination, char[] combinationSet) {
        if (i == lengthOfCombination) {
            System.out.println(Arrays.toString(arr));
            return;
        }
 
        for (int j=0; j<combinationSet.length; j++) {
            arr[i] = combinationSet[j];
            printCombinationOfGivenSet(arr, i+1, lengthOfCombination, combinationSet);
        }
    }

    public void saveCombinationOfGivenSet(char save[][], char[] arr, int permIncrement, int i, int lengthOfCombination, char[] combinationSet) {
        if (i == lengthOfCombination) {
            System.out.println("Saving at " + i + ": " + Arrays.toString(arr));
            save[i] = arr;
            return;
        }
 
        for (int j=0; j<combinationSet.length; j++) {
            arr[i] = combinationSet[j];
            saveCombinationOfGivenSet(save, arr, permIncrement, i+1, lengthOfCombination, combinationSet);
        }
    }



    // These two methods highly modified from https://stackoverflow.com/questions/53148835/how-to-print-out-x-number-of-permutations-in-java

    // save is the array where the permutation is saved, and count is the amount of permutations already generated.
    // pos is the current index, and K is the length of permutation you want to save.
    private static void repeatingPermutations(int[] arr, int pos, int length, ArrayList<Integer> perm, String str, ArrayList<ArrayList<Integer>> save, int count) {
        if (pos == length) {
            save.add(perm);
            System.out.println("String perm: " + str);
            System.out.println("Perm " + count + ": " + Main.listString(perm) + "\n");
            count++; // we have found a valid permutation, increment counter.
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            // perm.add(arr[i]);
            ArrayList<Integer> passedPerm = (ArrayList<Integer>) perm.clone(); // this is probably terrible for memory use
            passedPerm.add(arr[i]);
            repeatingPermutations(arr, pos + 1, length, passedPerm, str + arr[i], save, count);
        }
    }

    public static <T> void repeatingPermutations(T[] arr, int pos, int length, ArrayList<T> perm, ArrayList<ArrayList<T>> save) {
        if (pos == length) {
            save.add(perm);
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            // perm.add(arr[i]);
            ArrayList<T> passedPerm = (ArrayList<T>) perm.clone(); // this is probably terrible for memory use
            passedPerm.add(arr[i]);
            repeatingPermutations(arr, pos + 1, length, passedPerm, save);
        }
    }
  
}