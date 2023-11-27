package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.QueryDAO;
import lk.ijse.dep.pharmacy.entity.CustomEntity;
import lk.ijse.dep.pharmacy.entity.CustomEntity2;
import lk.ijse.dep.pharmacy.entity.CustomEntity4;
import lk.ijse.dep.pharmacy.entity.CustomEntity5;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class QueryDAOImpl implements QueryDAO {
    @Override
    public CustomEntity getOrderInfo(String orderId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT S.supplierId, S.supplierName, O.orderDate  FROM Supplier S INNER JOIN `Order` O ON S.supplierId=O.supplierId WHERE O.orderId=?", orderId);
        if (rst.next()){
            return new CustomEntity(orderId,
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3));
        }else{
            return null;
        }
    }

    @Override
    public CustomEntity getOrderInfo2(String orderId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT O.orderId, S.supplierId, O.orderDate, OD.drugCode, OD.quantity , OD.unitPrice FROM Supplier S INNER JOIN `Order` O ON S.supplierId=O.supplierId INNER JOIN OrderDetails OD on O.orderId = OD.orderId WHERE O.orderId=?", orderId);
        if (rst.next()){
            return new CustomEntity(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getDouble(5));
        }else{
            return null;
        }
    }

    @Override
    public List<CustomEntity> getOrdersInfo(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT O.orderId, S.supplierId, S.supplierName, O.orderDate, SUM(OD.quantity * OD.unitPrice) AS Total  FROM Supplier S INNER JOIN `Order` O ON S.supplierId=O.supplierId " +
                "INNER JOIN OrderDetails OD on O.orderId = OD.orderId WHERE O.orderId LIKE ? OR S.supplierId LIKE ? OR S.supplierName LIKE ? OR O.orderDate LIKE ? GROUP BY O.orderId", query,query,query,query);
        List<CustomEntity> al = new ArrayList<>();
        while (rst.next()){
            al.add(new CustomEntity(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getDouble(5)));
        }
        return al;
    }

    @Override
    public List<CustomEntity2> getPrescriptionsInfo(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT P.prescriptionId, P.patientId,P2.patientName,D.doctorName, P.prescriptionDate, SUM(PD.quantity * PD.unitPrice) AS Total FROM Prescription P " +
                "INNER JOIN Patient P2 on P.patientId = P2.patientId INNER JOIN Doctor D ON P.doctorId = D.doctorId INNER JOIN PrescriptionDetails PD on P.prescriptionId = PD.prescriptionId " +
                "WHERE P.prescriptionId LIKE ? OR P.patientId LIKE ? OR P2.patientName LIKE ? OR D.doctorName LIKE ? OR P.prescriptionDate LIKE ? GROUP BY P.prescriptionId", query,query,query,query,query);
        List<CustomEntity2> al = new ArrayList<>();
        while (rst.next()){
            al.add(new CustomEntity2(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDouble(6)));
        }
        return al;
    }

    @Override
    public List<CustomEntity4> getGrnInfo(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT G.grnId, O.orderId, O.orderDate, O.supplierId FROM Grn G INNER JOIN `Order` O on G.orderId = O.orderId " +
                "WHERE G.grnId LIKE ? OR O.orderId LIKE ? OR O.orderDate LIKE ? OR O.supplierId LIKE ?", query,query,query,query);
        List<CustomEntity4> al = new ArrayList<>();
        while (rst.next()){
            al.add(new CustomEntity4(rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4)));
        }
        return al;
    }

    @Override
    public List<CustomEntity5> getGrnDetailInfo(String grnId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT G.grnId, G.drugCode, DI.drugDes,G.qty, G.unitPrice FROM GrnDetails G INNER JOIN DrugItem DI on G.drugCode = DI.drugCode WHERE G.grnId = ?", grnId);
        List<CustomEntity5> al = new ArrayList<>();
        while (rst.next()){
            al.add(new CustomEntity5(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)));
        }
        return al;
    }
}
