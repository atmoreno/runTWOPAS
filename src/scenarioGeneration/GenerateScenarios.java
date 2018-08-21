package scenarioGeneration;

import data.*;
import resources.Properties;
import resources.Resources;

import java.io.PrintWriter;

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
        String file = Resources.INSTANCE.getString(Properties.BASE_DIRECTORY) + "/" + Resources.INSTANCE.getString(Properties.LIST_OF_SCENARIOS);
        PrintWriter listOfINPfiles = new PrintWriter(TWOPASutil.openFileForSequentialWriting(file, false));
        String fileSummary = Resources.INSTANCE.getString(Properties.BASE_DIRECTORY)+  "/" + Resources.INSTANCE.getString(Properties.SCENARIO_SUMMARY);
        PrintWriter summaryOfScenarios = new PrintWriter(TWOPASutil.openFileForSequentialWriting(fileSummary, false));
        summaryOfScenarios.println(obtainHeaderSummary());
        for (Geometry gg : dataSet.getGeometryMap().values()){
            for (Traffic tt : dataSet.getTrafficMap().values()){
                for (DriverSeed dd : dataSet.getDriverSeedMap().values()){
                    Simulation ss = new Simulation(counter++,tt, gg, dd);
                    ss.printINPfile(listOfINPfiles, counter);
                    ss.printSummary(summaryOfScenarios);
                }
            }
        }
        listOfINPfiles.close();
        summaryOfScenarios.close();
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

    private String obtainHeaderSummary(){
        String header = "id,inp,out,country,roadLength,geometry,percentTrucks,directionalSplit,randomSeed,directionalVolume,opposingVolume,";
        for (Direction direction : Direction.values()){
            for (ZoneCondition zoneCondition : ZoneCondition.values()){
                header += "percentLength"+ direction + "_" + zoneCondition + ",";
                header += "count"+ direction + "_" + zoneCondition + ",";
                header += "totalLength"+ direction + "_" + zoneCondition + ",";
                header += "averageLength"+ direction + "_" + zoneCondition + ",";
            }
        }
        header += "PF1,PF2";
        return header;
    }
}
