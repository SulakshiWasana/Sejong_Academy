package view.tm;

public class TuteTableTM {
    private  String RId;
    private String TId;
    private String TName;
    private Integer Qty;

    public TuteTableTM() {
    }

    public TuteTableTM(String RId, String TId, String TName, Integer qty) {
        this.RId = RId;
        this.TId = TId;
        this.TName = TName;
        Qty = qty;
    }

    public String getRId() {
        return RId;
    }

    public void setRId(String RId) {
        this.RId = RId;
    }

    public String getTId() {
        return TId;
    }

    public void setTId(String TId) {
        this.TId = TId;
    }

    public String getTName() {
        return TName;
    }

    public void setTName(String TName) {
        this.TName = TName;
    }

    public Integer getQty() {
        return Qty;
    }

    public void setQty(Integer qty) {
        Qty = qty;
    }

    @Override
    public String toString() {
        return "TuteTableTM{" +
                "RId='" + RId + '\'' +
                ", TId='" + TId + '\'' +
                ", TName='" + TName + '\'' +
                ", Qty=" + Qty +
                '}';
    }
}
