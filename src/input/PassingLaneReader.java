package input;

import data.DataSet;
import resources.Properties;
import resources.Resources;

import java.util.*;

public class PassingLaneReader extends CSVReader {

    private int countryIndex;
    private int valueIndex;
    private int frequencyIndex;

    public PassingLaneReader(DataSet dataSet){
        super(dataSet);
    }

    public void read(){super.read(Resources.INSTANCE.getString(Properties.PASSING_LANE_LENGTH_DISTRIBUTION), ",");}

    public void read(String fileName){}

    protected void processHeader(String[] header){
        List<String> headerList = Arrays.asList(header);
        countryIndex = headerList.indexOf("Country");
        valueIndex = headerList.indexOf("lengthInterval");
        frequencyIndex = headerList.indexOf("frequency");
    }

    protected void processRecord(String[] record){
        String country = record[countryIndex].replace("\"", "");
        int value = Integer.parseInt(record[valueIndex]);
        float frequency = Float.parseFloat(record[frequencyIndex]);
        if (dataSet.getPassingLaneLengths().containsKey(country)){
            dataSet.getPassingLaneLengths().get(country).put(value, frequency);
        } else {
            Map<Integer, Float> map = new LinkedHashMap<>();
            map.put(value, frequency);
            dataSet.getPassingLaneLengths().put(country, map);
        }

    }
}
