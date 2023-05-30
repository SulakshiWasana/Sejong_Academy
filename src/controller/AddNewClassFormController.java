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
import view.tm.ClassTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddNewClassFormController implements Initializable {

    public TextField txtCId;
    public TextField txtName;
    public TextField txtShedule;
    public TableView <ClassTM>tblClass;
    public TableColumn colCId;
    public TableColumn colName;
    public TableColumn colShedule;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            colCId.setCellValueFactory(new PropertyValueFactory<>("CId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            colShedule.setCellValueFactory(new PropertyValueFactory<>("Shedule"));

            setClassToTable(new ClassController().getAllClass());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setClassToTable(ArrayList<Class> c) {
        ObservableList<ClassTM> obList = FXCollections.observableArrayList();
        c.forEach(e -> {
            obList.add(
                    new ClassTM(e.getCId(), e.getName(), e.getShedule()));
        });
        tblClass.setItems(obList);
    }


    public void SaveClassOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Class c = new Class(
                txtCId.getText(),
                txtName.getText(),
                txtShedule.getText()
        );
        if (new ClassController().SaveClass(c))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        txtCId.clear();txtName.clear();txtShedule.clear();

    }

    public void SelectClassOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String classCId = txtCId.getText();

        Class c = new ClassController().getClass(classCId);
        if (c==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c);
        }
    }

    private void setData(Class c) {
        txtCId.setText(c.getCId());
        txtName.setText(c.getName());
        txtShedule.setText(c.getShedule());
    }


    public void UpdateClassOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Class c = new Class(
                txtCId.getText(),
                txtName.getText(),
                txtShedule.getText()
        );
        if (new ClassController().UpdateClass(c))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        txtCId.clear();txtName.clear();txtShedule.clear();
    }

    public void DeleteClassOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new ClassController().DeleteClass(txtCId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
            txtCId.clear();txtName.clear();txtShedule.clear();
        }
    }

}
