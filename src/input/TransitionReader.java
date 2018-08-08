package input;

import data.DataSet;
import resources.Properties;
import resources.Resources;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransitionReader extends CSVReader {

    private int countryIndex;
    private int valueIndex;
    private int frequencyIndex;
    private int typeIndex;

    public TransitionReader(DataSet dataSet){
        super(dataSet);
    }

    public void read(){super.read(Resources.INSTANCE.getString(Properties.TRANSITION_LENGTH_DISTRIBUTION), ",");}

    protected void processHeader(String[] header){
        List<String> headerList = Arrays.asList(header);
        countryIndex = headerList.indexOf("Country");
        valueIndex = headerList.indexOf("lengthInterval");
        frequencyIndex = headerList.indexOf("frequency");
        typeIndex = headerList.indexOf("Type");
    }

    protected void processRecord(String[] record){
        String country = record[countryIndex].replace("\"", "");
        String type = record[typeIndex].replace("\"","");
        int value = Integer.parseInt(record[valueIndex]);
        int frequency = Integer.parseInt(record[frequencyIndex]);
        if (dataSet.getTransitionLengths().containsKey(country)){
            if (dataSet.getTransitionLengths().get(country).containsKey(type)){
                dataSet.getTransitionLengths().get(country).get(type).put(value, frequency);
            } else {
                Map<Integer, Integer> map = new LinkedHashMap<>();
                map.put(value, frequency);
                dataSet.getTransitionLengths().get(country).put(type, map);
            }
        } else {
            Map<Integer, Integer> map = new LinkedHashMap<>();
            map.put(value, frequency);
            Map<String, Map<Integer, Integer>> outerMap = new LinkedHashMap<>();
            outerMap.put(type, map);
            dataSet.getTransitionLengths().put(country, outerMap);
        }

    }
}
