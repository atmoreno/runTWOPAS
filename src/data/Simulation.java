package data;

import resources.Properties;
import resources.Resources;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

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


    public void printINPfile(PrintWriter listWriter, int counter){

        if (TWOPASutil.isPowerOfTwo(counter)) {
            System.out.println("    " + this.simulationId + ": writing INP file");
        }
        String file = Resources.INSTANCE.getString(Properties.BASE_DIRECTORY) + "/" + Resources.INSTANCE.getString(Properties.OUTPUT_DIRECTORY) +
                "G" + this.geometry.getGeomId() + "T" + this.traffic.getTrafficId() + "D" +  this.driverSeed.getDriverSeedId() + "_" +
                this.geometry.getCountry() + "l" + this.geometry.getLength() + "p" + this.geometry.getGeomId() +
                "t" + this.traffic.getPercentTrucks()+ "r" + this.traffic.getDirectionalSplit() + "v" + this.traffic.getDirectionalTrafficClass() +
                ".INP";
        listWriter.println(file);
        PrintWriter pwh = TWOPASutil.openFileForSequentialWriting(file, false);


        pwh.println("   1 2+1 scenario for country: " + this.geometry.getCountry());
        pwh.println("    Running TWOPAS with geometry " + this.geometry.getGeomId() + "; traffic scenario " + this.traffic.getTrafficId() + " and seed " + this.driverSeed.getDriverSeedId() + ".");
        pwh.println(writeRecord1());
        pwh.println(writeRecord2());
        pwh.println(writeRecord3());
        pwh.println("41" + writeRecord4or5(this.traffic.getPercentTrucks()));
        pwh.println("52" + writeRecord4or5(this.traffic.getPercentTrucks()));
        for (int i = 0; i < 3; i++) {
            pwh.println(writeRecord6(i));
        }
        pwh.println("71   150   150   150   150   150   150   150   150   150   150   150   150   150");
        pwh.println("82   150   150   150   150   150   150   150   150   150   150   150   150   150");
        pwh.println("9");
        pwh.println(this.driverSeed.getCorBKMP());
        pwh.println(this.driverSeed.getRandomSeed());
        for (Zone zz : this.geometry.getPassingConditions().get(Direction.ASCENDENT).values()) {
            pwh.println(writePassingZoneLine(zz));
        }
        for (Zone zz : this.geometry.getPassingConditions().get(Direction.DESCENDENT).values()) {
            pwh.println(writePassingZoneLine(zz));
        }
        for (int i = 0; i < 13; i++){
            pwh.println(writeRecordVC(i));
        }
        for (StationLocation st : this.geometry.getStationLocations().get(Direction.ASCENDENT).values()){
            pwh.println(writeStationLocationLine(st));
        }
        for (StationLocation st : this.geometry.getStationLocations().get(Direction.DESCENDENT).values()){
            pwh.println(writeStationLocationLine(st));
        }
        pwh.println();
        pwh.println("  -1");

        pwh.close();
    }


    private String writeRecord1(){
        //simulation characteristics
        String record1 = "1 9999   1" +
                TWOPASutil.printWithBlanks(10, Resources.INSTANCE.getInt(Properties.SIMULATION_WARM_UP), 2)+
                TWOPASutil.printWithBlanks(10, Resources.INSTANCE.getInt(Properties.SIMULATION_DURATION), 2)+
                "       1.0       5.0      -1.0 ";
        return record1;
    }


    private String writeRecord2(){
        //road length, sight distances, number of station locations, PREC
        //default nominal sight distances
        String record2 = "2    0" +
                TWOPASutil.printWithBlanks(14, TWOPASutil.convertMetersToFeet(this.geometry.getLength()),1)+
                TWOPASutil.printWithBlanks(10, this.geometry.getStationLocations().get(Direction.ASCENDENT).size()) +
                TWOPASutil.printWithBlanks(10, this.geometry.getStationLocations().get(Direction.DESCENDENT).size()) +
                "       0.0    3000.0" +
                TWOPASutil.printWithBlanks(10, this.driverSeed.getPrec(),2);
        return record2;
    }

    private String writeRecord3(){
        //traffic volumes, percent followers
        //default type of preceding segment: level, tangent alignment
        String record3 = "3 " +
                TWOPASutil.printWithBlanks(6, this.traffic.getDirectionalTraffic(),1) +
                TWOPASutil.printWithBlanks(6, this.traffic.getDirectionalPercentFollowers(), 2) +
                "     1" +
                TWOPASutil.printWithBlanks(6, this.traffic.getDirectionalTraffic(),1) +
                TWOPASutil.printWithBlanks(6, this.traffic.getOpposingPercentFollowers(), 2) +
                "     1";
        return record3;
    }

    private String writeRecord4or5(int percentTrucks){
        //traffic composition
        String record = "";
        if (percentTrucks == 0) {
            record = "0.00000.00000.00000.00000.00000.00000.00000.00000.15000.10000.15000.30000.3000";
        } else if (percentTrucks == 10) {
            record = "0.07500.00000.00000.02500.00000.00000.00000.00000.13500.09000.13500.27000.2700";
        } else if (percentTrucks == 20) {
            record = "0.15000.00000.00000.05000.00000.00000.00000.00000.12000.08000.12000.24000.2400";
        } else if (percentTrucks == 30) {
            record = "0.22500.00000.00000.07500.00000.00000.00000.00000.10500.07000.10500.21000.2100";
        } else {
            throw new RuntimeException("Undefined percent of trucks " + percentTrucks);
        }
        return record;
    }


    private String writeRecord6(int number){
        //desired speeds
        String record6 = "";
        if (number == 0){
            record6 = "6  82.29                        0.62931.6293  0.73  0.90";
        } else if (number == 1){
            record6 = "6   3.92  5.83  9.20  3.10  5.83 10.39 ";
        } else {
            record6 = "6  -7.84  4.92  0.00 -6.47  4.92  0.73 ";
        }
        return record6;
    }

    private String writeRecord10(){
        String record10 = "10  0.88  1.84  0.26  1.40  1.79  1.78  1.75  1.91  1.93  1.74  1.44";
        return record10;
    }

    private String writeRecordRN(){
        String record10 = "RN                    93093239  24248117  18846467  14143019  23565221";
        return record10;
    }



    private String writePassingZoneLine(Zone zz){

        String record;
        if (zz.getDirection().equals(Direction.ASCENDENT)){
            record = "PS  1";
        } else {
            record = "PS  2";
        }
        String passingCondition;
        if (zz.getPassing().equals(PassingCondition.NO_PASSING)){
            passingCondition = "     -1.00          ";
        } else if (zz.getPassing().equals(PassingCondition.PASSING_LANE)) {
            passingCondition = "      2.00         2";
        } else {
            passingCondition = "      1.00          ";
        }
        record = record +
                TWOPASutil.printWithBlanks(5,this.geometry.getPassingConditions().get(Direction.ASCENDENT).size()) +
                TWOPASutil.printWithBlanks(5, this.geometry.getPassingConditions().get(Direction.DESCENDENT).size()) +
                TWOPASutil.printWithBlanks(5, zz.getSequence()) +
                TWOPASutil.printWithBlanks(10, TWOPASutil.convertMetersToFeet(zz.getStart()), 1) +
                passingCondition;
        return record;

    }

    private String writeStationLocationLine(StationLocation st){

        String direction;
        if (st.getDirection().equals(Direction.ASCENDENT)){
            direction = "   1   1   0";
        } else {
            direction = "   2   1   0";
        }
        String record = "SL" +
                TWOPASutil.printWithBlanks(6, st.getSequence()) +
                direction +
                TWOPASutil.printWithBlanks(10,TWOPASutil.convertMetersToFeet(st.getStart()), 1) +
                "          " + st.getDescription();

        return record;
    }

    private String writeRecordVC(int i){
        String record;
        if (i == 0) {
            record = "VC  1                   191.10    787.80      49.2       1.0     1.000";
        } else if (i == 1) {
            record = "VC  2                   196.40    988.60      61.5       1.0     1.000";
        } else if (i == 2) {
            record = "VC  3                   202.10    919.30      61.5       1.0     1.000";
        } else if (i == 3) {
            record = "VC  4                   184.40    346.20      24.6       1.0     1.000";
        } else if (i == 4) {
            record = "VC  5              1      9.00    110.00      36.0";
        } else if (i == 5) {
            record = "VC  6                    11.00    115.00      28.0";
        } else if (i == 6) {
            record = "VC  7                    12.50    120.00      21.0";
        } else if (i == 7) {
            record = "VC  8                    14.00    125.00      32.0";
        } else if (i == 8) {
            record = "VC  9                     8.00    112.80      15.1";
        } else if (i == 9) {
            record = "VC 10                     9.02    117.80      14.8";
        } else if (i == 10) {
            record = "VC 11                     7.18    121.10      15.4";
        } else if (i == 11) {
            record = "VC 12                     9.52    127.00      14.1";
        } else {
            record = "VC 13                    10.70    142.70      13.1";
        }
        return record;

    }


    public void printSummary(PrintWriter summaryWriter){

        summaryWriter.print(this.simulationId);
        summaryWriter.print(",");
        summaryWriter.print(Resources.INSTANCE.getString(Properties.BASE_DIRECTORY) + "/" + Resources.INSTANCE.getString(Properties.OUTPUT_DIRECTORY) +
                this.geometry.getCountry() + "_l" + this.geometry.getLength() +  "_g" + this.geometry.getGeomId() + "_t" +
                this.traffic.getPercentTrucks()+ "_r" + this.traffic.getDirectionalSplit() + "_v" + this.traffic.getDirectionalSplit() +
                "_s" + this.driverSeed.getDriverSeedId() + ".INP");
        summaryWriter.print(",");
        summaryWriter.print(Resources.INSTANCE.getString(Properties.BASE_DIRECTORY) + "/" + Resources.INSTANCE.getString(Properties.OUTPUT_DIRECTORY) +
                this.geometry.getCountry() + "_l" + this.geometry.getLength() +  "_g" + this.geometry.getGeomId() + "_t" +
                this.traffic.getPercentTrucks()+ "_r" + this.traffic.getDirectionalSplit() + "_v" + this.traffic.getDirectionalSplit() +
                "_s" + this.driverSeed.getDriverSeedId() + ".OUT");
        summaryWriter.print(",");
        summaryWriter.print(this.geometry.getCountry());
        summaryWriter.print(",");
        summaryWriter.print(this.geometry.getLength());
        summaryWriter.print(",");
        summaryWriter.print(this.geometry.getGeomId());
        summaryWriter.print(",");
        summaryWriter.print(this.traffic.getPercentTrucks());
        summaryWriter.print(",");
        summaryWriter.print(this.traffic.getDirectionalSplit());
        summaryWriter.print(",");
        summaryWriter.print(this.driverSeed.getDriverSeedId());
        summaryWriter.print(",");
        summaryWriter.print(this.traffic.getDirectionalTraffic());
        summaryWriter.print(",");
        summaryWriter.print(this.traffic.getOpposingTraffic());
        summaryWriter.print(",");
        for (Direction direction : Direction.values()){
            for (ZoneCondition zoneCondition : ZoneCondition.values()){
                summaryWriter.print(printPassingCharacteristics(direction, zoneCondition));
            }
        }
        //already includes the final comma to separate the next element
        summaryWriter.print(this.traffic.getDirectionalPercentFollowers());
        summaryWriter.print(",");
        summaryWriter.println(this.traffic.getOpposingPercentFollowers());
    }

    private String printPassingCharacteristics(Direction direction, ZoneCondition zoneCondition){
        String passing = "";
        int countZones = 0;
        int lengthZones = 0;
        int averageLengthZones = 0;
        for (Zone zz : this.geometry.getPassingConditions().get(direction).values()){
            if (zz.getZoneCondition().equals(zoneCondition)){
                countZones++;
                lengthZones += zz.getLength();
            }
        }
        if (countZones > 0) {
            averageLengthZones = (int) lengthZones / countZones;
        }
        int percentLength = (int) Math.round(lengthZones / this.geometry.getLength() * 100);
        passing = percentLength + "," + countZones + "," + lengthZones + "," + averageLengthZones + ",";
        return passing;
    }


}
