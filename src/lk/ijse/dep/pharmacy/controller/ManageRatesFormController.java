package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageRatesFormController implements Initializable {
    public Label lblRate;
    public JFXTextField txtNewRate;
    public JFXButton btnAddRate;
    public JFXButton btnOk;
    public AnchorPane root;
    int rateValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        txtNewRate.setText("2");
        lblRate.setText("2");

        btnOk.setDisable(true);
    }

    public void btnAddRate_OnAction(ActionEvent actionEvent) {

        boolean validRate = txtNewRate.getText().matches("[0-9]{1,2}");

        if(validRate){
            int rate = Integer.parseInt(txtNewRate.getText());
            lblRate.setText(Integer.toString(rate));
            rateValue = rate;
            btnOk.setDisable(false);
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid rate please double check").show();
        }

        if(!validRate){
            txtNewRate.requestFocus();
        }

    }

    public void btnOk_OnAction(ActionEvent actionEvent) {
        boolean validRate = txtNewRate.getText().matches("[0-9]{1,2}");

        if(validRate){
            lblRate.getScene().getWindow().hide();
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid rate please double check").show();
        }

        if(!validRate){
            txtNewRate.requestFocus();
        }


    }


    public void txtNewRate_OnAction(ActionEvent actionEvent) {
        btnAddRate_OnAction(actionEvent);
        btnOk_OnAction(actionEvent);
    }
}
