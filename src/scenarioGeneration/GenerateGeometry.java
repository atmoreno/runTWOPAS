package scenarioGeneration;

import data.*;
import resources.Properties;
import resources.Resources;
import java.util.LinkedHashMap;
import java.util.Map;

public class GenerateGeometry {

    private final DataSet dataSet;

    public GenerateGeometry(DataSet dataSet){
        this.dataSet = dataSet;
    }

    public void run(){

        int count = 0;
        int countZones = 0;
        int countStations = 0;
        for (String country : Resources.INSTANCE.getArray(Properties.LIST_OF_COUNTRIES)) {
            for (float roadLenght : Resources.INSTANCE.getFloatArray(Properties.ROAD_LENGTH)) {
                for (int i = 0; i < Resources.INSTANCE.getInt(Properties.NUMBER_GEOMETRIES); i++) {
                    Map<Direction, Map<Integer, Zone>> passingConditions = generatePassingConditions(roadLenght, country, countZones);
                    Map<Direction, Map<Integer, StationLocation>> stationLocations = generateStationLocations(passingConditions, countStations, roadLenght);
                    Geometry gg = new Geometry(count++, country, roadLenght, passingConditions, stationLocations);
                    dataSet.getGeometryMap().put(count, gg);
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
        TransitionType transition = selectTransitionType(nonCriticalTransition, country);
        float lengthTransition = TWOPASutil.select(dataSet.getTransitionLengths().get(country).get(transition));
        Map<Integer, Zone> pass1 = new LinkedHashMap<>();
        pass1.put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent, Direction.ASCENDENT, length, lengthTransition,
                PassingCondition.NO_PASSING, TransitionType.translateZoneCondition(transition)));
        passingConditions.put(Direction.ASCENDENT, pass1);
        passingConditions.get(Direction.ASCENDENT).put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent,
                Direction.ASCENDENT, length + lengthTransition, lengthPassingLane, PassingCondition.PASSING_LANE, ZoneCondition.PASSING_LANE));
        Map<Integer, Zone> pass2 = new LinkedHashMap<>();
        pass2.put(sequenceDescendent++,new Zone(countZones++, sequenceDescendent, Direction.DESCENDENT,
                0,lengthPassingLane + lengthTransition, PassingCondition.NO_PASSING, ZoneCondition.SINGLE_LANE));
        passingConditions.put(Direction.DESCENDENT,pass2);
        nonCriticalTransition = false;
        length = length + lengthTransition + lengthPassingLane;

        //next iterations
        while (!exceedingLength) {
            lengthPassingLane = TWOPASutil.select(dataSet.getPassingLaneLengths().get(country));
            transition = selectTransitionType(nonCriticalTransition, country);
            lengthTransition = TWOPASutil.select(dataSet.getTransitionLengths().get(country).get(transition));
            if (length + lengthTransition + lengthPassingLane < roadLength){
                if (nonCriticalTransition) {
                    //passing lane starts in my direction of analysis
                    passingConditions.get(Direction.ASCENDENT).put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent,
                            Direction.ASCENDENT, length, lengthTransition, PassingCondition.NO_PASSING, TransitionType.translateZoneCondition(transition)));
                    passingConditions.get(Direction.ASCENDENT).put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent,
                            Direction.ASCENDENT, length + lengthTransition, lengthPassingLane, PassingCondition.PASSING_LANE,ZoneCondition.PASSING_LANE));
                    passingConditions.get(Direction.DESCENDENT).put(sequenceDescendent++,new Zone(countZones++, sequenceDescendent,
                            Direction.DESCENDENT, length, lengthPassingLane + lengthTransition, PassingCondition.NO_PASSING, ZoneCondition.SINGLE_LANE));
                    nonCriticalTransition = false;
                } else {
                    //passing lane starts in the other direction
                    passingConditions.get(Direction.ASCENDENT).put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent,
                            Direction.ASCENDENT, length, lengthTransition + lengthPassingLane, PassingCondition.NO_PASSING, ZoneCondition.SINGLE_LANE));
                    passingConditions.get(Direction.DESCENDENT).put(sequenceDescendent++,new Zone(countZones++, sequenceDescendent,
                            Direction.DESCENDENT, length , lengthTransition, PassingCondition.NO_PASSING, TransitionType.translateZoneCondition(transition)));
                    passingConditions.get(Direction.DESCENDENT).put(sequenceDescendent++,new Zone(countZones++, sequenceDescendent,
                            Direction.DESCENDENT, length + lengthTransition, lengthPassingLane, PassingCondition.PASSING_LANE, ZoneCondition.PASSING_LANE));
                    nonCriticalTransition = true;
                }
                length = length + lengthTransition + lengthPassingLane;
            } else {
                exceedingLength = true;
                passingConditions.get(Direction.ASCENDENT).put(sequenceAscendent++,new Zone(countZones++, sequenceAscendent,
                        Direction.ASCENDENT, length, roadLength - length, PassingCondition.NO_PASSING, TransitionType.translateZoneCondition(transition)));
                passingConditions.get(Direction.DESCENDENT).put(sequenceDescendent++,new Zone(countZones++, sequenceDescendent,
                        Direction.DESCENDENT, roadLength, roadLength - length, PassingCondition.NO_PASSING, TransitionType.translateZoneCondition(transition)));
            }
        }

        //reorder passing restrictions in the descendent direction
        for (Zone zz : passingConditions.get(Direction.DESCENDENT).values()){
            zz.setSequence(passingConditions.get(Direction.DESCENDENT).size() + 1 - zz.getSequence());
        }
        return passingConditions;
    }

    private TransitionType selectTransitionType(boolean nonCriticalTransition, String country) {
        TransitionType transition;
        if (nonCriticalTransition){
            transition = TWOPASutil.select(dataSet.getTransitionProbability().get(country).get("noncritical"));
        } else {
            transition  = TWOPASutil.select(dataSet.getTransitionProbability().get(country).get("critical"));
        }
        return transition;
    }


    private Map<Direction, Map<Integer, StationLocation>> generateStationLocations(Map<Direction, Map<Integer, Zone>> passingConditions, int countStations, float roadLength) {

        Map<Direction, Map<Integer, StationLocation>> stationLocations = new LinkedHashMap<>();

        //Direction ASCENDENT
        Map<Integer, StationLocation> direction1 = new LinkedHashMap<>();
        int sequence = 0;
        direction1.put(sequence++, new StationLocation(countStations++,sequence,Direction.ASCENDENT,300,"1: Start"));
        for (Zone zz : passingConditions.get(Direction.ASCENDENT).values()) {
            if (zz.getPassing().equals(PassingCondition.PASSING_LANE)) {
                direction1.put(sequence++, new StationLocation(countStations++,sequence,Direction.ASCENDENT,zz.getStart(),"1: " + zz.getSequence()));
            }
        }
        direction1.put(sequence++, new StationLocation(countStations++,sequence,Direction.ASCENDENT,roadLength - 300,"1: End"));
        stationLocations.put(Direction.ASCENDENT, direction1);

        //Direction DESCENDENT
        Map<Integer, StationLocation> direction2 = new LinkedHashMap<>();
        sequence = 0;
        direction2.put(sequence++, new StationLocation(countStations++,sequence,Direction.DESCENDENT,300,"2: End"));
        for (Zone zz : passingConditions.get(Direction.DESCENDENT).values()) {
            if (zz.getPassing().equals(PassingCondition.PASSING_LANE)) {
                direction2.put(sequence++, new StationLocation(countStations++,sequence,Direction.DESCENDENT,zz.getStart(),"2: " + zz.getSequence()));
            }
        }
        direction2.put(sequence++, new StationLocation(countStations++,sequence,Direction.DESCENDENT,roadLength - 300,"2: Start"));
        stationLocations.put(Direction.DESCENDENT, direction2);

        //reorder STATIONS in the descendent direction
        for (StationLocation st : stationLocations.get(Direction.DESCENDENT).values()){
            st.setSequence(stationLocations.get(Direction.DESCENDENT).size() + 1 - st.getSequence());
        }
        return stationLocations;
    }
}
