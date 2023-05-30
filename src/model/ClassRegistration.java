package model;

public class ClassRegistration {
    private String Num;
    private String RId;
    private String CId;
    private String StudentName;
    private String ClassName;

    public ClassRegistration() {
    }

    public ClassRegistration(String num, String RId, String CId, String studentName, String className) {
        Num = num;
        this.RId = RId;
        this.CId = CId;
        StudentName = studentName;
        ClassName = className;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
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
        return "ClassRegistration{" +
                "Num='" + Num + '\'' +
                ", RId='" + RId + '\'' +
                ", CId='" + CId + '\'' +
                ", StudentName='" + StudentName + '\'' +
                ", ClassName='" + ClassName + '\'' +
                '}';
    }
}
