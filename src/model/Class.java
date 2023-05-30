package model;

public class Class {
    private String CId;
    private String Name;
    private String Shedule;

    public Class() {
    }

    public Class(String CId, String name, String shedule) {
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
        return "Class{" +
                "CId='" + CId + '\'' +
                ", Name='" + Name + '\'' +
                ", Shedule='" + Shedule + '\'' +
                '}';
    }
}
