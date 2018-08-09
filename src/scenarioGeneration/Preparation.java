package scenarioGeneration;

import data.DataSet;
import input.PassingLaneReader;
import input.ProbabilityReader;
import input.TransitionReader;

public class Preparation {

    private final DataSet dataSet;

    public Preparation(DataSet dataSet){
        this.dataSet = dataSet;
    }

    public void run(){
        readPassingLaneDistribution();
        readTransitionDistribution();
        readProbabilityTransition();
    }

    private void readProbabilityTransition() {
        new ProbabilityReader(dataSet).read();
    }


    private void readTransitionDistribution() {
        new TransitionReader(dataSet).read();
    }

    private void readPassingLaneDistribution() {
        new PassingLaneReader(dataSet).read();

    }
}
