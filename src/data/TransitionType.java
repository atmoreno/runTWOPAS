package data;

public enum TransitionType {

    CRITICAL,
    NONCRITICAL,
    INTERSECTION;

    public static TransitionType valueOf (int code){
        switch (code){
            case 1: return CRITICAL;
            case 2: return NONCRITICAL;
            case 3: return INTERSECTION;
            default: throw new RuntimeException("Undefined transition code " + code);
        }
    }

    public static ZoneCondition translateZoneCondition (TransitionType type){
        switch (type){
            case CRITICAL: return ZoneCondition.CRITICAL_TRANSITION;
            case NONCRITICAL: return ZoneCondition.NON_CRITICAL_TRANSITION;
            case INTERSECTION: return ZoneCondition.INTERSECTION;
            default: throw new RuntimeException("Undefined transition ");
        }
    }
}
