package data;

import java.util.Map;

public class DriverSeed {

    private final int driverSeedId;
    private final float prec;
    private final String corBKMP;
    private final String randomSeed;

    public DriverSeed(int driverSeedId, float prec, String corBKMP,String randomSeed) {
        this.driverSeedId = driverSeedId;
        this.prec = prec;
        this.corBKMP = corBKMP;
        this.randomSeed = randomSeed;
    }

    public int getDriverSeedId() {
        return driverSeedId;
    }

    public float getPrec() {
        return prec;
    }

    public String getCorBKMP() {
        return corBKMP;
    }

    public String getRandomSeed() {
        return randomSeed;
    }
}
