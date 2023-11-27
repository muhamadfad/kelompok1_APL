package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.GrnDetail;
import lk.ijse.dep.pharmacy.entity.GrnDetailPK;

public interface GrnDetailDAO extends CrudDAO<GrnDetail, GrnDetailPK> {
    boolean existsByDrugCode(String drugCode) throws Exception;
}
