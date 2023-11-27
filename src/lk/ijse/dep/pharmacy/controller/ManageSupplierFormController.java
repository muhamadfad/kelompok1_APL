package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.DoctorBO;
import lk.ijse.dep.pharmacy.business.custom.SupplierBO;
import lk.ijse.dep.pharmacy.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pharmacy.dto.DoctorDTO;
import lk.ijse.dep.pharmacy.dto.SupplierDTO;
import lk.ijse.dep.pharmacy.util.DoctorTM;
import lk.ijse.dep.pharmacy.util.SupplierTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ManageSupplierFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtSupplierID;
    public JFXTextField txtSupplierCompanyName;
    public JFXTextField txtSupplierAddress;
    public JFXTextField txtSupplierMobile;
    public JFXTextField txtSupplierLand;
    public JFXTextField txtSupplierEmail;
    public JFXTextField txtSupplierVDetails;
    public Label lblDate;
    public JFXTextField txtSupplierSearch;
    public TableView<SupplierTM> tblSupplierDetails;
    public JFXButton btnClear;
    public JFXButton btnAdd;
    public JFXButton btnDelete;

    private SupplierBO supplierBO = BOFactory.getInstance().getBO(BOTypes.SUPPLIER);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        lblDate.setText(LocalDate.now() + "");

        tblSupplierDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblSupplierDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblSupplierDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblSupplierDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("mobile"));
        tblSupplierDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("email"));

        btnDelete.setDisable(true);
        txtSupplierID.setDisable(true);
        generateNewId();

        loadTable();

        tblSupplierDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SupplierTM>() {
            @Override
            public void changed(ObservableValue<? extends SupplierTM> observable, SupplierTM oldValue, SupplierTM newValue) {
                SupplierTM selectedItem = tblSupplierDetails.getSelectionModel().getSelectedItem();

                System.out.println(selectedItem);

                if (selectedItem == null) {
                    btnAdd.setText("Add");
                    btnDelete.setDisable(true);
                    return;
                }
                btnAdd.setText("Update");
                btnDelete.setDisable(false);

                try {
                    SupplierDTO supplier = supplierBO.findSupplier(selectedItem.getId());

                    txtSupplierID.setText(supplier.getId());
                    txtSupplierCompanyName.setText(supplier.getName());
                    txtSupplierAddress.setText(supplier.getAddress());
                    txtSupplierMobile.setText(supplier.getMobile());
                    txtSupplierLand.setText(supplier.getLand());
                    txtSupplierEmail.setText(supplier.getEmail());
                    txtSupplierVDetails.setText(supplier.getVehicalNo());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        txtSupplierSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });
    }

    public void loadTable() {
        tblSupplierDetails.getItems().clear();
        try {
            List<SupplierDTO> allSuppliers = supplierBO.searchSupplier(txtSupplierSearch.getText());
            ObservableList<SupplierTM> suppliers = tblSupplierDetails.getItems();
            for (SupplierDTO s : allSuppliers) {
                suppliers.add(new SupplierTM(s.getId(), s.getName(), s.getAddress(), s.getMobile(), s.getEmail()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void btnAddSupplier_OnAction(ActionEvent actionEvent) {
        boolean validName = txtSupplierCompanyName.getText().matches("[A-Za-z][A-Za-z. ]+");
        boolean validAddress = txtSupplierAddress.getText().matches("[A-Za-z][A-Za-z. ]+[ , ]*");
        boolean validMobile = txtSupplierMobile.getText().matches("0\\d{9}");
        boolean validLand = txtSupplierLand.getText().matches("0\\d{9}");
        boolean validEmail = txtSupplierEmail.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
//        boolean validVehicleDetails = txtSupplierVDetails.getText().matches("[A-Za-z][A-Za-z. ]+");

        if (validName && validAddress && validMobile && validLand && validEmail) {
            if (btnAdd.getText().equals("Add")) {
                ObservableList<SupplierTM> suppliers = tblSupplierDetails.getItems();
                SupplierDTO newSupplier = new SupplierDTO(
                        txtSupplierID.getText(),
                        txtSupplierCompanyName.getText(),
                        txtSupplierAddress.getText(),
                        txtSupplierMobile.getText(),
                        txtSupplierLand.getText(),
                        txtSupplierEmail.getText(),
                        txtSupplierVDetails.getText()
                );
                try {
                    supplierBO.saveSupplier(newSupplier);
                    suppliers.add(new SupplierTM(newSupplier.getId(), newSupplier.getName(), newSupplier.getAddress(), newSupplier.getMobile(), newSupplier.getEmail()));
                    btnClearPatient_OnAction(actionEvent);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            } else {
                SupplierTM selectedSupplier = tblSupplierDetails.getSelectionModel().getSelectedItem();
                try {
                    supplierBO.updateSupplier(new SupplierDTO(
                            selectedSupplier.getId(),
                            txtSupplierCompanyName.getText(),
                            txtSupplierAddress.getText(),
                            txtSupplierMobile.getText(),
                            txtSupplierLand.getText(),
                            txtSupplierEmail.getText(),
                            txtSupplierVDetails.getText()
                    ));
                    selectedSupplier.setName(txtSupplierCompanyName.getText());
                    selectedSupplier.setAddress(txtSupplierAddress.getText());
                    selectedSupplier.setId(txtSupplierID.getText());
                    selectedSupplier.setMobile(txtSupplierMobile.getText());
                    selectedSupplier.setEmail(txtSupplierEmail.getText());
                    tblSupplierDetails.refresh();
                    btnClearPatient_OnAction(actionEvent);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input please double check the highlighted text").show();
        }

        if (!validName) {
            txtSupplierCompanyName.requestFocus();
        } else if (!validAddress) {
            txtSupplierAddress.requestFocus();
        } else if (!validMobile) {
            txtSupplierMobile.requestFocus();
        } else if (!validLand) {
            txtSupplierLand.requestFocus();
        } else if (!validEmail) {
            txtSupplierEmail.requestFocus();
        }
    }

    public void btnDeleteSupplier_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this Supplier?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            SupplierTM selectedItem = tblSupplierDetails.getSelectionModel().getSelectedItem();
            try {
                supplierBO.deleteSupplier(selectedItem.getId());
                tblSupplierDetails.getItems().remove(selectedItem);
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
        txtSupplierID.clear();
        txtSupplierCompanyName.clear();
        txtSupplierAddress.clear();
        txtSupplierMobile.clear();
        txtSupplierLand.clear();
        txtSupplierEmail.clear();
        txtSupplierVDetails.clear();
        txtSupplierCompanyName.requestFocus();
        btnAdd.setText("Add");
        generateNewId();
        btnDelete.setDisable(true);
    }

    public void generateNewId() {
        int maxId = 0;

        try {
            String lastSupplierId = supplierBO.getLastSupplierId();

            if (lastSupplierId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastSupplierId.replace("SUP", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "SUP000" + maxId;
            } else if (maxId < 100) {
                id = "SUP00" + maxId;
            } else if (maxId < 1000) {
                id = "SUP0" + maxId;
            } else {
                id = "SUP" + maxId;
            }
            txtSupplierID.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }
}
