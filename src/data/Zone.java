package data;

public class Zone {

    private final int zoneId;
    private final float start;
    private final float length;
    private final Direction direction;
    private final PassingCondition passing;
    private int sequence;

    public Zone(int zoneId, int sequence,Direction direction, float start, float length, PassingCondition passing) {
        this.zoneId = zoneId;
        this.sequence = sequence;
        this.start = start;
        this.length = length;
        this.direction = direction;
        this.passing = passing;
    }

    public int getZoneId() {
        return zoneId;
    }

    public float getStart() {
        return start;
    }

    public float getLength() {
        return length;
    }

    public Direction getDirection() {
        return direction;
    }

    public PassingCondition getPassing() {
        return passing;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
