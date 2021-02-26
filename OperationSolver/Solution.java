public class Solution {
    double[] inputPermutation;
    double targetNum;
    Operation[] operations;

    public Solution(double targetNum) {
        this.targetNum = targetNum;
    }
    
    
    public Operation[] getOperations() {
        return operations;
    }
    
    // operations assumed to be in order with inputNums, with one less operation than inputNum
    private void setOperations(Operation[] operations) {
        this.operations = operations;
    }

    private void setInputPermutation(double[] inputPermutation) {
        this.inputPermutation = inputPermutation;
    }

    public void setSolution(double[] inputPermutation, Operation[] operations) {
        setInputPermutation(inputPermutation);
        setOperations(operations);
    }

    
    public String toString() {
        return genericToString(inputPermutation, targetNum, operations);
    }

    public static String genericToString(double[] inputPermutation, double targetNum, Operation[] operations) {
        if (operations == null) {
            // without operations set, there is no solution.
            return "No Solution";
        }

        String out = "";
        for (int i = 0; i < inputPermutation.length; i++) {
            if (i < inputPermutation.length-1) {
                out += inputPermutation[i] + " " + operations[i] + " ";
            } else {
                out += inputPermutation[i];
            }
        }

        out += " = " + targetNum;
        return out;
    }
}
