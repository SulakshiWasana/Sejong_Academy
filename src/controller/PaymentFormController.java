package controller;

import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import model.*;
import model.Class;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.AttendenceTM;
import view.tm.ClassRegistrationTM;
import view.tm.PaymentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class PaymentFormController implements Initializable {

    public Label lblDate;
    public Label lblTime;
    public Label lblPId;
    public ComboBox <String>cmbClassIds;
    public TextField txtClassName;
    public ComboBox <String> cmbInstallmentIds;
    public TextField txtInstallmentName;
    public TextField txtInstallmentFee;
    public TextField txtRId;
    public TextField txtRName;
    public TextField txtStatus;
    public TableView <PaymentTM> tblPayment;
    public TableColumn colPId;
    public TableColumn colRId;
    public TableColumn colCId;
    public TableColumn colIId;
    public TableColumn colPaymentDate;
    public TableColumn colPaymentTime;
    public TableColumn colStatus;
    public TextField txtSearch;
    public Button btnPrint;
    public Button btnPayment;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern ridPattern = Pattern.compile("^(R-)[0-9]{3,4}$");

    public void openAddNewInstallmentForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddNewInstallmentForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnPayment.setDisable(true);

        loadDateAndTime();
        setPId();
        storeValidations();

        try {
            colPId.setCellValueFactory(new PropertyValueFactory<>("PId"));
            colRId.setCellValueFactory(new PropertyValueFactory<>("RId"));
            colCId.setCellValueFactory(new PropertyValueFactory<>("CId"));
            colIId.setCellValueFactory(new PropertyValueFactory<>("IId"));
            colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("PaymentDate"));
            colPaymentTime.setCellValueFactory(new PropertyValueFactory<>("PaymentTime"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

            setPaymentsToTable(new PaymentController().getAllPayment());

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

        try {
            loadInstallmentIds();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbInstallmentIds.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setInstallmentData(newValue);
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

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setPId() {
        try {
            lblPId.setText(new PaymentController().getPId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setClassData(String ClassCID) throws SQLException, ClassNotFoundException {
        Class c = new ClassController().getClass(ClassCID);
        if (c == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtClassName.setText(c.getName());
        }
    }

    private void loadClassIds() throws SQLException, ClassNotFoundException {
        List<String> classIds = new PaymentController().getClassIds();
        cmbClassIds.getItems().addAll(classIds);

    }

    private void setInstallmentData(String installmentId) throws SQLException, ClassNotFoundException {
        Installment installment = new PaymentController().getInstallment(installmentId);
        if (installment == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtInstallmentName.setText(installment.getInstallmentName());
            txtInstallmentFee.setText(String.valueOf(installment.getInstallmentFee()));
        }
    }

    private void loadInstallmentIds() throws SQLException, ClassNotFoundException {
        List<String> installmentIds = new PaymentController().getInstallmentIds();
        cmbInstallmentIds.getItems().addAll(installmentIds);

    }

    private void setPaymentsToTable(ArrayList<Payment> paymentsToTable) {
        ObservableList<PaymentTM> obList = FXCollections.observableArrayList();
        paymentsToTable.forEach(e -> {
            obList.add(
                    new PaymentTM(e.getPId(), e.getRId(), e.getCId(), e.getIId(), e.getPaymentDate(), e.getPaymentTime(), e.getStatus()));
        });
        tblPayment.setItems(obList);
    }


    public void searchRegistrationOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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
        txtRName.setText(registration.getName());
    }

    public void makePaymentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Payment payment = new Payment(
                lblPId.getText(),
                txtRId.getText(),
                cmbClassIds.getValue(),
                cmbInstallmentIds.getValue(),
                lblDate.getText(),
                lblTime.getText(),
                txtStatus.getText()
        );

        if(new PaymentController().savePayment(payment)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            setPaymentsToTable(new PaymentController().getAllPayment());
        }
        txtRId.clear();txtRName.clear();txtClassName.clear();txtInstallmentName.clear();txtInstallmentFee.clear();txtStatus.clear();
    }

    public void searchOnAction(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value) {
        try {
            List<Payment> payments = PaymentController.searchPayment(value);
            ObservableList<PaymentTM> tableData = FXCollections.observableArrayList();
            for (Payment payment : payments) {
                tableData.add(new PaymentTM(
                        payment.getPId(),
                        payment.getRId(),
                        payment.getCId(),
                        payment.getIId(),
                        payment.getPaymentDate(),
                        payment.getPaymentTime(),
                        payment.getStatus()
                ));
            }
            // Set Data to  table
            tblPayment.getItems().setAll(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deletePaymentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PaymentTM paymentTM = tblPayment.getSelectionModel().getSelectedItem();
        String RId = paymentTM.getRId();

        if (new PaymentController().deletePayment(RId)) {
            new Alert(Alert.AlertType.INFORMATION, "Delete Complete").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void genarateSqlReportEvent(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/PaymentSlipReport.jrxml"));
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
        Object response = ValidationUtil.validateRegistration(map,btnPayment);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
               /* new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();*/
            }
        }
    }
}
