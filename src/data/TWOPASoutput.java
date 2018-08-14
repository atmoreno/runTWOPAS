package data;

import java.util.Map;

public class TWOPASoutput {

    private final int outputId;
    private final double length;
    private final String country;
    private final Map<String, String> trafficPerformance;


    public TWOPASoutput(int outputId, String country, double length, Map<String, String> trafficPerformance){
        this.outputId = outputId;
        this.length = length;
        this.country = country;
        this.trafficPerformance = trafficPerformance;
    }
}
