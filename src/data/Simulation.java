package data;

public class Simulation {

    private final int simulationId;
    private final Traffic traffic;
    private final Geometry geometry;
    private final DriverSeed driverSeed;

    public Simulation(int simulationId, Traffic traffic, Geometry geometry, DriverSeed driverSeed) {
        this.simulationId = simulationId;
        this.traffic = traffic;
        this.geometry = geometry;
        this.driverSeed = driverSeed;
    }

    public void printFile(){
        System.out.println(this.simulationId + " has " + this.geometry.getLength() + " m and " + this.traffic.getVolume() + " cars.");
    }
}
