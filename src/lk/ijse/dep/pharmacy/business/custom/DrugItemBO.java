package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.DrugItemDTO;
import lk.ijse.dep.pharmacy.dto.DrugItemDTO2;

import java.util.List;

public interface DrugItemBO extends SuperBO {
    boolean saveDrugItem(DrugItemDTO2 drugItem)throws Exception;

    boolean updateDrugItem(DrugItemDTO drugItem)throws Exception;

    boolean deleteDrugItem(String drugItemId) throws Exception;

    List<DrugItemDTO> findAllDrugItems() throws Exception;

    String getLastDrugItemId()throws Exception;

    DrugItemDTO findDrugItem(String drugItemId) throws Exception;

    List<String> getAllDrugItemIds() throws Exception;

    List<DrugItemDTO> searchDrugItem(String query) throws Exception;

}
