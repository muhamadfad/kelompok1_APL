package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.BatchBO;
import lk.ijse.dep.pharmacy.business.custom.DrugItemBO;
import lk.ijse.dep.pharmacy.business.custom.OrderBO;
import lk.ijse.dep.pharmacy.business.custom.SupplierBO;
import lk.ijse.dep.pharmacy.db.DBConnection;
import lk.ijse.dep.pharmacy.dto.*;
import lk.ijse.dep.pharmacy.util.DrugItemDetailTM;
import lk.ijse.dep.pharmacy.util.OrderInfoTM;
import lk.ijse.dep.pharmacy.util.PrescriptionInfoTM;
import lk.ijse.dep.pharmacy.util.SupplierTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ManageSupplierOrderFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtOrderRefNo;
    public JFXTextField txtOrderSupName;
    public JFXTextField txtOrderSupRegNo;
    public JFXTextField txtOrderBatchId;
    public JFXTextField txtOrderBatchName;
    public Label lblCurrentDate;
    public Label lblCurrentTime;
    public JFXTextField txtOrderDrugName;
    public JFXTextField txtOrderDrugQty;
    public JFXTextField txtOrderDrugCode;
    public TableView<DrugItemDetailTM> tblOrderDrugDetails;
    public TableView<OrderInfoTM> tblOrderDetails;
    public Label lblOrderTotal;
    public JFXTextField txtOrderSearch;
    public Label lblSupplierNotReg;
    public DatePicker dtpM_Date;
    public DatePicker dtpExpDate;
    public JFXButton btnViewDrugs;
    public JFXButton btnViewSuppliers;
    public JFXComboBox<String> cmbOrderSupRegNo;
    public JFXButton btnManageDrugs;
    public JFXTextField txtOrderDrugUnitPrice;
    public JFXButton btnMakePayment;
    public JFXComboBox<String> cmbOrderDrugCode;
    public JFXTextField txtOrderDrugQtyIn;
    public JFXTextField txtOrderDrugNewUnitPrice;
    public JFXComboBox<String> cmbOrderBatchCode;
    public Label lblBatchNotAvailable;
    public JFXButton btnClear;
    public JFXButton btnAdd;
    public JFXButton btnAddOrder;

    private List<DrugItemDetailTM> tempItems = new ArrayList<>();
    private double netTotal;
    private ArrayList<DrugItemDTO> tempDrugItems = new ArrayList();
    private boolean readOnly = false;


    private SupplierBO supplierBO = BOFactory.getInstance().getBO(BOTypes.SUPPLIER);
    private DrugItemBO drugItemBO = BOFactory.getInstance().getBO(BOTypes.DRUG_ITEM);
    private BatchBO batchBO = BOFactory.getInstance().getBO(BOTypes.BATCH);
    private OrderBO orderBO = BOFactory.getInstance().getBO(BOTypes.ORDER);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        btnMakePayment.setDisable(true);
        txtOrderRefNo.setDisable(true);
        btnAdd.setDisable(true);

        txtOrderSupName.setDisable(true);
        txtOrderBatchName.setDisable(true);

        cmbOrderDrugCode.setDisable(true);
        txtOrderDrugName.setDisable(true);
        txtOrderDrugUnitPrice.setDisable(true);
        txtOrderDrugQtyIn.setDisable(true);

        lblCurrentDate.setText(LocalDate.now() + "");
        lblCurrentTime.setText(java.time.LocalTime.now() + "");

        tblOrderDrugDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderDrugDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDrugDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDrugDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDrugDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        tblOrderDrugDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));


        generateNewOrderId();
        loadTable();

        try {
            List<String> ids = supplierBO.getAllSupplierIds();
            cmbOrderSupRegNo.setItems(FXCollections.observableArrayList(ids));

            List<String> drugItemIds = drugItemBO.getAllDrugItemIds();
            cmbOrderDrugCode.setItems(FXCollections.observableArrayList(drugItemIds));

            List<String> batchIds = batchBO.getAllAvailableBatchIds();
            cmbOrderBatchCode.setItems(FXCollections.observableArrayList(batchIds));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }


        cmbOrderSupRegNo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedSupplierId = cmbOrderSupRegNo.getSelectionModel().getSelectedItem();
                txtOrderSupName.setText("");
                if (selectedSupplierId == null) {
                    return;
                }
                try {
                    SupplierDTO supplier = supplierBO.findSupplier(selectedSupplierId);
                    txtOrderSupName.setText(supplier.getName());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            }
        });

        cmbOrderDrugCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedDrugCode = cmbOrderDrugCode.getSelectionModel().getSelectedItem();
                txtOrderDrugName.setText("");
                txtOrderDrugQty.setText("");
                txtOrderDrugUnitPrice.setText("");
                txtOrderDrugQtyIn.setText("");
                txtOrderDrugNewUnitPrice.setText("");
                if (selectedDrugCode == null) {
                    txtOrderDrugUnitPrice.clear();
                    txtOrderDrugQtyIn.clear();
                    txtOrderDrugQty.clear();
                    txtOrderDrugQty.setEditable(false);
                    btnAdd.setDisable(true);
                    return;
                }
                txtOrderDrugQty.setEditable(true);
                if (!readOnly) {
                    btnAdd.setText("Add");
                    btnAdd.setDisable(false);
                }
                try {
                    DrugItemDTO drugItem = drugItemBO.findDrugItem(selectedDrugCode);
                    txtOrderDrugName.setText(drugItem.getDescription());
                    txtOrderDrugQtyIn.setText(Integer.toString(drugItem.getQtyOnHand()));
                    txtOrderDrugUnitPrice.setText(Double.toString(drugItem.getUnitPrice()));
                    txtOrderDrugNewUnitPrice.setText(Double.toString(drugItem.getUnitPrice()));
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            }
        });

        cmbOrderBatchCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedBatchId = cmbOrderBatchCode.getSelectionModel().getSelectedItem();
                txtOrderBatchName.setText("");
                cmbOrderDrugCode.setDisable(false);
                if (selectedBatchId == null) {
                    return;
                }
                try {
                    BatchDTO batch = batchBO.findBatch(selectedBatchId);
                    txtOrderBatchName.setText(batch.getDes());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            }
        });


        tblOrderDrugDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DrugItemDetailTM>() {
            @Override
            public void changed(ObservableValue<? extends DrugItemDetailTM> observable, DrugItemDetailTM oldValue, DrugItemDetailTM newValue) {
                DrugItemDetailTM selectedOrderDetail = tblOrderDrugDetails.getSelectionModel().getSelectedItem();
                if (selectedOrderDetail == null) {
                    if (!readOnly) {
                        btnAdd.setText("Add");
                    }
                    return;
                }
                for (DrugItemDTO tempItem : tempDrugItems) {
                    if (tempItem.getCode().equals(selectedOrderDetail.getCode())) {
                        try {
                            DrugItemDTO item = drugItemBO.findDrugItem(selectedOrderDetail.getCode());
                            tempItem.setQtyOnHand(item.getQtyOnHand());
                        } catch (Exception e) {
                            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
                        }
                    }
                }
                cmbOrderDrugCode.getSelectionModel().select(selectedOrderDetail.getCode());
                txtOrderDrugQty.setText(selectedOrderDetail.getQty() + "");

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        txtOrderDrugQty.requestFocus();
                        txtOrderDrugQty.selectAll();
                    }
                });
                if (!readOnly) {
                    btnAdd.setText("Update");
                }
            }
        });

        txtOrderSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderInfoTM>() {
            @Override
            public void changed(ObservableValue<? extends OrderInfoTM> observable, OrderInfoTM oldValue, OrderInfoTM newValue) {
                OrderInfoTM selectedItem = tblOrderDetails.getSelectionModel().getSelectedItem();

                System.out.println(selectedItem);

                if (selectedItem == null) {
                    return;
                }

                try {
                    OrderDTO order = orderBO.findOrder(selectedItem.getOrderId());
                    List<OrderDetailDTO> orderDetails = orderBO.findOrderDetails(selectedItem.getOrderId());
                    txtOrderRefNo.setText(order.getId());
                    cmbOrderSupRegNo.setValue(order.getSupId());
                    lblCurrentDate.setText(order.getDate() + "");
                    ObservableList<DrugItemDetailTM> items = tblOrderDrugDetails.getItems();

                    items.clear();
                    for (OrderDetailDTO orderDetail : orderDetails) {
                        double total = orderDetail.getQty() * orderDetail.getUnitPrice();
                        DrugItemDTO drugItem = drugItemBO.findDrugItem(orderDetail.getCode());
                        items.add(new DrugItemDetailTM(orderDetail.getCode(), drugItem.getDescription(), orderDetail.getQty(), orderDetail.getUnitPrice(), total, null));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void loadTable() {
        tblOrderDetails.getItems().clear();
        try {
            List<OrderDTO2> allOrderInfo = orderBO.getOrderInfo(txtOrderSearch.getText());
            ObservableList<OrderInfoTM> orderInfo = tblOrderDetails.getItems();
            for (OrderDTO2 info : allOrderInfo) {
                orderInfo.add(new OrderInfoTM(info.getOrderId(), info.getSupplierId(), info.getSupplierName(), info.getOrderDate().toString(), info.getTotal()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void patientNotReg_OnMouseClicked(MouseEvent mouseEvent) {
    }

    public void btnOrderDrugAdd_OnAction(ActionEvent actionEvent) {
        boolean validQty = txtOrderDrugQty.getText().matches("[0-9]{1,5}");
        boolean validUnitPrice = txtOrderDrugNewUnitPrice.getText().matches("(\\d+\\.\\d{1,2})");
        if (validQty && validUnitPrice) {
            int qty = Integer.parseInt(txtOrderDrugQty.getText());
            double unitPrice = Double.parseDouble(txtOrderDrugNewUnitPrice.getText());



            if (qty <= 0) {
                new Alert(Alert.AlertType.ERROR, "Invalid Qty", ButtonType.OK).show();
                txtOrderDrugQty.requestFocus();
                txtOrderDrugQty.selectAll();
                return;
            }
            String selectedItemCode = cmbOrderDrugCode.getSelectionModel().getSelectedItem();
            ObservableList<DrugItemDetailTM> details = tblOrderDrugDetails.getItems();

            boolean isExists = false;
            for (DrugItemDetailTM detail : tblOrderDrugDetails.getItems()) {
                if (detail.getCode().equals(selectedItemCode)) {
                    isExists = true;

                    if (btnAdd.getText().equals("Update")) {
                        detail.setQty(qty);
                    } else {
                        detail.setQty(detail.getQty() + qty);
                    }
                    detail.setTotal(detail.getQty() * detail.getUnitPrice());
                    tblOrderDrugDetails.refresh();
                    break;
                }
            }

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            nf.setGroupingUsed(false);

            double total = qty*unitPrice;

            if (!isExists) {
                JFXButton btnDelete = new JFXButton("Delete");
                DrugItemDetailTM drugDetailTM = new DrugItemDetailTM(
                        cmbOrderDrugCode.getSelectionModel().getSelectedItem(),
                        txtOrderDrugName.getText(),
                        qty,
                        unitPrice,
                        Double.parseDouble(nf.format(total)),
                        btnDelete
                );

                btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (DrugItemDTO tempItem : tempDrugItems) {
                            if (tempItem.getCode().equals(drugDetailTM.getCode())) {
                                // Let's restore the qty
                                int qtyOnHand = tempItem.getQtyOnHand() + drugDetailTM.getQty();
                                tempItem.setQtyOnHand(qtyOnHand);
                                break;
                            }
                        }
                        tblOrderDrugDetails.getItems().remove(drugDetailTM);
                        calculateTotal();
                        enablePlaceOrderButton();
                        txtOrderDrugName.requestFocus();
                        tblOrderDrugDetails.getSelectionModel().clearSelection();
                    }
                });

                details.add(drugDetailTM);
                tempItems.add(drugDetailTM);
                System.out.println(tempItems);
            }
            updateQty(cmbOrderDrugCode.getSelectionModel().getSelectedItem(), qty);

            calculateTotal();
            enablePlaceOrderButton();

            txtOrderDrugName.clear();
            txtOrderDrugQty.clear();
            txtOrderDrugQtyIn.clear();
            txtOrderDrugUnitPrice.clear();
            cmbOrderDrugCode.getSelectionModel().clearSelection();

        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input please double check the highlighted text").show();
        }

        if (!validQty) {
            txtOrderDrugQty.requestFocus();
        }else if(!validUnitPrice){
            txtOrderDrugNewUnitPrice.requestFocus();
        }


    }

    private void updateQty(String selectedItemCode, int qty) {
        for (DrugItemDTO item : tempDrugItems) {
            if (item.getCode().equals(selectedItemCode)) {
                item.setQtyOnHand(item.getQtyOnHand() - qty);
                break;
            }
        }
    }


    private void calculateTotal() {
        ObservableList<DrugItemDetailTM> details = tblOrderDrugDetails.getItems();

        double total = 0;
        for (DrugItemDetailTM detail : details) {
            total += detail.getTotal();
        }

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setGroupingUsed(false);

        lblOrderTotal.setText(nf.format(total));
        netTotal = total;
    }

    public void btnPrintSearched(ActionEvent actionEvent) throws JRException {
        if (tblOrderDetails.getItems().size() == 0) {
            new Alert(Alert.AlertType.ERROR,
                    "PLEASE ENTER CORRECT DETAILS",
                    ButtonType.OK).show();
        } else {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pharmacy/report/PharmacyIssuedOrderReport.jasper"));
            Map<String, Object> params = new HashMap<>();
            params.put("searchValue", txtOrderSearch.getText());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        }
    }

    public void btnOrderMakePayment_OnAction(ActionEvent actionEvent) {

        String selectedSupplierId = cmbOrderSupRegNo.getSelectionModel().getSelectedItem();
        String supplierName = txtOrderSupName.getText();
        String selectedBatchCode = cmbOrderBatchCode.getSelectionModel().getSelectedItem();
        String orderId = txtOrderRefNo.getText();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/payment/ManagePaymentSupplierForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.centerOnScreen();
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            ManagePaymentSupplierFormController ctrl = fxmlLoader.getController();
            ctrl.getSupplierPaymentDetails(selectedSupplierId, tempItems, netTotal, selectedBatchCode, orderId);

            secondaryStage.showAndWait();

            clearFields();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void supplierNotReg_OnMouseClicked(MouseEvent mouseEvent) {
        URL resource = getClass().getResource("/lk/ijse/dep/pharmacy/view/supplier/ManageSupplierForm.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(scene);
        secondaryStage.centerOnScreen();
        secondaryStage.setResizable(false);
        secondaryStage.show();
    }

    public void btnOrderViewSuppliers_OnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/supplier/ViewAllSuppliersForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.centerOnScreen();
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.showAndWait();
            ViewAllSupplierFormController ctrl = fxmlLoader.getController();
            SupplierTM selectedItem = ctrl.tblSupplierDetails.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            cmbOrderSupRegNo.setValue(selectedItem.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getSelectedDetails(String supplierId) {
        txtOrderSupName.setText(supplierId);
        System.out.println(supplierId);
    }

    public void btnOrderManageDrug_OnAction(ActionEvent actionEvent) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            cmbOrderDrugCode.getItems().clear();
            List<String> ids = drugItemBO.getAllDrugItemIds();
            cmbOrderDrugCode.setItems(FXCollections.observableArrayList(ids));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }


    public void generateNewOrderId() {
        int maxId = 0;

        try {
            String lastOrderId = orderBO.getLastOrderId();

            if (lastOrderId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastOrderId.replace("ODR", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "ODR000" + maxId;
            } else if (maxId < 100) {
                id = "ODR00" + maxId;
            } else if (maxId < 1000) {
                id = "ODR0" + maxId;
            } else {
                id = "ODR" + maxId;
            }
            txtOrderRefNo.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void batchNotAvailable_OnMouseClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ManageBatchForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.centerOnScreen();
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.showAndWait();

            try {

                cmbOrderBatchCode.getItems().clear();
                List<String> batchIds = batchBO.getAllAvailableBatchIds();
                cmbOrderBatchCode.setItems(FXCollections.observableArrayList(batchIds));

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearFields() {

        cmbOrderSupRegNo.getSelectionModel().clearSelection();
        txtOrderSupName.clear();

        cmbOrderBatchCode.getSelectionModel().clearSelection();
        txtOrderBatchName.clear();

        tblOrderDrugDetails.getItems().clear();

        lblOrderTotal.setText("0.00");

        generateNewOrderId();
        try {
            cmbOrderSupRegNo.getItems().clear();
            cmbOrderDrugCode.getItems().clear();
            cmbOrderBatchCode.getItems().clear();
            List<String> ids = supplierBO.getAllSupplierIds();
            cmbOrderSupRegNo.setItems(FXCollections.observableArrayList(ids));

            List<String> drugItemIds = drugItemBO.getAllDrugItemIds();
            cmbOrderDrugCode.setItems(FXCollections.observableArrayList(drugItemIds));

            List<String> batchIds = batchBO.getAllAvailableBatchIds();
            cmbOrderBatchCode.setItems(FXCollections.observableArrayList(batchIds));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }

        tblOrderDetails.getItems().clear();
        tempItems.clear();
        btnMakePayment.setDisable(true);
        loadTable();

    }

    public void banClearAllFields_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to clear all fields ?", ButtonType.OK);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            cmbOrderSupRegNo.getSelectionModel().clearSelection();
            txtOrderSupName.clear();

            cmbOrderBatchCode.getSelectionModel().clearSelection();
            txtOrderBatchName.clear();

            tblOrderDrugDetails.getItems().clear();

            cmbOrderDrugCode.getSelectionModel().clearSelection();
            txtOrderDrugQty.clear();
            txtOrderDrugQtyIn.clear();
            txtOrderDrugUnitPrice.clear();
            txtOrderDrugName.clear();
            txtOrderDrugNewUnitPrice.clear();
            txtOrderSearch.clear();

            lblOrderTotal.setText("0.00");
            txtOrderRefNo.clear();
            generateNewOrderId();
            tempItems.clear();

            btnMakePayment.setDisable(true);
        }

    }

    private void enablePlaceOrderButton() {
        String selectedBatch = cmbOrderBatchCode.getSelectionModel().getSelectedItem();
        String selectedSupplier = cmbOrderSupRegNo.getSelectionModel().getSelectedItem();
        int size = tblOrderDrugDetails.getItems().size();
        if (selectedBatch == null || selectedSupplier == null || size == 0) {
            btnMakePayment.setDisable(true);
        } else {
            btnMakePayment.setDisable(false);
        }
    }

    public void txtQty_OnAction(ActionEvent actionEvent) {
        if (!btnAdd.isDisable()) {
            btnOrderDrugAdd_OnAction(actionEvent);
        }
    }

    public void btnOrderAddNewOrder_OnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void tblSupplierOrderDetail_OnMouseClicked(MouseEvent mouseEvent) throws JRException {
        if (mouseEvent.getClickCount() == 2) {

            OrderInfoTM selectedItem = tblOrderDetails.getSelectionModel().getSelectedItem();


            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pharmacy/report/PharmacyIssuedOrderBill.jasper"));
            Map<String, Object> params = new HashMap<>();
            params.put("orderId", selectedItem.getOrderId());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        }
    }
}
