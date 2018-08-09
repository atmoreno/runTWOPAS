package data;

public enum PassingCondition {
    PASSING_LANE,
    PASSING_ZONE,
    NO_PASSING;

    public static int valueOf (PassingCondition code){
        //codes from TWOPAS INP
        switch (code){
            case PASSING_LANE: return 2;
            case PASSING_ZONE: return 1;
            case NO_PASSING: return -1;
            default: throw new RuntimeException("Undefined passing condition " + code);
        }
    }
}
