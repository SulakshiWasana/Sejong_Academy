package controller;

import db.DbConnection;
import model.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistrationController implements RegistrationService {

    public String getRId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT RId FROM Registration ORDER BY RId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "R-00"+tempId;
            }else if(tempId<99){
                return "R-0"+tempId;
            }else{
                return "R-"+tempId;
            }
        }else{
            return "R-001";
        }
    }

    @Override
    public boolean RegisterStudent(Registration registration) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO Registration VALUE (?,?,?,?,?)");
        pstm.setObject(1, registration.getNIC());
        pstm.setObject(2, registration.getRId());
        pstm.setObject(3, registration.getName());
        pstm.setObject(4, registration.getRegistrationDate());
        pstm.setObject(5, registration.getRegistrationFee());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean DeleteRegisterStudent(String RId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Registration WHERE RId='"+RId+"'").executeUpdate()>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Registration getRegistration(String RId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Registration WHERE RId=?");
        stm.setObject(1,RId);
        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            Registration registration = new Registration(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            return  registration;

        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Registration> getAllRegistration() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Registration");
        ResultSet rst = stm.executeQuery();
        ArrayList<Registration> registration = new ArrayList<>();
        while (rst.next()) {
            registration.add(new Registration(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return registration;
    }
}
