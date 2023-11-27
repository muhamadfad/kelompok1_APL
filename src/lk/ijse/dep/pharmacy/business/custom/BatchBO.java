package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.BatchDTO;

import java.util.List;

public interface BatchBO extends SuperBO {
    boolean saveBatch(BatchDTO batch) throws Exception;

    boolean updateBatch(BatchDTO batch) throws Exception;

    boolean deleteBatch(String batchId) throws Exception;

    List<BatchDTO> findAllBatch() throws Exception;

    String getLastBatchId() throws Exception;

    BatchDTO findBatch(String batchId) throws Exception;

    List<String> getAllBatchIds() throws Exception;

    List<BatchDTO> searchBatch(String query) throws Exception;

    List<String> getAllAvailableBatchIds() throws Exception;
}
