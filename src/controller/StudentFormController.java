package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Student;
import view.tm.StudentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    public AnchorPane RegistrationFormContext;
    public TableView<StudentTM> tblStudent;
    public TableColumn colNIC;
    public TableColumn colName;
    public TableColumn colGender;
    public TableColumn colDOB;
    public TableColumn colAge;
    public TableColumn colPassportNo;
    public TableColumn colAddress;
    public TableColumn colContactNo;
    public TableColumn colEmail;
    public TextField txtSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
            colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
            colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
            colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
            colPassportNo.setCellValueFactory(new PropertyValueFactory<>("PassportNo"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
            colContactNo.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));

            setStudentsToTable(new StudentController().getAllStudents());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setStudentsToTable(ArrayList<Student> customers) {
        ObservableList<StudentTM> obList = FXCollections.observableArrayList();
        customers.forEach(e -> {
            obList.add(
                    new StudentTM(e.getNIC(), e.getName(), e.getGender(), e.getDOB(), e.getAge(), e.getPassportNo(), e.getAddress(), e.getContactNo(), e.getEmail()));
        });
        tblStudent.setItems(obList);
    }

    public void OpenAddNewStudent(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddNewStudentForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void OpenUpdateStudentForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/UpdateStudentForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    public void txtSearchOnAction(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value) {
        try {
            List<Student> students = StudentController.searchStudent(value);
            ObservableList<StudentTM> tableData = FXCollections.observableArrayList();
            for (Student student : students) {
                tableData.add(new StudentTM(
                        student.getNIC(),
                        student.getName(),
                        student.getGender(),
                        student.getDOB(),
                        student.getAge(),
                        student.getPassportNo(),
                        student.getAddress(),
                        student.getContactNo(),
                        student.getEmail()
                ));
            }
            // Set Data to  table
            tblStudent.getItems().setAll(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void DeleteStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StudentTM studentTM = tblStudent.getSelectionModel().getSelectedItem();
        String NIC = studentTM.getNIC();

        if (new StudentController().DeleteStudent(NIC)) {
            new Alert(Alert.AlertType.INFORMATION, "Delete Complete").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }
}