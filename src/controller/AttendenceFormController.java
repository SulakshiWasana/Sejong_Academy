package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Attendence;
import model.Registration;
import model.Student;
import util.ValidationUtil;
import view.tm.AttendenceTM;
import view.tm.RegistrationTM;
import view.tm.StudentTM;


import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class AttendenceFormController implements Initializable {
    public Label lblDate;
    public TextField txtName;
    public TextField txtRId;
    public TextField txtmark;
    public Label lblAId;
    public TableView<AttendenceTM> tblAttendence;
    public TableColumn colRId;
    public TableColumn colName;
    public TableColumn colAId;
    public TableColumn colDate;
    public TableColumn colStatus;
    public TextField txtSearch;
    public Button btnAdd;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern ridPattern = Pattern.compile("^(R-)[0-9]{3,4}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdd.setDisable(true);

        loadDate();
        setAId();
        storeValidations();

        try {
            colRId.setCellValueFactory(new PropertyValueFactory<>("RId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

            setAttendenceToTable(new AttendenceController().getAllAttendence());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void storeValidations() {
        map.put(txtRId, ridPattern);
    }

    private void setAttendenceToTable(ArrayList<Attendence> attendence) {
        ObservableList<AttendenceTM> obList = FXCollections.observableArrayList();
        attendence.forEach(e -> {
            obList.add(
                    new AttendenceTM(e.getRId(), e.getAId(), e.getName(), e.getDate(), e.getStatus()));
        });
        tblAttendence.setItems(obList);
    }

    private void loadDate(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("         yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    private void setAId() {
        try {
            lblAId.setText(new AttendenceController().getAId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void SearchRegistrationOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String studentRId = txtRId.getText();

        Registration registration = new RegistrationController().getRegistration(studentRId);
        if (registration==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setDetail(registration);
        }
    }

    private void setDetail(Registration registration){
        txtRId.setText(registration.getRId());
        txtName.setText(registration.getName());
    }

    public void MarkAttendenceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Attendence attendence = new Attendence(
                txtRId.getText(),
                lblAId.getText(),
                txtName.getText(),
                lblDate.getText(),
                txtmark.getText()
        );
        if (new AttendenceController().AttendentStudent(attendence)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setAttendenceToTable(new AttendenceController().getAllAttendence());
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
        txtRId.clear();txtName.clear();txtmark.clear();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value) {
        try {
            List<Attendence> attendences = AttendenceController.searchAttendence(value);
            ObservableList<AttendenceTM> tableData = FXCollections.observableArrayList();
            for (Attendence attendence : attendences) {
                tableData.add(new AttendenceTM(
                        attendence.getRId(),
                        attendence.getName(),
                        attendence.getAId(),
                        attendence.getDate(),
                        attendence.getStatus()
                ));
            }
            // Set Data to  table
            tblAttendence.getItems().setAll(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void DeleteAttendenceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        AttendenceTM attendenceTM = tblAttendence.getSelectionModel().getSelectedItem();
        String RId = attendenceTM.getRId();

        if (new AttendenceController().DeleteAttendence(RId)) {
            new Alert(Alert.AlertType.INFORMATION, "Delete Complete").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void enterRegistrationEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateRegistration(map,btnAdd);
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

