package Model;

public class Table {
    private int tableID;
    private String tableName;
    private int capacity;
    private String view;

    public Table(int tableID, String tableName, int capacity, String view) {
        this.tableID = tableID;
        this.tableName = tableName;
        this.capacity = capacity;
        this.view = view;
    }

    public int getTableID() { return tableID; }
    public String getTableName() { return tableName; }
    public int getCapacity() { return capacity; }
    public String getView() { return view; }
}
