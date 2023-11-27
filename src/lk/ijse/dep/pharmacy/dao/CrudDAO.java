package lk.ijse.dep.pharmacy.dao;

import lk.ijse.dep.pharmacy.entity.SuperEntity;

import java.util.List;

public interface CrudDAO<T extends SuperEntity, ID> extends SuperDAO {
    public abstract List<T> findAll() throws Exception;

    public abstract T find(ID id) throws Exception;

    public abstract boolean save(T entity) throws Exception;

    public abstract boolean update(T entity) throws Exception;

    public abstract boolean delete(ID id) throws Exception;

    public abstract List<T> search(ID query) throws Exception;
}
