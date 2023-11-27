package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.BatchBO;
import lk.ijse.dep.pharmacy.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.BatchDAO;
import lk.ijse.dep.pharmacy.dao.custom.StockDAO;
import lk.ijse.dep.pharmacy.dto.BatchDTO;
import lk.ijse.dep.pharmacy.entity.Batch;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class BatchBOImpl implements BatchBO {

    private BatchDAO batchDAO = DAOFactory.getInstance().getDAO(DAOTypes.BATCH);
    private StockDAO stockDAO = DAOFactory.getInstance().getDAO(DAOTypes.STOCK);

    @Override
    public boolean saveBatch(BatchDTO batch) throws Exception {
        return batchDAO.save(new Batch(batch.getId(), batch.getDes(), batch.getExpDate(), batch.isStatus()));
    }

    @Override
    public boolean updateBatch(BatchDTO batch) throws Exception {
        return batchDAO.update(new Batch(batch.getId(), batch.getDes(), batch.getExpDate(), batch.isStatus()));
    }

    @Override
    public boolean deleteBatch(String batchId) throws Exception {
        if (stockDAO.existsByBatchId(batchId)) {
            throw new AlreadyExistsInOrderException("Batch already exists in an Stock, hence unable to delete");
        }
        return batchDAO.delete(batchId);
    }

    @Override
    public List<BatchDTO> findAllBatch() throws Exception {
        List<Batch> allBatches = batchDAO.findAll();
        List<BatchDTO> dtos = new ArrayList<>();

        for (Batch batch : allBatches) {
            dtos.add(new BatchDTO(batch.getBatchId(), batch.getBatchDes(), batch.getBatchExpireDate(), batch.isBatchStatus()));
        }
        return dtos;
    }

    @Override
    public String getLastBatchId() throws Exception {
        return batchDAO.getLastBatchId();
    }

    @Override
    public BatchDTO findBatch(String batchId) throws Exception {
        Batch batch = batchDAO.find(batchId);
        return new BatchDTO(batch.getBatchId(), batch.getBatchDes(), batch.getBatchExpireDate(), batch.isBatchStatus());
    }

    @Override
    public List<String> getAllBatchIds() throws Exception {
        List<Batch> batches = batchDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Batch batch : batches) {
            ids.add(batch.getBatchId());
        }
        return ids;
    }

    @Override
    public List<BatchDTO> searchBatch(String query) throws Exception {
        List<Batch> search = batchDAO.search(query + "%");
        List<BatchDTO> dtos = new ArrayList<>();

        for (Batch searchBatch : search) {
            dtos.add(new BatchDTO(searchBatch.getBatchId(), searchBatch.getBatchDes(), searchBatch.getBatchExpireDate(), searchBatch.isBatchStatus()));
        }
        return dtos;
    }

    @Override
    public List<String> getAllAvailableBatchIds() throws Exception {
        List<Batch> batches = batchDAO.findAvailableBatches();
        List<String> ids = new ArrayList<>();
        for (Batch batch : batches) {
            ids.add(batch.getBatchId());
        }
        return ids;
    }
}
