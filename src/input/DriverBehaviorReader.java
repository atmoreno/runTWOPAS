package input;

import data.DataSet;
import resources.Properties;
import resources.Resources;

import java.util.Arrays;
import java.util.List;

public class DriverBehaviorReader extends CSVReader {

    private int driverIndex;
    private int corBKMPIndex;
    private int precIndex;

    public DriverBehaviorReader(DataSet dataSet){
        super(dataSet);
    }

    public void read(){super.read(Resources.INSTANCE.getString(Properties.DRIVER_BEHAVIOR_CHARACTERISTICS), ",");}

    protected void processHeader(String[] header){
        List<String> headerList = Arrays.asList(header);
        driverIndex = headerList.indexOf("Number");
        corBKMPIndex = headerList.indexOf("COR_BKMP");
        precIndex = headerList.indexOf("PREC");
    }

    protected void processRecord(String[] record){
        int id = Integer.parseInt(record[driverIndex]);
        String cor = record[corBKMPIndex];
        float prec = Float.parseFloat(record[precIndex]);
        dataSet.getcorBKMPMap().put(id, cor);
        dataSet.getPrecMap().put(id,prec);
    }
}
