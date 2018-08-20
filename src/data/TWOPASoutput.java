package data;

import resources.Properties;
import resources.Resources;

import java.io.PrintWriter;
import java.util.Map;

public class TWOPASoutput {

    private final int outputId;
    private final String fileName;
    private final Map<Direction, Map<String, String>> trafficOutputs;


    public TWOPASoutput(int outputId, String fileName, Map<Direction, Map<String, String>> trafficOutputs){
        this.outputId = outputId;
        this.fileName = fileName;
        this.trafficOutputs = trafficOutputs;
    }

    public int getOutputId() {
        return outputId;
    }

    public String getFileName() {
        return fileName;
    }

    public Map<Direction, Map<String, String>> getTrafficOutputs() {
        return trafficOutputs;
    }

    public void printSummary(PrintWriter summaryWriter) {

        for (Direction direction : Direction.values()) {
            summaryWriter.print(this.outputId);
            summaryWriter.print(",");
            summaryWriter.print(this.fileName);
            summaryWriter.print(",");
            summaryWriter.print(direction);
            summaryWriter.print(",");
            for (String variable : this.trafficOutputs.get(Direction.ASCENDENT).keySet()) {
                summaryWriter.print(this.trafficOutputs.get(direction).get(variable) + ",");
            }
            summaryWriter.println();
        }

    }
}
