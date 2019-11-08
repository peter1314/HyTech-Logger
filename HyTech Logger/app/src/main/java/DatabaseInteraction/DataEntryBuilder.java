package DatabaseInteraction;

public abstract class DataEntryBuilder {

    static DataEntryBuilder instance;
    DataEntry dataEntry;

    public void clear() {
        dataEntry.clear();
    }

    public DataEntry buildEntry() {
        return dataEntry;
    }
}
