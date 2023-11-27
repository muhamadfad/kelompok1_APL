package lk.ijse.dep.pharmacy.business;

import lk.ijse.dep.pharmacy.business.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getInstance(){
        return (boFactory == null)? (boFactory = new BOFactory()): boFactory;
    }

    public <T extends SuperBO> T getBO(BOTypes boTypes){
        switch (boTypes){
            case PATIENT:
                return (T) new PatientBOImpl();
            case DOCTOR:
                return (T) new DoctorBOImpl();
            case SUPPLIER:
                return (T) new SupplierBOImpl();
            case DRUG_ITEM:
                return (T) new DrugItemBOImpl();
            case ORDER:
                return (T) new OrderBOImpl();
            case LOCATION:
                return (T) new LocationBOImpl();
            case BATCH:
                return (T) new BatchBOImpl();
            case PAYMENT:
                return (T) new PaymentBOImpl();
            case PRESCRIPTION:
                return (T) new PrescriptionBOImpl();
            case STOCK:
                return (T) new StockBOImpl();
            case GRN:
                return (T) new GrnBOImpl();
            case USER:
                return (T) new UserBOImpl();
            default:
                return null;
        }
    }
}
