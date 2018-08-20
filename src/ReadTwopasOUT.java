import data.DataSet;
import summarizeOutputs.SummarizeTwopasOutputs;

import java.io.*;

public class ReadTwopasOUT {

    private BufferedReader reader;
    private int numberOfRecords = 0;
    private DataSet dataSet;

    public static void main(String[] args) throws IOException, InterruptedException {

        SummarizeTwopasOutputs pp = new SummarizeTwopasOutputs(args[0]);
        pp.run();

    }


}
