package data;

public class DriverSeed {

    private final int driverSeedId;
    private final float prec;
    private final float corr;
    private final int[] kmbp;

    public DriverSeed(int driverSeedId, float prec, float corr, int[] kmbp) {
        this.driverSeedId = driverSeedId;
        this.prec = prec;
        this.corr = corr;
        this.kmbp = kmbp;
    }

    public int getDriverSeedId() {
        return driverSeedId;
    }

    public float getPrec() {
        return prec;
    }

    public float getCorr() {
        return corr;
    }

    public int[] getKmbp() {
        return kmbp;
    }
}
