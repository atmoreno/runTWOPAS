package data;

public enum Direction {
    ASCENDENT,
    DESCENDENT;

    public static int valueOf(Direction code){
        switch (code){
            case ASCENDENT: return 1;
            case DESCENDENT: return 2;
            default: throw new RuntimeException("Undefined direction " + code);
        }
    }

}
