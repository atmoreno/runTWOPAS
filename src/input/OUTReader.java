package input;

import data.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class OUTReader {

    protected final DataSet dataSet;
    private BufferedReader reader;
    private Map<Direction, Map<String, String>> twopasOutputs;

    public OUTReader(DataSet dataSet){
        this.dataSet = dataSet;
    }


    public Map<Direction, Map<String, String>> read (String fileName) throws IOException {
        initializeReader(fileName);
        try{
            String record;
            OutputType type = OutputType.HIGHWAY;
            initializeMaps(fileName);
            while ((record = reader.readLine()) != null){
                String b = record;
                boolean condition = checkCondition(record, dataSet.getOutputTypeCondition().get(type));
                if (condition){
                    processRecords(type);
                    type = updateType(type);
                }
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
        return twopasOutputs;
    }


    private void initializeMaps(String fileName){
        twopasOutputs = new LinkedHashMap<>();
        twopasOutputs.put(Direction.ASCENDENT, new LinkedHashMap<>());
        twopasOutputs.put(Direction.DESCENDENT, new LinkedHashMap<>());
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
        } else if (record.length() == condition.length()){
            if (record.substring(0,condition.length()).equals(condition)){
                status = true;
            }
        }
        return  status;
    }


    private void processRecords(OutputType type) throws IOException {

        Map<String, String> characteristicsASC = new LinkedHashMap<>();
        Map<String, String> characteristicsDESC = new LinkedHashMap<>();
        if (twopasOutputs.get(Direction.ASCENDENT) != null){
            characteristicsASC = twopasOutputs.get(Direction.ASCENDENT);
            characteristicsDESC = twopasOutputs.get(Direction.DESCENDENT);
        }
        int prevLine = 1;
        int i = 1;
        String prevString = reader.readLine();
        for (VariableOutput vv : dataSet.getOutputVariablesMap().get(type).values()) {
            if (vv.getLinesAfterCondition() == prevLine) {
                characteristicsASC.put(vv.getDescription(), prevString.substring(vv.getInitialCharacter(), vv.getFinalCharacter()));
                characteristicsDESC.put(vv.getDescriptionDESC(), prevString.substring(vv.getInitialCharacter(), vv.getFinalCharacter()));
            } else {
                String line = prevString;
                while (i < vv.getLinesAfterCondition()) {
                    line = reader.readLine();
                    i++;
                }
                characteristicsASC.put(vv.getDescription(), line.substring(vv.getInitialCharacter(), vv.getFinalCharacter()));
                characteristicsDESC.put(vv.getDescriptionDESC(), line.substring(vv.getInitialCharacter(), vv.getFinalCharacter()));
                prevLine = i;
                prevString = line;
            }
        }
        twopasOutputs.put(Direction.ASCENDENT, characteristicsASC);
        twopasOutputs.put(Direction.DESCENDENT, characteristicsDESC);

    }


    private OutputType updateType (OutputType type){
        OutputType nextType;
        if (type.equals(OutputType.HIGHWAY)){
            nextType = OutputType.ATS;
        } else if (type.equals(OutputType.ATS)) {
            nextType = OutputType.TRAVEL_TIMES;
        } else if (type.equals(OutputType.TRAVEL_TIMES)) {
            nextType = OutputType.PTSF;
        } else if (type.equals(OutputType.PTSF)) {
            nextType = OutputType.PF_entry;
        } else if (type.equals(OutputType.PF_entry)) {
            nextType = OutputType.PF_exit;
        } else if (type.equals(OutputType.PF_exit)) {
            nextType = OutputType.PASSES;
        } else {
            nextType = OutputType.PASSES;
        }
        return nextType;
    }

}
