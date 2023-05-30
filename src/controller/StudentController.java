package controller;

import db.DbConnection;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentController implements StudentService{

    @Override
    public boolean SaveStudent(Student student) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO student VALUE (?,?,?,?,?,?,?,?,?)");
        pstm.setObject(1, student.getNIC());
        pstm.setObject(2, student.getName());
        pstm.setObject(3, student.getGender());
        pstm.setObject(4, student.getDOB());
        pstm.setObject(5, student.getAge());
        pstm.setObject(6, student.getPassportNo());
        pstm.setObject(7, student.getAddress());
        pstm.setObject(8, student.getContactNo());
        pstm.setObject(9, student.getEmail());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean UpdateStudent(Student student) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Student SET Name=?, Gender=?, DOB=?, Age=?, PassportNo=?, Address=?, ContactNo=?, Email=? WHERE NIC=?");
        pstm.setObject(1, student.getName());
        pstm.setObject(2, student.getGender());
        pstm.setObject(3, student.getDOB());
        pstm.setObject(4, student.getAge());
        pstm.setObject(5, student.getPassportNo());
        pstm.setObject(6, student.getAddress());
        pstm.setObject(7, student.getContactNo());
        pstm.setObject(8, student.getEmail());
        pstm.setObject(9, student.getNIC());
        return pstm.executeUpdate() > 0;

    }

    @Override
    public boolean DeleteStudent(String NIC) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Student WHERE NIC='"+NIC+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Student getStudent(String NIC) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student WHERE NIC=?");
        stm.setObject(1, NIC);

        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            return new Student(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            );
        } else {
            return null;
        }

    }

    @Override
    public ArrayList<Student> getAllStudents() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student");
        ResultSet rst = stm.executeQuery();
        ArrayList<Student> students = new ArrayList<>();
        while (rst.next()) {
            students.add(new Student(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            ));
        }
        return students;
    }

    public static List<Student> searchStudent(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Student WHERE NIC LIKE '%"+value+"%'");
        ResultSet rst = pstm.executeQuery();

        List<Student> students = new ArrayList<>();
        while (rst.next()) {
            students.add(new Student(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            ));
        }
        return students;
    }
}

