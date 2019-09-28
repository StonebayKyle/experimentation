import java.util.ArrayList;
import java.util.Arrays;

public class TrainSet
{
    public final int INPUT_SIZE;
    public final int OUTPUT_SIZE;

    //double[][] <- index1: 0 = input, 1 = output || index2: index of element
    private ArrayList<double[][]> data = new ArrayList<>(); // ArrayList allows for the data sizes to vary

    public TrainSet(int INPUT_SIZE, int OUTPUT_SIZE) // constructor
    {
        this.INPUT_SIZE = INPUT_SIZE;
        this.OUTPUT_SIZE = OUTPUT_SIZE;
    }

    public void addData(double[] in, double[] expected)
    {
        if (in.length != INPUT_SIZE || expected.length != OUTPUT_SIZE) return;
        data.add(new double[][] { in, expected }); // adds data to end of list
    }

    public TrainSet extractBatch(int size) // chooses *size random data sets to be trained on
    {
        if(size > 0 && size <= this.size())
        {
            TrainSet set = new TrainSet(INPUT_SIZE, OUTPUT_SIZE);
            Integer[] ids = NetworkTools.randomValues(0,this.size() - 1, size);
            for(Integer i:ids)
            {
                set.addData(this.getInput(i),this.getOutput(i));
            }
            return set;
        }else return this;
    }

    public static void main(String[] args)
    {
        TrainSet set = new TrainSet(3, 2); // input size, output size

        for (int i = 0; i < 8; i++) // adds 8 sets of random arrays to data ArrayList
        {
            double[] a = new double[3];
            double[] b = new double[2];
            for (int k = 0; k < 3; k++) // sets random values to each entry in the arrays.
            {
                a[k] = (double)((int)(Math.random() * 10)) / (double)10;
                if (k < 2)
                {
                    b[k] = (double)((int)(Math.random() * 10)) / (double)10;
                }
            }
            set.addData(a,b);
        }

        System.out.println(set);
        System.out.println(set.extractBatch(3));
    }

    public String toString()
    {
        String s = "TrainSet ["+INPUT_SIZE+ " ; "+OUTPUT_SIZE+"]\n";
        int index = 0;
        for(double[][] r:data)
        {
            s += index +":   "+Arrays.toString(r[0]) +"  >-||-<  "+Arrays.toString(r[1]) +"\n";
            index++;
        }
        return s;
    }

    public int size() { return data.size(); }

    public double[] getInput(int index)
    {
        if(index >= 0 && index < size())
            return data.get(index)[0];
        else return null;
    }
    
    public double[] getOutput(int index)
    {
        if(index >= 0 && index < size())
            return data.get(index)[1];
        else return null;   
    }

    public int getINPUT_SIZE() { return INPUT_SIZE; }

    public int getOUTPUT_SIZE() { return OUTPUT_SIZE; }
}