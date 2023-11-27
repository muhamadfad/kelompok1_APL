package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import lk.ijse.dep.pharmacy.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pharmacy.dto.DoctorDTO;
import lk.ijse.dep.pharmacy.util.DoctorTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ManageDoctorFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtDoctorID;
    public JFXTextField txtDoctorNic;
    public JFXTextField txtDoctorFName;
    public JFXTextField txtDoctorLName;
    public JFXTextField txtDoctorMobile;
    public JFXTextField txtDoctorLand;
    public JFXTextField txtDoctorEmail;
    public Label lblDate;
    public JFXTextField txtDoctorSearch;
    public TableView<DoctorTM> tblDoctorDetails;
    public JFXTextField txtDoctorSpecalization;
    public JFXTextField txtDoctorRegHospital;
    public JFXButton btnClear;
    public JFXTextField txtDoctorAddress;
    public JFXButton btnAdd;
    public JFXButton btnDelete;

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

        btnDelete.setDisable(true);
        txtDoctorID.setDisable(true);
        generateNewId();

        loadTable();

        tblDoctorDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DoctorTM>() {
            @Override
            public void changed(ObservableValue<? extends DoctorTM> observable, DoctorTM oldValue, DoctorTM newValue) {
                DoctorTM selectedItem = tblDoctorDetails.getSelectionModel().getSelectedItem();

                System.out.println(selectedItem);

                if (selectedItem == null) {
                    btnAdd.setText("Add");
                    btnDelete.setDisable(true);
                    return;
                }
                btnAdd.setText("Update");
                btnDelete.setDisable(false);

                try {
                    DoctorDTO doctor = doctorBO.findDoctor(selectedItem.getNic());

                    String name = doctor.getName();
                    String[] split = name.split("\\s+");

                    txtDoctorID.setText(doctor.getId());
                    txtDoctorNic.setText(doctor.getNic());
                    txtDoctorFName.setText(split[0]);
                    txtDoctorLName.setText(split[1]);
                    txtDoctorAddress.setText(doctor.getAddress());
                    txtDoctorMobile.setText(doctor.getMobile());
                    txtDoctorLand.setText(doctor.getLand());
                    txtDoctorEmail.setText(doctor.getEmail());
                    txtDoctorSpecalization.setText(doctor.getSpecialization());
                    txtDoctorRegHospital.setText(doctor.getRegHospital());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

    public void btnAddDoctor_OnAction(ActionEvent actionEvent) {
        boolean validNic = txtDoctorNic.getText().matches("[0-9]{9}[x|X|v|V]$");
        boolean validFirstName = txtDoctorFName.getText().matches("[A-Za-z][A-Za-z. ]+");
        boolean validLastName = txtDoctorLName.getText().matches("[A-Za-z][A-Za-z. ]+");
        boolean validAddress = txtDoctorAddress.getText().matches("[A-Za-z][A-Za-z. ]+[ , ]*");
        boolean validMobile = txtDoctorMobile.getText().matches("0\\d{9}");
        boolean validLand = txtDoctorLand.getText().matches("0\\d{9}");
        boolean validEmail = txtDoctorEmail.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        boolean validRegHospital = txtDoctorRegHospital.getText().matches("[A-Za-z][A-Za-z. ]+");
        boolean validSpecialization = txtDoctorSpecalization.getText().matches("[A-Za-z][A-Za-z. ]+");

        if (validNic && validFirstName && validLastName && validAddress && validMobile && validLand && validEmail && validSpecialization && validRegHospital) {
            if (btnAdd.getText().equals("Add")) {
                ObservableList<DoctorTM> doctors = tblDoctorDetails.getItems();
                DoctorDTO newDoctor = new DoctorDTO(
                        txtDoctorID.getText(),
                        txtDoctorNic.getText(),
                        txtDoctorFName.getText() + " " + txtDoctorLName.getText(),
                        txtDoctorAddress.getText(),
                        txtDoctorMobile.getText(),
                        txtDoctorLand.getText(),
                        txtDoctorEmail.getText(),
                        txtDoctorSpecalization.getText(),
                        txtDoctorRegHospital.getText()
                );
                try {
                    doctorBO.saveDoctor(newDoctor);
                    doctors.add(new DoctorTM(newDoctor.getId(), newDoctor.getName(), newDoctor.getSpecialization(), newDoctor.getMobile(), newDoctor.getRegHospital()));
                    btnClearPatient_OnAction(actionEvent);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            } else {
                DoctorTM selectedDoctor = tblDoctorDetails.getSelectionModel().getSelectedItem();
                try {
                    doctorBO.updateDoctor(new DoctorDTO(
                            txtDoctorID.getText(),
                            selectedDoctor.getNic(),
                            txtDoctorFName.getText() + " " + txtDoctorLName.getText(),
                            txtDoctorAddress.getText(),
                            txtDoctorMobile.getText(),
                            txtDoctorLand.getText(),
                            txtDoctorEmail.getText(),
                            txtDoctorSpecalization.getText(),
                            txtDoctorRegHospital.getText()
                    ));
                    selectedDoctor.setName(txtDoctorFName.getText() + " " + txtDoctorLName.getText());
                    selectedDoctor.setSpecialization(txtDoctorSpecalization.getText());
                    selectedDoctor.setNic(txtDoctorNic.getText());
                    selectedDoctor.setMobile(txtDoctorMobile.getText());
                    selectedDoctor.setRegHospital(txtDoctorRegHospital.getText());
                    tblDoctorDetails.refresh();
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
            txtDoctorNic.requestFocus();
        } else if (!validFirstName) {
            txtDoctorFName.requestFocus();
        } else if (!validLastName) {
            txtDoctorLand.requestFocus();
        } else if (!validAddress) {
            txtDoctorAddress.requestFocus();
        } else if (!validMobile) {
            txtDoctorMobile.requestFocus();
        } else if (!validLand) {
            txtDoctorLand.requestFocus();
        } else if (!validEmail) {
            txtDoctorEmail.requestFocus();
        } else if (!validSpecialization) {
            txtDoctorSpecalization.requestFocus();
        } else if (!validRegHospital) {
            txtDoctorRegHospital.requestFocus();
        }
    }


    public void btnDeleteDoctor_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this Patient?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            DoctorTM selectedItem = tblDoctorDetails.getSelectionModel().getSelectedItem();
            try {
                doctorBO.deleteDoctor(selectedItem.getNic());
                tblDoctorDetails.getItems().remove(selectedItem);
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
        txtDoctorID.clear();
        txtDoctorNic.clear();
        txtDoctorFName.clear();
        txtDoctorLName.clear();
        txtDoctorAddress.clear();
        txtDoctorMobile.clear();
        txtDoctorLand.clear();
        txtDoctorEmail.clear();
        txtDoctorSpecalization.clear();
        txtDoctorRegHospital.clear();
        txtDoctorNic.requestFocus();
        btnAdd.setText("Add");
        generateNewId();
        btnDelete.setDisable(true);

    }

    public void generateNewId() {
        int maxId = 0;

        try {
            String lastDoctorId = doctorBO.getLastDoctorId();

            if (lastDoctorId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastDoctorId.replace("DOC", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "DOC000" + maxId;
            } else if (maxId < 100) {
                id = "DOC00" + maxId;
            } else if (maxId < 1000) {
                id = "DOC0" + maxId;
            } else {
                id = "DOC" + maxId;
            }
            txtDoctorID.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
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
