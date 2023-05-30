package controller;

import model.Attendence;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AttendenceService {
    public boolean AttendentStudent(Attendence attendence) throws SQLException, ClassNotFoundException;
    public boolean DeleteAttendence(String RId) throws SQLException, ClassNotFoundException;
    public ArrayList<Attendence> getAllAttendence() throws SQLException, ClassNotFoundException;

}
