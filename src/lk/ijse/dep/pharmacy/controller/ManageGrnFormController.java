package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.GrnBO;
import lk.ijse.dep.pharmacy.db.DBConnection;
import lk.ijse.dep.pharmacy.dto.GrnDTO2;
import lk.ijse.dep.pharmacy.dto.GrnDetailDTO2;
import lk.ijse.dep.pharmacy.util.GrnDetailTM;
import lk.ijse.dep.pharmacy.util.GrnTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ManageGrnFormController implements Initializable {
    public AnchorPane root;
    public TableView<GrnTM> tblGrn;
    public TableView<GrnDetailTM> tblGrnDetails;
    public JFXTextField txtGrnSearch;
    public JFXTextField txtGrnDetailSearch;

    private GrnBO grnBO = BOFactory.getInstance().getBO(BOTypes.GRN);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        loadTable();

        tblGrn.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("grnId"));
        tblGrn.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblGrn.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("oderDate"));
        tblGrn.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        tblGrnDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("grnId"));
        tblGrnDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblGrnDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("des"));
        tblGrnDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblGrnDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        txtGrnSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });

        tblGrn.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GrnTM>() {
            @Override
            public void changed(ObservableValue<? extends GrnTM> observable, GrnTM oldValue, GrnTM newValue) {
                GrnTM selectedItem = tblGrn.getSelectionModel().getSelectedItem();

                if(selectedItem == null){
                    return;
                }

                tblGrnDetails.getItems().clear();
                try {
                    List<GrnDetailDTO2> allGrnDetails = grnBO.getGrnDetailInfo(selectedItem.getGrnId());
                    ObservableList<GrnDetailTM> grnDetails = tblGrnDetails.getItems();
                    for (GrnDetailDTO2 g : allGrnDetails) {
                        grnDetails.add(new GrnDetailTM(g.getGrnId(), g.getCode(), g.getDrugDes(), g.getQty(),g.getUnitPrice()));
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                    Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
                }

            }
        });
    }

    public void loadTable() {
        tblGrn.getItems().clear();
        try {
            List<GrnDTO2> allGrn = grnBO.getGrnInfo(txtGrnSearch.getText());
            ObservableList<GrnTM> grns = tblGrn.getItems();
            for (GrnDTO2 g : allGrn) {
                grns.add(new GrnTM(g.getGrnId(), g.getOrderId(), g.getOrderDate().toString(), g.getSupplierId()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void btnPrintSearchedGrn(ActionEvent actionEvent) throws JRException {
        if (tblGrn.getItems().size() == 0) {
            new Alert(Alert.AlertType.ERROR,
                    "PLEASE ENTER CORRECT DETAILS",
                    ButtonType.OK).show();
        } else {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pharmacy/report/PharmacyGrnReport.jasper"));
            Map<String, Object> params = new HashMap<>();
            params.put("searchValue", txtGrnSearch.getText());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        }
    }

    public void btnPrintSearchedGrnDetails(ActionEvent actionEvent) {
    }

    public void btnGenerateSummaryReport(ActionEvent actionEvent) throws JRException {
        GrnTM selectedItem = tblGrn.getSelectionModel().getSelectedItem();

        if(selectedItem == null){
            return;
        }

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/pharmacy/report/PharmacyGrnDetailReport.jasper"));
        Map<String, Object> params = new HashMap<>();
        params.put("searchValue",selectedItem.getGrnId());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }
}
