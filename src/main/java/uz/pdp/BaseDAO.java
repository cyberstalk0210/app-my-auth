package uz.pdp;

import java.util.List;

public interface BaseDAO<T, ID> {

    //create, update, read, delete

    T save(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(ID id);

    T findById(ID id);

    List<T> findAll();

    List<T> findAll(int page, int size);
}
