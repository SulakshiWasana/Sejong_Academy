package controller;

import db.DbConnection;
import model.Exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamController implements ExamService {

    public List<String> getExamIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Exam").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public boolean SaveExam(Exam exam) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO Exam VALUE (?,?,?)");
        pstm.setObject(1, exam.getEId());
        pstm.setObject(2, exam.getExamTitle());
        pstm.setObject(3, exam.getDate());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean UpdateExam(Exam exam) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Exam SET ExamTitle=?, Date=? WHERE EId=?");
        pstm.setObject(1, exam.getExamTitle());
        pstm.setObject(2, exam.getDate());
        pstm.setObject(3, exam.getEId());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean DeleteExam(String EId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Exam WHERE EId='" + EId + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Exam getExam(String EId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Exam WHERE EId=?");
        stm.setObject(1, EId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Exam(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Exam> getAllExam() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Exam");
        ResultSet rst = stm.executeQuery();
        ArrayList<Exam> exams = new ArrayList<>();
        while (rst.next()) {
            exams.add(new Exam(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return exams;
    }
}
