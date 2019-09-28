// Followed tutorial by Finn Eggers

package fullyconnectednetwork;

import trainset.TrainSet;

import java.util.Arrays;

public class Network
{

    private double[][] output; // 2D: 1st for current layer, 2nd for current neuron. (output[layer][neuron])
    private double[][][] weights; // 3D: 1st for layer, 2nd for neuron, 3rd for neuron in previous layer.(weights[layer][neuron][prevNeuron])
    private double[][] bias; // 2D: 1st for current layer, 2nd for current neuron. (bias[layer][neuron])  
    
    private double[][] error_signal;
    private double[][] output_derivative;

    public final int[] NETWORK_LAYER_SIZES; // amount of neurons in layer at [index]
    public final int INPUT_SIZE; // amount of inputs
    public final int OUTPUT_SIZE; // amount of outputs
    public final int NETWORK_SIZE; // amount of layers

    public Network(int... NETWORK_LAYER_SIZES) // ... : 0 or more arguments may be passed through. this.* = NETWORK_LAYER_SIZES sets each next value placed in function paramater area to the corresponding value.
    { // (makes an array out of NETWORK_LAYER-SIZES inputs)
        this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
        this.INPUT_SIZE = NETWORK_LAYER_SIZES[0];
        this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
        this.OUTPUT_SIZE = NETWORK_LAYER_SIZES[NETWORK_SIZE-1];

        this.output = new double[NETWORK_SIZE][];
        this.weights = new double[NETWORK_SIZE][][];
        this.bias = new double[NETWORK_SIZE][];

        this.error_signal = new double[NETWORK_SIZE][];
        this.output_derivative = new double[NETWORK_SIZE][];

        for (int i = 0; i < NETWORK_SIZE; i++)
        {
            this.output[i] = new double[NETWORK_LAYER_SIZES[i]]; // each neuron has 1 output, 1 error signal, and 1 output derivative.
            this.error_signal[i] = new double[NETWORK_LAYER_SIZES[i]];
            this.output_derivative[i] = new double[NETWORK_LAYER_SIZES[i]];
            
            this.bias[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], 0.3, 0.7);

            if (i > 0) // only create weights array for every layer except input layer.
            {   
                weights[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i],NETWORK_LAYER_SIZES[i-1],-0.3,0.5);
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
                output_derivative[layer][neuron] = output[layer][neuron] * (1 - output[layer][neuron]);
            }
        }
        return output[NETWORK_SIZE-1]; // returns output. -1 because index start at 0 and last index is the arraylength (NETWORK_SIZE) -1.
    }

    public void train(TrainSet set, int loops, int batch_size)
    {
        if (set.INPUT_SIZE != INPUT_SIZE || set.OUTPUT_SIZE != OUTPUT_SIZE) { return; } // data does not match and can't be used.
        for (int i = 0; i < loops; i++)
        {
            TrainSet batch = set.extractBatch(batch_size); // extracts random data
            for (int b = 0; b < batch_size; b++)
            {
                this.train(batch.getInput(b), batch.getOutput(b), 0.3); // sets train to use input and output
            }
            System.out.println(MSE(batch)); // prints mean square error of the batch to keep track of how good the learning process is.
        }
    }

    public double MSE(double[] input, double[] target) // Mean square error. Calculates how close output and target vectors are
    {
        if (input.length != INPUT_SIZE || target.length != OUTPUT_SIZE) return 0;
        calculate(input);
        double v = 0;
        for (int i = 0; i < target.length; i++)
        {
            v += (target[i] - output[NETWORK_SIZE-1][i]) * (target[i] - output[NETWORK_SIZE-1][i]);
        }
        return v / (2d * target.length);
    }

    public double MSE(TrainSet set) // Mean square error. Calculates the MSE for each of the datasets and returns the average
    {
        double v = 0;
        for (int i = 0; i < set.size(); i++)
        {
            v += MSE(set.getInput(i), set.getOutput(i));
        }
        return v / set.size();

    }

    public void train(double[] input, double[] target, double eta) // eta is learning rate
    {
        if (input.length != INPUT_SIZE || target.length != OUTPUT_SIZE) { return; } // data does not match and can't be used.
        calculate(input);
        backpropError(target);
        updateWeights(eta);
    }

    public void backpropError(double[] target) // calculates error using the error formula
    {
        for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[NETWORK_SIZE-1]; neuron++)
        {
            error_signal[NETWORK_SIZE-1][neuron] = (output[NETWORK_SIZE-1][neuron] - target[neuron])
                * output_derivative[NETWORK_SIZE-1][neuron]; // finds the error signal in a neuron in current layer
        }
        for (int layer = NETWORK_SIZE-2; layer > 0; layer--) // NETWORK_SIZE-2 is index of last hidden layer. layer > 0 because we ignore input layer as it just passes data through and does not have error.
        {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) // go through every neuron in layer
            {
                double sum = 0;
                for (int nextNeuron = 0; nextNeuron < NETWORK_LAYER_SIZES[layer+1]; nextNeuron++ ) // layer+1 because that is the layer that the next neuron is in
                {
                    sum += weights[layer+1][nextNeuron][neuron] * error_signal[layer+1][nextNeuron]; // increase sum by weight that connects next neuron and current neuron
                }
                this.error_signal[layer][neuron] = sum * output_derivative[layer][neuron];
            }

        }
    }

    public void updateWeights(double eta)
    {
        for (int layer = 1; layer < NETWORK_SIZE; layer++) // starts at first hidden layer, until reach output layer
        {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) // go through each neuron in the layer
            {
                // each neuron has multiple weights (from having multiple connections), but only one bias, so it is done outside of weights loop.
                double delta = -eta * error_signal[layer][neuron]; // delta is change in bias
                bias[layer][neuron] += delta;
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer-1]; prevNeuron++) // go through each previous neuron to get each weight
                {
                    weights[layer][neuron][prevNeuron] += delta * output[layer-1][prevNeuron]; // Uses previous delta because it is less calculations to do and is the same formula.
                }
            }
        }
    }

    private double sigmoid(double x)
    {
        return 1d/ (1 + Math.exp(-x)); // activation function
    }

    public static void main(String[] args)
    {
        // generally have the highest amount of neurons first, and go down until the lowest (i.e no 4,100,3,2)
        Network net = new Network(4,3,3,2); // first[0]: amount of input neurons. In between[1->last-1]: amount of neurons in each hidden layer. last[length-1]: amount of output neurons.
        // MATCH input and output data amounts with input and output neuron amounts, else data won't match Network and it will not train.

        TrainSet set = new TrainSet(4,2); // input size, output size
                                // data                     // labels
        set.addData(new double[]{0.1,0.2,0.3,0.4}, new double[]{0.9,0.1}); // adds sets of new data to be trained with
        set.addData(new double[]{0.9,0.8,0.7,0.6}, new double[]{0.1,0.9});
        set.addData(new double[]{0.8,0.3,0.1,0.4}, new double[]{0.3,0.7});
        set.addData(new double[]{0.9,0.8,0.1,0.2}, new double[]{0.7,0.3});

        net.train(set, 100000, 4); // dataset group, amount of loops/iterations to run, batchsize

        for (int i = 0; i < 4; i++) // runs for each dataset
        {
            System.out.println(Arrays.toString(net.calculate(set.getInput(i)))); // Prints the arrays of the trained and calculated result of the set input.
        }
    }
}