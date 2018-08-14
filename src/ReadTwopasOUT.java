import data.DataSet;
import summarizeOutputs.PrepareAndReadOutputs;

import java.io.*;

public class ReadTwopasOUT {

    private BufferedReader reader;
    private int numberOfRecords = 0;
    private DataSet dataSet;

    public static void main(String[] args) throws IOException, InterruptedException {

        PrepareAndReadOutputs pp = new PrepareAndReadOutputs(args[0]);
        pp.run();


    }


}
