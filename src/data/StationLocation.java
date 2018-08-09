package data;

public class StationLocation {

    private final int stationId;
    private int sequence;
    private final Direction direction;
    private final float start;
    private final String description;

    public StationLocation(int stationId, int sequence, Direction direction, float start, String description) {
        this.stationId = stationId;
        this.sequence = sequence;
        this.direction = direction;
        this.start = start;
        this.description = description;
    }


    public int getStationId() {
        return stationId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getStart() {
        return start;
    }

    public String getDescription() {
        return description;
    }
}
