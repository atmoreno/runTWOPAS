package data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataSet {

    private final Map<String, Map<Integer, Integer>> passingLaneLengths = new LinkedHashMap<>();
    private final Map<String, Map<String, Map <Integer, Integer>>> transitionLengths = new LinkedHashMap<>();
    private final Map<String, Map<Integer, Integer>> intersectionLengths = new LinkedHashMap<>();
    private final Map<String, Map<String, Float>> transitionProbability = new LinkedHashMap<>();
    private String country;

    private Map<Integer, Geometry> geometryMap = new LinkedHashMap<>();
    private Map<Integer, Traffic> trafficMap = new LinkedHashMap<>();
    private Map<Integer, DriverSeed> driverSeedMap = new LinkedHashMap<>();
    private Map<Integer, Simulation> simulationMap = new LinkedHashMap<>();


    public Map<String, Map<Integer, Integer>> getPassingLaneLengths() {
        return passingLaneLengths;
    }
    public Map<String, Map<String, Map<Integer, Integer>>> getTransitionLengths() {
        return transitionLengths;
    }
    public Map<String, Map<Integer, Integer>> getIntersectionLengths() {
        return intersectionLengths;
    }

    public Map<String, Map<String, Float>> getTransitionProbability() {
        return transitionProbability;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public Map<Integer, Geometry> getGeometryMap() {
        return geometryMap;
    }

    public Map<Integer, Traffic> getTrafficMap() {
        return trafficMap;
    }

    public Map<Integer, DriverSeed> getDriverSeedMap() {
        return driverSeedMap;
    }

    public Map<Integer, Simulation> getSimulationMap() {
        return simulationMap;
    }
}
