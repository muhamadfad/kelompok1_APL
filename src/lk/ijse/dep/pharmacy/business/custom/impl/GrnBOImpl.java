package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.GrnBO;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.GrnDAO;
import lk.ijse.dep.pharmacy.dao.custom.OrderDAO;
import lk.ijse.dep.pharmacy.dao.custom.QueryDAO;
import lk.ijse.dep.pharmacy.dto.GrnDTO2;
import lk.ijse.dep.pharmacy.dto.GrnDetailDTO2;
import lk.ijse.dep.pharmacy.dto.OrderDTO2;
import lk.ijse.dep.pharmacy.entity.CustomEntity;
import lk.ijse.dep.pharmacy.entity.CustomEntity4;
import lk.ijse.dep.pharmacy.entity.CustomEntity5;

import java.util.ArrayList;
import java.util.List;

public class GrnBOImpl implements GrnBO {

    private GrnDAO grnDAO = DAOFactory.getInstance().getDAO(DAOTypes.GRN);
    private QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);


    @Override
    public String getLastGrnId() throws Exception {
        return grnDAO.getLastGrnId();
    }

    @Override
    public List<GrnDTO2> getGrnInfo(String query) throws Exception {
        List<CustomEntity4> grnInfo = queryDAO.getGrnInfo(query + "%");
        List<GrnDTO2> dtos = new ArrayList<>();

        for (CustomEntity4 info : grnInfo) {
            dtos.add(new GrnDTO2(info.getGrnId(), info.getOrderId(), info.getOrderDate(),
                    info.getSupplierId()));
        }
        return dtos;
    }

    @Override
    public List<GrnDetailDTO2> getGrnDetailInfo(String grnId) throws Exception {
        List<CustomEntity5> grnInfo = queryDAO.getGrnDetailInfo(grnId);
        List<GrnDetailDTO2> dtos = new ArrayList<>();

        for (CustomEntity5 info : grnInfo) {
            dtos.add(new GrnDetailDTO2(info.getGrnId(), info.getDrugCode(), info.getDrugDes(),
                    info.getQty(),info.getUnitPrice()));
        }
        return dtos;
    }
}
