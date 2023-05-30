package controller;

import model.Class;
import model.ClassRegistration;
import model.Installment;
import model.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentService {
    public boolean saveInstallment(Installment i) throws SQLException, ClassNotFoundException;
    public boolean updateInstallment(Installment i) throws SQLException, ClassNotFoundException;
    public boolean deleteInstallment(String IId) throws SQLException, ClassNotFoundException;
    public Installment getInstallment(String IId) throws SQLException, ClassNotFoundException;
    public ArrayList<Installment> getAllInstallment() throws SQLException, ClassNotFoundException;

    public boolean savePayment(Payment payment) throws SQLException, ClassNotFoundException;
    public ArrayList<Payment> getAllPayment() throws SQLException, ClassNotFoundException;

}
