package model;

public class Exam {
    private String EId;
    private String ExamTitle;
    private String Date;

    public Exam() {
    }

    public Exam(String EId, String examTitle, String date) {
        this.EId = EId;
        ExamTitle = examTitle;
        Date = date;
    }

    public String getEId() {
        return EId;
    }

    public void setEId(String EId) {
        this.EId = EId;
    }

    public String getExamTitle() {
        return ExamTitle;
    }

    public void setExamTitle(String examTitle) {
        ExamTitle = examTitle;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "EId='" + EId + '\'' +
                ", ExamTitle='" + ExamTitle + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
