package data;

public class Geometry {

    private final int geomId;

    public int getGeomId() {
        return geomId;
    }

    public double getLength() {
        return length;
    }

    private final double length;

    public Geometry(int geomId, double length) {
        this.geomId = geomId;
        this.length = length;
    }



}
