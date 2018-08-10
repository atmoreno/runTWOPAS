package data;

public enum ZoneCondition {

    PASSING_LANE,
    SINGLE_LANE,
    CRITICAL_TRANSITION,
    NON_CRITICAL_TRANSITION,
    INTERSECTION;

    public static int valueOf (ZoneCondition code){
        //
        switch (code){
            case PASSING_LANE: return 1;
            case SINGLE_LANE: return 2;
            case CRITICAL_TRANSITION: return 3;
            case NON_CRITICAL_TRANSITION: return 4;
            case INTERSECTION: return 5;
            default: throw new RuntimeException("Undefined passing condition " + code);
        }
    }

}
