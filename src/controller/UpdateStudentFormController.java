package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Student;
import util.ValidationUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UpdateStudentFormController implements Initializable {

    public TextField txtNIC;
    public TextField txtName;
    public TextField txtGender;
    public TextField txtDOB;
    public TextField txtAge;
    public TextField txtPassportNo;
    public TextField txtAddress;
    public TextField txtContactNo;
    public TextField txtEmail;
    public Button btnUpdateStudent;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern nicPattern = Pattern.compile("^([0-9]{9}[x|X|v|V]|[0-9]{12})$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnUpdateStudent.setDisable(true);
        storeValidations();

    }

    private void storeValidations() {
        map.put(txtNIC, nicPattern);
    }

    public void UpdateStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Student student = new Student(
                txtNIC.getText(),
                txtName.getText(),
                txtGender.getText(),
                txtDOB.getText(),
                txtAge.getText(),
                txtPassportNo.getText(),
                txtAddress.getText(),
                txtContactNo.getText(),
                txtEmail.getText()
        );
        if (new StudentController().UpdateStudent(student))
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        txtNIC.clear();txtName.clear();txtGender.clear();txtDOB.clear();txtAge.clear();
        txtPassportNo.clear();txtAddress.clear();txtContactNo.clear();txtEmail.clear();
    }

    public void selectStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String studentNIC = txtNIC.getText();
        Student student = new StudentController().getStudent(studentNIC);
        if (student == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(student);
        }
    }

    private void setData(Student student) {
        txtNIC.setText(student.getNIC());
        txtName.setText(student.getName());
        txtGender.setText(student.getGender());
        txtDOB.setText(student.getDOB());
        txtAge.setText(student.getAge());
        txtPassportNo.setText(student.getPassportNo());
        txtAddress.setText(student.getAddress());
        txtContactNo.setText(student.getContactNo());
        txtEmail.setText(student.getEmail());
    }

    public void ClearStudentOnAction(ActionEvent actionEvent) {
        txtNIC.clear();
        txtName.clear();
        txtGender.clear();
        txtDOB.clear();
        txtAge.clear();
        txtPassportNo.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtEmail.clear();
    }

    public void textField_KeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnUpdateStudent);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Searching Successful..").showAndWait();
            }
        }
    }
}
