public class NetworkTools
{
    public static double[] createArray(int size, double init_value) // creates and initializes an array
    {
        if (size < 1) { return null; } // array too small
        double[] ar = new double[size];
        for (int i = 0; i < size; i++)
        {
            ar[i] = init_value
        }
        return ar;
    }

    public static double[] createRandomArray(int size, double lower_bound, double upper_bound)
    {
        if (size < 1) { return null; }
        double ar = new double[size];
        for (int i = 0; i < size; i++)
        {
            ar[i] = randomValue(lower_bound,upper_bound);
        }
        return ar;
    }

    public static double[][] createRandomArray(int sizeX, int sizeY, double lower_bound, double upper_bound) // function overloading. Decides which function to run based on amount of parameters
    {
        if (sizeX < 1 || sizeY < 1) { return null; }
        double ar = new double[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++)
        {
            ar[i] = createRandomArray(sizeY,lower_bound,upper_bound); // nested for loop but using already made function
        }
        return ar;
    }

    public static double randomValue(double lower_bound, double upper_bound)
    {
        return Math.random() * (upper_bound-lower_bound) + lower_bound;
    }

    public static Integer[] randomValues(int lower_bound, int upper_bound, int amount) // generates array of random values with no duplicates.
    {
        lower_bound--;
        if (amount > upper_bound-lower_bound) { return null; }

        Integer[] values = new Integer[amount];
        for (int i = 0; i < amount; i++)
        {
            int n = (int)(Math.random() * (upper_bound-lower_bound+1) + lower_bound);
            while (containsValue(values,n))
            {
                n = (int)(Math.random() * (upper_bound-lower_bound+1) + lower_bound);
            }
            values[i] = n;
        }
    }

    public static <T extends Comparable<T>> boolean containsValue(T[] ar, T value)
    {
        for (int i = 0; i < ar.length; i++)
        {
            if (ar[i] != null)
            {
                if (value.compareTo(ar[i] == 0))
                {
                    return true;
                }
            }
        }
        return false;
    }
}