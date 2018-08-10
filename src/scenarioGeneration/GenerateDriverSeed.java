package scenarioGeneration;

import data.DataSet;
import data.DriverSeed;
import data.TWOPASutil;
import resources.Properties;
import resources.Resources;

public class GenerateDriverSeed {

    private final DataSet dataSet;

    public GenerateDriverSeed(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public void run() {

        int count = 0;
        for (int seed = 0; seed < Resources.INSTANCE.getInt(Properties.NUMBER_OF_SEEDS); seed++) {
            int selectedDriverBehavior = TWOPASutil.selectFromUniformDistribution(dataSet.getcorBKMPMap().size());
            int selectedRandomSeed = TWOPASutil.selectFromUniformDistribution(dataSet.getTwopasRandomSeeds().size());
            DriverSeed dd = new DriverSeed(count++, dataSet.getPrecMap().get(selectedDriverBehavior),
                    dataSet.getcorBKMPMap().get(selectedDriverBehavior), dataSet.getTwopasRandomSeeds().get(selectedRandomSeed));
            dataSet.getDriverSeedMap().put(count, dd);
        }
    }


}
