package data;

import java.util.Map;

public class Geometry {

    private final int geomId;
    private final double length;
    private final String country;
    private final Map<Direction, Map<Integer, Zone>> passingConditions;
    private final Map<Direction, Map<Integer, StationLocation>> stationLocations;

    public Geometry(int geomId, String country, double length, Map<Direction, Map<Integer, Zone>> passingLanes, Map<Direction, Map<Integer, StationLocation>> stationLocations) {
        this.geomId = geomId;
        this.length = length;
        this.country = country;
        this.passingConditions = passingLanes;
        this.stationLocations = stationLocations;
    }

    public int getGeomId() {
        return geomId;
    }

    public double getLength() {
        return length;
    }

    public String getCountry() {
        return country;
    }

    public Map<Direction, Map<Integer, Zone>> getPassingConditions() {
        return passingConditions;
    }

    public Map<Direction, Map<Integer, StationLocation>> getStationLocations() {
        return stationLocations;
    }

}
