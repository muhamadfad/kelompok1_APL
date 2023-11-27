package lk.ijse.dep.pharmacy.controller;

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
import lk.ijse.dep.pharmacy.business.custom.LocationBO;
import lk.ijse.dep.pharmacy.business.custom.PaymentBO;
import lk.ijse.dep.pharmacy.db.DBConnection;
import lk.ijse.dep.pharmacy.dto.DrugItemDTO;
import lk.ijse.dep.pharmacy.dto.PaymentDTO;
import lk.ijse.dep.pharmacy.util.PaymentExpenseTM;
import lk.ijse.dep.pharmacy.util.PaymentIncomeTM;
import lk.ijse.dep.pharmacy.util.PaymentTM;
import lk.ijse.dep.pharmacy.util.StockDrugItemTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class PaymentViewAllFormController implements Initializable {
    public JFXTextField txtPaySearch;
    public TableView<PaymentTM> tblPaymentDetails;
    public AnchorPane root;
    public TableView<PaymentIncomeTM> tblIncomeDetails;
    public TableView<PaymentExpenseTM> tblExpensesDetails;
    public Label lblIncomeTotal;
    public Label lblExpenseTotal;
    public DatePicker dtpFrom;
    public DatePicker dtpTo;
    public Label lblDate;
    double income=0;
    double expense=0;
    private PaymentBO paymentBO = BOFactory.getInstance().getBO(BOTypes.PAYMENT);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        loadTable();

        tblPaymentDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblPaymentDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblPaymentDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblPaymentDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("presId"));
        tblPaymentDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblPaymentDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));

        tblExpensesDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblExpensesDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblExpensesDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("total"));

        tblIncomeDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblIncomeDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("presId"));
        tblIncomeDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("total"));

        txtPaySearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setGroupingUsed(false);



        ObservableList<PaymentIncomeTM> in = tblIncomeDetails.getItems();
        for (PaymentIncomeTM item : in) {
            income += item.getTotal();

        }

        lblIncomeTotal.setText("Total : "+nf.format(income));

        ObservableList<PaymentExpenseTM> exp = tblExpensesDetails.getItems();
        for (PaymentExpenseTM item : exp) {
            expense += item.getTotal();

        }

        lblExpenseTotal.setText("Total : "+nf.format(expense));
    }

    public void loadTable(){
        tblPaymentDetails.getItems().clear();
        try {
            List<PaymentDTO> allPayemnts = paymentBO.searchPayment(txtPaySearch.getText());
            ObservableList<PaymentTM> payment = tblPaymentDetails.getItems();
            for (PaymentDTO p : allPayemnts) {
                payment.add(new PaymentTM(p.getId(), p.getDate().toString(), p.getType(), p.getPresId(),p.getOrderId(),p.getTotal()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }

        try {
            List<PaymentDTO> allPayemnts = paymentBO.findAllOrderPayment();
            ObservableList<PaymentExpenseTM> payment = tblExpensesDetails.getItems();
            for (PaymentDTO p : allPayemnts) {
                payment.add(new PaymentExpenseTM(p.getId(),p.getOrderId(),p.getTotal()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }

        try {
            List<PaymentDTO> allPayemnts = paymentBO.findAllPresPayment();
            ObservableList<PaymentIncomeTM> payment = tblIncomeDetails.getItems();
            for (PaymentDTO p : allPayemnts) {
                payment.add(new PaymentIncomeTM(p.getId(),p.getPresId(),p.getTotal()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact IT TEAM").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }

    }

    public void btnPrintSearchedPay(ActionEvent actionEvent) throws JRException {

        if (tblPaymentDetails.getItems().size() == 0) {
            new Alert(Alert.AlertType.ERROR,
                    "PLEASE ENTER CORRECT DETAILS",
                    ButtonType.OK).show();
        } else {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pharmacy/report/PharmacyIssuedPaymentDetailReport.jasper"));
            Map<String, Object> params = new HashMap<>();
            params.put("searchValue", txtPaySearch.getText());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        }
    }

    public void btnPrintIncome(ActionEvent actionEvent) throws JRException {
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pharmacy/report/PharmacyIncomeReport.jasper"));
        Map<String, Object> params = new HashMap<>();
        params.put("total", income);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void btnPrintExpencesPay(ActionEvent actionEvent) throws JRException {
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pharmacy/report/PharmacyExpenseReport.jasper"));
        Map<String, Object> params = new HashMap<>();
        params.put("total", expense);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }
}
