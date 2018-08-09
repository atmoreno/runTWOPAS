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
}
