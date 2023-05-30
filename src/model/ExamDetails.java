package model;

public class ExamDetails {
    private String RId;
    private String EId;
    private Integer MarksForExam;

    public ExamDetails() {
    }

    public ExamDetails(String RId, String EId, Integer marksForExam) {
        this.RId = RId;
        this.EId = EId;
        MarksForExam = marksForExam;
    }

    public String getRId() {
        return RId;
    }

    public void setRId(String RId) {
        this.RId = RId;
    }

    public String getEId() {
        return EId;
    }

    public void setEId(String EId) {
        this.EId = EId;
    }

    public Integer getMarksForExam() {
        return MarksForExam;
    }

    public void setMarksForExam(Integer marksForExam) {
        MarksForExam = marksForExam;
    }

    @Override
    public String toString() {
        return "ExamDetails{" +
                "RId='" + RId + '\'' +
                ", EId='" + EId + '\'' +
                ", MarksForExam=" + MarksForExam +
                '}';
    }
}
