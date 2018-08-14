package data;

public class VariableOutput {

    private final int variableId;
    private final String description;
    private final int initialCharacter;
    private final int finalCharacter;
    private final int linesAfterCondition;


    public VariableOutput(int variableId, String description, int initialCharacter, int finalCharacter, int linesAfterCondition) {
        this.variableId = variableId;
        this.description = description;
        this.initialCharacter = initialCharacter;
        this.finalCharacter = finalCharacter;
        this.linesAfterCondition = linesAfterCondition;
    }

    public int getVariableId() {
        return variableId;
    }

    public String getDescription() {
        return description;
    }

    public int getInitialCharacter() {
        return initialCharacter;
    }

    public int getFinalCharacter() {
        return finalCharacter;
    }

    public int getLinesAfterCondition() {
        return linesAfterCondition;
    }
}
