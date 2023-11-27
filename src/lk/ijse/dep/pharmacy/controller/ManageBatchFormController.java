package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.BatchBO;
import lk.ijse.dep.pharmacy.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pharmacy.dto.BatchDTO;
import lk.ijse.dep.pharmacy.util.BatchTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ManageBatchFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtMngStkBatchCode;
    public JFXTextField txtMngStkBatchDes;
    public DatePicker dtpBatchExpireDate;
    public JFXButton btnAddBatch;
    public JFXButton btnClearBatch;
    public JFXTextField txtMngStkSearchBatch;
    public TableView<BatchTM> tblMngStockBatchDetails;
    public JFXTextField txtMngStkBatchStatus;

    private BatchBO batchBO = BOFactory.getInstance().getBO(BOTypes.BATCH);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        generateNewBatchId();
        txtMngStkBatchCode.setDisable(true);
        txtMngStkBatchStatus.setDisable(true);

        loadTable();

        tblMngStockBatchDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblMngStockBatchDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("des"));
        tblMngStockBatchDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblMngStockBatchDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));
        tblMngStockBatchDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

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

        txtMngStkSearchBatch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });
    }

    public void loadTable() {
        tblMngStockBatchDetails.getItems().clear();

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
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
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
                        tblMngStockBatchDetails.refresh();
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

    public void btnClearBatch_OnAction(ActionEvent actionEvent) {
        txtMngStkBatchCode.clear();
        txtMngStkBatchDes.clear();
        txtMngStkBatchStatus.clear();
        dtpBatchExpireDate.setValue(null);
        txtMngStkBatchDes.requestFocus();
        generateNewBatchId();
        btnAddBatch.setText("Add");
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


}
