package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.DrugItemBO;
import lk.ijse.dep.pharmacy.business.custom.LocationBO;
import lk.ijse.dep.pharmacy.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pharmacy.dto.DrugItemDTO;
import lk.ijse.dep.pharmacy.dto.DrugItemDTO2;
import lk.ijse.dep.pharmacy.dto.LocationDTO;
import lk.ijse.dep.pharmacy.dto.SupplierDTO;
import lk.ijse.dep.pharmacy.util.DrugItemTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ManageDrugItemFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtDrugCode;
    public JFXTextField txtDrugDes;
    public Label lblDate;
    public JFXButton btnAdd;
    public JFXButton btnDelete;
    public JFXTextField txtDrugSearch;
    public TableView<DrugItemTM> tblDrugDetails;
    public JFXButton btnClear;
    public JFXTextField txtDrugQty;
    public DatePicker dtpM_Date;
    public DatePicker dtpExpDate;
    public JFXTextField txtDrugUnitPrice;
    public JFXComboBox<String> cmbLocationCode;
    public JFXTextField txtDrugLocationDes;

    private DrugItemBO drugItemBO = BOFactory.getInstance().getBO(BOTypes.DRUG_ITEM);
    private LocationBO locationBO = BOFactory.getInstance().getBO(BOTypes.LOCATION);



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        lblDate.setText(LocalDate.now() + "");
        txtDrugLocationDes.setDisable(true);

        try {
            List<String> ids = locationBO.getAllAvailableLocationIds();
            cmbLocationCode.setItems(FXCollections.observableArrayList(ids));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null,e);
        }

        tblDrugDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblDrugDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblDrugDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblDrugDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblDrugDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("manuDate"));
        tblDrugDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("expireDate"));
        tblDrugDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("locationCode"));

        btnDelete.setDisable(true);
        txtDrugCode.setDisable(true);
        generateNewId();

        loadTable();

        cmbLocationCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedLocationCode = cmbLocationCode.getSelectionModel().getSelectedItem();
                txtDrugLocationDes.setText("");
                if (selectedLocationCode == null) {
                    return;
                }
                try {
                    LocationDTO location = locationBO.findLocation(selectedLocationCode);
                    txtDrugLocationDes.setText(location.getDes());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR,"Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null,e);
                }
            }
        });

        tblDrugDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DrugItemTM>() {
            @Override
            public void changed(ObservableValue<? extends DrugItemTM> observable, DrugItemTM oldValue, DrugItemTM newValue) {
                DrugItemTM selectedItem = tblDrugDetails.getSelectionModel().getSelectedItem();

                System.out.println(selectedItem);

                if (selectedItem == null) {
                    btnAdd.setText("Add");
                    btnDelete.setDisable(true);
                    return;
                }
                btnAdd.setText("Update");
                btnDelete.setDisable(false);

                try {
                    DrugItemDTO drugItem = drugItemBO.findDrugItem(selectedItem.getCode());

                    txtDrugCode.setText(drugItem.getCode());
                    txtDrugDes.setText(drugItem.getDescription());
                    txtDrugQty.setText(Integer.toString(drugItem.getQtyOnHand()));
                    txtDrugUnitPrice.setText(Double.toString(drugItem.getUnitPrice()));
                    dtpM_Date.setValue(drugItem.getManuDate().toLocalDate());
                    dtpExpDate.setValue(drugItem.getExpireDate().toLocalDate());
                    cmbLocationCode.setValue(drugItem.getLocationId());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        txtDrugSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });
    }

    public void loadTable() {
        tblDrugDetails.getItems().clear();
        try {
            List<DrugItemDTO> allDrugItems = drugItemBO.searchDrugItem(txtDrugSearch.getText());
            ObservableList<DrugItemTM> drugItems = tblDrugDetails.getItems();
            for (DrugItemDTO d : allDrugItems) {
                drugItems.add(new DrugItemTM(d.getCode(), d.getDescription(), d.getQtyOnHand(), d.getUnitPrice(), d.getManuDate().toString(), d.getExpireDate().toString(),d.getLocationId()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void btnAddDrug_OnAction(ActionEvent actionEvent) {
        boolean validDes = txtDrugDes.getText().matches("[A-Za-z][A-Za-z. ]+");
        boolean validUnitPrice = txtDrugUnitPrice.getText().matches("(\\d+\\.\\d{1,2})");
        boolean validQty = txtDrugQty.getText().matches("[0-9]{1,5}");
        LocalDate mDateValue = dtpM_Date.getValue();
        LocalDate expDateValue = dtpExpDate.getValue();
        if (mDateValue == null || expDateValue == null || cmbLocationCode.getSelectionModel().getSelectedItem() == null) {
            new Alert(Alert.AlertType.ERROR, "Dates or Locations cannot be Empty !!!").show();
            return;
        } else {
            boolean validManDate = dtpM_Date.getValue().toString().matches("[0-9]{4}-(0[123456789]|10|11|12)-(0[123456789]|1[0123456789]|2[0123456789]|3[01])");
            boolean validExpDate = dtpExpDate.getValue().toString().matches("[0-9]{4}-(0[123456789]|10|11|12)-(0[123456789]|1[0123456789]|2[0123456789]|3[01])");

            if (validDes && validUnitPrice && validQty && validManDate && validExpDate) {
                if (btnAdd.getText().equals("Add")) {

                    LocationDTO locationDetails = new LocationDTO(
                            cmbLocationCode.getSelectionModel().getSelectedItem(),
                            txtDrugLocationDes.getText(),
                            false
                    );

                    ObservableList<DrugItemTM> drugItems = tblDrugDetails.getItems();
                    DrugItemDTO2 newDrugItem = new DrugItemDTO2(
                            txtDrugCode.getText(),
                            txtDrugDes.getText(),
                            java.sql.Date.valueOf(dtpM_Date.getValue()),
                            java.sql.Date.valueOf(dtpExpDate.getValue()),
                            Integer.parseInt(txtDrugQty.getText()),
                            Double.parseDouble(txtDrugUnitPrice.getText()),
                            cmbLocationCode.getSelectionModel().getSelectedItem(),
                            locationDetails
                    );
                    try {
                        drugItemBO.saveDrugItem(newDrugItem);
                        drugItems.add(new DrugItemTM(newDrugItem.getCode(), newDrugItem.getDescription(), newDrugItem.getQtyOnHand(), newDrugItem.getUnitPrice(), newDrugItem.getManuDate().toString(), newDrugItem.getExpireDate().toString(),newDrugItem.getLocationId()));
                        btnClearDrug_OnAction(actionEvent);
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                        Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                    }
                } else {
                    DrugItemTM selectedDrugItem = tblDrugDetails.getSelectionModel().getSelectedItem();
                    try {
                        drugItemBO.updateDrugItem(new DrugItemDTO(
                                selectedDrugItem.getCode(),
                                txtDrugDes.getText(),
                                java.sql.Date.valueOf(dtpM_Date.getValue()),
                                java.sql.Date.valueOf(dtpExpDate.getValue()),
                                Integer.parseInt(txtDrugQty.getText()),
                                Double.parseDouble(txtDrugUnitPrice.getText()),
                                cmbLocationCode.getSelectionModel().getSelectedItem()
                        ));
                        selectedDrugItem.setDescription(txtDrugDes.getText());
                        selectedDrugItem.setQty(Integer.parseInt(txtDrugQty.getText()));
                        selectedDrugItem.setUnitPrice(Double.parseDouble(txtDrugUnitPrice.getText()));
                        selectedDrugItem.setManuDate(dtpM_Date.getValue().toString());
                        selectedDrugItem.setExpireDate(dtpExpDate.getValue().toString());
                        tblDrugDetails.refresh();
                        btnClearDrug_OnAction(actionEvent);
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                        Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                    }
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid input please double check the highlighted text").show();
            }

            if (!validDes) {
                txtDrugDes.requestFocus();
            } else if (!validQty) {
                txtDrugQty.requestFocus();
            } else if (!validUnitPrice) {
                txtDrugUnitPrice.requestFocus();
            }else if (!validManDate) {
                dtpM_Date.requestFocus();
            }else if (!validExpDate) {
                dtpExpDate.requestFocus();
            }
        }

        try {
            cmbLocationCode.getItems().clear();
            List<String> ids = locationBO.getAllAvailableLocationIds();
            cmbLocationCode.setItems(FXCollections.observableArrayList(ids));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null,e);
        }
    }

    public void btnDeleteDrug_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this Drug Item?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            DrugItemTM selectedItem = tblDrugDetails.getSelectionModel().getSelectedItem();
            try {
                drugItemBO.deleteDrugItem(selectedItem.getCode());
                tblDrugDetails.getItems().remove(selectedItem);
                btnClearDrug_OnAction(actionEvent);
            } catch (AlreadyExistsInOrderException e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
            }
        }
    }

    public void btnClearDrug_OnAction(ActionEvent actionEvent) {
        txtDrugCode.clear();
        txtDrugDes.clear();
        txtDrugQty.clear();
        txtDrugLocationDes.clear();
        txtDrugUnitPrice.clear();
        dtpExpDate.setValue(null);
        dtpM_Date.setValue(null);
        cmbLocationCode.getSelectionModel().clearSelection();
        txtDrugDes.requestFocus();
        btnAdd.setText("Add");
        generateNewId();
        btnDelete.setDisable(true);
    }

    public void generateNewId() {
        int maxId = 0;

        try {
            String lastDrugItemId = drugItemBO.getLastDrugItemId();

            if (lastDrugItemId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastDrugItemId.replace("DG", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "DG000" + maxId;
            } else if (maxId < 100) {
                id = "DG00" + maxId;
            } else if (maxId < 1000) {
                id = "DG0" + maxId;
            } else {
                id = "DG" + maxId;
            }
            txtDrugCode.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }
}
