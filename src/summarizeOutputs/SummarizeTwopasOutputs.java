package summarizeOutputs;

import data.*;
import input.OUTReader;
import resources.Properties;
import resources.Resources;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SummarizeTwopasOutputs {

    private final DataSet dataSet;

    public SummarizeTwopasOutputs(String propertiesFile){
        this.dataSet = new DataSet();
        Resources.initializeResources(propertiesFile);
    }

    public void run() throws IOException {
        obtainOutputVariables();
        readTwopasOut(obtainLScenarioList());
        writeTwopasOutputs();
    }


    private void readTwopasOut(ArrayList<String> listFiles) throws IOException {
        int i = 0;
        for (String fileName : listFiles) {
            Map<Direction, Map<String, String>> outsputs =  new OUTReader(dataSet).read(fileName);
            TWOPASoutput output = new TWOPASoutput(i++, fileName, outsputs);
            dataSet.getTwopasOutputMap().put(i, output);
        }
    }


    private void writeTwopasOutputs(){

        int counter = 0;
        String fileSummary = Resources.INSTANCE.getString(Properties.BASE_DIRECTORY) + "/" + Resources.INSTANCE.getString(Properties.OUTPUT_SUMMARY) +
                Resources.INSTANCE.getString(Properties.LIST_OF_COUNTRIES) + ".csv";
        PrintWriter summaryOfScenarios = new PrintWriter(TWOPASutil.openFileForSequentialWriting(fileSummary, false));
        String header = "id,out,direction,";
        for (String attribute : dataSet.getTwopasOutputMap().get(1).getTrafficOutputs().get(Direction.ASCENDENT).keySet()){
            header += attribute + ",";
        }
        summaryOfScenarios.println(header);
        for (TWOPASoutput output : dataSet.getTwopasOutputMap().values()){
            output.printSummary(summaryOfScenarios);
        }
        summaryOfScenarios.close();
    }


    private ArrayList<String> obtainLScenarioList() throws IOException {
        BufferedReader inputList = null;
        try {
            String fileName = Resources.INSTANCE.getString(Properties.LIST_OF_SCENARIOS) + Resources.INSTANCE.getString(Properties.LIST_OF_COUNTRIES) + ".txt";
            inputList = new BufferedReader(new FileReader(fileName));
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
        variableOutputMap.put(i++,new VariableOutput(i,"PREC","PREC",81,87,2));
        variableOutputMap.put(i++,new VariableOutput(i,"zcor","zcor",36,42,12));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp1","bkmp1",34,39,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp2","bkmp2",41,46,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp3","bkmp3",48,53,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp4","bkmp4",55,60,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp5","bkmp5",62,67,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp6","bkmp6",69,74,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp7","bkmp7",76,81,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp8","bkmp8",83,88,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp9","bkmp9",90,95,13));
        variableOutputMap.put(i++,new VariableOutput(i,"bkmp10","bkmp10",97,102,13));
        variableOutputMap.put(i++,new VariableOutput(i,"RN","RN",30,38,14));
        dataSet.getOutputVariablesMap().put(OutputType.HIGHWAY,variableOutputMap);

        dataSet.getOutputTypeCondition().put(OutputType.TRAVEL_TIMES, "0TRAVEL TIMES AND DELAYS");
        variableOutputMap = new LinkedHashMap<>();
        i = 0;
        variableOutputMap.put(i++,new VariableOutput(i,"inputTrucks_d","inputTrucks_o",21,28,12));
        variableOutputMap.put(i++,new VariableOutput(i,"inputPc_d", "inputPc_o",21,28,27));
        variableOutputMap.put(i++,new VariableOutput(i,"inputTrucks_o","inputTrucks_d",21,28,39));
        variableOutputMap.put(i++,new VariableOutput(i,"inputPc_o","inputPc_d",21,28,54));
        dataSet.getOutputVariablesMap().put(OutputType.TRAVEL_TIMES,variableOutputMap);

        dataSet.getOutputTypeCondition().put(OutputType.PASSES, "0OVERTAKING EVENTS   CLASSIFIED ON SPEED DIFFERENCE");
        variableOutputMap = new LinkedHashMap<>();
        i = 0;
        variableOutputMap.put(i++,new VariableOutput(i,"passesSpeedDif10_d","passesSpeedDif10_o",104,108,5));
        variableOutputMap.put(i++,new VariableOutput(i,"passesSpeedDif20_d","passesSpeedDif20_o",104,108,6));
        variableOutputMap.put(i++,new VariableOutput(i,"passesSpeedDif30_d","passesSpeedDif30_o",104,108,7));
        variableOutputMap.put(i++,new VariableOutput(i,"passes_d","passes_o",104,108,13));
        variableOutputMap.put(i++,new VariableOutput(i,"passesSpeedDif10_o","passesSpeedDif10_d",104,108,17));
        variableOutputMap.put(i++,new VariableOutput(i,"passesSpeedDif20_o","passesSpeedDif20_d",104,108,18));
        variableOutputMap.put(i++,new VariableOutput(i,"passesSpeedDif30_o","passesSpeedDif30_d",104,108,19));
        variableOutputMap.put(i++,new VariableOutput(i,"passes_o","passes_d",104,108,25));

        dataSet.getOutputVariablesMap().put(OutputType.PASSES,variableOutputMap);

        dataSet.getOutputTypeCondition().put(OutputType.PTSF,"0PERCENT  OF  TIME  UNIMPEDED");
        variableOutputMap = new LinkedHashMap<>();
        i = 0;
        variableOutputMap.put(i++,new VariableOutput(i,"PTSF_d","PTSF_o",15,20,6));
        variableOutputMap.put(i++,new VariableOutput(i,"PTSF_o","PTSF_d",25,30,6));
        dataSet.getOutputVariablesMap().put(OutputType.PTSF,variableOutputMap);

        dataSet.getOutputTypeCondition().put(OutputType.PF_entry,"0HEADWAYS  AT  START  LINES");
        variableOutputMap = new LinkedHashMap<>();
        i = 0;
        variableOutputMap.put(i++,new VariableOutput(i,"PF2Start_d","PF2Start_o",42,47,4));
        variableOutputMap.put(i++,new VariableOutput(i,"PF2Start_o","PF2Start_d",72,77,4));
        variableOutputMap.put(i++,new VariableOutput(i,"PF3Start_d","PF3Start_o",42,47,5));
        variableOutputMap.put(i++,new VariableOutput(i,"PF3Start_o","PF3Start_d",72,77,5));
        variableOutputMap.put(i++,new VariableOutput(i,"PF4Start_d","PF4Start_o",42,47,6));
        variableOutputMap.put(i++,new VariableOutput(i,"PF4Start_o","PF4Start_d",72,77,6));
        dataSet.getOutputVariablesMap().put(OutputType.PF_entry,variableOutputMap);

        dataSet.getOutputTypeCondition().put(OutputType.PF_exit,"0HEADWAYS  AT  FINISH LINES");
        variableOutputMap = new LinkedHashMap<>();
        i = 0;
        variableOutputMap.put(i++,new VariableOutput(i,"PF2_d","PF2_o",42,47,2));
        variableOutputMap.put(i++,new VariableOutput(i,"PF2_o","PF2_d",72,77,2));
        variableOutputMap.put(i++,new VariableOutput(i,"PF3_d","PF3_o",42,47,3));
        variableOutputMap.put(i++,new VariableOutput(i,"PF3_o","PF3_d",72,77,3));
        variableOutputMap.put(i++,new VariableOutput(i,"PF4_d","PF4_o",42,47,4));
        variableOutputMap.put(i++,new VariableOutput(i,"PF4_o","PF4_d",72,77,4));
        dataSet.getOutputVariablesMap().put(OutputType.PF_exit,variableOutputMap);

        dataSet.getOutputTypeCondition().put(OutputType.ATS," DATA FROM SPACE MEASUREMENTS");
        variableOutputMap = new LinkedHashMap<>();
        i = 0;
        variableOutputMap.put(i++,new VariableOutput(i,"ATSHv_d","ATSHv_o",25,30,11));
        variableOutputMap.put(i++,new VariableOutput(i,"ATSHv_o","ATSHv_d",85,90,11));
        variableOutputMap.put(i++,new VariableOutput(i,"VHv_d","VHv_o",33,40,11));
        variableOutputMap.put(i++,new VariableOutput(i,"VHv_o","VHv_d",93,100,11));
        variableOutputMap.put(i++,new VariableOutput(i,"ATSpc_d","ATSpc_o",25,30,22));
        variableOutputMap.put(i++,new VariableOutput(i,"ATSpc_o","ATSpc_d",85,90,22));
        variableOutputMap.put(i++,new VariableOutput(i,"Vpc_d","Vpc_o",33,40,22));
        variableOutputMap.put(i++,new VariableOutput(i,"Vpc_o","Vpc_d",93,100,22));
        variableOutputMap.put(i++,new VariableOutput(i,"ATS_d","ATS_o",25,30,23));
        variableOutputMap.put(i++,new VariableOutput(i,"ATS_o","ATS_d",85,90,23));
        variableOutputMap.put(i++,new VariableOutput(i,"V_d","V_o",33,40,23));
        variableOutputMap.put(i++,new VariableOutput(i,"V_o","V_d",93,100,23));
        dataSet.getOutputVariablesMap().put(OutputType.ATS,variableOutputMap);


    }



}
