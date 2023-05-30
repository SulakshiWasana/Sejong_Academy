package controller;

import db.DbConnection;
import model.Class;
import model.ClassRegistration;
import model.Registration;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassController implements ClassService {

    public String getNum() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT Num FROM ClassRegistration ORDER BY Num DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "CR-00"+tempId;
            }else if(tempId<99){
                return "CR-0"+tempId;
            }else{
                return "CR-"+tempId;
            }
        }else{
            return "CR-001";
        }
    }

    public List<String> getClassIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Class").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public boolean SaveClass(Class c) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO Class VALUE (?,?,?)");
        pstm.setObject(1, c.getCId());
        pstm.setObject(2, c.getName());
        pstm.setObject(3, c.getShedule());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean UpdateClass(Class c) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Class SET Name=?, Shedule=? WHERE CId=?");
        pstm.setObject(1, c.getName());
        pstm.setObject(2, c.getShedule());
        pstm.setObject(3, c.getCId());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean DeleteClass(String RId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM ClassRegistration WHERE RId='" + RId + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Class getClass(String CId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Class WHERE CId=?");
        stm.setObject(1, CId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Class(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Class> getAllClass() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Class");
        ResultSet rst = stm.executeQuery();
        ArrayList<Class> c = new ArrayList<>();
        while (rst.next()) {
            c.add(new Class(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return c;
    }

    @Override
    public boolean SaveClassRegistration(ClassRegistration classRegistration) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO ClassRegistration VALUE (?,?,?,?,?)");
        pstm.setObject(1,classRegistration.getNum());
        pstm.setObject(2, classRegistration.getRId());
        pstm.setObject(3, classRegistration.getCId());
        pstm.setObject(4, classRegistration.getStudentName());
        pstm.setObject(5, classRegistration.getClassName());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public ArrayList<ClassRegistration> getAllClassRegistration() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM ClassRegistration");
        ResultSet rst = stm.executeQuery();
        ArrayList<ClassRegistration> classRegistrations = new ArrayList<>();
        while (rst.next()) {
            classRegistrations.add(new ClassRegistration(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return classRegistrations;
    }

    public static List<ClassRegistration> searchClass(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM ClassRegistration WHERE RId LIKE '%" + value + "%'");
        ResultSet rst = pstm.executeQuery();

        List<ClassRegistration> classRegistrations = new ArrayList<>();
        while (rst.next()) {
            classRegistrations.add(new ClassRegistration(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return classRegistrations;
    }
}
