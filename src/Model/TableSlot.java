package Model;

public class TableSlot {
    private int slotID;
    private int tableID;
    private String slotType;
    private boolean isAvailable;

    public TableSlot(int slotID, int tableID, String slotType, boolean isAvailable) {
        this.slotID = slotID;
        this.tableID = tableID;
        this.slotType = slotType;
        this.isAvailable = isAvailable;
    }

    public int getSlotID() { return slotID; }
    public int getTableID() { return tableID; }
    public String getSlotType() { return slotType; }
    public boolean isAvailable() { return isAvailable; }
}
