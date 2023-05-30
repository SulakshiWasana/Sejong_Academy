package view.tm;

public class AttendenceTM {
    private String RId;
    private String AId;
    private String Name;
    private String Date;
    private String Status;

    public AttendenceTM() {
    }

    public AttendenceTM(String RId, String AId, String name, String date, String status) {
        this.RId = RId;
        this.AId = AId;
        Name = name;
        Date = date;
        Status = status;
    }

    public String getRId() {
        return RId;
    }

    public void setRId(String RId) {
        this.RId = RId;
    }

    public String getAId() {
        return AId;
    }

    public void setAId(String AId) {
        this.AId = AId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "AttendenceTM{" +
                "RId='" + RId + '\'' +
                ", AId='" + AId + '\'' +
                ", Name='" + Name + '\'' +
                ", Date='" + Date + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
