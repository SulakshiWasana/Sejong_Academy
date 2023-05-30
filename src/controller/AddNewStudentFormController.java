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

public class AddNewStudentFormController implements Initializable {

    public TextField txtNIC;
    public TextField txtName;
    public TextField txtGender;
    public TextField txtDOB;
    public TextField txtAge;
    public TextField txtPassportNo;
    public TextField txtAddress;
    public TextField txtContactNo;
    public TextField txtEmail;
    public Button btnSaveStudent;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern nicPattern = Pattern.compile("^([0-9]{9}[x|X|v|V]|[0-9]{12})$");
    Pattern namePattern = Pattern.compile("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
    Pattern genderPattern = Pattern.compile("^M(ale)?$|^F(emale)?$");
    Pattern dobPattern = Pattern.compile("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$");
    Pattern agePattern = Pattern.compile("^100|[1-9]?\\d$");
    Pattern passportNoPattern = Pattern.compile("^[A-Z]{1}[0-9]{7}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ,]{6,30}[.]?$");
    Pattern contactNoPattern = Pattern.compile("^([+]\\d{2})?\\d{10}$");
    Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSaveStudent.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtNIC,nicPattern);
        map.put(txtName,namePattern);
        map.put(txtGender,genderPattern);
        map.put(txtDOB,dobPattern);
        map.put(txtAge,agePattern);
        map.put(txtPassportNo,passportNoPattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContactNo,contactNoPattern);
        map.put(txtEmail,emailPattern);
    }

    public void SaveStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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
        if (new StudentController().SaveStudent(student)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
            txtNIC.clear();txtName.clear();txtGender.clear();txtDOB.clear();txtAge.clear();
            txtPassportNo.clear();txtAddress.clear();txtContactNo.clear();txtEmail.clear();
        }
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
        Object response = ValidationUtil.validate(map,btnSaveStudent);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField){
                TextField errorText =(TextField) response;
                errorText.requestFocus();
            }else if (response instanceof Boolean){
                new Alert(Alert.AlertType.INFORMATION,"Added").showAndWait();
            }
        }
    }
}







 /* if (keyEvent.getCode() == KeyCode.ENTER) {

            String typedNIC = txtNIC.getText();
            if (nicPattern.matcher(typedNIC).matches()) {
                txtNIC.getParent().setStyle("-fx-border-color: green");
                txtName.requestFocus();

                String typedName = txtName.getText();
                if (namePattern.matcher(typedName).matches()) {
                    txtName.getParent().setStyle("-fx-border-color: green");
                    txtGender.requestFocus();

                    String typedGender = txtGender.getText();
                    if (genderPattern.matcher(typedGender).matches()) {
                        txtGender.getParent().setStyle("-fx-border-color: green");
                        txtDOB.requestFocus();

                        String typedDOB = txtDOB.getText();
                        if (dobPattern.matcher(typedDOB).matches()) {
                            txtDOB.getParent().setStyle("-fx-border-color: green");
                            txtAge.requestFocus();

                            String typedAge = txtAge.getText();
                            if (agePattern.matcher(typedAge).matches()) {
                                txtAge.getParent().setStyle("-fx-border-color: green");
                                txtPassportNo.requestFocus();

                                String typedPassportNo = txtPassportNo.getText();
                                if (passportNoPattern.matcher(typedPassportNo).matches()) {
                                    txtPassportNo.getParent().setStyle("-fx-border-color: green");
                                    txtAddress.requestFocus();

                                    String typedAddress = txtAddress.getText();
                                    if (addressPattern.matcher(typedAddress).matches()) {
                                        txtAddress.getParent().setStyle("-fx-border-color: green");
                                        txtContactNo.requestFocus();

                                        String typedContactNo = txtContactNo.getText();
                                        if (contactNoPattern.matcher(typedContactNo).matches()) {
                                            txtContactNo.getParent().setStyle("-fx-border-color: green");
                                            txtEmail.requestFocus();

                                            String typedEmail = txtEmail.getText();
                                            if (emailPattern.matcher(typedEmail).matches()) {
                                                txtEmail.getParent().setStyle("-fx-border-color: green");
                                                btnSaveStudent.requestFocus();
                                            } else {
                                                txtEmail.getParent().setStyle("-fx-border-color: red");
                                                txtEmail.requestFocus();
                                            }

                                        } else {
                                            txtContactNo.getParent().setStyle("-fx-border-color: red");
                                            txtContactNo.requestFocus();
                                        }


                                    } else {
                                        txtAddress.getParent().setStyle("-fx-border-color: red");
                                        txtAddress.requestFocus();
                                    }

                                } else {
                                    txtPassportNo.getParent().setStyle("-fx-border-color: red");
                                    txtPassportNo.requestFocus();
                                }

                            } else {
                                txtAge.getParent().setStyle("-fx-border-color: red");
                                txtAge.requestFocus();
                            }

                        } else {
                            txtDOB.getParent().setStyle("-fx-border-color: red");
                            txtDOB.requestFocus();
                        }


                    } else {
                        txtGender.getParent().setStyle("-fx-border-color: red");
                        txtGender.requestFocus();
                    }

                } else {
                    txtName.getParent().setStyle("-fx-border-color: red");
                    txtName.requestFocus();
                }

            } else {
                txtNIC.getParent().setStyle("-fx-border-color: red");
                txtNIC.requestFocus();
            }
        }*/