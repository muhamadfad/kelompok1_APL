package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.UserBO;
import lk.ijse.dep.pharmacy.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;
    public Label lblForgetPassword;

    private UserBO userBo = BOFactory.getInstance().getBO(BOTypes.USER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {

        try {
            boolean userName = userBo.findUserName(txtUsername.getText());
            if (userName) {
                UserDTO user = userBo.findUser(txtUsername.getText());
                String password = user.getPassword();

                if(txtPassword.getText().equals(password)){
                    try {

                        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/DashboardForm.fxml"));
                        Parent root = fxmlLoader.load();
//                        root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/DashboardForm.fxml"));
                        if (root != null) {
                            Scene scene = new Scene(root);
                            Stage secondaryStage = (Stage) (this.root.getScene().getWindow());
                            secondaryStage.setScene(scene);
                            secondaryStage.centerOnScreen();

                            DashboardFormController ctrl = fxmlLoader.getController();
                            ctrl.getUser(txtUsername.getText());

                            TranslateTransition tt = new TranslateTransition(Duration.millis(350), scene.getRoot());
                            tt.setFromX(-scene.getWidth());
                            tt.setToX(0);
                            tt.play();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
                    txtPassword.requestFocus();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Username").show();
                txtUsername.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void forgetPassword_OnMouseClicked(MouseEvent mouseEvent) {
        new Alert(Alert.AlertType.INFORMATION, "Please contact KV..   0770670581").show();
    }

    public void userName_OnAction(ActionEvent actionEvent) {
        btnLoginOnAction(actionEvent);
    }

    public void password_OnAction(ActionEvent actionEvent) {
        btnLoginOnAction(actionEvent);
    }
}
