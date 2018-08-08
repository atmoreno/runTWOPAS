package scenarioGeneration;

import data.DataSet;
import data.Geometry;
import data.Traffic;
import resources.Properties;
import resources.Resources;

import java.util.Map;

public class GenerateTraffic {


    private final DataSet dataSet;
    private Map<Integer, Integer> passingLanes;

    public GenerateTraffic(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public void run() {

        passingLanes = dataSet.getPassingLaneLengths().get(dataSet.getCountry());

        for (int i = 0; i < 5; i++) {

            Traffic tt = new Traffic(i, 10 * i);
            dataSet.getTrafficMap().put(i, tt);
        }

    }


}
