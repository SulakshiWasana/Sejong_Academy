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
import model.TuteBook;
import view.tm.ClassTM;
import view.tm.TuteBookTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddNewTuteBookFormController implements Initializable {

    public TextField txtTId;
    public TextField txtName;
    public TextField txtQtyOnHand;
    public TableView tblTuteBook;
    public TableColumn colTId;
    public TableColumn colTuteName;
    public TableColumn colQtyOnHand;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            colTId.setCellValueFactory(new PropertyValueFactory<>("TId"));
            colTuteName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));

            setTuteToTable(new TuteBookController().getAllTute());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTuteToTable(ArrayList<TuteBook> tuteBooks) {
        ObservableList<TuteBookTM> obList = FXCollections.observableArrayList();
        tuteBooks.forEach(e -> {
            obList.add(
                    new TuteBookTM(e.getTId(), e.getName(), e.getQtyOnHand()));
        });
        tblTuteBook.setItems(obList);
    }

    public void SaveTuteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        TuteBook tuteBook = new TuteBook(
                txtTId.getText(),
                txtName.getText(),
                Integer.parseInt(txtQtyOnHand.getText())
        );
        if (new TuteBookController().SaveTute(tuteBook))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        txtTId.clear();txtName.clear();txtQtyOnHand.clear();
    }

    public void SelectTuteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String tuteTId = txtTId.getText();
        TuteBook tuteBook = new TuteBookController().getTute(tuteTId);
        if (tuteBook==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(tuteBook);
        }
    }

    private void setData(TuteBook tuteBook) {
        txtTId.setText(tuteBook.getTId());
        txtName.setText(tuteBook.getName());
        txtQtyOnHand.setText(String.valueOf(tuteBook.getQtyOnHand()));
    }

    public void UpdateTuteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        TuteBook tuteBook = new TuteBook(
                txtTId.getText(),
                txtName.getText(),
                Integer.parseInt(txtQtyOnHand.getText())
        );
        if (new TuteBookController().UpdateTute(tuteBook))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        txtTId.clear();txtName.clear();txtQtyOnHand.clear();
    }

    public void DeleteTuteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new TuteBookController().DeleteTute(txtTId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
            txtTId.clear();txtName.clear();txtQtyOnHand.clear();
        }
    }
}
