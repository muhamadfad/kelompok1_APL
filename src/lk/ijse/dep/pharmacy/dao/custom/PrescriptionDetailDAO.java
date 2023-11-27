package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.PrescriptionDetail;
import lk.ijse.dep.pharmacy.entity.PrescriptionDetailPK;

import java.util.List;

public interface PrescriptionDetailDAO extends CrudDAO<PrescriptionDetail, PrescriptionDetailPK> {
    boolean existsByDrugCode(String drugCode) throws Exception;

    List<PrescriptionDetail> findPrescriptionDetailsByPresId(String presId) throws Exception;
}
