package controller;

import model.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentService {
    public boolean SaveStudent(Student student) throws SQLException, ClassNotFoundException;
    public boolean UpdateStudent(Student student) throws SQLException, ClassNotFoundException;
    public boolean DeleteStudent(String NIC) throws SQLException, ClassNotFoundException;
    public Student getStudent(String NIC) throws SQLException, ClassNotFoundException;
    public ArrayList<Student> getAllStudents() throws SQLException, ClassNotFoundException;

}
