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
import view.tm.TuteTableTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;


public class TuteBookFormController implements Initializable {

    public ComboBox<String> cmbTuteIds;
    public TextField txtTName;
    public TextField txtQtyOnHand;
    public TextField txtRId;
    public TextField txtName;
    public TextField txtQty;
    public TableView<TuteTableTM> tblTuteTable;
    public TableColumn colRId;
    public TableColumn colTId;
    public TableColumn colQty;
    public TableColumn colTName;
    public Button btnGivenTuteBook;
    public Button btnAdd;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern ridPattern = Pattern.compile("^(R-)[0-9]{3,4}$");

    int cartSelectedRowForRemove = -1;

    public void OpenAddNewTuteBookForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddNewTuteBookForm.fxml"));
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
        colTId.setCellValueFactory(new PropertyValueFactory<>("TId"));
        colTName.setCellValueFactory(new PropertyValueFactory<>("TName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));

        //load TuteIds for cmb
        try {
            loadTuteIds();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbTuteIds.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setTuteData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        tblTuteTable.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
    }

    private void storeValidations() {
        map.put(txtRId, ridPattern);
    }

    private void setTuteData(String TuteTId) throws SQLException, ClassNotFoundException {
        TuteBook tuteBook = new TuteBookController().getTute(TuteTId);
        if (tuteBook == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtTName.setText(tuteBook.getName());
            txtQtyOnHand.setText(String.valueOf(tuteBook.getQtyOnHand()));
        }
    }

    private void loadTuteIds() throws SQLException, ClassNotFoundException {
        List<String> TuteTId = new TuteBookController().getTuteIds();
        cmbTuteIds.getItems().addAll(TuteTId);

    }

    //Search RId through data to text Field
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
        txtName.setText(registration.getName());
    }

    ObservableList<TuteTableTM> obList = FXCollections.observableArrayList();

    public void addToTableOnAction(ActionEvent actionEvent) {

        
        String rId = txtRId.getText();
        String tName = txtTName.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int qtyForStudent = Integer.parseInt(txtQty.getText());

        if (qtyOnHand < qtyForStudent) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        TuteTableTM tuteTableTM = new TuteTableTM(
                rId,
                cmbTuteIds.getValue(),
                tName,
                qtyForStudent
        );

        int rowNumber = isExists(tuteTableTM);

        if (rowNumber == -1) {
            obList.add(tuteTableTM);
        } else {
            TuteTableTM temp = obList.get(rowNumber);
            TuteTableTM newTm = new TuteTableTM(
                    temp.getRId(),
                    temp.getTId(),
                    temp.getTName(),
                    temp.getQty() + qtyForStudent
            );

            if (qtyOnHand < temp.getQty()) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }
            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblTuteTable.setItems(obList);
    }

    private int isExists(TuteTableTM tuteTableTM) {
        for (int i = 0; i < obList.size(); i++) {
            if (tuteTableTM.getTId().equals(obList.get(i).getTId())) {
                return i;
            }
        }
        return -1;
    }

    public void ClearOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            tblTuteTable.refresh();
        }
    }

    public void RegisterTuteBookOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<TuteBookDetails> tuteBookDetails = new ArrayList<>();
        for (TuteTableTM tuteTableTM : obList) {
            tuteBookDetails.add(new TuteBookDetails(tuteTableTM.getRId(),tuteTableTM.getTId(),tuteTableTM.getQty()));
        }

        if (new TuteBookDetailsController().saveTuteBookDetail(tuteBookDetails)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
        txtRId.clear();txtName.clear();txtTName.clear();txtQtyOnHand.clear();txtQty.clear();
    }

    public void givenTuteBook(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/GivenTuteReport.jrxml"));
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
               /* new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();*/
            }
        }
    }
}
