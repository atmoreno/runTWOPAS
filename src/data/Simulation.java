package data;

import resources.Properties;
import resources.Resources;

import java.io.PrintWriter;

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

        System.out.println("    " + this.simulationId + ": writing INP file");
        String file = Resources.INSTANCE.getString(Properties.BASE_DIRECTORY) + "/" + Resources.INSTANCE.getString(Properties.OUTPUT_DIRECTORY) + this.simulationId+ "_t.INP";
        PrintWriter pwh = TWOPASutil.openFileForSequentialWriting(file, false);

        //
        pwh.println("  1 Geometric scenario: " + this.geometry.getGeomId() + "; traffic scenario: " + this.traffic.getTrafficId() + "; seed " + this.driverSeed.getDriverSeedId());
        pwh.println("     Running TWOPAS");
        //simulation characteristics---------
        pwh.println("1  999   1     10.00     60.00       1.0       5.0      -1.0 ");
        int stations = this.geometry.getStationLocations().get(Direction.ASCENDENT).size();
        int stations2 = this.geometry.getStationLocations().get(Direction.DESCENDENT).size();
        String stationLine = "2    0       32808.4         " + stations + "         " + stations2 + "       0.0    3000.0      0.55";
        pwh.println(stationLine);
        //traffic volume, entering platoons and composition-----
        pwh.println("3 0106.0 17.00     10046.0 08.00     1");
        pwh.println("410.02600.00810.02650.03910.00000.00000.00000.00000.01180.04240.00780.01960.8154");
        pwh.println("520.02020.00310.02930.04640.00000.00000.00000.00000.02460.04900.02710.03530.7556");
        pwh.println("6 100.84                        0.62931.6293  0.73  0.90");
        pwh.println("6   9.79  5.83 11.83  7.06  5.83 14.08");
        pwh.println("6 -10.43-13.62  0.00-20.15-13.62 -5.60");
        pwh.println("71   150   150   150   150   150   150   150   150   150   150   150   150   150");
        pwh.println("82   150   150   150   150   150   150   150   150   150   150   150   150   150");
        pwh.println("9");
        pwh.println("10  0.88  1.84  0.26  1.40  1.79  1.78  1.75  1.91  1.93  1.74  1.44");
        pwh.println("RN                    93093239  24248117  18846467  14143019  23565221");
        //desired speeds----------------

        //

        //random numbers----------


        //passing conditions---------
        for (Zone zz : this.geometry.getPassingLanes().get(Direction.ASCENDENT).values()) {
            pwh.println(writePassingZoneLine(zz));
        }
        for (Zone zz : this.geometry.getPassingLanes().get(Direction.DESCENDENT).values()) {
            pwh.println(writePassingZoneLine(zz));
        }
        //vehicle characteristics---------
        pwh.println("VC  1                   191.10    787.80      49.2       1.0     1.000");
        pwh.println("VC  2                   196.40    988.60      61.5       1.0     1.000");
        pwh.println("VC  3                   202.10    919.30      61.5       1.0     1.000");
        pwh.println("VC  4                   184.40    346.20      24.6       1.0     1.000");
        pwh.println("VC  5              1      9.00    110.00      36.0");
        pwh.println("VC  6                    11.00    115.00      28.0");
        pwh.println("VC  7                    12.50    120.00      21.0");
        pwh.println("VC  8                    14.00    125.00      32.0");
        pwh.println("VC  9                     8.00    112.80      15.1");
        pwh.println("VC 10                     9.02    117.80      14.8");
        pwh.println("VC 11                     7.18    121.10      15.4");
        pwh.println("VC 12                     9.52    127.00      14.1");
        pwh.println("VC 13                    10.70    142.70      13.1");
        //station locations----------
        for (StationLocation st : this.geometry.getStationLocations().get(Direction.ASCENDENT).values()){
            pwh.println(writeStationLocationLine(st));
        }
        for (StationLocation st : this.geometry.getStationLocations().get(Direction.DESCENDENT).values()){
            pwh.println(writeStationLocationLine(st));
        }
        pwh.println("  -1");

        pwh.close();
    }




    private String writePassingZoneLine(Zone zz){

        String line = "";
        if (zz.getDirection().equals(Direction.ASCENDENT)){
            line = "PS  1";
        } else {
            line = "PS  2";
        }
        //Number of zones
        String numberZonesAscendent = "   " + this.geometry.getPassingLanes().get(Direction.ASCENDENT).size();
        if (this.geometry.getPassingLanes().get(Direction.ASCENDENT).size() < 10){
            numberZonesAscendent =  "    " + this.geometry.getPassingLanes().get(Direction.ASCENDENT).size();
        }
        String numberZonesDescendent = "   " + this.geometry.getPassingLanes().get(Direction.DESCENDENT).size();
        if (this.geometry.getPassingLanes().get(Direction.DESCENDENT).size() < 10){
            numberZonesDescendent =  "    " + this.geometry.getPassingLanes().get(Direction.DESCENDENT).size();
        }
        line = line + numberZonesAscendent + numberZonesDescendent;
        //Sequence
        String sequence;
        if (zz.getSequence() < 10) {
            sequence = "    " + zz.getSequence();
        } else {
            sequence = "   " + zz.getSequence();
        }
        line = line + sequence;
        //Location
        String start;
        float length = (float) Math.round(TWOPASutil.convertmToFeet(zz.getStart()) * 10) / 10;
        if (length == 0){
            start = "       " + length + "     ";
        } else if (length < 10){
            start = "       " + length + "     ";
        } else if (length < 100){
            start = "      " + length + "     ";
        } else if (length < 1000){
            start = "     " + length + "     ";
        } else if (length < 10000){
            start = "    " + length + "     ";
        } else if (length < 100000){
            start = "   " + length + "     ";
        } else {
            start = "   " + length + "     ";
        }
        line = line + start;
        //passing condition
        String passing;
        if (zz.getPassing().equals(PassingCondition.NO_PASSING)){
            passing = "-1.00          ";
        } else if (zz.getPassing().equals(PassingCondition.PASSING_LANE)) {
            passing = " 2.00         2";
        } else {
            passing = " 1.00          ";
        }
        line = line + passing;

        return line;

    }

    private String writeStationLocationLine(StationLocation st){
        String line = "SL";
        //Sequence
        String sequence;
        if (st.getSequence() < 10) {
            sequence = "     " + st.getSequence();
        } else {
            sequence = "    " + st.getSequence();
        }
        line = line + sequence;
        //Direction and results to print
        String direction;
        if (st.getDirection().equals(Direction.ASCENDENT)){
            direction = "   1   1   2";
        } else {
            direction = "   2   1   2";
        }
        line = line + direction;
        //Location
        String start;
        float length = (float) Math.round(TWOPASutil.convertmToFeet(st.getStart()) * 10) / 10;
        if (length == 0){
            start = "       " + length;
        } else if (length < 10){
            start = "       " + length;
        } else if (length < 100){
            start = "      " + length;
        } else if (length < 1000){
            start = "     " + length;
        } else if (length < 10000){
            start = "    " + length;
        } else if (length < 100000){
            start = "   " + length;
        } else {
            start = "   " + length;
        }
        line = line + start;
        //Description
        String description = "          " + st.getDescription();
        line = line + description;

        return line;
    }

}
