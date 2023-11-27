package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.BatchBO;
import lk.ijse.dep.pharmacy.business.custom.DrugItemBO;
import lk.ijse.dep.pharmacy.business.custom.LocationBO;
import lk.ijse.dep.pharmacy.business.custom.StockBO;
import lk.ijse.dep.pharmacy.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pharmacy.dto.BatchDTO;
import lk.ijse.dep.pharmacy.dto.DrugItemDTO;
import lk.ijse.dep.pharmacy.dto.LocationDTO;
import lk.ijse.dep.pharmacy.dto.StockDTO2;
import lk.ijse.dep.pharmacy.util.BatchTM;
import lk.ijse.dep.pharmacy.util.LocationTM;
import lk.ijse.dep.pharmacy.util.StockDrugItemTM;
import lk.ijse.dep.pharmacy.util.StockTM;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ManageStockFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtMngStkLocationDes;
    public JFXTextField txtMngStkLocationCode;
    public TableView<LocationTM> tblMngStockLocationDetails;
    public TableView<StockDrugItemTM> tblMngStockDrugDetails;
    public JFXTextField txtMngStkSearchLocation;
    public JFXTextField txtMngStkSearchDrug;
    public TableView<StockTM> tblMngStock_StockDetails;
    public JFXTextField txtMngStkSearchStock;
    public TableView<BatchTM> tblMngStockBatchDetails;
    public JFXTextField txtMngStkSearchBatch;
    public JFXTextField txtMngStkBatchDes;
    public JFXTextField txtMngStkBatchCode;
    public JFXTextField txtMngStkBatchStockCode;
    public JFXTextField txtMngStkLocationStatus;
    public JFXButton btnClearLocation;
    public JFXButton btnClearBatch;
    public JFXButton btnAddLocation;
    public DatePicker dtpBatchExpireDate;
    public JFXButton btnAddBatch;
    public JFXTextField txtMngStkBatchStatus;
    public JFXButton btnManageDrugs;

    private LocationBO locationBO = BOFactory.getInstance().getBO(BOTypes.LOCATION);
    private BatchBO batchBO = BOFactory.getInstance().getBO(BOTypes.BATCH);
    private StockBO stockBO = BOFactory.getInstance().getBO(BOTypes.STOCK);
    private DrugItemBO drugItemBO = BOFactory.getInstance().getBO(BOTypes.DRUG_ITEM);



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        generateNewLocationId();
        txtMngStkLocationCode.setDisable(true);
        txtMngStkLocationStatus.setDisable(true);

        generateNewBatchId();
        txtMngStkBatchCode.setDisable(true);
        txtMngStkBatchStatus.setDisable(true);

        loadTable();

        tblMngStockLocationDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblMngStockLocationDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("des"));
        tblMngStockLocationDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));
        tblMngStockLocationDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        tblMngStockBatchDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblMngStockBatchDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("des"));
        tblMngStockBatchDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblMngStockBatchDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));
        tblMngStockBatchDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        tblMngStock_StockDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblMngStock_StockDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblMngStock_StockDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblMngStock_StockDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        tblMngStockDrugDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblMngStockDrugDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("des"));
        tblMngStockDrugDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("manDate"));
        tblMngStockDrugDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("expDate"));
        tblMngStockDrugDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblMngStockDrugDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));



        tblMngStockLocationDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LocationTM>() {
            @Override
            public void changed(ObservableValue<? extends LocationTM> observable, LocationTM oldValue, LocationTM newValue) {
                LocationTM selectedItem = tblMngStockLocationDetails.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {
                    btnAddLocation.setText("Add");
                    return;
                }
                btnAddLocation.setText("Update");

                try {
                    LocationDTO location = locationBO.findLocation(selectedItem.getId());

                    txtMngStkLocationCode.setText(location.getId());
                    txtMngStkLocationDes.setText(location.getDes());
                    txtMngStkLocationStatus.setText(String.valueOf(location.isStatus()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        tblMngStockBatchDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BatchTM>() {
            @Override
            public void changed(ObservableValue<? extends BatchTM> observable, BatchTM oldValue, BatchTM newValue) {
                BatchTM selectedItem = tblMngStockBatchDetails.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {
                    btnAddBatch.setText("Add");
                    return;
                }
                btnAddBatch.setText("Update");

                try {
                    BatchDTO batch = batchBO.findBatch(selectedItem.getId());

                    txtMngStkBatchCode.setText(batch.getId());
                    txtMngStkBatchDes.setText(batch.getDes());
                    txtMngStkBatchStatus.setText(String.valueOf(batch.isStatus()));
                    dtpBatchExpireDate.setValue(batch.getExpDate().toLocalDate());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        txtMngStkSearchLocation.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });

        txtMngStkSearchBatch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });

        txtMngStkSearchStock.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }

        });

        txtMngStkSearchDrug.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }

        });
    }


    public void loadTable() {
        tblMngStockLocationDetails.getItems().clear();
        tblMngStockBatchDetails.getItems().clear();
        tblMngStockDrugDetails.getItems().clear();
        tblMngStock_StockDetails.getItems().clear();
        try {
            List<LocationDTO> allLocations = locationBO.searchLocation(txtMngStkSearchLocation.getText());
            ObservableList<LocationTM> locations = tblMngStockLocationDetails.getItems();
            for (LocationDTO l : allLocations) {
                JFXButton btnDelete = new JFXButton("Delete");

                locations.add(new LocationTM(l.getId(), l.getDes(), l.isStatus(), btnDelete));

                btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure whether you want to delete this Location?",
                                ButtonType.YES, ButtonType.NO);
                        Optional<ButtonType> buttonType = alert.showAndWait();
                        if (buttonType.get() == ButtonType.YES) {
                            LocationTM selectedItem = tblMngStockLocationDetails.getSelectionModel().getSelectedItem();

                            if (selectedItem == null) {
                                return;
                            }

                            try {
                                locationBO.deleteLocation(selectedItem.getId());
                                tblMngStockLocationDetails.getItems().remove(selectedItem);
                                txtMngStkLocationCode.clear();
                                txtMngStkLocationDes.clear();
                                txtMngStkLocationStatus.clear();
                                generateNewLocationId();
                            } catch (AlreadyExistsInOrderException e) {
                                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
                            } catch (Exception e) {
                                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                                Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                            }
                        }

                    }
                });
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }

        //--------------------------------LOAD BATCH TABLE ---------------------------------

        try {
            List<BatchDTO> allBatches = batchBO.searchBatch(txtMngStkSearchBatch.getText());
            ObservableList<BatchTM> batches = tblMngStockBatchDetails.getItems();
            for (BatchDTO b : allBatches) {
                JFXButton btnDelete = new JFXButton("Delete");

                batches.add(new BatchTM(b.getId(), b.getDes(), b.getExpDate().toString(), b.isStatus(), btnDelete));

                btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure whether you want to delete this Batch ?",
                                ButtonType.YES, ButtonType.NO);
                        Optional<ButtonType> buttonType = alert.showAndWait();
                        if (buttonType.get() == ButtonType.YES) {
                            BatchTM selectedItem = tblMngStockBatchDetails.getSelectionModel().getSelectedItem();

                            if (selectedItem == null) {
                                return;
                            }

                            try {
                                batchBO.deleteBatch(selectedItem.getId());
                                tblMngStockBatchDetails.getItems().remove(selectedItem);
                                txtMngStkBatchCode.clear();
                                txtMngStkBatchDes.clear();
                                txtMngStkBatchStatus.clear();
                                dtpBatchExpireDate.setValue(null);
                                generateNewBatchId();
                            } catch (AlreadyExistsInOrderException e) {
                                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
                            } catch (Exception e) {
                                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                                Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                            }
                        }

                    }
                });
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }

        //-----------------------------------------LOAD STOCK TABLE----------------------------------------------------------
        try {
            List<StockDTO2> allStocks = stockBO.searchStock(txtMngStkSearchStock.getText());
            ObservableList<StockTM> stocks = tblMngStock_StockDetails.getItems();
            for (StockDTO2 s : allStocks) {
                stocks.add(new StockTM(s.getId(), s.getCode(), s.getQty(), s.getUnitPrice()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }

        //-----------------------------------------LOAD DRUG DETAILS-------------------------------------------------------


        try {
            List<DrugItemDTO> allItems = drugItemBO.searchDrugItem(txtMngStkSearchDrug.getText());
            ObservableList<StockDrugItemTM> drugItems = tblMngStockDrugDetails.getItems();
            for (DrugItemDTO d : allItems) {
                drugItems.add(new StockDrugItemTM(d.getCode(), d.getDescription(), d.getManuDate().toString(), d.getExpireDate().toString(),d.getQtyOnHand(),d.getUnitPrice()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }


    }

    public void btnMngStkAddLocation_OnAction(ActionEvent actionEvent) {
        boolean validDes = txtMngStkLocationDes.getText().matches("[A-Za-z0-9][A-Za-z0-9. ]+");
        if (validDes) {
            if (btnAddLocation.getText().equals("Add")) {
                ObservableList<LocationTM> details = tblMngStockLocationDetails.getItems();
                JFXButton btnDelete = new JFXButton("Delete");
                LocationTM locationTM = new LocationTM(
                        txtMngStkLocationCode.getText(),
                        txtMngStkLocationDes.getText(),
                        true,
                        btnDelete
                );

                LocationDTO newLocation = new LocationDTO(
                        txtMngStkLocationCode.getText(),
                        txtMngStkLocationDes.getText(),
                        true
                );
                try {
                    locationBO.saveLocation(newLocation);
                    details.add(locationTM);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }

                btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure whether you want to delete this Location?",
                                ButtonType.YES, ButtonType.NO);
                        Optional<ButtonType> buttonType = alert.showAndWait();
                        if (buttonType.get() == ButtonType.YES) {
                            LocationTM selectedItem = tblMngStockLocationDetails.getSelectionModel().getSelectedItem();
                            if (selectedItem == null) {
                                return;
                            }
                            try {
                                locationBO.deleteLocation(selectedItem.getId());
                                tblMngStockLocationDetails.getItems().remove(selectedItem);
                                btnClearLocation_OnAction(actionEvent);
                            } catch (AlreadyExistsInOrderException e) {
                                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
                            } catch (Exception e) {
                                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                                Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                            }
                        }

                    }
                });
                btnClearLocation_OnAction(actionEvent);
            } else {
                String status = txtMngStkLocationStatus.getText();
                boolean value = false;
                System.out.println(value);
                value = status.equals("true");
                LocationTM selectedLocation = tblMngStockLocationDetails.getSelectionModel().getSelectedItem();
                try {
                    locationBO.updateLocation(new LocationDTO(
                            selectedLocation.getId(),
                            txtMngStkLocationDes.getText(),
                            value
                    ));
                    selectedLocation.setDes(txtMngStkLocationDes.getText());
                    selectedLocation.setId(txtMngStkLocationCode.getText());
                    selectedLocation.setStatus(value);
                    tblMngStockLocationDetails.refresh();
                    btnClearBatch_OnAction(actionEvent);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid input please double check the highlighted text").show();

        }
        if (!validDes) {
            txtMngStkLocationDes.requestFocus();
        }

    }

    public void btnMngStkAddBatch_OnAction(ActionEvent actionEvent) {
        boolean validDes = txtMngStkBatchDes.getText().matches("[A-Za-z0-9][A-Za-z0-9. ]+");
        LocalDate expDateValue = dtpBatchExpireDate.getValue();

        if (expDateValue == null) {
            new Alert(Alert.AlertType.ERROR, "Date cannot be Empty !!!").show();
            return;
        } else {
            boolean validExpDate = dtpBatchExpireDate.getValue().toString().matches("[0-9]{4}-(0[123456789]|10|11|12)-(0[123456789]|1[0123456789]|2[0123456789]|3[01])");
            if (validDes && validExpDate) {

                if (btnAddBatch.getText().equals("Add")) {
                    ObservableList<BatchTM> details = tblMngStockBatchDetails.getItems();
                    JFXButton btnDelete = new JFXButton("Delete");
                    BatchTM batchTM = new BatchTM(
                            txtMngStkBatchCode.getText(),
                            txtMngStkBatchDes.getText(),
                            dtpBatchExpireDate.getValue().toString(),
                            true,
                            btnDelete
                    );

                    BatchDTO newBatch = new BatchDTO(
                            txtMngStkBatchCode.getText(),
                            txtMngStkBatchDes.getText(),
                            java.sql.Date.valueOf(dtpBatchExpireDate.getValue()),
                            true
                    );
                    try {
                        batchBO.saveBatch(newBatch);
                        details.add(batchTM);
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                        Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                    }

                    btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                    "Are you sure whether you want to delete this Batch?",
                                    ButtonType.YES, ButtonType.NO);
                            Optional<ButtonType> buttonType = alert.showAndWait();
                            if (buttonType.get() == ButtonType.YES) {
                                BatchTM selectedItem = tblMngStockBatchDetails.getSelectionModel().getSelectedItem();
                                if (selectedItem == null) {
                                    return;
                                }
                                try {
                                    batchBO.deleteBatch(selectedItem.getId());
                                    tblMngStockBatchDetails.getItems().remove(selectedItem);
                                    btnClearBatch_OnAction(actionEvent);
                                } catch (AlreadyExistsInOrderException e) {
                                    new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
                                } catch (Exception e) {
                                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                                }
                            }

                        }
                    });

                    txtMngStkBatchDes.clear();
                    txtMngStkBatchCode.clear();
                    txtMngStkBatchStatus.clear();
                    dtpBatchExpireDate.setValue(null);
                    generateNewBatchId();

                } else {
                    String status = txtMngStkBatchStatus.getText();
                    boolean value = false;
                    System.out.println(value);
                    value = status.equals("true");
                    BatchTM selectedBatch = tblMngStockBatchDetails.getSelectionModel().getSelectedItem();
                    try {
                        batchBO.updateBatch(new BatchDTO(
                                selectedBatch.getId(),
                                txtMngStkBatchDes.getText(),
                                java.sql.Date.valueOf(dtpBatchExpireDate.getValue()),
                                value
                        ));
                        selectedBatch.setDes(txtMngStkBatchDes.getText());
                        selectedBatch.setId(txtMngStkBatchCode.getText());
                        selectedBatch.setStatus(value);
                        tblMngStockLocationDetails.refresh();
                        btnClearBatch_OnAction(actionEvent);
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                        Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                    }
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid input please double check the highlighted text").show();
            }

            if (!validDes) {
                txtMngStkBatchDes.requestFocus();
            } else if (!validExpDate) {
                dtpBatchExpireDate.requestFocus();
            }
        }

    }

    public void btnClearLocation_OnAction(ActionEvent actionEvent) {
        txtMngStkLocationCode.clear();
        txtMngStkLocationDes.clear();
        txtMngStkLocationStatus.clear();
        txtMngStkLocationDes.requestFocus();
        generateNewLocationId();
        btnAddLocation.setText("Add");
    }

    public void btnClearBatch_OnAction(ActionEvent actionEvent) {
        txtMngStkBatchCode.clear();
        txtMngStkBatchDes.clear();
        txtMngStkBatchStatus.clear();
        dtpBatchExpireDate.setValue(null);
        txtMngStkBatchDes.requestFocus();
        generateNewBatchId();
        btnAddBatch.setText("Add");
    }

    public void generateNewLocationId() {
        int maxId = 0;

        try {
            String lastLocationId = locationBO.getLastLocationId();

            if (lastLocationId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastLocationId.replace("LOC", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "LOC000" + maxId;
            } else if (maxId < 100) {
                id = "LOC00" + maxId;
            } else if (maxId < 1000) {
                id = "LOC0" + maxId;
            } else {
                id = "LOC" + maxId;
            }
            txtMngStkLocationCode.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void generateNewBatchId() {
        int maxId = 0;

        try {
            String lastBatchId = batchBO.getLastBatchId();

            if (lastBatchId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastBatchId.replace("BAT", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "BAT000" + maxId;
            } else if (maxId < 100) {
                id = "BAT00" + maxId;
            } else if (maxId < 1000) {
                id = "BAT0" + maxId;
            } else {
                id = "BAT" + maxId;
            }
            txtMngStkBatchCode.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void btnStockManageDrug_OnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ManageDrugItemForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.centerOnScreen();
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.showAndWait();
            loadTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
