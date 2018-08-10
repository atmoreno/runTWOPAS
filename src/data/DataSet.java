package data;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataSet {

    private final Map<String, Map<Integer, Float>> passingLaneLengths = new LinkedHashMap<>();
    private final Map<String, Map<TransitionType, Map<Integer, Float>>> transitionLengths = new LinkedHashMap<>();
    private final Map<String, Map<String, Map<TransitionType, Float>>> transitionProbability = new LinkedHashMap<>();
    private final Map<Integer, String> corBKMPMap = new LinkedHashMap<>();
    private final Map<Integer, Float> precMap = new LinkedHashMap<>();
    private final Map<Integer, String> twopasRandomSeeds = new LinkedHashMap<>();
    private String country;

    private Map<Integer, Geometry> geometryMap = new LinkedHashMap<>();
    private Map<Integer, Traffic> trafficMap = new LinkedHashMap<>();
    private Map<Integer, DriverSeed> driverSeedMap = new LinkedHashMap<>();
    private Map<Integer, Simulation> simulationMap = new LinkedHashMap<>();


    public Map<String, Map<Integer, Float>> getPassingLaneLengths() {
        return passingLaneLengths;
    }
    public Map<String, Map<TransitionType, Map<Integer, Float>>> getTransitionLengths() {
        return transitionLengths;
    }
    public Map<String, Map<String, Map<TransitionType, Float>>> getTransitionProbability() {
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

    public Map<Integer, String> getcorBKMPMap() {
        return corBKMPMap;
    }

    public Map<Integer, Float> getPrecMap() {
        return precMap;
    }

    public void setGeometryMap(Map<Integer, Geometry> geometryMap) {
        this.geometryMap = geometryMap;
    }

    public void setTrafficMap(Map<Integer, Traffic> trafficMap) {
        this.trafficMap = trafficMap;
    }

    public void setDriverSeedMap(Map<Integer, DriverSeed> driverSeedMap) {
        this.driverSeedMap = driverSeedMap;
    }

    public void setSimulationMap(Map<Integer, Simulation> simulationMap) {
        this.simulationMap = simulationMap;
    }

    public Map<Integer, String> getTwopasRandomSeeds() {
        return twopasRandomSeeds;
    }

}
