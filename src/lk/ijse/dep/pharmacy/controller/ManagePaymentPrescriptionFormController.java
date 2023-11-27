package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.*;
import lk.ijse.dep.pharmacy.db.DBConnection;
import lk.ijse.dep.pharmacy.dto.*;
import lk.ijse.dep.pharmacy.util.DrugItemDetailTM2;
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
public class ManagePaymentPrescriptionFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtPaymentId;
    public Label lblDate;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtTotal;
    public JFXTextField txtVat;
    public JFXTextField txtNetTotal;
    public ImageView ImgVisa;
    public ImageView ImgCash;
    public AnchorPane aspPaymentStage;
    public Label lblPaymentType;
    public JFXButton btnPay;
    private List<DrugItemDetailTM2> tempItems = new ArrayList<>();
    private String supplierId;
    private String patientId;
    private String batchCode;
    private double total;
    private String orderId;
    private String doctorId;
    private String prescriptionId;

    private SupplierBO supplierBO = BOFactory.getInstance().getBO(BOTypes.SUPPLIER);
    private PatientBO patientBO = BOFactory.getInstance().getBO(BOTypes.PATIENT);
    private OrderBO orderBO = BOFactory.getInstance().getBO(BOTypes.ORDER);
    private PaymentBO paymentBO = BOFactory.getInstance().getBO(BOTypes.PAYMENT);
    private PrescriptionBO prescriptionBO = BOFactory.getInstance().getBO(BOTypes.PRESCRIPTION);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        lblDate.setText(LocalDate.now() + "");

        generatePaymentId();

        txtPaymentId.setDisable(true);
        txtId.setDisable(true);
        txtName.setDisable(true);
        txtTotal.setDisable(true);
        txtNetTotal.setDisable(true);
        btnPay.setDisable(true);


    }


    public void getPrescriptionPaymentDetails(String prescriptionId, String patientId, String doctorId, double total, List<DrugItemDetailTM2> orderDetails) {
        tempItems = orderDetails;
        this.patientId = patientId;
        this.prescriptionId = prescriptionId;
        this.doctorId = doctorId;
        this.total = total;

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setGroupingUsed(false);

        txtId.setText(patientId);
        txtTotal.setText(nf.format(total));
        try {
            PatientDTO patient = patientBO.findPatient(patientId);
            txtName.setText(patient.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getSupplierPaymentDetails(String selectedSupplierId, List<DrugItemDetailTM2> orderDetails, double total, String batchCode, String orderId) {

        tempItems = orderDetails;
        supplierId = selectedSupplierId;
        this.total = total;
        txtTotal.setText(Double.toString(total));
        this.batchCode = batchCode;
        this.orderId = orderId;
        txtId.setText(selectedSupplierId);
        try {
            SupplierDTO supplier = supplierBO.findSupplier(selectedSupplierId);
            txtName.setText(supplier.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void calculateNetTotal() {

        boolean validVat = txtVat.getText().matches("[0-9]{1,2}");

        if (validVat) {
            double netTotal = 0;

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            nf.setGroupingUsed(false);

            int vat = Integer.parseInt(txtVat.getText());

            int ans = (vat + 100);

            netTotal = total * ans / 100;
            txtNetTotal.setText(nf.format(netTotal));
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid VAT").show();
        }


    }

    public void cardPayment_OnMouseClicked(MouseEvent mouseEvent) {

    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
        }
    }

    public void cashPayment_OnMouseClicked(MouseEvent mouseEvent) {

    }

    public void btnPayBill(ActionEvent actionEvent) {

//            System.out.println(tempItems);
//
//            PaymentDTO payments = new PaymentDTO(txtPaymentId.getText(), java.sql.Date.valueOf(LocalDate.now()), lblPaymentType.getText(), null, null, Double.parseDouble(txtNetTotal.getText()));
//
//
//            List<OrderDetailDTO> orderDetails = new ArrayList<>();
//            for (DrugItemDetailTM item : tempItems) {
//                orderDetails.add(new OrderDetailDTO(item.getCode(), item.getQty(), item.getUnitPrice()));
//            }
//
//            List<StockDTO> stockDetails = new ArrayList<>();
//            for (DrugItemDetailTM item : tempItems) {
//                stockDetails.add(new StockDTO(item.getCode(), item.getQty(), item.getUnitPrice()));
//            }
//
//            OrderDTO order = new OrderDTO(orderId, batchCode, null, supplierId, orderDetails, stockDetails, payments);
//            try {
//                orderBO.placeOrder(order);
//
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
//                        "Your payment has done successfully", ButtonType.OK);
//                Optional<ButtonType> buttonType = alert.showAndWait();
//                if (buttonType.get() == ButtonType.OK) {
//                    try {
//                        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/supplier/ManageSupplierOrderForm.fxml"));
//                        fxmlLoader.load();
//                        ManageSupplierOrderFormController ctrl = fxmlLoader.getController();
//                        ctrl.clearFields();
//                        txtPaymentId.getScene().getWindow().hide();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            } catch (Exception e) {
//                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
//                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
//            }

        PaymentDTO payments = new PaymentDTO(txtPaymentId.getText(), java.sql.Date.valueOf(LocalDate.now()), lblPaymentType.getText(), null, null, Double.parseDouble(txtNetTotal.getText()));

        List<PrescriptionDetailDTO> prescriptionDetails = new ArrayList<>();
        for (DrugItemDetailTM2 item : tempItems) {
            prescriptionDetails.add(new PrescriptionDetailDTO(item.getCode(), item.getQty(), item.getUnitPrice()));
        }

        List<StockDTO2> stockDetails = new ArrayList<>();
        for (DrugItemDetailTM2 stock : tempItems) {
            stockDetails.add(new StockDTO2(stock.getBatchId(), stock.getCode(), stock.getQty(), stock.getUnitPrice()));
        }


        PrescriptionDTO3 prescription = new PrescriptionDTO3(prescriptionId, patientId, null, doctorId, prescriptionDetails, stockDetails,payments);

        try {
            prescriptionBO.placeOrderPrescription(prescription);



            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Your payment has done successfully", ButtonType.OK);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.OK) {
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pharmacy/report/PharmacyIssuedPrescriptionBill.jasper"));
                Map<String, Object> params = new HashMap<>();
                params.put("prescriptionId", prescriptionId);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, DBConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint, false);

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/supplier/ManageSupplierOrderForm.fxml"));
                    fxmlLoader.load();
                    ManageSupplierOrderFormController ctrl = fxmlLoader.getController();
                    ctrl.clearFields();
                    txtPaymentId.getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }


    }

    public void btnViewBill(ActionEvent actionEvent) {
    }

    public void calculateTotal_OnAction(ActionEvent actionEvent) {
        calculateNetTotal();
    }

    public void generatePaymentId() {
        int maxId = 0;

        try {
            String lastPaymentId = paymentBO.getLastPaymentId();

            if (lastPaymentId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastPaymentId.replace("PAY", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "PAY000" + maxId;
            } else if (maxId < 100) {
                id = "PAY00" + maxId;
            } else if (maxId < 1000) {
                id = "PAY0" + maxId;
            } else {
                id = "PAY" + maxId;
            }
            txtPaymentId.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void PaymentType_OnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "ImgCash":
                    enablePayButton();
                    lblPaymentType.setText("Cash");
                    try {
                        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/payment/PaymentMethodCashForm.fxml"));
                        aspPaymentStage.getChildren().clear();
                        aspPaymentStage.getChildren().add(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ImgVisa":
                    enablePayButton();
                    lblPaymentType.setText("Card");
                    try {
                        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pharmacy/view/payment/PaymentMethodCardForm.fxml"));
                        aspPaymentStage.getChildren().clear();
                        aspPaymentStage.getChildren().add(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }

    public void enablePayButton(){
        if(txtVat.getText() == null){
            return;
        }else {
            btnPay.setDisable(false);
        }
    }
}
