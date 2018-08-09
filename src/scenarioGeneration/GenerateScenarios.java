package scenarioGeneration;

import data.*;
import resources.Properties;
import resources.Resources;

public class GenerateScenarios {

    private final DataSet dataSet;

    public GenerateScenarios(String propertiesFile){
        this.dataSet = new DataSet();
        Resources.initializeResources(propertiesFile);
        TWOPASutil.initializeRandomNumber(Resources.INSTANCE.getInt(Properties.RANDOM_SEED));
    }

    public void run() {
        readInputs();
        generateGeometry();
        generateTraffic();
        generateDriverSeed();
        assignSimulation();


    }



    private void assignSimulation() {
        int counter = 0;
        for (Geometry gg : dataSet.getGeometryMap().values()){
            for (Traffic tt : dataSet.getTrafficMap().values()){
                for (DriverSeed dd : dataSet.getDriverSeedMap().values()){
                    Simulation ss = new Simulation(counter,tt, gg, dd);
                    ss.printFile();
                    counter++;
                }
            }
        }
    }

    private void readInputs() {
        new Preparation(dataSet).run();
    }

    private void generateGeometry (){
        new GenerateGeometry(dataSet).run();
    }

    private void generateDriverSeed() {
        new GenerateDriverSeed(dataSet).run();
    }

    private void generateTraffic() {
        new GenerateTraffic(dataSet).run();
    }
}
