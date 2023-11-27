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
import lk.ijse.dep.pharmacy.business.custom.PatientBO;
import lk.ijse.dep.pharmacy.dto.PatientDTO;
import lk.ijse.dep.pharmacy.util.PatientTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ViewAllPatientFormController implements Initializable {
    public Label lblDate;
    public JFXTextField txtPatientSearch;
    public TableView<PatientTM> tblPatientDetails;
    public AnchorPane root;

    private PatientBO patientBO = BOFactory.getInstance().getBO(BOTypes.PATIENT);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        lblDate.setText(LocalDate.now() + "");

        tblPatientDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblPatientDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblPatientDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblPatientDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("mobile"));
        tblPatientDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("regDate"));

        loadTable();

        txtPatientSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });

    }
    public void loadTable() {
        tblPatientDetails.getItems().clear();
        try {
            List<PatientDTO> allPatients = patientBO.searchPatient(txtPatientSearch.getText());
            ObservableList<PatientTM> patients = tblPatientDetails.getItems();
            for (PatientDTO p : allPatients) {
                patients.add(new PatientTM(p.getId(), p.getName(), p.getAddress(), p.getMobile(), p.getBirthDate().toString()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void tblPatientDetails_OnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.hide();

        }
    }


}
