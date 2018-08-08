package scenarioGeneration;

import data.DataSet;
import data.DriverSeed;
import data.Traffic;
import resources.Properties;
import resources.Resources;

import java.util.Map;

public class GenerateDriverSeed {

    private final DataSet dataSet;
    private Map<Integer, Integer> passingLanes;

    public GenerateDriverSeed(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public void run() {

        for (int i = 0; i < 1; i++) {

            DriverSeed dd = new DriverSeed(i, 100 * i);
            dataSet.getDriverSeedMap().put(i, dd);
        }

    }
}
