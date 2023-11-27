package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.PatientBO;
import lk.ijse.dep.pharmacy.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pharmacy.dto.PatientDTO;
import lk.ijse.dep.pharmacy.util.PatientTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ManagePatientFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtPatientID;
    public JFXTextField txtPatientNic;
    public JFXTextField txtPatientFName;
    public JFXTextField txtPatientLName;
    public Label lblDate;
    public JFXTextField txtPatientAddress;
    public JFXTextField txtPatientMobile;
    public JFXTextField txtPatientLand;
    public JFXTextField txtPatientEmail;
    public JFXComboBox cmbPatientGender;
    public DatePicker dtpPatientDateOfBirth;
    public JFXTextField txtPatientAge;
    public TableView<PatientTM> tblPatientDetails;
    public JFXTextField txtPatientSearch;
    public JFXButton btnAdd;
    public JFXButton btnDelete;
    public JFXButton btnClear;

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

        btnDelete.setDisable(true);
        txtPatientID.setDisable(true);
        generateNewId();

        ObservableList items = cmbPatientGender.getItems();
        items.add("MALE");
        items.add("FEMALE");

        loadTable();

        tblPatientDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PatientTM>() {
            @Override
            public void changed(ObservableValue<? extends PatientTM> observable, PatientTM oldValue, PatientTM newValue) {
                PatientTM selectedItem = tblPatientDetails.getSelectionModel().getSelectedItem();

                System.out.println(selectedItem);

                if (selectedItem == null) {
                    btnAdd.setText("Add");
                    btnDelete.setDisable(true);
                    return;
                }
                btnAdd.setText("Update");
                btnDelete.setDisable(false);

                try {
                    PatientDTO patient = patientBO.findPatient(selectedItem.getNic());

                    String name = patient.getName();
                    String[] split = name.split("\\s+");

                    txtPatientID.setText(patient.getId());
                    txtPatientNic.setText(patient.getNic());
                    txtPatientFName.setText(split[0]);
                    txtPatientLName.setText(split[1]);
                    txtPatientAddress.setText(patient.getAddress());
                    txtPatientMobile.setText(patient.getMobile());
                    txtPatientLand.setText(patient.getLand());
                    txtPatientEmail.setText(patient.getEmail());
                    cmbPatientGender.setValue(patient.getGender());
                    dtpPatientDateOfBirth.setValue(patient.getBirthDate().toLocalDate());
                    txtPatientAge.setText(Integer.toString(patient.getAge()));


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

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

    public void btnAddPatient_OnAction(ActionEvent actionEvent) {

        boolean validNic = txtPatientNic.getText().matches("[0-9]{9}[x|X|v|V]$");
        boolean validFirstName = txtPatientFName.getText().matches("[A-Za-z][A-Za-z. ]+");
        boolean validLastName = txtPatientLName.getText().matches("[A-Za-z][A-Za-z. ]+");
        boolean validAddress = txtPatientAddress.getText().matches("[A-Za-z][A-Za-z. ]+[ , ]*");
        boolean validMobile = txtPatientMobile.getText().matches("0\\d{9}");
        boolean validLand = txtPatientLand.getText().matches("0\\d{9}");
        boolean validEmail = txtPatientEmail.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        boolean validAge = txtPatientAge.getText().matches("[0-9]{1,2}");

        if (validNic && validFirstName && validLastName && validAddress && validMobile && validLand && validEmail && validAge) {
            if (btnAdd.getText().equals("Add")) {
                ObservableList<PatientTM> patients = tblPatientDetails.getItems();
                PatientDTO newPatient = new PatientDTO(
                        txtPatientID.getText(),
                        txtPatientNic.getText(),
                        txtPatientFName.getText() + " " + txtPatientLName.getText(),
                        txtPatientAddress.getText(),
                        txtPatientMobile.getText(),
                        txtPatientLand.getText(),
                        txtPatientEmail.getText(),
                        (String) cmbPatientGender.getSelectionModel().getSelectedItem(),
                        java.sql.Date.valueOf(dtpPatientDateOfBirth.getValue()),
                        Integer.parseInt(txtPatientAge.getText())
                );
                try {
                    patientBO.savePatient(newPatient);
                    patients.add(new PatientTM(newPatient.getId(), newPatient.getName(), newPatient.getAddress(), newPatient.getMobile(), newPatient.getBirthDate().toString()));
                    btnClearPatient_OnAction(actionEvent);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            } else {
                PatientTM selectedPatient = tblPatientDetails.getSelectionModel().getSelectedItem();
                try {
                    patientBO.updatePatient(new PatientDTO(
                            txtPatientID.getText(),
                            selectedPatient.getNic(),
                            txtPatientFName.getText() + " " + txtPatientLName.getText(),
                            txtPatientAddress.getText(),
                            txtPatientMobile.getText(),
                            txtPatientLand.getText(),
                            txtPatientEmail.getText(),
                            (String) cmbPatientGender.getSelectionModel().getSelectedItem(),
                            java.sql.Date.valueOf(dtpPatientDateOfBirth.getValue()),
                            Integer.parseInt(txtPatientAge.getText())
                    ));
                    selectedPatient.setName(txtPatientFName.getText() + " " + txtPatientLName.getText());
                    selectedPatient.setAddress(txtPatientAddress.getText());
                    selectedPatient.setNic(txtPatientNic.getText());
                    selectedPatient.setMobile(txtPatientMobile.getText());
                    selectedPatient.setRegDate(dtpPatientDateOfBirth.getValue().toString());
                    tblPatientDetails.refresh();
                    btnClearPatient_OnAction(actionEvent);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input please double check the highlighted text").show();
        }

        if (!validNic) {
            txtPatientNic.requestFocus();
        } else if (!validFirstName) {
            txtPatientFName.requestFocus();
        } else if (!validLastName) {
            txtPatientLName.requestFocus();
        } else if (!validAddress) {
            txtPatientAddress.requestFocus();
        } else if (!validMobile) {
            txtPatientMobile.requestFocus();
        } else if (!validLand) {
            txtPatientLand.requestFocus();
        } else if (!validEmail) {
            txtPatientEmail.requestFocus();
        } else if (!validAge) {
            txtPatientAge.requestFocus();
        }


    }

    public void btnDeletePatient_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this Patient?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            PatientTM selectedItem = tblPatientDetails.getSelectionModel().getSelectedItem();
            try {
                patientBO.deletePatient(selectedItem.getNic());
                tblPatientDetails.getItems().remove(selectedItem);
                btnClearPatient_OnAction(actionEvent);
            } catch (AlreadyExistsInOrderException e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
            }
        }
    }


    public void btnClearPatient_OnAction(ActionEvent actionEvent) {
        txtPatientID.clear();
        txtPatientNic.clear();
        txtPatientFName.clear();
        txtPatientLName.clear();
        txtPatientAddress.clear();
        txtPatientMobile.clear();
        txtPatientLand.clear();
        txtPatientEmail.clear();
        cmbPatientGender.getSelectionModel().clearSelection();
        dtpPatientDateOfBirth.setValue(null);
        txtPatientAge.clear();
        txtPatientNic.requestFocus();
        btnAdd.setText("Add");
        generateNewId();
        btnDelete.setDisable(true);

    }

    public void generateNewId() {
        int maxId = 0;

        try {
            String lastPatientId = patientBO.getLastPatientId();

            if (lastPatientId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastPatientId.replace("PT", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "PT000" + maxId;
            } else if (maxId < 100) {
                id = "PT00" + maxId;
            } else if (maxId < 1000) {
                id = "PT0" + maxId;
            } else {
                id = "PT" + maxId;
            }
            txtPatientID.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }
}
