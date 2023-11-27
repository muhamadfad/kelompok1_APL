package lk.ijse.dep.pharmacy.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import lk.ijse.dep.pharmacy.business.BOFactory;
import lk.ijse.dep.pharmacy.business.BOTypes;
import lk.ijse.dep.pharmacy.business.custom.SupplierBO;
import lk.ijse.dep.pharmacy.dto.SupplierDTO;
import lk.ijse.dep.pharmacy.util.SupplierTM;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class ViewAllSupplierFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtSupplierSearch;
    public TableView<SupplierTM> tblSupplierDetails;

    private SupplierBO supplierBO = BOFactory.getInstance().getBO(BOTypes.SUPPLIER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        tblSupplierDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblSupplierDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblSupplierDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblSupplierDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("mobile"));
        tblSupplierDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("email"));

        loadTable();

        txtSupplierSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });

        tblSupplierDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SupplierTM>() {
            @Override
            public void changed(ObservableValue<? extends SupplierTM> observable, SupplierTM oldValue, SupplierTM newValue) {
                SupplierTM selectedItem = tblSupplierDetails.getSelectionModel().getSelectedItem();

                System.out.println(selectedItem.getId());
            }
        });

    }

    public void loadTable() {
        tblSupplierDetails.getItems().clear();
        try {
            List<SupplierDTO> allSuppliers = supplierBO.searchSupplier(txtSupplierSearch.getText());
            ObservableList<SupplierTM> suppliers = tblSupplierDetails.getItems();
            for (SupplierDTO s : allSuppliers) {
                suppliers.add(new SupplierTM(s.getId(), s.getName(), s.getAddress(), s.getMobile(), s.getEmail()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
        }
    }

    public void tblSupplierDetails_OnMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {

            URL resource = this.getClass().getResource("/lk/ijse/dep/pharmacy/view/supplier/ManageSupplierOrderForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent root = fxmlLoader.load();
//            Scene placeOrderScene = new Scene(root);
//            Stage secondaryStage = new Stage();
//            secondaryStage.setScene(placeOrderScene);
//            secondaryStage.centerOnScreen();
//            secondaryStage.setResizable(false);

//            ManageSupplierOrderFormController ctrl = fxmlLoader.getController();
//            SupplierTM selectedItem = tblSupplierDetails.getSelectionModel().getSelectedItem();
//            ctrl.getSelectedDetails(selectedItem.getId());

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.close();

//            Window stage2 = this.root.getScene().getWindow();
//            stage2.hide();
//            secondaryStage.show();
        }
    }
}
