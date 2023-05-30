package controller;

import animatefx.animation.ZoomIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {

    public AnchorPane PanelRoot;
    public Button btnStudent;
    public Button btnRegistration;
    public Button btnAttendence;
    public Button btnTuteBook;
    public Button btnExamShedule;
    public Button btnPayment;
    public Button btnClassShedule;

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void btnStudentOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/StudentForm.fxml"));
            pane = fxmlLoader.load();
            PanelRoot.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnRegistrationOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/RegistrationForm.fxml"));
            pane = fxmlLoader.load();
            PanelRoot.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnAttendenceOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/AttendenceForm.fxml"));
            pane = fxmlLoader.load();
            PanelRoot.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void btnTuteBookOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/TuteBookForm.fxml"));
            pane = fxmlLoader.load();
            PanelRoot.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnExamSheduleOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ExamSheduleForm.fxml"));
            pane = fxmlLoader.load();
            PanelRoot.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnPaymentOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/PaymentForm.fxml"));
            pane = fxmlLoader.load();
            PanelRoot.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnClassSheduleOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ClassSheduleForm.fxml"));
            pane = fxmlLoader.load();
            PanelRoot.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
