package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Registration;
import model.Student;
import util.ValidationUtil;
import view.tm.RegistrationTM;
import view.tm.StudentTM;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegistrationFormController implements Initializable {
    public TextField txtNIC;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtAge;
    public TextField txtPassportNo;
    public Label lblDate;
    public Label lblRId;
    public TextField txtRegistrationFee;
    public TableView<RegistrationTM> tblRegistration;
    public TableColumn colNIC;
    public TableColumn colRId;
    public TableColumn colDate;
    public TableColumn colFee;
    public TableColumn colName;
    public Button btnRegister;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern nicPattern = Pattern.compile("^([0-9]{9}[x|X|v|V]|[0-9]{12})$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnRegister.setDisable(true);

        loadDate();
        setRId();
        storeValidations();

        try {
            colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
            colRId.setCellValueFactory(new PropertyValueFactory<>("RId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("RegistrationDate"));
            colFee.setCellValueFactory(new PropertyValueFactory<>("RegistrationFee"));

            setRegistrationToTable(new RegistrationController().getAllRegistration());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void storeValidations() {
        map.put(txtNIC, nicPattern);
    }

    private void setRegistrationToTable(ArrayList<Registration> registration) {
        ObservableList<RegistrationTM> obList = FXCollections.observableArrayList();
        registration.forEach(e -> {
            obList.add(
                    new RegistrationTM(e.getNIC(), e.getRId(), e.getName(), e.getRegistrationDate(), e.getRegistrationFee()));
        });
        tblRegistration.setItems(obList);
    }

    public void SearchStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String studentNIC = txtNIC.getText();
        Student student = new StudentController().getStudent(studentNIC);
        if (student==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(student);
        }
    }

    private void setData(Student student) {
        txtNIC.setText(student.getNIC());
        txtName.setText(student.getName());
        txtAddress.setText(student.getAddress());
        txtAge.setText(student.getAge());
        txtPassportNo.setText(student.getPassportNo());
    }

    public void RegisterStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Registration registration = new Registration(
                txtNIC.getText(),
                lblRId.getText(),
                txtName.getText(),
                lblDate.getText(),
                txtRegistrationFee.getText()
        );

        if (new RegistrationController().RegisterStudent(registration)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setRegistrationToTable(new RegistrationController().getAllRegistration());
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
        txtNIC.clear();txtName.clear();txtAddress.clear();txtAge.clear();txtPassportNo.clear();txtRegistrationFee.clear();
    }

    private void loadDate(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("         yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    private void setRId() {
        try {
            lblRId.setText(new RegistrationController().getRId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void DeleteRegisterStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        RegistrationTM registrationTM = tblRegistration.getSelectionModel().getSelectedItem();
        String RId = registrationTM.getRId();

        if (new RegistrationController().DeleteRegisterStudent(RId)) {
            new Alert(Alert.AlertType.INFORMATION, "Delete Complete").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void enterNICEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateRegistration(map,btnRegister);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
             /*   new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();*/
            }
        }
    }
}
