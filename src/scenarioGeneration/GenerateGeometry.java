package scenarioGeneration;

import data.DataSet;
import data.Geometry;
import resources.Properties;
import resources.Resources;

import java.util.Map;

public class GenerateGeometry {

    private final DataSet dataSet;
    private Map<Integer, Integer> passingLanes;

    public GenerateGeometry(DataSet dataSet){
        this.dataSet = dataSet;
    }

    public void run(){

        passingLanes = dataSet.getPassingLaneLengths().get(dataSet.getCountry());

        for (int i = 0; i < Resources.INSTANCE.getInt(Properties.NUMBER_GEOMETRIES); i++){

            Geometry gg = new Geometry(i, 100*i);
            dataSet.getGeometryMap().put(i,gg);
        }

    }
}
