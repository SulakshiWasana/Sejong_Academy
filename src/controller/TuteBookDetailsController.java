package controller;

import db.DbConnection;
import model.TuteBookDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class TuteBookDetailsController {

    public boolean saveTuteBookDetail(ArrayList<TuteBookDetails>tuteBookDetails) throws SQLException, ClassNotFoundException {
        for (TuteBookDetails t : tuteBookDetails) {
            boolean b = saveTuteBookDetail(t);
            if (!b){
                return false;
            }
        }
        return true;
    }

    public boolean saveTuteBookDetail(TuteBookDetails t) throws SQLException, ClassNotFoundException {
        Connection con=null;

        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO TuteBookDetails VALUES (?,?,?)");
            stm.setObject(1, t.getRId());
            stm.setObject(2, t.getTId());
            stm.setObject(3, t.getQty());
            System.out.println(t.getTId());

            if (stm.executeUpdate()>0){
                if (updateQty(t)){
                    con.commit();
                    return true;
                }else {
                    con.rollback();
                    return false;
                }
            }
            con.rollback();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateQty(TuteBookDetails t) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE TuteBook  SET qtyOnHand=(qtyOnHand-?) WHERE TID= ?");
        stm.setObject(1,t.getQty());
        stm.setObject(2,t.getTId());
        return stm.executeUpdate()>0;
    }
}
