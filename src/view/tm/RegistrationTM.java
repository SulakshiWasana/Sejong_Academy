package view.tm;

public class RegistrationTM {
    private String NIC;
    private String RId;
    private String Name;
    private String RegistrationDate;
    private String RegistrationFee;

    public RegistrationTM() {
    }

    public RegistrationTM(String NIC, String RId, String name, String registrationDate, String registrationFee) {
        this.NIC = NIC;
        this.RId = RId;
        Name = name;
        RegistrationDate = registrationDate;
        RegistrationFee = registrationFee;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getRId() {
        return RId;
    }

    public void setRId(String RId) {
        this.RId = RId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        RegistrationDate = registrationDate;
    }

    public String getRegistrationFee() {
        return RegistrationFee;
    }

    public void setRegistrationFee(String registrationFee) {
        RegistrationFee = registrationFee;
    }

    @Override
    public String toString() {
        return "RegistrationTM{" +
                "NIC='" + NIC + '\'' +
                ", RId='" + RId + '\'' +
                ", Name='" + Name + '\'' +
                ", RegistrationDate='" + RegistrationDate + '\'' +
                ", RegistrationFee='" + RegistrationFee + '\'' +
                '}';
    }
}