package controller;

import model.Class;
import model.ClassRegistration;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClassService {
    public boolean SaveClass(Class c) throws SQLException, ClassNotFoundException;
    public boolean UpdateClass(Class c) throws SQLException, ClassNotFoundException;
    public boolean DeleteClass(String CId) throws SQLException, ClassNotFoundException;
    public Class getClass(String CId) throws SQLException, ClassNotFoundException;
    public ArrayList<Class> getAllClass() throws SQLException, ClassNotFoundException;

    public boolean SaveClassRegistration(ClassRegistration classRegistration) throws SQLException, ClassNotFoundException;
    public ArrayList<ClassRegistration> getAllClassRegistration() throws SQLException, ClassNotFoundException;

}
