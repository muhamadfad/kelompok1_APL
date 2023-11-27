package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.DrugItemBO;
import lk.ijse.dep.pharmacy.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.DrugItemDAO;
import lk.ijse.dep.pharmacy.dao.custom.LocationDAO;
import lk.ijse.dep.pharmacy.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pharmacy.db.DBConnection;
import lk.ijse.dep.pharmacy.dto.DrugItemDTO;
import lk.ijse.dep.pharmacy.dto.DrugItemDTO2;
import lk.ijse.dep.pharmacy.entity.DrugItem;
import lk.ijse.dep.pharmacy.entity.Location;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class DrugItemBOImpl implements DrugItemBO {

    private DrugItemDAO drugItemDAO = DAOFactory.getInstance().getDAO(DAOTypes.DRUG_ITEM);
    private LocationDAO locationDAO = DAOFactory.getInstance().getDAO(DAOTypes.LOCATION);
    private OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_DETAIL);


    @Override
    public boolean saveDrugItem(DrugItemDTO2 drugItem) throws Exception {

        Connection connection = DBConnection.getInstance().getConnection();

        try {

            // Let's start a transaction
            connection.setAutoCommit(false);

            boolean result = drugItemDAO.save(new DrugItem(drugItem.getCode(), drugItem.getDescription(), drugItem.getManuDate(), drugItem.getExpireDate(),
                    drugItem.getQtyOnHand(), drugItem.getUnitPrice(), drugItem.getLocationId()));

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something, something went wrong");
            }


            result = locationDAO.update(new Location(drugItem.getLocationDetails().getId(), drugItem.getLocationDetails().getDes(), drugItem.getLocationDetails().isStatus()));

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
    public boolean updateDrugItem(DrugItemDTO drugItem) throws Exception {
        return drugItemDAO.update(new DrugItem(drugItem.getCode(), drugItem.getDescription(), drugItem.getManuDate(), drugItem.getExpireDate(),
                drugItem.getQtyOnHand(), drugItem.getUnitPrice(), drugItem.getLocationId()));
    }

    @Override
    public boolean deleteDrugItem(String drugItemId) throws Exception {
        if (orderDetailDAO.existsByDrugCode(drugItemId)) {
            throw new AlreadyExistsInOrderException("Drug Item already exists in an Order Details, hence unable to delete");
        }
        return drugItemDAO.delete(drugItemId);
    }

    @Override
    public List<DrugItemDTO> findAllDrugItems() throws Exception {
        List<DrugItem> alDrugItem = drugItemDAO.findAll();
        List<DrugItemDTO> dtos = new ArrayList<>();

        for (DrugItem drugItem : alDrugItem) {
            dtos.add(new DrugItemDTO(drugItem.getDrugCode(), drugItem.getDrugDes(), drugItem.getDrugManufactureDate(), drugItem.getDrugExpireDate(), drugItem.getDrugQtyOnHand(),
                    drugItem.getDrugUnitPrice(), drugItem.getLocationId()));
        }
        return dtos;
    }

    @Override
    public String getLastDrugItemId() throws Exception {
        return drugItemDAO.getLastDrugCode();
    }

    @Override
    public DrugItemDTO findDrugItem(String drugItemId) throws Exception {
        DrugItem drugItem = drugItemDAO.find(drugItemId);
        return new DrugItemDTO(drugItem.getDrugCode(), drugItem.getDrugDes(), drugItem.getDrugManufactureDate(), drugItem.getDrugExpireDate(), drugItem.getDrugQtyOnHand(),
                drugItem.getDrugUnitPrice(), drugItem.getLocationId());
    }

    @Override
    public List<String> getAllDrugItemIds() throws Exception {
        List<DrugItem> drugItems = drugItemDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (DrugItem drugItem : drugItems) {
            ids.add(drugItem.getDrugCode());
        }
        return ids;
    }

    @Override
    public List<DrugItemDTO> searchDrugItem(String query) throws Exception {
        List<DrugItem> search = drugItemDAO.search(query + "%");
        List<DrugItemDTO> dtos = new ArrayList<>();

        for (DrugItem searchDrugItems : search) {
            dtos.add(new DrugItemDTO(searchDrugItems.getDrugCode(), searchDrugItems.getDrugDes(), searchDrugItems.getDrugManufactureDate(),
                    searchDrugItems.getDrugExpireDate(), searchDrugItems.getDrugQtyOnHand(), searchDrugItems.getDrugUnitPrice(), searchDrugItems.getLocationId()));
        }
        return dtos;
    }
}
