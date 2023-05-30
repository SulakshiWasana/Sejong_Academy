package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Class;
import model.Installment;
import view.tm.ClassTM;
import view.tm.InstallmentTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddNewInstallmentFormController implements Initializable {
    public TextField txtIId;
    public TextField txtName;
    public TextField txtIFee;
    public TableView <InstallmentTM>tblInstallment;
    public TableColumn colIId;
    public TableColumn colIName;
    public TableColumn colIFee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            colIId.setCellValueFactory(new PropertyValueFactory<>("IId"));
            colIName.setCellValueFactory(new PropertyValueFactory<>("InstallmentName"));
            colIFee.setCellValueFactory(new PropertyValueFactory<>("InstallmentFee"));

            setInstallmentToTable(new PaymentController().getAllInstallment());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setInstallmentToTable(ArrayList<Installment> i) {
        ObservableList<InstallmentTM> obList = FXCollections.observableArrayList();
        i.forEach(e -> {
            obList.add(
                    new InstallmentTM(e.getIId(), e.getInstallmentName(), e.getInstallmentFee()));
        });
        tblInstallment.setItems(obList);
    }


    public void saveInstallmentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Installment i = new Installment(
                txtIId.getText(),
                txtName.getText(),
                Integer.parseInt(txtIFee.getText())
        );
        if (new PaymentController().saveInstallment(i))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();

        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        txtIId.clear();txtName.clear();txtIFee.clear();
    }

    public void selectInstallmentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String installmentId = txtIId.getText();

        Installment i = new PaymentController().getInstallment(installmentId);
        if (i==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(i);
        }
    }

    private void setData( Installment i) {
        txtIId.setText(i.getIId());
        txtName.setText(i.getInstallmentName());
        txtIFee.setText(String.valueOf(i.getInstallmentFee()));
    }


    public void updateInstallmentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Installment i = new Installment(
                txtIId.getText(),
                txtName.getText(),
                Integer.parseInt(txtIFee.getText())
        );

        if (new PaymentController().updateInstallment(i))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        txtIId.clear();txtName.clear();txtIFee.clear();
    }

    public void deleteInstallmentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new PaymentController().deleteInstallment(txtIId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
        txtIId.clear();txtName.clear();txtIFee.clear();
    }

}
