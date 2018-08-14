package summarizeOutputs;

import data.DataSet;
import data.OutputType;
import data.VariableOutput;
import input.OUTReader;
import resources.Resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class PrepareAndReadOutputs {

    private final DataSet dataSet;
    private final String propertiesFile;

    public PrepareAndReadOutputs(String propertiesFile){
        this.dataSet = new DataSet();
        this.propertiesFile = propertiesFile;
        Resources.initializeResources(propertiesFile);
    }

    public void run() throws IOException {
        ArrayList<String> listFiles = obtainLScenarioList();
        obtainOutputVariables();
        for (String fileName : listFiles) {
            OUTReader reader = new OUTReader(dataSet);
            reader.read(fileName);
        }
        //maybe create here the output CSV
    }


    private ArrayList<String> obtainLScenarioList() throws IOException {
        BufferedReader inputList = null;
        try {
            inputList = new BufferedReader(new FileReader(propertiesFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String list = null;
        ArrayList<String> listFiles = new ArrayList<>();
        while ((list = inputList.readLine())!= null) {
            String outFile = list.substring(0, list.length() - 4);
            outFile += ".OUT";
            listFiles.add(outFile);
        }
        return listFiles;
    }


    private void obtainOutputVariables(){
        dataSet.getOutputTypeCondition().put(OutputType.HIGHWAY, " Running TWOPAS");
        Map<Integer, VariableOutput> variableOutputMap = new LinkedHashMap<>();
        int i = 0;
        variableOutputMap.put(i++,new VariableOutput(i,"PREC",81,87,2));
        variableOutputMap.put(i++,new VariableOutput(i,"zcor",36,42,12));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp1",34,39,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp2",41,46,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp3",48,53,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp4",55,60,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp5",62,67,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp6",69,74,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp7",76,81,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp8",83,88,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp9",90,95,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp10",97,102,13));
        variableOutputMap.put(i++,new VariableOutput(i,"RN",30,38,14));
        dataSet.getOutputVariablesMap().put(OutputType.HIGHWAY,variableOutputMap);

        dataSet.getOutputTypeCondition().put(OutputType.TRAVEL_TIMES, "0TRAVEL TIMES AND DELAYS");
        variableOutputMap = new LinkedHashMap<>();
        i = 0;
        variableOutputMap.put(i++,new VariableOutput(i,"trucksASC",21,28,12));
        variableOutputMap.put(i++,new VariableOutput(i,"pcASC",21,28,27));
        variableOutputMap.put(i++,new VariableOutput(i,"trucksDESC",21,28,39));
        variableOutputMap.put(i++,new VariableOutput(i,"pcDESC",21,28,54));
        dataSet.getOutputVariablesMap().put(OutputType.TRAVEL_TIMES,variableOutputMap);
    }



}
