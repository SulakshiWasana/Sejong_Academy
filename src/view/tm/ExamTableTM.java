package view.tm;

public class ExamTableTM {
    private  String RId;
    private String EId;
    private String ExamTitle;
    private Integer MarksForExam;

    public ExamTableTM() {
    }

    public ExamTableTM(String RId, String EId, String examTitle, Integer marksForExam) {
        this.RId = RId;
        this.EId = EId;
        ExamTitle = examTitle;
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

    public String getExamTitle() {
        return ExamTitle;
    }

    public void setExamTitle(String examTitle) {
        ExamTitle = examTitle;
    }

    public Integer getMarksForExam() {
        return MarksForExam;
    }

    public void setMarksForExam(Integer marksForExam) {
        MarksForExam = marksForExam;
    }

    @Override
    public String toString() {
        return "ExamTableTM{" +
                "RId='" + RId + '\'' +
                ", EId='" + EId + '\'' +
                ", ExamTitle='" + ExamTitle + '\'' +
                ", MarksForExam=" + MarksForExam +
                '}';
    }
}
