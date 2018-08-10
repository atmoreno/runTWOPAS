package data;

public class Traffic {

    private final int trafficId;
    private final int directionalTraffic;
    private final int opposingTraffic;
    private final int percentTrucks;
    private final int directionalSplit;
    private final float directionalPercentFollowers;
    private final float opposingPercentFollowers;

    public Traffic(int trafficId, int directionalTraffic, int opposingTraffic, int percentTrucks, int directionalSplit, float directionalPercentFollowers, float opposingPercentFollowers) {
        this.trafficId = trafficId;
        this.directionalTraffic = directionalTraffic;
        this.opposingTraffic = opposingTraffic;
        this.percentTrucks = percentTrucks;
        this.directionalSplit = directionalSplit;
        this.directionalPercentFollowers = directionalPercentFollowers;
        this.opposingPercentFollowers = opposingPercentFollowers;
    }

    public int getTrafficId() {
        return trafficId;
    }

    public int getDirectionalTraffic() {
        return directionalTraffic;
    }

    public int getOpposingTraffic() {
        return opposingTraffic;
    }

    public int getPercentTrucks() {
        return percentTrucks;
    }

    public int getDirectionalSplit() {
        return directionalSplit;
    }

    public float getDirectionalPercentFollowers() {
        return directionalPercentFollowers;
    }

    public float getOpposingPercentFollowers() {
        return opposingPercentFollowers;
    }
}
