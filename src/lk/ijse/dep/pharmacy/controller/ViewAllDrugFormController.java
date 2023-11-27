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
import lk.ijse.dep.pharmacy.business.custom.DrugItemBO;
import lk.ijse.dep.pharmacy.dto.DrugItemDTO;
import lk.ijse.dep.pharmacy.util.DrugItemTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ViewAllDrugFormController implements Initializable {
    public Label lblDate;
    public JFXTextField txtDrugSearch;
    public TableView<DrugItemTM> tblDrugDetails;
    public AnchorPane root;

    private DrugItemBO drugItemBO = BOFactory.getInstance().getBO(BOTypes.DRUG_ITEM);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        lblDate.setText(LocalDate.now() + "");

        tblDrugDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblDrugDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblDrugDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblDrugDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblDrugDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("manuDate"));
        tblDrugDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("expireDate"));
        tblDrugDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("locationCode"));

        loadTable();

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

    public void tblDrugDetails_OnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.hide();

        }
    }


}
