package input;

import data.DataSet;
import data.OutputType;
import data.VariableOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class OUTReader {

    protected final DataSet dataSet;
    private BufferedReader reader;
    private Map<String, String> twopasOutputs;

    public OUTReader(DataSet dataSet){
        this.dataSet = dataSet;
    }


    public void read (String fileName) throws IOException {
        initializeReader(fileName);
        try{
            twopasOutputs = new LinkedHashMap<>();
            twopasOutputs.put("scenario", fileName);
            String record;
            OutputType type = OutputType.HIGHWAY;
            while ((record = reader.readLine()) != null){
                String b = record;
                boolean condition = checkCondition(record, dataSet.getOutputTypeCondition().get(type));
                if (condition){
                    processRecords(type);
                    type = updateType(type);
                }

            }
            for (String st : twopasOutputs.keySet()){
                System.out.println(st + ": " + twopasOutputs.get(st));
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            try{
                reader.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("Read " + fileName);
    }

    private void initializeReader(String fileName){
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            //System.out.println("Error initializing csv reader: " + e.getMessage(), e);
        }
    }

    private boolean checkCondition(String record, String condition){
        boolean status = false;
        if (record.length() > condition.length()){
            if (record.substring(0,condition.length()).equals(condition)){
                status = true;
            }
        }
        return  status;
    }


    private void processRecords(OutputType type) throws IOException {

        Map<String, String> characteristics = new LinkedHashMap<>();
        int prevLine = 1;
        int i = 1;
        String prevString = reader.readLine();
        for (VariableOutput vv : dataSet.getOutputVariablesMap().get(type).values()) {
            if (vv.getLinesAfterCondition() == prevLine) {
                twopasOutputs.put(vv.getDescription(), prevString.substring(vv.getInitialCharacter(), vv.getFinalCharacter()));
            } else {
                String line = prevString;
                while (i < vv.getLinesAfterCondition()) {
                    line = reader.readLine();
                    i++;
                }
                twopasOutputs.put(vv.getDescription(), line.substring(vv.getInitialCharacter(), vv.getFinalCharacter()));
                prevLine = i;
                prevString = line;

            }
        }

/*        VariableOutput vv = dataSet.getOutputVariablesMap().get(type).get(variableCount);
        for (int i = 1; i <= maxSubsequentLine(type); i++) {
            if (vv.getLinesAfterCondition() == prevLine){
                twopasOutputs.put(vv.getDescription(), prevString.substring(vv.getInitialCharacter(), vv.getFinalCharacter()));
                variableCount++;
                vv = dataSet.getOutputVariablesMap().get(type).get(variableCount);
            } else {
                String line = reader.readLine();
                if (i == vv.getLinesAfterCondition()) {
                    twopasOutputs.put(vv.getDescription(), line.substring(vv.getInitialCharacter(), vv.getFinalCharacter()));
                    variableCount++;
                    vv = dataSet.getOutputVariablesMap().get(type).get(variableCount);
                    prevLine = i;
                    prevString = line;
                }
            }
        }*/
    }


    private int maxSubsequentLine(OutputType type){
        int line = 0;
        for (VariableOutput vv : dataSet.getOutputVariablesMap().get(type).values()){
           line = Math.max(line, vv.getLinesAfterCondition());
        }
        return line;
    }


    private OutputType updateType (OutputType type){
        OutputType nextType;
        if (type.equals(OutputType.HIGHWAY)){
            nextType = OutputType.TRAVEL_TIMES;
        } else {
            nextType = OutputType.TRAVEL_TIMES;
        }
        return nextType;
    }



}
