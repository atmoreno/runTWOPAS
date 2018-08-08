package input;

import data.DataSet;
import resources.Properties;
import resources.Resources;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProbabilityReader extends CSVReader {

    private int countryIndex;
    private int criticalIndex;
    private int noncriticalIndex;

    public ProbabilityReader(DataSet dataSet){
        super(dataSet);
    }

    public void read(){super.read(Resources.INSTANCE.getString(Properties.TRANSITION_PROBABILITY), ",");}

    protected void processHeader(String[] header){
        List<String> headerList = Arrays.asList(header);
        countryIndex = headerList.indexOf("Country");
        criticalIndex = headerList.indexOf("probCriticalTransition");
        noncriticalIndex = headerList.indexOf("probNonCriticalTransition");
    }

    protected void processRecord(String[] record){
        String country = record[countryIndex].replace("\"", "");
        float probCritical = Float.parseFloat(record[criticalIndex]);
        float probNonCritical = Float.parseFloat(record[noncriticalIndex]);
        if (dataSet.getTransitionProbability().containsKey(country)){
            dataSet.getTransitionProbability().get(country).put("critical", probCritical);
            dataSet.getTransitionProbability().get(country).put("noncritical", probNonCritical);
        } else {
            Map<String, Float> map = new LinkedHashMap<>();
            map.put("critical", probCritical);
            map.put("noncritical", probNonCritical);
            dataSet.getTransitionProbability().put(country, map);
        }

    }
}
