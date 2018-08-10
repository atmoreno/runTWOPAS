package input;

import data.DataSet;
import resources.Properties;
import resources.Resources;

import java.util.Arrays;
import java.util.List;


public class RandomSeedReader extends CSVReader {

    private int id;
    private int seedIndex;



    public RandomSeedReader(DataSet dataSet){
        super(dataSet);
    }

    public void read(){super.read(Resources.INSTANCE.getString(Properties.RANDOM_SEEDS_CHARACTERISTICS), ",");}

    protected void processHeader(String[] header){
        List<String> headerList = Arrays.asList(header);
        id = headerList.indexOf("id");
        seedIndex = headerList.indexOf("RandomSeeds");
    }

    protected void processRecord(String[] record){
        int id = Integer.parseInt(record[this.id]);
        String cor = record[seedIndex];
        dataSet.getTwopasRandomSeeds().put(id, cor);
    }
}
