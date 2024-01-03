package carsharing.DAO;

import java.util.List;

public interface AbstractDao<T> {

    public List<T> getAll();

    public void create(T t);

    public void update(T t);

    public void delete(int ID);
}