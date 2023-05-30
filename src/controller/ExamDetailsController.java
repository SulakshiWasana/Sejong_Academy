package controller;

import db.DbConnection;
import model.ExamDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamDetailsController {

    public boolean saveExamDetail(ArrayList<ExamDetails> examDetails) throws SQLException, ClassNotFoundException {
        for (ExamDetails e : examDetails) {
            boolean b = saveExamDetail(e);
            if (!b) {
                return false;
            }
        }
        return true;
    }

    public boolean saveExamDetail(ExamDetails e) throws SQLException, ClassNotFoundException {
        Connection con = null;

        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO ExamDetails VALUES (?,?,?)");
            stm.setObject(1, e.getRId());
            stm.setObject(2, e.getEId());
            stm.setObject(3, e.getMarksForExam());
            System.out.println(e.getEId());

            if (stm.executeUpdate() > 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
}
