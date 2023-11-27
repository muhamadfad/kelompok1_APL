package lk.ijse.dep.pharmacy.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.UserBO;
import lk.ijse.dep.pharmacy.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@SuppressWarnings("Duplicates")
public class DashboardFormController implements Initializable {
    public AnchorPane root;
    public AnchorPane arpMainStage;
    public ImageView ImgStock;
    public ImageView ImgGrn;
    public ImageView ImgSupplier;
    public ImageView ImgPerson;
    public ImageView ImgPayment;
    public ImageView ImgPrescription;
    public ImageView ImgReport;
    public ImageView ImgBack;
    public Label lblLoggedUser;
    public Label lblRole;

    private UserBO userBo = BOFactory.getInstance().getBO(BOTypes.USER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/WelcomeDashboardForm.fxml"));
            arpMainStage.getChildren().clear();
            arpMainStage.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getUser(String userId){
        try {
            UserDTO user = userBo.findUser(userId);
            lblLoggedUser.setText("Logged As : " + user.getUserFullName());
            lblRole.setText(user.getUserRole());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void logOut_OnClicked(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Really do you want to Log out from system ?", ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            URL resource = this.getClass().getResource("/lk/ijse/dep/pharmacy/view/LoginForm.fxml");
            Parent root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) (this.root.getScene().getWindow());
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
        }

    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "ImgPrescription":
//                    lblMenu.setText("Manage Customers");
//                    lblDescription.setText("Click to add, edit, delete, search or lk.ijse.dep.pos.view customers");
                    break;
                case "ImgStock":
//                    lblMenu.setText("Manage Items");
//                    lblDescription.setText("Click to add, edit, delete, search or lk.ijse.dep.pos.view items");
                    break;
                case "ImgGrn":
//                    lblMenu.setText("Place Orders");
//                    lblDescription.setText("Click here if you want to place a new order");
                    break;
                case "ImgSupplier":
//                    lblMenu.setText("Search Orders");
//                    lblDescription.setText("Click if you want to search orders");
                    break;
                case "ImgPerson":
//                    lblMenu.setText("Manage Customers");
//                    lblDescription.setText("Click to add, edit, delete, search or lk.ijse.dep.pos.view customers");
                    break;
                case "ImgPayment":
//                    lblMenu.setText("Manage Customers");
//                    lblDescription.setText("Click to add, edit, delete, search or lk.ijse.dep.pos.view customers");
                    break;
                case "ImgReport":
//                    lblMenu.setText("Manage Customers");
//                    lblDescription.setText("Click to add, edit, delete, search or lk.ijse.dep.pos.view customers");
                    break;
            }

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

    @SuppressWarnings("Duplicates")
    public void navigate(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();


            if(lblRole.getText().equalsIgnoreCase("ADMIN") || lblRole.getText().equalsIgnoreCase("MANAGER")){
                switch (icon.getId()) {
                    case "ImgPrescription":
                        changeIcon(ImgPrescription);
                        try {
                            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ManagePrescriptionsForm.fxml"));
                            arpMainStage.getChildren().clear();
                            arpMainStage.getChildren().add(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ImgStock":
                        changeIcon(ImgStock);
                        try {
                            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ManageStockForm.fxml"));
                            arpMainStage.getChildren().clear();
                            arpMainStage.getChildren().add(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ImgGrn":
                        changeIcon(ImgGrn);
                        try {
                            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ManageGrnForm.fxml"));
                            arpMainStage.getChildren().clear();
                            arpMainStage.getChildren().add(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ImgSupplier":
                        changeIcon(ImgSupplier);
                        try {
                            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/supplier/ManageSupplierOrderForm.fxml"));
                            arpMainStage.getChildren().clear();
                            arpMainStage.getChildren().add(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ImgPerson":
                        changeIcon(ImgPerson);
                        try {
                            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ManagePersonForm.fxml"));
                            arpMainStage.getChildren().clear();
                            arpMainStage.getChildren().add(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ImgPayment":
                        changeIcon(ImgPayment);
                        try {
                            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/payment/PaymentViewAllForm.fxml"));
                            arpMainStage.getChildren().clear();
                            arpMainStage.getChildren().add(root);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
//                case "ImgReport":
//                    changeIcon(ImgReport);
//                    try {
//                        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ManageReportForm.fxml"));
//                        arpMainStage.getChildren().clear();
//                        arpMainStage.getChildren().add(root);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;

                }
            }else {
                switch (icon.getId()) {
                    case "ImgPrescription":
                        changeIcon(ImgPrescription);
                        try {
                            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ManagePrescriptionsForm.fxml"));
                            arpMainStage.getChildren().clear();
                            arpMainStage.getChildren().add(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ImgStock":
                        new Alert(Alert.AlertType.INFORMATION,"Sorry!! You don't have enough permission\nto access Stock").show();
                        break;
                    case "ImgGrn":
                        new Alert(Alert.AlertType.INFORMATION,"Sorry!! You don't have enough permission\nto access GRN").show();
                        break;
                    case "ImgSupplier":
                        new Alert(Alert.AlertType.INFORMATION,"Sorry!! You don't have enough permission\nto access Supplier Orders").show();
                        break;
                    case "ImgPerson":
                        new Alert(Alert.AlertType.INFORMATION,"Sorry!! You don't have enough permission\nto access Person Management").show();
                    case "ImgPayment":
                        new Alert(Alert.AlertType.INFORMATION,"Sorry!! You don't have enough permission\nto access Payment Management").show();
                        break;
//                case "ImgReport":
//                    changeIcon(ImgReport);
//                    try {
//                        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ManageReportForm.fxml"));
//                        arpMainStage.getChildren().clear();
//                        arpMainStage.getChildren().add(root);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;

                }
            }



        }
    }

    public void changeIcon(ImageView name) {
        ImageView icon = name;
        Image image;
        switch (icon.getId()) {
            case "ImgPrescription":
                image = new Image("lk/ijse/dep/pharmacy/asset/prescriptionb.png");
                ImgPrescription.setImage(image);
                ImgStock.setDisable(true);
                ImgGrn.setDisable(true);
                ImgSupplier.setDisable(true);
                ImgPerson.setDisable(true);
                ImgPayment.setDisable(true);
                break;
            case "ImgStock":
                image = new Image("lk/ijse/dep/pharmacy/asset/capsuleb.png");
                ImgPrescription.setDisable(true);
                ImgStock.setImage(image);
                ImgGrn.setDisable(true);
                ImgSupplier.setDisable(true);
                ImgPerson.setDisable(true);
                ImgPayment.setDisable(true);
                break;
            case "ImgGrn":
                image = new Image("lk/ijse/dep/pharmacy/asset/certificateb.png");
                ImgPrescription.setDisable(true);
                ImgStock.setDisable(true);
                ImgGrn.setImage(image);
                ImgSupplier.setDisable(true);
                ImgPerson.setDisable(true);
                ImgPayment.setDisable(true);
                break;
            case "ImgSupplier":
                image = new Image("lk/ijse/dep/pharmacy/asset/manufactureb.png");
                ImgPrescription.setDisable(true);
                ImgStock.setDisable(true);
                ImgGrn.setDisable(true);
                ImgSupplier.setImage(image);
                ImgPerson.setDisable(true);
                ImgPayment.setDisable(true);
                break;
            case "ImgPerson":
                image = new Image("lk/ijse/dep/pharmacy/asset/suitb.png");
                ImgPrescription.setDisable(true);
                ImgStock.setDisable(true);
                ImgGrn.setDisable(true);
                ImgSupplier.setDisable(true);
                ImgPerson.setImage(image);
                ImgPayment.setDisable(true);
                break;
            case "ImgPayment":
                image = new Image("lk/ijse/dep/pharmacy/asset/paymentb.png");
                ImgPrescription.setDisable(true);
                ImgStock.setDisable(true);
                ImgGrn.setDisable(true);
                ImgSupplier.setDisable(true);
                ImgPerson.setDisable(true);
                ImgPayment.setImage(image);
                break;


        }
    }

    public void back_OnClicked(MouseEvent mouseEvent) {
        arpMainStage.getChildren().clear();
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/WelcomeDashboardForm.fxml"));
            arpMainStage.getChildren().clear();
            arpMainStage.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImgPrescription.setDisable(false);
        ImgPrescription.setImage(new Image("lk/ijse/dep/pharmacy/asset/prescription.png"));
        ImgStock.setDisable(false);
        ImgStock.setImage(new Image("lk/ijse/dep/pharmacy/asset/capsule.png"));
        ImgGrn.setDisable(false);
        ImgGrn.setImage(new Image("lk/ijse/dep/pharmacy/asset/certificate.png"));
        ImgSupplier.setDisable(false);
        ImgSupplier.setImage(new Image("lk/ijse/dep/pharmacy/asset/manufacture.png"));
        ImgPerson.setDisable(false);
        ImgPerson.setImage(new Image("lk/ijse/dep/pharmacy/asset/suit.png"));
        ImgPayment.setDisable(false);
        ImgPayment.setImage(new Image("lk/ijse/dep/pharmacy/asset/payment.png"));
//        ImgReport.setDisable(false);
//        ImgReport.setImage(new Image("lk/ijse/dep/pharmacy/asset/report.png"));
    }
}
