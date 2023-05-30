package view.tm;

public class ClassRegistrationTM {
    private String RId;
    private String CId;
    private String StudentName;
    private String ClassName;

    public ClassRegistrationTM() {
    }

    public ClassRegistrationTM(String RId, String CId, String studentName, String className) {
        this.RId = RId;
        this.CId = CId;
        StudentName = studentName;
        ClassName = className;
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

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    @Override
    public String toString() {
        return "ClassRegistrationTM{" +
                "RId='" + RId + '\'' +
                ", CId='" + CId + '\'' +
                ", StudentName='" + StudentName + '\'' +
                ", ClassName='" + ClassName + '\'' +
                '}';
    }
}
