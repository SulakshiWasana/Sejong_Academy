package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;
import model.Class;
import util.ValidationUtil;
import view.tm.AttendenceTM;
import view.tm.ClassRegistrationTM;
import view.tm.ClassTM;
import view.tm.StudentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ClassSheduleFormController implements Initializable {

    public ComboBox<String> cmbClassIds;
    public TextField txtName;
    public TextField txtShedule;
    public TextField txtRName;
    public TextField txtRId;
    public TableView <ClassRegistrationTM>tblClassRegistration;
    public TableColumn colCId;
    public TableColumn colRId;
    public TableColumn colName;
    public TableColumn colCName;
    public TextField txtSearch;
    public Label lbNum;
    public Button btnAdd;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern ridPattern = Pattern.compile("^(R-)[0-9]{3,4}$");

    public void OpenAddNewClassForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddNewClassForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdd.setDisable(true);

        setNum();
        storeValidations();

        try {
            colRId.setCellValueFactory(new PropertyValueFactory<>("RId"));
            colCId.setCellValueFactory(new PropertyValueFactory<>("CId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
            colCName.setCellValueFactory(new PropertyValueFactory<>("ClassName"));

            setClassRegistationToTable(new ClassController().getAllClassRegistration());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            loadClassIds();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbClassIds.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setClassData(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

    }

    private void storeValidations() {
        map.put(txtRId, ridPattern);
    }

    private void setClassRegistationToTable(ArrayList<ClassRegistration> allClassRegistration) {
        ObservableList<ClassRegistrationTM> obList = FXCollections.observableArrayList();
        allClassRegistration.forEach(e -> {
            obList.add(
                    new ClassRegistrationTM(e.getRId(), e.getCId(), e.getStudentName(), e.getClassName()));
        });
        tblClassRegistration.setItems(obList);
    }


    private void setClassData(String ClassCID) throws SQLException, ClassNotFoundException {
        Class c = new ClassController().getClass(ClassCID);
        if (c == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtName.setText(c.getName());
            txtShedule.setText(c.getShedule());
        }
    }

    private void loadClassIds() throws SQLException, ClassNotFoundException {
            List<String> classIds = new ClassController().getClassIds();
            cmbClassIds.getItems().addAll(classIds);

    }

    private void setNum() {
        try {
            lbNum.setText(new ClassController().getNum());

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
        txtRName.setText(registration.getName());
    }

    public void SaveClassRegistrationOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ClassRegistration classRegistration = new ClassRegistration(
                lbNum.getText(),
                txtRId.getText(),
                cmbClassIds.getValue(),
                txtRName.getText(),
                txtName.getText()
        );

        if (new ClassController().SaveClassRegistration(classRegistration)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setClassRegistationToTable(new ClassController().getAllClassRegistration());
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
        txtRId.clear();txtRName.clear();txtName.clear();txtShedule.clear();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value) {
        try {
            List<ClassRegistration> classRegistrations = ClassController.searchClass(value);
            ObservableList<ClassRegistrationTM> tableData = FXCollections.observableArrayList();
            for (ClassRegistration classRegistration : classRegistrations) {
                tableData.add(new ClassRegistrationTM(
                        classRegistration.getRId(),
                        classRegistration.getCId(),
                        classRegistration.getStudentName(),
                        classRegistration.getClassName()
                ));
            }
            // Set Data to  table
            tblClassRegistration.getItems().setAll(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void DeleteClassOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ClassRegistrationTM classRegistrationTM = tblClassRegistration.getSelectionModel().getSelectedItem();
        String RId = classRegistrationTM.getRId();

        if (new ClassController().DeleteClass(RId)) {
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
