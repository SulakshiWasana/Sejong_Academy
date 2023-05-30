package controller;

import db.DbConnection;
import model.ClassRegistration;
import model.Installment;
import model.Payment;
import model.TuteBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentController implements PaymentService {

    public String getPId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT PId FROM Payment ORDER BY PId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "P-00"+tempId;
            }else if(tempId<99){
                return "P-0"+tempId;
            }else{
                return "P-"+tempId;
            }
        }else{
            return "P-001";
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

    public List<String> getInstallmentIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Installment").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public boolean saveInstallment(Installment i) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO Installment VALUE (?,?,?)");
        pstm.setObject(1, i.getIId());
        pstm.setObject(2, i.getInstallmentName());
        pstm.setObject(3, i.getInstallmentFee());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean updateInstallment(Installment i) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Installment SET InstallmentName=?, InstallmentFee=? WHERE IId=?");
        pstm.setObject(1, i.getInstallmentName());
        pstm.setObject(2, i.getInstallmentFee());
        pstm.setObject(3, i.getIId());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteInstallment(String IId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Installment WHERE IId='" + IId + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Installment getInstallment(String IId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Installment WHERE IId=?");
        stm.setObject(1, IId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Installment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Installment> getAllInstallment() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Installment");
        ResultSet rst = stm.executeQuery();
        ArrayList<Installment> installments = new ArrayList<>();
        while (rst.next()) {
            installments.add(new Installment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            ));
        }
        return installments;
    }

    @Override
    public boolean savePayment(Payment payment) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO Payment VALUE (?,?,?,?,?,?,?)");
        pstm.setObject(1, payment.getPId());
        pstm.setObject(2, payment.getRId());
        pstm.setObject(3, payment.getCId());
        pstm.setObject(4, payment.getIId());
        pstm.setObject(5, payment.getPaymentDate());
        pstm.setObject(6, payment.getPaymentTime());
        pstm.setObject(7, payment.getStatus());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public ArrayList<Payment> getAllPayment() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment");
        ResultSet rst = stm.executeQuery();
        ArrayList<Payment> payments = new ArrayList<>();
        while (rst.next()) {
            payments.add(new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return payments;
    }

    public static List<Payment> searchPayment(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Payment WHERE RId LIKE '%" + value + "%'");
        ResultSet rst = pstm.executeQuery();

        List<Payment> payments = new ArrayList<>();
        while (rst.next()) {
            payments.add(new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return payments;
    }


    public boolean deletePayment(String RId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Payment WHERE RId='"+RId+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
}
