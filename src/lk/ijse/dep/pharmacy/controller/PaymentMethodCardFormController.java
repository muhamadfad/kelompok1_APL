package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentMethodCardFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtCardPayNetTotal;
    public Label lblCardNum;
    public Label lblDiscount;
    public Label lblBalance;
    public Label lblCardName;
    public Label lblCardDigit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void btnPayBill(ActionEvent actionEvent) {
    }

    public void btnViewBill(ActionEvent actionEvent) {
    }
}
