package input;

import data.DataSet;
import resources.Properties;
import resources.Resources;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IntersectionReader extends CSVReader {

    private int countryIndex;
    private int valueIndex;
    private int frequencyIndex;

    public IntersectionReader(DataSet dataSet){
        super(dataSet);
    }

    public void read(){super.read(Resources.INSTANCE.getString(Properties.INTERSECTION_LENGTH_DISTRIBUTION), ",");}

    protected void processHeader(String[] header){
        List<String> headerList = Arrays.asList(header);
        countryIndex = headerList.indexOf("Country");
        valueIndex = headerList.indexOf("lengthInterval");
        frequencyIndex = headerList.indexOf("frequency");
    }

    protected void processRecord(String[] record){
        String country = record[countryIndex].replace("\"", "");
        int value = Math.max(Integer.parseInt(record[valueIndex]), 0);
        int frequency = Integer.parseInt(record[frequencyIndex]);
        if (dataSet.getIntersectionLengths().containsKey(country)){
            dataSet.getIntersectionLengths().get(country).put(value, frequency);
        } else {
            Map<Integer, Integer> map = new LinkedHashMap<>();
            map.put(value, frequency);
            dataSet.getIntersectionLengths().put(country, map);
        }

    }
}
