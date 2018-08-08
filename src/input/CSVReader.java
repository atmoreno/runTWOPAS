package input;

import data.DataSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class CSVReader extends AbstractInputReader {

    //private static final Logger logger = Logger.getLogger(CSVReader.class);

    private BufferedReader reader;

    private int numberOfRecords = 0;

    protected CSVReader(DataSet dataSet){
        super(dataSet);
    }

    protected abstract void processHeader(String[] header);

    protected abstract void processRecord(String[] record);

    public void read (String fileName, String delimiter){
        initializeReader(fileName, delimiter);
        try {
            String record;
            while ((record = reader.readLine()) != null) {
                numberOfRecords++;
                processRecord(record.split(delimiter));
            }
        } catch (IOException e) {
            //System.out.println("Error parsing record number " + numberOfRecords + ": " + e.getMessage(), e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Read " + numberOfRecords + " records.");

    }

    private void initializeReader(String fileName, String delimiter) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
            processHeader(reader.readLine().split(delimiter));
        } catch (IOException e) {
            //System.out.println("Error initializing csv reader: " + e.getMessage(), e);
        }
    }
}
