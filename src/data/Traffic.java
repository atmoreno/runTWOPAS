package data;

public class Traffic {

    private final int trafficId;

    public int getTrafficId() {
        return trafficId;
    }

    public int getVolume() {
        return volume;
    }

    private final int volume;

    public Traffic(int trafficId, int volume) {
        this.trafficId = trafficId;
        this.volume = volume;
    }
}
