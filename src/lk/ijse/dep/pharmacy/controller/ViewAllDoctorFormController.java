package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.DoctorBO;
import lk.ijse.dep.pharmacy.dto.DoctorDTO;
import lk.ijse.dep.pharmacy.util.DoctorTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ViewAllDoctorFormController implements Initializable {
    public Label lblDate;
    public JFXTextField txtDoctorSearch;
    public TableView<DoctorTM> tblDoctorDetails;
    public AnchorPane root;

    private DoctorBO doctorBO = BOFactory.getInstance().getBO(BOTypes.DOCTOR);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        lblDate.setText(LocalDate.now() + "");

        tblDoctorDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblDoctorDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblDoctorDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("specialization"));
        tblDoctorDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("mobile"));
        tblDoctorDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("regHospital"));


        loadTable();

        txtDoctorSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });
    }

    public void loadTable() {
        tblDoctorDetails.getItems().clear();
        try {
            List<DoctorDTO> allDoctors = doctorBO.searchDoctor(txtDoctorSearch.getText());
            ObservableList<DoctorTM> doctors = tblDoctorDetails.getItems();
            for (DoctorDTO d : allDoctors) {
                doctors.add(new DoctorTM(d.getId(), d.getName(), d.getSpecialization(), d.getMobile(), d.getRegHospital()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void tblDoctorDetails_OnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.hide();

        }
    }


}
