package scenarioGeneration;

import data.DataSet;
import data.DriverSeed;

import java.util.Map;

public class GenerateDriverSeed {

    private final DataSet dataSet;
    private Map<Integer, Integer> passingLanes;

    public GenerateDriverSeed(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public void run() {

        for (int i = 0; i < 1; i++) {

            int[] km = new int[2];
            km[0] = 1;
            km[1] = 2;
            DriverSeed dd = new DriverSeed(i, 100 * i, 2, km);
            dataSet.getDriverSeedMap().put(i, dd);
        }

    }
}
