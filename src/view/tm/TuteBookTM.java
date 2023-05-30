package view.tm;

public class TuteBookTM {
    private String TId;
    private String Name;
    private Integer QtyOnHand;

    public TuteBookTM(String tId, String name, String qtyOnHand) {
    }

    public TuteBookTM(String TId, String name, Integer qtyOnHand) {
        this.TId = TId;
        Name = name;
        QtyOnHand = qtyOnHand;
    }

    public String getTId() {
        return TId;
    }

    public void setTId(String TId) {
        this.TId = TId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getQtyOnHand() {
        return QtyOnHand;
    }

    public void setQtyOnHand(Integer qtyOnHand) {
        QtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "TuteBookTM{" +
                "TId='" + TId + '\'' +
                ", Name='" + Name + '\'' +
                ", QtyOnHand=" + QtyOnHand +
                '}';
    }
}
