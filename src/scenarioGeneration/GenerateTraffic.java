package scenarioGeneration;

import data.DataSet;
import data.TWOPASutil;
import data.Traffic;
import resources.Properties;
import resources.Resources;

public class GenerateTraffic {

    private final DataSet dataSet;

    public GenerateTraffic(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public void run() {

        int count = 0;
        for (int percentTrucks : Resources.INSTANCE.getIntArray(Properties.PERCENT_TRUCKS)){
            for (int directionalSplit : Resources.INSTANCE.getIntArray(Properties.DIRECTIONAL_SPLIT)) {
                for (int traffic = Resources.INSTANCE.getIntArray(Properties.DIRECTIONAL_TRAFFIC_FLOW_RANGE)[0];
                     traffic <= Resources.INSTANCE.getIntArray(Properties.DIRECTIONAL_TRAFFIC_FLOW_RANGE)[1];
                     traffic = traffic + Resources.INSTANCE.getInt(Properties.DIRECTIONAL_TRAFFIC_FLOW_STEP)) {
                    int directionalTraffic = (int) TWOPASutil.randommizeFlow(traffic, Resources.INSTANCE.getInt(Properties.DIRECTIONAL_TRAFFIC_FLOW_STEP));
                    int opposingTraffic = (int) directionalTraffic * (100 - directionalSplit) / directionalSplit;
                    float directionalPercentFollowers = obtainEntryPercentFollowers(directionalTraffic);
                    float opposingPercentFollowers = obtainEntryPercentFollowers(opposingTraffic);
                    Traffic tt = new Traffic(count++, directionalTraffic, opposingTraffic, percentTrucks,
                            directionalSplit, directionalPercentFollowers, opposingPercentFollowers);
                    dataSet.getTrafficMap().put(count, tt);
                }
            }
        }
    }


    private float obtainEntryPercentFollowers(int flow){
        //model of percent followers from Brazil

        float a;
        float b;
        float c;
        if (flow < 300) {
            a = 1.084f;
            b = -0.007f;
            c = 0.783f;
        } else if (flow < 500){
            a = 1.119f;
            b = -0.017f;
            c = 0.680f;
        } else if (flow < 700){
            a = 1.162f;
            b = -0.026f;
            c = 0.631f;
        } else if (flow < 900){
            a = 1.193f;
            b = -0.034f;
            c = 0.598f;
        } else if (flow < 1100){
            a = 1.212f;
            b = -0.037f;
            c = 0.594f;
        } else if (flow < 1300){
            a = 1.196f;
            b = -0.034f;
            c = 0.608f;
        } else if (flow < 1500){
            a = 1.211f;
            b = -0.037f;
            c = 0.599f;
        } else {
            a = 1.197f;
            b = -0.033f;
            c = 0.613f;
        }

        return (float)(100 * (1 - a * Math.exp(b * Math.pow(flow, c))));

    }

}
