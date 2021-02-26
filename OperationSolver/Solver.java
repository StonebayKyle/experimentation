import java.util.ArrayList;
import java.util.Arrays;

public class Solver {

    private Solver() {}

    // this method randomly guesses many times per input permutation. It is slower, and is not guarenteed to work.
    public static Solution slowSolve(double[] inputNums, double targetNum) {
        Solution solution = new Solution(targetNum);
        int attempts = 0; // attempt counter
        
        ArrayList<Double> inputList = Main.doubleArrayToList(inputNums);
        ArrayList<Double[]> permutationList = new ArrayList<>();
        System.out.println("Generating possible permutations of the input numbers...");
        PermutationGenerator.permutingArray(inputList, 0, permutationList);
        System.out.println("Complete. There are " + permutationList.size() + " permutations of the input numbers.");

        // permutation random guessing is slow. Other solve function calculate permutations of operations.
        Operation[] currentOperations = new Operation[inputNums.length-1];
        final int attemptsPerPermutation = 1000000;

        int permIncrement = 0;
        // for each permutation
        for (Double[] permutationObj : permutationList) {
            double[] permutation = Main.objToPrimDouble(permutationObj);
            System.out.println(progressString(permIncrement, permutationList.size()));
            System.out.println("Attempting next permutation: " + Arrays.toString(permutation));
            // for each currentOperation
            for (int i = 0; i < attemptsPerPermutation; i++) {
                for (int j = 0; j < currentOperations.length; j++) {
                    currentOperations[j] = Operation.random(); // this should be reworked to also be based on permutations
                }
                attempts++;
                if (validOperation(permutation, targetNum, currentOperations)) {
                    System.out.println(attempts + ": SUCCESS! " + Solution.genericToString(permutation, targetNum, currentOperations));
                    solution.setSolution(permutation, currentOperations);
                    return solution;
                } else {
                    // System.out.println(attempts + ": FAIL: " + Solution.genericToString(permutation, targetNum, currentOperations));
                }
            }

            System.out.println(attempts + ": Solution not found.");
            permIncrement++;
        }

        System.out.println(progressString(permIncrement, permutationList.size()));

        return solution;
    }


    public static Solution solve(double[] inputNums, double targetNum) {
        Solution solution = new Solution(targetNum);
        int attempts = 0; // attempt counter
        
        System.out.println("Generating possible permutations of the input numbers...");
        ArrayList<Double> inputList = Main.doubleArrayToList(inputNums);
        ArrayList<Double[]> permutationList = new ArrayList<>();
        PermutationGenerator.permutingArray(inputList, 0, permutationList);
        System.out.println("Complete. There are " + permutationList.size() + " permutations of the input numbers.");

        System.out.println("Generating possible permutations of the operations at length " + inputNums.length + "...");
        ArrayList<ArrayList<Operation>> possibleOperations = new ArrayList<>();
        ArrayList<Operation> currentOperations = new ArrayList<>();
        PermutationGenerator.repeatingPermutations(Operation.list(), 0, inputNums.length-1, currentOperations, possibleOperations);
        System.out.println("Complete. There are " + possibleOperations.size() + " permutations of the operations.");
        //System.out.println(Main.list2DString(possibleOperations));

        final int totalPossibilities = permutationList.size() * possibleOperations.size();
        System.out.println("Total number of possibilities to attempt: " + totalPossibilities);

        System.out.println("Attempting solutions...");
        int permIncrement = 0;
        // for each permutation of input
        for (Double[] permutationObj : permutationList) {
            double[] permutation = Main.objToPrimDouble(permutationObj);
            System.out.println(progressString(permIncrement, permutationList.size()));
            System.out.println("Attempting next permutation: " + Arrays.toString(permutation));
            // for each possible operation permutation
            for (ArrayList<Operation> operationObj : possibleOperations) {
                Operation[] operations = Main.operationListToArray(operationObj);
                attempts++;
                if (validOperation(permutation, targetNum, operations)) {
                    System.out.println(attempts + ": SUCCESS! " + Solution.genericToString(permutation, targetNum, operations));
                    solution.setSolution(permutation, operations);
                    return solution;
                } else {
                    // System.out.println(attempts + ": FAIL: " + Solution.genericToString(permutation, targetNum, currentOperations));
                }
            }

            System.out.println(attempts + ": Solution not found.");
            permIncrement++;
        }

        System.out.println(progressString(permIncrement, permutationList.size()));

        return solution;
    }

    // whether or not the operations end up matching targetNum
    private static boolean validOperation(double[] inputNums, double targetNum, Operation[] operations) {
        double currentValue = inputNums[0];
        for (int i = 0; i < operations.length; i++) {
            currentValue = operations[i].apply(currentValue, inputNums[i+1]);
        }

        return currentValue == targetNum;
    }

    private static String progressString(int currentIncrement, int totalPermutations) {
        double progress = Math.round(((double)currentIncrement/totalPermutations)*10000)/100.0;
        String out = currentIncrement + "/" + totalPermutations + "-------" + progress + "%-------";
        return out;
    }
}