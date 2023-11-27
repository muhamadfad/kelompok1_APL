package lk.ijse.dep.pharmacy.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("Duplicates")
public class ManagePersonFormController implements Initializable {
    public AnchorPane root;
    public ImageView ImgPatient;
    public ImageView ImgDoctor;
    public ImageView ImgSupplier;
    public AnchorPane arpManagePersonStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void navigate(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "ImgPatient":
                    try {
                        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/patient/ManagePatientForm.fxml"));
                        arpManagePersonStage.getChildren().clear();
                        arpManagePersonStage.getChildren().add(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ImgDoctor":
                    try {
                        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/doctor/ManageDoctorForm.fxml"));
                        arpManagePersonStage.getChildren().clear();
                        arpManagePersonStage.getChildren().add(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ImgSupplier":
                    try {
                        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/supplier/ManageSupplierForm.fxml"));
                        arpManagePersonStage.getChildren().clear();
                        arpManagePersonStage.getChildren().add(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;


            }

        }
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
//            lblMenu.setText("Welcome");
//            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }
}
