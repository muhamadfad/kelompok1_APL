package lk.ijse.dep.pharmacy.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class WelComeDashboardFormController implements Initializable {
    public AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }
}
