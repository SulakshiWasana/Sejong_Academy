package controller;

import model.Registration;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RegistrationService {
    public boolean RegisterStudent(Registration registration) throws SQLException, ClassNotFoundException;
    public boolean DeleteRegisterStudent(String RId) throws SQLException, ClassNotFoundException;
    public Registration getRegistration(String RId) throws SQLException, ClassNotFoundException;
    public ArrayList<Registration> getAllRegistration() throws SQLException, ClassNotFoundException;
}
