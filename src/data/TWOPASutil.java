package data;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

public class TWOPASutil {

    private static Random rand;

    private TWOPASutil(){

    }

    public static void initializeRandomNumber(int seed) {
        if (seed == -1)
            rand = new Random();
        else
            rand = new Random(seed);
    }

    public static <T> T select(Map<T, ? extends Number> mappedProbabilities) {
        // select item based on probabilities (for mapped double probabilities)
        return select(mappedProbabilities, getSum(mappedProbabilities.values()));
    }

    public static <T> T select(Map<T, ? extends Number> mappedProbabilities, double sum) {
        // select item based on probabilities (for mapped double probabilities)
        double selectedWeight = rand.nextDouble() * sum;
        double select = 0;
        for (Map.Entry<T, ? extends Number> entry : mappedProbabilities.entrySet()) {
            select += entry.getValue().doubleValue();
            if (select > selectedWeight) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static double getSum(Collection<? extends Number> values) {
        double sm = 0;
        for (Number value : values) {
            sm += value.doubleValue();
        }
        return sm;
    }

}
