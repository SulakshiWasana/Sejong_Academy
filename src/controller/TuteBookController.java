package controller;

import db.DbConnection;
import model.TuteBook;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TuteBookController implements TuteBookService{

    public List<String> getTuteIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM TuteBook").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public boolean SaveTute(TuteBook tuteBook) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO TuteBook VALUE (?,?,?)");
        pstm.setObject(1, tuteBook.getTId());
        pstm.setObject(2, tuteBook.getName());
        pstm.setObject(3, tuteBook.getQtyOnHand());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean UpdateTute(TuteBook tuteBook) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE TuteBook SET Name=?, QtyOnHand=? WHERE TId=?");
        pstm.setObject(1, tuteBook.getName());
        pstm.setObject(2, tuteBook.getQtyOnHand());
        pstm.setObject(3, tuteBook.getTId());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean DeleteTute(String TId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM TuteBook WHERE TId='" + TId + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TuteBook getTute(String TId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM TuteBook WHERE TId=?");
        stm.setObject(1, TId);

        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            return new TuteBook(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            );

        } else {
            return null;
        }
    }

    @Override
    public ArrayList<TuteBook> getAllTute() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM TuteBook");
        ResultSet rst = stm.executeQuery();
        ArrayList<TuteBook> tuteBooks = new ArrayList<>();
        while (rst.next()) {
            tuteBooks.add(new TuteBook(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            ));
        }
        return tuteBooks;
    }
}
