package model;

public class TuteBookDetails {
    private String RId;
    private String TId;
    private Integer Qty;

    public TuteBookDetails() {
    }

    public TuteBookDetails(String RId, String TId, Integer qty) {
        this.RId = RId;
        this.TId = TId;
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

    public Integer getQty() {
        return Qty;
    }

    public void setQty(Integer qty) {
        Qty = qty;
    }

    @Override
    public String toString() {
        return "TuteBookDetails{" +
                "RId='" + RId + '\'' +
                ", TId='" + TId + '\'' +
                ", Qty=" + Qty +
                '}';
    }
}
