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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.DoctorBO;
import lk.ijse.dep.pharmacy.business.custom.DrugItemBO;
import lk.ijse.dep.pharmacy.business.custom.PatientBO;
import lk.ijse.dep.pharmacy.business.custom.PrescriptionBO;
import lk.ijse.dep.pharmacy.db.DBConnection;
import lk.ijse.dep.pharmacy.dto.*;
import lk.ijse.dep.pharmacy.util.*;
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
public class ManagePrescriptionFormController implements Initializable {

    public AnchorPane root;
    public JFXTextField txtPresRefNo;
    public JFXTextField txtPresPatientName;
    public Label lblDocNotReg;
    public Label lblCurrentDate;
    public Label lblCurrentTime;
    public JFXTextField txtPresDrugName;
    public JFXTextField txtPresDrugQty;
    public JFXTextField txtPresDrugCode;
    public TableView<DrugItemDetailTM2> tblPresDrugDetails;
    public TableView<PrescriptionInfoTM> tblPrescriptionDetails;
    public Label lblPresTotal;
    public JFXTextField txtPresSearch;
    public JFXTextField txtPresDoctorName;
    public JFXButton btnAddPres;
    public JFXComboBox<String> cmbPresDocCode;
    public JFXComboBox<String> cmbPresPatientNic;
    public JFXComboBox<String> cmbPresDrugCode;
    public JFXTextField txtPresDrugQtyIn;
    public JFXTextField txtPresDrugUnitPrice;
    public JFXButton btnAdd;
    public JFXButton btnViewDrugs;
    public JFXButton btnClear;
    public JFXButton btnMakePayment;
    public JFXButton btnViewDoctors;
    public JFXButton btnViewPatient;
    public JFXButton btnViewStocks;
    public Label lblBatch;
    public JFXButton btnRate;
    public Label lblRate;

    private List<DrugItemDetailTM2> tempItems = new ArrayList<>();
    private double netTotal;
    private ArrayList<DrugItemDTO> tempDrugItems = new ArrayList();
    private boolean readOnly = false;
    private int rateValue = 2;
    private ObservableList<DrugItemDetailTM2> details;

    private PatientBO patientBO = BOFactory.getInstance().getBO(BOTypes.PATIENT);
    private DoctorBO doctorBO = BOFactory.getInstance().getBO(BOTypes.DOCTOR);
    private DrugItemBO drugItemBO = BOFactory.getInstance().getBO(BOTypes.DRUG_ITEM);
    //    private BatchBO batchBO = BOFactory.getInstance().getBO(BOTypes.BATCH);
    private PrescriptionBO prescriptionBO = BOFactory.getInstance().getBO(BOTypes.PRESCRIPTION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        btnMakePayment.setDisable(true);
        txtPresRefNo.setDisable(true);
        btnAdd.setDisable(true);

        cmbPresDrugCode.setDisable(true);
        txtPresDoctorName.setDisable(true);
        txtPresPatientName.setDisable(true);

        txtPresDrugUnitPrice.setDisable(true);
        txtPresDrugQtyIn.setDisable(true);
        txtPresDrugName.setDisable(true);

        btnAdd.setDisable(true);

        lblRate.setText(String.valueOf(rateValue));

        lblCurrentDate.setText(LocalDate.now() + "");
        lblCurrentTime.setText(java.time.LocalTime.now() + "");

        tblPresDrugDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblPresDrugDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("batchId"));
        tblPresDrugDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblPresDrugDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblPresDrugDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblPresDrugDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
        tblPresDrugDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        tblPrescriptionDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblPrescriptionDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblPrescriptionDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("patientName"));
        tblPrescriptionDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        tblPrescriptionDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblPrescriptionDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));

        generateNewPrescriptionId();

        loadTable();

        try {
            List<String> ids = patientBO.getAllPatientIDs();
            cmbPresPatientNic.setItems(FXCollections.observableArrayList(ids));

            List<String> drugItemIds = drugItemBO.getAllDrugItemIds();
            cmbPresDrugCode.setItems(FXCollections.observableArrayList(drugItemIds));

            List<String> doctorIds = doctorBO.getAllDoctorIds();
            cmbPresDocCode.setItems(FXCollections.observableArrayList(doctorIds));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }

        cmbPresPatientNic.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedPatientNic = cmbPresPatientNic.getSelectionModel().getSelectedItem();
                txtPresPatientName.setText("");
                if (selectedPatientNic == null) {
                    return;
                }
                try {
                    PatientDTO patient = patientBO.findPatient(selectedPatientNic);
                    txtPresPatientName.setText(patient.getName());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            }
        });

        cmbPresDocCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedDoctorId = cmbPresDocCode.getSelectionModel().getSelectedItem();
                txtPresDoctorName.setText("");
                cmbPresDrugCode.setDisable(false);
                if (selectedDoctorId == null) {
                    return;
                }
                try {
                    DoctorDTO doctor = doctorBO.findDoctor(selectedDoctorId);
                    txtPresDoctorName.setText(doctor.getName());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            }
        });

        cmbPresDrugCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedDrugCode = cmbPresDrugCode.getSelectionModel().getSelectedItem();
                txtPresDrugName.setText("");
                txtPresDrugQty.setText("");
                txtPresDrugUnitPrice.setText("");
                txtPresDrugQtyIn.setText("");
                btnAdd.setDisable(false);
                if (selectedDrugCode == null) {
                    txtPresDrugUnitPrice.clear();
                    txtPresDrugQtyIn.clear();
                    txtPresDrugQty.clear();
                    txtPresDrugQty.setEditable(false);
                    btnAdd.setDisable(true);
                    return;
                }
                txtPresDrugQty.setEditable(true);
                if (!readOnly) {
                    btnAdd.setText("Add");
                    btnAdd.setDisable(false);
                }
                try {
                    DrugItemDTO drugItem = drugItemBO.findDrugItem(selectedDrugCode);
                    txtPresDrugName.setText(drugItem.getDescription());
                    txtPresDrugQtyIn.setText(Integer.toString(drugItem.getQtyOnHand()));
                    txtPresDrugUnitPrice.setText(Double.toString(drugItem.getUnitPrice()));
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
                    Logger.getLogger("lk.ijse.dep.pharmacy.controller").log(Level.SEVERE, null, e);
                }
            }
        });


//        tblPresDrugDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DrugItemDetailTM>() {
//            @Override
//            public void changed(ObservableValue<? extends DrugItemDetailTM> observable, DrugItemDetailTM oldValue, DrugItemDetailTM newValue) {
//                DrugItemDetailTM selectedOrderDetail = tblPresDrugDetails.getSelectionModel().getSelectedItem();
//                if (selectedOrderDetail == null) {
//                    if (!readOnly) {
//                        btnAdd.setText("Add");
//                    }
//                    return;
//                }
//                for (DrugItemDTO tempItem : tempDrugItems) {
//                    if (tempItem.getCode().equals(selectedOrderDetail.getCode())) {
//                        try {
//                            DrugItemDTO item = drugItemBO.findDrugItem(selectedOrderDetail.getCode());
//                            tempItem.setQtyOnHand(item.getQtyOnHand());
//                        } catch (Exception e) {
//                            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
//                            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
//                        }
//                    }
//                }
//                cmbPresDrugCode.getSelectionModel().select(selectedOrderDetail.getCode());
//                txtPresDrugQty.setText(selectedOrderDetail.getQty() + "");
//
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        txtPresDrugQty.requestFocus();
//                        txtPresDrugQty.selectAll();
//                    }
//                });
//                if (!readOnly) {
//                    btnAdd.setText("Update");
//                }
//            }
//        });

        tblPresDrugDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DrugItemDetailTM2>() {
            @Override
            public void changed(ObservableValue<? extends DrugItemDetailTM2> observable, DrugItemDetailTM2 oldValue, DrugItemDetailTM2 newValue) {
                DrugItemDetailTM2 selectedOrderDetail = tblPresDrugDetails.getSelectionModel().getSelectedItem();
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
                cmbPresDrugCode.getSelectionModel().select(selectedOrderDetail.getCode());
                lblBatch.setText(selectedOrderDetail.getBatchId());
                txtPresDrugQty.setText(selectedOrderDetail.getQty() + "");

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        txtPresDrugQty.requestFocus();
                        txtPresDrugQty.selectAll();
                    }
                });
                if (!readOnly) {
                    btnAdd.setText("Update");
                }
            }
        });

        txtPresSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });

        tblPrescriptionDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PrescriptionInfoTM>() {
            @Override
            public void changed(ObservableValue<? extends PrescriptionInfoTM> observable, PrescriptionInfoTM oldValue, PrescriptionInfoTM newValue) {
                PrescriptionInfoTM selectedItem = tblPrescriptionDetails.getSelectionModel().getSelectedItem();

                System.out.println(selectedItem);

                if (selectedItem == null) {
                    return;
                }

                try {
                    PrescriptionDTO prescription = prescriptionBO.findPrescription(selectedItem.getId());
                    List<PrescriptionDetailDTO> prescriptionDetails = prescriptionBO.findPrescriptionDetails(selectedItem.getId());
                    txtPresRefNo.setText(prescription.getId());
                    cmbPresPatientNic.setValue(prescription.getPatientId());
                    cmbPresDocCode.setValue(prescription.getDoctorId());
                    lblCurrentDate.setText(prescription.getDate() + "");
                    ObservableList<DrugItemDetailTM2> items = tblPresDrugDetails.getItems();

                    items.clear();
                    for (PrescriptionDetailDTO prescriptionDetail : prescriptionDetails) {
                        double total = prescriptionDetail.getQty() * prescriptionDetail.getUnitPrice();
                        DrugItemDTO drugItem = drugItemBO.findDrugItem(prescriptionDetail.getCode());
                        items.add(new DrugItemDetailTM2(prescriptionDetail.getCode(), lblBatch.getText(), drugItem.getDescription(), prescriptionDetail.getQty(), prescriptionDetail.getUnitPrice(), total, null));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }


    public void loadTable() {
        tblPrescriptionDetails.getItems().clear();
        try {
            List<PrescriptionDTO2> allPresInfo = prescriptionBO.getPrescriptionInfo(txtPresSearch.getText());
            System.out.println("Info  " + allPresInfo);
            ObservableList<PrescriptionInfoTM> prescriptionInfo = tblPrescriptionDetails.getItems();
            for (PrescriptionDTO2 info : allPresInfo) {
                prescriptionInfo.add(new PrescriptionInfoTM(info.getPresId(), info.getPatientNic(), info.getPatientName(), info.getDoctorName(), info.getPresDate().toString(), info.getTotal()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void docNotReg_OnMouseClicked(MouseEvent mouseEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/doctor/ManageDoctorForm.fxml"));
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
            cmbPresDocCode.getItems().clear();

            List<String> doctorIds = doctorBO.getAllDoctorIds();
            cmbPresDocCode.setItems(FXCollections.observableArrayList(doctorIds));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void patientNotReg_OnMouseClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/patient/ManagePatientForm.fxml"));
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
            cmbPresPatientNic.getItems().clear();
            List<String> ids = patientBO.getAllPatientIDs();
            cmbPresPatientNic.setItems(FXCollections.observableArrayList(ids));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void btnPresDrugAdd_OnAction(ActionEvent actionEvent) {
        boolean validQty = txtPresDrugQty.getText().matches("[0-9]{1,5}");
        if (validQty) {
            int qty = Integer.parseInt(txtPresDrugQty.getText());

            System.out.println(rateValue);

            int ans = (rateValue + 100);
            double unitPrice = Double.parseDouble(txtPresDrugUnitPrice.getText() )* ans / 100;
            System.out.println(unitPrice);

            if (qty <= 0) {
                new Alert(Alert.AlertType.ERROR, "Invalid Qty", ButtonType.OK).show();
                txtPresDrugQty.requestFocus();
                txtPresDrugQty.selectAll();
                return;
            }
            String selectedItemCode = cmbPresDrugCode.getSelectionModel().getSelectedItem();
            details = tblPresDrugDetails.getItems();

            boolean isExists = false;
            for (DrugItemDetailTM2 detail : tblPresDrugDetails.getItems()) {
                if (detail.getCode().equals(selectedItemCode)) {
                    isExists = true;

                    String selectedDrugCode = cmbPresDrugCode.getSelectionModel().getSelectedItem();

                    if (btnAdd.getText().equals("Update")) {
//                        try {
//                            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ViewStockForm.fxml"));
//                            Parent root = fxmlLoader.load();
//                            Scene scene = new Scene(root);
//                            Stage secondaryStage = new Stage();
//                            secondaryStage.setScene(scene);
//                            secondaryStage.centerOnScreen();
//                            secondaryStage.setResizable(false);
//                            secondaryStage.initModality(Modality.WINDOW_MODAL);
//                            ViewStockFormController ctrl = fxmlLoader.getController();
//                            ctrl.getDrugCode(selectedDrugCode);
//
//                            secondaryStage.showAndWait();
//
//                            StockTM selectedItem = ctrl.tblStockDetails.getSelectionModel().getSelectedItem();
//                            if (selectedItem == null) {
//                                return;
//                            }
//                            lblBatch.setText(selectedItem.getId());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        detail.setQty(qty);
                        detail.setBatchId(lblBatch.getText());
                    } else {
                        detail.setQty(detail.getQty() + qty);
                    }
                    detail.setTotal(detail.getQty() * detail.getUnitPrice());
                    tblPresDrugDetails.refresh();
                    break;
                }
            }

            if (!isExists) {


                String selectedDrugCode = cmbPresDrugCode.getSelectionModel().getSelectedItem();

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ViewStockForm.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Stage secondaryStage = new Stage();
                    secondaryStage.setScene(scene);
                    secondaryStage.centerOnScreen();
                    secondaryStage.setResizable(false);
                    secondaryStage.initModality(Modality.WINDOW_MODAL);
                    ViewStockFormController ctrl = fxmlLoader.getController();
                    ctrl.getDrugCode(selectedDrugCode);

                    secondaryStage.showAndWait();

                    StockTM selectedItem = ctrl.tblStockDetails.getSelectionModel().getSelectedItem();
                    if (selectedItem == null) {
                        return;
                    }
                    lblBatch.setText(selectedItem.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                enablePlaceOrderButton();

                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                nf.setMinimumFractionDigits(2);
                nf.setGroupingUsed(false);

                double total = qty *unitPrice;
                String formattedTotal = nf.format(total);


                JFXButton btnDelete = new JFXButton("Delete");
              //  ObservableList<String> styleClass = btnDelete.getStyleClass();

                DrugItemDetailTM2 drugDetailTM = new DrugItemDetailTM2(
                        cmbPresDrugCode.getSelectionModel().getSelectedItem(),
                        lblBatch.getText(),
                        txtPresDrugName.getText(),
                        qty,
                        Double.parseDouble(nf.format(unitPrice)),
                        Double.parseDouble(formattedTotal),
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
                        tblPresDrugDetails.getItems().remove(drugDetailTM);
                        calculateTotal();
                        enablePlaceOrderButton();
                        txtPresDrugName.requestFocus();
                        tblPresDrugDetails.getSelectionModel().clearSelection();
                    }
                });

                details.add(drugDetailTM);
                tempItems.add(drugDetailTM);
                System.out.println(tempItems);
            }
            updateQty(cmbPresDrugCode.getSelectionModel().getSelectedItem(), qty);
            lblRate.setText("2");
            calculateTotal();
            enablePlaceOrderButton();

            txtPresDrugName.clear();
            lblBatch.setText("");
            txtPresDrugQty.clear();
            txtPresDrugQtyIn.clear();
            txtPresDrugUnitPrice.clear();
            cmbPresDrugCode.getSelectionModel().clearSelection();

        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input please double check the highlighted text").show();
        }

        if (!validQty) {
            txtPresDrugQty.requestFocus();
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
        ObservableList<DrugItemDetailTM2> details = tblPresDrugDetails.getItems();

        double total = 0;
        for (DrugItemDetailTM2 detail : details) {
            total += detail.getTotal();
        }

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setGroupingUsed(false);

        lblPresTotal.setText(nf.format(total));
        netTotal = total;
    }

    public void btnPresViewDrug_OnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ViewAllDrugForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.centerOnScreen();
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.showAndWait();
            ViewAllDrugFormController ctrl = fxmlLoader.getController();
            DrugItemTM selectedItem = ctrl.tblDrugDetails.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            cmbPresDrugCode.setValue(selectedItem.getCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnPrintSearched(ActionEvent actionEvent) throws JRException {
        if (tblPrescriptionDetails.getItems().size() == 0) {
            new Alert(Alert.AlertType.ERROR,
                    "PLEASE ENTER CORRECT DETAILS",
                    ButtonType.OK).show();
        } else {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pharmacy/report/PharmacyIssuedPrescriptionReport.jasper"));
            Map<String, Object> params = new HashMap<>();
            params.put("searchValue", txtPresSearch.getText());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        }
    }

    public void btnPresMakePayment_OnAction(ActionEvent actionEvent) throws IOException {

        String selectedPatient = cmbPresPatientNic.getSelectionModel().getSelectedItem();
        String selectedDoctor = cmbPresDocCode.getSelectionModel().getSelectedItem();
        String presId = txtPresRefNo.getText();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/payment/ManagePaymentPrescriptionForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.centerOnScreen();
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            ManagePaymentPrescriptionFormController ctrl = fxmlLoader.getController();
            ctrl.getPrescriptionPaymentDetails(presId, selectedPatient, selectedDoctor, netTotal, tempItems);

            secondaryStage.showAndWait();

            clearFields();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearFields() {

        cmbPresPatientNic.getSelectionModel().clearSelection();
        txtPresPatientName.clear();

        cmbPresDocCode.getSelectionModel().clearSelection();
        txtPresDoctorName.clear();

        tblPresDrugDetails.getItems().clear();

        lblPresTotal.setText("0.00");

        generateNewPrescriptionId();
        try {
            cmbPresPatientNic.getItems().clear();
            cmbPresDocCode.getItems().clear();
            cmbPresDrugCode.getItems().clear();
            List<String> ids = patientBO.getAllPatientIDs();
            cmbPresPatientNic.setItems(FXCollections.observableArrayList(ids));

            List<String> drugItemIds = drugItemBO.getAllDrugItemIds();
            cmbPresDrugCode.setItems(FXCollections.observableArrayList(drugItemIds));

            List<String> doctorIds = doctorBO.getAllDoctorIds();
            cmbPresDocCode.setItems(FXCollections.observableArrayList(doctorIds));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }

        tblPrescriptionDetails.getItems().clear();
        loadTable();
        btnMakePayment.setDisable(true);
        cmbPresDocCode.requestFocus();
        details.clear();
        tempItems.clear();
        System.out.println(details);

    }

    public void btnOrderAddNewPres_OnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void txtQty_OnAction(ActionEvent actionEvent) {
        if (!btnAdd.isDisable()) {
            btnPresDrugAdd_OnAction(actionEvent);
        }
    }

    public void btnOrderViewDrug_OnAction(ActionEvent actionEvent) {
    }

    public void banClearAllFields_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to clear all fields ?", ButtonType.OK);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            cmbPresPatientNic.getSelectionModel().clearSelection();
            txtPresPatientName.clear();

            cmbPresDocCode.getSelectionModel().clearSelection();
            txtPresDoctorName.clear();

            tblPresDrugDetails.getItems().clear();

            cmbPresDrugCode.getSelectionModel().clearSelection();
            txtPresDrugQty.clear();
            txtPresDrugQtyIn.clear();
            txtPresDrugUnitPrice.clear();
            txtPresDrugName.clear();
            txtPresSearch.clear();

            lblPresTotal.setText("0.00");
            txtPresRefNo.clear();
            btnMakePayment.setDisable(true);
            lblRate.setText("2");
            lblBatch.setText("");
            details.clear();
            tempItems.clear();
            generateNewPrescriptionId();
            System.out.println(details);


        }
    }

    public void generateNewPrescriptionId() {
        int maxId = 0;

        try {
            String lastPresId = prescriptionBO.getLastPrescriptionId();

            if (lastPresId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastPresId.replace("PRES", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "PRES000" + maxId;
            } else if (maxId < 100) {
                id = "PRES00" + maxId;
            } else if (maxId < 1000) {
                id = "PRES0" + maxId;
            } else {
                id = "PRES" + maxId;
            }
            txtPresRefNo.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    private void enablePlaceOrderButton() {
        String selectedDoctor = cmbPresDocCode.getSelectionModel().getSelectedItem();
        String selectedPatient = cmbPresPatientNic.getSelectionModel().getSelectedItem();
        int size = tblPresDrugDetails.getItems().size();
        if (selectedDoctor == null || selectedPatient == null || size == 0) {
            btnMakePayment.setDisable(true);
        } else {
            btnMakePayment.setDisable(false);
        }
    }

    public void btnPresViewDoctor_OnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ViewAllDoctorForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.centerOnScreen();
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.showAndWait();
            ViewAllDoctorFormController ctrl = fxmlLoader.getController();
            DoctorTM selectedItem = ctrl.tblDoctorDetails.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            cmbPresDocCode.setValue(selectedItem.getNic());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnPresViewPatient_OnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ViewAllPatientForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.centerOnScreen();
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.showAndWait();
            ViewAllPatientFormController ctrl = fxmlLoader.getController();
            PatientTM selectedItem = ctrl.tblPatientDetails.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            cmbPresPatientNic.setValue(selectedItem.getNic());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnPresViewStocks_OnAction(ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ViewStockForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.centerOnScreen();
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            ViewStockFormController ctrl = fxmlLoader.getController();

            secondaryStage.showAndWait();

            StockTM selectedItem = ctrl.tblStockDetails.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            lblBatch.setText(selectedItem.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tblDrugDetails_OnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            DrugItemDetailTM2 selectedDrugCode = tblPresDrugDetails.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ViewStockForm.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage secondaryStage = new Stage();
                secondaryStage.setScene(scene);
                secondaryStage.centerOnScreen();
                secondaryStage.setResizable(false);
                secondaryStage.initModality(Modality.WINDOW_MODAL);
                ViewStockFormController ctrl = fxmlLoader.getController();
                ctrl.getDrugCode(selectedDrugCode.getCode());
                secondaryStage.showAndWait();

                StockTM selectedItem = ctrl.tblStockDetails.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    return;
                }
                lblBatch.setText(selectedItem.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void tblPrescriptionDetails_OnMouseClicked(MouseEvent mouseEvent) throws JRException {
        if (mouseEvent.getClickCount() == 2) {

            PrescriptionInfoTM selectedItem = tblPrescriptionDetails.getSelectionModel().getSelectedItem();

            if(selectedItem == null){
                return;
            }


            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pharmacy/report/PharmacyIssuedPrescriptionBill.jasper"));
            Map<String, Object> params = new HashMap<>();
            params.put("prescriptionId", selectedItem.getId());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        }
    }

    public void btnRateOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/ManageRatesForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.centerOnScreen();
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            ManageRatesFormController ctrl = fxmlLoader.getController();
            secondaryStage.showAndWait();

            String text = ctrl.lblRate.getText();
            lblRate.setText(text);
//            System.out.println(text);
            rateValue = Integer.parseInt(text);
//            lblBatch.setText(selectedItem.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getRate(int rate){
        System.out.println("rateValue : "+ rateValue);
        System.out.println("rate : "+ rate);
    }

}
