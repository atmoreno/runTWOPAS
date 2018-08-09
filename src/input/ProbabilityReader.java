package input;

import data.DataSet;
import data.TransitionType;
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
            dataSet.getTransitionProbability().get(country).get("critical").put(TransitionType.CRITICAL, probCritical);
            dataSet.getTransitionProbability().get(country).get("critical").put(TransitionType.INTERSECTION, 1 - probCritical);
            dataSet.getTransitionProbability().get(country).get("noncritical").put(TransitionType.NONCRITICAL, probNonCritical);
            dataSet.getTransitionProbability().get(country).get("noncritical").put(TransitionType.INTERSECTION, 1 - probNonCritical);
        } else {
            Map<TransitionType, Float> mapC = new LinkedHashMap<>();
            mapC.put(TransitionType.CRITICAL, probCritical);
            mapC.put(TransitionType.INTERSECTION, 1 - probCritical);
            Map<TransitionType, Float> mapNC = new LinkedHashMap<>();
            mapNC.put(TransitionType.NONCRITICAL, probNonCritical);
            mapNC.put(TransitionType.INTERSECTION, 1 - probNonCritical);
            Map<String, Map<TransitionType, Float>> outerMap = new LinkedHashMap<>();
            outerMap.put("critical", mapC);
            outerMap.put("noncritical", mapNC);
            dataSet.getTransitionProbability().put(country, outerMap);
        }

    }
}
