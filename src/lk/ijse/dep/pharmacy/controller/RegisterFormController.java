package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.UserBO;
import lk.ijse.dep.pharmacy.dto.UserDTO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class RegisterFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtRegName;
    public JFXTextField txtRegUsername;
    public JFXPasswordField txtPassword;
    public JFXComboBox<String> cmbRegRoll;
    public JFXTextField txtEmail;
    public JFXPasswordField txtRePassword;
    public JFXButton btnSignUp;
    public JFXTextField txtNic;
    public Label lblValid;
    public Label lblInValid;


    private UserBO userBo = BOFactory.getInstance().getBO(BOTypes.USER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();


//        userId();

        getUserRoles();
    }

    public void userId() {
        int maxId = 0;

        try {
            String lastUserId = userBo.getLastUserId();

            if (lastUserId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastUserId.replace("USR", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "USR000" + maxId;
            } else if (maxId < 100) {
                id = "USR00" + maxId;
            } else if (maxId < 1000) {
                id = "USR0" + maxId;
            } else {
                id = "USR" + maxId;
            }
//            txtRegId.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        boolean validName = txtRegName.getText().matches("[A-Za-z][A-Za-z. ]+");
        boolean validUserName = txtRegUsername.getText().matches("[A-Za-z][A-Za-z. ]+");
        boolean validNic = txtNic.getText().matches("[0-9]{9}[x|X|v|V]$");
        boolean validPassword = txtPassword.getText().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        boolean validRePass = txtRePassword.getText().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        boolean validEmail = txtEmail.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

        if (validName && validNic && validEmail && validUserName && validPassword && validRePass) {
            if (txtPassword.getText().equals(txtRePassword.getText())) {
                UserDTO newUser = new UserDTO(
                        txtRegUsername.getText(),
                        txtRegName.getText(),
                        txtNic.getText(),
                        cmbRegRoll.getSelectionModel().getSelectedItem(),
                        txtEmail.getText(),
                        txtRegUsername.getText(),
                        txtRePassword.getText());

                try {
                    userBo.saveUser(newUser);

                    txtRegUsername.getScene().getWindow().hide();

                    txtRegName.clear();
                    txtNic.clear();
                    cmbRegRoll.getSelectionModel().clearSelection();
                    txtEmail.clear();
                    txtRegUsername.clear();
                    txtPassword.clear();
                    txtRePassword.clear();
                    lblInValid.setText("");
                    lblValid.setText("");
                    getUserRoles();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Passwords you have entered is not matching").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input please double check the highlighted text").show();
        }

        if (!validName) {
            txtRegName.requestFocus();
        } else if (!validNic) {
            txtNic.requestFocus();
        } else if (!validEmail) {
            txtEmail.requestFocus();
        } else if (!validUserName) {
            txtRegUsername.requestFocus();
        } else if (!validPassword) {
            txtPassword.requestFocus();
        } else if (!validRePass) {
            txtRePassword.requestFocus();
        }
    }

    public void getUserRoles() {
        try {
            List<String> ids = userBo.getAllUserRoles();
            cmbRegRoll.setItems(FXCollections.observableArrayList(ids));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void password_OnKeyPressed(KeyEvent keyEvent) {

    }

    public void userName_KeyRelease(KeyEvent keyEvent) {
        try {
            boolean userName = userBo.findUserName(txtRegUsername.getText());
            if (userName) {
                lblValid.setText("");
                lblInValid.setText("Username Already Used!!");
            } else {
                lblInValid.setText("");
                lblValid.setText("OK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
