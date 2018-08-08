package input;

import data.DataSet;

import javax.xml.crypto.Data;

abstract class AbstractInputReader {

    protected final DataSet dataSet;

    AbstractInputReader(DataSet dataSet){this.dataSet = dataSet;}

    public abstract void read();

}
