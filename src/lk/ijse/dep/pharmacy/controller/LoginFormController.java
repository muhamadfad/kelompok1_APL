package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.db.DBConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings({"Duplicates", "DuplicateExpressions"})
public class LoginFormController implements Initializable {
    public AnchorPane root;
    public AnchorPane arpStage;
    public ImageView imgFront;
    public JFXButton btnSignUp;
    public ImageView ImgBackup;
    public ImageView ImgRestore;
    public Label lblAction;
    public ProgressIndicator pgd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        pgd.setVisible(false);


        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/SignInForm.fxml"));
            arpStage.getChildren().clear();
            arpStage.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/RegisterForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.centerOnScreen();
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void backUpDB_OnMouseClicked(MouseEvent mouseEvent) {


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save the DB Backup");
        fileChooser.getExtensionFilters().
                add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
        File file = fileChooser.showSaveDialog(this.root.getScene().getWindow());
        if (file != null) {

            // Now, we have to backup the DB...
            // Long running task == We have to backup
            this.root.getScene().setCursor(Cursor.WAIT);
            this.pgd.setVisible(true);

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    String[] commands;
                    if (DBConnection.password.length() > 0){
                        commands = new String[]{"mysqldump", "-h", DBConnection.host, "-u", DBConnection.username,
                                "-p" + DBConnection.password,"--port",DBConnection.port, DBConnection.db, "--result-file", file.getAbsolutePath() + ((file.getAbsolutePath().endsWith(".sql")) ? "" : ".sql")};
                    }else{
                        commands = new String[]{"mysqldump", "-h", DBConnection.host, "-u", DBConnection.username, "--port",DBConnection.port,
                                DBConnection.db, "--result-file", file.getAbsolutePath() + ((file.getAbsolutePath().endsWith(".sql")) ? "" : ".sql")};
                    }

                    Process process = Runtime.getRuntime().exec(commands);
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        br.lines().forEach(System.out::println);
                        br.close();
                        throw new RuntimeException("Something went wrong");
                    } else {
                        return null;
                    }
                }
            };

            task.setOnSucceeded(event -> {
                this.pgd.setVisible(false);
                this.root.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.INFORMATION,"Backup process has been done successfully").show();
            });

            task.setOnFailed(event -> {
                this.pgd.setVisible(false);
                this.root.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.ERROR,"Failed to back up. Contact KV").show();
            });

            new Thread(task).start();
        }

    }

    public void restoreDB_OnMouseClicked(MouseEvent mouseEvent) {


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Let's restore the backup");
        fileChooser.getExtensionFilters().
                add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
        File file = fileChooser.showOpenDialog(this.root.getScene().getWindow());
        if (file != null) {

            String[] commands;
            if (DBConnection.password.length() > 0){
                commands = new String[]{"mysql", "-h", DBConnection.host, "-u", DBConnection.username,
                        "-p" + DBConnection.password,"--port",DBConnection.port, DBConnection.db, "-e", "source " + file.getAbsolutePath()};
            }else{
                commands = new String[]{"mysql", "-h", DBConnection.host, "-u", DBConnection.username,"--port",DBConnection.port,
                        DBConnection.db, "-e", "source " + file.getAbsolutePath()};
            }


            this.root.getScene().setCursor(Cursor.WAIT);
            this.pgd.setVisible(true);

            Task task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Process process = Runtime.getRuntime().exec(commands);
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        br.lines().forEach(System.out::println);
                        br.close();
                        throw new RuntimeException("Something went wrong");
                    } else {
                        return null;
                    }
                }
            };

            task.setOnSucceeded(event -> {
                this.pgd.setVisible(false);
                this.root.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.INFORMATION, "Restore process has been successfully done").show();
            });
            task.setOnFailed(event -> {
                this.pgd.setVisible(false);
                this.root.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.ERROR, "Failed to restore the backup. Contact KV").show();
            } );

            new Thread(task).start();
        }

    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "ImgRestore":
                    lblAction.setText("Restore Database");
                    break;
                case "ImgBackup":
                    lblAction.setText("Backup Database");
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
            lblAction.setText("");
        }
    }
}
