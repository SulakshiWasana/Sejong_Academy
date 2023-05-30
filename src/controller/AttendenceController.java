package controller;

import db.DbConnection;
import model.Attendence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendenceController implements AttendenceService {

    public String getAId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT AID FROM Attendence ORDER BY AId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "A-00"+tempId;
            }else if(tempId<99){
                return "A-0"+tempId;
            }else{
                return "A-"+tempId;
            }
        }else{
            return "A-001";
        }
    }

    @Override
    public boolean AttendentStudent(Attendence attendence) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO Attendence VALUE (?,?,?,?,?)");
        pstm.setObject(1, attendence.getRId());
        pstm.setObject(2, attendence.getAId());
        pstm.setObject(3, attendence.getName());
        pstm.setObject(4, attendence.getDate());
        pstm.setObject(5, attendence.getStatus());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean DeleteAttendence(String RId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Attendence WHERE RId='"+RId+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<Attendence> getAllAttendence() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Attendence");
        ResultSet rst = stm.executeQuery();
        ArrayList<Attendence> attendences = new ArrayList<>();
        while (rst.next()) {
            attendences.add(new Attendence(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return attendences;
    }

    public static List<Attendence> searchAttendence(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Attendence WHERE RId LIKE '%"+value+"%'");
        ResultSet rst = pstm.executeQuery();

        List<Attendence> attendences = new ArrayList<>();
        while (rst.next()) {
            attendences.add(new Attendence(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return attendences;
    }
}
