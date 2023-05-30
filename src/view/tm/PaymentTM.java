package view.tm;

public class PaymentTM {
    private String PId;
    private String RId;
    private String CId;
    private String IId;
    private String PaymentDate;
    private String PaymentTime;
    private String Status;

    public PaymentTM() {
    }

    public PaymentTM(String PId, String RId, String CId, String IId, String paymentDate, String paymentTime, String status) {
        this.PId = PId;
        this.RId = RId;
        this.CId = CId;
        this.IId = IId;
        PaymentDate = paymentDate;
        PaymentTime = paymentTime;
        Status = status;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }

    public String getRId() {
        return RId;
    }

    public void setRId(String RId) {
        this.RId = RId;
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String getIId() {
        return IId;
    }

    public void setIId(String IId) {
        this.IId = IId;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        PaymentDate = paymentDate;
    }

    public String getPaymentTime() {
        return PaymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        PaymentTime = paymentTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "PaymentTM{" +
                "PId='" + PId + '\'' +
                ", RId='" + RId + '\'' +
                ", CId='" + CId + '\'' +
                ", IId='" + IId + '\'' +
                ", PaymentDate='" + PaymentDate + '\'' +
                ", PaymentTime='" + PaymentTime + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
