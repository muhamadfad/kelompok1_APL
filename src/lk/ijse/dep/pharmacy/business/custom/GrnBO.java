package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.GrnDTO2;
import lk.ijse.dep.pharmacy.dto.GrnDetailDTO2;

import java.util.List;

public interface GrnBO extends SuperBO {
    String getLastGrnId() throws Exception;

    List<GrnDTO2> getGrnInfo(String query) throws Exception;

    List<GrnDetailDTO2> getGrnDetailInfo(String grnId) throws Exception;
}
