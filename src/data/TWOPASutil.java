package data;

import java.io.*;
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

    public static PrintWriter openFileForSequentialWriting(String fileName, boolean appendFile) {
        // open file and return PrintWriter object

        File outputFile = new File(fileName);
        if(outputFile.getParent() != null) {
            File parent = outputFile.getParentFile();
            parent.mkdirs();
        }

        try {
            FileWriter fw = new FileWriter(outputFile, appendFile);
            BufferedWriter bw = new BufferedWriter(fw);
            return new PrintWriter(bw);
        } catch (IOException e) {
            System.out.println("Could not open file <" + fileName + ">.");
            return null;
        }
    }

    public static float convertMetersToFeet(float value){
        return (float) value * 3.28084f;
    }


    public static float convertMetersToFeet(double value){
        return (float) value * 3.28084f;
    }


    public static float randommizeFlow(int value, int step){

        float randomValue;
        if (rand.nextFloat() < 0.5)
            randomValue = value + rand.nextFloat() * step / 2;
        else {
            randomValue = value - rand.nextFloat() * step / 2;
        }

        return randomValue;
    }

    public static float roundToTwoDecimals(float value){
        return (float) Math.round(value * 100) / 100;
    }

    public static String addBlanksToString(int number){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    public static String addZerosToString(int number){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            builder.append("0");
        }
        return builder.toString();
    }

    public static String printWithBlanks(int characters, int number){
        int numberBlanks = characters - Integer.toString(number).length();
        String line = addBlanksToString(numberBlanks) + number;
        if (numberBlanks < 0){
            throw new RuntimeException("The number exceeds the characters maximum " + number);
        }
        return line;
    }

    public static String printWithBlanks(int characters, int number, int decimals){
        int numberBlanks = characters - Integer.toString(number).length() - decimals - 1;
        String line = addBlanksToString(numberBlanks) + number + "." + addZerosToString(decimals);
        if (numberBlanks < 0){
            throw new RuntimeException("The number exceeds the characters maximum " + number);
        }
        return line;
    }

    public static String printWithBlanks(int characters, float number){
        int numberBlanks = characters - Float.toString(number).length();
        String line = addBlanksToString(numberBlanks) + number;
        if (numberBlanks < 0){
            throw new RuntimeException("The number exceeds the characters maximum " + number);
        }
        return line;
    }

    public static String printWithBlanks(int characters, float number, int decimals){

        String numberString = String.format("%." + decimals + "f", number);
        int numberBlanks = characters - numberString.length();
        String line;
        int differenceOnDecimals = decimals - numberString.split("\\.")[1].length();
        if (differenceOnDecimals == 0){
            line = addBlanksToString(numberBlanks) + numberString;
        } else {
            numberBlanks = numberBlanks - differenceOnDecimals;
            line = addBlanksToString(numberBlanks) + numberString + addZerosToString(differenceOnDecimals);
        }
        if (numberBlanks < 0){
            throw new RuntimeException("The number exceeds the characters maximum " + number);
        }
        return line;
    }

    public static String printWithBlanks(int characters, double number, int decimals){

        String numberString = String.format("%." + decimals + "f", number);
        int numberBlanks = characters - numberString.length();
        String line;
        int differenceOnDecimals = decimals - numberString.split("\\.")[1].length();
        if (differenceOnDecimals == 0){
            line = addBlanksToString(numberBlanks) + numberString;
        } else {
            numberBlanks = numberBlanks - differenceOnDecimals;
            line = addBlanksToString(numberBlanks) + numberString + addZerosToString(differenceOnDecimals);
        }
        if (numberBlanks < 0){
            throw new RuntimeException("The number exceeds the characters maximum " + number);
        }
        return line;
    }


    public static int selectFromUniformDistribution(int size){

        double selectedWeight = rand.nextDouble() * size;
        double select = 0;
        for (int index = 0; index < size; index++){
            if (index > selectedWeight){
                return index + 1;
            }
        }

        return size;

    }
}
