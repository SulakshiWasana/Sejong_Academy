package controller;

import db.DbConnection;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.ExamTableTM;
import view.tm.TuteTableTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class ExamSheduleFormController implements Initializable {

    public TextField txtExamTitle;
    public TextField txtDate;
    public ComboBox<String> cmbExamIds;
    public TextField txtName;
    public TextField txtRId;
    public TextField txtMarksForExam;
    public TableView<ExamTableTM> tblExamTable;
    public TableColumn colRId;
    public TableColumn colEId;
    public TableColumn colExamTitle;
    public TableColumn colMarksForExam;
    public Button btnExamRecord;
    public Button btnMarkSheet;
    public Button btnAdd;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern ridPattern = Pattern.compile("^(R-)[0-9]{3,4}$");

    int tableSelectedRowForRemove = -1;

    public void OpenAddNewExamForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddNewExamForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdd.setDisable(true);
        storeValidations();

        colRId.setCellValueFactory(new PropertyValueFactory<>("RId"));
        colEId.setCellValueFactory(new PropertyValueFactory<>("EId"));
        colExamTitle.setCellValueFactory(new PropertyValueFactory<>("ExamTitle"));
        colMarksForExam.setCellValueFactory(new PropertyValueFactory<>("MarksForExam"));

        try {
            loadExamIds();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbExamIds.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setExamData(newValue);
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

    private void setExamData(String EId) throws SQLException, ClassNotFoundException {
        Exam exam = new ExamController().getExam(EId);
        if (exam == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtExamTitle.setText(exam.getExamTitle());
            txtDate.setText(exam.getDate());
        }
    }

    private void loadExamIds() throws SQLException, ClassNotFoundException {
        List<String> examIds = new ExamController().getExamIds();
        cmbExamIds.getItems().addAll(examIds);
    }

    public void SearchRegistrationOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String studentRId = txtRId.getText();
        Registration registration = new RegistrationController().getRegistration(studentRId);
        if (registration == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setDetail(registration);
        }
    }

    private void setDetail(Registration registration) {
        txtRId.setText(registration.getRId());
        txtName.setText(registration.getName());
    }

    ObservableList<ExamTableTM> obList = FXCollections.observableArrayList();

    public void addToTableOnAction(ActionEvent actionEvent) {
        String rId = txtRId.getText();
        String examTitle = txtExamTitle.getText();
        int marksForExam = Integer.parseInt(txtMarksForExam.getText());

        ExamTableTM examTableTM = new ExamTableTM(
                rId,
                cmbExamIds.getValue(),
                examTitle,
                marksForExam
        );

        int rowNumber = isExists(examTableTM);

        if (rowNumber == -1) {
            obList.add(examTableTM);
        } else {
            ExamTableTM temp = obList.get(rowNumber);
            ExamTableTM newTm = new ExamTableTM(
                    temp.getRId(),
                    temp.getEId(),
                    temp.getExamTitle(),
                    temp.getMarksForExam()
            );

            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblExamTable.setItems(obList);
    }

    private int isExists(ExamTableTM examTableTM) {
        for (int i = 0; i < obList.size(); i++) {
            if (examTableTM.getEId().equals(obList.get(i).getEId())) {
                return i;
            }
        }
        return -1;
    }

    public void clearOnAction(ActionEvent actionEvent) {
        if (tableSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obList.remove(tableSelectedRowForRemove);
            tblExamTable.refresh();
        }
    }

    public void sheduleExamOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<ExamDetails> examDetails = new ArrayList<>();
        for (ExamTableTM examTableTM : obList) {
            examDetails.add(new ExamDetails(examTableTM.getRId(),examTableTM.getEId(),examTableTM.getMarksForExam()));
        }

        if (new ExamDetailsController().saveExamDetail(examDetails)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
        txtRId.clear();txtName.clear();txtExamTitle.clear();txtDate.clear();txtMarksForExam.clear();
    }

    public void printMarkSheetEvent(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/printMarkSheet.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            String RID = txtRId.getText();

            HashMap map = new HashMap();
            map.put("Search_ID", RID);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void enterRegistrationEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateRegistration(map,btnAdd);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
              /*  new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();*/
            }
        }
    }
}