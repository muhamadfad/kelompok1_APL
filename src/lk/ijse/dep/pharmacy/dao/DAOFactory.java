package lk.ijse.dep.pharmacy.dao;

import lk.ijse.dep.pharmacy.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoType) {
        switch (daoType) {
            case PATIENT:
                return (T) new PatientDAOImpl();
            case DOCTOR:
                return (T) new DoctorDAOImpl();
            case SUPPLIER:
                return (T) new SupplierDAOImpl();
            case ORDER:
                return (T) new OrderDAOImpl();
            case ORDER_DETAIL:
                return (T) new OrderDetailDAOImpl();
            case DRUG_ITEM:
                return (T) new DrugItemDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            case LOCATION:
                return (T) new LocationDAOImpl();
            case BATCH:
                return (T) new BatchDAOImpl();
            case STOCK:
                return (T) new StockDAOImpl();
            case GRN:
                return (T) new GrnDAOImpl();
            case GRN_DETAIL:
                return (T) new GrnDetailDAOImpl();
            case PAYMENT:
                return (T) new PaymentDAOImpl();
            case PRESCRIPTION:
                return (T) new PrescriptionDAOImpl();
            case PRESCRIPTION_DETAIL:
                return (T) new PrescriptionDetailDAOImpl();
            case USER:
                return (T) new UserDAOImpl();
            case USER_ROLE:
                return (T) new UserRoleDAOImpl();
            default:
                return null;
        }
    }
}
