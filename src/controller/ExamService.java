package controller;

import model.Exam;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ExamService {
    public boolean SaveExam(Exam exam) throws SQLException, ClassNotFoundException;
    public boolean UpdateExam(Exam exam) throws SQLException, ClassNotFoundException;
    public boolean DeleteExam(String EId) throws SQLException, ClassNotFoundException;
    public Exam getExam(String EId) throws SQLException, ClassNotFoundException;
    public ArrayList<Exam> getAllExam() throws SQLException, ClassNotFoundException;

}
