package view.tm;

public class InstallmentTM {
    private String IId;
    private String InstallmentName;
    private Integer InstallmentFee;

    public InstallmentTM() {
    }

    public InstallmentTM(String IId, String installmentName, Integer installmentFee) {
        this.IId = IId;
        InstallmentName = installmentName;
        InstallmentFee = installmentFee;
    }

    public String getIId() {
        return IId;
    }

    public void setIId(String IId) {
        this.IId = IId;
    }

    public String getInstallmentName() {
        return InstallmentName;
    }

    public void setInstallmentName(String installmentName) {
        InstallmentName = installmentName;
    }

    public Integer getInstallmentFee() {
        return InstallmentFee;
    }

    public void setInstallmentFee(Integer installmentFee) {
        InstallmentFee = installmentFee;
    }

    @Override
    public String toString() {
        return "InstallmentTM{" +
                "IId='" + IId + '\'' +
                ", InstallmentName='" + InstallmentName + '\'' +
                ", InstallmentFee=" + InstallmentFee +
                '}';
    }
}
