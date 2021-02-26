import java.util.ArrayList;

public class PermutationGenerator {
    
    public static void main(String[] args) {
        System.out.println("Testing permutation algorithm...");
        int[] arr = {1, 2, 3, 4}; // possibilities each slot
        int length = 3; // output permutation length
        int count = 0; // current permutation index

        ArrayList<Integer> perm = new ArrayList<>(length);
        ArrayList<ArrayList<Integer>> save = new ArrayList<>();

        repeatingPermutations(arr, 0, length, perm, "", save, count);

        System.out.println("Save: ");
        System.out.println(Main.list2DString(save));
    }

    // generates all permutations of the arrayList, and then saves the results in outputList
    public static void permutingArray(java.util.List<Double> arrayList, int element, ArrayList<Double[]> outputList) {
        for (int i = element; i < arrayList.size(); i++) {
            java.util.Collections.swap(arrayList, i, element);
            permutingArray(arrayList, element + 1, outputList);
            java.util.Collections.swap(arrayList, element, i);
        }
        if (element == arrayList.size() - 1) {
            outputList.add((Double[])arrayList.toArray(new Double[0]));
        }
    }

    // The repeatingPermutations functions are highly modified from https://stackoverflow.com/questions/53148835/how-to-print-out-x-number-of-permutations-in-java

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