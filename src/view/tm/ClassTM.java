package view.tm;

public class ClassTM {
    private String CId;
    private String Name;
    private String Shedule;

    public ClassTM() {
    }

    public ClassTM(String CId, String name, String shedule) {
        this.CId = CId;
        Name = name;
        Shedule = shedule;
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getShedule() {
        return Shedule;
    }

    public void setShedule(String shedule) {
        Shedule = shedule;
    }

    @Override
    public String toString() {
        return "ClassTM{" +
                "CId='" + CId + '\'' +
                ", Name='" + Name + '\'' +
                ", Shedule='" + Shedule + '\'' +
                '}';
    }
}
