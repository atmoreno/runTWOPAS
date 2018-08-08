import scenarioGeneration.GenerateScenarios;

public class GenerateTWOPAS {

    public static void main(String[] args){
        GenerateScenarios generateScenarios = new GenerateScenarios(args[0]);
        generateScenarios.run();
    }
}
