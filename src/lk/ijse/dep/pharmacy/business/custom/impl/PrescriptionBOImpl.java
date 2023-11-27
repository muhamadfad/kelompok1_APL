package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.PrescriptionBO;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.*;
import lk.ijse.dep.pharmacy.db.DBConnection;
import lk.ijse.dep.pharmacy.dto.*;
import lk.ijse.dep.pharmacy.entity.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PrescriptionBOImpl implements PrescriptionBO {

    private PrescriptionDAO prescriptionDAO  = DAOFactory.getInstance().getDAO(DAOTypes.PRESCRIPTION);
    private PrescriptionDetailDAO prescriptionDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.PRESCRIPTION_DETAIL);
    private StockDAO stockDAO = DAOFactory.getInstance().getDAO(DAOTypes.STOCK);
    private BatchDAO batchDAO = DAOFactory.getInstance().getDAO(DAOTypes.BATCH);
    private DrugItemDAO drugItemDAO = DAOFactory.getInstance().getDAO(DAOTypes.DRUG_ITEM);
    private GrnDAO grnDAO = DAOFactory.getInstance().getDAO(DAOTypes.GRN);
    private GrnDetailDAO grnDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.GRN_DETAIL);
    private PaymentDAO paymentDAO = DAOFactory.getInstance().getDAO(DAOTypes.PAYMENT);
    private QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);

    @Override
    public String getLastPrescriptionId() throws Exception {
        return prescriptionDAO.getLastPrescriptionId();
    }

    @Override
    public boolean placeOrderPrescription(PrescriptionDTO3 prescription) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        try {

            // Let's start a transaction
            connection.setAutoCommit(false);

            String presId = prescription.getId();
            boolean result = prescriptionDAO.save(new Prescription(presId, new java.sql.Date(new Date().getTime()),
                    prescription.getDoctorId(),prescription.getPatientId()));

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something, something went wrong");
            }


//            String bId = order.getBatchId();
//            for (StockDTO stock : order.getStock()) {
//                result = stockDAO.save(new Stock(bId, stock.getCode(), stock.getQty(), stock.getUnitPrice()));
//
//                if (!result) {
//                    connection.rollback();
//                    throw new RuntimeException("Something, something went wrong");
//                }
//
//
//                Batch batch = batchDAO.find(bId);
//                result = batchDAO.update(new Batch(bId,batch.getBatchDes(),batch.getBatchExpireDate(),false));
//
//                if (!result) {
//                    connection.rollback();
//                    throw new RuntimeException("Something, something went wrong");
//                }
//            }

            for (PrescriptionDetailDTO prescriptionDetail : prescription.getPrescriptionDetail()) {
                result = prescriptionDetailDAO.save(new PrescriptionDetail(presId, prescriptionDetail.getCode(),
                        prescriptionDetail.getQty(), prescriptionDetail.getUnitPrice()));

                if (!result) {
                    connection.rollback();
                    throw new RuntimeException("Something, something went wrong");
                }

//                DrugItem drugItem = drugItemDAO.find(orderDetail.getCode());
//                result = drugItemDAO.update(new DrugItem(drugItem.getDrugCode(), drugItem.getDrugDes(), drugItem.getDrugManufactureDate(), drugItem.getDrugExpireDate(),
//                        orderDetail.getQty(), orderDetail.getUnitPrice(), drugItem.getLocationId()));
//
//                if (!result) {
//                    connection.rollback();
//                    throw new RuntimeException("Something, something went wrong");
//                }

//                result = grnDetailDAO.save(new GrnDetail(,orderDetail.getCode(),orderDetail.getQty(),orderDetail.getUnitPrice()));
//
//                if (!result) {
//                    connection.rollback();
//                    throw new RuntimeException("Something, something went wrong");
//                }
                DrugItem item = drugItemDAO.find(prescriptionDetail.getCode());
                item.setDrugQtyOnHand(item.getDrugQtyOnHand() - prescriptionDetail.getQty());
                result = drugItemDAO.update(item);

                if (!result) {
                    connection.rollback();
                    throw new RuntimeException("Something, something went wrong");
                }
            }

            for (StockDTO2 stockDetail : prescription.getStockDetails()) {
                Stock stock = stockDAO.find(new StockPK(stockDetail.getId(), stockDetail.getCode()));
                System.out.println(stock);
                stock.setDrugUnitPrice(stockDetail.getUnitPrice());
                stock.setDrugQty((stock.getDrugQty() - stockDetail.getQty()));
                result = stockDAO.update(stock);

                if (!result) {
                    connection.rollback();
                    throw new RuntimeException("Something, something went wrong");
                }
            }

            result = paymentDAO.save(new Payment(prescription.getPaymentDTO().getId(),prescription.getPaymentDTO().getDate(),
                    prescription.getPaymentDTO().getType(),presId, prescription.getPaymentDTO().getOrderId(),prescription.getPaymentDTO().getTotal()));

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something, something went wrong");
            }

            connection.commit();
            return true;

        } catch (Throwable e) {

            try {
                connection.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<PrescriptionDTO2> getPrescriptionInfo(String query) throws Exception {
        List<CustomEntity2> prescriptionsInfo = queryDAO.getPrescriptionsInfo(query + "%");
        List<PrescriptionDTO2> dtos = new ArrayList<>();

        for (CustomEntity2 info : prescriptionsInfo) {
            dtos.add(new PrescriptionDTO2(info.getPrescriptionId(), info.getPatientNic(), info.getPatientName(),info.getDoctorName(),
                    info.getPrescriptionDate(),info.getTotal()));
        }
        return dtos;
    }

    @Override
    public PrescriptionDTO findPrescription(String presId) throws Exception {
        Prescription prescription = prescriptionDAO.find(presId);
        return new PrescriptionDTO(prescription.getPrescriptionId(), prescription.getPatientId(), prescription.getPrescriptionDate(),prescription.getDoctorId());
    }

    @Override
    public List<PrescriptionDetailDTO> findPrescriptionDetails(String presId) throws Exception {
        List<PrescriptionDetail> prescriptionDetails = prescriptionDetailDAO.findPrescriptionDetailsByPresId(presId);
        List<PrescriptionDetailDTO> dtos = new ArrayList<>();
        for (PrescriptionDetail info : prescriptionDetails) {
            dtos.add(new PrescriptionDetailDTO(info.getPrescriptionDetailPK().getDrugCode(), info.getQuantity(), info.getUnitPrice()));
        }
        return dtos;
    }
}
