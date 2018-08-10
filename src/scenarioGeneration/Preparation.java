package scenarioGeneration;

import data.DataSet;
import input.*;

public class Preparation {

    private final DataSet dataSet;

    public Preparation(DataSet dataSet){
        this.dataSet = dataSet;
    }

    public void run(){
        readPassingLaneDistribution();
        readTransitionDistribution();
        readProbabilityTransition();
        readDriverBehavior();
        readRandomSeeds();
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

    private void readDriverBehavior(){
        new DriverBehaviorReader(dataSet).read();
    }

    private void readRandomSeeds(){
        new RandomSeedReader(dataSet).read();
    }
}
