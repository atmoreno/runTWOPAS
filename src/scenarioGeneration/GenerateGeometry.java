package scenarioGeneration;

import com.sun.javafx.scene.control.behavior.TwoLevelFocusBehavior;
import data.*;
import resources.Properties;
import resources.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class GenerateGeometry {

    private final DataSet dataSet;

    public GenerateGeometry(DataSet dataSet){
        this.dataSet = dataSet;
    }

    public void run(){

        int count = 1;
        int countZones = 0;
        int countStations = 0;
        for (String country : Resources.INSTANCE.getArray(Properties.LIST_OF_COUNTRIES)) {
            for (float roadLenght : Resources.INSTANCE.getFloatArray(Properties.ROAD_LENGTH)) {
                for (int i = 0; i < Resources.INSTANCE.getInt(Properties.NUMBER_GEOMETRIES); i++) {
                    Map<Direction, Map<Integer, Zone>> passingConditions = generatePassingConditions(roadLenght, country, countZones);
                    Map<Direction, Map<Integer, StationLocation>> stationLocations = generateStationLocations(passingConditions, countStations, roadLenght);
                    Geometry gg = new Geometry(count, country, roadLenght, passingConditions, stationLocations);
                    dataSet.getGeometryMap().put(count, gg);
                    count++;
                }
            }
        }

    }



    private Map<Direction, Map<Integer, Zone>> generatePassingConditions(float roadLength, String country, int countZones){

        Map<Direction, Map<Integer, Zone>> passingConditions = new LinkedHashMap<>();
        float length = 0;
        boolean exceedingLength = false;
        boolean nonCriticalTransition = true;
        int sequenceAscendent = 0;
        int sequenceDescendent = 0;

        //first iteration
        float lengthPassingLane = TWOPASutil.select(dataSet.getPassingLaneLengths().get(country));
        float lengthTransition = selectTransitionLength(nonCriticalTransition, country);
        Map<Integer, Zone> pass1 = new LinkedHashMap<>();
        pass1.put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent,
                Direction.ASCENDENT, length, lengthTransition, PassingCondition.NO_PASSING));
        passingConditions.put(Direction.ASCENDENT, pass1);
        passingConditions.get(Direction.ASCENDENT).put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent,
                Direction.ASCENDENT, length + lengthTransition, lengthPassingLane, PassingCondition.PASSING_LANE));
        Map<Integer, Zone> pass2 = new LinkedHashMap<>();
        pass2.put(sequenceDescendent++,new Zone(countZones++, sequenceDescendent,
                Direction.DESCENDENT, 0,lengthPassingLane + lengthTransition, PassingCondition.NO_PASSING));
        passingConditions.put(Direction.DESCENDENT,pass2);
        nonCriticalTransition = false;
        length = length + lengthTransition + lengthPassingLane;

        //next iterations
        while (!exceedingLength) {
            lengthPassingLane = TWOPASutil.select(dataSet.getPassingLaneLengths().get(country));
            lengthTransition = selectTransitionLength(nonCriticalTransition, country);
            if (length + lengthTransition + lengthPassingLane < roadLength){
                if (nonCriticalTransition) {
                    //passing lane starts in my direction of analysis
                    passingConditions.get(Direction.ASCENDENT).put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent,
                            Direction.ASCENDENT, length, lengthTransition, PassingCondition.NO_PASSING));
                    passingConditions.get(Direction.ASCENDENT).put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent,
                            Direction.ASCENDENT, length + lengthTransition, lengthPassingLane, PassingCondition.PASSING_LANE));
                    passingConditions.get(Direction.DESCENDENT).put(sequenceDescendent++,new Zone(countZones++, sequenceDescendent,
                            Direction.DESCENDENT, length, lengthPassingLane + lengthTransition, PassingCondition.NO_PASSING));
                    nonCriticalTransition = false;
                } else {
                    //passing lane starts in the other direction
                    passingConditions.get(Direction.ASCENDENT).put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent,
                            Direction.ASCENDENT, length, lengthTransition + lengthPassingLane, PassingCondition.NO_PASSING));
                    passingConditions.get(Direction.DESCENDENT).put(sequenceDescendent++,new Zone(countZones++, sequenceDescendent,
                            Direction.DESCENDENT, length , lengthTransition, PassingCondition.NO_PASSING));
                    passingConditions.get(Direction.DESCENDENT).put(sequenceDescendent++,new Zone(countZones++, sequenceDescendent,
                            Direction.DESCENDENT, length + lengthTransition, lengthPassingLane, PassingCondition.PASSING_LANE));
                    nonCriticalTransition = true;
                }
                length = length + lengthTransition + lengthPassingLane;
            } else {
                exceedingLength = true;
                passingConditions.get(Direction.ASCENDENT).put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent,
                        Direction.ASCENDENT, length, roadLength - length, PassingCondition.NO_PASSING));
                passingConditions.get(Direction.DESCENDENT).put(sequenceDescendent++,new Zone(countZones++, sequenceDescendent,
                        Direction.DESCENDENT, roadLength, roadLength - length, PassingCondition.NO_PASSING));
            }
        }

        //reorder passing restrictions in the opposing direction
        for (Zone zz : passingConditions.get(Direction.DESCENDENT).values()){
            zz.setSequence(passingConditions.get(Direction.DESCENDENT).size() + 1 - zz.getSequence());
        }
        return passingConditions;
    }

    private float selectTransitionLength(boolean nonCriticalTransition, String country) {
        float length = 0;
        TransitionType transition;
        if (nonCriticalTransition){
            transition = TWOPASutil.select(dataSet.getTransitionProbability().get(country).get("noncritical"));
        } else {
            transition  = TWOPASutil.select(dataSet.getTransitionProbability().get(country).get("critical"));
        }
        length = TWOPASutil.select(dataSet.getTransitionLengths().get(country).get(transition));
        return length;
    }

    private Map<Direction, Map<Integer, StationLocation>> generateStationLocations(Map<Direction, Map<Integer, Zone>> passingConditions, int countStations, float roadLength) {
        Map<Direction, Map<Integer, StationLocation>> stationLocations = new LinkedHashMap<>();
        Map<Integer, StationLocation> direction1 = new LinkedHashMap<>();
        Map<Integer, StationLocation> direction2 = new LinkedHashMap<>();
        int sequence = 0;
        direction1.put(sequence++, new StationLocation(countStations++,sequence,Direction.ASCENDENT,0,"1: Start"));
        for (Zone zz : passingConditions.get(Direction.ASCENDENT).values()) {
            if (zz.getPassing().equals(PassingCondition.PASSING_LANE)) {
                direction1.put(sequence++, new StationLocation(countStations++,sequence,Direction.ASCENDENT,zz.getStart(),"1: " + zz.getSequence()));
            }
        }
        direction1.put(sequence++, new StationLocation(countStations++,sequence,Direction.ASCENDENT,roadLength,"1: End"));
        sequence = 0;
        direction2.put(sequence++, new StationLocation(countStations++,sequence,Direction.DESCENDENT,0,"2: End"));
        for (Zone zz : passingConditions.get(Direction.DESCENDENT).values()) {
            if (zz.getPassing().equals(PassingCondition.PASSING_LANE)) {
                direction2.put(sequence++, new StationLocation(countStations++,sequence,Direction.DESCENDENT,zz.getStart(),"2: " + zz.getSequence()));
            }
        }
        direction2.put(sequence++, new StationLocation(countStations++,sequence,Direction.DESCENDENT,roadLength,"2: Start"));
        stationLocations.put(Direction.ASCENDENT, direction1);
        stationLocations.put(Direction.DESCENDENT, direction2);

        //reorder STATIONS in the opposing direction
        for (StationLocation st : stationLocations.get(Direction.DESCENDENT).values()){
            st.setSequence(stationLocations.get(Direction.DESCENDENT).size() + 1 - st.getSequence());
        }
        return stationLocations;
    }
}
