package controller;

import model.TuteBook;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TuteBookService {
    public boolean SaveTute(TuteBook tuteBook) throws SQLException, ClassNotFoundException;
    public boolean UpdateTute(TuteBook tuteBook) throws SQLException, ClassNotFoundException;
    public boolean DeleteTute(String TId) throws SQLException, ClassNotFoundException;
    public TuteBook getTute(String TId) throws SQLException, ClassNotFoundException;
    public ArrayList<TuteBook> getAllTute() throws SQLException, ClassNotFoundException;

}
