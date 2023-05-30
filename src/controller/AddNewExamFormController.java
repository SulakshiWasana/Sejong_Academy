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
import model.Exam;
import view.tm.ClassTM;
import view.tm.ExamTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddNewExamFormController implements Initializable {

    public TextField txtEId;
    public TextField txtName;
    public TextField txtDate;
    public TableView tblExam;
    public TableColumn colEId;
    public TableColumn colName;
    public TableColumn colDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            colEId.setCellValueFactory(new PropertyValueFactory<>("EId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("ExamTitle"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

            setExamToTable(new ExamController().getAllExam());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setExamToTable(ArrayList<Exam> exam) {
        ObservableList<ExamTM> obList = FXCollections.observableArrayList();
        exam.forEach(e -> {
            obList.add(
                    new ExamTM(e.getEId(), e.getExamTitle(), e.getDate()));
        });
        tblExam.setItems(obList);
    }

    public void SaveExamOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Exam exam = new Exam(
                txtEId.getText(),
                txtName.getText(),
                txtDate.getText()
        );
        if (new ExamController().SaveExam(exam))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        txtEId.clear();txtName.clear();txtDate.clear();
    }

    public void SelectExamOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String examEId = txtEId.getText();
        Exam exam = new ExamController().getExam(examEId);
        if (exam==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(exam);
        }
    }

    private void setData(Exam exam) {
        txtEId.setText(exam.getEId());
        txtName.setText(exam.getExamTitle());
        txtDate.setText(exam.getDate());
    }

    public void UpdateExamOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Exam exam = new Exam(
                txtEId.getText(),
                txtName.getText(),
                txtDate.getText()
        );
        if (new ExamController().UpdateExam(exam))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        txtEId.clear();txtName.clear();txtDate.clear();
    }

    public void DeleteExamOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new ExamController().DeleteExam(txtEId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
            txtEId.clear();txtName.clear();txtDate.clear();
        }
    }

}
