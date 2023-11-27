package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.StockBO;
import lk.ijse.dep.pharmacy.business.custom.SupplierBO;
import lk.ijse.dep.pharmacy.dto.PatientDTO;
import lk.ijse.dep.pharmacy.dto.StockDTO2;
import lk.ijse.dep.pharmacy.util.PatientTM;
import lk.ijse.dep.pharmacy.util.StockTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ViewStockFormController implements Initializable {
    public AnchorPane root;
    public Label lblDate;
    public JFXTextField txtFindStock;
    public TableView<StockTM> tblStockDetails;

    private String drugCode;

    private StockBO stockBO = BOFactory.getInstance().getBO(BOTypes.STOCK);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        lblDate.setText(LocalDate.now() + "");

        tblStockDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblStockDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblStockDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblStockDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        loadTable();

        txtFindStock.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }

        });

    }
    public void getDrugCode(String drugCode){
        txtFindStock.setText(drugCode);
    }

    public void loadTable() {
        tblStockDetails.getItems().clear();
        try {
            List<StockDTO2> allStocks = stockBO.searchStock(txtFindStock.getText());
            ObservableList<StockTM> stocks = tblStockDetails.getItems();
            for (StockDTO2 s : allStocks) {
                stocks.add(new StockTM(s.getId(), s.getCode(), s.getQty(), s.getUnitPrice()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void tblStockDetails_OnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.hide();

        }
    }


}
