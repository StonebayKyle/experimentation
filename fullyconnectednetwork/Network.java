// Followed tutorial by Finn Eggers

import java.util.Arrays;

public class Network
{

    private double[][] output; // 2D: 1st for current layer, 2nd for current neuron. (output[layer][neuron])
    private double[][][] weights; // 3D: 1st for layer, 2nd for neuron, 3rd for neuron in previous layer.(weights[layer][neuron][prevNeuron])
    private double[][] bias; // 2D: 1st for current layer, 2nd for current neuron. (bias[layer][neuron])    

    public final int[] NETWORK_LAYER_SIZES; // amount of neurons in layer at [index]
    public final int INPUT_SIZE; // amount of inputs
    public final int OUTPUT_SIZE; // amount of outputs
    public final int NETWORK_SIZE; // amount of layers

    public Network(int... NETWORK_LAYER_SIZES)
    {
        this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
        this.INPUT_SIZE = NETWORK_LAYER_SIZES[0];
        this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
        this.OUTPUT_SIZE = NETWORK_LAYER_SIZES[NETWORK_SIZE-1];

        this.output = new double[NETWORK_SIZE][];
        this.weights = new double[NETWORK_SIZE][][];
        this.bias = new double[NETWORK_SIZE][];

        for (int i = 0; i < NETWORK_SIZE; i++)
        {
            this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
            this.bias[i] = new double[NETWORK_LAYER_SIZES[i]];

            if (i > 0) // only create weights array for every layer except input layer.
            {
                weights[i] = new double[NETWORK_LAYER_SIZES[i]][NETWORK_LAYER_SIZES[i-1]];
            }
        }
    }

    public double[] calculate(double... input)
    {
        if (input.length != this.INPUT_SIZE) { return null; }
        this.output[0] = input; // first layer simply passes data through
        for (int layer = 1; layer < NETWORK_SIZE; layer++) // loop through each layer
        {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) // loop through each neuron
            {
                double sum = bias[layer][neuron]; // setting sum to the bias to start for part1 (addition in formula; intercept)
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer-1]; prevNeuron++) // loop through each previous neuron
                {
                    sum += output[layer-1][prevNeuron] * weights[layer][neuron][prevNeuron]; // Completes part1 of formula (multiplication in formula)
                }

                output[layer][neuron] = sigmoid(sum); // applies activation formula to sum and sets it to output
            }
        }
        return output[NETWORK_SIZE-1]; // returns output. -1 because index start at 0 and last index is the arraylength (NETWORK_SIZE) -1.
    }

    private double sigmoid(double x)
    {
        return 1d/ (1 + Math.exp(-x)); // activation function
    }

    public static void main(String[] args)
    {
        Network net = new Network(4,1,3,4);
        double[] output = net.calculate(0.2,0.9,0.3,0.4);
        System.out.println(Arrays.toString(output)); // should return all 0.5 due to everything resulting in 0 until activation function.
    }
}